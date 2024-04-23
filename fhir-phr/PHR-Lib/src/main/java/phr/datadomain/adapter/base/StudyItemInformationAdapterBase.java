/********************************************************************************
 * システム名      ：MInCS for ePRO
 * コンポーネント名：研究項目情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2022/06/06
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
import phr.datadomain.entity.StudyItemInformationEntity;

/**
 * 研究項目情報のデータオブジェクトです。
 */
public abstract class StudyItemInformationAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(StudyItemInformationAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public StudyItemInformationAdapterBase(Connection conn)
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
        sql.append("    StudyItemInformation.StudyId As StudyId  \r\n");
        sql.append("    , StudyItemInformation.SubjectId As SubjectId  \r\n");
        sql.append("    , StudyItemInformation.Subject As Subject  \r\n");
        sql.append("    , StudyItemInformation.ConsentType As ConsentType  \r\n");
        sql.append("    , StudyItemInformation.Url As Url  \r\n");
        sql.append("    , StudyItemInformation.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , StudyItemInformation.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from StudyItemInformation \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into StudyItemInformation \r\n");
        sql.append("(StudyId, SubjectId, Subject, ConsentType, Url, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update StudyItemInformation set \r\n");
        sql.append("Subject = ?, ConsentType = ?, Url = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where StudyId = ? AND SubjectId = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  StudyItemInformation \r\n");
        sql.append("where StudyId = ? AND SubjectId = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(StudyItemInformationEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getStudyId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getStudyId());
            }
            if (entity.getSubjectId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getSubjectId());
            }
            if (entity.getSubject() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getSubject());
            }
            if (entity.getConsentType() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getConsentType());
            }
            if (entity.getUrl() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getUrl());
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
    public int update(StudyItemInformationEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getSubject() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getSubject());
            }
            if (entity.getConsentType() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getConsentType());
            }
            if (entity.getUrl() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getUrl());
            }
            if (entity.getStudyId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getStudyId());
            }
            if (entity.getSubjectId() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getSubjectId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて研究項目情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(StudyItemInformationEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getStudyId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getStudyId());
            }
            if (entity.getStudyId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getStudyId());
            }
            
        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて研究項目情報を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public StudyItemInformationEntity findByPrimaryKey(String key1, String key2) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where StudyItemInformation.StudyId = ?";
        sql += " where StudyItemInformation.SubjectId = ?";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);
        
        StudyItemInformationEntity entity = null;
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            entity = StudyItemInformationEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entity;
    }
}
