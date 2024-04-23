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
import phr.datadomain.adapter.DosagePatientInputAdapter;
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
public class DosageUpdateService implements IDosageUpdateService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageUpdateService.class);
    

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
            result = result + headadapter.update(head);

            //患者特記
//                PatientSpecialInstructionAdapter pSIAadapter = new PatientSpecialInstructionAdapter(dao.getConnection());
//                result = result + pSIAadapter.update(entity);
            //手帳メモ
//                DosageNoteAdapter memoadapter = new DosageNoteAdapter(dao.getConnection());
//                result = result + memoadapter.update(entity);
            //かかりつけ薬剤師情報
//                PharmaceutistAdapter pharmacistadapter = new PharmaceutistAdapter(dao.getConnection());
//                result = result + pharmacistadapter.update(entity);

            //調剤情報
            DosageAdapter dosageadapter = new DosageAdapter(dao.getConnection());
            result = result + dosageadapter.update(dosage);
       


            
        //患者等記入情報
//            DosagePatientInputAdapter patInfoadapter = new DosagePatientInputAdapter(dao.getConnection());
//            result = result + patInfoadapter.update(entity);
        //備考情報
            DosageRemarkAdapter remarkadapter = new DosageRemarkAdapter(dao.getConnection());
            if(remark==null){
                DosageRemarkEntity oldRemark = remarkadapter.findByPrimaryKey(dosage.getDosageId(), 1, 1);
                if(oldRemark!=null){
                    result = result + remarkadapter.delete(oldRemark);
                }
            }else{
                int remarkresult = remarkadapter.update(remark);
                if(remarkresult==0){
                    remarkresult = remarkadapter.insert(remark);
                }
                result = result + remarkresult;
            }
        //医療機関等提供情報
//            DosageOrganProvisionInfoAdapter dOPIadapter = new DosageOrganProvisionInfoAdapter(dao.getConnection());
//            result = result + dOPIadapter.update(entity);
        //服用注意情報
