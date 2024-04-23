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
public class PRBDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(PRBDto.class);

    /**
     * セグメントID
     */
    private String PRB00;
    /**
     * アクションコード
     */
    private String PRB01;
    /**
     * アクション日付／時刻
     */
    private String PRB02;
    /**
     * プロブレムID
     */
    private CWEDto PRB03;
    /**
     * プロブレムインスタンスID
     */
    private String PRB04;
    /**
     * 診療IDエピソード
     */
    private String PRB05;
    /**
     * プロブレムリストの優先権
     */
    private String PRB06;
    /**
     * プロブレム設定日付／時刻
     */
    private String PRB07;
    /**
     * 予想されるプロブレム解決日付／時刻
     */
    private String PRB08;
    /**
     * 実際のプロブレム解決日付／時刻
     */
    private String PRB09;
    /**
     * プロブレムの分類
     */
    private CWEDto PRB10;
    /**
     * プロブレムマネジメント職種
     */
    private CWEDto PRB11;
    /**
     * プロブレムの長期化
     */
    private CWEDto PRB12;
    /**
     * プロブレムの確認状態
     */
    private CWEDto PRB13;
    /**
     * プロブレムのライフサイクル状態
     */
    private CWEDto PRB14;
    /**
     * プロブレムのライフサイクル状態の日付／時刻
     */
    private String PRB15;
    /**
     * プロブレムの発生日付
     */
    private String PRB16;
    /**
     * プロブレムの発生テキスト
     */
    private String PRB17;
    /**
     * プロブレムのランキング
     */
    private CWEDto PRB18;
    /**
     * プロブレムの確実性
     */
    private CWEDto PRB19;
    /**
     * プロブレムの確率（0-1）
     */
    private String PRB20;
    /**
     * プロブレムの個々の認識
     */
    private CWEDto PRB21;
    /**
     * プロブレムの予後
     */
    private CWEDto PRB22;
    /**
     * 予後についての患者個人の認識
     */
    private CWEDto PRB23;
    /**
     * プロブレム／予後についての家族その他重要な関係者の認識
     */
    private String PRB24;
    /**
     * セキュリティ／感受性
     */
    private CWEDto PRB25;

    /**
     * @return the PRB00
     */
    public String getPRB00() {
        return PRB00;
    }

    /**
     * @param PRB00 the PRB00 to set
     */
    public void setPRB00(String PRB00) {
        this.PRB00 = PRB00;
    }

    /**
     * @return the PRB01
     */
    public String getPRB01() {
        return PRB01;
    }

    /**
     * @param PRB01 the PRB01 to set
     */
    public void setPRB01(String PRB01) {
        this.PRB01 = PRB01;
    }

    /**
     * @return the PRB02
     */
    public String getPRB02() {
        return PRB02;
    }

    /**
     * @param PRB02 the PRB02 to set
     */
    public void setPRB02(String PRB02) {
        this.PRB02 = PRB02;
    }

    /**
     * @return the PRB03
     */
    public CWEDto getPRB03() {
        return PRB03;
    }

    /**
     * @param PRB03 the PRB03 to set
     */
    public void setPRB03(CWEDto PRB03) {
        this.PRB03 = PRB03;
    }

    /**
     * @return the PRB04
     */
    public String getPRB04() {
        return PRB04;
    }

    /**
     * @param PRB04 the PRB04 to set
     */
    public void setPRB04(String PRB04) {
        this.PRB04 = PRB04;
    }

    /**
     * @return the PRB05
     */
    public String getPRB05() {
        return PRB05;
    }

    /**
     * @param PRB05 the PRB05 to set
     */
    public void setPRB05(String PRB05) {
        this.PRB05 = PRB05;
    }

    /**
     * @return the PRB06
     */
    public String getPRB06() {
        return PRB06;
    }

    /**
     * @param PRB06 the PRB06 to set
     */
    public void setPRB06(String PRB06) {
        this.PRB06 = PRB06;
    }

    /**
     * @return the PRB07
     */
    public String getPRB07() {
        return PRB07;
    }

    /**
     * @param PRB07 the PRB07 to set
     */
    public void setPRB07(String PRB07) {
        this.PRB07 = PRB07;
    }

    /**
     * @return the PRB08
     */
    public String getPRB08() {
        return PRB08;
    }

    /**
     * @param PRB08 the PRB08 to set
     */
    public void setPRB08(String PRB08) {
        this.PRB08 = PRB08;
    }

    /**
     * @return the PRB09
     */
    public String getPRB09() {
        return PRB09;
    }

    /**
     * @param PRB09 the PRB09 to set
     */
    public void setPRB09(String PRB09) {
        this.PRB09 = PRB09;
    }

    /**
     * @return the PRB10
     */
    public CWEDto getPRB10() {
        return PRB10;
    }

    /**
     * @param PRB10 the PRB10 to set
     */
    public void setPRB10(CWEDto PRB10) {
        this.PRB10 = PRB10;
    }

    /**
     * @return the PRB11
     */
    public CWEDto getPRB11() {
        return PRB11;
    }

    /**
     * @param PRB11 the PRB11 to set
     */
    public void setPRB11(CWEDto PRB11) {
        this.PRB11 = PRB11;
    }

    /**
     * @return the PRB12
     */
    public CWEDto getPRB12() {
        return PRB12;
    }

    /**
     * @param PRB12 the PRB12 to set
     */
    public void setPRB12(CWEDto PRB12) {
        this.PRB12 = PRB12;
    }

    /**
     * @return the PRB13
     */
    public CWEDto getPRB13() {
        return PRB13;
    }

    /**
     * @param PRB13 the PRB13 to set
     */
    public void setPRB13(CWEDto PRB13) {
        this.PRB13 = PRB13;
    }

    /**
     * @return the PRB14
     */
    public CWEDto getPRB14() {
        return PRB14;
    }

    /**
     * @param PRB14 the PRB14 to set
     */
    public void setPRB14(CWEDto PRB14) {
        this.PRB14 = PRB14;
    }

    /**
     * @return the PRB15
     */
    public String getPRB15() {
        return PRB15;
    }

    /**
     * @param PRB15 the PRB15 to set
     */
    public void setPRB15(String PRB15) {
        this.PRB15 = PRB15;
    }

    /**
     * @return the PRB16
     */
    public String getPRB16() {
        return PRB16;
    }

    /**
     * @param PRB16 the PRB16 to set
     */
    public void setPRB16(String PRB16) {
        this.PRB16 = PRB16;
    }

    /**
     * @return the PRB17
     */
    public String getPRB17() {
        return PRB17;
    }

    /**
     * @param PRB17 the PRB17 to set
     */
    public void setPRB17(String PRB17) {
        this.PRB17 = PRB17;
    }

    /**
     * @return the PRB18
     */
    public CWEDto getPRB18() {
        return PRB18;
    }

    /**
     * @param PRB18 the PRB18 to set
     */
    public void setPRB18(CWEDto PRB18) {
        this.PRB18 = PRB18;
    }

    /**
     * @return the PRB19
     */
    public CWEDto getPRB19() {
        return PRB19;
    }

    /**
     * @param PRB19 the PRB19 to set
     */
    public void setPRB19(CWEDto PRB19) {
        this.PRB19 = PRB19;
    }

    /**
     * @return the PRB20
     */
    public String getPRB20() {
        return PRB20;
    }

    /**
     * @param PRB20 the PRB20 to set
     */
    public void setPRB20(String PRB20) {
        this.PRB20 = PRB20;
    }

    /**
     * @return the PRB21
     */
    public CWEDto getPRB21() {
        return PRB21;
    }

    /**
     * @param PRB21 the PRB21 to set
     */
    public void setPRB21(CWEDto PRB21) {
        this.PRB21 = PRB21;
    }

    /**
     * @return the PRB22
     */
    public CWEDto getPRB22() {
        return PRB22;
    }

    /**
     * @param PRB22 the PRB22 to set
     */
    public void setPRB22(CWEDto PRB22) {
        this.PRB22 = PRB22;
    }

    /**
     * @return the PRB23
     */
    public CWEDto getPRB23() {
        return PRB23;
    }

    /**
     * @param PRB23 the PRB23 to set
     */
    public void setPRB23(CWEDto PRB23) {
        this.PRB23 = PRB23;
    }

    /**
     * @return the PRB24
     */
    public String getPRB24() {
        return PRB24;
    }

    /**
     * @param PRB24 the PRB24 to set
     */
    public void setPRB24(String PRB24) {
        this.PRB24 = PRB24;
    }

    /**
     * @return the PRB25
     */
    public CWEDto getPRB25() {
        return PRB25;
    }

    /**
     * @param PRB25 the PRB25 to set
     */
    public void setPRB25(CWEDto PRB25) {
        this.PRB25 = PRB25;
    }
    
    /**
     * PRBのセット
     */
    public void setPRB(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "PRB" + st_num;

       switch(target){
            case "PRB00":
                setPRB00(value);
                break;
            case "PRB01":
                setPRB01(value);
                break;
            case "PRB02":
                setPRB02(value);
                break;
            case "PRB03":
                CWEDto prb03_cwe = setCWE(value);
                setPRB03(prb03_cwe);
                break;
            case "PRB04":
                setPRB04(value);
                break;
            case "PRB05":
                setPRB05(value);
                break;
            case "PRB06":
                setPRB06(value);
                break;
            case "PRB07":
                setPRB07(value);
                break;
            case "PRB08":
                setPRB08(value);
                break;
            case "PRB09":
                setPRB09(value);
                break;
            case "PRB10":
                CWEDto prb10_cwe = setCWE(value);
                setPRB10(prb10_cwe);
                break;
            case "PRB11":
                CWEDto prb11_cwe = setCWE(value);
                setPRB11(prb11_cwe);
                break;
            case "PRB12":
                CWEDto prb12_cwe = setCWE(value);
                setPRB12(prb12_cwe);
                break;
            case "PRB13":
                CWEDto prb13_cwe = setCWE(value);
                setPRB13(prb13_cwe);
                break;
            case "PRB14":
                CWEDto prb14_cwe = setCWE(value);
                setPRB14(prb14_cwe);
                break;
            case "PRB15":
                setPRB15(value);
                break;
            case "PRB16":
                setPRB16(value);
                break;
            case "PRB17":
                setPRB17(value);
                break;
            case "PRB18":
                CWEDto prb18_cwe = setCWE(value);
                setPRB18(prb18_cwe);
                break;
            case "PRB19":
                CWEDto prb19_cwe = setCWE(value);
                setPRB19(prb19_cwe);
                break;
            case "PRB20":
                setPRB20(value);
                break;
            case "PRB21":
                CWEDto prb21_cwe = setCWE(value);
                setPRB21(prb21_cwe);
                break;
            case "PRB22":
                CWEDto prb22_cwe = setCWE(value);
                setPRB22(prb22_cwe);
                break;
            case "PRB23":
                CWEDto prb23_cwe = setCWE(value);
                setPRB23(prb23_cwe);
                break;
            case "PRB24":
                setPRB24(value);
                break;
            case "PRB25":
                CWEDto prb25_cwe = setCWE(value);
                setPRB25(prb25_cwe);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
       }
   }
    
    /**
     *  PRBのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getPRB(Integer f_num , Integer e_num , Integer r_num){
        String result = null;
        
        switch(f_num){
            case 0:
                result = getPRB00();
                break;
            case 1:
                result = getPRB01();
                break;
            case 2:
                result = getPRB02();
                break;
            case 3:
                result = getCWE(getPRB03() , e_num );
                break;
            case 4:
                result = getPRB04();
                break;
            case 5:
                result = getPRB05();
                break;
            case 6:
                result = getPRB06();
                break;
            case 7:
                result = getPRB07();
                break;
            case 8:
                result = getPRB08();
                break;
            case 9:
                result = getPRB09();
                break;
            case 10:
                result = getCWE(getPRB10() , e_num );
                break;
            case 11:
                result = getCWE(getPRB11() , e_num );
                break;
            case 12:
                result = getCWE(getPRB12() , e_num );
                break;
            case 13:
                result = getCWE(getPRB13() , e_num );
                break;
            case 14:
                result = getCWE(getPRB14() , e_num );
                break;
            case 15:
                result = getPRB15();
                break;
            case 16:
                result = getPRB16();
                break;
            case 17:
                result = getPRB17();
                break;
            case 18:
                result = getCWE(getPRB18() , e_num );
                break;
            case 19:
                result = getCWE(getPRB19() , e_num );
                break;
            case 20:
                result = getPRB20();
                break;
            case 21:
                result = getCWE(getPRB21() , e_num );
                break;
            case 22:
                result = getCWE(getPRB22() , e_num );
                break;
            case 23:
                result = getCWE(getPRB23() , e_num );
                break;
            case 24:
                result = getPRB24();
                break;
            case 25:
                result = getCWE(getPRB25() , e_num );
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }

    /**
     *  PRBのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public Object getPRBOb(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getPRB00();
                break;
            case 1:
                result = getPRB01();
                break;
            case 2:
                result = getPRB02();
                break;
            case 3:
                result = getPRB03();
                break;
            case 4:
                result = getPRB04();
                break;
            case 5:
                result = getPRB05();
                break;
            case 6:
                result = getPRB06();
                break;
            case 7:
                result = getPRB07();
                break;
            case 8:
                result = getPRB08();
                break;
            case 9:
                result = getPRB09();
                break;
            case 10:
                result = getPRB10();
                break;
            case 11:
                result = getPRB11();
                break;
            case 12:
                result = getPRB12();
                break;
            case 13:
                result = getPRB13();
                break;
            case 14:
                result = getPRB14();
                break;
            case 15:
                result = getPRB15();
                break;
            case 16:
                result = getPRB16();
                break;
            case 17:
                result = getPRB17();
                break;
            case 18:
                result = getPRB18();
                break;
            case 19:
                result = getPRB19();
                break;
            case 20:
                result = getPRB20();
                break;
            case 21:
                result = getPRB21();
                break;
            case 22:
                result = getPRB22();
                break;
            case 23:
                result = getPRB23();
                break;
            case 24:
                result = getPRB24();
                break;
            case 25:
                result = getPRB25();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
}
