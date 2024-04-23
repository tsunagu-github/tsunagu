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

import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.PLDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;

/**
 *
 * @author kis-note
 */
public class PV2Dto extends baseSegmentDto{
    /**
     * ロギングコンポーネント
     */
    private static Log logger = LogFactory.getLog(PV2Dto.class);
    /**
     * セグメントID
     */
    private String PV200;
    /**
     * 事前保留所在場所
     */
    private PLDto PV201;
    /**
     * 宿泊設備コード
     */
    private CWEDto PV202;
    /**
     * 入院の理由
     */
    private CWEDto PV203;
    /**
     * 転科転棟の理由
     */
    private CWEDto PV204;
    /**
     * 患者貴重品情報
     */
    private List<String> PV205;
    /**
     * 患者貴重品の保管場所
     */
    private String PV206;
    /**
     * 来院種別コード
     */
    private List<String> PV207;
    /**
     * 予定入院日時
     */
    private String PV208;
    /**
     * 予定退院日時
     */
    private String PV209;
    /**
     * 予定入院期間
     */
    private String PV210;
    /**
     * 入院期間
     */
    private String PV211;
    /**
     * 来院時記述情報
     */
    private String PV212;
    /**]
     * 紹介元情報
     */
    private List<XCNDto> PV213;
    /**
     * 前回来院日
     */
    private String PV214;
    /**
     * 職業由来疾病情報
     */
    private String PV215;
    /**
     * 削除状態標識
     */
    private String PV216;
    /**
     * 削除予定日
     */
    private String PV217;
    /**
     * 特別プログラムコード
     */
    private String PV218;
    /**
     * 保持標識
     */
    private String PV219;
    /**
     * 適応可能保険の数
     */
    private String PV220;
    /**
     * 来院情報周知範囲
     */
    private String PV221;
    /**
     * 来院情報保護標識
     */
    private String PV222;
    /**
     * 診療部門名
     */
    private List<String> PV223;
    /**
     * 患者状態
     */
    private String PV224;
    /**
     * 受診優先指標
     */
    private String PV225;
    /**
     * 最終治療日
     */
    private String PV226;
    /**
     * 希望退院種別
     */
    private String PV227;
    /**
     * 署名日
     */
    private String PV228;
    /**
     * 発症日
     */
    private String PV229;
    /**
     * 患者費用補正コード
     */
    private CWEDto PV230;
    /**
     * 通院治療標識
     */
    private String PV231;
    /**
     * 請求媒体コード
     */
    private String PV232;
    /**
     * 予定手術日時
     */
    private String PV233;
    /**
     * 軍隊との契約の有無
     */
    private String PV234;
    /**
     * 非軍隊施設の利用許可の有無
     */
    private String PV235;
    /**
     * 新生児標識
     */
    private String PV236;
    /**
     * 新生児残留標識
     */
    private String PV237;
    /**
     * 患者到着手段
     */
    private CWEDto PV238;
    /**
     * 嗜好情報
     */
    private List<CWEDto> PV239;
    /**
     * 入院時患者重症度
     */
    private CWEDto PV240;
    /**
     * 要注意コード
     */
    private List<CWEDto> PV241;
    /**
     * 患者容態標識
     */
    private CWEDto PV242;
    /**
     * 延命希望標識
     */
    private String PV243;
    /**
     * 臓器提供希望標識
     */
    private String PV244;
    /**
     * その他の患者要望
     */
    private List<CWEDto> PV245;
    /**
     * 患者状態(PV2-24)の発症日
     */
    private String PV246;
    /**
     * 予定帰院日時
     */
    private String PV247;
    /**
     * 入院前検査予定日時
     */
    private String PV248;
    /**
     * 聖職者通知標識
     */
    private List<String> PV249;
    /**
     * @return the PV100
     */
    public String getPV200() {
        return PV200;
    }

    /**
     * @param PV200 the PV200 to set
     */
    public void setPV200(String PV200) {
        this.PV200 = PV200;
    }

    /**
     * @return the PV201
     */
    public PLDto getPV201() {
        return PV201;
    }

    /**
     * @param PV201 the PV201 to set
     */
    public void setPV201(PLDto PV201) {
        this.PV201 = PV201;
    }

