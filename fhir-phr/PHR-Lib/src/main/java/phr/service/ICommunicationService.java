/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import phr.datadomain.entity.CommunicationEntity;
import phr.datadomain.entity.CommunicationReceiverEntity;
import phr.datadomain.entity.InsurerEntity;
import phr.datadomain.entity.ReminderMessageEntity;

/**
 *
 * @author KISNOTE011
 */
public interface ICommunicationService {

    /**
     * 未読件数を取得する
     * @param phrid
     * @return
     * @throws Throwable 
     */
    int countUnreadCommunication(String phrid) throws Throwable;
    
    /**
     * PHRIDからおしらせ及びメッセージを検索します。（複数件直近情報）
     * @param phrid
     * @param organizationCd
     * @param basedate
     * @param targetCd
     * @return 
     * @throws java.lang.Throwable 
     */
    List<CommunicationEntity> SearchCommunicationByPhridOrganization(String phrid,String organizationCd,Timestamp basedate, int targetCd) throws Throwable;
    
    /**
     * コミニュケーションID,Seqからおしらせ及びメッセージを検索します
     * @param communicationId
     * @param seq
     * @return 
     * @throws java.lang.Throwable 
     */
    List<CommunicationEntity> SearchCommunicationByCommuidAndSeq(String communicationId,int seq) throws Throwable;
    
    /**
     * 対象のおしらせ及びメッセージを既読に更新します
     * @param entity
     * @return 
     * @throws java.lang.Throwable 
     */
    int UpdateCommunicationByReadFlg(CommunicationReceiverEntity entity) throws Throwable;
    
    /**
     * （患者View向け）PHRIDから受診＆送信済みの、おしらせ及びメッセージを検索します。
     * @param phrid
     * @param basedate
     * @return 
     * @throws java.lang.Throwable 
     */
    List<CommunicationEntity> SearchCommunicationByPhridForPatient(String phrid, Timestamp basedate) throws Throwable;
    
    /**
     * （患者View向け）メッセージを登録します。
     * @param entity
     * @param receiverList
     * @throws java.lang.Throwable 
     */
    void CreateCommunicationForPatient(CommunicationEntity entity, List<CommunicationReceiverEntity> receiverList) throws Throwable;
    
    /**
     * PHRIDから、患者の加入している保険者情報を検索します。
     * @param phrid
     * @return 
     * @throws java.lang.Throwable 
     */
    InsurerEntity SearchInsurerByPatientPhrid(String phrid) throws Throwable;
    
    /**
     * 指定された患者へのリマインダプッシュメッセージを登録します。
     * @param phrId
     * @param reminderMessageList
     * @throws java.lang.Throwable
     */
    void CreateCommunicationForReminderPush(String phrId, List<ReminderMessageEntity> reminderMessageList) throws Throwable;

    /**
     * PhrID,コミニュケーションIDからReceiverを検索します
     * @param communicationId
     * @param phrid
     * @return 
     * @throws java.lang.Throwable 
     */
    CommunicationReceiverEntity SearchReceiverByCommuidAndPhrid(String communicationId ,String phrid) throws Throwable;

    /**
     * 指定された患者への同意通知を登録、送信します。
     *
     * @param phrId
     * @param reminderMessageList
     * @throws java.lang.Throwable
     */
    void CreateSendCommunication(String phrId, List<ReminderMessageEntity> reminderMessageList) throws Throwable;

    /**
     * 指定された患者への同意通知を登録、送信します。
     *
     * @param phrId
     * @param reminderMessageList
     * @throws java.lang.Throwable
     */
    void receptionCompleted(String phrId, List<ReminderMessageEntity> reminderMessageList) throws Throwable;
}
