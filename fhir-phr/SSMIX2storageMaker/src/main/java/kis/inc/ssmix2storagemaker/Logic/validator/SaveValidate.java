/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.validator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import kis.inc.ssmix2storagemaker.Enums.DataTypeEnum;
import kis.inc.ssmix2storagemaker.utility.StringUtility;
import kis.inc.ssmix2storagemaker.Service.SaveOML11Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * 標準化ストレージにファイルを保存するときのvalidation
 * 引数に問題がないかを確認する
 * 
 */
public class SaveValidate {

    /**
    * ロギングコンポーネント
    */
    private static final Log logger = LogFactory.getLog(SaveValidate.class);
 
    /*
    * エラーメッセージ用リスト
    */
    private List<String> messageList;
    
    /**
     * ファイルを保存するときのValidation
     * @parm patientId 患者を特定するID（PHRならPHRID）
     * @parm date 検査日
     * @parm file 保存するファイル
     * @parm type データ種別
    */
    public boolean saveValidation(String patientId , String date , String rootPath , File file , String type){
        logger.debug("start");
        logger.debug(type + "の保存Validationを開始");
        boolean resultflg = true;
        
        messageList = new ArrayList<String>();
        
        //IDの確認　6文字以下の場合はエラーとする
        if(StringUtility.isNullOrWhiteSpace(patientId)){
            getMessageList().add("患者を特定するIDが異常です " + patientId);
            resultflg = false;
        }else{
            if(patientId.length() < 6){
                getMessageList().add("患者を特定するIDが6文字未満です " + patientId);
                resultflg = false;
            }
        }
        
        //dateの確認
        if(StringUtility.isNullOrWhiteSpace(date)){
            getMessageList().add("日付が異常です " + date);
            resultflg = false;
        }else{
            if(date.length() < 8){
                getMessageList().add("日付がyyyyMMdd形式ではありません " + date);
                resultflg = false;
            }
        }
        
        //データ種別の確認
        if(StringUtility.isNullOrWhiteSpace(type)){
            getMessageList().add("データ種別が選択されていません " );
            resultflg = false;
        }else{
            if(DataTypeEnum.selectName(type) == null){
                getMessageList().add("指定されたデータ種別に対応していません " + type );
                resultflg = false;
            }
        }
        
        //fileの確認　ファイル名にデータ種別があることを確認する
        if(file == null){
            getMessageList().add("保存対象ファイルがありません " );
            resultflg = false;
        }else{
            String name = file.getName();
            if(!name.contains(type)){
                getMessageList().add("保存対象ファイルとデータ種別が一致しません" + name + " , " + type);
                resultflg = false;
            }
        }

        if(!resultflg){
            logger.info("標準化ストレージへの保存処理に必要な情報が不足していたため処理を中断します。");
            for(String message : getMessageList()){
                logger.info(message);
            }
        }
        
        logger.debug("End");
        return resultflg;
    }

    /**
     * @return the messageList
     */
    public List<String> getMessageList() {
        return messageList;
    }

}
