/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：エンティティークラスの基底クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Entityの基底クラス
 * @author daisuke
 *
 */
public abstract class AbstractEntity {


    /**
     * データベースコネクション
     */
    protected DataAccessObject dao = null;
   
    /**
     * 指定した値を「boolean」型にて返却します。
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException 
     */
    public static boolean getBoolean(ResultSet resultSet, String column) throws SQLException {
    	return resultSet.getBoolean(column.toUpperCase());
    }
   /**
    * 指定した値を「Boolean」型にて返却します。
    * @param resultSet
    * @param column
    * @return
    * @throws SQLException 
    */
    public static Boolean getNullBoolean(ResultSet resultSet, String column) throws SQLException {
    	Object obj = resultSet.getObject(column.toUpperCase());
    	if (obj != null) {
    		return Boolean.valueOf(obj.toString());
    	}
    	return null;
    }
    /**
     * 指定した値を「int」型にて返却します。
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException 
     */
    public static int getInt(ResultSet resultSet, String column) throws SQLException {
    	return resultSet.getInt(column.toUpperCase());
    }
    /**
     * 指定した値を「Integer」型にて返却します。
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException 
     */
    public static Integer getNullInt(ResultSet resultSet, String column) throws SQLException {
    	Object obj = resultSet.getObject(column.toUpperCase());
    	if (obj != null) {
    		return new Integer(obj.toString());
    	}
    	return null;
    }
    /**
     * 指定した値を「long」型にて返却します。
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException 
     */
    public static long getLong(ResultSet resultSet, String column) throws SQLException {
    	return resultSet.getLong(column.toUpperCase());
    }
    /**
     *  指定した値を「Long」型にて返却します。
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException 
     */
    public static Long getNullLong(ResultSet resultSet, String column) throws SQLException {
    	Object obj = resultSet.getObject(column.toUpperCase());
    	if (obj != null) {
    		return new Long(obj.toString());
    	}
    	return null;
    }
    /**
     *  指定した値を「double」型にて返却します。
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException 
     */
    public static double getDouble(ResultSet resultSet, String column) throws SQLException {
    	return resultSet.getDouble(column.toUpperCase());
    }
    /**
     * 指定した値を「Double」型にて返却します。
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException 
     */
    public static Double getNullDouble(ResultSet resultSet, String column) throws SQLException {
    	Object obj = resultSet.getObject(column.toUpperCase());
    	if (obj != null) {
    		return new Double(obj.toString());
    	}
    	return null;
    }
    /**
     * 指定した値を「String」型にて返却します。
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException 
     */
    public static String getString(ResultSet resultSet, String column) throws SQLException {
    	return resultSet.getString(column.toUpperCase());
    }
    /**
     * 指定した値を「Timestamp」型にて返却します。
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException 
     */
    public static Timestamp getDateTime(ResultSet resultSet, String column) throws SQLException {
    	return resultSet.getTimestamp(column.toUpperCase());
    }
    /**
     * 指定した値を「Date」型にて返却します。
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException 
     */
    public static java.util.Date getDate(ResultSet resultSet, String column) throws SQLException {
        java.util.Date date = resultSet.getDate(column.toUpperCase());
    	return date;
    }
    
}