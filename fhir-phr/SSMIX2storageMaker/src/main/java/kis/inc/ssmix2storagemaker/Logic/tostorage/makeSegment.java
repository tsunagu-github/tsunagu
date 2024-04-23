/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.tostorage;

import java.util.ArrayList;
import java.util.List;
import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.PLDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XADDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XPNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XTNDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class makeSegment {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(makeSegment.class);
    
    public String makeSegment(Object target , String elemntsp, String repeatsp){
        String classname = target.getClass().getSimpleName();
        String result = "";
        
        logger.debug("makeSegmetnClass : " + classname);
        switch(classname){
            case "String":
                result = (String) target;
                break;
            case "CWEDto":
                CWEDto cwe = (CWEDto) target;
                result = makeCWE(cwe , elemntsp);
                break;
            case "ArrayList":
                List<Object> list = (ArrayList<Object>) target;
                result = makeList(list,elemntsp,repeatsp);
                break;
            case "XADDto":
                XADDto xad = (XADDto) target;
                result = makeXAD(xad , elemntsp);
                break;
            case "XTNDto":
                XTNDto xtn = (XTNDto) target;
                result = makeXTN(xtn , elemntsp);
                break;
            case "XPNDto":
                XPNDto xpn = (XPNDto) target;
                result = makeXPN(xpn , elemntsp);
                break;
            case "XCNDto":
                XCNDto xcn = (XCNDto) target;
                result = makeXCN(xcn , elemntsp);
                break;
            case "PLDto":
                PLDto pl = (PLDto) target;
                result = makePL(pl , elemntsp);
                break;
            case "String[]":
                String[] array = (String[]) target;
                result = makeArray(array , elemntsp);
                break;
        }
        
        return result;
    }

    private String makeCWE(CWEDto cwe, String elemntsp) {
        StringBuilder sb = new StringBuilder();

        int count = 0;
        for(int i = 1 ; i <=6 ; i++){
            String target = cwe.getCWE(i);
            if(target == null){
                count++;
                continue;                    
            }
            if(count > 0){
                for(int m = count; m > 0 ; m--){
                        sb.append(elemntsp);
                }
            }else if(i > 1){
                sb.append(elemntsp);
            }
            sb.append(target);
        }
        
        return sb.toString();
    }
    
    private String makeXAD(XADDto xad, String elemntsp) {
        StringBuilder sb = new StringBuilder();

        int count = 0;
        for(int i = 1 ; i <=8 ; i++){
            String target = xad.getXAD(i);
            if(target == null){
                count++;
                continue;                    
            }
            if(count > 0){
                for(int m = count; m >= 0 ; m--){
                        sb.append(elemntsp);
                }
                count = 0;
            }else if(i > 1){
                sb.append(elemntsp);
            }
            sb.append(target);
        }
        
        return sb.toString();
    }

    private String makeXTN(XTNDto xtn, String elemntsp) {
         StringBuilder sb = new StringBuilder();

        int count = 0;
        for(int i = 1 ; i <=12 ; i++){
            String target = xtn.getXTN(i);
            if(target == null){
                count++;
                continue;                    
            }
            if(count > 0){
                for(int m = count; m > 0 ; m--){
                        sb.append(elemntsp);
                }
            }else if(i > 1){
                sb.append(elemntsp);
            }
            sb.append(target);
        }
        
        return sb.toString();
    }

    private String makeList(List<Object> list, String elemntsp, String repeatsp) {
        StringBuilder sb = new StringBuilder();
        
        boolean flg = false;
        for(Object object : list){
            String repeat = makeSegment(object , elemntsp , repeatsp);
            if(flg)sb.append(repeatsp);
            sb.append(repeat);
            flg = true;
        }
        
        return sb.toString();

    }

    private String makeXPN(XPNDto xpn, String elemntsp) {
        StringBuilder sb = new StringBuilder();

        int count = 0;
        for(int i = 1 ; i <=8 ; i++){
            String target = xpn.getXPN(i);
            if(target == null){
                count++;
                continue;                    
            }
            if(count > 0){
                for(int m = count; m >= 0 ; m--){
                        sb.append(elemntsp);
                }
                count = 0;
            }else if(i > 1){
                sb.append(elemntsp);
            }
            sb.append(target);
        }
        
        return sb.toString();
    }

    private String makeXCN(XCNDto xcn, String elemntsp) {
         StringBuilder sb = new StringBuilder();

        int count = 0;
        for(int i = 1 ; i <=15 ; i++){
            String target = xcn.getXCN(i);
            if(target == null){
                count++;
                continue;                    
            }
            if(count > 0){
                for(int m = count; m > 0 ; m--){
                        sb.append(elemntsp);
                }
            }else if(i > 1){
                sb.append(elemntsp);
            }
            sb.append(target);
        }
        
        return sb.toString();
    }

    private String makePL(PLDto pl, String elemntsp) {
          StringBuilder sb = new StringBuilder();

        int count = 0;
        for(int i = 1 ; i <=6 ; i++){
            String target = pl.getPL(i);
            if(target == null){
                count++;
                continue;                    
            }
            if(count > 0){
                for(int m = count; m > 0 ; m--){
                        sb.append(elemntsp);
                }
            }else if(i > 1){
                sb.append(elemntsp);
            }
            sb.append(target);
        }
        
        return sb.toString();
    }

    private String makeArray(String[] array, String elemntsp) {
        StringBuilder sb = new StringBuilder();

        int count = 0;
        for(int i = 0 ; i <array.length ; i++){
            String target = array[i];
            if(target == null){
                count++;
                continue;                    
            }
            if(count > 0){
                for(int m = count; m > 0 ; m--){
                        sb.append(elemntsp);
                }
            }else if(i > 1){
                sb.append(elemntsp);
            }
            sb.append(target);
        }
        
        return sb.toString();
    }
}
