/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;
import  java.util.ArrayList;

public class MedicalCreateConfirmForm extends AbstractForm {

    /**
     * 登録成功フラグ
     */
    private boolean sucessed;

    /**
     * @return the sucessed
     */
    public boolean isSucessed() {
        return sucessed;
    }

    /**
     * @param sucessed the sucessed to set
     */
    public void setSucessed(boolean sucessed) {
        this.sucessed = sucessed;
    }
}
