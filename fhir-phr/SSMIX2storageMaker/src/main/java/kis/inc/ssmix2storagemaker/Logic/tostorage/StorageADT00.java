/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.tostorage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import kis.inc.ssmix2storagemaker.Enums.DataTypeEnum;
import static kis.inc.ssmix2storagemaker.Logic.tostorage.DoEncode.doEncode;
import kis.inc.ssmix2storagemaker.dto.ADT00.ADT00BaseDto;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OML11BaseDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OrderDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.AL1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IN1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * ファイルからOML-11のモデルに変換する
 * 
 */
public class StorageADT00 extends StorageBase{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(StorageADT00.class);
    
    public boolean execute(ModelDto modelDto , String path , String patientId , String date , String eventId) throws IOException, ParseException{
        logger.debug("ADT-00のメッセージ作成Start");
        String rootPath = path;
        if(patientId != null){
            rootPath = makeNoDateRootPath(path , patientId , "ADT-00");
        }

        String fieldsp = "|";
        String elemntsp = "^";
        String repeatsp = "~";
        
        messageSplitDto splitDto = new messageSplitDto();
        splitDto.setFieldseparator(fieldsp);
        splitDto.setCompseparator(elemntsp);
        splitDto.setRepeatseparator(repeatsp);
        
        ADT00BaseDto adt00  = (ADT00BaseDto) modelDto;
        String filename = makeFileName(patientId , "ADT-00" , eventId , "000" , "-" , adt00.getCdate());
        
        String targetPath = rootPath + "/" + filename;
        logger.debug("targetPath : " + targetPath );
        
        File targetFile = new File(targetPath);
        
        FileOutputStream fos = new FileOutputStream(targetFile);
        OutputStreamWriter osw = new OutputStreamWriter(fos ,"JIS");
        BufferedWriter bw  = new BufferedWriter(osw);
        PrintWriter pw = new PrintWriter(bw);

        StringBuilder sb = new StringBuilder();
        
        //MSHセグメントの処理
        String msh = mekeLine(adt00.getMshDto() , splitDto);
        String linecd = System.getProperty("line.separator");
        sb.append(msh);
        sb.append(linecd);
        
         //MSHセグメントの処理
        String evn = mekeLine(adt00.getEvnDto() , splitDto);
        sb.append(evn);
        sb.append(linecd);
        //PIDセグメントの処理
        String pid = mekeLine(adt00.getPidDto() , splitDto);
        sb.append(pid);
        sb.append(linecd);

        //PV1セグメントの処理
        String pv1 = mekeLine(adt00.getPv1Dto() , splitDto);
        if(pv1 != null){
            sb.append(pv1);
            sb.append(linecd);
        }
        
        //OBXの処理する
        List<OBXDto> obxList = adt00.getObxList();

        if(obxList != null && obxList.size() > 0){
            for(OBXDto obxDto : obxList){
                String obx = mekeLine(obxDto , splitDto);
                if(obx != null){
                    sb.append(obx);
                    sb.append(linecd);
                }
            }
        }

        //AL1の処理する
        List<AL1Dto> al1List = adt00.getAl1List();

        if(al1List != null && al1List.size() > 0){
            for(AL1Dto al1Dto : al1List){
                String al1 = mekeLine(al1Dto , splitDto);
                if(al1 != null){
                    sb.append(al1);
                    sb.append(linecd);
                }
            }
        }

        //OBXの処理する
        List<IN1Dto> in1List = adt00.getIn1List();

        if(in1List != null && in1List.size() > 0){
            for(IN1Dto in1Dto : in1List){
                String in1 = mekeLine(in1Dto , splitDto);
                if(in1 != null){
                    sb.append(in1);
                    sb.append(linecd);
                }
            }
        }
        //pw.println(sb.toString());
        String outputText = doEncode(sb.toString());
        pw.println(outputText);
        
        pw.close();
        bw.close();
        osw.close();
        fos.close();
        
        logger.debug("ADT-00のメッセージ作成End");
        return true;
    }

}
