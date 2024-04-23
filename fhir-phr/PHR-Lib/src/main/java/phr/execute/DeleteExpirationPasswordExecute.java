/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.execute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.impl.OnetimePasswordService;

/**
 *
 * @author kis-note-025
 */
public class DeleteExpirationPasswordExecute {
         /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(DeleteExpirationPasswordExecute.class);
    
    	public static void main(String[] args) {
		logger.trace("Start");
                try{
                    OnetimePasswordService service = new OnetimePasswordService();
                    service.deleteExpirationPassword();
                }
                catch(Throwable th)
                {
                    logger.error(th);
                    System.exit(-1);
                }
                // 戻り値(正常)
		System.exit(0);
        }
}
