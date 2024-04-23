package phr.config;

/**
 * PHRコンフィグ定数クラス
 */
public class ConfigConst {
    /**
     * データベースホスト
     */
     public static final String DATABASE_HOST = "database.host";

    /**
     * データベースポート
     */
     public static final String DATABASE_PORT = "database.port";

    /**
     * データベース名称
     */
     public static final String DATABASE_NAME = "database.name";

    /**
     * データベースユーザー
     */
     public static final String DATABASE_USER = "database.user";

    /**
     * データベースパスワード
     */
     public static final String DATABASE_PASSWORD = "database.password";

    /**
     * パスワードの最小文字数
     */
     public static final String PASSWORD_MIN_DIGITS = "password_min_digits";

    /**
     * パスワードの設定文字モード
     */
     public static final String PASSWORD_MODE = "password_mode";

    /**
     * パスワード変更時、同一パスワードの設定許可
     */
     public static final String SAME_PW_SETTINGS = "same_pw_settings";

    /**
     * パスワード有効期間（単位：日）
     */
     public static final String PASSWORD_VALIDITY_PERIOD = "password_validity_period";

    /**
     * SS-MIX2ストレージ
     */
     public static final String STORAGE_PATH = "storage_path";

    /**
     * バックアップ用SS-MIX2ストレージ作成フォルダ
     */
     public static final String BACKUP_STORAGE = "backup_storage";

    /**
     * MInCSからのデータを受信するフォルダパス
     */
     public static final String MINCS_DATA_PATH = "mincs_data_path";

    /**
     * MInCSからのデータを受信するフォルダパス
     */
     public static final String MINCS_KEY_PATH = "mincs_key_path";

    /**
     * MInCSからのデータを受信するフォルダパス
     */
     public static final String MINCS_RESULT_SUCCESS_PATH = "mincs_result_success_path";

    /**
     * MInCSからのデータを受信するフォルダパス
     */
     public static final String MINCS_RESULT_FAILED_PATH = "mincs_result_failed_path";

    /**
     * MInCS受信したデータを展開するフォルダパス
     */
     public static final String MINCS_DECOMPRESS_PATH = "mincs_decompress_path";

    /**
     * バックアップセンターのPHR事業者番号
     */
     public static final String BACKUP_PHR_ASSOCIATION_NO = "backup.phr.association.no";

    /**
     * バックアップセンターのバックアップログインID
     */
     public static final String BACKUP_CENTER_BACKUP_LOGINID = "backup.center.backup.loginid";

    /**
     * バックアップセンターのバックアップ用パスワード
     */
     public static final String BACKUP_CENTER_BACKUP_PASSWORD = "backup.center.backup.password";

    /**
     * バックアップセンターのリストア用ログインID
     */
     public static final String BACKUP_CENTER_RESTORE_LOGINID = "backup.center.restore.loginid";

    /**
     * バックアップセンターのリストア用パスワード
     */
     public static final String BACKUP_CENTER_RESTORE_PASSWORD = "backup.center.restore.password";

    /**
     * バックアップセンターの基底URL
     */
     public static final String BACKUP_CENTER_URL_BASE = "backup.center.url.base";

    /**
     * バックアップセンタークライアント証明書有無
     */
     public static final String BACKUP_CENTER_CLIENT_AUTH = "backup.center.client.auth";

    /**
     * バックアップセンタークライアント証明書ファイル
     */
     public static final String BACKUP_CENTER_CLIENT_CERT_FILE = "backup.center.client.cert.file";

    /**
     * バックアップセンタークライアント証明書PIN
     */
     public static final String BACKUP_CENTER_CLIENT_CERT_PIN = "backup.center.client.cert.pin";

    /**
     * バックアップセンタークライアントCA証明書ファイル
     */
     public static final String BACKUP_CENTER_CLIENT_CACERT_FILE = "backup.center.client.cacert.file";

    /**
     * バックアップセンタークライアント証明書キーストアパスワード
     */
     public static final String BACKUP_CENTER_CLIENT_KEYSTORE_PASSWORD = "backup.center.client.keystore.password";

