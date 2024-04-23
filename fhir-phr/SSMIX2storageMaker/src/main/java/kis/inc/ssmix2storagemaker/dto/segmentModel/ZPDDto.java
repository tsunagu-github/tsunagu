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
public class ZPDDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(ZPDDto.class);

    /**
     * セグメントID
     */
    private String ZPD00;
    /**
     * セットID-ZPD
     */
    private String ZPD01;
    /**
     * 歯式情報
     */
    private CWEDto ZPD02;

    /**
     * @return the ZPD00
     */
    public String getZPD00() {
        return ZPD00;
    }

    /**
     * @param ZPD00 the ZPD00 to set
     */
    public void setZPD00(String ZPD00) {
        this.ZPD00 = ZPD00;
    }

    /**
     * @return the ZPD01
     */
    public String getZPD01() {
        return ZPD01;
    }

    /**
     * @param ZPD01 the ZPD01 to set
     */
    public void setZPD01(String ZPD01) {
        this.ZPD01 = ZPD01;
    }

    /**
     * @return the ZPD02
     */
    public CWEDto getZPD02() {
        return ZPD02;
    }

    /**
     * @param ZPD02 the ZPD02 to set
     */
    public void setZPD02(CWEDto ZPD02) {
        this.ZPD02 = ZPD02;
    }
    
    /**
     * ZPDのセット
     */
    public void setZPD(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "ZPD" + st_num;

       switch(target){
            case "ZPD00":
                setZPD00(value);
                break;
            case "ZPD01":
                setZPD01(value);
                break;
            case "ZPD02":
                CWEDto zpd02_cwe = setCWE(value);
                setZPD02(zpd02_cwe);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
       }
   }
    
    /**
     *  ZPDのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getZPD(Integer f_num , Integer e_num , Integer r_num){
        String result = null;
        
        switch(f_num){
            case 0:
                result = getZPD00();
                break;
            case 1:
                result = getZPD01();
                break;
            case 2:
                result = getCWE(getZPD02() , e_num );
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }

    /**
     *  ZPDのゲット
     * 
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public Object getZPDOb(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getZPD00();
                break;
            case 1:
                result = getZPD01();
                break;
            case 2:
                result = getZPD02();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
}
