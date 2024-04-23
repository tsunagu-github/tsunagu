/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import phr.datadomain.entity.PatientSpecialInstructionEntity;
import phr.datadomain.entity.PharmaceutistEntity;
import phr.datadomain.entity.RecordCreatorTypeCd;
import phr.datadomain.entity.UnusedDrugInfoEntity;
import phr.dto.DosageEntitySetDto;
import phr.dto.MedicineFormatRecordNo;
import phr.dto.MedicineQRVersion;
import phr.service.IDosageImportService;
import phr.service.IImportMedicineSetService;

/**
 *
 * @author kis-note-027_user
 */
public class ImportMedicineSetService implements IImportMedicineSetService{
    /**
     * 
     * @param recordString (set your import Text after the Text split("\r\n"))
     * @param phrId
     * @param dosageId
     * @return
     * @throws Throwable 
     */
    @Override
    public DosageEntitySetDto getImportMedicineSet(String[] recordString, String phrId, String dosageId) throws Throwable {
        IDosageImportService dosageImportService = new DosageImportService();
        
        DosageEntitySetDto setDto = new DosageEntitySetDto();
                //DosageIdを取得する
        //String dosageId = "";
        String nonpreDosageId = "";
        //各Seqを設定する
        int pSISeq = 0;
        int nopreSeq = 0;
        int dNoteSeq = 0;
        int dosageSeq = 0;
        int medicineSeq = 0;
        int medAddSeq = 0;
        int medAttentSeq = 0;
        int recAttentSeq = 0;
        int recAddSeq = 0;
        int dosageAttentSeq = 0;
        int provSeq = 0;
        int remarkSeq = 0;
        int patInputSeq = 0;
        int parmaCeutistSeq = 0;
        //バージョン情報
        String QRver = "";
            for(int i=0;i<recordString.length;i++){
                //カンマで分割する
                String[] recordSet = recordString[i].split(",",-1);
                String recordNo = recordSet[0];

                if(recordNo.equals(MedicineFormatRecordNo.DOSAGEHEAD.getId())){
                    //ヘッダ情報
                    //setDto.setDosageHead(setHead(recordSet,QRver,dosageId));
                    setDto.setDosageHeadListItem(setHead(recordSet,QRver,dosageId));
                    //setDto.getDosageHead().setDosageId(dosageId);
                }else if(recordNo.equals(MedicineFormatRecordNo.SPINST.getId())){
                    // 患者特記情報
                    pSISeq = pSISeq+1;
                    setDto.setPatientSpecialInstruction(setPSInstruction(recordSet,dosageId,pSISeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.NONPRESC.getId())){
                    //市販薬情報
                    nopreSeq = nopreSeq + 1;
                    //if(nonpreDosageId.isEmpty()){
                        //市販薬用dosageId取得
                        nonpreDosageId = dosageImportService.getNewDosageId();
                        //DosagHead作成
                        //setDto.setDosageHeadNonpre(setNonpreHead(setDto.getDosageHead(),nonpreDosageId));
                        setDto.setDosageHeadNonpreListItem(setNonpreHead(setDto.getDosageHeadListItem(0),nonpreDosageId));
                    //}
                    setDto.setNonPrescriptionDrugs(setNonpre(recordSet,nonpreDosageId,nopreSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.DOSAGENOTE.getId())){
                    //手帳メモ情報
                    dNoteSeq = dNoteSeq + 1;
                    setDto.setDosageNote(setDosageNote(recordSet,dosageId,dNoteSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.DOSAGE.getId())){
                    //調剤情報
                    if(setDto.getDosageList().isEmpty()){
                        dosageSeq = 1;                        
                    }else{
                        dosageId = dosageImportService.getNewDosageId();
                        pSISeq = 0;
                        nopreSeq = 0;
                        dNoteSeq = 0;
                        dosageSeq = 1;
                        medicineSeq = 0;
                        medAddSeq = 0;
                        medAttentSeq = 0;
                        recAttentSeq = 0;
                        recAddSeq = 0;
                        dosageAttentSeq = 0;
                        provSeq = 0;
                        remarkSeq = 0;
                        patInputSeq = 0;
                        parmaCeutistSeq = 0;
                        
                        setDto.setDosageHeadListItem(setNonpreHead(setDto.getDosageHeadListItem(0),dosageId));
                    }
                    DosageEntity dEnt = setDosageEntity(recordSet,dosageId,dosageSeq,phrId); 
                    setDto.setDosage(dEnt);
                }else if(recordNo.equals(MedicineFormatRecordNo.PHARMACY.getId())){
                    //薬局情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }
                    if(setDto.getMedicalOrganizationList().size()>0){
                        if(checkhospitalDosageSeq(setDto.getMedicalOrganizationList(),dosageId,DosageTypeCd.DOSAGE,dosageSeq)){
                            dosageSeq = dosageSeq+1;
                            for(DosageEntity dEnt:setDto.getDosageList()){
                                if(dEnt.getSeq()==(dosageSeq-1)){
                                    DosageEntity dosageent = setNewDosageEntity(dEnt,dosageId,dosageSeq,phrId); 
                                    setDto.setDosage(dosageent);
                                    break;
                                }
                            }
                        }
                    }
                    setDto.setMedicalOrganization(setPharmacy(recordSet,dosageId,dosageSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.PHARMACIST.getId())){
                    //薬剤師情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }
                    if(setDto.getDosageDoctorList().size()>0){
                        if(checkDoctorDosageSeq(setDto.getDosageDoctorList(),dosageId,DosageTypeCd.DOSAGE,dosageSeq)){
                            dosageSeq = dosageSeq+1;
                            for(DosageEntity dEnt:setDto.getDosageList()){
                                if(dEnt.getSeq()==(dosageSeq-1)){
                                    DosageEntity dosageent = setNewDosageEntity(dEnt,dosageId,dosageSeq,phrId);                                     
                                    setDto.setDosage(dosageent);
                                    break;
                                }
                            }
                            for(DosageMedicalOrganizationEntity copypharm:setDto.getMedicalOrganizationList()){
                                if(copypharm.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
                                    if(copypharm.getSeq()==(dosageSeq-1)){
                                        DosageMedicalOrganizationEntity phaEnt = setNewHospital(copypharm,dosageSeq);
                                        setDto.setMedicalOrganization(phaEnt);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    setDto.setDosageDoctor(setPharmacist(recordSet,dosageId,dosageSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.MEDORGAN.getId())){
                    //医療機関情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }
                    if(setDto.getMedicalOrganizationList().size()>1){
                        if(checkhospitalDosageSeq(setDto.getMedicalOrganizationList(),dosageId,DosageTypeCd.RECIPE,dosageSeq)){
                            dosageSeq = dosageSeq+1;
                            for(DosageEntity dEnt:setDto.getDosageList()){
                                if(dEnt.getSeq()==(dosageSeq-1)){
                                    DosageEntity dosageent = setNewDosageEntity(dEnt,dosageId,dosageSeq,phrId);                                     
                                    setDto.setDosage(dosageent);
                                    break;
                                }
                            }
                            for(DosageMedicalOrganizationEntity copypharm:setDto.getMedicalOrganizationList()){
                                if(copypharm.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
                                    if(copypharm.getSeq()==(dosageSeq-1)){
                                        DosageMedicalOrganizationEntity phaEnt = setNewHospital(copypharm,dosageSeq);
                                        setDto.setMedicalOrganization(phaEnt);
                                        break;
                                    }
                                }
                            }
                            if(!setDto.getDosageDoctorList().isEmpty()){
                                for(DosageDoctorEntity copydoc:setDto.getDosageDoctorList()){
                                    if(copydoc.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
                                        if(copydoc.getSeq()==(dosageSeq-1)){
                                            DosageDoctorEntity docEnt = setNewDoctor(copydoc,dosageSeq);
                                            setDto.setDosageDoctor(docEnt);
                                            break;
                                        }
                                    }
                                }         
                            }
                        }
                    }
                    setDto.setMedicalOrganization(setHospital(recordSet,dosageId,dosageSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.DOCTOR.getId())){
                    //医師情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }
                    if(setDto.getDosageDoctorList().size()>1){
                        if(checkDoctorDosageSeq(setDto.getDosageDoctorList(),dosageId,DosageTypeCd.RECIPE,dosageSeq)){
                            dosageSeq = dosageSeq+1;
                            for(DosageEntity dEnt:setDto.getDosageList()){
                                if(dEnt.getSeq()==(dosageSeq-1)){
                                    DosageEntity dosageent = setNewDosageEntity(dEnt,dosageId,dosageSeq,phrId);                                     
                                    setDto.setDosage(dosageent);
                                    break;
                                }
                            }
                            for(DosageMedicalOrganizationEntity copypharm:setDto.getMedicalOrganizationList()){
                                if(copypharm.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
                                    if(copypharm.getSeq()==(dosageSeq-1)){
                                        DosageMedicalOrganizationEntity phaEnt = setNewHospital(copypharm,dosageSeq);
                                        setDto.setMedicalOrganization(phaEnt);
                                        break;
                                    }
                                }
                            }
                            for(DosageMedicalOrganizationEntity copypharm:setDto.getMedicalOrganizationList()){
                                if(copypharm.getDosageTypeCd().equals(DosageTypeCd.RECIPE.getId())){
                                    if(copypharm.getSeq()==(dosageSeq-1)){
                                        DosageMedicalOrganizationEntity hospEnt = setNewHospital(copypharm,dosageSeq);
                                        setDto.setMedicalOrganization(hospEnt);
                                        break;
                                    }
                                }
                            }
                            if(!setDto.getDosageDoctorList().isEmpty()){
                                for(DosageDoctorEntity copydoc:setDto.getDosageDoctorList()){
                                    if(copydoc.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
                                        if(copydoc.getSeq()==(dosageSeq-1)){
                                            DosageDoctorEntity docEnt = setNewDoctor(copydoc,dosageSeq);
                                            setDto.setDosageDoctor(docEnt);
                                            break;
                                        }
                                    }
                                }         
                            }                            
                        }
                    }
                    setDto.setDosageDoctor(setDoctor(recordSet,dosageId,dosageSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.MEDICINE.getId())){
                    //薬剤情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }                    
                    medicineSeq = medicineSeq + 1;
                    setDto.setDosageMedicine(setMedicine(recordSet,dosageId,dosageSeq,medicineSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.MEDADD.getId())){
                    //薬剤補足情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }
                    medAddSeq = medAddSeq + 1;
                    setDto.setMedicineAddition(setMedAddition(recordSet,dosageId,dosageSeq,medicineSeq,medAddSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.MEDATTENT.getId())){
                    //薬品服用注意情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }                    
                    medAttentSeq = medAttentSeq + 1;
                    setDto.setMedicineAttention(setMedAttent(recordSet,dosageId,dosageSeq,medicineSeq,medAttentSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.RECIPE.getId())){
                    //レシピ情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }                    
                    setDto.setDosageRecipe(setRecipe(recordSet,dosageId,dosageSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.RECIPEADD.getId())){
                    //用法補足情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }                    
                    recAddSeq = recAddSeq + 1;
                    setDto.setDosageRecipeAdd(setRecipeAdd(recordSet,dosageId,dosageSeq,recAddSeq));                    
                }else if(recordNo.equals(MedicineFormatRecordNo.RECATTENT.getId())){
                    //処方服用注意情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }                    
                    recAttentSeq = recAttentSeq + 1;
                    setDto.setRecipeAttention(setRecipeAttent(recordSet,dosageId,dosageSeq,recAttentSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.DOSAGEATTENT.getId())){
                    //服用注意情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }
                    dosageAttentSeq = dosageAttentSeq +1;
                    //int dosageSeqforDA = 0;
                    //for(int l=0;l<dosageSeq;l++){
                    //    dosageSeqforDA = dosageSeqforDA+1;
                        setDto.setDosageAttention(setDosageAttent(recordSet,dosageId,dosageSeq,dosageAttentSeq));
                    //    setDto.setDosageAttention(setDosageAttent(recordSet,dosageId,dosageSeqforDA,dosageAttentSeq));
                    //}
                }else if(recordNo.equals(MedicineFormatRecordNo.ORGANPROVINFO.getId())){
                    //医療機関等提供情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }
                    provSeq = provSeq +1;
                    //int dosageSeqforPR = 0;
                    //for(int l=0;l<dosageSeq;l++){
                    //    dosageSeqforPR = dosageSeqforPR+1;
                        setDto.setOrganProvisionInfo(setDosageOrganInfo(recordSet,dosageId,dosageSeq,provSeq));
                        //setDto.setOrganProvisionInfo(setDosageOrganInfo(recordSet,dosageId,dosageSeqforPR,provSeq));                        
                    //}
                }else if(recordNo.equals(MedicineFormatRecordNo.UNUSEDDRUGINFO.getId())){
                    //残薬確認情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }
                    setDto.setUnusedDrugInfo(setUnusedDrugInfo(recordSet,dosageId,dosageSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.REMARK.getId())){
                    //備考情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }
                    remarkSeq = remarkSeq + 1;
                    //int dosageSeqforRM = 0;
                    //for(int l=0;l<dosageSeq;l++){
                    //    dosageSeqforRM = dosageSeqforRM+1;
                        setDto.setDosageRemark(setDosageRemark(recordSet,dosageId,dosageSeq,remarkSeq));
                        //setDto.setDosageRemark(setDosageRemark(recordSet,dosageId,dosageSeqforRM,remarkSeq));
                    //}
                }else if(recordNo.equals(MedicineFormatRecordNo.PATINPUT.getId())){
                    //患者等記入情報
                    if(dosageSeq==0){
                        dosageSeq=1;
                    }
                    patInputSeq = patInputSeq +1;
                    //int dosageSeqforPI =0;
                    //for(int l=0;l<dosageSeq;l++){
                    //    dosageSeqforPI= dosageSeqforPI+1;
                        setDto.setPatientInput(setPatientInput(recordSet,dosageId,dosageSeq,patInputSeq));
                        //setDto.setPatientInput(setPatientInput(recordSet,dosageId,dosageSeqforPI,patInputSeq));
                    //}
                }else if(recordNo.equals(MedicineFormatRecordNo.PARMACEUTIST.getId())){
                    //かかりつけ薬剤師情報
                    parmaCeutistSeq = parmaCeutistSeq + 1;
                    setDto.setPharmaceutist(setPharmaceutist(recordSet,dosageId,parmaCeutistSeq));
                }else if(recordNo.equals(MedicineFormatRecordNo.SEPARATOR.getId())){

                }else if(i==0){
                    QRver = recordNo;
                    if(!recordNo.contains(MedicineQRVersion.FIXEDVALUE.getText())){
                        return null;
                    }
                }
            }
            
            //市販薬用Dosage作成
            //if(setDto.getDosageHeadNonpre()==null){
            if(setDto.getDosageHeadNonpreList().isEmpty()){    
                //Dosageがない場合
                if(setDto.getDosageList().isEmpty()){
                        DosageEntity tmpEnt = new DosageEntity();
                        tmpEnt.setDosageId(dosageId);
                        if(dosageSeq==0){
                            dosageSeq=1;
                        }                    
                        tmpEnt.setSeq(dosageSeq);
                        tmpEnt.setDosageDate(new Date());
                        tmpEnt.setPHR_ID(phrId);
                        tmpEnt.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
                        setDto.setDosage(tmpEnt);
                }                
            }else{
                for(NonPrescriptionDrugsEntity nDrugEnt:setDto.getNonPrescriptionDrugsList()){
                    DosageEntity nonpreEnt = new DosageEntity();
                    //nonpreEnt.setDosageId(nonpreDosageId);
                    nonpreEnt.setDosageId(nDrugEnt.getDosageId());
                    nonpreEnt.setSeq(nDrugEnt.getSeq());
                    if(nDrugEnt.getStartDate()!=null){
                        nonpreEnt.setDosageDate(nDrugEnt.getStartDate());
                    }else if(nDrugEnt.getEndDate()!=null){
                        nonpreEnt.setDosageDate(nDrugEnt.getEndDate());
                    }else{
                        nonpreEnt.setDosageDate(new Date());
                    }
                    nonpreEnt.setPHR_ID(phrId);
                    nonpreEnt.setRecordCreatorType(nDrugEnt.getRecordCreatorType());
                    setDto.setDosage(nonpreEnt);
                }
            }

            //dosage,parmaceutist,dosageNote,patientSpecialInstructionを持たないDosageHeadは削除
            //if(setDto.getDosageHead()!=null){
            if(!setDto.getDosageHeadList().isEmpty()){    
            if(setDto.getPharmaceutistList().isEmpty()){
                if(setDto.getDosageNoteList().isEmpty()){
                    if(setDto.getPatientSpecialInstructionList().isEmpty()){
                        int dcount = 0;
                        for(DosageEntity checkDosage:setDto.getDosageList()){
                            for(DosageHeadEntity headent:setDto.getDosageHeadList()){
                                if(checkDosage.getDosageId().equals(headent.getDosageId())){
                                    dcount = dcount+1;
                                }
                            }
                        }
                        if(dcount==0){
                            setDto.setDosageHeadList(null);
                        }
                    }
                }
            }
            }
                //Dosageに日付が入っていなかった場合
                for(DosageEntity entity:setDto.getDosageList()){
                    if(entity.getDosageDate()==null){
                        if(setDto.getNonPrescriptionDrugsList().isEmpty()){
                        //しょうがないので今日の日付
                                entity.setDosageDate(new Date());
                        }else{
                            //市販薬がある場合
                            for(NonPrescriptionDrugsEntity nmEnt:setDto.getNonPrescriptionDrugsList()){
                                boolean getDate = false;
                                if(nmEnt.getStartDate()!=null){
                                    //(開始日)
                                    getDate = true;
                                    entity.setDosageDate(nmEnt.getStartDate());
                                }else if(nmEnt.getEndDate()!=null){
                                    //(終了日)
                                    getDate = true;
                                    entity.setDosageDate(nmEnt.getEndDate());                            
                                }

                                if(!getDate){
                                    //しょうがないので今日の日付
                                    entity.setDosageDate(new Date());    
                                }                
                            }
                        }

                    }
                }        

        return setDto;
        
    }

    private DosageHeadEntity setHead(String[] recordSet,String QRver,String dosageId) throws ParseException{
        DosageHeadEntity entity = new DosageHeadEntity();
        entity.setQRVersion(QRver);
        entity.setDosageId(dosageId);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                   entity.setPatientName(recordSet[1]);
                   break;
                case 2:
                    entity.setSexCd(recordSet[2]);
                    break;
                case 3:
                    entity.setBirthDate(StrToDate(recordSet[3]));
                    break;
                case 4:
                    entity.setZipCode(recordSet[4]);
                    break;
                case 5:
                    entity.setAddressLine(recordSet[5]);
                    break;
                case 6:
                    entity.setTelNo(recordSet[6]);
                    break;
                case 7:
                    entity.setEmergencyContact(recordSet[7]);
                    break;
                case 8:
                    entity.setBloodType(recordSet[8]);
                    break;
                case 9:
                    entity.setWeight(recordSet[9]);
                    break;
                case 10:
                    entity.setNameInKana(recordSet[10]);
                    break;
            }
        }
        return entity;
    }

    private DosageHeadEntity setNonpreHead(DosageHeadEntity orgHead,String dosageId) throws ParseException{
        DosageHeadEntity entity = new DosageHeadEntity();
        entity.setDosageId(dosageId);
        entity.setQRVersion(orgHead.getQRVersion());
        if(orgHead.getPatientName()!=null){
            entity.setPatientName(orgHead.getPatientName());
        }
        if(orgHead.getSexCd()!=null){
            entity.setSexCd(orgHead.getSexCd());
        }
        if(orgHead.getBirthDate()!=null){
            entity.setBirthDate(orgHead.getBirthDate());
        }
        if(orgHead.getZipCode()!=null){
            entity.setZipCode(orgHead.getZipCode());
        }
        if(orgHead.getAddressLine()!=null){
            entity.setAddressLine(orgHead.getAddressLine());            
        }
        if(orgHead.getTelNo()!=null){
            entity.setTelNo(orgHead.getTelNo());
        }
        if(orgHead.getEmergencyContact()!=null){
            entity.setEmergencyContact(orgHead.getEmergencyContact());
        }
        if(orgHead.getBloodType()!=null){
            entity.setBloodType(orgHead.getBloodType());
        }
        if(orgHead.getWeight()!=null){
            entity.setWeight(orgHead.getWeight());
        }
        if(orgHead.getNameInKana()!=null){
            entity.setNameInKana(orgHead.getNameInKana());
        }
        return entity;
    }    
    
    private PatientSpecialInstructionEntity setPSInstruction(String[] recordSet,String dosageId,int pSISeq){
        PatientSpecialInstructionEntity entity = new PatientSpecialInstructionEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(pSISeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setTypeCd(recordSet[1]);
                    break;
                case 2:
                    entity.setNoteValue(recordSet[2]);
                    break;
                case 3:
                    entity.setRecordCreatorType(recordSet[3]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private NonPrescriptionDrugsEntity setNonpre(String[] recordSet,String dosageId,int nopreSeq) throws ParseException{
        NonPrescriptionDrugsEntity entity = new NonPrescriptionDrugsEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(nopreSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setMedicineName(recordSet[1]); 
                    break;
                case 2:
                    entity.setStartDate(StrToDate(recordSet[2]));
                    break;
                case 3:
                    entity.setEndDate(StrToDate(recordSet[3]));
                    break;
                case 4:
                    entity.setRecordCreatorType(recordSet[4]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private DosageNoteEntity setDosageNote(String[] recordSet,String dosageId,int dNoteSeq) throws ParseException{
        DosageNoteEntity entity = new DosageNoteEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dNoteSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setNoteValue(recordSet[1]);
                    break;
                case 2:
                    entity.setInputDate(StrToDate(recordSet[2]));
                    break;
                case 3:
                    entity.setRecordCreatorType(recordSet[3]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private DosageEntity setDosageEntity(String[] recordSet,String dosageId,int dosageSeq,String phrId) throws ParseException{
        DosageEntity entity = new DosageEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        entity.setPHR_ID(phrId);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setDosageDate(StrToDate(recordSet[1]));
                    break;
                case 2:
                    entity.setRecordCreatorType(recordSet[2]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }

    private DosageEntity setNewDosageEntity(DosageEntity orgEntity,String dosageId,int dosageSeq,String phrId){
        DosageEntity entity = new DosageEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        entity.setDosageDate(orgEntity.getDosageDate());
        entity.setPHR_ID(phrId);
        entity.setRecordCreatorType(orgEntity.getRecordCreatorType());
        return entity;
    }    
      
    private DosageMedicalOrganizationEntity setPharmacy(String[] recordSet,String dosageId, int dosageSeq){
        DosageMedicalOrganizationEntity entity = new DosageMedicalOrganizationEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);  
        entity.setDosageTypeCd(DosageTypeCd.DOSAGE.getId());
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setMedicalOrganizationName(recordSet[1]);
                    break;
                case 2:
                    entity.setPrefectureCd(recordSet[2]);
                    break;
                case 3:
                    entity.setOrganizationType(recordSet[3]);
                    break;
                case 4:
                    entity.setMedicalOrganizationCd(recordSet[4]);
                    break;
                case 5:
                    entity.setZipCode(recordSet[5]);
                    break;
                case 6:
                    entity.setAddressLine(recordSet[6]);
                    break;
                case 7:
                    entity.setTelNo(recordSet[7]);
                    break;
                case 8:
                    entity.setRecordCreatorType(recordSet[8]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }

    private DosageDoctorEntity setPharmacist(String[] recordSet,String dosageId,int dosageSeq){
        DosageDoctorEntity entity = new DosageDoctorEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        entity.setDosageTypeCd(DosageTypeCd.DOSAGE.getId());
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setDoctorName(recordSet[1]);
                    break;
                case 2:
                    entity.setContactAddress(recordSet[2]);
                    break;
                case 3:
                    entity.setRecordCreatorType(recordSet[3]);
                    break;
            }
        }
//        entity.setDepartmentName();
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private DosageMedicalOrganizationEntity setHospital(String[] recordSet,String dosageId, int dosageSeq){
        DosageMedicalOrganizationEntity entity = new DosageMedicalOrganizationEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);  
        entity.setDosageTypeCd(DosageTypeCd.RECIPE.getId());
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setMedicalOrganizationName(recordSet[1]);
                    break;
                case 2:
                    entity.setPrefectureCd(recordSet[2]);        
                    break;
                case 3:
                    entity.setOrganizationType(recordSet[3]);
                    break;
                case 4:
                    entity.setMedicalOrganizationCd(recordSet[4]);
                    break;
                case 5:
                    entity.setRecordCreatorType(recordSet[5]);
                    break;
            }
        }
        //entity.setZipCode(recordSet[5]);
        //entity.setAddressLine(recordSet[6]);
        //entity.setTelNo(recordSet[7]);
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private DosageMedicalOrganizationEntity setNewHospital(DosageMedicalOrganizationEntity oldHospital, int dosageSeq){
        DosageMedicalOrganizationEntity entity = new DosageMedicalOrganizationEntity();
        entity.setDosageId(oldHospital.getDosageId());
        entity.setSeq(dosageSeq);  
        entity.setDosageTypeCd(oldHospital.getDosageTypeCd());
        if(oldHospital.getMedicalOrganizationName()!=null){
            entity.setMedicalOrganizationName(oldHospital.getMedicalOrganizationName());
        }
        if(oldHospital.getMedicalOrganizationCd()!=null){
        entity.setMedicalOrganizationCd(oldHospital.getMedicalOrganizationCd());
        }
        if(oldHospital.getOrganizationType()!=null){
        entity.setOrganizationType(oldHospital.getOrganizationType());
        }
        if(oldHospital.getPrefectureCd()!=null){
            entity.setPrefectureCd(oldHospital.getPrefectureCd());
        }
        if(oldHospital.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
            if(oldHospital.getZipCode()!=null){
                entity.setZipCode(oldHospital.getZipCode());
            }
            if(oldHospital.getAddressLine()!=null){
                entity.setAddressLine(oldHospital.getAddressLine());
            }
            if(oldHospital.getTelNo()!=null){
                entity.setTelNo(oldHospital.getTelNo());
            }
        }
        entity.setRecordCreatorType(oldHospital.getRecordCreatorType());
        
        return entity;
    }
    
    private DosageDoctorEntity setDoctor(String[] recordSet,String dosageId,int dosageSeq){
        DosageDoctorEntity entity = new DosageDoctorEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        entity.setDosageTypeCd(DosageTypeCd.RECIPE.getId());
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setDoctorName(recordSet[1]);
                    break;
                case 2:
                    entity.setDepartmentName(recordSet[2]);
                    break;
                case 3:
                    entity.setRecordCreatorType(recordSet[3]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        //        entity.setContactAddress();
        return entity;
    }
    
    private DosageDoctorEntity setNewDoctor(DosageDoctorEntity orgDoc,int dosageSeq){
        DosageDoctorEntity entity = new DosageDoctorEntity();
        entity.setDosageId(orgDoc.getDosageId());
        entity.setSeq(dosageSeq);
        entity.setDosageTypeCd(orgDoc.getDosageTypeCd());
        if(orgDoc.getDoctorName()!=null){
            entity.setDoctorName(orgDoc.getDoctorName());
        }
        if(orgDoc.getRecordCreatorType()!=null){
            entity.setRecordCreatorType(orgDoc.getRecordCreatorType());
        }
        if(orgDoc.getDosageTypeCd().equals(DosageTypeCd.DOSAGE.getId())){
            if(orgDoc.getContactAddress()!=null){
                entity.setContactAddress(orgDoc.getContactAddress());
            }
        }else{
            if(orgDoc.getDepartmentName()!=null){
                entity.setDepartmentName(orgDoc.getDepartmentName());
            }
        }
        return entity;
    }    

    private DosageMedicineEntity setMedicine(String[] recordSet,String dosageId,int dosageSeq,int medicineSeq){
        DosageMedicineEntity entity = new DosageMedicineEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        entity.setMedicineSeq(medicineSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
                    break;
                case 2:
                    entity.setMedicineName(recordSet[2]);
                    break;
                case 3:
                    entity.setAmount(recordSet[3]);
                    break;
                case 4:
                    entity.setUnitName(recordSet[4]);
                    break;
                case 5:
                    entity.setMedicineCdType(recordSet[5]);
                    break;
                case 6:
                    entity.setMedicineCd(recordSet[6]);
                    break;
                case 7:
                    entity.setRecordCreatorType(recordSet[7]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private DosageMedicineAdditionEntity setMedAddition(String[] recordSet,String dosageId,int dosageSeq,int medicineSeq,int medAddSeq){
        DosageMedicineAdditionEntity entity = new DosageMedicineAdditionEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        entity.setMedicineSeq(medicineSeq);
        entity.setAdditionSeq(medAddSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
                    break;
                case 2:
                    entity.setAdditionText(recordSet[2]);
                    break;
                case 3:
                    entity.setRecordCreatorType(recordSet[3]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }

    private DosageMedicineAttentionEntity setMedAttent(String[] recordSet,String dosageId,int dosageSeq,int medicineSeq,int medAttentSeq){
        DosageMedicineAttentionEntity entity = new DosageMedicineAttentionEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        entity.setMedicineSeq(medicineSeq);
        entity.setAttentionSeq(medAttentSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
                    break;
                case 2:
                    entity.setAttentionText(recordSet[2]);
                    break;
                case 3:
                    entity.setRecordCreatorType(recordSet[3]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private DosageRecipeEntity setRecipe(String[] recordSet,String dosageId,int dosageSeq){
        DosageRecipeEntity entity = new DosageRecipeEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
                    break;
                case 2:
                    entity.setUsageName(recordSet[2]);
                    break;
                case 3:        
                    entity.setDosageQuantity(Integer.parseInt(recordSet[3]));
                    break;
                case 4:
                    entity.setDosageUnit(recordSet[4]);
                    break;
                case 5:
                    entity.setDosageForms(recordSet[5]);
                    break;
                case 6:
                    entity.setUsageCdType(recordSet[6]);
                    break;
                case 7:
                    entity.setUsageCd(recordSet[7]);
                    break;
                case 8:
                    entity.setRecordCreatorType(recordSet[8]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }

    private DosageRecipeAdditionEntity setRecipeAdd(String[] recordSet,String dosageId,int dosageSeq,int recAddSeq){
         DosageRecipeAdditionEntity entity = new  DosageRecipeAdditionEntity();
         entity.setDosageId(dosageId);
         entity.setSeq(dosageSeq);
         entity.setRecipeAdditionSeq(recAddSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:         
                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
                    break;
                case 2:
                     entity.setAdditionText(recordSet[2]);
                    break;
                case 3:
                     entity.setRecordCreatorType(recordSet[3]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private DosageRecipeAttentionEntity setRecipeAttent(String[] recordSet,String dosageId,int dosageSeq,int recAttenetSeq){
         DosageRecipeAttentionEntity entity = new  DosageRecipeAttentionEntity();
         entity.setDosageId(dosageId);
         entity.setSeq(dosageSeq);
         entity.setAttentionSeq(recAttenetSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:         
                    entity.setRecipeNo(Integer.parseInt(recordSet[1]));
                    break;
                case 2:
                     entity.setAttentionText(recordSet[2]);
                    break;
                case 3:
                     entity.setRecordCreatorType(recordSet[3]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private DosageAttentionEntity setDosageAttent(String[] recordSet,String dosageId,int dosageSeq,int dosageAttentSeq){
         DosageAttentionEntity entity = new  DosageAttentionEntity();
         entity.setDosageId(dosageId);
         entity.setSeq(dosageSeq);
         entity.setAttentionSeq(dosageAttentSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setAttentionText(recordSet[1]);
                    break;
                case 2:
                    entity.setRecordCreatorType(recordSet[2]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
         return entity;
    }    

    private DosageOrganProvisionInfoEntity setDosageOrganInfo(String[] recordSet,String dosageId,int dosageSeq,int provSeq){
        DosageOrganProvisionInfoEntity entity = new  DosageOrganProvisionInfoEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        entity.setProvisionSeq(provSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setProvisionText(recordSet[1]);
                    break;
                case 2:
                    entity.setProvisionType(recordSet[2]);
                    break;
                case 3:
                    entity.setRecordCreatorType(recordSet[3]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }   

    private UnusedDrugInfoEntity setUnusedDrugInfo(String[] recordSet,String dosageId,int dosageSeq){
    	UnusedDrugInfoEntity entity = new  UnusedDrugInfoEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setUnusedDrugs(recordSet[1]);
                    break;
                case 2:
                    entity.setRecordCreatorType(recordSet[2]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }   

    private DosageRemarkEntity setDosageRemark(String[] recordSet,String dosageId,int dosageSeq,int remarkSeq){
        DosageRemarkEntity entity = new  DosageRemarkEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        entity.setRemarkSeq(remarkSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:        
                    entity.setRemarkText(recordSet[1]);
                    break;
                case 2:
                    entity.setRecordCreatorType(recordSet[2]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private DosagePatientInputEntity setPatientInput(String[] recordSet,String dosageId,int dosageSeq,int patInputSeq) throws ParseException{
        DosagePatientInputEntity entity = new  DosagePatientInputEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(dosageSeq);
        entity.setInputSeq(patInputSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setInputText(recordSet[1]);
                    break;
                case 2:
                    entity.setInputDate(StrToDate(recordSet[2]));
                    break;
            }
        }
        return entity;
    }    

    private PharmaceutistEntity setPharmaceutist(String[] recordSet,String dosageId,int parmaCeutistSeq) throws ParseException{
        PharmaceutistEntity entity = new PharmaceutistEntity();
        entity.setDosageId(dosageId);
        entity.setSeq(parmaCeutistSeq);
        for(int i=0;i<recordSet.length;i++){
            switch(i){
                case 1:
                    entity.setPharmaceutistName(recordSet[1]);
                    break;
                case 2:
                    entity.setPharmacy(recordSet[2]);
                    break;
                case 3:
                    entity.setContactAddress(recordSet[3]);
                    break;
                case 4:
                    entity.setStartDate(StrToDate(recordSet[4]));
                    break;
                case 5:
                    entity.setEndDate(StrToDate(recordSet[5]));
                    break;
                case 6:
                    entity.setRecordCreatorType(recordSet[6]);
                    break;
            }
        }
        if(entity.getRecordCreatorType()==null){
            entity.setRecordCreatorType(RecordCreatorTypeCd.UNKNOWN.getId());
        }
        return entity;
    }
    
    private Date StrToDate(String dateStr) throws ParseException{
        Date returnDate = null;
        Locale loc = new Locale("jp","jp","jp");
        //Calendar cal = Calendar.getInstance(loc);
        DateFormat form = new SimpleDateFormat("yyyyMMdd",loc);
        if(dateStr.length()==8){
            //DateFormat form = new SimpleDateFormat("yyyyMMdd",loc);
            returnDate = form.parse(dateStr);
        }else if(dateStr.length()==7){
            //DateFormat jForm = new SimpleDateFormat("GyyMMdd",loc);
            //returnDate = jForm.parse(dateStr);
            String eraName = dateStr.substring(0,1);
            int jpYear = Integer.parseInt(dateStr.substring(1,3));
            String monthday = dateStr.substring(3);
            int year = 0;
            if(eraName.equals("M")){
               year = 1867 + jpYear; 
            }else if(eraName.equals("T")){
                year = 1911 + jpYear;
            }else if(eraName.equals("S")){
                year = 1925 + jpYear;
            }else if(eraName.equals("H")){
                year = 1988 + jpYear;
            }else if(eraName.equals("R")){
                year = 2018 + jpYear;
            }
            String yearStr = Integer.toString(year);
            String fulldateStr = yearStr + monthday;
            returnDate = form.parse(fulldateStr);
        }
        return returnDate;
    }
    
    private boolean checkhospitalDosageSeq(List<DosageMedicalOrganizationEntity> hospitallist,String dosageId,DosageTypeCd targetType,int dosageSeq){
        for(DosageMedicalOrganizationEntity entity:hospitallist){
            if(entity.getDosageId().equals(dosageId)){
            if(entity.getDosageTypeCd().equals(targetType.getId())){
                if(entity.getSeq()==dosageSeq){
                   return true; 
                }
            }
            }
        }
        return false;
    }    

    private boolean checkDoctorDosageSeq(List<DosageDoctorEntity> doctorlist,String dosageId,DosageTypeCd targetType,int dosageSeq){
        for(DosageDoctorEntity entity:doctorlist){
            if(entity.getDosageId().equals(dosageId)){
            if(entity.getDosageTypeCd().equals(targetType.getId())){
                if(entity.getSeq()==dosageSeq){
                   return true; 
                }
            }
            }
        }
        return false;
    }
    
}
