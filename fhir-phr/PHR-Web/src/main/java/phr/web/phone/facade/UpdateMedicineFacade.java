/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：おくすりリスト更新クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/07
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.DosageDoctorEntity;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.DosageHeadEntity;
import phr.datadomain.entity.DosageMedicalOrganizationEntity;
import phr.datadomain.entity.DosageMedicineAdditionEntity;
import phr.datadomain.entity.DosageMedicineEntity;
import phr.datadomain.entity.DosageRecipeEntity;
import phr.datadomain.entity.DosageRemarkEntity;
import phr.datadomain.entity.DosageTypeCd;
import phr.datadomain.entity.NonPrescriptionDrugsEntity;
import phr.datadomain.entity.OrganizationTypeCd;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.RecordCreatorTypeCd;
import phr.dto.MedicineQRVersion;
import phr.service.IDosageUpdateService;
import phr.service.IPatientManagementService;
import phr.service.impl.DosageInsertService;
import phr.service.impl.DosageUpdateService;
import phr.service.impl.PatientManagementService;
import phr.utility.TypeUtility;
import phr.web.phone.dto.MedicineEntryItemDto;
import phr.web.phone.dto.RequestMedicineUpdateDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseMedicineUpdateDto;
import phr.web.phone.dto.dosageMedicineDto;
import phr.web.phone.dto.nonpreMedicineDto;




/**
 *
 * @author iwaasa
 */
public class UpdateMedicineFacade extends PhoneFacade{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UpdateMedicineFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
       RequestMedicineUpdateDto requestDtoOrg = gson.fromJson(json, RequestMedicineUpdateDto.class);
       MedicineEntryItemDto requestDto = requestDtoOrg.getEntryItem();
       IPatientManagementService patService = new PatientManagementService();
       IDosageUpdateService service;
       if(requestDto.getDosageId()==null||requestDto.getDosageId().isEmpty()){
           service = new DosageInsertService();
           String dosageId = service.getNewDosageId();
           requestDto.setDosageId(dosageId);
           requestDto.setSeq("1");
       }else{
          service = new DosageUpdateService();
       }       
       PatientEntity patientInfo = patService.getPatient(requestDtoOrg.getPhrId());
       DosageHeadEntity head = setHEAD(requestDto,patientInfo);
       DosageEntity dosage = setDOSAGE(requestDto, requestDtoOrg.getPhrId());
       DosageMedicalOrganizationEntity pharmacy = setPHARMACY(requestDto);
       DosageDoctorEntity pharmacist = setPHARMACIST(requestDto);
       DosageMedicalOrganizationEntity hospital = setHOSPITAL(requestDto);
       DosageDoctorEntity doctor = setDOCTOR(requestDto);
       DosageRemarkEntity remark = setREMARK(requestDto);
       List<DosageRecipeEntity> recipelist = null;
       List<DosageMedicineEntity> medicineList = null;
       List<DosageMedicineAdditionEntity> additionList=null;
       List<NonPrescriptionDrugsEntity> nonpreList = null;

       if(requestDto.getNonpre()!=null){
           //レシピは一件作成
           //お薬は件数ぶん回す
           nonpreList = new ArrayList();
           List<nonpreMedicineDto> reqnonpreList = requestDto.getNonpre();
           int nonpreseq = 1;
           for(nonpreMedicineDto nonpreEnt:reqnonpreList){
               if(nonpreEnt.getMedicineSeq()!=null&& !nonpreEnt.getMedicineSeq().isEmpty()){
                   int npExSeq = Integer.parseInt(nonpreEnt.getMedicineSeq());
                   if(nonpreseq<npExSeq){
                       nonpreseq = npExSeq + 1;
                   }
               }
           }
           for(nonpreMedicineDto nonpreEnt:reqnonpreList){
               NonPrescriptionDrugsEntity nopreEntity = setNONPRE(nonpreEnt,requestDto.getDosageId(),nonpreseq); 
               nonpreList.add(nopreEntity);
               nonpreseq++;
           }
       }
       if(requestDto.getMedicines()!=null){
           recipelist = new ArrayList();
           medicineList = new ArrayList();
           additionList = new ArrayList();
           List<dosageMedicineDto> reqMlist = requestDto.getMedicines();
           int dosageSeq = Integer.parseInt(requestDto.getSeq());
            //レシピはお薬の数だけ作成
            int mseq = 1;
            int addseq = 1;
           for(dosageMedicineDto reqMdto:reqMlist){
               if(reqMdto.getMedicineSeq()!=null&& !reqMdto.getMedicineSeq().isEmpty()){
                   int mExSeq = Integer.parseInt(reqMdto.getMedicineSeq());
                   if(mseq<mExSeq){
                       mseq = mExSeq + 1;
                   }
               }
           }
            for(dosageMedicineDto reqMdto:reqMlist){
                recipelist.add(setRECIPE(reqMdto, requestDto.getDosageId(), dosageSeq, mseq));
                medicineList.add(setMEDICINE(reqMdto, requestDto.getDosageId(), dosageSeq, mseq));
                if(reqMdto.getAdditional()==null||reqMdto.getAdditional().isEmpty()){
                }else{
                    additionList.add(setADDITION(reqMdto, requestDto.getDosageId(), dosageSeq, mseq, addseq));
                    addseq++;
                } 
                mseq++;
            }
            
           
       }
       
