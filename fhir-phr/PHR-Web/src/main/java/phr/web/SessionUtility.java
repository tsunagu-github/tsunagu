/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 *
 * @author daisuke
 */
@Component
public class SessionUtility implements ISessionUtility {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SessionUtility.class);

    /**
     * セッションにオブジェクトを設定する
     *
     * @param key
     * @param object
     */
    public void setSession(String key, Object object) {
        logger.debug("Start");

        try {
            RequestContextHolder.getRequestAttributes().setAttribute(key,
                    object,
                    RequestAttributes.SCOPE_SESSION);
        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }
    }

    /**
     * セッションよりオブジェクトを取得する
     *
     * @param key
     * @return
     */
    public Object getSession(String key) {
        logger.debug("Start");
        Object object = null;
        try {
            logger.debug("key=" + key);
            object = RequestContextHolder.getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_SESSION);

        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }
        return object;
    }

    /**
     * セッションをクリアする
     */
    public void ClearSession() {
        logger.debug("Start");
        try {
            String[] names = RequestContextHolder.getRequestAttributes().getAttributeNames(RequestAttributes.SCOPE_SESSION);

            for (int i = 0; i < names.length; i++) {
                RequestContextHolder.getRequestAttributes().removeAttribute(names[i], RequestAttributes.SCOPE_SESSION);
            }

        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }

    }

    /**
     * セッションを削除するs
     *
     * @param key
     */
    public void removeSession(String key) {
        logger.debug("Start");
        try {
            RequestContextHolder.getRequestAttributes().removeAttribute(key, RequestAttributes.SCOPE_SESSION);
        } catch (Throwable t) {
            logger.error(t);
        } finally {
            logger.debug("End");
        }

    }

}
