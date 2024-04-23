/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：メッセージ宛先リスト取得クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/23
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.ICooperationService;
import phr.service.ICommunicationService;
import phr.service.impl.CooperationService;
import phr.service.impl.CommunicationService;
import phr.datadomain.entity.InsurerEntity;
import phr.web.phone.dto.CommunicationReceiverDto;
import phr.web.phone.dto.RequestBaseDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseCommunicationAddressDto;

/**
 *
 * @author kikkawa
 */
public class GetCommunicationAddressFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ClinicalTestResultFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 処理を開始する
     *
     * @param json
     * @return
     * @throws java.lang.Throwable
     */
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        try {
            ICooperationService service = new CooperationService();
            ICommunicationService comservice = new CommunicationService();
            RequestBaseDto requestDto = gson.fromJson(json, RequestBaseDto.class);
            
            // 保険者
            InsurerEntity insurerResult = comservice.SearchInsurerByPatientPhrid(requestDto.getPhrId());

            // レスポンスを作成
            ResponseCommunicationAddressDto responseDto = new ResponseCommunicationAddressDto();
           
            // レスポンスのリストを設定する
            List<CommunicationReceiverDto> resultList = new ArrayList<>(); 
            if (insurerResult != null ) {
                CommunicationReceiverDto dto = new CommunicationReceiverDto();
                dto.setName(insurerResult.getInsurerName());
                dto.setInsurerNo(insurerResult.getInsurerNo());
                resultList.add(dto);
            }

            responseDto.setAddressList(resultList);
            responseDto.setStatus(ResponseBaseDto.SUCCESS);
            
            return responseDto;
            
        } catch (Throwable t) {
            throw new Exception("["+ this.getClass().getSimpleName() +"] Error", t);
        }
    }
}
