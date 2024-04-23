/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class DB1Dto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(DB1Dto.class);
    /**
     * セグメントID
     */
    private String DB100;
    /**
     * セット ID- DB1
     */
    private String DB101;
    /**
     * 身体障害情報
     */
    private String DB102;
    /**
     * 身体障害者 ID
     */
    private List<String> DB103;
    /**
     * 身体障害の有無
     */
    private String DB104;
    /**
     * 身体障害発生日
     */
    private String DB105;
    /**
     * 身体障害回復日
     */
    private String DB106;
    /**
     * 職場復帰日
     */
    private String DB107;
    /**
     * 職場離脱期間
     */
    private String DB108;

    /**
     * @return the DB100
     */
    public String getDB100() {
        return DB100;
    }

    /**
     * @param DB100 the DB100 to set
     */
    public void setDB100(String DB100) {
        this.DB100 = DB100;
    }

    /**
     * @return the DB101
     */
    public String getDB101() {
        return DB101;
    }

    /**
     * @param DB101 the DB101 to set
     */
    public void setDB101(String DB101) {
        this.DB101 = DB101;
    }

    /**
     * @return the DB102
     */
    public String getDB102() {
        return DB102;
    }

    /**
     * @param DB102 the DB102 to set
     */
    public void setDB102(String DB102) {
        this.DB102 = DB102;
    }

    /**
     * @return the DB103
     */
    public List<String> getDB103() {
        return DB103;
    }

    /**
     * @param DB103 the DB103 to set
     */
    public void setDB103(List<String> DB103) {
        this.DB103 = DB103;
    }

    /**
     * @return the DB104
     */
    public String getDB104() {
        return DB104;
    }

    /**
     * @param DB104 the DB104 to set
     */
    public void setDB104(String DB104) {
        this.DB104 = DB104;
    }

    /**
     * @return the DB105
     */
    public String getDB105() {
        return DB105;
    }

    /**
     * @param DB105 the DB105 to set
     */
    public void setDB105(String DB105) {
        this.DB105 = DB105;
    }

    /**
     * @return the DB106
     */
    public String getDB106() {
        return DB106;
    }

    /**
     * @param DB106 the DB106 to set
     */
    public void setDB106(String DB106) {
        this.DB106 = DB106;
    }

    /**
     * @return the DB107
     */
    public String getDB107() {
        return DB107;
    }

    /**
     * @param DB107 the DB107 to set
     */
    public void setDB107(String DB107) {
        this.DB107 = DB107;
    }

    /**
     * @return the DB108
     */
    public String getDB108() {
        return DB108;
    }

    /**
     * @param DB108 the DB108 to set
     */
    public void setDB108(String DB108) {
        this.DB108 = DB108;
    }

    /**
     * DB1のセット
     */
    public void setDB1(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "DB1" + st_num;

        switch(target){
            case "DB100":
                setDB100(value);
                break;
            case "DB101":
                setDB101(value);
                break;
            case "DB102":
                setDB102(value);
                break;
            case "DB103":
                List<String> db103List = new ArrayList<>(Arrays.asList(value.split(getRepeatSp())));
                setDB103(db103List);
                break;
            case "DB104":
                setDB104(value);
                break;
            case "DB105":
                setDB105(value);
                break;
            case "DB106":
                setDB106(value);
                break;
            case "DB107":
                setDB107(value);
                break;
            case "DB108":
                setDB108(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }

    }

    /**
     *  DB1のゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getDB1(Integer f_num , Integer e_num , Integer r_num){
        String result = null;

        switch(f_num){
            case 0:
                result = getDB100();
                break;
            case 1:
                result = getDB101();
                break;
            case 2:
                result = getDB102();
                break;
            case 3:
                result = getDB103().get(e_num);
                break;
            case 4:
                result = getDB104();
                break;
            case 5:
                result = getDB105();
                break;
            case 6:
                result = getDB106();
                break;
            case 7:
                result = getDB107();
                break;
            case 8:
                result = getDB108();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }

    /**
     *  DB1のゲット
     *
     * @param f_num フィールドNo　必須
     */
    public Object getDB1Ob(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getDB100();
                break;
            case 1:
                result = getDB101();
                break;
            case 2:
                result = getDB102();
                break;
            case 3:
                result = getDB103();
                break;
            case 4:
                result = getDB104();
                break;
            case 5:
                result = getDB105();
                break;
            case 6:
                result = getDB106();
                break;
            case 7:
                result = getDB107();
                break;
            case 8:
                result = getDB108();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }
}
