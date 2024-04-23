/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.Map;
import java.util.List;
import phr.fcm.server.Result;
import phr.fcm.server.MulticastResult;
import phr.fcm.server.Message;
import phr.fcm.server.Notification;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;

/**
 *
 * @author KISO-NOTE-014
 */
public interface IFCMNotificationService {
    
    /**
     * FCMサーバーに通知リクエストをPostします。
     * @param entity 
     * @param receiverList 
     * @param retries 
     * @return success count
     * @Throwable 
     */
    int sendFCMMessage(CommunicationEntity entity, List<CommunicationReceiverEntity> receiverList, int retries) throws Throwable;

    /**
     * FCMサーバーにPostします。
     * @param message message to be sent, including the device's registration id.
     * @param to registration token or topic where the message will be sent.
     * @param retries number of retries in case of service unavailability errors.
     * @return 
     * @throws java.lang.Throwable 
     */
    Result doPost(Message message, String to, int retries) throws Throwable;
    
    /**
     * FCMサーバーに配信先複数でPostします。
     * @param message message to be sent, including the device's registration id.
     * @param tokens registration id of the devices that will receive the message.
     * @param retries number of retries in case of service unavailability errors.
     * @return 
     * @throws java.lang.Throwable 
     */
    MulticastResult doPost(Message message, List<String> tokens, int retries) throws Throwable;
    
    /**
     * 通知メッセージを作成します。
     * @param notification 通知ペイロードの事前定義済みでユーザーに表示される Key-Value ペア
     * @param priority メッセージの優先度。「normal」or「high」
     * @param timeToLive 端末がオフラインの場合にメッセージを FCM のストレージに保持する期間（秒単位）最大４週間
     * @param dataKeyValue メッセージのペイロードのカスタム Key-Value ペア
     * @param collapseKey 端末がオンラインに戻ったときなどに、同じメッセージが過剰に送信されないようにするためのまとめキー(最大４つ)
     * @return 
     * @throws java.lang.Throwable 
     */
    Message createMessage(Notification notification, Message.Priority priority, Integer timeToLive, Map<String, String> dataKeyValue, String collapseKey) throws Throwable;
    
    /**
     * リクエストテスト用、配信なし通知メッセージを作成します。
     * @param notification 通知ペイロードの事前定義済みでユーザーに表示される Key-Value ペア
     * @param priority メッセージの優先度。「normal」or「high」
     * @param timeToLive 端末がオフラインの場合にメッセージを FCM のストレージに保持する期間（秒単位）最大４週間
     * @param dataKeyValue メッセージのペイロードのカスタム Key-Value ペア
     * @param collapseKey 端末がオンラインに戻ったときなどに、同じメッセージが過剰に送信されないようにするためのまとめキー(最大４つ)
     * @return 
     * @throws java.lang.Throwable 
     */
    Message createMessageDryRun(Notification notification, Message.Priority priority, Integer timeToLive, Map<String, String> dataKeyValue, String collapseKey) throws Throwable;
    
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
     * @throws java.lang.Throwable 
     */
    Notification createNotification(String title, String body, String icon, String sound, Integer badge, String tag, String color, String click_action) throws Throwable;
    
}
