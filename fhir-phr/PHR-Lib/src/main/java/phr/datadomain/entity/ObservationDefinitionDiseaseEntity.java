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
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 項目疾病情報のデータオブジェクトです。
 */
public class ObservationDefinitionDiseaseEntity extends ObservationDefinitionDiseaseEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionDiseaseEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionDiseaseEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */
    /* 疾患種別Cd */
    private int dataInputTypeCd = 0;

    /**
     * 疾患種別Cd
     * @return the dataInputTypeCd
     */
    public int getDataInputTypeCd() {
        return dataInputTypeCd;
    }

    /**
     * 疾患種別Cd
     * @param dataInputTypeCd the dataInputTypeCd to set
     */
    public void setDataInputTypeCd(int dataInputTypeCd) {
        this.dataInputTypeCd = dataInputTypeCd;
    }
    
}
