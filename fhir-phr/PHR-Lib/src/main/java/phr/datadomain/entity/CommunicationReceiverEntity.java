/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：コミュニケーション受信者のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * コミュニケーション受信者のデータオブジェクトです。
 */
public class CommunicationReceiverEntity extends CommunicationReceiverEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationReceiverEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public CommunicationReceiverEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

    /* 医療機関名称 */
    protected String medicalOrganizationName = null;
    
    /**
     * 医療機関名称を取得する
     *
     * @return
     */
    public String getMedicalOrganizationName() {
        return this.medicalOrganizationName;
    }
    /**
     * 医療機関名称を設定する
     *
     * @param value
     */
    public void setMedicalOrganizationName(String value) {
        this.medicalOrganizationName = value;
    }
}
