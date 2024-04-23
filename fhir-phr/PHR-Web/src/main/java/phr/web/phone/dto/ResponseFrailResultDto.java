/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.dto;

import java.util.Date;

/**
 *
 * @author suzuki
 */
public class ResponseFrailResultDto extends ResponseBaseDto{
    
    //患者氏名
    private String patientName;

    //住所
    private String addressLine;
    
    //実施日
    private String targetDate;
    
    //実施場所
    private String targetLocation;
    
    //受付番号
    private String receiptNumber;
    
    //総合結果
    private int totalResult;
    
    //運動機能結果
    private int bodyFunctionResulet;
    
    //栄養結果
    private int nutritionResult;
    
    //口腔機能結果
    private int oralFuncitionResult;
    
    //閉じこもり予防結果
    private int notIndoorResult;
    
    //もの忘れ結果
    private int forgetfulResult;
    
    //こころの健康結果
    private int mentalResult;
    
    //歯の数
    private String numberOfTeeth;
    
    //租借点数
    private double pointOfTeeth;

    /**
     * 患者氏名
     * 
     * @return the parientName
     */
    public String getParientName() {
        return patientName;
    }

    /**
     * 患者氏名
     * 
     * @param parientName the parientName to set
     */
    public void setPatientName(String parientName) {
        this.patientName = parientName;
    }

    /**
     * 住所
     * 
     * @return the addressLine
     */
    public String getAddressLine() {
        return addressLine;
    }

    /**
     * 住所
     * 
     * @param addressLine the addressLine to set
     */
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    /**
     * 実施日
     * 
     * @return the targetDate
     */
    public String getTargetDate() {
        return targetDate;
    }

    /**
     * 実施日
     * 
     * @param targetDate the targetDate to set
     */
    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    /**
     * 実施場所
     * 
     * @return the targetLocation
     */
    public String getTargetLocation() {
        return targetLocation;
    }

    /**
     * 実施場所
     * 
     * @param targetLocation the targetLocation to set
     */
    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }

    /**
     * 受付番号
     * 
     * @return the receiptNumber
     */
    public String getReceiptNumber() {
        return receiptNumber;
    }

    /**
     * 受付番号
     * 
     * @param receiptNumber the receiptNumber to set
     */
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    /**
     * 総合結果
     * 
     * @return the totalResult
     */
    public int getTotalResult() {
        return totalResult;
    }

    /**
     * 総合結果
     * 
     * @param totalResult the totalResult to set
     */
    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    /**
     * 運動機能結果
     * 
     * @return the bodyFunctionResulet
     */
    public int getBodyFunctionResulet() {
        return bodyFunctionResulet;
    }

    /**
     * 運動機能結果
     * 
     * @param bodyFunctionResulet the bodyFunctionResulet to set
     */
    public void setBodyFunctionResulet(int bodyFunctionResulet) {
        this.bodyFunctionResulet = bodyFunctionResulet;
    }

    /**
     * 栄養結果
     * 
     * @return the nutritionResult
     */
    public int getNutritionResult() {
        return nutritionResult;
    }

    /**
     * 栄養結果
     * 
     * @param nutritionResult the nutritionResult to set
     */
    public void setNutritionResult(int nutritionResult) {
        this.nutritionResult = nutritionResult;
    }

    /**
     * 口腔機能結果
     * 
     * @return the oralFuncitionResult
     */
    public int getOralFuncitionResult() {
        return oralFuncitionResult;
    }

    /**
     * 口腔機能結果
     * 
     * @param oralFuncitionResult the oralFuncitionResult to set
     */
    public void setOralFuncitionResult(int oralFuncitionResult) {
        this.oralFuncitionResult = oralFuncitionResult;
    }

    /**
     * 閉じこもり予防結果
     * 
     * @return the notIndoorResult
     */
    public int getNotIndoorResult() {
        return notIndoorResult;
    }

    /**
     * 閉じこもり予防結果
     * 
     * @param notIndoorResult the notIndoorResult to set
     */
    public void setNotIndoorResult(int notIndoorResult) {
        this.notIndoorResult = notIndoorResult;
    }

    /**
     * もの忘れ結果
     * 
     * @return the forgetfulResult
     */
    public int getForgetfulResult() {
        return forgetfulResult;
    }

    /**
     * もの忘れ結果
     * 
     * @param forgetfulResult the forgetfulResult to set
     */
    public void setForgetfulResult(int forgetfulResult) {
        this.forgetfulResult = forgetfulResult;
    }

    /**
     * こころの健康結果
     * 
     * @return the mentalResult
     */
    public int getMentalResult() {
        return mentalResult;
    }

    /**
     * こころの健康結果
     * 
     * @param mentalResult the mentalResult to set
     */
    public void setMentalResult(int mentalResult) {
        this.mentalResult = mentalResult;
    }

    /**
     * 歯の数
     * 
     * @return the numberOfTeeth
     */
    public String getNumberOfTeeth() {
        return numberOfTeeth;
    }

    /**
     * 歯の数
     * 
     * @param numberOfTeeth the numberOfTeeth to set
     */
    public void setNumberOfTeeth(String numberOfTeeth) {
        this.numberOfTeeth = numberOfTeeth;
    }

    /**
     * 咀嚼点数
     * 
     * @return the pointOfTeeth
     */
    public double getPointOfTeeth() {
        return pointOfTeeth;
    }

    /**
     * 咀嚼点数
     * 
     * @param pointOfTeeth the pointOfTeeth to set
     */
    public void setPointOfTeeth(double pointOfTeeth) {
        this.pointOfTeeth = pointOfTeeth;
    }
    
}
