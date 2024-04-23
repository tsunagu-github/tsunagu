/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.PhrConfig;
import phr.config.SystemConfigConst;
import phr.fcm.server.Message;
import phr.fcm.server.Notification;
import phr.fcm.server.Sender;
import phr.fcm.server.Result;
import phr.fcm.server.MulticastResult;
import phr.fcm.server.Constants;
import phr.fcm.server.InvalidRequestException;
import phr.service.IFCMNotificationService;
import phr.utility.TypeUtility;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.PatientAdapter;

/**
 *
 * @author KISO-NOTE-014
 */
public class FCMNotificationService implements IFCMNotificationService{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(FCMNotificationService.class);
    
    /**
     * FCMサーバーに通知リクエストをPostします。
     * @param entity CommunicationEntity
     * @param receiverList CommunicationReceiverEntityのリスト(communicationIdとseqの設定されているもの)
     * @param retries 
     * @return success count
     * @Throwable 
     */
    @Override
    public int sendFCMMessage(CommunicationEntity entity, List<CommunicationReceiverEntity> receiverList, int retries) throws Throwable
    {
        logger.debug("Start");

        //ログに出す用のCommunicationReceiverシーケンス
        Map seqMap = new HashMap<>();
        //宛先のPhrIDのリスト(患者宛てのもの)
        List<String> inPhridList = new ArrayList<>();

        //宛先PhrIDリスト
        if(receiverList != null && receiverList.size() > 0) {
            for(CommunicationReceiverEntity receiver : receiverList) {
                String phrid = receiver.getPHR_ID();
                if(!TypeUtility.isNull(phrid)) {
                    inPhridList.add(phrid);
                    seqMap.put(phrid, receiver.getSeq());
                } else {
                    String errorMsg = " (" + entity.getCommunicationId() + ",seq " + receiver.getSeq() + ")";
                    logger.debug(errorMsg + " : 宛先がPatientでないため、スキップします");
                }
            }
        }
        
        //宛先のPhrIDのうち、tokenIdが空でないものの順序付きマップを取得
        Map tokenMap = getTokenMap(inPhridList);

        //tokenListを取得し、対応するphrIdList取得
        List<String> tokenList = new ArrayList<>(tokenMap.values());
        List<String> phrIdList = new ArrayList<>(tokenMap.keySet());

        int successCount = 0;
        
        //tokenを持った患者がいない場合
        if(tokenList.size() < 1) {
            logger.debug("End");
            return successCount;
        }

        //通知規定部分オブジェクト作成
        Notification notification = createNotification(
                entity.getSenderName(), //タイトル (Android)
                entity.getSubject(),    //本文 (iOS,Android)
                "fcm_push_icon",        //アイコン (Android)
                null,                   //音 (iOS,Android)
                1,                      //ホームアイコン上バッジ (iOS)
                Integer.toString(entity.getCommunicationTypeCd()),//通知タグ (Android)
                "#8a2be2",              //アイコン色 (Android)
                "FCM_PLUGIN_ACTIVITY"   //アクション (iOS,Android)
        );

        Map<String, String> map = new HashMap<>();
        String DATA_KEY = "communication_id";
        map.put(DATA_KEY, entity.getCommunicationId());
        map.put("phr_subject", entity.getSubject());
        map.put("phr_bodyText", entity.getBodyText());

        //通知メッセージオブジェクト作成
        //Message message = createMessageDryRun(
        //※↑テスト用カラ送信
        // ↓実際に送るときはこっち
        Message message = createMessage(
                notification,           //規定部分オブジェクト
                Message.Priority.HIGH,  //優先度
                null,                   //保持期間（秒単位）
                map,                    //カスタム Key-Value
                null                    //まとめキー
        );
        /*logger.debug("message   : " + message);
        for(String receiver : tokenList) {
            logger.debug("tokenList : " + receiver);
        }*/

        if(tokenList.size() == 1) {
            Result result = doPost(message, tokenList.get(0), retries);
            logger.debug(result);
            if(result != null) {
                String phrid = phrIdList.get(0);
                boolean successFlg = checkResult(result, entity.getCommunicationId(), (int)seqMap.get(phrid), phrid);
                //successCount += result.getSuccess();
                //result.getSuccess()がnullでないのはグループ宛ての場合
                if(successFlg) successCount += 1;
            }
        } else {
            MulticastResult multicastResult = doPost(message, tokenList, retries);
            logger.debug(multicastResult);
            List<Result> results = multicastResult.getResults();
            
            if(results != null) {
                int i = 0;
                for(Result result : results) {
                    String phrid = phrIdList.get(i);
                    checkResult(result, entity.getCommunicationId(), (int)seqMap.get(phrid), phrid);
                    i++;
                }
            }
            successCount += multicastResult.getSuccess();
        }

        logger.debug("End");
        return successCount;
    }

