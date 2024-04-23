/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：JLAC11測定物情報のデータオブジェクト
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
import phr.datadomain.entity.JLAC11AnalyteCodeEntity;

/**
 * JLAC11測定物情報のデータオブジェクトです。
 */
public abstract class JLAC11AnalyteCodeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(JLAC11AnalyteCodeEntityBase.class);
	private static Logger logger = Logger.getLogger(JLAC11AnalyteCodeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public JLAC11AnalyteCodeEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 測定物分類コード */
    protected String analyteCode = null;
    /* 測定物コード */
    protected String analyteType = null;
    /* 名称 */
    protected String analyteName = null;
    /* JLAC10分析物コード */
    protected String jLAC10AnalyteCode = null;
    /* 備考 */
    protected String note = null;
    /* 病院検査結果表示対象 */
    protected boolean hospitalLabResultTargetFlg = false;
    /* グラフ表示対象 */
    protected boolean graphTargetFllg = false;
    /* JLAC11バージョン */
    protected String jLAC11Version = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  JLAC11AnalyteCodeEntity
     */
    public static JLAC11AnalyteCodeEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  JLAC11AnalyteCodeEntity
     */
    public static JLAC11AnalyteCodeEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.debug("Start");
        JLAC11AnalyteCodeEntity entity = new JLAC11AnalyteCodeEntity();
        entity.setAnalyteCode(getString(dataRow, "AnalyteCode"));
        entity.setAnalyteType(getString(dataRow, "AnalyteType"));
        entity.setAnalyteName(getString(dataRow, "AnalyteName"));
        entity.setJLAC10AnalyteCode(getString(dataRow, "JLAC10AnalyteCode"));
        entity.setNote(getString(dataRow, "Note"));
        entity.setHospitalLabResultTargetFlg(getBoolean(dataRow, "HospitalLabResultTargetFlg"));
        entity.setGraphTargetFllg(getBoolean(dataRow, "GraphTargetFllg"));
        entity.setJLAC11Version(getString(dataRow, "JLAC11Version"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("測定物分類コード        ：" + entity.getAnalyteCode());
            logger.debug("測定物コード    ：" + entity.getAnalyteType());
            logger.debug("名称                ：" + entity.getAnalyteName());
            logger.debug("JLAC10分析物コード  ：" + entity.getJLAC10AnalyteCode());
            logger.debug("備考                ：" + entity.getNote());
            logger.debug("病院検査結果表示対象：" + entity.isHospitalLabResultTargetFlg());
            logger.debug("グラフ表示対象      ：" + entity.isGraphTargetFllg());
            logger.debug("JLAC11バージョン    ：" + entity.getJLAC11Version());
            logger.debug("作成日時            ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時        ：" + entity.getUpdateDateTime());
        }
        logger.debug("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 測定物分類コードを取得する
     *
     * @return
     */
    public String getAnalyteCode() {
        return this.analyteCode;
    }
    /**
     * 測定物分類コードを設定する
     *
     * @param value
     */
    public void setAnalyteCode(String value) {
        this.analyteCode = value;
    }

    /**
     * 測定物コードを取得する
     *
     * @return
     */
    public String getAnalyteType() {
        return this.analyteType;
    }
    /**
     * 測定物コードを設定する
     *
     * @param value
     */
    public void setAnalyteType(String value) {
        this.analyteType = value;
    }

    /**
     * 名称を取得する
     *
     * @return
     */
    public String getAnalyteName() {
        return this.analyteName;
    }
    /**
     * 名称を設定する
     *
     * @param value
     */
    public void setAnalyteName(String value) {
        this.analyteName = value;
    }

    /**
     * JLAC10分析物コードを取得する
     *
     * @return
     */
    public String getJLAC10AnalyteCode() {
        return this.jLAC10AnalyteCode;
    }
    /**
     * JLAC10分析物コードを設定する
     *
     * @param value
     */
    public void setJLAC10AnalyteCode(String value) {
        this.jLAC10AnalyteCode = value;
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
     * 病院検査結果表示対象を取得する
     *
     * @return
     */
    public boolean isHospitalLabResultTargetFlg() {
        return this.hospitalLabResultTargetFlg;
    }
    /**
     * 病院検査結果表示対象を設定する
     *
     * @param value
     */
    public void setHospitalLabResultTargetFlg(boolean value) {
        this.hospitalLabResultTargetFlg = value;
    }

    /**
     * グラフ表示対象を取得する
     *
     * @return
     */
    public boolean isGraphTargetFllg() {
        return this.graphTargetFllg;
    }
    /**
     * グラフ表示対象を設定する
     *
     * @param value
     */
    public void setGraphTargetFllg(boolean value) {
        this.graphTargetFllg = value;
    }

    /**
     * JLAC11バージョンを取得する
     *
     * @return
     */
    public String getJLAC11Version() {
        return this.jLAC11Version;
    }
    /**
     * JLAC11バージョンを設定する
     *
     * @param value
     */
    public void setJLAC11Version(String value) {
        this.jLAC11Version = value;
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
                "analyteCode=" + analyteCode + 
                ", analyteType=" + analyteType + 
                ", analyteName=" + analyteName + 
                ", jLAC10AnalyteCode=" + jLAC10AnalyteCode + 
                ", note=" + note + 
                ", hospitalLabResultTargetFlg=" + hospitalLabResultTargetFlg + 
                ", graphTargetFllg=" + graphTargetFllg + 
                ", jLAC11Version=" + jLAC11Version + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                " }\r\n";
    }
}
