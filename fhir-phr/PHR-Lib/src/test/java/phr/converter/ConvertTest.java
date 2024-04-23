/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.kis_inc.csvconverter.src.controller.CsvConvertController;
import jp.kis_inc.csvconverter.src.dto.ConvertDto;
import jp.kis_inc.csvconverter.src.dto.ResultObservationDto;
//import jp.kis_inc.csvconverter.src.dto.ResultObservationDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author kis-note
 */
public class ConvertTest {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ConvertTest.class);

    @Test
    public void testConvert() {
       try{
            String path = "F://opt/csvconverter/sample.xml";
            File file = new File(path);


            CsvConvertController ccf = new CsvConvertController();

            ConvertDto resultdto = ccf.execute(file);

            if(resultdto != null ){
               System.out.println("保険者番号 : " + resultdto.getInsureNo() + " , 実施日 : " + resultdto.getExaminationDate());

                List<ResultObservationDto> list = resultdto.getObservationList();
                for(ResultObservationDto dto : list){
                    System.out.println("code : " + dto.getCode() + " , value : " + dto.getValue());
                }

                logger.debug("取得成功");   
                
                File rfile = new File("F://opt/csvconverter/jlac10.csv");
                InputStream inputStream = null;
                Reader reader = null;
                FileReader fr = null;
                Map<String,String> codeMap = new HashMap<String,String>();
                try {
                    fr = new FileReader(rfile);
                    BufferedReader br = new BufferedReader(fr);

                    String line;
                    boolean b = false;
                    while ((line = br.readLine()) != null) {
                        if(!b){
                            b = true;
                            continue;
                        }
                        
                        String[] lines = line.split(",");
                        codeMap.put(lines[0],lines[1]);
                        logger.debug("取得成功");
                    }
                    
                    for(ResultObservationDto dto : list){
                        System.out.println(codeMap.get(dto.getCode())+ ", " +  dto.getValue());
                    }

                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ConvertTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(ConvertTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ConvertTest.class.getName()).log(Level.SEVERE, null, ex);
                }

                
                
            }else{
                List<String> elist = ccf.getErrList();

                for(String message : elist){
                    System.out.println(message);
                }
                logger.debug("取得失敗");
            }
        }catch (Throwable t) {
        }
    }
    
//    @Test
//    public void testCSVConvert() {
//        try{
//            String path = "F://opt/csvconverter/sample.xml";
//            File file = new File(path);
//
//            CsvConvertController ccf = new CsvConvertController();
//
//            File rfile = ccf.executeCSV(file);
//
//            if(rfile.getName().equals("result.csv")){
//                InputStream inputStream = null;
//                Reader reader = null;
//                FileReader fr = null;
//                try {
//                    fr = new FileReader(rfile);
//                    BufferedReader br = new BufferedReader(fr);
//
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        String[] lines = line.split(",");
//                        logger.debug("取得成功");
//                    }
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(ConvertTest.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (UnsupportedEncodingException ex) {
//                        Logger.getLogger(ConvertTest.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(ConvertTest.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }else{
//                logger.debug("取得失敗");
//            }
//        }catch (Throwable t) {
//        }
//
//    }

}
