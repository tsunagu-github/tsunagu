/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Facade;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelADT00;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelADT12;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelADT21;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelADT22;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelADT41;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelADT42;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelADT51;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelADT52;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelADT61;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelExCheckUp;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelExMedicineNoteBook;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelOML11;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelOMP01;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelOMP11;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelOMP12;
import kis.inc.ssmix2storagemaker.Logic.tomodel.ModelPPR01;
import kis.inc.ssmix2storagemaker.Logic.validator.ToModelValidate;
import kis.inc.ssmix2storagemaker.Service.FieldSeparatorSevice;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;

/**
 *
 * @author kis-note
 * 標準ストレージからModelで返す
 */
public class StorageToModelFacade {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(StorageToModelFacade.class);

    /*
    * メッセージリスト
    */
    private List<String> messageList;
    /**
     * 対象ファイルを指定されたデータ種別で取得しModelに格納する
     * @param type データ種別
     * @param file　対象ファイル
     * @return
     */
    public ModelDto exChangeToModel(String type , File file) throws IOException{
        messageList = new ArrayList<String>();
        ToModelValidate val = new ToModelValidate();

        if(!val.saveValidation(file, type)){
            messageList.addAll(val.getMessageList());
            return null;
        }

        //ファイルパスから診療日を取得する
        String cdate = getCdate(file);

        //ファイルから区切り文字情報を取得する
        //判定はファイルごとに行いDtoにつめる
        FieldSeparatorSevice fss = new FieldSeparatorSevice();
        messageSplitDto splitDto = fss.getSeparator(file);

        ModelDto result = new ModelDto();

        switch(type){
            case "OML-11":
                ModelOML11 oml11 = new ModelOML11();
                result = oml11.execute(file,splitDto,cdate);
                break;
            case "ADT-00":
                ModelADT00 adt00 = new ModelADT00();
                result = adt00.execute(file,splitDto,cdate);
                break;
            case "ADT-12":
                ModelADT12 adt12 = new ModelADT12();
                result = adt12.execute(file,splitDto,cdate);
                break;
            case "ADT-21":
                ModelADT21 adt21 = new ModelADT21();
                result = adt21.execute(file,splitDto,cdate);
                break;
            case "ADT-22":
                ModelADT22 adt22 = new ModelADT22();
                result = adt22.execute(file,splitDto,cdate);
                break;
            case "ADT-41":
                ModelADT41 adt41 = new ModelADT41();
                result = adt41.execute(file,splitDto,cdate);
                break;
            case "ADT-42":
                ModelADT42 adt42 = new ModelADT42();
                result = adt42.execute(file,splitDto,cdate);
                break;
            case "ADT-51":
                ModelADT51 adt51 = new ModelADT51();
                result = adt51.execute(file,splitDto,cdate);
                break;
            case "ADT-52":
                ModelADT52 adt52 = new ModelADT52();
                result = adt52.execute(file,splitDto,cdate);
                break;
            case "PPR-01":
                ModelPPR01 ppr01 = new ModelPPR01();
                result = ppr01.execute(file,splitDto,cdate);
                break;
            case "ADT-61":
                ModelADT61 adt61 = new ModelADT61();
                result = adt61.execute(file,splitDto,cdate);
                break;
            case "OMP-01":
                ModelOMP01 omp01 = new ModelOMP01();
                result = omp01.execute(file,splitDto,cdate);
                break;
            case "OMP-11":
                ModelOMP11 omp11 = new ModelOMP11();
                result = omp11.execute(file,splitDto,cdate);
                break;
            case "OMP-12":
                ModelOMP12 omp12 = new ModelOMP12();
                result = omp12.execute(file,splitDto,cdate);
                break;
            case "MEDICALBOOK":
                ModelExMedicineNoteBook mnb = new ModelExMedicineNoteBook();
                result = mnb.execute(file);
                break;
            case "CHECKUP":
                //CHECKUPデータのモデル
                ModelExCheckUp mcu = new ModelExCheckUp();
                result = mcu.execute(file);
                break;
        }

        return result;
    }

    private String getCdate(File file) {
        String path = file.getParent();
        logger.debug(path);

        File t1 = new File(path);
        String p1 = t1.getParent();

        File t2 = new File(p1);
        String p2 = t2.getName();

        return p2;
    }
}
