
package phr.web.phone.dto;

/**
 *
 * @author iwaasa
 */
public class CheckUpResultValueDto {
    
    //健診日付
    private String examdate=null;
    //健診値
    private String data=null;
    //基準値
    private String referenceValue = null;
    //結果値文字色
    private String fontColor = "#000000";
    //結果値背景色
    private String backgroundColor = null;
    
    /**
     * examdate取得
     * @return examdate
     */
    public String getExamdate() {
        return examdate;
    }

    /**
     * examdate設定
     * @param examdate(String)
     */
    public void setExamdate(String examdate) {
        this.examdate = examdate;
    }      
    
    /**
     * data取得
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * data設定
     * @param data(String)
     */
    public void setData(String data) {
        this.data = data;
    }

	/**
	 * @return referenceValue
	 */
	public String getReferenceValue() {
		return referenceValue;
	}

	/**
	 * @param referenceValue セットする referenceValue
	 */
	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}

	/**
	 * @return fontColor
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor セットする fontColor
	 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * @return backgroundColor
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor セットする backgroundColor
	 */
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}