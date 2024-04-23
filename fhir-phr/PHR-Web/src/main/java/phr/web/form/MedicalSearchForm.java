/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

public class MedicalSearchForm extends AbstractForm {
    /**
     * serialVersionUID
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     */
    public MedicalSearchForm(){};
    
    /**
     * コンストラクタ（医療機関CD、医療機関名称）
     * @param userIdStr     医療機関CD
     * @param userNameStr   医療機関名称
     */
    public MedicalSearchForm(String userIdStr,String userNameStr){
        this.userIdStr = userIdStr;
        this.userNameStr=userNameStr;
    }
    /**
     *  医療機関CD
     */
    private String userIdStr;
    public String getUserIdStr(){
        return userIdStr;
    }
    
    public void setUserIdStr(String userIdStr){
        this.userIdStr = userIdStr;
    }
    
    /**
     * 医療機関名称
     */
    private String userNameStr;
    public String getUserNameStr(){
        return  userNameStr;
    }
    
    public void setUserNameStr(String userNameStr){
        this.userNameStr = userNameStr;
    }
    
    /**
     * ページ数(改ページ用)
     */
    private int currentPage;
    public int getCurrentPage(){
        return  currentPage;
    }
    
    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
    
    /**
     * トータル件数
     */
    private int totalNum;
    public int getTotalNum(){
        return  totalNum;
    }
    public void setTotalNum(int totalNum){
        this.totalNum = totalNum;
    }
    
    /**
     * 選択したリストのINDEX
     */
    private String index;
    public String getIndex() {
    	return index;
    }
    public void setIndex(String index) {
    	this.index = index;
    }

    /**
     * 次ページ有無
     */
    private boolean nextPage;
    public boolean isNextPage() {
    	return nextPage;
    }
    public void setNextPage(boolean nextPage) {
    	this.nextPage = nextPage;
    }
        
    /**
     * 前ページ有無
     */
    private boolean prePage;
    public boolean isPrePage() {
    	return prePage;
    }
    public void setPrePage(boolean prePage) {
    	this.prePage = prePage;
    }
    
    /**
     * 次ページ番号
     */
    private int nextPageNo;
    public int getNextPageNo() {
    	return nextPageNo;
    }
    public void setNextPageNo(int nextPageNo) {
    	this.nextPageNo = nextPageNo;
    }
    
    /**
     * 前ページ番号
     */
    private int prePageNo;
    public int getPrePageNo() {
    	return prePageNo;
    }
    public void setPrePageNo(int prePageNo) {
    	this.prePageNo = prePageNo;
    }
    
}
