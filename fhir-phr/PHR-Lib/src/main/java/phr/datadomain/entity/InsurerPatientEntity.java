/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者患者関連情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
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
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 保険者患者関連情報のデータオブジェクトです。
 */
public class InsurerPatientEntity extends InsurerPatientEntityBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(InsurerPatientEntity.class);
    private static Logger logger = Logger.getLogger(InsurerPatientEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public InsurerPatientEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
