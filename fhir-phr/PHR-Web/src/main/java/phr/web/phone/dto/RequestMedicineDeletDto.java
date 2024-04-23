/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おくすり削除リクエストDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/07
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

/**
 *
 * @author iwaasa
 */
public class RequestMedicineDeletDto extends RequestBaseDto{
    //調剤ID
    private String dosageId = null;
    //調剤番号
//    private String seq = null;


    /**
     * @return the dosageId
     */
    public String getDosageId() {
        return dosageId;
    }

    /**
     * @param dosageId the dosageId to set
     */
    public void setDosageId(String dosageId) {
        this.dosageId = dosageId;
    }

//    /**
//     * @return the seq
//     */
//    public String getSeq() {
//        return seq;
//    }
//
//    /**
//     * @param seq the sec to set
//     */
//    public void setSeq(String seq) {
//        this.seq = seq;
//    }
}
