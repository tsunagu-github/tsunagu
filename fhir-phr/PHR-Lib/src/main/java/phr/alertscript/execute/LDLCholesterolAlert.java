/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.alertscript.execute;

import static java.lang.Math.abs;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.adapter.ObservationAdapter;
import phr.datadomain.entity.ObservationDefinitionScriptEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.IAlertExecuteService;
import phr.service.IAlertSpecificService;
import phr.service.impl.AlertSpecificService;

/**
 * ALTのアラート判別
 * @author kis-note
 */
public class LDLCholesterolAlert  implements IAlertExecuteService {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(LDLCholesterolAlert.class);
   /**
     * 抽出を行うメインサービス
     * @param
     * @return
     * @throws Throwable
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Integer getAlert(ObservationDefinitionScriptEntity entity, ObservationEntity target,PatientEntity pentity,Timestamp date) throws Throwable{
            logger.debug("start");
            IAlertSpecificService service = new AlertSpecificService();
            
            //取得期間　遡る時は-をつける
            String days = "-3";
            //期間の種別　1:日　2:月
            Integer type = 2;
            //比較値
            Double compValue = 50.0;
            
            String result = service.periodValue(pentity.getPHR_ID(), target, date , days , type);
            
            String value = target.getValue();
            
            Double d_result = Double.parseDouble(result);
            //Double d_value = Double.parseDouble(days);
            Double d_value = Double.parseDouble(value);

            if((d_value - d_result) >= compValue){
                return 1;
            }
            
            //年齢計算
            int age = calcAge(pentity.getBirthDate(),date);
            
            //因子
            int factorcount = 0;
            ObservationEntity obs = new ObservationEntity();
            obs.setObservationDefinitionId("0000000007");
            String smoking = service.periodValue(pentity.getPHR_ID(), obs, date , "-24" , type);//喫煙　1:あり,2;なし,3:過去にあり
            if(getSmokingInfo(smoking)){factorcount=factorcount+1;};
            obs.setObservationDefinitionId("0000000015");
            String hyperTension = service.periodValue(pentity.getPHR_ID(), obs, date , "-480" , type);//高血圧診断年齢
            if(getDiagnosisAgeInfo(hyperTension)){factorcount=factorcount+1;};
            obs.setObservationDefinitionId("0000000006");
            String hDLch = service.periodValue(pentity.getPHR_ID(), obs, date , "-12" , type);//HDLコレステロール値
            if(getHDLchInfo(hDLch)){factorcount=factorcount+1;};
            //高リスク因子
            boolean highliskflg = false;
            obs.setObservationDefinitionId("0000000011");
            String diabete = service.periodValue(pentity.getPHR_ID(), obs, date , "-480" , type);//糖尿病診断年齢
            if(getDiagnosisAgeInfo(diabete)){highliskflg = true;};
            obs.setObservationDefinitionId("0000000021");
            String cKD = service.periodValue(pentity.getPHR_ID(), obs, date , "-480" , type);//CKD診断年齢
            if(getDiagnosisAgeInfo(cKD)){highliskflg = true;};
             //冠動脈疾患の既往
            obs.setObservationDefinitionId("0000000020");
            String caDisease = service.periodValue(pentity.getPHR_ID(), obs, date , "-24" , type);
            boolean caDiseaseflg = getCADiseaseInfo(caDisease);
            
            
            
            //上限値
            Double limitValue = getLimitValue(pentity.getSexCd(),age,highliskflg, factorcount,caDiseaseflg);
            
            if(d_value > limitValue){
                return 1;
            }

            return 0;
            
        }    

    private int calcAge(Date birthDate, Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        
        String birth = sdf.format(birthDate);
        String cdate = sdf.format(date.getTime());

        int i_birth = Integer.parseInt(birth);
        int i_cdate = Integer.parseInt(cdate);
        
        int age = (i_cdate - i_birth)/10000;
        
        return age;
    }
    
    private boolean getDiagnosisAgeInfo(String checkData){
        if(checkData==null){
            return false;
        }else{
            return true;
        }
    }
    
    private boolean getHDLchInfo(String checkData){
        if(checkData==null){
            return false;
        }else{
            Double d_HDL = Double.parseDouble(checkData);
            if(d_HDL < 40.0){
                return true;
            }else{
                return false;
            }
        }
    }
    
    private boolean getSmokingInfo(String checkData){
        if(checkData==null){
            return false;
        }else{
            Double d_smoking = Double.parseDouble(checkData);
            if(d_smoking < 2.0){
                return true;
            }else{
                return false;
            }
        }
    }
    private boolean getCADiseaseInfo(String checkData){
        if(checkData==null){
            return false;
        }else{
            Double d_CAD = Double.parseDouble(checkData);
            if(d_CAD < 3.0){
                return true;
            }else{
                return false;
            }
        }
    }
    
    private Double getLimitValue(String sexCd,int age, boolean highliskflg,int factorcount,boolean caDiseaseflg){
        Double specialLisk = 100.0;
        Double heighLisk = 120.0;
        Double middleLisk = 140.0;
        Double lowLisk = 160.0;
        
        if(caDiseaseflg){
            return specialLisk;
        }
        if(highliskflg){
            return heighLisk;
        }

        if(sexCd.equals("M")){            
            if(age >= 60){
                //男性60歳以上
                if(factorcount>=1){
                    return heighLisk;
                }else{
                    return middleLisk;
                }
            }else if(age >= 40){
                //男性40歳以上
                if(factorcount<=1){
                    return middleLisk; 
                }else{
                    return heighLisk;
                }   
            }else{
                //男性40歳以下
                return lowLisk;
            }
        }else{
            if(age >= 60){
                //女性60歳以上
                if(factorcount>=2){
                    return heighLisk;                    
                }else{
                    return middleLisk;                    
                }
            }else if(age >= 40){
                //女性40歳以上
                if(factorcount<=1){
                    return lowLisk; 
                }else{
                    return middleLisk;
                }   
            }else{
                //女性40歳以下
                return lowLisk;
            }            
        }
    }
    
}
