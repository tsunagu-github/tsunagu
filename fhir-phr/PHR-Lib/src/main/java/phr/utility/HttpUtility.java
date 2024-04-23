/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.utility;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author daisuke
 */
public class HttpUtility {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(HttpUtility.class);
    /**
     * 初期化有無
     */
    private static boolean isInit = false;

    /**
     * HTTPコネクションを作成する
     *
     * @param dto
     * @return
     * @throws HttpConnectionException
     */
    public static HttpURLConnection createHttpURLConnection(HttpConnectDto dto) throws HttpConnectionException {
        logger.debug("Start");
        HttpURLConnection connection = null;
        URL httpsUrl;
        try {
            // クライアント証明書有無を取得
            boolean isClientAuth = dto.isClientAuth();

            // プロキシの利用有無を取得
            boolean isProxy = dto.isProxy();

            // プロキシ認証の有無を取得
            boolean isProxyAuth = dto.isProxyAuth();

            // カレントPathの取得
            //String currPath = new File(".").getAbsoluteFile().getParent();
            // クライアント証明書有りの場合(最初の１回のみ実行)
            if (!isInit && isClientAuth) {
                logger.debug("クライアント証明書有り");

                // --------------------------
                // クライアント証明書関連
                // --------------------------
                String pinStr = dto.getClientCertPinNo();           // 証明書のPIN
                String clientCertFile = dto.getClientCertFile();    // クライアント証明書

                logger.debug("KeyStore作成");
                KeyStore clientStore = KeyStore.getInstance("PKCS12");
                clientStore.load(new FileInputStream(new File(clientCertFile)), pinStr.toCharArray());

                logger.debug("KeyManagerFactory作成");
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

                kmf.init(clientStore, pinStr.toCharArray());
                KeyManager[] kms =null;
                {
                    kms = kmf.getKeyManagers();
                }
                // --------------------------
                // CA証明書関連
                // --------------------------
                String trustCertFile = dto.getClientCaSertFile();       // Javaの証明書ストア(予めCA証明書をセットした物)のファイル
                String keyStorePass = dto.getKeyStorePass();            // Javaの証明書ストアのパスワード

                logger.debug("trustStore作成");
                KeyStore trustStore = KeyStore.getInstance("JKS");
                trustStore.load(new FileInputStream(new File(trustCertFile)), keyStorePass.toCharArray());

                logger.debug("TrustManagerFactory作成");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(trustStore);
                TrustManager[] tms = tmf.getTrustManagers();

                // TLS1.2のクライアント証明書接続をするように初期化する
                logger.debug("TLS");
                SSLContext sslctx = SSLContext.getInstance("TLSv1.2");
                sslctx.init(kms, tms, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sslctx.getSocketFactory());
            }

            // プロキシを利用する場合(最初の１回のみ実行)
            if (!isInit && isProxy) {
                // プロキシ情報をJava環境変数に設定する
                String proxyAddr = dto.getProxyHost();
                String proxyPort = dto.getProxyPort();
                System.setProperty("proxySet", "true");
                System.setProperty("proxyHost", proxyAddr);
                System.setProperty("proxyPort", proxyPort);

                // プロキシ認証をする場合
                if (isProxyAuth) {
                    // 認証情報を設定する
                    String proxyUser = dto.getProxyUser();
                    String proxyPass = dto.getProxyPassword();
                    Authenticator authenticator = new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(proxyUser, proxyPass.toCharArray());
                        }
                    };
                    Authenticator.setDefault(authenticator);
                }
            }

