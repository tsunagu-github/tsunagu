/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;
import kis.inc.ssmix2storagemaker.dto.fieldModel.PLDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.baseFieldDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class PV1Dto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(PV1Dto.class);
    /**
     * セグメントID
     */
    private String PV100;
    /**
     * セットID-PV1
     */
    private String PV101;
    /**
     * 患者区分
     */
    private String PV102;
    /**
     * 患者所在住所
     */
    private PLDto PV103;
    /**
     * 入院区分
     */
    private String PV104;
    /**
     * 事前入院番号
     */
    private String PV105;
    /**
     * 患者の以前の所在
     */
    private PLDto PV106;
    /**
     * 主治医
     */
    private List<XCNDto> PV107;
    /**
     * 紹介医
     */
    private List<XCNDto> PV108;
    /**
     * コンサルタント医師
     */
    private List<XCNDto> PV109;
    /**
     * 診療部門
     */
    private String PV110;
    /**
     * 一時的な所在場所
     */
    private PLDto PV111;
    /**
     * 入院前検査標識
     */
    private String PV112;
    /**]
     * 再入院標識
     */
    private String PV113;
    /**
     * 紹介元
     */
    private String PV114;
    /**
     * 外来状況
     */
    private String PV115;
    /**
     * VIP標識
     */
    private String PV116;
    /**
     * 入院時医師
     */
    private List<XCNDto> PV117;
    /**
     * 患者種別
     */
    private String PV118;
    /**
     * 来院番号
     */
    private String PV119;
    /**
     * 保健種別
     */
    private String PV120;
    /**
     * 費用識別標識
     */
    private String PV121;
    /**
     * 優待コード
     */
    private String PV122;
    /**
     * 信用等級
     */
    private String PV123;
    /**
     * 契約コード
     */
    private String PV124;
    /**
     * 契約発行日
     */
    private String PV125;
    /**
     * 契約金額
     */
    private String PV126;
    /**
     * 契約期間
     */
    private String PV127;
    /**
     * 利息コード
     */
    private String PV128;
    /**
     * 不良負債転換コード
     */
    private String PV129;
    /**
     * 不良負債転換日
     */
    private String PV130;
    /**
     * 不良負債代理店コード
     */
    private String PV131;
    /**
     * 不良負債転換額
     */
    private String PV132;
    /**
     * 不良負債回収額
     */
    private String PV133;
    /**
     * 会計情報削除標識
     */
    private String PV134;
    /**
     * 会計情報削除日
     */
    private String PV135;
    /**
     * 退院区分
     */
    private String PV136;
    /**
     * 退院先
     */
    private String PV137;
    /**
     * 食事種別
     */
    private String PV138;
    /**
     * 担当施設
     */
    private String PV139;
    /**
     * ベット状態
     */
    private String PV140;
    /**
     * 会計状態
     */
    private String PV141;
    /**
     * 保留所在場所
     */
    private PLDto PV142;
    /**
     * 事前の一時的所在
     */
    private PLDto PV143;
    /**
     * 入院日時
     */
    private String PV144;
    /**
     * 退院日時
     */
    private String PV145;
    /**
     * 未納額
     */
    private String PV146;
    /**
     * 総費用
     */
    private String PV147;
    /**
     * 総調整額
     */
    private String PV148;
    /**
     * 合計支払額
     */
    private String PV149;
    /**
     * 代替来院ID
     */
    private String PV150;
    /**
     * 来院標識
     */
    private String PV151;
    /**
     * 他のヘルスケア供給者
     */
    private List<XCNDto> PV152;

    /**
     * @return the PV100
     */
    public String getPV100() {
        return PV100;
    }

    /**
     * @param PV100 the PV100 to set
     */
    public void setPV100(String PV100) {
        this.PV100 = PV100;
    }

    /**
     * @return the PV101
     */
    public String getPV101() {
        return PV101;
    }

    /**
     * @param PV101 the PV101 to set
     */
    public void setPV101(String PV101) {
        this.PV101 = PV101;
    }

    /**
     * @return the PV102
     */
    public String getPV102() {
        return PV102;
    }

    /**
     * @param PV102 the PV102 to set
     */
    public void setPV102(String PV102) {
        this.PV102 = PV102;
    }

    /**
     * @return the PV103
     */
    public PLDto getPV103() {
        return PV103;
    }

    /**
     * @param PV103 the PV103 to set
     */
    public void setPV103(PLDto PV103) {
        this.PV103 = PV103;
    }

    /**
     * @return the PV104
     */
    public String getPV104() {
        return PV104;
    }

    /**
     * @param PV104 the PV104 to set
     */
    public void setPV104(String PV104) {
        this.PV104 = PV104;
    }

    /**
     * @return the PV105
     */
    public String getPV105() {
        return PV105;
    }

    /**
     * @param PV105 the PV105 to set
     */
    public void setPV105(String PV105) {
        this.PV105 = PV105;
    }

    /**
     * @return the PV106
     */
    public PLDto getPV106() {
        return PV106;
    }

    /**
     * @param PV106 the PV106 to set
     */
    public void setPV106(PLDto PV106) {
        this.PV106 = PV106;
    }

    /**
     * @return the PV107
     */
    public List<XCNDto> getPV107() {
        return PV107;
    }

    /**
     * @param PV107 the PV107 to set
     */
    public void setPV107(List<XCNDto> PV107) {
        this.PV107 = PV107;
    }

    /**
     * @return the PV108
     */
    public List<XCNDto> getPV108() {
        return PV108;
    }

    /**
     * @param PV108 the PV108 to set
     */
    public void setPV108(List<XCNDto> PV108) {
        this.PV108 = PV108;
    }

    /**
     * @return the PV109
     */
    public List<XCNDto> getPV109() {
        return PV109;
    }

    /**
     * @param PV109 the PV109 to set
     */
    public void setPV109(List<XCNDto> PV109) {
        this.PV109 = PV109;
    }

    /**
     * @return the PV110
     */
    public String getPV110() {
        return PV110;
    }

    /**
     * @param PV110 the PV110 to set
     */
    public void setPV110(String PV110) {
        this.PV110 = PV110;
    }

    /**
     * @return the PV111
     */
    public PLDto getPV111() {
        return PV111;
    }

    /**
     * @param PV111 the PV111 to set
     */
    public void setPV111(PLDto PV111) {
        this.PV111 = PV111;
    }

    /**
     * @return the PV112
     */
    public String getPV112() {
        return PV112;
    }

    /**
     * @param PV112 the PV112 to set
     */
    public void setPV112(String PV112) {
        this.PV112 = PV112;
    }

    /**
     * @return the PV113
     */
    public String getPV113() {
        return PV113;
    }

    /**
     * @param PV113 the PV113 to set
     */
    public void setPV113(String PV113) {
        this.PV113 = PV113;
    }

    /**
     * @return the PV114
     */
    public String getPV114() {
        return PV114;
    }

    /**
     * @param PV114 the PV114 to set
     */
    public void setPV114(String PV114) {
        this.PV114 = PV114;
    }

    /**
     * @return the PV115
     */
    public String getPV115() {
        return PV115;
    }

    /**
     * @param PV115 the PV115 to set
     */
    public void setPV115(String PV115) {
        this.PV115 = PV115;
    }

    /**
     * @return the PV116
     */
    public String getPV116() {
        return PV116;
    }

    /**
     * @param PV116 the PV116 to set
     */
    public void setPV116(String PV116) {
        this.PV116 = PV116;
    }

    /**
     * @return the PV117
     */
    public List<XCNDto> getPV117() {
        return PV117;
    }

    /**
     * @param PV117 the PV117 to set
     */
    public void setPV117(List<XCNDto> PV117) {
        this.PV117 = PV117;
    }

    /**
     * @return the PV118
     */
    public String getPV118() {
        return PV118;
    }

    /**
     * @param PV118 the PV118 to set
     */
    public void setPV118(String PV118) {
        this.PV118 = PV118;
    }

    /**
     * @return the PV119
     */
    public String getPV119() {
        return PV119;
    }

    /**
     * @param PV119 the PV119 to set
     */
    public void setPV119(String PV119) {
        this.PV119 = PV119;
    }

    /**
     * @return the PV120
     */
    public String getPV120() {
        return PV120;
    }

    /**
     * @param PV120 the PV120 to set
     */
    public void setPV120(String PV120) {
        this.PV120 = PV120;
    }

    /**
     * @return the PV121
     */
    public String getPV121() {
        return PV121;
    }

    /**
     * @param PV121 the PV121 to set
     */
    public void setPV121(String PV121) {
        this.PV121 = PV121;
    }

    /**
     * @return the PV122
     */
    public String getPV122() {
        return PV122;
    }

    /**
     * @param PV122 the PV122 to set
     */
    public void setPV122(String PV122) {
        this.PV122 = PV122;
    }

    /**
     * @return the PV123
     */
    public String getPV123() {
        return PV123;
    }

    /**
     * @param PV123 the PV123 to set
     */
    public void setPV123(String PV123) {
        this.PV123 = PV123;
    }

    /**
     * @return the PV124
     */
    public String getPV124() {
        return PV124;
    }

    /**
     * @param PV124 the PV124 to set
     */
    public void setPV124(String PV124) {
        this.PV124 = PV124;
    }

    /**
     * @return the PV125
     */
    public String getPV125() {
        return PV125;
    }

    /**
     * @param PV125 the PV125 to set
     */
    public void setPV125(String PV125) {
        this.PV125 = PV125;
    }

    /**
     * @return the PV126
     */
    public String getPV126() {
        return PV126;
    }

    /**
     * @param PV126 the PV126 to set
     */
    public void setPV126(String PV126) {
        this.PV126 = PV126;
    }

    /**
     * @return the PV127
     */
    public String getPV127() {
        return PV127;
    }

    /**
     * @param PV127 the PV127 to set
     */
    public void setPV127(String PV127) {
        this.PV127 = PV127;
    }

    /**
     * @return the PV128
     */
    public String getPV128() {
        return PV128;
    }

    /**
     * @param PV128 the PV128 to set
     */
    public void setPV128(String PV128) {
        this.PV128 = PV128;
    }

    /**
     * @return the PV129
     */
    public String getPV129() {
        return PV129;
    }

    /**
     * @param PV129 the PV129 to set
     */
    public void setPV129(String PV129) {
        this.PV129 = PV129;
    }

    /**
     * @return the PV130
     */
    public String getPV130() {
        return PV130;
    }

    /**
     * @param PV130 the PV130 to set
     */
    public void setPV130(String PV130) {
        this.PV130 = PV130;
    }

    /**
     * @return the PV131
     */
    public String getPV131() {
        return PV131;
    }

    /**
     * @param PV131 the PV131 to set
     */
    public void setPV131(String PV131) {
        this.PV131 = PV131;
    }

    /**
     * @return the PV132
     */
    public String getPV132() {
        return PV132;
    }

    /**
     * @param PV132 the PV132 to set
     */
    public void setPV132(String PV132) {
        this.PV132 = PV132;
    }

    /**
     * @return the PV133
     */
    public String getPV133() {
        return PV133;
    }

    /**
     * @param PV133 the PV133 to set
     */
    public void setPV133(String PV133) {
        this.PV133 = PV133;
    }

    /**
     * @return the PV134
     */
    public String getPV134() {
        return PV134;
    }

    /**
     * @param PV134 the PV134 to set
     */
    public void setPV134(String PV134) {
        this.PV134 = PV134;
    }

    /**
     * @return the PV135
     */
    public String getPV135() {
        return PV135;
    }

    /**
     * @param PV135 the PV135 to set
     */
    public void setPV135(String PV135) {
        this.PV135 = PV135;
    }

    /**
     * @return the PV136
     */
    public String getPV136() {
        return PV136;
    }

    /**
     * @param PV136 the PV136 to set
     */
    public void setPV136(String PV136) {
        this.PV136 = PV136;
    }

    /**
     * @return the PV137
     */
    public String getPV137() {
        return PV137;
    }

    /**
     * @param PV137 the PV137 to set
     */
    public void setPV137(String PV137) {
        this.PV137 = PV137;
    }

    /**
     * @return the PV138
     */
    public String getPV138() {
        return PV138;
    }

    /**
     * @param PV138 the PV138 to set
     */
    public void setPV138(String PV138) {
        this.PV138 = PV138;
    }

    /**
     * @return the PV139
     */
    public String getPV139() {
        return PV139;
    }

    /**
     * @param PV139 the PV139 to set
     */
    public void setPV139(String PV139) {
        this.PV139 = PV139;
    }

    /**
     * @return the PV140
     */
    public String getPV140() {
        return PV140;
    }

    /**
     * @param PV140 the PV140 to set
     */
    public void setPV140(String PV140) {
        this.PV140 = PV140;
    }

    /**
     * @return the PV141
     */
    public String getPV141() {
        return PV141;
    }

    /**
     * @param PV141 the PV141 to set
     */
    public void setPV141(String PV141) {
        this.PV141 = PV141;
    }

    /**
     * @return the PV142
     */
    public PLDto getPV142() {
        return PV142;
    }

    /**
     * @param PV142 the PV142 to set
     */
    public void setPV142(PLDto PV142) {
        this.PV142 = PV142;
    }

    /**
     * @return the PV143
     */
    public PLDto getPV143() {
        return PV143;
    }

    /**
     * @param PV143 the PV143 to set
     */
    public void setPV143(PLDto PV143) {
        this.PV143 = PV143;
    }

    /**
     * @return the PV144
     */
    public String getPV144() {
        return PV144;
    }

    /**
     * @param PV144 the PL144 to set
     */
    public void setPV144(String PV144) {
        this.PV144 = PV144;
    }

    /**
     * @return the PV145
     */
    public String getPV145() {
        return PV145;
    }

    /**
     * @param PV145 the PV145 to set
     */
    public void setPV145(String PV145) {
        this.PV145 = PV145;
    }

    /**
     * @return the PV146
     */
    public String getPV146() {
        return PV146;
    }

    /**
     * @param PV146 the PV146 to set
     */
    public void setPV146(String PV146) {
        this.PV146 = PV146;
    }

    /**
     * @return the PV147
     */
    public String getPV147() {
        return PV147;
    }

    /**
     * @param PV147 the PV147 to set
     */
    public void setPV147(String PV147) {
        this.PV147 = PV147;
    }

    /**
     * @return the PV148
     */
    public String getPV148() {
        return PV148;
    }

    /**
     * @param PV148 the PV148 to set
     */
    public void setPV148(String PV148) {
        this.PV148 = PV148;
    }

    /**
     * @return the PV149
     */
    public String getPV149() {
        return PV149;
    }

    /**
     * @param PV149 the PV149 to set
     */
    public void setPV149(String PV149) {
        this.PV149 = PV149;
    }

    /**
     * @return the PV150
     */
    public String getPV150() {
        return PV150;
    }

    /**
     * @param PV150 the PV150 to set
     */
    public void setPV150(String PV150) {
        this.PV150 = PV150;
    }

    /**
     * @return the PV151
     */
    public String getPV151() {
        return PV151;
    }

    /**
     * @param PV151 the PV151 to set
     */
    public void setPV151(String PV151) {
        this.PV151 = PV151;
    }

    /**
     * @return the PV152
     */
    public List<XCNDto> getPV152() {
        return PV152;
    }

    /**
     * @param PV152 the PV152 to set
     */
    public void setPV152(List<XCNDto> PV152) {
        this.PV152 = PV152;
    }
    
    /**
     * PV1のセット
     */
    public void setPV1(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "PV1" + st_num;

        switch(target){
            case "PV100":
                setPV100(value);
                break;
            case "PV101":
                setPV101(value);
                break;
            case "PV102":
                setPV102(value);
                break;
            case "PV103":
                PLDto pv103 = setPL(value);
                setPV103(pv103);
                break;
            case "PV104":
                setPV104(value);
                break;
            case "PV105":
                setPV105(value);
                break;
            case "PV106":
                PLDto pv106 = setPL(value);
                setPV106(pv106);
                break;
            case "PV107":
                List<XCNDto> pv107List = setXCN(value);
                setPV107(pv107List);
                break;
            case "PV108":
                List<XCNDto> pv108List = setXCN(value);
                setPV108(pv108List);
                break;
            case "PV109":
                List<XCNDto> pv109List = setXCN(value);
                setPV109(pv109List);
                break;
            case "PV110":
                setPV110(value);
                break;
            case "PV111":
                PLDto pv111 = setPL(value);
                setPV111(pv111);
                break;
            case "PV112":
                setPV112(value);
                break;
            case "PV113":
                setPV113(value);
                break;
            case "PV114":
                setPV114(value);
                break;
            case "PV115":
                setPV115(value);
                break;
            case "PV116":
                setPV116(value);
                break;
            case "PV117":
                List<XCNDto> pv117List = setXCN(value);
                setPV117(pv117List);
                break;
            case "PV118":
                setPV118(value);
                break;
            case "PV119":
                setPV119(value);
                break;
            case "PV120":
                setPV120(value);
                break;
            case "PV121":
                setPV121(value);
                break;
            case "PV122":
                setPV122(value);
                break;
            case "PV123":
                setPV123(value);
                break;
            case "PV124":
                setPV124(value);
                break;
            case "PV125":
                setPV125(value);
                break;
            case "PV126":
                setPV126(value);
                break;
            case "PV127":
                setPV127(value);
                break;
            case "PV128":
                setPV128(value);
                break;
            case "PV129":
                setPV129(value);
                break;
            case "PV130":
                setPV130(value);
                break;
            case "PV131":
                setPV131(value);
                break;
            case "PV132":
                setPV132(value);
                break;
            case "PV133":
                setPV133(value);
                break;
            case "PV134":
                setPV134(value);
                break;
            case "PV135":
                setPV135(value);
                break;
            case "PV136":
                setPV136(value);
                break;
            case "PV137":
                setPV137(value);
                break;
            case "PV138":
                setPV138(value);
                break;
            case "PV139":
                setPV139(value);
                break;
            case "PV140":
                setPV140(value);
                break;
            case "PV141":
                setPV141(value);
                break;
            case "PV142":
                PLDto pv142 = setPL(value);
                setPV142(pv142);
                break;
            case "PV143":
                PLDto pv143 = setPL(value);
                setPV143(pv143);
                break;
            case "PV144":
                setPV144(value);
                break;
            case "PV145":
                setPV145(value);
                break;
            case "PV146":
                setPV146(value);
                break;
            case "PV147":
                setPV147(value);
                break;
            case "PV148":
                setPV148(value);
                break;
            case "PV149":
                setPV149(value);
                break;
            case "PV150":
                setPV150(value);
                break;
            case "PV151":
                setPV151(value);
                break;
            case "PV152":
                List<XCNDto>pv152 = setXCN(value);
                setPV152(pv152);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
        
    }
    
    /**
     *  PV1のゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getPV1(Integer f_num , Integer e_num , Integer r_num){
        String result = null;
        
        switch(f_num){
            case 0:
                result = getPV100();
                break;
            case 1:
                result = getPV101();
                break;
            case 2:
                result = getPV102();
                break;
            case 3:
                result = getPL(getPV103() , e_num );
                break;
            case 4:
                result = getPV104();
                break;
            case 5:
                result = getPV105();
                break;
            case 6:
                result = getPL(getPV106() , e_num);
                break;
            case 7:
                result = getXCN(getPV107() , e_num , r_num);
                break;
            case 8:
                result = getXCN(getPV108() , e_num , r_num);
                break;
            case 9:
                result = getXCN(getPV109() , e_num , r_num);
                break;
            case 10:
                result = getPV110();
                break;
            case 11:
                result = getPL(getPV111() , e_num);
                break;
            case 12:
                result = getPV112();
                break;
            case 13:
                result = getPV113();
                break;
            case 14:
                result = getPV114();
                break;
            case 15:
                result = getPV115();
                break;
            case 16:
                result = getPV116();
                break;
            case 17:
                result = getXCN(getPV117() , e_num , r_num);
                break;
            case 18:
                result = getPV118();
                break;
            case 19:
                result = getPV119();
                break;
            case 20:
                result = getPV120();
                break;
            case 21:
                result = getPV121();
                break;
            case 22:
                result = getPV122();
                break;
            case 23:
                result = getPV123();
                break;
            case 24:
                result = getPV124();
                break;
            case 25:
                result = getPV125();
                break;
            case 26:
                result = getPV126();
                break;
            case 27:
                result = getPV127();
                break;
            case 28:
                result = getPV128();
                break;
            case 29:
                result = getPV129();
                break;
            case 30:
                result = getPV130();
                break;
            case 31:
                result = getPV131();
                break;
            case 32:
                result = getPV132();
                break;
            case 33:
                result = getPV133();
                break;
            case 34:
                result = getPV134();
                break;
            case 35:
                result = getPV135();
                break;
            case 36:
                result = getPV136();
                break;
            case 37:
                result = getPV137();
                break;
            case 38:
                result = getPV138();
                break;
            case 39:
                result = getPV139();
                break;
            case 40:
                result = getPV140();
                break;
            case 41:
                result = getPV141();
                break;
            case 42:
                result = getPL(getPV142() , e_num);
                break;
            case 43:
                result = getPL(getPV143() ,e_num);
                break;
            case 44:
                result = getPV144();
                break;
            case 45:
                result = getPV145();
                break;
            case 46:
                result = getPV146();
                break;
            case 47:
                result = getPV147();
                break;
            case 48:
                result = getPV148();
                break;
            case 49:
                result = getPV149();
                break;
            case 50:
                result = getPV150();
                break;
            case 51:
                result = getPV151();
                break;
            case 52:
                result = getXCN(getPV152() , e_num , r_num);
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        
        return result;
    }    
    
    /**
     *  PV1のゲット
     * 
     * @param f_num フィールドNo　必須
     */
    public Object getPV1Ob(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getPV100();
                break;
            case 1:
                result = getPV101();
                break;
            case 2:
                result = getPV102();
                break;
            case 3:
                result = getPV103();
                break;
            case 4:
                result = getPV104();
                break;
            case 5:
                result = getPV105();
                break;
            case 6:
                result = getPV106();
                break;
            case 7:
                result = getPV107();
                break;
            case 8:
                result = getPV108();
                break;
            case 9:
                result = getPV109();
                break;
            case 10:
                result = getPV110();
                break;
            case 11:
                result = getPV111();
                break;
            case 12:
                result = getPV112();
                break;
            case 13:
                result = getPV113();
                break;
            case 14:
                result = getPV114();
                break;
            case 15:
                result = getPV115();
                break;
            case 16:
                result = getPV116();
                break;
            case 17:
                result = getPV117();
                break;
            case 18:
                result = getPV118();
                break;
            case 19:
                result = getPV119();
                break;
            case 20:
                result = getPV120();
                break;
            case 21:
                result = getPV121();
                break;
            case 22:
                result = getPV122();
                break;
            case 23:
                result = getPV123();
                break;
            case 24:
                result = getPV124();
                break;
            case 25:
                result = getPV125();
                break;
            case 26:
                result = getPV126();
                break;
            case 27:
                result = getPV127();
                break;
            case 28:
                result = getPV128();
                break;
            case 29:
                result = getPV129();
                break;
            case 30:
                result = getPV130();
                break;
            case 31:
                result = getPV131();
                break;
            case 32:
                result = getPV132();
                break;
            case 33:
                result = getPV133();
                break;
            case 34:
                result = getPV134();
                break;
            case 35:
                result = getPV135();
                break;
            case 36:
                result = getPV136();
                break;
            case 37:
                result = getPV137();
                break;
            case 38:
                result = getPV138();
                break;
            case 39:
                result = getPV139();
                break;
            case 40:
                result = getPV140();
                break;
            case 41:
                result = getPV141();
                break;
            case 42:
                result = getPV142();
                break;
            case 43:
                result = getPV143();
                break;
            case 44:
                result = getPV144();
                break;
            case 45:
                result = getPV145();
                break;
            case 46:
                result = getPV146();
                break;
            case 47:
                result = getPV147();
                break;
            case 48:
                result = getPV148();
                break;
            case 49:
                result = getPV149();
                break;
            case 50:
                result = getPV150();
                break;
            case 51:
                result = getPV151();
                break;
            case 52:
                result = getPV152();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        
        return result;
    } 
}