    /**
     * @return the PV202
     */
    public CWEDto getPV202() {
        return PV202;
    }

    /**
     * @param PV202 the PV202 to set
     */
    public void setPV202(CWEDto PV202) {
        this.PV202 = PV202;
    }

    /**
     * @return the PV203
     */
    public CWEDto getPV203() {
        return PV203;
    }

    /**
     * @param PV203 the PV203 to set
     */
    public void setPV203(CWEDto PV203) {
        this.PV203 = PV203;
    }

    /**
     * @return the PV204
     */
    public CWEDto getPV204() {
        return PV204;
    }

    /**
     * @param PV204 the PV204 to set
     */
    public void setPV204(CWEDto PV204) {
        this.PV204 = PV204;
    }

    /**
     * @return the PV205
     */
    public List<String> getPV205() {
        return PV205;
    }

    /**
     * @param PV205 the PV205 to set
     */
    public void setPV205(List<String> PV205) {
        this.PV205 = PV205;
    }

    /**
     * @return the PV206
     */
    public String getPV206() {
        return PV206;
    }

    /**
     * @param PV206 the PV206 to set
     */
    public void setPV206(String PV206) {
        this.PV206 = PV206;
    }

    /**
     * @return the PV207
     */
    public List<String> getPV207() {
        return PV207;
    }

    /**
     * @param PV207 the PV207 to set
     */
    public void setPV207(List<String> PV207) {
        this.PV207 = PV207;
    }

    /**
     * @return the PV208
     */
    public String getPV208() {
        return PV208;
    }

    /**
     * @param PV208 the PV208 to set
     */
    public void setPV208(String PV208) {
        this.PV208 = PV208;
    }

    /**
     * @return the PV209
     */
    public String getPV209() {
        return PV209;
    }

    /**
     * @param PV209 the PV209 to set
     */
    public void setPV209(String PV209) {
        this.PV209 = PV209;
    }

    /**
     * @return the PV210
     */
    public String getPV210() {
        return PV210;
    }

    /**
     * @param PV210 the PV210 to set
     */
    public void setPV210(String PV210) {
        this.PV210 = PV210;
    }

    /**
     * @return the PV211
     */
    public String getPV211() {
        return PV211;
    }

    /**
     * @param PV211 the PV211 to set
     */
    public void setPV211(String PV211) {
        this.PV211 = PV211;
    }

    /**
     * @return the PV212
     */
    public String getPV212() {
        return PV212;
    }

    /**
     * @param PV212 the PV212 to set
     */
    public void setPV212(String PV212) {
        this.PV212 = PV212;
    }

    /**
     * @return the PV213
     */
    public List<XCNDto> getPV213() {
        return PV213;
    }

    /**
     * @param PV213 the PV213 to set
     */
    public void setPV213(List<XCNDto> PV213) {
        this.PV213 = PV213;
    }

    /**
     * @return the PV214
     */
    public String getPV214() {
        return PV214;
    }

    /**
     * @param PV214 the PV214 to set
     */
    public void setPV214(String PV214) {
        this.PV214 = PV214;
    }

    /**
     * @return the PV215
     */
    public String getPV215() {
        return PV215;
    }

    /**
     * @param PV215 the PV215 to set
     */
    public void setPV215(String PV215) {
        this.PV215 = PV215;
    }

    /**
     * @return the PV216
     */
    public String getPV216() {
        return PV216;
    }

    /**
     * @param PV216 the PV216 to set
     */
    public void setPV216(String PV216) {
        this.PV216 = PV216;
    }

    /**
     * @return the PV217
     */
    public String getPV217() {
        return PV217;
    }

    /**
     * @param PV217 the PV217 to set
     */
    public void setPV217(String PV217) {
        this.PV217 = PV217;
    }

    /**
     * @return the PV218
     */
    public String getPV218() {
        return PV218;
    }

    /**
     * @param PV218 the PV218 to set
     */
    public void setPV218(String PV218) {
        this.PV218 = PV218;
    }

    /**
     * @return the PV219
     */
    public String getPV219() {
        return PV219;
    }

    /**
     * @param PV219 the PV219 to set
     */
    public void setPV219(String PV219) {
        this.PV219 = PV219;
    }

