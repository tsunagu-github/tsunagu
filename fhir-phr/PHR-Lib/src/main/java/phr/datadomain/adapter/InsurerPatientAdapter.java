/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者患者関連情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
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
import phr.datadomain.entity.InsurerPatientEntity;

/**
 * 保険者患者関連情報のデータオブジェクトです。
 */
public class InsurerPatientAdapter extends InsurerPatientAdapterBase
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(InsurerPatientAdapter.class);
    private static Logger logger = Logger.getLogger(InsurerPatientAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public InsurerPatientAdapter(Connection conn)
    {
        super(conn);
    }
    
    /**
     * PHR_IDにて保険者患者関連情報を検索します。
     * @param phrId
     * @return
     * @throws Throwable
     */
    public InsurerPatientEntity findByPhrId(String phrId) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where InsurerPatient.PHR_ID = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);

        InsurerPatientEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = InsurerPatientEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }
    /* -------------------------------------------------------------------------------------- */
    
    /**
     * InsurerNoにて保険者患者関連情報を検索します。
     * @param insurerNo
     * @return
     * @throws Throwable
     */
    public List<InsurerPatientEntity> findByInsurerNo(String insurerNo) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where InsurerPatient.InsurerNo = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);

        List<InsurerPatientEntity> entityList = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            InsurerPatientEntity entity = InsurerPatientEntity.setData(dataTable);
            entityList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entityList;
    }

    /**
     * PHR_IDにて保険者患者関連情報を検索します。
     * @param phrId
     * @return
     * @throws Throwable
     */
    public List<InsurerPatientEntity> selectByPhrId(String phrId) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where InsurerPatient.PHR_ID = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);

        List<InsurerPatientEntity> entity = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity.add( InsurerPatientEntity.setData(dataTable) );
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * PHR_IDでInsurerPatinetテーブルのレコードを削除します。
     * @param phr_id
     * @return
     * @throws Throwable
     */
    public int deleteInsurerPatientRecord(String phr_id) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();
        sql += "delete from InsurerPatient";
        sql += " where InsurerPatient.PHR_ID = ?";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, phr_id);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
}
