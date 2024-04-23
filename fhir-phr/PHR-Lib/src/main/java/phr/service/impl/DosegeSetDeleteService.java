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
import phr.service.IDosegeSetDeleteService;

/**
 *
 * @author kis-note-027_user
 */
public class DosegeSetDeleteService implements IDosegeSetDeleteService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosegeSetDeleteService.class);

    @Override
    public int deleteByDosageId(String dosageId) throws Throwable {
        int result = 0;
        DataAccessObject dao = null;
        
        try {
            logger.debug("Start");            
            dao = new DataAccessObject();
            
         //dosageIdとseqでDosageテーブル周りを削除する
        //患者等記入情報
            DosagePatientInputAdapter patInfoadapter = new DosagePatientInputAdapter(dao.getConnection());
//            result = result + patInfoadapter.deleteByDosageId(dosageId,seq);
            result = result + patInfoadapter.deleteByDosageId(dosageId);
        //備考情報
            DosageRemarkAdapter remarkadapter = new DosageRemarkAdapter(dao.getConnection());
//            result = result + remarkadapter.deleteByDosageId(dosageId,seq);
            result = result + remarkadapter.deleteByDosageId(dosageId);
        //医療機関等提供情報
            DosageOrganProvisionInfoAdapter dOPIadapter = new DosageOrganProvisionInfoAdapter(dao.getConnection());
//            result = result + dOPIadapter.deleteByDosageId(dosageId,seq);
            result = result + dOPIadapter.deleteByDosageId(dosageId);
        //服用注意情報
            DosageAttentionAdapter dAttentadapter = new DosageAttentionAdapter(dao.getConnection());
//            result = result + dAttentadapter.deleteByDosageId(dosageId,seq);
            result = result + dAttentadapter.deleteByDosageId(dosageId);

        //医師薬剤師情報
            DosageDoctorAdapter doctoradapter = new DosageDoctorAdapter(dao.getConnection());
//            result = result + doctoradapter.deleteByDosageId(dosageId,seq);
            result = result + doctoradapter.deleteByDosageId(dosageId);
        //医療機関情報
            DosageMedicalOrganizationAdapter dMOadapter = new DosageMedicalOrganizationAdapter(dao.getConnection());
//            result = result + dMOadapter.deleteByDosageId(dosageId,seq);
            result = result + dMOadapter.deleteByDosageId(dosageId);

       //薬品服用注意情報
            DosageMedicineAttentionAdapter mAttentadapter = new DosageMedicineAttentionAdapter(dao.getConnection());
//            result = result + mAttentadapter.deleteByDosageId(dosageId,seq);
            result = result + mAttentadapter.deleteByDosageId(dosageId);
        //薬剤補足情報
            DosageMedicineAdditionAdapter mAddadapter = new DosageMedicineAdditionAdapter(dao.getConnection());
//            result = result + mAddadapter.deleteByDosageId(dosageId,seq);
            result = result + mAddadapter.deleteByDosageId(dosageId);

        //薬剤情報
            DosageMedicineAdapter medicineadapter = new DosageMedicineAdapter(dao.getConnection());
//            result = result + medicineadapter.deleteByDosageId(dosageId,seq);
            result = result + medicineadapter.deleteByDosageId(dosageId);
 
        //処方服用注意情報
            DosageRecipeAttentionAdapter rAttentadapter = new DosageRecipeAttentionAdapter(dao.getConnection());
//            result = result + rAttentadapter.deleteByDosageId(dosageId,seq);
            result = result + rAttentadapter.deleteByDosageId(dosageId);

        //用法補足情報
           DosageRecipeAdditionAdapter recAddadapter = new DosageRecipeAdditionAdapter(dao.getConnection());
//           result = result + recAddadapter.deleteByDosageId(dosageId, seq);
           result = result + recAddadapter.deleteByDosageId(dosageId);
           
         //残薬確認情報
           UnusedDrugInfoAdapter unusedAdapter = new UnusedDrugInfoAdapter(dao.getConnection());
           result = result + unusedAdapter.deleteByDosageId(dosageId);
           
        //レシピ情報
            DosageRecipeAdapter recipeadapter = new DosageRecipeAdapter(dao.getConnection());
//            result = result + recipeadapter.deleteByDosageId(dosageId,seq);
            result = result + recipeadapter.deleteByDosageId(dosageId);
            
            //調剤情報
            DosageAdapter dosageadapter = new DosageAdapter(dao.getConnection());
//            result = result + dosageadapter.deleteByDosageId(dosageId,seq);
            result = result + dosageadapter.deleteByDosageId(dosageId);
            
            //市販薬を削除する
        //一般医薬品
            NonPrescriptionDrugsAdapter nonPreadapter = new NonPrescriptionDrugsAdapter(dao.getConnection());
            result = result + nonPreadapter.deleteByDosageId(dosageId);
            
            //同じDosageIdの調剤が何件あるか取得する
            int dosageCount =dosageadapter.getDosageIdCount(dosageId);
            
            //1件もなければ、dosageHead周りを削除する
            if(dosageCount==0){

            //患者特記
                PatientSpecialInstructionAdapter pSIAadapter = new PatientSpecialInstructionAdapter(dao.getConnection());
                result = result + pSIAadapter.deleteByDosageId(dosageId);
            //手帳メモ
                DosageNoteAdapter memoadapter = new DosageNoteAdapter(dao.getConnection());
                result = result + memoadapter.deleteByDosageId(dosageId);
            //かかりつけ薬剤師情報
                PharmaceutistAdapter pharmacistadapter = new PharmaceutistAdapter(dao.getConnection());
                result = result + pharmacistadapter.deleteByDosageId(dosageId);

            //調剤ヘッダ情報
                DosageHeadAdapter headadapter = new DosageHeadAdapter(dao.getConnection());
                result = result + headadapter.deletebyDosageId(dosageId);
             
            //セパレータ情報（あれば）
                SeparatorInfoAdapter sepInfoAdapter = new SeparatorInfoAdapter(dao.getConnection());
                result = result + sepInfoAdapter.deleteByDosageId(dosageId);

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
