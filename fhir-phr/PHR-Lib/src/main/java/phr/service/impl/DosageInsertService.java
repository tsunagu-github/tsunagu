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
import phr.datadomain.adapter.DosageDoctorAdapter;
import phr.datadomain.adapter.DosageHeadAdapter;
import phr.datadomain.adapter.DosageMedicalOrganizationAdapter;
import phr.datadomain.adapter.DosageMedicineAdapter;
import phr.datadomain.adapter.DosageMedicineAdditionAdapter;
import phr.datadomain.adapter.DosageRecipeAdapter;
import phr.datadomain.adapter.DosageRemarkAdapter;
import phr.datadomain.adapter.NonPrescriptionDrugsAdapter;
import phr.datadomain.entity.DosageDoctorEntity;
import phr.datadomain.entity.DosageEntity;
import phr.datadomain.entity.DosageHeadEntity;
import phr.datadomain.entity.DosageMedicalOrganizationEntity;
import phr.datadomain.entity.DosageMedicineAdditionEntity;
import phr.datadomain.entity.DosageMedicineEntity;
import phr.datadomain.entity.DosagePatientInputEntity;
import phr.datadomain.entity.DosageRecipeEntity;
import phr.datadomain.entity.DosageRemarkEntity;
import phr.datadomain.entity.NonPrescriptionDrugsEntity;
import phr.service.IDosageUpdateService;

/**
 *
 * @author kis-note-027_user
 */
public class DosageInsertService implements IDosageUpdateService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageInsertService.class);

    @Override
    public int setDosage(DosageHeadEntity head, DosageEntity dosage, DosageMedicalOrganizationEntity pharmacy, DosageDoctorEntity pharmacist, DosageMedicalOrganizationEntity hospital, DosageDoctorEntity doctor, DosageRemarkEntity remark, List<DosageRecipeEntity> recipelist, List<DosageMedicineEntity> medicineList, List<DosageMedicineAdditionEntity> additionList, List<NonPrescriptionDrugsEntity> nonpreList) throws Throwable {
        int result =0;
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");            
            dao = new DataAccessObject();
            
            //dosageHead周りを登録する
            //調剤ヘッダ情報
            DosageHeadAdapter headadapter = new DosageHeadAdapter(dao.getConnection());
            result = result + headadapter.insert(head);

            //患者特記
//                PatientSpecialInstructionAdapter pSIAadapter = new PatientSpecialInstructionAdapter(dao.getConnection());
//                result = result + pSIAadapter.insert(entity);
            //手帳メモ
//                DosageNoteAdapter memoadapter = new DosageNoteAdapter(dao.getConnection());
//                result = result + memoadapter.insert(entity);
            //かかりつけ薬剤師情報
//                PharmaceutistAdapter pharmacistadapter = new PharmaceutistAdapter(dao.getConnection());
//                result = result + pharmacistadapter.insert(entity);

            //調剤情報
            DosageAdapter dosageadapter = new DosageAdapter(dao.getConnection());
            result = result + dosageadapter.insert(dosage);
       


            
        //患者等記入情報
//            DosagePatientInputAdapter patInfoadapter = new DosagePatientInputAdapter(dao.getConnection());
//            result = result + patInfoadapter.insert(entity);
        //備考情報
            if(remark!=null){
                DosageRemarkAdapter remarkadapter = new DosageRemarkAdapter(dao.getConnection());
                result = result + remarkadapter.insert(remark);
            }
        //医療機関等提供情報
//            DosageOrganProvisionInfoAdapter dOPIadapter = new DosageOrganProvisionInfoAdapter(dao.getConnection());
//            result = result + dOPIadapter.insert(entity);
        //服用注意情報
//            DosageAttentionAdapter dAttentadapter = new DosageAttentionAdapter(dao.getConnection());
//            result = result + dAttentadapter.insert(entity);
        //医療機関情報
            
                DosageMedicalOrganizationAdapter dMOadapter = new DosageMedicalOrganizationAdapter(dao.getConnection());
            if(hospital!=null){
                result = result + dMOadapter.insert(hospital);
            }
            if(pharmacy!=null){
                result = result + dMOadapter.insert(pharmacy);
            }
        //医師薬剤師情報
            DosageDoctorAdapter doctoradapter = new DosageDoctorAdapter(dao.getConnection());
            if(doctor!=null){
                result = result + doctoradapter.insert(doctor);
            }
            if(pharmacist!=null){
                result = result + doctoradapter.insert(pharmacist);
            }
        //一般医薬品
            if(nonpreList!=null){
                NonPrescriptionDrugsAdapter nonPreadapter = new NonPrescriptionDrugsAdapter(dao.getConnection());
                for(NonPrescriptionDrugsEntity nonpreEnt:nonpreList){
                    result = result + nonPreadapter.insert(nonpreEnt);
                }
            }    

        //レシピ情報
            if(recipelist!=null){
                for(DosageRecipeEntity recEnt:recipelist){
                    DosageRecipeAdapter recipeadapter = new DosageRecipeAdapter(dao.getConnection());
                    result = result + recipeadapter.insert(recEnt);                
                }
            } 
            //処方服用注意情報
    //            DosageRecipeAttentionAdapter rAttentadapter = new DosageRecipeAttentionAdapter(dao.getConnection());
    //            result = result + rAttentadapter.insert(entity);
            //???用法補足情報        
            
        
            if(medicineList!=null){
            //薬剤情報
                for(DosageMedicineEntity medEnt:medicineList){
                    DosageMedicineAdapter medicineadapter = new DosageMedicineAdapter(dao.getConnection());
                    result = result + medicineadapter.insert(medEnt);
                }
            //薬剤補足情報
                if(additionList!=null){
                    for(DosageMedicineAdditionEntity adEnt:additionList){
                        DosageMedicineAdditionAdapter mAddadapter = new DosageMedicineAdditionAdapter(dao.getConnection());
                        result = result + mAddadapter.insert(adEnt);
                    }
                }
            }
            //薬品服用注意情報
    //            DosageMedicineAttentionAdapter mAttentadapter = new DosageMedicineAttentionAdapter(dao.getConnection());
    //            result = result + mAttentadapter.insert(entity);
        
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
    
    public String getNewDosageId()throws Throwable{
        String dosageId = DosageHeadAdapter.numberingDosageId();
        return dosageId;
    }

    @Override
    public int updatePatientInput(List<DosagePatientInputEntity> patInputList) throws Throwable {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
