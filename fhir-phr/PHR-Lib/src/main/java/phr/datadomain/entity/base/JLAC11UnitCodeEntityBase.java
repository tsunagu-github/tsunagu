/********************************************************************************
 * システム名      ：phr
 * コンポーネント名：JLAC11単位コード情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/12/08
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.JLAC11UnitCodeEntity;

/**
 * JLAC11単位コード情報のデータオブジェクトです。
 */
public abstract class JLAC11UnitCodeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = Logger.getLogger(JLAC11UnitCodeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public JLAC11UnitCodeEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* JLAC11単位コード */
    protected String jLAC11UnitCode = null;
    /* 単位（表示名） */
    protected String unit = null;
    /* 備考 */
    protected String note = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  JLAC11UnitCodeEntity
     */
    public static JLAC11UnitCodeEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  JLAC11UnitCodeEntity
     */
    public static JLAC11UnitCodeEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        JLAC11UnitCodeEntity entity = new JLAC11UnitCodeEntity();
        entity.setJLAC11UnitCode(getString(dataRow, "JLAC11UnitCode"));
        entity.setUnit(getString(dataRow, "Unit"));
        entity.setNote(getString(dataRow, "Note"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("JLAC11単位コード：" + entity.getJLAC11UnitCode());
            logger.debug("単位（表示名）  ：" + entity.getUnit());
            logger.debug("備考            ：" + entity.getNote());
            logger.debug("作成日時        ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時    ：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * JLAC11単位コードを取得する
     *
     * @return
     */
    public String getJLAC11UnitCode() {
        return this.jLAC11UnitCode;
    }
    /**
     * JLAC11単位コードを設定する
     *
     * @param value
     */
    public void setJLAC11UnitCode(String value) {
        this.jLAC11UnitCode = value;
    }

    /**
     * 単位（表示名）を取得する
     *
     * @return
     */
    public String getUnit() {
        return this.unit;
    }
    /**
     * 単位（表示名）を設定する
     *
     * @param value
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * 備考を取得する
     *
     * @return
     */
    public String getNote() {
        return this.note;
    }
    /**
     * 備考を設定する
     *
     * @param value
     */
    public void setNote(String value) {
        this.note = value;
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
                "jLAC11UnitCode=" + jLAC11UnitCode + 
                ", unit=" + unit + 
                ", note=" + note + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