    /**
     * @return the PV220
     */
    public String getPV220() {
        return PV220;
    }

    /**
     * @param PV220 the PV220 to set
     */
    public void setPV220(String PV220) {
        this.PV220 = PV220;
    }

    /**
     * @return the PV221
     */
    public String getPV221() {
        return PV221;
    }

    /**
     * @param PV221 the PV221 to set
     */
    public void setPV221(String PV221) {
        this.PV221 = PV221;
    }

    /**
     * @return the PV222
     */
    public String getPV222() {
        return PV222;
    }

    /**
     * @param PV222 the PV222 to set
     */
    public void setPV222(String PV222) {
        this.PV222 = PV222;
    }

    /**
     * @return the PV223
     */
    public List<String> getPV223() {
        return PV223;
    }

    /**
     * @param PV223 the PV223 to set
     */
    public void setPV223(List<String> PV223) {
        this.PV223 = PV223;
    }

    /**
     * @return the PV224
     */
    public String getPV224() {
        return PV224;
    }

    /**
     * @param PV224 the PV224 to set
     */
    public void setPV224(String PV224) {
        this.PV224 = PV224;
    }

    /**
     * @return the PV225
     */
    public String getPV225() {
        return PV225;
    }

    /**
     * @param PV225 the PV225 to set
     */
    public void setPV225(String PV225) {
        this.PV225 = PV225;
    }

    /**
     * @return the PV226
     */
    public String getPV226() {
        return PV226;
    }

    /**
     * @param PV226 the PV226 to set
     */
    public void setPV226(String PV226) {
        this.PV226 = PV226;
    }

    /**
     * @return the PV227
     */
    public String getPV227() {
        return PV227;
    }

    /**
     * @param PV227 the PV227 to set
     */
    public void setPV227(String PV227) {
        this.PV227 = PV227;
    }

    /**
     * @return the PV228
     */
    public String getPV228() {
        return PV228;
    }

    /**
     * @param PV228 the PV228 to set
     */
    public void setPV228(String PV228) {
        this.PV228 = PV228;
    }

    /**
     * @return the PV229
     */
    public String getPV229() {
        return PV229;
    }

    /**
     * @param PV229 the PV229 to set
     */
    public void setPV229(String PV229) {
        this.PV229 = PV229;
    }

    /**
     * @return the PV230
     */
    public CWEDto getPV230() {
        return PV230;
    }

    /**
     * @param PV230 the PV230 to set
     */
    public void setPV230(CWEDto PV230) {
        this.PV230 = PV230;
    }

    /**
     * @return the PV231
     */
    public String getPV231() {
        return PV231;
    }

    /**
     * @param PV231 the PV231 to set
     */
    public void setPV231(String PV231) {
        this.PV231 = PV231;
    }

    /**
     * @return the PV232
     */
    public String getPV232() {
        return PV232;
    }

    /**
     * @param PV232 the PV232 to set
     */
    public void setPV232(String PV232) {
        this.PV232 = PV232;
    }

    /**
     * @return the PV233
     */
    public String getPV233() {
        return PV233;
    }

    /**
     * @param PV233 the PV233 to set
     */
    public void setPV233(String PV233) {
        this.PV233 = PV233;
    }

    /**
     * @return the PV234
     */
    public String getPV234() {
        return PV234;
    }

    /**
     * @param PV234 the PV234 to set
     */
    public void setPV234(String PV234) {
        this.PV234 = PV234;
    }

    /**
     * @return the PV235
     */
    public String getPV235() {
        return PV235;
    }

    /**
     * @param PV235 the PV235 to set
     */
    public void setPV235(String PV235) {
        this.PV235 = PV235;
    }

    /**
     * @return the PV236
     */
    public String getPV236() {
        return PV236;
    }

    /**
     * @param PV236 the PV236 to set
     */
    public void setPV236(String PV236) {
        this.PV236 = PV236;
    }

    /**
     * @return the PV237
     */
    public String getPV237() {
        return PV237;
    }

    /**
     * @param PV237 the PV237 to set
     */
    public void setPV237(String PV237) {
        this.PV237 = PV237;
    }

    /**
     * @return the PV238
     */
    public CWEDto getPV238() {
        return PV238;
    }

