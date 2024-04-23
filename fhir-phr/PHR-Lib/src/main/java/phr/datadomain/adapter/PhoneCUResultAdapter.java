/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：スマートフォン対応健診（健診・問診・診察）結果情報のデータオブジェクト
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
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.PhoneCUResultAdapterBase;
import phr.datadomain.entity.PhoneCUItemEntity;
import phr.datadomain.entity.PhoneCUValueEntity;


/**
 *
 * @author iwaasa
 */
public class PhoneCUResultAdapter extends PhoneCUResultAdapterBase{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationEventAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public PhoneCUResultAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */
    
    /**
     * 対象PHR_IDの対象健診年月リストを取得します。
     * @param phrId
     * @param dataInputTypeCd
     * @return List<Date> 対象無の場合はnull
     * @throws Throwable
     */
    public List<Date> findyearList(String phrId, String dataInputTypeCd) throws Throwable {
        logger.trace("Start");
        //int intTypeCd = Integer.parseInt(dataInputTypeCd);
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getYearListSelectedSql();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);
        preparedStatement.setString(2, dataInputTypeCd);

        List <Date> yearList = new ArrayList();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            Date date = dataTable.getDate("ExaminationDate");
            yearList.add(date);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return yearList;
    }
    
    /**
     * 健診項目リストを取得します。
     * @param dataInputTypeCd
     * @return List<PhoneCUItemEntity> 対象無の場合はnull
     * @throws Throwable
     */
    public List<PhoneCUItemEntity> findItemList(String dataInputTypeCd, String insurerNo) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getItemListSelectedSql();
//        int idx = sqlorg.indexOf("AND");
//        String sql = sqlorg.substring(0, idx);        
//        sql += " order by ObservationDefinition.SortNo\r\n";
        sql += " order by ObservationDefinitionInsurer.SortNo \r\n";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, dataInputTypeCd);
        //preparedStatement.setString(2, insurerNo);
        
        List<PhoneCUItemEntity> itemList = new ArrayList();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            PhoneCUItemEntity entity = PhoneCUItemEntity.setData(dataTable);
            itemList.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return itemList;
    }    

    /**
     * 健診値リストを取得します。
     * @param phrId
     * @param dataInputTypeCd
     * @param observationDefinitionId
     * @return List<PhoneCUItemEntity> 対象無の場合はnull
     * @throws Throwable
     */
    public List<PhoneCUValueEntity> findValueList(String phrId, String dataInputTypeCd,String observationDefinitionId,List<Date> showYearList) throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
//        String sql = getValueListSelectedSql();
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    ObservationEvent.ExaminationDate As Examdate \r\n");
        sql.append("    , Observation.Value As Value \r\n");
        sql.append("    , Observation.MinReferenceValue As MinReferenceValue \r\n");
        sql.append("    , Observation.MaxReferenceValue As MaxReferenceValue \r\n");
        sql.append("    , Observation.Unit As Unit \r\n");
        sql.append("    , ObservationEvent.ObservationEventId As ObservationEventId \r\n");
        sql.append("    , ObservationEvent.MedicalOrganizationCd As MedicalOrganizationCd \r\n");
        sql.append("from ObservationEvent \r\n");
        sql.append("    inner join  Observation on ObservationEvent.ObservationEventId = Observation.ObservationEventId \r\n");
        sql.append("where ObservationEvent.PHR_ID = ? \r\n");
        sql.append("    and ObservationEvent.DataInputTypeCd = ? \r\n");
        sql.append("    and Observation.ObservationDefinitionId = ? \r\n");
        sql.append("order by ObservationEvent.ExaminationDate DESC \r\n");

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);
        preparedStatement.setString(2, dataInputTypeCd);
        preparedStatement.setString(3, observationDefinitionId);

        List<PhoneCUValueEntity> valueList = new ArrayList();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }
        
//        List<Date> showYearList = new ArrayList();//何とかして取得済みの日付リストをゲット
        for(Date showYear:showYearList){
            dataTable.beforeFirst();
            boolean okflg = false;
            PhoneCUValueEntity entity = null;
            while( dataTable.next() ) {
                entity = PhoneCUValueEntity.setData(dataTable);
                if(showYear.equals(entity.getExamdate())){
                    okflg=true;
                    break;
                }    
            }
            if(okflg){
            }else if(entity!=null){
                entity.setExamdate(showYear);
//                entity.setValue("-");
                entity.setValue(" ");
            }
            valueList.add(entity);
        }
//         Original
//        while( dataTable.next() ) {
//            PhoneCUValueEntity entity = PhoneCUValueEntity.setData(dataTable);
//            valueList.add(entity);
//        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return valueList;
    }    

    /**
     * 健診日とPHR_IDから医療機関名を取得
     * @param examDate
     * @param phr_id
     * @return hospitalName
     * @throws
     */
    public String getHospitalName(Date examDate, String phr_id) throws Throwable {
        logger.debug("Start");
        String hospitalName = null;
        
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();     
        sql += "select MedicalOrganization.MedicalOrganizationName As MedicalOrganizationName  \r\n";
        sql += "from MedicalOrganization  \r\n";
        sql += "inner join ObservationEvent  \r\n";
        sql += "where ObservationEvent.ExaminationDate = ?  \r\n";
        sql += "and ObservationEvent.PHR_ID = ?  \r\n";
        sql += "limit 1  \r\n";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setDate(1, (java.sql.Date) examDate);
        preparedStatement.setString(2, phr_id);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            hospitalName = dataTable.getString("MedicalOrganizationName");
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return hospitalName;
    }

    /**
     * 対象PHR_IDの対象健診年月リストを取得
     * @param phrId
     * @return List<Date> 対象無の場合はnull
     * @throws Throwable
     */
    public List<Date> findById(String phrId) throws Throwable {
        logger.trace("Start");
        
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    Distinct ExaminationDate As ExaminationDate  \r\n");
        sql.append("from ObservationEvent \r\n");
        sql.append("where PHR_ID = ? \r\n");
        sql.append("and DataInputTypeCd in ('3', '4', '5') \r\n");
        sql.append("order by ExaminationDate DESC \r\n");

        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, phrId);

        List <Date> yearList = new ArrayList();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            Date date = dataTable.getDate("ExaminationDate");
            yearList.add(date);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return yearList;
    }

    /**
     * EnumValueを取得
     * @param value
     * @param observationDefinitionId
     * @param viewId
     * @return enumValue
     * @throws
     */
    public String getEnumValue(String value, String observationDefinitionId, int viewId) throws Throwable {
        logger.debug("Start");
        String enumValue = null;
        
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = new String();     
        sql += "select ObservationDefinitionEnumValue.EnumValue As EnumValue  \r\n";
        sql += "from ObservationDefinitionEnumValue  \r\n";
        sql += "where ObservationDefinitionEnumValue.EnumName = ?  \r\n";
        sql += "and ObservationDefinitionEnumValue.ObservationDefinitionId = ?  \r\n";
        sql += "and ObservationDefinitionEnumValue.ViewId = ?  \r\n";
        
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, value);
        preparedStatement.setString(2, observationDefinitionId);
        preparedStatement.setInt(3, viewId);

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            enumValue = dataTable.getString("EnumValue");
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        
        logger.debug("End");
        return enumValue;
    }
}
