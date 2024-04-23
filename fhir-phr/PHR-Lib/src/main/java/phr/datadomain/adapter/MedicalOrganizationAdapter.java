/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：医療機関情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.base.*;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.utility.TypeUtility;

/**
 * 医療機関情報のデータオブジェクトです。
 */
public class MedicalOrganizationAdapter extends MedicalOrganizationAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicalOrganizationAdapter.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public MedicalOrganizationAdapter(Connection conn)
    {
        super(conn);
    }
    /* -------------------------------------------------------------------------------------- */

    public List<MedicalOrganizationEntity> findByMedOrgCd(String medOrgCd) throws SQLException, Throwable         
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    MedicalOrganization.MedicalOrganizationCd As MedicalOrganizationCd  \r\n");
        sql.append("    , MedicalOrganization.MedicalOrganizationName As MedicalOrganizationName  \r\n");
        sql.append("from MedicalOrganization \r\n");
        sql.append(" where MedicalOrganizationPatient.medOrgCd = ? \r\n");
        dao.setSql(sql.toString());

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, medOrgCd);
        
       MedicalOrganizationEntity entity = null;
       List<MedicalOrganizationEntity> ret = new ArrayList<>();

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = MedicalOrganizationEntity.setData(dataTable);
            ret.add(entity);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return ret;
    }    
    
    /**
     * 医療機関コード、名称取得
     * @param userIdStr
     * @param userNameStr
     * @param limitRow
     * @param currentPage
     * @return
     * @throws Throwable 
     */
    public ArrayList<MedicalOrganizationEntity> getMedicalOrganizationInfo(String userIdStr, String userNameStr,int limitRow,int currentPage)throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);

        int flg =0;
        int displayPage = limitRow*currentPage;
        
// ---SQL文作成 Start---//
        String sql = getSelectedSql();
        //*-----------------------*//
        // 医療機関コード、医療機関名称がいずれも入力されている時。
        if(userIdStr!=null && userIdStr.length()>0){
            if(userNameStr!=null && userNameStr.length()>0){
                sql+="where MedicalOrganization.MedicalOrganizationCd = ? \r\n";
                sql+=" and MedicalOrganization.MedicalOrganizationName like ? \r\n";
                sql+="order by medicalOrganizationCd" + "\r\n";
                sql+="limit "+ limitRow+ "\r\n";
                sql+="offset "+ displayPage + "\r\n";
                flg=1;
            }
        }
        //*-----------------------*//
        // 医療機関コードのみ入力されている時。
        if(userIdStr!=null && userIdStr.length()>0){
            if(userNameStr==null || userNameStr.length()==0){
                sql+="where MedicalOrganization.MedicalOrganizationCd = ? \r\n";
                sql+="order by medicalOrganizationCd \r\n";
                sql+="limit "+ limitRow+ "\r\n";
                sql+="offset "+ displayPage + "\r\n";
                flg=2;
            }
        }
        //*-----------------------*//
        // 医療機関名称のみ入力されている時。
        if(userIdStr==null || userIdStr.length()==0){
            if(userNameStr!=null && userNameStr.length()>0){
                sql+="where MedicalOrganization.MedicalOrganizationName like ? \r\n";
                sql+="order by medicalOrganizationCd \r\n";
                sql+="limit "+ limitRow+ "\r\n";
                sql+="offset "+ displayPage + "\r\n";
                flg=3;
            }
        }
        //*-----------------------*//
        // 全件検索
        if(userIdStr==null || userIdStr.length()==0){
            if(userNameStr==null || userNameStr.length()==0){
                sql+="order by medicalOrganizationCd \r\n";
                sql+="limit "+ limitRow+ "\r\n";
                sql+="offset "+ displayPage + "\r\n";
            }
        }
        