    /**
     * @param PV238 the PV238 to set
     */
    public void setPV238(CWEDto PV238) {
        this.PV238 = PV238;
    }

    /**
     * @return the PV239
     */
    public List<CWEDto> getPV239() {
        return PV239;
    }

    /**
     * @param PV239 the PV239 to set
     */
    public void setPV239(List<CWEDto> PV239) {
        this.PV239 = PV239;
    }

    /**
     * @return the PV240
     */
    public CWEDto getPV240() {
        return PV240;
    }

    /**
     * @param PV240 the PV240 to set
     */
    public void setPV240(CWEDto PV240) {
        this.PV240 = PV240;
    }

    /**
     * @return the PV241
     */
    public List<CWEDto> getPV241() {
        return PV241;
    }

    /**
     * @param PV241 the PV241 to set
     */
    public void setPV241(List<CWEDto> PV241) {
        this.PV241 = PV241;
    }

    /**
     * @return the PV242
     */
    public CWEDto getPV242() {
        return PV242;
    }

    /**
     * @param PV242 the PV242 to set
     */
    public void setPV242(CWEDto PV242) {
        this.PV242 = PV242;
    }

    /**
     * @return the PV243
     */
    public String getPV243() {
        return PV243;
    }

    /**
     * @param PV243 the PV243 to set
     */
    public void setPV243(String PV243) {
        this.PV243 = PV243;
    }

    /**
     * @return the PV244
     */
    public String getPV244() {
        return PV244;
    }

    /**
     * @param PV244 the PL144 to set
     */
    public void setPV244(String PV244) {
        this.PV244 = PV244;
    }

    /**
     * @return the PV245
     */
    public List<CWEDto> getPV245() {
        return PV245;
    }

    /**
     * @param PV245 the PV245 to set
     */
    public void setPV245(List<CWEDto> PV245) {
        this.PV245 = PV245;
    }

    /**
     * @return the PV246
     */
    public String getPV246() {
        return PV246;
    }

    /**
     * @param PV246 the PV246 to set
     */
    public void setPV246(String PV246) {
        this.PV246 = PV246;
    }

    /**
     * @return the PV247
     */
    public String getPV247() {
        return PV247;
    }

    /**
     * @param PV247 the PV247 to set
     */
    public void setPV247(String PV247) {
        this.PV247 = PV247;
    }

    /**
     * @return the PV248
     */
    public String getPV248() {
        return PV248;
    }

    /**
     * @param PV248 the PV248 to set
     */
    public void setPV248(String PV248) {
        this.PV248 = PV248;
    }

    /**
     * @return the PV249
     */
    public List<String> getPV249() {
        return PV249;
    }

    /**
     * @param PV249 the PV249 to set
     */
    public void setPV249(List<String> PV249) {
        this.PV249 = PV249;
    }

