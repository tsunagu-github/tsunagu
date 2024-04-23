/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.CommunicationAdapter;
import phr.datadomain.entity.CommunicationEntity;
import phr.service.INoticeService;

/**
 *
 * おしらせ画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class NoticeService implements INoticeService{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(NoticeService.class);
    
    /**
     * おしらせ情報の取得を行う
     * @param startDate
     * @param endDate
     * @return entity
     * @throws java.lang.Throwable 
     */
    @Override
    public List<CommunicationEntity> noticeSearch(Date startDate, Date endDate, String insurerNo) throws Throwable {
        
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            
            dao = new DataAccessObject();
            CommunicationAdapter adapter = new CommunicationAdapter(dao.getConnection());
            List<CommunicationEntity> entity = adapter.findByFromToDate(insurerNo, startDate, endDate);
            
            return entity;
            
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
}
