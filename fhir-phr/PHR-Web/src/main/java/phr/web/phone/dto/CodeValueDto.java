/*
 * コードと値を保持するDTO
 */
package phr.web.phone.dto;

/**
 *
 * @author daisuke
 */
public class CodeValueDto {
 
    /**
     * コード
     */
    private String cd;
    /**
     * 値
     */
    private String val;

    /**
     * デフォルトコンストラクタ
     */
    public CodeValueDto() {
        
    }
    /**
     * コンストラクタ
     * @param cd
     * @param val 
     */
    public CodeValueDto(String cd, String val) {
        this.cd = cd;
        this.val = val;
    }
    /**
     * コード
     * @return the cd
     */
    public String getCd() {
        return cd;
    }

    /**
     * コード
     * @param cd the cd to set
     */
    public void setCd(String cd) {
        this.cd = cd;
    }

    /**
     * 値
     * @return the val
     */
    public String getVal() {
        return val;
    }

    /**
     * 値
     * @param val the val to set
     */
    public void setVal(String val) {
        this.val = val;
    }
    
}
