/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.tostorage;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.baseSegmentDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class StorageBase extends makeField{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(StorageBase.class);
    
    /**
     * 診療日込みでフォルダを作成してメッセージを置く場所を確保する
     * ただし、患者IDがnullの場合は直下におく
     * @param path
     * @param patientId
     * @param date
     * @return 
     */
    public String makeDateRootPath(String path, String patientId , String date , String type) throws ParseException {
        logger.debug("ルートパスの作成開始");
        
        if(patientId == null){
            logger.debug("患者ID指定がないためpathの直下で対応");
            return path;
        }
        
        if(date == null || date.trim().length() <= 0){
            logger.debug("日付の指定が異常のためpath直下で対応");
            return path;
        }
        
        if(patientId.length() <= 5){
            logger.debug("患者IDの文字数が足りません");
            return path;
        }
        
        String ftid = patientId.substring(0, 3);
        String sdid = patientId.substring(3, 6);

        String rootPath = path;
        date = date.substring(0, 8);
        
        rootPath = rootPath + "/"  + ftid + "/" + sdid + "/" + patientId + "/" +date + "/" + type;
        
        logger.debug("targetPath: " + rootPath);
        
        File file = new File(rootPath);
        if(!file.exists()) file.mkdirs();
        
        logger.debug("ルートパスの作成終了");

        return rootPath;
        
    }

    /**
     * 日付管理なしのフォルダを作成してメッセージを置く場所を確保する
     * ただし、患者IDがnullの場合は直下におく
     * @param path
     * @param patientId
     * @param date
     * @return 
     */
    public String makeNoDateRootPath(String path, String patientId , String type) {
        logger.debug("ルートパスの作成開始");
        
        if(patientId == null){
            logger.debug("患者ID指定がないためpathの直下で対応");
            return path;
        }
        
        if(patientId.length() <= 5){
            logger.debug("患者IDの文字数が足りません");
            return path;
        }
        
        String ftid = patientId.substring(0, 3);
        String sdid = patientId.substring(3, 6);

        String rootPath = path;
        
        rootPath = rootPath + "/"  + ftid + "/" + sdid + "/" + patientId + "/-/" + type;
        
        logger.debug("targetPath: " + rootPath);
        
        File file = new File(rootPath);
        if(!file.exists()) file.mkdirs();
        
        logger.debug("ルートパスの作成終了");

        return rootPath;
        
    }

    /**
     * ファイル名の作成
     * @param patientId　対象の患者ID
     * @param type　データ種別
     * @param eventId　オーダNo（PHRではObservationEventIdとする）
     * @param code　診療科コード
     * @return 
     */
    public String makeFileName(String patientId , String type , String eventId , String code , String cdate ,String tdate){
        logger.debug("ファイル名生成開始");
        String filename = "";
        
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date_st = sdf.format(date);
        
        if(tdate != null) date_st = tdate;

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        String date_c = cdate;
        if(!cdate.equals("-")){
            logger.debug(cdate.substring(0, 8));
            date_c = cdate.substring(0, 8);
        }
        
        filename = patientId + "_" + date_c + "_" + type + "_" + eventId + "_" + date_st + "_" + code + "_1";
        
        logger.debug("作成するメッセージのファイル名： " + filename);
        logger.debug("ファイル名生成終了");
        return filename;
    }

   /**
     * ファイル名の作成
     * @param targetDto 対象のセグメントDto
     * @param splitDto　セパレイト情報
     * @return 
     */
    public String  mekeLine(baseSegmentDto targetDto ,messageSplitDto splitDto){
        String classname = targetDto.getClass().getSimpleName();
        String result = "";
        logger.debug(classname + "セグメントの作成開始");
        
        switch(classname){
            case "MSHDto":
                result = this.makeMSH(targetDto , splitDto); 
                break;
            case "PIDDto":
                result = this.makePID(targetDto , splitDto); 
                break;
            case "PV1Dto":
                result = this.makePV1(targetDto , splitDto); 
                break;
            case "SPMDto":
                result = this.makeSPM(targetDto , splitDto); 
                break;
            case "OBRDto":
                result = this.makeOBR(targetDto , splitDto); 
                break;
            case "ORCDto":
                result = this.makeORC(targetDto , splitDto); 
                break;
            case "OBXDto":
                result = this.makeOBX(targetDto , splitDto); 
                break;
            case "AL1Dto":
                result = this.makeAL1(targetDto , splitDto); 
                break;
            case "IN1Dto":
                result = this.makeIN1(targetDto , splitDto); 
                break;
            case "EVNDto":
                result = this.makeEVN(targetDto , splitDto); 
                break;
            default :
                result = null;
        }
        
        return result;
    }


}
