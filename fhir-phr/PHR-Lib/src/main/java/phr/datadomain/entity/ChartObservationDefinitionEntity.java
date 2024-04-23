/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：グラフ項目定義のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2019/05/09
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
 * グラフ項目定義のデータオブジェクトです。
 */
public class ChartObservationDefinitionEntity extends ChartObservationDefinitionEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartObservationDefinitionEntity.class);

    /**
     * 項目名
     */
    protected String observationDefinitionName;
 
    /**
     * 単位
     */
    private String unitValue;
   
    /**
     * データ入力種別コード
     */
    private String dataInputTypeCd;
    
    /**
     * 重複項目フラグ
     */
    private boolean duplicateFlg;
    
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ChartObservationDefinitionEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * 項目名を取得する
     *
     * @return
     */
    public String getObservationDefinitionName() {
        return this.observationDefinitionName;
    }
    /**
     * 項目名を設定する
     *
     * @param value
     */
    public void setObservationDefinitionName(String value) {
        this.observationDefinitionName = value;
    }

    /**
     * 単位を取得する
     * @return the unitValue
     */
    public String getUnitValue() {
        return unitValue;
    }

    /**
     * 単位を設定する
     * @param unitValue the unitValue to set
     */
    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    /**
     * @return dataInputTypeCd
     */
    public String getDataInputTypeCd() {
        return dataInputTypeCd;
    }
    /**
     * @param dataInputTypeCd セットする dataInputTypeCd
     */
    public void setDataInputTypeCd(String dataInputTypeCd) {
        this.dataInputTypeCd = dataInputTypeCd;
    }
	/**
	 * @return duplicateFlg
	 */
	public boolean isDuplicateFlg() {
		return duplicateFlg;
	}
	/**
	 * @param duplicateFlg セットする duplicateFlg
	 */
	public void setDuplicateFlg(boolean duplicateFlg) {
		this.duplicateFlg = duplicateFlg;
	}
}
