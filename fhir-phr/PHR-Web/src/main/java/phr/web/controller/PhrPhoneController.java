/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：スマフォアプリ用のコントローラー
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/31
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jp.kis_inc.core.utility.HttpSender;
import phr.config.PhrConfig;
import phr.config.SystemConfigConst;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.PhoneAccessLogsAdapter;
import phr.datadomain.entity.PhoneAccessLogsEntity;
import phr.datadomain.entity.ReminderMessageEntity;
import phr.service.IAccessLogsService;
import phr.service.ICommunicationService;
import phr.service.impl.CommunicationService;
import phr.utility.TypeUtility;
import phr.web.phone.PhoneMessageConst;
import phr.web.phone.dto.RequestBaseDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponsePhrIdNumberingDto;
import phr.web.phone.facade.ChartManagementFacade;
import phr.web.phone.facade.CheckUpResultFacade;
import phr.web.phone.facade.ClinicalTestResultFacade;
import phr.web.phone.facade.CreatePatienFacade;
import phr.web.phone.facade.DeleteMedicineFacade;
import phr.web.phone.facade.DynamicConsentFacade;
import phr.web.phone.facade.EncryptIdFacade;
import phr.web.phone.facade.EntryTakeOverCodeFacade;
import phr.web.phone.facade.FrailResultFacade;
import phr.web.phone.facade.GetCommunicationAddressFacade;
import phr.web.phone.facade.GetCommunicationDetailFacade;
import phr.web.phone.facade.GetCommunicationListFacade;
import phr.web.phone.facade.GetCooperationListFacade;
import phr.web.phone.facade.GetMedicineListFacade;
import phr.web.phone.facade.GetOneTimePasswordFacade;
import phr.web.phone.facade.GetSelfCheckListFacade;
import phr.web.phone.facade.GetTakeOverCodeFacade;
import phr.web.phone.facade.ImportMedicineFacade;
import phr.web.phone.facade.MeasurementResultFacade;
import phr.web.phone.facade.MedicinePatientInputFacade;
import phr.web.phone.facade.NoActionFacade;
import phr.web.phone.facade.OperationBackupRestoreFacade;
import phr.web.phone.facade.OperationCommunicationFacade;
import phr.web.phone.facade.OperationCooperationFacade;
import phr.web.phone.facade.OperationSelfCheckListFacade;
import phr.web.phone.facade.PhoneFacade;
import phr.web.phone.facade.SendOneTimePasswordFacade;
import phr.web.phone.facade.TerminalAuthenticationFacade;
import phr.web.phone.facade.UpdateMedicineFacade;
import phr.web.phone.facade.UpdatePatienFacade;
import phr.web.phone.facade.WithDrawActionFacade;

/**
 *
 * @author daisuke
 */
