/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：項目疾病情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/05
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static phr.datadomain.AbstractEntity.getInt;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationDefinitionDiseaseEntity;

/**
 * 項目疾病情報のデータオブジェクトです。
 */
public class ObservationDefinitionDiseaseAdapter extends ObservationDefinitionDiseaseAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionDiseaseAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionDiseaseAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    /**
     * 保険者No、年度で項目疾病情報を検索します
     *    ([疾病種別CD] = [項目IDリスト])
     * @param insurerNo
     * @param Year
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionDiseaseEntity> findByInsurerNoYear(String insurerNo, int Year) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql = sql.replaceFirst("from ObservationDefinitionDisease \r\n", " , ObservationDefinitionType.DataInputTypeCd as DataInputTypeCd \r\n");
        sql += " from ObservationDefinitionDisease \r\n";
       sql += " inner join ObservationDefinitionType on ";
        sql += "     ObservationDefinitionType.InsurerNo = ObservationDefinitionDisease.InsurerNo ";
        sql += "     and ObservationDefinitionType.Year = ObservationDefinitionDisease.Year ";
        sql += "     and ObservationDefinitionType.ObservationDefinitionId = ObservationDefinitionDisease.ObservationDefinitionId ";
        sql += " where ObservationDefinitionDisease.InsurerNo = ?";
        sql += "   and ObservationDefinitionDisease.Year = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);
        preparedStatement.setInt(2, Year);

        List<ObservationDefinitionDiseaseEntity> list = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            ObservationDefinitionDiseaseEntity entity = ObservationDefinitionDiseaseEntity.setData(dataTable);
            entity.setDataInputTypeCd(getInt(dataTable, "DataInputTypeCd"));
            list.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return list;
    }
}
