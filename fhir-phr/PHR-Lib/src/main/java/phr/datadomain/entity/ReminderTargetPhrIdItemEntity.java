/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain.entity;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author chiba
 */
public class ReminderTargetPhrIdItemEntity {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ReminderTargetPhrIdItemEntity.class);
    
    /**
     * コンストラクタ
     */
    public ReminderTargetPhrIdItemEntity()
    {
    }

    public String getPhrId() {
        return phrId;
    }

    public void setPhrId(String phrId) {
        this.phrId = phrId;
    }

    public String getObservationDefinitionId() {
        return observationDefinitionId;
    }

    public void setObservationDefinitionId(String observationDefinitionId) {
        this.observationDefinitionId = observationDefinitionId;
    }

    public Date getLastExaminationDate() {
        return lastExaminationDate;
    }

    public void setLastExaminationDate(Date lastExaminationDate) {
        this.lastExaminationDate = lastExaminationDate;
    }

    /* PHR-ID */
    private String phrId = null;
    /* 項目ID */
    private String observationDefinitionId = null;
    /* 最終検査日 */
    private Date lastExaminationDate = null;
    
}
