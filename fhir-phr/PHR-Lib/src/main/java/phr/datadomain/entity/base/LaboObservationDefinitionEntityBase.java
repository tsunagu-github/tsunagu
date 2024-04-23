/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査会社検査項目情報のデータオブジェクト
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
import phr.datadomain.entity.LaboObservationDefinitionEntity;

/**
 * 検査会社検査項目情報のデータオブジェクトです。
 */
public abstract class LaboObservationDefinitionEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(LaboObservationDefinitionEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public LaboObservationDefinitionEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 検査会社CD */
    protected String laboratoryCd = null;
    /* 検査会社検査CD */
    protected String laboratoryDefinitionCd = null;
    /* 項目ID */
    protected String observationDefinitionId = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  LaboObservationDefinitionEntity
     */
    public static LaboObservationDefinitionEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  LaboObservationDefinitionEntity
     */
    public static LaboObservationDefinitionEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        LaboObservationDefinitionEntity entity = new LaboObservationDefinitionEntity();
        entity.setLaboratoryCd(getString(dataRow, "LaboratoryCd"));
        entity.setLaboratoryDefinitionCd(getString(dataRow, "LaboratoryDefinitionCd"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));

        if (logger.isDebugEnabled())
        {
            logger.debug("検査会社CD    ：" + entity.getLaboratoryCd());
            logger.debug("検査会社検査CD：" + entity.getLaboratoryDefinitionCd());
            logger.debug("項目ID        ：" + entity.getObservationDefinitionId());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 検査会社CDを取得する
     *
     * @return
     */
    public String getLaboratoryCd() {
        return this.laboratoryCd;
    }
    /**
     * 検査会社CDを設定する
     *
     * @param value
     */
    public void setLaboratoryCd(String value) {
        this.laboratoryCd = value;
    }

    /**
     * 検査会社検査CDを取得する
     *
     * @return
     */
    public String getLaboratoryDefinitionCd() {
        return this.laboratoryDefinitionCd;
    }
    /**
     * 検査会社検査CDを設定する
     *
     * @param value
     */
    public void setLaboratoryDefinitionCd(String value) {
        this.laboratoryDefinitionCd = value;
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
                "aboratoryCd=" + laboratoryCd + 
                ", aboratoryDefinitionCd=" + laboratoryDefinitionCd + 
                ", bservationDefinitionId=" + observationDefinitionId + 
                " }\r\n";
    }
}
