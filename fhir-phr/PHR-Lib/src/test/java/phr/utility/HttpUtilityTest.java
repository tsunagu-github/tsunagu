/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author daisuke
 */
public class HttpUtilityTest {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(HttpUtilityTest.class);

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of sendFCMMessage method, of class FCMNotificationService.
     */
    @Test
    public void ssssTest() throws Throwable {
        try {
        HttpConnectDto dto = new HttpConnectDto();
        dto.setClientAuth(true);
        dto.setClientCaSertFile("/opt/phr/cert/truststore.key");
        dto.setClientCertFile("/opt/phr/cert/keystore.key");
        dto.setClientCertPinNo("phr-backu_pcl12");
        dto.setKeyStorePass("phr_client_store");
        dto.setUrl("https://bctest.j-phr.jp/PHRBackupViewWeb/");
        //dto.setUrl("https://bctest.j-phr.jp/PHRBackupWeb/login");
        HttpURLConnection connect = HttpUtility.createHttpURLConnection(dto);

        Map<String, String> requestMap = new LinkedHashMap<>();
        requestMap.put("TEST","1");
        HttpUtility.sendHttpPost(connect, null);
        // 接続が確立したとき
        BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        String responseData = null;
        // レスポンスデータを取得
        StringBuilder responseString = new StringBuilder();
        while ((responseData = reader.readLine()) != null) {
            responseString.append(responseData).append("\r\n");
        }
        logger.info(responseString.toString());
        connect.disconnect();
        } catch (Exception ex) {
            logger.error("", ex);
            throw ex;
        }
    }
}
