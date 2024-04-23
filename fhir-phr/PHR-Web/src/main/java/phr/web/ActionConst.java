package  phr.web;

public class ActionConst {

	/**
	 * 保険者ログイン
	 */
	public static final String INSURER_LOGIN_ACTION = "/insurerLogin";

	/**
	 * ログアウト
	 */
	public static final String LOGOUT_ACTION = "/logout";

	/**
	 * パスワード変更
	 */
	public static final String PASSWORD_CHANGE_ACTION = "/passwordChange";

	/**
	 * パスワード変更確認
	 */
	public static final String PASSWORD_CHANGE_CONFIRM_ACTION = "/passwordChangeConfirm";

	/**
	 * 対象患者指定
	 */
	public static final String TARGETING_PATIENT_ACTION = "/targetingPatient";

	/**
	 * ユーザ管理
	 */
	public static final String INSURER_ACCOUNT_MANAGEMENT_ACTION = "/insurerAccountManagement";

	/**
	 * ユーザ情報変更
	 */
	public static final String INSURER_ACCOUNT_EDIT_ACTION = "/insurerAccountEdit";

	/**
	 * ユーザ情報変更確認
	 */
	public static final String INSURER_ACCOUNT_CONFIRM_ACTION = "/insurerAccountConfirm";

	/**
	 * 対象患者指定メッセージ
	 */
	public static final String TARGETING_PATIENT_MSG_VIEW_ACTION = "/targetingPatientMsgView";

	/**
	 * 対象者情報
	 */
	public static final String TARGETING_PATIENT_INFO_ACTION = "/targetingPatientInfo";

	/**
	 * 患者指定解除
	 */
	public static final String UNASSIGN_PATIENT_ACTION = "/unassignPatient";

	/**
	 * メッセージ一覧
	 */
	public static final String MESSAGE_LIST_ACTION = "/messageList";

	/**
	 * メッセージ詳細
	 */
	public static final String MESSAGE_SELECT_ACTION = "/messageSelect";

	/**
	 * メッセージ返信
	 */
	public static final String MESSAGE_REPLAY_ACTION = "/messageReplay";

	/**
	 * メッセージ返信確認
	 */
	public static final String MESSAGE_REPLAY_CONFIRM_ACTION = "/messageReplayConfrim";

	/**
	 * おしらせ一覧
	 */
	public static final String NOTICE_ACTION = "/notice";

	/**
	 * おしらせ詳細
	 */
	public static final String NOTICE_DETAIL_ACTION = "/noticeDetail";

	/**
	 * おしらせ新規登録
	 */
	public static final String NOTICE_NEWCREATE_ACTION = "/noticeNewCreate";

	/**
	 * アラート一覧
	 */
	public static final String ALERT_SEARCH_ACTION = "/alertSearch";

	/**
	 * アラート患者情報
	 */
	public static final String ALERT_PATIENT_INFO_ACTION = "/alertPatientInfo";

	/**
	 * 特定健診登録画面
	 */
	public static final String SPECIFIC_MEDICAL_CHECKUP_ACTION = "/specificMedicalCheakUpForm";

	/**
	 * 医療機関ログイン
	 */
	public static final String MEDICAL_LOGIN_ACTION = "/medicalLogin";

	/**
	 * 医療機関-メッセージ一覧
	 */
	public static final String MEDICAL_MESSAGE_LIST_ACTION = "/medicalMessageList";

	/**
	 * 医療機関-メッセージ詳細
	 */
	public static final String MEDICAL_MESSAGE_SELECT_ACTION = "/medicalMessageSelect";

	/**
	 * 医療機関-メッセージ返信
	 */
	public static final String MEDICAL_MESSAGE_REPLAY_ACTION = "/medicalMessageReplay";

	/**
	 * 医療機関-メッセージ返信確認
	 */
	public static final String MEDICAL_MESSAGE_REPLAY_CONFIRM_ACTION = "/medicalMessageReplayConfirm";

	/**
	 * 対象患者新規メッセージ
	 */
	public static final String TARGETING_PATIENT_MESSAGE_ACTION = "/targetingPatientMessage";

	/**
	 * 対象患者新規メッセージ確認
	 */
	public static final String TARGETING_PATIENT_MESSAGE_CONFIRM_ACTION = "/targetingPatientMessageConfirm";

	/**
	 * 端末認証
	 */
	public static final String TARIMINAL_AUTH_ACTION = "/TariminalAuthAction";

	/**
	 * 利用者更新
	 */
	public static final String UPDATE_PATIEN_ACTION = "/UpdatePatienAction";

	/**
	 * PHRID発行
	 */
	public static final String CREATE_PATIEN_ACTION = "/CreatePatienAction";

	/**
	 * 検査結果
	 */
	public static final String GETCLINICALTESTRESULT = "/GetClinicalTestResult";

	/**
	 * 引継ぎコード発行
	 */
	public static final String GET_TAKE_OVER_CODE = "/GetTakeOverCode";

	/**
	 * 参照用ワンタイムパスワード発行
	 */
	public static final String GET_ONE_TIME_PASSWORD = "/GetOneTimePassword";

