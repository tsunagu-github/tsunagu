/**！！未完成！！
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：おくすりリスト取得クラス
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
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.DosageAttentionEntity;
import phr.datadomain.entity.DosageDoctorEntity;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.DosageMedicalOrganizationEntity;
import phr.datadomain.entity.DosageMedicineAdditionEntity;
import phr.datadomain.entity.DosageMedicineAttentionEntity;
import phr.datadomain.entity.DosageMedicineEntity;
import phr.datadomain.entity.DosagePatientInputEntity;
import phr.datadomain.entity.DosageRecipeAdditionEntity;
import phr.datadomain.entity.DosageRecipeAttentionEntity;
import phr.datadomain.entity.DosageRecipeEntity;
import phr.datadomain.entity.DosageRemarkEntity;
import phr.datadomain.entity.DosageTypeCd;
import phr.datadomain.entity.NonPrescriptionDrugsEntity;
import phr.dto.DosageEntitySetDto;
import phr.service.IDosageExportService;
import phr.service.impl.DosageExportService;
import phr.utility.TypeUtility;
import phr.web.phone.dto.DosageDataDto;
import phr.web.phone.dto.DosagePatientInputEntityDto;
import phr.web.phone.dto.MedicineDosageDto;
import phr.web.phone.dto.MedicineDto;
import phr.web.phone.dto.MedicineListDto;
import phr.web.phone.dto.RequestMedicineListDto;

import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseMedicineListDto;

/**
 *
 * @author iwaasa
 */
