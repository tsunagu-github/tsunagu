/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：自己測定レスポンスDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/12
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
public class ResponseOperationSelfCheckDto extends ResponseBaseDto {

    private SelfCheckListDto addData;

    public SelfCheckListDto getAddData() {
        return addData;
    }

    public void setAddData(SelfCheckListDto addData) {
        this.addData = addData;
    }

}
