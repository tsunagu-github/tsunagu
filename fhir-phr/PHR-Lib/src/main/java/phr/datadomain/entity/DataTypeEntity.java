/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データ種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * データ種別のデータオブジェクトです。
 */
public class DataTypeEntity extends DataTypeEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DataTypeEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DataTypeEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
