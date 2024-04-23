/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：おくすりリスト更新クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/11/07
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.DosagePatientInputEntity;
import phr.service.IDosageUpdateService;
import phr.service.impl.DosageUpdateService;
import phr.web.phone.dto.RequestMedicinePatientInputDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseMedicineUpdateDto;

/**
 *
 * @author iwaasa
 */
public class MedicinePatientInputFacade extends PhoneFacade{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicinePatientInputFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
       RequestMedicinePatientInputDto requestDto = gson.fromJson(json, RequestMedicinePatientInputDto.class);
       IDosageUpdateService service = new DosageUpdateService();
       String dosageId = requestDto.getDosageId();
       DosagePatientInputEntity[] entityList = requestDto.getPatientInputList();
       List<DosagePatientInputEntity> inputEntityList = new ArrayList();
       for(int i=0;i<entityList.length;i++){
           //dosageIdがないものは、新規追加
           
           if(entityList[i].getDosageId()==null ||entityList[i].getDosageId().isEmpty()){
               entityList[i].setDosageId(dosageId);
               entityList[i].setSeq(1);
               int num = entityList.length;
               entityList[i].setInputSeq(num);
               entityList[i].setInputDate(new Date());
           }
           if(entityList[i].getInputText()==null || entityList[i].getInputText().isEmpty()){
           }else{
               String str = changeComma(entityList[i].getInputText());
               entityList[i].setInputText(str);
           }
           inputEntityList.add(entityList[i]);
       }
       
       int result;

       result = service.updatePatientInput(inputEntityList);
       
       ResponseBaseDto responseDto = new ResponseMedicineUpdateDto(); 
       responseDto.setStatus(ResponseBaseDto.SUCCESS);
       return responseDto;
    }
    
    private String changeComma(String targetString){
        String resultString = "";
        if(targetString.contains(",")){
            resultString = targetString.replace(",", "，");
        }else{
            resultString = targetString;
        }
        return resultString;
    } 
}