public class GetMedicineListFacade extends PhoneFacade{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog( GetMedicineListFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();    
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        IDosageExportService exService = new DosageExportService();
        
        RequestMedicineListDto requestDto = gson.fromJson(json, RequestMedicineListDto.class);
        java.util.Date baseDate = TypeUtility.stringToDate(requestDto.getBaseDate());
        
        List<MedicineDosageDto> dosageList = new ArrayList();

        java.sql.Date targetDate = new java.sql.Date(baseDate.getTime());
        List<DosageEntitySetDto> setDtoList = exService.getDosageSetList(requestDto.getPhrId());
        
        for(DosageEntitySetDto dosageBaseEntity:setDtoList){
            String dosageId=dosageBaseEntity.getDosage(0).getDosageId();
            boolean doDtoSetting=true;
            for(MedicineDosageDto dtoEnt:dosageList){
                if(dosageId.equals(dtoEnt.getDosageId())){
                    doDtoSetting=false;
                }
            }
            int dosageSeq = dosageBaseEntity.getDosage(0).getSeq();
            if(dosageSeq==0){
                doDtoSetting=false;
            }
           
            if(doDtoSetting){
                MedicineDosageDto dosageDto = new MedicineDosageDto();           
               //服用注意、備考、患者記入情報
               List<String> attentextList = new ArrayList();
               for(DosageAttentionEntity atEnt:dosageBaseEntity.getDosageAttentionList()){
                   attentextList.add(atEnt.getAttentionText());
               }
               dosageDto.setDosageAttention(attentextList);
               List<String> remarktextList = new ArrayList();
               for(DosageRemarkEntity remarkEnt:dosageBaseEntity.getDosageRemarkList()){
                   remarktextList.add(remarkEnt.getRemarkText());
               }
               dosageDto.setDosageRemark(remarktextList);
    //           List<String> pInputtextList = new ArrayList();
    //           for(DosagePatientInputEntity patInputEnt:dosageBaseEntity.getPatientInputList()){
    //               pInputtextList.add(patInputEnt.getInputText());
    //           }
    //           dosageDto.setPatientInput(pInputtextList);
               if(!dosageBaseEntity.getPatientInputList().isEmpty()){
                   List<DosagePatientInputEntityDto> patList = new ArrayList();
                   for(DosagePatientInputEntity patEnt:dosageBaseEntity.getPatientInputList()){
                        DosagePatientInputEntityDto patDto = new DosagePatientInputEntityDto();
                        patDto.setDosageId(patEnt.getDosageId());
                        patDto.setSeq(patEnt.getSeq());
                        patDto.setInputSeq(patEnt.getInputSeq());
                        patDto.setInputDate(patEnt.getInputDate().toString());
                        patDto.setInputText(patEnt.getInputText());
                        patList.add(patDto);
                   }

                   dosageDto.setPatientInput(patList);
               }

               List<DosageDataDto> doasagedataList = new ArrayList();
                for(DosageEntity dosageEnt:dosageBaseEntity.getDosageList()){

                    int seq = dosageEnt.getSeq();

                    //調剤メタ情報（未設定時のみ）
                    if(dosageDto.getDosageId()==null){
                        //調剤ID、調剤日、作成者情報取得
                        dosageDto.setDosageId(dosageId);
                       if(dosageEnt.getDosageDate()!=null){
                        dosageDto.setDosageDate(dosageEnt.getDosageDate().toString());
                       }
                       dosageDto.setRecordCreatorType(dosageEnt.getRecordCreatorType());
                       //薬局、薬剤師情報　dosageId,seq=1,DosageTypeCd==DOSAGE
                       for(DosageMedicalOrganizationEntity pharmacy:dosageBaseEntity.getMedicalOrganizationList()){
                           if(pharmacy.getDosageId().equals(dosageId) && pharmacy.getSeq()==seq && pharmacy.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
                               dosageDto.setPharmacy(pharmacy.getMedicalOrganizationName());
                               break;
                           }
                       }
                       for(DosageDoctorEntity pharmacist:dosageBaseEntity.getDosageDoctorList()){
                           if(pharmacist.getDosageId().equals(dosageId) && pharmacist.getSeq()==seq && pharmacist.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
                                dosageDto.setPharmacistName(pharmacist.getDoctorName());
                                break;
                           }
                       }
                    }

                    DosageDataDto dataDto = new DosageDataDto();
                    dataDto.setSeq(seq);
                    //医療機関情報 dosageId,seq=1,DosageTypeCd==RECIPE
                   for(DosageMedicalOrganizationEntity hospital:dosageBaseEntity.getMedicalOrganizationList()){
                       if(hospital.getDosageId().equals(dosageId) && hospital.getSeq()==seq && hospital.getDosageTypeCd().equals(DosageTypeCd.RECIPE.getId())){
                           dataDto.setMedicalOrganization(hospital.getMedicalOrganizationName());
                           break;
                       }
                   }
                   for(DosageDoctorEntity doctor:dosageBaseEntity.getDosageDoctorList()){
                       if(doctor.getDosageId().equals(dosageId) && doctor.getSeq()==seq && doctor.getDosageTypeCd().equals(DosageTypeCd.RECIPE.getId())){
                            dataDto.setDepartmentName(doctor.getDepartmentName());
                            dataDto.setDoctorName(doctor.getDoctorName());
                            break;
                       }
                   }

                   List<MedicineListDto> recipeList = new ArrayList(); 
                    //レシピ情報

                    if(dosageBaseEntity.getNonPrescriptionDrugsList().isEmpty()){
                        //調剤薬
                        dataDto.setMedicineType("2");
                        for(DosageRecipeEntity recipent:dosageBaseEntity.getDosageRecipeList()){
                            if(dosageId.equals(recipent.getDosageId()) && seq==recipent.getSeq()){
                                MedicineListDto recipe = new MedicineListDto();
                                recipe.setRecipeNo(recipent.getRecipeNo());
                                recipe.setDosageForm(recipent.getDosageForms());
                                recipe.setUsageName(recipent.getUsageName());
                                recipe.setDosageQuantity(recipent.getDosageQuantity());
                                recipe.setDosageUnit(recipent.getDosageUnit());
                                //処方服用注意、用法補足
                                if(!dosageBaseEntity.getDosageRecipeAddList().isEmpty()){
                                    List<String> raddtext = new ArrayList();
                                    for(DosageRecipeAdditionEntity raddEnt:dosageBaseEntity.getDosageRecipeAddList()){
                                        if(dosageId.equals(raddEnt.getDosageId()) && seq==raddEnt.getSeq() && recipent.getRecipeNo()==raddEnt.getRecipeNo()){
                                            raddtext.add(raddEnt.getAdditionText());
                                        }
                                    }
                                    if(!raddtext.isEmpty()){
                                        recipe.setRecipeAddition(raddtext);
                                    }
                                }
                                if(!dosageBaseEntity.getRecipeAttentionList().isEmpty()){
                                    List<String> rattentext = new ArrayList();
                                    for(DosageRecipeAttentionEntity rattEnt:dosageBaseEntity.getRecipeAttentionList()){
                                        if(dosageId.equals(rattEnt.getDosageId()) && seq==rattEnt.getSeq() && recipent.getRecipeNo()==rattEnt.getRecipeNo()){
                                            rattentext.add(rattEnt.getAttentionText());
                                        }
                                    }
                                    if(!rattentext.isEmpty()){
                                        recipe.setRecipeAttention(rattentext);
                                    }

                                }
                                //薬剤情報
                                List<MedicineDto> medList = new ArrayList();
                                for(DosageMedicineEntity medicineEnt:dosageBaseEntity.getDosageMedicineList()){
                                    if(dosageId.equals(medicineEnt.getDosageId()) && seq==medicineEnt.getSeq() &&recipent.getRecipeNo()==medicineEnt.getRecipeNo()){
                                        MedicineDto medicine = new MedicineDto();
                                        medicine.setMedicineSeq(medicineEnt.getMedicineSeq());
                                        medicine.setMedicineName(medicineEnt.getMedicineName());
                                        medicine.setAmount(medicineEnt.getAmount());
                                        medicine.setUnitName(medicineEnt.getUnitName());
                                        //薬剤補足情報
                                        if(!dosageBaseEntity.getMedicineAdditionList().isEmpty()){
                                            List<String> maddList = new ArrayList();
                                            for(DosageMedicineAdditionEntity medadd:dosageBaseEntity.getMedicineAdditionList()){
                                                if(medadd.getDosageId().equals(dosageId) && seq==medadd.getSeq() && medicineEnt.getRecipeNo()==medadd.getRecipeNo() && medicineEnt.getMedicineSeq()==medadd.getMedicineSeq()){
                                                    maddList.add(medadd.getAdditionText());
                                                }
                                            }
                                            if(!maddList.isEmpty()){
                                                medicine.setAdditionalList(maddList);
                                            }
                                        }        
                                        //薬剤服用注意情報
                                        if(!dosageBaseEntity.getMedicineAttentionList().isEmpty()){
                                            List<String> mattList = new ArrayList();
                                            for(DosageMedicineAttentionEntity mattEnt:dosageBaseEntity.getMedicineAttentionList()){
                                                if(dosageId.equals(mattEnt.getDosageId()) && seq==mattEnt.getSeq() && medicineEnt.getRecipeNo()==mattEnt.getRecipeNo() && medicineEnt.getMedicineSeq()==mattEnt.getMedicineSeq()){
                                                    mattList.add(mattEnt.getAttentionText());
                                                }
                                            }
                                            if(!mattList.isEmpty()){
                                                medicine.setAttention(mattList);
                                            }
                                        }
                                        medList.add(medicine);
                                    }
                                }
                                if(!medList.isEmpty()){
                                    recipe.setMedicines(medList);
                                }
                                recipeList.add(recipe);
                            }
                        }
                    }else{
                        //市販薬
                        dataDto.setMedicineType("1");
                        MedicineListDto recipe = new MedicineListDto();
                        List<MedicineDto> medicines = new ArrayList();
                        for(NonPrescriptionDrugsEntity nonEnt:dosageBaseEntity.getNonPrescriptionDrugsList()){
                            //if(dosageId.equals(nonEnt.getDosageId()) && seq==nonEnt.getSeq()){
                            if(dosageId.equals(nonEnt.getDosageId())){    
                                MedicineDto medicine = new MedicineDto();
                                medicine.setMedicineName(nonEnt.getMedicineName());
                                if(nonEnt.getStartDate()!=null){
                                    medicine.setStartDate(nonEnt.getStartDate().toString());
                                }
                                if(nonEnt.getEndDate()!=null){
                                    medicine.setEndDate(nonEnt.getEndDate().toString());
                                }
                                medicines.add(medicine);
                                //break;
                            }
                        }
                        recipe.setMedicines(medicines);
                        recipeList.add(recipe);
                    }
                    //薬剤情報
                    dataDto.setRecipeList(recipeList);
                    doasagedataList.add(dataDto);
                }

                dosageDto.setDosageDataList(doasagedataList);
                dosageList.add(dosageDto);
            }
        }
        dosageList.sort((a,b)->b.getDosageDate().compareTo(a.getDosageDate()));
        ResponseMedicineListDto responseDto = new ResponseMedicineListDto();
        responseDto.setDosageList(dosageList);
        responseDto.setStatus(ResponseBaseDto.SUCCESS);
        
        return responseDto;
    }

//    @Override
//    public ResponseBaseDto execute(String json) throws Throwable {
//        IDosageService dosageSVC = new DosageService();
//        IDosageProviderService providerSVC = new DosageProviderService();
//        IDosageRemarkService remarkSVC = new DosageRemarkService();
//        INonPrescriptionDrugsService nonpreSVC = new NonPrescriptionDrugsService();
//        IDosageRecipeService recipeSVC = new DosageRecipeService();
//        IDosegeMedicineService medicineSVC = new DosegeMedicineService();
//        IDosegeMedicineAddition adSVC = new DosegeMedicineAdditionService();
//        
//        RequestMedicineListDto requestDto = gson.fromJson(json, RequestMedicineListDto.class);
//        java.util.Date baseDate = TypeUtility.stringToDate(requestDto.getBaseDate());
//        
//        List<MedicineDosageDto> dosageList = new ArrayList();
//
//        java.sql.Date targetDate = new java.sql.Date(baseDate.getTime());
//        List<DosageEntity> dosageBaseList = dosageSVC.searchDosageByPhrid(requestDto.getPhrId(), targetDate);
//        for(DosageEntity dosageBaseEntity:dosageBaseList){
//            MedicineDosageDto dosageDto = new MedicineDosageDto(); 
//            //調剤日、調剤ID、作成者情報取得
//           dosageDto.setDosageId(dosageBaseEntity.getDosageId());
//           dosageDto.setSeq(dosageBaseEntity.getSeq());
//           if(dosageBaseEntity.getDosageDate()!=null){
//            dosageDto.setDosageDate(dosageBaseEntity.getDosageDate().toString());
//           }
//           dosageDto.setRecordCreatorType(dosageBaseEntity.getRecordCreatorType());
//           
//            //薬剤師、医師情報取得
//            DosageProviderEntity providerEntity = providerSVC.searchDosageProviderByDosageid(dosageBaseEntity.getDosageId(), dosageBaseEntity.getSeq());
//            dosageDto.setPharmacy(providerEntity.getPharmacy());
//            dosageDto.setPharmacistName(providerEntity.getPharmacistName());
//            dosageDto.setMedicalOrganization(providerEntity.getMedicalOrganization());
//            dosageDto.setDepartmentName(providerEntity.getDepartmentName());
//            dosageDto.setDoctorName(providerEntity.getDoctorName());
//  
//            DosageRemarkEntity remark = remarkSVC.getDosageRemark(dosageBaseEntity.getDosageId(), dosageBaseEntity.getSeq(),1);
//            if(remark!=null){
//                dosageDto.setDosageRemark(remark.getRemarkText());
//            }
//            //お薬情報
//            List<MedicineListDto> medicineList =new ArrayList();
//            //(市販薬)
//            MedicineListDto nopremlistDto = new MedicineListDto();
//            nopremlistDto.setMedicineType("1");
//            List<NonPrescriptionDrugsEntity> nonpreEntityList = nonpreSVC.searchMedicineByDosageId(dosageBaseEntity.getDosageId());
//            List<MedicineDto> nonpremedicines = new ArrayList();
//            for(NonPrescriptionDrugsEntity nonpreEntity:nonpreEntityList){
//                MedicineDto mDto = new MedicineDto();
//                mDto.setMedicineSeq(nonpreEntity.getSeq());
//                mDto.setMedicineName(nonpreEntity.getMedicineName());
//                if(nonpreEntity.getStartDate()!=null){
//                    mDto.setStartDate(nonpreEntity.getStartDate().toString());
//                }
//                if(nonpreEntity.getEndDate() !=null){
//                    mDto.setEndDate(nonpreEntity.getEndDate().toString());
//                }
//                nonpremedicines.add(mDto);
//            }
//            if(nonpremedicines.size()>0){
//                nopremlistDto.setMedicines(nonpremedicines);
//                medicineList.add(nopremlistDto);
//                //dosageDto.setRecordCreatorType(nonpreEntityList.get(0).getRecordCreatorType());
//            }
//            
//        
//            //（調剤薬）
//            List<DosageRecipeEntity> recipeEntList = recipeSVC.searchRecipeByDosageId(dosageBaseEntity.getDosageId(), dosageBaseEntity.getSeq());
//            for(DosageRecipeEntity recipeEntity:recipeEntList){
//                MedicineListDto mlistDto = new MedicineListDto();
//                mlistDto.setMedicineType("2");
//                mlistDto.setRecordCreatorType(recipeEntity.getRecordCreatorType());
//                mlistDto.setRecipeNo(recipeEntity.getRecipeNo());
//                mlistDto.setDosageForm(recipeEntity.getDosageForms());
//                mlistDto.setUsageName(recipeEntity.getUsageName());
//                mlistDto.setDosageQuantity(recipeEntity.getDosageQuantity());
//                mlistDto.setDosageUnit(recipeEntity.getDosageUnit());
//                List<MedicineDto> medicines = new ArrayList();
//                List<DosageMedicineEntity> medicineEntityList = medicineSVC.searchMedicineByDosageId(dosageBaseEntity.getDosageId(), dosageBaseEntity.getSeq(),recipeEntity.getRecipeNo());
//                for(DosageMedicineEntity medicineEntity:medicineEntityList){
//                    MedicineDto mDto = new MedicineDto();
//                    mDto.setMedicineSeq(medicineEntity.getMedicineSeq());
//                    mDto.setMedicineName(medicineEntity.getMedicineName());
//                    mDto.setAmount(medicineEntity.getAmount());
//                    mDto.setUnitName(medicineEntity.getUnitName());
//                    DosageMedicineAdditionEntity add = adSVC.getMedicineAddition(dosageBaseEntity.getDosageId(), dosageBaseEntity.getSeq(), recipeEntity.getRecipeNo(), mDto.getMedicineSeq());
//                    if(add!=null){
//                        mDto.setAdditional(add.getAdditionText());
//                    }
//                    medicines.add(mDto);
//                }
//                if(medicines.size()>0){
//                    mlistDto.setMedicines(medicines);
//                    //dosageDto.setRecordCreatorType(medicineEntityList.get(0).getRecordCreatorType());
//                }
//                medicineList.add(mlistDto);            
//            }
//            
//            dosageDto.setMedicineList(medicineList);
//            dosageList.add(dosageDto);
//        }
//        dosageList.sort((a,b)->b.getDosageDate().compareTo(a.getDosageDate()));
//        ResponseMedicineListDto responseDto = new ResponseMedicineListDto();
//        responseDto.setDosageList(dosageList);
//        responseDto.setStatus(ResponseBaseDto.SUCCESS);
//        
//        return responseDto;
//    }
    
}
