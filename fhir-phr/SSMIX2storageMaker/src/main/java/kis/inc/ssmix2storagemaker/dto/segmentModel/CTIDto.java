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
public class CTIDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(CTIDto.class);
    /**
     * セグメントID
     */
    private String CTI00;
    /**
     * 後援者試験識別子
     */
    private String CTI01;
    /**
     * 試験相識別子
     */
    private CWEDto CTI02;
    /**
     * 試験予定時間ポイント
     */
    private CWEDto CTI03;

    /**
     * @return the CTI00
     */
    public String getCTI00() {
        return CTI00;
    }

    /**
     * @param CTI00 the CTI00 to set
     */
    public void setCTI00(String CTI00) {
        this.CTI00 = CTI00;
    }

    /**
     * @return the CTI01
     */
    public String getCTI01() {
        return CTI01;
    }

    /**
     * @param CTI01 the CTI01 to set
     */
    public void setCTI01(String CTI01) {
        this.CTI01 = CTI01;
    }

    /**
     * @return the CTI02
     */
    public CWEDto getCTI02() {
        return CTI02;
    }

    /**
     * @param CTI02 the CTI02 to set
     */
    public void setCTI02(CWEDto CTI02) {
        this.CTI02 = CTI02;
    }

    /**
     * @return the CTI03
     */
    public CWEDto getCTI03() {
        return CTI03;
    }

    /**
     * @param CTI03 the CTI03 to set
     */
    public void setCTI03(CWEDto CTI03) {
        this.CTI03 = CTI03;
    }

    /**
     * CTIのセット
     */
    public void setCTI(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "CTI" + st_num;

        switch(target){
            case "CTI00":
                setCTI00(value);
                break;
            case "CTI01":
                setCTI01(value);
                break;
            case "CTI02":
                CWEDto cti02 = setCWE(value);
                setCTI02(cti02);
                break;
            case "CTI03":
                CWEDto cti03 = setCWE(value);
                setCTI03(cti03);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }

    }

    /**
     *  CTIのゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getCTI(Integer f_num , Integer e_num , Integer r_num){
        String result = null;

        switch(f_num){
            case 0:
                result = getCTI00();
                break;
            case 1:
                result = getCTI01();
                break;
            case 2:
                result = getCWE(getCTI02() , e_num );
                break;
            case 3:
                result = getCWE(getCTI03() , e_num );
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }

    /**
     *  CTIのゲット
     *
     * @param f_num フィールドNo　必須
     */
    public Object getCTIOb(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getCTI00();
                break;
            case 1:
                result = getCTI01();
                break;
            case 2:
                result = getCTI02();
                break;
            case 3:
                result = getCTI03();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }
}
