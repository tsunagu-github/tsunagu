/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.search.dto;

/**
 * SS-MIX2を検索するときの検索条件Dto
 * 検索結果はモデルで返すものとする
 * @author kis-note
 */
public class SSMIXSearchDto {

    /**
     * @return the datecount
     */
    public Integer getDatecount() {
        return datecount;
    }

    /**
     * @param datecount the datecount to set
     */
    public void setDatecount(Integer datecount) {
        this.datecount = datecount;
    }

    /**
     * 検索タイプ
     * 1:患者単位の検索
     * 2:集団検索（ストレージにある患者すべてを対象とする）
     */
    private Integer searchType;
    
    /**
     * 標準化ストレージのルートパス
     */
    private String stdRootPath;
    
    /**
     * 拡張ストレージのルートパス
     */
    private String exRootPath;
    
    /**
     * 対象患者
     * 集団検索時は利用しない
     */
    private String patientId;
    
    /**
     * 検索対象のデータ種別
     * 例外としてALL、STD、EXTと入れると基準日基準でその患者の情報をすべて取得する
     * 2016/12　ALLで取得するのは
     * ADT-00（患者基本) OML-11(検体検査結果 OMP-01(処方オーダ）
     * 診療情報（拡張ストレージ）　お薬情報（拡張ストレージ）
     * 
     * 2016/12　STDで取得するのは
     * ADT-00（患者基本) OML-11(検体検査結果 OMP-01(処方オーダ）
     *
     * 2016/12　EXTで取得するのは
     * 診療情報（拡張ストレージ）　お薬情報（拡張ストレージ）
     */
    private String dataType;
    
    /**
     * 基準日
     * 対象のデータ種別が日付管理されていない場合のみnullを認める
     */
    private String baseDate;
    
    /**
     * 検索方向
     * 1:過去検索　2:未来検索
     */
    private Integer searchDirection;
    
    /**
     * 遡及回数
     * 回数のカウントはデータ種別単位で行う
     */
    private Integer count;

    /**
     * 遡及日数
     * 回数のカウントはデータ種別単位で行う
     */
    private Integer datecount;

    /**
     * @return the searchType
     */
    public Integer getSearchType() {
        return searchType;
    }

    /**
     * @param searchType the searchType to set
     */
    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    /**
     * @return the stdRootPath
     */
    public String getStdRootPath() {
        return stdRootPath;
    }

    /**
     * @param stdRootPath the stdRootPath to set
     */
    public void setStdRootPath(String stdRootPath) {
        this.stdRootPath = stdRootPath;
    }

    /**
     * @return the exRootPath
     */
    public String getExRootPath() {
        return exRootPath;
    }

    /**
     * @param exRootPath the exRootPath to set
     */
    public void setExRootPath(String exRootPath) {
        this.exRootPath = exRootPath;
    }

    /**
     * @return the patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the baseDate
     */
    public String getBaseDate() {
        return baseDate;
    }

    /**
     * @param baseDate the baseDate to set
     */
    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    /**
     * @return the searchDirection
     */
    public Integer getSearchDirection() {
        return searchDirection;
    }

    /**
     * @param searchDirection the searchDirection to set
     */
    public void setSearchDirection(Integer searchDirection) {
        this.searchDirection = searchDirection;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }
    
    
}
