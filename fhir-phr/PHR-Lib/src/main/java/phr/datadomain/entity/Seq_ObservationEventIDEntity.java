/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果ID管理のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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
 * 検査結果ID管理のデータオブジェクトです。
 */
public class Seq_ObservationEventIDEntity extends Seq_ObservationEventIDEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(Seq_ObservationEventIDEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public Seq_ObservationEventIDEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
