/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査会社情報のデータオブジェクト
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
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.LaboratoryEntity;

/**
 * 検査会社情報のデータオブジェクトです。
 */
public abstract class LaboratoryEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(LaboratoryEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public LaboratoryEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* 検査会社CD */
    protected String laboratoryCd = null;
    /* 検査会社名 */
    protected String laboratoryName = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  LaboratoryEntity
     */
    public static LaboratoryEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  LaboratoryEntity
     */
    public static LaboratoryEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        LaboratoryEntity entity = new LaboratoryEntity();
        entity.setLaboratoryCd(getString(dataRow, "LaboratoryCd"));
        entity.setLaboratoryName(getString(dataRow, "LaboratoryName"));

        if (logger.isDebugEnabled())
        {
            logger.debug("検査会社CD：" + entity.getLaboratoryCd());
            logger.debug("検査会社名：" + entity.getLaboratoryName());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * 検査会社CDを取得する
     *
     * @return
     */
    public String getLaboratoryCd() {
        return this.laboratoryCd;
    }
    /**
     * 検査会社CDを設定する
     *
     * @param value
     */
    public void setLaboratoryCd(String value) {
        this.laboratoryCd = value;
    }

    /**
     * 検査会社名を取得する
     *
     * @return
     */
    public String getLaboratoryName() {
        return this.laboratoryName;
    }
    /**
     * 検査会社名を設定する
     *
     * @param value
     */
    public void setLaboratoryName(String value) {
        this.laboratoryName = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "aboratoryCd=" + laboratoryCd + 
                ", aboratoryName=" + laboratoryName + 
                " }\r\n";
    }
}
