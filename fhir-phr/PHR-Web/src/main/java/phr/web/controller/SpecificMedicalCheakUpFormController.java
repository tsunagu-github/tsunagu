/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import jp.kis_inc.csvconverter.src.controller.CsvConvertController;
import jp.kis_inc.csvconverter.src.dto.ConvertDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import phr.web.ActionConst;
import phr.web.ISessionUtility;
import phr.web.form.SpecificMedicalCheakUpForm;
import phr.service.ISpecificMedicalCheakUpFormService;
import jp.kis_inc.csvconverter.src.dto.ResultObservationDto;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ObservationDefinitionTypeAdapter;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.dto.ObservationEntryDto;
import phr.web.FormConst;
import phr.web.PhrWebConfig;
import phr.web.PhrWebConst;
import phr.service.IAlertSetService;
import phr.service.IAutoCalcService;
import phr.service.IObservationEntryService;
import phr.utility.TypeUtility;
import phr.web.SessionConst;
import phr.utility.Zip4jUtility;

/**
 *
 * @author kis.o-note-002
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.SPECIFIC_MEDICAL_CHECKUP_ACTION, "/"})
public class SpecificMedicalCheakUpFormController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SpecificMedicalCheakUpFormController.class);

    /**
     * @return the downloadFileDir
     */
    public static String getDownloadFileDir() {
        return downloadFileDir;
    }

    /**
     * @param aDownloadFileDir the downloadFileDir to set
     */
    public static void setDownloadFileDir(String aDownloadFileDir) {
        downloadFileDir = aDownloadFileDir;
    }

    /**画面ID */
    private final String FORM_CD = FormConst.SPECIFIC_MEDICAL_CHECKUP_FORM_CD;
    /**画面名称 */
    private final String FORM_NAME = FormConst.SPECIFIC_MEDICAL_CHECKUP_FORM_TITLE;

    /**
     *  ダウンロードボタン制御フラグ
     */
    private static final boolean downloadOK = true;   // クリックOK（データエラー発生時）
    private static final boolean downloadNG = false;   // クリックNG（初期起動 or 正常登録時）
    
    /**
     * エラーダウンロードファイルのパス
     */
    private static String downloadFileDir =PhrWebConfig.getPhrWebProperty(PhrWebConst.CHEAKUP_ERR_PATH);
    /**
     * ダウンロード一時ファイル
     */
    private static final String downloadTmpFile ="errFile.txt";

    /**
     * ダウンロードファイル名
     */
    private static final String downloadFileHeader ="SpecificMedicalExamination_";
    /**
     * 区切り文字
     */
    private static final String comma ="、";
    private static final String tab = "\t";
    
    /**
     *  xmlファイルのヘッダー情報 
     */