    /**
     * FCMサーバーからのResponseをチェックします。
     */
    private boolean checkResult(Result result, String communicationId, int seq, String phrid) throws Throwable
    {
        if(result != null) {
            if(result.getMessageId() == null) {
                //エラー
                String errorCd = result.getErrorCodeName();
                String errorMsg = errorCd + " (" + communicationId + ",seq " + seq + ")";
                switch (errorCd) {
                    case Constants.ERROR_MISSING_REGISTRATION :
                        logger.error(errorMsg + " : 登録トークンがリクエストに含まれているか調べてください");
                        break;
                    case Constants.ERROR_INVALID_REGISTRATION :
                        logger.error(errorMsg + " : サーバーに渡す登録トークンの形式を確認してください");
                        break;
                    case Constants.ERROR_NOT_REGISTERED :
                        updateToken(phrid, null);
                        logger.error(errorMsg + " : アプリサーバーから既存の登録トークンを削除し、これを使用したメッセージの送信を停止してください");
                        break;
                    case "InvalidPackageName" :
                        logger.error(errorMsg + " : メッセージの宛先は、リクエストで渡された値に一致するパッケージ名を持つ登録トークンにしてください");
                        break;
                    case Constants.ERROR_MISMATCH_SENDER_ID :
                        logger.error(errorMsg + " : 登録トークンは、特定の送信者グループに結び付けられています");
                        break;
                    case Constants.ERROR_MESSAGE_TOO_BIG :
                        logger.error(errorMsg + " : メッセージに含まれているペイロード データの合計サイズが、FCM の上限を超えていないことを確認してください");
                        break;
                    case "InvalidDataKey" :
                        logger.error(errorMsg + " : ペイロード データに、FCM 内部で使用されるキー（from、gcm、先頭に google が付けられたすべての値など）が含まれていないことを確認してください");
                        break;
                    case Constants.ERROR_INVALID_TTL :
                        logger.error(errorMsg + " : time_to_live で使用される値が、0～2,419,200（4 週間）の期間（秒単位）を示す整数であることを確認してください");
                        break;
                    case Constants.ERROR_UNAVAILABLE :
                        //TODO: リトライしないでログに出すだけでいいか検討
                        logger.error(errorMsg + " : サーバーで時間内にリクエストを処理できませんでした。同じリクエストを再試行してください");
                        break;
                    case Constants.ERROR_INTERNAL_SERVER_ERROR :
                        //TODO: リトライしないでログに出すだけでいいか検討
                        logger.error(errorMsg + " : リクエストを処理しようとしてサーバーでエラーが発生しました");
                        break;
                    case "DeviceMessageRateExceeded" :
                        logger.error(errorMsg + " : 特定の端末へのメッセージ レートが高すぎます。この端末に送信されるメッセージの数を減らして、送信を再試行するまでにしばらく時間を空けてください");
                        break;
                    case "TopicsMessageRateExceeded" :
                        logger.error(errorMsg + " : 特定のトピックの登録者へのメッセージ レートが高すぎます。このトピックについて送信されるメッセージの数を減らして、送信を再試行するまでにしばらく時間を空けてください");
                        break;
                    default :
                }
            } else {
                //成功
                String registrationId = result.getCanonicalRegistrationId();
                if(registrationId != null) {
                    //GCMのregistrationIdが更新されている場合
                    updateToken(phrid, registrationId);
                    logger.debug("正規Registration_IDは更新されています。今後はこちらを指定してください。 new id: " + registrationId);
                }
                return true;
            }
        }

        return false;
    }

