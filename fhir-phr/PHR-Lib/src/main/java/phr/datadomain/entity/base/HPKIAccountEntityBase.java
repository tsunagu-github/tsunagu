/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：HPKIアカウント情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/02/27
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
import phr.datadomain.entity.HPKIAccountEntity;

/**
 * HPKIアカウント情報のデータオブジェクトです。
 */
public abstract class HPKIAccountEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(HPKIAccountEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public HPKIAccountEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 医師名 */
    protected String commonName = null;
    /* シリアル番号 */
    protected String serialNo = null;
    /* 保険者番号 */
    protected String insurerNo = null;
    /* 医療機関コード */
    protected String medicalOrganizationCd = null;
    /* 最終ログイン日時 */
    protected Timestamp lastLoginDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  HPKIAccountEntity
     */
    public static HPKIAccountEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  HPKIAccountEntity
     */
    public static HPKIAccountEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        HPKIAccountEntity entity = new HPKIAccountEntity();
        entity.setCommonName(getString(dataRow, "CommonName"));
        entity.setSerialNo(getString(dataRow, "SerialNo"));
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setMedicalOrganizationCd(getString(dataRow, "MedicalOrganizationCd"));
        entity.setLastLoginDateTime(getDateTime(dataRow, "LastLoginDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("医師名          ：" + entity.getCommonName());
            logger.debug("シリアル番号    ：" + entity.getSerialNo());
            logger.debug("保険者番号      ：" + entity.getInsurerNo());
            logger.debug("医療機関コード  ：" + entity.getMedicalOrganizationCd());
            logger.debug("最終ログイン日時：" + entity.getLastLoginDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 医師名を取得する
     *
     * @return
     */
    public String getCommonName() {
        return this.commonName;
    }
    /**
     * 医師名を設定する
     *
     * @param value
     */
    public void setCommonName(String value) {
        this.commonName = value;
    }

    /**
     * シリアル番号を取得する
     *
     * @return
     */
    public String getSerialNo() {
        return this.serialNo;
    }
    /**
     * シリアル番号を設定する
     *
     * @param value
     */
    public void setSerialNo(String value) {
        this.serialNo = value;
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
     * 最終ログイン日時を取得する
     *
     * @return
     */
    public Timestamp getLastLoginDateTime() {
        return this.lastLoginDateTime;
    }
    /**
     * 最終ログイン日時を設定する
     *
     * @param value
     */
    public void setLastLoginDateTime(Timestamp value) {
        this.lastLoginDateTime = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "ommonName=" + commonName + 
                ", erialNo=" + serialNo + 
                ", nsurerNo=" + insurerNo + 
                ", edicalOrganizationCd=" + medicalOrganizationCd + 
                ", astLoginDateTime=" + lastLoginDateTime + 
                " }\r\n";
    }
}
