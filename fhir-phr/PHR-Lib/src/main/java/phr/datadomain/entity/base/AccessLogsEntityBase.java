/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：アクセスログのデータオブジェクト
 * 作成者          ：kis-inc
 * 作成日          ：2016/10/07
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.datadomain.entity.base;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.AbstractEntity;
import phr.datadomain.entity.AccessLogsEntity;

/**
 * アクセスログのデータオブジェクトです。
 */
public abstract class AccessLogsEntityBase extends AbstractEntity
{

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(AccessLogsEntityBase.class);

    /* -------------------------------------------------------------------------------------- */
    /**
     * コンストラクタ
     */
    public AccessLogsEntityBase()
    {
    }
    /* -------------------------------------------------------------------------------------- */


    /* -------------------------------------------------------------------------------------- */
    /* 変数定義 */
    /* -------------------------------------------------------------------------------------- */
    /* アクセス日時 */
    protected Timestamp accessDatetime = null;
    /* アクセス日時SEQ */
    protected long accessDatetimeSeq = 0;
    /* アクセスユーザ */
    protected String accessUser = null;
    /* アクセスユーザ名 */
    protected String accessUserName = null;
    /* サイト種別 */
    protected int siteType = 0;
    /* アクションID */
    protected String actionId = null;
    /* アクション名 */
    protected String actionName = null;
    /* 画面CD */
    protected String screenCd = null;
    /* 画面名称 */
    protected String screenName = null;
    /* URL */
    protected String url = null;
    /* 参照元 */
    protected String refer = null;
    /* リクエストメソッド */
    protected String requestMethod = null;
    /* リモートアドレス */
    protected String remoteAddress = null;
    /* リモートホスト */
    protected String remoteHost = null;
    /* エージェント */
    protected String agent = null;
    /* 参照PHRID */
    protected String referPhrid = null;
    /* -------------------------------------------------------------------------------------- */

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  AccessLogsEntity
     */
    public static AccessLogsEntity setData(ResultSet dataRow) throws Throwable {
        return setData(dataRow , true);
    }

    /**
     * オブジェクトにデータをセットします
     *
     * @param  dataRow  結果リスト
     * @return  AccessLogsEntity
     */
    public static AccessLogsEntity setData(ResultSet dataRow, boolean isChildTable) throws Throwable {
        logger.trace("Start");
        AccessLogsEntity entity = new AccessLogsEntity();
        entity.setAccessDatetime(getDateTime(dataRow, "AccessDatetime"));
        entity.setAccessDatetimeSeq(getLong(dataRow, "AccessDatetimeSeq"));
        entity.setAccessUser(getString(dataRow, "AccessUser"));
        entity.setAccessUserName(getString(dataRow, "AccessUserName"));
        entity.setSiteType(getInt(dataRow, "SiteType"));
        entity.setActionId(getString(dataRow, "ActionId"));
        entity.setActionName(getString(dataRow, "ActionName"));
        entity.setScreenCd(getString(dataRow, "ScreenCd"));
        entity.setScreenName(getString(dataRow, "ScreenName"));
        entity.setUrl(getString(dataRow, "Url"));
        entity.setRefer(getString(dataRow, "Refer"));
        entity.setRequestMethod(getString(dataRow, "RequestMethod"));
        entity.setRemoteAddress(getString(dataRow, "RemoteAddress"));
        entity.setRemoteHost(getString(dataRow, "RemoteHost"));
        entity.setAgent(getString(dataRow, "Agent"));
        entity.setReferPhrid(getString(dataRow, "ReferPhrid"));

        if (logger.isDebugEnabled())
        {
            logger.debug("アクセス日時      ：" + entity.getAccessDatetime());
            logger.debug("アクセス日時SEQ   ：" + entity.getAccessDatetimeSeq());
            logger.debug("アクセスユーザ    ：" + entity.getAccessUser());
            logger.debug("アクセスユーザ名  ：" + entity.getAccessUserName());
            logger.debug("サイト種別        ：" + entity.getSiteType());
            logger.debug("アクションID      ：" + entity.getActionId());
            logger.debug("アクション名      ：" + entity.getActionName());
            logger.debug("画面CD            ：" + entity.getScreenCd());
            logger.debug("画面名称          ：" + entity.getScreenName());
            logger.debug("URL               ：" + entity.getUrl());
            logger.debug("参照元            ：" + entity.getRefer());
            logger.debug("リクエストメソッド：" + entity.getRequestMethod());
            logger.debug("リモートアドレス  ：" + entity.getRemoteAddress());
            logger.debug("リモートホスト    ：" + entity.getRemoteHost());
            logger.debug("エージェント      ：" + entity.getAgent());
            logger.debug("参照PHRID         ：" + entity.getReferPhrid());
        }
        logger.trace("End");
        return entity;
    }

    /* -------------------------------------------------------------------------------------- */

    /**
     * アクセス日時を取得する
     *
     * @return
     */
    public Timestamp getAccessDatetime() {
        return this.accessDatetime;
    }
    /**
     * アクセス日時を設定する
     *
     * @param value
     */
    public void setAccessDatetime(Timestamp value) {
        this.accessDatetime = value;
    }

