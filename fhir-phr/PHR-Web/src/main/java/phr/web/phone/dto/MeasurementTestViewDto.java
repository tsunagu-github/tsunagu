/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：検査結果を格納するDTOクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.dto;

import java.util.List;

import phr.web.dto.ObservationDto;

/**
 *
 * @author daisuke
 */
public class MeasurementTestViewDto extends ResponseBaseDto {
    
    /**
     * 検査結果リスト
     */
    private List<ObservationDto> resultList;

    /**
     * 検査結果リスト
     *
     * @return the resultList
     */
    public List<ObservationDto> getResultList() {
        return resultList;
    }

    /**
     * 検査結果リスト
     *
     * @param resultList the resultList to set
     */
    public void setResultList(List<ObservationDto> resultList) {
        this.resultList = resultList;
    }
}
