/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：コミュニケーション情報のデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity;

import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.base.*;

/**
 * コミュニケーション情報のデータオブジェクトです。
 */
public class CommunicationEntity extends CommunicationEntityBase
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationEntity.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public CommunicationEntity()
    {
    }
    /* -------------------------------------------------------------------------------------- */

    /**
     * @return the senderStatus
     */
    public int getSenderStatus() {
        return senderStatus;
    }

    /**
     * @param senderStatus the senderStatus to set
     */
    public void setSenderStatus(int senderStatus) {
        this.senderStatus = senderStatus;
    }

    /**
     * @return the patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    //<editor-fold defaultstate="collapsed" desc="検索対象列挙型">
    public enum TargetFlgEnum {
        INSURER(1),
        MEDICAL(2),
        OTHER(3);

        TargetFlgEnum(final int anIntValue) {
            intValue = anIntValue;
        }
        // enum定数から整数へ変換
        public int getIntValue() {
            return intValue;
        }
        // 整数からenum定数へ変換
        public static TargetFlgEnum valueOf(final int anIntValue) {
            for (TargetFlgEnum d : values()) {
                if (d.getIntValue() == anIntValue) {
                    return d;
                }
            }
            logger.debug("unknown intValue : " + anIntValue);
            return null;
        }

        private final int intValue;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="直近検索時の他テーブルの追加必要情報">
    // 既読フラグ
    private boolean readFlg;
    public boolean getReadFlg(){
        return this.readFlg;
    }
    public void setReadFlg(boolean readFlg){
        this.readFlg=readFlg;
    }

    // 送信者組織名
    private String sendOrganizationName;
    public String getSendOrganizationName(){
        return this.sendOrganizationName;
    }
    public void setSendOrganizationName(String sendOrganizationName){
        this.sendOrganizationName=sendOrganizationName;
    }

    // SEQ(レシーバー連番）
    private int seqNo;
    public int getSeqNo(){
        return this.seqNo;
    }
    public void setSeqNo(int seqNo){
        this.seqNo=seqNo;
    }
 
    /* PHRID */
    protected String phrid = null;
    public String getPhrid(){
        return this.phrid;
    }
    public void setPhrid(String phrid){
        this.phrid=phrid;
    }
    
    /* 保険者 */
    protected String insurerNo = null;
    public String getInsurerNo(){
        return this.insurerNo;
    }
    public void setInsurerNo(String insurerNo){
        this.insurerNo=insurerNo;
    }

    /* 医療機関 */
    protected String medicalOrganizationCd = null;
    public String getMedicalOrganizationCd(){
        return this.medicalOrganizationCd;
    }
    public void setMedicalOrganizationCd(String medicalOrganizationCd){
        this.medicalOrganizationCd=medicalOrganizationCd;
    }

    /* 更新日時 */
    protected Timestamp recUpdateDateTime = null;
    public Timestamp getRecUpdateDateTime(){
        return this.recUpdateDateTime;
    }
    public void setRecUpdateDateTime(Timestamp recUpdateDateTime){
        this.recUpdateDateTime=recUpdateDateTime;
    }
    
    // 医療機関送信メッセージ時 同時送信PHRID
    private String recPhrId;
    public String getRecPhrId(){
        return this.recPhrId;
    }
    public void setRecPhrId(String recPhrId){
        this.recPhrId=recPhrId;
    }
    
    // 医療機関送信メッセージ時 同時送信患者名称
    private String recPhrName;
    public String getRecPhrName(){
        return this.recPhrName;
    }
    public void setRecPhrName(String recPhrName){
        this.recPhrName=recPhrName;
    }
   //</editor-fold>
    
    /*
    * 送信者の情報
    * 保険者が送信(1)
    * 医療機関が送信(2)
    * 患者が送信(3)
    */
    private int senderStatus;

    /* 患者ID */
    private String patientId = null;

}