    /**
     * PV2のセット
     */
    public void setPV2(String value , int num){
        String st_num = String.format("%1$02d", num);
        String target = "PV2" + st_num;

        switch(target){
            case "PV200":
                setPV200(value);
                break;
            case "PV201":
                PLDto pv201 = setPL(value);
                setPV201(pv201);
                break;
            case "PV202":
                CWEDto pv202 = setCWE(value);
                setPV202(pv202);
                break;
            case "PV203":
            	CWEDto pv203 = setCWE(value);
                setPV203(pv203);
                break;
            case "PV204":
            	CWEDto pv204 = setCWE(value);
                setPV204(pv204);
                break;
            case "PV205":
            	List<String> pv205List = new ArrayList<>(Arrays.asList(value.split(getRepeatSp())));
                setPV205(pv205List);
                break;
            case "PV206":
                setPV206(value);
                break;
            case "PV207":
            	List<String> pv207List = new ArrayList<>(Arrays.asList(value.split(getRepeatSp())));
                setPV207(pv207List);
                break;
            case "PV208":
                setPV208(value);
                break;
            case "PV209":
                setPV209(value);
                break;
            case "PV210":
                setPV210(value);
                break;
            case "PV211":
                setPV211(value);
                break;
            case "PV212":
                setPV212(value);
                break;
            case "PV213":
                List<XCNDto> pv213List = setXCN(value);
                setPV213(pv213List);
                break;
            case "PV214":
                setPV214(value);
                break;
            case "PV215":
                setPV215(value);
                break;
            case "PV216":
                setPV216(value);
                break;
            case "PV217":
                setPV217(value);
                break;
            case "PV218":
                setPV218(value);
                break;
            case "PV219":
                setPV219(value);
                break;
            case "PV220":
                setPV220(value);
                break;
            case "PV221":
                setPV221(value);
                break;
            case "PV222":
                setPV222(value);
                break;
            case "PV223":
            	List<String> pv223List = new ArrayList<>(Arrays.asList(value.split(getRepeatSp())));
                setPV223(pv223List);
                break;
            case "PV224":
                setPV224(value);
                break;
            case "PV225":
                setPV225(value);
                break;
            case "PV226":
                setPV226(value);
                break;
            case "PV227":
                setPV227(value);
                break;
            case "PV228":
                setPV228(value);
                break;
            case "PV229":
                setPV229(value);
                break;
            case "PV230":
            	CWEDto pv230 = setCWE(value);
                setPV230(pv230);
                break;
            case "PV231":
                setPV231(value);
                break;
            case "PV232":
                setPV232(value);
                break;
            case "PV233":
                setPV233(value);
                break;
            case "PV234":
                setPV234(value);
                break;
            case "PV235":
                setPV235(value);
                break;
            case "PV236":
                setPV236(value);
                break;
            case "PV237":
                setPV237(value);
                break;
            case "PV238":
            	CWEDto pv238 = setCWE(value);
                setPV238(pv238);
                break;
            case "PV239":
            	List<CWEDto> pv239List = setCWEList(value);
                setPV239(pv239List);
                break;
            case "PV240":
            	CWEDto pv240 = setCWE(value);
                setPV240(pv240);
                break;
            case "PV241":
            	List<CWEDto> pv241List = setCWEList(value);
                setPV241(pv241List);
                break;
            case "PV242":
            	CWEDto pv242 = setCWE(value);
                setPV242(pv242);
                break;
            case "PV243":
                setPV243(value);
                break;
            case "PV244":
                setPV244(value);
                break;
            case "PV245":
            	List<CWEDto> pv245List = setCWEList(value);
                setPV245(pv245List);
                break;
            case "PV246":
                setPV246(value);
                break;
            case "PV247":
                setPV247(value);
                break;
            case "PV248":
                setPV248(value);
                break;
            case "PV249":
            	List<String> pv249List = new ArrayList<>(Arrays.asList(value.split(getRepeatSp())));
                setPV249(pv249List);
                break;
            default:
                logger.debug("指定番号は対応していません " + num);
        }

    }

