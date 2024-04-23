/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

/**
 *
 * @author chiba
 */
public class SelfCheckListDto {
    
    private String seqno="";
    private String checkDate="";
    private String checkDateView="";
    private String systolicBP="";
    private String diastolicBP="";
    private String weight="";
    private String bloodGlucose="";
    private String observationEventId="";

    public String getSeqno(){
        return seqno;
    }
    public void setSeqNo( String value ){
        this.seqno = value;
    }
    public String getCheckDate(){
        return checkDate;
    }
    public void setCheckDate( String value ){
        this.checkDate = value;
    }
    public String getCheckDateView(){
        return checkDateView;
    }
    public void setCheckDateView( String value ){
        this.checkDateView = value;
    }
    public String getSystolicBp(){
        return systolicBP;
    }
    public void setSystolicBp( String value ){
        this.systolicBP = value;
    }
    public String getDiastolicBp(){
        return diastolicBP;
    }
    public void setDiastolicBp( String value ){
        this.diastolicBP = value;
    }
    public String getWeight(){
        return weight;
    }
    public void setWeight( String value ){
        this.weight = value;
    }
    public String getBloodGlucose(){
        return bloodGlucose;
    }
    public void setBloodGlucose( String value ){
        this.bloodGlucose = value;
    }
    public String getObservationEventId(){
        return observationEventId;
    }
    public void setObservationEventId( String value ){
        this.observationEventId = value;
    }
}
