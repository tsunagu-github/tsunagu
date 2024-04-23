/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 管理項目種別のデータオブジェクトです。
 */
public class ObservationDefinitionTypeEntity extends ObservationDefinitionTypeEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionTypeEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionTypeEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * 項目表示名
     */
    private String displayName;
     /**
     * 項目表示名
     */
    private Integer sortNo;    /**
     * JLAC10
     */
    private String jlac10;
    /** 
     * UnitValue
     */
    private String unitValue;
    /**
     * 項目表示名
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }
    /**
     * 項目表示名
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the jlac10
     */
    public String getJlac10() {
        return jlac10;
    }

    /**
     * @param jlac10 the jlac10 to set
     */
    public void setJlac10(String jlac10) {
        this.jlac10 = jlac10;
    }
    /**
     * @return the sortNo
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * @param sortNo the sortNo to set
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

	/**
	 * @return unitValue
	 */
	public String getUnitValue() {
		return unitValue;
	}
	/**
	 * @param unitValue セットする unitValue
	 */
	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	/**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionTypeEntity
     */
    public static ObservationDefinitionTypeEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  ObservationDefinitionTypeEntity
     */
    public static ObservationDefinitionTypeEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        ObservationDefinitionTypeEntity entity = new ObservationDefinitionTypeEntity();
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setObservationDefinitionId(getString(dataRow, "ObservationDefinitionId"));
        entity.setDataInputTypeCd(getInt(dataRow, "DataInputTypeCd"));
        entity.setUnitValue(getString(dataRow, "UnitValue"));

        if (logger.isDebugEnabled())
        {
            logger.debug("保険者番号：" + entity.getInsurerNo());
            logger.debug("項目ID    ：" + entity.getObservationDefinitionId());
            logger.debug("疾患種別Cd：" + entity.getDataInputTypeCd());
            logger.debug("単位：" + entity.getUnitValue());
        }
        logger.trace("End");
        return entity;
    }
}
