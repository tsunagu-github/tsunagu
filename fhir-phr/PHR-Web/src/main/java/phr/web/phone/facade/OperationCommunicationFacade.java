/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：メッセージ操作クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/21
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.service.ICommunicationService;
import phr.service.impl.CommunicationService;
import phr.web.phone.dto.RequestOperationCommunicationDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.CommunicationReceiverDto;

/**
 *
 * @author kikkawa
 */
public class OperationCommunicationFacade extends PhoneFacade {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CreatePatienFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    /**
     * 処理を開始する
     *
     * @param json
     * @return
     */
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {

        ICommunicationService service = new CommunicationService();

        RequestOperationCommunicationDto requestDto = gson.fromJson(json, RequestOperationCommunicationDto.class);

        CommunicationEntity targetEntity = new CommunicationEntity();

        // とりあえず現在新規作成のみ
        targetEntity.setCommunicationTypeCd(2);
        targetEntity.setSendPHRID(requestDto.getPhrId());
        targetEntity.setSenderName(requestDto.getSenderName());
        targetEntity.setSubject(requestDto.getSubject());
        targetEntity.setBodyText(requestDto.getBodyText());

        List<CommunicationReceiverEntity> receiverEntities = new ArrayList<>();
        // 受信者リスト
        for (CommunicationReceiverDto receirver : requestDto.getReceiver()) {
            CommunicationReceiverEntity receiverEntity = new CommunicationReceiverEntity();
            receiverEntity.setInsurerNo(receirver.getInsurerNo());
            receiverEntity.setMedicalOrganizationCd(receirver.getOrganizationCd());
            receiverEntities.add(receiverEntity);
        }
        
        service.CreateCommunicationForPatient(targetEntity, receiverEntities);
        
        ResponseBaseDto responseDto = new ResponseBaseDto();
        responseDto.setStatus( ResponseBaseDto.SUCCESS );
        
        return responseDto;
    }    
}
