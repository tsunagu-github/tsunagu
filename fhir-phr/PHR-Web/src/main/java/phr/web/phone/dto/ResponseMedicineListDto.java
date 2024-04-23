/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：おくすりリストレスポンスDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/07
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
 * @author iwaasa
 */
public class ResponseMedicineListDto extends ResponseBaseDto{
    
    protected List<MedicineDosageDto> dosageList;
    
    /**
     * dosageList取得
     * @return List<MedicineDosageDto>
     */
    public List<MedicineDosageDto> getDosageList() {
        return dosageList;
    }

    /**
     * dosageList設定
     * @param dosageList(List<MedicineDosageDto>) 
     */
    public void setDosageList(List<MedicineDosageDto> dosageList) {
        this.dosageList = dosageList;
    }    
        
    
//    protected List<MedicineDosageDto> dosageList;
//    
//    /**
//     * dosageList取得
//     * @return List<MedicineDosageDto>
//     */
//    public List<MedicineDosageDto> getDosageList() {
//        return dosageList;
//    }
//
//    /**
//     * dosageList設定
//     * @param dosageList(List<MedicineDosageDto>) 
//     */
//    public void setDosageList(List<MedicineDosageDto> dosageList) {
//        this.dosageList = dosageList;
//    }    
    
}
