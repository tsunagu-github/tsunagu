/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.tostorage;

import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.messageSplitDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.AL1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.EVNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.IN1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.MSHDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBRDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.ORCDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PV1Dto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.SPMDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.baseSegmentDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class makeField extends makeSegment{
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(makeField.class);
    
    public String makeMSH(baseSegmentDto targetDto, messageSplitDto splitDto) {
        String fieldsp = splitDto.getFieldseparator();
        String elemntsp = splitDto.getCompseparator();
        String repeatsp = splitDto.getRepeatseparator();
        
        MSHDto msh = (MSHDto) targetDto;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0 ; i <= 21 ; i++){
            String target = msh.getMSH(i);
            if(target == null){
                count++;
                continue;
            }else{
                if(i == 1) continue;
                if(count > 0){
                    for(int n = count ;n >= 0;n--){
                        sb.append(fieldsp);
                    }
                    count = 0;
                }else if(count==0 && i > 1){
                    sb.append(fieldsp);
                }
                
                sb.append(target);
            }
        }

        logger.debug(sb.toString());
        return sb.toString();
    }
    
    public String makeSPM(baseSegmentDto targetDto, messageSplitDto splitDto) {
        String fieldsp = splitDto.getFieldseparator();
        String elemntsp = splitDto.getCompseparator();
        String repeatsp = splitDto.getRepeatseparator();
        
        SPMDto spm = (SPMDto) targetDto;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0 ; i <= 29 ; i++){
             Object target = spm.getSPMOb(i);
            if(target == null){
                count++;
                continue;
            }else{
                if(count > 0){
                    for(int n = count ;n >= 0;n--){
                        sb.append(fieldsp);
                    }
                    count = 0;
                }else if(count==0 && i > 0){
                    sb.append(fieldsp);
                }
                
                String line = this.makeSegment(target, elemntsp, repeatsp);
                sb.append(line);
            }
       }

        logger.debug(sb.toString());
        return sb.toString();
    }

    public String makePV1(baseSegmentDto targetDto, messageSplitDto splitDto) {
        String fieldsp = splitDto.getFieldseparator();
        String elemntsp = splitDto.getCompseparator();
        String repeatsp = splitDto.getRepeatseparator();
        
        PV1Dto pv1 = (PV1Dto) targetDto;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0 ; i <= 33 ; i++){
            Object target = pv1.getPV1Ob(i);
            if(target == null){
                count++;
                continue;
            }else{
                if(count > 0){
                    for(int n = count ;n >= 0;n--){
                        sb.append(fieldsp);
                    }
                    count = 0;
                }else if(count==0 && i > 0){
                    sb.append(fieldsp);
                }
                
                String line = this.makeSegment(target, elemntsp, repeatsp);
                sb.append(line);
            }
        }

        logger.debug(sb.toString());
        return sb.toString();
    }

    
    
    public String makePID(baseSegmentDto targetDto, messageSplitDto splitDto) {
        String fieldsp = splitDto.getFieldseparator();
        String elemntsp = splitDto.getCompseparator();
        String repeatsp = splitDto.getRepeatseparator();
        
        PIDDto pid = (PIDDto) targetDto;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0 ; i <= 33 ; i++){
            Object target = pid.getPIDOb(i);
            if(target == null){
                count++;
                continue;
            }else{
                if(count > 0){
                    for(int n = count ;n >= 0;n--){
                        sb.append(fieldsp);
                    }
                    count = 0;
                }else if(count==0 && i > 0){
                    sb.append(fieldsp);
                }
                
                String line = this.makeSegment(target, elemntsp, repeatsp);
                sb.append(line);
            }
        }

        logger.debug(sb.toString());
        return sb.toString();
    }

    
    public String makeOBR(baseSegmentDto targetDto, messageSplitDto splitDto) {
        String fieldsp = splitDto.getFieldseparator();
        String elemntsp = splitDto.getCompseparator();
        String repeatsp = splitDto.getRepeatseparator();
        
        OBRDto pid = (OBRDto) targetDto;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0 ; i <= 49 ; i++){
            Object target = pid.getOBROb(i);
            if(target == null){
                count++;
                continue;
            }else{
                if(count > 0){
                    for(int n = count ;n >= 0;n--){
                        sb.append(fieldsp);
                    }
                    count = 0;
                }else if(count==0 && i > 0){
                    sb.append(fieldsp);
                }
                
                String line = this.makeSegment(target, elemntsp, repeatsp);
                sb.append(line);
            }
        }

        logger.debug(sb.toString());
        return sb.toString();
    }
    
    public String makeORC(baseSegmentDto targetDto, messageSplitDto splitDto) {
        String fieldsp = splitDto.getFieldseparator();
        String elemntsp = splitDto.getCompseparator();
        String repeatsp = splitDto.getRepeatseparator();
        
        ORCDto orc = (ORCDto) targetDto;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0 ; i <= 30 ; i++){
            Object target = orc.getORCOb(i);
            if(target == null){
                count++;
                continue;
            }else{
                if(count > 0){
                    for(int n = count ;n >= 0;n--){
                        sb.append(fieldsp);
                    }
                    count = 0;
                }else if(count==0 && i > 0){
                    sb.append(fieldsp);
                }
                
                String line = this.makeSegment(target, elemntsp, repeatsp);
                sb.append(line);
            }
        }

        logger.debug(sb.toString());
        return sb.toString();
    }    
    
    public String makeOBX(baseSegmentDto targetDto, messageSplitDto splitDto) {
        String fieldsp = splitDto.getFieldseparator();
        String elemntsp = splitDto.getCompseparator();
        String repeatsp = splitDto.getRepeatseparator();
        
        OBXDto obx = (OBXDto) targetDto;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0 ; i <= 19 ; i++){
            Object target = obx.getOBXOb(i);
            if(target == null){
                count++;
                continue;
            }else{
                if(count > 0){
                    for(int n = count ;n >= 0;n--){
                        sb.append(fieldsp);
                    }
                    count = 0;
                }else if(count==0 && i > 0){
                    sb.append(fieldsp);
                }
                
                String line = this.makeSegment(target, elemntsp, repeatsp);
                sb.append(line);
            }
        }

        logger.debug(sb.toString());
        return sb.toString();
    }    

    public String makeAL1(baseSegmentDto targetDto, messageSplitDto splitDto) {
        String fieldsp = splitDto.getFieldseparator();
        String elemntsp = splitDto.getCompseparator();
        String repeatsp = splitDto.getRepeatseparator();
        
        AL1Dto al1 = (AL1Dto) targetDto;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0 ; i <= 6 ; i++){
            Object target = al1.getAL1Ob(i);
            if(target == null){
                count++;
                continue;
            }else{
                if(count > 0){
                    for(int n = count ;n >= 0;n--){
                        sb.append(fieldsp);
                    }
                    count = 0;
                }else if(count==0 && i > 0){
                    sb.append(fieldsp);
                }
                
                String line = this.makeSegment(target, elemntsp, repeatsp);
                sb.append(line);
            }
        }

        logger.debug(sb.toString());
        return sb.toString();
    }    
        
    public String makeIN1(baseSegmentDto targetDto, messageSplitDto splitDto) {
        String fieldsp = splitDto.getFieldseparator();
        String elemntsp = splitDto.getCompseparator();
        String repeatsp = splitDto.getRepeatseparator();
        
        IN1Dto in1 = (IN1Dto) targetDto;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0 ; i <= 53 ; i++){
            Object target = in1.getIN1Ob(i);
            if(target == null){
                count++;
                continue;
            }else{
                if(count > 0){
                    for(int n = count ;n >= 0;n--){
                        sb.append(fieldsp);
                    }
                    count = 0;
                }else if(count==0 && i > 0){
                    sb.append(fieldsp);
                }
                
                String line = this.makeSegment(target, elemntsp, repeatsp);
                sb.append(line);
            }
        }

        logger.debug(sb.toString());
        return sb.toString();
    }   
    
    public String makeEVN(baseSegmentDto targetDto, messageSplitDto splitDto) {
        String fieldsp = splitDto.getFieldseparator();
        String elemntsp = splitDto.getCompseparator();
        String repeatsp = splitDto.getRepeatseparator();
        
        EVNDto evn = (EVNDto) targetDto;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0 ; i <= 7 ; i++){
            Object target = evn.getEVNOb(i);
            if(target == null){
                count++;
                continue;
            }else{
                if(count > 0){
                    for(int n = count ;n >= 0;n--){
                        sb.append(fieldsp);
                    }
                    count = 0;
                }else if(count==0 && i > 0){
                    sb.append(fieldsp);
                }
                
                String line = this.makeSegment(target, elemntsp, repeatsp);
                sb.append(line);
            }
        }

        logger.debug(sb.toString());
        return sb.toString();
    }   
}
