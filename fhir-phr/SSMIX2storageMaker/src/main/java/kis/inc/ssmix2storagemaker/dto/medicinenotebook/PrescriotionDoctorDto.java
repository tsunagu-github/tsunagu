/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.medicinenotebook;

/**
 *
 * @author kis-note
 * 処方医師レコード
 */
public class PrescriotionDoctorDto {
    
    //医師氏名
    private String name;
    
    //診療科名
    private String diagnosis;
    
    //レコード作成者
    private String recorder;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the diagnosis
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * @param diagnosis the diagnosis to set
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * @return the recorder
     */
    public String getRecorder() {
        return recorder;
    }

    /**
     * @param recorder the recorder to set
     */
    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }
    
    public PrescriotionDoctorDto setPrescriotionDoctor(String line){
        PrescriotionDoctorDto dto = new PrescriotionDoctorDto();
        String[] elements = line.split(",");
        int size = elements.length;
        
        if(size >= 2)dto.setName(elements[1]);
        if(size >= 3)dto.setDiagnosis(elements[2]);
        if(size >= 4)dto.setRecorder(elements[3]);
        
        return dto;
    }
}
