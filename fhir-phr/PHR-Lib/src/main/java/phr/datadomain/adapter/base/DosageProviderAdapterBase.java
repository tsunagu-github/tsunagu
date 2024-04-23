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
package phr.datadomain.adapter.base;

import java.sql.Connection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author iwaasa
 */
public abstract class DosageProviderAdapterBase {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DosageProviderAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public DosageProviderAdapterBase(Connection conn)
    {
        connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 健診日リスト抽出用SQLを返却します。
     * @return
     */
    protected static String getDosageProviderSqlLeft() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    DosageMedicalOrganization.DosageTypeCd As DosageTypeCd  \r\n");
        sql.append("    ,DosageMedicalOrganization.MedicalOrganizationName As MedicalOrganizationName  \r\n");
        sql.append("    ,DosageDoctor.DoctorName As DoctorName  \r\n");
        sql.append("    ,DosageDoctor.DepartmentName As DepartmentName  \r\n");
        sql.append("from DosageMedicalOrganization \r\n");
        sql.append("    Left outer join DosageDoctor \r\n");
        sql.append("    on DosageMedicalOrganization.DosageId = DosageDoctor.DosageId \r\n");
        sql.append("    And DosageMedicalOrganization.Seq =  DosageDoctor.Seq \r\n");
        sql.append("    And DosageMedicalOrganization.DosageTypeCd = DosageDoctor.DosageTypeCd \r\n");
        sql.append("where DosageMedicalOrganization.DosageId = ? \r\n");
        sql.append("    and DosageMedicalOrganization.Seq = ? \r\n");
        sql.append("    and DosageMedicalOrganization.DosageTypeCd = ? \r\n");
        
        return sql.toString();
    }
    /**
     * 健診日リスト抽出用SQLを返却します。
     * @return
     */
    protected static String getDosageProviderSqlRight() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    DosageDoctor.DosageTypeCd As DosageTypeCd  \r\n");
        sql.append("    ,DosageMedicalOrganization.MedicalOrganizationName As MedicalOrganizationName  \r\n");
        sql.append("    ,DosageDoctor.DoctorName As DoctorName  \r\n");
        sql.append("    ,DosageDoctor.DepartmentName As DepartmentName  \r\n");
        sql.append("from DosageMedicalOrganization \r\n");
        sql.append("    Right outer join DosageDoctor \r\n");
        sql.append("    on DosageMedicalOrganization.DosageId = DosageDoctor.DosageId \r\n");
        sql.append("    And DosageMedicalOrganization.Seq =  DosageDoctor.Seq \r\n");
        sql.append("    And DosageMedicalOrganization.DosageTypeCd = DosageDoctor.DosageTypeCd \r\n");
        sql.append("where DosageDoctor.DosageId = ? \r\n");
        sql.append("    and DosageDoctor.Seq = ? \r\n");
        sql.append("    and DosageDoctor.DosageTypeCd = ? \r\n");
        
        return sql.toString();
    }
    
}
