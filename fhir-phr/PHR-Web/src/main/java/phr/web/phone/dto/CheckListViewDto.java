/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果を格納するDTOクラス
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
public class CheckListViewDto extends ResponseBaseDto {
    
    /**
     * 確認項目
     */
    private String checkItem;
    
    /**
     * 選択状態（0:未選択、1:選択）
     */
    private String selectStatus;

	/**
	 * @return checkItem
	 */
	public String getCheckItem() {
		return checkItem;
	}

	/**
	 * @param checkItem セットする checkItem
	 */
	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}

	/**
	 * @return selectStatus
	 */
	public String getSelectStatus() {
		return selectStatus;
	}

	/**
	 * @param selectStatus セットする selectStatus
	 */
	public void setSelectStatus(String selectStatus) {
		this.selectStatus = selectStatus;
	}

}
