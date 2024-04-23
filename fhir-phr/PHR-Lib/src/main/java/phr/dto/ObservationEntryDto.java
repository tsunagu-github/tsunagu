/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.dto;

import java.util.List;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;

/**
 *
 * @author KISNOTE011
 */
public class ObservationEntryDto {
    /*
     * 検査結果情報
     */
    private ObservationEventEntity observationEventEntity;
    public ObservationEventEntity getObservationEventEntity(){
        return observationEventEntity;
    }
    public void setObservationEventEntity(ObservationEventEntity observationEventEntity){
        this.observationEventEntity = observationEventEntity;
    }
    
    /*
     * 検査結果項目情報
     */
    private List<ObservationEntity> observationEntity;
    public List<ObservationEntity> getObservationEntity(){
        return observationEntity;
    }
    public void setObservationEntity(List<ObservationEntity> observationEntity){
        this.observationEntity = observationEntity;
    }
    
    /*
     * 重複情報
     */
    private boolean sameFlg=false;
    public boolean getSameFlg(){
        return sameFlg;
    }
    public void setSameFlg(boolean sameFlg){
        this.sameFlg = sameFlg;
    }
    
    /*
     * 重複ファイルパス
     */
    private String samePath="";
    public String getSamePath(){
        return samePath;
    }
    public void setSamePath(String samePath){
        this.samePath = samePath;
    }
    
    /*
     * 更新情報
     */
    private boolean updateFlg=false;
    public boolean getUpdateFlg(){
        return updateFlg;
    }
    public void setUpdateFlg(boolean updateFlg){
        this.updateFlg = updateFlg;
    }
    
    /*
     * 検査日
     */
    private String examinationDate;
    public String getExaminationDate() {
        return examinationDate;
    }
    public void setExaminationDate(String examinationDate) {
        this.examinationDate = examinationDate;
    }
    
    /*
     * zipパス
     */
    private String zipPath;
    public String getZipPath() {
        return zipPath;
    }
    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }
    
    /*
     * zipファイル
     */
    private String zipFile;
    public String getZipFile() {
        return zipFile;
    }
    public void setZipFile(String zipFile) {
        this.zipFile = zipFile;
    }
    
    /**
     * 
     */
    private String tempPath;
    public String getTempPath() {
        return tempPath;
    }
    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }
    
}
