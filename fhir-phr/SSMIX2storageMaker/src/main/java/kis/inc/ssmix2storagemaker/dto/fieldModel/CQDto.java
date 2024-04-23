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
 * CQ型のDto
 */
public class CQDto extends baseFieldDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(CQDto.class);

    /**
     * @param subcomp 副成分区切り文字
     */
    public CQDto(String subcomp) {
    	this.setSubcompSp(subcomp);
    }

    /**
     * CQ型の成分１
     */
    private String CQ01;
    /**
     * CQ型の成分2
     */
    private CWEDto CQ02;

    /**
     * @return the CQ01
     */
    public String getCQ01() {
        return CQ01;
    }

    /**
     * @param CQ01 the CQ01 to set
     */
    public void setCQ01(String CQ01) {
        this.CQ01 = CQ01;
    }

    /**
     * @return the CQ02
     */
    public CWEDto getCQ02() {
        return CQ02;
    }

    /**
     * @param CQ02 the CQ02 to set
     */
    public void setCQ02(CWEDto CQ02) {
        this.CQ02 = CQ02;
    }

    /**
     * CQのセット
     */
    public void setCQ(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "CQ" + st_num;

        switch(target){
            case "CQ01":
                setCQ01(value);
                break;
            case "CQ02":
            	CWEDto cq02 = setCWE(value);
                setCQ02(cq02);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
    }

    /**
     * CQのゲット
     */
    public String getCQ(int num , Integer e_num){
        String result = null;

        switch(num){
            case 1:
                result = getCQ01();
                break;
            case 2:
            	result = getCWE(getCQ02() , e_num);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
        return result;
    }

    /**
     * CQのゲット
     */
    public Object getCQ(int num){
    	Object result = null;

        switch(num){
            case 1:
                result = getCQ01();
                break;
            case 2:
                result = getCQ02();
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
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
