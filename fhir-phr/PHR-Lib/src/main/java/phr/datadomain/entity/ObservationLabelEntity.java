/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain.entity;

import java.sql.ResultSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.ObservationDefinitionInsurerEntityBase;

/**
 *
 * @author kis-note
 */
public class ObservationLabelEntity extends AbstractEntity{
        /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationLabelEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationLabelEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 項目ID */
    private String observationDefinitionId = null;
    /* 項目名称 */
    private String observationDefinitionName = null;
    /* 表示名 */
    private String displayName = null;
    /* 疾患種別Cd */
    private int diseaseTypeCd = 0;
    /* データ種別Cd */
    private int dataInputTypeCd = 0;
    /* -------------------------------------------------------------------------------------- */

        /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionInsurerEntity
     */
    public static ObservationLabelEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionInsurerEntity
     */
    public static ObservationLabelEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ObservationLabelEntity entity = new ObservationLabelEntity();
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setObservationDefinitionName(getString(dataRow, "ObservationDefinitionName"));
        entity.setDisplayName(getString(dataRow, "DisplayName"));
        entity.setDiseaseTypeCd(getInt(dataRow, "DiseaseTypeCd"));
        entity.setDataInputTypeCd(getInt(dataRow, "DataInputTypeCd"));

        if (logger.isDebugEnabled())
        {
            logger.debug("項目ID                ：" + entity.getObservationDefinitionId());
            logger.debug("項目名称              ：" + entity.getObservationDefinitionName());
            logger.debug("表示名                ：" + entity.getDisplayName());
            logger.debug("疾患種別Cd            ：" + entity.getDiseaseTypeCd());
            logger.debug("データ種別Cd          ：" + entity.getDataInputTypeCd());
        }
        logger.trace("End");
        return entity;
    }

    /**
     * @return the observationDefinitionId
     */
    public String getObservationDefinitionId() {
        return observationDefinitionId;
    }

    /**
     * @param observationDefinitionId the observationDefinitionId to set
     */
    public void setObservationDefinitionId(String observationDefinitionId) {
        this.observationDefinitionId = observationDefinitionId;
    }

    /**
     * @return the observationDefinitionName
     */
    public String getObservationDefinitionName() {
        return observationDefinitionName;
    }

    /**
     * @param observationDefinitionName the observationDefinitionName to set
     */
    public void setObservationDefinitionName(String observationDefinitionName) {
        this.observationDefinitionName = observationDefinitionName;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the dataInputTypeCd
     */
    public int getDataInputTypeCd() {
        return dataInputTypeCd;
    }

    /**
     * @param dataInputTypeCd the dataInputTypeCd to set
     */
    public void setDataInputTypeCd(int dataInputTypeCd) {
        this.dataInputTypeCd = dataInputTypeCd;
    }

    /**
     * @return the diseaseTypeCd
     */
    public int getDiseaseTypeCd() {
        return diseaseTypeCd;
    }

    /**
     * @param diseaseTypeCd the diseaseTypeCd to set
     */
    public void setDiseaseTypeCd(int diseaseTypeCd) {
        this.diseaseTypeCd = diseaseTypeCd;
    }
}
