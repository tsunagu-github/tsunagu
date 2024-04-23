/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.adapter.ObservationDefinitionInsurerAdapter;
import phr.datadomain.adapter.ObservationDefinitionScriptAdapter;
import phr.datadomain.adapter.ObservationEventAdapter;
import phr.datadomain.adapter.PatientAdapter;
import phr.datadomain.entity.ObservationDefinitionScriptEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.*;

/**
 *
 * @author kis-note
 */
public class AutoCalcService implements IAutoCalcService{
    
    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(AutoCalcService.class);
	private static Logger logger = Logger.getLogger(AutoCalcService.class);
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /*
    * 自動計算を実施する
    */
    public List<ObservationEntity> autoCalcSet(String phrid ,String insurerNo, int year , Timestamp date , List<ObservationEntity> observationList)  throws Throwable{
        logger.debug("自動計算開始");
        
        List<ObservationEntity> resultList = new ArrayList<ObservationEntity>();
        
        logger.debug("検査のIDと結果値のMAPを作成");
        Map<String,String> targetMap = new HashMap<String,String>();
        String eventId = null;
        for(ObservationEntity observation : observationList){
            targetMap.put(observation.getObservationDefinitionId(),observation.getValue());
            eventId = observation.getObservationEventId();
            resultList.add(observation);
        }

        logger.debug("自動計算リストの作成");
        logger.debug("insurerNo=" + insurerNo);
         logger.debug("year=" + year);
       List<String> autoCalcList = getAutoCalcList(insurerNo,year);
        
        if(autoCalcList == null || autoCalcList.size() == 0) return observationList;
        
        //患者基本情報の取得
        logger.debug("患者基本情報の取得");
        PatientEntity pentity = getPatientInfo(phrid);
        
        //2016年9月段階では
        //対応はBMI,eGFR,nonHDLの計算のみ対応
        for(String id : autoCalcList){
            ObservationEntity result = new ObservationEntity();
            String value = null;
            String jlac10 = null;
            String unit = null;
            
            if(targetMap.containsKey(id)){
                logger.debug("数値入力があるため自動計算は実施しない");
                continue;
            }
            try{
                switch(id){
                    //BMIの計算
                    case "1000000001":
                        value = calcBMI(phrid,targetMap,date);
                        jlac10 = "9N011000000000001";
                        break;
                    //eGFR(血清クレアチニン）の計算
                    case "1000000002":
                        value = calceGFRCre(pentity , insurerNo , year , date , targetMap);
                        jlac10 = "8A065000002391901";
                        unit = "ml/min/1.73m2";
                        break;
                    //eGFR(システチンC）の計算
                    case "1000000003":
                        value = calceGFRCys(pentity , insurerNo , year , date , targetMap);
                        jlac10 = "";
                        break;   
                    //non-HDLコレステロールの計算
                    case "1000000004":
                        value = calcenonHDLC(phrid,targetMap,date);
                        jlac10 = "3F069000002391901";
                        break;
                    default:
                         logger.info(id + "は自動計算項目として実装されていません。");
                         break;
                }
            }catch(Exception e){
                logger.debug(id + "の自動計算処理に失敗尾しました。");
            }
            
            if(value != null){
                result.setCreateDateTime(date);
                result.setUpdateDateTime(date);
                result.setValue(value);
                result.setJLAC10(jlac10);
                if (unit != null) {
                    result.setUnit(unit);
                }
                result.setObservationDefinitionId(id);
                result.setObservationEventId(eventId);
                result.setDiseaseManagementTargetFlg(true);
                resultList.add(result);
            }
        }

        logger.debug("自動計算終了");
        
        return resultList;
    }

    private List<String> getAutoCalcList(String insurerNo, int year) throws Throwable {
        List<String> resultList = null;
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationDefinitionInsurerAdapter adapter = new ObservationDefinitionInsurerAdapter(dao.getConnection());
            resultList = adapter.findActoCalc(insurerNo, year);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            return null;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        return resultList;
    }

    /*
    * PHRIDから患者情報を取得する
    */
    private PatientEntity getPatientInfo(String phrid) throws SQLException, ClassNotFoundException {
        logger.debug("Start");

        DataAccessObject dao = new DataAccessObject();
        PatientEntity rtnPatientEntity = null;
    	try{
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            dao.beginTransaction();
            PatientEntity account = adapter.findByPrimaryKey(phrid);

            rtnPatientEntity = account;
    	}catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	} catch (Throwable ex) {
//            Logger.getLogger(AlertSetService.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(AlertSetService.class.getName()).log(Level.WARN, null, ex);
        }finally{
            if (dao != null) {
                try{
                    dao.close();
                }catch(Throwable e){
                    logger.error(e.toString(), e);
                }
            }
        }

