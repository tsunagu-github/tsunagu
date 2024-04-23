/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.PLDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XADDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;

/**
 *
 * @author kis-note
 */
public class RXEDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(RXEDto.class);
    /**
     * セグメントID
     */
    private String RXE00;
    /**
     * 数量/タイミング
     */
    private String RXE01;
    /**
     * 与薬コード
     */
    private CWEDto RXE02;
    /**
     * 与薬量－最小
     */
    private String RXE03;
    /**
     * 与薬量－最大
     */
    private String RXE04;
    /**
     * 与薬単位
     */
    private CWEDto RXE05;
    /**
     * 与薬剤形
     */
    private CWEDto RXE06;
    /**
     * 依頼者の投薬指示
     */
    private List<CWEDto> RXE07;
    /**
     * 配布先
     */
    private String RXE08;
    /**
     * 代替品状態
     */
    private String RXE09;
    /**
     * 調剤量
     */
    private String RXE10;
    /**
     * 調剤単位
     */
    private CWEDto RXE11;
    /**
     * 同一薬発行数
     */
    private String RXE12;
    /**]
     * オーダ発行者の DEA 番号
     */
    private List<XCNDto> RXE13;
    /**
     * 薬剤師/治療提供者の検証 ID
     */
    private List<XCNDto> RXE14;
    /**
     * 処方箋番号
     */
    private String RXE15;
    /**
     * 薬剤残数
     */
    private String RXE16;
    /**
     * 調剤済薬品数または投与数
     */
    private String RXE17;
    /**
     * 調剤済薬品数または投与数の最新日時
     */
    private String RXE18;
    /**
     * 1 日あたりの総投与量
     */
    private String RXE19;
    /**
     * 人によるレビューの必要性
     */
    private String RXE20;
    /**
     * 薬剤部門/治療部門による特別な調剤指示
     */
    private List<CWEDto> RXE21;
    /**
     * 時間あたりの与薬
     */
    private String RXE22;
    /**
     * 与薬速度
     */
    private String RXE23;
    /**
     * 与薬速度単位
     */
    private CWEDto RXE24;
    /**
     * 与薬力価
     */
    private String RXE25;
    /**
     * 与薬力価単位
     */
    private CWEDto RXE26;
    /**
     * 与薬指示
     */
    private List<CWEDto> RXE27;
    /**
     * 調剤パッケージサイズ
     */
    private String RXE28;
    /**
     * 調剤パッケージサイズの単位
     */
    private CWEDto RXE29;
    /**
     * 調剤パッケージ方法
     */
    private String RXE30;
    /**
     * 補足コード
     */
    private List<CWEDto> RXE31;
    /**
     * 当初のオーダ日/時間
     */
    private String RXE32;
    /**
     * 与薬力価量
     */
    private String RXE33;
    /**
     * 与薬力価量単位
     */
    private CWEDto RXE34;
    /**
     * 薬物コントロールスケジュール
     */
    private CWEDto RXE35;
    /**
     * 処方集ステータス
     */
    private String RXE36;
    /**
     * 薬物代替え
     */
    private List<CWEDto> RXE37;
    /**
     * 最新の与薬の薬剤部
     */
    private CWEDto RXE38;
    /**
     * 最初の調剤量
     */
    private String RXE39;
    /**
     * 調剤薬剤部
     */
    private CWEDto RXE40;
    /**
     * 調剤薬剤部の住所
     */
    private XADDto RXE41;
    /**
     * 患者への配達場所
     */
    private PLDto RXE42;
    /**
     * 配達先住所
     */
    private XADDto RXE43;
    /**
     * 薬剤オーダタイプ
     */
    private String RXE44;

    /**
     * @return the RXE00
     */
    public String getRXE00() {
        return RXE00;
    }

    /**
     * @param RXE00 the RXE00 to set
     */
    public void setRXE00(String RXE00) {
        this.RXE00 = RXE00;
    }

    /**
     * @return the RXE01
     */
    public String getRXE01() {
        return RXE01;
    }

    /**
     * @param RXE01 the RXE01 to set
     */
    public void setRXE01(String RXE01) {
        this.RXE01 = RXE01;
    }

    /**
     * @return the RXE02
     */
    public CWEDto getRXE02() {
        return RXE02;
    }

    /**
     * @param RXE02 the RXE02 to set
     */
    public void setRXE02(CWEDto RXE02) {
        this.RXE02 = RXE02;
    }

    /**
     * @return the RXE03
     */
    public String getRXE03() {
        return RXE03;
    }

    /**
     * @param RXE03 the RXE03 to set
     */
    public void setRXE03(String RXE03) {
        this.RXE03 = RXE03;
    }

    /**
     * @return the RXE04
     */
    public String getRXE04() {
        return RXE04;
    }

    /**
     * @param RXE04 the RXE04 to set
     */
    public void setRXE04(String RXE04) {
        this.RXE04 = RXE04;
    }

    /**
     * @return the RXE05
     */
    public CWEDto getRXE05() {
        return RXE05;
    }

    /**
     * @param RXE05 the RXE05 to set
     */
    public void setRXE05(CWEDto RXE05) {
        this.RXE05 = RXE05;
    }

    /**
     * @return the RXE06
     */
    public CWEDto getRXE06() {
        return RXE06;
    }

    /**
     * @param RXE06 the RXE06 to set
     */
    public void setRXE06(CWEDto RXE06) {
        this.RXE06 = RXE06;
    }

    /**
     * @return the RXE07
     */
    public List<CWEDto> getRXE07() {
        return RXE07;
    }

    /**
     * @param RXE07 the RXE07 to set
     */
    public void setRXE07(List<CWEDto> RXE07) {
        this.RXE07 = RXE07;
    }

    /**
     * @return the RXE08
     */
    public String getRXE08() {
        return RXE08;
    }

    /**
     * @param RXE08 the RXE08 to set
     */
    public void setRXE08(String RXE08) {
        this.RXE08 = RXE08;
    }

    /**
     * @return the RXE09
     */
    public String getRXE09() {
        return RXE09;
    }

    /**
     * @param RXE09 the RXE09 to set
     */
    public void setRXE09(String RXE09) {
        this.RXE09 = RXE09;
    }

    /**
     * @return the RXE10
     */
    public String getRXE10() {
        return RXE10;
    }

    /**
     * @param RXE10 the RXE10 to set
     */
    public void setRXE10(String RXE10) {
        this.RXE10 = RXE10;
    }

    /**
     * @return the RXE11
     */
    public CWEDto getRXE11() {
        return RXE11;
    }

    /**
     * @param RXE11 the RXE11 to set
     */
    public void setRXE11(CWEDto RXE11) {
        this.RXE11 = RXE11;
    }

    /**
     * @return the RXE12
     */
    public String getRXE12() {
        return RXE12;
    }

    /**
     * @param RXE12 the RXE12 to set
     */
    public void setRXE12(String RXE12) {
        this.RXE12 = RXE12;
    }

    /**
     * @return the RXE13
     */
    public List<XCNDto> getRXE13() {
        return RXE13;
    }

    /**
     * @param RXE13 the RXE13 to set
     */
    public void setRXE13(List<XCNDto> RXE13) {
        this.RXE13 = RXE13;
    }

    /**
     * @return the RXE14
     */
    public List<XCNDto> getRXE14() {
        return RXE14;
    }

    /**
     * @param RXE14 the RXE14 to set
     */
    public void setRXE14(List<XCNDto> RXE14) {
        this.RXE14 = RXE14;
    }

    /**
     * @return the RXE15
     */
    public String getRXE15() {
        return RXE15;
    }

    /**
     * @param RXE15 the RXE15 to set
     */
    public void setRXE15(String RXE15) {
        this.RXE15 = RXE15;
    }

    /**
     * @return the RXE16
     */
    public String getRXE16() {
        return RXE16;
    }

    /**
     * @param RXE16 the RXE16 to set
     */
    public void setRXE16(String RXE16) {
        this.RXE16 = RXE16;
    }

    /**
     * @return the RXE17
     */
    public String getRXE17() {
        return RXE17;
    }

    /**
     * @param RXE17 the RXE17 to set
     */
    public void setRXE17(String RXE17) {
        this.RXE17 = RXE17;
    }

    /**
     * @return the RXE18
     */
    public String getRXE18() {
        return RXE18;
    }

    /**
     * @param RXE18 the RXE18 to set
     */
    public void setRXE18(String RXE18) {
        this.RXE18 = RXE18;
    }

    /**
     * @return the RXE19
     */
    public String getRXE19() {
        return RXE19;
    }

    /**
     * @param RXE19 the RXE19 to set
     */
    public void setRXE19(String RXE19) {
        this.RXE19 = RXE19;
    }

    /**
     * @return the RXE20
     */
    public String getRXE20() {
        return RXE20;
    }

    /**
     * @param RXE20 the RXE20 to set
     */
    public void setRXE20(String RXE20) {
        this.RXE20 = RXE20;
    }

    /**
     * @return the RXE21
     */
    public List<CWEDto> getRXE21() {
        return RXE21;
    }

    /**
     * @param RXE21 the RXE21 to set
     */
    public void setRXE21(List<CWEDto> RXE21) {
        this.RXE21 = RXE21;
    }

    /**
     * @return the RXE22
     */
    public String getRXE22() {
        return RXE22;
    }

    /**
     * @param RXE22 the RXE22 to set
     */
    public void setRXE22(String RXE22) {
        this.RXE22 = RXE22;
    }

    /**
     * @return the RXE23
     */
    public String getRXE23() {
        return RXE23;
    }

    /**
     * @param RXE23 the RXE23 to set
     */
    public void setRXE23(String RXE23) {
        this.RXE23 = RXE23;
    }

    /**
     * @return the RXE24
     */
    public CWEDto getRXE24() {
        return RXE24;
    }

    /**
     * @param RXE24 the RXE24 to set
     */
    public void setRXE24(CWEDto RXE24) {
        this.RXE24 = RXE24;
    }

    /**
     * @return the RXE25
     */
    public String getRXE25() {
        return RXE25;
    }

    /**
     * @param RXE25 the RXE25 to set
     */
    public void setRXE25(String RXE25) {
        this.RXE25 = RXE25;
    }

    /**
     * @return the RXE26
     */
    public CWEDto getRXE26() {
        return RXE26;
    }

    /**
     * @param RXE26 the RXE26 to set
     */
    public void setRXE26(CWEDto RXE26) {
        this.RXE26 = RXE26;
    }

    /**
     * @return the RXE27
     */
    public List<CWEDto> getRXE27() {
        return RXE27;
    }

    /**
     * @param RXE27 the RXE27 to set
     */
    public void setRXE27(List<CWEDto> RXE27) {
        this.RXE27 = RXE27;
    }

    /**
     * @return the RXE28
     */
    public String getRXE28() {
        return RXE28;
    }

    /**
     * @param RXE28 the RXE28 to set
     */
    public void setRXE28(String RXE28) {
        this.RXE28 = RXE28;
    }

    /**
     * @return the RXE29
     */
    public CWEDto getRXE29() {
        return RXE29;
    }

    /**
     * @param RXE29 the RXE29 to set
     */
    public void setRXE29(CWEDto RXE29) {
        this.RXE29 = RXE29;
    }

    /**
     * @return the RXE30
     */
    public String getRXE30() {
        return RXE30;
    }

    /**
     * @param RXE30 the RXE30 to set
     */
    public void setRXE30(String RXE30) {
        this.RXE30 = RXE30;
    }

    /**
     * @return the RXE31
     */
    public List<CWEDto> getRXE31() {
        return RXE31;
    }

    /**
     * @param RXE31 the RXE31 to set
     */
    public void setRXE31(List<CWEDto> RXE31) {
        this.RXE31 = RXE31;
    }

    /**
     * @return the RXE32
     */
    public String getRXE32() {
        return RXE32;
    }

    /**
     * @param RXE32 the RXE32 to set
     */
    public void setRXE32(String RXE32) {
        this.RXE32 = RXE32;
    }

    /**
     * @return the RXE33
     */
    public String getRXE33() {
        return RXE33;
    }

    /**
     * @param RXE33 the RXE33 to set
     */
    public void setRXE33(String RXE33) {
        this.RXE33 = RXE33;
    }

    /**
     * @return the RXE34
     */
    public CWEDto getRXE34() {
        return RXE34;
    }

    /**
     * @param RXE34 the RXE34 to set
     */
    public void setRXE34(CWEDto RXE34) {
        this.RXE34 = RXE34;
    }

    /**
     * @return the RXE35
     */
    public CWEDto getRXE35() {
        return RXE35;
    }

    /**
     * @param RXE35 the RXE35 to set
     */
    public void setRXE35(CWEDto RXE35) {
        this.RXE35 = RXE35;
    }

    /**
     * @return the RXE36
     */
    public String getRXE36() {
        return RXE36;
    }

    /**
     * @param RXE36 the RXE36 to set
     */
    public void setRXE36(String RXE36) {
        this.RXE36 = RXE36;
    }

    /**
     * @return the RXE37
     */
    public List<CWEDto> getRXE37() {
        return RXE37;
    }

    /**
     * @param RXE37 the RXE37 to set
     */
    public void setRXE37(List<CWEDto> RXE37) {
        this.RXE37 = RXE37;
    }

    /**
     * @return the RXE38
     */
    public CWEDto getRXE38() {
        return RXE38;
    }

    /**
     * @param RXE38 the RXE38 to set
     */
    public void setRXE38(CWEDto RXE38) {
        this.RXE38 = RXE38;
    }

    /**
     * @return the RXE39
     */
    public String getRXE39() {
        return RXE39;
    }

    /**
     * @param RXE39 the RXE39 to set
     */
    public void setRXE39(String RXE39) {
        this.RXE39 = RXE39;
    }

    /**
     * @return the RXE40
     */
    public CWEDto getRXE40() {
        return RXE40;
    }

    /**
     * @param RXE40 the RXE40 to set
     */
    public void setRXE40(CWEDto RXE40) {
        this.RXE40 = RXE40;
    }

    /**
     * @return the RXE41
     */
    public XADDto getRXE41() {
        return RXE41;
    }

    /**
     * @param RXE41 the RXE41 to set
     */
    public void setRXE41(XADDto RXE41) {
        this.RXE41 = RXE41;
    }

    /**
     * @return the RXE42
     */
    public PLDto getRXE42() {
        return RXE42;
    }

    /**
     * @param RXE42 the RXE42 to set
     */
    public void setRXE42(PLDto RXE42) {
        this.RXE42 = RXE42;
    }

    /**
     * @return the RXE43
     */
    public XADDto getRXE43() {
        return RXE43;
    }

    /**
     * @param RXE43 the RXE43 to set
     */
    public void setRXE43(XADDto RXE43) {
        this.RXE43 = RXE43;
    }

    /**
     * @return the RXE44
     */
    public String getRXE44() {
        return RXE44;
    }

    /**
     * @param RXE44 the PL144 to set
     */
    public void setRXE44(String RXE44) {
        this.RXE44 = RXE44;
    }

    /**
     * RXEのセット
     */
    public void setRXE(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "RXE" + st_num;

        switch(target){
            case "RXE00":
                setRXE00(value);
                break;
            case "RXE01":
                setRXE01(value);
                break;
            case "RXE02":
                CWEDto rxe02 = setCWE(value);
                setRXE02(rxe02);
                break;
            case "RXE03":
                setRXE03(value);
                break;
            case "RXE04":
                setRXE04(value);
                break;
            case "RXE05":
                CWEDto rxe05 = setCWE(value);
                setRXE05(rxe05);
                break;
            case "RXE06":
                CWEDto rxe06 = setCWE(value);
                setRXE06(rxe06);
                break;
            case "RXE07":
            	List<CWEDto> rxe07List = setCWEList(value);
                setRXE07(rxe07List);
                break;
            case "RXE08":
                setRXE08(value);
                break;
            case "RXE09":
                setRXE09(value);
                break;
            case "RXE10":
                setRXE10(value);
                break;
            case "RXE11":
                CWEDto rxe11 = setCWE(value);
                setRXE11(rxe11);
                break;
            case "RXE12":
                setRXE12(value);
                break;
            case "RXE13":
                List<XCNDto> rxe13List = setXCN(value);
                setRXE13(rxe13List);
                break;
            case "RXE14":
                List<XCNDto> rxe14List = setXCN(value);
                setRXE14(rxe14List);
                break;
            case "RXE15":
                setRXE15(value);
                break;
            case "RXE16":
                setRXE16(value);
                break;
            case "RXE17":
                setRXE17(value);
                break;
            case "RXE18":
                setRXE18(value);
                break;
            case "RXE19":
                setRXE19(value);
                break;
            case "RXE20":
                setRXE20(value);
                break;
            case "RXE21":
            	List<CWEDto> rxe21List = setCWEList(value);
                setRXE21(rxe21List);
                break;
            case "RXE22":
                setRXE22(value);
                break;
            case "RXE23":
                setRXE23(value);
                break;
            case "RXE24":
                CWEDto rxe24 = setCWE(value);
                setRXE24(rxe24);
                break;
            case "RXE25":
                setRXE25(value);
                break;
            case "RXE26":
                CWEDto rxe26 = setCWE(value);
                setRXE26(rxe26);
                break;
            case "RXE27":
            	List<CWEDto> rxe27List = setCWEList(value);
                setRXE27(rxe27List);
                break;
            case "RXE28":
                setRXE28(value);
                break;
            case "RXE29":
                CWEDto rxe29 = setCWE(value);
                setRXE29(rxe29);
                break;
            case "RXE30":
                setRXE30(value);
                break;
            case "RXE31":
            	List<CWEDto> rxe31List = setCWEList(value);
                setRXE31(rxe31List);
                break;
            case "RXE32":
                setRXE32(value);
                break;
            case "RXE33":
                setRXE33(value);
                break;
            case "RXE34":
                CWEDto rxe34 = setCWE(value);
                setRXE34(rxe34);
                break;
            case "RXE35":
                CWEDto rxe35 = setCWE(value);
                setRXE35(rxe35);
                break;
            case "RXE36":
                setRXE36(value);
                break;
            case "RXE37":
            	List<CWEDto> rxe37List = setCWEList(value);
                setRXE37(rxe37List);
                break;
            case "RXE38":
            	CWEDto rxe38 = setCWE(value);
                setRXE38(rxe38);
                break;
            case "RXE39":
                setRXE39(value);
                break;
            case "RXE40":
            	CWEDto rxe40 = setCWE(value);
                setRXE40(rxe40);
                break;
            case "RXE41":
            	XADDto rxe41 = setXAD(value);
                setRXE41(rxe41);
                break;
            case "RXE42":
            	PLDto rxe42 = setPL(value);
                setRXE42(rxe42);
                break;
            case "RXE43":
            	XADDto rxe43 = setXAD(value);
                setRXE43(rxe43);
                break;
            case "RXE44":
                setRXE44(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }

    }

    /**
     *  RXEのゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getRXE(Integer f_num , Integer e_num , Integer r_num){
        String result = null;

        switch(f_num){
            case 0:
                result = getRXE00();
                break;
            case 1:
                result = getRXE01();
                break;
            case 2:
                result = getCWE(getRXE02() , e_num );
                break;
            case 3:
                result = getRXE03();
                break;
            case 4:
                result = getRXE04();
                break;
            case 5:
                result = getCWE(getRXE05() , e_num );
                break;
            case 6:
                result = getCWE(getRXE06() , e_num );
                break;
            case 7:
                result = getCWE(getRXE07() , e_num , r_num);
                break;
            case 8:
                result = getRXE08();
                break;
            case 9:
                result = getRXE09();
                break;
            case 10:
                result = getRXE10();
                break;
            case 11:
                result = getCWE(getRXE11() , e_num );
                break;
            case 12:
                result = getRXE12();
                break;
            case 13:
                result = getXCN(getRXE13() , e_num , r_num);
                break;
            case 14:
                result = getXCN(getRXE14() , e_num , r_num);
                break;
            case 15:
                result = getRXE15();
                break;
            case 16:
                result = getRXE16();
                break;
            case 17:
                result = getRXE17();
                break;
            case 18:
                result = getRXE18();
                break;
            case 19:
                result = getRXE19();
                break;
            case 20:
                result = getRXE20();
                break;
            case 21:
                result = getCWE(getRXE21() , e_num , r_num);
                break;
            case 22:
                result = getRXE22();
                break;
            case 23:
                result = getRXE23();
                break;
            case 24:
                result = getCWE(getRXE24() , e_num );
                break;
            case 25:
                result = getRXE25();
                break;
            case 26:
                result = getCWE(getRXE26() , e_num );
                break;
            case 27:
                result = getCWE(getRXE27() , e_num , r_num);
                break;
            case 28:
                result = getRXE28();
                break;
            case 29:
                result = getCWE(getRXE29() , e_num );
                break;
            case 30:
                result = getRXE30();
                break;
            case 31:
                result = getCWE(getRXE31() , e_num , r_num);
                break;
            case 32:
                result = getRXE32();
                break;
            case 33:
                result = getRXE33();
                break;
            case 34:
                result = getCWE(getRXE34() , e_num );
                break;
            case 35:
                result = getCWE(getRXE35() , e_num );
                break;
            case 36:
                result = getRXE36();
                break;
            case 37:
                result = getCWE(getRXE37() , e_num , r_num);
                break;
            case 38:
                result = getCWE(getRXE38() , e_num );
                break;
            case 39:
                result = getRXE39();
                break;
            case 40:
                result = getCWE(getRXE40() , e_num );
                break;
            case 41:
                result = getXAD(getRXE41() , e_num );
                break;
            case 42:
                result = getPL(getRXE42() , e_num );
                break;
            case 43:
                result = getXAD(getRXE43() , e_num );
                break;
            case 44:
                result = getRXE44();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }

    /**
     *  RXEのゲット
     *
     * @param f_num フィールドNo　必須
     */
    public Object getRXEOb(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getRXE00();
                break;
            case 1:
                result = getRXE01();
                break;
            case 2:
                result = getRXE02();
                break;
            case 3:
                result = getRXE03();
                break;
            case 4:
                result = getRXE04();
                break;
            case 5:
                result = getRXE05();
                break;
            case 6:
                result = getRXE06();
                break;
            case 7:
                result = getRXE07();
                break;
            case 8:
                result = getRXE08();
                break;
            case 9:
                result = getRXE09();
                break;
            case 10:
                result = getRXE10();
                break;
            case 11:
                result = getRXE11();
                break;
            case 12:
                result = getRXE12();
                break;
            case 13:
                result = getRXE13();
                break;
            case 14:
                result = getRXE14();
                break;
            case 15:
                result = getRXE15();
                break;
            case 16:
                result = getRXE16();
                break;
            case 17:
                result = getRXE17();
                break;
            case 18:
                result = getRXE18();
                break;
            case 19:
                result = getRXE19();
                break;
            case 20:
                result = getRXE20();
                break;
            case 21:
                result = getRXE21();
                break;
            case 22:
                result = getRXE22();
                break;
            case 23:
                result = getRXE23();
                break;
            case 24:
                result = getRXE24();
                break;
            case 25:
                result = getRXE25();
                break;
            case 26:
                result = getRXE26();
                break;
            case 27:
                result = getRXE27();
                break;
            case 28:
                result = getRXE28();
                break;
            case 29:
                result = getRXE29();
                break;
            case 30:
                result = getRXE30();
                break;
            case 31:
                result = getRXE31();
                break;
            case 32:
                result = getRXE32();
                break;
            case 33:
                result = getRXE33();
                break;
            case 34:
                result = getRXE34();
                break;
            case 35:
                result = getRXE35();
                break;
            case 36:
                result = getRXE36();
                break;
            case 37:
                result = getRXE37();
                break;
            case 38:
                result = getRXE38();
                break;
            case 39:
                result = getRXE39();
                break;
            case 40:
                result = getRXE40();
                break;
            case 41:
                result = getRXE41();
                break;
            case 42:
                result = getRXE42();
                break;
            case 43:
                result = getRXE43();
                break;
            case 44:
                result = getRXE44();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }
}
