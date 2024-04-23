/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.fieldModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.utility.StringUtility;

/**
 *
 * @author kis-note
 * RPT型のDto
 */
public class RPTDto extends baseFieldDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(RPTDto.class);

    /**
     * @param subcomp 副成分区切り文字
     */
    public RPTDto(String subcomp) {
    	this.setSubcompSp(subcomp);
    }

    /**
     * RPT型の成分1
     *
     */
    private CWEDto RPT01;
    /**
     * RPT型の成分2
     */
    private String RPT02;
    /**
     * RPT型の成分3
     */
    private String RPT03;
    /**
     * RPT型の成分4
     *
     */
    private String RPT04;
    /**
     * RPT型の成分5
     *
     */
    private String RPT05;
    /**
     * RPT型の成分6
     *
     */
    private String RPT06;
    /**
     * RPT型の成分7
     *
     */
    private String RPT07;
    /**
     * RPT型の成分8
     *
     */
    private String RPT08;
    /**
     * RPT型の成分9
     *
     */
    private String RPT09;
    /**
     * RPT型の成分10
     *
     */
    private String RPT10;
    /**
     * RPT型の成分11
     *
     */
    private String RPT11;

    /**
     * @return the RPT01
     */
    public CWEDto getRPT01() {
        return RPT01;
    }

    /**
     * @param RPT01 the RPT01 to set
     */
    public void setRPT01(CWEDto RPT01) {
        this.RPT01 = RPT01;
    }

    /**
     * @return the RPT02
     */
    public String getRPT02() {
        return RPT02;
    }

    /**
     * @param RPT02 the RPT02 to set
     */
    public void setRPT02(String RPT02) {
        this.RPT02 = RPT02;
    }

    /**
     * @return the RPT03
     */
    public String getRPT03() {
        return RPT03;
    }

    /**
     * @param RPT03 the RPT03 to set
     */
    public void setRPT03(String RPT03) {
        this.RPT03 = RPT03;
    }

    /**
     * @return the RPT04
     */
    public String getRPT04() {
        return RPT04;
    }

    /**
     * @param RPT04 the RPT04 to set
     */
    public void setRPT04(String RPT04) {
        this.RPT04 = RPT04;
    }

    /**
     * @return the RPT05
     */
    public String getRPT05() {
        return RPT05;
    }

    /**
     * @param RPT05 the RPT05 to set
     */
    public void setRPT05(String RPT05) {
        this.RPT05 = RPT05;
    }

    /**
     * @return the RPT06
     */
    public String getRPT06() {
        return RPT06;
    }

    /**
     * @param RPT06 the RPT06 to set
     */
    public void setRPT06(String RPT06) {
        this.RPT06 = RPT06;
    }

    /**
     * @return the RPT07
     */
    public String getRPT07() {
        return RPT07;
    }

    /**
     * @param RPT07 the RPT07 to set
     */
    public void setRPT07(String RPT07) {
        this.RPT07 = RPT07;
    }

    /**
     * @return the RPT08
     */
    public String getRPT08() {
        return RPT08;
    }

    /**
     * @param RPT08 the RPT08 to set
     */
    public void setRPT08(String RPT08) {
        this.RPT08 = RPT08;
    }

    /**
     * @return the RPT09
     */
    public String getRPT09() {
        return RPT09;
    }

    /**
     * @param RPT09 the RPT09 to set
     */
    public void setRPT09(String RPT09) {
        this.RPT09 = RPT09;
    }

    /**
     * @return the RPT10
     */
    public String getRPT10() {
        return RPT10;
    }

    /**
     * @param RPT10 the RPT10 to set
     */
    public void setRPT10(String RPT10) {
        this.RPT10 = RPT10;
    }

    /**
     * @return the RPT11
     */
    public String getRPT11() {
        return RPT11;
    }

    /**
     * @param RPT11 the RPT11 to set
     */
    public void setRPT11(String RPT11) {
        this.RPT11 = RPT11;
    }

    /**
     * RPT型にセット
     */
    public void setRPT(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "RPT" + st_num;

        switch(target){
            case "RPT01":
            	CWEDto rpt01 = setCWE(value);
                setRPT01(rpt01);
                break;
            case "RPT02":
                setRPT02(value);
                break;
            case "RPT03":
                setRPT03(value);
                break;
            case "RPT04":
                setRPT04(value);
                break;
            case "RPT05":
                setRPT05(value);
                break;
            case "RPT06":
                setRPT06(value);
                break;
            case "RPT07":
                setRPT07(value);
                break;
            case "RPT08":
                setRPT08(value);
                break;
            case "RPT09":
                setRPT09(value);
                break;
            case "RPT10":
                setRPT10(value);
                break;
            case "RPT11":
                setRPT11(value);
                break;
            default:
                logger.debug("指定番号は対応していません "+ num);
        }
    }

    /**
    * 番号からゲット
    */
   public String getRPT(int num , Integer e_num){
       String target = null;

       switch(num){
           case 1:
        	   target = getCWE(getRPT01() , e_num);
        	   break;
           case 2:
        	   target = getRPT02();
        	   break;
           case 3:
        	   target = getRPT03();
        	   break;
           case 4:
        	   target = getRPT04();
        	   break;
           case 5:
        	   target = getRPT05();
        	   break;
           case 6:
        	   target = getRPT06();
        	   break;
           case 7:
        	   target = getRPT07();
        	   break;
           case 8:
        	   target = getRPT08();
        	   break;
           case 9:
        	   target = getRPT09();
    	       break;
           case 10:
        	   target = getRPT10();
    	       break;
           case 11:
        	   target = getRPT11();
        	   break;
           default :
               logger.debug("未定義の数値がきています " + num);
       }

       return target;
   }

    /**
     * RPTのゲット
     */
    public Object getRPT(int num){
        Object result = null;

        switch(num){
            case 1:
                result = getRPT01();
                break;
            case 2:
                result = getRPT02();
                break;
            case 3:
                result = getRPT03();
                break;
            case 4:
                result = getRPT04();
                break;
            case 5:
                result = getRPT05();
                break;
            case 6:
                result = getRPT06();
                break;
            case 7:
                result = getRPT07();
                break;
            case 8:
                result = getRPT08();
                break;
            case 9:
                result = getRPT09();
                break;
            case 10:
                result = getRPT10();
                break;
            case 11:
                result = getRPT11();
                break;
            default:
                logger.debug("指定番号は対応していません "+ num);
        }

        return result;
    }

    /**
    * CWE型のセット処理
    * @param value
    * @return
    */
   private CWEDto setCWE(String value){
       if(StringUtility.isNullOrWhiteSpace(value)){
           return null;
       }
       String[] elements = value.split(this.getSubcompSp());
       CWEDto dto = new CWEDto();

       for(int m=0; m<elements.length;m++){
           dto.setCWE(elements[m], m+1);
       }

       return dto;
   }

   /**
    * CWE型のゲット処理
    * @param target
    * @param e_num
    * @return
    */
   public String getCWE(CWEDto target , Integer e_num){
       String result = target.getCWE(e_num);

       return result;
   }
}
