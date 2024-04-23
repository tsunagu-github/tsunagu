/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果情報のデータオブジェクト
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
import phr.datadomain.entity.ObservationEventEntity;

/**
 * 検査結果情報のデータオブジェクトです。
 */
public abstract class ObservationEventEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(ObservationEventEntityBase.class);
    private static Logger logger = Logger.getLogger(ObservationEventEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationEventEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 検査結果ID */
    protected String observationEventId = null;
    /* データ入力種別CD */
    protected int dataInputTypeCd = 0;
    /* PHR ID */
    protected String pHR_ID = null;
    /* 検査日 */
    protected Timestamp examinationDate = null;
    /* 対象年度 */
    protected int year = 0;
    /* 保険者番号 */
    protected String insurerNo = null;
    /* 検査会社CD */
    protected String laboratoryCd = null;
    /* 検査オーダーNo */
    protected String orderNo = null;
    /* 医療機関コード */
    protected String medicalOrganizationCd = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* 疾病管理対象フラグ */
    protected boolean diseaseManagementTargetFlg = false;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationEventEntity
     */
    public static ObservationEventEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationEventEntity
     */
    public static ObservationEventEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.debug("Start");
        ObservationEventEntity entity = new ObservationEventEntity();
        entity.setObservationEventId(getString(dataRow, "ObservationEventId"));
        entity.setDataInputTypeCd(getInt(dataRow, "DataInputTypeCd"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setExaminationDate(getDateTime(dataRow, "ExaminationDate"));
        entity.setYear(getInt(dataRow, "Year"));
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setLaboratoryCd(getString(dataRow, "LaboratoryCd"));
        entity.setOrderNo(getString(dataRow, "OrderNo"));
        entity.setMedicalOrganizationCd(getString(dataRow, "MedicalOrganizationCd"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));
        entity.setDiseaseManagementTargetFlg(getBoolean(dataRow, "DiseaseManagementTargetFlg"));

        if (logger.isDebugEnabled())
        {
            logger.debug("検査結果ID        ：" + entity.getObservationEventId());
            logger.debug("データ入力種別CD  ：" + entity.getDataInputTypeCd());
            logger.debug("PHR ID            ：" + entity.getPHR_ID());
            logger.debug("検査日            ：" + entity.getExaminationDate());
            logger.debug("対象年度          ：" + entity.getYear());
            logger.debug("保険者番号        ：" + entity.getInsurerNo());
            logger.debug("検査会社CD        ：" + entity.getLaboratoryCd());
            logger.debug("検査オーダーNo    ：" + entity.getOrderNo());
            logger.debug("医療機関コード    ：" + entity.getMedicalOrganizationCd());
            logger.debug("作成日時          ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時      ：" + entity.getUpdateDateTime());
            logger.debug("疾病管理対象フラグ：" + entity.isDiseaseManagementTargetFlg());
        }
        logger.debug("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 検査結果IDを取得する
     *
     * @return
     */
    public String getObservationEventId() {
        return this.observationEventId;
    }
    /**
     * 検査結果IDを設定する
     *
     * @param value
     */
    public void setObservationEventId(String value) {
        this.observationEventId = value;
    }

    /**
     * データ入力種別CDを取得する
     *
     * @return
     */
    public int getDataInputTypeCd() {
        return this.dataInputTypeCd;
    }
    /**
     * データ入力種別CDを設定する
     *
     * @param value
     */
    public void setDataInputTypeCd(int value) {
        this.dataInputTypeCd = value;
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
     * 検査日を取得する
     *
     * @return
     */
    public Timestamp getExaminationDate() {
        return this.examinationDate;
    }
    /**
     * 検査日を設定する
     *
     * @param value
     */
    public void setExaminationDate(Timestamp value) {
        this.examinationDate = value;
    }

    /**
     * 対象年度を取得する
     *
     * @return
     */
    public int getYear() {
        return this.year;
    }
    /**
     * 対象年度を設定する
     *
     * @param value
     */
    public void setYear(int value) {
        this.year = value;
    }

    /**
     * 保険者番号を取得する
     *
     * @return
     */
    public String getInsurerNo() {
        return this.insurerNo;
    }
    /**
     * 保険者番号を設定する
     *
     * @param value
     */
    public void setInsurerNo(String value) {
        this.insurerNo = value;
    }

    /**
     * 検査会社CDを取得する
     *
     * @return
     */
    public String getLaboratoryCd() {
        return this.laboratoryCd;
    }
    /**
     * 検査会社CDを設定する
     *
     * @param value
     */
    public void setLaboratoryCd(String value) {
        this.laboratoryCd = value;
    }

    /**
     * 検査オーダーNoを取得する
     *
     * @return
     */
    public String getOrderNo() {
        return this.orderNo;
    }
    /**
     * 検査オーダーNoを設定する
     *
     * @param value
     */
    public void setOrderNo(String value) {
        this.orderNo = value;
    }

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
     * 作成日時を取得する
     *
     * @return
     */
    public Timestamp getCreateDateTime() {
        return this.createDateTime;
    }
    /**
     * 作成日時を設定する
     *
     * @param value
     */
    public void setCreateDateTime(Timestamp value) {
        this.createDateTime = value;
    }

    /**
     * 最終更新日時を取得する
     *
     * @return
     */
    public Timestamp getUpdateDateTime() {
        return this.updateDateTime;
    }
    /**
     * 最終更新日時を設定する
     *
     * @param value
     */
    public void setUpdateDateTime(Timestamp value) {
        this.updateDateTime = value;
    }

    /**
     * 疾病管理対象フラグを取得する
     *
     * @return
     */
    public boolean isDiseaseManagementTargetFlg() {
        return this.diseaseManagementTargetFlg;
    }
    /**
     * 疾病管理対象フラグを設定する
     *
     * @param value
     */
    public void setDiseaseManagementTargetFlg(boolean value) {
        this.diseaseManagementTargetFlg = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "observationEventId=" + observationEventId + 
                ", dataInputTypeCd=" + dataInputTypeCd + 
                ", pHR_ID=" + pHR_ID + 
                ", examinationDate=" + examinationDate + 
                ", year=" + year + 
                ", insurerNo=" + insurerNo + 
                ", laboratoryCd=" + laboratoryCd + 
                ", orderNo=" + orderNo + 
                ", medicalOrganizationCd=" + medicalOrganizationCd + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                ", diseaseManagementTargetFlg=" + diseaseManagementTargetFlg + 
                " }\r\n";
    }
}
