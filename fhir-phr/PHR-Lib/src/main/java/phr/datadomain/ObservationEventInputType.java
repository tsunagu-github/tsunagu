/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.enums.DataInputTypeCdEnum;
import static phr.enums.DataInputTypeCdEnum.values;

/**
 *
 * @author daisuke
 */
public enum ObservationEventInputType {
    KENNSA(1, "検査結果"),
    KATEI(2,"家庭測定"),
    KENNSHIN(3,"健診（検査）"),
    ;
    
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationEventInputType.class);

    /*
    * コード
    */
    private final int code;
    /*
    * 名称
    */
    private final String name;

    ObservationEventInputType(final int ancode , String anname) {
        code = ancode;
        name = anname;
    }

    // enum定数から整数へ変換
    public String getName() {
        return name;
    }

    // enum定数から整数へ変換
    public int getCode() {
        return code;
    }



    // codeからnameを取得する
    public static String selectName(int code) {
        for (ObservationEventInputType d : values()) {
            if (d.getCode()==code) {
                return d.getName();
            }
        }
        logger.debug("unknown intValue : " + code);
        return null;
    }    
}