       int result = 0;

       result = service.setDosage(head,dosage,pharmacy,pharmacist,hospital,doctor,remark,recipelist,medicineList,additionList,nonpreList);
       
       ResponseBaseDto responseDto = new ResponseMedicineUpdateDto(); 
       if(result>0){
           responseDto.setStatus(ResponseBaseDto.SUCCESS);
       }
        return responseDto;
    }
    
    /**
     * DosageHeadEntity作成
     * @param dto
     * @return 
     */
    private DosageHeadEntity setHEAD(MedicineEntryItemDto dto,PatientEntity patientInfo){
        DosageHeadEntity entity = new DosageHeadEntity();
        entity.setDosageId(dto.getDosageId());
        entity.setQRVersion(MedicineQRVersion.VERSION2_4.getText());
        //患者情報取得
        String nameString = changeComma(patientInfo.getFamilyName() + "　" + patientInfo.getGivenName());
        entity.setPatientName(nameString);
        String SxCd = "";
        if(patientInfo.getSexCd()=="F"){
            SxCd="2";
        }else{
            SxCd="1";
        }
        entity.setSexCd(SxCd);
        entity.setBirthDate(patientInfo.getBirthDate());
        entity.setZipCode(patientInfo.getZipCode());
        if(patientInfo.getAddressLine()!=null){
            String addressString="";
            if(patientInfo.getBuildingName()!=null){
                addressString=changeComma(patientInfo.getAddressLine() + patientInfo.getBuildingName());                
            }else{
                addressString=changeComma(patientInfo.getAddressLine());
            }
            entity.setAddressLine(addressString);
        }
        entity.setTelNo(patientInfo.getTelNo());
        if(patientInfo.getOtherContactNo()!=null){
            entity.setEmergencyContact(patientInfo.getOtherContactNo());
        }
        String kanaStr = changeComma(patientInfo.getFamilyKana() + "　" + patientInfo.getGivenKana());
        entity.setNameInKana(kanaStr);
        
        return entity;
    }

    private DosageEntity setDOSAGE(MedicineEntryItemDto dto,String phrId){
        DosageEntity entity = new DosageEntity();
        entity.setDosageId(dto.getDosageId());
        Date dosageDate = null;
        try{
            String dateStr = dto.getDosageDate().substring(0, 10);
            dosageDate = TypeUtility.stringToDate(dateStr.replace("-", "/"));
        }catch(Exception e){
        }
        entity.setDosageDate(dosageDate);
        entity.setSeq(Integer.parseInt(dto.getSeq()));
        entity.setPHR_ID(phrId);
        entity.setRecordCreatorType(RecordCreatorTypeCd.PATIENT.getId());
        return entity;
    }
    
    private DosageMedicalOrganizationEntity setPHARMACY(MedicineEntryItemDto dto){
        if(dto.getPharmacy()==null||dto.getPharmacy().isEmpty()){
            return null;
        }
        DosageMedicalOrganizationEntity entity = new DosageMedicalOrganizationEntity();
        entity.setDosageId(dto.getDosageId());
        entity.setSeq(Integer.parseInt(dto.getSeq()));
        entity.setDosageTypeCd(DosageTypeCd.DOSAGE.getId());
        entity.setOrganizationType(OrganizationTypeCd.DISPENSE.getId());
        entity.setMedicalOrganizationCd("9999999");
        String nameStr = changeComma(dto.getPharmacy());
        entity.setMedicalOrganizationName(nameStr);        
        entity.setRecordCreatorType(RecordCreatorTypeCd.PATIENT.getId());
        //entity.setRecordCreatorType(dto.getPharmacyRecordcreator);
        return entity;
    }
    
    private DosageDoctorEntity setPHARMACIST(MedicineEntryItemDto dto){
        if(dto.getPharmacistName()==null||dto.getPharmacistName().isEmpty()){
            return null;
        }
        DosageDoctorEntity entity = new DosageDoctorEntity();
        entity.setDosageId(dto.getDosageId());
        entity.setSeq(Integer.parseInt(dto.getSeq()));
        entity.setDosageTypeCd(DosageTypeCd.DOSAGE.getId());
        String nameStr = changeComma(dto.getPharmacistName());
        entity.setDoctorName(nameStr);       
        entity.setRecordCreatorType(RecordCreatorTypeCd.PATIENT.getId());
        //entity.setRecordCreatorType(dto.getPharmacistRecordcreator);
        return entity;
    }

    private DosageMedicalOrganizationEntity setHOSPITAL(MedicineEntryItemDto dto){
        if(dto.getMedicalOrganization()==null||dto.getMedicalOrganization().isEmpty()){
            return null;
        }
        DosageMedicalOrganizationEntity entity = new DosageMedicalOrganizationEntity();
        entity.setDosageId(dto.getDosageId());
        entity.setSeq(Integer.parseInt(dto.getSeq()));
        entity.setDosageTypeCd(DosageTypeCd.RECIPE.getId());
        entity.setOrganizationType(OrganizationTypeCd.MEDICAL.getId());
        entity.setMedicalOrganizationCd("9999999");
        String nameStr = changeComma(dto.getMedicalOrganization());
        entity.setMedicalOrganizationName(nameStr);        
        entity.setRecordCreatorType(RecordCreatorTypeCd.PATIENT.getId());
        //entity.setRecordCreatorType(dto.getHospitalRecordcreator);
        return entity;
    }
    
    private DosageDoctorEntity setDOCTOR(MedicineEntryItemDto dto){
        DosageDoctorEntity entity = new DosageDoctorEntity();
        if(dto.getDoctorName()==null||dto.getDoctorName().isEmpty()){
            entity.setDoctorName(null);
            if(dto.getDepartmentName()==null||dto.getDepartmentName().isEmpty()){
                return null;
            }
        }else{
            String drNameStr = changeComma(dto.getDoctorName());
            entity.setDoctorName(drNameStr);
        }
        entity.setDosageId(dto.getDosageId());
        entity.setSeq(Integer.parseInt(dto.getSeq()));
        entity.setDosageTypeCd(DosageTypeCd.RECIPE.getId());
        
        String depNameStr = changeComma(dto.getDepartmentName());
        entity.setDepartmentName(depNameStr);
        entity.setRecordCreatorType(RecordCreatorTypeCd.PATIENT.getId());
        //entity.setRecordCreatorType(dto.getDoctorRecordcreator);
        return entity;
    }
    
    private NonPrescriptionDrugsEntity setNONPRE(nonpreMedicineDto dto,String dosageId,int seq){
        NonPrescriptionDrugsEntity entity = new NonPrescriptionDrugsEntity();
        entity.setDosageId(dosageId);
        if(dto.getMedicineSeq()==null||dto.getMedicineSeq().isEmpty()){
            entity.setSeq(seq);
        }else{
            entity.setSeq(Integer.parseInt(dto.getMedicineSeq()));
        }
        String nameStr = changeComma(dto.getMedicineName());
        entity.setMedicineName(nameStr);
        Date startDate = null;
        Date endDate = null;
        try{
           // Calendar cal = Calendar.getInstance();
            if(dto.getStartDate()!=null){
                String startStr = dto.getStartDate().substring(0, 10);
                startDate = TypeUtility.stringToDate(startStr.replace("-", "/"));
           //     cal.setTime(startDate);
           //     cal.add(Calendar.DAY_OF_MONTH,1);
           //     startDate = cal.getTime();                
            }
            if(dto.getEndDate()!=null){
                String endStr = dto.getEndDate().substring(0, 10);
                endDate = TypeUtility.stringToDate(endStr.replace("-", "/"));
          //      cal.setTime(endDate);
          //      cal.add(Calendar.DAY_OF_MONTH,1);
          //      endDate = cal.getTime();            
            } 
        }catch(Exception e){
        }
        entity.setStartDate(startDate);
        entity.setEndDate(endDate);
        entity.setRecordCreatorType(RecordCreatorTypeCd.PATIENT.getId());
        //entity.setRecordCreatorType(dto.getRecordCreatorTypeCd());
        entity.setIsDelete(Boolean.valueOf(dto.getIsdelete()));
        return entity;
    }
    
    private DosageRecipeEntity setRECIPE(dosageMedicineDto dto,String dosageId,int dosageSeq,int mSeq){
        DosageRecipeEntity entity = new DosageRecipeEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        if(dto.getRecipeNo()==null||dto.getRecipeNo().isEmpty()){
            entity.setRecipeNo(mSeq);
        }else{
            entity.setRecipeNo(Integer.parseInt(dto.getRecipeNo()));
        }
        
        String textStr = changeComma(dto.getUsageName());
        entity.setUsageName(textStr);
        entity.setDosageQuantity(Integer.parseInt(dto.getDosageQuantity()));
        entity.setDosageUnit(dto.getDosageUnit());
        entity.setDosageForms(dto.getDosageForm());
        //if(dto.getUsageCdType==null||dto.getUsageCdType.isEmpty){
            entity.setUsageCdType("1");
            entity.setUsageCd("");
        //}else{
        //    entity.setUsageCdType(dto.getUsageCdType);
        //    entity.setUsageCd(dto.getUsageCd);
        //}
        entity.setRecordCreatorType(RecordCreatorTypeCd.PATIENT.getId());
        //entity.setRecordCreatorType(dto.getRecipeRecordCreatorTypeCd());
        
        return entity;
    }    

    private DosageMedicineEntity setMEDICINE(dosageMedicineDto dto,String dosageId,int dosageSeq,int mSeq){
        DosageMedicineEntity entity = new DosageMedicineEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        if(dto.getRecipeNo()==null||dto.getRecipeNo().isEmpty()){
            entity.setRecipeNo(mSeq);
        }else{
            entity.setRecipeNo(Integer.parseInt(dto.getRecipeNo()));
        }
        if(dto.getMedicineSeq()==null||dto.getMedicineSeq().isEmpty()){
            entity.setMedicineSeq(mSeq);
        }else{
            entity.setMedicineSeq(Integer.parseInt(dto.getMedicineSeq()));
        }
        String nameStr = changeComma(dto.getMedicineName());
        entity.setMedicineName(nameStr);
        entity.setAmount(dto.getAmount());
        entity.setUnitName(dto.getUnitName());
        //if(dto.getUsageCdType==null||dto.getUsageCdType.isEmpty){
            entity.setMedicineCdType("1");
            entity.setMedicineCd("");
        //}else{
        //    entity.setUsageCdType(dto.getUsageCdType);
        //    entity.setUsageCd(dto.getUsageCd);
        //}
        entity.setRecordCreatorType(RecordCreatorTypeCd.PATIENT.getId());
        //entity.setRecordCreatorType(dto.getRecordCreatorTypeCd());
        entity.setIsDelete(Boolean.valueOf(dto.getIsdelete()));
        return entity;
    } 
    
    private DosageMedicineAdditionEntity setADDITION(dosageMedicineDto dto,String dosageId,int dosageSeq,int mSeq,int addSeq){
        DosageMedicineAdditionEntity entity = new DosageMedicineAdditionEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        if(dto.getRecipeNo()==null||dto.getRecipeNo().isEmpty()){
            entity.setRecipeNo(mSeq);
        }else{
            entity.setRecipeNo(Integer.parseInt(dto.getRecipeNo()));
        }
        if(dto.getMedicineSeq()==null||dto.getMedicineSeq().isEmpty()){
            entity.setMedicineSeq(mSeq);
        }else{
            entity.setMedicineSeq(Integer.parseInt(dto.getMedicineSeq()));
        }
//        if(dto.getAdditionSeq()==null||dto.getAdditionSeq().isEmpty){
            entity.setAdditionSeq(addSeq);
 //       }else{
 //           entity.setAdditionSeq(Integer.parseInt(dto.getAdditionSeq()));
 //       }
        String textStr = changeComma(dto.getAdditional());
        entity.setAdditionText(textStr);
        entity.setRecordCreatorType(RecordCreatorTypeCd.PATIENT.getId());
        //entity.setRecordCreatorType(dto.getRecordCreatorTypeCd());
        return entity;
    } 

    private DosageRemarkEntity setREMARK(MedicineEntryItemDto requestDto) {
        DosageRemarkEntity entity = new DosageRemarkEntity();
        if(requestDto.getDosageRemark()==null||requestDto.getDosageRemark().isEmpty()){
            return null;
        }else{
            entity.setDosageId(requestDto.getDosageId());
            entity.setSeq(Integer.parseInt(requestDto.getSeq()));
            entity.setRemarkSeq(1);
            String textStr = changeComma(requestDto.getDosageRemark());
            entity.setRemarkText(textStr);
            entity.setRecordCreatorType(RecordCreatorTypeCd.PATIENT.getId());
        }
        return entity;
    }
    
    private String changeComma(String targetString){
        String resultString = "";
        if(targetString.contains(",")){
            resultString = targetString.replace(",", "，");
        }else{
            resultString = targetString;
        }
        return resultString;
    } 
       
    

}
