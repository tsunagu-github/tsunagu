/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：コミュニケーション受信者のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter.base;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import java.sql.PreparedStatement;
import java.sql.Types;
import phr.datadomain.DataAccessObject;
import phr.utility.TypeUtility;

import phr.datadomain.entity.CommunicationReceiverEntity;

/**
 * コミュニケーション受信者のデータオブジェクトです。
 */
public abstract class CommunicationReceiverAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationReceiverAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public CommunicationReceiverAdapterBase(Connection conn)
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
        sql.append("    CommunicationReceiver.CommunicationId As CommunicationId  \r\n");
        sql.append("    , CommunicationReceiver.Seq As Seq  \r\n");
        sql.append("    , CommunicationReceiver.ReadFlg As ReadFlg  \r\n");
        sql.append("    , CommunicationReceiver.PHR_ID As PHR_ID  \r\n");
        sql.append("    , CommunicationReceiver.InsurerNo As InsurerNo  \r\n");
        sql.append("    , CommunicationReceiver.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , CommunicationReceiver.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , CommunicationReceiver.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from CommunicationReceiver \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into CommunicationReceiver \r\n");
        sql.append("(CommunicationId, Seq, ReadFlg, PHR_ID, InsurerNo, MedicalOrganizationCd, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update CommunicationReceiver set \r\n");
        sql.append("ReadFlg = ?, PHR_ID = ?, InsurerNo = ?, MedicalOrganizationCd = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where CommunicationId = ? AND Seq = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  CommunicationReceiver \r\n");
        sql.append("where CommunicationId = ? AND Seq = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(CommunicationReceiverEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getCommunicationId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getCommunicationId());
            }
            preparedStatement.setInt(2, entity.getSeq());
            preparedStatement.setBoolean(3, entity.isReadFlg());
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getPHR_ID());
            }
            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getInsurerNo());
            }
            if (entity.getMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getMedicalOrganizationCd());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();

        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(CommunicationReceiverEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setBoolean(1, entity.isReadFlg());
            if (entity.getPHR_ID() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getPHR_ID());
            }
            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getInsurerNo());
            }
            if (entity.getMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getMedicalOrganizationCd());
            }
            if (entity.getCommunicationId() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getCommunicationId());
            }
            preparedStatement.setInt(6, entity.getSeq());
            preparedStatement.setTimestamp(7, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてコミュニケーション受信者の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(CommunicationReceiverEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getCommunicationId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getCommunicationId());
            }
            preparedStatement.setInt(2, entity.getSeq());
            preparedStatement.setTimestamp(3, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてコミュニケーション受信者を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public CommunicationReceiverEntity findByPrimaryKey(String key1, int key2) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where CommunicationReceiver.CommunicationId = ?";
        sql += "   and CommunicationReceiver.Seq = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);

        CommunicationReceiverEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = CommunicationReceiverEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
