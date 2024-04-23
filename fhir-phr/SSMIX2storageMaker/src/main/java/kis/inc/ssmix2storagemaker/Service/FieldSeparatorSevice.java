/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.Facade.StorageToModelFacade;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * ファイルのセパレータを取得するサービス
 */
public class FieldSeparatorSevice {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(FieldSeparatorSevice.class);
    
    public messageSplitDto getSeparator(File file){
        messageSplitDto dto = new messageSplitDto();
        
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"JIS"));
            
            String line = br.readLine();
            
            String field = line.substring(3, 4);
            if(field.equals("|")) field = "\\|";
            String element = line.substring(4, 5);
            if(element.equals("^")) element = "\\^";
            String repeat = line.substring(5, 6);
            if(repeat.equals("~")) repeat = "\\~";
            String escape = line.substring(6, 7);
            if(repeat.equals("\\")) repeat = "\\";
            String subele = line.substring(7, 8);
            if(repeat.equals("&")) repeat = "\\&";
            
            logger.debug("フィールド区切り文字　：　" + field);
            logger.debug("成分セパレータ　：　" + element);
            logger.debug("反復セパレータ　：　" + repeat);
            logger.debug("エスケープ文字　：　" + escape);
            logger.debug("副成分セパレータ　：　" + subele);
            
            dto.setFieldseparator(field);
            dto.setCompseparator(element);
            dto.setRepeatseparator(repeat);
            dto.setEscape(escape);
            dto.setSubcomp(subele);
            
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FieldSeparatorSevice.class.getName()).log(Level.SEVERE, null, ex);
            logger.debug("ファイルが読めません");
        } catch (IOException ex) {
            Logger.getLogger(FieldSeparatorSevice.class.getName()).log(Level.SEVERE, null, ex);
            logger.debug("正常に区切り文字が取得できません");
        }

        return dto;
    }
}
