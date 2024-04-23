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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
 * 電子お薬手帳ファイルからモデル
 * 対象のファイルをモデルにコンバートする
 * もし、１ファイルに複数日の調剤結果があった場合は変更が必要
 * 
 */
public class ModelExMedicineNoteBook {
     /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ModelExMedicineNoteBook.class);
    
    public ModelDto execute(File file) throws IOException{
        MedicineNoteBoookDto resultDto = new MedicineNoteBoookDto();
        List<MedicineInfoDto> medicalInfoList = new ArrayList<>();
        resultDto.setMedicineInfoList(medicalInfoList);
        
        List<MedicinePatientMentionDto> mpmList = new ArrayList<>();
        resultDto.setPatientMentionList(mpmList);
//        List<PrescriotionInfoDto> pinfoList = new ArrayList<>();
//        List<RPInfoDto> rpinfoList = new ArrayList<>();
//        List<DrugInfoDto> drugInfoList = new ArrayList<>();
        
        try {
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"JIS"));
            
            String line = bf.readLine();
            
            StringBuffer textdata = new StringBuffer();//Files.lines(Paths.get(file.getPath()), Charset.forName("shift-JIS")).collect(Collectors.joining("\r\n"));
            textdata.append(line + "\r\n");
            resultDto.setVersion(line);
            
            line = bf.readLine();
//            MedicineInfoDto midto = new MedicineInfoDto();
            
            boolean pinfoFlg = false;
            boolean rpFlg = true;
