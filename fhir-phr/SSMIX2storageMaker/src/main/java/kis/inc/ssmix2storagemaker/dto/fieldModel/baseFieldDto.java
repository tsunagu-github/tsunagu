/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.fieldModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class baseFieldDto {
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(baseFieldDto.class);

    /**
     * 副成分セパレータ
     */
    private String subcompSp;

    /**
     * @return the subcompSp
     */
    public String getSubcompSp() {
        return subcompSp;
    }

    /**
     * @param subcompSp the subcompSp to set
     */
    public void setSubcompSp(String subcompSp) {
        this.subcompSp = subcompSp;
    }
}
