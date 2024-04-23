/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：スマートフォンアプリ対応健診（健診・問診・診察）結果のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/08
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.sql.ResultSet;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import static phr.datadomain.AbstractEntity.getString;
import phr.datadomain.entity.PhoneCUValueEntity;


/**
 *
 * @author iwaasa
 */
public abstract class PhoneCUValueEntityBase extends AbstractEntity{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PhoneCUValueEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public  PhoneCUValueEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */
    
    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 調剤日付 */
    protected Date examdate = null;
    /* 健診値 */
    protected String value = null;
    /*健診値Org*/
    protected String valueOrg = null;

    /* -------------------------------------------------------------------------------------- */
    
        /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageEntity
     */
    public static PhoneCUValueEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  DosageEntity
     */
    public static PhoneCUValueEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        PhoneCUValueEntity entity = new PhoneCUValueEntity();
        entity.setExamdate(getDate(dataRow, "Examdate"));
        entity.setValue(getString(dataRow, "Value"));

        if (logger.isDebugEnabled())
        {

            logger.debug("健診日    ：" + entity.getExamdate());
            logger.debug("健診値       ：" + entity.getValue());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 健診日を取得する
     *
     * @return
     */
    public Date getExamdate() {
        return this.examdate;
    }
    /**
     * 健診日を設定する
     *
     * @param value
     */
    public void setExamdate(Date value) {
        this.examdate = value;
    }

    /**
     * 項目値を取得する
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }
    /**
     * 項目値を設定する
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 項目値Orgを取得する
     *
     * @return
     */
    public String getValueOrg() {
        return this.valueOrg;
    }
    /**
     * 項目値Orgを設定する
     *
     * @param value
     */
    public void setValueOrg(String valueorg) {
        this.valueOrg = valueorg;
    }    
    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "xamdate=" + examdate + 
                ", alue=" + value + 
                " }\r\n";
    }
    
}
