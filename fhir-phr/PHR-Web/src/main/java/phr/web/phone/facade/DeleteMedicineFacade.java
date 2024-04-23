/**！！未完成！！
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：おくすり削除クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/07
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.IDosegeSetDeleteService;
import phr.service.impl.DosegeSetDeleteService;
import phr.web.phone.dto.RequestMedicineDeletDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseMedicineListDto;
import phr.web.phone.dto.ResponseMedicineUpdateDto;

/**
 *
 * @author iwaasa
 */
public class DeleteMedicineFacade extends PhoneFacade{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog( DeleteMedicineFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();    

    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        IDosegeSetDeleteService deleteService = new DosegeSetDeleteService();
        
        RequestMedicineDeletDto requestDto = gson.fromJson(json, RequestMedicineDeletDto.class);
//        String seqString = requestDto.getSeq();
//        int seq = Integer.parseInt(seqString);
//       int result = deleteService.deleteByDosageId(requestDto.getDosageId(),seq);
       int result = deleteService.deleteByDosageId(requestDto.getDosageId());
        
        
       // GetMedicineListFacade responseFacade = new GetMedicineListFacade();
       // Date today = new Date();
       // SimpleDateFormat sf = new SimpleDateFormat("YYYY/MM/DD");
       // String requestJson = "{\"action\":\"GetMedicineList\",\"phrId\":\"" + requestDto.getPhrId() + "\",\"baseDate\":\" " + sf.format(today) + "\"}";
       // ResponseBaseDto responseDto = responseFacade.execute(requestJson);
       ResponseBaseDto responseDto = new ResponseMedicineUpdateDto(); 
       if(result>0){
           responseDto.setStatus(ResponseBaseDto.SUCCESS);
       }else{
            responseDto.setStatus(ResponseBaseDto.ERROR);
            responseDto.setMessage("お薬の削除中にエラーが発生しました");           
       }
        return responseDto;
    }
    //"{"action":"GetMedicineList","phrId":"1234567-000017","baseDate":"2016/09/12","timestamp":"1473657422818","keyValue":"72d35447515b043da6a5eb4fc3e17163f59bca864958d6e2b0f5199ca3332970e91103c10e10fca115fc7be2c9c8acb7fa16df94a138dde30abc16d5a776ae3d"}"
}
