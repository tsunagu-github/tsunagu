/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：メッセージ一覧画面の表示を行うサービスクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/25
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.CommunicationAdapter;
import phr.datadomain.entity.CommunicationEntity;
import phr.service.IMessageListService;

/**
 *メッセージ一覧画面の表示を行うサービスクラス
 * @author kis.o-note-003
 */
public class MessageListService implements IMessageListService{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UserAuthenticationService.class);
    
    /**
     * メッセージ一覧の取得を行う
     * @param phrId
     * @param startDate
     * @param endDate
     * @param readFlg
     * @return
     * @throws Throwable 
     */
    @Override
    public List<CommunicationEntity> messageSearch(String phrId, Date startDate, Date endDate, Boolean readFlg, String insurerNo) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            List<CommunicationEntity> entity = adapter.findByPkDateFlgInsurer(phrId, startDate, endDate, readFlg, insurerNo);
            
            return entity;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            System.out.println(ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
}
