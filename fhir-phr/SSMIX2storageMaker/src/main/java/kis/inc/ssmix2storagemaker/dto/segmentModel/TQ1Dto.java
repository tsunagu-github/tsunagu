/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CQDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.RPTDto;

/**
 *
 * @author kis-note
 */
public class TQ1Dto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(TQ1Dto.class);
    /**
     * セグメントID
     */
    private String TQ100;
    /**
     * セット ID - TQ1
     */
    private String TQ101;
    /**
     * 数量
     */
    private CQDto TQ102;
    /**
     * 繰返しパターン
     */
    private List<RPTDto> TQ103;
    /**
     * 明示的な時間
     */
    private String TQ104;
    /**
     * 相対時間/単位
     */
    private String TQ105;
    /**
     * サービス時間
     */
    private CQDto TQ106;
    /**
     * 開始日時
     */
    private String TQ107;
    /**
     * 終了日時
     */
    private String TQ108;
    /**
     * 優先度
     */
    private List<CWEDto> TQ109;
    /**
     * 条件テキスト
     */
    private String TQ110;
    /**
     * テキスト指令
     */
    private String TQ111;
    /**
     * 連結
     */
    private String TQ112;
    /**
     * 事象継続期間
     */
    private String TQ113;
    /**
     * 事象総数
     */
    private String TQ114;

    /**
     * @return the TQ100
     */
    public String getTQ100() {
        return TQ100;
    }

    /**
     * @param TQ100 the TQ100 to set
     */
    public void setTQ100(String TQ100) {
        this.TQ100 = TQ100;
    }

    /**
     * @return the TQ101
     */
    public String getTQ101() {
        return TQ101;
    }

    /**
     * @param TQ101 the TQ101 to set
     */
    public void setTQ101(String TQ101) {
        this.TQ101 = TQ101;
    }

    /**
     * @return the TQ102
     */
    public CQDto getTQ102() {
        return TQ102;
    }

    /**
     * @param TQ102 the TQ102 to set
     */
    public void setTQ102(CQDto TQ102) {
        this.TQ102 = TQ102;
    }

    /**
     * @return the TQ103
     */
    public List<RPTDto> getTQ103() {
        return TQ103;
    }

    /**
     * @param TQ103 the TQ103 to set
     */
    public void setTQ103(List<RPTDto> TQ103) {
        this.TQ103 = TQ103;
    }

    /**
     * @return the TQ104
     */
    public String getTQ104() {
        return TQ104;
    }

    /**
     * @param TQ104 the TQ104 to set
     */
    public void setTQ104(String TQ104) {
        this.TQ104 = TQ104;
    }

    /**
     * @return the TQ105
     */
    public String getTQ105() {
        return TQ105;
    }

    /**
     * @param TQ105 the TQ105 to set
     */
    public void setTQ105(String TQ105) {
        this.TQ105 = TQ105;
    }

    /**
     * @return the TQ106
     */
    public CQDto getTQ106() {
        return TQ106;
    }

    /**
     * @param TQ106 the TQ106 to set
     */
    public void setTQ106(CQDto TQ106) {
        this.TQ106 = TQ106;
    }

    /**
     * @return the TQ107
     */
    public String getTQ107() {
        return TQ107;
    }

    /**
     * @param TQ107 the TQ107 to set
     */
    public void setTQ107(String TQ107) {
        this.TQ107 = TQ107;
    }

    /**
     * @return the TQ108
     */
    public String getTQ108() {
        return TQ108;
    }

    /**
     * @param TQ108 the TQ108 to set
     */
    public void setTQ108(String TQ108) {
        this.TQ108 = TQ108;
    }

    /**
     * @return the TQ109
     */
    public  List<CWEDto> getTQ109() {
        return TQ109;
    }

    /**
     * @param TQ109 the TQ109 to set
     */
    public void setTQ109(List<CWEDto> TQ109) {
        this.TQ109 = TQ109;
    }

    /**
     * @return the TQ110
     */
    public String getTQ110() {
        return TQ110;
    }

    /**
     * @param TQ110 the TQ110 to set
     */
    public void setTQ110(String TQ110) {
        this.TQ110 = TQ110;
    }

    /**
     * @return the TQ111
     */
    public String getTQ111() {
        return TQ111;
    }

    /**
     * @param TQ111 the TQ111 to set
     */
    public void setTQ111(String TQ111) {
        this.TQ111 = TQ111;
    }

    /**
     * @return the TQ112
     */
    public String getTQ112() {
        return TQ112;
    }

    /**
     * @param TQ112 the TQ112 to set
     */
    public void setTQ112(String TQ112) {
        this.TQ112 = TQ112;
    }

    /**
     * @return the TQ113
     */
    public String getTQ113() {
        return TQ113;
    }

    /**
     * @param TQ113 the TQ113 to set
     */
    public void setTQ113(String TQ113) {
        this.TQ113 = TQ113;
    }

    /**
     * @return the TQ114
     */
    public String getTQ114() {
        return TQ114;
    }

    /**
     * @param TQ114 the TQ114 to set
     */
    public void setTQ114(String TQ114) {
        this.TQ114 = TQ114;
    }

    /**
     * TQ1のセット
     */
    public void setTQ1(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "TQ1" + st_num;

        switch(target){
            case "TQ100":
                setTQ100(value);
                break;
            case "TQ101":
                setTQ101(value);
                break;
            case "TQ102":
            	CQDto tq102 = setCQ(value);
                setTQ102(tq102);
                break;
            case "TQ103":
                List<RPTDto> tq103List = setRPTList(value);
                setTQ103(tq103List);
                break;
            case "TQ104":
                setTQ104(value);
                break;
            case "TQ105":
                setTQ105(value);
                break;
            case "TQ106":
            	CQDto tq106 = setCQ(value);
                setTQ106(tq106);
                break;
            case "TQ107":
                setTQ107(value);
                break;
            case "TQ108":
                setTQ108(value);
                break;
            case "TQ109":
            	List<CWEDto> tq109List = setCWEList(value);
                setTQ109(tq109List);
                break;
            case "TQ110":
                setTQ110(value);
                break;
            case "TQ111":
                setTQ111(value);
                break;
            case "TQ112":
                setTQ112(value);
                break;
            case "TQ113":
                setTQ113(value);
                break;
            case "TQ114":
                setTQ114(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }

    }

    /**
     *  TQ1のゲット
     *
     * @param f_num フィールドNo　必須
     */
    public Object getTQ1Ob(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getTQ100();
                break;
            case 1:
                result = getTQ101();
                break;
            case 2:
                result = getTQ102();
                break;
            case 3:
                result = getTQ103();
                break;
            case 4:
                result = getTQ104();
                break;
            case 5:
                result = getTQ105();
                break;
            case 6:
                result = getTQ106();
                break;
            case 7:
                result = getTQ107();
                break;
            case 8:
                result = getTQ108();
                break;
            case 9:
                result = getTQ109();
                break;
            case 10:
                result = getTQ110();
                break;
            case 11:
                result = getTQ111();
                break;
            case 12:
                result = getTQ112();
                break;
            case 13:
                result = getTQ113();
                break;
            case 14:
                result = getTQ114();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }
}
