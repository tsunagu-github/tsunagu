/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：設定ファイルより値を取得するクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/26
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.web;

import java.io.InputStream;
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.PhrConfig;
import phr.config.SystemConfigConst;
import phr.utility.TypeUtility;

/**
 *
 * @author KISNOTE011
 */
public class PhrWebConfig {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PhrWebConfig.class);

    /**
     * phr-webプロパティーファイルよりKeyで指定した値を取得します。
     * @param key	プロパティーファイルのKey
     * @return keyの値
     */
    public static String getPhrWebProperty(String key) {
        String getPropStr="";
        try {
            String configPath = PhrConfig.getSystemConfigProperty(SystemConfigConst.CONFIG_PATH);
//            File file = new File("/WEB-INF/phr-web.properties");
//            file = new File("C:/phr/src/PHR-Web/target/PHR-Web-1.0/WEB-INF/phr-web.properties");
//            getPropStr=PhrConfig.getProperty(key, file);
            if (TypeUtility.isNullOrEmpty(configPath)) {
                URL obUrl = PhrWebConfig.class.getResource(PhrWebConfig.class.getSimpleName()+".class");
                obUrl = new URL(obUrl,"../../../phr-web.properties");
                InputStream inputStream = obUrl.openStream();
                getPropStr=PhrConfig.getProperty(key, inputStream);
            }else{
                getPropStr=PhrConfig.getProperty(key, configPath+"phr-web.properties");
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
        return getPropStr;
    }

}
