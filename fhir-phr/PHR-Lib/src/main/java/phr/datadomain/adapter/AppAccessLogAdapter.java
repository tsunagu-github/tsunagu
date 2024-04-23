/********************************************************************************
 * システム名      ：MInCS for ePRO
 * コンポーネント名：Appアクセスログのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2022/05/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

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

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.AnswerStatusEntity;
import phr.datadomain.entity.AppAccessLogEntity;

/**
 * Appアクセスログのデータオブジェクトです。
 */
@Repository
public class AppAccessLogAdapter extends AppAccessLogAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static Logger logger = Logger.getLogger(AppAccessLogAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public AppAccessLogAdapter(Connection conn)
    {
        super(conn);
    }
    /* --
    /* -------------------------------------------------------------------------------------- */

    /**
     * Appアクセスログテーブルから最大Seq_IDを持つレコードを取得する
     * @return entity
     */
    public AppAccessLogEntity searchRecord() throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " order by AppAccessLog.Seq_Id desc limit 1";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        AppAccessLogEntity entity = new AppAccessLogEntity();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            entity = AppAccessLogEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entity;
    }

    /**
     * アプリ起動時にサーバに存在確認のリクエストを投げる
     * @param entity
     * @return rowCount
     */
    public int insertRecord(AppAccessLogEntity entity) throws Throwable {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject();
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        if (entity.getSeq_Id() < 0) {
            preparedStatement.setNull(1, Types.INTEGER );
        } else {
            preparedStatement.setInt(1, entity.getSeq_Id());
        }
        if (entity.getPHR_ID() == null) {
            preparedStatement.setNull(2, Types.VARCHAR );
        } else {
            preparedStatement.setString(2, entity.getPHR_ID());
        }
        if (entity.getAccessDateTime() == null) {
            preparedStatement.setNull(3, Types.VARCHAR );
        } else {
            preparedStatement.setString(3, entity.getAccessDateTime().toString());
        }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        
        logger.debug("End");
        return rowCount;
    }

    /**
     * Appアクセスログテーブルから該当利用者のアクセス日時が最新のレコードを取得
     * @param phr_id
     * @return entity
     */
    public AppAccessLogEntity findByPhrId(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where AppAccessLog.PHR_ID = ? ";
        sql += " order by AppAccessLog.AccessDateTime desc limit 1";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phr_id);

        AppAccessLogEntity entity = new AppAccessLogEntity();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        while( dataTable.next() ) {
            entity = AppAccessLogEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return entity;
    }
}
