package phr.enums;

import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.CommunicationEntity;
import static phr.datadomain.entity.CommunicationEntity.TargetFlgEnum.values;

/**
 *
 * 都道府県用コード
 */
public enum DataInputTypeCdEnum {

    KENNSA(1, "検査結果"),
    KATEI(2,"家庭測定"),
    KENNSHIN(3,"健診（検査）"),
    MONSHIN(4,"健診（問診）"),
    SHINSATSU(5,"健診（診察）")
    ;

     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DataInputTypeCdEnum.class);

    /*
    * コード
    */
    private final int code;
    /*
    * 名称
    */
    private final String name;

    DataInputTypeCdEnum(final int ancode , String anname) {
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
        for (DataInputTypeCdEnum d : values()) {
            if (d.getCode()==code) {
                return d.getName();
            }
        }
        logger.debug("unknown intValue : " + code);
        return null;
    }

}
