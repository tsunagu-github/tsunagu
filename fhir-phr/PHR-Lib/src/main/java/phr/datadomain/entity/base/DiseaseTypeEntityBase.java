/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：疾病種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/19
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
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.DiseaseTypeEntity;

/**
 * 疾病種別のデータオブジェクトです。
 */
public abstract class DiseaseTypeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(DiseaseTypeEntityBase.class);
    private static Logger logger = Logger.getLogger(DiseaseTypeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DiseaseTypeEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* ViewID */
    protected int viewId = 0;
    /* 疾病種別CD */
    protected int diseaseTypeCd = 0;
    /* 名称 */
    protected String name = null;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DiseaseTypeEntity
     */
    public static DiseaseTypeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DiseaseTypeEntity
     */
    public static DiseaseTypeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.debug("Start");
        DiseaseTypeEntity entity = new DiseaseTypeEntity();
        entity.setViewId(getInt(dataRow, "ViewId"));
        entity.setDiseaseTypeCd(getInt(dataRow, "DiseaseTypeCd"));
        entity.setName(getString(dataRow, "Name"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));

        if (logger.isDebugEnabled())
        {
            logger.debug("ViewID    ：" + entity.getViewId());
            logger.debug("疾病種別CD：" + entity.getDiseaseTypeCd());
            logger.debug("名称      ：" + entity.getName());
            logger.debug("項目ID    ：" + entity.getObservationDefinitionId());
        }
        logger.debug("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * ViewIDを取得する
     *
     * @return
     */
    public int getViewId() {
        return this.viewId;
    }
    /**
     * ViewIDを設定する
     *
     * @param value
     */
    public void setViewId(int value) {
        this.viewId = value;
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

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "iewId=" + viewId + 
                ", iseaseTypeCd=" + diseaseTypeCd + 
                ", ame=" + name + 
                ", bservationDefinitionId=" + observationDefinitionId + 
                " }\r\n";
    }
}
