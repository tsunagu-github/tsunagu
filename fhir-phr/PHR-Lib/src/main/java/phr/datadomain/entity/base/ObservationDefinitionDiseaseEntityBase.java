/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：項目疾病情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/05
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
import phr.datadomain.entity.ObservationDefinitionDiseaseEntity;

/**
 * 項目疾病情報のデータオブジェクトです。
 */
public abstract class ObservationDefinitionDiseaseEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionDiseaseEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionDiseaseEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 保険者番号 */
    protected String insurerNo = null;
    /* 対象年度 */
    protected int year = 0;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* 疾病種別CD */
    protected int diseaseTypeCd = 0;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionDiseaseEntity
     */
    public static ObservationDefinitionDiseaseEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionDiseaseEntity
     */
    public static ObservationDefinitionDiseaseEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ObservationDefinitionDiseaseEntity entity = new ObservationDefinitionDiseaseEntity();
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setYear(getInt(dataRow, "Year"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setDiseaseTypeCd(getInt(dataRow, "DiseaseTypeCd"));

        if (logger.isDebugEnabled())
        {
            logger.debug("保険者番号：" + entity.getInsurerNo());
            logger.debug("対象年度  ：" + entity.getYear());
            logger.debug("項目ID    ：" + entity.getObservationDefinitionId());
            logger.debug("疾病種別CD：" + entity.getDiseaseTypeCd());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

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
     * 対象年度を取得する
     *
     * @return
     */
    public int getYear() {
        return this.year;
    }
    /**
     * 対象年度を設定する
     *
     * @param value
     */
    public void setYear(int value) {
        this.year = value;
    }

    /**
     * 項目IDを取得する
     *
     * @return
     */
    public String getObservationDefinitionId() {
        return this.observationDefinitionId;
    }
    /**
     * 項目IDを設定する
     *
     * @param value
     */
    public void setObservationDefinitionId(String value) {
        this.observationDefinitionId = value;
    }

    /**
     * 疾病種別CDを取得する
     *
     * @return
     */
    public int getDiseaseTypeCd() {
        return this.diseaseTypeCd;
    }
    /**
     * 疾病種別CDを設定する
     *
     * @param value
     */
    public void setDiseaseTypeCd(int value) {
        this.diseaseTypeCd = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "nsurerNo=" + insurerNo + 
                ", ear=" + year + 
                ", bservationDefinitionId=" + observationDefinitionId + 
                ", iseaseTypeCd=" + diseaseTypeCd + 
                " }\r\n";
    }
}
