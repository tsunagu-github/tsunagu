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
import kis.inc.ssmix2storagemaker.Logic.reader.ORCReader;
import kis.inc.ssmix2storagemaker.Logic.reader.PIDReader;
import kis.inc.ssmix2storagemaker.Logic.reader.PRBReader;
import kis.inc.ssmix2storagemaker.Logic.reader.ZI1Reader;
import kis.inc.ssmix2storagemaker.Logic.reader.ZPDReader;
import kis.inc.ssmix2storagemaker.Logic.reader.ZPRReader;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.PPR01.PPR01BaseDto;
import kis.inc.ssmix2storagemaker.dto.PPR01.ProblemDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PRBDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ZI1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ZPDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ZPRDto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * ファイルからPPR-01のモデルに変換する
 * 
 */
public class ModelPPR01 {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ModelPPR01.class);
    
    public ModelDto execute(File file , messageSplitDto splitDto , String cdate) throws IOException{
        logger.debug("PPR-01のモデル取得Start");
        ModelDto result = new ModelDto();
        PPR01BaseDto PPR01 = new PPR01BaseDto();
        
        try {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"JIS"));
            
            String line = bf.readLine();
            String fieldsp = splitDto.getFieldseparator();
            
            List<ProblemDto> problemList = new ArrayList<ProblemDto>();
            List<ZPDDto> zpdList = new ArrayList<ZPDDto>();
            List<ZI1Dto> zi1List = new ArrayList<ZI1Dto>();
            List<ORCDto> orcList = new ArrayList<ORCDto>();
            ProblemDto problem = new ProblemDto();
            while(line != null){
                String[] lines = line.split(fieldsp);
                PPR01.setProblemList(problemList);
                PPR01.setCdate(cdate);
                
                //Field毎の処理に移行
                switch(lines[0]){
                    case "MSH":
                        MSHReader msh = new MSHReader();
                        MSHDto mshDto = msh.readMSH(lines,splitDto);
                        PPR01.setMshDto(mshDto);
                        break;
                    case "PID":
                        PIDReader pid = new PIDReader();
                        PIDDto pidDto = pid.readPID(lines,splitDto);
                        PPR01.setPidDto(pidDto);
                        break;
                    case "PRB":
                        PRBReader prb = new PRBReader();
                        PRBDto prbDto = prb.readPRB(lines,splitDto);
                        problem = new ProblemDto();
                        problemList.add(problem);
                        problem.setPrbDto(prbDto);
                        zpdList = new ArrayList<ZPDDto>();
                        problem.setZpdList(zpdList);
                        zi1List = new ArrayList<ZI1Dto>();
                        problem.setZi1List(zi1List);
                        orcList = new ArrayList<ORCDto>();
                        problem.setOrcList(orcList);
                        break;
                    case "ZPR":
                        ZPRReader zpr = new ZPRReader();
                        ZPRDto zprDto = zpr.readZPR(lines,splitDto);
                        problem.setZprDto(zprDto);
                        break;
                    case "ZPD":
                        ZPDReader zpd = new ZPDReader();
                        ZPDDto zpdDto = zpd.readZPD(lines,splitDto);
                        zpdList.add(zpdDto);
                        break;
                    case "ZI1":
                        ZI1Reader zi1 = new ZI1Reader();
                        ZI1Dto zi1Dto = zi1.readZI1(lines,splitDto);
                        zi1List.add(zi1Dto);
                        break;
                    case "ORC":
                        ORCReader orc = new ORCReader();
                        ORCDto orcDto = orc.readORC(lines,splitDto);
                        orcList.add(orcDto);
                        break;
                }
                
                line = bf.readLine();
                
            }
            bf.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelPPR01.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        result = PPR01;
        
        logger.debug("PPR-01のモデル取得End");
        return result;
    }
}
