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
import static kis.inc.ssmix2storagemaker.Logic.tostorage.DoEncode.doEncode;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OML11BaseDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OrderDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * ファイルからOML-11のモデルに変換する
 * 
 */
public class StorageOML11 extends StorageBase{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(StorageOML11.class);
    
    public boolean execute(ModelDto modelDto , String path , String patientId , String date , String eventId) throws IOException, ParseException{
        logger.debug("OML-11のメッセージ作成Start");
        String rootPath = path;
        if(patientId != null){
            rootPath = makeDateRootPath(path , patientId , date , "OML-11");
        }

        String fieldsp = "|";
        String elemntsp = "^";
        String repeatsp = "~";
        
        messageSplitDto splitDto = new messageSplitDto();
        splitDto.setFieldseparator(fieldsp);
        splitDto.setCompseparator(elemntsp);
        splitDto.setRepeatseparator(repeatsp);
        
        OML11BaseDto oml11  = (OML11BaseDto) modelDto;
        String filename = makeFileName(patientId , "OML-11" , eventId , "000" , oml11.getCdate() , oml11.getCdate());
        
        String targetPath = rootPath + "/" + filename;
        logger.debug("targetPath : " + targetPath );
        
        File targetFile = new File(targetPath);
        
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"JIS")));

        StringBuilder sb = new StringBuilder();
        
        //MSHセグメントの処理
        String msh = mekeLine(oml11.getMshDto() , splitDto);
        String linecd = System.getProperty("line.separator");
        sb.append(msh);
        sb.append(linecd);
        
        //PIDセグメントの処理
        String pid = mekeLine(oml11.getPidDto() , splitDto);
        sb.append(pid);
        sb.append(linecd);

        //PV1セグメントの処理
        String pv1 = mekeLine(oml11.getPv1Dto() , splitDto);
        if(pv1 != null){
            sb.append(pv1);
            sb.append(linecd);
        }
        
        //Order毎に処理する
        List<OrderDto> orderList = oml11.getOrderList();
        for(OrderDto order : orderList){
            //SPMセグメントの処理
            String spm = mekeLine(order.getSpmDto() , splitDto);
            if(spm != null){
                sb.append(spm);
                sb.append(linecd);
            }
                
            //OBRセグメントの処理
            String obr = mekeLine(order.getObrDto() , splitDto);
            if(obr != null){
                sb.append(obr);
                sb.append(linecd);
            }
            //ORCセグメントの処理
            String orc = mekeLine(order.getOrcDto() , splitDto);
            if(orc != null){
                sb.append(orc);
                sb.append(linecd);
            }
            List<OBXDto> obxList = order.getObxList();
            
            for(OBXDto obxDto : obxList){
                String obx = mekeLine(obxDto , splitDto);
                if(obx != null){
                    sb.append(obx);
                    sb.append(linecd);
                }
            }
        }
        //pw.println(sb.toString());
        String outputText = doEncode(sb.toString());
        pw.println(outputText);
        
        pw.close();

        
        logger.debug("OML-11のメッセージ作成End");
        return true;
    }


}