// ---SQL文作成 End---//
        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();   

        if(flg==1){
            preparedStatement.setString(1, userIdStr);
            preparedStatement.setString(2, "%%"+userNameStr+"%%");
        }
        if(flg==2){
            preparedStatement.setString(1, userIdStr);
        }
        if(flg==3){
            preparedStatement.setString(1, "%%"+userNameStr+"%%");
        }

        // SQL実行
        ArrayList<MedicalOrganizationEntity> entity = new ArrayList<>();
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return null;
        }
        while (dataTable.next()) {
            entity.add(MedicalOrganizationEntity.setData(dataTable));
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }

    /**
     * 医療機関情報登録処理
     * @param entity
     * @return
     * @throws Throwable 
     */
    public int InsertMedicalOrganization(MedicalOrganizationEntity entity) throws Throwable{
            
        DataAccessObject dao = new DataAccessObject(connection);
        dao.beginTransaction();
        String sql = getInsertSql();
        
        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1,entity.getMedicalOrganizationCd());   // 医療機関CD
        preparedStatement.setString(2,entity.getMedicalOrganizationName());     // 医療機関名称
        preparedStatement.setString(3,entity.getZipCode());  // 郵便番号
        preparedStatement.setString(4,entity.getAddress());   // 所在地
        preparedStatement.setString(5,entity.getTelNo());     // 電話番号
        preparedStatement.setString(6,entity.getPassword());  // パスワード
        preparedStatement.setBoolean(7,entity.isInitPassword());  //ログイン時パスワード変更フラグ
        preparedStatement.setString(8, null);  // パスワード有効期限
        
        // 登録処理
        int i = preparedStatement.executeUpdate();
        preparedStatement.close();

        return i;
    }
    /**
     * 医療機関情報更新処理
     * @param entity
     * @return
     * @throws Throwable 
     */
    public int UpdateMedicalOrganization(MedicalOrganizationEntity entity) throws Throwable{
            
        DataAccessObject dao = new DataAccessObject(connection);
        dao.beginTransaction();
        StringBuilder sb = new StringBuilder();
        sb.append("update MedicalOrganization set \r\n");
        if (entity.getPassword() != null && !"".equals(entity.getPassword())) {
            sb.append("MedicalOrganizationName = ?, ZipCode = ?, Address = ?, TelNo = ?, Password = ?, InitPassword = ?, PasswordExpirationDate = ? \r\n");
        } else {
            sb.append("MedicalOrganizationName = ?, ZipCode = ?, Address = ?, TelNo = ? \r\n");
        }
        sb.append("where MedicalOrganizationCd = ? \r\n");
        String sql = sb.toString();
        
        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

        preparedStatement.setString(1,entity.getMedicalOrganizationName());     // 医療機関名称
        preparedStatement.setString(2,entity.getZipCode());  // 郵便番号
        preparedStatement.setString(3,entity.getAddress());   // 所在地
        preparedStatement.setString(4,entity.getTelNo());     // 電話番号
        if(entity.getPassword() != null && !"".equals(entity.getPassword())) {
            preparedStatement.setString(5,entity.getPassword());  // パスワード
            preparedStatement.setBoolean(6, entity.isInitPassword());  //ログイン時パスワード変更フラグ
            preparedStatement.setDate(7, TypeUtility.convertDate(entity.getPasswordExpirationDate()));  // パスワード有効期限
            preparedStatement.setString(8, entity.getMedicalOrganizationCd());  // 医療名称CD
        } else {
            preparedStatement.setString(5, entity.getMedicalOrganizationCd());  // 医療名称CD
        }
        
        // 更新処理
        int i = preparedStatement.executeUpdate();
        preparedStatement.close();

        return i;
    }
    
        /**
     * 医療機関コード、名称取得
     * @param userIdStr
     * @param userNameStr
     * @param limitRow
     * @param currentPage
     * @return
     * @throws Throwable 
     */
    public int getMedicalOrganizationInfoCount(String userIdStr, String userNameStr)throws Throwable {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);

        int flg =0;
        int count =0;
        
// ---SQL文作成 Start---//
        String sql = getSelectedSql();
        //*-----------------------*//
        // 医療機関コード、医療機関名称がいずれも入力されている時。
        if(userIdStr!=null && userIdStr.length()>0){
            if(userNameStr!=null && userNameStr.length()>0){
                sql+="where MedicalOrganization.MedicalOrganizationCd = ? \r\n";
                sql+=" and MedicalOrganization.MedicalOrganizationName like ? \r\n";
                flg=1;
            }
        }
        //*-----------------------*//
        // 医療機関コードのみ入力されている時。
        if(userIdStr!=null && userIdStr.length()>0){
            if(userNameStr==null || userNameStr.length()==0){
                sql+="where MedicalOrganization.MedicalOrganizationCd = ? \r\n";
                flg=2;
            }
        }
        //*-----------------------*//
        // 医療機関名称のみ入力されている時。
        if(userIdStr==null || userIdStr.length()==0){
            if(userNameStr!=null && userNameStr.length()>0){
                sql+="where MedicalOrganization.MedicalOrganizationName like ? \r\n";
                flg=3;
            }
        }
// ---SQL文作成 End---//
        dao.setSql(sql);
        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();   

        if(flg==1){
            preparedStatement.setString(1, userIdStr);
            preparedStatement.setString(2, "%%"+userNameStr+"%%");
        }
        if(flg==2){
            preparedStatement.setString(1, userIdStr);
        }
        if(flg==3){
            preparedStatement.setString(1, "%%"+userNameStr+"%%");
        }

        // SQL実行
        ArrayList<MedicalOrganizationEntity> entity = new ArrayList<>();
        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null) {
            return 0;
        }
        while (dataTable.next()) {
            count=dataTable.getRow();
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return count;
    }

}
