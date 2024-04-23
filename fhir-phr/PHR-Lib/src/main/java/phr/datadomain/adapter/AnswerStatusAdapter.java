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
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.AnswerStatusEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.StudyDetailedInformationEntity;

/**
 * 回答情報のデータオブジェクトです。
 */
@Repository
public class AnswerStatusAdapter extends AnswerStatusAdapterBase {

    /**
     * ロギングコンポーネント
     */
	private static Logger logger = Logger.getLogger(AnswerStatusAdapter.class);

    /* -------------------------------------------------------------------------------------- */
	/**
     * コンストラクタ
     * @param conn
     */
    public AnswerStatusAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * PHR_IDにて回答情報を検索します。
     * @param phr_id
     * @return list
     * @throws Throwable
     */
    public List<AnswerStatusEntity> findByPhrId(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where AnswerStatus.PHR_ID = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);

        List<AnswerStatusEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(AnswerStatusEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return list;
    }

    /**
     * PHR_IDとStudyIdとSubjectIdにて回答情報を検索します。
     * @param phr_id
     * @param studyId
     * @param subjectId
     * @return list
     * @throws Throwable
     */
    public List<AnswerStatusEntity> findByPhrIdAndStudyId(String phr_id, String studyId, String subjectId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where AnswerStatus.PHR_ID = ?";
        sql += " and AnswerStatus.StudyId = ?";
        sql += " and AnswerStatus.SubjectId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);
        preparedStatement.setString(2, studyId);
        preparedStatement.setString(3, subjectId);

        List<AnswerStatusEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(AnswerStatusEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return list;
    }

    /**
     * 回答情報テーブルを更新する
     * @param entity
     * @return rowCount
     */
    public int updateRecord(AnswerStatusEntity entity) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject();
        StringBuilder sql = new StringBuilder();
        sql.append("update AnswerStatus set \r\n");
        sql.append("Status = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where PHR_ID = ? AND StudyId = ? AND SubjectId = ? AND CheckListId = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setBoolean(1, entity.isStatus());
            if (entity.getPHR_ID() == null) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPHR_ID());
            }
            if (entity.getStudyId() == null) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getStudyId());
            }
            if (entity.getSubjectId() == null) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getSubjectId());
            }
            if (entity.getCheckListId() == null) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getCheckListId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        
        logger.debug("End");
        return rowCount;
    }

    /**
     * 回答情報テーブルに登録する
     * @param entity
     * @return rowCount
     */
    public int insertRecord(AnswerStatusEntity entity) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject();
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

	        if (entity.getPHR_ID() == null) {
	            preparedStatement.setNull(1, Types.VARCHAR );
	        } else {
	            preparedStatement.setString(1, entity.getPHR_ID());
	        }
	        if (entity.getStudyId() == null) {
	            preparedStatement.setNull(2, Types.VARCHAR );
	        } else {
	            preparedStatement.setString(2, entity.getStudyId());
	        }
	        if (entity.getSubjectId() == null) {
	            preparedStatement.setNull(3, Types.VARCHAR );
	        } else {
	            preparedStatement.setString(3, entity.getSubjectId());
	        }
            if (entity.getCheckListId() == null) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getCheckListId());
            }
            preparedStatement.setBoolean(5, entity.isStatus());


        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        
        logger.debug("End");
        return rowCount;
    }
}
