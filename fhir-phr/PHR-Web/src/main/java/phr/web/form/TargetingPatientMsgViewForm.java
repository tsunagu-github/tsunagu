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
public class TargetingPatientMsgViewForm extends AbstractForm {
    /**
     * serialVersionUID
     */
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**　情報 */
    private String information;
    public String getInformation() {
        return information;
    }
    public void setInformation(String information) {
        this.information = information;
    }
}
