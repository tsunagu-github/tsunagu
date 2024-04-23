/********************************************************************************
 * システム名      ：PHR
 * コンポーネント名：連携医療機関システム情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2021/09/29
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.DataAccessTimingEntity;
import phr.datadomain.entity.MedicalOrganizationSystemEntity;

/**
 * 連携医療機関システム情報のデータオブジェクトです。
 */
@Repository
public class MedicalOrganizationSystemAdapter extends MedicalOrganizationSystemAdapterBase {

    /**
     * ロギングコンポーネント
     */
//    private static final Logger logger = LoggerFactory.getLogger(MedicalOrganizationSystemAdapter.class);
	private static Logger logger = Logger.getLogger(MedicalOrganizationSystemAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public MedicalOrganizationSystemAdapter() {
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * データ連携システムIDでレコードを取得
     * @param systemId
     * @return entity
     * @throws Throwable
     */
    public MedicalOrganizationSystemEntity findBySystemId(String systemId) throws Throwable 
    {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject();
        String sql = getSelectedSql();
        sql += " where DataCooperationSystemId = ?";
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, systemId);

        MedicalOrganizationSystemEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = MedicalOrganizationSystemEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.debug("End");
        return entity;
    }
}
