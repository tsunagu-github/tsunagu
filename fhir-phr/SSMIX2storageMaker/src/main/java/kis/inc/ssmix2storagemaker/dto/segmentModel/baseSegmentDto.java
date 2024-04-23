/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.dto.segmentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kis.inc.ssmix2storagemaker.dto.fieldModel.CNEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.CQDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.LA2Dto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.PLDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.RPTDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XADDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XCNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XPNDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.XTNDto;
import kis.inc.ssmix2storagemaker.utility.StringUtility;

/**
 *
 * @author kis-note
 * セグメントの基本情報のDto
 * 各セグメントは必ずこのDtoを継承する
 */
public class baseSegmentDto {

    /**
    * フィールド区切り文字
    */
    private String fieldSp;

    /**
     * 成分セパレータ
     */
    private String elementSp;

    /**
     * 反復セパレータ
     */
    private String repeatSp;

    /**
     * 副成分セパレータ
     */
    private String subcompSp;

    /**
     * @return the fieldSp
     */
    public String getFieldSp() {
        return fieldSp;
    }

    /**
     * @param fieldSp the fieldSp to set
     */
    public void setFieldSp(String fieldSp) {
        this.fieldSp = fieldSp;
    }

    /**
     * @return the elementSp
     */
    public String getElementSp() {
        return elementSp;
    }

    /**
     * @param elementSp the elementSp to set
     */
    public void setElementSp(String elementSp) {
        this.elementSp = elementSp;
    }

    /**
     * @return the repeatSp
     */
    public String getRepeatSp() {
        return repeatSp;
    }

    /**
     * @param repeatSp the repeatSp to set
     */
    public void setRepeatSp(String repeatSp) {
        this.repeatSp = repeatSp;
    }

    /**
     * @return the subcompSp
     */
    public String getSubcompSp() {
        return subcompSp;
    }

    /**
     * @param subcompSp the subcompSp to set
     */
    public void setSubcompSp(String subcompSp) {
        this.subcompSp = subcompSp;
    }

    /**
     * XPN型のセット処理
     * @param value
     * @return
     */
    public List<XPNDto> setXPN(String value){
        if(StringUtility.isNullOrWhiteSpace(value)){
            return null;
        }
        //反復処理
        String[] fields = value.split(repeatSp);
        List<XPNDto> resultList = new ArrayList<XPNDto>();

        for(int i=0; i< fields.length; i++){
            String element = fields[i];
            String[] elements = element.split(elementSp);
            XPNDto dto = new XPNDto();

            for(int m=0; m<elements.length;m++){
                dto.setXPN(elements[m], m+1);
            }
            resultList.add(dto);
        }

        return resultList;
    }

    /**
     * XPN型のゲット処理
     * @param targetList
     * @param e_num
     * @param r_num
     * @return
     */
    public String getXPN(List<XPNDto> targetList , Integer e_num , Integer r_num){
        //反復処理
        XPNDto target = targetList.get(r_num);

        String result = target.getXPN(e_num);

        return result;
    }

     /**
     * CWE型のセット処理
     * @param value
     * @return
     */
    public CWEDto setCWE(String value){
        if(StringUtility.isNullOrWhiteSpace(value)){
            return null;
        }
        String[] elements = value.split(elementSp);
        CWEDto dto = new CWEDto();

        for(int m=0; m<elements.length;m++){
            dto.setCWE(elements[m], m+1);
        }

        return dto;
    }

	/**
	* CWE型のセット処理
	* @param value
	* @return
	*/
    public List<CWEDto> setCWEList(String value){

        List<CWEDto> resultList = new ArrayList<CWEDto>();
        Arrays.asList(value.split(getRepeatSp())).forEach(x->resultList.add(setCWE(x)));

        return resultList;
    }

     /**
     * CWE型のセット処理
     * OBX-5の配列用
     * @param value
     * @return
     */
    public String[] setCWEAL(CWEDto value){
        if(value == null){
            return null;
        }

        String[] values = new String[6];

        for(int i = 0;i<6;i++){
            values[i] = value.getCWE(i+1);
        }

        return values;
    }

    /**
     * CWE型のゲット処理
     * @param target
     * @param e_num
     * @return
     */
    public String getCWE(CWEDto target , Integer e_num){
        String result = target.getCWE(e_num);

        return result;
    }

    /**
     * CWE型のゲット処理
     * @param targetList
     * @param e_num
     * @param r_num
     * @return
     */
    public String getCWE(List<CWEDto> targetList , Integer e_num , Integer r_num){
        //反復処理
    	CWEDto target = targetList.get(r_num);

        String result = target.getCWE(e_num);

        return result;
    }

    /**
     * CWE型のゲット処理
     * @param target
     * @param e_num
     * @return
     */
    public CWEDto getCWEAL(String[] value){
        CWEDto cweDto = new CWEDto();

        for(int i = 0;i<6;i++){
            cweDto.setCWE(value[i], i+1);
        }

        return cweDto;
    }

     /**
     * XAD型のセット処理
     * @param value
     * @return
     */
    public XADDto setXAD(String value){
        if(StringUtility.isNullOrWhiteSpace(value)){
            return null;
        }

        String[] elements = value.split(elementSp);
        XADDto dto = new XADDto();

        for(int m=0; m<elements.length;m++){
            dto.setXAD(elements[m], m+1);
        }

        return dto;
    }

