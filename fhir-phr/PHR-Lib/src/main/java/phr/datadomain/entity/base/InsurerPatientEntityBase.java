/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：保険者患者関連情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.InsurerPatientEntity;

/**
 * 保険者患者関連情報のデータオブジェクトです。
 */
public abstract class InsurerPatientEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(InsurerPatientEntityBase.class);
    private static Logger logger = Logger.getLogger(InsurerPatientEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public InsurerPatientEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 保険者番号 */
    protected String insurerNo = null;
    /* PHR ID */
    protected String pHR_ID = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  InsurerPatientEntity
     */
    public static InsurerPatientEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  InsurerPatientEntity
     */
    public static InsurerPatientEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.debug("Start");
        InsurerPatientEntity entity = new InsurerPatientEntity();
        entity.setInsurerNo(getString(dataRow, "InsurerNo"));
        entity.setPHR_ID(getString(dataRow, "PHR_ID"));

        if (logger.isDebugEnabled())
        {
            logger.debug("保険者番号：" + entity.getInsurerNo());
            logger.debug("PHR ID    ：" + entity.getPHR_ID());
        }
        logger.debug("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 保険者番号を取得する
     *
     * @return
     */
    public String getInsurerNo() {
        return this.insurerNo;
    }
    /**
     * 保険者番号を設定する
     *
     * @param value
     */
    public void setInsurerNo(String value) {
        this.insurerNo = value;
    }

    /**
     * PHR IDを取得する
     *
     * @return
     */
    public String getPHR_ID() {
        return this.pHR_ID;
    }
    /**
     * PHR IDを設定する
     *
     * @param value
     */
    public void setPHR_ID(String value) {
        this.pHR_ID = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "nsurerNo=" + insurerNo + 
                ", HR_ID=" + pHR_ID + 
                " }\r\n";
    }
}
