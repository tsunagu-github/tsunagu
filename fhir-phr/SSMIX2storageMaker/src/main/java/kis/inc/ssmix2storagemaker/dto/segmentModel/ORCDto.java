/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.PLDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XADDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XTNDto;

/**
 *
 * @author kis-note
 */
public class ORCDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(ORCDto.class);

    /**
     * セグメントID
     */
    private String ORC00;
    /**
     * オーダ制御
     */
    private String ORC01;
    /**
     * 依頼者オーダ番号
     */
    private String ORC02;
    /**
     * 実施者オーダ番号
     */
    private String ORC03;
    /**
     * 依頼者グループ番号
     */
    private String ORC04;
    /**
     * オーダ状態
     */
    private String ORC05;
    /**
     * 応答フラグ
     */
    private String ORC06;
    /**
     * 数量/タイミング
     */
    private String ORC07;
    /**
     * 親
     */
    private String ORC08;
    /**
     * トランザクション日時
     */
    private String ORC09;
    /**
     * 入力者
     */
    private List<XCNDto> ORC10;
    /**
     * 検証者
     */
    private List<XCNDto> ORC11;
    /**
     * 依頼者
     */
    private List<XCNDto> ORC12;
    /**
     * 入力場所
     */
    private PLDto ORC13;
    /**
     * コールバック用電話番号
     */
    private XTNDto ORC14;
    /**
     * オーダ有効日時
     */
    private String ORC15;
    /**
     * オーダ制御コードの理由
     */
    private CWEDto ORC16;
    /**
     * 入力組織
     */
    private CWEDto ORC17;
    /**
     * 入力装置
     */
    private CWEDto ORC18;
    /**
     * 実施者
     */
    private List<XCNDto> ORC19;
    /**
     * 樹液者事前通知コード
     */
    private CWEDto ORC20;
    /**
     * オーダ施設名
     */
    private String ORC21;
    /**
     * オーダ施設住所
     */
    private XADDto ORC22;
    /**
     * オーダ施設電話番号
     */
    private XTNDto ORC23;
    /**
     * オーダ依頼者住所
     */
    private XADDto ORC24;
    /**
     * オーダ状態修飾子
     */
    private CWEDto ORC25;
    /**
     * 受益者事前通知無効理由
     */
    private CWEDto ORC26;
    /**
     * 実施者可能日時
     */
    private String ORC27;
    /**
     * 守秘コード
     */
    private CWEDto ORC28;
    /**
     *　オーダタイプ
     */
    private CWEDto ORC29;
    /**
     * 入力者承認モード
     */
    private String ORC30;

    /**
     * @return the ORC00
     */
    public String getORC00() {
        return ORC00;
    }

    /**
     * @param ORC00 the ORC00 to set
     */
    public void setORC00(String ORC00) {
        this.ORC00 = ORC00;
    }

    /**
     * @return the ORC01
     */
    public String getORC01() {
        return ORC01;
    }

    /**
     * @param ORC01 the ORC01 to set
     */
    public void setORC01(String ORC01) {
        this.ORC01 = ORC01;
    }

    /**
     * @return the ORC02
     */
    public String getORC02() {
        return ORC02;
    }

    /**
     * @param ORC02 the ORC02 to set
     */
    public void setORC02(String ORC02) {
        this.ORC02 = ORC02;
    }

    /**
     * @return the ORC03
     */
    public String getORC03() {
        return ORC03;
    }

    /**
     * @param ORC03 the ORC03 to set
     */
    public void setORC03(String ORC03) {
        this.ORC03 = ORC03;
    }

    /**
     * @return the ORC04
     */
    public String getORC04() {
        return ORC04;
    }

    /**
     * @param ORC04 the ORC04 to set
     */
    public void setORC04(String ORC04) {
        this.ORC04 = ORC04;
    }

    /**
     * @return the ORC05
     */
    public String getORC05() {
        return ORC05;
    }

    /**
     * @param ORC05 the ORC05 to set
     */
    public void setORC05(String ORC05) {
        this.ORC05 = ORC05;
    }

    /**
     * @return the ORC06
     */
    public String getORC06() {
        return ORC06;
    }

    /**
     * @param ORC06 the ORC06 to set
     */
    public void setORC06(String ORC06) {
        this.ORC06 = ORC06;
    }

    /**
     * @return the ORC07
     */
    public String getORC07() {
        return ORC07;
    }

    /**
     * @param ORC07 the ORC07 to set
     */
    public void setORC07(String ORC07) {
        this.ORC07 = ORC07;
    }

    /**
     * @return the ORC08
     */
    public String getORC08() {
        return ORC08;
    }

    /**
     * @param ORC08 the ORC08 to set
     */
    public void setORC08(String ORC08) {
        this.ORC08 = ORC08;
    }

    /**
     * @return the ORC09
     */
    public String getORC09() {
        return ORC09;
    }

    /**
     * @param ORC09 the ORC09 to set
     */
    public void setORC09(String ORC09) {
        this.ORC09 = ORC09;
    }

    /**
     * @return the ORC10
     */
    public List<XCNDto> getORC10() {
        return ORC10;
    }

    /**
     * @param ORC10 the ORC10 to set
     */
    public void setORC10(List<XCNDto> ORC10) {
        this.ORC10 = ORC10;
    }

    /**
     * @return the ORC11
     */
    public List<XCNDto> getORC11() {
        return ORC11;
    }

    /**
     * @param ORC11 the ORC11 to set
     */
    public void setORC11(List<XCNDto> ORC11) {
        this.ORC11 = ORC11;
    }

    /**
     * @return the ORC12
     */
    public List<XCNDto> getORC12() {
        return ORC12;
    }

    /**
     * @param ORC12 the ORC12 to set
     */
    public void setORC12(List<XCNDto> ORC12) {
        this.ORC12 = ORC12;
    }

    /**
     * @return the ORC13
     */
    public PLDto getORC13() {
        return ORC13;
    }

    /**
     * @param ORC13 the ORC13 to set
     */
    public void setORC13(PLDto ORC13) {
        this.ORC13 = ORC13;
    }

    /**
     * @return the ORC14
     */
    public XTNDto getORC14() {
        return ORC14;
    }

    /**
     * @param ORC14 the ORC14 to set
     */
    public void setORC14(XTNDto ORC14) {
        this.ORC14 = ORC14;
    }

    /**
     * @return the ORC15
     */
    public String getORC15() {
        return ORC15;
    }

    /**
     * @param ORC15 the ORC15 to set
     */
    public void setORC15(String ORC15) {
        this.ORC15 = ORC15;
    }

    /**
     * @return the ORC16
     */
    public CWEDto getORC16() {
        return ORC16;
    }

    /**
     * @param ORC16 the ORC16 to set
     */
    public void setORC16(CWEDto ORC16) {
        this.ORC16 = ORC16;
    }

    /**
     * @return the ORC17
     */
    public CWEDto getORC17() {
        return ORC17;
    }

    /**
     * @param ORC17 the ORC17 to set
     */
    public void setORC17(CWEDto ORC17) {
        this.ORC17 = ORC17;
    }

    /**
     * @return the ORC18
     */
    public CWEDto getORC18() {
        return ORC18;
    }

    /**
     * @param ORC18 the ORC18 to set
     */
    public void setORC18(CWEDto ORC18) {
        this.ORC18 = ORC18;
    }

    /**
     * @return the ORC19
     */
    public List<XCNDto> getORC19() {
        return ORC19;
    }

    /**
     * @param ORC19 the ORC19 to set
     */
    public void setORC19(List<XCNDto> ORC19) {
        this.ORC19 = ORC19;
    }

    /**
     * @return the ORC20
     */
    public CWEDto getORC20() {
        return ORC20;
    }

    /**
     * @param ORC20 the ORC20 to set
     */
    public void setORC20(CWEDto ORC20) {
        this.ORC20 = ORC20;
    }

    /**
     * @return the ORC21
     */
    public String getORC21() {
        return ORC21;
    }

    /**
     * @param ORC21 the ORC21 to set
     */
    public void setORC21(String ORC21) {
        this.ORC21 = ORC21;
    }

    /**
     * @return the ORC22
     */
    public XADDto getORC22() {
        return ORC22;
    }

    /**
     * @param ORC22 the ORC22 to set
     */
    public void setORC22(XADDto ORC22) {
        this.ORC22 = ORC22;
    }

    /**
     * @return the ORC23
     */
    public XTNDto getORC23() {
        return ORC23;
    }

    /**
     * @param ORC23 the ORC23 to set
     */
    public void setORC23(XTNDto ORC23) {
        this.ORC23 = ORC23;
    }

    /**
     * @return the ORC24
     */
    public XADDto getORC24() {
        return ORC24;
    }

    /**
     * @param ORC24 the ORC24 to set
     */
    public void setORC24(XADDto ORC24) {
        this.ORC24 = ORC24;
    }

    /**
     * @return the ORC25
     */
    public CWEDto getORC25() {
        return ORC25;
    }

    /**
     * @param ORC25 the ORC25 to set
     */
    public void setORC25(CWEDto ORC25) {
        this.ORC25 = ORC25;
    }

    /**
     * @return the ORC26
     */
    public CWEDto getORC26() {
        return ORC26;
    }

    /**
     * @param ORC26 the ORC26 to set
     */
    public void setORC26(CWEDto ORC26) {
        this.ORC26 = ORC26;
    }

    /**
     * @return the ORC27
     */
    public String getORC27() {
        return ORC27;
    }

    /**
     * @param ORC27 the ORC27 to set
     */
    public void setORC27(String ORC27) {
        this.ORC27 = ORC27;
    }

    /**
     * @return the ORC28
     */
    public CWEDto getORC28() {
        return ORC28;
    }

    /**
     * @param ORC28 the ORC28 to set
     */
    public void setORC28(CWEDto ORC28) {
        this.ORC28 = ORC28;
    }

    /**
     * @return the ORC29
     */
    public CWEDto getORC29() {
        return ORC29;
    }

    /**
     * @param ORC29 the ORC29 to set
     */
    public void setORC29(CWEDto ORC29) {
        this.ORC29 = ORC29;
    }

    /**
     * @return the ORC30
     */
    public String getORC30() {
        return ORC30;
    }

    /**
     * @param ORC30 the ORC30 to set
     */
    public void setORC30(String ORC30) {
        this.ORC30 = ORC30;
    }

    /**
     * ORCのセット
     */
    public void setORC(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "ORC" + st_num;

       switch(target){
            case "ORC00":
                setORC00(value);
                break;
            case "ORC01":
                setORC01(value);
                break;
            case "ORC02":
                setORC02(value);
                break;
            case "ORC03":
                setORC03(value);
                break;
            case "ORC04":
                setORC04(value);
                break;
            case "ORC05":
                setORC05(value);
                break;
            case "ORC06":
                setORC06(value);
                break;
            case "ORC07":
                setORC07(value);
                break;
            case "ORC08":
                setORC08(value);
                break;
            case "ORC09":
                setORC09(value);
                break;
            case "ORC10":
                List<XCNDto> orc10_xcn = setXCN(value);
                setORC10(orc10_xcn);
                break;
            case "ORC11":
                List<XCNDto> orc11_xcn = setXCN(value);
                setORC11(orc11_xcn);
                break;
            case "ORC12":
                List<XCNDto> orc12_xcn = setXCN(value);
                setORC12(orc12_xcn);
                break;
            case "ORC13":
                PLDto orc13_pl = setPL(value);
                setORC13(orc13_pl);
                break;
            case "ORC14":
                XTNDto orc14_xtn = setXTN(value);
                setORC14(orc14_xtn);
                break;
            case "ORC15":
                setORC15(value);
                break;
            case "ORC16":
                CWEDto obr16_cwe = setCWE(value);
                setORC16(obr16_cwe);
                break;
            case "ORC17":
                CWEDto obr17_cwe = setCWE(value);
                setORC16(obr17_cwe);
                break;
            case "ORC18":
                CWEDto obr18_cwe = setCWE(value);
                setORC18(obr18_cwe);
                break;
            case "ORC19":
                List<XCNDto> orc19_xcn = setXCN(value);
                setORC19(orc19_xcn);
                break;
            case "ORC20":
                CWEDto obr20_cwe = setCWE(value);
                setORC20(obr20_cwe);
                break;
            case "ORC21":
                setORC21(value);
                break;
            case "ORC22":
                XADDto orc22_xad = setXAD(value);
                setORC22(orc22_xad);
                break;
            case "ORC23":
                XTNDto orc23_xtn = setXTN(value);
                setORC23(orc23_xtn);
                break;
            case "ORC24":
                XADDto orc24_xad = setXAD(value);
                setORC24(orc24_xad);
                break;
            case "ORC25":
                CWEDto orc25_cwe = setCWE(value);
                setORC25(orc25_cwe);
                break;
            case "ORC26":
                CWEDto orc26_cwe = setCWE(value);
                setORC26(orc26_cwe);
                break;
            case "ORC27":
                setORC27(value);
                break;
            case "ORC28":
                CWEDto orc28_cwe = setCWE(value);
                setORC28(orc28_cwe);
                break;
            case "ORC29":
                CWEDto orc29_cwe = setCWE(value);
                setORC29(orc29_cwe);
                break;
            case "ORC30":
                setORC30(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
       }
   }

    /**
     *  ORCのゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getORC(Integer f_num , Integer e_num , Integer r_num){
        String result = null;

        switch(f_num){
            case 0:
                result = getORC00();
                break;
            case 1:
                result = getORC01();
                break;
            case 2:
                result = getORC02();
                break;
            case 3:
                result = getORC03();
                break;
            case 4:
                result =getORC04();
                break;
            case 5:
                result = getORC05();
                break;
            case 6:
                result = getORC06();
                break;
            case 7:
                result = getORC07();
                break;
            case 8:
                result = getORC08();
                break;
            case 9:
                result = getORC09();
                break;
            case 10:
                result = getXCN(getORC10() , e_num ,r_num);
                break;
            case 11:
                result = getXCN(getORC11() , e_num ,r_num);
                break;
            case 12:
                result = getXCN(getORC12() , e_num ,r_num);
                break;
            case 13:
                result = getPL(getORC13() , e_num );
                break;
            case 14:
                result = getXTN(getORC14() , e_num );
                break;
            case 15:
                result = getORC15();
                break;
            case 16:
                result = getCWE(getORC16() , e_num );
                break;
            case 17:
                result = getCWE(getORC17() , e_num );
                break;
            case 18:
                result = getCWE(getORC18() , e_num );
                break;
            case 19:
                result = getXCN(getORC19() , e_num ,r_num);
                break;
            case 20:
                result = getCWE(getORC20() , e_num );
                break;
            case 21:
                result = getORC21();
                break;
            case 22:
                result = getXAD(getORC22() , e_num );
                break;
            case 23:
                result = getXTN(getORC23() , e_num );
                break;
            case 24:
                result = getXAD(getORC24() , e_num );
                break;
            case 25:
                result = getCWE(getORC25() , e_num );
                break;
            case 26:
                result = getCWE(getORC26() , e_num );
                break;
            case 27:
                result = getORC27();
                break;
            case 28:
                result = getCWE(getORC28() , e_num );
                break;
            case 29:
                result = getCWE(getORC29() , e_num );
                break;
            case 30:
                result = getORC30();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }

    /**
     *  ORCのゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public Object getORCOb(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getORC00();
                break;
            case 1:
                result = getORC01();
                break;
            case 2:
                result = getORC02();
                break;
            case 3:
                result = getORC03();
                break;
            case 4:
                result =getORC04();
                break;
            case 5:
                result = getORC05();
                break;
            case 6:
                result = getORC06();
                break;
            case 7:
                result = getORC07();
                break;
            case 8:
                result = getORC08();
                break;
            case 9:
                result = getORC09();
                break;
            case 10:
                result = getORC10();
                break;
            case 11:
                result = getORC11();
                break;
            case 12:
                result = getORC12();
                break;
            case 13:
                result = getORC13();
                break;
            case 14:
                result = getORC14();
                break;
            case 15:
                result = getORC15();
                break;
            case 16:
                result = getORC16();
                break;
            case 17:
                result = getORC17();
                break;
            case 18:
                result = getORC18();
                break;
            case 19:
                result = getORC19();
                break;
            case 20:
                result = getORC20();
                break;
            case 21:
                result = getORC21();
                break;
            case 22:
                result = getORC22();
                break;
            case 23:
                result = getORC23();
                break;
            case 24:
                result = getORC24();
                break;
            case 25:
                result = getORC25();
                break;
            case 26:
                result = getORC26();
                break;
            case 27:
                result = getORC27();
                break;
            case 28:
                result = getORC28();
                break;
            case 29:
                result = getORC29();
                break;
            case 30:
                result = getORC30();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
}
