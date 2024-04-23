/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;

/**
 *
 * @author kis-note
 */
public class OBXDto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(OBXDto.class);

    /**
     * セグメントID
     */
    private String OBX00;
    /**
     * セットID-OBX
     */
    private String OBX01;
    /**
     * 値型
     */
    private String OBX02;
    /**
     * 検査項目
     */
    private CWEDto OBX03;
    /**
     * 検査割ID
     */
    private String OBX04;
    /**
     * 結果値
     * ここはOBX-2での指定が有効になるため配列で用意する
     * setter,getterは特殊対応とする
     */
    private String[] OBX05;
    /**
     * 単位
     */
    private CWEDto OBX06;
    /**
     * 基準値範囲
     */
    private String OBX07;
    /**
     * 異常フラグ
     */
    private List<String> OBX08;
    /**
     *　確率
     */
    private String OBX09;
    /**
     * 異常検査の特質
     */
    private String OBX10;
    /**
     * 検査結果状態
     */
    private String OBX11;
    /**
     * 基準値範囲有効日付
     */
    private String OBX12;
    /**
     * 使用者定義アクセス点検
     */
    private String OBX13;
    /**
     * 検査日時
     */
    private String OBX14;
    /**
     * 実施者ID
     */
    private CWEDto OBX15;
    /**
     * 検査責任者
     */
    private List<XCNDto> OBX16;
    /**
     * 検査方法
     */
    private CWEDto OBX17;
    /**
     * 装置ID
     */
    private String OBX18;
    /**
     * 分析日時
     */
    private String OBX19;

    /**
     * @return the OBX00
     */
    public String getOBX00() {
        return OBX00;
    }

    /**
     * @param OBX00 the OBX00 to set
     */
    public void setOBX00(String OBX00) {
        this.OBX00 = OBX00;
    }

    /**
     * @return the OBX01
     */
    public String getOBX01() {
        return OBX01;
    }

    /**
     * @param OBX01 the OBX01 to set
     */
    public void setOBX01(String OBX01) {
        this.OBX01 = OBX01;
    }

    /**
     * @return the OBX02
     */
    public String getOBX02() {
        return OBX02;
    }

    /**
     * @param OBX02 the OBX02 to set
     */
    public void setOBX02(String OBX02) {
        this.OBX02 = OBX02;
    }

    /**
     * @return the OBX03
     */
    public CWEDto getOBX03() {
        return OBX03;
    }

    /**
     * @param OBX03 the OBX03 to set
     */
    public void setOBX03(CWEDto OBX03) {
        this.OBX03 = OBX03;
    }

    /**
     * @return the OBX04
     */
    public String getOBX04() {
        return OBX04;
    }

    /**
     * @param OBX04 the OBX04 to set
     */
    public void setOBX04(String OBX04) {
        this.OBX04 = OBX04;
    }

    /**
     * @return the OBX05
     */
    public String[] getOBX05() {
        return OBX05;
    }

    /**
     * @param OBX05 the OBX05 to set
     */
    public void setOBX05(String[] OBX05) {
        this.OBX05 = OBX05;
    }

    /**
     * @return the OBX06
     */
    public CWEDto getOBX06() {
        return OBX06;
    }

    /**
     * @param OBX06 the OBX06 to set
     */
    public void setOBX06(CWEDto OBX06) {
        this.OBX06 = OBX06;
    }

    /**
     * @return the OBX07
     */
    public String getOBX07() {
        return OBX07;
    }

    /**
     * @param OBX07 the OBX07 to set
     */
    public void setOBX07(String OBX07) {
        this.OBX07 = OBX07;
    }

    /**
     * @return the OBX08
     */
    public List<String> getOBX08() {
        return OBX08;
    }

    /**
     * @param OBX08 the OBX08 to set
     */
    public void setOBX08(List<String> OBX08) {
        this.OBX08 = OBX08;
    }

    /**
     * @return the OBX09
     */
    public String getOBX09() {
        return OBX09;
    }

    /**
     * @param OBX09 the OBX09 to set
     */
    public void setOBX09(String OBX09) {
        this.OBX09 = OBX09;
    }

    /**
     * @return the OBX10
     */
    public String getOBX10() {
        return OBX10;
    }

    /**
     * @param OBX10 the OBX10 to set
     */
    public void setOBX10(String OBX10) {
        this.OBX10 = OBX10;
    }

    /**
     * @return the OBX11
     */
    public String getOBX11() {
        return OBX11;
    }

    /**
     * @param OBX11 the OBX11 to set
     */
    public void setOBX11(String OBX11) {
        this.OBX11 = OBX11;
    }

    /**
     * @return the OBX12
     */
    public String getOBX12() {
        return OBX12;
    }

    /**
     * @param OBX12 the OBX12 to set
     */
    public void setOBX12(String OBX12) {
        this.OBX12 = OBX12;
    }

    /**
     * @return the OBX13
     */
    public String getOBX13() {
        return OBX13;
    }

    /**
     * @param OBX13 the OBX13 to set
     */
    public void setOBX13(String OBX13) {
        this.OBX13 = OBX13;
    }

    /**
     * @return the OBX14
     */
    public String getOBX14() {
        return OBX14;
    }

    /**
     * @param OBX14 the OBX14 to set
     */
    public void setOBX14(String OBX14) {
        this.OBX14 = OBX14;
    }

    /**
     * @return the OBX15
     */
    public CWEDto getOBX15() {
        return OBX15;
    }

    /**
     * @param OBX15 the OBX15 to set
     */
    public void setOBX15(CWEDto OBX15) {
        this.OBX15 = OBX15;
    }

    /**
     * @return the OBX16
     */
    public List<XCNDto> getOBX16() {
        return OBX16;
    }

    /**
     * @param OBX16 the OBX16 to set
     */
    public void setOBX16(List<XCNDto> OBX16) {
        this.OBX16 = OBX16;
    }

    /**
     * @return the OBX17
     */
    public CWEDto getOBX17() {
        return OBX17;
    }

    /**
     * @param OBX17 the OBX17 to set
     */
    public void setOBX17(CWEDto OBX17) {
        this.OBX17 = OBX17;
    }

    /**
     * @return the OBX18
     */
    public String getOBX18() {
        return OBX18;
    }

    /**
     * @param OBX18 the OBX18 to set
     */
    public void setOBX18(String OBX18) {
        this.OBX18 = OBX18;
    }

    /**
     * @return the OBX19
     */
    public String getOBX19() {
        return OBX19;
    }

    /**
     * @param OBX19 the OBX19 to set
     */
    public void setOBX19(String OBX19) {
        this.OBX19 = OBX19;
    }

    /**
     * OBXのセット
     */
    public void setOBX(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "OBX" + st_num;

       switch(target){
            case "OBX00":
                setOBX00(value);
                break;
            case "OBX01":
                setOBX01(value);
                break;
            case "OBX02":
                setOBX02(value);
                break;
            case "OBX03":
                CWEDto obx03_cwe = setCWE(value);
                setOBX03(obx03_cwe);
                break;
            case "OBX04":
                setOBX04(value);
                break;
            case "OBX05":
                String unit = getOBX02();

                if(unit.equals("CWE")){
                    CWEDto obx05_cwe = setCWE(value);
                    String[] values = setCWEAL(obx05_cwe);
                    setOBX05(values);
                }else{
                    String[] values = new String[1];
                    values[0] = value;
                    setOBX05(values);
                }
                break;
            case "OBX06":
                CWEDto obx06_cwe = setCWE(value);
                setOBX06(obx06_cwe);
                 break;
            case "OBX07":
                setOBX07(value);
                break;
            case "OBX08":
                setOBX08(Arrays.asList(value.split(this.getRepeatSp())));
                break;
            case "OBX09":
                setOBX09(value);
                break;
            case "OBX10":
                setOBX10(value);
                break;
            case "OBX11":
                setOBX11(value);
                break;
            case "OBX12":
                setOBX12(value);
                break;
            case "OBX13":
                setOBX13(value);
                break;
            case "OBX14":
                setOBX14(value);
                break;
            case "OBX15":
                CWEDto obx15_cwe = setCWE(value);
                setOBX15(obx15_cwe);
                break;
            case "OBX16":
                List<XCNDto>obr16_xcn = setXCN(value);
                setOBX16(obr16_xcn);
                break;
            case "OBX17":
                CWEDto obx17_cwe = setCWE(value);
                setOBX17(obx17_cwe);
                break;
            case "OBX18":
                setOBX18(value);
                break;
            case "OBX19":
                setOBX19(value);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
       }
   }

    /**
     *  OBXのゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getOBX(Integer f_num , Integer e_num , Integer r_num){
        String result = null;

        switch(f_num){
            case 0:
                result = getOBX00();
                break;
            case 1:
                result = getOBX01();
                break;
            case 2:
                result = getOBX02();
                break;
            case 3:
                result = getCWE(getOBX03() , e_num);
                break;
            case 4:
                result = getOBX04();
                break;
            case 5:
                String unit = getOBX02();
                if(unit.equals("CWE")){
                    CWEDto obx_cwe = getCWEAL(getOBX05());
                    result = getCWE(obx_cwe, e_num);
                }else{
                    String[] values = getOBX05();
                    result = values[0];
                }
                break;
            case 6:
                result = getCWE(getOBX06() , e_num);
                break;
            case 7:
                result = getOBX07();
                break;
            case 8:
                result = getOBX08().get(e_num);
                break;
            case 9:
                result = getOBX09();
                break;
            case 10:
                result = getOBX10();
                break;
            case 11:
                result = getOBX11();
                break;
            case 12:
                result = getOBX12();
                break;
            case 13:
                result = getOBX13();
                break;
            case 14:
                result = getOBX14();
                break;
            case 15:
                result = getCWE(getOBX15() , e_num );
                break;
            case 16:
                result = getXCN(getOBX16() , e_num ,r_num);
                break;
            case 17:
                result = getCWE(getOBX17() , e_num);
                break;
            case 18:
                result = getOBX18();
                break;
            case 19:
                result = getOBX19();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }

    /**
     *  OBXのゲット
     *
     * @param f_num フィールドNo　必須
     */
    public Object getOBXOb(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getOBX00();
                break;
            case 1:
                result = getOBX01();
                break;
            case 2:
                result = getOBX02();
                break;
            case 3:
                result = getOBX03();
                break;
            case 4:
                result = getOBX04();
                break;
            case 5:
                result = getOBX05();
                break;
            case 6:
                result = getOBX06();
                break;
            case 7:
                result = getOBX07();
                break;
            case 8:
                result = getOBX08();
                break;
            case 9:
                result = getOBX09();
                break;
            case 10:
                result = getOBX10();
                break;
            case 11:
                result = getOBX11();
                break;
            case 12:
                result = getOBX12();
                break;
            case 13:
                result = getOBX13();
                break;
            case 14:
                result = getOBX14();
                break;
            case 15:
                result = getOBX15();
                break;
            case 16:
                result = getOBX16();
                break;
            case 17:
                result = getOBX17();
                break;
            case 18:
                result = getOBX18();
                break;
            case 19:
                result = getOBX19();
                break;
           default:
                logger.debug("指定番号は対応していません " + f_num);
        }
        return result;
    }
}
