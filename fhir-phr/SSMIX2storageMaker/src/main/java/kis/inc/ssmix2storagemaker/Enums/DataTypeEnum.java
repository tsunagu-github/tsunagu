/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * 対応データ種別のEnum
 * ここにないものは未対応
 *
 */
public enum DataTypeEnum {
    ADT00("ADT-00","患者基本情報","STD",false),
    ADT12("ADT-12", "外来診察","STD",true),
    ADT21("ADT-21", "入院予定","STD",true),
    ADT22("ADT-22", "入院実施","STD",true),
    ADT41("ADT-41", "転科・転棟（転室・転床）予定","STD",true),
    ADT42("ADT-42", "転科・転棟（転室・転床）実施","STD",true),
    ADT51("ADT-51", "退院予定","STD",true),
    ADT52("ADT-52", "退院実施","STD",true),
    ADT61("ADT-61","アレルギー情報","STD",false),
    OML11("OML-11", "検体検査結果","STD",true),
    OMP01("OMP-01", "処方オーダ","STD",true),
    OMP11("OMP-11", "処方実施通知","STD",true),
    OMP12("OMP-12", "注射実施通知","STD",true),
    PPR01("PPR-01","病名情報","STD",false),
    //お薬手帳のデータ種別は仮置き
    MEDICALBOOK("MEDICALBOOK", "お薬手帳","EXT",true),
    //健診XMLのデータ種別も仮置き
    CHECKUP("CHECKUP", "健診XML","EXT",true),
    ALL("ALL","全データ","ALL",true),
    STD("STD","標準化ストレージ","STD",true),
    EXT("EXT","拡張ストレージ","EXT",true);

     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DataTypeEnum.class);

    /*
    * コード
    */
    private final String code;
    /*
    * 名称
    */
    private final String name;
    /*
    * 種別
    */
    private final String type;
    /*
    * 日付管理
    * 管理する場合はtrue,しない場合はfalse
    */
    private final boolean dateFlg;

    DataTypeEnum(final String ancode , String anname ,String antype , boolean anflg) {
        code = ancode;
        name = anname;
        type = antype;
        dateFlg = anflg;
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
        for (DataTypeEnum d : values()) {
            if (d.getCode().equals(code)) {
                return d.getName();
            }
        }
        logger.debug("unknown intValue : " + code);
        return null;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    // codeからnameを取得する
    public static String selectType(String code) {
        for (DataTypeEnum d : values()) {
            if (d.getCode().equals(code)) {
                return d.getType();
            }
        }
        logger.debug("unknown intValue : " + code);
        return null;
    }

    /**
     * @return the dateFlg
     */
    public boolean isDateFlg() {
        return dateFlg;
    }

    // codeからnameを取得する
    public static Boolean selectFlg(String code) {
        for (DataTypeEnum d : values()) {
            if (d.getCode().equals(code)) {
                return d.isDateFlg();
            }
        }
        logger.debug("unknown intValue : " + code);
        return null;
    }

    // codeからnameを取得する
    public static List<String> selectSType(String type) {
        List<String> result = new ArrayList<>();
        for (DataTypeEnum d : values()) {
            if (d.getType().equals(type)) {
                if(!d.getCode().equals(type) && !d.getCode().equals("PPR-01") && !d.getCode().equals("ADT-61"))
                    result.add(d.getCode());
            }
        }
        return result;
    }

}
