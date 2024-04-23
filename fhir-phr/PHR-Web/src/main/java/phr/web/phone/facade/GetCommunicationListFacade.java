/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：おしらせ・メッセージ取得クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/16
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.CommunicationEntity;
import phr.service.ICommunicationService;
import phr.service.impl.CommunicationService;
import phr.web.phone.dto.CommunicationDto;
import phr.web.phone.dto.RequestBaseDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseCommunicationListDto;

/**
 *
 * @author kikkawa
 */
public class GetCommunicationListFacade extends PhoneFacade {

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
            RequestBaseDto requestDto = gson.fromJson(json, RequestBaseDto.class);

            // 現在日付を取得
            // FIXME: 1年前までしかとってこれないようになっている。それ以前のが見たいとなったら改修が必要
            LocalDateTime d = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(d);
            
            List<CommunicationEntity> communicationList = service.SearchCommunicationByPhridForPatient(requestDto.getPhrId(), timestamp);

            // レスポンスを作成
            ResponseCommunicationListDto responseDto = new ResponseCommunicationListDto();
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
           
            // レスポンスのリストを設定する
            List<CommunicationDto> resultList = new ArrayList<>();
            int infoUnreadCount = 0;
            int msgUnreadCount = 0;
            if (communicationList.size() > 0) {
                
                // 結果を当てはめる
                for (CommunicationEntity entity : communicationList) {
                    CommunicationDto dto = new CommunicationDto();
                    dto.setId(entity.getCommunicationId());
                    dto.setSeq(entity.getSeqNo());
                    dto.setSenderName(entity.getSenderName());
                    dto.setType(entity.getCommunicationTypeCd());
                    dto.setDate(sdfDate.format(entity.getUpdateDateTime()));
                    dto.setSubject(entity.getSubject());
                    // 本文はリストに含めない
                    //dto.setBodyText(entity.getBodyText());
                    dto.setReadFlg(entity.getReadFlg());
                    if(!entity.getReadFlg()) {
                        //種別がメッセージの場合
                        if(entity.getCommunicationTypeCd() == 2) {
                            // 自分が送信フラグ
                            if(requestDto.getPhrId().equals(entity.getSendPHRID())) {
                            }else{
                                msgUnreadCount++;
                            }
                        } else {
                            //3と9も、返信できないという意味ではおしらせ？
                            infoUnreadCount++;
                        }
                    }
                    // 自分が送信フラグ
                    if(requestDto.getPhrId().equals(entity.getSendPHRID())) {
                        dto.setSelf(true);
                    }
                    resultList.add(dto);
                }
            }
            
            responseDto.setMsgInfoList(resultList);
            responseDto.setAllUnreadCount(msgUnreadCount+infoUnreadCount);
            responseDto.setInfoUnreadCount(infoUnreadCount);
            responseDto.setMsgUnreadCount(msgUnreadCount);
            responseDto.setStatus(ResponseBaseDto.SUCCESS);
            
            return responseDto;
            
        } catch (Throwable t) {
            throw new Exception("["+ this.getClass().getSimpleName() +"] Error", t);
        }
    }
}
