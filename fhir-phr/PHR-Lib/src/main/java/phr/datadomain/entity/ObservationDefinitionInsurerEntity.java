/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者管理項目情報のデータオブジェクト
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
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 保険者管理項目情報のデータオブジェクトです。
 */
public class ObservationDefinitionInsurerEntity extends ObservationDefinitionInsurerEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionInsurerEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationDefinitionInsurerEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

    //疾病種別CD
    private int diseaseTypeCd;
    
    //項目表示名
    private String displayName;

    //リマインダ
    private String reminders;
    
    /**
     * @return the diseaseTypeCd
     */
    public int getDiseaseTypeCd() {
        return diseaseTypeCd;
    }

    /**
     * @param diseaseTypeCd the diseaseTypeCd to set
     */
    public void setDiseaseTypeCd(int diseaseTypeCd) {
        this.diseaseTypeCd = diseaseTypeCd;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the reminders
     */
    public String getReminders() {
        return reminders;
    }

    /**
     * @param reminders the reminders to set
     */
    public void setReminders(String reminders) {
        this.reminders = reminders;
    }
}
