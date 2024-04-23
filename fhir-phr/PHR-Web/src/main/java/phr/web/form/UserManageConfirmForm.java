/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.datadomain.entity.InsurerEntity;

/**
 *
 * @author kis.o-note-003
 */
public class UserManageConfirmForm extends AbstractForm {
    
    /**
     * 登録成功フラグ
     */
    private boolean successed;

    /**
     * 登録失敗フラグ
     */
    private boolean failed;

    /**
     * @return the successed
     */
    public boolean isSuccessed() {
        return successed;
    }

    /**
     * @param successed the successed to set
     */
    public void setSuccessed(boolean successed) {
        this.successed = successed;
    }

    /**
     * @return the failed
     */
    public boolean isFailed() {
        return failed;
    }

    /**
     * @param failed the failed to set
     */
    public void setFailed(boolean failed) {
        this.failed = failed;
    }
}
