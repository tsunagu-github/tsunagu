/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：連携システムデータ取得タイミング情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/29
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.DataAccessTimingEntity;

/**
 * 連携システムデータ取得タイミング情報のデータオブジェクトです。
 */
public abstract class DataAccessTimingEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(DataAccessTimingEntityBase.class);
    private static Logger logger = Logger.getLogger(DataAccessTimingEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DataAccessTimingEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* データ連携システムID */
    protected int dataCooperationSystemId = 0;
    /* 連番 */
    protected int seq = 0;
    /* 取得時刻 */
    protected String dataAccessTime = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DataAccessTimingEntity
     */
    public static DataAccessTimingEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DataAccessTimingEntity
     */
    public static DataAccessTimingEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.debug("Start");
        DataAccessTimingEntity entity = new DataAccessTimingEntity();
        entity.setDataCooperationSystemId(getInt(dataRow, "DataCooperationSystemId"));
        entity.setSeq(getInt(dataRow, "Seq"));
        entity.setDataAccessTime(getString(dataRow, "DataAccessTime"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("データ連携システムID：" + entity.getDataCooperationSystemId());
            logger.debug("連番                ：" + entity.getSeq());
            logger.debug("取得時刻            ：" + entity.getDataAccessTime());
            logger.debug("作成日時            ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時        ：" + entity.getUpdateDateTime());
        }
        logger.debug("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * データ連携システムIDを取得する
     *
     * @return
     */
    public int getDataCooperationSystemId() {
        return this.dataCooperationSystemId;
    }
    /**
     * データ連携システムIDを設定する
     *
     * @param value
     */
    public void setDataCooperationSystemId(int value) {
        this.dataCooperationSystemId = value;
    }

    /**
     * 連番を取得する
     *
     * @return
     */
    public int getSeq() {
        return this.seq;
    }
    /**
     * 連番を設定する
     *
     * @param value
     */
    public void setSeq(int value) {
        this.seq = value;
    }

    /**
     * 取得時刻を取得する
     *
     * @return
     */
    public String getDataAccessTime() {
        return this.dataAccessTime;
    }
    /**
     * 取得時刻を設定する
     *
     * @param value
     */
    public void setDataAccessTime(String value) {
        this.dataAccessTime = value;
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
                "dataCooperationSystemId=" + dataCooperationSystemId + 
                ", seq=" + seq + 
                ", dataAccessTime=" + dataAccessTime + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
