/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.kis.inc.ssmix2stragemaker.Facade;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelExMedicineNoteBook;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author kis-note
 */
public class MedicineConvertTest {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(MedicineConvertTest.class);
    
    @Test
    public void execute(){
        String path = "/opt/strage/test.txt";
        File file = new File(path);
        
        ModelExMedicineNoteBook book = new ModelExMedicineNoteBook();
        
        try {
            ModelDto result = book.execute(file);
            
            Gson gson = new Gson();
            String r = gson.toJson(result);
            logger.debug(r);
        } catch (IOException ex) {
            Logger.getLogger(MedicineConvertTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
