/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者特記事項のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.adapter.base;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import java.sql.PreparedStatement;
import java.sql.Types;
import phr.datadomain.DataAccessObject;
import phr.utility.TypeUtility;

import phr.datadomain.entity.PatientSpecialInstructionEntity;

/**
 * 患者特記事項のデータオブジェクトです。
 */
public abstract class PatientSpecialInstructionAdapterBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientSpecialInstructionAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public PatientSpecialInstructionAdapterBase(Connection conn)
    {
        connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */


    /**
     * 抽出用SQLを返却します。
     * @return
     */
    protected static String getSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    PatientSpecialInstruction.DosageId As DosageId  \r\n");
        sql.append("    , PatientSpecialInstruction.Seq As Seq  \r\n");
        sql.append("    , PatientSpecialInstruction.TypeCd As TypeCd  \r\n");
        sql.append("    , PatientSpecialInstruction.NoteValue As NoteValue  \r\n");
        sql.append("    , PatientSpecialInstruction.RecordCreatorType As RecordCreatorType  \r\n");
        sql.append("from PatientSpecialInstruction \r\n");
        return sql.toString();
    }

    /**
     * 新規追加SQLを返却します。
     * @return
     */
    protected static String getInsertSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into PatientSpecialInstruction \r\n");
        sql.append("(DosageId, Seq, TypeCd, NoteValue, RecordCreatorType) \r\n");
        sql.append("values (?, ?, ?, ?, ?) \r\n");
        return sql.toString();
    }

    /**
     * 更新SQLを返却します。
     * @return
     */
    protected static String getUpdateSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("update PatientSpecialInstruction set \r\n");
        sql.append("TypeCd = ?, NoteValue = ?, RecordCreatorType = ? \r\n");
        sql.append("where DosageId = ? AND Seq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * 削除SQLを返却します。
     * @return
     */
    protected static String getDeleteSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from  PatientSpecialInstruction \r\n");
        sql.append("where DosageId = ? AND Seq = ? \r\n");
        sql.append(" \r\n");
        return sql.toString();
    }

    /**
     * オブジェクトの値データベースに登録します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int insert(PatientSpecialInstructionEntity entity) throws Throwable  
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getInsertSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();


            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getDosageId());
            }
            preparedStatement.setInt(2, entity.getSeq());
            if (entity.getTypeCd() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getTypeCd());
            }
            if (entity.getNoteValue() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getNoteValue());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(5, Types.VARCHAR );
            } else {
                preparedStatement.setString(5, entity.getRecordCreatorType());
            }

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();

        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にてデータベースを更新します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int update(PatientSpecialInstructionEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getUpdateSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getTypeCd() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getTypeCd());
            }
            if (entity.getNoteValue() == null ) {
                preparedStatement.setNull(2, Types.VARCHAR );
            } else {
                preparedStatement.setString(2, entity.getNoteValue());
            }
            if (entity.getRecordCreatorType() == null ) {
                preparedStatement.setNull(3, Types.VARCHAR );
            } else {
                preparedStatement.setString(3, entity.getRecordCreatorType());
            }
            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(4, Types.VARCHAR );
            } else {
                preparedStatement.setString(4, entity.getDosageId());
            }
            preparedStatement.setInt(5, entity.getSeq());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }


    /**
     * オブジェクトの値にて患者特記事項の情報を削除します。
     * @param entity
     * @return
     * @throws Throwable
     */
    public int delete(PatientSpecialInstructionEntity entity) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getDeleteSql();
        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();

            if (entity.getDosageId() == null ) {
                preparedStatement.setNull(1, Types.VARCHAR );
            } else {
                preparedStatement.setString(1, entity.getDosageId());
            }
            preparedStatement.setInt(2, entity.getSeq());

        int rowCount = preparedStatement.executeUpdate();
        dao.clearBindParam();
        preparedStatement.close();
        logger.trace("End");
        return rowCount;
    }

    /**
     * 主キーにて患者特記事項を検索します。
     * @param key1
     * @param key2
     * @return
     * @throws Throwable
     */
    public PatientSpecialInstructionEntity findByPrimaryKey(String key1, int key2) throws Throwable 
    {
        logger.trace("Start");
        DataAccessObject dao = new DataAccessObject(connection);
        String sql = getSelectedSql();
        sql += " where PatientSpecialInstruction.DosageId = ?";
        sql += "   and PatientSpecialInstruction.Seq = ?";

        dao.setSql(sql);

        dao.clearBindParam();
        PreparedStatement preparedStatement = dao.getPreparedStatement();
        preparedStatement.setString(1, key1);
        preparedStatement.setInt(2, key2);

        PatientSpecialInstructionEntity entity = null;

        ResultSet dataTable = preparedStatement.executeQuery();
        if (dataTable == null)
        {
            return null;
        }

        while( dataTable.next() ) {
            entity = PatientSpecialInstructionEntity.setData(dataTable);
        }
        dao.clearBindParam();
        dataTable.close();
        preparedStatement.close();
        logger.trace("End");
        return entity;
    }
}
