/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：薬剤情報のデータオブジェクト
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
 * 薬剤情報のデータオブジェクトです。
 */
public class DosageMedicineEntity extends DosageMedicineEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageMedicineEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public DosageMedicineEntity()
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
