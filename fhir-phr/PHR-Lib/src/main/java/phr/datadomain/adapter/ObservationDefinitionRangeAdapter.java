/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：管理項目範囲情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import static phr.datadomain.AbstractEntity.getInt;
import static phr.datadomain.AbstractEntity.getNullDouble;
import static phr.datadomain.AbstractEntity.getString;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.ObservationDefinitionRangeEntity;
import phr.enums.DataInputTypeCdEnum;

/**
 * 管理項目範囲情報のデータオブジェクトです。
 */
public class ObservationDefinitionRangeAdapter extends ObservationDefinitionRangeAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationDefinitionRangeAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationDefinitionRangeAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * <pre>管理項目範囲情報よりリマインダ種別コードを取得</pre>
     * @param insurerNo
     * @param year
     * @param id
     * @param diseaseCd
     * @return
     * @throws Throwable 
     */
    public String SearchReminderCd(String insurerNo , int year, String id , int diseaseCd) throws Throwable{
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationDefinitionRange.InsurerNo = ? \r\n";
        sql += " and ObservationDefinitionRange.Year = ? \r\n ";
        sql += " and ObservationDefinitionRange.ObservationDefinitionId = ?  \r\n";
        sql += " and ObservationDefinitionRange.DiseaseTypeCd = ?  \r\n";
        
        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, insurerNo);  
        preparedStatement.setInt(2, year);
        preparedStatement.setString(3, id);
        preparedStatement.setInt(4, diseaseCd);
        
        // 検索処理
        ResultSet dataTable = preparedStatement.executeQuery();
        if(dataTable==null){    // データがない時
            return null;    
        }
        
        ObservationDefinitionRangeEntity entity =null;
        while( dataTable.next() ) {
            entity = ObservationDefinitionRangeEntity.setData(dataTable);
        }
        
        int reminderTypeCd =entity.getReminderTypeCd();

        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return String.valueOf(reminderTypeCd);
        
    } 
    
    public boolean InsertObservation(HashMap<String,String> map) throws Throwable{
        
        DataAccessObject dao = new DataAccessObject(connection);
        dao.beginTransaction();
        String sql = getInsertSql();
        
        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, map.get("InsurerNo"));   // 保険者番号
        preparedStatement.setInt(2, Integer.parseInt(map.get("Year")));   // 対象年月
        preparedStatement.setString(3, map.get("ObservationDefinitionId"));   // 項目ID
        preparedStatement.setInt(4, Integer.parseInt(map.get("DiseaseTypeCd")));   // 疾病種別コード
        preparedStatement.setInt(5, Integer.parseInt(map.get("ReminderTypeCd")));   // リマインダ種別コード
        preparedStatement.setInt(6, Integer.parseInt(map.get("RangeTypeCd")));   // 範囲種別コード
        
        for(int i=7;i<19;i++){
            preparedStatement.setInt(i, Integer.parseInt(null));
        }

        // 登録処理
        int i = preparedStatement.executeUpdate(sql);
        
        System.out.println("処理件数===>" + i);
        
        dao.commitTransaction();
        dao.rollbackTransaction();
        
        preparedStatement.close();
        dao.close();
        return false;
    }

    /**
     * 入力異常値チェックをします。
     * @param value
     * @param InsurerNo
     * @param Year
     * @param DiseaseTypeCd
     * @param ObservationDeifinitionId
     * @return
     * @throws Throwable
     */
    public ObservationDefinitionRangeEntity checkInput(String value, String InsurerNo, int Year, String ObservationDeifinitionId, String DiseaseTypeCd) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where ObservationDefinitionRange.InsurerNo = ? ";
        sql += " and ObservationDefinitionRange.Year = ? ";
        sql += " and ObservationDefinitionRange.ObservationDefinitionID = ? ";
        sql += " and ObservationDefinitionRange.DiseaseTypeCd = ? ";
        sql += " and (ObservationDefinitionRange.MinInput < ? or ObservationDefinitionRange.MinInput is null) ";
        sql += " and (ObservationDefinitionRange.MaxInput > ? or ObservationDefinitionRange.MaxInput is null) ";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, InsurerNo);
        preparedStatement.setInt(2, Year);
        preparedStatement.setString(3, ObservationDeifinitionId);
        preparedStatement.setString(4, DiseaseTypeCd);
        preparedStatement.setString(5, value);
        preparedStatement.setString(6, value);

        ObservationDefinitionRangeEntity oentity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        
        while( dataTable.next() ) {
            oentity = ObservationDefinitionRangeEntity.setData(dataTable);
        }
        
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return oentity;
    }
    
    /**
     * 検査一覧を取得
     * @param insurerNo
     * @param year
     * @param sexCd
     * @return
     * @throws Throwable
     */
    public List<ObservationDefinitionRangeEntity> SearchObservationDefinitionId(String insurerNo , int year, String sexCd) throws Throwable{
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("select  DISTINCT\r\n");
        sb.append("ObservationDefinitionType.ObservationDefinitionId As ObservationDefinitionId \r\n");
        sb.append(", ObservationDefinition.ObservationDefinitionName As ObservationDefinitionName \r\n");
        sb.append(", ObservationDefinition.DisplayName As DisplayName \r\n");
        sb.append(", ObservationDefinition.SortNo As SortNo \r\n");
        sb.append(", ObservationDefinitionInsurer.UnitValue As UnitValue \r\n");
        sb.append(", ObservationDefinitionEnumValue.EnumId As EnumId \r\n");
        sb.append(", ObservationDefinitionEnumValue.EnumName As EnumName \r\n");
        sb.append(", ObservationDefinitionEnumValue.EnumValue As EnumValue \r\n");
        sb.append(", table1.MinReferenceValue As MinReferenceValue \r\n");
        sb.append(", table1.MaxReferenceValue As MaxReferenceValue \r\n");
        sb.append("from ObservationDefinitionType \r\n");
        sb.append("inner join ObservationDefinition \r\n");
        sb.append("on ObservationDefinitionType.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \r\n");
        sb.append("inner join ObservationDefinitionInsurer \r\n");
        sb.append("on ObservationDefinitionType.ObservationDefinitionId = ObservationDefinitionInsurer.ObservationDefinitionId \r\n");
        sb.append("left outer join ObservationDefinitionEnumValue \r\n");
        sb.append("on ObservationDefinitionEnumValue.ObservationDefinitionId = ObservationDefinitionInsurer.ObservationDefinitionId \r\n");
        sb.append("and ObservationDefinitionEnumValue.InputValueFlg = ? \r\n");
        sb.append("inner join (  \r\n");
        sb.append("select DISTINCT \r\n");
        sb.append("ObservationDefinitionJlac10.ObservationDefinitionId As ObservationDefinitionId \r\n");
        sb.append(", case \r\n");
        sb.append("when CommonreferenceRange.CommonLowerLimit is null \r\n");
        sb.append("then case ? \r\n");
        sb.append("when '男性' then CommonreferenceRange.MaleLowerLimit \r\n");
        sb.append("when '女性' then CommonreferenceRange.FemaleLowerLimit \r\n");
        sb.append("end \r\n");
        sb.append("else CommonreferenceRange.CommonLowerLimit \r\n");
        sb.append("end As MinReferenceValue \r\n");
        sb.append(", case \r\n");
        sb.append("when CommonreferenceRange.CommonUpperLimit is null \r\n");
        sb.append("then case ? \r\n");
        sb.append("when '男性' then CommonreferenceRange.MaleUpperLimit \r\n");
        sb.append("when '女性' then CommonreferenceRange.FemaleUpperLimit \r\n");
        sb.append("end \r\n");
        sb.append("else CommonreferenceRange.CommonUpperLimit \r\n");
        sb.append("end As MaxReferenceValue \r\n");
        sb.append("from \r\n");
        sb.append("ObservationDefinitionJlac10 \r\n");
        sb.append("left join CommonreferenceRange \r\n");
        sb.append("on CommonreferenceRange.JLAC10AnalyteCode = left (ObservationDefinitionJlac10.JLAC10, 5) \r\n");
        sb.append("and case ? \r\n");
        sb.append("when '男性' then CommonreferenceRange.ReferenceRangeType not in (2) \r\n");
        sb.append("when '女性' then CommonreferenceRange.ReferenceRangeType not in (1)  \r\n");
        sb.append("end \r\n");
        sb.append("where \r\n");
        sb.append("left (ObservationDefinitionJlac10.JLAC10, 5) not in ('3D045', '2A990') \r\n");
        sb.append(") as table1 \r\n");
        sb.append("on table1.ObservationDefinitionId = ObservationDefinitionType.ObservationDefinitionId \r\n");
        sb.append("where ObservationDefinitionType.InsurerNo = ? \r\n");
        sb.append("and ObservationDefinitionType.DataInputTypeCd = ? \r\n");
        sb.append("order by ObservationDefinition.SortNo, ObservationDefinitionEnumValue.EnumId\r\n");
        String sql = sb.toString();
        
        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, sexCd);
        preparedStatement.setString(3, sexCd);
        preparedStatement.setString(4, sexCd);
        preparedStatement.setString(5, insurerNo);
        preparedStatement.setInt(6, DataInputTypeCdEnum.KENNSA.getCode());
        
        // 検索処理
        ResultSet dataTable = preparedStatement.executeQuery();
        if(dataTable==null){    // データがない時
            return null;    
        }
        
        List<ObservationDefinitionRangeEntity> ret = new ArrayList<ObservationDefinitionRangeEntity>();
        while( dataTable.next() ) {
            ObservationDefinitionRangeEntity entity = new ObservationDefinitionRangeEntity();
            //entity.setInsurerNo(getString(dataTable, "InsurerNo"));
            //entity.setYear(getInt(dataTable, "Year"));
            entity.setObservationDefinitionId(getString(dataTable, "ObservationDefinitionId"));
            //entity.setDiseaseTypeCd(getInt(dataTable, "DiseaseTypeCd"));
            //entity.setReminderTypeCd(getInt(dataTable, "ReminderTypeCd"));
            entity.setReminderTypeCd(1);
            //entity.setRangeTypeCd(getInt(dataTable, "RangeTypeCd"));
            entity.setMinReferenceValue(getNullDouble(dataTable, "MinReferenceValue"));
            entity.setMaxReferenceValue(getNullDouble(dataTable, "MaxReferenceValue"));
            entity.setObservationDefinitionName(getString(dataTable, "ObservationDefinitionName"));
            entity.setDisplayName(getString(dataTable, "DisplayName"));
            entity.setSortNo(getInt(dataTable,"SortNo"));
            entity.setUnitValue(getString(dataTable, "UnitValue"));
            entity.setEnumId(getInt(dataTable, "EnumId"));
            entity.setEnumName(getString(dataTable, "EnumName"));
            entity.setEnumValue(getString(dataTable, "EnumValue"));
            entity.setDiseaseManagementTargetFlg(true);
            ret.add(entity);
            
            if (logger.isDebugEnabled())
            {
                //logger.debug("保険者番号      ：" + entity.getInsurerNo());
                //logger.debug("対象年度        ：" + entity.getYear());
                logger.debug("項目ID          ：" + entity.getObservationDefinitionId());
                //logger.debug("疾病種別CD      ：" + entity.getDiseaseTypeCd());
                //logger.debug("範囲種別CD      ：" + entity.getRangeTypeCd());
                logger.debug("基準値下限 　   ：" + entity.getMinReferenceValue());
                logger.debug("基準値上限  　  ：" + entity.getMaxReferenceValue());
                logger.debug("検査名称        ：" + entity.getObservationDefinitionName());
                logger.debug("検査表示名称    ：" + entity.getDisplayName());
                logger.debug("ソート順　　    ：" + entity.getSortNo());
                logger.debug("単位　　　　    ：" + entity.getUnitValue());
                logger.debug("列挙ID　　　    ：" + entity.getEnumId());
                logger.debug("列挙名称　　    ：" + entity.getEnumName());
                logger.debug("列挙値　　　    ：" + entity.getEnumValue());
            }
        }
        
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return ret;
        
    } 
}
