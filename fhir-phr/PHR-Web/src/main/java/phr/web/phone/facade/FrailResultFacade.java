/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IMedicalSearchService;
import phr.service.IObservationEventService;
import phr.service.IPatientManagementService;
import phr.service.impl.MedicalSearchService;
import phr.service.impl.ObservationEventService;
import phr.service.impl.PatientManagementService;
import phr.web.phone.dto.RequestFrailResultDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseFrailResultDto;

/**
 *
 * @author suzuki
 */
public class FrailResultFacade extends PhoneFacade{
       
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(FrailResultFacade.class);

    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Override
    public ResponseBaseDto execute(String Json) throws Throwable{
             
        RequestFrailResultDto requestDto = gson.fromJson(Json, RequestFrailResultDto.class);
        ResponseFrailResultDto responseDto = new ResponseFrailResultDto();
        
        //検査結果を取得
        IObservationEventService service = new ObservationEventService();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        List<ObservationEventEntity> checkupObservationList = service.searchObsevationByOrderNo(2020, requestDto.getPhrId(), timestamp, 3, 103);
        
        //患者情報を取得
        IPatientManagementService patientManagementService = new PatientManagementService();
        PatientEntity patientEntity = patientManagementService.getPatient(requestDto.getPhrId());
               
        //各得点をカウント
        int totalResultCount = 0;
        int bodyFunctionResuletCount = 0;
        int nutritionResultCount = 0;
        int oralFuncitionResultCount = 0;
        int notIndoorResultCount = 0;
        int forgetfulResultCount = 0;
        int mentalResultCount = 0;
        String numberOfTeethCount = "-";
        double pointOfTeethCount = 0;

        for(ObservationEventEntity entity: checkupObservationList){
            switch (entity.getObservationDefinitionId()) {
            case "2000000001":
                if ("いいえ".equals(entity.getObservationValue())) {
                   totalResultCount++;
                }
                
                break;
            case "2000000002":
                if ("いいえ".equals(entity.getObservationValue())) {
                    totalResultCount++;
                    notIndoorResultCount++;
                }
                     
                //実施日を取得
                Locale locale = new Locale("ja","JP","JP");
                SimpleDateFormat sdf = new SimpleDateFormat("GGGGyyyy年 M月 d日",locale);
                responseDto.setTargetDate(sdf.format(entity.getNewExaminationDate()));
                
                //実施施設を取得
                String code = entity.getMedicalOrganizationCd();
                IMedicalSearchService medicalSearchService = new MedicalSearchService();
                MedicalOrganizationEntity medicalOrganizationEntity = medicalSearchService.getMedicalOrganizationInfo(code);
                String targetLocation= medicalOrganizationEntity.getMedicalOrganizationName();
                responseDto.setTargetLocation(targetLocation);
                
                break;
            case "2000000003":
                if ("いいえ".equals(entity.getObservationValue())) {
                    totalResultCount++;
                    notIndoorResultCount++;
                }
                break;
            case "2000000004":
                if ("いいえ".equals(entity.getObservationValue())) {
                    totalResultCount++;
                    notIndoorResultCount++;
                }
                break;
            case "2000000005":
                if ("いいえ".equals(entity.getObservationValue())) {
                    totalResultCount++;
                    notIndoorResultCount++;
                }
                break;
            case "2000000006":
                if ("いいえ".equals(entity.getObservationValue())) {
                    totalResultCount++;
                    notIndoorResultCount++;
                }
                break;
            case "2000000007":
                if ("いいえ".equals(entity.getObservationValue())) {
                    totalResultCount++;
                    notIndoorResultCount++;
                }
                break;
            case "2000000008":
                if ("はい".equals(entity.getObservationValue())) {
                    totalResultCount++;
                    notIndoorResultCount++;
                }
                break;
            case "2000000009":
                if ("はい".equals(entity.getObservationValue())) {
                    totalResultCount++;
                    forgetfulResultCount++;
                }
                break;
            case "2000000010":
                if ("いいえ".equals(entity.getObservationValue())) {
                    totalResultCount++;
                    forgetfulResultCount++;
                }
                break;
            case "2000000011":
                if ("はい".equals(entity.getObservationValue())) {
                    totalResultCount++;
                    forgetfulResultCount++;
                }
                break;
            case "20000000012":
                if ("いいえ".equals(entity.getObservationValue())) {
                    bodyFunctionResuletCount++;
                }
                break;
            case "2000000013":
                if ("いいえ".equals(entity.getObservationValue())) {
                    bodyFunctionResuletCount++;
                }
                break;
            case "2000000014":
                if ("いいえ".equals(entity.getObservationValue())) {
                    bodyFunctionResuletCount++;
                }
                break;
            case "20000000015":
                if ("はい".equals(entity.getObservationValue())) {
                    bodyFunctionResuletCount++;
                }
                break;
            case "2000000016":
                if ("はい".equals(entity.getObservationValue())) {
                    bodyFunctionResuletCount++;
                }
                break;
            case "2000000017":
                if ("はい".equals(entity.getObservationValue())) {
                    nutritionResultCount++;
                }
                break;
            case "2000000018":
                if ("はい".equals(entity.getObservationValue())) {
                    numberOfTeethCount = "0本";
                }
                break;
            case "2000000019":
                if ("はい".equals(entity.getObservationValue())) {
                    numberOfTeethCount = "1～4本";
                }
                break;
            case "2000000020":
                if ("はい".equals(entity.getObservationValue())) {
                    numberOfTeethCount = "5～9本";
                }
                break;      
            case "2000000021":
                if ("はい".equals(entity.getObservationValue())) {
                    numberOfTeethCount = "10～19本";
                }
                break;  
            case "2000000022":
                if ("はい".equals(entity.getObservationValue())) {
                    numberOfTeethCount = "20本以上";
                }
                break;
            case "2000000023":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000024":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;       
            case "2000000025":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000026":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                     pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;
            case "2000000027":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000028":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;       
            case "2000000029":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000030":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;
            case "2000000031":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000032":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;       
            case "2000000033":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000034":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;
            case "2000000035":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000036":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;       
            case "2000000037":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000038":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;
            case "2000000039":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000040":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;
            case "2000000041":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000042":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;       
            case "2000000043":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000044":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;
            case "2000000045":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;       
            case "2000000046":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;   
            case "2000000047":
                if ("〇".equals(entity.getObservationValue())) {
                    pointOfTeethCount++;
                }else if ("△".equals(entity.getObservationValue())){
                    pointOfTeethCount = pointOfTeethCount + 0.5;
                }
                break;
            case "20000000048":
                if ("はい".equals(entity.getObservationValue())) {
                    oralFuncitionResultCount++;
                }
                break;
            case "2000000049":
                if ("はい".equals(entity.getObservationValue())) {
                    oralFuncitionResultCount++;
                }
                break;
            case "2000000050":
                if ("はい".equals(entity.getObservationValue())) {
                    oralFuncitionResultCount++;
                }
                break;
            case "20000000051":
                if ("はい".equals(entity.getObservationValue())) {
                    mentalResultCount++;
                }
                break;
            case "2000000052":
                if ("はい".equals(entity.getObservationValue())) {
                    mentalResultCount++;
                }
                break;
            case "2000000053":
                if ("はい".equals(entity.getObservationValue())) {
                    mentalResultCount++;
                }
                break;
            case "2000000054":
                if ("はい".equals(entity.getObservationValue())) {
                    mentalResultCount++;
                }
                break;
            case "2000000055":
                if ("はい".equals(entity.getObservationValue())) {
                    mentalResultCount++;
                }
                break;   
           default:
               
           }  
       }

        responseDto.setPatientName(patientEntity.getFamilyName() + patientEntity.getGivenName());
        
        String address = "";
        if (patientEntity.getAddressLine() != null) {
            address = patientEntity.getAddressLine();
        }
        if (patientEntity.getBuildingName() != null) {
            address = address + patientEntity.getBuildingName();
        }
        if (address.isEmpty()) {
            address = "-";
        }
        responseDto.setAddressLine(address);
        responseDto.setTotalResult(totalResultCount);
        responseDto.setBodyFunctionResulet(bodyFunctionResuletCount);
        responseDto.setNutritionResult(nutritionResultCount);
        responseDto.setOralFuncitionResult(oralFuncitionResultCount);
        responseDto.setNotIndoorResult(notIndoorResultCount);
        responseDto.setForgetfulResult(forgetfulResultCount);
        responseDto.setMentalResult(mentalResultCount);
        responseDto.setNumberOfTeeth(numberOfTeethCount);
        responseDto.setPointOfTeeth(pointOfTeethCount);
        
        //受付番号は未入力のため無し
        //responseDto.setReceiptNumber();
        responseDto.setStatus(ResponseBaseDto.SUCCESS);
        return responseDto;
    }
}
