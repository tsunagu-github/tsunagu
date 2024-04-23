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
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 保険者管理項目疾病種別のデータオブジェクトです。
 */
public class InsurerDiseaseTypeEntity extends InsurerDiseaseTypeEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(InsurerDiseaseTypeEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public InsurerDiseaseTypeEntity()
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
