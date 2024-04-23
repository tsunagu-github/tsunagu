/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：おしらせ・メッセージ詳細取得クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/20
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
import java.text.SimpleDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.utility.TypeUtility;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.service.ICommunicationService;
import phr.service.impl.CommunicationService;
import phr.web.phone.dto.CommunicationDto;
import phr.web.phone.dto.RequestCommunicationDetailDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseCommunicationListDto;

/**
 *
 * @author kikkawa
 */
public class GetCommunicationDetailFacade extends PhoneFacade {

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
            ICommunicationService service = new CommunicationService();
            RequestCommunicationDetailDto requestDto = gson.fromJson(json, RequestCommunicationDetailDto.class);

            // レスポンスを作成
            ResponseCommunicationListDto responseDto = new ResponseCommunicationListDto();
           
            // プッシュ通知をタップからのアクセスの場合
            if(requestDto.isOnNotification()) {
                // 通知はSeq持ってないから仕方なく、CommuidとPhrIdから検索
                CommunicationReceiverEntity res = service.SearchReceiverByCommuidAndPhrid(requestDto.getId(), requestDto.getPhrId());
                if(res == null) {
                    responseDto.setStatus( ResponseBaseDto.ERROR );
                    responseDto.setMessage( "おしらせ・メッセージ取得エラー" );
                    logger.error("おしらせ・メッセージ Pushからの詳細取得失敗");
                    return responseDto;
                } else {
                    requestDto.setSeq(res.getSeq());
                }
            }
            
            // リスト？
            List<CommunicationEntity> communicationList = service.SearchCommunicationByCommuidAndSeq(requestDto.getId(), requestDto.getSeq());

            // レスポンスのリストを設定する
            List<CommunicationDto> resultList = new ArrayList<>(); 
            if (communicationList.size() > 0) {
                
                // 結果を当てはめる
                for (CommunicationEntity entity : communicationList) {
                    // 念のため宛先or送信元のPhrIDがリクエストと同一のものかを確認
                    if(!requestDto.getPhrId().equals(entity.getPhrid())
                            && !requestDto.getPhrId().equals(entity.getSendPHRID())) {
                        continue;
                    }
                    CommunicationDto dto = new CommunicationDto();
                    dto.setId(entity.getCommunicationId());
                    dto.setSeq(entity.getSeqNo());
                    dto.setInsurerNo(entity.getSendInsurerNo());
                    dto.setOrganizationCd(entity.getSendMedicalOrganizationCd());
                    //本文
                    dto.setBodyText(entity.getBodyText());
                    
                    if(requestDto.isOnNotification()) {
                        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
                        dto.setSenderName(entity.getSenderName());
                        dto.setType(entity.getCommunicationTypeCd());
                        dto.setDate(sdfDate.format(entity.getUpdateDateTime()));
                        dto.setSubject(entity.getSubject());
                    }

                    resultList.add(dto);
                    
                    // readFlgがfalse、且つ、自身送信でない場合、既読化（以下PhrWebから引用）
                    if(!entity.getReadFlg() && !requestDto.getPhrId().equals(entity.getSendPHRID())) {
                        CommunicationReceiverEntity upEntity = new CommunicationReceiverEntity();
                        upEntity.setCommunicationId(entity.getCommunicationId());
                        upEntity.setCreateDateTime(entity.getCreateDateTime());
                        if(TypeUtility.isNullOrEmpty(entity.getInsurerNo())){
                            upEntity.setInsurerNo(null);
                        }else{
                            upEntity.setInsurerNo(entity.getInsurerNo());
                        }
                        if(TypeUtility.isNullOrEmpty(entity.getMedicalOrganizationCd())){
                            upEntity.setMedicalOrganizationCd(null);
                        }else{
                            upEntity.setMedicalOrganizationCd(entity.getMedicalOrganizationCd());
                        }
                        if(TypeUtility.isNullOrEmpty(entity.getPhrid())){
                            upEntity.setPHR_ID(null);
                        }else{
                            upEntity.setPHR_ID(entity.getPhrid());
                        }
                        upEntity.setReadFlg(true);
                        upEntity.setSeq(entity.getSeqNo());
                        upEntity.setUpdateDateTime(entity.getRecUpdateDateTime());
                    
                        //更新
                        if(service.UpdateCommunicationByReadFlg(upEntity)!=1){
                            logger.error("コミュニケーション情報既読化失敗");
                        }else{
                           //更新したので既読で返却
                           //dto.get(0).setReadFlg(true);
                        }
                    }
                }
            }
            
            responseDto.setMsgInfoList(resultList);
            responseDto.setStatus(ResponseBaseDto.SUCCESS);
            
            return responseDto;
            
        } catch (Throwable t) {
            throw new Exception("["+ this.getClass().getSimpleName() +"] Error", t);
        }
    }
}
