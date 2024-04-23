/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.ssmix.maker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XADDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XPNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XTNDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.PIDDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.PatientEntity;
import phr.enums.PrefectureCdEnum;

/**
 *
 * @author kis-note
 */
public class PIDMaker {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PIDMaker.class);    
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    //MSHはほぼ固定でセットする
    //ただし、メッセージ型セットしない。
    public PIDDto execute(PatientEntity patient){
        logger.debug("Start");
        PIDDto pid = new PIDDto();
        
        pid.setPID00("PID");
        pid.setPID01("0001");
        pid.setPID03(patient.getPHR_ID());
        pid.setPID05(makeXPNPatientName(patient));
        pid.setPID07(sdf.format(patient.getBirthDate()));
        pid.setPID08(patient.getSexCd());
        pid.setPID11(makeXADAdress(patient));
        pid.setPID13(makeXTNTelNo(patient));

        Date date = new Date();
        pid.setPID33(sdf2.format(date));

        logger.debug("End");
        return pid;
    }

    /**
     * 氏名のDto作成
     * @param patient
     * @return 
     */
    private List<XPNDto> makeXPNPatientName(PatientEntity patient) {
        logger.debug("Start");
        List<XPNDto> xpnList = new ArrayList<XPNDto>();
        
        //漢字氏名
        XPNDto xpn = new XPNDto();
        xpn.setXPN01(patient.getFamilyName());
        xpn.setXPN02(patient.getGivenName());
        xpn.setXPN07("L");
        xpn.setXPN08("I");
        xpnList.add(xpn);
        
        //カナ氏名
        XPNDto xpnKana = new XPNDto();
        xpnKana.setXPN01(patient.getFamilyKana());
        xpnKana.setXPN02(patient.getGivenKana());
        xpnKana.setXPN07("L");
        xpnKana.setXPN08("P");
        xpnList.add(xpnKana);

        logger.debug("End");
        return xpnList;
    }

    /**
     * 住所のDto作成
     * @param patient
     * @return 
     */
    private XADDto makeXADAdress(PatientEntity patient) {
        logger.debug("Start");
        XADDto xad = new XADDto();
        String preCd = patient.getPrefectureCd();
        String preName = PrefectureCdEnum.selectName(preCd);

        String address = patient.getAddressLine();
        
        String buildingName = patient.getBuildingName();
        
        String result = preName;
        if(address != null && address.trim().length() > 0) result = result + address;
        if(buildingName != null && buildingName.trim().length() > 0) result = result + buildingName;
        
        xad.setXAD01(result);
        String zipcode = patient.getZipCode();
        String zip = zipcode.substring(0, 3) + "-" + zipcode.substring(3, 7);
        xad.setXAD05(zip);
        xad.setXAD06("JAN");
        xad.setXAD07("H");
        
        logger.debug("End");
        return xad;
    }

    private XTNDto makeXTNTelNo(PatientEntity patient) {
        logger.debug("Start");
        XTNDto xtn = new XTNDto();
        
        xtn.setXTN02("PRN");
        xtn.setXTN03("PH");
        xtn.setXTN12(patient.getTelNo());
        logger.debug("End");
        return xtn;
    }
}
