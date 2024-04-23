/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import phr.datadomain.entity.DosageAttentionEntity;
import phr.datadomain.entity.DosageDoctorEntity;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.DosageHeadEntity;
import phr.datadomain.entity.DosageMedicalOrganizationEntity;
import phr.datadomain.entity.DosageMedicineAdditionEntity;
import phr.datadomain.entity.DosageMedicineAttentionEntity;
import phr.datadomain.entity.DosageMedicineEntity;
import phr.datadomain.entity.DosageNoteEntity;
import phr.datadomain.entity.DosageOrganProvisionInfoEntity;
import phr.datadomain.entity.DosagePatientInputEntity;
import phr.datadomain.entity.DosageRecipeAdditionEntity;
import phr.datadomain.entity.DosageRecipeAttentionEntity;
import phr.datadomain.entity.DosageRecipeEntity;
import phr.datadomain.entity.DosageRemarkEntity;
import phr.datadomain.entity.DosageTypeCd;
import phr.datadomain.entity.NonPrescriptionDrugsEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.PatientSpecialInstructionEntity;
import phr.datadomain.entity.PharmaceutistEntity;
import phr.datadomain.entity.SeparatorInfoEntity;
import phr.dto.DosageEntitySetDto;
import phr.dto.MedicineFormatRecordNo;
import phr.dto.MedicineQRVersion;
import phr.service.IDosageExportService;
import phr.service.IExportMedicineListService;
import phr.service.IPatientManagementService;

/**
 *
 * @author kis-note-027_user
 */
public class ExportMedicineListService implements IExportMedicineListService{