//            DosageAttentionAdapter dAttentadapter = new DosageAttentionAdapter(dao.getConnection());
//            result = result + dAttentadapter.update(entity);
        //医療機関情報
            
                DosageMedicalOrganizationAdapter dMOadapter = new DosageMedicalOrganizationAdapter(dao.getConnection());               
            if(hospital==null){
                DosageMedicalOrganizationEntity hospitalOld = dMOadapter.findByPrimaryKey(dosage.getDosageId(), 1, "2");
                if(hospitalOld!=null){
                    result = result + dMOadapter.delete(hospitalOld);
                }
            }else{
                int hresult = dMOadapter.update(hospital);
                if(hresult == 0){
                    hresult = dMOadapter.insert(hospital);
                }
                result = result + hresult;
                
            }
            if(pharmacy==null){
                DosageMedicalOrganizationEntity pharmacyOld = dMOadapter.findByPrimaryKey(dosage.getDosageId(), 1, "1");
                if(pharmacyOld!=null){
                    result = result + dMOadapter.delete(pharmacyOld);
                }
            }else{
                int presult = dMOadapter.update(pharmacy);
                if(presult == 0){
                    presult = dMOadapter.insert(pharmacy);
                }
                result = result + presult;
            }
        //医師薬剤師情報
            DosageDoctorAdapter doctoradapter = new DosageDoctorAdapter(dao.getConnection());
            if(doctor==null){
                DosageDoctorEntity doctorOld = doctoradapter.findByPrimaryKey(dosage.getDosageId(), 1, "2");
                if(doctorOld!=null){
                    result = result + doctoradapter.delete(doctorOld);
                }
            }else{
                int docresult = doctoradapter.update(doctor);
                if(docresult == 0){
                    docresult = doctoradapter.insert(doctor);
                }
                result = result + docresult;
            }
            if(pharmacist==null){
                DosageDoctorEntity pharmacistOld = doctoradapter.findByPrimaryKey(dosage.getDosageId(), 1, "1");
                if(pharmacistOld!=null){
                    result = result + doctoradapter.delete(pharmacistOld);
                }
            }else{
                int phresult = doctoradapter.update(pharmacist);
                if(phresult == 0){
                    phresult = doctoradapter.insert(pharmacist);
                }
                result = result + phresult;
            }
        //一般医薬品
            if(nonpreList!=null){
                NonPrescriptionDrugsAdapter nonPreadapter = new NonPrescriptionDrugsAdapter(dao.getConnection());
                for(NonPrescriptionDrugsEntity nonpreEnt:nonpreList){
                    if(nonpreEnt.isIsDelete()){
                        result = result + nonPreadapter.delete(nonpreEnt);
                    }else{
                        int nresult = nonPreadapter.update(nonpreEnt);
                        if(nresult == 0){
                            nresult = nonPreadapter.insert(nonpreEnt);
                        }
                        result = result + nresult;
                    }
                }
            }    
       
            if(medicineList!=null){
                DosageMedicineAdapter medicineadapter = new DosageMedicineAdapter(dao.getConnection());
            //薬剤情報
                for(DosageMedicineEntity medEnt:medicineList){
                    if(medEnt.isIsDelete()){
                        result = result + medicineadapter.delete(medEnt);
                    }else{
                        int mresult = medicineadapter.update(medEnt);
                        if(mresult==0){
                            mresult = medicineadapter.insert(medEnt);
                        }
                        result = result + mresult;
                    }
                }
            //薬剤補足情報
                if(additionList!=null){
                    DosageMedicineAdditionAdapter mAddadapter = new DosageMedicineAdditionAdapter(dao.getConnection());
                    for(DosageMedicineAdditionEntity adEnt:additionList){ 
                        int aresult = mAddadapter.update(adEnt);
                        if(aresult==0){
                            aresult = mAddadapter.insert(adEnt);
                        }
                        result = result + aresult;
                    }
                }
            //薬品服用注意情報
    //            DosageMedicineAttentionAdapter mAttentadapter = new DosageMedicineAttentionAdapter(dao.getConnection());
    //            result = result + mAttentadapter.update(entity);
    
        //レシピ情報
                DosageRecipeAdapter recipeadapter = new DosageRecipeAdapter(dao.getConnection());
                if(recipelist!=null){
                    for(DosageRecipeEntity recEnt:recipelist){
                        int recresult = recipeadapter.update(recEnt);
                        if(recresult==0){
                            recresult = recipeadapter.insert(recEnt);
                        }else{
                            int medicineCount = medicineadapter.getCountByRecipeNo(recEnt.getDosageId(),recEnt.getSeq(),recEnt.getRecipeNo());
                            if(medicineCount == 0){
                                recresult = recipeadapter.delete(recEnt);
                            }
                        }

                        result = result + recresult;
                    }

                }
            //処方服用注意情報
    //            DosageRecipeAttentionAdapter rAttentadapter = new DosageRecipeAttentionAdapter(dao.getConnection());
    //            result = result + rAttentadapter.update(entity);
            //用法補足情報
    //            DosageRecipeAdditionAdapter recAddadapter = new DosageRecipeAdditionAdapter(dao.getConnection());
    //            result = result + recAddadapter.update(entity);
            
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
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public int updatePatientInput(List<DosagePatientInputEntity> patInputList) throws Throwable {
        int result =0;
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");            
            dao = new DataAccessObject();
            DosagePatientInputAdapter patInfoadapter = new DosagePatientInputAdapter(dao.getConnection());
            for(DosagePatientInputEntity entity:patInputList){
            //削除
                result = patInfoadapter.delete(entity);
            //登録
                if(entity.getInputText()==null || entity.getInputText().isEmpty()){
                    
                }else{
                    result = result + patInfoadapter.insert(entity);
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
     
}
