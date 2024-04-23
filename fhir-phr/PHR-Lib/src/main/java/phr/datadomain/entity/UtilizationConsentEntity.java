/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：活用同意一覧情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2022/04/13
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.base.*;

/**
 * 活用同意一覧情報のデータオブジェクトです。
 */
public class UtilizationConsentEntity extends UtilizationConsentEntityBase {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UtilizationConsentEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public UtilizationConsentEntity() { 
    }
    /* -------------------------------------------------------------------------------------- */

    private String familyName = null;
    
    private String givenName = null;
    
    private Date birthDate = null;
    
    private String studyName = null;
    
    private List<String> responseStatusList = null;
    
    protected String birthStr = null;
    
    private String notificationStr = null;
    
    private String studyId = null;

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public static Log getLogger() {
        return logger;
    }
    
    public List<String> getResponseStatusList() {
        return responseStatusList;
    }

    public void setResponseStatusList(List<String> responseStatusList) {
        this.responseStatusList = responseStatusList;
    }

    public String getBirthStr() {
        return birthStr;
    }

    public void setBirthStr(String birthStr) {
        this.birthStr = birthStr;
    }

    public String getNotificationStr() {
        return notificationStr;
    }

    public void setNotificationStr(String notificationStr) {
        this.notificationStr = notificationStr;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  UtilizationConsentEntity
     */
    public static UtilizationConsentEntity setData(ResultSet dataRow) throws SQLException {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  UtilizationConsentEntity
     */
    public static UtilizationConsentEntity setData(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        UtilizationConsentEntity entity = new UtilizationConsentEntity();
        entity.setUtilizationConsentId(getString(dataRow, "UtilizationConsentId"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setStudyId(getString(dataRow, "StudyId"));
        entity.setSubjectId(getString(dataRow, "SubjectId"));
        entity.setConsentType(getString(dataRow, "ConsentType"));
        entity.setResponseStatus(getString(dataRow, "ResponseStatus"));
        entity.setNewArrivalFlg(getBoolean(dataRow, "NewArrivalFlg"));
        entity.setInvalid(getString(dataRow, "Invalid"));
        entity.setNotificationDate(getDateTime(dataRow, "NotificationDate"));
        entity.setResponseDate(getDateTime(dataRow, "ResponseDate"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));
        entity.setFamilyName(getString(dataRow, "familyName"));
        entity.setGivenName(getString(dataRow, "givenName"));
        entity.setBirthDate(getDate(dataRow, "birthDate"));
        entity.setStudyName(getString(dataRow, "studyName"));

        if (logger.isDebugEnabled())
        {
            logger.debug("活用同意一覧ID：" + entity.getUtilizationConsentId());
            logger.debug("PHR_ID        ：" + entity.getPHR_ID());
            logger.debug("研究ID        ：" + entity.getStudyId());
            logger.debug("研究ID        ：" + entity.getSubjectId());
            logger.debug("同意種別      ：" + entity.getConsentType());
            logger.debug("回答ステータス：" + entity.getResponseStatus());
            logger.debug("新着フラグ    ：" + entity.isNewArrivalFlg());
            logger.debug("無効フラグ    ：" + entity.getInvalid());
            logger.debug("通知日        ：" + entity.getNotificationDate());
            logger.debug("回答更新日      ：" + entity.getResponseDate());
            logger.debug("作成日時      ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時  ：" + entity.getUpdateDateTime());
            logger.debug("苗字：" + entity.getFamilyName());
            logger.debug("名前：" + entity.getGivenName());
            logger.debug("生年月日：" + entity.getBirthDate());
            logger.debug("研究名：" + entity.getStudyName());
        }
        logger.trace("End");
        return entity;
    }
    
    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  UtilizationConsentEntity
     */
    public static UtilizationConsentEntity setDataList(ResultSet dataRow) throws SQLException {
        return setDataList(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  UtilizationConsentEntity
     */
    public static UtilizationConsentEntity setDataList(ResultSet dataRow, boolean isChildTable) throws SQLException {
        logger.trace("Start");
        UtilizationConsentEntity entity = new UtilizationConsentEntity();
        entity.setUtilizationConsentId(getString(dataRow, "UtilizationConsentId"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));
        entity.setStudyId(getString(dataRow, "StudyId"));
        entity.setSubjectId(getString(dataRow, "SubjectId"));
        entity.setConsentType(getString(dataRow, "ConsentType"));
        entity.setResponseStatus(getString(dataRow, "ResponseStatus"));
        entity.setNewArrivalFlg(getBoolean(dataRow, "NewArrivalFlg"));
        entity.setInvalid(getString(dataRow, "Invalid"));
        entity.setNotificationDate(getDateTime(dataRow, "NotificationDate"));
        entity.setResponseDate(getDateTime(dataRow, "ResponseDate"));
        entity.setCreateDateTime(getDateTime(dataRow, "CreateDateTime"));
        entity.setUpdateDateTime(getDateTime(dataRow, "UpdateDateTime"));

        if (logger.isDebugEnabled())
        {
            logger.debug("活用同意一覧ID：" + entity.getUtilizationConsentId());
            logger.debug("PHR_ID        ：" + entity.getPHR_ID());
            logger.debug("研究ID        ：" + entity.getStudyId());
            logger.debug("項目ID        ：" + entity.getSubjectId());
            logger.debug("同意種別      ：" + entity.getConsentType());
            logger.debug("回答ステータス：" + entity.getResponseStatus());
            logger.debug("新着フラグ    ：" + entity.isNewArrivalFlg());
            logger.debug("無効フラグ    ：" + entity.getInvalid());
            logger.debug("通知日        ：" + entity.getNotificationDate());
            logger.debug("回答更新日      ：" + entity.getResponseDate());
            logger.debug("作成日時      ：" + entity.getCreateDateTime());
            logger.debug("最終更新日時  ：" + entity.getUpdateDateTime());
        }
        logger.trace("End");
        return entity;
    }
}
