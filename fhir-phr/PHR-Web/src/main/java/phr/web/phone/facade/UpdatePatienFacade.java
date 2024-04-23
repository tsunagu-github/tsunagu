/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：利用者更新クラス
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.PatientEntity;
import phr.service.IPatientManagementService;
import phr.service.impl.PatientManagementService;
import phr.web.phone.dto.RequestPatientDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponsePatientDto;

/**
 *
 * @author daisuke
 */
public class UpdatePatienFacade extends PhoneFacade {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UpdatePatienFacade.class);
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
        PatientEntity entity = service.getPatient(dto.getPhrId());
        requestDto.getPatientDto().copyFromPatientEntity(entity);
        service.updatePatient(entity);

        ResponseBaseDto responsDto = new ResponseBaseDto();
        responsDto.setStatus(ResponseBaseDto.SUCCESS);
        return responsDto;
    }
}
