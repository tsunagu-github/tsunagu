package phr.config;

/**
 * PHR-SYSTEMコンフィグ定数クラス
 */
public class SystemConfigConst {
    /**
     * ビルドバージョン
     */
     public static final String BUILDVERSION = "buildVersion";

    /**
     * 設定ファイルのパス(ブランクの場合はclasses直下)
     */
     public static final String CONFIG_PATH = "config.path";

    /**
     * 設定ファイル名
     */
     public static final String CONFIG_FILENAME = "config.filename";

    /**
     * 開発環境（development） or 本番環境（production）
     */
     public static final String SYSTEM_ENV = "system.env";

    /**
     * JNDI DataSource Factory
     */
     public static final String JNDI_DATASOURCE_FACTORY = "jndi.datasource.factory";

    /**
     * JNDI DataSource URLPkgPrefixes
     */
     public static final String URL_PKG_PREFIXES = "url.pkg.prefixes";

    /**
     * JDBC接続のURL
     */
     public static final String JDBC_URL_BASE = "jdbc.url.base";

    /**
     * データソース名
     */
     public static final String DATASOURCE_NAME = "datasource.name";

    /**
     * データベース最大接続数
     */
     public static final String DATABASE_MAX_CONNECTION = "database.max.connection";

    /**
     * データベースドライバー
     */
     public static final String DATABASE_DRIVER = "database.driver";

    /**
     * パスワード暗号化の共通鍵
     */
     public static final String PASSWORD_ENCRIPT_KEY = "password.encript.key";

    /**
     * FCMトークン（サーバーキー）
     */
     public static final String FCM_TOKEN = "fcm.token";

    /**
     * スマフォI/Fバージョン
     */
     public static final String PHONE_IF_VERSION = "phone.if.version";

    /**
     * Version1.0用URL
     */
     public static final String PHONE_IF_VERSION1_0_URL = "phone.if.version1.0.URL";

    /**
     * ワンタイムID暗号化の共通鍵
     */
     public static final String ONTIME_ID_ENCRIPT_KEY = "ontime.id.encript.key";

}