    @Override
    public Map<String, String> getExportMedicineList(String phrId) throws Throwable {
//    public List<String> getExportMedicineList(String phrId) throws Throwable {
        IDosageExportService service = new DosageExportService();
        
       List<DosageEntitySetDto> setDtoList = service.getDosageSetList(phrId);
       List<String> medicineTextList = new ArrayList();
       Map medicineTextMap = new TreeMap();
       //1調剤ごとに出力
       for(DosageEntitySetDto dosageEntity:setDtoList){
           if(dosageEntity.getDosage(0)!=null){
               if(dosageEntity.getDosage(0).getSeq()==0){
                   continue;
               }
           }
           //出力用テキスト
           StringBuilder expText = new StringBuilder();
           //出力タイトル
           String titleText ="";
           
           //バージョン情報
           expText.append(getVersionRecord());
           //患者情報
           if(dosageEntity.getDosageHead()==null||dosageEntity.getDosageHead().getPatientName()==null){
               expText.append(getDosageHeadRecord(getPatientInfo(phrId)));
           }else{
                expText.append(getDosageHeadRecord(dosageEntity.getDosageHead()));
           }
           //患者特記
           if(!dosageEntity.getPatientSpecialInstructionList().isEmpty()){
               for(PatientSpecialInstructionEntity psiEntity:dosageEntity.getPatientSpecialInstructionList()){
                   expText.append(getPatientSpecialRecord(psiEntity));
               }
           }
           //一般用医薬品情報
           if(!dosageEntity.getNonPrescriptionDrugsList().isEmpty()){
               for(NonPrescriptionDrugsEntity nonpreEntity:dosageEntity.getNonPrescriptionDrugsList()){
                   expText.append(getNonpreDrugRecord(nonpreEntity));
               }
           }
           //手帳メモ情報
           if(!dosageEntity.getDosageNoteList().isEmpty()){
               for(DosageNoteEntity noteEntity:dosageEntity.getDosageNoteList()){
                    expText.append(getDosageNoteRecord(noteEntity));
               }
           }
           //調剤年月日
           if(!dosageEntity.getDosageList().isEmpty()){
               titleText = phrId +"_"+ dosageEntity.getDosageList().get(0).getDosageId() + "_" + setDatetoString(dosageEntity.getDosageList().get(0).getDosageDate());
               //市販薬のみで調剤薬がない(＝レシピ情報がない)場合は以下の処理は実行しない
               if(!dosageEntity.getDosageRecipeList().isEmpty()){
                   for(DosageEntity dosageent:dosageEntity.getDosageList()){
                        int seq = dosageent.getSeq();
                        expText.append(getDosageRecord(dosageent));
                        //調剤医療機関情報（薬局情報）
                        if(!dosageEntity.getMedicalOrganizationList().isEmpty()){
                            for(DosageMedicalOrganizationEntity pharmacy:dosageEntity.getMedicalOrganizationList()){
                                if(pharmacy.getSeq()==seq && pharmacy.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
                                    expText.append(getPharmacyRecord(pharmacy));
                                }
                            }
                        }
                        //調剤医師薬剤師情報（薬剤師情報）
                        if(!dosageEntity.getDosageDoctorList().isEmpty()){
                            for(DosageDoctorEntity pharmacist:dosageEntity.getDosageDoctorList()){
                                if(pharmacist.getSeq()==seq && pharmacist.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
                                    expText.append(getPharmacistRecord(pharmacist));
                                }                                
                            }
                        }
                        //処方医療機関情報
                        if(!dosageEntity.getMedicalOrganizationList().isEmpty()){
                            for(DosageMedicalOrganizationEntity hospital:dosageEntity.getMedicalOrganizationList()){
                                if(hospital.getSeq()==seq && hospital.getDosageTypeCd().equals(DosageTypeCd.RECIPE.getId())){
                                    expText.append(getHospitalRecord(hospital));
                                }
                            }
                        }
                        //処方医師情報
                        if(!dosageEntity.getDosageDoctorList().isEmpty()){
                            for(DosageDoctorEntity doctor:dosageEntity.getDosageDoctorList()){
                                if(doctor.getSeq()==seq && doctor.getDosageTypeCd().equals(DosageTypeCd.RECIPE.getId())){
                                    expText.append(getDoctorRecord(doctor));
                                }                                
                            }
                        }
                        for(DosageRecipeEntity recipe:dosageEntity.getDosageRecipeList()){
                            if(recipe.getSeq()==seq){
                                int recipeNo=recipe.getRecipeNo();
                                //薬品情報
                                if(!dosageEntity.getDosageMedicineList().isEmpty()){
                                   for(DosageMedicineEntity medicine:dosageEntity.getDosageMedicineList()){
                                        int medicineSeq=medicine.getMedicineSeq();
                                        if(medicine.getSeq()==seq && medicine.getRecipeNo()==recipeNo){
                                            expText.append(getMedicineRecord(medicine));
                                        }
                                        //薬品補足情報
                                        if(!dosageEntity.getMedicineAdditionList().isEmpty()){
                                            for(DosageMedicineAdditionEntity madd:dosageEntity.getMedicineAdditionList()){
                                                if(madd.getSeq()==seq && madd.getRecipeNo()==recipeNo && madd.getMedicineSeq()==medicineSeq){
                                                    expText.append(getMedicineAdditionRecord(madd));
                                                }
                                            }
                                        }
                                        //薬品服用注意情報
                                        if(!dosageEntity.getMedicineAttentionList().isEmpty()){
                                            for(DosageMedicineAttentionEntity matt:dosageEntity.getMedicineAttentionList()){
                                                if(matt.getSeq()==seq && matt.getRecipeNo()==recipeNo && matt.getMedicineSeq()==medicineSeq){
                                                    expText.append(getMedicineAttentionRecord(matt));
                                                }
                                            }
                                        }

                                   } 
                                }
                                //用法
                                expText.append(getDosageRecipeRecord(recipe));
                                //用法補足
                                if(!dosageEntity.getDosageRecipeAddList().isEmpty()){
                                    for(DosageRecipeAdditionEntity radd:dosageEntity.getDosageRecipeAddList()){
                                        if(radd.getSeq()==seq && radd.getRecipeNo()==recipeNo){
                                            expText.append(getRecipeAdditionRecord(radd));
                                        }
                                    }
                                }
                            //処方服用注意
                                if(!dosageEntity.getRecipeAttentionList().isEmpty()){
                                    for(DosageRecipeAttentionEntity ratt:dosageEntity.getRecipeAttentionList()){
                                        if(ratt.getSeq()==seq && ratt.getRecipeNo()==recipeNo){
                                            expText.append(getRecipeAttentionRecord(ratt));
                                        }
                                    }
                                }
                            }
                        }

                        //服用注意
                        if(!dosageEntity.getDosageAttentionList().isEmpty()){
                           for(DosageAttentionEntity dattent:dosageEntity.getDosageAttentionList()){
                               if(dattent.getSeq()==seq){
                                    expText.append(getDosageAttentionRecord(dattent));
                               }
                           }
                        }
                        //医療機関提供情報
                       if(!dosageEntity.getOrganProvisionInfoList().isEmpty()){
                           for(DosageOrganProvisionInfoEntity provinfo:dosageEntity.getOrganProvisionInfoList()){
                               if(provinfo.getSeq()==seq){
                                    expText.append(getProvisionInfoRecord(provinfo));
                               }
                           }
                       }
                        //備考
                       if(!dosageEntity.getDosageRemarkList().isEmpty()){
                           for(DosageRemarkEntity remark:dosageEntity.getDosageRemarkList()){
                               if(remark.getSeq()==seq){
                                    expText.append(getDosageRemarkRecord(remark));
                               }
                           }
                       }
                        //患者記入情報
                       if(!dosageEntity.getPatientInputList().isEmpty()){
                           for(DosagePatientInputEntity patinput:dosageEntity.getPatientInputList()){
                               if(patinput.getSeq()==seq){
                                    expText.append( getPatientInputRecord(patinput));
                               }
                           }
                       }
                   }
               }
           }


           //かかりつけ薬剤師情報
           if(!dosageEntity.getPharmaceutistList().isEmpty()){
               for(PharmaceutistEntity pharmEntity:dosageEntity.getPharmaceutistList()){
                   expText.append(getParmaceutistRecord(pharmEntity));
               }
           }


           medicineTextMap.put(titleText, expText.toString());
           //medicineTextList.add(expText.toString());
       }
        
        
        return medicineTextMap;
        //return medicineTextList;
    }
    
