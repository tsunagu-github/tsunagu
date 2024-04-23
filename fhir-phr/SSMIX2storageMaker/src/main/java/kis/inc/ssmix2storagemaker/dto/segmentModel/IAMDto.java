/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CNEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XPNDto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class IAMDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(IAMDto.class);

    /**
     * セグメントID
     */
    private String IAM00;
    /**
     * セットID-IAM
     */
    private String IAM01;
    /**
     * アレルゲン分類
     */
    private CWEDto IAM02;
    /**
     * アレルゲン情報
     */
    private CWEDto IAM03;
    /**
     * アレルギー重症度
     */
    private CWEDto IAM04;
    /**
     * アレルギー反応情報
     */
    private String IAM05;
    /**
     * アレルギーアクションコード
     */
    private CNEDto IAM06;
    /**
     * アレルギー識別情報
     */
    private String IAM07;
    /**
     * アレルギー情報追加・変更理由
     */
    private String IAM08;
    /**
     * アレルギー物質に対する感受性
     */
    private CWEDto IAM09;
    /**
     * アレルゲン群情報
     */
    private CWEDto IAM10;
    /**
     * アレルギー発症日
     */
    private String IAM11;
    /**
     * アレルギー発症時期
     */
    private String IAM12;
    /**
     * 情報提供日時
     */
    private String IAM13;
    /**
     * 情報提供者
     */
    private List<XPNDto> IAM14;
    /**
     * 情報提供者と患者の続柄
     */
    private CWEDto IAM15;
    /**
     * 要注意物質コード
     */
    private CWEDto IAM16;
    /**
     * アレルギー臨床確認状況
     */
    private CWEDto IAM17;
    /**
     * 確認者
     */
    private List<XCNDto> IAM18;
    /**
     * 確認機関
     */
    private String IAM19;
    /**
     * 確認日時
     */
    private String IAM20;

    /**
     * @return the IAM00
     */
    public String getIAM00() {
        return IAM00;
    }

    /**
     * @param IAM00 the IAM00 to set
     */
    public void setIAM00(String IAM00) {
        this.IAM00 = IAM00;
    }

    /**
     * @return the IAM01
     */
    public String getIAM01() {
        return IAM01;
    }

    /**
     * @param IAM01 the IAM01 to set
     */
    public void setIAM01(String IAM01) {
        this.IAM01 = IAM01;
    }

    /**
     * @return the IAM02
     */
    public CWEDto getIAM02() {
        return IAM02;
    }

    /**
     * @param IAM02 the IAM02 to set
     */
    public void setIAM02(CWEDto IAM02) {
        this.IAM02 = IAM02;
    }

    /**
     * @return the IAM03
     */
    public CWEDto getIAM03() {
        return IAM03;
    }

    /**
     * @param IAM03 the IAM03 to set
     */
    public void setIAM03(CWEDto IAM03) {
        this.IAM03 = IAM03;
    }

    /**
     * @return the IAM04
     */
    public CWEDto getIAM04() {
        return IAM04;
    }

    /**
     * @param IAM04 the IAM04 to set
     */
    public void setIAM04(CWEDto IAM04) {
        this.IAM04 = IAM04;
    }

    /**
     * @return the IAM05
     */
    public String getIAM05() {
        return IAM05;
    }

    /**
     * @param IAM05 the IAM05 to set
     */
    public void setIAM05(String IAM05) {
        this.IAM05 = IAM05;
    }

    /**
     * @return the IAM06
     */
    public CNEDto getIAM06() {
        return IAM06;
    }

    /**
     * @param IAM06 the IAM06 to set
     */
    public void setIAM06(CNEDto IAM06) {
        this.IAM06 = IAM06;
    }

    /**
     * @return the IAM07
     */
    public String getIAM07() {
        return IAM07;
    }

    /**
     * @param IAM07 the IAM07 to set
     */
    public void setIAM07(String IAM07) {
        this.IAM07 = IAM07;
    }

    /**
     * @return the IAM08
     */
    public String getIAM08() {
        return IAM08;
    }

    /**
     * @param IAM08 the IAM08 to set
     */
    public void setIAM08(String IAM08) {
        this.IAM08 = IAM08;
    }

    /**
     * @return the IAM09
     */
    public CWEDto getIAM09() {
        return IAM09;
    }

    /**
     * @param IAM09 the IAM09 to set
     */
    public void setIAM09(CWEDto IAM09) {
        this.IAM09 = IAM09;
    }

    /**
     * @return the IAM10
     */
    public CWEDto getIAM10() {
        return IAM10;
    }

    /**
     * @param IAM10 the IAM10 to set
     */
    public void setIAM10(CWEDto IAM10) {
        this.IAM10 = IAM10;
    }

    /**
     * @return the IAM11
     */
    public String getIAM11() {
        return IAM11;
    }

    /**
     * @param IAM11 the IAM11 to set
     */
    public void setIAM11(String IAM11) {
        this.IAM11 = IAM11;
    }

    /**
     * @return the IAM12
     */
    public String getIAM12() {
        return IAM12;
    }

    /**
     * @param IAM12 the IAM12 to set
     */
    public void setIAM12(String IAM12) {
        this.IAM12 = IAM12;
    }

    /**
     * @return the IAM13
     */
    public String getIAM13() {
        return IAM13;
    }

    /**
     * @param IAM13 the IAM13 to set
     */
    public void setIAM13(String IAM13) {
        this.IAM13 = IAM13;
    }

    /**
     * @return the IAM14
     */
    public List<XPNDto> getIAM14() {
        return IAM14;
    }

    /**
     * @param IAM14 the IAM14 to set
     */
    public void setIAM14(List<XPNDto> IAM14) {
        this.IAM14 = IAM14;
    }

    /**
     * @return the IAM15
     */
    public CWEDto getIAM15() {
        return IAM15;
    }

    /**
     * @param IAM15 the IAM15 to set
     */
    public void setIAM15(CWEDto IAM15) {
        this.IAM15 = IAM15;
    }

    /**
     * @return the IAM16
     */
    public CWEDto getIAM16() {
        return IAM16;
    }

    /**
     * @param IAM16 the IAM16 to set
     */
    public void setIAM16(CWEDto IAM16) {
        this.IAM16 = IAM16;
    }

    /**
     * @return the IAM17
     */
    public CWEDto getIAM17() {
        return IAM17;
    }

    /**
     * @param IAM17 the IAM17 to set
     */
    public void setIAM17(CWEDto IAM17) {
        this.IAM17 = IAM17;
    }

    /**
     * @return the IAM18
     */
    public List<XCNDto> getIAM18() {
        return IAM18;
    }

    /**
     * @param IAM18 the IAM18 to set
     */
    public void setIAM18(List<XCNDto> IAM18) {
        this.IAM18 = IAM18;
    }

    /**
     * @return the IAM19
     */
    public String getIAM19() {
        return IAM19;
    }

    /**
     * @param IAM19 the IAM19 to set
     */
    public void setIAM19(String IAM19) {
        this.IAM19 = IAM19;
    }

    /**
     * @return the IAM20
     */
    public String getIAM20() {
        return IAM20;
    }

    /**
     * @param IAM20 the IAM20 to set
     */
    public void setIAM20(String IAM20) {
        this.IAM20 = IAM20;
    }
    
    /**
     * IAMのセット
     */
    public void setIAM(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "IAM" + st_num;

       switch(target){
            case "IAM00":
                setIAM00(value);
                break;
            case "IAM01":
                setIAM01(value);
                break;
            case "IAM02":
                CWEDto iam02_cwe = setCWE(value);
                setIAM02(iam02_cwe);
                break;
            case "IAM03":
                CWEDto iam03_cwe = setCWE(value);
                setIAM03(iam03_cwe);
                break;
            case "IAM04":
                CWEDto iam04_cwe = setCWE(value);
                setIAM04(iam04_cwe);
                break;
            case "IAM05":
                setIAM05(value);
                break;
            case "IAM06":
                CNEDto iam06_cne = setCNE(value);
                setIAM06(iam06_cne);
                break;
            case "IAM07":
                setIAM07(value);
                break;
            case "IAM08":
                setIAM08(value);
                break;
            case "IAM09":
                CWEDto iam09_cwe = setCWE(value);
                setIAM09(iam09_cwe);
                break;
            case "IAM10":
                CWEDto iam10_cwe = setCWE(value);
                setIAM10(iam10_cwe);
                break;
            case "IAM11":
                setIAM11(value);
                break;
            case "IAM12":
                setIAM12(value);
                break;
            case "IAM13":
                setIAM13(value);
                break;
            case "IAM14":
                List<XPNDto> iam14List = setXPN(value);
                setIAM14(iam14List);
                break;
            case "IAM15":
                CWEDto iam15_cwe = setCWE(value);
                setIAM15(iam15_cwe);
                break;
            case "IAM16":
                CWEDto iam16_cwe = setCWE(value);
                setIAM16(iam16_cwe);
                break;
            case "IAM17":
                CWEDto iam17_cwe = setCWE(value);
                setIAM17(iam17_cwe);
                break;
            case "IAM18":
                List<XCNDto> iam18List = setXCN(value);
                setIAM18(iam18List);
                break;
            case "IAM19":
                setIAM19(value);
                break;
            case "IAM20":
                setIAM20(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
       }
   }
    
    /**
     *  IAMのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getIAM(Integer f_num , Integer e_num , Integer r_num){
        String result = null;
        
        switch(f_num){
            case 0:
                result = getIAM00();
                break;
            case 1:
                result = getIAM01();
                break;
            case 2:
                result = getCWE(getIAM02() , e_num );
                break;
            case 3:
                result = getCWE(getIAM03() , e_num );
                break;
            case 4:
                result = getCWE(getIAM04() , e_num );
                break;
            case 5:
                result = getIAM05();
                break;
            case 6:
                result = getCNE(getIAM06() , e_num );
                break;
            case 7:
                result = getIAM07();
                break;
            case 8:
                result = getIAM08();
                break;
            case 9:
                result = getCWE(getIAM09() , e_num );
                break;
            case 10:
                result = getCWE(getIAM10() , e_num );
                break;
            case 11:
                result = getIAM11();
                break;
            case 12:
                result = getIAM12();
                break;
            case 13:
                result = getIAM13();
                break;
            case 14:
                result = getXPN(getIAM14() , e_num , r_num);
                break;
            case 15:
                result = getCWE(getIAM15() , e_num );
                break;
            case 16:
                result = getCWE(getIAM16() , e_num );
                break;
            case 17:
                result = getCWE(getIAM17() , e_num );
                break;
            case 18:
                result = getXCN(getIAM18() , e_num , r_num);
                break;
            case 19:
                result = getIAM19();
                break;
            case 20:
                result = getIAM20();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }

    /**
     *  IAMのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public Object getIAMOb(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getIAM00();
                break;
            case 1:
                result = getIAM01();
                break;
            case 2:
                result = getIAM02();
                break;
            case 3:
                result = getIAM03();
                break;
            case 4:
                result = getIAM04();
                break;
            case 5:
                result = getIAM05();
                break;
            case 6:
                result = getIAM06();
                break;
            case 7:
                result = getIAM07();
                break;
            case 8:
                result = getIAM08();
                break;
            case 9:
                result = getIAM09();
                break;
            case 10:
                result = getIAM10();
                break;
            case 11:
                result = getIAM11();
                break;
            case 12:
                result = getIAM12();
                break;
            case 13:
                result = getIAM13();
                break;
            case 14:
                result = getIAM14();
                break;
            case 15:
                result = getIAM15();
                break;
            case 16:
                result = getIAM16();
                break;
            case 17:
                result = getIAM17();
                break;
            case 18:
                result = getIAM18();
                break;
            case 19:
                result = getIAM19();
                break;
            case 20:
                result = getIAM20();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
}
