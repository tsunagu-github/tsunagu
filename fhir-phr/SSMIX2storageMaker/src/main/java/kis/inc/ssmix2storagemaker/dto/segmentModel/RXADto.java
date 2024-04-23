/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.LA2Dto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;

/**
 *
 * @author kis-note
 */
public class RXADto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(RXADto.class);
    /**
     * セグメントID
     */
    private String RXA00;
    /**
     * 与薬サブ ID カウンター
     */
    private String RXA01;
    /**
     * 投薬サブ ID カウンター
     */
    private String RXA02;
    /**
     * 投薬開始日時
     */
    private String RXA03;
    /**
     * 投薬終了日時
     */
    private String RXA04;
    /**
     * 投薬コード
     */
    private CWEDto RXA05;
    /**
     * 投薬量
     */
    private String RXA06;
    /**
     * 投薬単位
     */
    private CWEDto RXA07;
    /**
     * 投薬剤形
     */
    private CWEDto RXA08;
    /**
     * 投薬注記
     */
    private List<CWEDto> RXA09;
    /**
     * 投薬者
     */
    private List<XCNDto> RXA10;
    /**
     * 投薬場所
     */
    private LA2Dto RXA11;
    /**
     * 時間あたりの投薬
     */
    private String RXA12;
    /**
     * 投薬力価
     */
    private String RXA13;
    /**
     * 投薬力価単位
     */
    private CWEDto RXA14;
    /**
     * 薬剤ロット番号
     */
    private List<String> RXA15;
    /**
     * 薬剤有効期限
     */
    private List<String> RXA16;
    /**
     * 薬剤製造者名
     */
    private List<CWEDto> RXA17;
    /**
     * 薬剤/治療拒否理由
     */
    private List<CWEDto> RXA18;
    /**
     * 指示
     */
    private List<CWEDto> RXA19;
    /**
     * 完了状態
     */
    private String RXA20;
    /**
     * アクションコード＿RXA
     */
    private String RXA21;
    /**
     * システム入力日時
     */
    private String RXA22;
    /**
     * 投薬力価量
     */
    private String RXA23;
    /**
     * 投薬力価量単位
     */
    private CWEDto RXA24;
    /**
     * 投薬バーコード識別機能
     */
    private CWEDto RXA25;
    /**
     * 薬剤オーダタイプ
     */
    private String RXA26;

    /**
     * @return the RXA00
     */
    public String getRXA00() {
        return RXA00;
    }

    /**
     * @param RXA00 the RXA00 to set
     */
    public void setRXA00(String RXA00) {
        this.RXA00 = RXA00;
    }

    /**
     * @return the RXA01
     */
    public String getRXA01() {
        return RXA01;
    }

    /**
     * @param RXA01 the RXA01 to set
     */
    public void setRXA01(String RXA01) {
        this.RXA01 = RXA01;
    }

    /**
     * @return the RXA02
     */
    public String getRXA02() {
        return RXA02;
    }

    /**
     * @param RXA02 the RXA02 to set
     */
    public void setRXA02(String RXA02) {
        this.RXA02 = RXA02;
    }

    /**
     * @return the RXA03
     */
    public String getRXA03() {
        return RXA03;
    }

    /**
     * @param RXA03 the RXA03 to set
     */
    public void setRXA03(String RXA03) {
        this.RXA03 = RXA03;
    }

    /**
     * @return the RXA04
     */
    public String getRXA04() {
        return RXA04;
    }

    /**
     * @param RXA04 the RXA04 to set
     */
    public void setRXA04(String RXA04) {
        this.RXA04 = RXA04;
    }

    /**
     * @return the RXA05
     */
    public CWEDto getRXA05() {
        return RXA05;
    }

    /**
     * @param RXA05 the RXA05 to set
     */
    public void setRXA05(CWEDto RXA05) {
        this.RXA05 = RXA05;
    }

    /**
     * @return the RXA06
     */
    public String getRXA06() {
        return RXA06;
    }

    /**
     * @param RXA06 the RXA06 to set
     */
    public void setRXA06(String RXA06) {
        this.RXA06 = RXA06;
    }

    /**
     * @return the RXA07
     */
    public CWEDto getRXA07() {
        return RXA07;
    }

    /**
     * @param RXA07 the RXA07 to set
     */
    public void setRXA07(CWEDto RXA07) {
        this.RXA07 = RXA07;
    }

    /**
     * @return the RXA08
     */
    public CWEDto getRXA08() {
        return RXA08;
    }

    /**
     * @param RXA08 the RXA08 to set
     */
    public void setRXA08(CWEDto RXA08) {
        this.RXA08 = RXA08;
    }

    /**
     * @return the RXA09
     */
    public List<CWEDto> getRXA09() {
        return RXA09;
    }

    /**
     * @param RXA09 the RXA09 to set
     */
    public void setRXA09(List<CWEDto> RXA09) {
        this.RXA09 = RXA09;
    }

    /**
     * @return the RXA10
     */
    public List<XCNDto> getRXA10() {
        return RXA10;
    }

    /**
     * @param RXA10 the RXA10 to set
     */
    public void setRXA10(List<XCNDto> RXA10) {
        this.RXA10 = RXA10;
    }

    /**
     * @return the RXA11
     */
    public LA2Dto getRXA11() {
        return RXA11;
    }

    /**
     * @param RXA11 the RXA11 to set
     */
    public void setRXA11(LA2Dto RXA11) {
        this.RXA11 = RXA11;
    }

    /**
     * @return the RXA12
     */
    public String getRXA12() {
        return RXA12;
    }

    /**
     * @param RXA12 the RXA12 to set
     */
    public void setRXA12(String RXA12) {
        this.RXA12 = RXA12;
    }

    /**
     * @return the RXA13
     */
    public String getRXA13() {
        return RXA13;
    }

    /**
     * @param RXA13 the RXA13 to set
     */
    public void setRXA13(String RXA13) {
        this.RXA13 = RXA13;
    }

    /**
     * @return the RXA14
     */
    public CWEDto getRXA14() {
        return RXA14;
    }

    /**
     * @param RXA14 the RXA14 to set
     */
    public void setRXA14(CWEDto RXA14) {
        this.RXA14 = RXA14;
    }

    /**
     * @return the RXA15
     */
    public List<String> getRXA15() {
        return RXA15;
    }

    /**
     * @param RXA15 the RXA15 to set
     */
    public void setRXA15(List<String> RXA15) {
        this.RXA15 = RXA15;
    }

    /**
     * @return the RXA16
     */
    public List<String> getRXA16() {
        return RXA16;
    }

    /**
     * @param RXA16 the RXA16 to set
     */
    public void setRXA16(List<String> RXA16) {
        this.RXA16 = RXA16;
    }

    /**
     * @return the RXA17
     */
    public List<CWEDto> getRXA17() {
        return RXA17;
    }

    /**
     * @param RXA17 the RXA17 to set
     */
    public void setRXA17(List<CWEDto> RXA17) {
        this.RXA17 = RXA17;
    }

    /**
     * @return the RXA18
     */
    public List<CWEDto> getRXA18() {
        return RXA18;
    }

    /**
     * @param RXA18 the RXA18 to set
     */
    public void setRXA18(List<CWEDto> RXA18) {
        this.RXA18 = RXA18;
    }

    /**
     * @return the RXA19
     */
    public List<CWEDto> getRXA19() {
        return RXA19;
    }

    /**
     * @param RXA19 the RXA19 to set
     */
    public void setRXA19(List<CWEDto> RXA19) {
        this.RXA19 = RXA19;
    }

    /**
     * @return the RXA20
     */
    public String getRXA20() {
        return RXA20;
    }

    /**
     * @param RXA20 the RXA20 to set
     */
    public void setRXA20(String RXA20) {
        this.RXA20 = RXA20;
    }

    /**
     * @return the RXA21
     */
    public String getRXA21() {
        return RXA21;
    }

    /**
     * @param RXA21 the RXA21 to set
     */
    public void setRXA21(String RXA21) {
        this.RXA21 = RXA21;
    }

    /**
     * @return the RXA22
     */
    public String getRXA22() {
        return RXA22;
    }

    /**
     * @param RXA22 the RXA22 to set
     */
    public void setRXA22(String RXA22) {
        this.RXA22 = RXA22;
    }

    /**
     * @return the RXA23
     */
    public String getRXA23() {
        return RXA23;
    }

    /**
     * @param RXA23 the RXA23 to set
     */
    public void setRXA23(String RXA23) {
        this.RXA23 = RXA23;
    }

    /**
     * @return the RXA24
     */
    public CWEDto getRXA24() {
        return RXA24;
    }

    /**
     * @param RXA24 the RXA24 to set
     */
    public void setRXA24(CWEDto RXA24) {
        this.RXA24 = RXA24;
    }

    /**
     * @return the RXA25
     */
    public CWEDto getRXA25() {
        return RXA25;
    }

    /**
     * @param RXA25 the RXA25 to set
     */
    public void setRXA25(CWEDto RXA25) {
        this.RXA25 = RXA25;
    }

    /**
     * @return the RXA26
     */
    public String getRXA26() {
        return RXA26;
    }

    /**
     * @param RXA26 the RXA26 to set
     */
    public void setRXA26(String RXA26) {
        this.RXA26 = RXA26;
    }

    /**
     * RXAのセット
     */
    public void setRXA(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "RXA" + st_num;

        switch(target){
            case "RXA00":
                setRXA00(value);
                break;
            case "RXA01":
                setRXA01(value);
                break;
            case "RXA02":
                setRXA02(value);
                break;
            case "RXA03":
                setRXA03(value);
                break;
            case "RXA04":
                setRXA04(value);
                break;
            case "RXA05":
                CWEDto rxa05 = setCWE(value);
                setRXA05(rxa05);
                break;
            case "RXA06":
                if (value.equals("\"\"")) {
                    value = "";
                }
                setRXA06(value);
                break;
            case "RXA07":
                CWEDto rxa07 = setCWE(value);
                setRXA07(rxa07);
                break;
            case "RXA08":
                CWEDto rxa08 = setCWE(value);
                setRXA08(rxa08);
                break;
            case "RXA09":
            	List<CWEDto> rxa09List = setCWEList(value);
                setRXA09(rxa09List);
                break;
            case "RXA10":
                List<XCNDto> rxa10List = setXCN(value);
                setRXA10(rxa10List);
                break;
            case "RXA11":
            	LA2Dto rxa11 = setLA2(value);
                setRXA11(rxa11);
                break;
            case "RXA12":
                setRXA12(value);
                break;
            case "RXA13":
                setRXA13(value);
                break;
            case "RXA14":
                CWEDto rxa14 = setCWE(value);
                setRXA14(rxa14);
                break;
            case "RXA15":
            	List<String> rxa15List = new ArrayList<>(Arrays.asList(value.split(getElementSp())));
                setRXA15(rxa15List);
                break;
            case "RXA16":
            	List<String> rxa16List = new ArrayList<>(Arrays.asList(value.split(getElementSp())));
                setRXA16(rxa16List);
                break;
            case "RXA17":
            	List<CWEDto> rxa17List = setCWEList(value);
                setRXA17(rxa17List);
                break;
            case "RXA18":
            	List<CWEDto> rxa18List = setCWEList(value);
                setRXA18(rxa18List);
                break;
            case "RXA19":
            	List<CWEDto> rxa19List = setCWEList(value);
                setRXA19(rxa19List);
                break;
            case "RXA20":
                setRXA20(value);
                break;
            case "RXA21":
                setRXA21(value);
                break;
            case "RXA22":
                setRXA22(value);
                break;
            case "RXA23":
                setRXA23(value);
                break;
            case "RXA24":
                CWEDto rxa24 = setCWE(value);
                setRXA24(rxa24);
                break;
            case "RXA25":
                CWEDto rxa25 = setCWE(value);
                setRXA25(rxa25);
                break;
            case "RXA26":
                setRXA26(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }

    }

    /**
     *  RXAのゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getRXA(Integer f_num , Integer e_num , Integer r_num){
        String result = null;

        switch(f_num){
            case 0:
                result = getRXA00();
                break;
            case 1:
                result = getRXA01();
                break;
            case 2:
                result = getRXA02();
                break;
            case 3:
                result = getRXA03();
                break;
            case 4:
                result = getRXA04();
                break;
            case 5:
                result = getCWE(getRXA05() , e_num);
                break;
            case 6:
                result = getRXA06();
                break;
            case 7:
                result = getCWE(getRXA07() , e_num);
                break;
            case 8:
                result = getCWE(getRXA08() , e_num);
                break;
            case 9:
                result = getCWE(getRXA09() , e_num , r_num);
                break;
            case 10:
                result = getXCN(getRXA10() , e_num , r_num);
                break;
            case 11:
                result = getLA2(getRXA11() , e_num);
                break;
            case 12:
                result = getRXA12();
                break;
            case 13:
                result = getRXA13();
                break;
            case 14:
                result = getCWE(getRXA14() , e_num);
                break;
            case 15:
                result = getRXA15().get(r_num);
                break;
            case 16:
                result = getRXA16().get(r_num);
                break;
            case 17:
                result = getCWE(getRXA17() , e_num , r_num);
                break;
            case 18:
                result = getCWE(getRXA18() , e_num , r_num);
                break;
            case 19:
                result = getCWE(getRXA19() , e_num , r_num);
                break;
            case 20:
                result = getRXA20();
                break;
            case 21:
                result = getRXA21();
                break;
            case 22:
                result = getRXA22();
                break;
            case 23:
                result = getRXA23();
                break;
            case 24:
                result = getCWE(getRXA24() , e_num );
                break;
            case 25:
                result = getCWE(getRXA25() , e_num );
                break;
            case 26:
                result = getRXA26();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }

    /**
     *  RXAのゲット
     *
     * @param f_num フィールドNo　必須
     */
    public Object getRXAOb(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getRXA00();
                break;
            case 1:
                result = getRXA01();
                break;
            case 2:
                result = getRXA02();
                break;
            case 3:
                result = getRXA03();
                break;
            case 4:
                result = getRXA04();
                break;
            case 5:
                result = getRXA05();
                break;
            case 6:
                result = getRXA06();
                break;
            case 7:
                result = getRXA07();
                break;
            case 8:
                result = getRXA08();
                break;
            case 9:
                result = getRXA09();
                break;
            case 10:
                result = getRXA10();
                break;
            case 11:
                result = getRXA11();
                break;
            case 12:
                result = getRXA12();
                break;
            case 13:
                result = getRXA13();
                break;
            case 14:
                result = getRXA14();
                break;
            case 15:
                result = getRXA15();
                break;
            case 16:
                result = getRXA16();
                break;
            case 17:
                result = getRXA17();
                break;
            case 18:
                result = getRXA18();
                break;
            case 19:
                result = getRXA19();
                break;
            case 20:
                result = getRXA20();
                break;
            case 21:
                result = getRXA21();
                break;
            case 22:
                result = getRXA22();
                break;
            case 23:
                result = getRXA23();
                break;
            case 24:
                result = getRXA24();
                break;
            case 25:
                result = getRXA25();
                break;
            case 26:
                result = getRXA26();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }
}
