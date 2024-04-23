/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.form;

import java.util.List;
import phr.dto.ManagementItemListDto;

/**
 *
 * @author KISNOTE011
 */
public class ManagementItemSettingForm extends AbstractForm {
    /**
     * 選択年
     */
    private int selectYear;
    
    /**
     * 選択View
     */
    private String selectView;

    /**
     * 保険者管理項目疾病種別
     */
    private String insurerDiseaseType[];

    /**
     * 保険者管理項目並び順
     */
    private String observationDefinitionOrder[];

    public int getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(int selectYear) {
        this.selectYear = selectYear;
    }

    public String getSelectView() {
        return selectView;
    }

    public void setSelectView(String selectView) {
        this.selectView = selectView;
    }

    public String[] getInsurerDiseaseType() {
        return insurerDiseaseType;
    }

    public void setInsurerDiseaseType(String[] insurerDiseaseType) {
        this.insurerDiseaseType = insurerDiseaseType;
    }

    public String[] getObservationDefinitionOrder() {
        return observationDefinitionOrder;
    }

    public void setObservationDefinitionOrder(String[] observationDefinitionOrder) {
        this.observationDefinitionOrder = observationDefinitionOrder;
    }
}
