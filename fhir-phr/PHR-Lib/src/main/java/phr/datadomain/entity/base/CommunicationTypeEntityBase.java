/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：コミュニケーション種別のデータオブジェクト
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
import phr.datadomain.entity.CommunicationTypeEntity;

/**
 * コミュニケーション種別のデータオブジェクトです。
 */
public abstract class CommunicationTypeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationTypeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public CommunicationTypeEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* コミュニケーション種別CD */
    protected int communicationTypeCd = 0;
    /* 名称 */
    protected String name = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  CommunicationTypeEntity
     */
    public static CommunicationTypeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  CommunicationTypeEntity
     */
    public static CommunicationTypeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        CommunicationTypeEntity entity = new CommunicationTypeEntity();
        entity.setCommunicationTypeCd(getInt(dataRow, "CommunicationTypeCd"));
        entity.setName(getString(dataRow, "Name"));

        if (logger.isDebugEnabled())
        {
            logger.debug("コミュニケーション種別CD：" + entity.getCommunicationTypeCd());
            logger.debug("名称                    ：" + entity.getName());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * コミュニケーション種別CDを取得する
     *
     * @return
     */
    public int getCommunicationTypeCd() {
        return this.communicationTypeCd;
    }
    /**
     * コミュニケーション種別CDを設定する
     *
     * @param value
     */
    public void setCommunicationTypeCd(int value) {
        this.communicationTypeCd = value;
    }

    /**
     * 名称を取得する
     *
     * @return
     */
    public String getName() {
        return this.name;
    }
    /**
     * 名称を設定する
     *
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "ommunicationTypeCd=" + communicationTypeCd + 
                ", ame=" + name + 
                " }\r\n";
    }
}
