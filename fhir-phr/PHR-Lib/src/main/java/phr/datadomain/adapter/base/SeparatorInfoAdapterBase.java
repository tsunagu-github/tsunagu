/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：用法補足情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/10/17
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

import phr.datadomain.entity.SeparatorInfoEntity;

/**
 * 用法補足情報のデータオブジェクトです。
 */
public abstract class SeparatorInfoAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SeparatorInfoAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public SeparatorInfoAdapterBase(Connection conn)
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
        sql.append("    SeparatorInfo.SeparatorId As SeparatorId  \r\n");
        sql.append("    , SeparatorInfo.DosageId As DosageId  \r\n");
        sql.append("    , SeparatorInfo.SeparateNo As SeparateNo  \r\n");
        sql.append("    , SeparatorInfo.SeparateCount As SeparateCount  \r\n");
        sql.append("    , SeparatorInfo.SeparatText As SeparatText  \r\n");
        sql.append("from SeparatorInfo \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into SeparatorInfo \r\n");
        sql.append("(SeparatorId, DosageId, SeparateNo, SeparateCount, SeparatText) \r\n");
        sql.append("values (?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update SeparatorInfo set \r\n");
        sql.append("SeparateCount = ?, SeparatText = ? \r\n");
        sql.append("where SeparatorId = ? AND DosageId = ? AND SeparateNo = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  SeparatorInfo \r\n");
        sql.append("where SeparatorId = ? AND DosageId = ? AND SeparateNo = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(SeparatorInfoEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            preparedStatement.setLong(1, entity.getSeparatorId());
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getDosageId());
            }
            preparedStatement.setInt(3, entity.getSeparateNo());
            preparedStatement.setInt(4, entity.getSeparateCount());
            if (entity.getSeparatText() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getSeparatText());
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
    public int update(SeparatorInfoEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setInt(1, entity.getSeparateCount());
            if (entity.getSeparatText() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getSeparatText());
            }

            preparedStatement.setLong(3, entity.getSeparatorId());
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getDosageId());
            }
            preparedStatement.setInt(5, entity.getSeparateNo());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて用法補足情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(SeparatorInfoEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setLong(1, entity.getSeparatorId());
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getDosageId());
            }
            preparedStatement.setInt(3, entity.getSeparateNo());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて用法補足情報を検索します。
     * @param key1
     * @param key2
     * @param key3
     * @return
     * @throws Throwable
     */
    public SeparatorInfoEntity findByPrimaryKey(long key1, String key2, int key3) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where SeparatorInfo.SeparatorId = ?";
        sql += "   and SeparatorInfo.DosageId = ?";
        sql += "   and SeparatorInfo.SeparateNo = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setLong(1, key1);
        preparedStatement.setString(2, key2);
        preparedStatement.setInt(3, key3);

        SeparatorInfoEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = SeparatorInfoEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
