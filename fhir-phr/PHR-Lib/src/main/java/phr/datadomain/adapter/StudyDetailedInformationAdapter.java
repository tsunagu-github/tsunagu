/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：研究詳細情報のデータオブジェクト
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
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.StudyDetailedInformationEntity;
import phr.datadomain.entity.StudyItemInformationEntity;

/**
 * 研究詳細情報のデータオブジェクトです。
 */
@Repository
public class StudyDetailedInformationAdapter extends StudyDetailedInformationAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(StudyDetailedInformationAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public StudyDetailedInformationAdapter(Connection conn) 
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 研究IDと項目IDにて研究詳細情報を検索します。
     * @param studyId
     * @param subjectId
     * @return list
     * @throws Throwable
     */
    public List<StudyDetailedInformationEntity> findByStudyId(String studyId, String subjectId) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where StudyDetailedInformation.StudyId = ?";
        sql += " and StudyDetailedInformation.SubjectId = ?";
        sql += " and StudyDetailedInformation.Status = '1'";
        sql += " order by StudyDetailedInformation.SortNo";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, studyId);
        preparedStatement.setString(2, subjectId);
        
        List<StudyDetailedInformationEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(StudyDetailedInformationEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }
    
    /**
     * 主キーにてアカウント情報を検索します。(status=0のみ)
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public List<StudyDetailedInformationEntity> findByPrimaryKey(String key1, String key2) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
        sql += "select";
        sql += " StudyInformation.StudyId as StudyId  \r\n";
        sql += " , StudyItemInformation.SubjectId as SubjectId  \r\n";
        sql += " , StudyItemInformation.Subject as Subject  \r\n";
        sql += " , StudyItemInformation.ConsentType as ConsentType  \r\n";
        sql += " , StudyItemInformation.Url as Url  \r\n";
        sql += " , StudyItemInformation.CreateDateTime as CreateDateTime  \r\n";
        sql += " , StudyItemInformation.UpdateDateTime as UpdateDateTime  \r\n";
        sql += " , StudyInformation.StudyName as StudyName  \r\n";
        sql += " , studydetailedinformation.CheckList As CheckList  \r\n";
        sql += " , StudyDetailedInformation.Status As Status \r\n";
        sql += " , StudyDetailedInformation.SortNo As SortNo \r\n";
        sql += " , StudyDetailedInformation.SeqId As SeqId \r\n";
        sql += " from StudyInformation  \r\n";
        sql += " inner join StudyItemInformation  \r\n";
        sql += " on Studyinformation.StudyId = StudyItemInformation.StudyId  \r\n";
        sql += " inner join Studydetailedinformation  \r\n";
        sql += " on StudyItemInformation.SubjectId = studydetailedinformation.SubjectId  \r\n";
        sql += " where studydetailedinformation.StudyId = ?  \r\n";
        sql += " and studydetailedinformation.SubjectId = ?  \r\n";
        sql += " and studydetailedinformation.Status = ?  \r\n";

        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);
        preparedStatement.setString(3, "0");
        List<StudyDetailedInformationEntity> entityList = new ArrayList<StudyDetailedInformationEntity>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            StudyDetailedInformationEntity entity = StudyDetailedInformationEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entityList;
    }
    
    /**
     * 主キーにてアカウント情報を検索します。(status=1のみ)
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public List<StudyDetailedInformationEntity> findByPrimaryKey1(String key1, String key2) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
        sql += "select";
        sql += " StudyInformation.StudyId as StudyId  \r\n";
        sql += " , StudyItemInformation.SubjectId as SubjectId  \r\n";
        sql += " , StudyItemInformation.Subject as Subject  \r\n";
        sql += " , StudyItemInformation.ConsentType as ConsentType  \r\n";
        sql += " , StudyItemInformation.Url as Url  \r\n";
        sql += " , StudyItemInformation.CreateDateTime as CreateDateTime  \r\n";
        sql += " , StudyItemInformation.UpdateDateTime as UpdateDateTime  \r\n";
        sql += " , StudyInformation.StudyName as StudyName  \r\n";
        sql += " , studydetailedinformation.CheckList As CheckList  \r\n";
        sql += " , StudyDetailedInformation.Status As Status \r\n";
        sql += " , StudyDetailedInformation.SortNo As SortNo \r\n";
        sql += " , StudyDetailedInformation.SeqId As SeqId \r\n";
        sql += " from StudyInformation  \r\n";
        sql += " inner join StudyItemInformation  \r\n";
        sql += " on Studyinformation.StudyId = StudyItemInformation.StudyId  \r\n";
        sql += " inner join Studydetailedinformation  \r\n";
        sql += " on StudyItemInformation.SubjectId = studydetailedinformation.SubjectId  \r\n";
        sql += " where studydetailedinformation.StudyId = ?  \r\n";
        sql += " and studydetailedinformation.SubjectId = ?  \r\n";
        sql += " and studydetailedinformation.Status = ?  \r\n";

        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);
        preparedStatement.setString(3, "1");
        List<StudyDetailedInformationEntity> entityList = new ArrayList<StudyDetailedInformationEntity>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            StudyDetailedInformationEntity entity = StudyDetailedInformationEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entityList;
    }
    
    /**
     * 研究項目情報の登録処理
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insertDetalied(StudyDetailedInformationEntity newEntityDetailed) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        if (newEntityDetailed.getCheckList() != null) {
            String[] checkLists= newEntityDetailed.getCheckList().split("\r\n|\r|\n");
                for (String checkList : checkLists) {
                    if (newEntityDetailed.getSeqId() == null ) {
                        preparedStatement.setNull(1, Types.VARCHAR );
                    } else {
                        preparedStatement.setString(1, numberingSeqId());
                    }
                    if (newEntityDetailed.getStudyId() == null ) {
                    preparedStatement.setNull(2, Types.VARCHAR );
                    } else {
                        preparedStatement.setString(2, newEntityDetailed.getStudyId());
                    }
                    if (newEntityDetailed.getSubjectId() == null ) {
                        preparedStatement.setNull(3, Types.VARCHAR );
                    } else {
                        preparedStatement.setString(3, newEntityDetailed.getSubjectId());
                    }
                    if (newEntityDetailed.getCheckList() == null ) {
                        preparedStatement.setNull(4, Types.VARCHAR );
                    } else {
                        preparedStatement.setString(4, checkList);
                    }
                    if (newEntityDetailed.getStatus() == null ) {
                        preparedStatement.setNull(5, Types.VARCHAR );
                    } else {
//                        preparedStatement.setString(5, newEntityDetailed.getStatus());
                        preparedStatement.setString(5, "0");
                    }
                    if (newEntityDetailed.getSortNo() == null ) {
                        preparedStatement.setNull(6, Types.VARCHAR );
                    } else {
                        preparedStatement.setString(6, String.valueOf(Arrays.asList(checkLists).indexOf(checkList) + 1));
                    }
                    preparedStatement.executeUpdate();
                }
                int rowCount = checkLists.length;
        } else {
            String[] checkLists= newEntityDetailed.getNewCheckList().split("\r\n|\r|\n");
            for (String checkList : checkLists) {
                if (newEntityDetailed.getSeqId() == null ) {
                    preparedStatement.setNull(1, Types.VARCHAR );
                } else {
                    preparedStatement.setString(1, numberingSeqId());
                }
                if (newEntityDetailed.getStudyId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
                } else {
                    preparedStatement.setString(2, newEntityDetailed.getStudyId());
                }
                if (newEntityDetailed.getSubjectId() == null ) {
                    preparedStatement.setNull(3, Types.VARCHAR );
                } else {
                    preparedStatement.setString(3, newEntityDetailed.getSubjectId());
                }
                if (newEntityDetailed.getNewCheckList() == null ) {
                    preparedStatement.setNull(4, Types.VARCHAR );
                } else {
                    preparedStatement.setString(4, checkList);
                }
                if (newEntityDetailed.getStatus() == null ) {
                    preparedStatement.setNull(5, Types.VARCHAR );
                } else {
                    preparedStatement.setString(5, newEntityDetailed.getStatus());
                }
                if (newEntityDetailed.getSortNo() == null ) {
                    preparedStatement.setNull(6, Types.VARCHAR );
                } else {
                    preparedStatement.setString(6, String.valueOf(Arrays.asList(checkLists).indexOf(checkList) + 1));
                }
                preparedStatement.executeUpdate();
            }
            int rowCount = checkLists.length;
        }
        int rowCount = 1;
            
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
    
    /**
     * 研究詳細項目情報のstautsを2に更新
     * @param entity
     * @return
     * @throws Throwable
     */
    public void updateStatus(StudyDetailedInformationEntity detailed) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
