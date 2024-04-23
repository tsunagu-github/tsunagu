/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：各処理を共通I/F
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import org.springframework.context.MessageSource;
import phr.web.phone.dto.ResponseBaseDto;

/**
 *
 * @author daisuke
 */
public abstract class PhoneFacade {

    /**
     * メッセージリソース
     */
    protected MessageSource messageSource;

    /**
     * 処理を開始する
     *
     * @param json
     * @return
     * @throws java.lang.Throwable
     */
    public abstract ResponseBaseDto execute(String json) throws Throwable;

    /**
     * メッセージリソース
     * @param messageSource the messageSource to set
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
}