    /**
     * PHR_IDリストから、患者のデバイスのトークンマップを取得します。
     * @param phrIdList 
     * @return token map
     * @Throwable 
     */
    public Map getTokenMap(List<String> phrIdList) throws Throwable
    {
        logger.debug("Start");
        Map tokenMap = null;
        DataAccessObject dao = null;

        try {
            
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            tokenMap = adapter.findPatientTokenMap(phrIdList);

        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }

        logger.debug("End");
        return tokenMap;
    }

    /**
     * 対象の患者のデバイスのFCMトークンを更新します。
     * @param phrid 
     * @param newToken 
     * @return 
     * @Throwable 
     */
    public boolean updateToken(String phrid, String newToken) throws Throwable
    {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            PatientAdapter adapter = new PatientAdapter(dao.getConnection());
            dao.beginTransaction();
            int iRet = adapter.updatePatientToken(phrid, newToken);
            dao.commitTransaction();

            logger.debug("End");
            return iRet>0;
        } catch (Throwable ex) {
            logger.error("", ex);
            if(dao!=null){dao.rollbackTransaction();}
            throw ex;
        } finally {
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }
    }
    
    /**
     * FCMサーバーにPostします。
     * @param message message to be sent, including the device's registration id.
     * @param to registration token or topic where the message will be sent.
     * @param retries number of retries in case of service unavailability errors.
     * @return 
     * @Throwable 
     */
    @Override
    public Result doPost(Message message, String to, int retries) throws Throwable
    {
        logger.debug("Start");
        // ※ FCMTokenとは、かつてServer Keyだったものです。
        String FCMToken = PhrConfig.getSystemConfigProperty(SystemConfigConst.FCM_TOKEN);

        Result result = null;
        Sender sender = new Sender(FCMToken);
        
        try {
            result = sender.send(message, to, retries);
        } catch (IllegalArgumentException | InvalidRequestException ex) {
            logger.error("再試行する前に確認してください。" + ex.getMessage());
        } catch (IOException ex) {
            logger.error("送信に失敗しました。" + ex.getMessage());
        } finally {
            logger.debug("End");
        }

        return result;
    }
    
    /**
     * FCMサーバーに配信先複数でPostします。
     * @param message message to be sent, including the device's registration id.
     * @param tokens registration id of the devices that will receive the message.
     * @param retries number of retries in case of service unavailability errors.
     * @return 
     * @Throwable 
     */
    @Override
    public MulticastResult doPost(Message message, List<String> tokens, int retries) throws Throwable
    {
        logger.debug("Start");
        // ※ FCMTokenとは、かつてServer Keyだったものです。
        String FCMToken = PhrConfig.getSystemConfigProperty(SystemConfigConst.FCM_TOKEN);

        MulticastResult results = null;
        Sender sender = new Sender(FCMToken);
        
        try {
            results = sender.send(message, tokens, retries);
        } catch (IllegalArgumentException | InvalidRequestException ex) {
            logger.error("再試行する前に確認してください。" + ex.getMessage());
        } catch (IOException ex) {
            logger.error("送信に失敗しました。" + ex.getMessage());
        } finally {
            logger.debug("End");
        }

        return results;
    }
    
    /**
     * 通知メッセージを作成します。
     * @param notification 通知ペイロードの事前定義済みでユーザーに表示される Key-Value ペア
     * @param priority メッセージの優先度。「normal」or「high」
     * @param timeToLive 端末がオフラインの場合にメッセージを FCM のストレージに保持する期間（秒単位）最大４週間
     * @param dataKeyValue メッセージのペイロードのカスタム Key-Value ペア
     * @param collapseKey 端末がオンラインに戻ったときなどに、同じメッセージが過剰に送信されないようにするためのまとめキー(最大４つ)
     * @return 
     * @Throwable 
     */
    @Override
    public Message createMessage(Notification notification, Message.Priority priority, Integer timeToLive, Map<String, String> dataKeyValue, String collapseKey) throws Throwable
    {
        Message.Builder builder = createMessageBuilder(notification, priority, timeToLive, dataKeyValue, collapseKey, false);
        
        return builder.build();
    }
    
    /**
     * リクエストテスト用、配信なし通知メッセージを作成します。
     * @param notification 通知ペイロードの事前定義済みでユーザーに表示される Key-Value ペア
     * @param priority メッセージの優先度。「normal」or「high」
     * @param timeToLive 端末がオフラインの場合にメッセージを FCM のストレージに保持する期間（秒単位）最大４週間
     * @param dataKeyValue メッセージのペイロードのカスタム Key-Value ペア
     * @param collapseKey 端末がオンラインに戻ったときなどに、同じメッセージが過剰に送信されないようにするためのまとめキー(最大４つ)
     * @return 
     * @Throwable 
     */
    @Override
    public Message createMessageDryRun(Notification notification, Message.Priority priority, Integer timeToLive, Map<String, String> dataKeyValue, String collapseKey) throws Throwable
    {
        Message.Builder builder = createMessageBuilder(notification, priority, timeToLive, dataKeyValue, collapseKey, true);
        
        return builder.build();
    }
    
    private Message.Builder createMessageBuilder(Notification notification, Message.Priority priority, Integer timeToLive, Map<String, String> dataKeyValue, String collapseKey, boolean dryRun) throws Throwable
    {
        Message.Builder builder = new Message.Builder();
        if (!TypeUtility.isNull(notification)) builder.notification(notification);
        if (!TypeUtility.isNull(timeToLive)) builder.timeToLive(timeToLive);
        if (!TypeUtility.isNull(priority)) builder.priority(priority);
        if (!TypeUtility.isNull(collapseKey)) builder.collapseKey(collapseKey);
        builder.dryRun(dryRun);

        if (!TypeUtility.isNull(dataKeyValue)) {
            for(Map.Entry entry : dataKeyValue.entrySet()) {
                builder.addData(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        
        return builder;
    }
    
    /**
     * 通知メッセージのうち、通知領域に表示される規定部分を作成します。
     * @param title [iOS(ウォッチ),Android]: 通知タイトル。このフィールドは iOS のスマートフォンやタブレットには表示されません。
     * @param body [iOS,Android]: 通知の本文テキストを示します。 
     * @param icon [Android]: 通知アイコン
     * @param sound [iOS,Android]: 通知音。Android の通知音ファイルは、/res/raw/ に置く必要があり、iOS の通知音ファイルはクライアント アプリのメインバンドルにあるか、アプリのデータコンテナの Library/Sounds フォルダ
     * @param badge [iOS]: クライアント アプリのホームアイコン上のバッジを示します。
     * @param tag [Android]: 各通知によって Android 上の通知ドロワーに新しいエントリが作成されるかどうかを示します。 
     * @param color [Android]: アイコンの色を #rrggbb 形式で表します。
     * @param click_action [iOS,Android]: ユーザーが通知をクリックする操作に関連付けられたアクションを示します。
     * @return 
     * @Throwable 
     */
    @Override
    public Notification createNotification(String title, String body, String icon, String sound, Integer badge, String tag, String color, String click_action) throws Throwable
    {
        String notifIcon = TypeUtility.isNull(icon) ? "icon" : icon ;
        Notification.Builder builder = new Notification.Builder(notifIcon);
        String notifTitle = TypeUtility.isNull(title) ? "PHR" : title ;
        builder.title(notifTitle);
        if (!TypeUtility.isNull(body)) builder.body(body);
        if (!TypeUtility.isNull(sound)) builder.sound(sound);
        if (!TypeUtility.isNull(badge)) builder.badge(badge);
        if (!TypeUtility.isNull(tag)) builder.tag(tag);
        if (!TypeUtility.isNull(color)) builder.color(color);
        if (!TypeUtility.isNull(click_action)) builder.clickAction(click_action);

        return builder.build();
    }
}
