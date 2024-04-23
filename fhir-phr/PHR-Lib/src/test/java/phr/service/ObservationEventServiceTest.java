/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import phr.service.impl.ObservationEventService;

/**
 *
 * @author daisuke
 */
public class ObservationEventServiceTest {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(ObservationEventServiceTest.class);

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void searchObsevationByPhrIdTargetDateTest() throws Throwable {

        IObservationEventService service = new ObservationEventService();

        String phrId = "1234567-000000";
        Date targetDate = new Date();
        try {
            //ObservationEventDto dto = service.searchObsevationByPhrIdTargetDate(phrId, targetDate);

            //logger.debug(gson.toJson(dto));
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        }
    }

    @Test
    public void スマフォ検査取得テスト() throws Throwable {
/*        try {
            int year = 2016;
            String phrId = "1234567-000000";
            Timestamp timestamp = new Timestamp(new Date().getTime());

            IObservationEventService service = new ObservationEventService();
            //List<DiseaseTypeEntity> diseaseTypeList = service.searchDiseaseType();
            Map<Integer, List<String>> diseaseDefinitionMap = service.searchObservationDefinitionDiseaseForMap(year, phrId, 1);
            List<ObservationEventEntity> observationList = service.searchObsevationByOrderNo(year, phrId, timestamp);

            List<ObservationEventEntity> resultList = new ArrayList<>();
            List<String> testList = new ArrayList<>();
            if (observationList.size() > 0) {
                List<ObservationDefinitionTypeEntity> definitionTypeList
                        = service.searchObservationDefinitionType(observationList.get(0).getInsurerNo(), year);

                for (ObservationDefinitionTypeEntity entity : definitionTypeList) {
                    ObservationEventEntity dto = new ObservationEventEntity();
                    logger.debug(entity.getObservationDefinitionId());
                    Optional<ObservationEventEntity> optEntity
                            = observationList.stream().filter(
                                    x -> x.getObservationDefinitionId().equals(entity.getObservationDefinitionId())
                            ).findFirst();

                    dto.setObservationDefinitionId(entity.getObservationDefinitionId());
                    String cd = "";
                    if (optEntity.isPresent()) {
                        ObservationEventEntity observationEventEntity = optEntity.get();
                        cd = observationEventEntity.getObservationDefinitionId();
                    }
                    testList.add(cd);
                    resultList.add(dto);
                }
            }
            logger.debug(gson.toJson(testList));
        } catch (Throwable ex) {
            logger.error("", ex);
            //throw ex;
        }
*/
    }
}
