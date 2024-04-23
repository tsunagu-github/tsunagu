/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

/**
 *
 * @author KISNOTE011
 */
public class ManagementItemViewForm extends AbstractForm {
    /**
     * 選択年
     */
    private int selectYear;
    /**
     * 選択View
     */
    private String selectView;

    public int getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(int selectYear) {
        this.selectYear = selectYear;
    }

    public String getSelectView() {
        return selectView;
    }

    public void setSelectView(String selectView) {
        this.selectView = selectView;
    }
}
