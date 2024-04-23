/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関患者関連情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/10/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;

/**
 * 医療機関患者関連情報のデータオブジェクトです。
 */
public abstract class MedicalOrganizationPatientEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(MedicalOrganizationPatientEntityBase.class);
    private static Logger logger = Logger.getLogger(MedicalOrganizationPatientEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public MedicalOrganizationPatientEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 医療機関コード */
    protected String medicalOrganizationCd = null;
    /* PHR ID */
    protected String pHR_ID = null;
    /* 患者ID */
    protected String patientId = null;
    /* 閲覧同意フラグ */
    protected Boolean agreesToShare = null;
    /* -------------------------------------------------------------------------------------- */

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
        entity.setAgreesToShare(getNullBoolean(dataRow, "AgreesToShare"));

        if (logger.isDebugEnabled())
        {
            logger.debug("医療機関コード：" + entity.getMedicalOrganizationCd());
            logger.debug("PHR ID        ：" + entity.getPHR_ID());
            logger.debug("患者ID        ：" + entity.getPatientId());
            logger.debug("閲覧同意フラグ：" + entity.isAgreesToShare());
        }
        logger.debug("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 医療機関コードを取得する
     *
     * @return
     */
    public String getMedicalOrganizationCd() {
        return this.medicalOrganizationCd;
    }
    /**
     * 医療機関コードを設定する
     *
     * @param value
     */
    public void setMedicalOrganizationCd(String value) {
        this.medicalOrganizationCd = value;
    }

    /**
     * PHR IDを取得する
     *
     * @return
     */
    public String getPHR_ID() {
        return this.pHR_ID;
    }
    /**
     * PHR IDを設定する
     *
     * @param value
     */
    public void setPHR_ID(String value) {
        this.pHR_ID = value;
    }

    /**
     * 患者IDを取得する
     *
     * @return
     */
    public String getPatientId() {
        return this.patientId;
    }
    /**
     * 患者IDを設定する
     *
     * @param value
     */
    public void setPatientId(String value) {
        this.patientId = value;
    }

    /**
     * 閲覧同意フラグを取得する
     *
     * @return
     */
    public Boolean isAgreesToShare() {
        return this.agreesToShare;
    }
    /**
     * 閲覧同意フラグを設定する
     *
     * @param value
     */
    public void setAgreesToShare(Boolean value) {
        this.agreesToShare = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "medicalOrganizationCd=" + medicalOrganizationCd + 
                ", pHR_ID=" + pHR_ID + 
                ", patientId=" + patientId + 
                ", agreesToShare=" + agreesToShare + 
                " }\r\n";
    }
}
