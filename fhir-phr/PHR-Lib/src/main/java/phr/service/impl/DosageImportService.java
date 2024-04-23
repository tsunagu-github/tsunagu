/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.DosageAdapter;
import phr.datadomain.adapter.DosageAttentionAdapter;
import phr.datadomain.adapter.DosageDoctorAdapter;
import phr.datadomain.adapter.DosageHeadAdapter;
import phr.datadomain.adapter.DosageMedicalOrganizationAdapter;
import phr.datadomain.adapter.DosageMedicineAdapter;
import phr.datadomain.adapter.DosageMedicineAdditionAdapter;
import phr.datadomain.adapter.DosageMedicineAttentionAdapter;
import phr.datadomain.adapter.DosageNoteAdapter;
import phr.datadomain.adapter.DosageOrganProvisionInfoAdapter;
import phr.datadomain.adapter.DosagePatientInputAdapter;
import phr.datadomain.adapter.DosageRecipeAdapter;
import phr.datadomain.adapter.DosageRecipeAdditionAdapter;
import phr.datadomain.adapter.DosageRecipeAttentionAdapter;
import phr.datadomain.adapter.DosageRemarkAdapter;
import phr.datadomain.adapter.NonPrescriptionDrugsAdapter;
import phr.datadomain.adapter.PatientSpecialInstructionAdapter;
import phr.datadomain.adapter.PharmaceutistAdapter;
import phr.datadomain.adapter.SeparatorInfoAdapter;
import phr.datadomain.adapter.UnusedDrugInfoAdapter;
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
import phr.datadomain.entity.NonPrescriptionDrugsEntity;
import phr.datadomain.entity.PatientSpecialInstructionEntity;
import phr.datadomain.entity.PharmaceutistEntity;
import phr.datadomain.entity.SeparatorInfoEntity;
import phr.datadomain.entity.UnusedDrugInfoEntity;
import phr.dto.DosageEntitySetDto;
import phr.service.IDosageImportService;

/**
 *
 * @author kis-note-027_user
 */
