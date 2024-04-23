/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;
import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class AL1Dto  extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AL1Dto.class);

    /**
     * セグメントID
     */
    private String AL100;    
    /**
     * セットID-AL1
     */
    private String AL101;    
    /**
     * アレルゲン分類
     */
    private CWEDto AL102;    
    /**
     * アレルゲン情報
     */
    private CWEDto AL103;    
    /**
     * アレルギー重症度
     */
    private CWEDto AL104;    
    /**
     * アレルギー反応情報
     */
    private String AL105;    
    /**
     * 判定日
     */
    private String AL106;    

    /**
     * @return the AL100
     */
    public String getAL100() {
        return AL100;
    }

    /**
     * @param AL100 the AL100 to set
     */
    public void setAL100(String AL100) {
        this.AL100 = AL100;
    }

    /**
     * @return the AL101
     */
    public String getAL101() {
        return AL101;
    }

    /**
     * @param AL101 the AL101 to set
     */
    public void setAL101(String AL101) {
        this.AL101 = AL101;
    }

    /**
     * @return the AL102
     */
    public CWEDto getAL102() {
        return AL102;
    }

    /**
     * @param AL102 the AL102 to set
     */
    public void setAL102(CWEDto AL102) {
        this.AL102 = AL102;
    }

    /**
     * @return the AL103
     */
    public CWEDto getAL103() {
        return AL103;
    }

    /**
     * @param AL103 the AL103 to set
     */
    public void setAL103(CWEDto AL103) {
        this.AL103 = AL103;
    }

    /**
     * @return the AL104
     */
    public CWEDto getAL104() {
        return AL104;
    }

    /**
     * @param AL104 the AL104 to set
     */
    public void setAL104(CWEDto AL104) {
        this.AL104 = AL104;
    }

    /**
     * @return the AL105
     */
    public String getAL105() {
        return AL105;
    }

    /**
     * @param AL105 the AL105 to set
     */
    public void setAL105(String AL105) {
        this.AL105 = AL105;
    }

    /**
     * @return the AL106
     */
    public String getAL106() {
        return AL106;
    }

    /**
     * @param AL106 the AL106 to set
     */
    public void setAL106(String AL106) {
        this.AL106 = AL106;
    }
    
     /**
     * 番号からセット
     * ファイルから取得したときは配列にいれるのでそこのcaseを想定する
     * @param value 保存値
     * @param num　対象Number
     */
    public void setAL1(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "AL1" + st_num;
        
        switch(target){
            case "AL100":
                setAL100(value);
                break;
            case "AL101":
                setAL101(value);
                break;
            case "AL102":
                CWEDto al102_cwe = setCWE(value);
                setAL102(al102_cwe);
                break;
            case "AL103":
                CWEDto al103_cwe = setCWE(value);
                setAL103(al103_cwe);
                break;
            case "AL104":
                CWEDto al104_cwe = setCWE(value);
                setAL104(al104_cwe);
                break;
            case "AL105":
                setAL105(value);
                break;
            case "AL106":
                setAL106(value);
                break;
            default :
                logger.debug("未定義の数値がきています " + num);
        }
    }
    
     /**
     * 番号からゲット
     * ファイルから取得したときは配列にいれるのでそこのcaseを想定する
     */
    public String getAL1(int num , Integer e_num , Integer r_num){
        String target = null;
        
        switch(num){
            case 0:
                target = getAL100();
                break;
            case 1:
                target = getAL101();
                break;
            case 2:
                target = getCWE(getAL102() , e_num);
                break;
            case 3:
                target = getCWE(getAL103() , e_num);
                break;
            case 4:
                target = getCWE(getAL104() , e_num);
                break;
            case 5:
                target = getAL105();
                break;
            case 6:
                target = getAL106();
                break;
            default :
                logger.debug("未定義の数値がきています " + num);
        }
        
        return target;
    }
    
    /**
     *  AL1のゲット
     * 
     * @param f_num フィールドNo　必須
     */
    public Object getAL1Ob(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getAL100();
                break;
            case 1:
                result = getAL101();
                break;
            case 2:
                result = getAL102();
                break;
            case 3:
                result = getAL103();
                break;
            case 4:
                result = getAL104();
                break;
            case 5:
                result = getAL105();
                break;
            case 6:
                result = getAL106();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }     
}
