/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.dto;

/**
 *
 * @author kis.o-note-003
 */
public class MedicalSearchDto {
	
	/**
	 * 医療機関名称
	 */
	private String userNameStr;
	/**
	 * 医療機関Cd
	 */
	private String userIdStr;
	/**
	 * ページ番号
	 */
	private int pageNo;
        /**
         * 検索件数
         */
        private int totalNum;
        
	public String getUserNameStr() {
		return userNameStr;
	}
	public void setUserNameStr(String userNameStr) {
		this.userNameStr = userNameStr;
	}
	public String getUserIdStr() {
		return userIdStr;
	}
	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
        public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

}
