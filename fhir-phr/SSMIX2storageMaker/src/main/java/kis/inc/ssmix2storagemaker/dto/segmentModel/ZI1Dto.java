/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XADDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XPNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XTNDto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class ZI1Dto  extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ZI1Dto.class);

    /**
     * セグメントID
     */
    private String ZI100;    
    /**
     * セットID-ZI1
     */
    private String ZI101;    
    /**
     * 保険プランID
     */
    private CWEDto ZI102;    
    /**
     * 保険会社ID
     */
    private String ZI103;    
    /**
     * 保険会社名
     */
    private String ZI104;    
    /**
     * 保険会社住所
     */
    private XADDto ZI105;    
    /**
     * 保険会社連絡者
     */
    private List<XPNDto> ZI106;    
    /**
     * 保険会社電話番号
     */
    private XTNDto ZI107;    
    /**
     * グループ番号
     */
    private String ZI108;    
    /**
     * ウループ名
     */
    private String ZI109;    
    /**
     * 被保険者グループ雇用者ID
     */
    private String ZI110;    
    /**
     * 被保険者グループ雇用者名
     */
    private String ZI111;    
    /**
     * プラン有効日付
     */
    private String ZI112;    
    /**
     * プラン失効日付
     */
    private String ZI113;    
    /**
     * 認定情報
     */
    private String ZI114;    
    /**
     * プランタイプ
     */
    private String ZI115;    
    /**
     * 被保険者名
     */
    private List<XPNDto> ZI116;    
    /**
     * 被保険者と患者の関係
     */
    private CWEDto ZI117;    
    /**
     * 被保険者生年月日
     */
    private String ZI118;    
    /**
     * 被保険者住所
     */
    private XADDto ZI119;    
    /**
     * 給付金の配分
     */
    private String ZI120;    
    /**
     * 給付金の調整
     */
    private String ZI121;    
    /**
     * 給付金優先順位の調整
     */
    private String ZI122;    
    /**
     * 入院通知フラグ
     */
    private String ZI123;    
    /**
     * 入院通知日付
     */
    private String ZI124;    
    /**
     * 有資格報告フラグ
     */
    private String ZI125;    
    /**
     * 有資格報告日付
     */
    private String ZI126;    
    /**
     * 情報公開コード
     */
    private String ZI127;    
    /**
     * 入院前認定書
     */
    private String ZI128;    
    /**
     * 確認日付/時刻
     */
    private String ZI129;    
    /**
     * 確認書
     */
    private List<XCNDto> ZI130;    
    /**
     * 同意種別コード
     */
    private String ZI131;    
    /**
     * 請求状況
     */
    private String ZI132;    
    /**
     * 生涯予備日数
     */
    private String ZI133;    
    /**
     * 生涯予備日数までの遅延
     */
    private String ZI134;    
    /**
     * 会社プランコード
     */
    private String ZI135;    
    /**
     * ポリシーコード
     */
    private String ZI136;    
    /**
     * 免責ポリシー
     */
    private String ZI137;    
    /**
     * ポリシー総額
     */
    private String ZI138;    
    /**
     * ポリシー限度日数
     */
    private String ZI139;    
    /**
     * 室料ー準備室
     */
    private String ZI140;    
    /**
     * 室料ー個室
     */
    private String ZI141;    
    /**
     * 被保険者雇用状態
     */
    private CWEDto ZI142;    
    /**
     * 被保険者性別
     */
    private String ZI143;    
    /**
     * 被保険者雇用者住所
     */
    private XADDto ZI144;    
    /**
     * 確認状態
     */
    private String ZI145;    
    /**
     * 以前の保険プランID
     */
    private String ZI146;    
    /**
     * 保険範囲タイプ
     */
    private String ZI147;    
    /**
     * ハンディキャップ
     */
    private String ZI148;    
    /**
     * 被保険者ID番号
     */
    private String ZI149;    
    /**
     * 著名コード
     */
    private String ZI150;    
    /**
     * 著名日
     */
    private String ZI151;    
    /**
     * 被保険者出生地
     */
    private String ZI152;    
    /**
     * VIP標識
     */
    private String ZI153;    

    /**
     * @return the ZI100
     */
    public String getZI100() {
        return ZI100;
    }

    /**
     * @param ZI100 the ZI100 to set
     */
    public void setZI100(String ZI100) {
        this.ZI100 = ZI100;
    }

    /**
     * @return the ZI101
     */
    public String getZI101() {
        return ZI101;
    }

    /**
     * @param ZI101 the ZI101 to set
     */
    public void setZI101(String ZI101) {
        this.ZI101 = ZI101;
    }

    /**
     * @return the ZI102
     */
    public CWEDto getZI102() {
        return ZI102;
    }

    /**
     * @param ZI102 the ZI102 to set
     */
    public void setZI102(CWEDto ZI102) {
        this.ZI102 = ZI102;
    }

    /**
     * @return the ZI103
     */
    public String getZI103() {
        return ZI103;
    }

    /**
     * @param ZI103 the ZI103 to set
     */
    public void setZI103(String ZI103) {
        this.ZI103 = ZI103;
    }

    /**
     * @return the ZI104
     */
    public String getZI104() {
        return ZI104;
    }

    /**
     * @param ZI104 the ZI104 to set
     */
    public void setZI104(String ZI104) {
        this.ZI104 = ZI104;
    }

    /**
     * @return the ZI105
     */
    public XADDto getZI105() {
        return ZI105;
    }

    /**
     * @param ZI105 the ZI105 to set
     */
    public void setZI105(XADDto ZI105) {
        this.ZI105 = ZI105;
    }

    /**
     * @return the ZI106
     */
    public List<XPNDto> getZI106() {
        return ZI106;
    }

    /**
     * @param ZI106 the ZI106 to set
     */
    public void setZI106(List<XPNDto> ZI106) {
        this.ZI106 = ZI106;
    }

    /**
     * @return the ZI107
     */
    public XTNDto getZI107() {
        return ZI107;
    }

    /**
     * @param ZI107 the ZI107 to set
     */
    public void setZI107(XTNDto ZI107) {
        this.ZI107 = ZI107;
    }

    /**
     * @return the ZI108
     */
    public String getZI108() {
        return ZI108;
    }

    /**
     * @param ZI108 the ZI108 to set
     */
    public void setZI108(String ZI108) {
        this.ZI108 = ZI108;
    }

    /**
     * @return the ZI109
     */
    public String getZI109() {
        return ZI109;
    }

    /**
     * @param ZI109 the ZI109 to set
     */
    public void setZI109(String ZI109) {
        this.ZI109 = ZI109;
    }

    /**
     * @return the ZI110
     */
    public String getZI110() {
        return ZI110;
    }

    /**
     * @param ZI110 the ZI110 to set
     */
    public void setZI110(String ZI110) {
        this.ZI110 = ZI110;
    }

    /**
     * @return the ZI111
     */
    public String getZI111() {
        return ZI111;
    }

    /**
     * @param ZI111 the ZI111 to set
     */
    public void setZI111(String ZI111) {
        this.ZI111 = ZI111;
    }

    /**
     * @return the ZI112
     */
    public String getZI112() {
        return ZI112;
    }

    /**
     * @param ZI112 the ZI112 to set
     */
    public void setZI112(String ZI112) {
        this.ZI112 = ZI112;
    }

    /**
     * @return the ZI113
     */
    public String getZI113() {
        return ZI113;
    }

    /**
     * @param ZI113 the ZI113 to set
     */
    public void setZI113(String ZI113) {
        this.ZI113 = ZI113;
    }

    /**
     * @return the ZI114
     */
    public String getZI114() {
        return ZI114;
    }

    /**
     * @param ZI114 the ZI114 to set
     */
    public void setZI114(String ZI114) {
        this.ZI114 = ZI114;
    }

    /**
     * @return the ZI115
     */
    public String getZI115() {
        return ZI115;
    }

    /**
     * @param ZI115 the ZI115 to set
     */
    public void setZI115(String ZI115) {
        this.ZI115 = ZI115;
    }

    /**
     * @return the ZI116
     */
    public List<XPNDto> getZI116() {
        return ZI116;
    }

    /**
     * @param ZI116 the ZI116 to set
     */
    public void setZI116(List<XPNDto> ZI116) {
        this.ZI116 = ZI116;
    }

    /**
     * @return the ZI117
     */
    public CWEDto getZI117() {
        return ZI117;
    }

    /**
     * @param ZI117 the ZI117 to set
     */
    public void setZI117(CWEDto ZI117) {
        this.ZI117 = ZI117;
    }

    /**
     * @return the ZI118
     */
    public String getZI118() {
        return ZI118;
    }

    /**
     * @param ZI118 the ZI118 to set
     */
    public void setZI118(String ZI118) {
        this.ZI118 = ZI118;
    }

    /**
     * @return the ZI119
     */
    public XADDto getZI119() {
        return ZI119;
    }

    /**
     * @param ZI119 the ZI119 to set
     */
    public void setZI119(XADDto ZI119) {
        this.ZI119 = ZI119;
    }

    /**
     * @return the ZI120
     */
    public String getZI120() {
        return ZI120;
    }

    /**
     * @param ZI120 the ZI120 to set
     */
    public void setZI120(String ZI120) {
        this.ZI120 = ZI120;
    }

    /**
     * @return the ZI121
     */
    public String getZI121() {
        return ZI121;
    }

    /**
     * @param ZI121 the ZI121 to set
     */
    public void setZI121(String ZI121) {
        this.ZI121 = ZI121;
    }

    /**
     * @return the ZI122
     */
    public String getZI122() {
        return ZI122;
    }

    /**
     * @param ZI122 the ZI122 to set
     */
    public void setZI122(String ZI122) {
        this.ZI122 = ZI122;
    }

    /**
     * @return the ZI123
     */
    public String getZI123() {
        return ZI123;
    }

    /**
     * @param ZI123 the ZI123 to set
     */
    public void setZI123(String ZI123) {
        this.ZI123 = ZI123;
    }

    /**
     * @return the ZI124
     */
    public String getZI124() {
        return ZI124;
    }

    /**
     * @param ZI124 the ZI124 to set
     */
    public void setZI124(String ZI124) {
        this.ZI124 = ZI124;
    }

    /**
     * @return the ZI125
     */
    public String getZI125() {
        return ZI125;
    }

    /**
     * @param ZI125 the ZI125 to set
     */
    public void setZI125(String ZI125) {
        this.ZI125 = ZI125;
    }

    /**
     * @return the ZI126
     */
    public String getZI126() {
        return ZI126;
    }

    /**
     * @param ZI126 the ZI126 to set
     */
    public void setZI126(String ZI126) {
        this.ZI126 = ZI126;
    }

    /**
     * @return the ZI127
     */
    public String getZI127() {
        return ZI127;
    }

    /**
     * @param ZI127 the ZI127 to set
     */
    public void setZI127(String ZI127) {
        this.ZI127 = ZI127;
    }

    /**
     * @return the ZI128
     */
    public String getZI128() {
        return ZI128;
    }

    /**
     * @param ZI128 the ZI128 to set
     */
    public void setZI128(String ZI128) {
        this.ZI128 = ZI128;
    }

    /**
     * @return the ZI129
     */
    public String getZI129() {
        return ZI129;
    }

    /**
     * @param ZI129 the ZI129 to set
     */
    public void setZI129(String ZI129) {
        this.ZI129 = ZI129;
    }

    /**
     * @return the ZI130
     */
    public List<XCNDto> getZI130() {
        return ZI130;
    }

    /**
     * @param ZI130 the ZI130 to set
     */
    public void setZI130(List<XCNDto> ZI130) {
        this.ZI130 = ZI130;
    }

    /**
     * @return the ZI131
     */
    public String getZI131() {
        return ZI131;
    }

    /**
     * @param ZI131 the ZI131 to set
     */
    public void setZI131(String ZI131) {
        this.ZI131 = ZI131;
    }

    /**
     * @return the ZI132
     */
    public String getZI132() {
        return ZI132;
    }

    /**
     * @param ZI132 the ZI132 to set
     */
    public void setZI132(String ZI132) {
        this.ZI132 = ZI132;
    }

    /**
     * @return the ZI133
     */
    public String getZI133() {
        return ZI133;
    }

    /**
     * @param ZI133 the ZI133 to set
     */
    public void setZI133(String ZI133) {
        this.ZI133 = ZI133;
    }

    /**
     * @return the ZI134
     */
    public String getZI134() {
        return ZI134;
    }

    /**
     * @param ZI134 the ZI134 to set
     */
    public void setZI134(String ZI134) {
        this.ZI134 = ZI134;
    }

    /**
     * @return the ZI135
     */
    public String getZI135() {
        return ZI135;
    }

    /**
     * @param ZI135 the ZI135 to set
     */
    public void setZI135(String ZI135) {
        this.ZI135 = ZI135;
    }

    /**
     * @return the ZI136
     */
    public String getZI136() {
        return ZI136;
    }

    /**
     * @param ZI136 the ZI136 to set
     */
    public void setZI136(String ZI136) {
        this.ZI136 = ZI136;
    }

    /**
     * @return the ZI137
     */
    public String getZI137() {
        return ZI137;
    }

    /**
     * @param ZI137 the ZI137 to set
     */
    public void setZI137(String ZI137) {
        this.ZI137 = ZI137;
    }

    /**
     * @return the ZI138
     */
    public String getZI138() {
        return ZI138;
    }

    /**
     * @param ZI138 the ZI138 to set
     */
    public void setZI138(String ZI138) {
        this.ZI138 = ZI138;
    }

    /**
     * @return the ZI139
     */
    public String getZI139() {
        return ZI139;
    }

    /**
     * @param ZI139 the ZI139 to set
     */
    public void setZI139(String ZI139) {
        this.ZI139 = ZI139;
    }

    /**
     * @return the ZI140
     */
    public String getZI140() {
        return ZI140;
    }

    /**
     * @param ZI140 the ZI140 to set
     */
    public void setZI140(String ZI140) {
        this.ZI140 = ZI140;
    }

    /**
     * @return the ZI141
     */
    public String getZI141() {
        return ZI141;
    }

    /**
     * @param ZI141 the ZI141 to set
     */
    public void setZI141(String ZI141) {
        this.ZI141 = ZI141;
    }

    /**
     * @return the ZI142
     */
    public CWEDto getZI142() {
        return ZI142;
    }

    /**
     * @param ZI142 the ZI142 to set
     */
    public void setZI142(CWEDto ZI142) {
        this.ZI142 = ZI142;
    }

    /**
     * @return the ZI143
     */
    public String getZI143() {
        return ZI143;
    }

    /**
     * @param ZI143 the ZI143 to set
     */
    public void setZI143(String ZI143) {
        this.ZI143 = ZI143;
    }

    /**
     * @return the ZI144
     */
    public XADDto getZI144() {
        return ZI144;
    }

    /**
     * @param ZI144 the ZI144 to set
     */
    public void setZI144(XADDto ZI144) {
        this.ZI144 = ZI144;
    }

    /**
     * @return the ZI145
     */
    public String getZI145() {
        return ZI145;
    }

    /**
     * @param ZI145 the ZI145 to set
     */
    public void setZI145(String ZI145) {
        this.ZI145 = ZI145;
    }

    /**
     * @return the ZI146
     */
    public String getZI146() {
        return ZI146;
    }

    /**
     * @param ZI146 the ZI146 to set
     */
    public void setZI146(String ZI146) {
        this.ZI146 = ZI146;
    }

    /**
     * @return the ZI147
     */
    public String getZI147() {
        return ZI147;
    }

    /**
     * @param ZI147 the ZI147 to set
     */
    public void setZI147(String ZI147) {
        this.ZI147 = ZI147;
    }

    /**
     * @return the ZI148
     */
    public String getZI148() {
        return ZI148;
    }

    /**
     * @param ZI148 the ZI148 to set
     */
    public void setZI148(String ZI148) {
        this.ZI148 = ZI148;
    }

    /**
     * @return the ZI149
     */
    public String getZI149() {
        return ZI149;
    }

    /**
     * @param ZI149 the ZI149 to set
     */
    public void setZI149(String ZI149) {
        this.ZI149 = ZI149;
    }

    /**
     * @return the ZI150
     */
    public String getZI150() {
        return ZI150;
    }

    /**
     * @param ZI150 the ZI150 to set
     */
    public void setZI150(String ZI150) {
        this.ZI150 = ZI150;
    }

    /**
     * @return the ZI151
     */
    public String getZI151() {
        return ZI151;
    }

    /**
     * @param ZI151 the ZI151 to set
     */
    public void setZI151(String ZI151) {
        this.ZI151 = ZI151;
    }

    /**
     * @return the ZI152
     */
    public String getZI152() {
        return ZI152;
    }

    /**
     * @param ZI152 the ZI152 to set
     */
    public void setZI152(String ZI152) {
        this.ZI152 = ZI152;
    }

    /**
     * @return the ZI153
     */
    public String getZI153() {
        return ZI153;
    }

    /**
     * @param ZI153 the ZI153 to set
     */
    public void setZI153(String ZI153) {
        this.ZI153 = ZI153;
    }

    /**
     * ZI1のセット
     */
    public void setZI1(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "ZI1" + st_num;

       switch(target){
            case "ZI100":
                setZI100(value);
                break;
            case "ZI101":
                setZI101(value);
                break;
            case "ZI102":
                CWEDto zi102_cwe = setCWE(value);
                setZI102(zi102_cwe);
                break;
            case "ZI103":
                setZI103(value);
                break;
            case "ZI104":
                setZI104(value);
                break;
            case "ZI105":
                XADDto zi105_xad = setXAD(value);
                setZI105(zi105_xad);
                break;
            case "ZI106":
                List<XPNDto> zi106_xpn = setXPN(value);
                setZI106(zi106_xpn);
                break;
            case "ZI107":
                XTNDto zi107_xtn = setXTN(value);
                setZI107(zi107_xtn);
                break;
            case "ZI108":
                setZI108(value);
                break;
            case "ZI109":
                setZI109(value);
                break;
            case "ZI110":
                setZI110(value);
                break;
            case "ZI111":
                setZI111(value);
                break;
            case "ZI112":
                setZI112(value);
                break;
            case "ZI113":
                setZI113(value);
                break;
            case "ZI114":
                setZI114(value);
                break;
            case "ZI115":
                setZI115(value);
                break;
            case "ZI116":
                List<XPNDto>zi116_xpn = setXPN(value);
                setZI116(zi116_xpn);
                break;
            case "ZI117":
                CWEDto zi117_cwe = setCWE(value);
                setZI117(zi117_cwe);
                break;
            case "ZI118":
                setZI118(value);
                break;
            case "ZI119":
                XADDto zi119_xad = setXAD(value);
                setZI119(zi119_xad);
                break;
            case "ZI120":
                setZI120(value);
                break;
            case "ZI121":
                setZI121(value);
                break;
            case "ZI122":
                setZI122(value);
                break;
            case "ZI123":
                setZI123(value);
                break;
            case "ZI124":
                setZI124(value);
                break;
            case "ZI125":
                setZI125(value);
                break;
            case "ZI126":
                setZI126(value);
                break;
            case "ZI127":
                setZI127(value);
                break;
            case "ZI128":
                setZI128(value);
                break;
            case "ZI129":
                setZI129(value);
                break;
            case "ZI130":
                List<XCNDto> zi130_xcn = setXCN(value);
                setZI130(zi130_xcn);
                break;
            case "ZI131":
                setZI131(value);
                break;
            case "ZI132":
                setZI132(value);
                break;
            case "ZI133":
                setZI133(value);
                break;
            case "ZI134":
                setZI134(value);
                break;
            case "ZI135":
                setZI135(value);
                break;
            case "ZI136":
                setZI136(value);
                break;
            case "ZI137":
                setZI137(value);
                break;
            case "ZI138":
                setZI138(value);
                break;
            case "ZI139":
                setZI139(value);
                break;
            case "ZI140":
                setZI140(value);
                break;
            case "ZI141":
                setZI141(value);
                break;
            case "ZI142":
                CWEDto zi142_cwe = setCWE(value);
                setZI142(zi142_cwe);
                break;
            case "ZI143":
                setZI143(value);
                break;
            case "ZI144":
                XADDto zi144_xad = setXAD(value);
                setZI144(zi144_xad);
                break;
            case "ZI145":
                setZI145(value);
                break;
            case "ZI146":
                setZI146(value);
                break;
            case "ZI147":
                setZI147(value);
                break;
            case "ZI148":
                setZI148(value);
                break;
            case "ZI149":
                setZI149(value);
                break;
            case "ZI150":
                setZI150(value);
                break;
            case "ZI151":
                setZI151(value);
                break;
            case "ZI152":
                setZI152(value);
                break;
            case "ZI153":
                setZI153(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
       }
   }
    
    /**
     *  ZI1のゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getZI1(Integer f_num , Integer e_num , Integer r_num){
        String result = null;
        
        switch(f_num){
            case 0:
                result = getZI100();
                break;
            case 1:
                result = getZI101();
                break;
            case 2:
                result = getCWE(getZI102() , e_num );
                break;
            case 3:
                result = getZI103();
                break;
            case 4:
                result = getZI104();
                break;
            case 5:
                result = getXAD(getZI105() , e_num);
                break;
            case 6:
                result = getXPN(getZI106() , e_num , r_num);
                break;
            case 7:
                result = getXTN(getZI107() , e_num);
                break;
            case 8:
                result = getZI108();
                break;
            case 9:
                result = getZI109();
                break;
            case 10:
                result = getZI110() ;
                break;
            case 11:
                result = getZI111();
                break;
            case 12:
                result = getZI112();
                break;
            case 13:
                result = getZI113();
                break;
            case 14:
                result = getZI114();
                break;
            case 15:
                result = getZI115();
                break;
            case 16:
                result = getXPN(getZI116() , e_num ,r_num);
                break;
            case 17:
                result = getCWE(getZI117() , e_num);
                break;
            case 18:
                result = getZI118();
                break;
            case 19:
                result = getXAD(getZI119() , e_num );
                break;
            case 20:
                result = getZI120();
                break;
            case 21:
                result = getZI121();
                break;
            case 22:
                result = getZI122();
                break;
            case 23:
                result = getZI123();
                break;
            case 24:
                result = getZI124();
                break;
            case 25:
                result = getZI125();
                break;
            case 26:
                result = getZI126();
                break;
            case 27:
                result = getZI127();
                break;
            case 28:
                result = getZI128();
                break;
            case 29:
                result = getZI129();
                break;
            case 30:
                result = getXCN(getZI130() , e_num , r_num);
                break;
            case 31:
                result = getZI131();
                break;
            case 32:
                result = getZI132();
                break;
            case 33:
                result = getZI133();
                break;
            case 34:
                result = getZI134();
                break;
            case 35:
                result = getZI135();
                break;
            case 36:
                result = getZI136();
                break;
            case 37:
                result = getZI137();
                break;
            case 38:
                result =getZI138();
                break;
            case 39:
                result = getZI139();
                break;
            case 40:
                result = getZI140();
                break;
            case 41:
                result = getZI141();
                break;
            case 42:
                result = getCWE(getZI142() , e_num );
                break;
            case 43:
                result = getZI143();
                break;
            case 44:
                result = getXAD(getZI144() , e_num);
                break;
            case 45:
                result = getZI145();
                break;
            case 46:
                result = getZI146();
                break;
            case 47:
                result = getZI147();
                break;
            case 48:
                result = getZI148();
                break;
            case 49:
                result = getZI149();
                break;
            case 50:
                result = getZI150();
                break;
            case 51:
                result = getZI151();
                break;
            case 52:
                result = getZI152();
                break;
            case 53:
                result = getZI153();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
     
    /**
     *  ZI1のゲット
     * 
     * @param f_num フィールドNo　必須
     */
    public Object getZI1Ob(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getZI100();
                break;
            case 1:
                result = getZI101();
                break;
            case 2:
                result = getZI102();
                break;
            case 3:
                result = getZI103();
                break;
            case 4:
                result = getZI104();
                break;
            case 5:
                result = getZI105();
                break;
            case 6:
                result = getZI106();
                break;
            case 7:
                result = getZI107();
                break;
            case 8:
                result = getZI108();
                break;
            case 9:
                result = getZI109();
                break;
            case 10:
                result = getZI110();
                break;
            case 11:
                result = getZI111();
                break;
            case 12:
                result = getZI112();
                break;
            case 13:
                result = getZI113();
                break;
            case 14:
                result = getZI114();
                break;
            case 15:
                result = getZI115();
                break;
            case 16:
                result = getZI116();
                break;
            case 17:
                result = getZI117();
                break;
            case 18:
                result = getZI118();
                break;
            case 19:
                result = getZI119();
                break;
            case 20:
                result = getZI120();
                break;
            case 21:
                result = getZI121();
                break;
            case 22:
                result = getZI122();
                break;
            case 23:
                result = getZI123();
                break;
            case 24:
                result = getZI124();
                break;
            case 25:
                result = getZI125();
                break;
            case 26:
                result = getZI126();
                break;
            case 27:
                result = getZI127();
                break;
            case 28:
                result = getZI128();
                break;
            case 29:
                result = getZI129();
                break;
            case 30:
                result = getZI130();
                break;
            case 31:
                result = getZI131();
                break;
            case 32:
                result = getZI132();
                break;
            case 33:
                result = getZI133();
                break;
            case 34:
                result = getZI134();
                break;
            case 35:
                result = getZI135();
                break;
            case 36:
                result = getZI136();
                break;
            case 37:
                result = getZI137();
                break;
            case 38:
                result = getZI138();
                break;
            case 39:
                result = getZI139();
                break;
            case 40:
                result = getZI140();
                break;
            case 41:
                result = getZI141();
                break;
            case 42:
                result = getZI142();
                break;
            case 43:
                result = getZI143();
                break;
            case 44:
                result = getZI144();
                break;
            case 45:
                result = getZI145();
                break;
            case 46:
                result = getZI146();
                break;
            case 47:
                result = getZI147();
                break;
            case 48:
                result = getZI148();
                break;
            case 49:
                result = getZI149();
                break;
            case 50:
                result = getZI150();
                break;
            case 51:
                result = getZI151();
                break;
            case 52:
                result = getZI152();
                break;
            case 53:
                result = getZI153();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }        
}