//        String sql = getUpdateSql();
        String sql = new String();
        sql += "update StudyDetailedInformation set \r\n";
        sql += "Status = ?,  UpdateDateTime = CURRENT_TIMESTAMP \r\n";
        sql += "where StudyId = ? AND SubjectId = ? \r\n";
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        preparedStatement.setString(1, "2");
        preparedStatement.setString(2, detailed.getStudyId());
        preparedStatement.setString(3, detailed.getSubjectId());
        preparedStatement.executeUpdate();
           
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
    }
    
    /**
     * 研究詳細項目情報のstautsが0のものを1に更新
     * @param entity
     * @return
     * @throws Throwable
     */
    public void updateStatus(String studyId, String subjectId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
//        String sql = getUpdateSql();
        String sql = new String();
        sql += "update StudyDetailedInformation set \r\n";
        sql += "Status = ?,  UpdateDateTime = CURRENT_TIMESTAMP \r\n";
        sql += "where StudyId = ? \r\n";
        sql += "and SubjectId = ? \r\n";
        sql += "and Status = ? \r\n";
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        preparedStatement.setString(1, "1");
        preparedStatement.setString(2, studyId);
        preparedStatement.setString(3, subjectId);
        preparedStatement.setString(4, "0");
        preparedStatement.executeUpdate();
           
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
    }
    
    /**
     * 研究詳細項目情報のstautsが1のものを2に更新
     * @param entity
     * @return
     * @throws Throwable
     */
    public void updateStatusOld(String studyId, String subjectId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
//        String sql = getUpdateSql();
        String sql = new String();
        sql += "update StudyDetailedInformation set \r\n";
        sql += "Status = ?,  UpdateDateTime = CURRENT_TIMESTAMP \r\n";
        sql += "where StudyId = ? \r\n";
        sql += "and SubjectId = ? \r\n";
        sql += "and Status = ? \r\n";
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        preparedStatement.setString(1, "2");
        preparedStatement.setString(2, studyId);
        preparedStatement.setString(3, subjectId);
        preparedStatement.setString(4, "1");
        preparedStatement.executeUpdate();
           
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
    }
    /**
     * 研究詳細項目情報の削除処理
     * @param entity
     * @return
     * @throws Throwable
     */
    public void deleteDetalied(StudyDetailedInformationEntity detailed) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
