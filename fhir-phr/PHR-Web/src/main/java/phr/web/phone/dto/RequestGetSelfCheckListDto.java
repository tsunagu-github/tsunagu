/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者連携設定一覧リクエストDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/09
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

/**
 *
 * @author chiba
 */
public class RequestGetSelfCheckListDto extends RequestBaseDto {
    
    protected String agoDays = null;

    public String getAgoDays() {
        return agoDays;
    }

    public void setAgoDays(String agoDays) {
        this.agoDays = agoDays;
    }

}
