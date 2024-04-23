/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：WEB用のコントローラー
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.entity.AccountEntity;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.IAccessLogsService;
import phr.service.ICommunicationService;
import phr.service.IDosageService;
import phr.service.IJLAC10AnalyteCodeService;
import phr.service.IJLAC11AnalyteCodeService;
import phr.service.IObservationDefinitionJlac10Service;
import phr.service.IObservationEntryService;
import phr.service.IObservationEventService;
import phr.service.IObservationService;
import phr.service.IOnetimePasswordService;
import phr.service.impl.OnetimePasswordService;
import phr.service.impl.OnetimePasswordService.OneTimePasswordResult;
import phr.utility.ObservationSupportUtility;
import phr.utility.TypeUtility;
import phr.web.ISessionUtility;
import phr.web.SessionConst;
import phr.web.dto.CommunicationDto;
import phr.web.dto.DosageDetailDto;
import phr.web.dto.ObservationDto;

/**
 *
 * @author KISNOTE011
 */
@Controller
public class PhrWebController {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PhrWebController.class);

    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    /**
     * インジェクション：検査結果項目サービス
     */
    @Autowired
    @Qualifier("ObservationEventService")
    private IObservationEventService observationEventService;

    /**
     * インジェクション：調剤情報サービス
     */
    @Autowired
    @Qualifier("DosageService")
    private IDosageService dosageService;

    /**
     * インジェクション：コミニケーションサービス
     */
    @Autowired
    @Qualifier("CommunicationService")
    private ICommunicationService communicationService;

    /**
     * インジェクション：ワンタイムパスワードサービス
     */
    @Autowired
    @Qualifier("OnetimePasswordService")
    private IOnetimePasswordService onetimePasswordService;

    /**
     * インジェクション：アクセスログサービス
     */
    @Autowired
    @Qualifier("AccessLogsService")
    private IAccessLogsService accessLogsService;

    /**
     * インジェクション：
     */
    @Autowired
    @Qualifier("ObservationEntryService")
    private IObservationEntryService observationEntryService;
    
    @Autowired
    @Qualifier("ObservationService")
    private IObservationService observationService;
    
    private static final String EXAM_BACKGROUND_COLOR = PhrConfig.getConfigProperty(ConfigConst.EXAM_BACKGROUND_COLOR);
    private static final String SELF_BACKGROUND_COLOR = PhrConfig.getConfigProperty(ConfigConst.SELF_BACKGROUND_COLOR);
    private static final String CHECKUP_BACKGROUND_COLOR = PhrConfig.getConfigProperty(ConfigConst.CHECKUP_BACKGROUND_COLOR);
    private static final String ALERT_BACKGROUND_COLOR = PhrConfig.getConfigProperty(ConfigConst.ALERT_BACKGROUND_COLOR);
    
    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"getJsonData"}, method = {RequestMethod.POST})
    @ResponseBody
    public String ajaxDefaultAction(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        logger.debug("Start");

        String getAllStr=null;
        //リクエストJSONの取得
        try(Reader reqReader=request.getReader();
                BufferedReader reqBufReader =  new BufferedReader(reqReader);){
            StringBuilder sBuilder = new StringBuilder();
            String getStr;
            do{
                //１行読む
                getStr = reqBufReader.readLine();
                if(getStr!=null){
                    sBuilder.append(getStr);
                }
            }while (getStr != null);
            getAllStr=sBuilder.toString();
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }

        String value=null;
        int viewId = 0;
        int allDataNum = 0;
        //リクエストJSONから値の取得
        try{
            String[] spStrs=getAllStr.split(",");
            for(String item:spStrs){
                String[] keyValues=item.split(":");
                String keyValue=commonReplace(keyValues[0]);
                if(keyValue.equals("value1")){
                    //必要な値を取得
                    value=commonReplace(keyValues[1]);
                    value=value+" 23:59:59";
                    logger.debug(value);
                }
                if(keyValue.equals("viewId")){
                    //必要な値を取得
                    viewId=Integer.parseInt(commonReplace(keyValues[1]));
                    logger.debug(viewId);
                }
                if(keyValue.equals("nextDataNum")) {
                    allDataNum = Integer.parseInt(commonReplace(keyValues[1]));
                    logger.debug(allDataNum);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }

        //要求に応じた情報をJSON形式文字列にして戻す
//        OneTimePasswordResult getOTPR=(OneTimePasswordResult)sessionUtility.getSession(SessionConst.VIEW_PASSWORD_INFO);
        MedicalOrganizationPatientEntity getOTPR=(MedicalOrganizationPatientEntity)sessionUtility.getSession(SessionConst.AGREE_PATIET_INFO);
        Timestamp object;
        String returnJson="";
        List<ObservationDto> dto = new ArrayList<>();
        try{
            Integer year=0;
            if(value!=null){
                String[] baseDate=value.split("/");
                year = Integer.parseInt(baseDate[0]);
                Integer month = Integer.parseInt(baseDate[1]);
                if(month <= 3){
                    year = year - 1;
                }
            }
            object = new Timestamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(value).getTime());
            List<ObservationEventEntity> noSetEntity=observationEventService.searchObsevationByOrderNo(year, getOTPR.getPHR_ID(), object , viewId);

            //            noSetEntity = ObservationSupportUtility.mergeList(noSetEntity);
            List<ObservationEventEntity> obEntities = ObservationSupportUtility.setObsevationKensiToKensa(noSetEntity, noSetEntity);
            int i =0;
            int iKensin =0;
            Timestamp hikakuTS = new Timestamp(0);
            Calendar cal_date = Calendar.getInstance();
            Timestamp lastDay=null;
            boolean lastNoSetFlg=true;
            for(ObservationEventEntity entity:obEntities){
                if(entity.getDataInputTypeCd()==1 && entity.getExaminationDate()==null){
                    continue;
                }

                if (!TypeUtility.isNullOrEmpty( entity.getObservationValue())) {
                    //取得情報
                    dto.add(new ObservationDto());
                    dto.get(i).setId(entity.getObservationDefinitionId());
                    String sExaminationDate;
                    if(entity.getDataInputTypeCd()==2){
                        sExaminationDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(entity.getExaminationDate());
                        dto.get(i).setEventId(entity.getObservationEventId());  //家庭測定はEventIDも取得
                    }else{
                        sExaminationDate = new SimpleDateFormat("yyyy/MM/dd").format(entity.getExaminationDate());
                    }
                    dto.get(i).setExaminationDate(sExaminationDate);
                    if(entity.getObservationValue()==null){
                        dto.get(i).setValue("");
                    }else{
                        dto.get(i).setValue(entity.getObservationValue());
                    }
                    if(entity.getUnitValue()==null || entity.getObservationValue()==null || entity.getObservationValue().isEmpty()){
                        dto.get(i).setUnit("");
                    }else{
                        dto.get(i).setUnit(entity.getUnitValue());
                    }
                    dto.get(i).setAlertLevel(entity.getAlertLevelCd());
                    if(entity.getReminderTypeCd()!=null && entity.getReminderTypeCd()!=0 && entity.getReminderTypeCd()!=99){
                        Timestamp nowTimeStamp = new Timestamp(System.currentTimeMillis());
                        Date nowDate = new Date(nowTimeStamp.getTime());
                        cal_date.setTime(nowDate);
                        cal_date.add(Calendar.MONTH, -1*entity.getReminderTypeCd());
                        if(entity.getNewExaminationDate().getTime()<cal_date.getTime().getTime()){
                            //リマインダ対象
                            dto.get(i).setReminderFlg(true);
                        }else{
                            dto.get(i).setReminderFlg(false);
                        }
                    }
                    dto.get(i).setDataInputTypeCd(entity.getDataInputTypeCd());
                    dto.get(i).setDispPos(entity.getDispNo());
                    //検査結果(1)と健診(3)の過去日検索の日付を取得し、各項目の最初にセット
                    if(entity.getDataInputTypeCd()==1){
                        if(entity.getExaminationDate().after(hikakuTS)){
                            //最終的に過去日の一番未来日が残る
                            hikakuTS=entity.getExaminationDate();
                            //セットはループ完了後
                        }
                    }else if(entity.getDataInputTypeCd()==3){
                        if(iKensin==0 && lastDay==null){
                            //健診の先頭インデックスを記憶
                            iKensin=i;
                            lastDay=entity.getExaminationDate();
                        }else if(lastDay!=null && entity.getDispNo()==3 && lastNoSetFlg){
                            lastNoSetFlg=false;
                            List<ObservationEventEntity> eLates=observationEventService.searchObsevationByFutureDay(
                                    getOTPR.getPHR_ID(), entity.getExaminationDate(),1); //過去直近
                            for (ObservationEventEntity item : eLates) {
                                if(item.getDataInputTypeCd()==3){
                                    dto.get(iKensin).setRightFlg(
                                            new SimpleDateFormat("yyyy/MM/dd").format(
                                                    new Timestamp(lastDay.getTime()-86400000)));   //1日減算
                                }
                            }
                        }
                    }
                    i+=1;
                }
            }
            if(!hikakuTS.equals(new Timestamp(0))){
                List<ObservationEventEntity> eKensaLates=observationEventService.searchObsevationByFutureDay(getOTPR.getPHR_ID(), hikakuTS,1); //過去直近
                for (ObservationEventEntity item : eKensaLates) {
                    if(item.getDataInputTypeCd()==1){
                        dto.get(0).setRightFlg(
                                new SimpleDateFormat("yyyy/MM/dd").format(
                                        new Timestamp(hikakuTS.getTime()-86400000)));   //1日減算
                        break;  //検査結果の過去がある場合は終了
                    }else{
                        //検査結果がない場合、健診の過去を設定
                        dto.get(0).setRightFlg(
                                new SimpleDateFormat("yyyy/MM/dd").format(
                                        new Timestamp(hikakuTS.getTime()-86400000)));   //1日減算
                    }
                }
            }
            //検査結果(1)と健診(3)の未来日検索の日付を取得し、各項目の最初にセット
            List<ObservationEventEntity> eFutures=observationEventService.searchObsevationByFutureDay(getOTPR.getPHR_ID(), object,0); //未来直近
            boolean kensaNoset=true;    //検査結果の未来日[1]があったか判定用
            if(obEntities.size()>0 && dto.size() > 0){
                dto.get(0).setLeftFlg("");
                dto.get(iKensin).setLeftFlg("");
                for(ObservationEventEntity eFuture:eFutures){
                    if(eFuture.getDataInputTypeCd()==1){
                        if(dto.get(0).getDataInputTypeCd()==1){
                            //最初が検査結果項目の場合のみ設定
                            dto.get(0).setLeftFlg(
                                    new SimpleDateFormat("yyyy/MM/dd").format(
                                            new Timestamp(eFuture.getExaminationDate().getTime())));
                        }else{
                            //未来日付あるのに設定箇所がない場合、追加
                            dto.add(new ObservationDto());
                            dto.get(dto.size()-1).setDataInputTypeCd(1);
                            dto.get(dto.size()-1).setLeftFlg(
                                    new SimpleDateFormat("yyyy/MM/dd").format(
                                            new Timestamp(eFuture.getExaminationDate().getTime())));
                            i+=1;
                        }
                        kensaNoset=false;   //検査結果の未来日ありをセット
                    }else if(eFuture.getDataInputTypeCd()==3){
                        if(lastDay!=null && dto.get(iKensin).getDataInputTypeCd()==3){
                            //健診の最初の項目に設定
                            dto.get(iKensin).setLeftFlg(
                                    new SimpleDateFormat("yyyy/MM/dd").format(
                                            new Timestamp(eFuture.getExaminationDate().getTime())));
                        }else{
                            //未来日付あるのに設定箇所がない場合、追加
                            dto.add(new ObservationDto());
                            dto.get(dto.size()-1).setDataInputTypeCd(3);
                            dto.get(dto.size()-1).setLeftFlg(
                                    new SimpleDateFormat("yyyy/MM/dd").format(
                                            new Timestamp(eFuture.getExaminationDate().getTime())));
                            i+=1;
                        }
                        if(kensaNoset){
                            if(dto.get(0).getDataInputTypeCd()==1){
                                //最初が検査結果項目の場合のみ設定
                                dto.get(0).setLeftFlg(
                                        new SimpleDateFormat("yyyy/MM/dd").format(
                                                new Timestamp(eFuture.getExaminationDate().getTime())));
                            }else{
                                //未来日付あるのに設定箇所がない場合、追加
                                dto.add(new ObservationDto());
                                dto.get(dto.size()-1).setDataInputTypeCd(1);
                                dto.get(dto.size()-1).setLeftFlg(
                                        new SimpleDateFormat("yyyy/MM/dd").format(
                                                new Timestamp(eFuture.getExaminationDate().getTime())));
                                i+=1;
                            }
                        }
                    }
                }
            }
            java.sql.Date sqlDt = new  java.sql.Date(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(value).getTime());
            List<DosageEntity> dosageEntity = dosageService.searchDosageByPhrid(getOTPR.getPHR_ID(), sqlDt);
            sessionUtility.setSession(SessionConst.DOSAGE_ENTITIES, dosageEntity);
            for(DosageEntity entity:dosageEntity){
                //取得情報
                dto.add(new ObservationDto());
                dto.get(i).setId(entity.getDosageId());
                dto.get(i).setSeq(entity.getSeq());
                dto.get(i).setExaminationDate(new SimpleDateFormat("yyyy/MM/dd").format(entity.getDosageDate()));
                String tmpStr=entity.getPharmacy();
                if(TypeUtility.isNullOrEmpty(tmpStr)){
                    dto.get(i).setName("");
                }else{
                    dto.get(i).setName(tmpStr);
                }
                dto.get(i).setDataInputTypeCd(-1);  //おくすりはこの処理に限り「-1」に設定
                i+=1;
            }
            int targetFlg;
            String targetCd;
            AccountEntity getEntity=(AccountEntity)sessionUtility.getSession(SessionConst.ACCOUNT_ENTITY);
            if(getEntity==null){
                MedicalOrganizationEntity getMediEntity=(MedicalOrganizationEntity)sessionUtility.getSession(SessionConst.MEDICALACCOUNT_ENTITY);
                targetFlg=CommunicationEntity.TargetFlgEnum.MEDICAL.getIntValue();
                targetCd=getMediEntity.getMedicalOrganizationCd();
            }else{
                targetFlg=CommunicationEntity.TargetFlgEnum.INSURER.getIntValue();
                targetCd=getEntity.getInsurerNo();
            }
            List<CommunicationEntity> communicationEntity = communicationService.SearchCommunicationByPhridOrganization(getOTPR.getPHR_ID(), targetCd, object, targetFlg);
            sessionUtility.setSession(SessionConst.COMMUNICAATION_ENTITIES, communicationEntity);
            for(CommunicationEntity entity:communicationEntity){
                //取得情報
                dto.add(new ObservationDto());
                dto.get(i).setId(entity.getCommunicationId());
                dto.get(i).setExaminationDate(new SimpleDateFormat("yyyy/MM/dd").format(entity.getCreateDateTime()));
                String tmpStr=entity.getSenderName();
                if(TypeUtility.isNullOrEmpty(tmpStr)){
                    dto.get(i).setName("");
                }else{
                    dto.get(i).setName(tmpStr);
                }
                tmpStr=entity.getSendOrganizationName();
                if(TypeUtility.isNullOrEmpty(tmpStr)){
                    //そのまま
                }else{
                    dto.get(i).setName(tmpStr);
                }

                tmpStr=entity.getSubject();
                if(tmpStr.isEmpty()){
                    dto.get(i).setTitle("");
                }else{
                    dto.get(i).setTitle(tmpStr);
                }
                dto.get(i).setDataInputTypeCd(-2);  //コミニケーションはこの処理に限り「-2」に設定
                dto.get(i).setSeq(entity.getSeqNo());
                dto.get(i).setReadFlg(entity.getReadFlg());
                if(targetCd.equals(entity.getSendInsurerNo()) 
                        || targetCd.equals(entity.getSendMedicalOrganizationCd())){
                    dto.get(i).setReadFlg(true);
                }
                i+=1;
            }
            if(obEntities.isEmpty() && i>0){
                //検査、健診、家庭が全くない場合でも、おしらせかおくすりがあった場合
                kensaNoset=true;
                for(ObservationEventEntity eNullFuture:eFutures){
                    if(eNullFuture.getDataInputTypeCd()==1){
                        //未来日付あるのに設定箇所がない場合、追加
                        dto.add(new ObservationDto());
                        dto.get(dto.size()-1).setDataInputTypeCd(1);
                        dto.get(dto.size()-1).setLeftFlg(
                                new SimpleDateFormat("yyyy/MM/dd").format(
                                        new Timestamp(eNullFuture.getExaminationDate().getTime())));
                        kensaNoset=false;
                    }else if(eNullFuture.getDataInputTypeCd()==3){
                        //未来日付あるのに設定箇所がない場合、追加
                        dto.add(new ObservationDto());
                        dto.get(dto.size()-1).setDataInputTypeCd(3);
                        dto.get(dto.size()-1).setLeftFlg(
                                new SimpleDateFormat("yyyy/MM/dd").format(
                                        new Timestamp(eNullFuture.getExaminationDate().getTime())));
                        if(kensaNoset){
                            //未来日付あるのに設定箇所がない場合、追加
                            dto.add(new ObservationDto());
                            dto.get(dto.size()-1).setDataInputTypeCd(1);
                            dto.get(dto.size()-1).setLeftFlg(
                                    new SimpleDateFormat("yyyy/MM/dd").format(
                                            new Timestamp(eNullFuture.getExaminationDate().getTime())));
                        }
                    }
                }
            }
            
            // 全検査タブのデータを取得
            // 対象患者の検査結果リストを取得
            List<ObservationEventEntity> observationEventIdList = observationEventService.searchObservationEventList(getOTPR.getPHR_ID());

            //ObservationEventEntityのリストで取得
            List<ObservationEntity> entityList  = new ArrayList<ObservationEntity>();
            int dataNum = allDataNum;
            while (entityList.size() == 0) {
                if (dataNum > observationEventIdList.size()) 
                    break;

                // 全検査タブのデータを取得(DataInputTypeCd⇒自己管理：1、自己測定:2、特定検診:3)
                if (observationEventIdList.get(dataNum).getDataInputTypeCd() == 1) {
                    entityList = observationService.getObservation1ByObservationEventId(observationEventIdList.get(dataNum).getObservationEventId());
                } else {
                    entityList = observationService.getObservation23ByObservationEventId(observationEventIdList.get(dataNum).getObservationEventId());
                }
                if (entityList.size() == 0)
                    dataNum++;
            }

            int rightNum = -1;
            int leftNum = -1;
            if (entityList.size() > 0) {
                // 次のデータ確認
                for (int num = dataNum+1; num < observationEventIdList.size(); num++) {
                    // 全検査タブのデータを取得
                    List<ObservationEntity> rightEntityList = new ArrayList<ObservationEntity>();
                    if (observationEventIdList.get(num).getDataInputTypeCd() == 1) {
                        rightEntityList = observationService.getObservation1ByObservationEventId(observationEventIdList.get(num).getObservationEventId());
                    } else {
                        rightEntityList = observationService.getObservation23ByObservationEventId(observationEventIdList.get(num).getObservationEventId());
                    }
                    if (rightEntityList.size() > 0) {
                        rightNum = num;
                        break;
                    }
                }

                // 前のデータ確認
                for (int num = dataNum-1; num > -1; num--) {
                    // 全検査タブのデータを取得
                    List<ObservationEntity> leftEntityList = new ArrayList<ObservationEntity>();
                            if (observationEventIdList.get(num).getDataInputTypeCd() == 1) {
                                leftEntityList = observationService.getObservation1ByObservationEventId(observationEventIdList.get(num).getObservationEventId());
                            } else {
                                leftEntityList = observationService.getObservation23ByObservationEventId(observationEventIdList.get(num).getObservationEventId());
                            }
                    
                    if (leftEntityList.size() > 0) {
                        leftNum = num;
                        break;
                    }
                }
            }

            int dtoNum = dto.size();
            for (ObservationEntity entity : entityList) {
                dto.add(new ObservationDto());
                
                dto.get(dtoNum).setMedicalOrganizationName(entity.getMedicalOrganizationName());
                
                // DataInputTypeCd⇒自己管理：-3、自己測定:-4、特定検診:-5とする
                if (entity.getDataInputTypeCd() == 1) {
                    dto.get(dtoNum).setDataInputTypeCd(-3);
                    dto.get(dtoNum).setHeaderColor(EXAM_BACKGROUND_COLOR);// 青
                } else if (entity.getDataInputTypeCd() == 2) {
                    dto.get(dtoNum).setDataInputTypeCd(-4);
                    dto.get(dtoNum).setHeaderColor(SELF_BACKGROUND_COLOR);// オレンジ
                } else if (entity.getDataInputTypeCd() == 3 || entity.getDataInputTypeCd() == 4 || entity.getDataInputTypeCd() == 5) {
                    dto.get(dtoNum).setDataInputTypeCd(-5);
                    dto.get(dtoNum).setHeaderColor(CHECKUP_BACKGROUND_COLOR);// 緑
                }
                
                dto.get(dtoNum).setEventId(entity.getObservationEventId());
                dto.get(dtoNum).setExaminationDate(new SimpleDateFormat("yyyy/MM/dd").format(entity.getExaminationDate()));
                dto.get(dtoNum).setId(entity.getObservationDefinitionId());
                
                boolean setColor = true;
                String unitValue = (entity.getValue());
                if (!TypeUtility.isNullOrEmpty(entity.getUnit1())) {
                    unitValue = unitValue + " "+ entity.getUnit1();
                } else {
                    setColor = false;
                }
                dto.get(dtoNum).setValue(unitValue);
                dto.get(dtoNum).setName(entity.getObservationDefinitionName());

                // 基準値
                String refVal = "";
                Double MaxReferenceValue = null;
                Double MinReferenceValue = null;;
                if (TypeUtility.isNullOrEmpty(entity.getReferenceEnumName())) {
                    // 基準値がobservationにある
                    if (!(entity.getMinReferenceValue1() == null && entity.getMaxReferenceValue1() == null)) {
                        if(entity.getMinReferenceValue1() != null && entity.getMaxReferenceValue1() != null) {
                            refVal = entity.getMinReferenceValue1() + " ~ " + entity.getMaxReferenceValue1();
                        } else if (entity.getMinReferenceValue1() == null) {
                            refVal = "~ " + entity.getMaxReferenceValue1();
                        } else {
                            refVal = entity.getMinReferenceValue1() + " ~";
                        }
                        MaxReferenceValue = entity.getMaxReferenceValue1();
                        MinReferenceValue = entity.getMinReferenceValue1();
                    } else {
                        // 基準値がマスタにある(マスタ上の単位がobservation上の単位と一致しない場合のみ、単位も併せて表示)
                        if (!(entity.getMinReferenceValue2() == null && entity.getMaxReferenceValue2() == null)) {
                            if(entity.getMinReferenceValue2() != null && entity.getMaxReferenceValue2() != null) {
                                refVal = entity.getMinReferenceValue2() + " ~ " + entity.getMaxReferenceValue2();
                                if (!entity.getUnit2().equals(entity.getUnit1())) {
                                    refVal = refVal + " " + entity.getUnit2();
                                    setColor = false;
                                }
                            } else if (entity.getMinReferenceValue2() == null) {
                                refVal = "~ " + entity.getMaxReferenceValue2();
                                if (!entity.getUnit2().equals(entity.getUnit1())) {
                                    refVal = refVal + " " + entity.getUnit2();
                                    setColor = false;
                                }
                            } else {
                                refVal = entity.getMinReferenceValue2() + " ~";
                                if (!entity.getUnit2().equals(entity.getUnit1())) {
                                    refVal = refVal + " " + entity.getUnit2();
                                    setColor = false;
                                }
                            }
                        }
                        MaxReferenceValue = entity.getMaxReferenceValue2();
                        MinReferenceValue = entity.getMinReferenceValue2();
                    }
                } else {
                    refVal = entity.getReferenceEnumName();
                    setColor = false;
                }
                dto.get(dtoNum).setReferenceValue(refVal);
                
                // 値の背景色
                String bgColor = null;
                if (setColor) {
                    //入力値
                    double obsValue = Double.parseDouble(entity.getValue());
                    if (MaxReferenceValue != null && MinReferenceValue != null) {
                        if (MaxReferenceValue < obsValue || MinReferenceValue > obsValue)
                            bgColor = ALERT_BACKGROUND_COLOR;
                    } else if (MaxReferenceValue == null && MinReferenceValue != null) {
                        if (MinReferenceValue > obsValue)
                            bgColor = ALERT_BACKGROUND_COLOR;
                    } else if (MaxReferenceValue != null && MinReferenceValue == null) {
                        if (MaxReferenceValue < obsValue)
                            bgColor = ALERT_BACKGROUND_COLOR;
                    }
                }
                dto.get(dtoNum).setAlertColor(bgColor);
                
                if (rightNum == -1) {
                    dto.get(dtoNum).setRightFlg(null);
                } else {
                    dto.get(dtoNum).setRightFlg(Integer.toString(rightNum));
                }
                if (leftNum == -1) {
                    dto.get(dtoNum).setLeftFlg(null);
                } else {
                    dto.get(dtoNum).setLeftFlg(Integer.toString(leftNum));
                }
                dtoNum++;
            }

            returnJson = gson.toJson(dto);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }

        logger.debug("End");
        return returnJson;
    }

    /**
     * おくすり情報の詳細を取得する
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"getJsonDataKusuri"}, method = {RequestMethod.POST})
    @ResponseBody
    public String ajaxKusuriDataAction(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        logger.debug("Start");

        String getAllStr=null;
        //リクエストJSONの取得
        try(Reader reqReader=request.getReader();
                BufferedReader reqBufReader =  new BufferedReader(reqReader);){
            StringBuilder sBuilder = new StringBuilder();
            String getStr;
            do{
                //１行読む
                getStr = reqBufReader.readLine();
                if(getStr!=null){
                    sBuilder.append(getStr);
                }
            }while (getStr != null);
            getAllStr=sBuilder.toString();
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }

        String value=null;
        String seqNo=null;
        //リクエストJSONから値の取得
        try{
            String[] spStrs=getAllStr.split(",");
            for(String item:spStrs){
                String[] keyValues=item.split(":");
                String keyValue=commonReplace(keyValues[0]);
                if(keyValue.equals("value1")){
                    //必要な値を取得
                    value=commonReplace(keyValues[1]);
                    logger.debug(value);
                }
                if(keyValue.equals("value2")){
                    //必要な値を取得
                    seqNo=commonReplace(keyValues[1]);
                    logger.debug(seqNo);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }

        //要求に応じた情報をJSON形式文字列にして戻す
//        OneTimePasswordResult getOTPR=(OneTimePasswordResult)sessionUtility.getSession(SessionConst.VIEW_PASSWORD_INFO);
        MedicalOrganizationPatientEntity getOTPR=(MedicalOrganizationPatientEntity)sessionUtility.getSession(SessionConst.AGREE_PATIET_INFO);
        String returnJson="";
        List<DosageDetailDto> dto = new ArrayList<>();
        try{
            //            object = new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(value).getTime());
            List<DosageEntity> obEntities=dosageService.searchDosageByDosageIdSeqPhrid(value, seqNo, getOTPR.getPHR_ID());
            int i =0;
            for(DosageEntity item:obEntities){
                //取得情報
                dto.add(new DosageDetailDto());
                dto.get(i).setDosageId(item.getDosageId());
                dto.get(i).setSeq(item.getSeq());
                dto.get(i).setPHR_ID(item.getPHR_ID());
                dto.get(i).setDosageDate(new SimpleDateFormat("yyyy/MM/dd").format(item.getDosageDate()));

                String tmpStr=item.getPharmaceutistEntity().getPharmacy();
                if(TypeUtility.isNullOrEmpty(tmpStr)){
                    dto.get(i).setPharmacy("");
                }else{
                    dto.get(i).setPharmacy(tmpStr);
                }
                tmpStr=item.getPharmaceutistEntity().getPharmaceutistName();
                if(TypeUtility.isNullOrEmpty(tmpStr)){
                    dto.get(i).setPharmaceutistName("");
                }else{
                    dto.get(i).setPharmaceutistName(tmpStr);
                }

                dto.get(i).setMedicalOrganizationCd(item.getDosageMedicalOrganizationEntity().getMedicalOrganizationCd());
                tmpStr=item.getDosageMedicalOrganizationEntity().getMedicalOrganizationName();
                if(TypeUtility.isNullOrEmpty(tmpStr)){
                    dto.get(i).setMedicalOrganizationName("");
                }else{
                    dto.get(i).setMedicalOrganizationName(tmpStr);
                }
                dto.get(i).setDosageTypeCd(item.getDosageMedicalOrganizationEntity().getDosageTypeCd());

                tmpStr=item.getDosageDoctorEntity().getDoctorName();
                if(TypeUtility.isNullOrEmpty(tmpStr)){
                    dto.get(i).setDoctorName("");
                }else{
                    dto.get(i).setDoctorName(tmpStr);
                }

                tmpStr=item.getDosageRemarkEntity().getRemarkText();
                if(TypeUtility.isNullOrEmpty(tmpStr)){
                    dto.get(i).setDosageRemark("");
                }else{
                    dto.get(i).setDosageRemark(tmpStr);
                }

                dto.get(i).setRecipeNo(item.getDosageRecipeEntity().getRecipeNo());
                dto.get(i).setUsageName(item.getDosageRecipeEntity().getUsageName());
                dto.get(i).setDosageQuantity(item.getDosageRecipeEntity().getDosageQuantity());
                dto.get(i).setDosageUnit(item.getDosageRecipeEntity().getDosageUnit());
                String dosageFormsName="一般用医薬品";
                if(item.getDosageRecipeEntity().getDosageForms()!=null){
                    switch(item.getDosageRecipeEntity().getDosageForms()){
                    case "1":
                        //1内服
                        dosageFormsName="内服";
                        break;
                    case "2":
                        //2内滴
                        dosageFormsName="内滴";
                        break;
                    case "3":
                        //3屯服
                        dosageFormsName="屯服";
                        break;
                    case "4":
                        //4注射
                        dosageFormsName="注射";
                        break;
                    case "5":
                        //5外用
                        dosageFormsName="外用";
                        break;
                    case "6":
                        //6浸煎
                        dosageFormsName="浸煎";
                        break;
                    case "7":
                        //7湯
                        dosageFormsName="湯";
                        break;
                    case "9":
                        //9材料
                        dosageFormsName="材料";
                        break;
                    case "10":
                        //10その他
                        dosageFormsName="その他";
                        break;
                    default:
                        dosageFormsName="";
                        break;
                    }
                }
                dto.get(i).setDosageForms(dosageFormsName);

                dto.get(i).setMedicineSeq(item.getDosageMedicineEntity().getMedicineSeq());
                dto.get(i).setMedicineName(item.getDosageMedicineEntity().getMedicineName());
                dto.get(i).setAmount(item.getDosageMedicineEntity().getAmount());
                dto.get(i).setUnitName(item.getDosageMedicineEntity().getUnitName());
                dto.get(i).setMedicineCdType(item.getDosageMedicineEntity().getMedicineCdType());
                dto.get(i).setMedicineCd(item.getDosageMedicineEntity().getMedicineCd());
                dto.get(i).setRecordCreatorType(item.getDosageMedicineEntity().getRecordCreatorType());

                dto.get(i).setNonPreMedicineName(item.getNonPrescriptionDrugsEntity().getMedicineName());
                dto.get(i).setNonPreRecordCreatorType(item.getNonPrescriptionDrugsEntity().getRecordCreatorType());
                if(dto.get(i).getNonPreMedicineName()!=null){
                    dto.get(i).setMedicineName(dto.get(i).getNonPreMedicineName());
                    dto.get(i).setUsageName("");
                    dto.get(i).setDosageQuantity(-1);
                    dto.get(i).setDosageUnit("");
                    dto.get(i).setAmount("");
                    dto.get(i).setUnitName("");
                }

                if(i==0){
                    List<DosageEntity> dosageEntity = (List<DosageEntity>)sessionUtility.getSession(SessionConst.DOSAGE_ENTITIES);
                    if(dosageEntity==null || dosageEntity.size()==1){
                        dto.get(0).setLeftFlg(null);
                        dto.get(0).setRightFlg(null);
                    }else{
                        boolean bHit=false;
                        String lFlg=null;
                        String rFlg=null;
                        for(DosageEntity item2:dosageEntity){
                            if(item.getDosageId().equals(item2.getDosageId()) && 
                                    item.getSeq()==item2.getSeq()){
                                bHit=true;
                            }else if(bHit){
                                rFlg="\'"+item2.getDosageId()+"\',\'"+String.valueOf(item2.getSeq())+"\'";
                                break;
                            }else if(bHit==false){
                                lFlg="\'"+item2.getDosageId()+"\',\'"+String.valueOf(item2.getSeq())+"\'";
                            }
                        }
                        dto.get(0).setLeftFlg(lFlg);
                        dto.get(0).setRightFlg(rFlg);
                    }
                }

                i+=1;
            }
            returnJson = gson.toJson(dto);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }

        logger.debug("End");
        return returnJson;
    }

    /**
     * コミュニケーション情報の詳細を取得する
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"getJsonDataCommu"}, method = {RequestMethod.POST})
    @ResponseBody
    public String ajaxCommuDataAction(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        logger.debug("Start");

        String getAllStr=null;
        //リクエストJSONの取得
        try(Reader reqReader=request.getReader();
                BufferedReader reqBufReader =  new BufferedReader(reqReader);){
            StringBuilder sBuilder = new StringBuilder();
            String getStr;
            do{
                //１行読む
                getStr = reqBufReader.readLine();
                if(getStr!=null){
                    sBuilder.append(getStr);
                }
            }while (getStr != null);
            getAllStr=sBuilder.toString();
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }

        String value=null;
        String seqNo=null;
        String readFlg=null;
        //リクエストJSONから値の取得
        try{
            String[] spStrs=getAllStr.split(",");
            for(String item:spStrs){
                String[] keyValues=item.split(":");
                String keyValue=commonReplace(keyValues[0]);
                if(keyValue.equals("value1")){
                    //必要な値を取得
                    value=commonReplace(keyValues[1]);
                    logger.debug(value);
                }
                if(keyValue.equals("value2")){
                    //必要な値を取得
                    seqNo=commonReplace(keyValues[1]);
                    logger.debug(seqNo);
                }
                if(keyValue.equals("value3")){
                    //必要な値を取得
                    readFlg=commonReplace(keyValues[1]);
                    logger.debug(readFlg);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }

        //要求に応じた情報をJSON形式文字列にして戻す
        String returnJson="";
        List<CommunicationDto> dto=new ArrayList<>();
        try{
            if(value==null){
                value="";
            }
            List<CommunicationEntity> obEntities=communicationService.SearchCommunicationByCommuidAndSeq(value,Integer.parseInt(seqNo));
            //取得情報
            for(CommunicationEntity obEntity:obEntities){
                dto.add(new CommunicationDto());
                dto.get(0).setCommunicationId(obEntity.getCommunicationId());
                dto.get(0).setCommunicationTypeCd(obEntity.getCommunicationTypeCd());
                dto.get(0).setSendPHRID(obEntity.getSendPHRID());
                dto.get(0).setSendInsurerNo(obEntity.getSendInsurerNo());
                dto.get(0).setSendAccountId(obEntity.getSendAccountId());
                dto.get(0).setSendMedicalOrganizationCd(obEntity.getSendMedicalOrganizationCd());
                String tmpStr=obEntity.getSenderName();
                if(TypeUtility.isNullOrEmpty(tmpStr)){
                    dto.get(0).setSenderName("");
                }else{
                    dto.get(0).setSenderName(tmpStr);
                }
                dto.get(0).setSubject(obEntity.getSubject());
                dto.get(0).setBodyText(obEntity.getBodyText());
                dto.get(0).setCreateDateTime(obEntity.getCreateDateTime());
                dto.get(0).setUpdateDateTime(obEntity.getUpdateDateTime());

                dto.get(0).setSeqNo(obEntity.getSeqNo());
                dto.get(0).setReadFlg(false);   //更新してない場合は未読で返却
                if(obEntity.getReadFlg()==false && TypeUtility.isNullOrEmpty(obEntity.getPhrid())){
                    //未読を既読にする(患者指定画面からなので医療機関または保険者宛のものが対象)
                    CommunicationReceiverEntity upEntity = new CommunicationReceiverEntity();
                    upEntity.setCommunicationId(obEntity.getCommunicationId());
                    upEntity.setCreateDateTime(obEntity.getCreateDateTime());
                    if(TypeUtility.isNullOrEmpty(obEntity.getInsurerNo())){
                        upEntity.setInsurerNo(null);
                    }else{
                        upEntity.setInsurerNo(obEntity.getInsurerNo());
                    }
                    if(TypeUtility.isNullOrEmpty(obEntity.getMedicalOrganizationCd())){
                        upEntity.setMedicalOrganizationCd(null);
                    }else{
                        upEntity.setMedicalOrganizationCd(obEntity.getMedicalOrganizationCd());
                    }
                    if(TypeUtility.isNullOrEmpty(obEntity.getPhrid())){
                        upEntity.setPHR_ID(null);
                    }else{
                        upEntity.setPHR_ID(obEntity.getPhrid());
                    }
                    upEntity.setReadFlg(true);
                    upEntity.setSeq(obEntity.getSeqNo());
                    upEntity.setUpdateDateTime(obEntity.getRecUpdateDateTime());

                    //更新
                    if(communicationService.UpdateCommunicationByReadFlg(upEntity)!=1){
                        logger.error("コミュニケーション情報既読化失敗");
                    }else{
                        //更新したので既読で返却
                        dto.get(0).setReadFlg(true);
                    }
                }
                tmpStr=obEntity.getSendOrganizationName();
                if(TypeUtility.isNullOrEmpty(tmpStr)){
                    dto.get(0).setSendOrganizationName("");
                }else{
                    dto.get(0).setSendOrganizationName(tmpStr);
                }
                dto.get(0).setStrUpdateDateTime(new SimpleDateFormat("yyyy/MM/dd").format(obEntity.getCreateDateTime()));
                if(obEntity.getCommunicationTypeCd()==1){
                    dto.get(0).setCommunicationTypeName("おしらせ");
                }else{
                    dto.get(0).setCommunicationTypeName("メッセージ");
                }
                List<CommunicationEntity> communicationEntity = (List<CommunicationEntity>)sessionUtility.getSession(SessionConst.COMMUNICAATION_ENTITIES);
                if(communicationEntity==null || communicationEntity.size()==1){
                    dto.get(0).setLeftFlg(null);
                    dto.get(0).setRightFlg(null);
                }else{
                    boolean bHit=false;
                    String lFlg=null;
                    String rFlg=null;
                    for(CommunicationEntity item:communicationEntity){
                        if(obEntity.getCommunicationId().equals(item.getCommunicationId())){
                            bHit=true;
                            if(!item.getReadFlg() && !TypeUtility.isNullOrEmpty(item.getSendPHRID())){
                                item.setReadFlg(true);
                            }
                        }else if(bHit){
                            if(!Boolean.valueOf(readFlg)){
                                //全部対象時
                                rFlg="\'"+item.getCommunicationId()+"\',\'"+item.getSeqNo()+"\'";
                                break;
                            }else if(Boolean.valueOf(readFlg) && !item.getReadFlg() && !TypeUtility.isNullOrEmpty(item.getSendPHRID())){
                                //未読のみ対象時
                                rFlg="\'"+item.getCommunicationId()+"\',\'"+item.getSeqNo()+"\'";
                                break;
                            }
                        }else if(bHit==false){
                            if(!Boolean.valueOf(readFlg)){
                                //全部対象時
                                lFlg="\'"+item.getCommunicationId()+"\',\'"+item.getSeqNo()+"\'";
                            }else if(Boolean.valueOf(readFlg) && !item.getReadFlg() && !TypeUtility.isNullOrEmpty(item.getSendPHRID())){
                                //未読のみ対象時
                                lFlg="\'"+item.getCommunicationId()+"\',\'"+item.getSeqNo()+"\'";
                            }
                        }
                    }
                    dto.get(0).setLeftFlg(lFlg);
                    dto.get(0).setRightFlg(rFlg);
                    sessionUtility.setSession(SessionConst.COMMUNICAATION_ENTITIES, communicationEntity);
                }
            }

            returnJson = gson.toJson(dto);
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }

        logger.debug("End");
        return returnJson;
    }

    /**
     * 患者指定（ワンタイムPW）を解除する
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"unassignPatient"}, method = {RequestMethod.POST})
    @ResponseBody
    public String ajaxUnassignPatientAction(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        logger.debug("Start");

        //        String getAllStr=null;
        //        //リクエストJSONの取得
        //        try(Reader reqReader=request.getReader();
        //            BufferedReader reqBufReader =  new BufferedReader(reqReader);){
        //            StringBuilder sBuilder = new StringBuilder();
        //            String getStr;
        //            do{
        //                //１行読む
        //                getStr = reqBufReader.readLine();
        //                if(getStr!=null){
        //                    sBuilder.append(getStr);
        //                }
        //            }while (getStr != null);
        //            getAllStr=sBuilder.toString();
        //        } catch (Exception e) {
        //            logger.error(e);
        //            throw e;
        //        }

        //        String value=null;
        //        String seqNo=null;
        //        //リクエストJSONから値の取得
        //        try{
        //            String[] spStrs=getAllStr.split(",");
        //            for(String item:spStrs){
        //                String[] keyValues=item.split(":");
        //                String keyValue=commonReplace(keyValues[0]);
        //                if(keyValue.equals("value1")){
        //                    //必要な値を取得
        //                    value=commonReplace(keyValues[1]);
        //                    logger.debug(value);
        //                }
        //                if(keyValue.equals("value2")){
        //                    //必要な値を取得
        //                    seqNo=commonReplace(keyValues[1]);
        //                    logger.debug(seqNo);
        //                }
        //            }
        //        } catch (Exception e) {
        //            logger.error(e);
        //            throw e;
        //        }

        //要求に応じた情報をJSON形式文字列にして戻す
        OneTimePasswordResult getOTPR=(OneTimePasswordResult)sessionUtility.getSession(SessionConst.VIEW_PASSWORD_INFO);
        String returnJson="";
        String[] strData = {""};
        try{
            //アクセスログ
            AccesslLogBackUpUtility.writeAccessLog(request, accessLogsService, "ajaxUnassignPatient");

            OneTimePasswordResult obResult=onetimePasswordService.deletePassword(getOTPR.getPassword());
            if(obResult.getResultCd().equals(OnetimePasswordService.OnetimePasswordResultCd.SUCCCESS)){
                //解除成功
                sessionUtility.removeSession(SessionConst.VIEW_PASSWORD_INFO);
            }else{
                //解除失敗
                returnJson="指定解除でエラーが発生しました。";
            }
            strData[0] = returnJson;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }

        logger.debug("End");
        return gson.toJson(strData);
    }

    private String commonReplace(String item) throws Throwable {
        String ret;
        ret=item.replaceAll("\\{", "")
                .replaceAll("\\[", "")
                .replaceAll("\\}", "")
                .replaceAll("\\]", "")
                .replaceAll("\"", "");
        return ret;
    }

}
