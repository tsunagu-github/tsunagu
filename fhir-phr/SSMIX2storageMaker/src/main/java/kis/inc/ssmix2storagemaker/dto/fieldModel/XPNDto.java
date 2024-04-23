/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.fieldModel;

import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * XPN型のDto
 * 患者氏名に使用
 */
public class XPNDto extends baseFieldDto{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(XPNDto.class);
    
    /**
     * XPN型の成分1
     * 姓が入る
     */
    private String XPN01;
    /**
     * XPN型の成分2
     * 名が入る
     */
    private String XPN02;
    /**
     * XPN型の成分3
     * ブランク
     */
    private String XPN03;
    /**
     * XPN型の成分4
     * ブランク
     */
    private String XPN04;
    /**
     * XPN型の成分5
     * ブランク
     */
    private String XPN05;
    /**
     * XPN型の成分6
     * ブランク
     */
    private String XPN06;
    /**
     * XPN型の成分7
     * 名前タイプ　基本はL
     */
    private String XPN07;
    /**
     * XPN型の成分8
     * 名前表記　I：漢字表記　P：カナ表記
     */
    private String XPN08;

    /**
     * @return the XPN01
     */
    public String getXPN01() {
        return XPN01;
    }

    /**
     * @param XPN01 the XPN01 to set
     */
    public void setXPN01(String XPN01) {
        this.XPN01 = XPN01;
    }

    /**
     * @return the XPN02
     */
    public String getXPN02() {
        return XPN02;
    }

    /**
     * @param XPN02 the XPN02 to set
     */
    public void setXPN02(String XPN02) {
        this.XPN02 = XPN02;
    }

    /**
     * @return the XPN03
     */
    public String getXPN03() {
        return XPN03;
    }

    /**
     * @param XPN03 the XPN03 to set
     */
    public void setXPN03(String XPN03) {
        this.XPN03 = XPN03;
    }

    /**
     * @return the XPN04
     */
    public String getXPN04() {
        return XPN04;
    }

    /**
     * @param XPN04 the XPN04 to set
     */
    public void setXPN04(String XPN04) {
        this.XPN04 = XPN04;
    }

    /**
     * @return the XPN05
     */
    public String getXPN05() {
        return XPN05;
    }

    /**
     * @param XPN05 the XPN05 to set
     */
    public void setXPN05(String XPN05) {
        this.XPN05 = XPN05;
    }

    /**
     * @return the XPN06
     */
    public String getXPN06() {
        return XPN06;
    }

    /**
     * @param XPN06 the XPN06 to set
     */
    public void setXPN06(String XPN06) {
        this.XPN06 = XPN06;
    }

    /**
     * @return the XPN07
     */
    public String getXPN07() {
        return XPN07;
    }

    /**
     * @param XPN07 the XPN07 to set
     */
    public void setXPN07(String XPN07) {
        this.XPN07 = XPN07;
    }

    /**
     * @return the XPN08
     */
    public String getXPN08() {
        return XPN08;
    }

    /**
     * @param XPN08 the XPN08 to set
     */
    public void setXPN08(String XPN08) {
        this.XPN08 = XPN08;
    }
    
    /**
    * XPN型のセット
    * 
    */
    public void setXPN(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "XPN" + st_num;
        
        switch(target){
            case "XPN01":
                setXPN01(value);
                break;
            case "XPN02":
                setXPN02(value);
                break;
            case "XPN03":
                setXPN03(value);
                break;
            case "XPN04":
                setXPN04(value);
                break;
            case "XPN05":
                setXPN05(value);
                break;
            case "XPN06":
                setXPN06(value);
                break;
            case "XPN07":
                setXPN07(value);
                break;
            case "XPN08":
                setXPN08(value);
                break;
            default:
                logger.debug("指定された番号は対応していません " + num);
        }
    }
    
        /**
    * XPN型のセット
    * 
    */
    public String getXPN(int num){
        String result = null;
        
        switch(num){
            case 1:
                result = getXPN01();
                break;
            case 2:
                result = getXPN02();
                break;
            case 3:
                result = getXPN03();
                break;
            case 4:
                result = getXPN04();
                break;
            case 5:
                result = getXPN05();
                break;
            case 6:
                result = getXPN06();
                break;
            case 7:
                result = getXPN07();
                break;
            case 8:
                result = getXPN08();
                break;
            default:
                logger.debug("指定された番号は対応していません " + num);
        }
        
        return result;
    }
}
