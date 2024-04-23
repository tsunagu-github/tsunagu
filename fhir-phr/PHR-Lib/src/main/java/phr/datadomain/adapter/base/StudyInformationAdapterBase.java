/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：研究情報のデータオブジェクト
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
import java.sql.SQLException; 
import java.sql.Timestamp;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.Seq_ObservationImportHistoryIdAdapter;
import phr.datadomain.entity.StudyInformationEntity;
import phr.datadomain.entity.UtilizationConsentEntity;

/**
 * 研究情報のデータオブジェクトです。
 */
public abstract class StudyInformationAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(StudyInformationAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public StudyInformationAdapterBase(Connection conn)
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
        sql.append("    StudyInformation.StudyId As StudyId  \r\n");
        sql.append("    , StudyInformation.StudyName As StudyName  \r\n");
        sql.append("    , StudyInformation.Explanation As Explanation  \r\n");
        sql.append("    , StudyInformation.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , StudyInformation.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from StudyInformation \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into StudyInformation \r\n");
        sql.append("(StudyId, StudyName, Explanation, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update StudyInformation set \r\n");
        sql.append("StudyName = ?, Explanation = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where StudyId = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  StudyInformation \r\n");
        sql.append("where StudyId = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(StudyInformationEntity entity) throws Throwable {
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
            if (entity.getStudyName() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getStudyName());
            }
            if (entity.getExplanation() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getExplanation());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(StudyInformationEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getStudyName() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getStudyName());
            }
            if (entity.getExplanation() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getExplanation());
            }
            if (entity.getStudyId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getStudyId());
            }
            if (entity.getUpdateDateTime() == null) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getUpdateDateTime().toString());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて研究情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(StudyInformationEntity entity) throws Throwable {
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
            
        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて研究情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public StudyInformationEntity findByPrimaryKey(String key1) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where StudyInformation.StudyId = ?";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        
        StudyInformationEntity entity = null;
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            entity = StudyInformationEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entity;
    }
}
