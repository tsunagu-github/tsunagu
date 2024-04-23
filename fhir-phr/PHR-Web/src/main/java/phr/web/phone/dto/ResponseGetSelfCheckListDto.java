/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：患者連携設定一覧レスポンスDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

import java.util.List;

/**
 *
 * @author chiba
 */
public class ResponseGetSelfCheckListDto extends ResponseBaseDto {
 
    /**
     * 患者連携設定一覧
     */
    private List<SelfCheckListDto> selfCheckList;

    /**
     * 自己測定一覧取得
     * @return 
     */
    public List<SelfCheckListDto> getSelfCheckList(){
        return selfCheckList;
    }
    
    /**
     * 自己測定一覧設定
     * @param value 
     */
    public void setCooperationList( List<SelfCheckListDto>value ){
        this.selfCheckList = value;
    }
   
}
