/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：アクセスログのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/10/07
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
 * アクセスログのデータオブジェクトです。
 */
public class AccessLogsEntity extends AccessLogsEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AccessLogsEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public AccessLogsEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