    /**
     * バージョンレコード作成
     * @return 
     */
    private String getVersionRecord(){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineQRVersion.VERSION2_4.getText());
        itemlist.add("2");
        return setCommaSentence(itemlist);
    }
    
    /**
     * 患者情報レコード作成
     * @return 
     */
    private String getDosageHeadRecord(DosageHeadEntity dosageHead){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.DOSAGEHEAD.getId());
        itemlist.add(dosageHead.getPatientName());
        itemlist.add(dosageHead.getSexCd());
        if(dosageHead.getBirthDate()==null){
            itemlist.add("");
        }else{
            itemlist.add(setDatetoString(dosageHead.getBirthDate()));
        }
        itemlist.add(dosageHead.getZipCode());
        itemlist.add(dosageHead.getAddressLine());
        itemlist.add(dosageHead.getTelNo());
        itemlist.add(dosageHead.getEmergencyContact());
        itemlist.add("");
        itemlist.add("");
        itemlist.add(dosageHead.getNameInKana());
        
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getPatientSpecialRecord(PatientSpecialInstructionEntity psiEntity){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.SPINST.getId());
        itemlist.add(psiEntity.getTypeCd());
        itemlist.add(psiEntity.getNoteValue());
        itemlist.add(psiEntity.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getNonpreDrugRecord(NonPrescriptionDrugsEntity nonpreEntity){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.NONPRESC.getId());
        itemlist.add(nonpreEntity.getMedicineName());
        if(nonpreEntity.getStartDate()==null){
            itemlist.add("");
        }else{
            itemlist.add(setDatetoString(nonpreEntity.getStartDate()));
        }
        if(nonpreEntity.getEndDate()==null){
            itemlist.add("");
        }else{
            itemlist.add(setDatetoString(nonpreEntity.getEndDate()));
        }
        itemlist.add(nonpreEntity.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getDosageNoteRecord(DosageNoteEntity noteEntity){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.DOSAGENOTE.getId());
        itemlist.add(noteEntity.getNoteValue());
        if(noteEntity.getInputDate()==null){
            itemlist.add("");
        }else{
            itemlist.add(setDatetoString(noteEntity.getInputDate()));
        }
        itemlist.add(noteEntity.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getDosageRecord(DosageEntity dosageEntity){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.DOSAGE.getId());
        itemlist.add(setDatetoString(dosageEntity.getDosageDate()));
        itemlist.add(dosageEntity.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getPharmacyRecord(DosageMedicalOrganizationEntity pharmacy){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.PHARMACY.getId());
        itemlist.add(pharmacy.getMedicalOrganizationName());
        itemlist.add(pharmacy.getPrefectureCd());
        itemlist.add(pharmacy.getOrganizationType());
        itemlist.add(pharmacy.getMedicalOrganizationCd());
        itemlist.add(pharmacy.getZipCode());
        itemlist.add(pharmacy.getAddressLine());
        itemlist.add(pharmacy.getTelNo());
        itemlist.add(pharmacy.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getPharmacistRecord(DosageDoctorEntity pharmacist){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.PHARMACIST.getId());
        itemlist.add(pharmacist.getDoctorName());
        itemlist.add(pharmacist.getContactAddress());
        itemlist.add(pharmacist.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getHospitalRecord(DosageMedicalOrganizationEntity hospital){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.MEDORGAN.getId());
        itemlist.add(hospital.getMedicalOrganizationName());
        itemlist.add(hospital.getPrefectureCd());
        itemlist.add(hospital.getOrganizationType());
        itemlist.add(hospital.getMedicalOrganizationCd());
        itemlist.add(hospital.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getDoctorRecord(DosageDoctorEntity doctor){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.DOCTOR.getId());
        itemlist.add(doctor.getDoctorName());
        itemlist.add(doctor.getDepartmentName());
        itemlist.add(doctor.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getMedicineRecord(DosageMedicineEntity medicine){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.MEDICINE.getId());
        itemlist.add(String.valueOf(medicine.getRecipeNo()));
        itemlist.add(medicine.getMedicineName());
        itemlist.add(medicine.getAmount());
        itemlist.add(medicine.getUnitName());
        itemlist.add(medicine.getMedicineCdType());
        itemlist.add(medicine.getMedicineCd());
        itemlist.add(medicine.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getMedicineAdditionRecord(DosageMedicineAdditionEntity addition){
        List<String> itemlist = new ArrayList();
       itemlist.add(MedicineFormatRecordNo.MEDADD.getId());
       itemlist.add(String.valueOf(addition.getRecipeNo()));
       itemlist.add(addition.getAdditionText());
       itemlist.add(addition.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getMedicineAttentionRecord(DosageMedicineAttentionEntity attention){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.MEDATTENT.getId());
        itemlist.add(String.valueOf(attention.getRecipeNo()));
        itemlist.add(attention.getAttentionText());
        itemlist.add(attention.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getDosageRecipeRecord(DosageRecipeEntity recipe){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.RECIPE.getId());
        itemlist.add(String.valueOf(recipe.getRecipeNo()));
        itemlist.add(recipe.getUsageName());
        itemlist.add(String.valueOf(recipe.getDosageQuantity()));
        itemlist.add(recipe.getDosageUnit());
        itemlist.add(recipe.getDosageForms());
        itemlist.add(recipe.getUsageCdType());
        itemlist.add(recipe.getUsageCd());
        itemlist.add(recipe.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getRecipeAdditionRecord(DosageRecipeAdditionEntity raddtion){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.RECIPEADD.getId());
        itemlist.add(String.valueOf(raddtion.getRecipeNo()));
        itemlist.add(raddtion.getAdditionText());
        itemlist.add(raddtion.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getRecipeAttentionRecord(DosageRecipeAttentionEntity rattent){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.RECATTENT.getId());
        itemlist.add(String.valueOf(rattent.getRecipeNo()));
        itemlist.add(rattent.getAttentionText());
        itemlist.add(rattent.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getDosageAttentionRecord(DosageAttentionEntity dattent){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.DOSAGEATTENT.getId());
        itemlist.add(dattent.getAttentionText());
        itemlist.add(dattent.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getProvisionInfoRecord(DosageOrganProvisionInfoEntity provinfo){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.ORGANPROVINFO.getId());
        itemlist.add(provinfo.getProvisionText());
        itemlist.add(provinfo.getProvisionType());
        itemlist.add(provinfo.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getDosageRemarkRecord(DosageRemarkEntity remark){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.REMARK.getId());
        itemlist.add(remark.getRemarkText());
        itemlist.add(remark.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getPatientInputRecord(DosagePatientInputEntity patinput){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.PATINPUT.getId());
        itemlist.add(patinput.getInputText());
        if(patinput.getInputDate()==null){
            itemlist.add("");
        }else{
            itemlist.add(setDatetoString(patinput.getInputDate()));
        }
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getParmaceutistRecord(PharmaceutistEntity pharment){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.PARMACEUTIST.getId());
        itemlist.add(pharment.getPharmaceutistName());
        itemlist.add(pharment.getPharmacy());
        itemlist.add(pharment.getContactAddress());
        if(pharment.getStartDate()==null){
            itemlist.add("");
        }else{
            itemlist.add(setDatetoString(pharment.getStartDate()));
        }
        if(pharment.getEndDate()==null){
            itemlist.add("");
        }else{
            itemlist.add(setDatetoString(pharment.getStartDate()));
        }
        itemlist.add(pharment.getRecordCreatorType());
        return setCommaSentence(itemlist);
    }
    /**
     * 
     * @return 
     */
    private String getSeparatorRecord(SeparatorInfoEntity separator){
        List<String> itemlist = new ArrayList();
        itemlist.add(MedicineFormatRecordNo.SEPARATOR.getId());
        itemlist.add(String.valueOf(separator.getSeparatorId()));
        itemlist.add(String.valueOf(separator.getSeparateCount()));
        itemlist.add(String.valueOf(separator.getSeparateNo()));
        return setCommaSentence(itemlist);
    }
    

    /**
     * カンマ区切りセンテンスへの変換
     * @param itemlist
     * @return 
     */
    private String setCommaSentence(List<String> itemlist){
        StringBuilder targetString = new StringBuilder();
        for(int i=0;i<itemlist.size();i++){
            if(i>0){
                targetString.append(",");
            }
            if(itemlist.get(i)==null){
                targetString.append("");                
            }else{
                targetString.append(itemlist.get(i));
            }
        }
        targetString.append("\r\n");
        return targetString.toString();
    }
    
    private String setDatetoString(Date d) {
      StringBuilder s = new StringBuilder();
      Calendar c = Calendar.getInstance();
      c.setTime(d);
      s.append(c.get(Calendar.YEAR));
      s.append(getZero(c.get(Calendar.MONTH) + 1));
      s.append(getZero(c.get(Calendar.DAY_OF_MONTH)));
      return s.toString();
    }    
    private String getZero(int number){
      String sNumber = "";
      if(number<10){
          sNumber = "0"+number;
      }else{
          sNumber = ""+number;
      }
      return sNumber;
    }
    private DosageHeadEntity getPatientInfo(String phrId) throws Throwable{
        DosageHeadEntity entity = new DosageHeadEntity();
        IPatientManagementService patService = new PatientManagementService();
        PatientEntity patientInfo = patService.getPatient(phrId);
        entity.setPatientName(patientInfo.getFamilyName() + "　" + patientInfo.getGivenName());
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
            if(patientInfo.getBuildingName()!=null){
                entity.setAddressLine(patientInfo.getAddressLine() + patientInfo.getBuildingName());                
            }else{
                entity.setAddressLine(patientInfo.getAddressLine());
            }
        }
        entity.setTelNo(patientInfo.getTelNo());
        if(patientInfo.getOtherContactNo()!=null){
            entity.setEmergencyContact(patientInfo.getOtherContactNo());
        }
        entity.setNameInKana(patientInfo.getFamilyKana() + "　" + patientInfo.getGivenKana());
         
        return entity;
    }
}
