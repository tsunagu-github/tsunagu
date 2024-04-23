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
 */
public class LA2Dto extends baseFieldDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(LA2Dto.class);

    /**
     * LA2型の成分１
     */
    private String LA201;
    /**
     * LA2型の成分２
     */
    private String LA202;
    /**
     * LA2型の成分３
     */
    private String LA203;
    /**
     * LA2型の成分４
     */
    private String LA204;
    /**
     * LA2型の成分５
     */
    private String LA205;
    /**
     * LA2型の成分6
     */
    private String LA206;
    /**
     * LA2型の成分７
     */
    private String LA207;
    /**
     * LA2型の成分８
     */
    private String LA208;
    /**
     * LA2型の成分9
     */
    private String LA209;
    /**
     * LA2型の成分１０
     */
    private String LA210;
    /**
     * LA2型の成分１１
     */
    private String LA211;
    /**
     * LA2型の成分１２
     */
    private String LA212;
    /**
     * LA2型の成分１３
     */
    private String LA213;
    /**
     * LA2型の成分１４
     */
    private String LA214;
    /**
     * LA2型の成分１５
     */
    private String LA215;
    /**
     * LA2型の成分１６
     */
    private String LA216;

    /**
     * @return the LA201
     */
    public String getLA201() {
        return LA201;
    }

    /**
     * @param LA201 the LA201 to set
     */
    public void setLA201(String LA201) {
        this.LA201 = LA201;
    }

    /**
     * @return the LA202
     */
    public String getLA202() {
        return LA202;
    }

    /**
     * @param LA202 the LA202 to set
     */
    public void setLA202(String LA202) {
        this.LA202 = LA202;
    }

    /**
     * @return the LA203
     */
    public String getLA203() {
        return LA203;
    }

    /**
     * @param LA203 the LA203 to set
     */
    public void setLA203(String LA203) {
        this.LA203 = LA203;
    }

    /**
     * @return the LA204
     */
    public String getLA204() {
        return LA204;
    }

    /**
     * @param LA204 the LA204 to set
     */
    public void setLA204(String LA204) {
        this.LA204 = LA204;
    }

    /**
     * @return the LA205
     */
    public String getLA205() {
        return LA205;
    }

    /**
     * @param LA205 the LA205 to set
     */
    public void setLA205(String LA205) {
        this.LA205 = LA205;
    }

    /**
     * @return the LA206
     */
    public String getLA206() {
        return LA206;
    }

    /**
     * @param LA206 the LA206 to set
     */
    public void setLA206(String LA206) {
        this.LA206 = LA206;
    }

    /**
     * @return the LA207
     */
    public String getLA207() {
        return LA207;
    }

    /**
     * @param LA207 the LA207 to set
     */
    public void setLA207(String LA207) {
        this.LA207 = LA207;
    }

    /**
     * @return the LA208
     */
    public String getLA208() {
        return LA208;
    }

    /**
     * @param LA208 the LA208 to set
     */
    public void setLA208(String LA208) {
        this.LA208 = LA208;
    }

    /**
     * @return the LA209
     */
    public String getLA209() {
        return LA209;
    }

    /**
     * @param LA209 the LA209 to set
     */
    public void setLA209(String LA209) {
        this.LA209 = LA209;
    }

    /**
     * @return the LA210
     */
    public String getLA210() {
        return LA210;
    }

    /**
     * @param LA210 the LA210 to set
     */
    public void setLA210(String LA210) {
        this.LA210 = LA210;
    }

    /**
     * @return the LA211
     */
    public String getLA211() {
        return LA211;
    }

    /**
     * @param LA211 the LA211 to set
     */
    public void setLA211(String LA211) {
        this.LA211 = LA211;
    }

    /**
     * @return the LA212
     */
    public String getLA212() {
        return LA212;
    }

    /**
     * @param LA212 the LA212 to set
     */
    public void setLA212(String LA212) {
        this.LA212 = LA212;
    }

    /**
     * @return the LA213
     */
    public String getLA213() {
        return LA213;
    }

    /**
     * @param LA213 the LA213 to set
     */
    public void setLA213(String LA213) {
        this.LA213 = LA213;
    }

    /**
     * @return the LA214
     */
    public String getLA214() {
        return LA214;
    }

    /**
     * @param LA214 the LA214 to set
     */
    public void setLA214(String LA214) {
        this.LA214 = LA214;
    }

    /**
     * @return the LA215
     */
    public String getLA215() {
        return LA215;
    }

    /**
     * @param LA215 the LA215 to set
     */
    public void setLA215(String LA215) {
        this.LA215 = LA215;
    }

    /**
     * @return the LA216
     */
    public String getLA216() {
        return LA216;
    }

    /**
     * @param LA216 the LA216 to set
     */
    public void setLA216(String LA216) {
        this.LA216 = LA216;
    }

   /**
     * LA2のセット
     * @param value
     * @param num
     */
    public void setLA2(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "LA2" + st_num;

        switch(target){
            case "LA201":
                setLA201(value);
                break;
            case "LA202":
                setLA202(value);
                break;
            case "LA203":
                setLA203(value);
                break;
            case "LA204":
                setLA204(value);
                break;
            case "LA205":
                setLA205(value);
                break;
            case "LA206":
                setLA206(value);
                break;
            case "LA207":
                setLA207(value);
                break;
            case "LA208":
                setLA208(value);
                break;
            case "LA209":
                setLA209(value);
                break;
            case "LA210":
                setLA210(value);
                break;
            case "LA211":
                setLA211(value);
                break;
            case "LA212":
                setLA212(value);
                break;
            case "LA213":
                setLA213(value);
                break;
            case "LA214":
                setLA214(value);
                break;
            case "LA215":
                setLA215(value);
                break;
            case "LA216":
                setLA216(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
    }

    /**
     * LA2のゲット
     */
    public String getLA2(int num){
        String result = null;

        switch(num){
            case 1:
                result = getLA201();
                break;
            case 2:
                result = getLA202();
                break;
            case 3:
                result = getLA203();
                break;
            case 4:
                result = getLA204();
                break;
            case 5:
                result = getLA205();
                break;
            case 6:
                result = getLA206();
                break;
            case 7:
                result = getLA207();
                break;
            case 8:
                result = getLA208();
                break;
            case 9:
                result = getLA209();
                break;
            case 10:
                result = getLA210();
                break;
            case 11:
                result = getLA211();
                break;
            case 12:
                result = getLA212();
                break;
            case 13:
                result = getLA213();
                break;
            case 14:
                result = getLA214();
                break;
            case 15:
                result = getLA215();
                break;
            case 16:
                result = getLA216();
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }
        return result;
    }
}
