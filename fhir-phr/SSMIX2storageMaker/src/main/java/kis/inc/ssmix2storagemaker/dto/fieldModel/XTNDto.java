/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.fieldModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * XTN型のDto
 * 電話番号に使用
 */
public class XTNDto extends baseFieldDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(XTNDto.class);
    
    /**
     * XTN型の成分1
     * 
     */
    private String XTN01;
    /**
     * XTN型の成分2
     * 遠距離通信用途コード　PRN：主たる住居の番号
     */
    private String XTN02;
    /**
     * XTN型の成分3
     * 通信機器対応　PH:電話
     */
    private String XTN03;
    /**
     * XTN型の成分4
     * 
     */
    private String XTN04;
    /**
     * XTN型の成分5
     * 
     */
    private String XTN05;
    /**
     * XTN型の成分6
     * 
     */
    private String XTN06;
    /**
     * XTN型の成分7
     * 
     */
    private String XTN07;
    /**
     * XTN型の成分8
     * 
     */
    private String XTN08;
    /**
     * XTN型の成分9
     * 
     */
    private String XTN09;
    /**
     * XTN型の成分10
     * 
     */
    private String XTN10;
    /**
     * XTN型の成分11
     * 
     */
    private String XTN11;
    /**
     * XTN型の成分12
     * 電話番号
     */
    private String XTN12;

    /**
     * @return the XTN01
     */
    public String getXTN01() {
        return XTN01;
    }

    /**
     * @param XTN01 the XTN01 to set
     */
    public void setXTN01(String XTN01) {
        this.XTN01 = XTN01;
    }

    /**
     * @return the XTN02
     */
    public String getXTN02() {
        return XTN02;
    }

    /**
     * @param XTN02 the XTN02 to set
     */
    public void setXTN02(String XTN02) {
        this.XTN02 = XTN02;
    }

    /**
     * @return the XTN03
     */
    public String getXTN03() {
        return XTN03;
    }

    /**
     * @param XTN03 the XTN03 to set
     */
    public void setXTN03(String XTN03) {
        this.XTN03 = XTN03;
    }

    /**
     * @return the XTN04
     */
    public String getXTN04() {
        return XTN04;
    }

    /**
     * @param XTN04 the XTN04 to set
     */
    public void setXTN04(String XTN04) {
        this.XTN04 = XTN04;
    }

    /**
     * @return the XTN05
     */
    public String getXTN05() {
        return XTN05;
    }

    /**
     * @param XTN05 the XTN05 to set
     */
    public void setXTN05(String XTN05) {
        this.XTN05 = XTN05;
    }

    /**
     * @return the XTN06
     */
    public String getXTN06() {
        return XTN06;
    }

    /**
     * @param XTN06 the XTN06 to set
     */
    public void setXTN06(String XTN06) {
        this.XTN06 = XTN06;
    }

    /**
     * @return the XTN07
     */
    public String getXTN07() {
        return XTN07;
    }

    /**
     * @param XTN07 the XTN07 to set
     */
    public void setXTN07(String XTN07) {
        this.XTN07 = XTN07;
    }

    /**
     * @return the XTN08
     */
    public String getXTN08() {
        return XTN08;
    }

    /**
     * @param XTN08 the XTN08 to set
     */
    public void setXTN08(String XTN08) {
        this.XTN08 = XTN08;
    }

    /**
     * @return the XTN09
     */
    public String getXTN09() {
        return XTN09;
    }

    /**
     * @param XTN09 the XTN09 to set
     */
    public void setXTN09(String XTN09) {
        this.XTN09 = XTN09;
    }

    /**
     * @return the XTN10
     */
    public String getXTN10() {
        return XTN10;
    }

    /**
     * @param XTN10 the XTN10 to set
     */
    public void setXTN10(String XTN10) {
        this.XTN10 = XTN10;
    }

    /**
     * @return the XTN11
     */
    public String getXTN11() {
        return XTN11;
    }

    /**
     * @param XTN11 the XTN11 to set
     */
    public void setXTN11(String XTN11) {
        this.XTN11 = XTN11;
    }

    /**
     * @return the XTN12
     */
    public String getXTN12() {
        return XTN12;
    }

    /**
     * @param XTN12 the XTN12 to set
     */
    public void setXTN12(String XTN12) {
        this.XTN12 = XTN12;
    }

    /**
     * XTN型にセット
     */
    public void setXTN(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "XTN" + st_num;
        
        switch(target){
            case "XTN01":
                setXTN01(value);
                break;
            case "XTN02":
                setXTN02(value);
                break;
            case "XTN03":
                setXTN03(value);
                break;
            case "XTN04":
                setXTN04(value);
                break;
            case "XTN05":
                setXTN05(value);
                break;
            case "XTN06":
                setXTN06(value);
                break;
            case "XTN07":
                setXTN07(value);
                break;
            case "XTN08":
                setXTN08(value);
                break;
            case "XTN09":
                setXTN09(value);
                break;
            case "XTN10":
                setXTN10(value);
                break;
            case "XTN11":
                setXTN11(value);
                break;
            case "XTN12":
                setXTN12(value);
                break;
            default:
                logger.debug("指定番号は対応していません "+ num);
        }
    }
    
    /**
     * XTNのゲット
     */
    public String getXTN(int num){
        String result = null;
        
        switch(num){
            case 1:
                result = getXTN01();
                break;
            case 2:
                result = getXTN02();
                break;
            case 3:
                result = getXTN03();
                break;
            case 4:
                result = getXTN04();
                break;
            case 5:
                result = getXTN05();
                break;
            case 6:
                result = getXTN06();
                break;
            case 7:
                result = getXTN07();
                break;
            case 8:
                result = getXTN08();
                break;
            case 9:
                result = getXTN09();
                break;
            case 10:
                result = getXTN10();
                break;
            case 11:
                result = getXTN11();
                break;
            case 12:
                result = getXTN12();
                break;
            default:
                logger.debug("指定番号は対応していません "+ num);
        }
        
        return result;
    }
}
