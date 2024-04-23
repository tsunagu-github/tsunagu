/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査項目結果アラート情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/25
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.ObservationAlertEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.utility.TypeUtility;

/**
 * 検査項目結果アラート情報のデータオブジェクトです。
 */
public class ObservationAlertAdapter extends ObservationAlertAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationAlertAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public ObservationAlertAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    public List<ObservationAlertEntity> findByAlertList(String insurerNo, String phrid , Timestamp startDate , Timestamp endDate) throws SQLException{
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        boolean flg = false;
        
        StringBuilder sb = new StringBuilder();
        sb.append(" select  \r\n");
        sb.append("    ObservationAlert.ObservationEventId As ObservationEventId   \r\n");
        sb.append("    , ObservationAlert.ObservationDefinitionId As ObservationDefinitionId   \r\n");
        sb.append("    , ObservationAlert.ViewId As ViewId   \r\n");
        sb.append("    , ObservationAlert.AlertLevelCd As AlertLevelCd   \r\n");
        sb.append("    , ObservationAlert.AlertFlg As AlertFlg   \r\n");
        sb.append("    , ObservationAlert.CreateDateTime As CreateDateTime   \r\n");
        sb.append("    , ObservationAlert.UpdateDateTime As UpdateDateTime   \r\n");
        sb.append("    , ObservationEvent.ExaminationDate As ExaminationDate  \r\n");
        sb.append("    , ObservationDefinition.ObservationDefinitionName \r\n");
        sb.append("    , Patient.PHR_ID As PHR_ID \r\n");
        sb.append("    , Patient.FamilyName As FamilyName \r\n");
        sb.append("    , Patient.GivenName As GivenName \r\n");
        sb.append("    , InsurerViewDefinition.ViewName As ViewName \r\n");
        sb.append("    , MedicalOrganizationPatient.AgreesToShare As AgreesToShare \r\n");
        sb.append(" from ObservationAlert  \r\n");
        sb.append(" inner join ObservationEvent on \r\n");
        sb.append(" ObservationAlert.ObservationEventId = ObservationEvent.ObservationEventId \r\n");
        sb.append(" inner join ObservationDefinition on \r\n");
        sb.append(" ObservationAlert.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \r\n");
        sb.append(" inner join Patient on \r\n");
        sb.append(" ObservationEvent.PHR_ID = Patient.PHR_ID \r\n");
        sb.append(" inner join InsurerViewDefinition on \r\n");
        sb.append(" ObservationAlert.ViewId = InsurerViewDefinition.ViewId \r\n");
        sb.append(" inner join InsurerPatient on \r\n");
        sb.append("  InsurerPatient.InsurerNo = ? \r\n");
        sb.append("  and InsurerPatient.PHR_ID = Patient.PHR_ID \r\n");
        sb.append(" inner join MedicalOrganizationPatient on \r\n");
        sb.append("  MedicalOrganizationPatient.MedicalOrganizationCd = ? \r\n");
        sb.append("  and MedicalOrganizationPatient.PHR_ID = Patient.PHR_ID \r\n");
        
        sb.append(" where  \r\n");
        sb.append(" ObservationAlert.AlertFlg = 1  \r\n");
        
        
        if(!TypeUtility.isNullOrEmpty(phrid) || startDate != null || endDate != null){
            
            if(!TypeUtility.isNullOrEmpty(phrid) ){
                sb.append(" and Patient.PHR_ID = ? \r\n");
            }
            
            if(startDate != null){
                sb.append(" and  ObservationEvent.ExaminationDate >= ? \r\n");
            }
            
            if(endDate != null){
                if(flg) sb.append("  and \r\n");
                sb.append(" and ObservationEvent.ExaminationDate <= ? \r\n");
            }
            sb.append(" Order By ObservationEventId DESC \r\n");
        }
        
        String sql = sb.toString();
        dao.setSql(sql);
        
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        
        preparedStatement.setString(1, insurerNo);
        preparedStatement.setString(2, insurerNo);
        
        int count = 3;
        if(!TypeUtility.isNullOrEmpty(phrid)){
            preparedStatement.setString(count, phrid);
            count++;
        }
        
        if(startDate != null){
            preparedStatement.setTimestamp(count, startDate);
            count++;
        }

        if(endDate != null){
            preparedStatement.setTimestamp(count, endDate);
            count++;
        }   
        
        List<ObservationAlertEntity> retList = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        try {
            while( dataTable.next() ) {
                ObservationAlertEntity entity;
                entity = ObservationAlertEntity.setData(dataTable);
                entity.setExaminationDate(AbstractEntity.getDateTime(dataTable, "ExaminationDate"));
                entity.setFamilyName(AbstractEntity.getString(dataTable, "FamilyName"));
                entity.setGivenName(AbstractEntity.getString(dataTable, "GivenName"));
                entity.setpHR_ID(AbstractEntity.getString(dataTable, "PHR_ID"));
                entity.setObservationDefinitionName(AbstractEntity.getString(dataTable, "ObservationDefinitionName"));
                entity.setViewName(AbstractEntity.getString(dataTable, "ViewName"));
                entity.setAgreesToShare(AbstractEntity.getBoolean(dataTable, "AgreesToShare"));
                
                retList.add(entity);
            }
        } catch (Throwable ex) {
            Logger.getLogger(ObservationAlertAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();

        logger.debug("End");
        return retList;
    }

    /**
     * ObservationEventIdでObservationAlertテーブルからレコードを削除します。
     * @param observationEventId
     * @return rowCount
     * @throws Throwable
     */
    public int deleteObservationAlertRecord(String observationEventId) throws Throwable {
        logger.debug("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sb = new StringBuilder();
        sb.append("delete from  ObservationAlert \r\n");
        sb.append("where ObservationAlert.ObservationEventId = ? \r\n");
        String sql = sb.toString();

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1, observationEventId);

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        dao.commitTransaction();
        logger.debug("End");
        return rowCount;
    }
}
