/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.alertscript.execute;

import static java.lang.Math.abs;
import java.sql.Timestamp;
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
 * Hbのアラート判別
 * @author kis-note
 */
public class HbA1cAlert  implements IAlertExecuteService {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(HbA1cAlert.class);
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
            Double compValue = 1.0;
            
            String result = service.periodValue(pentity.getPHR_ID(), target, date , days , type);
            
            String value = target.getValue();
            
            Double d_result = Double.parseDouble(result);
            //Double d_value = Double.parseDouble(days);
            Double d_value = Double.parseDouble(value);
            
            //単体チェック
            if(d_value > 8.5) return 1;
            
            if((d_result - d_value) >= compValue){
                return 1;
            }
            return 0;
            
        }    
    
    
}
