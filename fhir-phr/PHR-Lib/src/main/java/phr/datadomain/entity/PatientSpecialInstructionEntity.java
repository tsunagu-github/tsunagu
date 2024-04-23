/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者特記事項のデータオブジェクト
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
 * 患者特記事項のデータオブジェクトです。
 */
public class PatientSpecialInstructionEntity extends PatientSpecialInstructionEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientSpecialInstructionEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public PatientSpecialInstructionEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
