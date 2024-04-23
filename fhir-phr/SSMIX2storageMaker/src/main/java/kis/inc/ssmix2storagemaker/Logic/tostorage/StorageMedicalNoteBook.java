/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.tostorage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import kis.inc.ssmix2storagemaker.Enums.DataTypeEnum;
import static kis.inc.ssmix2storagemaker.Logic.tostorage.DoEncode.doEncode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class StorageMedicalNoteBook extends StorageBase{
    
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(StorageMedicalNoteBook.class);
    
    /**
     * ストレージ作成メソッド
     * @return 
     */
    public boolean execute(String name , String target , String path , String patientId) throws ParseException, FileNotFoundException, UnsupportedEncodingException{
        logger.debug("お薬手帳メッセージ作成開始");
        
        String infos[] = name.split("_");
        
        String rootPath = path;
        if(patientId != null){
            rootPath = makeDateRootPath(path , patientId , infos[2] , DataTypeEnum.MEDICALBOOK.getCode());
        }

        String targetPath = rootPath + "/" + name;
        logger.debug("targetPath : " + targetPath );

        File targetFile = new File(targetPath);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"JIS")));
        String outputText = doEncode(target);
        //pw.println(target);
        pw.println(outputText);
        pw.close();

        logger.debug("お薬手帳メッセージ作成終了");
        return true;
    }
    
}
