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
import kis.inc.ssmix2storagemaker.Logic.reader.CTIReader;
import kis.inc.ssmix2storagemaker.Logic.reader.MSHReader;
import kis.inc.ssmix2storagemaker.Logic.reader.ORCReader;
import kis.inc.ssmix2storagemaker.Logic.reader.PIDReader;
import kis.inc.ssmix2storagemaker.Logic.reader.PV1Reader;
import kis.inc.ssmix2storagemaker.Logic.reader.RXAReader;
import kis.inc.ssmix2storagemaker.Logic.reader.RXCReader;
import kis.inc.ssmix2storagemaker.Logic.reader.RXEReader;
import kis.inc.ssmix2storagemaker.Logic.reader.RXRReader;
import kis.inc.ssmix2storagemaker.Logic.reader.TQ1Reader;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.OMP12.AdministrationDto;
import kis.inc.ssmix2storagemaker.dto.OMP12.OMP12BaseDto;
import kis.inc.ssmix2storagemaker.dto.OMP12.OrderDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.AL1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.CTIDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXADto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXEDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.RXRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.TQ1Dto;

/**
 *
 * @author kis-note
 * ファイルからOMP-12のモデルに変換する
 *
 */
public class ModelOMP12 {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ModelOMP12.class);

    public ModelDto execute(File file , messageSplitDto splitDto , String cdate) throws IOException{
        logger.debug("OMP-12のモデル取得Start");
        ModelDto result = new ModelDto();
        OMP12BaseDto OMP12 = new OMP12BaseDto();

        try {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"JIS"));

            String line = bf.readLine();
            String fieldsp = splitDto.getFieldseparator();

            List<AL1Dto> al1List = new ArrayList<AL1Dto>();
            OrderDto order = new OrderDto();
            List<OrderDto> orderList = new ArrayList<OrderDto>();
            List<RXRDto> rxrList = new ArrayList<RXRDto>();
            List<RXCDto> rxcList = new ArrayList<RXCDto>();
            AdministrationDto administrationDto = new AdministrationDto();
            List<AdministrationDto> administrationList = new ArrayList<AdministrationDto>();
            List<RXADto> rxaList = new ArrayList<RXADto>();
            List<OBXDto> obxList = new ArrayList<OBXDto>();
            List<CTIDto> ctiList = new ArrayList<CTIDto>();
            boolean rxaRepeat = false;
            while(line != null){
                String[] lines = line.split(fieldsp);
                OMP12.setOrderList(orderList);
                OMP12.setCdate(cdate);
                //Field毎の処理に移行
                switch(lines[0]){
                    case "MSH":
                        MSHReader msh = new MSHReader();
                        MSHDto mshDto = msh.readMSH(lines,splitDto);
                        OMP12.setMshDto(mshDto);
                        break;
                    case "PID":
                        PIDReader pid = new PIDReader();
                        PIDDto pidDto = pid.readPID(lines,splitDto);
                        OMP12.setPidDto(pidDto);
                        break;
                    case "AL1":
                        AL1Reader al1 = new AL1Reader();
                        AL1Dto al1Dto = al1.readAL1(lines,splitDto);
                        al1List.add(al1Dto);
                        OMP12.setAl1List(al1List);
                        break;
                    case "PV1":
                        PV1Reader pv1 = new PV1Reader();
                        PV1Dto pv1Dto = pv1.readPV1(lines,splitDto);
                        OMP12.setPv1Dto(pv1Dto);
                        break;
                    case "ORC":
                        ORCReader orc = new ORCReader();
                        ORCDto orcDto = orc.readORC(lines,splitDto);
                        order = new OrderDto();
                        orderList.add(order);
                        rxrList = new ArrayList<RXRDto>();
                        order.setRxrList(rxrList);
                        rxcList = new ArrayList<RXCDto>();
                        order.setRxcList(rxcList);
                        administrationList = new ArrayList<AdministrationDto>();
                        order.setAdministrationList(administrationList);
                        rxaList = new ArrayList<RXADto>();
                        ctiList = new ArrayList<CTIDto>();
                        order.setCtiList(ctiList);
                        order.setOrcDto(orcDto);
                        break;
                    case "RXE":
                        RXEReader rxe = new RXEReader();
                        RXEDto rxeDto = rxe.readRXE(lines,splitDto);
                        order.setRxeDto(rxeDto);
                        break;
                    case "TQ1":
                        TQ1Reader tq1 = new TQ1Reader();
                        TQ1Dto tq1Dto = tq1.readTQ1(lines,splitDto);
                        order.setTq1Dto(tq1Dto);
                        break;
                    case "RXR":
                        RXRReader rxr = new RXRReader();
                        RXRDto rxrDto = rxr.readRXR(lines,splitDto);
                        if(rxaList.size()==0) {
                        	rxrList.add(rxrDto);
                        }
                        else {
                        	administrationDto.setRxrDto(rxrDto);
                        }
                        break;
                    case "RXC":
                        RXCReader rxc = new RXCReader();
                        RXCDto rxcDto = rxc.readRXC(lines,splitDto);
                        rxcList.add(rxcDto);
                        break;
                    case "RXA":
                        if(rxaRepeat==false) {
                            administrationDto = new AdministrationDto();
                            administrationList.add(administrationDto);
                            rxaList = new ArrayList<RXADto>();
                            administrationDto.setRxaList(rxaList);
                            obxList = new ArrayList<OBXDto>();
                            administrationDto.setObxList(obxList);
                        }
                        RXAReader rxa = new RXAReader();
                        RXADto rxaDto = rxa.readRXA(lines,splitDto);
                        rxaList.add(rxaDto);
                        break;
                    case "CTI":
                        CTIReader cti = new CTIReader();
                        CTIDto ctiDto = cti.readCTI(lines,splitDto);
                        ctiList.add(ctiDto);
                        break;
                }

                rxaRepeat = lines[0].equals("RXA");

                line = bf.readLine();

            }
            bf.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModelOMP12.class.getName()).log(Level.SEVERE, null, ex);
        }

        result = OMP12;

        logger.debug("OMP-12のモデル取得End");
        return result;
    }
}
