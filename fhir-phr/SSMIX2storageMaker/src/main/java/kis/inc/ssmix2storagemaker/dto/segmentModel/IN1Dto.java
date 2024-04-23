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
public class IN1Dto  extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(IN1Dto.class);

    /**
     * セグメントID
     */
    private String IN100;    
    /**
     * セットID-IN1
     */
    private String IN101;    
    /**
     * 保険プランID
     */
    private CWEDto IN102;    
    /**
     * 保険会社ID
     */
    private String IN103;    
    /**
     * 保険会社名
     */
    private String IN104;    
    /**
     * 保険会社住所
     */
    private XADDto IN105;    
    /**
     * 保険会社連絡者
     */
    private List<XPNDto> IN106;    
    /**
     * 保険会社電話番号
     */
    private XTNDto IN107;    
    /**
     * グループ番号
     */
    private String IN108;    
    /**
     * ウループ名
     */
    private String IN109;    
    /**
     * 被保険者グループ雇用者ID
     */
    private String IN110;    
    /**
     * 被保険者グループ雇用者名
     */
    private String IN111;    
    /**
     * プラン有効日付
     */
    private String IN112;    
    /**
     * プラン失効日付
     */
    private String IN113;    
    /**
     * 認定情報
     */
    private String IN114;    
    /**
     * プランタイプ
     */
    private String IN115;    
    /**
     * 被保険者名
     */
    private List<XPNDto> IN116;    
    /**
     * 被保険者と患者の関係
     */
    private CWEDto IN117;    
    /**
     * 被保険者生年月日
     */
    private String IN118;    
    /**
     * 被保険者住所
     */
    private XADDto IN119;    
    /**
     * 給付金の配分
     */
    private String IN120;    
    /**
     * 給付金の調整
     */
    private String IN121;    
    /**
     * 給付金優先順位の調整
     */
    private String IN122;    
    /**
     * 入院通知フラグ
     */
    private String IN123;    
    /**
     * 入院通知日付
     */
    private String IN124;    
    /**
     * 有資格報告フラグ
     */
    private String IN125;    
    /**
     * 有資格報告日付
     */
    private String IN126;    
    /**
     * 情報公開コード
     */
    private String IN127;    
    /**
     * 入院前認定書
     */
    private String IN128;    
    /**
     * 確認日付/時刻
     */
    private String IN129;    
    /**
     * 確認書
     */
    private List<XCNDto> IN130;    
    /**
     * 同意種別コード
     */
    private String IN131;    
    /**
     * 請求状況
     */
    private String IN132;    
    /**
     * 生涯予備日数
     */
    private String IN133;    
    /**
     * 生涯予備日数までの遅延
     */
    private String IN134;    
    /**
     * 会社プランコード
     */
    private String IN135;    
    /**
     * ポリシーコード
     */
    private String IN136;    
    /**
     * 免責ポリシー
     */
    private String IN137;    
    /**
     * ポリシー総額
     */
    private String IN138;    
    /**
     * ポリシー限度日数
     */
    private String IN139;    
    /**
     * 室料ー準備室
     */
    private String IN140;    
    /**
     * 室料ー個室
     */
    private String IN141;    
    /**
     * 被保険者雇用状態
     */
    private CWEDto IN142;    
    /**
     * 被保険者性別
     */
    private String IN143;    
    /**
     * 被保険者雇用者住所
     */
    private XADDto IN144;    
    /**
     * 確認状態
     */
    private String IN145;    
    /**
     * 以前の保険プランID
     */
    private String IN146;    
    /**
     * 保険範囲タイプ
     */
    private String IN147;    
    /**
     * ハンディキャップ
     */
    private String IN148;    
    /**
     * 被保険者ID番号
     */
    private String IN149;    
    /**
     * 著名コード
     */
    private String IN150;    
    /**
     * 著名日
     */
    private String IN151;    
    /**
     * 被保険者出生地
     */
    private String IN152;    
    /**
     * VIP標識
     */
    private String IN153;    

    /**
     * @return the IN100
     */
    public String getIN100() {
        return IN100;
    }

    /**
     * @param IN100 the IN100 to set
     */
    public void setIN100(String IN100) {
        this.IN100 = IN100;
    }

    /**
     * @return the IN101
     */
    public String getIN101() {
        return IN101;
    }

    /**
     * @param IN101 the IN101 to set
     */
    public void setIN101(String IN101) {
        this.IN101 = IN101;
    }

    /**
     * @return the IN102
     */
    public CWEDto getIN102() {
        return IN102;
    }

    /**
     * @param IN102 the IN102 to set
     */
    public void setIN102(CWEDto IN102) {
        this.IN102 = IN102;
    }

    /**
     * @return the IN103
     */
    public String getIN103() {
        return IN103;
    }

    /**
     * @param IN103 the IN103 to set
     */
    public void setIN103(String IN103) {
        this.IN103 = IN103;
    }

    /**
     * @return the IN104
     */
    public String getIN104() {
        return IN104;
    }

    /**
     * @param IN104 the IN104 to set
     */
    public void setIN104(String IN104) {
        this.IN104 = IN104;
    }

    /**
     * @return the IN105
     */
    public XADDto getIN105() {
        return IN105;
    }

    /**
     * @param IN105 the IN105 to set
     */
    public void setIN105(XADDto IN105) {
        this.IN105 = IN105;
    }

    /**
     * @return the IN106
     */
    public List<XPNDto> getIN106() {
        return IN106;
    }

    /**
     * @param IN106 the IN106 to set
     */
    public void setIN106(List<XPNDto> IN106) {
        this.IN106 = IN106;
    }

    /**
     * @return the IN107
     */
    public XTNDto getIN107() {
        return IN107;
    }

    /**
     * @param IN107 the IN107 to set
     */
    public void setIN107(XTNDto IN107) {
        this.IN107 = IN107;
    }

    /**
     * @return the IN108
     */
    public String getIN108() {
        return IN108;
    }

    /**
     * @param IN108 the IN108 to set
     */
    public void setIN108(String IN108) {
        this.IN108 = IN108;
    }

    /**
     * @return the IN109
     */
    public String getIN109() {
        return IN109;
    }

    /**
     * @param IN109 the IN109 to set
     */
    public void setIN109(String IN109) {
        this.IN109 = IN109;
    }

    /**
     * @return the IN110
     */
    public String getIN110() {
        return IN110;
    }

    /**
     * @param IN110 the IN110 to set
     */
    public void setIN110(String IN110) {
        this.IN110 = IN110;
    }

    /**
     * @return the IN111
     */
    public String getIN111() {
        return IN111;
    }

    /**
     * @param IN111 the IN111 to set
     */
    public void setIN111(String IN111) {
        this.IN111 = IN111;
    }

    /**
     * @return the IN112
     */
    public String getIN112() {
        return IN112;
    }

    /**
     * @param IN112 the IN112 to set
     */
    public void setIN112(String IN112) {
        this.IN112 = IN112;
    }

    /**
     * @return the IN113
     */
    public String getIN113() {
        return IN113;
    }

    /**
     * @param IN113 the IN113 to set
     */
    public void setIN113(String IN113) {
        this.IN113 = IN113;
    }

    /**
     * @return the IN114
     */
    public String getIN114() {
        return IN114;
    }

    /**
     * @param IN114 the IN114 to set
     */
    public void setIN114(String IN114) {
        this.IN114 = IN114;
    }

    /**
     * @return the IN115
     */
    public String getIN115() {
        return IN115;
    }

    /**
     * @param IN115 the IN115 to set
     */
    public void setIN115(String IN115) {
        this.IN115 = IN115;
    }

    /**
     * @return the IN116
     */
    public List<XPNDto> getIN116() {
        return IN116;
    }

    /**
     * @param IN116 the IN116 to set
     */
    public void setIN116(List<XPNDto> IN116) {
        this.IN116 = IN116;
    }

    /**
     * @return the IN117
     */
    public CWEDto getIN117() {
        return IN117;
    }

    /**
     * @param IN117 the IN117 to set
     */
    public void setIN117(CWEDto IN117) {
        this.IN117 = IN117;
    }

    /**
     * @return the IN118
     */
    public String getIN118() {
        return IN118;
    }

    /**
     * @param IN118 the IN118 to set
     */
    public void setIN118(String IN118) {
        this.IN118 = IN118;
    }

    /**
     * @return the IN119
     */
    public XADDto getIN119() {
        return IN119;
    }

    /**
     * @param IN119 the IN119 to set
     */
    public void setIN119(XADDto IN119) {
        this.IN119 = IN119;
    }

    /**
     * @return the IN120
     */
    public String getIN120() {
        return IN120;
    }

    /**
     * @param IN120 the IN120 to set
     */
    public void setIN120(String IN120) {
        this.IN120 = IN120;
    }

    /**
     * @return the IN121
     */
    public String getIN121() {
        return IN121;
    }

    /**
     * @param IN121 the IN121 to set
     */
    public void setIN121(String IN121) {
        this.IN121 = IN121;
    }

    /**
     * @return the IN122
     */
    public String getIN122() {
        return IN122;
    }

    /**
     * @param IN122 the IN122 to set
     */
    public void setIN122(String IN122) {
        this.IN122 = IN122;
    }

    /**
     * @return the IN123
     */
    public String getIN123() {
        return IN123;
    }

    /**
     * @param IN123 the IN123 to set
     */
    public void setIN123(String IN123) {
        this.IN123 = IN123;
    }

    /**
     * @return the IN124
     */
    public String getIN124() {
        return IN124;
    }

    /**
     * @param IN124 the IN124 to set
     */
    public void setIN124(String IN124) {
        this.IN124 = IN124;
    }

    /**
     * @return the IN125
     */
    public String getIN125() {
        return IN125;
    }

    /**
     * @param IN125 the IN125 to set
     */
    public void setIN125(String IN125) {
        this.IN125 = IN125;
    }

    /**
     * @return the IN126
     */
    public String getIN126() {
        return IN126;
    }

    /**
     * @param IN126 the IN126 to set
     */
    public void setIN126(String IN126) {
        this.IN126 = IN126;
    }

    /**
     * @return the IN127
     */
    public String getIN127() {
        return IN127;
    }

    /**
     * @param IN127 the IN127 to set
     */
    public void setIN127(String IN127) {
        this.IN127 = IN127;
    }

    /**
     * @return the IN128
     */
    public String getIN128() {
        return IN128;
    }

    /**
     * @param IN128 the IN128 to set
     */
    public void setIN128(String IN128) {
        this.IN128 = IN128;
    }

    /**
     * @return the IN129
     */
    public String getIN129() {
        return IN129;
    }

    /**
     * @param IN129 the IN129 to set
     */
    public void setIN129(String IN129) {
        this.IN129 = IN129;
    }

    /**
     * @return the IN130
     */
    public List<XCNDto> getIN130() {
        return IN130;
    }

    /**
     * @param IN130 the IN130 to set
     */
    public void setIN130(List<XCNDto> IN130) {
        this.IN130 = IN130;
    }

    /**
     * @return the IN131
     */
    public String getIN131() {
        return IN131;
    }

    /**
     * @param IN131 the IN131 to set
     */
    public void setIN131(String IN131) {
        this.IN131 = IN131;
    }

    /**
     * @return the IN132
     */
    public String getIN132() {
        return IN132;
    }

    /**
     * @param IN132 the IN132 to set
     */
    public void setIN132(String IN132) {
        this.IN132 = IN132;
    }

    /**
     * @return the IN133
     */
    public String getIN133() {
        return IN133;
    }

    /**
     * @param IN133 the IN133 to set
     */
    public void setIN133(String IN133) {
        this.IN133 = IN133;
    }

    /**
     * @return the IN134
     */
    public String getIN134() {
        return IN134;
    }

    /**
     * @param IN134 the IN134 to set
     */
    public void setIN134(String IN134) {
        this.IN134 = IN134;
    }

    /**
     * @return the IN135
     */
    public String getIN135() {
        return IN135;
    }

    /**
     * @param IN135 the IN135 to set
     */
    public void setIN135(String IN135) {
        this.IN135 = IN135;
    }

    /**
     * @return the IN136
     */
    public String getIN136() {
        return IN136;
    }

    /**
     * @param IN136 the IN136 to set
     */
    public void setIN136(String IN136) {
        this.IN136 = IN136;
    }

    /**
     * @return the IN137
     */
    public String getIN137() {
        return IN137;
    }

    /**
     * @param IN137 the IN137 to set
     */
    public void setIN137(String IN137) {
        this.IN137 = IN137;
    }

    /**
     * @return the IN138
     */
    public String getIN138() {
        return IN138;
    }

    /**
     * @param IN138 the IN138 to set
     */
    public void setIN138(String IN138) {
        this.IN138 = IN138;
    }

    /**
     * @return the IN139
     */
    public String getIN139() {
        return IN139;
    }

    /**
     * @param IN139 the IN139 to set
     */
    public void setIN139(String IN139) {
        this.IN139 = IN139;
    }

    /**
     * @return the IN140
     */
    public String getIN140() {
        return IN140;
    }

    /**
     * @param IN140 the IN140 to set
     */
    public void setIN140(String IN140) {
        this.IN140 = IN140;
    }

    /**
     * @return the IN141
     */
    public String getIN141() {
        return IN141;
    }

    /**
     * @param IN141 the IN141 to set
     */
    public void setIN141(String IN141) {
        this.IN141 = IN141;
    }

    /**
     * @return the IN142
     */
    public CWEDto getIN142() {
        return IN142;
    }

    /**
     * @param IN142 the IN142 to set
     */
    public void setIN142(CWEDto IN142) {
        this.IN142 = IN142;
    }

    /**
     * @return the IN143
     */
    public String getIN143() {
        return IN143;
    }

    /**
     * @param IN143 the IN143 to set
     */
    public void setIN143(String IN143) {
        this.IN143 = IN143;
    }

    /**
     * @return the IN144
     */
    public XADDto getIN144() {
        return IN144;
    }

    /**
     * @param IN144 the IN144 to set
     */
    public void setIN144(XADDto IN144) {
        this.IN144 = IN144;
    }

    /**
     * @return the IN145
     */
    public String getIN145() {
        return IN145;
    }

    /**
     * @param IN145 the IN145 to set
     */
    public void setIN145(String IN145) {
        this.IN145 = IN145;
    }

    /**
     * @return the IN146
     */
    public String getIN146() {
        return IN146;
    }

    /**
     * @param IN146 the IN146 to set
     */
    public void setIN146(String IN146) {
        this.IN146 = IN146;
    }

    /**
     * @return the IN147
     */
    public String getIN147() {
        return IN147;
    }

    /**
     * @param IN147 the IN147 to set
     */
    public void setIN147(String IN147) {
        this.IN147 = IN147;
    }

    /**
     * @return the IN148
     */
    public String getIN148() {
        return IN148;
    }

    /**
     * @param IN148 the IN148 to set
     */
    public void setIN148(String IN148) {
        this.IN148 = IN148;
    }

    /**
     * @return the IN149
     */
    public String getIN149() {
        return IN149;
    }

    /**
     * @param IN149 the IN149 to set
     */
    public void setIN149(String IN149) {
        this.IN149 = IN149;
    }

    /**
     * @return the IN150
     */
    public String getIN150() {
        return IN150;
    }

    /**
     * @param IN150 the IN150 to set
     */
    public void setIN150(String IN150) {
        this.IN150 = IN150;
    }

    /**
     * @return the IN151
     */
    public String getIN151() {
        return IN151;
    }

    /**
     * @param IN151 the IN151 to set
     */
    public void setIN151(String IN151) {
        this.IN151 = IN151;
    }

    /**
     * @return the IN152
     */
    public String getIN152() {
        return IN152;
    }

    /**
     * @param IN152 the IN152 to set
     */
    public void setIN152(String IN152) {
        this.IN152 = IN152;
    }

    /**
     * @return the IN153
     */
    public String getIN153() {
        return IN153;
    }

    /**
     * @param IN153 the IN153 to set
     */
    public void setIN153(String IN153) {
        this.IN153 = IN153;
    }

    /**
     * IN1のセット
     */
    public void setIN1(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "IN1" + st_num;

       switch(target){
            case "IN100":
                setIN100(value);
                break;
            case "IN101":
                setIN101(value);
                break;
            case "IN102":
                CWEDto in102_cwe = setCWE(value);
                setIN102(in102_cwe);
                break;
            case "IN103":
                setIN103(value);
                break;
            case "IN104":
                setIN104(value);
                break;
            case "IN105":
                XADDto in105_xad = setXAD(value);
                setIN105(in105_xad);
                break;
            case "IN106":
                List<XPNDto> in106_xpn = setXPN(value);
                setIN106(in106_xpn);
                break;
            case "IN107":
                XTNDto in107_xtn = setXTN(value);
                setIN107(in107_xtn);
                break;
            case "IN108":
                setIN108(value);
                break;
            case "IN109":
                setIN109(value);
                break;
            case "IN110":
                setIN110(value);
                break;
            case "IN111":
                setIN111(value);
                break;
            case "IN112":
                setIN112(value);
                break;
            case "IN113":
                setIN113(value);
                break;
            case "IN114":
                setIN114(value);
                break;
            case "IN115":
                setIN115(value);
                break;
            case "IN116":
                List<XPNDto>in116_xpn = setXPN(value);
                setIN116(in116_xpn);
                break;
            case "IN117":
                CWEDto in117_cwe = setCWE(value);
                setIN117(in117_cwe);
                break;
            case "IN118":
                setIN118(value);
                break;
            case "IN119":
                XADDto in119_xad = setXAD(value);
                setIN119(in119_xad);
                break;
            case "IN120":
                setIN120(value);
                break;
            case "IN121":
                setIN121(value);
                break;
            case "IN122":
                setIN122(value);
                break;
            case "IN123":
                setIN123(value);
                break;
            case "IN124":
                setIN124(value);
                break;
            case "IN125":
                setIN125(value);
                break;
            case "IN126":
                setIN126(value);
                break;
            case "IN127":
                setIN127(value);
                break;
            case "IN128":
                setIN128(value);
                break;
            case "IN129":
                setIN129(value);
                break;
            case "IN130":
                List<XCNDto> in130_xcn = setXCN(value);
                setIN130(in130_xcn);
                break;
            case "IN131":
                setIN131(value);
                break;
            case "IN132":
                setIN132(value);
                break;
            case "IN133":
                setIN133(value);
                break;
            case "IN134":
                setIN134(value);
                break;
            case "IN135":
                setIN135(value);
                break;
            case "IN136":
                setIN136(value);
                break;
            case "IN137":
                setIN137(value);
                break;
            case "IN138":
                setIN138(value);
                break;
            case "IN139":
                setIN139(value);
                break;
            case "IN140":
                setIN140(value);
                break;
            case "IN141":
                setIN141(value);
                break;
            case "IN142":
                CWEDto in142_cwe = setCWE(value);
                setIN142(in142_cwe);
                break;
            case "IN143":
                setIN143(value);
                break;
            case "IN144":
                XADDto in144_xad = setXAD(value);
                setIN144(in144_xad);
                break;
            case "IN145":
                setIN145(value);
                break;
            case "IN146":
                setIN146(value);
                break;
            case "IN147":
                setIN147(value);
                break;
            case "IN148":
                setIN148(value);
                break;
            case "IN149":
                setIN149(value);
                break;
            case "IN150":
                setIN150(value);
                break;
            case "IN151":
                setIN151(value);
                break;
            case "IN152":
                setIN152(value);
                break;
            case "IN153":
                setIN153(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
       }
   }
    
    /**
     *  IN1のゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getIN1(Integer f_num , Integer e_num , Integer r_num){
        String result = null;
        
        switch(f_num){
            case 0:
                result = getIN100();
                break;
            case 1:
                result = getIN101();
                break;
            case 2:
                result = getCWE(getIN102() , e_num );
                break;
            case 3:
                result = getIN103();
                break;
            case 4:
                result = getIN104();
                break;
            case 5:
                result = getXAD(getIN105() , e_num);
                break;
            case 6:
                result = getXPN(getIN106() , e_num , r_num);
                break;
            case 7:
                result = getXTN(getIN107() , e_num);
                break;
            case 8:
                result = getIN108();
                break;
            case 9:
                result = getIN109();
                break;
            case 10:
                result = getIN110() ;
                break;
            case 11:
                result = getIN111();
                break;
            case 12:
                result = getIN112();
                break;
            case 13:
                result = getIN113();
                break;
            case 14:
                result = getIN114();
                break;
            case 15:
                result = getIN115();
                break;
            case 16:
                result = getXPN(getIN116() , e_num ,r_num);
                break;
            case 17:
                result = getCWE(getIN117() , e_num);
                break;
            case 18:
                result = getIN118();
                break;
            case 19:
                result = getXAD(getIN119() , e_num );
                break;
            case 20:
                result = getIN120();
                break;
            case 21:
                result = getIN121();
                break;
            case 22:
                result = getIN122();
                break;
            case 23:
                result = getIN123();
                break;
            case 24:
                result = getIN124();
                break;
            case 25:
                result = getIN125();
                break;
            case 26:
                result = getIN126();
                break;
            case 27:
                result = getIN127();
                break;
            case 28:
                result = getIN128();
                break;
            case 29:
                result = getIN129();
                break;
            case 30:
                result = getXCN(getIN130() , e_num , r_num);
                break;
            case 31:
                result = getIN131();
                break;
            case 32:
                result = getIN132();
                break;
            case 33:
                result = getIN133();
                break;
            case 34:
                result = getIN134();
                break;
            case 35:
                result = getIN135();
                break;
            case 36:
                result = getIN136();
                break;
            case 37:
                result = getIN137();
                break;
            case 38:
                result =getIN138();
                break;
            case 39:
                result = getIN139();
                break;
            case 40:
                result = getIN140();
                break;
            case 41:
                result = getIN141();
                break;
            case 42:
                result = getCWE(getIN142() , e_num );
                break;
            case 43:
                result = getIN143();
                break;
            case 44:
                result = getXAD(getIN144() , e_num);
                break;
            case 45:
                result = getIN145();
                break;
            case 46:
                result = getIN146();
                break;
            case 47:
                result = getIN147();
                break;
            case 48:
                result = getIN148();
                break;
            case 49:
                result = getIN149();
                break;
            case 50:
                result = getIN150();
                break;
            case 51:
                result = getIN151();
                break;
            case 52:
                result = getIN152();
                break;
            case 53:
                result = getIN153();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
     
    /**
     *  IN1のゲット
     * 
     * @param f_num フィールドNo　必須
     */
    public Object getIN1Ob(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getIN100();
                break;
            case 1:
                result = getIN101();
                break;
            case 2:
                result = getIN102();
                break;
            case 3:
                result = getIN103();
                break;
            case 4:
                result = getIN104();
                break;
            case 5:
                result = getIN105();
                break;
            case 6:
                result = getIN106();
                break;
            case 7:
                result = getIN107();
                break;
            case 8:
                result = getIN108();
                break;
            case 9:
                result = getIN109();
                break;
            case 10:
                result = getIN110();
                break;
            case 11:
                result = getIN111();
                break;
            case 12:
                result = getIN112();
                break;
            case 13:
                result = getIN113();
                break;
            case 14:
                result = getIN114();
                break;
            case 15:
                result = getIN115();
                break;
            case 16:
                result = getIN116();
                break;
            case 17:
                result = getIN117();
                break;
            case 18:
                result = getIN118();
                break;
            case 19:
                result = getIN119();
                break;
            case 20:
                result = getIN120();
                break;
            case 21:
                result = getIN121();
                break;
            case 22:
                result = getIN122();
                break;
            case 23:
                result = getIN123();
                break;
            case 24:
                result = getIN124();
                break;
            case 25:
                result = getIN125();
                break;
            case 26:
                result = getIN126();
                break;
            case 27:
                result = getIN127();
                break;
            case 28:
                result = getIN128();
                break;
            case 29:
                result = getIN129();
                break;
            case 30:
                result = getIN130();
                break;
            case 31:
                result = getIN131();
                break;
            case 32:
                result = getIN132();
                break;
            case 33:
                result = getIN133();
                break;
            case 34:
                result = getIN134();
                break;
            case 35:
                result = getIN135();
                break;
            case 36:
                result = getIN136();
                break;
            case 37:
                result = getIN137();
                break;
            case 38:
                result = getIN138();
                break;
            case 39:
                result = getIN139();
                break;
            case 40:
                result = getIN140();
                break;
            case 41:
                result = getIN141();
                break;
            case 42:
                result = getIN142();
                break;
            case 43:
                result = getIN143();
                break;
            case 44:
                result = getIN144();
                break;
            case 45:
                result = getIN145();
                break;
            case 46:
                result = getIN146();
                break;
            case 47:
                result = getIN147();
                break;
            case 48:
                result = getIN148();
                break;
            case 49:
                result = getIN149();
                break;
            case 50:
                result = getIN150();
                break;
            case 51:
                result = getIN151();
                break;
            case 52:
                result = getIN152();
                break;
            case 53:
                result = getIN153();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }        
}
