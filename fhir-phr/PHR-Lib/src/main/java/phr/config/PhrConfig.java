/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：設定ファイルより値を取得するクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import phr.utility.TypeUtility;

/**
 * システム利用設定情報を読込むためのクラス
 *
 * @author daisuke
 *
 */
public class PhrConfig {

    /**
     * コンフィグファイル名
     */
    private static String configFileName;
    /**
     * ロギングコンポーネント
     */
//    private static final Log logger = LogFactory.getLog(PhrConfig.class);
    private static Logger logger = Logger.getLogger(PhrConfig.class);

    /**
     * プロパティーの値を取得する
     *
     * @param key プロパティーKey
     * @return 値
     */
    public static String getSystemConfigProperty(String key) {
        try {
            //jarファイルを参照(PHR-Webからの呼び出しなど)した際にもconfigのリソースを参照出来るように
            String configName="../../phr-system.config";
            String classSimpName=PhrConfig.class.getSimpleName()+".class";
            URL resource = PhrConfig.class.getResource(classSimpName);
            resource = new URL(resource,configName);
            InputStream inputStream = resource.openStream();
//            InputStream inputStream = PhrConfig.class.getResourceAsStream("../../phr-system.config");
            String message = getProperty(key, inputStream);
            return message;
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return null;
    }

    /**
     * プロパティーの値を取得する
     *
     * @param key プロパティーKey
     * @return 値
     */
    public static String getConfigProperty(String key) {
        logger.debug("Start");
        try {
            String value = null;
            if (TypeUtility.isNullOrEmpty(configFileName)) {
                String configPath = getSystemConfigProperty(SystemConfigConst.CONFIG_PATH);
                String configFile = getSystemConfigProperty(SystemConfigConst.CONFIG_FILENAME);

                if (TypeUtility.isNullOrEmpty(configPath)) {
                    //jarファイルを参照(PHR-Webからの呼び出しなど)した際にもconfigのリソースを参照出来るように
                    String configName="../../"+configFile;
                    String classSimpName=PhrConfig.class.getSimpleName()+".class";
                    URL resource = PhrConfig.class.getResource(classSimpName);
                    resource = new URL(resource,configName);
                    InputStream inputStream = resource.openStream();
//                    InputStream inputStream = PhrConfig.class.getResourceAsStream("../../" + configFile);
                    value = PhrConfig.getProperty(key, inputStream);
                } else {
                    configFileName = configPath + configFile;
                }
            }
            if (!TypeUtility.isNullOrEmpty(configFileName)) {
                value = PhrConfig.getProperty(key, configFileName);
            }
            logger.debug(key + " = " + value);
            return value;
        } catch (Throwable e) {
            logger.error(e.toString(), e);
        }
        logger.debug("End");
        return null;
    }

    /**
     * 指定したプロパティーファイルよりKeyで指定した値を取得します。
     *
     * @param key	プロパティーファイルのKey
     * @param filePath プロパティーファイルのパス
     * @return keyの値
     * @throws FileNotFoundException
     */
    public static String getProperty(String key, String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        return getProperty(key, file);
    }

    /**
     * 指定したプロパティーファイルよりKeyで指定した値を取得します。
     *
     * @param key
     * @param file
     * @return keyの値
     * @throws FileNotFoundException
     */
    public static String getProperty(String key, File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            return getProperty(key, inputStream);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioException) {
                }
            }
        }
    }

    /**
     * 指定したプロパティーファイルよりKeyで指定した値を取得します。
     *
     * @param key
     * @param inputStream
     * @return
     */
    public static String getProperty(String key, InputStream inputStream) {
        try {
            Properties prop = new Properties();
            prop.load(inputStream);
            String message = prop.getProperty(key);
            return message;
        } catch (IOException ioException) {
            logger.error("ファイルの読込に失敗しました。", ioException);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioException) {
                    logger.error("ファイルクローズに失敗しました。", ioException);
                }
            }
        }
        return null;
    }
}
