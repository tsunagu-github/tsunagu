/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：疾病種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.DiseaseTypeEntity;

/**
 * 疾病種別のデータオブジェクトです。
 */
public class DiseaseTypeAdapter extends DiseaseTypeAdapterBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(DiseaseTypeAdapter.class);
    private static Logger logger = Logger.getLogger(DiseaseTypeAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DiseaseTypeAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
   
    /**
     * DiseaseTypeCdでソートして疾患リストを取得します
     * @return
     * @throws Throwable
     */
    public List<DiseaseTypeEntity> findTypeList(int viewId) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += "  where ViewId = ?";

        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);
        
        List<DiseaseTypeEntity> entitylist = new ArrayList<DiseaseTypeEntity>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entitylist.add(DiseaseTypeEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entitylist;
    }

    /**
     * DiseaseTypeCdでソートして疾患リストを取得します
     * @param viewId
     * @return
     * @throws Throwable
     */
    public List<DiseaseTypeEntity> findDiseaseTypeList(int viewId) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DiseaseType.ViewId = ?";
        sql += "  ORDER BY DiseaseTypeCd ASC";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, viewId);

        List<DiseaseTypeEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            list.add(DiseaseTypeEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return list;
    }

    /**
     * ObservationDefinitionIdを使用してレコードを取得します
     * @param observationDefinitionId
     * @return
     * @throws Throwable
     */
    public List<DiseaseTypeEntity> findByObservationDefinitionId(String observationDefinitionId) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where DiseaseType.ObservationDefinitionId = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationDefinitionId);

        List<DiseaseTypeEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            list.add(DiseaseTypeEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return list;
    }
}