    /**
     * バックアップセンターのバックアップ情報送付URL
     */
     public static final String BACKUP_CENTER_BACKUP_INFO_URL = "backup.center.backup.info.url";

    /**
     * バックアップセンターのバックアップデータ送付URL
     */
     public static final String BACKUP_CENTER_BACKUP_DATA_URL = "backup.center.backup.data.url";

    /**
     * バックアップセンターのリストア情報送付URL
     */
     public static final String BACKUP_CENTER_RESTORE_INFO_URL = "backup.center.restore.info.url";

    /**
     * バックアップセンターのリストア完了通知URL
     */
     public static final String BACKUP_CENTER_RESTORE_FINISH_URL = "backup.center.restore.finish.url";

    /**
     * バックアップセンターのリストア（移動）情報送付URL
     */
     public static final String BACKUP_CENTER_TRANSFER_INFO_URL = "backup.center.transfer.info.url";

    /**
     * バックアップセンターのリストア（移動）完了通知URL
     */
     public static final String BACKUP_CENTER_TRANSFER_FINISH_URL = "backup.center.transfer.finish.url";

    /**
     * バックアップセンターのリストア（プロジェクト移動）情報送付URL
     */
     public static final String BACKUP_CENTER_TRANSFER_PROJECT_INFO_URL = "backup.center.transfer.project.info.url";

    /**
     * バックアップセンターのリストア（プロジェクト移動）完了通知URL
     */
     public static final String BACKUP_CENTER_TRANSFER_PROJECT_FINISH_URL = "backup.center.transfer.project.finish.url";

    /**
     * バックアップセンターの削除用URL
     */
     public static final String BACKUP_CENTER_DELETE_URL = "backup.center.delete.url";

    /**
     * リストアファイル一時置き場
     */
     public static final String BACKUP_CENTER_RESTORE_FILE_PATH = "backup.center.restore.file.path";

    /**
     * 健診XMLファイル保存フォルダ
     */
     public static final String CHECKUP_SAVE_PATH = "checkup.save.path";

    /**
     * リマインダプッシュメッセージ対象を抽出したデータを持つファイルのパス
     */
     public static final String REMINDER_TARGET_FILE_PATH = "reminder_target_file_path";

    /**
      * FHIRサーバのベースURL
      */
      public static final String FHIR_SERVER_BASE_URL = "fhir.server.base.url";

    /**
     * 患者ID取得時のPatient.identifier.systemの値
     */
     public static final String PATIENT_IDENTIFIER_SYSTEM = "patient_indentifier_system";

    /**
     * 医療機関コード取得時のOrganization.identifier.systemの値
     */
     public static final String ORGANIZATION_IDENTIFIER_SYSTEM = "organization_identifier_system"; 

    /**
     * 検査依頼ID取得時のServiceRequest.identifier.systemの値
     */
     public static final String SERVICEREQUEST_IDENTIFIER_SYSTEM = "servicerequest_identifier_system";

    /**
     * バッチ処理を行う一定時間間隔（分）
     */
     public static final String BATCH_TIME_INTERVAL = "batch_time_interval";

     /**
      * 測定・全検査結果画面の背景色（検査結果）
      */
     public static final String EXAM_BACKGROUND_COLOR = "exam_background_color";

     /**
      * 測定・全検査結果画面の背景色（自己測定）
      */
     public static final String SELF_BACKGROUND_COLOR = "self_background_color";

     /**
      * 測定・全検査結果画面の背景色（特定健診）
      */
     public static final String CHECKUP_BACKGROUND_COLOR = "checkup_background_color";

     /**
      * アラート時の背景色
      */
     public static final String ALERT_BACKGROUND_COLOR = "alert_background_color";

     /**
      * 特定健診XMLファイル一時保存場所
      */
     public static final String XML_PATH = "xml_path";

     /**
      * ダイナミックコンセントの利用停止を行う間隔（日）
      */
     public static final String DYNAMICCONSENT_STOP_INTERVAL = "dynamicConsent_stop_interval";
}
