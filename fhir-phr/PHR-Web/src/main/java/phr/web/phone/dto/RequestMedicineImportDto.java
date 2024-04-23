/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おくすりインポートリクエストDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/10/06
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
 * @author kis-note-027_user
 */
public class RequestMedicineImportDto extends RequestBaseDto{
    //インポートテキスト
    private List<MedicineImportDto> importText=null;

    /**
     * @return the importText
     */
    public List<MedicineImportDto> getImportText() {
        return importText;
    }

    /**
     * @param importText the importText to set
     */
    public void setImportText(List<MedicineImportDto> importText) {
        this.importText = importText;
    }    
    
    
//    //インポートテキスト
//    private String importText=null;
//
//    /**
//     * @return the importText
//     */
//    public String getImportText() {
//        return importText;
//    }
//
//    /**
//     * @param importText the importText to set
//     */
//    public void setImportText(String importText) {
//        this.importText = importText;
//    }
    
}
