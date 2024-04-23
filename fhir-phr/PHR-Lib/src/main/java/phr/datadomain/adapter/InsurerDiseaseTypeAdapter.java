/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者管理項目疾病種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/19
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import static phr.datadomain.AbstractEntity.getInt;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.InsurerDiseaseTypeEntity;

/**
 * 保険者管理項目疾病種別のデータオブジェクトです。
 */
public class InsurerDiseaseTypeAdapter extends InsurerDiseaseTypeAdapterBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(InsurerDiseaseTypeAdapter.class);
    private static Logger logger = Logger.getLogger(InsurerDiseaseTypeAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public InsurerDiseaseTypeAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    public List<InsurerDiseaseTypeEntity> findByViewId(int viewId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where InsurerDiseaseType.ViewId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);

        List<InsurerDiseaseTypeEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            list.add(InsurerDiseaseTypeEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }

    /**
     * ビューIDにて保険者管理項目疾病種別の情報を削除します。
     * @param viewId
     * @return
     * @throws Throwable
     */
    public int deleteByViewId(int viewId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = "delete from InsurerDiseaseType where ViewId = ?";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setInt(1, viewId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    public List<InsurerDiseaseTypeEntity> findDiseaseTypeListByViewIdList(List<Integer> viewIdList)  throws Throwable{
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
            StringBuffer questions = new StringBuffer();
            questions.append("?");
            for ( int count = 1; count <  viewIdList.size(); count++){
                questions.append(",?");
            }        
        
        String sql = getSelectedSql();
        sql = sql.replaceFirst("from InsurerDiseaseType \r\n", " , ObservationDefinitionType.DataInputTypeCd as DataInputTypeCd \r\n");
        sql += " from InsurerDiseaseType \r\n";
       sql += " inner join ObservationDefinitionType on ";
        sql += "     ObservationDefinitionType.ObservationDefinitionId = InsurerDiseaseType.ObservationDefinitionId ";
        
        sql += " where InsurerDiseaseType.ViewId in(" + questions.toString() + " )";
        sql += "  ORDER BY DiseaseTypeCd ASC";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
          for(int count = 0; count <  viewIdList.size(); count++){
            preparedStatement.setInt(count + 1, viewIdList.get(count));
          }        

        List<InsurerDiseaseTypeEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        InsurerDiseaseTypeEntity entity = null;
        while( dataTable.next() ) {
            entity = InsurerDiseaseTypeEntity.setData(dataTable);
            entity.setDataInputTypeCd(getInt(dataTable, "DataInputTypeCd"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }
    
    public List<InsurerDiseaseTypeEntity> findDiseaseTypeListByViewId(int viewId)  throws Throwable{
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);        
        String sql = getSelectedSql();
        sql = sql.replaceFirst("from InsurerDiseaseType \r\n", " , ObservationDefinitionType.DataInputTypeCd as DataInputTypeCd \r\n");
        sql += " from InsurerDiseaseType \r\n";
       sql += " inner join ObservationDefinitionType on ";
        sql += "     ObservationDefinitionType.ObservationDefinitionId = InsurerDiseaseType.ObservationDefinitionId ";
        
        sql += " where InsurerDiseaseType.ViewId = ?";
        sql += "  ORDER BY DiseaseTypeCd ASC";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);

        List<InsurerDiseaseTypeEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        InsurerDiseaseTypeEntity entity = null;
        while( dataTable.next() ) {
            entity = InsurerDiseaseTypeEntity.setData(dataTable);
            entity.setDataInputTypeCd(getInt(dataTable, "DataInputTypeCd"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }    
    
    /**
     * ObservationDefinitionIdにて保険者管理項目疾病種別を検索します。
     * @param key1
     * @return
     * @throws Throwable
     */
    public List<InsurerDiseaseTypeEntity> findByObservationDefinitionId(String key1) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += "   where InsurerDiseaseType.ObservationDefinitionId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);

        List<InsurerDiseaseTypeEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        InsurerDiseaseTypeEntity entity = null;
        while( dataTable.next() ) {
            entity = InsurerDiseaseTypeEntity.setData(dataTable);
            list.add(entity);
        }

        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }
}
