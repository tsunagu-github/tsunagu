/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.fieldModel;

import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class PLDto {
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(PV1Dto.class);
    /**
     * PL型の成分１
     */
    private String PL01;
    /**
     * PL型の成分２
     */
    private String PL02;
    /**
     * PL型の成分３
     */
    private String PL03;
    /**
     * PL型の成分4
     */
    private String PL04;
    /**
     * PL型の成分5
     */
    private String PL05;
    /**
     * PL型の成分６
     */
    private String PL06;

    /**
     * @return the PL01
     */
    public String getPL01() {
        return PL01;
    }

    /**
     * @param PL01 the PL01 to set
     */
    public void setPL01(String PL01) {
        this.PL01 = PL01;
    }

    /**
     * @return the PL02
     */
    public String getPL02() {
        return PL02;
    }

    /**
     * @param PL02 the PL02 to set
     */
    public void setPL02(String PL02) {
        this.PL02 = PL02;
    }

    /**
     * @return the PL03
     */
    public String getPL03() {
        return PL03;
    }

    /**
     * @param PL03 the PL03 to set
     */
    public void setPL03(String PL03) {
        this.PL03 = PL03;
    }

    /**
     * @return the PL04
     */
    public String getPL04() {
        return PL04;
    }

    /**
     * @param PL04 the PL04 to set
     */
    public void setPL04(String PL04) {
        this.PL04 = PL04;
    }

    /**
     * @return the PL05
     */
    public String getPL05() {
        return PL05;
    }

    /**
     * @param PL05 the PL05 to set
     */
    public void setPL05(String PL05) {
        this.PL05 = PL05;
    }

    /**
     * @return the PL06
     */
    public String getPL06() {
        return PL06;
    }

    /**
     * @param PL06 the PL06 to set
     */
    public void setPL06(String PL06) {
        this.PL06 = PL06;
    }
    
     /**
     * PLのセット
     */
    public void setPL(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "PL" + st_num;

        switch(target){
            case "PL01":
                setPL01(value);
                break;
            case "PL02":
                setPL02(value);
                break;
            case "PL03":
                setPL03(value);
                break;
            case "PL04":
                setPL04(value);
                break;
            case "PL05":
                setPL05(value);
                break;
            case "PL06":
                setPL06(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
    }
    
    /**
     * PLのゲット
     */
    public String getPL(int num){
        String result = null;
        
        switch(num){
            case 1:
                result = getPL01();
                break;
            case 2:
                result = getPL02();
                break;
            case 3:
                result = getPL03();
                break;
            case 4:
                result = getPL04();
                break;
            case 5:
                result = getPL05();
                break;
            case 6:
                result = getPL06();
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
        return result;
    }
}
