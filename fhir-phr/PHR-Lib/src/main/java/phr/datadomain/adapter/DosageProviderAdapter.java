/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：お薬情報　対象調剤の薬剤師および医師情報項目のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/08
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.DosageProviderAdapterBase;
import phr.datadomain.entity.DosageProviderEntity;
import phr.datadomain.entity.DosageTypeCd;


/**
 *
 * @author kis-note-027_user
 */
public class DosageProviderAdapter extends DosageProviderAdapterBase{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageProviderAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageProviderAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     *提供者リストを取得します。
     * @param dataInputTypeCd
     * @return DosageProviderEntity 対象無の場合はnull
     * @throws Throwable
     */
    public DosageProviderEntity findByDosageid(String dosageid, int Seq) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDosageProviderSqlLeft();
        sql +="UNION \r\n";
        String sqlright1 = getDosageProviderSqlRight();
        sql += sqlright1;
        sql += "UNION ALL \r\n";
        String sqlleft1 = getDosageProviderSqlLeft();
        sql += sqlleft1;
        sql +="UNION \r\n";
        String sqlright2 = getDosageProviderSqlRight();
        sql += sqlright2;

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dosageid);
        preparedStatement.setInt(2, Seq);
        preparedStatement.setString(3, DosageTypeCd.DOSAGE.getId());
        preparedStatement.setString(4, dosageid);
        preparedStatement.setInt(5, Seq);
        preparedStatement.setString(6, DosageTypeCd.DOSAGE.getId());
        preparedStatement.setString(7, dosageid);
        preparedStatement.setInt(8, Seq);
        preparedStatement.setString(9, DosageTypeCd.RECIPE.getId());
        preparedStatement.setString(10, dosageid);
        preparedStatement.setInt(11, Seq);
        preparedStatement.setString(12, DosageTypeCd.RECIPE.getId());

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        //while( dataTable.next() ) {
           DosageProviderEntity entity = DosageProviderEntity.setData(dataTable);
        //}
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
  
    
}
