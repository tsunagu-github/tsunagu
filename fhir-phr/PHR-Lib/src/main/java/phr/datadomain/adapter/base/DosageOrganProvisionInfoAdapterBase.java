/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関等提供情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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

import phr.datadomain.entity.DosageOrganProvisionInfoEntity;

/**
 * 医療機関等提供情報のデータオブジェクトです。
 */
public abstract class DosageOrganProvisionInfoAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageOrganProvisionInfoAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageOrganProvisionInfoAdapterBase(Connection conn)
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
        sql.append("    DosageOrganProvisionInfo.DosageId As DosageId  \r\n");
        sql.append("    , DosageOrganProvisionInfo.Seq As Seq  \r\n");
        sql.append("    , DosageOrganProvisionInfo.ProvisionSeq As ProvisionSeq  \r\n");
        sql.append("    , DosageOrganProvisionInfo.ProvisionText As ProvisionText  \r\n");
        sql.append("    , DosageOrganProvisionInfo.ProvisionType As ProvisionType  \r\n");
        sql.append("    , DosageOrganProvisionInfo.RecordCreatorType As RecordCreatorType  \r\n");
        sql.append("from DosageOrganProvisionInfo \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into DosageOrganProvisionInfo \r\n");
        sql.append("(DosageId, Seq, ProvisionSeq, ProvisionText, ProvisionType, RecordCreatorType) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update DosageOrganProvisionInfo set \r\n");
        sql.append("ProvisionText = ?, ProvisionType = ?, RecordCreatorType = ? \r\n");
        sql.append("where DosageId = ? AND Seq = ? AND ProvisionSeq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  DosageOrganProvisionInfo \r\n");
        sql.append("where DosageId = ? AND Seq = ? AND ProvisionSeq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(DosageOrganProvisionInfoEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getDosageId());
            }
            preparedStatement.setInt(2, entity.getSeq());
            preparedStatement.setInt(3, entity.getProvisionSeq());
            if (entity.getProvisionText() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getProvisionText());
            }
            if (entity.getProvisionType() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getProvisionType());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getRecordCreatorType());
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
    public int update(DosageOrganProvisionInfoEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getProvisionText() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getProvisionText());
            }
            if (entity.getProvisionType() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getProvisionType());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getRecordCreatorType());
            }
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getDosageId());
            }
            preparedStatement.setInt(5, entity.getSeq());
            preparedStatement.setInt(6, entity.getProvisionSeq());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて医療機関等提供情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(DosageOrganProvisionInfoEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getDosageId());
            }
            preparedStatement.setInt(2, entity.getSeq());
            preparedStatement.setInt(3, entity.getProvisionSeq());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて医療機関等提供情報を検索します。
     * @param key1
     * @param key2
     * @param key3
     * @return
     * @throws Throwable
     */
    public DosageOrganProvisionInfoEntity findByPrimaryKey(String key1, int key2, int key3) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DosageOrganProvisionInfo.DosageId = ?";
        sql += "   and DosageOrganProvisionInfo.Seq = ?";
        sql += "   and DosageOrganProvisionInfo.ProvisionSeq = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);
        preparedStatement.setInt(3, key3);

        DosageOrganProvisionInfoEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = DosageOrganProvisionInfoEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
