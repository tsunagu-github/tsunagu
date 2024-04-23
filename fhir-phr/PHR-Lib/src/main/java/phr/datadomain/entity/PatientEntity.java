/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/30
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.base.*;

/**
 * 患者情報のデータオブジェクトです。
 */
public class PatientEntity extends PatientEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PatientEntity.class);
    /* 保険者番号 */
    private String insurerNo = null;
    /* 医療機関コード */
    protected String medicalOrganizationCd = null;
    /* 患者ID */
    protected String patientId = null;
    /* Push用トークン登録有無 */
    protected boolean tokenRegistered = false;
    /* 閲覧同意 */
    protected boolean agreesToShare;
    /* 文字列型生年月日 */
    protected String birthStr = null;
    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public PatientEntity()
    {
    }
    
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
    /* -------------------------------------------------------------------------------------- */
    
    /**
     * 医療機関CDを取得する
     *
     * @return
     */
    public String getMedicalOrganizationCd() {
        return this.medicalOrganizationCd;
    }
    /**
     * 医療機関CDを設定する
     *
     * @param value
     */
    public void setMedicalOrganizationCd(String value) {
        this.medicalOrganizationCd = value;
    }
    
    /**
     * 患者IDを取得する
     *
     * @return
     */
    public String getPatientId() {
        return this.patientId;
    }
    /**
     * 患者IDを設定する
     *
     * @param value
     */
    public void setPatientId(String value) {
        this.patientId = value;
    }

    /**
     * Push用トークン登録有無を取得する
     *
     * @return
     */
    public boolean isTokenRegistered() {
        return this.tokenRegistered;
    }
    /**
     * Push用トークン登録有無を設定する
     *
     * @param tokenRegistered
     */
    public void setTokenRegistered(boolean tokenRegistered) {
        this.tokenRegistered = tokenRegistered;
    }
    
    /**
     * 閲覧同意の有無を取得する
     * @return
     */
    public boolean isAgreesToShare() {
        return agreesToShare;
    }

    /**
     * 閲覧同意の有無を設定する
     * @param agreesToShare
     */
    public void setAgreesToShare(boolean agreesToShare) {
        this.agreesToShare = agreesToShare;
    }

    /**
     * YYYY/MM/DD形式の誕生日文字列を取得する
     *
     * @return
     */
    public String getBirthStr() {
        if(this.birthDate==null){
            return null;
        }else{
            return new SimpleDateFormat("yyyy/MM/dd").format(this.birthDate);
        }
    }
}