        logger.debug("end");
        return rtnPatientEntity;
    }

    private String findLatestValue(String phrid , String id,Timestamp date) throws Throwable {
        ObservationEventEntity entity = null;
        DataAccessObject dao = null;
        try {
            dao = new DataAccessObject();
            ObservationEventAdapter adapter = new ObservationEventAdapter(dao.getConnection());
            entity = adapter.findLatestValue(phrid,id,date);
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            return null;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
        
        if(entity == null ) return null;
        return entity.getObservationValue();
    }

    /*
    * BMI計算実施メソッド
    * 使用項目は身長[cm]と体重[kg]
    * 計算に必要になるObservaationDefinitionIdと単位は固定とする
    * 計算を実施するメソッドにおいて返り値は計算結果のStringとする。
    */
        private String calcBMI(String phrid,Map<String, String> targetMap, Timestamp date) throws Throwable {
        logger.debug("BMIの自動計算開始");
        
        boolean calcflg = false;

        //計算に使用するObservationDefinitionIdの宣言及び結果の取得先
        String tallid = "0000000001";
        String tall = null;

        String weightid = "0000000002";
        String weight = null;
        
        /*
        * 計算実施の確認
        * 計算を実施するタイミングは計算に必要な項目が１つでも更新されたときとする
        */
        if(targetMap.containsKey(tallid)){
            tall = targetMap.get(tallid);
            calcflg = true;
        }
        
        if(targetMap.containsKey(weightid)){
            weight = targetMap.get(weightid);
            calcflg = true;
        }
        
        if(!calcflg){
            logger.debug("身長、体重の更新がないため計算は実施しない。");
            return null;
        }
        
        /*
        * 計算に必要な項目の取得
        * 登録する項目の中に計算に必要な項目がない場合は、最新の結果を取得する。
        */
        if(tall == null) tall = findLatestValue(phrid,tallid,date);
        if(weight == null) weight = findLatestValue(phrid,weight,date);
        
        if(tall == null || weight == null) return null;

        //計算実施
        //BMIは小数点も考慮するのでDouble型で実施
        double d_tall = Double.parseDouble(tall);
        double d_weight = Double.parseDouble(weight);
        
        double result = d_weight / (d_tall * d_tall / 10000);
        
        /*小数点第３位で四捨五入する
        * ここは項目によって変更する。
        */
        BigDecimal bi = new BigDecimal(String.valueOf(result));
        double resultScale =  bi.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        
        logger.debug("BMIの自動計算終了");

        return String.valueOf(resultScale);
        
    }

    /*
    * eGFR(血清クレアチニン）計算実施メソッド
    * 使用項目は血清クレアチニン[mg/dL]と年齢と性別
    * 年齢と性別は患者基本情報から取得する
    * 年齢は生年月日と検査実施日から取得する
    * 計算に必要になるObservaationDefinitionIdと単位は固定とする
    * 計算を実施するメソッドにおいて返り値は計算結果のStringとする。
    */
        private String calceGFRCre(PatientEntity pentity, String insurerNo, int year, Timestamp date , Map<String, String> targetMap) throws Throwable {
        logger.debug("eGFR(Cre)の自動計算開始");

        boolean calcflg = false;

        //計算に使用するObservationDefinitionIdの宣言及び結果の取得先
        String creid = "0000000008";
        String cre = null;

        String sexCd = pentity.getSexCd();

        Date birthday = pentity.getBirthDate();
        
        Integer age = calcAge(birthday , sdf.parse(date.toString()));
        
        logger.debug("age : " + age);
        /*
        * 計算実施の確認
        * 計算を実施するタイミングは計算に必要な項目が１つでも更新されたときとする
        */
        if(targetMap.containsKey(creid)){
            cre = targetMap.get(creid);
            calcflg = true;
        }
        
        logger.debug("cre : " + cre);
        if(!calcflg){
            logger.debug("血清クレアチニンの更新がないため計算は実施しない。");
            return null;
        }

        
        //計算実施
        //BMIは小数点も考慮するのでDouble型で実施
        double d_cre = Double.parseDouble(cre);
        
        double sexConst = 1.0;
        if(sexCd.equals("F")) sexConst = 0.739;
        
        double result = 194.0*Math.pow(d_cre , -1.094)*Math.pow(age , -0.287) * sexConst;
        
        /*小数点第３位で四捨五入する
        * ここは項目によって変更する。
        */
        BigDecimal bi = new BigDecimal(String.valueOf(result));
        double resultScale =  bi.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        logger.debug("eGFR(Cre)の自動計算終了");

        return String.valueOf(resultScale);
        
    }

    /*
    * eGFR(システチンC）計算実施メソッド
    * 使用項目はシスタチンC[mg/dL]と年齢と性別
    * 年齢と性別は患者基本情報から取得する
    * 年齢は生年月日と検査実施日から取得する
    * 計算に必要になるObservaationDefinitionIdと単位は固定とする
    * 計算を実施するメソッドにおいて返り値は計算結果のStringとする。
    */
        private String calceGFRCys(PatientEntity pentity, String insurerNo, int year, Timestamp date , Map<String, String> targetMap) throws Throwable {
        logger.debug("eGFR(cys)の自動計算開始");

        boolean calcflg = false;

        //計算に使用するObservationDefinitionIdの宣言及び結果の取得先
        String cysid = "0000000040";
        String cys = null;

        String sexCd = pentity.getSexCd();

        Date birthday = pentity.getBirthDate();
        
        Integer age = calcAge(birthday , sdf.parse(date.toString()));
        /*
        * 計算実施の確認
        * 計算を実施するタイミングは計算に必要な項目が１つでも更新されたときとする
        */
        if(targetMap.containsKey(cysid)){
            cys = targetMap.get(cysid);
            calcflg = true;
        }
        
        if(!calcflg){
            logger.debug("シスタチンCの更新がないため計算は実施しない。");
            return null;
        }

        
        //計算実施
        //BMIは小数点も考慮するのでDouble型で実施
        double d_cys = Double.parseDouble(cys);
        
        double sexConst = 1.0;
        if(sexCd.equals("F")) sexConst = 0.929;
        
        double result = (104*Math.pow(d_cys,-1.019)*Math.pow(0.996 , age)*sexConst)-8.0;
        
        /*小数点第３位で四捨五入する
        * ここは項目によって変更する。
        */
        BigDecimal bi = new BigDecimal(String.valueOf(result));
        double resultScale =  bi.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

         logger.debug("eGFR(cys)の自動計算終了");
       
        return String.valueOf(resultScale);
        
    }        
    //年齢計算用メソッド
    private Integer calcAge(Date birthday, Date target) {
        
        logger.debug(sdf.format(birthday));
        logger.debug(sdf.format(target));
        
        
        Integer age = (Integer.parseInt(sdf.format(target)) - Integer.parseInt(sdf.format(birthday))) / 10000;
        
        return age;
        
    }

    /*
    * non-HDLコレステロール計算実施メソッド
    * 使用項目は総コレステロールとHDLコレステロールと性別
    * 計算に必要になるObservaationDefinitionIdと単位は固定とする
    * 計算を実施するメソッドにおいて返り値は計算結果のStringとする。
    */
    private String calcenonHDLC(String phrid, Map<String, String> targetMap , Timestamp date) throws Throwable {
        logger.debug("non-HDLコレステロールの自動計算開始");
        boolean calcflg = false;

        //計算に使用するObservationDefinitionIdの宣言及び結果の取得先
        String tcid = "0000000005";
        String tc = null;

        String hdlid = "0000000006";
        String hdl = null;
        
        /*
        * 計算実施の確認
        * 計算を実施するタイミングは計算に必要な項目が１つでも更新されたときとする
        */
        if(targetMap.containsKey(tcid)){
            tc = targetMap.get(tcid);
            calcflg = true;
        }
        
        if(targetMap.containsKey(hdlid)){
            hdl = targetMap.get(hdlid);
            calcflg = true;
        }
        
        if(!calcflg){
            logger.debug("総コレステロール、HDLコレステロールの更新がないため計算は実施しない。");
            return null;
        }
        
        /*
        * 計算に必要な項目の取得
        * 登録する項目の中に計算に必要な項目がない場合は、最新の結果を取得する。
        */
        if(tc == null) tc = findLatestValue(phrid,tcid,date);
        if(hdl == null) hdl = findLatestValue(phrid,hdlid,date);
        
        if(tc == null || hdl == null) return null;
        //計算実施
        //BMIは小数点も考慮するのでDouble型で実施
        double d_tc = Double.parseDouble(tc);
        double d_hdl = Double.parseDouble(hdl);
        
        double result = d_tc - d_hdl;
        
        /*小数点第３位で四捨五入する
        * ここは項目によって変更する。
        */
        BigDecimal bi = new BigDecimal(String.valueOf(result));
        double resultScale =  bi.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();

        logger.debug("non-HDLコレステロールの自動計算終了");
        
        return String.valueOf(resultScale);
    }
    
}
