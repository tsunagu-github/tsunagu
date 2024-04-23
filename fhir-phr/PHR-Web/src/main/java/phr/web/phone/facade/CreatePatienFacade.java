/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：利用者作成クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/01
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.PatientEntity;
import phr.datadomain.entity.StudyInformationEntity;
import phr.datadomain.entity.UtilizationConsentEntity;
import phr.service.IPatientManagementService;
import phr.service.impl.PatientManagementService;
import phr.web.phone.dto.RequestPatientDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponsePatientDto;
import phr.web.phone.dto.ResponsePhrIdNumberingDto;

/**
 *
 * @author daisuke
 */
public class CreatePatienFacade extends PhoneFacade {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(CreatePatienFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    /**
     * 端末認証処理を開始する
     *
     * @param json
     * @return
     */
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {

        IPatientManagementService service = new PatientManagementService();

        RequestPatientDto requestDto = gson.fromJson(json, RequestPatientDto.class);

        ResponsePatientDto dto = requestDto.getPatientDto();
        
        PatientEntity entity = requestDto.getPatientDto().createPatientEntity();
        String phrId = service.createPatient(entity);

        ResponsePhrIdNumberingDto responsDto = new ResponsePhrIdNumberingDto();
        if (phrId == null) {
            responsDto.setStatus(ResponseBaseDto.ERROR);
            responsDto.setMessage("PHR IDの採番でエラーが発生しました");
        } else {
//            // PHR_ID発行時にStudyInformationテーブルの情報をUtilizationConsentテーブルに登録する
//            logger.debug("StudyInformationテーブルの情報をUtilizationConsentテーブルに登録");
//            // まず、有効な研究情報レコードを全て取得
//            List<StudyInformationEntity> list = new ArrayList<>();
//            list = service.getStudyInformation();
//            // 次に、登録するためのList<UtilizationConsentEntity>を作成
//            List<UtilizationConsentEntity> uList = new ArrayList<>();
//            uList = service.createList(phrId, list);
//            // 作成したList<UtilizationConsentEntity>をDBに登録
//            int rowCount = service.insertList(uList);
//            logger.debug("UtilizationConsentテーブルに登録完了");
            
            responsDto.setPhrId(phrId);
            responsDto.setKeyId(entity.getKyeId());
            responsDto.setStatus(ResponseBaseDto.SUCCESS);
        }
        return responsDto;
    }    
}
