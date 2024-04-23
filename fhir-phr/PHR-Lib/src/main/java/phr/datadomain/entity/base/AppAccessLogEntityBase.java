/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：活用同意一覧情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2022/04/13
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.AppAccessLogEntity;

/**
 * Appアクセスログのデータオブジェクトです。
 */
public abstract class AppAccessLogEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AppAccessLogEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public AppAccessLogEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* SeqID */
    protected int seq_Id = 0;
    /* PHR_ID */
    protected String pHR_ID = null;
    /* アクセス日時 */
    protected Timestamp accessDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  AppAccessLogEntity
     */
    public static AppAccessLogEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  AppAccessLogEntity
     */
    public static AppAccessLogEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        AppAccessLogEntity entity = new AppAccessLogEntity();
        entity.setSeq_Id(getInt(dataRow, "Seq_Id"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setAccessDateTime(getDateTime(dataRow, "AccessDateTime"));

        if (logger.isDebugEnabled())
        {
        	logger.debug("Seq_Id      ：" + entity.getSeq_Id());
            logger.debug("PHR_ID      ：" + entity.getPHR_ID());
            logger.debug("アクセス日時：" + entity.getAccessDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * SeqIdを取得する
     *
     * @return
     */
    public int getSeq_Id() {
        return this.seq_Id;
    }
    /**
     * SeqIdを設定する
     *
     * @param value
     */
    public void setSeq_Id(int value) {
        this.seq_Id = value;
    }

    /**
     * PHR_IDを取得する
     *
     * @return
     */
    public String getPHR_ID() {
        return this.pHR_ID;
    }
    /**
     * PHR_IDを設定する
     *
     * @param value
     */
    public void setPHR_ID(String value) {
        this.pHR_ID = value;
    }

    /**
     * アクセス日時を取得する
     *
     * @return
     */
    public Timestamp getAccessDateTime() {
        return this.accessDateTime;
    }
    /**
     * アクセス日時を設定する
     *
     * @param value
     */
    public void setAccessDateTime(Timestamp value) {
        this.accessDateTime = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "Seq_Id=" + seq_Id + 
                "pHR_ID=" + pHR_ID + 
                ", accessDateTime=" + accessDateTime + 
                " }\r\n";
    }
}
