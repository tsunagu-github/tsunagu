/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：リマインダマスタのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/13
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
 * リマインダマスタのデータオブジェクトです。
 */
public class ReminderMasterEntity extends ReminderMasterEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderMasterEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ReminderMasterEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * ビュー名
     */
    private String viewName;
    /**
     * リマインダ種別名
     */
    private String reminderTypeName;
    /**
     * 項目ID
     */
    private String observationDefinitionId;
    /**
     * 項目名
     */
    private String observationDefinitionDisplayName;
    /**
     * ソートNo
     */
    private int sortNo;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getReminderTypeName() {
        return reminderTypeName;
    }

    public void setReminderTypeName(String reminderTypeName) {
        this.reminderTypeName = reminderTypeName;
    }

    public String getObservationDefinitionId() {
        return observationDefinitionId;
    }

    public void setObservationDefinitionId(String observationDefinitionId) {
        this.observationDefinitionId = observationDefinitionId;
    }

    public String getObservationDefinitionDisplayName() {
        return observationDefinitionDisplayName;
    }

    public void setObservationDefinitionDisplayName(String observationDefinitionDisplayName) {
        this.observationDefinitionDisplayName = observationDefinitionDisplayName;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }
}
