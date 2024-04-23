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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kis.inc.ssmix2storagemaker.Logic.reader.AL1Reader;
import kis.inc.ssmix2storagemaker.Logic.reader.DB1Reader;
import kis.inc.ssmix2storagemaker.Logic.reader.EVNReader;
import kis.inc.ssmix2storagemaker.Logic.reader.IN1Reader;
import kis.inc.ssmix2storagemaker.Logic.reader.MSHReader;
import kis.inc.ssmix2storagemaker.Logic.reader.NK1Reader;
import kis.inc.ssmix2storagemaker.Logic.reader.OBXReader;
import kis.inc.ssmix2storagemaker.Logic.reader.PIDReader;
import kis.inc.ssmix2storagemaker.Logic.reader.PV1Reader;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.ADT00.ADT00BaseDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.AL1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.DB1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.EVNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IN1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.NK1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;

/**
 *
 * @author kis-note
 * ファイルからADT-00のモデルに変換する
 *
 */
public class ModelADT00 {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ModelADT00.class);

    public ModelDto execute(File file , messageSplitDto splitDto , String cdate) throws IOException{
        logger.debug("ADT-00のモデル取得Start");
        ModelDto result = new ModelDto();
        ADT00BaseDto ADT00 = new ADT00BaseDto();

        try {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"JIS"));

            String line = bf.readLine();
            String fieldsp = splitDto.getFieldseparator();
            String elemntsp = splitDto.getCompseparator();
            String repeatsp = splitDto.getRepeatseparator();

            List<NK1Dto> nk1List = new ArrayList<NK1Dto>();
            List<DB1Dto> db1List = new ArrayList<DB1Dto>();
            List<OBXDto> obxList = new ArrayList<OBXDto>();
            List<AL1Dto> al1List = new ArrayList<AL1Dto>();
            List<IN1Dto> in1List = new ArrayList<IN1Dto>();
            while(line != null){
                String[] lines = line.split(fieldsp);
                ADT00.setCdate(cdate);
                //Field毎の処理に移行
                switch(lines[0]){
                    case "MSH":
                        MSHReader msh = new MSHReader();
                        MSHDto mshDto = msh.readMSH(lines,splitDto);
                        ADT00.setMshDto(mshDto);
                        break;
                    case "EVN":
                        EVNReader evn = new EVNReader();
                        EVNDto evnDto = evn.readEVN(lines,splitDto);
                        ADT00.setEvnDto(evnDto);
                        break;
                    case "PID":
                        PIDReader pid = new PIDReader();
                        PIDDto pidDto = pid.readPID(lines,splitDto);
                        ADT00.setPidDto(pidDto);
                        break;
                    case "NK1":
                        NK1Reader nk1 = new NK1Reader();
                        NK1Dto nk1Dto = nk1.readNK1(lines,splitDto);
                        nk1List.add(nk1Dto);
                        ADT00.setNk1List(nk1List);
                        break;
                    case "PV1":
                        PV1Reader pv1 = new PV1Reader();
                        PV1Dto pv1Dto = pv1.readPV1(lines,splitDto);
                        ADT00.setPv1Dto(pv1Dto);
                        break;
                    case "DB1":
                        DB1Reader db1 = new DB1Reader();
                        DB1Dto db1Dto = db1.readDB1(lines,splitDto);
                        db1List.add(db1Dto);
                        ADT00.setDb1List(db1List);
                        break;
                    case "OBX":
                        OBXReader obx = new OBXReader();
                        OBXDto obxDto = obx.readOBX(lines,splitDto);
                        obxList.add(obxDto);
                        ADT00.setObxList(obxList);
                        break;
                    case "AL1":
                        AL1Reader al1 = new AL1Reader();
                        AL1Dto al1Dto = al1.readAL1(lines,splitDto);
                        al1List.add(al1Dto);
                        ADT00.setAl1List(al1List);
                        break;
                    case "IN1":
                        IN1Reader in1 = new IN1Reader();
                        IN1Dto in1Dto = in1.readIN1(lines,splitDto);
                        in1List.add(in1Dto);
                        ADT00.setIn1List(in1List);
                        break;                }

                line = bf.readLine();

            }
            bf.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelADT00.class.getName()).log(Level.SEVERE, null, ex);
        }

        result = ADT00;

        logger.debug("ADT-00のモデル取得End");
        return result;
    }
}
