/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医師薬剤師情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.DosageDoctorEntity;

/**
 * 医師薬剤師情報のデータオブジェクトです。
 */
public abstract class DosageDoctorEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageDoctorEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageDoctorEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 調剤ID */
    protected String dosageId = null;
    /* 調剤番号 */
    protected int seq = 0;
    /* 処方調剤 */
    protected String dosageTypeCd = null;
    /* 医師･薬剤師氏名 */
    protected String doctorName = null;
    /* 医師・薬剤師連絡先 */
    protected String contactAddress = null;
    /* レコード作成者 */
    protected String recordCreatorType = null;
    /* 診療科名 */
    protected String departmentName = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageDoctorEntity
     */
    public static DosageDoctorEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageDoctorEntity
     */
    public static DosageDoctorEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        DosageDoctorEntity entity = new DosageDoctorEntity();
        entity.setDosageId(getString(dataRow, "DosageId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setDosageTypeCd(getString(dataRow, "DosageTypeCd"));
        entity.setDoctorName(getString(dataRow, "DoctorName"));
        entity.setContactAddress(getString(dataRow, "ContactAddress"));
        entity.setRecordCreatorType(getString(dataRow, "RecordCreatorType"));
        entity.setDepartmentName(getString(dataRow, "DepartmentName"));

        if (logger.isDebugEnabled())
        {
            logger.debug("調剤ID            ：" + entity.getDosageId());
            logger.debug("調剤番号          ：" + entity.getSeq());
            logger.debug("処方調剤          ：" + entity.getDosageTypeCd());
            logger.debug("医師･薬剤師氏名   ：" + entity.getDoctorName());
            logger.debug("医師・薬剤師連絡先：" + entity.getContactAddress());
            logger.debug("レコード作成者    ：" + entity.getRecordCreatorType());
            logger.debug("診療科名          ：" + entity.getDepartmentName());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 調剤IDを取得する
     *
     * @return
     */
    public String getDosageId() {
        return this.dosageId;
    }
    /**
     * 調剤IDを設定する
     *
     * @param value
     */
    public void setDosageId(String value) {
        this.dosageId = value;
    }

    /**
     * 調剤番号を取得する
     *
     * @return
     */
    public int getSeq() {
        return this.seq;
    }
    /**
     * 調剤番号を設定する
     *
     * @param value
     */
    public void setSeq(int value) {
        this.seq = value;
    }

    /**
     * 処方調剤を取得する
     *
     * @return
     */
    public String getDosageTypeCd() {
        return this.dosageTypeCd;
    }
    /**
     * 処方調剤を設定する
     *
     * @param value
     */
    public void setDosageTypeCd(String value) {
        this.dosageTypeCd = value;
    }

    /**
     * 医師･薬剤師氏名を取得する
     *
     * @return
     */
    public String getDoctorName() {
        return this.doctorName;
    }
    /**
     * 医師･薬剤師氏名を設定する
     *
     * @param value
     */
    public void setDoctorName(String value) {
        this.doctorName = value;
    }

    /**
     * 医師・薬剤師連絡先を取得する
     *
     * @return
     */
    public String getContactAddress() {
        return this.contactAddress;
    }
    /**
     * 医師・薬剤師連絡先を設定する
     *
     * @param value
     */
    public void setContactAddress(String value) {
        this.contactAddress = value;
    }

    /**
     * レコード作成者を取得する
     *
     * @return
     */
    public String getRecordCreatorType() {
        return this.recordCreatorType;
    }
    /**
     * レコード作成者を設定する
     *
     * @param value
     */
    public void setRecordCreatorType(String value) {
        this.recordCreatorType = value;
    }

    /**
     * 診療科名を取得する
     *
     * @return
     */
    public String getDepartmentName() {
        return this.departmentName;
    }
    /**
     * 診療科名を設定する
     *
     * @param value
     */
    public void setDepartmentName(String value) {
        this.departmentName = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "osageId=" + dosageId + 
                ", eq=" + seq + 
                ", osageTypeCd=" + dosageTypeCd + 
                ", octorName=" + doctorName + 
                ", ontactAddress=" + contactAddress + 
                ", ecordCreatorType=" + recordCreatorType + 
                ", epartmentName=" + departmentName + 
                " }\r\n";
    }
}
