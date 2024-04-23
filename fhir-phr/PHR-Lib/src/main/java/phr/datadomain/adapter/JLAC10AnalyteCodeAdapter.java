/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：JLAC10分析物情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/29
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import static phr.datadomain.AbstractEntity.getString;
import static phr.datadomain.AbstractEntity.getBoolean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.JLAC10AnalyteCodeEntity;
import phr.datadomain.entity.ObservationDefinitionJlac10Entity;

/**
 * JLAC10分析物情報のデータオブジェクトです。
 */
@Repository
public class JLAC10AnalyteCodeAdapter extends JLAC10AnalyteCodeAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(JLAC10AnalyteCodeAdapter.class);
    private static Logger logger = Logger.getLogger(JLAC10AnalyteCodeAdapter.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public JLAC10AnalyteCodeAdapter() {
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     *
     * @param conn
     */
    public JLAC10AnalyteCodeAdapter(Connection conn) {
    	connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 測定物分類コードからレコードを取得する
     * @param analyteType
     * @return entity
     */
    public JLAC10AnalyteCodeEntity findRecord(String analyteType) throws Throwable 
    {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    JLAC10AnalyteCode.AnalyteCode As AnalyteCode  \r\n");
        sql.append("    , JLAC10AnalyteCode.AnalyteType As AnalyteType  \r\n");
        sql.append("    , JLAC10AnalyteCode.AnalyteName1 As AnalyteName1  \r\n");
        sql.append("    , JLAC10AnalyteCode.AnalyteName2 As AnalyteName2  \r\n");
        sql.append("    , JLAC10AnalyteCode.Note As Note  \r\n");
        sql.append("    , JLAC10AnalyteCode.HospitalLabResultTargetFlg As HospitalLabResultTargetFlg  \r\n");
        sql.append("    , JLAC10AnalyteCode.GraphTargetFllg As GraphTargetFllg  \r\n");
        sql.append("    , JLAC10AnalyteCode.JLAC10Version As JLAC10Version  \r\n");
        sql.append("    , JLAC10AnalyteCode.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , JLAC10AnalyteCode.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("    , JLAC10AnalyteCode.SortNo As SortNo  \r\n");
        sql.append("from JLAC10AnalyteCode \r\n");
        sql.append("where JLAC10AnalyteCode.AnalyteType = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, analyteType);

        JLAC10AnalyteCodeEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = JLAC10AnalyteCodeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }
    
}
