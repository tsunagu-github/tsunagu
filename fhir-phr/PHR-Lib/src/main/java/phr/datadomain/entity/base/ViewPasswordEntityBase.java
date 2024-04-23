/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：参照用パスワード情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
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
import phr.datadomain.entity.ViewPasswordEntity;

/**
 * 参照用パスワード情報のデータオブジェクトです。
 */
public abstract class ViewPasswordEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ViewPasswordEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ViewPasswordEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 参照用パスワード */
    protected String password = null;
    /* PHR ID */
    protected String pHR_ID = null;
    /* 有効期限 */
    protected Timestamp expirationDate = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ViewPasswordEntity
     */
    public static ViewPasswordEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ViewPasswordEntity
     */
    public static ViewPasswordEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ViewPasswordEntity entity = new ViewPasswordEntity();
        entity.setPassword(getString(dataRow, "Password"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setExpirationDate(getDateTime(dataRow, "ExpirationDate"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("参照用パスワード：" + entity.getPassword());
            logger.debug("PHR ID          ：" + entity.getPHR_ID());
            logger.debug("有効期限        ：" + entity.getExpirationDate());
            logger.debug("作成日時        ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時    ：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 参照用パスワードを取得する
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * 参照用パスワードを設定する
     *
     * @param value
     */
    public void setPassword(String value) {
        this.password = value;
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

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "assword=" + password + 
                ", HR_ID=" + pHR_ID + 
                ", xpirationDate=" + expirationDate + 
                ", reateDateTime=" + createDateTime + 
                ", pdateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
