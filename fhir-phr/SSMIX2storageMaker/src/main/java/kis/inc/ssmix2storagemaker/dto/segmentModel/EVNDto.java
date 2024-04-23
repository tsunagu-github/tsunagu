/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class EVNDto extends baseSegmentDto{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(EVNDto.class);

    /**
     * セグメントID
     */
    private String EVN00;
    
    /**
     * 事象型コード
     */
    private String EVN01;

    /**
     * 事象記録日時
     */
    private String EVN02;

    /**
     * 事象計画日時
     */
    private String EVN03;

    /**
     * 事象理由コード
     */
    private String EVN04;

    /**
     * 操作ID
     */
    private List<XCNDto> EVN05;

    /**
     * 事象発生日時
     */
    private String EVN06;
    
    /**
     * 事象施設
     */
    private String EVN07;

    /**
     * @return the EVN00
     */
    public String getEVN00() {
        return EVN00;
    }

    /**
     * @param EVN00 the EVN00 to set
     */
    public void setEVN00(String EVN00) {
        this.EVN00 = EVN00;
    }

    /**
     * @return the EBN01
     */
    public String getEVN01() {
        return EVN01;
    }

    /**
     * @param EBN01 the EBN01 to set
     */
    public void setEVN01(String EVN01) {
        this.EVN01 = EVN01;
    }

    /**
     * @return the EVN02
     */
    public String getEVN02() {
        return EVN02;
    }

    /**
     * @param EVN02 the EVN02 to set
     */
    public void setEVN02(String EVN02) {
        this.EVN02 = EVN02;
    }

    /**
     * @return the EVN03
     */
    public String getEVN03() {
        return EVN03;
    }

    /**
     * @param EVN03 the EVN03 to set
     */
    public void setEVN03(String EVN03) {
        this.EVN03 = EVN03;
    }

    /**
     * @return the EVN04
     */
    public String getEVN04() {
        return EVN04;
    }

    /**
     * @param EVN04 the EVN04 to set
     */
    public void setEVN04(String EVN04) {
        this.EVN04 = EVN04;
    }

    /**
     * @return the EVN05
     */
    public List<XCNDto> getEVN05() {
        return EVN05;
    }

    /**
     * @param EVN05 the EVN05 to set
     */
    public void setEVN05(List<XCNDto> EVN05) {
        this.EVN05 = EVN05;
    }

    /**
     * @return the EVN06
     */
    public String getEVN06() {
        return EVN06;
    }

    /**
     * @param EVN06 the EVN06 to set
     */
    public void setEVN06(String EVN06) {
        this.EVN06 = EVN06;
    }

    /**
     * @return the EVN07
     */
    public String getEVN07() {
        return EVN07;
    }

    /**
     * @param EVN07 the EVN07 to set
     */
    public void setEVN07(String EVN07) {
        this.EVN07 = EVN07;
    }
    
     /**
     * 番号からセット
     * ファイルから取得したときは配列にいれるのでそこのcaseを想定する
     * @param value 保存値
     * @param num　対象Number
     */
    public void setEVN(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "EVN" + st_num;
        
        switch(target){
            case "EVN00":
                setEVN00(value);
                break;
            case "EVN01":
                setEVN01(value);
                break;
            case "EVN02":
                setEVN02(value);
                break;
            case "EVN03":
                setEVN03(value);
                break;
            case "EVN04":
                setEVN04(value);
                break;
            case "EVN05":
                List<XCNDto> evn05_xcn = setXCN(value);
                setEVN05(evn05_xcn);
                break;
            case "EVN06":
                setEVN06(value);
                break;
            case "EVN07":
                setEVN07(value);
                break;
            default :
                logger.debug("未定義の数値がきています " + num);
        }
    }
    
     /**
     * 番号からゲット
     * ファイルから取得したときは配列にいれるのでそこのcaseを想定する
     */
    public String getEVN(int num , Integer e_num , Integer r_num){
        String target = null;
        
        switch(num){
            case 0:
                target = getEVN00();
                break;
            case 1:
                target = getEVN01();
                break;
            case 2:
                target = getEVN02();
                break;
            case 3:
                target = getEVN03();
                break;
            case 4:
                target = getEVN04();
                break;
            case 5:
                target = getXCN(getEVN05() , e_num , r_num);
                break;
            case 6:
                target = getEVN06();
                break;
            case 7:
                target = getEVN07();
                break;
            default :
                logger.debug("未定義の数値がきています " + num);
        }
        
        return target;
    }
    
    /**
     *  EVNのゲット
     * 
     * @param f_num フィールドNo　必須
     */
    public Object getEVNOb(Integer f_num ){
        Object result = null;
        
        switch(f_num){
            case 0:
                result = getEVN00();
                break;
            case 1:
                result = getEVN01();
                break;
            case 2:
                result = getEVN02();
                break;
            case 3:
                result = getEVN03();
                break;
            case 4:
                result = getEVN04();
                break;
            case 5:
                result = getEVN05();
                break;
            case 6:
                result = getEVN06();
                break;
            case 7:
                result = getEVN07();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }     
}
