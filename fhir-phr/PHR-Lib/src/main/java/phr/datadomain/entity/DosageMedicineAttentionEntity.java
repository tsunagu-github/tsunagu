/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：薬品服用注意情報のデータオブジェクト
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
 * 薬品服用注意情報のデータオブジェクトです。
 */
public class DosageMedicineAttentionEntity extends DosageMedicineAttentionEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicineAttentionEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageMedicineAttentionEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}