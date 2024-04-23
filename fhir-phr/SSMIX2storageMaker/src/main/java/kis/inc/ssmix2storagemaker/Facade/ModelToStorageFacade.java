/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Facade;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kis.inc.ssmix2storagemaker.Service.FieldSeparatorSevice;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelOML11;
import kis.inc.ssmix2storagemaker.Logic.tostorage.StorageADT00;
import kis.inc.ssmix2storagemaker.Logic.tostorage.StorageMedicalNoteBook;
import kis.inc.ssmix2storagemaker.Logic.tostorage.StorageOML11;
import kis.inc.ssmix2storagemaker.Logic.validator.ToModelValidate;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * 標準ストレージからModelで返す
 */
public class ModelToStorageFacade {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ModelToStorageFacade.class);
    
    /*
    * メッセージリスト
    */
    private List<String> messageList;
    /**
     * 対象モデルを指定されたデータ種別のメッセージを作成する
     * @param modelDto 対象モデル
     * @param path 指定されたパス
     * @param patientId 患者ID（nullの場合は指定されたパスの直下にメッセージを置く） 
     * @param date 診療日（日付管理しないときのnullは認める）
     * @param eventId
     * @return 
     * @throws java.io.IOException 
     * @throws java.text.ParseException 
     */
    public boolean exChangeToStorage(ModelDto modelDto,String path , String patientId , String date , String eventId) throws IOException, ParseException{
        messageList = new ArrayList<String>();
        
        String dtoClass = modelDto.getClass().getSimpleName();
        
        logger.debug("ModelDtoのクラス　：" + dtoClass);

        //ファイルから区切り文字情報を取得する
        //判定はファイルごとに行いDtoにつめる
        
        switch(dtoClass){
            case "OML11BaseDto":
                StorageOML11 oml11 = new StorageOML11();
                oml11.execute(modelDto, path, patientId, date, eventId);
                break;
            case "ADT00BaseDto":
                StorageADT00 adt00 = new StorageADT00();
                adt00.execute(modelDto, path, patientId, date, eventId);
                break;        }
        
        return true;
    }

    /**
     * お薬手帳のメッセージを拡張ストレージ収める。
     * このメソッドではお薬手帳のモデルは使用しない
     * @param mnbMap
     * @param path 指定されたパス
     * @param patientId 患者ID（nullの場合は指定されたパスの直下にメッセージを置く） 
     * @param date 診療日（日付管理しないときのnullは認める）
     * @param eventId
     * @return 
     */
    public boolean exMNBChangeToStorage(Map<String , String > mnbMap,String path , String patientId , String date , String eventId) throws IOException, ParseException{
        messageList = new ArrayList<String>();
        
        StorageMedicalNoteBook smnb = new StorageMedicalNoteBook();
        
        for(String name : mnbMap.keySet()){
            smnb.execute(name, mnbMap.get(name), path, patientId);
        }
        
        return true;
    }
}
