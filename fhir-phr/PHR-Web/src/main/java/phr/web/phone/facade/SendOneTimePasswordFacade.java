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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.InsurerEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.ICommunicationService;
import phr.service.IPatientManagementService;
import phr.service.impl.CommunicationService;
import phr.service.impl.PatientManagementService;
import phr.web.phone.dto.RequestSendOneTimePasswordDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseSendOneTimePasswordDto;

/**
 *
 * @author chiba
 */
public class SendOneTimePasswordFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(GetOneTimePasswordFacade.class);

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
        
        RequestSendOneTimePasswordDto requestDto = gson.fromJson(json, RequestSendOneTimePasswordDto.class);
        ResponseSendOneTimePasswordDto responseDto = new ResponseSendOneTimePasswordDto();
        
        //  通知先の保険者情報
        ICommunicationService communicationService = new CommunicationService();
        InsurerEntity insurerEntity = communicationService.SearchInsurerByPatientPhrid( requestDto.getPhrId() );

        //  通知のタイトル
        IPatientManagementService patientService = new PatientManagementService();
        PatientEntity patienInfomation = patientService.getPatient( requestDto.getPhrId() );
        String messageTitle = "ワンタイムパスワード通知（名称不詳）";
        String patientName="名称不詳";
        if( patienInfomation != null ){
            patientName = patienInfomation.getFamilyName()+ patienInfomation.getGivenName();
            messageTitle = messageTitle.replaceAll( "名称不詳", patientName );
        }

        //  通知先の本文
        StringBuilder bodyText = new StringBuilder();
        String cr = System.getProperty("line.separator");
        bodyText.append( patientName ).append( " さんのアプリから送信されたワンタイムパスワード通知メッセージです。").append(cr);
        bodyText.append( "━━━━━━━━━━━━━━━━━━━" ).append(cr);
        bodyText.append( " PHR-ID" ).append(cr);
        bodyText.append( "　" ).append(requestDto.getPhrId()).append(cr);
        bodyText.append( " パスワード" ).append(cr);
        bodyText.append( "　" ).append(requestDto.getPassword()).append(cr);
        bodyText.append( " 有効期限" ).append(cr);
        bodyText.append( "　" ).append(requestDto.getExpirationDate()).append(cr);
        bodyText.append( "━━━━━━━━━━━━━━━━━━━" ).append(cr);
        bodyText.append( " このメッセージには返信しないでください。" ).append(cr);

        //  メッセージ送信
        CommunicationEntity targetEntity = new CommunicationEntity();
        targetEntity.setCommunicationTypeCd(2);
        targetEntity.setSendPHRID(requestDto.getPhrId());
        targetEntity.setSenderName(patientName);
        targetEntity.setSubject(messageTitle);
        targetEntity.setBodyText(bodyText.toString());

        List<CommunicationReceiverEntity> receiverEntities = new ArrayList<>();
        CommunicationReceiverEntity addressEntity = new CommunicationReceiverEntity();
        addressEntity.setInsurerNo( insurerEntity.getInsurerNo() );
        receiverEntities.add(addressEntity);

        communicationService.CreateCommunicationForPatient(targetEntity, receiverEntities);
        //  ↑成否返してこないため↓固定  
        boolean result = true;
        if( result ){
            //　成功
            responseDto.setStatus( ResponseBaseDto.SUCCESS );
            responseDto.setMessage( "ワンタイムパスワードを送信しました" );
        }else{
            //  失敗
            responseDto.setStatus( ResponseBaseDto.ERROR );
            responseDto.setMessage( "ワンタイムパスワード送信エラーです。" );
        }
        return responseDto;
    }
    
}
