/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;
import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XADDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XPNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XTNDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class PIDDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(PIDDto.class);
    
    /**
     * セグメントID
     */
    private String PID00;
    
    /**
     * セットID－PID
     */
    private String PID01;
    
    /**
     * 患者ID（旧Versionとの互換性のためにある）
     */
    private String PID02;
    
    /**
     * 患者IDリスト
     */
    private String PID03;
    
    /*
    * 代替患者ID
    */
    private String PID04;
    
    /**
     * 患者氏名(漢字,カナ）
     */
    private List<XPNDto> PID05;
 
    /*
    * 母親の旧姓（漢字、カナ）
    */
    private List<XPNDto> PID06;
    
    /**
     * 生年月日
     */
    private String PID07;
    
    /**
     * 性別
     */
    private String PID08;
    
    /**
     * 患者別名
     */
    private List<XPNDto> PID09;
    
    /**
     * 人種
     */
    private CWEDto PID10;
    
    /**
     * 患者住所
     */
   private XADDto PID11;
   
   /**
    * 群コード
    */
   private String PID12;
   
   /**
    * 電話番号ー自宅
    */
   private XTNDto PID13;
   
   /**
    * 電話番号ー勤務先
    */
   private XTNDto PID14;
   
   /**
    * 使用言語
    */
   private CWEDto PID15;
   
   /**
    * 婚姻状況
    */
   private CWEDto PID16;
   
   /**
    * 宗教
    */
   private CWEDto PID17;
   
   /**
    * 患者会計番号
    */
   private String PID18;
   
   /**
    * 社会保険番号
    */
    private String PID19;
    
    /**
     * 運転免許番号
     */
    private String PID20;
    
    /**
     * 母親の識別情報
     */
    private String PID21;
    
    /**
     * 民族
     */
    private CWEDto PID22;
    
    /**
     * 出生地
     */
    private String PID23;
    
    /**
     * 多胎児識別子
     */
    private String PID24;
    
    /**
     * 申請順序
     */
    private String PID25;
    
    /**
     * 市民権
     */
    private CWEDto PID26;
    
    /**
     * 退役軍人状況
     */
    private CWEDto PID27;
    
    /**
     * 国籍
     */
    private CWEDto PID28;
    
    /**
     * 患者死亡日時
     */
    private String PID29;
    
    /**
     * 患者死亡識別情報
     */
    private String PID30;
    
    /**
     * 身元不明識別
     */
    private String PID31;
    
    /**
     * 識別情報の信頼性
     */
    private String PID32;
    
    /**
     * 最終更新日時
     */
    private String PID33;
    
    //PIDセグメント39まであるが使用しないのでDtoは用意しない

    /**
     * @return the PID00
     */
    public String getPID00() {
        return PID00;
    }

    /**
     * @param PID00 the PID00 to set
     */
    public void setPID00(String PID00) {
        this.PID00 = PID00;
    }

    /**
     * @return the PID01
     */
    public String getPID01() {
        return PID01;
    }

    /**
     * @param PID01 the PID01 to set
     */
    public void setPID01(String PID01) {
        this.PID01 = PID01;
    }

    /**
     * @return the PID02
     */
    public String getPID02() {
        return PID02;
    }

    /**
     * @param PID02 the PID02 to set
     */
    public void setPID02(String PID02) {
        this.PID02 = PID02;
    }

    /**
     * @return the PID03
     */
    public String getPID03() {
        return PID03;
    }

    /**
     * @param PID03 the PID03 to set
     */
    public void setPID03(String PID03) {
        this.PID03 = PID03;
    }

    /**
     * @return the PID04
     */
    public String getPID04() {
        return PID04;
    }

    /**
     * @param PID04 the PID04 to set
     */
    public void setPID04(String PID04) {
        this.PID04 = PID04;
    }

    /**
     * @return the PID05
     */
    public List<XPNDto> getPID05() {
        return PID05;
    }

    /**
     * @param PID05 the PID05 to set
     */
    public void setPID05(List<XPNDto> PID05) {
        this.PID05 = PID05;
    }

    /**
     * @return the PID06
     */
    public List<XPNDto> getPID06() {
        return PID06;
    }

    /**
     * @param PID06 the PID06 to set
     */
    public void setPID06(List<XPNDto> PID06) {
        this.PID06 = PID06;
    }

    /**
     * @return the PID07
     */
    public String getPID07() {
        return PID07;
    }

    /**
     * @param PID07 the PID07 to set
     */
    public void setPID07(String PID07) {
        this.PID07 = PID07;
    }

    /**
     * @return the PID08
     */
    public String getPID08() {
        return PID08;
    }

    /**
     * @param PID08 the PID08 to set
     */
    public void setPID08(String PID08) {
        this.PID08 = PID08;
    }

    /**
     * @return the PID09
     */
    public List<XPNDto> getPID09() {
        return PID09;
    }

    /**
     * @param PID09 the PID09 to set
     */
    public void setPID09(List<XPNDto> PID09) {
        this.PID09 = PID09;
    }

    /**
     * @return the PID10
     */
    public CWEDto getPID10() {
        return PID10;
    }

    /**
     * @param PID10 the PID10 to set
     */
    public void setPID10(CWEDto PID10) {
        this.PID10 = PID10;
    }

    /**
     * @return the PID11
     */
    public XADDto getPID11() {
        return PID11;
    }

    /**
     * @param PID11 the PID11 to set
     */
    public void setPID11(XADDto PID11) {
        this.PID11 = PID11;
    }

    /**
     * @return the PID12
     */
    public String getPID12() {
        return PID12;
    }

    /**
     * @param PID12 the PID12 to set
     */
    public void setPID12(String PID12) {
        this.PID12 = PID12;
    }

    /**
     * @return the PID13
     */
    public XTNDto getPID13() {
        return PID13;
    }

    /**
     * @param PID13 the PID13 to set
     */
    public void setPID13(XTNDto PID13) {
        this.PID13 = PID13;
    }

    /**
     * @return the PID14
     */
    public XTNDto getPID14() {
        return PID14;
    }

    /**
     * @param PID14 the PID14 to set
     */
    public void setPID14(XTNDto PID14) {
        this.PID14 = PID14;
    }

    /**
     * @return the PID15
     */
    public CWEDto getPID15() {
        return PID15;
    }

    /**
     * @param PID15 the PID15 to set
     */
    public void setPID15(CWEDto PID15) {
        this.PID15 = PID15;
    }

    /**
     * @return the PID16
     */
    public CWEDto getPID16() {
        return PID16;
    }

    /**
     * @param PID16 the PID16 to set
     */
    public void setPID16(CWEDto PID16) {
        this.PID16 = PID16;
    }

    /**
     * @return the PID17
     */
    public CWEDto getPID17() {
        return PID17;
    }

    /**
     * @param PID17 the PID17 to set
     */
    public void setPID17(CWEDto PID17) {
        this.PID17 = PID17;
    }

    /**
     * @return the PID18
     */
    public String getPID18() {
        return PID18;
    }

    /**
     * @param PID18 the PID18 to set
     */
    public void setPID18(String PID18) {
        this.PID18 = PID18;
    }

    /**
     * @return the PID19
     */
    public String getPID19() {
        return PID19;
    }

    /**
     * @param PID19 the PID19 to set
     */
    public void setPID19(String PID19) {
        this.PID19 = PID19;
    }

    /**
     * @return the PID20
     */
    public String getPID20() {
        return PID20;
    }

    /**
     * @param PID20 the PID20 to set
     */
    public void setPID20(String PID20) {
        this.PID20 = PID20;
    }

    /**
     * @return the PID21
     */
    public String getPID21() {
        return PID21;
    }

    /**
     * @param PID21 the PID21 to set
     */
    public void setPID21(String PID21) {
        this.PID21 = PID21;
    }

    /**
     * @return the PID22
     */
    public CWEDto getPID22() {
        return PID22;
    }

    /**
     * @param PID22 the PID22 to set
     */
    public void setPID22(CWEDto PID22) {
        this.PID22 = PID22;
    }

    /**
     * @return the PID23
     */
    public String getPID23() {
        return PID23;
    }

    /**
     * @param PID23 the PID23 to set
     */
    public void setPID23(String PID23) {
        this.PID23 = PID23;
    }

    /**
     * @return the PID24
     */
    public String getPID24() {
        return PID24;
    }

    /**
     * @param PID24 the PID24 to set
     */
    public void setPID24(String PID24) {
        this.PID24 = PID24;
    }

    /**
     * @return the PID25
     */
    public String getPID25() {
        return PID25;
    }

    /**
     * @param PID25 the PID25 to set
     */
    public void setPID25(String PID25) {
        this.PID25 = PID25;
    }

    /**
     * @return the PID26
     */
    public CWEDto getPID26() {
        return PID26;
    }

    /**
     * @param PID26 the PID26 to set
     */
    public void setPID26(CWEDto PID26) {
        this.PID26 = PID26;
    }

    /**
     * @return the PID27
     */
    public CWEDto getPID27() {
        return PID27;
    }

    /**
     * @param PID27 the PID27 to set
     */
    public void setPID27(CWEDto PID27) {
        this.PID27 = PID27;
    }

    /**
     * @return the PUD28
     */
    public CWEDto getPID28() {
        return PID28;
    }

    /**
     * @param PID28 the PUD28 to set
     */
    public void setPID28(CWEDto PID28) {
        this.PID28 = PID28;
    }

    /**
     * @return the PID29
     */
    public String getPID29() {
        return PID29;
    }

    /**
     * @param PID29 the PID29 to set
     */
    public void setPID29(String PID29) {
        this.PID29 = PID29;
    }

    /**
     * @return the PID30
     */
    public String getPID30() {
        return PID30;
    }

    /**
     * @param PID30 the PID30 to set
     */
    public void setPID30(String PID30) {
        this.PID30 = PID30;
    }

    /**
     * @return the PID31
     */
    public String getPID31() {
        return PID31;
    }

    /**
     * @param PID31 the PID31 to set
     */
    public void setPID31(String PID31) {
        this.PID31 = PID31;
    }

    /**
     * @return the PID32
     */
    public String getPID32() {
        return PID32;
    }

    /**
     * @param PID32 the PID32 to set
     */
    public void setPID32(String PID32) {
        this.PID32 = PID32;
    }

    /**
     * @return the PID33
     */
    public String getPID33() {
        return PID33;
    }

    /**
     * @param PID33 the PID33 to set
     */
    public void setPID33(String PID33) {
        this.PID33 = PID33;
    }
    
    /**
     * PIDのセット
     * ファイルから取得してきたことを想定する
     * 
     */
    public void setPID(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "PID" + st_num;
        
        switch(target){
            case "PID00" :
                setPID00(value);
                break;
            case "PID01" :
                setPID01(value);
                break;
            case "PID02" :
                setPID02(value);
                break;
            case "PID03" :
                setPID03(value);
                break;
            case "PID04" :
                setPID04(value);
                break;
            case "PID05" :
                List<XPNDto> pid05List = setXPN(value);
                setPID05(pid05List);
                break;
            case "PID06" :
                List<XPNDto> pid06List = setXPN(value);
                setPID06(pid06List);
                break;
            case "PID07" :
                setPID07(value);
                break;
            case "PID08" :
                setPID08(value);
                break;
            case "PID09" :
                List<XPNDto> pid09List = setXPN(value);
                setPID09(pid09List);
                break;
            case "PID10" :
                CWEDto pid10_cwe = setCWE(value);
                setPID10(pid10_cwe);
                break;
            case "PID11" :
                XADDto pid11_xad = setXAD(value);
                setPID11(pid11_xad);
                break;
            case "PID12" :
                setPID12(value);
                break;
            case "PID13" :
                XTNDto pid13_xtn = setXTN(value);
                setPID13(pid13_xtn);
                break;
            case "PID14" :
                XTNDto pid14_xtn = setXTN(value);
                setPID14(pid14_xtn);
                break;
            case "PID15" :
                CWEDto pid15_cwe = setCWE(value);
                setPID15(pid15_cwe);
                break;
            case "PID16" :
                CWEDto pid16_cwe = setCWE(value);
                setPID16(pid16_cwe);
                break;
            case "PID17" :
                CWEDto pid17_cwe = setCWE(value);
                setPID17(pid17_cwe);
                break;
            case "PID18" :
                setPID18(value);
                break;
            case "PID19" :
                setPID19(value);
                break;
            case "PID20" :
                setPID20(value);
                break;
            case "PID21" :
                setPID21(value);
                break;
            case "PID22" :
                CWEDto pid22_cwe = setCWE(value);
                setPID22(pid22_cwe);
                break;
            case "PID23" :
                setPID23(value);
                break;
            case "PID24" :
                setPID24(value);
                break;
            case "PID25" :
                setPID25(value);
                break;
            case "PID26" :
                CWEDto pid26_cwe = setCWE(value);
                setPID26(pid26_cwe);
                break;
            case "PID27" :
                CWEDto pid27_cwe = setCWE(value);
                setPID27(pid27_cwe);
                break;
            case "PID28" :
                CWEDto pid28_cwe = setCWE(value);
                setPID28(pid28_cwe);
                break;
            case "PID29" :
                setPID29(value);
                break;
            case "PID30" :
                setPID30(value);
                break;
            case "PID31" :
                setPID31(value);
                break;
            case "PID32" :
                setPID32(value);
                break;
            case "PID33" :
                setPID33(value);
                break;
        }
    }
    
    /**
     *  PIDのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getPID(Integer f_num , Integer e_num , Integer r_num){
        String result = null;
        
        switch(f_num){
            case 0:
                result = getPID00();
                break;
            case 1:
                result = getPID01();
                break;
            case 2:
                result = getPID02();
                break;
            case 3:
                result = getPID03();
                break;
            case 4:
                result = getPID04();
                break;
            case 5:
                result = getXPN(getPID05() , e_num , r_num);
                break;
            case 6:
                result = getXPN(getPID06() , e_num , r_num);
                break;
            case 7:
                result = getPID07();
                break;
            case 8:
                result = getPID08();
                break;
            case 9:
                result = getXPN(getPID09() , e_num , r_num);
                break;
            case 10:
                result = getCWE(getPID10() , e_num);
                break;
            case 11:
                result = getXAD(getPID11() , e_num);
                break;
            case 12:
                result = getPID12();
                break;
            case 13:
                result = getXTN(getPID13() , e_num);
                break;
            case 14:
                result = getXTN(getPID14() , e_num);
                break;
            case 15:
                result = getCWE(getPID15() , e_num);
                break;
            case 16:
                result = getCWE(getPID16() , e_num);
                break;
            case 17:
                result = getCWE(getPID17() , e_num);
                break;
            case 18:
                result = getPID18();
                break;
            case 19:
                result = getPID19();
                break;
            case 20:
                result = getPID20();
                break;
            case 21:
                result = getPID21();
                break;
            case 22:
                result = getCWE(getPID22() , e_num);
                break;
            case 23:
                result = getPID23();
                break;
            case 24:
                result = getPID24();
                break;
            case 25:
                result = getPID25();
                break;
            case 26:
                result = getCWE(getPID26() , e_num);
                break;
            case 27:
                result = getCWE(getPID27() , e_num);
                break;
            case 28:
                result = getCWE(getPID28() , e_num);
                break;
            case 29:
                result = getPID29();
                break;
            case 30:
                result = getPID30();
                break;
            case 31:
                result = getPID31();
                break;
            case 32:
                result = getPID32();
                break;
            case 33:
                result = getPID33();
                break;
        }
        
        return result;
    }

    public Object getPIDOb(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getPID00();
                break;
            case 1:
                result = getPID01();
                break;
            case 2:
                result = getPID02();
                break;
            case 3:
                result = getPID03();
                break;
            case 4:
                result = getPID04();
                break;
            case 5:
                result = getPID05();
                break;
            case 6:
                result =getPID06();
                break;
            case 7:
                result = getPID07();
                break;
            case 8:
                result = getPID08();
                break;
            case 9:
                result = getPID09();
                break;
            case 10:
                result = getPID10();
                break;
            case 11:
                result = getPID11();
                break;
            case 12:
                result = getPID12();
                break;
            case 13:
                result = getPID13();
                break;
            case 14:
                result = getPID14();
                break;
            case 15:
                result = getPID15();
                break;
            case 16:
                result = getPID16();
                break;
            case 17:
                result = getPID17();
                break;
            case 18:
                result = getPID18();
                break;
            case 19:
                result = getPID19();
                break;
            case 20:
                result = getPID20();
                break;
            case 21:
                result = getPID21();
                break;
            case 22:
                result = getPID22();
                break;
            case 23:
                result = getPID23();
                break;
            case 24:
                result = getPID24();
                break;
            case 25:
                result = getPID25();
                break;
            case 26:
                result = getPID26();
                break;
            case 27:
                result = getPID27();
                break;
            case 28:
                result = getPID28();
                break;
            case 29:
                result = getPID29();
                break;
            case 30:
                result = getPID30();
                break;
            case 31:
                result = getPID31();
                break;
            case 32:
                result = getPID32();
                break;
            case 33:
                result = getPID33();
                break;
        }
        
        return result;
    }    
}
