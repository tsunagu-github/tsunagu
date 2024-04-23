/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Service;

import java.io.File;
import java.util.List;
import kis.inc.ssmix2storagemaker.controller.MakeStrageController;
import kis.inc.ssmix2storagemaker.Logic.validator.SaveValidate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * 対象ファイルをSS-MIX2形式のフォルダ構成で保存するサービスです
 * 
 */
public class SaveOML11Service {
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SaveOML11Service.class);
    
     /**
     * ロギングコンポーネント
     */
    private List<String> messageList;

    /**
     * データ種別
     */
    private String dataType = "OML-11";
    
    /**
     * データ種別
     */
    private String rootPath;
    
    
    /**
     * 検査結果保存用サービス 
     * @parm patientId 患者を特定するID（PHRならPHRID）
     * @parm date 検査日
     * @parm file 保存するファイル
    */
    public boolean saveOML11(String patientId , String date , File file){
        logger.debug("Start");
        
        SaveValidate validate = new SaveValidate();
        boolean result = true;
        
        result = validate.saveValidation(patientId,date,rootPath,file,dataType);
        
        if(!result){
            messageList.addAll(validate.getMessageList());
            result = false;
        }else{
            logger.debug("保存処理へ移行");
            MakeStrageController msc = new MakeStrageController();
            result = msc.makeStrage(patientId, date, rootPath,file, dataType);
        }
        
        logger.debug("End");
        return result;
    }

    /**
     * @param rootPath the rootPath to set
     */
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
   
    
}
