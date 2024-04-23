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
import phr.datadomain.entity.UtilizationConsentEntity;

/**
 * 活用同意一覧情報のデータオブジェクトです。
 */
@Repository
public class UtilizationConsentAdapter extends UtilizationConsentAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(UtilizationConsentAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public UtilizationConsentAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * PHR_IDにて活用同意一覧情報の有効なレコードのリストを取得（研究ID昇順）
     * @param phr_id
     * @return list
     * @throws Throwable
     */
    public List<UtilizationConsentEntity> findByPhrIdSortStudyId(String phr_id) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where UtilizationConsent.PHR_ID = ?";
        sql += " and UtilizationConsent.Invalid = false";
        sql += " order by UtilizationConsent.StudyId";
        sql += " , UtilizationConsent.SubjectId";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);
        
        List<UtilizationConsentEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(UtilizationConsentEntity.setDataList(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }

    /**
     * PHR_IDにて活用同意一覧情報の有効なレコードのリストを取得（研究ID降順）
     * @param phr_id
     * @return list
     * @throws Throwable
     */
    public List<UtilizationConsentEntity> findByPhrIdSortStudyIdDesc(String phr_id) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where UtilizationConsent.PHR_ID = ?";
        sql += " and UtilizationConsent.Invalid = false";
        sql += " order by UtilizationConsent.StudyId desc";
        sql += " , UtilizationConsent.SubjectId desc";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);
        
        List<UtilizationConsentEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(UtilizationConsentEntity.setDataList(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }

    /**
     * PHR_IDにて活用同意一覧情報の有効なレコードのリストを取得（通知日昇順）
     * @param phr_id
     * @return list
     * @throws Throwable
     */
    public List<UtilizationConsentEntity> findByPhrIdSortNotification(String phr_id) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where UtilizationConsent.PHR_ID = ?";
        sql += " and UtilizationConsent.Invalid = false";
        sql += " order by UtilizationConsent.NotificationDate";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);
        
        List<UtilizationConsentEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(UtilizationConsentEntity.setDataList(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }

    /**
     * PHR_IDにて活用同意一覧情報の有効なレコードのリストを取得（通知日降順）
     * @param phr_id
     * @return list
     * @throws Throwable
     */
    public List<UtilizationConsentEntity> findByPhrIdSortNotificationDesc(String phr_id) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where UtilizationConsent.PHR_ID = ?";
        sql += " and UtilizationConsent.Invalid = false";
        sql += " order by UtilizationConsent.NotificationDate desc";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);
        
        List<UtilizationConsentEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(UtilizationConsentEntity.setDataList(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }

    /**
     * PHR_IDにて活用同意一覧情報の有効なレコードのリストを取得（回答更新日昇順）
     * @param phr_id
     * @return list
     * @throws Throwable
     */
    public List<UtilizationConsentEntity> findByPhrIdSortUpdate(String phr_id) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where UtilizationConsent.PHR_ID = ?";
        sql += " and UtilizationConsent.Invalid = false";
//        sql += " and UtilizationConsent.ResponseDate IS NOT NULL";
        sql += " order by UtilizationConsent.ResponseDate";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);
        
        List<UtilizationConsentEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(UtilizationConsentEntity.setDataList(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }

    /**
     * PHR_IDにて活用同意一覧情報の有効なレコードのリストを取得（回答更新日降順）
     * @param phr_id
     * @return list
     * @throws Throwable
     */
    public List<UtilizationConsentEntity> findByPhrIdSortUpdateDesc(String phr_id) throws Throwable { 
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where UtilizationConsent.PHR_ID = ?";
        sql += " and UtilizationConsent.Invalid = false";
//        sql += " and UtilizationConsent.ResponseDate IS NOT NULL";
        sql += " order by UtilizationConsent.ResponseDate desc";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);
        
        List<UtilizationConsentEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(UtilizationConsentEntity.setDataList(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }

    /**
     * 活用同意一覧テーブルを更新する
     * @param phr_id
     * @param studyId
     * @param subjectId
     * @param responseStatus
     * @return rowCount
     */
    public int updateRecord(String phr_id, String studyId, String responseStatus, String subjectId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
        sql += " update UtilizationConsent set";
        sql += " UtilizationConsent.ResponseStatus = ?,";
        sql += " UtilizationConsent.NewArrivalFlg = false,";
        sql += " UtilizationConsent.ResponseDate = CURRENT_TIMESTAMP,";
        sql += " UtilizationConsent.UpdateDateTime = CURRENT_TIMESTAMP";
        sql += " where UtilizationConsent.PHR_ID = ?";
        sql += " and UtilizationConsent.StudyId = ?";
        sql += " and UtilizationConsent.SubjectId = ?";
        
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
            if (responseStatus == null ) {
            preparedStatement.setNull(1, Types.VARCHAR );
            } else {
            preparedStatement.setString(1, responseStatus);
            }
            if (phr_id == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, phr_id);
            }
            if (studyId == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, studyId);
            }
            if (subjectId == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, subjectId);
            }
            
        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }

    /**
     * PHR_IDでUtilizationConsentテーブルからレコードを削除します。
     * @param phr_id
     * @return rowCount
     * @throws Throwable
     */
    public int deleteUtilizationConsentRecord(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  UtilizationConsent \r\n");
        sb.append("where UtilizationConsent.PHR_ID = ? \r\n");
        String sql = sb.toString();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, phr_id);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
    
    /**
     * 活用同意一覧テーブルのレコードのリストを取得
     * @param phr_id
     * @return list
     * @throws Throwable
     */
    public List<UtilizationConsentEntity> findByStudyNameResponseStatus(String studyName, List<String> responseStatusList) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
