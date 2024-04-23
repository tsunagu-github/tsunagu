/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関患者関連情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
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
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 医療機関患者関連情報のデータオブジェクトです。
 */
public class MedicalOrganizationPatientEntity extends MedicalOrganizationPatientEntityBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(MedicalOrganizationPatientEntity.class);
    private static Logger logger = Logger.getLogger(MedicalOrganizationPatientEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public MedicalOrganizationPatientEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

    public boolean getMedicalOrganizationCd(String medicalCd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean getPatientId(String patientId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /* 医療機関名称 */
    private String medicalOrganizationName = null;

    /**
     * @return the medicalOrganizationName
     */
    public String getMedicalOrganizationName() {
        return medicalOrganizationName;
    }

    /**
     * @param medicalOrganizationName the medicalOrganizationName to set
     */
    public void setMedicalOrganizationName(String medicalOrganizationName) {
        this.medicalOrganizationName = medicalOrganizationName;
    }

    /* 閲覧同意フラグ */
    protected boolean agreesToShare;

    /**
     * 閲覧同意フラグを取得する
     *
     * @return
     */
    public boolean getAgreesToShare() {
        return this.agreesToShare;
    }
    /**
     * 閲覧同意フラグを設定する
     *
     * @param value
     */
    public void setAgreesToShare(boolean value) {
        this.agreesToShare = value;
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  MedicalOrganizationPatientEntity
     */
    public static MedicalOrganizationPatientEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  MedicalOrganizationPatientEntity
     */
    public static MedicalOrganizationPatientEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.debug("Start");
        MedicalOrganizationPatientEntity entity = new MedicalOrganizationPatientEntity();
        entity.setMedicalOrganizationCd(getString(dataRow, "MedicalOrganizationCd"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setPatientId(getString(dataRow, "PatientId"));
        entity.setAgreesToShare(getBoolean(dataRow, "AgreesToShare"));

        if (logger.isDebugEnabled())
        {
            logger.debug("医療機関コード：" + entity.getMedicalOrganizationCd());
            logger.debug("PHR ID        ：" + entity.getPHR_ID());
            logger.debug("患者ID        ：" + entity.getPatientId());
            logger.debug("閲覧同意フラグ        ：" + entity.getAgreesToShare());
        }
        logger.debug("End");
        return entity;
    }

}
