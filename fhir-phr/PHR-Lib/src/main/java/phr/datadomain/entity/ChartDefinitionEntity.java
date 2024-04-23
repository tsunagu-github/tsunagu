/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：グラフ定義のデータオブジェクト
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
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * グラフ定義のデータオブジェクトです。
 */
public class ChartDefinitionEntity extends ChartDefinitionEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ChartDefinitionEntity.class);

    /**
     * グラフ項目リスト
     */
    private List<ChartObservationDefinitionEntity> chartObservationDefinitionList;
    
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ChartDefinitionEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * グラフ項目リスト
     * @return the chartObservationDefinitionList
     */
    public List<ChartObservationDefinitionEntity> getChartObservationDefinitionList() {
        return chartObservationDefinitionList;
    }

    /**
     * グラフ項目リスト
     * @param chartObservationDefinitionList the chartObservationDefinitionList to set
     */
    public void setChartObservationDefinitionList(List<ChartObservationDefinitionEntity> chartObservationDefinitionList) {
        this.chartObservationDefinitionList = chartObservationDefinitionList;
    }

}