	/**
	 * 引継ぎ
	 */
	public static final String ENTRY_TAKE_OVER_CODE = "/EntryTakeOverCode";

	/**
	 * 健診結果
	 */
	public static final String GET_CHECK_UP_RESULT = "/GetCheckUpResult";

	/**
	 * 患者連携設定一覧
	 */
	public static final String GET_COOPERATION_LIST = "/GetCooperationList";

	/**
	 * 患者連携設定
	 */
	public static final String OPERATION_COOPERATION = "/OperationCooperation";

	/**
	 * 自己測定一覧
	 */
	public static final String GET_SELF_CHECK_LIST = "/GetSelfCheckList";

	/**
	 * 自己測定
	 */
	public static final String OPERATION_SELF_CHECK = "/OperationSelfCheck";

	/**
	 * おくすりリスト
	 */
	public static final String GET_MEDICINE_LIST = "/GetMedicineList";

	/**
	 * おくすり
	 */
	public static final String DELETE_MEDICINE = "/DeleteMedicine";

	/**
	 * おくすり
	 */
	public static final String UPDATE_MEDICINE = "/UpdateMedicine";

	/**
	 * コミュニケーションリスト
	 */
	public static final String GET_COMMUNICATION_LIST = "/GetCommunicationList";

	/**
	 * コミュニケーション詳細
	 */
	public static final String GET_COMMUNICATION_DETAIL = "/GetCommunicationDetail";

	/**
	 * コミュニケーション情報登録
	 */
	public static final String OPERATION_COMMUNICATION = "/OperationCommunication";

	/**
	 * コミュニケーション宛先リスト
	 */
	public static final String GET_COMMUNICATION_ADDRESS = "/GetCommunicationAddress";

	/**
	 * ユーザ管理画面
	 */
	public static final String USER_MANAGE_ACTION = "/userManage";

	/**
	 * ユーザ新規登録・修正画面
	 */
	public static final String USER_MANAGE_EDIT_ACTION = "/userManageEdit";

	/**
	 * ユーザ確認画面
	 */
	public static final String USER_MANAGE_CONFIRM_ACTION = "/userManageConfirm";

	/**
	 * 医療機関一覧画面
	 */
	public static final String MEDICAL_SEARCH_ACTION = "/medicalSearch";

	/**
	 * 医療機関新規登録画面
	 */
	public static final String MEDICAL_CREATE_EDIT_ACTION = "/medicalCreateEdit";

	/**
	 * 医療機関確認画面
	 */
	public static final String MEDICAL_CREATE_CONFIRM_ACTION = "/medicalCreateConfirm";

	/**
	 * 検査結果登録患者検索画面
	 */
	public static final String MEDICAL_KENSA_ENTRY_ACTION = "/medicalKensaEntry";

	/**
	 * 検査結果登録患者検査日一覧画面
	 */
	public static final String MEDICAL_KENSA_PATIENT_ACTION = "/medicalKensaPatient";

	/**
	 * 検査結果登録・修正画面
	 */
	public static final String MEDICAL_KENSA_ENTRY_INPUT_ACTION = "/medicalKensaEntryInput";

	/**
	 * 検査結果登録・修正確認画面
	 */
	public static final String MEDICAL_KENSA_ENTRY_CONFIRM_ACTION = "/medicalKensaEntryConfirm";

	/**
	 * 管理項目情報
	 */
	public static final String MANAGEMENT_ITEM_VIEW = "/managementItemView";

	/**
	 * 管理項目設定
	 */
	public static final String MANAGEMENT_ITEM_SETTING = "/managementItemSetting";

	/**
	 * 管理項目リマインダ設定
	 */
	public static final String MANAGEMENT_ITEM_REMINDER = "/managementItemReminder";

	/**
	 * 管理項目リマインダ設定詳細
	 */
	public static final String MANAGEMENT_ITEM_REMINDER_DETAIL = "/managementItemReminderDetail";

	/**
	 * 検査結果アップロード
	 */
	public static final String LABORATORY_RESULT_UPLOAD = "/laboratoryResultUpload";

	/**
	 * 参照用ワンタイムパスワード発行
	 */
	public static final String ONE_TIME_PASS = "/oneTimePass";
	
	public static String actionPage(String action) {
		if (action == null || action.length() == 0)
			return null;

		return action.substring(1);
	}

	public static String redirectActionPage(String action) {
		if (action == null || action.length() == 0)
			return null;

		return "redirect:" + actionPage(action);
	}
	
	   /**
     * 医療機関対象者一覧
     */
    public static final String TARGETING_PATIENT_LIST_ACTION = "/targetingPatientList";
    
    /**
     * 同意内容登録検索
     */
    public static final String STUDY_ITEM_INFORMATION = "/studyItemInformation";
    /**
     * 同意内容登録更新
     */
    public static final String STUDY_ITEM_INFORMATION_EDIT = "/studyItemInformationEdit";
    
    /**
     * 同意通知
     */
    public static final String CONSENT_NOTIFICATION = "/consentNotification";
    
}