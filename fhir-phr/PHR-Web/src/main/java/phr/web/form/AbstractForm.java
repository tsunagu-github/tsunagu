/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;

/**
 * フォーム基底クラス
 * @author KISNOTE011
 *
 */
public abstract class AbstractForm {
    /**
     * コマンド
     */
    private String command;
    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    
    /**
     * 画面ID
     */
    private String formCd;
    public String getFormCd() {
        return formCd;
    }
    public void setFormCd(String formCd) {
        this.formCd = formCd;
    }
    
    /**
     * 画面名
     */
    private String formName;
    public String getFormName() {
        return formName;
    }
    public void setFormName(String formName) {
        this.formName = formName;
    }
    
    /**
     * ページ数
     */
    private List<Integer> page;
    public List<Integer> getPage() {
        return page;
    }
    public void setPage(List<Integer> page) {
        this.page = page;
    }
	
    /**
     * クリックしたページの値
     */
    private int clickedPage;
    public int getClickedPage() {
        return clickedPage;
    }
    public void setClickedPage(int clickedPage) {
        this.clickedPage = clickedPage;
    }

    private String param1;
    private String param2;
    private String param3;
    public String getParam1() {
        return param1;
    }
    public void setParam1(String param1) {
        this.param1 = param1;
    }
    public String getParam2() {
        return param2;
    }
    public void setParam2(String param2) {
        this.param2 = param2;
    }
    public String getParam3() {
        return param3;
    }
    public void setParam3(String param3) {
        this.param3 = param3;
    }
}
