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
package phr.datadomain.adapter.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import org.apache.log4j.Logger;

import phr.datadomain.DataAccessObject;
import phr.datadomain.entity.UtilizationConsentEntity;

/**
 * 活用同意一覧情報のデータオブジェクトです。
 */
public abstract class UtilizationConsentAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(UtilizationConsentAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public UtilizationConsentAdapterBase(Connection conn)
    {
        connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */


    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    UtilizationConsent.UtilizationConsentId As UtilizationConsentId  \r\n");
        sql.append("    , UtilizationConsent.PHR_ID As PHR_ID  \r\n");
        sql.append("    , UtilizationConsent.StudyId As StudyId  \r\n");
        sql.append("    , UtilizationConsent.SubjectId As SubjectId  \r\n");
        sql.append("    , UtilizationConsent.ConsentType As ConsentType  \r\n");
        sql.append("    , UtilizationConsent.ResponseStatus As ResponseStatus  \r\n");
        sql.append("    , UtilizationConsent.NewArrivalFlg As NewArrivalFlg  \r\n");
        sql.append("    , UtilizationConsent.Invalid As Invalid  \r\n");
        sql.append("    , UtilizationConsent.NotificationDate As NotificationDate  \r\n");
        sql.append("    , UtilizationConsent.ResponseDate As ResponseDate  \r\n");
        sql.append("    , UtilizationConsent.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , UtilizationConsent.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from UtilizationConsent \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into UtilizationConsent \r\n");
        sql.append("(UtilizationConsentId, PHR_ID, StudyId, SubjectId, ConsentType, ResponseStatus, NewArrivalFlg, Invalid, NotificationDate, ResponseDate, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update UtilizationConsent set \r\n");
        sql.append("PHR_ID = ?, StudyId = ?, SubjectId = ?, ConsentType = ?, ResponseStatus = ?, NewArrivalFlg = ?, Invalid = ?, NotificationDate = ?, ResponseDate = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where UtilizationConsentId = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  UtilizationConsent \r\n");
        sql.append("where UtilizationConsentId = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(UtilizationConsentEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getUtilizationConsentId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getUtilizationConsentId());
            }
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPHR_ID());
            }
            if (entity.getStudyId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getStudyId());
            }
            if (entity.getSubjectId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getSubjectId());
            }
            if (entity.getConsentType() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getConsentType());
            }
            if (entity.getResponseStatus() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getResponseStatus());
            }
            if (!entity.isNewArrivalFlg()) {
                preparedStatement.setNull(7, Types.BOOLEAN );
            } else {
                preparedStatement.setBoolean(7, entity.isNewArrivalFlg());
            }
            if (entity.getInvalid() == null) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getInvalid());
            }
            if (entity.getNotificationDate() == null) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setNString(9, entity.getNotificationDate().toString());
            }
            if (entity.getResponseDate() == null) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setNString(10, entity.getResponseDate().toString());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();

        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(UtilizationConsentEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getPHR_ID());
            }
            if (entity.getStudyId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getStudyId());
            }
            if (entity.getSubjectId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getSubjectId());
            }
            if (entity.getConsentType() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getConsentType());
            }
            if (entity.getResponseStatus() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getResponseStatus());
            }
            if (!entity.isNewArrivalFlg()) {
                preparedStatement.setNull(6, Types.BOOLEAN );
            } else {
                preparedStatement.setBoolean(6, entity.isNewArrivalFlg());
            }
            if (entity.getInvalid() == null) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getInvalid());
            }
            if (entity.getNotificationDate() == null) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setNString(8, entity.getNotificationDate().toString());
            }
            if (entity.getResponseDate() == null) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setNString(9, entity.getResponseDate().toString());
            }
            if (entity.getUtilizationConsentId() == null) {
                preparedStatement.setNull(10, Types.VARCHAR );
            } else {
                preparedStatement.setNString(10, entity.getUtilizationConsentId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて活用同意一覧情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(UtilizationConsentEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getUtilizationConsentId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getUtilizationConsentId());
            }
            
        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて活用同意一覧情報を検索します。
     * @param key1
     * @param key2
     * @param key3
     * @param key4
     * @return
     * @throws Throwable
     */
    public UtilizationConsentEntity findByPrimaryKey(String key1, String key2, String key3, String key4) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where UtilizationConsent.UtilizationConsentId = ?";
        sql += " and UtilizationConsent.PHR_ID = ?";
        sql += " and UtilizationConsent.StudyId = ?";
        sql += " and UtilizationConsent.SubjectId = ?";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);
        preparedStatement.setString(3, key3);
        preparedStatement.setString(4, key4);
        
        UtilizationConsentEntity entity = null;
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            entity = UtilizationConsentEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entity;
    }
}
