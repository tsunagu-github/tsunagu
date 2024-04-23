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
 */
public class XADDto extends baseFieldDto{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(XADDto.class);
        /**
     * XAD型の成分１
     * ブランク
     */
    private String XAD01;
    /**
     * XAD型の成分2
     * ブランク
     */
    private String XAD02;
    /**
     * XAD型の成分3
     * ブランク
     */
    private String XAD03;
    /**
     * XAD型の成分4
     * ブランク
     */
    private String XAD04;
    /**
     * XAD型の成分5
     * 郵便番号
     */
    private String XAD05;
    /**
     * XAD型の成分6
     * 国コード　JPN:日本
     */
    private String XAD06;
    /**
     * XAD型の成分7
     * 住所タイプ　H;自宅
     */
    private String XAD07;
    /**
     * XAD型の成分8
     * 住所
     */
    private String XAD08;

    /**
     * @return the XAD01
     */
    public String getXAD01() {
        return XAD01;
    }

    /**
     * @param XAD01 the XAD01 to set
     */
    public void setXAD01(String XAD01) {
        this.XAD01 = XAD01;
    }

    /**
     * @return the XAD02
     */
    public String getXAD02() {
        return XAD02;
    }

    /**
     * @param XAD02 the XAD02 to set
     */
    public void setXAD02(String XAD02) {
        this.XAD02 = XAD02;
    }

    /**
     * @return the XAD03
     */
    public String getXAD03() {
        return XAD03;
    }

    /**
     * @param XAD03 the XAD03 to set
     */
    public void setXAD03(String XAD03) {
        this.XAD03 = XAD03;
    }

    /**
     * @return the XAD04
     */
    public String getXAD04() {
        return XAD04;
    }

    /**
     * @param XAD04 the XAD04 to set
     */
    public void setXAD04(String XAD04) {
        this.XAD04 = XAD04;
    }

    /**
     * @return the XAD05
     */
    public String getXAD05() {
        return XAD05;
    }

    /**
     * @param XAD05 the XAD05 to set
     */
    public void setXAD05(String XAD05) {
        this.XAD05 = XAD05;
    }

    /**
     * @return the XAD06
     */
    public String getXAD06() {
        return XAD06;
    }

    /**
     * @param XAD06 the XAD06 to set
     */
    public void setXAD06(String XAD06) {
        this.XAD06 = XAD06;
    }

    /**
     * @return the XAD07
     */
    public String getXAD07() {
        return XAD07;
    }

    /**
     * @param XAD07 the XAD07 to set
     */
    public void setXAD07(String XAD07) {
        this.XAD07 = XAD07;
    }

    /**
     * @return the XAD08
     */
    public String getXAD08() {
        return XAD08;
    }

    /**
     * @param XAD08 the XAD08 to set
     */
    public void setXAD08(String XAD08) {
        this.XAD08 = XAD08;
    }
    
    /**
     * XAD型にセットする
     * 
     */
    public void setXAD(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "XAD" + st_num;
        
        switch(target){
            case "XAD01":
                setXAD01(value);
                break;
            case "XAD02":
                setXAD02(value);
                break;
            case "XAD03":
                setXAD03(value);
                break;
            case "XAD04":
                setXAD04(value);
                break;
            case "XAD05":
                setXAD05(value);
                break;
            case "XAD06":
                setXAD06(value);
                break;
            case "XAD07":
                setXAD07(value);
                break;
            case "XAD08":
                setXAD08(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
    }
    
    /**
     * XAD型から取得する
     */
    public String getXAD(int num){
        String result = null;
        
        switch(num){
            case 1:
                result = getXAD01();
                break;
            case 2:
                result = getXAD02();
                break;
            case 3:
                result = getXAD03();
                break;
            case 4:
                result = getXAD04();
                break;
            case 5:
                result = getXAD05();
                break;
            case 6:
                result = getXAD06();
                break;
            case 7:
                result = getXAD07();
                break;
            case 8:
                result = getXAD08();
                break;
            default:
                logger.debug("指定番号は対応していません" + num);
        }
        return result;
    }
}
