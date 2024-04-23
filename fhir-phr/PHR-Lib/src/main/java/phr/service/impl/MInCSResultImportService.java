/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.MedicalOrganizationPatientAdapter;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationDefinitionJlac10Adapter;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.ObservationDefinitionJlac10Entity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.IMInCSResultImportService;

/**
 *
 * @author kis-note-025
 */
public class MInCSResultImportService implements IMInCSResultImportService {

//<editor-fold defaultstate="collapsed" desc="定数">
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MInCSResultImportService.class);

    //keyファイルに記載する解凍KEYのヘッダ
    private static final String ENCRIPTYKEY_INFO = "EncriptKey  :";

    /**
     * KEYファイル拡張子
     */
    private static final String KEYFILE_EXTENTION = "_key.txt";

    private static final int SEGMENT_INDEX = 0;
    private static final int PID_PATIENTID_INDEX = 3;
    private static final int OBR_ORDERID_INDEX = 2;
    private static final int OBR_EXAMINATIONDATE_INDEX = 22;
    private static final int ORC_MEDICALCD_INDEX = 21;
    private static final int OBX_OBSERVATIONIDENTIFER_INDEX = 3;
    private static final int ITEM_CD_INDEX = 0;
    private static final int OBX_VALUE_INDEX = 5;
    private static final int OBX_REFERENCERANGE_INDEX = 7;
    private static final int MINCS_DATA_INPUT_TYPE = 1;

