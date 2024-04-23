/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：JLAC11測定物情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/29
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import static phr.datadomain.AbstractEntity.getBoolean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.JLAC11AnalyteCodeEntity;

/**
 * JLAC11測定物情報のデータオブジェクトです。
 */
@Repository
public class JLAC11AnalyteCodeAdapter extends JLAC11AnalyteCodeAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(JLAC11AnalyteCodeAdapter.class);
    private static Logger logger = Logger.getLogger(JLAC11AnalyteCodeAdapter.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public JLAC11AnalyteCodeAdapter() {
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     *
     * @param conn
     */
    public JLAC11AnalyteCodeAdapter(Connection conn) {
    	connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 測定物分類コードからレコードを取得する
     * @param analyteType
     * @return entity
     */
    public JLAC11AnalyteCodeEntity findRecord(String analyteType) throws Throwable 
    {
        logger.debug("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    JLAC11AnalyteCode.AnalyteCode As AnalyteCode  \r\n");
        sql.append("    , JLAC11AnalyteCode.AnalyteType As AnalyteType  \r\n");
        sql.append("    , JLAC11AnalyteCode.AnalyteName As AnalyteName  \r\n");
        sql.append("    , JLAC11AnalyteCode.JLAC10AnalyteCode As JLAC10AnalyteCode  \r\n");
        sql.append("    , JLAC11AnalyteCode.Note As Note  \r\n");
        sql.append("    , JLAC11AnalyteCode.HospitalLabResultTargetFlg As HospitalLabResultTargetFlg  \r\n");
        sql.append("    , JLAC11AnalyteCode.GraphTargetFllg As GraphTargetFllg  \r\n");
        sql.append("    , JLAC11AnalyteCode.JLAC11Version As JLAC11Version  \r\n");
        sql.append("    , JLAC11AnalyteCode.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , JLAC11AnalyteCode.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from JLAC11AnalyteCode \r\n");
        sql.append("where JLAC11AnalyteCode.AnalyteType = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, analyteType);

        JLAC11AnalyteCodeEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = JLAC11AnalyteCodeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }

}
