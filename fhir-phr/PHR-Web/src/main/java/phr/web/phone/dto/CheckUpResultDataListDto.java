
package phr.web.phone.dto;

import java.util.List;
/**
 *
 * @author iwaasa
 */
public class CheckUpResultDataListDto{
    
    //健診項目（表示用）
    private String title=null;
    //単位
    private String unit=null;
    //健診値リスト
    private List<CheckUpResultValueDto> results;
    
    /**
     * title取得
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * inspectionType設定
     * @param title(String)
     */
    public void setTitle(String title) {
        this.title = title;
    }      
    
    /**
     * unit取得
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * unit設定
     * @param unit(String)
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    /**
     * results取得
     * @return results
     */
    public List<CheckUpResultValueDto> getResults() {
        return results;
    }

    /**
     * results設定
     * @param results(CheckUpResultValueDto[])
     */
    public void setResults(List<CheckUpResultValueDto> results) {
        this.results = results;
    }      
       
}
