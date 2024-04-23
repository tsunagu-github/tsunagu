/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/07
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

import phr.datadomain.entity.ObservationDefinitionTypeEntity;

/**
 * 管理項目種別のデータオブジェクトです。
 */
public abstract class ObservationDefinitionTypeAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionTypeAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionTypeAdapterBase(Connection conn)
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
        sql.append("    ObservationDefinitionType.InsurerNo As InsurerNo  \r\n");
        sql.append("    , ObservationDefinitionType.ObservationDefinitionId As ObservationDefinitionId  \r\n");
        sql.append("    , ObservationDefinitionType.DataInputTypeCd As DataInputTypeCd  \r\n");
        sql.append("from ObservationDefinitionType \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ObservationDefinitionType \r\n");
        sql.append("(InsurerNo, ObservationDefinitionId, DataInputTypeCd) \r\n");
        sql.append("values (?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update ObservationDefinitionType set \r\n");
        sql.append(" \r\n");
        sql.append("where InsurerNo = ? AND ObservationDefinitionId = ? AND DataInputTypeCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  ObservationDefinitionType \r\n");
        sql.append("where InsurerNo = ? AND ObservationDefinitionId = ? AND DataInputTypeCd = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(ObservationDefinitionTypeEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getInsurerNo());
            }

            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getObservationDefinitionId());
            }
            preparedStatement.setInt(4, entity.getDataInputTypeCd());

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
    public int update(ObservationDefinitionTypeEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getInsurerNo());
            }

            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getObservationDefinitionId());
            }
            preparedStatement.setInt(4, entity.getDataInputTypeCd());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて管理項目種別の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(ObservationDefinitionTypeEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getInsurerNo());
            }

            if (entity.getObservationDefinitionId() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getObservationDefinitionId());
            }
            preparedStatement.setInt(4, entity.getDataInputTypeCd());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて管理項目種別を検索します。
     * @param key1
     * @param key2
     * @param key3
     * @param key4
     * @return
     * @throws Throwable
     */
    public ObservationDefinitionTypeEntity findByPrimaryKey(String key1, int key2, String key3, int key4) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationDefinitionType.InsurerNo = ?";
        sql += "   and ObservationDefinitionType.ObservationDefinitionId = ?";
        sql += "   and ObservationDefinitionType.DataInputTypeCd = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setString(2, key3);
        preparedStatement.setInt(3, key4);

        ObservationDefinitionTypeEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionTypeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
