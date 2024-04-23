/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：参照用パスワード情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/25
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ViewPasswordEntity;

/**
 * 参照用パスワード情報のデータオブジェクトです。
 */
public class ViewPasswordAdapter extends ViewPasswordAdapterBase {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ViewPasswordAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     *
     * @param conn
     */
    public ViewPasswordAdapter(Connection conn) {
        super(conn);
    }

    /* -------------------------------------------------------------------------------------- */

    public int DeleteViewPasswordByExpirationDate(Timestamp ts) throws SQLException {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE from ViewPassword where ViewPassword.ExpirationDate <= ?");

        logger.debug(sql.toString());
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setTimestamp(1, ts);
        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    public ViewPasswordEntity getAliveViewPasswordByPHR_ID(String phrid,String insurerId,String MedicalOrgCd) throws SQLException, Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " left join InsurerPatient";
        sql += " on ViewPassword.PHR_ID=InsurerPatient.PHR_ID";
        sql += " left join MedicalOrganizationPatient";
        sql += " on ViewPassword.PHR_ID=MedicalOrganizationPatient.PHR_ID";
        sql += " where ViewPassword.Password = ?";
        sql += " and ViewPassword.ExpirationDate > CURDATE()";
        if(insurerId!=null){
            sql += " and InsurerPatient.InsurerNo = ?";
        }
        if(MedicalOrgCd!=null){
            sql += " and MedicalOrganizationPatient.MedicalOrganizationCd = ?";
        }

        logger.debug(sql);
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrid);
        int i=2;
        if(insurerId!=null){
            preparedStatement.setString(i, insurerId);
            i+=1;
        }
        if(MedicalOrgCd!=null){
            preparedStatement.setString(i, MedicalOrgCd);
//            i+=1;
        }

        ViewPasswordEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }

        while (dataTable.next()) {
            entity = ViewPasswordEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }

}
