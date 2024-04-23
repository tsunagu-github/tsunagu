/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;
import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XTNDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class OBRDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(OBRDto.class);
 
    /**
     * セグメントID
     */
    private String OBR00;
    
    /**
     * セットID－OBR
     */
    private String OBR01;
    
    /**
     * 依頼者オーダー番号
     */
    private String OBR02;
    
    /**
     * 実施者オーダ番号
     */
    private String OBR03;
    
    /**
     * 検査項目ID
     */
    private CWEDto OBR04;
 
    /**
     * 優先度
     */
    private String OBR05;
    
    /**
     * 要求日時
     */
    private String OBR06;
    
    /**
     * 検査/採取日時
     */
    private String OBR07;
    
    /**
     * 検査採取終了日時
     */
    private String OBR08;
    
    /**
     * 採取量
     */
    private String OBR09;
    
    /**
     * 採取者識別子
     */
    private List<XCNDto> OBR10;
    
    /**
     * 検体処理コード
     */
    private String OBR11;
    
    /**
     * 危険（検体）コード
     */
    private CWEDto OBR12;
    
    /**
     * 関連臨床情報
     */
    private String OBR13;
    
    /**
     * 検体受理日時
     */
    private String OBR14;
    
    /**
     * 検体材料/採取部位
     */
    private String OBR15;
    
    /**
     * 依頼者
     */
    private List<XCNDto> OBR16;
    
    /**
     * オーダコールバック用電話番号
     */
    private XTNDto OBR17;
    
    /**
     * 依頼者項目１
     */
    private String OBR18;
    
    /**
     * 依頼者項目２
     */
    private String OBR19;
    
    /**
     * 実施者項目１
     */
    private String OBR20;
    
    /**
     * 実施者項目２
     */
    private String OBR21;
    
    /**
     * 結果報告/上体変更ー日時
     */
    private String OBR22;
    
    /**
     * 課金
     */
    private String OBR23;
    
    /**
     * 診療部門
     */
    private String OBR24;
    
    /**
     * 結果状態
     */
    private String OBR25;
    
    /**
     * 親結果
     */
    private String OBR26;
    
    /**
     * 数量/タイミング
     */
    private String OBR27;
    
    /**
     * 結果配布先
     */
    private List<XCNDto> OBR28;
    
    /**
     * 親番号
     */
    private String OBR29;
    
    /**
     * 患者搬送モード
     */
    private String OBR30;
    
    /**
     * 検査理由
     */
    private CWEDto OBR31;
    
    /**
     * 結果判定責任者
     */
    private String OBR32;
    
    /**
     * 結果判定アシスタント
     */
    private String OBR33;

    /**
     * 医療技術者
     */
    private String OBR34;
    
    /**
     * 口述記録者
     */
    private String OBR35;
    
    /**
     * 予定日時
     */
    private String OBR36;
    
    /**
     * 検体容器数
     */
    private String OBR37;
    
    /**
     * 搾取検体搬送
     */
    private CWEDto OBR38;
    
    /**
     * 採取者コメント
     */
    private CWEDto OBR39;
    
    /**
     * 搬送調整者
     */
    private CWEDto OBR40;
    
    /**
     * 搬送調整結果
     */
     private String OBR41;

     /**
      * 随行者要否
      */
     private String OBR42;
     
     /**
      * 患者搬送コメント
      */
     private CWEDto OBR43;
     
     /**
      * 処理コード
      */
     private CWEDto OBR44;
     
     /**
      * 処理コード週力氏
      */
     private CWEDto OBR45;
     
     /**
      * 依頼者発行の追加サービス情報
      */
     private CWEDto OBR46;
     
     /**
      * 実施者発行の追加サービス情報
      */
     private CWEDto OBR47;
     
     /**
      * 医学的に必要な重複処理の理由
      */
     private CWEDto OBR48;
     
     /**
      * 結果の取り扱い
      */
     private String OBR49;

    /**
     * @return the OBR00
     */
    public String getOBR00() {
        return OBR00;
    }

    /**
     * @param OBR00 the OBR00 to set
     */
    public void setOBR00(String OBR00) {
        this.OBR00 = OBR00;
    }

    /**
     * @return the OBR01
     */
    public String getOBR01() {
        return OBR01;
    }

    /**
     * @param OBR01 the OBR01 to set
     */
    public void setOBR01(String OBR01) {
        this.OBR01 = OBR01;
    }

    /**
     * @return the OBR02
     */
    public String getOBR02() {
        return OBR02;
    }

    /**
     * @param OBR02 the OBR02 to set
     */
    public void setOBR02(String OBR02) {
        this.OBR02 = OBR02;
    }

    /**
     * @return the OBR03
     */
    public String getOBR03() {
        return OBR03;
    }

    /**
     * @param OBR03 the OBR03 to set
     */
    public void setOBR03(String OBR03) {
        this.OBR03 = OBR03;
    }

    /**
     * @return the OBR04
     */
    public CWEDto getOBR04() {
        return OBR04;
    }

    /**
     * @param OBR04 the OBR04 to set
     */
    public void setOBR04(CWEDto OBR04) {
        this.OBR04 = OBR04;
    }

    /**
     * @return the OBR05
     */
    public String getOBR05() {
        return OBR05;
    }

    /**
     * @param OBR05 the OBR05 to set
     */
    public void setOBR05(String OBR05) {
        this.OBR05 = OBR05;
    }

    /**
     * @return the OBR06
     */
    public String getOBR06() {
        return OBR06;
    }

    /**
     * @param OBR06 the OBR06 to set
     */
    public void setOBR06(String OBR06) {
        this.OBR06 = OBR06;
    }

    /**
     * @return the OBR07
     */
    public String getOBR07() {
        return OBR07;
    }

    /**
     * @param OBR07 the OBR07 to set
     */
    public void setOBR07(String OBR07) {
        this.OBR07 = OBR07;
    }

    /**
     * @return the OBR08
     */
    public String getOBR08() {
        return OBR08;
    }

    /**
     * @param OBR08 the OBR08 to set
     */
    public void setOBR08(String OBR08) {
        this.OBR08 = OBR08;
    }

    /**
     * @return the OBR09
     */
    public String getOBR09() {
        return OBR09;
    }

    /**
     * @param OBR09 the OBR09 to set
     */
    public void setOBR09(String OBR09) {
        this.OBR09 = OBR09;
    }

    /**
     * @return the OBR10
     */
    public List<XCNDto> getOBR10() {
        return OBR10;
    }

    /**
     * @param OBR10 the OBR10 to set
     */
    public void setOBR10(List<XCNDto> OBR10) {
        this.OBR10 = OBR10;
    }

    /**
     * @return the OBR11
     */
    public String getOBR11() {
        return OBR11;
    }

    /**
     * @param OBR11 the OBR11 to set
     */
    public void setOBR11(String OBR11) {
        this.OBR11 = OBR11;
    }

    /**
     * @return the OBR12
     */
    public CWEDto getOBR12() {
        return OBR12;
    }

    /**
     * @param OBR12 the OBR12 to set
     */
    public void setOBR12(CWEDto OBR12) {
        this.OBR12 = OBR12;
    }

    /**
     * @return the OBR13
     */
    public String getOBR13() {
        return OBR13;
    }

    /**
     * @param OBR13 the OBR13 to set
     */
    public void setOBR13(String OBR13) {
        this.OBR13 = OBR13;
    }

    /**
     * @return the OBR14
     */
    public String getOBR14() {
        return OBR14;
    }

    /**
     * @param OBR14 the OBR14 to set
     */
    public void setOBR14(String OBR14) {
        this.OBR14 = OBR14;
    }

    /**
     * @return the OBR15
     */
    public String getOBR15() {
        return OBR15;
    }

    /**
     * @param OBR15 the OBR15 to set
     */
    public void setOBR15(String OBR15) {
        this.OBR15 = OBR15;
    }

    /**
     * @return the OBR16
     */
    public List<XCNDto> getOBR16() {
        return OBR16;
    }

    /**
     * @param OBR16 the OBR16 to set
     */
    public void setOBR16(List<XCNDto> OBR16) {
        this.OBR16 = OBR16;
    }

    /**
     * @return the OBR17
     */
    public XTNDto getOBR17() {
        return OBR17;
    }

    /**
     * @param OBR17 the OBR17 to set
     */
    public void setOBR17(XTNDto OBR17) {
        this.OBR17 = OBR17;
    }

    /**
     * @return the OBR18
     */
    public String getOBR18() {
        return OBR18;
    }

    /**
     * @param OBR18 the OBR18 to set
     */
    public void setOBR18(String OBR18) {
        this.OBR18 = OBR18;
    }

    /**
     * @return the OBR19
     */
    public String getOBR19() {
        return OBR19;
    }

    /**
     * @param OBR19 the OBR19 to set
     */
    public void setOBR19(String OBR19) {
        this.OBR19 = OBR19;
    }

    /**
     * @return the OBR20
     */
    public String getOBR20() {
        return OBR20;
    }

    /**
     * @param OBR20 the OBR20 to set
     */
    public void setOBR20(String OBR20) {
        this.OBR20 = OBR20;
    }

    /**
     * @return the OBR21
     */
    public String getOBR21() {
        return OBR21;
    }

    /**
     * @param OBR21 the OBR21 to set
     */
    public void setOBR21(String OBR21) {
        this.OBR21 = OBR21;
    }

    /**
     * @return the OBR22
     */
    public String getOBR22() {
        return OBR22;
    }

    /**
     * @param OBR22 the OBR22 to set
     */
    public void setOBR22(String OBR22) {
        this.OBR22 = OBR22;
    }

    /**
     * @return the OBR23
     */
    public String getOBR23() {
        return OBR23;
    }

    /**
     * @param OBR23 the OBR23 to set
     */
    public void setOBR23(String OBR23) {
        this.OBR23 = OBR23;
    }

    /**
     * @return the OBR24
     */
    public String getOBR24() {
        return OBR24;
    }

    /**
     * @param OBR24 the OBR24 to set
     */
    public void setOBR24(String OBR24) {
        this.OBR24 = OBR24;
    }

    /**
     * @return the OBR25
     */
    public String getOBR25() {
        return OBR25;
    }

    /**
     * @param OBR25 the OBR25 to set
     */
    public void setOBR25(String OBR25) {
        this.OBR25 = OBR25;
    }

    /**
     * @return the OBR26
     */
    public String getOBR26() {
        return OBR26;
    }

    /**
     * @param OBR26 the OBR26 to set
     */
    public void setOBR26(String OBR26) {
        this.OBR26 = OBR26;
    }

    /**
     * @return the OBR27
     */
    public String getOBR27() {
        return OBR27;
    }

    /**
     * @param OBR27 the OBR27 to set
     */
    public void setOBR27(String OBR27) {
        this.OBR27 = OBR27;
    }

    /**
     * @return the OBR28
     */
    public List<XCNDto> getOBR28() {
        return OBR28;
    }

    /**
     * @param OBR28 the OBR28 to set
     */
    public void setOBR28(List<XCNDto> OBR28) {
        this.OBR28 = OBR28;
    }

    /**
     * @return the OBR29
     */
    public String getOBR29() {
        return OBR29;
    }

    /**
     * @param OBR29 the OBR29 to set
     */
    public void setOBR29(String OBR29) {
        this.OBR29 = OBR29;
    }

    /**
     * @return the OBR30
     */
    public String getOBR30() {
        return OBR30;
    }

    /**
     * @param OBR30 the OBR30 to set
     */
    public void setOBR30(String OBR30) {
        this.OBR30 = OBR30;
    }

    /**
     * @return the OBR31
     */
    public CWEDto getOBR31() {
        return OBR31;
    }

    /**
     * @param OBR31 the OBR31 to set
     */
    public void setOBR31(CWEDto OBR31) {
        this.OBR31 = OBR31;
    }

    /**
     * @return the OBR32
     */
    public String getOBR32() {
        return OBR32;
    }

    /**
     * @param OBR32 the OBR32 to set
     */
    public void setOBR32(String OBR32) {
        this.OBR32 = OBR32;
    }

    /**
     * @return the OBR33
     */
    public String getOBR33() {
        return OBR33;
    }

    /**
     * @param OBR33 the OBR33 to set
     */
    public void setOBR33(String OBR33) {
        this.OBR33 = OBR33;
    }

    /**
     * @return the OBR34
     */
    public String getOBR34() {
        return OBR34;
    }

    /**
     * @param OBR34 the OBR34 to set
     */
    public void setOBR34(String OBR34) {
        this.OBR34 = OBR34;
    }

    /**
     * @return the OBR35
     */
    public String getOBR35() {
        return OBR35;
    }

    /**
     * @param OBR35 the OBR35 to set
     */
    public void setOBR35(String OBR35) {
        this.OBR35 = OBR35;
    }

    /**
     * @return the OBR36
     */
    public String getOBR36() {
        return OBR36;
    }

    /**
     * @param OBR36 the OBR36 to set
     */
    public void setOBR36(String OBR36) {
        this.OBR36 = OBR36;
    }

    /**
     * @return the OBR37
     */
    public String getOBR37() {
        return OBR37;
    }

    /**
     * @param OBR37 the OBR37 to set
     */
    public void setOBR37(String OBR37) {
        this.OBR37 = OBR37;
    }

    /**
     * @return the OBR38
     */
    public CWEDto getOBR38() {
        return OBR38;
    }

    /**
     * @param OBR38 the OBR38 to set
     */
    public void setOBR38(CWEDto OBR38) {
        this.OBR38 = OBR38;
    }

    /**
     * @return the OBR39
     */
    public CWEDto getOBR39() {
        return OBR39;
    }

    /**
     * @param OBR39 the OBR39 to set
     */
    public void setOBR39(CWEDto OBR39) {
        this.OBR39 = OBR39;
    }

    /**
     * @return the OBR40
     */
    public CWEDto getOBR40() {
        return OBR40;
    }

    /**
     * @param OBR40 the OBR40 to set
     */
    public void setOBR40(CWEDto OBR40) {
        this.OBR40 = OBR40;
    }

    /**
     * @return the OBR41
     */
    public String getOBR41() {
        return OBR41;
    }

    /**
     * @param OBR41 the OBR41 to set
     */
    public void setOBR41(String OBR41) {
        this.OBR41 = OBR41;
    }

    /**
     * @return the OBR42
     */
    public String getOBR42() {
        return OBR42;
    }

    /**
     * @param OBR42 the OBR42 to set
     */
    public void setOBR42(String OBR42) {
        this.OBR42 = OBR42;
    }

    /**
     * @return the OBR43
     */
    public CWEDto getOBR43() {
        return OBR43;
    }

    /**
     * @param OBR43 the OBR43 to set
     */
    public void setOBR43(CWEDto OBR43) {
        this.OBR43 = OBR43;
    }

    /**
     * @return the OBR44
     */
    public CWEDto getOBR44() {
        return OBR44;
    }

    /**
     * @param OBR44 the OBR44 to set
     */
    public void setOBR44(CWEDto OBR44) {
        this.OBR44 = OBR44;
    }

    /**
     * @return the OBR45
     */
    public CWEDto getOBR45() {
        return OBR45;
    }

    /**
     * @param OBR45 the OBR45 to set
     */
    public void setOBR45(CWEDto OBR45) {
        this.OBR45 = OBR45;
    }

    /**
     * @return the OBR46
     */
    public CWEDto getOBR46() {
        return OBR46;
    }

    /**
     * @param OBR46 the OBR46 to set
     */
    public void setOBR46(CWEDto OBR46) {
        this.OBR46 = OBR46;
    }

    /**
     * @return the OBR47
     */
    public CWEDto getOBR47() {
        return OBR47;
    }

    /**
     * @param OBR47 the OBR47 to set
     */
    public void setOBR47(CWEDto OBR47) {
        this.OBR47 = OBR47;
    }

    /**
     * @return the OBR48
     */
    public CWEDto getOBR48() {
        return OBR48;
    }

    /**
     * @param OBR48 the OBR48 to set
     */
    public void setOBR48(CWEDto OBR48) {
        this.OBR48 = OBR48;
    }

    /**
     * @return the OBR49
     */
    public String getOBR49() {
        return OBR49;
    }

    /**
     * @param OBR49 the OBR49 to set
     */
    public void setOBR49(String OBR49) {
        this.OBR49 = OBR49;
    }
     

    /**
     * OBRのセット
     */
    public void setOBR(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "OBR" + st_num;

       switch(target){
            case "OBR00":
                setOBR00(value);
                break;
            case "OBR01":
                setOBR01(value);
                break;
            case "OBR02":
                setOBR02(value);
                break;
            case "OBR03":
                setOBR03(value);
                break;
            case "OBR04":
                CWEDto obr04_cwe = setCWE(value);
                setOBR04(obr04_cwe);
                break;
            case "OBR05":
                setOBR05(value);
                break;
            case "OBR06":
                setOBR06(value);
                break;
            case "OBR07":
                setOBR07(value);
                break;
            case "OBR08":
                setOBR08(value);
                break;
            case "OBR09":
                setOBR09(value);
                break;
            case "OBR10":
                List<XCNDto> obr10_xcn = setXCN(value);
                setOBR10(obr10_xcn);
                break;
            case "OBR11":
                setOBR11(value);
                break;
            case "OBR12":
                CWEDto obr12_cwe = setCWE(value);
                setOBR12(obr12_cwe);
                break;
            case "OBR13":
                setOBR13(value);
                break;
            case "OBR14":
                setOBR14(value);
                break;
            case "OBR15":
                setOBR15(value);
                break;
            case "OBR16":
                List<XCNDto>obr16_xcn = setXCN(value);
                setOBR16(obr16_xcn);
                break;
            case "OBR17":
                XTNDto obr17_xtn = setXTN(value);
                setOBR17(obr17_xtn);
                break;
            case "OBR18":
                setOBR18(value);
                break;
            case "OBR19":
                setOBR19(value);
                break;
            case "OBR20":
                setOBR20(value);
                break;
            case "OBR21":
                setOBR21(value);
                break;
            case "OBR22":
                setOBR22(value);
                break;
            case "OBR23":
                setOBR23(value);
                break;
            case "OBR24":
                setOBR24(value);
                break;
            case "OBR25":
                setOBR25(value);
                break;
            case "OBR26":
                setOBR26(value);
                break;
            case "OBR27":
                setOBR27(value);
                break;
            case "OBR28":
                 List<XCNDto> obr28_xcn = setXCN(value);
                setOBR28(obr28_xcn);
                break;
            case "OBR29":
                setOBR29(value);
                break;
            case "OBR30":
                setOBR30(value);
                break;
            case "OBR31":
                CWEDto obr31_cwe = setCWE(value);
                setOBR31(obr31_cwe);
                break;
            case "OBR32":
                setOBR32(value);
                break;
            case "OBR33":
                setOBR33(value);
                break;
            case "OBR34":
                setOBR34(value);
                break;
            case "OBR35":
                setOBR35(value);
                break;
            case "OBR36":
                setOBR36(value);
                break;
            case "OBR37":
                setOBR37(value);
                break;
            case "OBR38":
                CWEDto obr38_cwe = setCWE(value);
                setOBR38(obr38_cwe);
                break;
            case "OBR39":
                CWEDto obr39_cwe = setCWE(value);
                setOBR39(obr39_cwe);
                break;
            case "OBR40":
                CWEDto obr40_cwe = setCWE(value);
                setOBR40(obr40_cwe);
                break;
            case "OBR41":
                setOBR41(value);
                break;
            case "OBR42":
                setOBR42(value);
                break;
            case "OBR43":
                CWEDto obr43_cwe = setCWE(value);
                setOBR43(obr43_cwe);
                break;
            case "OBR44":
                CWEDto obr44_cwe = setCWE(value);
                setOBR44(obr44_cwe);
                break;
            case "OBR45":
                CWEDto obr45_cwe = setCWE(value);
                setOBR45(obr45_cwe);
                break;
            case "OBR46":
                CWEDto obr46_cwe = setCWE(value);
                setOBR46(obr46_cwe);
                break;
            case "OBR47":
                CWEDto obr47_cwe = setCWE(value);
                setOBR47(obr47_cwe);
                break;
            case "OBR48":
                CWEDto obr48_cwe = setCWE(value);
                setOBR48(obr48_cwe);
                break;
            case "OBR49":
                setOBR49(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
       }
   }
    
    /**
     *  OBRのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getOBR(Integer f_num , Integer e_num , Integer r_num){
        String result = null;
        
        switch(f_num){
            case 0:
                result = getOBR00();
                break;
            case 1:
                result = getOBR01();
                break;
            case 2:
                result = getOBR02();
                break;
            case 3:
                result = getOBR03();
                break;
            case 4:
                result = getCWE(getOBR04() , e_num );
                break;
            case 5:
                result = getOBR05();
                break;
            case 6:
                result = getOBR06();
                break;
            case 7:
                result = getOBR07();
                break;
            case 8:
                result = getOBR08();
                break;
            case 9:
                result = getOBR09();
                break;
            case 10:
                result = getXCN(getOBR10() , e_num ,r_num);
                break;
            case 11:
                result = getOBR11();
                break;
            case 12:
                result = getCWE(getOBR12() , e_num );
                break;
            case 13:
                result = getOBR13();
                break;
            case 14:
                result = getOBR14();
                break;
            case 15:
                result = getOBR15();
                break;
            case 16:
                result = getXCN(getOBR16() , e_num ,r_num);
                break;
            case 17:
                result = getXTN(getOBR17() , e_num);
                break;
            case 18:
                result = getOBR18();
                break;
            case 19:
                result = getOBR19();
                break;
            case 20:
                result = getOBR20();
                break;
            case 21:
                result = getOBR21();
                break;
            case 22:
                result = getOBR22();
                break;
            case 23:
                result = getOBR23();
                break;
            case 24:
                result = getOBR24();
                break;
            case 25:
                result = getOBR25();
                break;
            case 26:
                result = getOBR26();
                break;
            case 27:
                result = getOBR27();
                break;
            case 28:
                result = getXCN(getOBR28() , e_num ,r_num);
                break;
            case 29:
                result = getOBR29();
                break;
            case 30:
                result = getOBR30();
                break;
            case 31:
                result = getCWE(getOBR31() , e_num);
                break;
            case 32:
                result = getOBR32();
                break;
            case 33:
                result = getOBR33();
                break;
            case 34:
                result = getOBR34();
                break;
            case 35:
                result = getOBR35();
                break;
            case 36:
                result = getOBR36();
                break;
            case 37:
                result = getOBR37();
                break;
            case 38:
                result = getCWE(getOBR38() , e_num );
                break;
            case 39:
                result = getCWE(getOBR39() , e_num );
                break;
            case 40:
                result = getCWE(getOBR40() , e_num );
                break;
            case 41:
                result = getOBR41();
                break;
            case 42:
                result = getOBR42();
                break;
            case 43:
                result = getCWE(getOBR43() , e_num);
                break;
            case 44:
                result = getCWE(getOBR44() , e_num);
                break;
            case 45:
                result = getCWE(getOBR45() , e_num);
                break;
            case 46:
                result = getCWE(getOBR46() , e_num);
                break;
            case 47:
                result = getCWE(getOBR47() , e_num);
                break;
            case 48:
                result = getCWE(getOBR48() , e_num);
                break;
            case 49:
                result = getOBR49();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
     
    /**
     *  OBRのゲット
     * 
     * @param f_num フィールドNo　必須
     */
    public Object getOBROb(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getOBR00();
                break;
            case 1:
                result = getOBR01();
                break;
            case 2:
                result = getOBR02();
                break;
            case 3:
                result = getOBR03();
                break;
            case 4:
                result = getOBR04();
                break;
            case 5:
                result = getOBR05();
                break;
            case 6:
                result = getOBR06();
                break;
            case 7:
                result = getOBR07();
                break;
            case 8:
                result = getOBR08();
                break;
            case 9:
                result = getOBR09();
                break;
            case 10:
                result = getOBR10();
                break;
            case 11:
                result = getOBR11();
                break;
            case 12:
                result = getOBR12();
                break;
            case 13:
                result = getOBR13();
                break;
            case 14:
                result = getOBR14();
                break;
            case 15:
                result = getOBR15();
                break;
            case 16:
                result = getOBR16();
                break;
            case 17:
                result = getOBR17();
                break;
            case 18:
                result = getOBR18();
                break;
            case 19:
                result = getOBR19();
                break;
            case 20:
                result = getOBR20();
                break;
            case 21:
                result = getOBR21();
                break;
            case 22:
                result = getOBR22();
                break;
            case 23:
                result = getOBR23();
                break;
            case 24:
                result = getOBR24();
                break;
            case 25:
                result = getOBR25();
                break;
            case 26:
                result = getOBR26();
                break;
            case 27:
                result = getOBR27();
                break;
            case 28:
                result = getOBR28();
                break;
            case 29:
                result = getOBR29();
                break;
            case 30:
                result = getOBR30();
                break;
            case 31:
                result = getOBR31();
                break;
            case 32:
                result = getOBR32();
                break;
            case 33:
                result = getOBR33();
                break;
            case 34:
                result = getOBR34();
                break;
            case 35:
                result = getOBR35();
                break;
            case 36:
                result = getOBR36();
                break;
            case 37:
                result = getOBR37();
                break;
            case 38:
                result = getOBR38();
                break;
            case 39:
                result = getOBR39();
                break;
            case 40:
                result = getOBR40();
                break;
            case 41:
                result = getOBR41();
                break;
            case 42:
                result = getOBR42();
                break;
            case 43:
                result = getOBR43();
                break;
            case 44:
                result = getOBR44();
                break;
            case 45:
                result = getOBR45();
                break;
            case 46:
                result = getOBR46();
                break;
            case 47:
                result = getOBR47();
                break;
            case 48:
                result = getOBR48();
                break;
            case 49:
                result = getOBR49();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }     
}
