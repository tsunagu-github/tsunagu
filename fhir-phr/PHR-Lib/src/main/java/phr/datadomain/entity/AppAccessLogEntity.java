/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：活用同意一覧情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2022/04/13
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.base.*;

/**
 * Appアクセスログのデータオブジェクトです。
 */
public class AppAccessLogEntity extends AppAccessLogEntityBase {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AppAccessLogEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public AppAccessLogEntity() { 
    }
    /* -------------------------------------------------------------------------------------- */

}
