/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.utility;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.impl.ObservationEventService;

/**
 *
 * @author KISNOTE011
 */
public class ObservationSupportUtility {

    /**
     * ロギングコンポーネント
     *
     */
    private static final Log LOGGER = LogFactory.getLog(TypeUtility.class);

    /**
     * 検査結果の項目値(null)を特定健診の項目値を設定
     *
     * @param kensaList 値設定前検査結果情報（）
     * @param kensinList 特定健診情報
     * @return 設定後検査結果情報
     */
    public static List<ObservationEventEntity> setObsevationKensiToKensa(
            List<ObservationEventEntity> kensaList,List<ObservationEventEntity> kensinList) {
        try {
            List<ObservationEventEntity> retLists = new ArrayList<>();
            if (kensaList == null || kensaList.isEmpty()){
                //itemゼロの情報を返却
                return retLists;
            }else if (kensinList == null || kensinList.isEmpty()){
                //検査結果をそのまま返却
                return kensaList;
            }
            
            //検査結果項目値の設定
            ObservationEventService observationEventService=new ObservationEventService();
            List<ObservationEventEntity> eLates=observationEventService.searchObsevationByFutureDay(
                    kensaList.get(0).getPHR_ID(), new Timestamp(System.currentTimeMillis()),1); //過去直近
            Timestamp setTS=null;
            for(ObservationEventEntity item:eLates){
                if(item.getDataInputTypeCd()==3){
                    //健診の最新日を取得
                    setTS=item.getExaminationDate();
                    break;
                }
            }
            for(int i=0;i<kensaList.size();i++){
                if(kensaList.get(i).getDataInputTypeCd()==1){
                    //値ない場合、健診に値あれば設定(血圧はID異なるので個別指定)
                    for(ObservationEventEntity item:kensinList){
                        if(!(item.getDataInputTypeCd()>=3 && item.getDataInputTypeCd()<=5)){
                            continue;
                        }
                        if(kensaList.get(i).getObservationDefinitionId().equals(item.getObservationDefinitionId())
                                || (kensaList.get(i).getObservationDefinitionId().equals("0000000003")
                                && item.getObservationDefinitionId().equals("0000000081"))
                                || (kensaList.get(i).getObservationDefinitionId().equals("0000000004")
                                && item.getObservationDefinitionId().equals("0000000084"))){
                            
                            if(!TypeUtility.isNullOrEmpty(item.getObservationValue()) && 
                                    (TypeUtility.isNullOrEmpty(kensaList.get(i).getObservationValue()) || 
                                     kensaList.get(i).getExaminationDate().getTime() <item.getExaminationDate().getTime() )){
                                kensaList.get(i).setObservationValue(item.getObservationValue());
                                kensaList.get(i).setUnitValue(item.getUnitValue());
                                kensaList.get(i).setAlertLevelCd(item.getAlertLevelCd());
                                kensaList.get(i).setExaminationDate(item.getExaminationDate());
                                if(kensaList.get(i).getNewExaminationDate()==null ||
                                        kensaList.get(i).getNewExaminationDate().getTime() < item.getExaminationDate().getTime()){
                                    //検査結果がゼロの場合、健診の最新日を設定
                                    kensaList.get(i).setNewExaminationDate(setTS);
                                }
                            }
//                            break;
                        }
                    }
                }else if(kensaList.get(i).getDataInputTypeCd()!=1){
                    //検査結果終わったら終了
                    break;
                }
            }
            return kensaList;
        } catch (Throwable ex) {
            LOGGER.debug(ex);
        }
        return kensaList;
    }
    /**
     * リスト内の同項目IDの内容を最初に値の設定された項目IDにマージする
     * @param sourceList
     * @return 
     */
    public static List<ObservationEventEntity> mergeList(List<ObservationEventEntity> sourceList) {
        Map<String, ObservationEventEntity> map = new LinkedHashMap<>();
        List<ObservationEventEntity> list = new ArrayList<>();
        
        for (ObservationEventEntity entity : sourceList) {
            if (entity.getDataInputTypeCd() == 2) {
               list.add(entity);
               continue;
            }
            
            if(map.containsKey(entity.getObservationDefinitionId() + entity.getDataInputTypeCd())) {
                ObservationEventEntity mapEntity = map.get(entity.getObservationDefinitionId() + entity.getDataInputTypeCd());
                if (! TypeUtility.isNullOrEmpty(entity.getObservationValue()) && 
                        TypeUtility.isNullOrEmpty(mapEntity.getObservationValue())) {
                    map.put(entity.getObservationDefinitionId() + entity.getDataInputTypeCd(), entity);
                }
            } else {
                map.put(entity.getObservationDefinitionId() + entity.getDataInputTypeCd(), entity);
            }
        }
        
        for (Map.Entry<String, ObservationEventEntity> e : map.entrySet()) {
            list.add(e.getValue());
        }
        
        return list;
    }
}
