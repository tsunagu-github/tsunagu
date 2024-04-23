/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;

/**
 *
 * @author kis-note
 */
public class RXRDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(RXRDto.class);
    /**
     * セグメントID
     */
    private String RXR00;
    /**
     * 経路
     */
    private CWEDto RXR01;
    /**
     * 部位
     */
    private CWEDto RXR02;
    /**
     * 投薬装置
     */
    private CWEDto RXR03;
    /**
     * 投薬方法
     */
    private CWEDto RXR04;
    /**
     * 経路指示
     */
    private CWEDto RXR05;
    /**
     * 投薬現場モディファイア
     */
    private CWEDto RXR06;

    /**
     * @return the RXR00
     */
    public String getRXR00() {
        return RXR00;
    }

    /**
     * @param RXR00 the RXR00 to set
     */
    public void setRXR00(String RXR00) {
        this.RXR00 = RXR00;
    }

    /**
     * @return the RXR01
     */
    public CWEDto getRXR01() {
        return RXR01;
    }

    /**
     * @param RXR01 the RXR01 to set
     */
    public void setRXR01(CWEDto RXR01) {
        this.RXR01 = RXR01;
    }

    /**
     * @return the RXR02
     */
    public CWEDto getRXR02() {
        return RXR02;
    }

    /**
     * @param RXR02 the RXR02 to set
     */
    public void setRXR02(CWEDto RXR02) {
        this.RXR02 = RXR02;
    }

    /**
     * @return the RXR03
     */
    public CWEDto getRXR03() {
        return RXR03;
    }

    /**
     * @param RXR03 the RXR03 to set
     */
    public void setRXR03(CWEDto RXR03) {
        this.RXR03 = RXR03;
    }

    /**
     * @return the RXR04
     */
    public CWEDto getRXR04() {
        return RXR04;
    }

    /**
     * @param RXR04 the RXR04 to set
     */
    public void setRXR04(CWEDto RXR04) {
        this.RXR04 = RXR04;
    }

    /**
     * @return the RXR05
     */
    public CWEDto getRXR05() {
        return RXR05;
    }

    /**
     * @param RXR05 the RXR05 to set
     */
    public void setRXR05(CWEDto RXR05) {
        this.RXR05 = RXR05;
    }

    /**
     * @return the RXR06
     */
    public CWEDto getRXR06() {
        return RXR06;
    }

    /**
     * @param RXR06 the RXR06 to set
     */
    public void setRXR06(CWEDto RXR06) {
        this.RXR06 = RXR06;
    }

    /**
     * RXRのセット
     */
    public void setRXR(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "RXR" + st_num;

        switch(target){
            case "RXR00":
                setRXR00(value);
                break;
            case "RXR01":
                CWEDto rxr01 = setCWE(value);
                setRXR01(rxr01);
                break;
            case "RXR02":
                CWEDto rxr02 = setCWE(value);
                setRXR02(rxr02);
                break;
            case "RXR03":
                CWEDto rxr03 = setCWE(value);
                setRXR03(rxr03);
                break;
            case "RXR04":
                CWEDto rxr04 = setCWE(value);
                setRXR04(rxr04);
                break;
            case "RXR05":
                CWEDto rxr05 = setCWE(value);
                setRXR05(rxr05);
                break;
            case "RXR06":
                CWEDto rxr06 = setCWE(value);
                setRXR06(rxr06);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }

    }

    /**
     *  RXRのゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getRXR(Integer f_num , Integer e_num , Integer r_num){
        String result = null;

        switch(f_num){
            case 0:
                result = getRXR00();
                break;
            case 1:
                result = getCWE(getRXR01() , e_num );
                break;
            case 2:
                result = getCWE(getRXR02() , e_num );
                break;
            case 3:
                result = getCWE(getRXR03() , e_num );
                break;
            case 4:
                result = getCWE(getRXR04() , e_num );
                break;
            case 5:
                result = getCWE(getRXR05() , e_num );
                break;
            case 6:
                result = getCWE(getRXR06() , e_num );
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }

    /**
     *  RXRのゲット
     *
     * @param f_num フィールドNo　必須
     */
    public Object getRXROb(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getRXR00();
                break;
            case 1:
                result = getRXR01();
                break;
            case 2:
                result = getRXR02();
                break;
            case 3:
                result = getRXR03();
                break;
            case 4:
                result = getRXR04();
                break;
            case 5:
                result = getRXR05();
                break;
            case 6:
                result = getRXR06();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }
}
