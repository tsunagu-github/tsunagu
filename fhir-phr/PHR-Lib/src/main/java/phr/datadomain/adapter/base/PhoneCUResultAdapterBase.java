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
package phr.datadomain.adapter.base;

import java.sql.Connection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author iwaasa
 */
public abstract class PhoneCUResultAdapterBase {


    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PhoneCUResultAdapterBase.class);

    /**
     * データベースコネクション
     */
    protected Connection connection = null;

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     * @param conn
     */
    public PhoneCUResultAdapterBase(Connection conn)
    {
        connection = conn;
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * 健診日リスト抽出用SQLを返却します。
     * @return
     */
    protected static String getYearListSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    Distinct ExaminationDate As ExaminationDate  \r\n");
        sql.append("from ObservationEvent \r\n");
        sql.append("where PHR_ID = ? \r\n");
        sql.append("    and DataInputTypeCd = ? \r\n");
        sql.append("order by ExaminationDate DESC \r\n");
        return sql.toString();
    }

    /**
     * 検査項目リスト抽出用SQLを返却します。
     * @return
     */
    protected static String getItemListSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  distinct\r\n");
        sql.append("    ObservationDefinitionType.ObservationDefinitionId As ObservationDefinitionId \r\n");
        sql.append("    , ObservationDefinition.DisplayName As DisplayName \r\n");
//        sql.append("    , ObservationDefinitionInsurer.UnitValue As UnitValue \r\n");
        sql.append("    , ObservationDefinitionType.UnitValue As UnitValue \r\n");
//        sql.append("    , ObservationDefinition.SortNo As SortNo \r\n");
        sql.append("    , ObservationDefinitionInsurer.SortNo As SortNo \r\n");
        sql.append("from ObservationDefinitionType \r\n");
        sql.append("    inner join  ObservationDefinition on ObservationDefinitionType.ObservationDefinitionId = ObservationDefinition.ObservationDefinitionId \r\n");
        sql.append("    inner join ObservationDefinitionInsurer on ObservationDefinitionType.ObservationDefinitionId = ObservationDefinitionInsurer.ObservationDefinitionId \r\n");
        sql.append("where ObservationDefinitionType.DataInputTypeCd = ? \r\n");
        sql.append("and ObservationDefinitionInsurer.ViewId = '7' \r\n");
//        sql.append("and ObservationDefinitionInsurer.InsurerNo = ? \r\n");
//        sql.append("order by ObservationDefinitionInsurer.SortNo\r\n");
        return sql.toString();
    }    

    /**
     * 検査値リスト抽出用SQLを返却します。
     * @return
     */
    protected static String getValueListSelectedSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  \r\n");
        sql.append("    ObservationEvent.ExaminationDate As Examdate \r\n");
        sql.append("    , Observation.Value As Value \r\n");
        sql.append("    , Observation.MinReferenceValue As MinReferenceValue \r\n");
        sql.append("    , Observation.MaxReferenceValue As MaxReferenceValue \r\n");
        sql.append("from ObservationEvent \r\n");
        sql.append("    inner join  Observation on ObservationEvent.ObservationEventId = Observation.ObservationEventId \r\n");
        sql.append("where ObservationEvent.PHR_ID = ? \r\n");
        sql.append("    and ObservationEvent.DataInputTypeCd = ? \r\n");
        sql.append("    and Observation.ObservationDefinitionId = ? \r\n");
        sql.append("order by ObservationEvent.ExaminationDate DESC \r\n");
        return sql.toString();
    }   
}
