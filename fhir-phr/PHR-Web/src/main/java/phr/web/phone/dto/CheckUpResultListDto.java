package phr.web.phone.dto;

import java.util.List;

/**
 *
 * @author kis-note-027_user
 */
public class CheckUpResultListDto {
    
    //健診の種類
    protected String inspectionType=null;
    //健診年月リスト
    protected List<String> yearList;
    //健診値リスト
    protected List<CheckUpResultDataListDto> dataList;
    //健診実施日
    protected String examDate;
    
    /**
     * inspectionType取得
     *
     * @return the inspectionType(健診：3 問診：4 診察：5)
     */
    public String getInspectionType() {
        return inspectionType;
    }

    /**
     * inspectionType設定
     *
     * @param inspectionType the inspectionType to set
     * (健診：3 問診：4 診察：5)
     */
    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }    
    
    /**
     * yearList取得
     * @return List<String>
     */
    public List<String> getYearList() {
        return yearList;
    }

    /**
     * yearList設定
     * @param yearList(List<String>) 
     */
    public void setYearList(List<String> yearList) {
        this.yearList = yearList;
    }
    
    /**
     * dataList取得
     * @return List<CheckUpResultDataListDto>
     */
    public List<CheckUpResultDataListDto> getDataList() {
        return dataList;
    }

    /**
     * dataList設定
     * @param dataList(List<CheckUpResultDataListDto>) 
     */
    public void setDataList(List<CheckUpResultDataListDto> dataList) {
        this.dataList = dataList;
    }
    
    /**
     * examDate取得
     * @return String
     */
    public String getExamDate() {
        return examDate;
    }

    /**
     * examDate設定
     * @param examDate(String) 
     */
    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }
}
