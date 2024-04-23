/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おくすりリストリクエストDTO
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
public class RequestMedicineListDto extends RequestBaseDto{

    /**
     * 対象日
     */
    private String baseDate;


    /**対象日
     * @return the baseDate
     */
    public String getBaseDate() {
        return baseDate;
    }

    /**対象日
     * @param baseDate the baseDate to set
     */
    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }
    
    
}
