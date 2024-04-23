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
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.StudyInformationEntity;

/**
 * 研究情報のデータオブジェクトです。
 */
@Repository
public class StudyInformationAdapter extends StudyInformationAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(StudyInformationAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public StudyInformationAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 研究IDにて研究情報の有効なレコードのリストを取得
     * @param studyId
     * @return entity
     * @throws Throwable
     */
    public StudyInformationEntity findByStudyId(String studyId) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where StudyInformation.StudyId = ?";
        sql += " and StudyInformation.Status = '1'";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, studyId);
        
        StudyInformationEntity entity = new StudyInformationEntity();
        
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

    /**
     * 研究情報の有効なレコードのリストを取得
     * @return list
     * @throws Throwable
     */
    public List<StudyInformationEntity> getStudyInformation() throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where StudyInformation.Status = '1'";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        List<StudyInformationEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(StudyInformationEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }

    /**
     * 研究情報レコードをすべて取得
     * @param studyId
     * @return entity
     * @throws Throwable
     */
    public List<StudyInformationEntity> findAll() throws Throwable { 
        logger.debug("Start");
        List<StudyInformationEntity> entityList = new ArrayList<>();
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        StudyInformationEntity entity = new StudyInformationEntity();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            entity = StudyInformationEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entityList;
    }
}
