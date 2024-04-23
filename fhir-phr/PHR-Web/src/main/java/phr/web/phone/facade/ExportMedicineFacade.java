/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.dto.DosageEntitySetDto;
import phr.service.IDosageExportService;
import phr.service.IDosegeSetDeleteService;
import phr.service.IExportMedicineListService;
import phr.service.impl.DosageExportService;
import phr.service.impl.DosegeSetDeleteService;
import phr.service.impl.ExportMedicineListService;
import phr.web.phone.dto.RequestMedicineListDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseMedicineUpdateDto;

/**
 *
 * @author kis-note-027_user
 */
public class ExportMedicineFacade extends PhoneFacade{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ExportMedicineFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        IExportMedicineListService service = new ExportMedicineListService();
        
        RequestMedicineListDto requestDto = gson.fromJson(json, RequestMedicineListDto.class);
        String phrId = requestDto.getPhrId();
        
        //PHR-IDで全お薬情報の取得（１つの調剤単位で1つのString）
        Map<String, String> exportMedicineList = service.getExportMedicineList(phrId);
        
        //出力処理
        
        
       ResponseBaseDto responseDto = new ResponseMedicineUpdateDto(); 
       responseDto.setStatus(ResponseBaseDto.SUCCESS);
       return responseDto;
        
    }
}
