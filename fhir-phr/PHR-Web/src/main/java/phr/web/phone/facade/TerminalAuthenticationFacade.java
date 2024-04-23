/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：端末認証クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
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
import phr.service.IUserAuthenticationService;
import phr.service.impl.UserAuthenticationService;
import phr.web.phone.PhoneMessageConst;
import phr.web.phone.dto.RequestBaseDto;
import phr.web.phone.dto.RequestTerminalAuthDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponsePatientDto;

/**
 *
 * @author daisuke
 */
public class TerminalAuthenticationFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(TerminalAuthenticationFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 端末認証処理を開始する
     *
     * @param json
     * @return
     * @throws java.lang.Throwable
     */
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {

        IUserAuthenticationService userAuthenticationService = new UserAuthenticationService();

        RequestTerminalAuthDto requestDto = gson.fromJson(json, RequestTerminalAuthDto.class);
        PatientEntity entity = userAuthenticationService.authenticationPatient(
                requestDto.getPhrId(), requestDto.getTimestamp(), requestDto.getKeyValue(), requestDto.getTokenId());

        ResponseBaseDto dto;
        if (entity != null) {
            dto = new ResponsePatientDto(entity);
            dto.setStatus(ResponseBaseDto.SUCCESS);
            // アプリ起動時にサーバに存在確認のリクエストを投げる
            int rowCount = userAuthenticationService.insertRecord(entity.getPHR_ID());
        } else {
            dto = new ResponseBaseDto();
            dto.setStatus(ResponseBaseDto.TARMINAL_AUTH_ERROR);
            dto.setMessage(messageSource.getMessage(PhoneMessageConst.TERMINAL_AUTHENTICATION_FAILURE, null, null));
        }
        return dto;
    }
    /**
     * 端末認証を行う
     * @param requestDto
     * @return
     * @throws Throwable 
     */
    public boolean authenticationTerminal(RequestBaseDto requestDto) throws Throwable {

        IUserAuthenticationService userAuthenticationService = new UserAuthenticationService();

        PatientEntity entity = userAuthenticationService.authenticationPatient(
                requestDto.getPhrId(), requestDto.getTimestamp(), requestDto.getKeyValue(), null);
        return entity != null;
    }
}
