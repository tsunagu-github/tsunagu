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
 * CWE型のDto
 */
public class CWEDto extends baseFieldDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(CWEDto.class);
    
    /**
     * CWE型の成分１
     */
    private String CWE01;
    /**
     * CWE型の成分2
     */
    private String CWE02;
    /**
     * CWE型の成分3
     */
    private String CWE03;
    /**
     * CWE型の成分4
     */
    private String CWE04;
    /**
     * CWE型の成分5
     */
    private String CWE05;
    /**
     * CWE型の成分6
     */
    private String CWE06;

    /**
     * @return the CWE01
     */
    public String getCWE01() {
        return CWE01;
    }

    /**
     * @param CWE01 the CWE01 to set
     */
    public void setCWE01(String CWE01) {
        this.CWE01 = CWE01;
    }

    /**
     * @return the CWE02
     */
    public String getCWE02() {
        return CWE02;
    }

    /**
     * @param CWE02 the CWE02 to set
     */
    public void setCWE02(String CWE02) {
        this.CWE02 = CWE02;
    }

    /**
     * @return the CWE03
     */
    public String getCWE03() {
        return CWE03;
    }

    /**
     * @param CWE03 the CWE03 to set
     */
    public void setCWE03(String CWE03) {
        this.CWE03 = CWE03;
    }

    /**
     * @return the CWE04
     */
    public String getCWE04() {
        return CWE04;
    }

    /**
     * @param CWE04 the CWE04 to set
     */
    public void setCWE04(String CWE04) {
        this.CWE04 = CWE04;
    }

    /**
     * @return the CWE05
     */
    public String getCWE05() {
        return CWE05;
    }

    /**
     * @param CWE05 the CWE05 to set
     */
    public void setCWE05(String CWE05) {
        this.CWE05 = CWE05;
    }

    /**
     * @return the CWE06
     */
    public String getCWE06() {
        return CWE06;
    }

    /**
     * @param CWE06 the CWE06 to set
     */
    public void setCWE06(String CWE06) {
        this.CWE06 = CWE06;
    }

    /**
     * CWEのセット
     */
    public void setCWE(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "CWE" + st_num;

        switch(target){
            case "CWE01":
                setCWE01(value);
                break;
            case "CWE02":
                setCWE02(value);
                break;
            case "CWE03":
                setCWE03(value);
                break;
            case "CWE04":
                setCWE04(value);
                break;
            case "CWE05":
                setCWE05(value);
                break;
            case "CWE06":
                setCWE06(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
    }
    
    /**
     * CWEのゲット
     */
    public String getCWE(int num){
        String result = null;
        
        switch(num){
            case 1:
                result = getCWE01();
                break;
            case 2:
                result = getCWE02();
                break;
            case 3:
                result = getCWE03();
                break;
            case 4:
                result = getCWE04();
                break;
            case 5:
                result = getCWE05();
                break;
            case 6:
                result = getCWE06();
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
        return result;
    }
}
