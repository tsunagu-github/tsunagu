/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web;

import java.io.Serializable;

/**
 *
 * @author KISNOTE011
 */
public class SessionConst  implements Serializable  {
    private static final long serialVersionUID = -8551331822246164777L;

    /**
     * 保険者ログインユーザアカウント
     */
    public static final String ACCOUNT_ENTITY = "ACCOUNT_ENTITY";

    /**
     * 医療機関ログインユーザアカウント
     */
    public static final String MEDICALACCOUNT_ENTITY = "MEDICALACCOUNT_ENTITY";

    /**
     * ユーザ情報一覧
     */
    public static final String USER_LIST = "USER_LIST";

    /**
     * ワンタイム情報
     */
    public static final String VIEW_PASSWORD_INFO = "VIEW_PASSWORD_INFO";

    /**
     * 受信メッセージ内容情報
     */
    public static final String RECIEVE_COMMUNITY_INFO = "RECIEVE_COMMUNITY_INFO";

    /**
     * 返信メッセージ内容情報
     */
    public static final String SEND_MESSAGE_DTO = "SEND_MESSAGE_DTO";

    /**
     * コミュニケーション一覧情報
     */
    public static final String COMMUNICAATION_ENTITIES = "COMMUNICAATION_ENTITIES";

    /**
     * おくすり一覧情報
     */
    public static final String DOSAGE_ENTITIES = "DOSAGE_ENTITIES";

    /**
     * 特定健診Error情報
     */
    public static final String CHEKUP_ERRLIST = "CHECKUP_ERRLIST";

    /**
     * 特定健診重複情報
     */
    public static final String CHEKUP_OVERLAP_LIST = "CHEKUP_OVERLAP_LIST";
    
    /**
     * 特定健診重複情報
     */
    public static final String CHEKUP_OVERLAP_DATA = "CHEKUP_OVERLAP_DATA";
    
    /**
     * ユーザ情報
     */
    public static final String USER_INFO = "USER_INFO";
    
    /**
     * 医療機関コード情報
     */
    public static final String MEDICAL_ORGANIZATION_CODE = "MEDICAL_ORGANIZATION_CODE";
    
    /**
    * 医療機関検索DTO
    */
    public static final String MEDICAL_SEARCH_DTO = "MEDICAL_SEARCH_DTO";
    
    /**
    * 医療機関検索DTO
    */
    public static final String MEDICAL_INFO = "MEDICAL_INFO";
    
    /**
     * 検査結果登録患者情報
     */
    public static final String MEDICAL_KENSA_ENTRY_DTO = "MEDICAL_KENSA_ENTRY_DTO";
    /**
     * 検査結果一覧情報
     */
    public static final String MEDICAL_KENSA_OBSERVATION_LIST = "MEDICAL_KENSA_OBSERVATION_LIST";    
    
    /**
     * リマインダー情報
     */
    public static final String REMINDER_INFO = "REMINDER_INFO";
    
    /**
     * ヘッダ状態
     */
    public static final String HEADER_STATUS = "HEADER_STATUS";
    
    /**
     * 結果閲覧対象患者のID
     */
    public static final String TARGET_PHRID = "TARGET_PHRID";
    
    /**
     * 閲覧同意患者情報
     */
    public static final String AGREE_PATIET_INFO = "REMINDER_INFO";
    
    /**
     * 
     */
    public static final String PATIENT_ENTITY = "PATIENT_ENTITY";
    
    /**
     * 
     */
    public static final String UTILIZATION_CONSENT_ENTITY_LIST = "UTILIZATION_CONSENT_ENTITY_LIST";
    
    /**
     * 
     */
    public static final String STUDY_INFORMATION_ENTITY = "STUDY_INFORMATION_ENTITY";
    
    /**
     * 
     */
    public static final String STUDY_INFORMATION_ENTITY_LIST = "STUDY_INFORMATION_ENTITY_LIST";
    
    /**
     * 
     */
    public static final String CONSENT_ENTITY = "CONSENT_ENTITY";
    
    /**
     * 研究項目一覧情報
     */
    public static final String STUDY_ITEM_INFORMATION_ENTITY = "STUDY_ITEM_INFORMATION_ENTITY";
    
    /**
     * 研究項目一覧情報リスト
     */
    public static final String STUDY_ITEM_INFORMATION_ENTITY_LIST = "STUDY_ITEM_INFORMATION_ENTITY_LIST";
    
    /**
     * 修正研究項目情報
     */
    public static final String EDIT_STUDY_ITEM_INFORMATION_ENTITY = "EDIT_STUDY_ITEM_INFORMATION_ENTITY";
    /**
     * 修正研究詳細情報
     */
    public static final String EDIT_STUDY_DETAILED_INFORMATION_ENTITY = "EDIT_STUDY_DETAILED_INFORMATION_ENTITY";

}
