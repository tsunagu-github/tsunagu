/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：コミュニケーション受信者のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import static phr.datadomain.AbstractEntity.getInt;
import static phr.datadomain.AbstractEntity.getString;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;

/**
 * コミュニケーション受信者のデータオブジェクトです。
 */
public class CommunicationReceiverAdapter extends CommunicationReceiverAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationReceiverAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     *
     * @param conn
     */
    public CommunicationReceiverAdapter(Connection conn) {
        super(conn);
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * CommunicationIdをキーにおしらせを削除します。
     *
     * @param entity
     * @return
     * @throws Throwable
     */
    public int deleteByCommunicationId(CommunicationReceiverEntity entity) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  CommunicationReceiver \r\n");
        sb.append("where CommunicationId = ? \r\n");
        String sql = sb.toString();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        if (entity.getCommunicationId() == null) {
            preparedStatement.setNull(1, Types.VARCHAR);
        } else {
            preparedStatement.setString(1, entity.getCommunicationId());
        }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * CommunicationIdと医療機関CdからReceiverを取得する
     *
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public CommunicationReceiverEntity findById(String key1, String key2) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where CommunicationReceiver.CommunicationId = ?";
        sql += "   and CommunicationReceiver.MedicalOrganizationCd = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);

        CommunicationReceiverEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        while (dataTable.next()) {
            entity = CommunicationReceiverEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }

    /**
     * CommunicationIdとinsurerNoからReceiverを取得する
     *
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public CommunicationReceiverEntity findByIdInsurer(String key1, String key2) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where CommunicationReceiver.CommunicationId = ?";
        sql += "   and CommunicationReceiver.InsurerNo = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);

        CommunicationReceiverEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        while (dataTable.next()) {
            entity = CommunicationReceiverEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }

    /**
     * CommunicationIdからReceiverを取得する
     *
     * @param key1
     * @return
     * @throws Throwable
     */
    public List<CommunicationReceiverEntity> findMedInfo(String key1) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select  \r\n");
        sb.append("    CommunicationReceiver.CommunicationId As CommunicationId  \r\n");
        sb.append("    , CommunicationReceiver.Seq As Seq  \r\n");
        sb.append("    , CommunicationReceiver.ReadFlg As ReadFlg  \r\n");
        sb.append("    , CommunicationReceiver.PHR_ID As PHR_ID  \r\n");
        sb.append("    , CommunicationReceiver.InsurerNo As InsurerNo  \r\n");
        sb.append("    , CommunicationReceiver.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sb.append("    , CommunicationReceiver.CreateDateTime As CreateDateTime  \r\n");
        sb.append("    , CommunicationReceiver.UpdateDateTime As UpdateDateTime  \r\n");
        sb.append("    , MedicalOrganization.MedicalOrganizationName As MedicalOrganizationName  \r\n");
        sb.append("from CommunicationReceiver, MedicalOrganization \r\n");
        sb.append("where CommunicationReceiver.MedicalOrganizationCd = MedicalOrganization.MedicalOrganizationCd \r\n");
        sb.append("and CommunicationReceiver.Seq <> 0 \r\n");
        String sql = sb.toString();
        sql += " and CommunicationReceiver.CommunicationId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

        CommunicationReceiverEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        List<CommunicationReceiverEntity> ret = new ArrayList<>();
        while (dataTable.next()) {
            entity = CommunicationReceiverEntity.setData(dataTable);
            entity.setMedicalOrganizationName(getString(dataTable, "MedicalOrganizationName"));
            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return ret;
    }

    /**
     * CommunicationIdとPHR_IDからReceiverを取得する
     *
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public CommunicationReceiverEntity findByIdPhrid(String key1, String key2) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where CommunicationReceiver.CommunicationId = ?";
        sql += "   and CommunicationReceiver.PHR_ID = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key2);

        CommunicationReceiverEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        while (dataTable.next()) {
            entity = CommunicationReceiverEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }

    /**
     * 未読件数を取得する
     * @param phrId
     * @return
     * @throws SQLException 
     */
    public int countUnreadMessage(String phrId) throws SQLException {
        logger.trace("Start");
        int count = 0;
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "select count(*) as Count ";
        sql += "from CommunicationReceiver ";
        sql += "where CommunicationReceiver.PHR_ID = ? ";
        sql += "  and CommunicationReceiver.ReadFlg = 0 ";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);

        CommunicationReceiverEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return count;
        }

        while (dataTable.next()) {
            count = AbstractEntity.getInt(dataTable, "Count");
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return count;
    }

    /**
     * PHR_IDでCommunicationRecieverテーブルからレコードを削除します。
     * @param phr_id
     * @return rowCount
     * @throws Throwable
     */
    public int deleteCommunicationReceiverRecord(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  CommunicationReceiver \r\n");
        sb.append("where CommunicationReceiver.PHR_ID = ? \r\n");
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
}
