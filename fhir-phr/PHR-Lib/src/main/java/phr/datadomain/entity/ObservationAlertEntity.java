/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査項目結果アラート情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/25
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
 * 検査項目結果アラート情報のデータオブジェクトです。
 */
public class ObservationAlertEntity extends ObservationAlertEntityBase
{

    /**
     * @return the pHR_ID
     */
    public String getpHR_ID() {
        return pHR_ID;
    }

    /**
     * @param pHR_ID the pHR_ID to set
     */
    public void setpHR_ID(String pHR_ID) {
        this.pHR_ID = pHR_ID;
    }

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationAlertEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public ObservationAlertEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

    /* 検査日 */
    private Timestamp examinationDate = null;
    /* PHRID */
    private String pHR_ID = null;
     /* 画面名称 */
    private String viewName = null;
    /* 氏名_姓 */
    private String familyName = null;
    /* 氏名_名 */
    private String givenName = null;
    /* 検査名称　*/
    private String observationDefinitionName = null;
    /* 検査名称　*/
    private String displayName = null;
    /* 表示用検査実施日　*/
    private String displayDate = null;
    /* 検査結果閲覧同意*/
    private boolean agreesToShare = false;

    /**
     * @return the examinationDate
     */
    public Timestamp getExaminationDate() {
        return examinationDate;
    }

    /**
     * @param examinationDate the examinationDate to set
     */
    public void setExaminationDate(Timestamp examinationDate) {
        this.examinationDate = examinationDate;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @param familyName the familyName to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @param givenName the givenName to set
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * @return the observationDefinitionName
     */
    public String getObservationDefinitionName() {
        return observationDefinitionName;
    }

    /**
     * @param observationDefinitionName the observationDefinitionName to set
     */
    public void setObservationDefinitionName(String observationDefinitionName) {
        this.observationDefinitionName = observationDefinitionName;
    }

    /**
     * @return the viewName
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * @param viewName the viewName to set
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
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
     * @return the displayDate
     */
    public String getDisplayDate() {
        return displayDate;
    }

    /**
     * @param displayDate the displayDate to set
     */
    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    /*
     * 検査結果閲覧同意
     */
    public boolean isAgreesToShare() {
        return agreesToShare;
    }

    public void setAgreesToShare(boolean agreesToShare) {
        this.agreesToShare = agreesToShare;
    }

}
