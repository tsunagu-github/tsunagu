/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：データ入力種別のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/12
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationDefinitionScriptEntity;

/**
 * データ入力種別のデータオブジェクトです。
 */
public class ObservationDefinitionScriptAdapter extends ObservationDefinitionScriptAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionScriptAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionScriptAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 
     * @param key1
     * @param key2
     * @param key3
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionScriptEntity> searchScriptList(int key1, String key2, List<Integer> key3) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append(getSelectedSql());
        sb.append(" where ObservationDefinitionScript.ViewId = ? \r\n");
        sb.append("   and ObservationDefinitionScript.ObservationDefinitionId = ? \r\n");
        
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < key3.size(); i++) {
            if (i != 0) {
                sb2.append(",");
            }
            sb2.append("?");
        }
        sb.append("   and ObservationDefinitionScript.DiseaseTypeCd IN(" + sb2.toString() + ") \r\n");

        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setInt(1, key1);
        preparedStatement.setString(2, key2);
        int i = 3;
        for (int num : key3) {
            preparedStatement.setInt(i, num);
            i++;
        }

        List<ObservationDefinitionScriptEntity> entityList = new ArrayList<ObservationDefinitionScriptEntity>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationDefinitionScriptEntity entity = ObservationDefinitionScriptEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entityList;
    }

    /**
     * ObservationDefinitionIdとDiseaseTypeCdでレコードを取得する
     * @param observationDefinitionId
     * @param diseaseTypeCd
     * @param viewId
     * @return entity
     * @throws Throwable 
     */
    public ObservationDefinitionScriptEntity findByIdAndDiseasetypecd(String observationDefinitionId, int diseaseTypeCd, int viewId) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append(getSelectedSql());
        sb.append(" where ObservationDefinitionScript.ObservationDefinitionId = ? \r\n");
        sb.append("   and ObservationDefinitionScript.DiseaseTypeCd = ? \r\n");
        sb.append("   and ObservationDefinitionScript.ViewId = ? \r\n");
        dao.setSql(sb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, observationDefinitionId);
        preparedStatement.setInt(2, diseaseTypeCd);
        preparedStatement.setInt(3, viewId);

        ObservationDefinitionScriptEntity entity = new ObservationDefinitionScriptEntity();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = ObservationDefinitionScriptEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
