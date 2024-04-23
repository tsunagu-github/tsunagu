/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;

/**
 *
 * @author kikkawa
 */
public class FCMNotificationServiceTest {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(FCMNotificationServiceTest.class);
    
    private static FCMNotificationService service;

    
    @BeforeClass
    public static void setUpClass() {
        service = spy(new FCMNotificationService());
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of sendFCMMessage method, of class FCMNotificationService.
     */
    @Ignore
    @Test
    public void testSendFCMMessage() throws Throwable {
        System.out.println("sendFCMMessage");
        
        //jp.kisinc.phrで発行。Androidエミュレータのトークン(kis-note-014のAVDのNexus_5_Marshmallow、2016/12時点)
        String DeviceToken1 = "dPZadJqo4m8:APA91bE06E97l1HHeX8WgOEsu_eIqml3QQaaVEzRmbAfGM7QvpkIIit1cQNpIE5QqZxYNSouTlzkNEsviBXLPw_F1kkcg_2qH0kugQzufTmDh9Y96CDdEnAKCgxbOlNqsq4TlsiHLelL";
        //jp.kisinc.notificationsTestで発行。my実機(Android6.0、2016/12時点)
        //String DeviceToken2 = "eN7YGyiBwco:APA91bHYKr8mg4TYVr_R2SyosQFziXuukDelUr5yoQVpRKgjLpU0CrtDTpxk4a1XgaVlqi06S1OtUn6Bmq5ZKUD37AkUwmnNZ2XRznlYef4uiIsIJdhfaWiJZQbg-eyvkfnFaGqvLofl";

        //tokenIdが空でないものの順序付きマップ
        Map tokenMap = new LinkedHashMap();
        tokenMap.put("1234567-000006", DeviceToken1);
        //tokenMap.put("1234567-000001", DeviceToken2);

        doReturn(tokenMap).when(service).getTokenMap(anyObject());

        // serviceのgetToken()メソッドを一部引数でモック化
        //doReturn(DeviceToken1).when(service).getToken("1234567-000000");
        //doReturn(DeviceToken2).when(service).getToken("1234567-000001");

        // serviceのupdateToken()メソッドをモック化
        doReturn(true).when(service).updateToken((String)anyObject(), (String)anyObject());

        
        CommunicationEntity entity = new CommunicationEntity();
        
        String communicationId = "000000000001039";
        entity.setCommunicationTypeCd(2);
        entity.setSendInsurerNo("1234567");
        entity.setSenderName("テスト送信者");
        entity.setSubject("にほんごタイトル");
        entity.setBodyText("にほんごテキスト");
        entity.setCommunicationId(communicationId);
        
        List<CommunicationReceiverEntity> receiverList = new ArrayList<>();
        // 受信者リスト
            // 受信者(1)
            CommunicationReceiverEntity receiverEntity = new CommunicationReceiverEntity();
            receiverEntity.setCommunicationId(communicationId);
            receiverEntity.setSeq(1);
            receiverEntity.setPHR_ID("1234567-000006");
            receiverList.add(receiverEntity);
            // 受信者(2)
            CommunicationReceiverEntity receiverEntity2 = new CommunicationReceiverEntity();
            receiverEntity2.setCommunicationId(communicationId);
            receiverEntity2.setSeq(1);
            receiverEntity2.setPHR_ID("1234567-000001");
            //receiverList.add(receiverEntity2);

        int retries = 1;
        int successCount = service.sendFCMMessage(entity, receiverList, retries);

        assertEquals(receiverList.size(), successCount);
    }

}