//        String sql = getUpdateSql();
        String sql = new String();
        sql += "delete from  StudyDetailedInformation \r\n";
        sql += "where StudyId = ? AND SubjectId = ? \r\n";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        preparedStatement.setString(1, detailed.getStudyId());
        preparedStatement.setString(2, detailed.getSubjectId());
        preparedStatement.executeUpdate();
           
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
    }
    
    /**
     * SeqIdを採番する
     *
     * @return
     * @throws Throwable
     */
    public static String numberingSeqId() throws Throwable {
        String Id = "0000000000";
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            String sql = "update Seq_Id set Seq_Id=LAST_INSERT_ID(Seq_Id+1)";
            dao.setSql(sql);

            dao.clearBindParam();
            PreparedStatement preparedStatement = dao.getPreparedStatement();
            int rowCount = preparedStatement.executeUpdate();
            String sql2 = "SELECT LAST_INSERT_ID() as SeqId";
            dao.setSql(sql2);
            dao.clearBindParam();
            preparedStatement.close();
            
            PreparedStatement preparedStatement2 = dao.getPreparedStatement();
            ResultSet dataTable = preparedStatement2.executeQuery();
            if (dataTable == null) {
                return null;
            }

            while (dataTable.next()) {
                long id = AbstractEntity.getLong(dataTable, "SeqId");
                Id = String.format("%010d", id);
            }
            dao.clearBindParam();
            dataTable.close();
            preparedStatement2.close();
            dao.commitTransaction();
            return Id;
        } catch (Throwable ex) {
            if (dao != null) {
                dao.rollbackTransaction();
            }
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            logger.trace("End");
        }
    }
}