    /**
     * アクセス日時SEQを取得する
     *
     * @return
     */
    public long getAccessDatetimeSeq() {
        return this.accessDatetimeSeq;
    }
    /**
     * アクセス日時SEQを設定する
     *
     * @param value
     */
    public void setAccessDatetimeSeq(long value) {
        this.accessDatetimeSeq = value;
    }

    /**
     * アクセスユーザを取得する
     *
     * @return
     */
    public String getAccessUser() {
        return this.accessUser;
    }
    /**
     * アクセスユーザを設定する
     *
     * @param value
     */
    public void setAccessUser(String value) {
        this.accessUser = value;
    }

    /**
     * アクセスユーザ名を取得する
     *
     * @return
     */
    public String getAccessUserName() {
        return this.accessUserName;
    }
    /**
     * アクセスユーザ名を設定する
     *
     * @param value
     */
    public void setAccessUserName(String value) {
        this.accessUserName = value;
    }

    /**
     * サイト種別を取得する
     *
     * @return
     */
    public int getSiteType() {
        return this.siteType;
    }
    /**
     * サイト種別を設定する
     *
     * @param value
     */
    public void setSiteType(int value) {
        this.siteType = value;
    }

    /**
     * アクションIDを取得する
     *
     * @return
     */
    public String getActionId() {
        return this.actionId;
    }
    /**
     * アクションIDを設定する
     *
     * @param value
     */
    public void setActionId(String value) {
        this.actionId = value;
    }

    /**
     * アクション名を取得する
     *
     * @return
     */
    public String getActionName() {
        return this.actionName;
    }
    /**
     * アクション名を設定する
     *
     * @param value
     */
    public void setActionName(String value) {
        this.actionName = value;
    }

    /**
     * 画面CDを取得する
     *
     * @return
     */
    public String getScreenCd() {
        return this.screenCd;
    }
    /**
     * 画面CDを設定する
     *
     * @param value
     */
    public void setScreenCd(String value) {
        this.screenCd = value;
    }

    /**
     * 画面名称を取得する
     *
     * @return
     */
    public String getScreenName() {
        return this.screenName;
    }
    /**
     * 画面名称を設定する
     *
     * @param value
     */
    public void setScreenName(String value) {
        this.screenName = value;
    }

    /**
     * URLを取得する
     *
     * @return
     */
    public String getUrl() {
        return this.url;
    }
    /**
     * URLを設定する
     *
     * @param value
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * 参照元を取得する
     *
     * @return
     */
    public String getRefer() {
        return this.refer;
    }
    /**
     * 参照元を設定する
     *
     * @param value
     */
    public void setRefer(String value) {
        this.refer = value;
    }

    /**
     * リクエストメソッドを取得する
     *
     * @return
     */
    public String getRequestMethod() {
        return this.requestMethod;
    }
    /**
     * リクエストメソッドを設定する
     *
     * @param value
     */
    public void setRequestMethod(String value) {
        this.requestMethod = value;
    }

    /**
     * リモートアドレスを取得する
     *
     * @return
     */
    public String getRemoteAddress() {
        return this.remoteAddress;
    }
    /**
     * リモートアドレスを設定する
     *
     * @param value
     */
    public void setRemoteAddress(String value) {
        this.remoteAddress = value;
    }

    /**
     * リモートホストを取得する
     *
     * @return
     */
    public String getRemoteHost() {
        return this.remoteHost;
    }
    /**
     * リモートホストを設定する
     *
     * @param value
     */
    public void setRemoteHost(String value) {
        this.remoteHost = value;
    }

    /**
     * エージェントを取得する
     *
     * @return
     */
    public String getAgent() {
        return this.agent;
    }
    /**
     * エージェントを設定する
     *
     * @param value
     */
    public void setAgent(String value) {
        this.agent = value;
    }

    /**
     * 参照PHRIDを取得する
     *
     * @return
     */
    public String getReferPhrid() {
        return this.referPhrid;
    }
    /**
     * 参照PHRIDを設定する
     *
     * @param value
     */
    public void setReferPhrid(String value) {
        this.referPhrid = value;
    }

    /* -------------------------------------------------------------------------------------- */

    /// <summary>
    /// オブジェクトの文字列を返却します。
    /// </summary>
    /// <returns>オブジェクトの文字列</returns>
    public String toString() 
    {
        return "{ " + 
                "ccessDatetime=" + accessDatetime + 
                ", ccessDatetimeSeq=" + accessDatetimeSeq + 
                ", ccessUser=" + accessUser + 
                ", ccessUserName=" + accessUserName + 
                ", iteType=" + siteType + 
                ", ctionId=" + actionId + 
                ", ctionName=" + actionName + 
                ", creenCd=" + screenCd + 
                ", creenName=" + screenName + 
                ", rl=" + url + 
                ", efer=" + refer + 
                ", equestMethod=" + requestMethod + 
                ", emoteAddress=" + remoteAddress + 
                ", emoteHost=" + remoteHost + 
                ", gent=" + agent + 
                ", eferPhrid=" + referPhrid + 
                " }\r\n";
    }
}
