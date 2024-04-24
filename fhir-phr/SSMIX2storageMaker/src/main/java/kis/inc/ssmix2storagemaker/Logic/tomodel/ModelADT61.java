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

import kis.inc.ssmix2storagemaker.Logic.reader.EVNReader;
import kis.inc.ssmix2storagemaker.Logic.reader.IAMReader;
import kis.inc.ssmix2storagemaker.Logic.reader.MSHReader;
import kis.inc.ssmix2storagemaker.Logic.reader.PIDReader;
import kis.inc.ssmix2storagemaker.Logic.reader.PV1Reader;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.ADT61.ADT61BaseDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.EVNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IAMDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * ファイルからADT-61のモデルに変換する
 * 
 */
public class ModelADT61 {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ModelADT61.class);
    
    public ModelDto execute(File file , messageSplitDto splitDto , String cdate) throws IOException{
        logger.debug("ADT-61のモデル取得Start");
        ModelDto result = new ModelDto();
        ADT61BaseDto ADT61 = new ADT61BaseDto();
        
        try {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"JIS"));
            
            String line = bf.readLine();
            String fieldsp = splitDto.getFieldseparator();
            
            List<IAMDto> iamList = new ArrayList<IAMDto>();
            while(line != null){
                String[] lines = line.split(fieldsp);
                ADT61.setCdate(cdate);
                //Field毎の処理に移行
                switch(lines[0]){
                    case "MSH":
                        MSHReader msh = new MSHReader();
                        MSHDto mshDto = msh.readMSH(lines,splitDto);
                        ADT61.setMshDto(mshDto);
                        break;
                    case "EVN":
                        EVNReader evn = new EVNReader();
                        EVNDto evnDto = evn.readEVN(lines,splitDto);
                        ADT61.setEvnDto(evnDto);
                        break;
                    case "PID":
                        PIDReader pid = new PIDReader();
                        PIDDto pidDto = pid.readPID(lines,splitDto);
                        ADT61.setPidDto(pidDto);
                        break;
                    case "PV1":
                        PV1Reader pv1 = new PV1Reader();
                        PV1Dto pv1Dto = pv1.readPV1(lines,splitDto);
                        ADT61.setPv1Dto(pv1Dto);
                        break;
                    case "IAM":
                        IAMReader iam = new IAMReader();
                        IAMDto iamDto = iam.readIAM(lines,splitDto);
                        iamList.add(iamDto);
                        ADT61.setIamList(iamList);
                        break;
                }
                
                line = bf.readLine();
                
            }
            bf.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelADT61.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        result = ADT61;
        
        logger.debug("ADT-61のモデル取得End");
        return result;
    }
}