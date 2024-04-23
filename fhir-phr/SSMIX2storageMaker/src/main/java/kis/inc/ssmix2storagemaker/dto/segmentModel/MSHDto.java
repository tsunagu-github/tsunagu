/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import kis.inc.ssmix2storagemaker.controller.MakeStrageController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class MSHDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MSHDto.class);
    
    /**
    * セグメントID
    */
    private String MSH00;
    
    /**
    *　フィールド区切り文字
    */
    private String MSH01;

    /**
    * コード化文字
    */
    private String MSH02;

    /**
    *　送信アプリケーション
    */
    private String MSH03;

    /**
    *　送信施設 
    */
    private String MSH04;

    /**
    *　受信アプリケーション
    */
    private String MSH05;

    /**
    *　受信施設
    */
    private String MSH06;

    /**
    *　メッセージ日時
    */
    private String MSH07;

    /**
    *　セキュリティ
    */
    private String MSH08;

    /**
    *　メッセージ型
    */
    private String MSH09;

    /**
    *　メッセージ型制御ID
    */
    private String MSH10;

    /**
    *　処理ID
    */
    private String MSH11;

    /**
    *　バージョンID
    */
    private String MSH12;

    /**
    *　シーケンス番号
    */
    private String MSH13;

    /**
    *　継続ポインタ
    */
    private String MSH14;

    /**
    *　受諾肯定応答型
    */
    private String MSH15;

    /**
    *　アプリケーション肯定応答型
    */
    private String MSH16;

    /**
    *　国コード
    */
    private String MSH17;

    /**
    *　文字セット
    */
    private String MSH18;

    /**
    *　主要言語
    */
    private String MSH19;

    /**
    *　代替文字セット操作法
    */
    private String MSH20;
 
    /**
    *　メッセージプロファイル識別子
    */
    private String MSH21;

    /**
     * @return the MSH00
     */
    public String getMSH00() {
        return MSH00;
    }

    /**
     * @param MSH00 the MSH00 to set
     */
    public void setMSH00(String MSH00) {
        this.MSH00 = MSH00;
    }

    /**
     * @return the MSH01
     */
    public String getMSH01() {
        return MSH01;
    }

    /**
     * @param MSH01 the MSH01 to set
     */
    public void setMSH01(String MSH01) {
        this.MSH01 = MSH01;
    }

    /**
     * @return the MSH02
     */
    public String getMSH02() {
        return MSH02;
    }

    /**
     * @param MSH02 the MSH02 to set
     */
    public void setMSH02(String MSH02) {
        this.MSH02 = MSH02;
    }

    /**
     * @return the MSH03
     */
    public String getMSH03() {
        return MSH03;
    }

    /**
     * @param MSH03 the MSH03 to set
     */
    public void setMSH03(String MSH03) {
        this.MSH03 = MSH03;
    }

    /**
     * @return the MSH04
     */
    public String getMSH04() {
        return MSH04;
    }

    /**
     * @param MSH04 the MSH04 to set
     */
    public void setMSH04(String MSH04) {
        this.MSH04 = MSH04;
    }

    /**
     * @return the MSH05
     */
    public String getMSH05() {
        return MSH05;
    }

    /**
     * @param MSH05 the MSH05 to set
     */
    public void setMSH05(String MSH05) {
        this.MSH05 = MSH05;
    }

    /**
     * @return the MSH06
     */
    public String getMSH06() {
        return MSH06;
    }

    /**
     * @param MSH06 the MSH06 to set
     */
    public void setMSH06(String MSH06) {
        this.MSH06 = MSH06;
    }

    /**
     * @return the MSH07
     */
    public String getMSH07() {
        return MSH07;
    }

    /**
     * @param MSH07 the MSH07 to set
     */
    public void setMSH07(String MSH07) {
        this.MSH07 = MSH07;
    }

    /**
     * @return the MSH08
     */
    public String getMSH08() {
        return MSH08;
    }

    /**
     * @param MSH08 the MSH08 to set
     */
    public void setMSH08(String MSH08) {
        this.MSH08 = MSH08;
    }

    /**
     * @return the MSH09
     */
    public String getMSH09() {
        return MSH09;
    }

    /**
     * @param MSH09 the MSH09 to set
     */
    public void setMSH09(String MSH09) {
        this.MSH09 = MSH09;
    }

    /**
     * @return the MSH10
     */
    public String getMSH10() {
        return MSH10;
    }

    /**
     * @param MSH10 the MSH10 to set
     */
    public void setMSH10(String MSH10) {
        this.MSH10 = MSH10;
    }

    /**
     * @return the MSH11
     */
    public String getMSH11() {
        return MSH11;
    }

    /**
     * @param MSH11 the MSH11 to set
     */
    public void setMSH11(String MSH11) {
        this.MSH11 = MSH11;
    }

    /**
     * @return the MSH12
     */
    public String getMSH12() {
        return MSH12;
    }

    /**
     * @param MSH12 the MSH12 to set
     */
    public void setMSH12(String MSH12) {
        this.MSH12 = MSH12;
    }

    /**
     * @return the MSH13
     */
    public String getMSH13() {
        return MSH13;
    }

    /**
     * @param MSH13 the MSH13 to set
     */
    public void setMSH13(String MSH13) {
        this.MSH13 = MSH13;
    }

    /**
     * @return the MSH14
     */
    public String getMSH14() {
        return MSH14;
    }

    /**
     * @param MSH14 the MSH14 to set
     */
    public void setMSH14(String MSH14) {
        this.MSH14 = MSH14;
    }

    /**
     * @return the MSH15
     */
    public String getMSH15() {
        return MSH15;
    }

    /**
     * @param MSH15 the MSH15 to set
     */
    public void setMSH15(String MSH15) {
        this.MSH15 = MSH15;
    }

    /**
     * @return the MSH16
     */
    public String getMSH16() {
        return MSH16;
    }

    /**
     * @param MSH16 the MSH16 to set
     */
    public void setMSH16(String MSH16) {
        this.MSH16 = MSH16;
    }

    /**
     * @return the MSH17
     */
    public String getMSH17() {
        return MSH17;
    }

    /**
     * @param MSH17 the MSH17 to set
     */
    public void setMSH17(String MSH17) {
        this.MSH17 = MSH17;
    }

    /**
     * @return the MSH18
     */
    public String getMSH18() {
        return MSH18;
    }

    /**
     * @param MSH18 the MSH18 to set
     */
    public void setMSH18(String MSH18) {
        this.MSH18 = MSH18;
    }

    /**
     * @return the MSH19
     */
    public String getMSH19() {
        return MSH19;
    }

    /**
     * @param MSH19 the MSH19 to set
     */
    public void setMSH19(String MSH19) {
        this.MSH19 = MSH19;
    }

    /**
     * @return the MSH20
     */
    public String getMSH20() {
        return MSH20;
    }

    /**
     * @param MSH20 the MSH20 to set
     */
    public void setMSH20(String MSH20) {
        this.MSH20 = MSH20;
    }

    /**
     * @return the MSH21
     */
    public String getMSH21() {
        return MSH21;
    }

    /**
     * @param MSH21 the MSH21 to set
     */
    public void setMSH21(String MSH21) {
        this.MSH21 = MSH21;
    }
    
    /**
     * 番号からセット
     * ファイルから取得したときは配列にいれるのでそこのcaseを想定する
     * @param value 保存値
     * @param num　対象Number
     */
    public void setMSH(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "MSH" + st_num;
        
        switch(target){
            case "MSH00":
                setMSH00(value);
                break;
            case "MSH01":
                setMSH01(value);
                break;
            case "MSH02":
                setMSH02(value);
                break;
            case "MSH03":
                setMSH03(value);
                break;
            case "MSH04":
                setMSH04(value);
                break;
            case "MSH05":
                setMSH05(value);
                break;
            case "MSH06":
                setMSH06(value);
                break;
            case "MSH07":
                setMSH07(value);
                break;
            case "MSH08":
                setMSH08(value);
                break;
            case "MSH09":
                setMSH09(value);
                break;
            case "MSH10":
                setMSH10(value);
                break;
            case "MSH11":
                setMSH11(value);
                break;
            case "MSH12":
                setMSH12(value);
                break;
            case "MSH13":
                setMSH13(value);
                break;
            case "MSH14":
                setMSH14(value);
                break;
            case "MSH15":
                setMSH16(value);
                break;
            case "MSH16":
                setMSH16(value);
                break;
            case "MSH17":
                setMSH17(value);
                break;
            case "MSH18":
                setMSH18(value);
                break;
            case "MSH19":
                setMSH19(value);
                break;
            case "MSH20":
                setMSH20(value);
                break;
            case "MSH21":
                setMSH21(value);
                break;
            default :
                logger.debug("未定義の数値がきています " + num);
        }
    }
    
     /**
     * 番号からゲット
     * ファイルから取得したときは配列にいれるのでそこのcaseを想定する
     */
    public String getMSH(int num){
        String target = null;
        
        switch(num){
            case 0:
                target = getMSH00();
                break;
            case 1:
                target = getMSH01();
                break;
            case 2:
                target = getMSH02();
                break;
            case 3:
                target = getMSH03();
                break;
            case 4:
                target = getMSH04();
                break;
            case 5:
                target = getMSH05();
                break;
            case 6:
                target = getMSH06();
                break;
            case 7:
                target = getMSH07();
                break;
            case 8:
                target = getMSH08();
                break;
            case 9:
                target = getMSH09();
                break;
            case 10:
                target = getMSH10();
                break;
            case 11:
                target = getMSH11();
                break;
            case 12:
                target = getMSH12();
                break;
            case 13:
                target = getMSH13();
                break;
            case 14:
                target = getMSH14();
                break;
            case 15:
                target = getMSH15();
                break;
            case 16:
                target = getMSH16();
                break;
            case 17:
                target = getMSH17();
                break;
            case 18:
                target = getMSH18();
                break;
            case 19:
                target = getMSH19();
                break;
            case 20:
                target = getMSH20();
                break;
            case 21:
                target = getMSH21();
                break;
            default :
                logger.debug("未定義の数値がきています " + num);
        }
        
        return target;
    }
}
