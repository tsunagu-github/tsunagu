/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

import java.util.ArrayList;
import java.util.List;
import kis.inc.ssmix2storagemaker.dto.ModelDto;

/**
 *
 * @author kis-note
 * お薬手帳Dto
 */
public class MedicineNoteBoookDto extends ModelDto{
    
    //Versionレコ―ド
    private String version;
    
    //患者情報レコード
    private MedicinePatientDto patientDto;
    
    //患者特記レコード
    private List<MedicinePatientMentionDto> patientMentionList;
    
    //一般用医薬品服用レコード
//    private GenericDrugDto genericDrugDto;
    private List<GenericDrugDto> genericDrugDtoList;
    
    //手帳メモ
//    private BookMemoDto bookMemoDto;
    private List<BookMemoDto> bookMemoDtoList;
    
    //調剤情報
    private List<MedicineInfoDto> medicineInfoList;
    
    //制御情報
    private ControllInfoDto controllInfoDto;
    
    //元テキスト
    private String OriginalText; 

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the patientDto
     */
    public MedicinePatientDto getPatientDto() {
        return patientDto;
    }

    /**
     * @param patientDto the patientDto to set
     */
    public void setPatientDto(MedicinePatientDto patientDto) {
        this.patientDto = patientDto;
    }

    /**
     * @return the genericDrugDto
     */
//    public GenericDrugDto getGenericDrugDto() {
    public GenericDrugDto getGenericDrugDto(int i) {
        return genericDrugDtoList.get(i);
        //return genericDrugDto;
    }

    /**
     * @param genericDrugDto the genericDrugDto to set
     */
//    public void setGenericDrugDto(GenericDrugDto genericDrugDto) {
    public void setGenericDrugDto(GenericDrugDto genericDrugDto) {
        if(genericDrugDtoList == null){
            genericDrugDtoList = new ArrayList();
        }
        genericDrugDtoList.add(genericDrugDto);
        //this.genericDrugDto = genericDrugDto;
    }

    /**
     * @return the boolMemoDto
     */
//    public BookMemoDto getBoohMemoDto() {
    public BookMemoDto getBoohMemoDto(int i) {        
        return bookMemoDtoList.get(i);
        //return bookMemoDto;
    }    

    /**
     * @param bookMemoDto the boolMemoDto to set
     */
//    public void setBookMemoDto(BookMemoDto bookMemoDto) {
    public void setBookMemoDto(BookMemoDto bookMemoDto) {
        if(bookMemoDtoList == null){
            bookMemoDtoList = new ArrayList();
        }        
        bookMemoDtoList.add(bookMemoDto);
        //this.bookMemoDto = bookMemoDto;
    }

    /**
     * @return the medicineInfoList
     */
    public List<MedicineInfoDto> getMedicineInfoList() {
        return medicineInfoList;
    }

    /**
     * @param medicineInfoList the medicineInfoList to set
     */
    public void setMedicineInfoList(List<MedicineInfoDto> medicineInfoList) {
        this.medicineInfoList = medicineInfoList;
    }

    /**
     * @return the controllInfoDto
     */
    public ControllInfoDto getControllInfoDto() {
        return controllInfoDto;
    }

    /**
     * @param controllInfoDto the controllInfoDto to set
     */
    public void setControllInfoDto(ControllInfoDto controllInfoDto) {
        this.controllInfoDto = controllInfoDto;
    }

    /**
     * @return the patientMentionList
     */
    public List<MedicinePatientMentionDto> getPatientMentionList() {
        return patientMentionList;
    }

    /**
     * @param patientMentionList the patientMentionList to set
     */
    public void setPatientMentionList(List<MedicinePatientMentionDto> patientMentionList) {
        this.patientMentionList = patientMentionList;
    }

    /**
     * @return the OriginalText
     */
    public String getOriginalText() {
        return OriginalText;
    }

    /**
     * @param OriginalText the OriginalText to set
     */
    public void setOriginalText(String OriginalText) {
        this.OriginalText = OriginalText;
    }

    /**
     * @return the genericDrugDtoList
     */
    public List<GenericDrugDto> getGenericDrugDtoList() {
        return genericDrugDtoList;
    }

    /**
     * @param genericDrugDtoList the genericDrugDtoList to set
     */
    public void setGenericDrugDtoList(List<GenericDrugDto> genericDrugDtoList) {
        this.genericDrugDtoList = genericDrugDtoList;
    }

    /**
     * @return the bookMemoDtoList
     */
    public List<BookMemoDto> getBookMemoDtoList() {
        return bookMemoDtoList;
    }

    /**
     * @param bookMemoDtoList the bookMemoDtoList to set
     */
    public void setBookMemoDtoList(List<BookMemoDto> bookMemoDtoList) {
        this.bookMemoDtoList = bookMemoDtoList;
    }
}
