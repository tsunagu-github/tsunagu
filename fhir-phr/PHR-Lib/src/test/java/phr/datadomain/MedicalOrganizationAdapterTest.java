/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import phr.datadomain.adapter.MedicalOrganizationAdapter;
import phr.datadomain.entity.MedicalOrganizationEntity;
/**
 *
 * @author daisuke
 */
public class MedicalOrganizationAdapterTest {
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CommunicationAdapterTest.class);  
    
    @Test
    public void 医療機関パスワード作成() throws Throwable{
        
        String targetStr = "N8pLvDFJ,d5NAKq8u,B2bPsxrt,a4FMW62G,A4pEPYLs,Ws7Ua6mv,T8eguWzj,Nh5Uxe4C,Xi82cDBn,Pj5C4wfW,xX8vKPUz,D6mXMnjp,z8R5ZnCi,Lq4AXB5r,pE98evrq,T5u6J8Ct,hP8myHNq,Kw9DNQ4k,e2Ci3jb9,Xi58RuUm,T9c8sUi5,N7yGrWwm,xY8eKMuU,J4arDg5N,Tq7Q4FEt,Us5G3Zvg,Sf3vBUDs,hU6L4Fv3,Wu5tPZaj,N9mj7gvP,Pf8FRGhk,y9UEdijz,Wn3SJ7Be,R6fZxjSK,X2bMiTve,p8PzhXtH,yZ2wkdfV";
        
        String[] targets = targetStr.split(",");
        
        for (String pwd : targets) {
            MedicalOrganizationEntity entiry = new MedicalOrganizationEntity();
            entiry.setDecriptPassword(pwd);
            
            logger.debug(entiry.getPassword());
        }
    }
}