//</editor-fold>
    private List<ObservationDefinitionJlac10Entity> ObservationJLac10List;

    /**
     * 指定したファイルの検査結果を
     * @param laboCd
     * @param fileList
     * @return
     * @throws IOException 
     */
    @Override
    public List<String> importWebUploadFiles(String laboCd, List<File> fileList) throws IOException {

        List<String> list = new ArrayList<>();
        for (File file : fileList) {
            List<String> errorList = new ArrayList<>();
            InsertUpdateMincsResult(laboCd, file, errorList);
            list.addAll(errorList);
        }
        return list;
    }

    /**
     * 検査結果の登録
     */
    @Override
    public void ImportSpecimenResult() {

        //MInCSからの送信データパスを設定ファイルから取得する。
        String rootPath = PhrConfig.getConfigProperty(ConfigConst.MINCS_DATA_PATH);

        // SFTPのルートディレクトリより検査会社のディレクトリを取得
        File rootDir = new File(rootPath);
        File[] laboDirs = rootDir.listFiles();

        for (File laboDir : laboDirs) {
            String laboCd = laboDir.getName();
            File dir = new File(laboDir, laboCd);
            if (!dir.exists()) {
                continue;
            }

            //転送完了フラグ(.fin)ファイルリストを取得
            List<File> finList = Arrays.asList(dir.listFiles(new finFlagFiltter()));
            //検査結果圧縮ファイルリストの取得
            List<File> zipList = Arrays.asList(dir.listFiles(new zipFiltter()));

            //解凍可能なZipファイルの検索
            List<File> targetZipFileList = searchDecompressFilePath(zipList, finList);

            List<String> removeTargetList = new ArrayList<>();

            //Zipファイルの解凍
            targetZipFileList.stream().forEach((File fi) -> {
                try {
                    ReadSpecimenInfoFile(laboCd, fi);
                    logger.debug(fi.getName());
                    //削除対象一覧についか
                    removeTargetList.add(getNameWithOutExtension(fi));
                } catch (IOException ex) {
                    logger.error(ex.getMessage());
                }
            });

            //削除対象ファイルを削除
            for (String target : removeTargetList) {
                String zipfile = target + ".zip";
                String finFile = target + ".fin";

                //削除対象ファイル名と同名のfinファイルを削除する
                List<File> RemveList = finList.stream().filter(o -> o.getName().equals(finFile)).collect(Collectors.toList());
                for (File fi : RemveList) {
                    fi.deleteOnExit();
                }
                //削除対象ファイル名と同名のzipファイルを削除する
                RemveList = zipList.stream().filter(o -> o.getName().equals(zipfile)).collect(Collectors.toList());
                for (File fi : RemveList) {
                    fi.deleteOnExit();
                }
            }
        }
    }

    /**
     * 検査結果ファイルの読み込み。
     *
     * @param laboCd
     * @param fi
     * @throws IOException
     */
    public void ReadSpecimenInfoFile(String laboCd, File fi) throws IOException {
        File KeyDir = new File(PhrConfig.getConfigProperty(ConfigConst.MINCS_KEY_PATH), laboCd);
        File SuccessRootDir = new File(PhrConfig.getConfigProperty(ConfigConst.MINCS_RESULT_SUCCESS_PATH), laboCd);
        File FailedRootDir = new File(PhrConfig.getConfigProperty(ConfigConst.MINCS_RESULT_FAILED_PATH), laboCd);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String strDate = sdf.format(cal.getTime());
        if (!KeyDir.exists()) {
            KeyDir.mkdir();
        }
        if (!SuccessRootDir.exists()) {
            SuccessRootDir.mkdir();
        }
        if (!FailedRootDir.exists()) {
            FailedRootDir.mkdir();
        }

        //keyファイルリストの取得
        List<File> keyList = Arrays.asList((KeyDir.listFiles(new keyFiltter())));
        List<File> OmlList = decompressSpecmenFile(fi, keyList);

        for (File oi : OmlList) {
            List<String> errorList = new ArrayList<>();
            boolean result = InsertUpdateMincsResult(laboCd, oi, errorList);
            if (result) {
                File dateDir = new File(PhrConfig.getConfigProperty(ConfigConst.MINCS_RESULT_SUCCESS_PATH), strDate);
                if (!dateDir.exists()) {
                    dateDir.mkdir();
                }
                File targetFile = new File(dateDir.getPath(), oi.getName());
                oi.renameTo(targetFile);
            } else {
                File dateDir = new File(PhrConfig.getConfigProperty(ConfigConst.MINCS_RESULT_FAILED_PATH), strDate);
                if (!dateDir.exists()) {
                    dateDir.mkdir();
                }
                File targetFile = new File(dateDir.getPath(), oi.getName());
                oi.renameTo(targetFile);
            }
            oi.deleteOnExit();
        }
    }

    /**
     * 指定されたファイルリストを解凍する。
     *
     * @param FileList
     * @return
     */
    private List<File> decompressSpecmenFile(File targetFile, List<File> keyList) {
        boolean res = true;
        String destDir = PhrConfig.getConfigProperty(ConfigConst.MINCS_DECOMPRESS_PATH);

        List<File> resList = new ArrayList<>();
        File keyFile;
        keyFile = null;
        try {
            //圧縮ファイルと同名のKeyファイルが存在しないことを確認する。
            String fName = getNameWithOutExtension(targetFile) + KEYFILE_EXTENTION;
            logger.debug((fName));
            //
            if (keyList.stream().anyMatch(s -> s.getName().startsWith(fName))) {
                Optional<File> searchFile = keyList.stream().filter(s -> s.getName().equals(fName)).findFirst();
                if (searchFile.isPresent()) {
                    keyFile = searchFile.get();
                }
            }
            String password = "";

            //keyファイルが存在する場合、keyファイルから解凍パスワードを取得する。
            if (keyFile != null) {
                try (BufferedReader br = new BufferedReader(new FileReader(keyFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        //解凍パスワードを取得する。
                        if (line.startsWith(ENCRIPTYKEY_INFO)) {
                            password = line.replace(ENCRIPTYKEY_INFO, "");
                            break;
                        }
                    }
                }
            }

            ZipFile zipFile = new ZipFile(targetFile.getPath());
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destDir);

            //使用したKEYファイルの削除
            if (keyFile != null) {
                keyFile.deleteOnExit();
            }

            File dir = new File(destDir);
            resList = Arrays.asList(dir.listFiles());

        } catch (IOException | ZipException ex) {
            logger.error(ex.getMessage());
        }

        return resList;
    }

    /**
     * 拡張子を除いたファイル名の取得
     *
     * @param fi
     * @return
     */
    private String getNameWithOutExtension(File fi) {
        String fileName = fi.getName();
        int index = fileName.lastIndexOf('.');
        if (index != -1) {
            return fileName.substring(0, index);
        }
        return "";
    }

    /**
     *
     * @param zipList 圧縮ファイルリスト
     * @param finList 転送完了フラグファイルリスト
     * @return
     */
    private List<File> searchDecompressFilePath(List<File> zipList, List<File> finList) {
        logger.debug("Start");
        List<File> pathList = new ArrayList<>();
        //finと同名の.zipファイルのパスを戻り値として設定する
        zipList.stream().forEach((fi) -> {
            //拡張子を除いたファイル名の取得
            String fName = getNameWithOutExtension((fi));
            //finリストとファイル名が一致するか確認する。一致した場合、解凍候補リストに追加する
            if (finList.stream().anyMatch(s -> s.getName().startsWith((fName)))) {
                pathList.add(fi);
            }
        });

        return pathList;
    }

    /**
     * 検査結果の読み込み。
     *
     * @param fi
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean InsertUpdateMincsResult(String laboCd, File fi, List<String> errorList) throws FileNotFoundException, IOException {

        // 入力ストリームの生成
        String msg;
        String strExaminationDate = null;
        String strPatientId = null;
        String strOrderId = null;
        String strMedicalOrganizationCd = null;
        String ReferanceValue = null;
        String strLaboratoryCd = laboCd;
        List<ObservationMInCSData> observationDatas = new ArrayList<>();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        ObservationEntryService entryService = new ObservationEntryService();

        try {
            fis = new FileInputStream(fi);
            isr = new InputStreamReader(fis, "ISO-2022-JP");
            br = new BufferedReader(isr);

            while ((msg = br.readLine()) != null) {
                logger.debug(msg);
                String[] ary = msg.split("\\|");
                if (ary.length > 1) {
                    String segment = ary[SEGMENT_INDEX];
                    switch (segment.toUpperCase()) {
                        case "PID":
                            strPatientId = GetPID(ary[PID_PATIENTID_INDEX]);
                            break;
                        case "SPM":
                            break;
                        case "OBR":
                            strOrderId = ary[OBR_ORDERID_INDEX];
                            strExaminationDate = ary[OBR_EXAMINATIONDATE_INDEX];
                            break;
                        case "ORC":
                            strMedicalOrganizationCd = GetMedicalOrganizationCd(ary[ORC_MEDICALCD_INDEX]);
                            break;
                        case "OBX":

                            //登録用クラス作成
                            ObservationMInCSData od = new ObservationMInCSData();
                            String[] obx3Arry = ary[OBX_OBSERVATIONIDENTIFER_INDEX].split("\\^");
                            //od.setLaboartoryCd(obx3Arry[obx3Arry.length - 1]);
                            od.setLaboartoryCd(laboCd);
                            //strLaboratoryCd = obx3Arry[obx3Arry.length - 1];
                            od.setJLAC10(obx3Arry[ITEM_CD_INDEX]);
                            boolean isJLAC10 = false;
                            for (int i = 1; i < obx3Arry.length - 1; i++) {
                                if (obx3Arry[i].matches("^JC10")) {
                                    isJLAC10 = true;
                                    break;
                                }
                            }
                            od.setIsJLAC10(isJLAC10);
                            od.setValue(ary[OBX_VALUE_INDEX]);
                            od.setOutRangeType(ary[OBX_VALUE_INDEX]);
                            ReferanceValue = ary[OBX_REFERENCERANGE_INDEX];
                            if (ReferanceValue.matches("^[\\-0123456789].*\\-[\\-0123456789].*")) {
                                //基準値下限がマイナスの場合を考慮し、最初の一文字を切り取る。
                                String Initial = ReferanceValue.substring(0, 1);
                                //残った文字の最初の-で分割する。下限値には最初に切り取った文字をくっつける
                                String temp = ReferanceValue.substring(1);
                                int index = temp.indexOf("-");
                                String minValue = Initial + temp.substring(0, index);
                                String maxValue = temp.substring(index + 1);
                                od.setMinReferenceValue(minValue);
                                od.setMaxReferenceValue(maxValue);
                                logger.debug(minValue + " ～ " + maxValue);
                            } else if (ReferanceValue.matches("^[\\-0123456789].*<")) {
                                od.setMinReferenceValue(ReferanceValue.replaceFirst("<", ""));
                            } else if (ReferanceValue.matches("^<[\\-0123456789].*")) {
                                od.setMaxReferenceValue(ReferanceValue.replaceFirst("<", ""));
                            }
                            observationDatas.add(od);

                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }

//<editor-fold defaultstate="collapsed" desc="取得情報エラーチェック">
        //患者ID
        if (IsNullOrWhiteSpace(strPatientId)) {
            errorList.add("[" + fi.getName() + "] 患者IDが取得できません。");
            logger.error("患者IDが取得できません。");
            return false;
        }
        //検査依頼ID
        if (IsNullOrWhiteSpace(strOrderId)) {
            errorList.add("[" + fi.getName() + "] オーダーID情報が取得できません。");
            logger.error("オーダーID情報が取得できません。");
            return false;
        }
        //医療機関コード
        if (IsNullOrWhiteSpace(strMedicalOrganizationCd)) {
            errorList.add("[" + fi.getName() + "] 医療機関コードが取得できません。");
            logger.error("医療機関コードが取得できません。");
            return false;
        }

        Calendar examinationDate = dateTryParse(strExaminationDate);
        //検査日
        if (IsNullOrWhiteSpace(strExaminationDate) || examinationDate == null) {
            errorList.add("[" + fi.getName() + "] 検査日が取得できません。");
            logger.error("検査日が取得できません。");
            return false;
        }
        //検査結果レコード
        if (observationDatas.isEmpty()) {
            errorList.add("[" + fi.getName() + "] 検査結果項が存在しません。");
            logger.error("検査結果項が存在しません。");
            return false;
        }
        //検査項目ID
        if (observationDatas.stream().anyMatch(od -> IsNullOrWhiteSpace(od.getJLAC10()))) {
            errorList.add("[" + fi.getName() + "] 検査項目が取得出来ない検査が存在します。");
            logger.error("検査項目が取得出来ない検査が存在します。");
            return false;
        }

//</editor-fold>
        try {
            //PHRIDの取得
            MedicalOrganizationPatientEntity mopEntity = getMedicalOrganizationPatient(strPatientId, strMedicalOrganizationCd);
            if (mopEntity == null) {
                errorList.add("[" + fi.getName() + "] 該当の患者が登録されていません。");
                logger.info("該当の患者が登録されていないため、処理終了。");
                return false;
            }
            //InsurerIdの取得
            InsurerPatientEntity ipEntity = getInsurerPatientInfo(mopEntity.getPHR_ID());
            if (ipEntity == null) {
                errorList.add("[" + fi.getName() + "] 該当の患者が保険者に登録されていません。");
                logger.info("該当の患者が保険者に登録されていないため、処理終了。");
                return false;
            }

            //JLAC10の取得
            try {
                this.ObservationJLac10List = getObservationDefinitionJlac10List(ipEntity.getInsurerNo(), examinationDate.get(Calendar.YEAR));
                if (this.ObservationJLac10List == null) {
                    errorList.add("[" + fi.getName() + "] 検査項目が存在しません。");
                    logger.info("検査項目が存在しません");
                    return false;
                }
            } catch (Throwable ex) {
                logger.error(ex.getMessage());
                logger.error("検査項目の取得に失敗しました。");
                errorList.add("[" + fi.getName() + "] 検査項目の取得に失敗しました。");
                return false;
            }

            boolean isExistItem = false;
            List<ObservationMInCSData> removeList = new ArrayList<>();
            for (ObservationMInCSData od : observationDatas) {
                //検査結果に登録された項目コードがマスターに存在するか確認する。
                if (od.getIsJlac10()) {
                    String jlac10Cd = od.getJLAC10();
                    Optional<ObservationDefinitionJlac10Entity> entity
                            = this.ObservationJLac10List.stream().filter(o -> o.getJLAC10().equals(jlac10Cd)).findFirst();
                    if (entity.isPresent()) {
                        isExistItem = true;
                        od.setObservationId(entity.get().getObservationDefinitionId());
                    } else {
                        logger.info(od.getJLAC10() + "はマスターに存在しない項目のため対象外です。");
                        removeList.add(od);
                    }
                } else {
                    logger.info(od.getJLAC10() + "はJLAC10コードではないため対象外です。");
                    removeList.add(od);
                }
            }

            if (!isExistItem) {
                logger.error("対象検査の項目が１件も検査マスターに登録されていません。");
                errorList.add("[" + fi.getName() + "] 対象検査の項目が１件も検査マスターに登録されていません。");
                return false;
            }

            //対象外のレコードを削除する
            for (ObservationMInCSData removeTarget : removeList) {
                observationDatas.remove(removeTarget);
            }

            //検査依頼IDが一致する検査の検索
            ObservationEventEntity oEntity = entryService.searchObsevationByOrderNo(mopEntity.getPHR_ID(), strOrderId);
            //一致する検査依頼IDが存在しない場合は新規登録
            if (oEntity == null) {

                ObservationEventEntity eventEntity = new ObservationEventEntity();
                eventEntity.setDataInputTypeCd(MINCS_DATA_INPUT_TYPE);
                eventEntity.setPHR_ID(mopEntity.getPHR_ID());
                Timestamp ts = new Timestamp(examinationDate.getTime().getTime());
                eventEntity.setExaminationDate(ts);
                eventEntity.setYear(examinationDate.get(Calendar.YEAR));
                eventEntity.setInsurerNo(ipEntity.getInsurerNo());
                eventEntity.setLaboratoryCd(strLaboratoryCd);
                eventEntity.setOrderNo(strOrderId);
                eventEntity.setMedicalOrganizationCd(strMedicalOrganizationCd);

                List<ObservationEntity> resList = new ArrayList<>();
                for (ObservationMInCSData od : observationDatas) {
                    if (od.getObservationId() == null) {
                        continue;
                    }

                    ObservationEntity newItemEntity = new ObservationEntity();
                    newItemEntity.setJLAC10(od.getJLAC10());
                    newItemEntity.setValue(od.getValue());
                    newItemEntity.setObservationDefinitionId(od.getObservationId());
                    newItemEntity.setOutRangeTypeCd(od.getOutRangeType());
                    newItemEntity.setMaxReferenceValue(od.getMaxReferenceValue());
                    newItemEntity.setMinReferenceValue(od.getMinReferenceValue());
                    resList.add(newItemEntity);
                }

                //Alertの設定
                AutoCalcService autoCalcService = new AutoCalcService();
                resList = autoCalcService.autoCalcSet(eventEntity.getPHR_ID(), eventEntity.getInsurerNo(), eventEntity.getYear(), eventEntity.getExaminationDate(), resList);
//                AlertSetService aService = new AlertSetService();
//                List<ObservationEntity> insertResultList = null;
//                insertResultList = aService.alertSet(eventEntity.getPHR_ID(), eventEntity.getInsurerNo(), eventEntity.getYear(), resList , eventEntity.getExaminationDate());
                ObservationEntryService es = new ObservationEntryService();
                es.insertObservationAndObservationEvent(eventEntity, resList);

            } //一致する検査依頼IDが存在する場合は検査結果の更新
            else {
                List<ObservationEntity> observationEntitys = getObservationEntityList(oEntity.getObservationEventId());
                List<ObservationEntity> updateTargetList = new ArrayList<>();
                List<ObservationEntity> insertTargetList = new ArrayList<>();
                Calendar cal = Calendar.getInstance();
                Timestamp ts = new Timestamp(cal.getTime().getTime());
                for (ObservationMInCSData od : observationDatas) {
                    //既存の項目は更新
                    if (observationEntitys.stream().anyMatch(o -> o.getJLAC10().equals(od.getJLAC10()))) {
                        List<ObservationEntity> targetList = observationEntitys.stream().filter(o -> o.getJLAC10().equals(od.getJLAC10())).collect(Collectors.toList());
                        for (ObservationEntity oe : targetList) {
                            oe.setValue(od.getValue());
                            oe.setOutRangeTypeCd(od.getOutRangeType());
                            oe.setMaxReferenceValue(od.getMaxReferenceValue());
                            oe.setMinReferenceValue(od.getMinReferenceValue());
                            updateTargetList.add(oe);
                        }
                    } //新規項目は追加
                    else {
                        ObservationEntity newItem = new ObservationEntity();
                        newItem.setObservationEventId(oEntity.getObservationEventId());
                        newItem.setJLAC10(od.getJLAC10());
                        newItem.setObservationDefinitionId(od.getObservationId());
                        newItem.setValue(od.getValue());
                        newItem.setOutRangeTypeCd(od.getOutRangeType());
                        newItem.setMaxReferenceValue(od.getMaxReferenceValue());
                        newItem.setMinReferenceValue(od.getMinReferenceValue());
                        insertTargetList.add(newItem);
                    }
                }

                //Alertの設定
//                AlertSetService aService = new AlertSetService();
                AutoCalcService autoCalcService = new AutoCalcService();
                List<ObservationEntity> updateResultList = null;
                List<ObservationEntity> insertResultList = null;

                updateTargetList = autoCalcService.autoCalcSet(oEntity.getPHR_ID(), oEntity.getInsurerNo(), oEntity.getYear(), oEntity.getExaminationDate(), updateTargetList);
                insertResultList = autoCalcService.autoCalcSet(oEntity.getPHR_ID(), oEntity.getInsurerNo(), oEntity.getYear(), oEntity.getExaminationDate(), insertTargetList);
//                updateResultList = aService.alertSet(oEntity.getPHR_ID(), oEntity.getInsurerNo(), oEntity.getYear(), updateTargetList, oEntity.getExaminationDate());
//                insertResultList = aService.alertSet(oEntity.getPHR_ID(), oEntity.getInsurerNo(), oEntity.getYear(), insertTargetList, oEntity.getExaminationDate());

                ObservationEntryService es = new ObservationEntryService();
                es.updateObservationAndObservationEvent(updateTargetList, insertResultList);

            }

        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            return false;
        }
        return true;
    }
//<editor-fold defaultstate="collapsed" desc="Private">

    /**
     * OML11データの患者情報を取得する。左埋めされた0は削除する。
     *
     * @param msg
     * @return
     */
    private String GetPID(String msg) {
        String res;
        String ary[] = msg.split("\\^");
        res = ary[0].replaceFirst("^0+", "");
        return res;
    }

    /**
     * 医療機関番号の取得
     *
     * @param msg
     * @return
     */
    private String GetMedicalOrganizationCd(String msg) {
        String res;
        String ary[] = msg.split("\\^");
        res = ary[ary.length - 1];
        return res;
    }

    /**
     * 患者関連情報の取得
     *
     * @param medicalCd
     * @param patientId
     * @return
     * @throws Throwable
     */
    private MedicalOrganizationPatientEntity getMedicalOrganizationPatient(String patientId, String medicalCd) throws Throwable {
        MedicalOrganizationPatientEntity entity = null;

        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            MedicalOrganizationPatientAdapter adapter = new MedicalOrganizationPatientAdapter(dao.getConnection());
            entity = adapter.findByPatientIdAndMedicalCd(patientId, medicalCd);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }

        return entity;
    }

    /**
     * PHRIDから保険者患者関連情報の取得
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    private InsurerPatientEntity getInsurerPatientInfo(String phrId) throws Throwable {
        InsurerPatientEntity entity = null;
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            InsurerPatientAdapter adapter = new InsurerPatientAdapter(dao.getConnection());
            entity = adapter.findByPhrId(phrId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return entity;
    }

    /**
     * 空白かNULLか確認
     *
     * @param value
     * @return
     */
    private boolean IsNullOrWhiteSpace(String value) {
        return (value == null || value.trim().length() == 0);
    }

    /**
     * 文字列が正しい日付フォーマットか確認
     *
     * @param value
     * @return
     */
    private Calendar dateTryParse(String value) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
            Date date = format.parse(value);
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            return cl;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 文字列のDouble型への変換
     *
     * @param value
     * @return
     */
    private Double doubleTryParse(String value) {
        try {
            Double dt = Double.parseDouble(value);
            return dt;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * JLAC10情報を全て取得
     *
     * @return
     * @throws Throwable
     */
    private List<ObservationDefinitionJlac10Entity> getObservationDefinitionJlac10List(String insurerNo, int Year) throws Throwable {
        List<ObservationDefinitionJlac10Entity> res = null;
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationDefinitionJlac10Adapter adapter = new ObservationDefinitionJlac10Adapter(dao.getConnection());
            res = adapter.findByInsurerNoYearInputType(insurerNo, Year);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return res;
    }

    private List<ObservationEntity> getObservationEntityList(String EventId) throws Throwable {
        List<ObservationEntity> res = null;
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationAdapter adapter = new ObservationAdapter(dao.getConnection());
            res = adapter.findByObservationEventId(EventId);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return res;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="ObservationMInCSData">
    /**
     * 検査項目結果登録用データ保持クラス
     */
    private class ObservationMInCSData {

        private static final String MoreWord = "超";
        private static final String MoreEqualsWord = "以上";
        private static final String LessEqualsWord = "以下";
        private static final String LessWord = "未満";
        private static final int MoreValue = 4;
        private static final int MoreEqualsValue = 3;
        private static final int LessEqualsValue = 2;
        private static final int LessValue = 1;

        String jLAC10 = null;
        String laboratoryCd = null;
        String value = null;
        Double minReferenceValue = null;
        Double maxReferenceValue = null;
        String observationId = null;
        Integer outrangeType;
        boolean isjlac10;

        public String getJLAC10() {
            return this.jLAC10;
        }

        public String getObservationId() {
            return this.observationId;
        }

        public boolean getIsJlac10() {
            return this.isjlac10;
        }

        public String getLaboartoryCd() {
            return this.laboratoryCd;
        }

        public String getValue() {
            return this.value;
        }

        public Double getMinReferenceValue() {
            return this.minReferenceValue;
        }

        public Double getMaxReferenceValue() {
            return this.maxReferenceValue;
        }

        public Integer getOutRangeType() {
            return this.outrangeType;
        }

        public void setJLAC10(String value) {
            this.jLAC10 = value;
        }

        public void setIsJLAC10(boolean value) {
            this.isjlac10 = value;
        }

        public void setLaboartoryCd(String value) {
            this.laboratoryCd = value;
        }

        public void setValue(String value) {
            if (value.contains(MoreEqualsWord)) {
                value = value.replaceFirst(MoreEqualsWord, "");
            }
            if (value.contains(MoreWord)) {
                value = value.replaceFirst(MoreWord, "");
            }
            if (value.contains(LessEqualsWord)) {
                value = value.replaceFirst(LessEqualsWord, "");
            }
            if (value.contains(LessWord)) {
                value = value.replaceFirst(LessWord, "");
            }
            this.value = value;
        }

        public void setMinReferenceValue(String value) {
            this.minReferenceValue = doubleTryParse(value);
        }

        public void setMaxReferenceValue(String value) {
            this.maxReferenceValue = doubleTryParse(value);
        }

        void setObservationId(String value) {
            this.observationId = value;
        }

        void setOutRangeType(String value) {
            if (value.contains(MoreEqualsWord)) {
                this.outrangeType = MoreEqualsValue;
            } else if (value.contains(MoreWord)) {
                this.outrangeType = MoreValue;

            } else if (value.contains(LessEqualsWord)) {
                this.outrangeType = LessEqualsValue;
            } else if (value.contains(LessWord)) {
                this.outrangeType = LessValue;
            }

        }

        /**
         * 文字列のDouble型への変換
         *
         * @param value
         * @return
         */
        private Double doubleTryParse(String value) {
            try {
                Double dt = Double.parseDouble(value);
                return dt;
            } catch (Exception ex) {
                return null;
            }
        }

    }
//</editor-fold>
}

//<editor-fold defaultstate="collapsed" desc="FileFilterClass">
/**
 * 転送完了フラグファイル取得フィルター
 *
 * @author kis-note-025
 */
class finFlagFiltter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        return name.matches(".*\\.fin");
    }

}

/**
 * 検査結果圧縮ファイル取得フィルター
 *
 * @author kis-note-025
 */
class zipFiltter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        return name.matches(".*\\.zip");
    }

}

/**
 * 圧縮キーファイル取得フィルター
 *
 * @author kis-note-025
 */
class keyFiltter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        return name.matches(".*\\_key\\.txt");
    }
}
//</editor-fold>
