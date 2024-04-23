/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者管理項目疾病種別のデータオブジェクト
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
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.InsurerDiseaseTypeEntity;

/**
 * 保険者管理項目疾病種別のデータオブジェクトです。
 */
public abstract class InsurerDiseaseTypeEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(InsurerDiseaseTypeEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public InsurerDiseaseTypeEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* ViewID */
    protected int viewId = 0;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* 疾病種別CD */
    protected int diseaseTypeCd = 0;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  InsurerDiseaseTypeEntity
     */
    public static InsurerDiseaseTypeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  InsurerDiseaseTypeEntity
     */
    public static InsurerDiseaseTypeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        InsurerDiseaseTypeEntity entity = new InsurerDiseaseTypeEntity();
        entity.setViewId(getInt(dataRow, "ViewId"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setDiseaseTypeCd(getInt(dataRow, "DiseaseTypeCd"));

        if (logger.isDebugEnabled())
        {
            logger.debug("ViewID    ：" + entity.getViewId());
            logger.debug("項目ID    ：" + entity.getObservationDefinitionId());
            logger.debug("疾病種別CD：" + entity.getDiseaseTypeCd());
        }
        logger.trace("End");
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
                "iewId=" + viewId + 
                ", bservationDefinitionId=" + observationDefinitionId + 
                ", iseaseTypeCd=" + diseaseTypeCd + 
                " }\r\n";
    }
}
