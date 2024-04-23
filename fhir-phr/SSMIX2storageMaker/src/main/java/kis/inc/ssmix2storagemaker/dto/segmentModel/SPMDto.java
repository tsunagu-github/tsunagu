/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class SPMDto extends baseSegmentDto{
     /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(SPMDto.class);    
    
    /**
     * セグメントID
     */
    private String SPM00;
    /**
     * セットIDーSPM
     */
    private String SPM01;
    /**
     * 検体ID
     */
    private String SPM02;
    /**
     * 親検体ID
     */
    private String SPM03;
    /**
     * 検体タイプ
     */
    private CWEDto SPM04;
    /**
     * 検体タイプ修飾子
     */
    private CWEDto SPM05;
    /**
     * 検体添加物
     */
    private CWEDto SPM06;
    /**
     * 検体採取法
     */
    private CWEDto SPM07;
    /**
     * 検査材料-検査部位
     */
    private CWEDto SPM08;
    /**
     * 検査材料修飾子
     */
    private CWEDto SPM09;
    /**
     * 検体採取部位
     */
    private CWEDto SPM10;
    /**
     * 検体役割
     */
    private CWEDto SPM11;
    /**
     * 検体採取量
     */
    private String SPM12;
    /**
     * 検体総数
     */
    private String SPM13;
    /**
     * 検体記述
     */
    private String SPM14;
    /**
     * 検体取り扱いコード
     */
    private CWEDto SPM15;
    /**
     * 検体リスクコード
     */
    private CWEDto SPM16;
    /**
     * 検体採取日時
     */
    private String SPM17;
    /**
     * 検体受領日時
     */
    private String SPM18;
    /**
     * 検体有効日時
     */
    private String SPM19;
    /**
     * 検体有効性
     */
    private String SPM20;
    /**
     * 検体拒否理由
     */
    private CWEDto SPM21;
    /**
     * 検体品質
     */
    private CWEDto SPM22;
    /**
     * 検体適合性
     */
    private CWEDto SPM23;
    /**
     * 検体条件
     */
    private CWEDto SPM24;
    /**
     * 検体量
     */
    private String SPM25;
    /**
     * 検体容器数
     */
    private String SPM26;
    /**
     * 容器タイプ
     */
    private CWEDto SPM27;
    /**
     * 容器状態
     */
    private CWEDto SPM28;
    /**
     * 子検体役割
     */
    private CWEDto SPM29;

    /**
     * @return the SPM00
     */
    public String getSPM00() {
        return SPM00;
    }

    /**
     * @param SPM00 the SPM00 to set
     */
    public void setSPM00(String SPM00) {
        this.SPM00 = SPM00;
    }

    /**
     * @return the SPM01
     */
    public String getSPM01() {
        return SPM01;
    }

    /**
     * @param SPM01 the SPM01 to set
     */
    public void setSPM01(String SPM01) {
        this.SPM01 = SPM01;
    }

    /**
     * @return the SPM02
     */
    public String getSPM02() {
        return SPM02;
    }

    /**
     * @param SPM02 the SPM02 to set
     */
    public void setSPM02(String SPM02) {
        this.SPM02 = SPM02;
    }

    /**
     * @return the SPM03
     */
    public String getSPM03() {
        return SPM03;
    }

    /**
     * @param SPM03 the SPM03 to set
     */
    public void setSPM03(String SPM03) {
        this.SPM03 = SPM03;
    }

    /**
     * @return the SPM04
     */
    public CWEDto getSPM04() {
        return SPM04;
    }

    /**
     * @param SPM04 the SPM04 to set
     */
    public void setSPM04(CWEDto SPM04) {
        this.SPM04 = SPM04;
    }

    /**
     * @return the SPM05
     */
    public CWEDto getSPM05() {
        return SPM05;
    }

    /**
     * @param SPM05 the SPM05 to set
     */
    public void setSPM05(CWEDto SPM05) {
        this.SPM05 = SPM05;
    }

    /**
     * @return the SPM06
     */
    public CWEDto getSPM06() {
        return SPM06;
    }

    /**
     * @param SPM06 the SPM06 to set
     */
    public void setSPM06(CWEDto SPM06) {
        this.SPM06 = SPM06;
    }

    /**
     * @return the SPM07
     */
    public CWEDto getSPM07() {
        return SPM07;
    }

    /**
     * @param SPM07 the SPM07 to set
     */
    public void setSPM07(CWEDto SPM07) {
        this.SPM07 = SPM07;
    }

    /**
     * @return the SPM08
     */
    public CWEDto getSPM08() {
        return SPM08;
    }

    /**
     * @param SPM08 the SPM08 to set
     */
    public void setSPM08(CWEDto SPM08) {
        this.SPM08 = SPM08;
    }

    /**
     * @return the SPM09
     */
    public CWEDto getSPM09() {
        return SPM09;
    }

    /**
     * @param SPM09 the SPM09 to set
     */
    public void setSPM09(CWEDto SPM09) {
        this.SPM09 = SPM09;
    }

    /**
     * @return the SPM10
     */
    public CWEDto getSPM10() {
        return SPM10;
    }

    /**
     * @param SPM10 the SPM10 to set
     */
    public void setSPM10(CWEDto SPM10) {
        this.SPM10 = SPM10;
    }

    /**
     * @return the SPM11
     */
    public CWEDto getSPM11() {
        return SPM11;
    }

    /**
     * @param SPM11 the SPM11 to set
     */
    public void setSPM11(CWEDto SPM11) {
        this.SPM11 = SPM11;
    }

    /**
     * @return the SPM12
     */
    public String getSPM12() {
        return SPM12;
    }

    /**
     * @param SPM12 the SPM12 to set
     */
    public void setSPM12(String SPM12) {
        this.SPM12 = SPM12;
    }

    /**
     * @return the SPM13
     */
    public String getSPM13() {
        return SPM13;
    }

    /**
     * @param SPM13 the SPM13 to set
     */
    public void setSPM13(String SPM13) {
        this.SPM13 = SPM13;
    }

    /**
     * @return the SPM14
     */
    public String getSPM14() {
        return SPM14;
    }

    /**
     * @param SPM14 the SPM14 to set
     */
    public void setSPM14(String SPM14) {
        this.SPM14 = SPM14;
    }

    /**
     * @return the SPM15
     */
    public CWEDto getSPM15() {
        return SPM15;
    }

    /**
     * @param SPM15 the SPM15 to set
     */
    public void setSPM15(CWEDto SPM15) {
        this.SPM15 = SPM15;
    }

    /**
     * @return the SPM16
     */
    public CWEDto getSPM16() {
        return SPM16;
    }

    /**
     * @param SPM16 the SPM16 to set
     */
    public void setSPM16(CWEDto SPM16) {
        this.SPM16 = SPM16;
    }

    /**
     * @return the SPM17
     */
    public String getSPM17() {
        return SPM17;
    }

    /**
     * @param SPM17 the SPM17 to set
     */
    public void setSPM17(String SPM17) {
        this.SPM17 = SPM17;
    }

    /**
     * @return the SPM18
     */
    public String getSPM18() {
        return SPM18;
    }

    /**
     * @param SPM18 the SPM18 to set
     */
    public void setSPM18(String SPM18) {
        this.SPM18 = SPM18;
    }

    /**
     * @return the SPM19
     */
    public String getSPM19() {
        return SPM19;
    }

    /**
     * @param SPM19 the SPM19 to set
     */
    public void setSPM19(String SPM19) {
        this.SPM19 = SPM19;
    }

    /**
     * @return the SPM20
     */
    public String getSPM20() {
        return SPM20;
    }

    /**
     * @param SPM20 the SPM20 to set
     */
    public void setSPM20(String SPM20) {
        this.SPM20 = SPM20;
    }

    /**
     * @return the SPM21
     */
    public CWEDto getSPM21() {
        return SPM21;
    }

    /**
     * @param SPM21 the SPM21 to set
     */
    public void setSPM21(CWEDto SPM21) {
        this.SPM21 = SPM21;
    }

    /**
     * @return the SPM22
     */
    public CWEDto getSPM22() {
        return SPM22;
    }

    /**
     * @param SPM22 the SPM22 to set
     */
    public void setSPM22(CWEDto SPM22) {
        this.SPM22 = SPM22;
    }

    /**
     * @return the SPM23
     */
    public CWEDto getSPM23() {
        return SPM23;
    }

    /**
     * @param SPM23 the SPM23 to set
     */
    public void setSPM23(CWEDto SPM23) {
        this.SPM23 = SPM23;
    }

    /**
     * @return the SPM24
     */
    public CWEDto getSPM24() {
        return SPM24;
    }

    /**
     * @param SPM24 the SPM24 to set
     */
    public void setSPM24(CWEDto SPM24) {
        this.SPM24 = SPM24;
    }

    /**
     * @return the SPM25
     */
    public String getSPM25() {
        return SPM25;
    }

    /**
     * @param SPM25 the SPM25 to set
     */
    public void setSPM25(String SPM25) {
        this.SPM25 = SPM25;
    }

    /**
     * @return the SPM26
     */
    public String getSPM26() {
        return SPM26;
    }

    /**
     * @param SPM26 the SPM26 to set
     */
    public void setSPM26(String SPM26) {
        this.SPM26 = SPM26;
    }

    /**
     * @return the SPM27
     */
    public CWEDto getSPM27() {
        return SPM27;
    }

    /**
     * @param SPM27 the SPM27 to set
     */
    public void setSPM27(CWEDto SPM27) {
        this.SPM27 = SPM27;
    }

    /**
     * @return the SPM28
     */
    public CWEDto getSPM28() {
        return SPM28;
    }

    /**
     * @param SPM28 the SPM28 to set
     */
    public void setSPM28(CWEDto SPM28) {
        this.SPM28 = SPM28;
    }

    /**
     * @return the SPM29
     */
    public CWEDto getSPM29() {
        return SPM29;
    }

    /**
     * @param SPM29 the SPM29 to set
     */
    public void setSPM29(CWEDto SPM29) {
        this.SPM29 = SPM29;
    }

    /**
     * SPMのセット
     */
    public void setSPM(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "SPM" + st_num;

       switch(target){
            case "SPM00":
                setSPM00(value);
                break;
            case "SPM01":
                setSPM01(value);
                break;
            case "SPM02":
                setSPM02(value);
                break;
            case "SPM03":
                setSPM03(value);
                break;
            case "SPM04":
                CWEDto spm04_cwe = setCWE(value);
                setSPM04(spm04_cwe);
                break;
            case "SPM05":
                CWEDto spm05_cwe = setCWE(value);
                setSPM05(spm05_cwe);
                break;
            case "SPM06":
                CWEDto spm06_cwe = setCWE(value);
                setSPM06(spm06_cwe);
                break;
            case "SPM07":
                CWEDto spm07_cwe = setCWE(value);
                setSPM07(spm07_cwe);
                break;
            case "SPM08":
                CWEDto spm08_cwe = setCWE(value);
                setSPM08(spm08_cwe);
                break;
            case "SPM09":
                CWEDto spm09_cwe = setCWE(value);
                setSPM09(spm09_cwe);
                break;
            case "SPM10":
                CWEDto spm10_cwe = setCWE(value);
                setSPM10(spm10_cwe);
                break;
            case "SPM11":
                CWEDto spm11_cwe = setCWE(value);
                setSPM11(spm11_cwe);
                break;
            case "SPM12":
                setSPM12(value);
                break;
            case "SPM13":
                setSPM13(value);
                break;
            case "SPM14":
                setSPM14(value);
                break;
            case "SPM15":
                CWEDto spm15_cwe = setCWE(value);
                setSPM15(spm15_cwe);
                break;
            case "SPM16":
                CWEDto spm16_cwe = setCWE(value);
                setSPM16(spm16_cwe);
                break;
            case "SPM17":
                setSPM17(value);
                break;
            case "SPM18":
                setSPM18(value);
                break;
            case "SPM19":
                setSPM19(value);
                break;
            case "SPM20":
                setSPM20(value);
                break;
            case "SPM21":
                CWEDto spm21_cwe = setCWE(value);
                setSPM21(spm21_cwe);
                break;
            case "SPM22":
                CWEDto spm22_cwe = setCWE(value);
                setSPM22(spm22_cwe);
                break;
            case "SPM23":
                CWEDto spm23_cwe = setCWE(value);
                setSPM23(spm23_cwe);
                break;
            case "SPM24":
                CWEDto spm24_cwe = setCWE(value);
                setSPM24(spm24_cwe);
                break;
            case "SPM25":
                setSPM25(value);
                break;
            case "SPM26":
                setSPM26(value);
                break;
            case "SPM27":
                CWEDto spm27_cwe = setCWE(value);
                setSPM27(spm27_cwe);
                break;
            case "SPM28":
                 CWEDto spm28_cwe = setCWE(value);
                setSPM28(spm28_cwe);
                break;
            case "SPM29":
                 CWEDto spm29_cwe = setCWE(value);
                setSPM29(spm29_cwe);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
       }
   }

     /**
     *  SPMのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getSPM(Integer f_num , Integer e_num , Integer r_num){
        String result = null;
        
        switch(f_num){
            case 0:
                result = getSPM00();
                break;
            case 1:
                result = getSPM01();
                break;
            case 2:
                result = getSPM02();
                break;
            case 3:
                result = getSPM03();
                break;
            case 4:
                result = getCWE(getSPM04() , e_num );
                break;
            case 5:
                result = getCWE(getSPM05() , e_num );
                break;
            case 6:
                result = getCWE(getSPM06() , e_num );
                break;
            case 7:
                result = getCWE(getSPM07() , e_num );
                break;
            case 8:
                result = getCWE(getSPM08() , e_num );
                break;
            case 9:
                result = getCWE(getSPM09() , e_num );
                break;
            case 10:
                result = getCWE(getSPM10() , e_num );
                break;
            case 11:
                result = getCWE(getSPM11() , e_num );
                break;
            case 12:
                result = getSPM12();
                break;
            case 13:
                result = getSPM13();
                break;
            case 14:
                result = getSPM14();
                break;
            case 15:
                result = getCWE(getSPM15() , e_num );
                break;
            case 16:
                result = getCWE(getSPM16() , e_num );
                break;
            case 17:
                result = getSPM17();
                break;
            case 18:
                result = getSPM18();
                break;
            case 19:
                result = getSPM19();
                break;
            case 20:
                result = getSPM20();
                break;
            case 21:
                result = getCWE(getSPM21() , e_num );
                break;
            case 22:
                result = getCWE(getSPM22() , e_num );
                break;
            case 23:
                result = getCWE(getSPM23() , e_num );
                break;
            case 24:
                result = getCWE(getSPM24() , e_num );
                break;
            case 25:
                result = getSPM25();
                break;
            case 26:
                result = getSPM26();
                break;
            case 27:
                result = getCWE(getSPM27() , e_num );
                break;
            case 28:
                result = getCWE(getSPM28() , e_num );
                break;
            case 29:
                result = getCWE(getSPM29() , e_num );
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
    
     /**
     *  SPMのゲット
     * 
     * @param f_num フィールドNo　必須
     */
    public Object getSPMOb(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getSPM00();
                break;
            case 1:
                result = getSPM01();
                break;
            case 2:
                result = getSPM02();
                break;
            case 3:
                result = getSPM03();
                break;
            case 4:
                result = getSPM04();
                break;
            case 5:
                result = getSPM05();
                break;
            case 6:
                result = getSPM06();
                break;
            case 7:
                result = getSPM07();
                break;
            case 8:
                result = getSPM08();
                break;
            case 9:
                result = getSPM09();
                break;
            case 10:
                result = getSPM10();
                break;
            case 11:
                result = getSPM11();
                break;
            case 12:
                result = getSPM12();
                break;
            case 13:
                result = getSPM13();
                break;
            case 14:
                result = getSPM14();
                break;
            case 15:
                result = getSPM15();
                break;
            case 16:
                result = getSPM16();
                break;
            case 17:
                result = getSPM17();
                break;
            case 18:
                result = getSPM18();
                break;
            case 19:
                result = getSPM19();
                break;
            case 20:
                result = getSPM20();
                break;
            case 21:
                result = getSPM21();
                break;
            case 22:
                result = getSPM22();
                break;
            case 23:
                result = getSPM23();
                break;
            case 24:
                result = getSPM24();
                break;
            case 25:
                result = getSPM25();
                break;
            case 26:
                result = getSPM26();
                break;
            case 27:
                result = getSPM27();
                break;
            case 28:
                result = getSPM28();
                break;
            case 29:
                result = getSPM29();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
}
