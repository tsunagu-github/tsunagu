/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：調剤情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 調剤情報のデータオブジェクトです。
 */
public class DosageEntity extends DosageEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

    //<editor-fold defaultstate="collapsed" desc="直近検索時の他テーブルの追加必要情報">
    // 項目ID
    public String pharmacy;
    public String getPharmacy(){
        return this.pharmacy;
    }
    public void setPharmacy(String pharmacy){
        this.pharmacy=pharmacy;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="おくすり詳細リスト取得時の他テーブル情報">
    // かかりつけ薬剤師
    public PharmaceutistEntity pharmaceutist;
    public PharmaceutistEntity getPharmaceutistEntity(){
        return this.pharmaceutist;
    }
    public void setPharmaceutistEntity(PharmaceutistEntity pharmaceutist){
        this.pharmaceutist=pharmaceutist;
    }
    
    //医療機関情報
    public DosageMedicalOrganizationEntity dosageMedicalOrganization;
    public DosageMedicalOrganizationEntity getDosageMedicalOrganizationEntity(){
        return this.dosageMedicalOrganization;
    }
    public void setDosageMedicalOrganizationEntity(DosageMedicalOrganizationEntity dosageMedicalOrganization){
        this.dosageMedicalOrganization=dosageMedicalOrganization;
    }
    
    //医師薬剤師情報
    public DosageDoctorEntity dosageDoctor;
    public DosageDoctorEntity getDosageDoctorEntity(){
        return this.dosageDoctor;
    }
    public void setDosageDoctorEntity(DosageDoctorEntity dosageDoctor){
        this.dosageDoctor=dosageDoctor;
    }

    // レシピ情報（用法）
    public DosageRecipeEntity dosageRecipe;
    public DosageRecipeEntity getDosageRecipeEntity(){
        return this.dosageRecipe;
    }
    public void setDosageRecipeEntity(DosageRecipeEntity dosageRecipe){
        this.dosageRecipe=dosageRecipe;
    }

    // 薬剤情報
    public DosageMedicineEntity dosegemedicine;
    public DosageMedicineEntity getDosageMedicineEntity(){
        return this.dosegemedicine;
    }
    public void setDosageMedicineEntity(DosageMedicineEntity dosegemedicine){
        this.dosegemedicine=dosegemedicine;
    }

    // 一般薬情報
    public NonPrescriptionDrugsEntity nonPrescriptionDrugs;
    public NonPrescriptionDrugsEntity getNonPrescriptionDrugsEntity(){
        return this.nonPrescriptionDrugs;
    }
    public void setNonPrescriptionDrugsEntity(NonPrescriptionDrugsEntity nonPrescriptionDrugs){
        this.nonPrescriptionDrugs=nonPrescriptionDrugs;
    }

    // 備考情報
    public DosageRemarkEntity dosageRemarkEntity;
    public DosageRemarkEntity getDosageRemarkEntity(){
        return this.dosageRemarkEntity;
    }
    public void setDosageRemarkEntity(DosageRemarkEntity dosageRemarkEntity){
        this.dosageRemarkEntity=dosageRemarkEntity;
    }
    //</editor-fold>
}
