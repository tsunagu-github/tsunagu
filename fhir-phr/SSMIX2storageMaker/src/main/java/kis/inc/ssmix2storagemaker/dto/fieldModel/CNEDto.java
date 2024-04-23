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
 * CNE型のDto
 */
public class CNEDto extends baseFieldDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(CNEDto.class);
    
    /**
     * CNE型の成分１
     */
    private String CNE01;
    /**
     * CNE型の成分2
     */
    private String CNE02;
    /**
     * CNE型の成分3
     */
    private String CNE03;
    /**
     * CNE型の成分4
     */
    private String CNE04;
    /**
     * CNE型の成分5
     */
    private String CNE05;
    /**
     * CNE型の成分6
     */
    private String CNE06;

    /**
     * @return the CNE01
     */
    public String getCNE01() {
        return CNE01;
    }

    /**
     * @param CNE01 the CNE01 to set
     */
    public void setCNE01(String CNE01) {
        this.CNE01 = CNE01;
    }

    /**
     * @return the CNE02
     */
    public String getCNE02() {
        return CNE02;
    }

    /**
     * @param CNE02 the CNE02 to set
     */
    public void setCNE02(String CNE02) {
        this.CNE02 = CNE02;
    }

    /**
     * @return the CNE03
     */
    public String getCNE03() {
        return CNE03;
    }

    /**
     * @param CNE03 the CNE03 to set
     */
    public void setCNE03(String CNE03) {
        this.CNE03 = CNE03;
    }

    /**
     * @return the CNE04
     */
    public String getCNE04() {
        return CNE04;
    }

    /**
     * @param CNE04 the CNE04 to set
     */
    public void setCNE04(String CNE04) {
        this.CNE04 = CNE04;
    }

    /**
     * @return the CNE05
     */
    public String getCNE05() {
        return CNE05;
    }

    /**
     * @param CNE05 the CNE05 to set
     */
    public void setCNE05(String CNE05) {
        this.CNE05 = CNE05;
    }

    /**
     * @return the CNE06
     */
    public String getCNE06() {
        return CNE06;
    }

    /**
     * @param CNE06 the CNE06 to set
     */
    public void setCNE06(String CNE06) {
        this.CNE06 = CNE06;
    }

    /**
     * CNEのセット
     */
    public void setCNE(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "CNE" + st_num;

        switch(target){
            case "CNE01":
                setCNE01(value);
                break;
            case "CNE02":
                setCNE02(value);
                break;
            case "CNE03":
                setCNE03(value);
                break;
            case "CNE04":
                setCNE04(value);
                break;
            case "CNE05":
                setCNE05(value);
                break;
            case "CNE06":
                setCNE06(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
    }
    
    /**
     * CNEのゲット
     */
    public String getCNE(int num){
        String result = null;
        
        switch(num){
            case 1:
                result = getCNE01();
                break;
            case 2:
                result = getCNE02();
                break;
            case 3:
                result = getCNE03();
                break;
            case 4:
                result = getCNE04();
                break;
            case 5:
                result = getCNE05();
                break;
            case 6:
                result = getCNE06();
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
        return result;
    }
}
