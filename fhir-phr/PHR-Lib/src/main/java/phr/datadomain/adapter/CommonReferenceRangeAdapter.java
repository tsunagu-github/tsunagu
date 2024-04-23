/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：共用基準範囲のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/29
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.CommonReferenceRangeEntity;
import phr.datadomain.entity.DiseaseTypeEntity;
import phr.datadomain.entity.ObservationDefinitionJlac10Entity;
import phr.datadomain.entity.ObservationEventEntity;

/**
 * 共用基準範囲のデータオブジェクトです。
 */
@Repository
public class CommonReferenceRangeAdapter extends CommonReferenceRangeAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(CommonReferenceRangeAdapter.class);
    private static Logger logger = Logger.getLogger(CommonReferenceRangeAdapter.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public CommonReferenceRangeAdapter() {
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public CommonReferenceRangeAdapter(Connection conn)
    {
    	connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * JLAC10AnalyteCodeを使用してレコードを取得します
     * @param JLAC10AnalyteCode
     * @return
     * @throws Throwable
     */
    public CommonReferenceRangeEntity findByJLAC10(String JLAC10AnalyteCode) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    CommonReferenceRange.JLAC10AnalyteCode As JLAC10AnalyteCode  \r\n");
        sql.append("    , CommonReferenceRange.JLAC11AnalyteCode As JLAC11AnalyteCode  \r\n");
        sql.append("    , CommonReferenceRange.ObservationDefinitionName As ObservationDefinitionName  \r\n");
        sql.append("    , CommonReferenceRange.ObservationDefinitionShortName As ObservationDefinitionShortName  \r\n");
        sql.append("    , CommonReferenceRange.Unit As Unit  \r\n");
        sql.append("    , CommonReferenceRange.UnitCode As UnitCode  \r\n");
        sql.append("    , CommonReferenceRange.ReferenceRangeType As ReferenceRangeType  \r\n");
        sql.append("    , CommonReferenceRange.CommonLowerLimit As CommonLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.CommonUpperLimit As CommonUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.MaleLowerLimit As MaleLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.MaleUpperLimit As MaleUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.FemaleLowerLimit As FemaleLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.FemaleUpperLimit As FemaleUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , CommonReferenceRange.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from CommonReferenceRange \r\n");
        sql.append("where CommonReferenceRange.JLAC10AnalyteCode = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, JLAC10AnalyteCode);

        CommonReferenceRangeEntity entity = new CommonReferenceRangeEntity();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
        	entity = CommonReferenceRangeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }

    /**
     * JLAC11AnalyteCodeを使用してレコードを取得します
     * @param JLAC11AnalyteCode
     * @return
     * @throws Throwable
     */
    public CommonReferenceRangeEntity findByJLAC11(String JLAC11AnalyteCode) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    CommonReferenceRange.JLAC10AnalyteCode As JLAC10AnalyteCode  \r\n");
        sql.append("    , CommonReferenceRange.JLAC11AnalyteCode As JLAC11AnalyteCode  \r\n");
        sql.append("    , CommonReferenceRange.ObservationDefinitionName As ObservationDefinitionName  \r\n");
        sql.append("    , CommonReferenceRange.ObservationDefinitionShortName As ObservationDefinitionShortName  \r\n");
        sql.append("    , CommonReferenceRange.Unit As Unit  \r\n");
        sql.append("    , CommonReferenceRange.UnitCode As UnitCode  \r\n");
        sql.append("    , CommonReferenceRange.ReferenceRangeType As ReferenceRangeType  \r\n");
        sql.append("    , CommonReferenceRange.CommonLowerLimit As CommonLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.CommonUpperLimit As CommonUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.MaleLowerLimit As MaleLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.MaleUpperLimit As MaleUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.FemaleLowerLimit As FemaleLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.FemaleUpperLimit As FemaleUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , CommonReferenceRange.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from CommonReferenceRange \r\n");
        sql.append("where CommonReferenceRange.JLAC11AnalyteCode = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, JLAC11AnalyteCode);

        CommonReferenceRangeEntity entity = new CommonReferenceRangeEntity();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
        	entity = CommonReferenceRangeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }

    /**
     * JLAC10AnalyteCodeとJLAC11AnalyteCodeを使用してレコードを取得します
     * @param JLAC10AnalyteCode
     * @param JLAC11AnalyteCode
     * @return
     * @throws Throwable
     */
    public CommonReferenceRangeEntity findByJLAC10AndJLAC11(String JLAC10AnalyteCode, String JLAC11AnalyteCode) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    CommonReferenceRange.JLAC10AnalyteCode As JLAC10AnalyteCode  \r\n");
        sql.append("    , CommonReferenceRange.JLAC11AnalyteCode As JLAC11AnalyteCode  \r\n");
        sql.append("    , CommonReferenceRange.ObservationDefinitionName As ObservationDefinitionName  \r\n");
        sql.append("    , CommonReferenceRange.ObservationDefinitionShortName As ObservationDefinitionShortName  \r\n");
        sql.append("    , CommonReferenceRange.Unit As Unit  \r\n");
        sql.append("    , CommonReferenceRange.UnitCode As UnitCode  \r\n");
        sql.append("    , CommonReferenceRange.ReferenceRangeType As ReferenceRangeType  \r\n");
        sql.append("    , CommonReferenceRange.CommonLowerLimit As CommonLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.CommonUpperLimit As CommonUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.MaleLowerLimit As MaleLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.MaleUpperLimit As MaleUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.FemaleLowerLimit As FemaleLowerLimit  \r\n");
        sql.append("    , CommonReferenceRange.FemaleUpperLimit As FemaleUpperLimit  \r\n");
        sql.append("    , CommonReferenceRange.CreateDateTime As CreateDateTime  \r\n");
        sql.append("    , CommonReferenceRange.UpdateDateTime As UpdateDateTime  \r\n");
        sql.append("from CommonReferenceRange \r\n");
        sql.append("where CommonReferenceRange.JLAC10AnalyteCode = ? \r\n");
        sql.append("and CommonReferenceRange.JLAC11AnalyteCode = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, JLAC10AnalyteCode);
        preparedStatement.setString(2, JLAC11AnalyteCode);

        CommonReferenceRangeEntity entity = new CommonReferenceRangeEntity();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
        	entity = CommonReferenceRangeEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }

    /**
     * JLAC10AnalyteCodeを使用してレコードを取得します
     * @param JLAC10AnalyteCode
     * @return
     * @throws Throwable
     */
    public List<CommonReferenceRangeEntity> findAllList(String JLAC10AnalyteCode) throws Throwable{
    {
        logger.debug("Start");

        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sqlsb = new StringBuilder();
        sqlsb.append("select * \r\n");
        sqlsb.append("from CommonReferenceRange \r\n");
        sqlsb.append("where CommonReferenceRange.JLAC10AnalyteCode = ? \r\n");

        dao.setSql(sqlsb.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, JLAC10AnalyteCode);

        List<CommonReferenceRangeEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
        	CommonReferenceRangeEntity entity = CommonReferenceRangeEntity.setData(dataTable);
            ret.add(entity);
        }

        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();

        logger.debug("End");
        return ret;
        }
    }
}
