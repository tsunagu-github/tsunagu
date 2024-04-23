/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おくすり更新リクエストDTO
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
public class RequestMedicineUpdateDto extends RequestBaseDto{
    private MedicineEntryItemDto entryItem = null;

    /**
     * @return the entryItem
     */
    public MedicineEntryItemDto getEntryItem() {
        return entryItem;
    }

    /**
     * @param entryItem the entryItem to set
     */
    public void setEntryItem(MedicineEntryItemDto entryItem) {
        this.entryItem = entryItem;
    }
    
}
