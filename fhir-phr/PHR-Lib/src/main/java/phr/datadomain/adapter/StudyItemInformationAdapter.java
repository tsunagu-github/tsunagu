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
package phr.datadomain.adapter;

import static phr.datadomain.AbstractEntity.getBoolean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.StudyItemInformationEntity;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.StudyInformationEntity;
import phr.datadomain.entity.StudyItemInformationEntity;

/**
 * 研究項目情報のデータオブジェクトです。
 */
@Repository
public class StudyItemInformationAdapter extends StudyItemInformationAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(StudyItemInformationAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public StudyItemInformationAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * 研究項目情報一覧を取得する
     * @return
     * @throws Throwable
     */
    public List<StudyItemInformationEntity> getAllAccountList() throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
//        String sql = getSelectedSql();
        sql +="select  \r\n";
        sql +="    StudyItemInformation.StudyId As StudyId  \r\n";
        sql +="    , StudyItemInformation.SubjectId As SubjectId  \r\n";
        sql +="    , StudyItemInformation.Subject As Subject  \r\n";
        sql +="    , StudyItemInformation.ConsentType As ConsentType  \r\n";
        sql +="    , StudyItemInformation.Url As Url  \r\n";
        sql +="    , StudyItemInformation.CreateDateTime As CreateDateTime  \r\n";
        sql +="    , StudyItemInformation.UpdateDateTime As UpdateDateTime  \r\n";
        sql +="    , Studyinformation.StudyName As StudyName  \r\n";
        sql +="from StudyItemInformation \r\n";
        sql +="inner join Studyinformation \r\n";
        sql +="on StudyItemInformation.StudyId = Studyinformation.StudyId \r\n";
        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        List<StudyItemInformationEntity> entityList = new ArrayList<StudyItemInformationEntity>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
//        while( dataTable.next() ) {
//            list = StudyItemInformationEntity.setData(dataTable);
//        }
        while( dataTable.next() ) {
            StudyItemInformationEntity entity = StudyItemInformationEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entityList;
    }
    
    /**
     * 研究IDにて研究項目情報の有効なレコードのリストを取得
     * @param studyId
     * @param subjectId
     * @return entity
     * @throws Throwable
     */
    public StudyItemInformationEntity findByStudyId(String studyId, String subjectId) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
        sql += "select";
        sql += " StudyInformation.StudyId as StudyId";
        sql += " , StudyItemInformation.SubjectId as SubjectId";
        sql += " , StudyInformation.StudyName as StudyName";
        sql += " , StudyItemInformation.Subject as Subject";
        sql += " , StudyItemInformation.ConsentType as ConsentType";
        sql += " , StudyItemInformation.Url as Url";
        sql += " , StudyItemInformation.CreateDateTime as CreateDateTime";
        sql += " , StudyItemInformation.UpdateDateTime as UpdateDateTime";
        sql += " from StudyInformation";
        sql += " inner join StudyItemInformation";
        sql += " on StudyInformation.StudyId = StudyItemInformation.StudyId";
        sql += " where StudyInformation.StudyId = ?";
        sql += " and StudyItemInformation.SubjectId = ?";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, studyId);
        preparedStatement.setString(2, subjectId);
        
        StudyItemInformationEntity entity = new StudyItemInformationEntity();
        
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
    
    /**
     * 主キーにてアカウント情報を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public StudyItemInformationEntity findByPrimaryKey(String key1, String key2) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
        sql += "select";
        sql += " StudyInformation.StudyId as StudyId";
        sql += " , StudyItemInformation.SubjectId as SubjectId";
        sql += " , StudyInformation.StudyName as StudyName";
        sql += " , StudyItemInformation.Subject as Subject";
        sql += " , StudyItemInformation.ConsentType as ConsentType";
        sql += " , StudyItemInformation.Url as Url";
        sql += " , StudyItemInformation.CreateDateTime as CreateDateTime";
        sql += " , StudyItemInformation.UpdateDateTime as UpdateDateTime";
        sql += " from StudyInformation";
        sql += " inner join StudyItemInformation";
        sql += " on StudyInformation.StudyId = StudyItemInformation.StudyId";
        sql += " where StudyInformation.StudyId = ?";
        sql += " and StudyItemInformation.SubjectId = ?";

        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);
        StudyItemInformationEntity entity = new StudyItemInformationEntity();
        
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
    
    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int updateItem(StudyItemInformationEntity entity) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
        sql += "update StudyItemInformation set \r\n";
        sql += "Url = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n";
        sql += "where StudyId = ? AND SubjectId = ? \r\n";
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (entity.getSubject() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getUrl());
            }
            if (entity.getConsentType() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getStudyId());
            }
            if (entity.getUrl() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getSubjectId());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
    
    /**
     * オブジェクトの値にて研究項目情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int deleteItem(StudyItemInformationEntity entity) throws Throwable {
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
                preparedStatement.setString(2, entity.getSubjectId());
            }
            
        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        
        logger.debug("End");
        return rowCount;
    }
    
    /**
     * 項目IDを採番する
     *
     * @return
     * @throws Throwable
     */
    public static String numberingSubjectId() throws Throwable {
        String subjectId = "0000000000";
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            dao.beginTransaction();
            String sql = "update Seq_SubjectId set SubjectId=LAST_INSERT_ID(SubjectId+1)";
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
                subjectId = String.format("%010d", id);
            }
            dao.clearBindParam();
            dataTable.close();
            preparedStatement2.close();
            dao.commitTransaction();
            return subjectId;
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
