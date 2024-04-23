/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import phr.dto.DosageEntitySetDto;
import phr.service.IDosageExportService;

/**
 *
 * @author kis-note-027_user
 */
public class DosageExportService implements IDosageExportService{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageExportService.class);    

    @Override
    public List<DosageEntitySetDto> getDosageSetList(String phrId) throws Throwable {
        DataAccessObject dao = null;
        List<DosageEntitySetDto> dosageSetList = new ArrayList();
        
        try {
            logger.debug("Start");            
            dao = new DataAccessObject();
            
            
            //PHRIDから対象DosageIdリストを取得する。
            DosageAdapter dosageadapter = new DosageAdapter(dao.getConnection());
            List<String> list = dosageadapter.getDosageIdList(phrId);
            Set<String> set = new HashSet(list);
            List<String> idList = new ArrayList(set);
            for(String dosageId:idList){
                DosageEntitySetDto entity = new DosageEntitySetDto();
                
                //調剤ヘッダ情報
                DosageHeadAdapter headadapter = new DosageHeadAdapter(dao.getConnection());                
                DosageHeadEntity head = headadapter.findByPrimaryKey(dosageId);
                if(head!=null){
                    entity.setDosageHead(head);
                }

                //患者特記
                PatientSpecialInstructionAdapter psiAdapter = new PatientSpecialInstructionAdapter(dao.getConnection());
                List<PatientSpecialInstructionEntity> psiList = psiAdapter.findByDosageId(dosageId);
                entity.setPatientSpecialInstructionList(psiList);
               
                //手帳メモ
                DosageNoteAdapter memoadapter = new DosageNoteAdapter(dao.getConnection());
                List<DosageNoteEntity> noteList = memoadapter.findByDosageId(dosageId);
                entity.setDosageNoteList(noteList);
                
                //かかりつけ薬剤師情報
                PharmaceutistAdapter pharmacistadapter = new PharmaceutistAdapter(dao.getConnection());
                List<PharmaceutistEntity> phaList = pharmacistadapter.findByDosageId(dosageId);
                entity.setPharmaceutistList(phaList);

                //調剤情報
                List<DosageEntity> dosageList = dosageadapter.findByDosageId(dosageId);
                entity.setDosageList(dosageList);

                //患者等記入情報
                DosagePatientInputAdapter patInfoadapter = new DosagePatientInputAdapter(dao.getConnection());
                List<DosagePatientInputEntity> dpiList = patInfoadapter.findByDosageId(dosageId);
                entity.setPatientInputList(dpiList);
 
                //備考情報
                DosageRemarkAdapter remarkadapter = new DosageRemarkAdapter(dao.getConnection());
                List<DosageRemarkEntity> remark = remarkadapter.findByDosageID(dosageId);
                entity.setDosageRemarkList(remark);

            //医療機関等提供情報
                DosageOrganProvisionInfoAdapter dOPIadapter = new DosageOrganProvisionInfoAdapter(dao.getConnection());
                List<DosageOrganProvisionInfoEntity> opiList = dOPIadapter.findByDosageId(dosageId);
                entity.setOrganProvisionInfoList(opiList);
                
            //服用注意情報
                DosageAttentionAdapter dAttentadapter = new DosageAttentionAdapter(dao.getConnection());
                List<DosageAttentionEntity> dalist = dAttentadapter.findByDosageId(dosageId);
                entity.setDosageAttentionList(dalist);
                    
            //医療機関情報
                DosageMedicalOrganizationAdapter dMOadapter = new DosageMedicalOrganizationAdapter(dao.getConnection());
                List<DosageMedicalOrganizationEntity> hospitallist = dMOadapter.findByDosageId(dosageId);
                entity.setMedicalOrganizationList(hospitallist);
                    
            //医師薬剤師情報
                DosageDoctorAdapter doctoradapter = new DosageDoctorAdapter(dao.getConnection());
                List<DosageDoctorEntity> doctorlist = doctoradapter.findByDosageId(dosageId);
                entity.setDosageDoctorList(doctorlist);

            //一般医薬品
                NonPrescriptionDrugsAdapter nonPreadapter = new NonPrescriptionDrugsAdapter(dao.getConnection());
                List<NonPrescriptionDrugsEntity> nonpreList = nonPreadapter.findByDosageid(dosageId);
                entity.setNonPrescriptionDrugsList(nonpreList);

            //レシピ情報
                DosageRecipeAdapter recipeadapter = new DosageRecipeAdapter(dao.getConnection());
                List<DosageRecipeEntity> recList = recipeadapter.findByDosageidOnly(dosageId);
                entity.setDosageRecipeList(recList);
                
            //処方服用注意情報
                DosageRecipeAttentionAdapter rAttentadapter = new DosageRecipeAttentionAdapter(dao.getConnection());
                List<DosageRecipeAttentionEntity> draEntList = rAttentadapter.findByDosageId(dosageId);
                entity.setRecipeAttentionList(draEntList);

            //用法補足情報
                DosageRecipeAdditionAdapter recAddadapter = new DosageRecipeAdditionAdapter(dao.getConnection());
                List<DosageRecipeAdditionEntity> recipeAddList = recAddadapter.findByDosageId(dosageId);
                entity.setDosageRecipeAddList(recipeAddList);

            //薬剤情報
                DosageMedicineAdapter medicineadapter = new DosageMedicineAdapter(dao.getConnection());
                List<DosageMedicineEntity> medicineList = medicineadapter.findByDosageIdOnly(dosageId);
                entity.setDosageMedicineList(medicineList);

            //薬剤補足情報
                DosageMedicineAdditionAdapter mAddadapter = new DosageMedicineAdditionAdapter(dao.getConnection());
                List<DosageMedicineAdditionEntity> maddList = mAddadapter.findByDosageId(dosageId);
                entity.setMedicineAdditionList(maddList);

            //薬品服用注意情報
                DosageMedicineAttentionAdapter mAttentadapter = new DosageMedicineAttentionAdapter(dao.getConnection());
                List<DosageMedicineAttentionEntity> mAtttList = mAttentadapter.findByDosageId(dosageId);
                entity.setMedicineAttentionList(mAtttList);
                
                dosageSetList.add(entity);
            }  
            logger.debug("End");
            return dosageSetList;
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
