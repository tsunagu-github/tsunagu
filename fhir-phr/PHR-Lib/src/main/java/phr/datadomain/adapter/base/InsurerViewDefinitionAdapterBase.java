/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者View定義情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/16
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

import phr.datadomain.entity.InsurerViewDefinitionEntity;

/**
 * 保険者View定義情報のデータオブジェクトです。
 */
public abstract class InsurerViewDefinitionAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(InsurerViewDefinitionAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public InsurerViewDefinitionAdapterBase(Connection conn)
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
        sql.append("    InsurerViewDefinition.ViewId As ViewId  \r\n");
        sql.append("    , InsurerViewDefinition.InsurerNo As InsurerNo  \r\n");
        sql.append("    , InsurerViewDefinition.StartYear As StartYear  \r\n");
        sql.append("    , InsurerViewDefinition.EndYear As EndYear  \r\n");
        sql.append("    , InsurerViewDefinition.ViewName As ViewName  \r\n");
        sql.append("    , InsurerViewDefinition.ShortName As ShortName  \r\n");
        sql.append("from InsurerViewDefinition \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into InsurerViewDefinition \r\n");
        sql.append("(ViewId, InsurerNo, StartYear, EndYear, ViewName, ShortName) \r\n");
        sql.append("values (?, ?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update InsurerViewDefinition set \r\n");
        sql.append("InsurerNo = ?, StartYear = ?, EndYear = ?, ViewName = ?, ShortName = ? \r\n");
        sql.append("where ViewId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  InsurerViewDefinition \r\n");
        sql.append("where ViewId = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(InsurerViewDefinitionEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            preparedStatement.setInt(1, entity.getViewId());
            if (entity.getInsurerNo() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getInsurerNo());
            }
            preparedStatement.setInt(3, entity.getStartYear());
            preparedStatement.setInt(4, entity.getEndYear());
            if (entity.getViewName() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getViewName());
            }
            if (entity.getShortName() == null ) {
                preparedStatement.setNull(6, Types.VARCHAR );
            } else {
                preparedStatement.setString(6, entity.getShortName());
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
    public int update(InsurerViewDefinitionEntity entity) throws Throwable 
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
            preparedStatement.setInt(2, entity.getStartYear());
            preparedStatement.setInt(3, entity.getEndYear());
            if (entity.getViewName() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getViewName());
            }
            if (entity.getShortName() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getShortName());
            }
            preparedStatement.setInt(6, entity.getViewId());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて保険者View定義情報の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(InsurerViewDefinitionEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            preparedStatement.setInt(1, entity.getViewId());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて保険者View定義情報を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public InsurerViewDefinitionEntity findByPrimaryKey(int key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where InsurerViewDefinition.ViewId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, key1);

        InsurerViewDefinitionEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = InsurerViewDefinitionEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
