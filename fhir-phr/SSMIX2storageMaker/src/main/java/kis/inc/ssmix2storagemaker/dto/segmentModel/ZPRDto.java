/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class ZPRDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(ZPRDto.class);

    /**
     * セグメントID
     */
    private String ZPR00;
    /**
     * 修飾語管理番号（接頭語）
     */
    private CWEDto ZPR01;
    /**
     * 病名管理番号
     */
    private CWEDto ZPR02;
    /**
     * 修飾語管理番号（接尾語）
     */
    private CWEDto ZPR03;
    /**
     * 修飾語交換用コード（接頭語）
     */
    private CWEDto ZPR04;
    /**
     * 病名交換用コード
     */
    private CWEDto ZPR05;
    /**
     * 修飾語交換用コード（接尾語）
     */
    private CWEDto ZPR06;
    /**
     * コメント
     */
    private String ZPR07;

    /**
     * @return the ZPR00
     */
    public String getZPR00() {
        return ZPR00;
    }

    /**
     * @param ZPR00 the ZPR00 to set
     */
    public void setZPR00(String ZPR00) {
        this.ZPR00 = ZPR00;
    }

    /**
     * @return the ZPR01
     */
    public CWEDto getZPR01() {
        return ZPR01;
    }

    /**
     * @param ZPR01 the ZPR01 to set
     */
    public void setZPR01(CWEDto ZPR01) {
        this.ZPR01 = ZPR01;
    }

    /**
     * @return the ZPR02
     */
    public CWEDto getZPR02() {
        return ZPR02;
    }

    /**
     * @param ZPR02 the ZPR02 to set
     */
    public void setZPR02(CWEDto ZPR02) {
        this.ZPR02 = ZPR02;
    }

    /**
     * @return the ZPR03
     */
    public CWEDto getZPR03() {
        return ZPR03;
    }

    /**
     * @param ZPR03 the ZPR03 to set
     */
    public void setZPR03(CWEDto ZPR03) {
        this.ZPR03 = ZPR03;
    }

    /**
     * @return the ZPR04
     */
    public CWEDto getZPR04() {
        return ZPR04;
    }

    /**
     * @param ZPR04 the ZPR04 to set
     */
    public void setZPR04(CWEDto ZPR04) {
        this.ZPR04 = ZPR04;
    }

    /**
     * @return the ZPR05
     */
    public CWEDto getZPR05() {
        return ZPR05;
    }

    /**
     * @param ZPR05 the ZPR05 to set
     */
    public void setZPR05(CWEDto ZPR05) {
        this.ZPR05 = ZPR05;
    }

    /**
     * @return the ZPR06
     */
    public CWEDto getZPR06() {
        return ZPR06;
    }

    /**
     * @param ZPR06 the ZPR06 to set
     */
    public void setZPR06(CWEDto ZPR06) {
        this.ZPR06 = ZPR06;
    }

    /**
     * @return the ZPR07
     */
    public String getZPR07() {
        return ZPR07;
    }

    /**
     * @param ZPR07 the ZPR07 to set
     */
    public void setZPR07(String ZPR07) {
        this.ZPR07 = ZPR07;
    }
    
    /**
     * ZPRのセット
     */
    public void setZPR(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "ZPR" + st_num;

       switch(target){
            case "ZPR00":
                setZPR00(value);
                break;
            case "ZPR01":
                CWEDto zpr01_cwe = setCWE(value);
                setZPR01(zpr01_cwe);
                break;
            case "ZPR02":
                CWEDto zpr02_cwe = setCWE(value);
                setZPR02(zpr02_cwe);
                break;
            case "ZPR03":
                CWEDto zpr03_cwe = setCWE(value);
                setZPR03(zpr03_cwe);
                break;
            case "ZPR04":
                CWEDto zpr04_cwe = setCWE(value);
                setZPR04(zpr04_cwe);
                break;
            case "ZPR05":
                CWEDto zpr05_cwe = setCWE(value);
                setZPR05(zpr05_cwe);
                break;
            case "ZPR06":
                CWEDto zpr06_cwe = setCWE(value);
                setZPR06(zpr06_cwe);
                break;
            case "ZPR07":
                setZPR07(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
       }
   }
    
    /**
     *  ZPRのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getZPR(Integer f_num , Integer e_num , Integer r_num){
        String result = null;
        
        switch(f_num){
            case 0:
                result = getZPR00();
                break;
            case 1:
                result = getCWE(getZPR01() , e_num );
                break;
            case 2:
                result = getCWE(getZPR02() , e_num );
                break;
            case 3:
                result = getCWE(getZPR03() , e_num );
                break;
            case 4:
                result = getCWE(getZPR04() , e_num );
                break;
            case 5:
                result = getCWE(getZPR05() , e_num );
                break;
            case 6:
                result = getCWE(getZPR06() , e_num );
                break;
            case 7:
                result = getZPR07();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }

    /**
     *  ZPRのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public Object getZPROb(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getZPR00();
                break;
            case 1:
                result = getZPR01();
                break;
            case 2:
                result = getZPR02();
                break;
            case 3:
                result = getZPR03();
                break;
            case 4:
                result = getZPR04();
                break;
            case 5:
                result = getZPR05();
                break;
            case 6:
                result = getZPR06();
                break;
            case 7:
                result = getZPR07();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
}
