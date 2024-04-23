/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;
import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.kis_inc.csvconverter.src.dto.ResultObservationDto;
import kis.inc.ssmix2storagemaker.dto.CHECKUP.CheckUpXMLDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.dto.ObservationEntryDto;
import phr.service.IAutoCalcService;
import phr.service.ICheckUpBackUPRestoreService;
import phr.service.ISpecificMedicalCheakUpFormService;

/**
 *
 * @author kis-note-027_user
 */
public class CheckUpBackUPRestoreService implements ICheckUpBackUPRestoreService{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CheckUpBackUPRestoreService.class);
    
    @Override
    public int getBackUPCheckUpXML(String phrId, String exPath) throws Throwable {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");        
        String TypeCD = "CHECKUP";
        //コピー先の拡張ストレージを構成する
        String patientPath = exPath + "/" + phrId.substring(0, 3) + "/" + phrId.substring(3, 6) + "/" + phrId;// + "/" + phrId.substring(0, 3);
        
        //登録済み健診結果のObservationEventIdとExaminationDate一覧を取得する
        ObservationEventService eventsvc = new ObservationEventService();
        Map<String, String> checkuplist = eventsvc.getEventIdandDateList(phrId);
        if(checkuplist==null){
            return 0;
        }
        
        String checkupPath = "";
        String exDate = "";
        int count = 1;
        //対象PHRのxmlファイルリストを取得する。
        String xmlPath = PhrConfig.getConfigProperty(ConfigConst.CHECKUP_SAVE_PATH) + "/" + phrId.substring(0, 7) + "/" +  phrId;
        File xmlDir = new File(xmlPath);
        File[] files = xmlDir.listFiles();
        if(files == null){
            logger.debug("対象患者の健診XMLがありませんでした。");
            return 0;
        }
        for(File file:files){
            exDate = file.getName();
            if(exDate.length()<8){
                continue;
            }
            String eventID = checkuplist.get(exDate);
            if(eventID == null){
                eventID = "9000000" + String.format("%03d", count);
                 count++;
            }
            
            Date today = new Date();
            checkupPath = TypeCD + "/" + phrId + "_" + exDate + "_" + TypeCD + "_" + eventID + "_" + sdf.format(today) + "_-_1";
            
            String copypath = patientPath + "/" + exDate + "/" + checkupPath;
            File copyfile = new File(copypath);
            if(!copyfile.exists()) copyfile.mkdirs();
            for(File targetfile:file.listFiles()){
                String filename = targetfile.getName();
                if(!filename.toUpperCase().endsWith(".XML")){
                    continue;
                }
                File copiedfile = new File(copypath + "/" +targetfile.getName());
                Files.copy(targetfile.toPath(), copiedfile.toPath());//CopyOption.REPLACE_EXISTING
            }

        }
        
        return 1;
    }

    @Override
    public int setRestoreCheckUpXML(String phrId,CheckUpXMLDto model) throws Throwable {
        logger.debug("健診情報の登録開始");
        logger.debug(phrId + "の登録処理");
        String insurerNo = phrId.substring(0, 7);
        model.setInsureNo(insurerNo);
        
        List<ObservationEntryDto> dtoList = new ArrayList();
    //kokokara    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(new Date());
        ISpecificMedicalCheakUpFormService specificMedicalCheakUpFormService = new SpecificMedicalCheakUpFormService();

        Map<String , String > jlacmap = specificMedicalCheakUpFormService.getjlacmap(insurerNo);
       
       Timestamp date = new Timestamp(new SimpleDateFormat("yyyyMMdd").parse(model.getExaminationDate()).getTime());

        //ObservationEventの作成
        //eventIDはserviceで取得して設定する
        ObservationEventEntity eventEntity = new ObservationEventEntity();
        eventEntity.setDataInputTypeCd(3);         // データ入力種別
        eventEntity.setPHR_ID(phrId);  // PHR_ID   // ファイル名より取得処理追加
        eventEntity.setExaminationDate(new Timestamp(new SimpleDateFormat("yyyyMMdd").parse(model.getExaminationDate()).getTime()));  // 検査日
        eventEntity.setYear(Integer.parseInt(year));    // 対象年度 
        eventEntity.setInsurerNo(insurerNo);   // 保険者番号

        //Observationの作成
        //eventIdはserviceで取得して設定する
        List<ResultObservationDto> observationList = model.getObservationList();
        List<ObservationEntity> targetList = new ArrayList<>();

        for( ResultObservationDto observation : observationList){
            ObservationEntity target = new ObservationEntity();
            String jlac = observation.getCode();
            if(!jlacmap.containsKey(jlac)) continue;
            target.setJLAC10(jlac);
            target.setObservationDefinitionId(jlacmap.get(jlac));
            target.setValue(observation.getValue());
            if(observation.getHighrange() != null)target.setMaxReferenceValue(Double.parseDouble(observation.getHighrange()));
            if(observation.getLowrange() != null)target.setMinReferenceValue(Double.parseDouble(observation.getLowrange()));
            //特定健診においてH,Lの意味は入力範囲外かを見ているので不要
            if(observation.getValue().equals("H") || observation.getValue().equals("L")){
                continue;
            }

            targetList.add(target);
        }

        try{
            IAutoCalcService autoCalcService = new AutoCalcService();
            
            targetList = autoCalcService.autoCalcSet(phrId, insurerNo, Integer.parseInt(year), date, targetList);
//            this.observationEntryService.insertObservationAndObservationEvent(eventEntity, resultList);   //登録は後でまとめて
            ObservationEntryDto dto = new ObservationEntryDto();
            dto.setObservationEventEntity(eventEntity);
            dto.setObservationEntity(targetList);
            dto.setExaminationDate(model.getExaminationDate());
            
            dtoList.add(dto);
            
            boolean entryRetFlg=specificMedicalCheakUpFormService.deleteInsertObservationList(dtoList, null);

        }catch(Exception e){
            logger.debug(e);
        }
        
        logger.debug("健診情報の登録1件終了");
        return 1;
        
    }
    
}
