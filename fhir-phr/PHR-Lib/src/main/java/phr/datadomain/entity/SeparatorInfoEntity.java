/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：用法補足情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/10/17
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
 * 用法補足情報のデータオブジェクトです。
 */
public class SeparatorInfoEntity extends SeparatorInfoEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SeparatorInfoEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public SeparatorInfoEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
