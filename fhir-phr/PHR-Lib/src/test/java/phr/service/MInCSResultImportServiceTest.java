/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import phr.service.impl.MInCSResultImportService;

/**
 *
 * @author daisuke
 */
public class MInCSResultImportServiceTest {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MInCSResultImportServiceTest.class);

    @Test
    public void 検査結果インポートテスト() throws Throwable {
        
        MInCSResultImportService service  = new MInCSResultImportService();
        
        File file = new File("/project/04.kis/85.AMED-PHR/src/PHR-Lib/src/test/test_data/99_20160401_OML-11_000000991219110_20161004102250214_-_1");
        List<String> errorList = new ArrayList<>();
        service.InsertUpdateMincsResult("MinCSLabo",file, errorList);
        
    }
}
