/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：一般用医薬品のデータオブジェクト
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
 * 一般用医薬品のデータオブジェクトです。
 */
public class NonPrescriptionDrugsEntity extends NonPrescriptionDrugsEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NonPrescriptionDrugsEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public NonPrescriptionDrugsEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */
    private boolean isDelete=false;

    /**
     * @return the isDelete
     */
    public boolean isIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete the isDelete to set
     */
    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
    

}
