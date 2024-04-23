/********************************************************************************
 * システム名      ：phr
 * コンポーネント名：残薬確認情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/28
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.SeparatorInfoEntity;
import phr.datadomain.entity.UnusedDrugInfoEntity;

/**
 * 残薬確認情報のデータオブジェクトです。
 */
@Repository
public class UnusedDrugInfoAdapter extends UnusedDrugInfoAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Logger logger = LoggerFactory.getLogger(UnusedDrugInfoAdapter.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public UnusedDrugInfoAdapter() {
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public UnusedDrugInfoAdapter(Connection conn)
    {
        connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */
    
    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(UnusedDrugInfoEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into UnusedDrugInfo \r\n");
        sql.append("(DosageId, Seq, UnusedDrugs, RecordCreatorType) \r\n");
        sql.append("values (?, ?, ?, ?) \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setString(1, entity.getDosageId());
            preparedStatement.setLong(2, entity.getSeq());
            if (entity.getUnusedDrugs() != null) {
            	preparedStatement.setString(3, entity.getUnusedDrugs());
            }
            if (entity.getRecordCreatorType() != null) {
            	preparedStatement.setString(4, entity.getRecordCreatorType());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();

        logger.trace("End");
        return rowCount;
    }

    /**
     * dosageIdの値にて情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int deleteByDosageId(String dosageId) throws Throwable
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  UnusedDrugInfo \r\n");
        sql.append("where DosageId = ? \r\n");
        sql.append(" \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * DosageIdでUnusedDrugInfoテーブルからレコードを削除します。
     * @param dosageId
     * @return rowCount
     * @throws Throwable
     */
    public int deleteUnusedDrugInfoRecord(String dosageId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  UnusedDrugInfo \r\n");
        sb.append("where UnusedDrugInfo.DosageId = ? \r\n");
        String sql = sb.toString();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, dosageId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
}
