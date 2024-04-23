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
import java.sql.SQLException; 
import java.sql.Timestamp;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.AnswerStatusAdapter;
import phr.datadomain.entity.AnswerStatusEntity;
import phr.datadomain.entity.UtilizationConsentEntity;

/**
 * 回答情報のデータオブジェクトです。
 */
public abstract class AnswerStatusAdapterBase {

    /**
     * ロギングコンポーネント
     */
	private static Logger logger = Logger.getLogger(AnswerStatusAdapterBase.class);

	/**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public AnswerStatusAdapterBase(Connection conn)
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
        sql.append("    AnswerStatus.PHR_ID As PHR_ID  \r\n");
        sql.append("    , AnswerStatus.StudyId As StudyId  \r\n");
        sql.append("    , AnswerStatus.SubjectId As SubjectId  \r\n");
        sql.append("    , AnswerStatus.CheckListId As CheckListId  \r\n");
        sql.append("    , AnswerStatus.Status As Status  \r\n");
        sql.append("    , AnswerStatus.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , AnswerStatus.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from AnswerStatus \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into AnswerStatus \r\n");
        sql.append("(PHR_ID, StudyId, SubjectId, CheckListId, Status, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update AnswerStatus set \r\n");
        sql.append("Status = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where PHR_ID = ? AND StudyId = ? AND SubjectId = ? AND CheckListId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  AnswerStatus \r\n");
        sql.append("where PHR_ID = ? AND StudyId = ? AND SubjectId = ? AND CheckListId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(AnswerStatusEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
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
            if (entity.getCheckListId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getCheckListId());
            }
            if (!entity.isStatus()) {
                preparedStatement.setNull(5, Types.BOOLEAN );
            } else {
                preparedStatement.setBoolean(5, entity.isStatus());
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
    public int update(AnswerStatusEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (!entity.isStatus()) {
                preparedStatement.setNull(1, Types.BOOLEAN );
            } else {
                preparedStatement.setBoolean(1, entity.isStatus());
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
            if (entity.getCheckListId() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getCheckListId());
            }
            if (entity.getUpdateDateTime() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getUpdateDateTime().toString());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて回答情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(AnswerStatusEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
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
            if (entity.getCheckListId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getCheckListId());
            }
            if (entity.getUpdateDateTime() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getUpdateDateTime().toString());
            }
            
        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.debug("End");
        return rowCount;
    }

    /**
     * 主キーにて回答情報を検索します。
     * @param key1
     * @param key2
     * @param key3
     * @param key4
     * @return
     * @throws Throwable
     */
    public AnswerStatusEntity findByPrimaryKey(String key1, String key2, String key3, String key4) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where AnswerStatus.PHR_ID = ?";
        sql += "   and AnswerStatus.StudyId = ?";
        sql += "   and AnswerStatus.SubjectId = ?";
        sql += "   and AnswerStatus.CheckListId = ?";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);
        preparedStatement.setString(3, key3);
        preparedStatement.setString(4, key4);
        
        AnswerStatusEntity entity = null;
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            entity = AnswerStatusEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entity;
    }
}