	/**
	* XAD型のセット処理
	* @param value
	* @return
	*/
    public List<XADDto> setXADList(String value){

        List<XADDto> resultList = new ArrayList<XADDto>();
        Arrays.asList(value.split(getRepeatSp())).forEach(x->resultList.add(setXAD(x)));

        return resultList;
    }

    /**
     * XAD型のゲット処理
     * @param target
     * @param e_num
     * @return
     */
    public String getXAD(XADDto target , Integer e_num){
        String result = target.getXAD(e_num);

        return result;
    }

     /**
     * XTN型のセット処理
     */
    public XTNDto setXTN(String value){
        if(StringUtility.isNullOrWhiteSpace(value)){
            return null;
        }

        String[] elements = value.split(elementSp);
        XTNDto dto = new XTNDto();

        for(int m=0; m<elements.length;m++){
            dto.setXTN(elements[m], m+1);
        }

        return dto;
    }

	/**
	* XTN型のセット処理
	* @param value
	* @return
	*/
    public List<XTNDto> setXTNList(String value){

        List<XTNDto> resultList = new ArrayList<XTNDto>();
        Arrays.asList(value.split(getRepeatSp())).forEach(x->resultList.add(setXTN(x)));

        return resultList;
    }


    /**
     * XTN型のゲット処理
     * @param target
     * @param e_num
     * @return
     */
    public String getXTN(XTNDto target , Integer e_num){
        String result = target.getXTN(e_num);

        return result;
    }

    /**
     * PL型のセット処理
     * @param value
     * @return
     */
    public PLDto setPL(String value){
        if(StringUtility.isNullOrWhiteSpace(value)){
            return null;
        }

        String[] elements = value.split(elementSp);
        PLDto dto = new PLDto();

        for(int m=0; m<elements.length;m++){
            dto.setPL(elements[m], m+1);
        }

        return dto;
    }

     /**
     * PL型のゲット処理
     * @param target
     * @param e_num
     * @return
     */
    public String getPL(PLDto target , Integer e_num){
        String result = target.getPL(e_num);

        return result;
    }

     /**
     * XCN型のセット処理
     * @param value
     * @return
     */
    public List<XCNDto> setXCN(String value){
        if(StringUtility.isNullOrWhiteSpace(value)){
            return null;
        }
        //反復処理
        String[] fields = value.split(repeatSp);
        List<XCNDto> resultList = new ArrayList<XCNDto>();

        for(int i=0; i< fields.length; i++){
            String element = fields[i];
            String[] elements = element.split(elementSp);
            XCNDto dto = new XCNDto();

            for(int m=0; m<elements.length;m++){
                dto.setXCN(elements[m], m+1);
            }
            resultList.add(dto);
        }

        return resultList;
    }

    /**
     * XPN型のゲット処理
     * @param targetList
     * @param e_num
     * @param r_num
     * @return
     */
    public String getXCN(List<XCNDto> targetList , Integer e_num , Integer r_num){
        //反復処理
        XCNDto target = targetList.get(r_num);

        String result = target.getXCN(e_num);

        return result;
    }

    /**
    * CNE型のセット処理
    * @param value
    * @return
    */
   public CNEDto setCNE(String value){
       if(StringUtility.isNullOrWhiteSpace(value)){
           return null;
       }
       String[] elements = value.split(elementSp);
       CNEDto dto = new CNEDto();

       for(int m=0; m<elements.length;m++){
           dto.setCNE(elements[m], m+1);
       }

       return dto;
   }

   /**
    * CNE型のゲット処理
    * @param target
    * @param e_num
    * @return
    */
   public String getCNE(CNEDto target , Integer e_num){
       String result = target.getCNE(e_num);

       return result;
   }

   /**
   * RPT型のセット処理
   * @param value
   * @return
   */
   public RPTDto setRPT(String value){
       if(StringUtility.isNullOrWhiteSpace(value)){
    	   return null;
       }

       String[] elements = value.split(elementSp);
       RPTDto dto = new RPTDto(subcompSp);

       for(int m=0; m<elements.length;m++){
           dto.setRPT(elements[m], m+1);
       }

       return dto;
   }

	/**
	* RPT型のセット処理
	* @param value
	* @return
	*/
   public List<RPTDto> setRPTList(String value){

       List<RPTDto> resultList = new ArrayList<>();
       Arrays.asList(value.split(getRepeatSp())).forEach(x->resultList.add(setRPT(x)));

       return resultList;
   }

    /**
    * CQ型のセット処理
    * @param value
    * @return
    */
   public CQDto setCQ(String value){
       if(StringUtility.isNullOrWhiteSpace(value)){
           return null;
       }
       String[] elements = value.split(elementSp);
       CQDto dto = new CQDto(subcompSp);

       for(int m=0; m<elements.length;m++){
           dto.setCQ(elements[m], m+1);
       }

       return dto;
   }

   /**
   * LA2型のセット処理
   * @param value
   * @return
   */
  public LA2Dto setLA2(String value){
      if(StringUtility.isNullOrWhiteSpace(value)){
          return null;
      }
      String[] elements = value.split(elementSp);
      LA2Dto dto = new LA2Dto();

      for(int m=0; m<elements.length;m++){
          dto.setLA2(elements[m], m+1);
      }

      return dto;
  }

   /**
   * LA2型のゲット処理
   * @param target
   * @param e_num
   * @return
   */
  public String getLA2(LA2Dto target , Integer e_num){
      String result = target.getLA2(e_num);

      return result;
  }

}
