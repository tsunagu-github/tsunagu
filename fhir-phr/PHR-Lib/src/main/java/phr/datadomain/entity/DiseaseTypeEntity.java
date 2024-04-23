/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：疾病種別のデータオブジェクト
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
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 疾病種別のデータオブジェクトです。
 */
public class DiseaseTypeEntity extends DiseaseTypeEntityBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(DiseaseTypeEntity.class);
    private static Logger logger = Logger.getLogger(DiseaseTypeEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DiseaseTypeEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

}