@Controller
public class PhrPhoneController {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PhrPhoneController.class);
    /**
     * メッセージリソース
     */
    @Autowired
    private MessageSource phoneMessageSource;
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Map<String, String> actionMap;

    /**
     * インジェクション：アクセスログサービス
     */
    @Autowired
    @Qualifier("AccessLogsService")
    private IAccessLogsService accessLogsService;

    @RequestMapping(value = {"phoneWebInterface"}, method = {RequestMethod.POST, RequestMethod.OPTIONS}, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String phoneWebAction(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        logger.debug("Start");
        try {

            // リクエストBodyのJSONテキスト文字列を取得する
            String requestedJsonString = parseRequest(request);
            RequestBaseDto requestDto = gson.fromJson(requestedJsonString, RequestBaseDto.class);
            //アクセスログ出力（RequestInterceptorでHttpServletRequestを触るとSreamが閉じてしまうようなのでここで処理）
// kokoda なんだかエラーになるためコメント
//        AccesslLogBackUpUtility.writePhoneAccessLog(request, accessLogsService, requestDto);

            // ユーザーエージェント取得
            String userAgent = request.getHeader("user-agent");

            // カレントI/Fバージョンの取得
            String currVer = PhrConfig.getSystemConfigProperty(SystemConfigConst.PHONE_IF_VERSION);

            logger.debug("Action = " + requestDto.getAction());
            logger.debug("currVer = " + currVer);
            logger.debug("reqeVer = " + requestDto.getVersion());
            PhoneFacade facade;
            ResponseBaseDto responseDto;

            // カレントバージョンと異なる場合
            if (!currVer.equals(requestDto.getVersion())) {
                String redirectUrl = null;
                responseDto = new ResponseBaseDto();
                if ("1.0".equals(requestDto.getVersion())) {
                    // カレントI/Fバージョンの取得
                    redirectUrl = PhrConfig.getSystemConfigProperty(SystemConfigConst.PHONE_IF_VERSION1_0_URL);
                }
                logger.info("redirectUrl = " + redirectUrl);
                // リダイレクト先に送信する
                if (!TypeUtility.isNullOrEmpty(redirectUrl)) {
                    String returnString = redirectURL(redirectUrl, requestedJsonString);
                    writeAllowOrigin(request, response);
                    return returnString;
                } else {
                    if (!"TariminalAuthAction".equals(requestDto.getAction())) {
                        responseDto.setStatus(ResponseBaseDto.ERROR);
                        responseDto.setMessage(phoneMessageSource.getMessage(PhoneMessageConst.APPVARSION_ERROR, null, null));
                        writeAllowOrigin(request, response);
                        String returnJson = gson.toJson(responseDto);
                        return returnJson;
                   }
                }
            }
            //} else {
                // 認証、PHRID発行、データ引継ぎ以外は認証を行う
                if (!"TariminalAuthAction".equals(requestDto.getAction())
                        && !"CreatePatienAction".equals(requestDto.getAction())
                        && !"EntryTakeOverCode".equals(requestDto.getAction())) {
                    TerminalAuthenticationFacade authFacade = new TerminalAuthenticationFacade();
                    boolean isAuth = authFacade.authenticationTerminal(requestDto);
                    if (!isAuth) {
                        ResponseBaseDto dto = new ResponseBaseDto();
                        dto.setStatus(ResponseBaseDto.TARMINAL_AUTH_ERROR);
                        dto.setMessage(phoneMessageSource.getMessage(PhoneMessageConst.TERMINAL_AUTHENTICATION_FAILURE, null, null));
                        writeAllowOrigin(request, response);
                        String json = gson.toJson(dto);
                        logger.debug("End");
                        return json;

                    }
                }

                if ("TariminalAuthAction".equals(requestDto.getAction())) {
                    // 端末認証
                    facade = new TerminalAuthenticationFacade();
                } else if ("UpdatePatienAction".equals(requestDto.getAction())) {
                    // 利用者更新
                    facade = new UpdatePatienFacade();
                } else if ("CreatePatienAction".equals(requestDto.getAction())) {
                    // PHRID 発行
                    facade = new CreatePatienFacade();
                } else if ("GetClinicalTestResult".equals(requestDto.getAction())) {
                    // 健診結果取得
                    facade = new ClinicalTestResultFacade();
                } else if ("GetTakeOverCode".equals(requestDto.getAction())) {
                    // 引継ぎコード発行
                    facade = new GetTakeOverCodeFacade();
                } else if ("GetOneTimePassword".equals(requestDto.getAction())) {
                    // 参照用ワンタイムパスワード発行
                    facade = new GetOneTimePasswordFacade();
                } else if ("EntryTakeOverCode".equals(requestDto.getAction())) {
                    // 引継ぎ
                    facade = new EntryTakeOverCodeFacade();
                } else if ("GetCheckUpResult".equals(requestDto.getAction())) {
                    // 健診結果(健診・問診・診察)取得
                    facade = new CheckUpResultFacade();
                } else if ("GetCooperationList".equals(requestDto.getAction())) {
                    // 患者連携設定一覧
                    facade = new GetCooperationListFacade();
                } else if ("OperationCooperation".equals(requestDto.getAction())) {
                    // 患者連携設定操作
                    facade = new OperationCooperationFacade();
                } else if ("GetSelfCheckList".equals(requestDto.getAction())) {
                    // 自己測定一覧
                    facade = new GetSelfCheckListFacade();
                } else if ("OperationSelfCheck".equals(requestDto.getAction())) {
                    // 自己測定操作
                    facade = new OperationSelfCheckListFacade();
                } else if ("GetMedicineList".equals(requestDto.getAction())) {
                    // おくすりリスト取得
                    facade = new GetMedicineListFacade();
                } else if ("DeleteMedicine".equals(requestDto.getAction())) {
                    // おくすり削除
                    facade = new DeleteMedicineFacade();
                } else if ("MedicinePatientInput".equals(requestDto.getAction())) {
                    // おくすり患者入力情報更新・登録
                    facade = new MedicinePatientInputFacade();
                } else if ("UpdateMedicine".equals(requestDto.getAction())) {
                    // おくすり更新・登録
                    facade = new UpdateMedicineFacade();
                } else if ("ImportMedicine".equals(requestDto.getAction())) {
                    // おくすりインポート
                    facade = new ImportMedicineFacade();
                } else if ("GetCommunicationList".equals(requestDto.getAction())) {
                    // コミュニケーションリスト取得
                    facade = new GetCommunicationListFacade();
                } else if ("GetCommunicationDetail".equals(requestDto.getAction())) {
                    // コミュニケーション詳細取得＆既読化
                    facade = new GetCommunicationDetailFacade();
                } else if ("OperationCommunication".equals(requestDto.getAction())) {
                    // コミュニケーション情報登録等
                    facade = new OperationCommunicationFacade();
                } else if ("GetCommunicationAddress".equals(requestDto.getAction())) {
                    // コミュニケーション宛先リスト取得
                    facade = new GetCommunicationAddressFacade();
                } else if ("SendOneTimePassword".equals(requestDto.getAction())) {
                    // ワンタイムパスワード送信
                    facade = new SendOneTimePasswordFacade();
                } else if ("OperationBackupRestore".equals(requestDto.getAction())) {
                    // バックアップリストア
                    facade = new OperationBackupRestoreFacade();
                } else if ("EncryptIdAction".equals(requestDto.getAction())) {
                     // ID暗号化
                    facade = new EncryptIdFacade();
                } else if ("ChartListAction".equals(requestDto.getAction())) {
                     // グラフ管理
                    facade = new ChartManagementFacade();
                } else if ("ChartViewAction".equals(requestDto.getAction())) {
                     // グラフ表示
                    facade = new ChartManagementFacade();
                } else if ("ChartNewAction".equals(requestDto.getAction())) {
                     // グラフ作成
                    facade = new ChartManagementFacade();
                } else if ("ChartEntryAction".equals(requestDto.getAction())) {
                     // グラフ登録
                    facade = new ChartManagementFacade();
                } else if ("ChartDeleteAction".equals(requestDto.getAction())) {
                     // グラフ削除
                    facade = new ChartManagementFacade();
                } else if ("FrailSummaryAction".equals(requestDto.getAction())) {
                     // フレイル集計結果
                    facade = new FrailResultFacade();
                } else if ("GetMeasurementResult".equals(requestDto.getAction())) {
                     // 測定・全検査結果
                    facade = new MeasurementResultFacade();
                } else if ("DynamicConsent".equals(requestDto.getAction())) {
                    // ダイナミックコンセント
                   facade = new DynamicConsentFacade();
                } else if ("withDrawPatienAction".equals(requestDto.getAction())) {
                    // 退会登録
                   facade = new WithDrawActionFacade();
                 } else {
                    // アクション不正
                    facade = new NoActionFacade();
                }
                facade.setMessageSource(phoneMessageSource);
                responseDto = facade.execute(requestedJsonString);
            //}
                
                //患者に回答の受付通知を送信する
                if (requestedJsonString.contains("update")){
                  CommunicationService communicationService = new CommunicationService();
                  String phrId = requestDto.getPhrId();
                  String reminderTypeId = "B0000002";
                  int reminderLevel = 9;
                  ReminderMessageEntity entity = new ReminderMessageEntity();
                  entity.setReminderTypeId(reminderTypeId);
                  entity.setReminderLevel(reminderLevel);
                  List<ReminderMessageEntity> reminderMessageList = new ArrayList<>();
                  reminderMessageList.add(entity);
                  
                  try {
                      communicationService.receptionCompleted(
                          phrId, reminderMessageList );
                  } catch(Throwable th){
                      logger.error(th);
                  }
                }

                

            writeAccessLog(requestDto, responseDto, request);
            
            // 未読メッセージ件数取得
            ICommunicationService comService = new CommunicationService();
            int unReadCount = comService.countUnreadCommunication(requestDto.getPhrId());
            responseDto.setUnreadMessageCount(unReadCount);
            
            //  ユーザーエージェント設定
            responseDto.setUserAgent(userAgent);
            responseDto.setBrowserType(getBrowserType(userAgent));

            // クロスサイトAjax対応
            writeAllowOrigin(request, response);

            String returnJson = gson.toJson(responseDto);

            logger.debug("End");
            return returnJson;

        } catch (Throwable t) {
            t.printStackTrace();
            logger.error("", t);
            ResponseBaseDto dto = new ResponseBaseDto();
            dto.setStatus(ResponseBaseDto.ERROR);
            dto.setMessage(t.getMessage());
            writeAllowOrigin(request, response);
            String returnJson = gson.toJson(dto);
            return returnJson;
        }
    }

    /**
     * リクエストBodyのJSONテキスト文字列を取得する
     *
     * @param request
     * @return
     */
    private String parseRequest(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(request.getReader());
            String s = bufferedReader.readLine();//最初の１行を取り込む
            while (s != null) {
                stringBuilder.append(s.trim());
                s = bufferedReader.readLine();//次の１行を読む
            }
            return stringBuilder.toString();

        } catch (IOException ex) {
            throw ex;
        } catch (Exception e) {
            logger.error(e, e);
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
    }

    /**
     * レスポンスにクロスサイトAjaxを許可するように設定する
     *
     * @param request
     * @param response
     */
    private void writeAllowOrigin(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getMethod();
        StringBuffer url = request.getRequestURL();
        logger.debug(method);
        logger.debug(url);

        // 接続元ドメインをHTTPヘッダーの「Origin」より取得
        Enumeration<String> enums = request.getHeaders("Origin");
        String clientDomain = null;
        while (enums.hasMoreElements()) {
            clientDomain = enums.nextElement();
        }
        // 許可ドメインの場合「Access-Control-Allow-Origin」に接続元ドメインを設定する
        if (!TypeUtility.isNullOrEmpty(clientDomain)) {
            response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
            response.setHeader("Access-Control-Allow-Origin", clientDomain);
            response.setHeader("Access-Control-Allow-Headers", "accept, x-prototype-version, x-requested-with, content-type");
            response.setHeader("Access-Control-Max-Age", "-1");
        }
    }

    /**
     * ブラウザの種類を大体区別する
     *
     * @param userAgent
     * @return
     */
    private String getBrowserType(String userAgent) {
        String browserType = "";
        if (userAgent.contains("Windows NT")) {
            browserType = "Windows";
        } else if (userAgent.contains("Android")) {
            browserType = "Android";
        } else if (userAgent.contains("iPhone")) {
            browserType = "iPhone";
        } else {
            browserType = "unkunown";
        }
        return browserType;
    }

    /**
     * バージョン差異のリダイレクト処理
     *
     * @param requestedJsonString
     * @return
     * @throws Throwable
     */
    private String redirectURL(String requestedUrl, String requestedJsonString) throws Throwable {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL httpUrl = new URL(requestedUrl);
            connection = (HttpURLConnection) httpUrl.openConnection();
            HttpSender.sendHttpJsonPost(connection, requestedJsonString);
            // 接続が確立したとき
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            // 戻り値を取得する
            StringBuilder responseString = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                responseString.append(line);
            }
            logger.debug(responseString);

            return responseString.toString();

        } catch (Exception ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (Exception ex) {
                }
            }
        }

    }
    
    private void writeAccessLog(RequestBaseDto requestDto, ResponseBaseDto responseDto, HttpServletRequest request) throws Throwable {
    
        if (actionMap == null) {
            createActionMap();
        }
 
        boolean isWrite = true;
        Long curTimeMillis=System.currentTimeMillis();
        String remoteAddr = request.getHeader("X-Forwarded-For");
        if (remoteAddr == null || remoteAddr.length() == 0) {
            remoteAddr = request.getRemoteAddr();
        }
        String remoteHost = request.getHeader("X-Forwarded-Host");
        if (remoteHost == null || remoteHost.length() == 0) {
            remoteHost = request.getRemoteHost();
        }

        PhoneAccessLogsEntity entity = new PhoneAccessLogsEntity();
        entity.setAccessDatetimeSeq(curTimeMillis);
        entity.setAccessDatetime(new Timestamp(curTimeMillis));
        entity.setPhrId(requestDto.getPhrId());
        if ("CreatePatienAction".equals(requestDto.getAction())) {
            ResponsePhrIdNumberingDto dto = (ResponsePhrIdNumberingDto)responseDto;
            entity.setPhrId(dto.getPhrId());
            if (!ResponseBaseDto.SUCCESS.equals(responseDto.getStatus()))
                isWrite = false;
        }
        entity.setActionId(requestDto.getAction());
        if (actionMap.containsKey(requestDto.getAction())) {
            entity.setActionName(actionMap.get(requestDto.getAction()));
        }
        entity.setUrl(new String(request.getRequestURL()));
        entity.setRefer(request.getHeader("Referer"));
        entity.setRequestMethod(request.getMethod());
        entity.setRemoteAddress(remoteAddr);
        entity.setRemoteHost(remoteHost);
        entity.setAgent(request.getHeader("user-agent"));
        
        if (!isWrite)
            return;
        DataAccessObject dao = null;
        try {
            
            dao = new DataAccessObject();
            PhoneAccessLogsAdapter adapter = new PhoneAccessLogsAdapter(dao.getConnection());
            adapter.insert(entity);

        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }
    private void createActionMap() {
        if (actionMap == null) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("TariminalAuthAction", "端末認証");
            map.put("UpdatePatienAction", "利用者更新");
            map.put("CreatePatienAction", "PHRID 発行");
            map.put("GetClinicalTestResult", "健診結果取得");
            map.put("GetTakeOverCode", "引継ぎコード発行");
            map.put("GetOneTimePassword", "参照用ワンタイムパスワード発行");
            map.put("EntryTakeOverCode", "引継ぎ");
            map.put("GetCheckUpResult", "健診結果(健診・問診・診察)取得");
            map.put("GetCooperationList", "患者連携設定一覧");
            map.put("OperationCooperation", "患者連携設定操作");
            map.put("GetSelfCheckList", "自己測定一覧");
            map.put("OperationSelfCheck", "自己測定操作");
            map.put("GetMedicineList", "おくすりリスト取得");
            map.put("DeleteMedicine", "おくすり削除");
            map.put("UpdateMedicine", "おくすり更新・登録");
            map.put("ImportMedicine", "おくすりインポート");
            map.put("GetCommunicationList", "コミュニケーションリスト取得");
            map.put("GetCommunicationDetail", "コミュニケーション詳細取得＆既読化");
            map.put("OperationCommunication", "コミュニケーション情報登録等");
            map.put("GetCommunicationAddress", "コミュニケーション宛先リスト取得");
            map.put("SendOneTimePassword", "ワンタイムパスワード送信");
            map.put("OperationBackupRestore", "バックアップリストア");
            map.put("EncryptIdAction", "ID暗号化");
            map.put("ChartListAction", "グラフ一覧表示");
            map.put("ChartViewAction", "グラフ表示");
            map.put("ChartNewAction", "グラフ作成／編集");
            map.put("ChartEntryAction", "グラフ登録");
            map.put("ChartDeleteAction", "グラフ削除");
            map.put("FrailSummaryAction", "フレイル集計結果");
            map.put("GetMeasurementResult", "測定・全検査結果");
            actionMap = map;
        }
    }
}
