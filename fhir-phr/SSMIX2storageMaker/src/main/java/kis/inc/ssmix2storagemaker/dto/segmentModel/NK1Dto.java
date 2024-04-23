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
import kis.inc.ssmix2storagemaker.dto.fieldModel.XADDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XPNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XTNDto;

/**
 *
 * @author kis-note
 */
public class NK1Dto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(NK1Dto.class);
    /**
     * セグメント ID
     */
    private String NK100;
    /**
     * セット ID-NK1
     */
    private String NK101;
    /**
     * 氏名
     */
    private List<XPNDto> NK102;
    /**
     * 続柄
     */
    private CWEDto NK103;
    /**
     * 住所
     */
    private List<XADDto> NK104;
    /**
     * 電話番号
     */
    private List<XTNDto> NK105;
    /**
     * 勤務先電話番号
     */
    private List<XTNDto> NK106;
    /**
     * 役割
     */
    private CWEDto NK107;
    /**
     * 役割の開始日
     */
    private String NK108;
    /**
     * 役割の終了日
     */
    private String NK109;
    /**
     * 肩書
     */
    private String NK110;
    /**
     * 職種・職位
     */
    private String NK111;
    /**
     * 従業員番号
     */
    private String NK112;
    /**]
     * 所属組織名
     */
    private List<XCNDto> NK113;
    /**
     * 結婚状態
     */
    private String NK114;
    /**
     * 性別
     */
    private String NK115;
    /**
     * 生年月日
     */
    private String NK116;
    /**
     * 生活依存
     */
    private List<String> NK117;
    /**
     * 介護区分
     */
    private List<String> NK118;
    /**
     * 市民権情報
     */
    private List<CWEDto> NK119;
    /**
     * 使用言語
     */
    private CWEDto NK120;
    /**
     * 生活様式
     */
    private String NK121;
    /**
     * 周知標識
     */
    private CWEDto NK122;
    /**
     * 保護標識
     */
    private String NK123;
    /**
     * 学生標識
     */
    private String NK124;
    /**
     * 宗教
     */
    private CWEDto NK125;
    /**
     * 母親の旧姓
     */
    private List<XPNDto> NK126;
    /**
     * 国籍
     */
    private CWEDto NK127;
    /**
     * 民族
     */
    private List<CWEDto> NK128;
    /**
     * 連絡理由
     */
    private List<CWEDto> NK129;
    /**
     * 連絡先の名前
     */
    private List<XPNDto> NK130;
    /**
     * 連絡先の電話番号
     */
    private List<XTNDto> NK131;
    /**
     * 連絡先の住所
     */
    private XADDto NK132;
    /**
     * 近親者識別情報
     */
    private List<String> NK133;
    /**
     * 職業状態
     */
    private String NK134;
    /**
     * 人種
     */
    private List<CWEDto> NK135;
    /**
     * 障害情報
     */
    private String NK136;
    /**
     * 連絡先の社会保険番号
     */
    private String NK137;
    /**
     * 近親者生誕地
     */
    private String NK138;
    /**
     * VIP 標識
     */
    private String NK139;

    /**
     * @return the PV100
     */
    public String getNK100() {
        return NK100;
    }

    /**
     * @param NK100 the NK100 to set
     */
    public void setNK100(String NK100) {
        this.NK100 = NK100;
    }

    /**
     * @return the NK101
     */
    public String getNK101() {
        return NK101;
    }

    /**
     * @param NK101 the NK101 to set
     */
    public void setNK101(String NK101) {
        this.NK101 = NK101;
    }

    /**
     * @return the NK102
     */
    public List<XPNDto> getNK102() {
        return NK102;
    }

    /**
     * @param NK102 the NK102 to set
     */
    public void setNK102(List<XPNDto> NK102) {
        this.NK102 = NK102;
    }

    /**
     * @return the NK103
     */
    public CWEDto getNK103() {
        return NK103;
    }

    /**
     * @param NK103 the NK103 to set
     */
    public void setNK103(CWEDto NK103) {
        this.NK103 = NK103;
    }

    /**
     * @return the NK104
     */
    public List<XADDto> getNK104() {
        return NK104;
    }

    /**
     * @param NK104 the NK104 to set
     */
    public void setNK104(List<XADDto> NK104) {
        this.NK104 = NK104;
    }

    /**
     * @return the NK105
     */
    public List<XTNDto> getNK105() {
        return NK105;
    }

    /**
     * @param NK105 the NK105 to set
     */
    public void setNK105(List<XTNDto> NK105) {
        this.NK105 = NK105;
    }

    /**
     * @return the NK106
     */
    public List<XTNDto> getNK106() {
        return NK106;
    }

    /**
     * @param NK106 the NK106 to set
     */
    public void setNK106(List<XTNDto> NK106) {
        this.NK106 = NK106;
    }

    /**
     * @return the NK107
     */
    public CWEDto getNK107() {
        return NK107;
    }

    /**
     * @param NK107 the NK107 to set
     */
    public void setNK107(CWEDto NK107) {
        this.NK107 = NK107;
    }

    /**
     * @return the NK108
     */
    public String getNK108() {
        return NK108;
    }

    /**
     * @param NK108 the NK108 to set
     */
    public void setNK108(String NK108) {
        this.NK108 = NK108;
    }

    /**
     * @return the NK109
     */
    public String getNK109() {
        return NK109;
    }

    /**
     * @param NK109 the NK109 to set
     */
    public void setNK109(String NK109) {
        this.NK109 = NK109;
    }

    /**
     * @return the NK110
     */
    public String getNK110() {
        return NK110;
    }

    /**
     * @param NK110 the NK110 to set
     */
    public void setNK110(String NK110) {
        this.NK110 = NK110;
    }

    /**
     * @return the NK111
     */
    public String getNK111() {
        return NK111;
    }

    /**
     * @param NK111 the NK111 to set
     */
    public void setNK111(String NK111) {
        this.NK111 = NK111;
    }

    /**
     * @return the NK112
     */
    public String getNK112() {
        return NK112;
    }

    /**
     * @param NK112 the NK112 to set
     */
    public void setNK112(String NK112) {
        this.NK112 = NK112;
    }

    /**
     * @return the NK113
     */
    public List<XCNDto> getNK113() {
        return NK113;
    }

    /**
     * @param NK113 the NK113 to set
     */
    public void setNK113(List<XCNDto> NK113) {
        this.NK113 = NK113;
    }

    /**
     * @return the NK114
     */
    public String getNK114() {
        return NK114;
    }

    /**
     * @param NK114 the NK114 to set
     */
    public void setNK114(String NK114) {
        this.NK114 = NK114;
    }

    /**
     * @return the NK115
     */
    public String getNK115() {
        return NK115;
    }

    /**
     * @param NK115 the NK115 to set
     */
    public void setNK115(String NK115) {
        this.NK115 = NK115;
    }

    /**
     * @return the NK116
     */
    public String getNK116() {
        return NK116;
    }

    /**
     * @param NK116 the NK116 to set
     */
    public void setNK116(String NK116) {
        this.NK116 = NK116;
    }

    /**
     * @return the NK117
     */
    public List<String> getNK117() {
        return NK117;
    }

    /**
     * @param NK117 the NK117 to set
     */
    public void setNK117(List<String> NK117) {
        this.NK117 = NK117;
    }

    /**
     * @return the NK118
     */
    public List<String> getNK118() {
        return NK118;
    }

    /**
     * @param NK118 the NK118 to set
     */
    public void setNK118(List<String> NK118) {
        this.NK118 = NK118;
    }

    /**
     * @return the NK119
     */
    public List<CWEDto> getNK119() {
        return NK119;
    }

    /**
     * @param NK119 the NK119 to set
     */
    public void setNK119(List<CWEDto> NK119) {
        this.NK119 = NK119;
    }

    /**
     * @return the NK120
     */
    public CWEDto getNK120() {
        return NK120;
    }

    /**
     * @param NK120 the NK120 to set
     */
    public void setNK120(CWEDto NK120) {
        this.NK120 = NK120;
    }

    /**
     * @return the NK121
     */
    public String getNK121() {
        return NK121;
    }

    /**
     * @param NK121 the NK121 to set
     */
    public void setNK121(String NK121) {
        this.NK121 = NK121;
    }

    /**
     * @return the NK122
     */
    public CWEDto getNK122() {
        return NK122;
    }

    /**
     * @param NK122 the NK122 to set
     */
    public void setNK122(CWEDto NK122) {
        this.NK122 = NK122;
    }

    /**
     * @return the NK123
     */
    public String getNK123() {
        return NK123;
    }

    /**
     * @param NK123 the NK123 to set
     */
    public void setNK123(String NK123) {
        this.NK123 = NK123;
    }

    /**
     * @return the NK124
     */
    public String getNK124() {
        return NK124;
    }

    /**
     * @param NK124 the NK124 to set
     */
    public void setNK124(String NK124) {
        this.NK124 = NK124;
    }

    /**
     * @return the NK125
     */
    public CWEDto getNK125() {
        return NK125;
    }

    /**
     * @param NK125 the NK125 to set
     */
    public void setNK125(CWEDto NK125) {
        this.NK125 = NK125;
    }

    /**
     * @return the NK126
     */
    public List<XPNDto> getNK126() {
        return NK126;
    }

    /**
     * @param NK126 the NK126 to set
     */
    public void setNK126(List<XPNDto> NK126) {
        this.NK126 = NK126;
    }

    /**
     * @return the NK127
     */
    public CWEDto getNK127() {
        return NK127;
    }

    /**
     * @param NK127 the NK127 to set
     */
    public void setNK127(CWEDto NK127) {
        this.NK127 = NK127;
    }

    /**
     * @return the NK128
     */
    public List<CWEDto> getNK128() {
        return NK128;
    }

    /**
     * @param NK128 the NK128 to set
     */
    public void setNK128(List<CWEDto> NK128) {
        this.NK128 = NK128;
    }

    /**
     * @return the NK129
     */
    public List<CWEDto> getNK129() {
        return NK129;
    }

    /**
     * @param NK129 the NK129 to set
     */
    public void setNK129(List<CWEDto> NK129) {
        this.NK129 = NK129;
    }

    /**
     * @return the NK130
     */
    public List<XPNDto> getNK130() {
        return NK130;
    }

    /**
     * @param NK130 the NK130 to set
     */
    public void setNK130(List<XPNDto> NK130) {
        this.NK130 = NK130;
    }

    /**
     * @return the NK131
     */
    public List<XTNDto> getNK131() {
        return NK131;
    }

    /**
     * @param NK131 the NK131 to set
     */
    public void setNK131(List<XTNDto> NK131) {
        this.NK131 = NK131;
    }

    /**
     * @return the NK132
     */
    public XADDto getNK132() {
        return NK132;
    }

    /**
     * @param NK132 the NK132 to set
     */
    public void setNK132(XADDto NK132) {
        this.NK132 = NK132;
    }

    /**
     * @return the NK133
     */
    public List<String> getNK133() {
        return NK133;
    }

    /**
     * @param NK133 the NK133 to set
     */
    public void setNK133(List<String> NK133) {
        this.NK133 = NK133;
    }

    /**
     * @return the NK134
     */
    public String getNK134() {
        return NK134;
    }

    /**
     * @param NK134 the NK134 to set
     */
    public void setNK134(String NK134) {
        this.NK134 = NK134;
    }

    /**
     * @return the NK135
     */
    public List<CWEDto> getNK135() {
        return NK135;
    }

    /**
     * @param NK135 the NK135 to set
     */
    public void setNK135(List<CWEDto> NK135) {
        this.NK135 = NK135;
    }

    /**
     * @return the NK136
     */
    public String getNK136() {
        return NK136;
    }

    /**
     * @param NK136 the NK136 to set
     */
    public void setNK136(String NK136) {
        this.NK136 = NK136;
    }

    /**
     * @return the NK137
     */
    public String getNK137() {
        return NK137;
    }

    /**
     * @param NK137 the NK137 to set
     */
    public void setNK137(String NK137) {
        this.NK137 = NK137;
    }

    /**
     * @return the NK138
     */
    public String getNK138() {
        return NK138;
    }

    /**
     * @param NK138 the NK138 to set
     */
    public void setNK138(String NK138) {
        this.NK138 = NK138;
    }

    /**
     * @return the NK139
     */
    public String getNK139() {
        return NK139;
    }

    /**
     * @param NK139 the NK139 to set
     */
    public void setNK139(String NK139) {
        this.NK139 = NK139;
    }

    /**
     * NK1のセット
     */
    public void setNK1(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "NK1" + st_num;

        switch(target){
            case "NK100":
                setNK100(value);
                break;
            case "NK101":
                setNK101(value);
                break;
            case "NK102":
                List<XPNDto> nk102List = setXPN(value);
                setNK102(nk102List);
                break;
            case "NK103":
            	CWEDto nk103 = setCWE(value);
                setNK103(nk103);
                break;
            case "NK104":
            	List<XADDto> nk104List = setXADList(value);
                setNK104(nk104List);
                break;
            case "NK105":
            	List<XTNDto> nk105List = setXTNList(value);
                setNK105(nk105List);
                break;
            case "NK106":
            	List<XTNDto> nk106List = setXTNList(value);
                setNK106(nk106List);
                break;
            case "NK107":
            	CWEDto nk107 = setCWE(value);
                setNK107(nk107);
                break;
            case "NK108":
                setNK108(value);
                break;
            case "NK109":
                setNK109(value);
                break;
            case "NK110":
                setNK110(value);
                break;
            case "NK111":
                setNK111(value);
                break;
            case "NK112":
                setNK112(value);
                break;
            case "NK113":
                List<XCNDto> nk113List = setXCN(value);
                setNK113(nk113List);
                break;
            case "NK114":
                setNK114(value);
                break;
            case "NK115":
                setNK115(value);
                break;
            case "NK116":
                setNK116(value);
                break;
            case "NK117":
            	List<String> nk117List = new ArrayList<>(Arrays.asList(value.split(getRepeatSp())));
                setNK117(nk117List);
                break;
            case "NK118":
            	List<String> nk118List = new ArrayList<>(Arrays.asList(value.split(getRepeatSp())));
                setNK118(nk118List);
                break;
            case "NK119":
            	List<CWEDto> nk119List = setCWEList(value);
                setNK119(nk119List);
                break;
            case "NK120":
            	CWEDto nk120 = setCWE(value);
                setNK120(nk120);
                break;
            case "NK121":
                setNK121(value);
                break;
            case "NK122":
            	CWEDto nk122= setCWE(value);
                setNK122(nk122);
                break;
            case "NK123":
                setNK123(value);
                break;
            case "NK124":
                setNK124(value);
                break;
            case "NK125":
            	CWEDto nk125= setCWE(value);
                setNK125(nk125);
                break;
            case "NK126":
            	List<XPNDto> nk126List = setXPN(value);
                setNK126(nk126List);
                break;
            case "NK127":
            	CWEDto nk127= setCWE(value);
                setNK127(nk127);
                break;
            case "NK128":
            	List<CWEDto> nk128List = setCWEList(value);
                setNK128(nk128List);
                break;
            case "NK129":
            	List<CWEDto> nk129List = setCWEList(value);
                setNK129(nk129List);
                break;
            case "NK130":
            	List<XPNDto> nk130List = setXPN(value);
                setNK130(nk130List);
                break;
            case "NK131":
            	List<XTNDto> nk131List = setXTNList(value);
                setNK131(nk131List);
                break;
            case "NK132":
            	XADDto nk132= setXAD(value);
                setNK132(nk132);
                break;
            case "NK133":
            	List<String> nk133List = new ArrayList<>(Arrays.asList(value.split(getRepeatSp())));
                setNK133(nk133List);
                break;
            case "NK134":
                setNK134(value);
                break;
            case "NK135":
            	List<CWEDto> nk135List = setCWEList(value);
                setNK135(nk135List);
                break;
            case "NK136":
                setNK136(value);
                break;
            case "NK137":
                setNK137(value);
                break;
            case "NK138":
                setNK138(value);
                break;
            case "NK139":
                setNK139(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }

    }

    /**
     *  NK1のゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getNK1(Integer f_num , Integer e_num , Integer r_num){
        String result = null;

        switch(f_num){
            case 0:
                result = getNK100();
                break;
            case 1:
                result = getNK101();
                break;
            case 2:
                result = getXPN(getNK102() , e_num , r_num);
                break;
            case 3:
                result = getCWE(getNK103() , e_num );
                break;
            case 4:
                result = getXAD(getNK104().get(r_num) , e_num );
                break;
            case 5:
                result = getXTN(getNK105().get(r_num) , e_num);
                break;
            case 6:
                result = getXTN(getNK106().get(r_num) , e_num);
                break;
            case 7:
                result = getCWE(getNK107() , e_num );
                break;
            case 8:
                result = getNK108();
                break;
            case 9:
                result = getNK109();
                break;
            case 10:
                result = getNK110();
                break;
            case 11:
                result = getNK111();
                break;
            case 12:
                result = getNK112();
                break;
            case 13:
                result = getXCN(getNK113() , e_num , r_num);
                break;
            case 14:
                result = getNK114();
                break;
            case 15:
                result = getNK115();
                break;
            case 16:
                result = getNK116();
                break;
            case 17:
                result = getNK117().get(r_num);
                break;
            case 18:
                result = getNK118().get(r_num);
                break;
            case 19:
                result = getCWE(getNK119() , e_num , r_num);
                break;
            case 20:
                result = getCWE(getNK120() , e_num);
                break;
            case 21:
                result = getNK121();
                break;
            case 22:
                result = getCWE(getNK122() , e_num);
                break;
            case 23:
                result = getNK123();
                break;
            case 24:
                result = getNK124();
                break;
            case 25:
                result = getCWE(getNK125() , e_num);
                break;
            case 26:
                result = getXPN(getNK126() , e_num , r_num);
                break;
            case 27:
                result = getCWE(getNK127() , e_num);
                break;
            case 28:
                result = getCWE(getNK128() , e_num , r_num);
                break;
            case 29:
                result = getCWE(getNK129() , e_num , r_num);
                break;
            case 30:
                result = getXPN(getNK130() , e_num , r_num);
                break;
            case 31:
                result = getXTN(getNK131().get(r_num) , e_num);
                break;
            case 32:
                result = getXAD(getNK132() , e_num);
                break;
            case 33:
                result = getNK133().get(e_num);
                break;
            case 34:
                result = getNK134();
                break;
            case 35:
                result = getCWE(getNK135() , e_num , r_num);
                break;
            case 36:
                result = getNK136();
                break;
            case 37:
                result = getNK137();
                break;
            case 38:
                result = getNK138();
                break;
            case 39:
                result = getNK139();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }

    /**
     *  NK1のゲット
     *
     * @param f_num フィールドNo　必須
     */
    public Object getNK1Ob(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getNK100();
                break;
            case 1:
                result = getNK101();
                break;
            case 2:
                result = getNK102();
                break;
            case 3:
                result = getNK103();
                break;
            case 4:
                result = getNK104();
                break;
            case 5:
                result = getNK105();
                break;
            case 6:
                result = getNK106();
                break;
            case 7:
                result = getNK107();
                break;
            case 8:
                result = getNK108();
                break;
            case 9:
                result = getNK109();
                break;
            case 10:
                result = getNK110();
                break;
            case 11:
                result = getNK111();
                break;
            case 12:
                result = getNK112();
                break;
            case 13:
                result = getNK113();
                break;
            case 14:
                result = getNK114();
                break;
            case 15:
                result = getNK115();
                break;
            case 16:
                result = getNK116();
                break;
            case 17:
                result = getNK117();
                break;
            case 18:
                result = getNK118();
                break;
            case 19:
                result = getNK119();
                break;
            case 20:
                result = getNK120();
                break;
            case 21:
                result = getNK121();
                break;
            case 22:
                result = getNK122();
                break;
            case 23:
                result = getNK123();
                break;
            case 24:
                result = getNK124();
                break;
            case 25:
                result = getNK125();
                break;
            case 26:
                result = getNK126();
                break;
            case 27:
                result = getNK127();
                break;
            case 28:
                result = getNK128();
                break;
            case 29:
                result = getNK129();
                break;
            case 30:
                result = getNK130();
                break;
            case 31:
                result = getNK131();
                break;
            case 32:
                result = getNK132();
                break;
            case 33:
                result = getNK133();
                break;
            case 34:
                result = getNK134();
                break;
            case 35:
                result = getNK135();
                break;
            case 36:
                result = getNK136();
                break;
            case 37:
                result = getNK137();
                break;
            case 38:
                result = getNK138();
                break;
            case 39:
                result = getNK139();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }
}