//        String sql = getSelectedSql();
        sb.append("  select \r\n");
        sb.append("    UtilizationConsent.UtilizationConsentId As UtilizationConsentId \r\n");
        sb.append("    , UtilizationConsent.PHR_ID As PHR_ID \r\n");
        sb.append("    , UtilizationConsent.StudyId As StudyId \r\n");
        sb.append("    , UtilizationConsent.SubjectId As SubjectId \r\n");
        sb.append("    , UtilizationConsent.ConsentType As ConsentType \r\n");
        sb.append("    , UtilizationConsent.ResponseStatus As ResponseStatus \r\n");
        sb.append("    , UtilizationConsent.NewArrivalFlg As NewArrivalFlg \r\n");
        sb.append("    , UtilizationConsent.Invalid As Invalid \r\n");
        sb.append("    , UtilizationConsent.NotificationDate As NotificationDate \r\n");
        sb.append("    , UtilizationConsent.ResponseDate As ResponseDate \r\n");
        sb.append("    , UtilizationConsent.CreateDateTime As CreateDateTime \r\n");
        sb.append("    , UtilizationConsent.UpdateDateTime As UpdateDateTime \r\n");
        sb.append("    , Patient.FamilyName As FamilyName \r\n");
        sb.append("    , Patient.GivenName As GivenName \r\n");
        sb.append("    , Patient.BirthDate As BirthDate \r\n");
        sb.append("    , Studyinformation.StudyName As StudyName \r\n");
        sb.append("    from UtilizationConsent  \r\n");
        sb.append("    inner join Patient \r\n");
        sb.append("    on  UtilizationConsent.PHR_ID = Patient.PHR_ID \r\n");
        sb.append("    inner join Studyinformation \r\n");
        sb.append("    on  UtilizationConsent.StudyId = Studyinformation.StudyId \r\n");
//        sb.append("    where UtilizationConsent.Invalid = '0' \r\n");
        sb.append("    where studyinformation.StudyName = ? \r\n");
        String sqls = sb.toString();
        if (!(responseStatusList == null || responseStatusList.size() == 0)) {
            if (responseStatusList.size() == 1) {
                sqls += " and UtilizationConsent.ResponseStatus = ? ";
            } else {
                sqls += "and UtilizationConsent.ResponseStatus in (?";
                for(int i = 1; i < responseStatusList.size(); i++) {
                    sqls += ", ?";
                }
                sqls += ")";
            }
        }
//        dao.setSql(sb.toString());
        dao.setSql(sqls);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
//        preparedStatement.setString(1, onePhrId);
        preparedStatement.setString(1, studyName);
        if (!(responseStatusList == null || responseStatusList.size() == 0)) {
            if (responseStatusList.size() == 1) {
                preparedStatement.setString(2, responseStatusList.get(0));
            } else {
                int x = 2;
                for(String str : responseStatusList) {
                    preparedStatement.setString(x++, str);
                }
            }
        }
        
        List<UtilizationConsentEntity> list = new ArrayList<>();
        
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            list.add(UtilizationConsentEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return list;
    }
    
    /**
     * 活用同意一覧テーブルの通知日を更新し、NewArrivalFlg,Invalidを0にする
     * @param phr_id
     * @param studyId
     * @param responseStatus
     * @return rowCount
     */
    public int updateRecord(String phr_id, String studyId, String SubjectId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
        sql += " update UtilizationConsent set";
        sql += " UtilizationConsent.NotificationDate = CURRENT_TIMESTAMP \r\n";
        sql += " , UtilizationConsent.UpdateDateTime = CURRENT_TIMESTAMP \r\n";
        sql += " , UtilizationConsent.NewArrivalFlg = '1' \r\n" ;
        sql += " , UtilizationConsent.Invalid = '0' \r\n" ;
        sql += " where UtilizationConsent.PHR_ID = ? \r\n";
        sql += " and UtilizationConsent.StudyId = ? \r\n";
        sql += " and UtilizationConsent.SubjectId = ? \r\n";
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        if (phr_id == null ) {
        preparedStatement.setNull(1, Types.VARCHAR );
        } else {
        preparedStatement.setString(1, phr_id);
        }
        if (studyId == null ) {
            preparedStatement.setNull(2, Types.VARCHAR );
        } else {
            preparedStatement.setString(2, studyId);
        }
        if (studyId == null ) {
            preparedStatement.setNull(3, Types.VARCHAR );
        } else {
            preparedStatement.setString(3, SubjectId);
        }
        
        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
}
