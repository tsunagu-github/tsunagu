/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;

/**
 *
 * @author kis-note
 */
public class RXCDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(RXCDto.class);
    /**
     * セグメントID
     */
    private String RXC00;
    /**
     * RX 成分タイプ
     */
    private String RXC01;
    /**
     * 成分コード
     */
    private CWEDto RXC02;
    /**
     * 成分量
     */
    private String RXC03;
    /**
     * 成分単位
     */
    private CWEDto RXC04;
    /**
     * 成分力価
     */
    private String RXC05;
    /**
     * 成分力価単位
     */
    private CWEDto RXC06;
    /**
     * 補足コード
     */
    private List<CWEDto> RXC07;
    /**
     * 成分薬力価量
     */
    private String RXC08;
    /**
     * 成分力価量単位
     */
    private CWEDto RXC09;

    /**
     * @return the RXC00
     */
    public String getRXC00() {
        return RXC00;
    }

    /**
     * @param RXC00 the RXC00 to set
     */
    public void setRXC00(String RXC00) {
        this.RXC00 = RXC00;
    }

    /**
     * @return the RXC01
     */
    public String getRXC01() {
        return RXC01;
    }

    /**
     * @param RXC01 the RXC01 to set
     */
    public void setRXC01(String RXC01) {
        this.RXC01 = RXC01;
    }

    /**
     * @return the RXC02
     */
    public CWEDto getRXC02() {
        return RXC02;
    }

    /**
     * @param RXC02 the RXC02 to set
     */
    public void setRXC02(CWEDto RXC02) {
        this.RXC02 = RXC02;
    }

    /**
     * @return the RXC03
     */
    public String getRXC03() {
        return RXC03;
    }

    /**
     * @param RXC03 the RXC03 to set
     */
    public void setRXC03(String RXC03) {
        this.RXC03 = RXC03;
    }

    /**
     * @return the RXC04
     */
    public CWEDto getRXC04() {
        return RXC04;
    }

    /**
     * @param RXC04 the RXC04 to set
     */
    public void setRXC04(CWEDto RXC04) {
        this.RXC04 = RXC04;
    }

    /**
     * @return the RXC05
     */
    public String getRXC05() {
        return RXC05;
    }

    /**
     * @param RXC05 the RXC05 to set
     */
    public void setRXC05(String RXC05) {
        this.RXC05 = RXC05;
    }

    /**
     * @return the RXC06
     */
    public CWEDto getRXC06() {
        return RXC06;
    }

    /**
     * @param RXC06 the RXC06 to set
     */
    public void setRXC06(CWEDto RXC06) {
        this.RXC06 = RXC06;
    }

    /**
     * @return the RXC07
     */
    public List<CWEDto> getRXC07() {
        return RXC07;
    }

    /**
     * @param RXC07 the RXC07 to set
     */
    public void setRXC07(List<CWEDto> RXC07) {
        this.RXC07 = RXC07;
    }

    /**
     * @return the RXC08
     */
    public String getRXC08() {
        return RXC08;
    }

    /**
     * @param RXC08 the RXC08 to set
     */
    public void setRXC08(String RXC08) {
        this.RXC08 = RXC08;
    }

    /**
     * @return the RXC09
     */
    public CWEDto getRXC09() {
        return RXC09;
    }

    /**
     * @param RXC09 the RXC09 to set
     */
    public void setRXC09(CWEDto RXC09) {
        this.RXC09 = RXC09;
    }

    /**
     * RXCのセット
     */
    public void setRXC(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "RXC" + st_num;

        switch(target){
            case "RXC00":
                setRXC00(value);
                break;
            case "RXC01":
                setRXC01(value);
                break;
            case "RXC02":
                CWEDto rxc02 = setCWE(value);
                setRXC02(rxc02);
                break;
            case "RXC03":
                setRXC03(value);
                break;
            case "RXC04":
                CWEDto rxc04 = setCWE(value);
                setRXC04(rxc04);
                break;
            case "RXC05":
                setRXC05(value);
                break;
            case "RXC06":
                CWEDto rxc06 = setCWE(value);
                setRXC06(rxc06);
                break;
            case "RXC07":
                List<CWEDto> rxc07List = setCWEList(value);
                setRXC07(rxc07List);
                break;
            case "RXC08":
                setRXC08(value);
                break;
            case "RXC09":
                CWEDto rxc09 = setCWE(value);
                setRXC09(rxc09);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }

    }

    /**
     *  RXCのゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getRXC(Integer f_num , Integer e_num , Integer r_num){
        String result = null;

        switch(f_num){
            case 0:
                result = getRXC00();
                break;
            case 1:
                result = getRXC01();
                break;
            case 2:
                result = getCWE(getRXC02() , e_num );
                break;
            case 3:
                result = getRXC03();
                break;
            case 4:
                result = getCWE(getRXC04() , e_num );
                break;
            case 5:
                result = getRXC05();
                break;
            case 6:
                result = getCWE(getRXC06() , e_num );
                break;
            case 7:
                result = getCWE(getRXC07() , e_num , r_num);
                break;
            case 8:
                result = getRXC08();
                break;
            case 9:
                result = getCWE(getRXC09() , e_num );
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }

    /**
     *  RXCのゲット
     *
     * @param f_num フィールドNo　必須
     */
    public Object getRXCOb(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getRXC00();
                break;
            case 1:
                result = getRXC01();
                break;
            case 2:
                result = getRXC02();
                break;
            case 3:
                result = getRXC03();
                break;
            case 4:
                result = getRXC04();
                break;
            case 5:
                result = getRXC05();
                break;
            case 6:
                result = getRXC06();
                break;
            case 7:
                result = getRXC07();
                break;
            case 8:
                result = getRXC08();
                break;
            case 9:
                result = getRXC09();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }
}
