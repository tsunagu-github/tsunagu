/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：ワンタイムID情報のデータオブジェクト
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
import phr.datadomain.entity.OneTimeIdInfoEntity;

/**
 * ワンタイムID情報のデータオブジェクトです。
 */
public abstract class OneTimeIdInfoEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(OneTimeIdInfoEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public OneTimeIdInfoEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 医師名 */
    protected String oneTimeId = null;
    /* シリアル番号 */
    protected String commonName = null;
    /* 保険者番号 */
    protected String insurerNo = null;
    /* 医療機関コード */
    protected String medicalOrganizationCd = null;
    /* 有効期限 */
    protected Timestamp expirationDate = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  OneTimeIdInfoEntity
     */
    public static OneTimeIdInfoEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  OneTimeIdInfoEntity
     */
    public static OneTimeIdInfoEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        OneTimeIdInfoEntity entity = new OneTimeIdInfoEntity();
        entity.setOneTimeId(getString(dataRow, "OneTimeId"));
        entity.setCommonName(getString(dataRow, "CommonName"));
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setMedicalOrganizationCd(getString(dataRow, "MedicalOrganizationCd"));
        entity.setExpirationDate(getDateTime(dataRow, "ExpirationDate"));

        if (logger.isDebugEnabled())
        {
            logger.debug("医師名        ：" + entity.getOneTimeId());
            logger.debug("シリアル番号  ：" + entity.getCommonName());
            logger.debug("保険者番号    ：" + entity.getInsurerNo());
            logger.debug("医療機関コード：" + entity.getMedicalOrganizationCd());
            logger.debug("有効期限      ：" + entity.getExpirationDate());
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
    public String getOneTimeId() {
        return this.oneTimeId;
    }
    /**
     * 医師名を設定する
     *
     * @param value
     */
    public void setOneTimeId(String value) {
        this.oneTimeId = value;
    }

    /**
     * シリアル番号を取得する
     *
     * @return
     */
    public String getCommonName() {
        return this.commonName;
    }
    /**
     * シリアル番号を設定する
     *
     * @param value
     */
    public void setCommonName(String value) {
        this.commonName = value;
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
     * 有効期限を取得する
     *
     * @return
     */
    public Timestamp getExpirationDate() {
        return this.expirationDate;
    }
    /**
     * 有効期限を設定する
     *
     * @param value
     */
    public void setExpirationDate(Timestamp value) {
        this.expirationDate = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "neTimeId=" + oneTimeId + 
                ", ommonName=" + commonName + 
                ", nsurerNo=" + insurerNo + 
                ", edicalOrganizationCd=" + medicalOrganizationCd + 
                ", xpirationDate=" + expirationDate + 
                " }\r\n";
    }
}