//    private ConvertDto      header;
//    private ConvertPhrIdDto headerPhrId;     // PHR-ID

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;
    
    /**
     * インジェクション：各種マスタサービス処理
     */
    @Autowired
    @Qualifier("SpecificMedicalCheakUpFormService")
    private ISpecificMedicalCheakUpFormService specificMedicalCheakUpFormService;
    
    /**
     * インジェクション：各種マスタサービス処理
     */
    @Autowired
    @Qualifier("ObservationEntryService")
    private IObservationEntryService observationEntryService;

    /**
     * インジェクション：各種マスタサービス処理
     */
    @Autowired
    @Qualifier("AlertSetService")
    private IAlertSetService alertSetService;

    /**
     * インジェクション：各種マスタサービス処理
     */
    @Autowired
    @Qualifier("AutoCalcService")
    private IAutoCalcService autoCalcService;    
    /**
     * <pre>初期アクションメソッド</pre>
     *
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
       
        //画面初期化
        SpecificMedicalCheakUpForm form = new SpecificMedicalCheakUpForm();
//        model.addAttribute("downloadBtnCtl", downloadNG);   // 初期起動の時はダウンロードボタンをおせないようにする。
        form.setDownloadBtnCtl(downloadNG);
        form.setUpdateFlg(false);
        
        ControllerUtility.InitHeader(model);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.SPECIFIC_MEDICAL_CHECKUP_ACTION);
    }
    
    /**
     * <pre>ダウンロードボタンクリック時</pre>
     * @param form
     * @param result
     * @param model
     * @param request
     * @throws Throwable 
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=download")
    @ResponseBody
    public void downloadAction(@ModelAttribute @Valid SpecificMedicalCheakUpForm form, BindingResult result, Model model, HttpServletRequest request,HttpServletResponse response) throws Throwable {

        try{
            List<String> messageList = (List<String>)sessionUtility.getSession(SessionConst.CHEKUP_ERRLIST);
            
            response.setContentType ( "text/plain; charset=UTF-8" );
            response.setHeader ( "Content-Disposition", downloadTmpFile);

            StringBuffer sb  = new StringBuffer();
             for(String message : messageList){
                sb.append(message + "\n\r");
            }

             PrintWriter pw = response.getWriter();
             pw.println(sb.toString());
             pw.flush();
        } catch(Exception e) {
            logger.debug("ダウンロード処理にて異常が発生しました。");
        } 

    }
            
    /**
     * <pre>登録ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param xmlfile
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=insert2")
    public String insertAction2(@ModelAttribute SpecificMedicalCheakUpForm form,@RequestParam("file") MultipartFile xmlfile, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
        List<String> messageList = new ArrayList<>();
        List<ObservationEntryDto> dtoList=new ArrayList<>();
//        List<ObservationEventEntity> sameList=new ArrayList<>();
        form.setInsertResulrArea(messageList);
        Map<String, List<String>> phrids=new HashMap<>();
        String fileName=form.getFileCsv();
        String tmpPath="";
        boolean updateflg = false;
        boolean xmlFlg = false;
        if(fileName.toUpperCase().endsWith(".XML")){
            //PHRIDとファイルパスのマップを設定（zip処理と共通処理とするため）
            phrids.put(fileName.substring(0, fileName.length()-4), new ArrayList<>(Arrays.asList(fileName)));
            xmlFlg=true;    //xml単体の処理のフラグ
        }else{
            //zipを解凍し、各PHRIDとファイルパスを取得する
            tmpPath=PhrWebConfig.getPhrWebProperty(PhrWebConst.CHEAKUP_TEMP);   //一時処理及び退避フォルダ
            Date postDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
            String dateStr = sdf.format(postDate);
            tmpPath=tmpPath+"/unzip/"+dateStr;
            phrids=getPhrIdList(xmlfile, tmpPath);  //zipファイル処理
            if(phrids.isEmpty()){
                messageList.add("指定したファイルに登録対象となる情報はありませんでした。");
            }
        }
        
        //まずは全ファイルチェックを行い、OKなら登録処理に移行
        for(Map.Entry<String, List<String>> itemMap : phrids.entrySet()){
            String phrid;
            String insurerNo;
            String examinationDate="";
            phrid=itemMap.getKey();
            for(String itemValue: itemMap.getValue()){
                boolean sameFlg = false;
                PatientEntity entity = specificMedicalCheakUpFormService.getPatientInfo(phrid);
                sessionUtility.setSession(SessionConst.CHEKUP_OVERLAP_LIST, null);
                //PHRIDが存在しない場合はエラーで終了する。
                if(entity == null){
                    String message = "登録対象のPHR IDが存在しません。ファイル名のPHR IDを確認して下さい。【" +itemValue+"】";
                    messageList.add(message);
                }else{
                    ConvertDto resultDto = null;
                    insurerNo=entity.getInsurerNo();
                    if(messageList.size() == 0){
                        CsvConvertController ccf = new CsvConvertController();
                        ccf.setSchempath(PhrWebConfig.getPhrWebProperty(PhrWebConst.SCHEM_PATH));
                        if(xmlFlg){
                            //xmlファイル１つの処理
                            resultDto = ccf.executeTypeInput(xmlfile.getInputStream());
                        }else{
                            //zipの場合の処理（ファイルパスからStreemを作成）
                            File file = new File(itemValue);
                            try(InputStream inputStream = new FileInputStream(file);){
                                resultDto = ccf.executeTypeInput(inputStream);
                            }
                        }
                        
                        if(resultDto == null){
                            List<String> errorList = ccf.getErrList();
                            for(String err : errorList){
                                messageList.add(err);
                            }
                        }else{
                            //登録済みの情報を取得
                            examinationDate=resultDto.getExaminationDate();
                            // XML内の保険者番号をPHR利用者の保険者番号に置き換える
                            resultDto.setInsureNo(entity.getInsurerNo());
                            List<ObservationEventEntity> resultList = dataExitCheck(
                                    phrid , insurerNo , examinationDate);
                            if(resultList!=null){
//                                sameList.addAll(resultList);
                                updateflg = true;   //全体情報
                                sameFlg=true;   //個人毎情報
                            }
                        }
                    }
        
//                    //xmlの場合、実行
//                    if(messageList.size() == 0 && !updateflg){
//                        try{
//                            entryCheckup(phrid, resultDto);
//                        }catch(Exception e){
//                            messageList.add("登録処理に異常が発生したため登録処理を中断しました。管理者に連絡して下さい。");
//                            form.setInsertResulrArea(messageList);
//                        }
//                    }
//
//                    // 画面遷移（自画面へ戻る）
//                    if(messageList.isEmpty()){
//                        boolean saveFile = saveFile(xmlfile.getInputStream() , phrid, entity.getInsurerNo() , resultDto.getExaminationDate(),updateflg);
//                        if(!saveFile) messageList.add("保存処理に失敗しました。管理者に連絡して下さい。");
//                    }
//
//                    //重複データがある
//                    if(updateflg){
//                        messageList.add("登録しようとしたファイルと同一のPHR ID、健診実施日で特定健診の結果が登録されています。");
//                        messageList.add("メッセージ画面の下の「更新」ボタンをクリックすると更新処理を行います。");
//                        messageList.add("修正、更新の場合でしたら「更新」ボタンをクリックしてください。");
//                        sessionUtility.setSession(SessionConst.CHEKUP_OVERLAP_DATA, resultDto);
//                    }
                    //一括処理する為に蓄積
//                    if(messageList.isEmpty()){
                        try{
                            for (int i = 3; i < 6; i++) {
                                ObservationEntryDto tmpEntryDto=entryCheckup(phrid, resultDto, i);
                                // 医療機関番号をセット
                                tmpEntryDto.getObservationEventEntity().setMedicalOrganizationCd(resultDto.getMedicalOrganizationCd());
                                //展開実ファイルのパスを保存
                                tmpEntryDto.setTempPath(itemValue);
                                if(tmpEntryDto!=null){
                                    //登録情報生成成功
                                    if(sameFlg){
                                        //重複データ情報
                                        tmpEntryDto.setSameFlg(sameFlg);
                                        tmpEntryDto.setSamePath(itemValue);
                                        tmpEntryDto.setUpdateFlg(xmlFlg);
                                    }
                                    if(!xmlFlg){
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                        examinationDate=sdf.format(tmpEntryDto.getObservationEventEntity().getExaminationDate());
                                    }
                                    tmpEntryDto.setExaminationDate(examinationDate);
                                    dtoList.add(tmpEntryDto);
                                }else{
                                    messageList.add("登録情報作成で異常が発生したため登録処理を中断しました。管理者に連絡して下さい。");
                                }
                            }
                        }catch(Exception e){
                            messageList.add("登録情報作成で異常が発生したため登録処理を中断しました。管理者に連絡して下さい。");
                            logger.error("", e);
                        }
//                    }
                }
            }
        }
        
        //情報の登録削除処理
        boolean entryRetFlg=false;
        if(!dtoList.isEmpty() && messageList.isEmpty() && !updateflg){
            //登録削除（ここでは削除は空）
//            entryRetFlg=specificMedicalCheakUpFormService.deleteInsertObservationList(dtoList, sameList);
            entryRetFlg=specificMedicalCheakUpFormService.deleteInsertObservationList(dtoList, null);
            if(!entryRetFlg){
                messageList.add("登録処理に異常が発生したため登録処理を中断しました。管理者に連絡して下さい。");
                form.setInsertResulrArea(messageList);
            }
        }

        // 処理ファイルの保存
        boolean saveFile=true;
        if(xmlFlg){
            if(messageList.isEmpty()){
                //xmlファイル単体の処理
                saveFile = saveFile(
                        xmlfile.getInputStream()
                        , dtoList.get(0).getObservationEventEntity().getPHR_ID()
                        , dtoList.get(0).getObservationEventEntity().getInsurerNo()
                        , dtoList.get(0).getExaminationDate()
                        , updateflg
                );
            }
        }else{
            //zipの処理(登録完了した場合のみ)
            String insNo="";
            if(!dtoList.isEmpty()){
                insNo=dtoList.get(0).getObservationEventEntity().getInsurerNo();
            }
            //saveFile = moveDelFile(tmpPath, insNo, fileName, updateflg, entryRetFlg);
            saveFile = beforeMoveDelFile(tmpPath, insNo, fileName, updateflg, entryRetFlg, dtoList, true);
        }
        if(!saveFile) messageList.add("保存処理に失敗しました。管理者に連絡して下さい。");

        // 重複情報の作成
        if(updateflg){
            messageList.add("登録しようとしたファイルと同一のPHR ID、健診実施日で特定健診の結果が登録されています。");
            if(xmlFlg){
                messageList.add("メッセージ画面の下の「更新」ボタンをクリックすると更新処理を行います。");
                messageList.add("修正、更新の場合でしたら「更新」ボタンをクリックしてください。");
            }else{
                messageList.add("修正、更新の場合、一覧をチェックした後、「更新」ボタンをクリックしてください。");
                messageList.add("新規登録対象の特定健診の結果とチェックした更新対象の特定健診の結果を登録します。");
                dtoList.get(0).setZipPath(tmpPath);
                dtoList.get(0).setZipFile(fileName);
                form.setObservationEntryDto(dtoList);
            }
            sessionUtility.setSession(SessionConst.CHEKUP_OVERLAP_DATA, dtoList);
//            sessionUtility.setSession(SessionConst.CHEKUP_OVERLAP_LIST, sameList);
            form.setParam1(form.getFileCsv());
        }
        
        if(!messageList.isEmpty()){
            sessionUtility.setSession(SessionConst.CHEKUP_ERRLIST, messageList);
 //           model.addAttribute("downloadBtnCtl", downloadOK);   // 初期起動の時はダウンロードボタンをおせないようにする。
            form.setDownloadBtnCtl(downloadOK);
        }else{
            messageList.add("正常に登録しました。【" +form.getFileCsv()+"】");
            form.setDownloadBtnCtl(downloadNG);
        }
        form.setFileCsv("");

        ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
        form.setUpdateFlg(updateflg);

        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.SPECIFIC_MEDICAL_CHECKUP_ACTION);

    }
    
       /**
     * <pre>登録ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=update")
    public String updateAction(@ModelAttribute SpecificMedicalCheakUpForm form, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        
//        ConvertDto resultDto = (ConvertDto)sessionUtility.getSession(SessionConst.CHEKUP_OVERLAP_DATA);
        List<ObservationEntryDto> dtoList = (List<ObservationEntryDto>)sessionUtility.getSession(SessionConst.CHEKUP_OVERLAP_DATA);
//        List<ObservationEventEntity> eventList = (List<ObservationEventEntity>)sessionUtility.getSession(SessionConst.CHEKUP_OVERLAP_LIST);
        List<String> messageList = new ArrayList<>();
        form.setInsertResulrArea(messageList);

        ObservationEntryDto resultDto=dtoList.get(0);
//        ObservationEventEntity entity = eventList.get(0);
        ObservationEventEntity entity = resultDto.getObservationEventEntity();
        String phrid = entity.getPHR_ID();
        String insurerNo = entity.getInsurerNo();
        String date = resultDto.getExaminationDate();

        File xmlfile;
        if(resultDto.getZipFile()==null){
            //一時保存フォルダに逃がしたファイルを取得する
            xmlfile = loadFile(phrid , insurerNo , date);
        }else{
            //zip
            xmlfile = new File(resultDto.getZipPath());
            List<ObservationEntryDto> formDtoList = form.getObservationEntryDto();
            int i=0;
            for(ObservationEntryDto dto:dtoList){
                if(dto.getSameFlg()){
                    if(formDtoList.get(i).getUpdateFlg()){
                        dto.setUpdateFlg(true);
                    }
                    i+=1;
                }
            }
            form.setObservationEntryDto(null);
        }
        if(xmlfile == null){
            messageList.add("更新処理に異常が発生したため更新処理を中断しました。");
            messageList.add("お手数ですが、登録処理からやり直してください。");
        }

        boolean entryRetFlg=false;
        if(messageList.isEmpty()){
            try{
//                deleateCheckup(eventList);
//                entryCheckup(phrid , resultDto);
                //登録削除
//                entryRetFlg=specificMedicalCheakUpFormService.deleteInsertObservationList(dtoList, eventList);
                entryRetFlg=specificMedicalCheakUpFormService.deleteInsertObservationList(dtoList, null);
                if(!entryRetFlg){
                    messageList.add("登録処理に異常が発生したため登録処理を中断しました。管理者に連絡して下さい。");
                    form.setInsertResulrArea(messageList);
                }
            }catch(Exception e){
                messageList.add("登録処理に異常が発生したため登録処理を中断しました。管理者に連絡して下さい。");
                form.setInsertResulrArea(messageList);
                logger.error(e);
            }
        }

        boolean saveFile;
        if(resultDto.getZipFile()==null){
            try(InputStream input = new FileInputStream(xmlfile);){
                if(messageList.isEmpty() ){
                    saveFile = saveFile(input, phrid, entity.getInsurerNo(), resultDto.getExaminationDate(),false);
                }else{
                    saveFile=true;
                }
                deleatFile(xmlfile);
            }
        }else{   
            //zipの処理
//            saveFile = moveDelFile(
//                    resultDto.getZipPath(), dtoList.get(0).getObservationEventEntity().getInsurerNo(), resultDto.getZipFile()
//                    , false, entryRetFlg
//            );            
            saveFile = beforeMoveDelFile(
                    resultDto.getZipPath(), dtoList.get(0).getObservationEventEntity().getInsurerNo(), resultDto.getZipFile()
                    , false, entryRetFlg, dtoList, false
            );
        }
        if(!saveFile) messageList.add("保存処理に失敗しました。管理者に連絡して下さい。");
        
        if(!messageList.isEmpty()){
            sessionUtility.setSession(SessionConst.CHEKUP_ERRLIST, messageList);
//            model.addAttribute("downloadBtnCtl", downloadOK);   // 初期起動の時はダウンロードボタンをおせないようにする。
            form.setDownloadBtnCtl(downloadOK);
        }else{
//            messageList.add("正常に登録しました。【" +form.getFileCsv()+"】");
            messageList.add("正常に登録しました。【" +form.getParam1()+"】");
            form.setDownloadBtnCtl(downloadNG);
        }
        //重複情報のセッションを削除
        sessionUtility.removeSession(SessionConst.CHEKUP_OVERLAP_DATA);

        ControllerUtility.InitHeader(model);
        form.setFormCd(FORM_CD);
        form.setFormName(FORM_NAME);
        model.addAttribute(form);
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.SPECIFIC_MEDICAL_CHECKUP_ACTION);
    }
    
    //登録した特定健診のxmlファイルをServerに保存する
    private boolean saveFile(InputStream xmlfile, String phrid , String insurerNo , String date , boolean tmpflg) {
        
        String path = null;
        
        if(!tmpflg){
            path = PhrWebConfig.getPhrWebProperty(PhrWebConst.CHEAKUP_SAVE_PATH);
        }else{
            path = PhrWebConfig.getPhrWebProperty(PhrWebConst.CHEAKUP_TEMP);
        }
        
//        if(!new File(path).exists()) return false;
        
        path = path + "/" + insurerNo + "/" + phrid + "/" + date;

        File rootpath = new File(path);
        
        if(!rootpath.exists()) rootpath.mkdirs();
        try {
            
            File file = new File(path, phrid + ".xml");
            if(file.exists())file.delete();
            Files.copy(xmlfile, file.toPath());
            
        } catch (IOException ex) {
            Logger.getLogger(SpecificMedicalCheakUpFormController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    //一時保存した特定健診のxmlファイルを取得する
    private File loadFile(String phrid , String insurerNo , String date ) {
        
        String path = null;
        
        path = PhrWebConfig.getPhrWebProperty(PhrWebConst.CHEAKUP_TEMP);
        
        if(!new File(path).exists()) return null;
        
        path = path + "/" + insurerNo + "/" + phrid + "/" + date;

        File rootpath = new File(path);
        
        if(!rootpath.exists()) rootpath.mkdirs();
        File[] targets = rootpath.listFiles();
        for(File target : targets){
            String name = target.getName();
            if(name.equals(phrid + ".xml")) return target;
        }
        
        return null;
    }

    //一時保存ファイルを削除する
    private void deleatFile(File xmlfile) {
        File date = xmlfile.getAbsoluteFile();
        File phrid = date.getAbsoluteFile();
        File insurer = phrid.getAbsoluteFile();
       
        xmlfile.delete();
        date.delete();
        phrid.delete();
        insurer.delete();
       
    }

    /*
    * 過去に同一PHRID、健診実施日のxmlファイルの登録を行っているかの確認を行う
    * 登録済みの場合は登録済リスト、無い場合はnullを返信する。
    */
    private List<ObservationEventEntity> dataExitCheck(String phrid, String insurerNo, String date) {
        try {
            List<ObservationEventEntity> resultList = 
                    specificMedicalCheakUpFormService.confirmCheckup(phrid, insurerNo, date);
            if(resultList != null && resultList.size() > 0) {
                logger.debug("過去に同一PHR ID、健診実施日で登録したことがあります");
//                sessionUtility.setSession(SessionConst.CHEKUP_OVERLAP_LIST, resultList);
                return resultList;
            }
        } catch (Throwable ex) {
            logger.error(ex);
        }
        return null;
    }

    private ObservationEntryDto entryCheckup(String phrid, ConvertDto resultDto, int dataInputTypeCd) throws Throwable {
        logger.debug("start");
        logger.debug(phrid + "の登録処理");
        
        //String insurerNo = resultDto.getInsureNo().substring(0, 7);
        //insurerNoをphdidをもとに取得
        PatientEntity entity = specificMedicalCheakUpFormService.getPatientInfo(phrid);
        String insurerNo = entity.getInsurerNo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year = sdf.format(new Date());


        Map<String , String > jlacmap = specificMedicalCheakUpFormService.getjlacmap(insurerNo);
       
       Timestamp date = new Timestamp(new SimpleDateFormat("yyyyMMdd").parse(resultDto.getExaminationDate()).getTime());

        //ObservationEventの作成
        //eventIDはserviceで取得して設定する
        ObservationEventEntity eventEntity = new ObservationEventEntity();
        eventEntity.setDataInputTypeCd(dataInputTypeCd);         // データ入力種別
        eventEntity.setPHR_ID(phrid);  // PHR_ID   // ファイル名より取得処理追加
        eventEntity.setExaminationDate(new Timestamp(new SimpleDateFormat("yyyyMMdd").parse(resultDto.getExaminationDate()).getTime()));  // 検査日
        eventEntity.setYear(Integer.parseInt(year));    // 対象年度 
        eventEntity.setInsurerNo(insurerNo);   // 保険者番号

        //Observationの作成
        //eventIdはserviceで取得して設定する
        List<ResultObservationDto> observationList = resultDto.getObservationList();
        List<ObservationEntity> targetList = new ArrayList<>();

        for (ResultObservationDto observation : observationList) {
            ObservationEntity target = new ObservationEntity();
            String jlac = observation.getCode();
            if(!jlacmap.containsKey(jlac)) continue;

            // 該当の検査項目のみ抽出
            String observationDefinitionId = jlacmap.get(jlac);
            boolean ret = this.findById(observationDefinitionId, dataInputTypeCd);
            if (ret) {
                target.setJLAC10(jlac);
                target.setObservationDefinitionId(jlacmap.get(jlac));
                target.setValue(observation.getValue());
                target.setUnit(observation.getUnit());
                if(observation.getHighrange() != null)target.setMaxReferenceValue(Double.parseDouble(observation.getHighrange()));
                if(observation.getLowrange() != null)target.setMinReferenceValue(Double.parseDouble(observation.getLowrange()));
                //特定健診においてH,Lの意味は入力範囲外かを見ているので不要
                if(observation.getValue() != null && (observation.getValue().equals("H") || observation.getValue().equals("L"))){
                    continue;
                }
                targetList.add(target);
            }
        }

        try{
            targetList = autoCalcService.autoCalcSet(phrid, insurerNo, Integer.parseInt(year), date, targetList);
//            this.observationEntryService.insertObservationAndObservationEvent(eventEntity, resultList);   //登録は後でまとめて
            ObservationEntryDto dto = new ObservationEntryDto();
            dto.setObservationEventEntity(eventEntity);
            dto.setObservationEntity(targetList);

            return dto;
        }catch(Exception e){
            logger.debug(e);
        }
       
        logger.debug("end");
        return null;
    }

    private void deleateCheckup(List<ObservationEventEntity> eventList) throws Throwable {
        logger.debug("delrate Start");

        specificMedicalCheakUpFormService.deleateCheckup(eventList);

        logger.debug("delrate End");
    }

    /**
     * zipファイル内のxmlファイルからPHRIDのリストを作成
     * @param xmlfile zipファイル(MultipartFile)
     * @param tmpPath 解凍先パス
     */
    private Map<String, List<String>> getPhrIdList(MultipartFile xmlfile, String tmpPath) throws Throwable {
        logger.debug("Start");
        
        //zipファイルの解凍
        Map<String, List<String>> phridList;
        Zip4jUtility obZipUtil=new Zip4jUtility();
        if(TypeUtility.isNullOrEmpty(tmpPath)){
            //一時フォルダ取得失敗
            return null;
        }else{
            //一時フォルダ作成
            String fileName;
            File tmpFile=new File(tmpPath);
            if(!tmpFile.mkdirs()){
                //失敗
                return null;
            }else{
                if (xmlfile.getSize() > 0) {
                    //アップロード(zip)ファイル保存
                    fileName = xmlfile.getOriginalFilename();
                    File saveFile = new File(tmpPath+"/"+fileName);
                    try(InputStream inStream=xmlfile.getInputStream();){
                        FileUtils.copyInputStreamToFile(inStream, saveFile);
                    }
                    
                    //解凍（パスワードなし）
                    obZipUtil.unzip(tmpPath+"/"+fileName, tmpPath, "");
		}else{
                    //失敗
                    return null;
                }
                
                //解凍ファイルからPHRIDリストを作成
                phridList=getPhrid(tmpPath);
            }
	}

        logger.debug("End");
        return phridList;
    }
    
    private Map<String, List<String>> getPhrid(String tmpPath) throws Throwable {
        logger.debug("Start");

        Map<String, List<String>> getMap=new HashMap<>();
        File rootFile = new File(tmpPath);
        File[] dirRoot = rootFile.listFiles();
        if (dirRoot==null){
            //ルートパスなし
            throw new NullPointerException();
        }
        for(File item:dirRoot){
            //ディレクトリ判定
            if(item.isDirectory()){
                //ディレクトにて再帰処理
                Map<String, List<String>> recursiveMap=getPhrid(item.getPath());
                if(!recursiveMap.isEmpty()){
                    //ループ回してKeyごとに格納
                    for(Map.Entry<String, List<String>> mapItem: recursiveMap.entrySet()){
                        List<String> putList = new ArrayList<>();
                        String mapKey=mapItem.getKey();
                        if(getMap.containsKey(mapKey)){
                            putList.addAll(getMap.get(mapKey));
                        }
                        putList.addAll(mapItem.getValue());
                        getMap.put(mapKey,putList); //同一キーありは上書き、ないは追加
                    }
                }
            }else{
                //xmlファイルはファイル名からPHRID取得
                if(item.getName().toUpperCase().contains(".XML")){
                    List<String> putList = new ArrayList<>();
                    String mapKey=item.getName().substring(0, item.getName().length()-4);
                    if(getMap.containsKey(mapKey)){
                        putList.addAll(getMap.get(mapKey));
                    }
                    putList.add(item.getAbsolutePath());
                    getMap.put(mapKey,putList); //同一キーありは上書き、ないは追加
                }
            }
        }
        logger.debug("End");
        return getMap;
    }
    
    //特定健診のzipファイルをServerに保存し、展開したファイル群は削除する
    private boolean moveDelFile(String tmpPath, String insurerNo, String fileName, boolean updateflg, boolean entryRetFlg)throws Throwable{
        logger.debug("Start");
        
        String path = PhrWebConfig.getPhrWebProperty(PhrWebConst.CHEAKUP_SAVE_PATH);
        if(entryRetFlg){
            //保存パスの生成処理
            if(!new File(path).exists()) return false;
            Date postDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
            String dateStr = sdf.format(postDate);
            path = path + "/" + insurerNo + "/zips/" + dateStr;
            File rootpath = new File(path);
            if(!rootpath.exists()) rootpath.mkdirs();

            //一時パスの削除処理
            File tmpFile=new File(tmpPath+"/"+fileName);
            try(InputStream inputStream = new FileInputStream(tmpFile);){
                File copyFile=new File(path+"/"+fileName);
                FileUtils.copyInputStreamToFile(inputStream, copyFile);
            } catch (IOException ex) {
                logger.error(ex);
                return false;
            }
        }
        //コピー元のファイル及びフォルダを全て削除
        File rootPath = new File(tmpPath);
        delFiles(rootPath, updateflg);
        rootPath.delete();

        logger.debug("End");
        return true;
    }

    //特定健診のzipファイルをServerに保存し、展開したファイル群は削除する
    private boolean beforeMoveDelFile(String tmpPath, String insurerNo, String fileName, boolean updateflg, boolean entryRetFlg, List<ObservationEntryDto> dtoList,boolean onlyinsertflg)throws Throwable{
        logger.debug("Start");
        boolean saveFile=true;
        if(onlyinsertflg){
//            //全部処理
            if(!updateflg){
                for(ObservationEntryDto edto:dtoList){
                    File file = new File(edto.getTempPath());
                    try(InputStream inputStream = new FileInputStream(file);){
                       saveFile = saveFile(
                                inputStream
                                , edto.getObservationEventEntity().getPHR_ID()
                                , edto.getObservationEventEntity().getInsurerNo()
                                , edto.getExaminationDate().replace("/", "")
                                , updateflg
                        );
                    }
                }
            }
        }else{
            //updateなのでdto.UpdateFlg==trueのみ処理
            for(ObservationEntryDto edto:dtoList){
                if(!edto.getSameFlg() || edto.getUpdateFlg()){
                    File file = new File(edto.getTempPath());
                    try(InputStream inputStream = new FileInputStream(file);){
                       saveFile = saveFile(
                                inputStream
                                , edto.getObservationEventEntity().getPHR_ID()
                                , edto.getObservationEventEntity().getInsurerNo()
                                , edto.getExaminationDate().replace("/", "")
                                , updateflg
                        );
                    }
                }
            }
        }
        if(!updateflg){
            moveDelFile(tmpPath, insurerNo, fileName, updateflg, entryRetFlg);
        }
        logger.debug("End");
        return true;
    }
    
    //ディレクトリ削除（フォルダは再帰処理し、内容を全て削除後、自分を削除）
    private void delFiles(File rootFile, boolean updateflg)throws Throwable{
        try{
            File[] dirRoot = rootFile.listFiles();
            if (dirRoot!=null){
                for(File item:dirRoot){
                    //ディレクトリ判定
                    if(item.isDirectory()){
                        //ディレクトにて再帰処理（戻ってきたらフォルダを削除）
                        delFiles(item, updateflg);
                        //フォルダ削除（再帰処理でファイルは消えている）
                        item.delete();
                    }else{
                        //ファイルは削除(重複問合わせ前はzipファイルは非削除)
                        if(updateflg && item.getAbsolutePath().toUpperCase().endsWith(".ZIP")){
                            continue;
                        }
                        item.delete();
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
            throw ex;
        }
    }

    /**
     * IDとデータ種別CDでレコードを取得
     * @param observationDefinitionId
     * @param dataInputTypeCd
     * @return
     * @throws Throwable 
     */
    private boolean findById(String observationDefinitionId, int dataInputTypeCd) throws Throwable{
        logger.debug("Start");
        
        boolean ret = false;
        ObservationDefinitionTypeEntity e = new ObservationDefinitionTypeEntity();
        DataAccessObject dao=null;
        try {
            dao = new DataAccessObject();
            ObservationDefinitionTypeAdapter adapter = new ObservationDefinitionTypeAdapter((dao.getConnection()));
            e = adapter.findById(observationDefinitionId, dataInputTypeCd);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try{
                if(dao!=null){dao.close();}
            }catch(Exception ex){
                logger.debug(e);
            }
        }
        
        if (e.getObservationDefinitionId() != null) {
            ret = true;
        }
        
        logger.debug("End");
        return ret;
    }
}