            isInit = true;
            httpsUrl = new URL(dto.getUrl());
            connection = (HttpURLConnection) httpsUrl.openConnection();

        } catch (IOException e) {
            logger.error("IO例外が発生しました。", e);
            throw new HttpConnectionException("E0001", "IO例外が発生しました。");
        } catch (NoSuchAlgorithmException ex) {
            logger.error("SSLコンテキストを取得できませんでした。", ex);
            throw new HttpConnectionException("E0002", "SSLコンテキストを取得できませんでした。");
        } catch (KeyStoreException ex) {
            logger.error("SSL接続においてキーストア例外が発生しました。", ex);
            throw new HttpConnectionException("E0003", "SSL接続においてキーストア例外が発生しました。");
        } catch (CertificateException ex) {
            logger.error("SSL接続において証明書例外が発生しました。", ex);
            throw new HttpConnectionException("E0004", "SSL接続において証明書例外が発生しました。");
        } catch (UnrecoverableKeyException ex) {
            logger.error("SSL接続において鍵を取得できませんでした。", ex);
            throw new HttpConnectionException("E0005", "SSL接続において鍵を取得できませんでした。");
        } catch (KeyManagementException ex) {
            logger.error("SSL接続において鍵管理例外が発生しました。", ex);
            throw new HttpConnectionException("E0006", "SSL接続において鍵管理例外が発生しました。");
        }

        try {
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            //connection.setRequestMethod("GET");
            connection.setRequestProperty("Connection", "Keep-Alive");
        } catch (ProtocolException e) {
            logger.error("ProtocolException例外が発生しました。", e);
            throw new HttpConnectionException("E00071", "ProtocolException例外が発生しました。");
        }

        logger.debug("End");
        return connection;

    }

    /**
     * ファイルをダウンロードする
     *
     * @param connection
     * @param requestMap
     * @param outputPath
     * @return
     * @throws Exception
     */
    public static File downloadFile(HttpURLConnection connection, Map<String, String> requestMap, File outputPath) throws Exception {
        logger.debug("Start");
        File outFile = null;

        sendHttpPost(connection, requestMap);

        int httpStatusCode = connection.getResponseCode();
        if (httpStatusCode != HttpURLConnection.HTTP_OK) {
            logger.error("HttpStatusCode:" + httpStatusCode);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"))) {
                String errorData;
                StringBuilder errorString = new StringBuilder();
                while ((errorData = reader.readLine()) != null) {
                    errorString.append(errorData).append("\r\n");
                }
                logger.error(errorString.toString());
            }
            throw new Exception();
        }

        String disposition = connection.getHeaderField("Content-Disposition");
        if (disposition != null && disposition.contains("=")) {
            String dlFileName = disposition.split("=")[1];
            dlFileName = dlFileName.replaceAll("\"", "");
            outFile = new File(outputPath, dlFileName);
        }

        writeStream(connection.getInputStream(), outFile);

        logger.debug("End");
        return outFile;

    }

    /**
     * HTTP POST送信をする（ファイル無し）
     *
     * @param connection
     * @param textdata
     * @throws IOException
     */
    public static void sendHttpPost(HttpURLConnection connection, Map<String, String> textdata) throws IOException {
        logger.debug("Start");
        StringBuilder sb = new StringBuilder();
        if (textdata != null && textdata.size() > 0) {
            int i = 0;
            for (Map.Entry<String, String> entry : textdata.entrySet()) {
                if (i > 0) {
                    sb.append("&");
                }
                sb.append(urlEncode(entry.getKey())).append("=").append(urlEncode(entry.getValue()));
                i++;
            }
        }
        try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"))) {
            printWriter.print(sb.toString());
        }
        logger.debug("End");
    }

    /**
     * HTTP POST送信をする（ファイル有り）
     *
     * @param connection
     * @param fileMap
     * @param textdata
     * @throws IOException
     */
    public static void sendHttpPost(HttpURLConnection connection,
            Map<String, File> fileMap,
            Map<String, String> textdata) throws IOException {

        final String twoHyphens = "--";
        final String boundary = "*****" + UUID.randomUUID().toString() + "*****";
        final String lineEnd = "\r\n";
        final int maxBufferSize = 1024 * 1024 * 3;

        DataOutputStream outputStream;

        //connection.setDoInput(true);
        //connection.setDoOutput(true);
        //connection.setUseCaches(false);

        //connection.setRequestMethod("POST");
        //connection.setRequestProperty("Connection", "Keep-Alive");

        // POSTデータの形式を設定
        if (TypeUtility.isNull(fileMap) || fileMap.isEmpty()) {
            connection.setRequestProperty("Content-Type", "text/plain; boundary=" + boundary);
        } else {
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        }

        outputStream = new DataOutputStream(connection.getOutputStream());

        if (!TypeUtility.isNull(fileMap) || fileMap.size() > 0) {
            for (Map.Entry<String, File> e : fileMap.entrySet()) {
                String filefield = e.getKey();
                File filePath = e.getValue();
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + filePath.getName() + "\"" + lineEnd);
                outputStream.writeBytes("Content-Type: application/octet-stream" + lineEnd);
                outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
                outputStream.writeBytes(lineEnd);

                try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
                    int bytesAvailable = fileInputStream.available();
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    byte[] buffer = new byte[bufferSize];

                    int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    while (bytesRead > 0) {
                        outputStream.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }
                }
                outputStream.writeBytes(lineEnd);
            }
        }

        if (!TypeUtility.isNull(textdata)) {
            for (Map.Entry<String, String> entry : textdata.entrySet()) {
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + urlEncode(entry.getKey()) + "\"" + lineEnd);
                outputStream.writeBytes("Content-Type: text/plain" + lineEnd);
                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(urlEncode(entry.getValue()));
                outputStream.writeBytes(lineEnd);
            }
        }

        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
        outputStream.close();
    }

    /**
     * 値をURLエンコーディングする
     *
     * @param value
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String urlEncode(String value) throws UnsupportedEncodingException {
        String encodeStr = URLEncoder.encode(value, "UTF-8");
        // Javaでは「*-」はエンコードされない
        // さらに半角スペースは「+」にエンコードされてしまう
        encodeStr = encodeStr.replace("*", "%2a");
        encodeStr = encodeStr.replace("-", "%2d");
        encodeStr = encodeStr.replace("+", "%20");

        return encodeStr;
    }

    /**
     * Streamの内容をファイルに出力する
     *
     * @param inputStream
     * @param outputPath
     * @throws Exception
     */
    public static void writeStream(InputStream inputStream, File outputPath) throws Exception {
        logger.debug("Start");
        int availableByteNumber;
        byte[] buffers = new byte[4096];
        DataInputStream dataInputStream = null;
        DataOutputStream outputStream;
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            File pareFile = outputPath.getParentFile();
            if (!pareFile.exists()) {
                pareFile.mkdirs();
            }

            dataInputStream = new DataInputStream(inputStream);
            fileOutputStream = new FileOutputStream(outputPath);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            outputStream = new DataOutputStream(bufferedOutputStream);
            while ((availableByteNumber = dataInputStream.read(buffers)) > 0) {
                outputStream.write(buffers, 0, availableByteNumber);
            }
        } catch (Exception ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e) {
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e) {
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                }
            }
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (Exception e) {
                }
            }
            logger.debug("End");
        }
    }
}
