/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データ引継ぎ情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/07
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
 * データ引継ぎ情報のデータオブジェクトです。
 */
public class DataTransferEntity extends DataTransferEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DataTransferEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DataTransferEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
