/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：JLAC10分析物情報のデータオブジェクト
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
import phr.datadomain.entity.JLAC10AnalyteCodeEntity;

/**
 * JLAC10分析物情報のデータオブジェクトです。
 */
public abstract class JLAC10AnalyteCodeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(JLAC10AnalyteCodeEntityBase.class);
    private static Logger logger = Logger.getLogger(JLAC10AnalyteCodeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public JLAC10AnalyteCodeEntityBase(){ 
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 測定物分類コード */
    protected String analyteCode = null;
    /* 測定物コード */
    protected String analyteType = null;
    /* 名称１ */
    protected String analyteName1 = null;
    /* 名称２ */
    protected String analyteName2 = null;
    /* 備考 */
    protected String note = null;
    /* 病院検査結果表示対象 */
    protected boolean hospitalLabResultTargetFlg = false;
    /* グラフ表示対象 */
    protected boolean graphTargetFllg = false;
    /* JLAC10バージョン */
    protected String jLAC10Version = null;
    /* 作成日時 */
    protected Timestamp createDateTime = null;
    /* 最終更新日時 */
    protected Timestamp updateDateTime = null;
    /* ソート番号 */
    protected int sortNo = 0;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  JLAC10AnalyteCodeEntity
     */
    public static JLAC10AnalyteCodeEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  JLAC10AnalyteCodeEntity
     */
    public static JLAC10AnalyteCodeEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.debug("Start");
        JLAC10AnalyteCodeEntity entity = new JLAC10AnalyteCodeEntity();
        entity.setAnalyteCode(getString(dataRow, "AnalyteCode"));
        entity.setAnalyteType(getString(dataRow, "AnalyteType"));
        entity.setAnalyteName1(getString(dataRow, "AnalyteName1"));
        entity.setAnalyteName2(getString(dataRow, "AnalyteName2"));
        entity.setNote(getString(dataRow, "Note"));
        entity.setHospitalLabResultTargetFlg(getBoolean(dataRow, "HospitalLabResultTargetFlg"));
        entity.setGraphTargetFllg(getBoolean(dataRow, "GraphTargetFllg"));
        entity.setJLAC10Version(getString(dataRow, "JLAC10Version"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));
        entity.setSortNo(getInt(dataRow, "SortNo"));

        if (logger.isDebugEnabled())
        {
            logger.debug("測定物分類コード        ：" + entity.getAnalyteCode());
            logger.debug("測定物コード    ：" + entity.getAnalyteType());
            logger.debug("名称１              ：" + entity.getAnalyteName1());
            logger.debug("名称２              ：" + entity.getAnalyteName2());
            logger.debug("備考                ：" + entity.getNote());
            logger.debug("病院検査結果表示対象：" + entity.isHospitalLabResultTargetFlg());
            logger.debug("グラフ表示対象      ：" + entity.isGraphTargetFllg());
            logger.debug("JLAC10バージョン    ：" + entity.getJLAC10Version());
            logger.debug("作成日時            ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時        ：" + entity.getUpdateDateTime());
            logger.debug("ソート番号        ：" + entity.getSortNo());
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
     * 名称１を取得する
     *
     * @return
     */
    public String getAnalyteName1() {
        return this.analyteName1;
    }
    /**
     * 名称１を設定する
     *
     * @param value
     */
    public void setAnalyteName1(String value) {
        this.analyteName1 = value;
    }

    /**
     * 名称２を取得する
     *
     * @return
     */
    public String getAnalyteName2() {
        return this.analyteName2;
    }
    /**
     * 名称２を設定する
     *
     * @param value
     */
    public void setAnalyteName2(String value) {
        this.analyteName2 = value;
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
     * JLAC10バージョンを取得する
     *
     * @return
     */
    public String getJLAC10Version() {
        return this.jLAC10Version;
    }
    /**
     * JLAC10バージョンを設定する
     *
     * @param value
     */
    public void setJLAC10Version(String value) {
        this.jLAC10Version = value;
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
     * ソート番号を取得する
     *
     * @return
     */
    public int getSortNo() {
        return this.sortNo;
    }
    /**
     * ソート番号を設定する
     *
     * @param value
     */
    public void setSortNo(int value) {
        this.sortNo = value;
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
                ", analyteName1=" + analyteName1 + 
                ", analyteName2=" + analyteName2 + 
                ", note=" + note + 
                ", hospitalLabResultTargetFlg=" + hospitalLabResultTargetFlg + 
                ", graphTargetFllg=" + graphTargetFllg + 
                ", jLAC10Version=" + jLAC10Version + 
                ", createDateTime=" + createDateTime + 
                ", updateDateTime=" + updateDateTime + 
                ", sortNo=" + sortNo + 
                " }\r\n";
    }
}
