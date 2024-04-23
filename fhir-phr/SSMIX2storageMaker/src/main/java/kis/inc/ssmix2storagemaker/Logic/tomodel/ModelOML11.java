/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.tomodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.Logic.reader.MSHReader;
import kis.inc.ssmix2storagemaker.Logic.reader.OBRReader;
import kis.inc.ssmix2storagemaker.Logic.reader.OBXReader;
import kis.inc.ssmix2storagemaker.Logic.reader.ORCReader;
import kis.inc.ssmix2storagemaker.Logic.reader.PIDReader;
import kis.inc.ssmix2storagemaker.Logic.reader.PV1Reader;
import kis.inc.ssmix2storagemaker.Logic.reader.SPMReader;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OML11BaseDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OrderDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.SPMDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * ファイルからOML-11のモデルに変換する
 * 
 */
public class ModelOML11 {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ModelOML11.class);
    
    public ModelDto execute(File file , messageSplitDto splitDto , String cdate) throws IOException{
        logger.debug("OML-11のモデル取得Start");
        ModelDto result = new ModelDto();
        OML11BaseDto OML11 = new OML11BaseDto();
        
        try {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"JIS"));
            
            String line = bf.readLine();
            String fieldsp = splitDto.getFieldseparator();
            String elemntsp = splitDto.getCompseparator();
            String repeatsp = splitDto.getRepeatseparator();
            
            List<OrderDto> orderList = new ArrayList<OrderDto>();
            List<OBXDto> obxList = new ArrayList<OBXDto>();
            OrderDto order = new OrderDto();
            while(line != null){
                String[] lines = line.split(fieldsp);
                OML11.setOrderList(orderList);
                OML11.setCdate(cdate);
                
                //Field毎の処理に移行
                switch(lines[0]){
                    case "MSH":
                        MSHReader msh = new MSHReader();
                        MSHDto mshDto = msh.readMSH(lines,splitDto);
                        OML11.setMshDto(mshDto);
                        break;
                    case "PID":
                        PIDReader pid = new PIDReader();
                        PIDDto pidDto = pid.readPID(lines,splitDto);
                        OML11.setPidDto(pidDto);
                        break;
                    case "PV1":
                        PV1Reader pv1 = new PV1Reader();
                        PV1Dto pv1Dto = pv1.readPV1(lines,splitDto);
                        OML11.setPv1Dto(pv1Dto);
                        break;                
                    case "SPM":
                        SPMReader spm = new SPMReader();
                        SPMDto spmDto = spm.readSPM(lines,splitDto);
                        order = new OrderDto();
                        orderList.add(order);
                        order.setSpmDto(spmDto);
                        obxList = new ArrayList<OBXDto>();
                        order.setObxList(obxList);
                        break;
                    case "OBR":
                        OBRReader obr = new OBRReader();
                        OBRDto obrDto = obr.readOBR(lines,splitDto);
                        order.setObrDto(obrDto);
                        break;
                    case "ORC":
                        ORCReader orc = new ORCReader();
                        ORCDto orcDto = orc.readORC(lines,splitDto);
                        order.setOrcDto(orcDto);
                        break;
                    case "OBX":
                        OBXReader obx = new OBXReader();
                        OBXDto obxDto = obx.readOBX(lines,splitDto);
                        obxList.add(obxDto);
                        break;
                }
                
                line = bf.readLine();
                
            }
            bf.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelOML11.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        result = OML11;
        
        logger.debug("OML-11のモデル取得End");
        return result;
    }
}