    /**
     *  PV2のゲット
     *
     * @param f_num フィールドNo　必須
     * @param e_num　成分No　
     * @param r_num　反復No
     */
    public String getPV2(Integer f_num , Integer e_num , Integer r_num){
        String result = null;

        switch(f_num){
            case 0:
                result = getPV200();
                break;
            case 1:
                result = getPL(getPV201() , e_num);
                break;
            case 2:
                result = getCWE(getPV202() , e_num );
                break;
            case 3:
                result = getCWE(getPV203() , e_num );
                break;
            case 4:
                result = getCWE(getPV204() , e_num );
                break;
            case 5:
                result = getPV205().get(r_num);
                break;
            case 6:
                result = getPV206();
                break;
            case 7:
                result = getPV207().get(r_num);
                break;
            case 8:
                result = getPV208();
                break;
            case 9:
                result = getPV209();
                break;
            case 10:
                result = getPV210();
                break;
            case 11:
                result = getPV211();
                break;
            case 12:
                result = getPV212();
                break;
            case 13:
                result = getXCN(getPV213() , e_num , r_num);
                break;
            case 14:
                result = getPV214();
                break;
            case 15:
                result = getPV215();
                break;
            case 16:
                result = getPV216();
                break;
            case 17:
                result = getPV217();
                break;
            case 18:
                result = getPV218();
                break;
            case 19:
                result = getPV219();
                break;
            case 20:
                result = getPV220();
                break;
            case 21:
                result = getPV221();
                break;
            case 22:
                result = getPV222();
                break;
            case 23:
                result = getPV223().get(r_num);
                break;
            case 24:
                result = getPV224();
                break;
            case 25:
                result = getPV225();
                break;
            case 26:
                result = getPV226();
                break;
            case 27:
                result = getPV227();
                break;
            case 28:
                result = getPV228();
                break;
            case 29:
                result = getPV229();
                break;
            case 30:
                result = getCWE(getPV230() , e_num );
                break;
            case 31:
                result = getPV231();
                break;
            case 32:
                result = getPV232();
                break;
            case 33:
                result = getPV233();
                break;
            case 34:
                result = getPV234();
                break;
            case 35:
                result = getPV235();
                break;
            case 36:
                result = getPV236();
                break;
            case 37:
                result = getPV237();
                break;
            case 38:
                result = getCWE(getPV238() , e_num );
                break;
            case 39:
                result = getCWE(getPV239().get(r_num) , e_num );
                break;
            case 40:
                result = getCWE(getPV240() , e_num );
                break;
            case 41:
                result = getCWE(getPV241().get(r_num) , e_num );
                break;
            case 42:
                result = getCWE(getPV242() , e_num );
                break;
            case 43:
                result = getPV243();
                break;
            case 44:
                result = getPV244();
                break;
            case 45:
                result = getCWE(getPV245().get(r_num) , e_num );
                break;
            case 46:
                result = getPV246();
                break;
            case 47:
                result = getPV247();
                break;
            case 48:
                result = getPV248();
                break;
            case 49:
                result = getPV249().get(r_num);
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }

    /**
     *  PV2のゲット
     *
     * @param f_num フィールドNo　必須
     */
    public Object getPV2Ob(Integer f_num ){
        Object result = null;

        switch(f_num){
            case 0:
                result = getPV200();
                break;
            case 1:
                result = getPV201();
                break;
            case 2:
                result = getPV202();
                break;
            case 3:
                result = getPV203();
                break;
            case 4:
                result = getPV204();
                break;
            case 5:
                result = getPV205();
                break;
            case 6:
                result = getPV206();
                break;
            case 7:
                result = getPV207();
                break;
            case 8:
                result = getPV208();
                break;
            case 9:
                result = getPV209();
                break;
            case 10:
                result = getPV210();
                break;
            case 11:
                result = getPV211();
                break;
            case 12:
                result = getPV212();
                break;
            case 13:
                result = getPV213();
                break;
            case 14:
                result = getPV214();
                break;
            case 15:
                result = getPV215();
                break;
            case 16:
                result = getPV216();
                break;
            case 17:
                result = getPV217();
                break;
            case 18:
                result = getPV218();
                break;
            case 19:
                result = getPV219();
                break;
            case 20:
                result = getPV220();
                break;
            case 21:
                result = getPV221();
                break;
            case 22:
                result = getPV222();
                break;
            case 23:
                result = getPV223();
                break;
            case 24:
                result = getPV224();
                break;
            case 25:
                result = getPV225();
                break;
            case 26:
                result = getPV226();
                break;
            case 27:
                result = getPV227();
                break;
            case 28:
                result = getPV228();
                break;
            case 29:
                result = getPV229();
                break;
            case 30:
                result = getPV230();
                break;
            case 31:
                result = getPV231();
                break;
            case 32:
                result = getPV232();
                break;
            case 33:
                result = getPV233();
                break;
            case 34:
                result = getPV234();
                break;
            case 35:
                result = getPV235();
                break;
            case 36:
                result = getPV236();
                break;
            case 37:
                result = getPV237();
                break;
            case 38:
                result = getPV238();
                break;
            case 39:
                result = getPV239();
                break;
            case 40:
                result = getPV240();
                break;
            case 41:
                result = getPV241();
                break;
            case 42:
                result = getPV242();
                break;
            case 43:
                result = getPV243();
                break;
            case 44:
                result = getPV244();
                break;
            case 45:
                result = getPV245();
                break;
            case 46:
                result = getPV246();
                break;
            case 47:
                result = getPV247();
                break;
            case 48:
                result = getPV248();
                break;
            case 49:
                result = getPV249();
                break;
            default:
                logger.debug("指定番号は対応していません " + f_num);
        }

        return result;
    }
}