public class DosageImportService implements IDosageImportService{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageImportService.class);    
    
    @Override
    public int setDosage(DosageEntitySetDto dosageSet) throws Throwable {
        int result =0;
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");            
            dao = new DataAccessObject();
            
            //dosageHead周りを登録する
            //調剤ヘッダ情報
            //DosageHeadEntity head = dosageSet.getDosageHead();
            List<DosageHeadEntity> headList = dosageSet.getDosageHeadList();
            if(headList!=null){
                DosageHeadAdapter headadapter = new DosageHeadAdapter(dao.getConnection());
                for(DosageHeadEntity head:headList){
                    result = result + headadapter.insert(head);
                }
            }
            //市販薬
            //DosageHeadEntity nonpreHead = dosageSet.getDosageHeadNonpre();
            List<DosageHeadEntity> nonpHeadList = dosageSet.getDosageHeadNonpreList();
            if(nonpHeadList !=null){
                DosageHeadAdapter headadapter = new DosageHeadAdapter(dao.getConnection());
                for(DosageHeadEntity nonpreHead:nonpHeadList){
                    result = result + headadapter.insert(nonpreHead);
                }
            }            

            //患者特記
            int psiSize = dosageSet.getPatientSpecialInstructionList().size();
            if(psiSize>0){
                PatientSpecialInstructionAdapter pSIAadapter = new PatientSpecialInstructionAdapter(dao.getConnection());                
                for(int i=0;i<psiSize;i++){
                    PatientSpecialInstructionEntity psiEnt = dosageSet.getPatientSpecialInstruction(i);
                    result = result + pSIAadapter.insert(psiEnt);   
                }                
            }
            //手帳メモ
            int noteSize = dosageSet.getDosageNoteList().size();
            if(noteSize>0){
                DosageNoteAdapter memoadapter = new DosageNoteAdapter(dao.getConnection());
                for(int i=0;i<noteSize;i++){
                    DosageNoteEntity noteEnt = dosageSet.getDosageNote(i);
                    result = result + memoadapter.insert(noteEnt);
                }                
            }
            //かかりつけ薬剤師情報
            int phaSize = dosageSet.getPharmaceutistList().size();
            if(phaSize>0){
                PharmaceutistAdapter pharmacistadapter = new PharmaceutistAdapter(dao.getConnection());
                for(int i=0;i<phaSize;i++){
                    PharmaceutistEntity phaEnt = dosageSet.getPharmaceutist(i);
                    result = result + pharmacistadapter.insert(phaEnt);                
                }
            }
            


            //調剤情報
            int dosageSize = dosageSet.getDosageList().size();
            if(dosageSize>0){
                DosageAdapter dosageadapter = new DosageAdapter(dao.getConnection());
                for(int i=0;i<dosageSize;i++){
                    DosageEntity dosage = dosageSet.getDosage(i);
                    int dosageResult =dosageadapter.insert(dosage);
                    if(dosageResult==0){
                        //Dosageが登録されない理由-分割データであり登録済み
                        
                        //Dosageが登録されない理由-登録エラー
                        
                    }else{
                        result = result + dosageResult;
                    }
                }
            }
       
            //患者等記入情報
            int dpiSize = dosageSet.getPatientInputList().size();
            if(dpiSize>0){
                DosagePatientInputAdapter patInfoadapter = new DosagePatientInputAdapter(dao.getConnection());
                for(int i=0;i<dpiSize;i++){
                    DosagePatientInputEntity dpiEnt = dosageSet.getPatientInput(i);
                    result = result + patInfoadapter.insert(dpiEnt);                
                }
            }

            //備考情報
            int rmkSize = dosageSet.getDosageRemarkList().size();
            if(rmkSize>0){
                DosageRemarkAdapter remarkadapter = new DosageRemarkAdapter(dao.getConnection());
                for(int i=0;i<rmkSize;i++){
                    DosageRemarkEntity remark = dosageSet.getDosageRemark(i);
                    result = result + remarkadapter.insert(remark);                
                }
            }
        
        //医療機関等提供情報
            int opiSize= dosageSet.getOrganProvisionInfoList().size();
            if(opiSize>0){
                DosageOrganProvisionInfoAdapter dOPIadapter = new DosageOrganProvisionInfoAdapter(dao.getConnection());
                for(int i=0;i<opiSize;i++){
                    DosageOrganProvisionInfoEntity opiEnt = dosageSet.getOrganProvisionInfo(i);
                    result = result + dOPIadapter.insert(opiEnt);    
                }
            }
        //服用注意情報
            int daSize = dosageSet.getDosageAttentionList().size();
            if(daSize>0){
                DosageAttentionAdapter dAttentadapter = new DosageAttentionAdapter(dao.getConnection());
                for(int i=0;i<daSize;i++){
                    DosageAttentionEntity daEnt = dosageSet.getDosageAttention(i);
                    result = result + dAttentadapter.insert(daEnt);
                }
            }
        //医療機関情報
            int hospitalSize = dosageSet.getMedicalOrganizationList().size();
            if(hospitalSize>0){
                DosageMedicalOrganizationAdapter dMOadapter = new DosageMedicalOrganizationAdapter(dao.getConnection());
               for(int i=0;i<hospitalSize;i++){
                    DosageMedicalOrganizationEntity hospital = dosageSet.getMedicalOrganization(i);
                    result = result + dMOadapter.insert(hospital);
               } 
            }
        //医師薬剤師情報
            int doctorSize = dosageSet.getDosageDoctorList().size();
            if(doctorSize>0){
                DosageDoctorAdapter doctoradapter = new DosageDoctorAdapter(dao.getConnection());
                for(int i=0;i<doctorSize;i++){
                    DosageDoctorEntity doctor = dosageSet.getDosageDoctor(i);
                    result = result + doctoradapter.insert(doctor);
                }
            }

        //一般医薬品
            int nonpreSize = dosageSet.getNonPrescriptionDrugsList().size();
            if(nonpreSize>0){
                NonPrescriptionDrugsAdapter nonPreadapter = new NonPrescriptionDrugsAdapter(dao.getConnection());
                for(int i=0;i<nonpreSize;i++){
                    NonPrescriptionDrugsEntity nonpreEnt = dosageSet.getNonPrescriptionDrugs(i);
                    result = result + nonPreadapter.insert(nonpreEnt);
                }
            }

        //レシピ情報
            int recipeSize =dosageSet.getDosageRecipeList().size();
            if(recipeSize>0){
                DosageRecipeAdapter recipeadapter = new DosageRecipeAdapter(dao.getConnection());
                for(int i=0;i<recipeSize;i++){
                    DosageRecipeEntity recEnt = dosageSet.getDosageRecipe(i);
                    result = result + recipeadapter.insert(recEnt);
                }
            }
            //処方服用注意情報
            int draSize = dosageSet.getRecipeAttentionList().size();
            if(draSize>0){
                DosageRecipeAttentionAdapter rAttentadapter = new DosageRecipeAttentionAdapter(dao.getConnection());
                for(int i=0;i<draSize;i++){
                    DosageRecipeAttentionEntity draEnt = dosageSet.getRecipeAttention(i);
                    result = result + rAttentadapter.insert(draEnt);
                }
            }
            //用法補足情報
            int recAddSize = dosageSet.getDosageRecipeAddList().size();
            if(recAddSize>0){
                DosageRecipeAdditionAdapter recAddadapter = new DosageRecipeAdditionAdapter(dao.getConnection());
                for(int i=0;i<recAddSize;i++){
                    DosageRecipeAdditionEntity recAddEnt = dosageSet.getDosageRecipeAdd(i);
                    result = result + recAddadapter.insert(recAddEnt);
                }
            }
            
            
            //薬剤情報
            int medicSize = dosageSet.getDosageMedicineList().size();
            if(medicSize>0){
                DosageMedicineAdapter medicineadapter = new DosageMedicineAdapter(dao.getConnection());
                for(int i=0;i<medicSize;i++){
                    DosageMedicineEntity medEnt = dosageSet.getDosageMedicine(i);
                    result = result + medicineadapter.insert(medEnt);
                }
            }
            
            //薬剤補足情報
            int medAddSize = dosageSet.getMedicineAdditionList().size();
            if(medAddSize>0){
                DosageMedicineAdditionAdapter mAddadapter = new DosageMedicineAdditionAdapter(dao.getConnection());
                for(int i=0;i<medAddSize;i++){
                    DosageMedicineAdditionEntity adEnt = dosageSet.getMedicineAddition(i);
                    result = result + mAddadapter.insert(adEnt);
                }
            }
            
            //薬品服用注意情報
            int mAttSize = dosageSet.getMedicineAttentionList().size();
            if(mAttSize>0){
                DosageMedicineAttentionAdapter mAttentadapter = new DosageMedicineAttentionAdapter(dao.getConnection());
                for(int i=0;i<mAttSize;i++){
                    DosageMedicineAttentionEntity dmaEnt = dosageSet.getMedicineAttention(i);
                    result = result + mAttentadapter.insert(dmaEnt);
                }
            }
            
            //セパレータ情報
            int sepInfoSize = dosageSet.getSeparatorInfoList().size();
            if(sepInfoSize>0){
                SeparatorInfoAdapter sepInfoAdapter = new SeparatorInfoAdapter(dao.getConnection());
                for(int i=0;i<sepInfoSize;i++){
                    SeparatorInfoEntity sepInfo = dosageSet.getSeparatorInfo(i);
                    result = result + sepInfoAdapter.insert(sepInfo);
                }
            }
//            SeparatorInfoEntity sepInfo = dosageSet.getSeparatorInfo();
//            if(sepInfo!=null){
//                SeparatorInfoAdapter sepInfoAdapter = new SeparatorInfoAdapter(dao.getConnection());
//                result = result + sepInfoAdapter.insert(sepInfo);
//            }
            
            //残薬確認情報
            List<UnusedDrugInfoEntity> unusedList = dosageSet.getUnusedDrugInfoList();
            if(unusedList!=null){
            	UnusedDrugInfoAdapter unusedadapter = new UnusedDrugInfoAdapter(dao.getConnection());
                for(UnusedDrugInfoEntity unused:unusedList){
                    result = result + unusedadapter.insert(unused);
                }
            }
        
            dao.commitTransaction();
            
            logger.debug("End");
            return result;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }        

    }

    @Override
    public String getNewDosageId() throws Throwable {
        String dosageId = DosageHeadAdapter.numberingDosageId();
        return dosageId;
    }
    
    @Override
    public List<SeparatorInfoEntity> getSeparatorInfo(Long separatorId) throws Throwable{
        DataAccessObject dao = null;
        try {
            logger.debug("Start");            
            dao = new DataAccessObject();        
            SeparatorInfoAdapter sepInfoAdapter = new SeparatorInfoAdapter(dao.getConnection());

            List<SeparatorInfoEntity> separatorList = sepInfoAdapter.findBySeparatorId(separatorId);
            return separatorList;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }        

    }

    @Override
    public int deleteSeparatorInfo(Long separatorId, String dosageId) throws Throwable {
        int result = 0;
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");            
            dao = new DataAccessObject();
            
            //調剤情報
            DosageAdapter dosageadapter = new DosageAdapter(dao.getConnection());
            result = result + dosageadapter.deleteByDosageId(dosageId,0);
            
            //セパレータ情報
            SeparatorInfoAdapter sepInfoAdapter = new SeparatorInfoAdapter(dao.getConnection());
            result = result + sepInfoAdapter.deleteByDosageId(dosageId);
            dao.commitTransaction();
            
            logger.debug("End");
            return result;
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
  
}
