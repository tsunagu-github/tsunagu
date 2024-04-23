/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：コミュニケーション情報のデータオブジェクト
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

import phr.datadomain.entity.CommunicationEntity;

/**
 * コミュニケーション情報のデータオブジェクトです。
 */
public abstract class CommunicationAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public CommunicationAdapterBase(Connection conn)
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
        sql.append("    Communication.CommunicationId As CommunicationId  \r\n");
        sql.append("    , Communication.CommunicationTypeCd As CommunicationTypeCd  \r\n");
        sql.append("    , Communication.SendPHRID As SendPHRID  \r\n");
        sql.append("    , Communication.SendInsurerNo As SendInsurerNo  \r\n");
        sql.append("    , Communication.SendAccountId As SendAccountId  \r\n");
        sql.append("    , Communication.SendMedicalOrganizationCd As SendMedicalOrganizationCd  \r\n");
        sql.append("    , Communication.SenderName As SenderName  \r\n");
        sql.append("    , Communication.Subject As Subject  \r\n");
        sql.append("    , Communication.BodyText As BodyText  \r\n");
        sql.append("    , Communication.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , Communication.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from Communication \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into Communication \r\n");
        sql.append("(CommunicationId, CommunicationTypeCd, SendPHRID, SendInsurerNo, SendAccountId, SendMedicalOrganizationCd, SenderName, Subject, BodyText, CreateDateTime, UpdateDateTime) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update Communication set \r\n");
        sql.append("CommunicationTypeCd = ?, SendPHRID = ?, SendInsurerNo = ?, SendAccountId = ?, SendMedicalOrganizationCd = ?, SenderName = ?, Subject = ?, BodyText = ?, UpdateDateTime = CURRENT_TIMESTAMP \r\n");
        sql.append("where CommunicationId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  Communication \r\n");
        sql.append("where CommunicationId = ? \r\n");
        sql.append("and UpdateDateTime = ? \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(CommunicationEntity entity) throws Throwable  
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
            preparedStatement.setInt(2, entity.getCommunicationTypeCd());
            if (entity.getSendPHRID() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getSendPHRID());
            }
            if (entity.getSendInsurerNo() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getSendInsurerNo());
            }
            if (entity.getSendAccountId() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getSendAccountId());
            }
            if (entity.getSendMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getSendMedicalOrganizationCd());
            }
            if (entity.getSenderName() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getSenderName());
            }
            if (entity.getSubject() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getSubject());
            }
            if (entity.getBodyText() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getBodyText());
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
    public int update(CommunicationEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setInt(1, entity.getCommunicationTypeCd());
            if (entity.getSendPHRID() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getSendPHRID());
            }
            if (entity.getSendInsurerNo() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getSendInsurerNo());
            }
            if (entity.getSendAccountId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getSendAccountId());
            }
            if (entity.getSendMedicalOrganizationCd() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getSendMedicalOrganizationCd());
            }
            if (entity.getSenderName() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getSenderName());
            }
            if (entity.getSubject() == null ) {
                preparedStatement.setNull(7, Types.VARCHAR );
            } else {
                preparedStatement.setString(7, entity.getSubject());
            }
            if (entity.getBodyText() == null ) {
                preparedStatement.setNull(8, Types.VARCHAR );
            } else {
                preparedStatement.setString(8, entity.getBodyText());
            }
            if (entity.getCommunicationId() == null ) {
                preparedStatement.setNull(9, Types.VARCHAR );
            } else {
                preparedStatement.setString(9, entity.getCommunicationId());
            }
            preparedStatement.setTimestamp(10, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてコミュニケーション情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(CommunicationEntity entity) throws Throwable 
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
            preparedStatement.setTimestamp(2, entity.getUpdateDateTime());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにてコミュニケーション情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public CommunicationEntity findByPrimaryKey(String key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where Communication.CommunicationId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

        CommunicationEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = CommunicationEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