//            PrescriotionInfoDto p_infoDto = new PrescriotionInfoDto();
//            DrugInfoDto druginfo = new DrugInfoDto();
//            RPInfoDto rpinfoDto = new RPInfoDto();
            
            int medcineInfoCount = 0;
            int prescriotionInfoCount = 0;
            int rPInfoDtoCount = 0;
            int drugInfoDtoCount = 0;
            
            while(line != null){
                String[] lines = line.split(",");

                //Field毎の処理に移行
                switch(lines[0]){
                    case "1":
                        MedicinePatientDto mpDto = new MedicinePatientDto();
                        resultDto.setPatientDto(mpDto.setPatient(line));
                        break;
                    case "2":
                        MedicinePatientMentionDto mpmDto = new MedicinePatientMentionDto();
                        mpmList.add(mpmDto.setPatientMendtion(line));
                        break;
                    case "3":
                        GenericDrugDto gdDto = new GenericDrugDto();
                        resultDto.setGenericDrugDto(gdDto.setGeneric(line));
                        break;
                    case "4":
                        BookMemoDto bmDto = new BookMemoDto();
                        resultDto.setBookMemoDto(bmDto.setBookMemo(line));
                        break;
                    case "5":
                        MedicineInfoDto midto = new MedicineInfoDto();
                        medicalInfoList.add(midto);
                        medcineInfoCount = medcineInfoCount + 1;
                        
                        List<PrescriotionInfoDto> pinfoList = new ArrayList<>();
                        midto.setPrescriptionInfoList(pinfoList);
                        prescriotionInfoCount = 0;
                        
                        DispensingDateDto ddDto = new DispensingDateDto();
                        midto.setDispensingDateDto(ddDto.setDispensing(line));
                        pinfoFlg = true;
                        rpFlg = true;
                        break;
                    case "11":
                        MedicalOrgInfoDto moiDto = new MedicalOrgInfoDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).setMedicalInfoDto(moiDto.serMedicalOrgInfo(line));
                        break;
                    case "15":
                        DoctorInfoDto diDto = new DoctorInfoDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).setDoctorIndoDto(diDto.setDoctorInfo(line));
                        break;
                    case "51":
                        PrescriptionMedicalOrgDto pmoDto = new PrescriptionMedicalOrgDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).setP_medicalOrgDto(pmoDto.setPrescriptionMedicalOrg(line));
                        break;
                    case "55":
                        PrescriotionInfoDto p_infoDto = new PrescriotionInfoDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().add(p_infoDto);
                        prescriotionInfoCount = prescriotionInfoCount + 1;
                        
                        List<RPInfoDto> rpinfoList = new ArrayList<>();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).setRpInfoList(rpinfoList);
                        rPInfoDtoCount = 0;
                                
                        PrescriotionDoctorDto pdDto = new PrescriotionDoctorDto();
                        p_infoDto.setP_DoctorDto(pdDto.setPrescriotionDoctor(line));
                        
                        pinfoFlg = false;
                        rpFlg = true;
                        break;
                    case "201":
                        if(pinfoFlg){
                           PrescriotionInfoDto p_infoDto201 = new PrescriotionInfoDto();
                           resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().add(p_infoDto201);
                           prescriotionInfoCount = prescriotionInfoCount + 1; 

                            List<RPInfoDto> rpinfoList201 = new ArrayList<>();
                            resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).setRpInfoList(rpinfoList201);
                            rPInfoDtoCount = 0;
                            pinfoFlg = false;
                            rpFlg = true;
                        }
                        if(rpFlg){
                            rpFlg = false;
                            RPInfoDto rpinfoDto = new RPInfoDto();
                            rPInfoDtoCount = rPInfoDtoCount + 1;
                            resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).getRpInfoList().add(rpinfoDto);
                            List<DrugInfoDto> drugInfoList = new ArrayList<>();
                            resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).getRpInfoList().get(rPInfoDtoCount-1).setDrugInfoList(drugInfoList);
                            drugInfoDtoCount = 0;
                        }
                        //pinfoFlg = true;
                        DrugInfoDto druginfo = new DrugInfoDto();
                        drugInfoDtoCount = drugInfoDtoCount + 1;
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).getRpInfoList().get(rPInfoDtoCount-1).getDrugInfoList().add(druginfo);
                        
                        DrugRecordDto drDto = new DrugRecordDto();
                        druginfo.setDrugRecordDto( drDto.setDrugRecord(line));
                        break;
                    case "281":
                        DrugSupplementalDto dsDto = new DrugSupplementalDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).getRpInfoList().get(rPInfoDtoCount-1).getDrugInfoList().get(drugInfoDtoCount - 1).setDrugSupplementalDto(dsDto.setDrugSupplemental(line));
                        break;
                    case "291":
                        DrugCautionDto dcDto = new DrugCautionDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).getRpInfoList().get(rPInfoDtoCount-1).getDrugInfoList().get(drugInfoDtoCount - 1).setDrugCautionDto(dcDto.setDrugCaution(line));
                        break;
                    case "301":
                        DoseRecordDto doserDto = new DoseRecordDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).getRpInfoList().get(rPInfoDtoCount-1).setDoseRecordDto(doserDto.setDoseRecord(line));
                        rpFlg = true;
                        break;
                    case "311":
                        DoesSupplementalDto doessDto = new DoesSupplementalDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).getRpInfoList().get(rPInfoDtoCount-1).setDoesSupplementalDto(doessDto.setDoesSupplemental(line));
                        rpFlg = true;
                        break;
                    case "391":
                        PrescriptionCautionDto pcDto = new PrescriptionCautionDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).getRpInfoList().get(rPInfoDtoCount-1).setPrescriptionCautionDto(pcDto.setPrescriptionCaution(line));
                        rpFlg = true;
                        break;
                    case "401":
                        DosingCautionDto dosingCDto = new DosingCautionDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).setDosingCautionDto(dosingCDto.setDosingCaution(line));
                        break;
                    case "411":
                        OfferRecordDto orDto = new OfferRecordDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).setOfferRecordDto(orDto.setOfferRecord(line));
                        break;
                    case "501":
                        RemarksDto remarkDto = new RemarksDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).setRemarksDto(remarkDto.setRemarksDto(line));
                        break;
                    case "601":
                        PatientEntryDto peDto = new PatientEntryDto();
                        resultDto.getMedicineInfoList().get(medcineInfoCount-1).getPrescriptionInfoList().get(prescriotionInfoCount - 1).setPatientEntryDto(peDto.setPatientEntry(line));
                        break;
                    case "911":
                        ControllInfoDto cinfoDto = new ControllInfoDto();
                        resultDto.setControllInfoDto( cinfoDto.setControllInfo(line));
                        break;
                    default :
                        logger.info("レコードNoが対応していません。" + lines[0]);
                }
                textdata.append(line + "\r\n");
                line = bf.readLine();
                
            }
            resultDto.setOriginalText(textdata.toString());
            bf.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MedicineNoteBoookDto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultDto;
    }
    
}
