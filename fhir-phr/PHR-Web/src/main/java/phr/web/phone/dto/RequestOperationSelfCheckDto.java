/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：自己測定操作リクエストDTO
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

import java.sql.Timestamp;

/**
 *
 * @author chiba
 */
public class RequestOperationSelfCheckDto extends RequestBaseDto {
    
    protected String checkDate="";
    protected String systolicBp="";
    protected String diastolicBp="";
    protected String weight="";
    protected String bloodGlucose="";
    protected String operationMode="";
    protected String observationEventId="";

    public String getCheckdate(){
        return checkDate;
    }
    public void setCheckDate( String value ){
        this.checkDate = value;
    }
    public String getSystolicBp(){
        return systolicBp;
    }
    public void setSystolicBp( String value ){
        this.systolicBp = value;
    }
    public String getDiastolicBp(){
        return diastolicBp;
    }
    public void setDiastolicBp( String value ){
        this.diastolicBp = value;
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
    public String getOperationMode(){
        return operationMode;
    }
    public void setOperationMode( String value ){
        this.operationMode = value;
    }
    public String getObservationEventId(){
        return observationEventId;
    }
    public void setObservationEventId( String value ){
        this.observationEventId = value;
    }
}
