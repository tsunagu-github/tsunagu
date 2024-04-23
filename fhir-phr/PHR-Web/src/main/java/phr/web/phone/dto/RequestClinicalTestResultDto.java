/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果取得リクエストDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

/**
 *
 * @author daisuke
 */
public class RequestClinicalTestResultDto extends RequestBaseDto {

    /**
     * 対象日
     */
    private String targetDate;

    /**
     * 対象日
     *
     * @return the targetDate
     */
    public String getTargetDate() {
        return targetDate;
    }

    /**
     * 対象日
     *
     * @param targetDate the targetDate to set
     */
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

}
