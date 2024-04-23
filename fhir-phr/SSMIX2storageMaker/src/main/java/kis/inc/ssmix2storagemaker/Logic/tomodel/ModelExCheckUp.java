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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jp.kis_inc.csvconverter.src.controller.CsvConvertController;
import jp.kis_inc.csvconverter.src.dto.ConvertDto;
import kis.inc.ssmix2storagemaker.config.Ssmix2MakerConfig;
import kis.inc.ssmix2storagemaker.config.SystemConfigConst;
import kis.inc.ssmix2storagemaker.dto.CHECKUP.CheckUpXMLDto;
import kis.inc.ssmix2storagemaker.dto.ModelDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.BookMemoDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.ControllInfoDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.DispensingDateDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.DoctorInfoDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.DoesSupplementalDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.DoseRecordDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.DosingCautionDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.DrugCautionDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.DrugInfoDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.DrugRecordDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.DrugSupplementalDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.GenericDrugDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.MedicalOrgInfoDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.MedicineInfoDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.MedicineNoteBoookDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.MedicinePatientDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.MedicinePatientMentionDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.OfferRecordDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.PatientEntryDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.PrescriotionDoctorDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.PrescriotionInfoDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.PrescriptionCautionDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.PrescriptionMedicalOrgDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.RPInfoDto;
import kis.inc.ssmix2storagemaker.dto.medicinenotebook.RemarksDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 * 健診XMLからCSVコンバータを使用してモデル取得
 * 健診XML取得用CSVコンバータスキーマが必須です！！
 */
public class ModelExCheckUp {
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ModelExCheckUp.class);
    
    public ModelDto execute(File file) throws IOException{
        CheckUpXMLDto resultDto = new CheckUpXMLDto();
        resultDto.setFilePath(file.getPath());
        CsvConvertController ccf = new CsvConvertController();
        String schemPath = Ssmix2MakerConfig.getConfigProperty(SystemConfigConst.SCHEM_PATH); 
        ccf.setSchempath(schemPath);
        ConvertDto convertResult = null;
        String filename = file.getName();
        File targetfile = file;
        if(!filename.toUpperCase().endsWith(".XML")){
            for(File onefile:file.listFiles()){
                String filename2 = onefile.getName();
                if(!filename2.toUpperCase().endsWith(".XML")){
                    continue;
                }
                targetfile = onefile;
            }
        }
        try(InputStream inputStream = new FileInputStream(targetfile);) {            
            convertResult = ccf.executeTypeInput(inputStream);
            if(convertResult == null){
            }else{
                resultDto.setInsureNo(convertResult.getInsureNo());
                resultDto.setExaminationDate(convertResult.getExaminationDate());
                resultDto.setObservationList(convertResult.getObservationList());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MedicineNoteBoookDto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultDto;
    }
    
}
