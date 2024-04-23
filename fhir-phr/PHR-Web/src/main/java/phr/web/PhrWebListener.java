/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：PHRWebアプリケーションのリスナークラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;

/**
 *
 * @author daisuke
 */
public class PhrWebListener implements ServletContextListener {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PhrWebListener.class);

    /**
     * APServer起動時処理
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.debug("Start 終了ログ出力");
        //DataAccessObject.setJndi(true);
        logger.debug("End 終了ログ出力");
    }

    /**
     * APServer終了時の処理
     * @param event
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.debug("Start 終了ログ出力");

        logger.debug("End 終了ログ出力");

    }
}
