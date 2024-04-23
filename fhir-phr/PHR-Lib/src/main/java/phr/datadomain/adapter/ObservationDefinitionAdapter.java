/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
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
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationDefinitionEntity;

/**
 * 管理項目情報のデータオブジェクトです。
 */
public class ObservationDefinitionAdapter extends ObservationDefinitionAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

           /**
       * 年度に該当する項目を取得します。
       * @param key1
       * @param key1
       * @param key1
       * @return
       * @throws Throwable
       */
      public List<ObservationDefinitionEntity> findByYear(int year) throws Throwable 
      {
          logger.trace("Start");
          DataAccessObject dao = new DataAccessObject(connection);
          String sql = getSelectedSql();
          sql += " where ObservationDefinition.Year = ?";

          dao.setSql(sql);

          dao.clearBindParam();
          PreparedStatement preparedStatement = dao.getPreparedStatement();
          preparedStatement.setInt(1, year);

          List<ObservationDefinitionEntity> entityList = new ArrayList<ObservationDefinitionEntity>();

          ResultSet dataTable = preparedStatement.executeQuery();
          if (dataTable == null)
          {
              return null;
          }

          while( dataTable.next() ) {
              entityList.add(ObservationDefinitionEntity.setData(dataTable));
          }
          dao.clearBindParam();
          dataTable.close();
          preparedStatement.close();
          logger.trace("End");
          return entityList;
      }
      
      public List<ObservationDefinitionEntity> getEntityList(List<String> idList)throws Throwable{
          logger.trace("Start");
          DataAccessObject dao = new DataAccessObject(connection);
            StringBuffer questions = new StringBuffer();
            questions.append("?");
            for ( int count = 1; count <  idList.size(); count++){
                questions.append(",?");
            }
          String sql = getSelectedSql();
          sql += " where ObservationDefinition.ObservationDefinitionId in (" + questions.toString() + ")";

          dao.setSql(sql);

          dao.clearBindParam();
          PreparedStatement preparedStatement = dao.getPreparedStatement();
          
          for(int count = 0; count <  idList.size(); count++){
            preparedStatement.setString(count + 1, idList.get(count));
          }
          
          List<ObservationDefinitionEntity> entityList = new ArrayList<ObservationDefinitionEntity>();

          ResultSet dataTable = preparedStatement.executeQuery();
          if (dataTable == null)
          {
              return null;
          }

          while( dataTable.next() ) {
              entityList.add(ObservationDefinitionEntity.setData(dataTable));
          }
          dao.clearBindParam();
          dataTable.close();
          preparedStatement.close();
          logger.trace("End");
          return entityList;
      }
} 
