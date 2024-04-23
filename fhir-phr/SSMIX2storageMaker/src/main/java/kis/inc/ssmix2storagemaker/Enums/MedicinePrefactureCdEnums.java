/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Enums;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public enum MedicinePrefactureCdEnums {
    
    HOKKAIDO("01", "北海道"),
    AOMORI("02","青森県"),
    IWATE("03","岩手県"),
    MIYAGI("04","宮城県"),
    AKITA("05","秋田県"),
    YAMAGATA("06","山形県"),
    FUKUSIMA("07","福島県"),
    IBARAKI("08","茨城県"),
    TOCHIGI("09","栃木県"),
    GUNMA("10","群馬県"),
    SAITAMA("11", "埼玉県"),
    CHIBA("12", "千葉県"),
    TOKYO("13", "東京都"),
    KANAGAWA("14",  "神奈川県"),
    NIIGATA("15", "新潟県"),
    TOYAMA("16","富山県"),
    ISHIKAWA("17", "石川県"),
    FUKUI("18", "福井県"),
    YAMANASHI("19", "山梨県"),
    NAGANO("20", "長野県"),
    GIFU("21","岐阜県"),
    SHIZUOKA("22", "静岡県"),
    AITHI("23", "愛知県"),
    MIE("24","三重県"),
    SHIGA("25", "滋賀県"),
    KYOTO("26", "京都府"),
    OSAKA("27", "大阪府"),
    HYOGO("28", "兵庫県"),
    NARA("29", "奈良県"),
    WAKAYAMA("30","和歌山県"),
    TOTTORI("31","鳥取県"),
    SHIMANE("32", "島根県"),
    OKAYAMA("33", "岡山県"),
    HIROSHIMA("34", "広島県"),
    YAMAGUSHI("35","山口県"),
    TOKSHIMA("36", "徳島県"),
    KAGAWA("37", "香川県"),
    EHIME("38",  "愛媛県"),
    KOUCHI("39", "高知県"),
    FUKUOKA("40", "福岡県"),
    SAGA("41", "佐賀県"),
    NAGASAKI("42", "長崎県"),
    KUMAMOTO("43", "熊本県"),
    OOITA("44", "大分県"),
    MIYAZAKI("45", "宮崎県"),
    KAGOSHIMA("46", "鹿児島県"),
    OKINAWA("47", "沖縄県");

     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicinePrefactureCdEnums.class);

    /*
    * コード
    */
    private final String code;
    /*
    * 名称
    */
    private final String name;

    MedicinePrefactureCdEnums(final String ancode , String anname) {
        code = ancode;
        name = anname;
    }

    // enum定数から整数へ変換
    public String getName() {
        return name;
    }

    // enum定数から整数へ変換
    public String getCode() {
        return code;
    }



    // codeからnameを取得する
    public static String selectName(String code) {
        for (MedicinePrefactureCdEnums d : values()) {
            if (d.getCode().equals(code)) {
                return d.getName();
            }
        }
        logger.debug("unknown intValue : " + code);
        return null;
    }

}
