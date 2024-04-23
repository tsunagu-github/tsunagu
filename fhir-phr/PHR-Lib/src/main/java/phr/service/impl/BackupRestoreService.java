/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：バックアップリストアサービスクラス
 * 作成者          ：kis-inc
 * 作成日          ：2017/01/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.BackupRestoreEventAdapter;
import phr.datadomain.entity.BackupRestoreActionTypeEntity;
import phr.datadomain.entity.BackupRestoreEventEntity;
import phr.datadomain.entity.BackupRestoreStatusEntity;
import phr.service.IBackupDataService;
import phr.service.IBackupRestoreService;
import phr.service.IRestoraDataService;
import phr.utility.HttpConnectDto;
import phr.utility.HttpConnectionException;
import phr.utility.HttpUtility;

/**
 * バックアップセンターへのバックアップ／リストアを実施するサービスクラス
 * @author daisuke
 */
public class BackupRestoreService implements IBackupRestoreService {

    /**
     * バックアップセンターの基底URL
     */
    private static final String url = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_URL_BASE);
    /**
     * バックアップセンターのバックアップ情報送付URL
     */
    private static final String backupInfoUrl = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_BACKUP_INFO_URL);
    /**
     * バックアップセンターのバックアップデータ送付URL
     */
    private static final String backupDataUrl = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_BACKUP_DATA_URL);

    /**
     * バックアップセンターのバックアップデータ送付URL
     */
    private static final String phrAssociationId = PhrConfig.getConfigProperty(ConfigConst.BACKUP_PHR_ASSOCIATION_NO);
    /**
     * バックアップセンターのバックアップログインID
     */
    private static final String backupLoginId = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_BACKUP_LOGINID);
    /**
     * バックアップセンターのバックアップ用パスワード
     */
    private static final String backupLoginPassword = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_BACKUP_PASSWORD);
    /**
     * バックアップセンターのリストアログインID
     */
    private static final String restoreLoginId = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_RESTORE_LOGINID);
    /**
     * バックアップセンターのリストア用パスワード
     */
    private static final String restoreLoginPassword = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_RESTORE_PASSWORD);
    /**
     * バックアップセンターの移動情報送付URL
     */
    private static final String transferInfoUrl = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_TRANSFER_INFO_URL);
    /**
     * バックアップセンターの移動完了通知URL
     */
    private static final String transferFinishUrl = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_TRANSFER_FINISH_URL);
    /**
     * バックアップセンターのプロジェクト移動情報送付URL
     */
    private static final String transferProjectInfoUrl = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_TRANSFER_PROJECT_INFO_URL);
    /**
     * バックアップセンターのロジェクト移動完了通知URL
     */
    private static final String transferProjectFinishUrl = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_TRANSFER_PROJECT_FINISH_URL);
    /**
     * リストアファイルの一時置き場
     */
    private static final String restoreFileRoot = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_RESTORE_FILE_PATH);
    /**
     * バックアップセンターの削除URL
     */
    private static final String deleteUrl = PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_DELETE_URL);

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreService.class);

    /**
     * 利用者の最終バックアップ日時を返却します。 バックアップが無い場合はNULLを返却します。
     *
     * @param phrId
     * @return
     * @throws Throwable
     */
    @Override
    public Date getLastBackupDate(String phrId) throws Throwable {
        DataAccessObject dao = null;

        try {
            logger.debug("Start");
            dao = new DataAccessObject();
            BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
            BackupRestoreEventEntity entity = adapter.findByLastBackupEntity(phrId);
            if (entity == null) {
                return null;
            }

            return entity.getEndDateTime();
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    /**
     * バックアップ処理を実施します。
     *
     * @throws Throwable
     */
    @Override
    public void executeBackup() throws Throwable {
        DataAccessObject dao = null;
        File backupFile = null;
        logger.debug("Start");

        try {
            dao = new DataAccessObject();
            BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
            List<BackupRestoreEventEntity> entityList = adapter.findByStatus(
                    BackupRestoreActionTypeEntity.ACTION_BACKUP,
                    BackupRestoreStatusEntity.STATUS_RECEPTION);

            if (entityList.isEmpty()) {
                return;
            }

            IBackupDataService backupDataService = new BackupDataService();

            for (BackupRestoreEventEntity entity : entityList) {
                try {
                    // ステータスを処理中にする
                    Date now = new Date();
                    dao.beginTransaction();
                    entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_PROCESSING);
                    entity.setStartDateTiem(new Timestamp(now.getTime()));
                    adapter.update(entity);
                    dao.commitTransaction();
                    entity = adapter.findByPrimaryKey(entity.getBackupRestoreEventId());
                    
                    // バックアップアーカイブを作成
                    backupFile = backupDataService.makeBackupData(entity.getRequestPHR_ID());

                    // バックアップ情報を送信
                    callBackupInfoIF(entity, backupFile);
                    dao.beginTransaction();
                    adapter.update(entity);
                    dao.commitTransaction();
                    entity = adapter.findByPrimaryKey(entity.getBackupRestoreEventId());

                    if (entity.getBackupRestoreStatusCd() == BackupRestoreStatusEntity.STATUS_ERROR) {
                        continue;
                    }

                    // バックアップデータを送信
                    callBackupDataIF(entity, backupFile);

                    // ステータスを完了にする
                    dao.beginTransaction();
                    now = new Date();
                    entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_COMPLETED);
                    entity.setEndDateTime(new Timestamp(now.getTime()));
                    adapter.update(entity);
                    dao.commitTransaction();

                } catch (Throwable th) {
                    logger.error("", th);
                    entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_ERROR);
                    adapter.update(entity);
                    dao.commitTransaction();
                }
            }

        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
            if (backupFile != null) {
                if (backupFile.exists()) {
                    backupFile.delete();
                }
            }
        }
    }

    /**
     * リストア処理を実施します。
     *
     * @throws Throwable
     */
    @Override
    public void executeRestore() throws Throwable {
        DataAccessObject dao = null;
        logger.debug("Start");

        try {
            dao = new DataAccessObject();
            BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
            List<BackupRestoreEventEntity> entityList = adapter.findByStatus(
                    BackupRestoreActionTypeEntity.ACTION_RESTORE,
                    BackupRestoreStatusEntity.STATUS_RECEPTION);

            if (entityList.isEmpty()) {
                return;
            }

            IRestoraDataService restoraDataService = new RestoraDataService();

            for (BackupRestoreEventEntity entity : entityList) {
                try {
                    // ステータスを処理中にする
                    Date now = new Date();
                    dao.beginTransaction();
                    entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_PROCESSING);
                    entity.setStartDateTiem(new Timestamp(now.getTime()));
                    adapter.update(entity);
                    dao.commitTransaction();
                    entity = adapter.findByPrimaryKey(entity.getBackupRestoreEventId());

                    // リストア情報を送信する
                    BackupInfoReturnDto returnDto = callRestoreIF(entity);
                    dao.beginTransaction();
                    adapter.update(entity);
                    dao.commitTransaction();
                    entity = adapter.findByPrimaryKey(entity.getBackupRestoreEventId());
                    if (entity.getBackupRestoreStatusCd() == BackupRestoreStatusEntity.STATUS_ERROR) {
                        continue;
                    }

                    // リストア用ファイルをダウンロードする
                    File zipFile = downloadResroreData(entity, returnDto);
                    dao.beginTransaction();
                    adapter.update(entity);
                    dao.commitTransaction();
                    entity = adapter.findByPrimaryKey(entity.getBackupRestoreEventId());
                    if (entity.getBackupRestoreStatusCd() == BackupRestoreStatusEntity.STATUS_ERROR) {
                        continue;
                    }

                    // 取得したファイルをリストアする
                    restoraDataService.ececuteRestoraData(zipFile, entity.getRestorePHR_ID(), entity.getRequestPHR_ID());

                    // ステータスを完了にする
                    dao.beginTransaction();
                    now = new Date();
                    entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_COMPLETED);
                    entity.setEndDateTime(new Timestamp(now.getTime()));
                    adapter.update(entity);
                    dao.commitTransaction();

                } catch (Throwable th) {
                    logger.error("", th);
                    entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_ERROR);
                    adapter.update(entity);
                    dao.commitTransaction();
                }
            }

        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    /**
     * バックアップ削除処理を実施します。
     *
     * @throws Throwable
     */
    @Override
    public void executeBackupDelete() throws Throwable {
        DataAccessObject dao = null;
        logger.debug("Start");

        try {
            dao = new DataAccessObject();
            BackupRestoreEventAdapter adapter = new BackupRestoreEventAdapter(dao.getConnection());
            List<BackupRestoreEventEntity> entityList = adapter.findByStatus(
                    BackupRestoreActionTypeEntity.ACTION_DELETE,
                    BackupRestoreStatusEntity.STATUS_RECEPTION);

            if (entityList.isEmpty()) {
                return;
            }

            for (BackupRestoreEventEntity entity : entityList) {
                try {
                    // ステータスを処理中にする
                    Date now = new Date();
                    dao.beginTransaction();
                    entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_PROCESSING);
                    entity.setStartDateTiem(new Timestamp(now.getTime()));
                    adapter.update(entity);
                    dao.commitTransaction();
                    entity = adapter.findByPrimaryKey(entity.getBackupRestoreEventId());

                    // 削除情報を送信する
                    callDeleteIF(entity);
                    if (entity.getBackupRestoreStatusCd() != BackupRestoreStatusEntity.STATUS_ERROR) {
                        // ステータスを完了にする
                        now = new Date();
                        entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_COMPLETED);
                        entity.setEndDateTime(new Timestamp(now.getTime()));
                    }

                    dao.beginTransaction();
                    adapter.update(entity);
                    adapter.invalidBackupEvent(entity.getRequestPHR_ID());  // バックアップを無効にする
                    dao.commitTransaction();

                } catch (Throwable th) {
                    logger.error("", th);
                    entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_ERROR);
                    adapter.update(entity);
                    dao.commitTransaction();
                }
            }
        } catch (Throwable ex) {
            logger.error("", ex);
            throw ex;
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    /**
     * バックアップ情報送信I/Fを呼び出すメソッド
     *
     * @param entity
     * @param file
     * @throws Throwable
     */
    private void callBackupInfoIF(BackupRestoreEventEntity entity, File file) throws Throwable {
        Map<String, String> requestMap = new LinkedHashMap<>();
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            // バックアップI/Fにバックアップファイルの情報を送信する
            //URL httpUrl = new URL(url + backupInfoUrl);
            //connection = (HttpURLConnection) httpUrl.openConnection();
            connection = createHttpConnectDto(url + backupInfoUrl);
            
            requestMap.put("phrAssociationId", phrAssociationId);
            requestMap.put("projectId", entity.getRequestInsurerNo());
            requestMap.put("phrId", entity.getRequestPHR_ID());
            requestMap.put("backupLoginId", backupLoginId);
            requestMap.put("backupLoginPassword", backupLoginPassword);
            requestMap.put("restorePassword", entity.getDecriptPassword());
            requestMap.put("dataSize", String.valueOf(file.length()));

            //HttpSender.sendHttpPost(connection, requestMap);
            HttpUtility.sendHttpPost(connection, requestMap);

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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BackupInfoReturnDto returnDto = null;
            returnDto = gson.fromJson(responseString.toString(), BackupInfoReturnDto.class);

            // ステータスが1以外の時はエラー
            if (returnDto.getStatus() == null || returnDto.getStatus() != 1) {
                entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_ERROR);
                entity.setErrorMessage(returnDto.getErrorMessage());
                return;
            }
            entity.setReceiptNo(returnDto.getReceptionId());

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

    /**
     * バックアップデータを送信するI/Fを呼び出すメソッド
     *
     * @param entity
     * @param file
     * @throws Throwable
     */
    private void callBackupDataIF(BackupRestoreEventEntity entity, File file) throws Throwable {
        Map<String, File> requestFileMap = new LinkedHashMap<>();
        Map<String, String> requestMap = new LinkedHashMap<>();
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            // バックアップI/Fにバックアップファイルの情報を送信する
            //URL httpUrl = new URL(url + backupDataUrl);
            //connection = (HttpURLConnection) httpUrl.openConnection();
            connection = createHttpConnectDto(url + backupDataUrl);
            
            requestMap.put("receptionId", entity.getReceiptNo());
            requestMap.put("backupLoginId", backupLoginId);
            requestMap.put("backupLoginPassword", backupLoginPassword);
            requestMap.put("fileName", file.getName());

            requestFileMap.put("backupData", file);

            //HttpSender.sendHttpPost(connection, requestFileMap, requestMap);
            HttpUtility.sendHttpPost(connection, requestFileMap, requestMap);
            
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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BackupInfoReturnDto returnDto = null;
            returnDto = gson.fromJson(responseString.toString(), BackupInfoReturnDto.class);

            // ステータスが1以外の時はエラー
            if (returnDto.getStatus() == null || returnDto.getStatus() != 1) {
                entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_ERROR);
                entity.setErrorMessage(returnDto.getErrorMessage());
            } else {
                entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_COMPLETED);
            }

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

    /**
     * リストアI/Fを呼び出すメソッド
     *
     * @param entity
     * @return
     * @throws Throwable
     */
    private BackupInfoReturnDto callRestoreIF(BackupRestoreEventEntity entity) throws Throwable {
        Map<String, String> requestMap = new LinkedHashMap<>();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            String reqestUrl = transferProjectInfoUrl;
            // リストアI/Fにリストアの情報を送信する
            //if (entity.getRequestPHR_ID().equals(entity.getRestorePHR_ID())) {
            if (!entity.getRestorePhrAssociationNo().equals(phrAssociationId)) {
                reqestUrl = transferInfoUrl;
            }
            //URL httpUrl = new URL(url + reqestUrl);
            //connection = (HttpURLConnection) httpUrl.openConnection();
            connection = createHttpConnectDto(url + reqestUrl);

            requestMap.put("phrAssociationId", phrAssociationId);
            requestMap.put("projectId", entity.getRequestInsurerNo());
            requestMap.put("phrId", entity.getRequestPHR_ID());
            requestMap.put("restoreLoginId", restoreLoginId);
            requestMap.put("restoreLoginPassword", restoreLoginPassword);
            requestMap.put("restorePassword", entity.getDecriptPassword());
            requestMap.put("backupPhrAssociationId", entity.getRestorePhrAssociationNo());
            requestMap.put("backupProjectId", entity.getRestoreProjectNo());
            requestMap.put("backupPhrId", entity.getRestorePHR_ID());

            //HttpSender.sendHttpPost(connection, requestMap);
            HttpUtility.sendHttpPost(connection, requestMap);

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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BackupInfoReturnDto returnDto = null;
            returnDto = gson.fromJson(responseString.toString(), BackupInfoReturnDto.class);

            // ステータスが1以外の時はエラー
            if (returnDto.getStatus() == null || returnDto.getStatus() != 1) {
                entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_ERROR);
                entity.setErrorMessage(returnDto.getErrorMessage());
                return null;
            }
            entity.setReceiptNo(returnDto.getReceptionId());
            return returnDto;

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

    /**
     * リストア用ファイルをダウンロードする
     *
     * @param entity
     * @param dto
     * @return
     * @throws Throwable
     */
    private File downloadResroreData(BackupRestoreEventEntity entity, BackupInfoReturnDto dto) throws Throwable {
        Map<String, String> requestMap = new LinkedHashMap<>();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        File file = null;

        try {

            // 出力用ディレクトリの確認及び生成
            File rootDir = new File(restoreFileRoot);
            if (!rootDir.exists()) {
                rootDir.mkdir();
            }
            File receptionDir = new File(rootDir, entity.getReceiptNo());
            receptionDir.mkdir();
            file = new File(receptionDir, dto.getFileName());

            //URL httpUrl = new URL(url + dto.getRestoreURL());
            //connection = (HttpURLConnection) httpUrl.openConnection();
            connection = createHttpConnectDto(url + dto.getRestoreURL());

            requestMap.put("receptionId", entity.getReceiptNo());
            requestMap.put("restoreLoginId", restoreLoginId);
            requestMap.put("restoreLoginPassword", restoreLoginPassword);
            requestMap.put("restorePassword", entity.getDecriptPassword());

            // ダウンロード用URLにアクセス
            //HttpSender.sendHttpPost(connection, requestMap);
            HttpUtility.sendHttpPost(connection, requestMap);
 
            // 結果を取得する
            InputStream is = connection.getInputStream();
            //reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
                int b;
                while ((b = is.read()) != -1) {
                    fileOutputStream.write(b);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
            }
            is.close();
            reader = null;
            connection.disconnect();

            String status = "1";
            // チェックする
            if (file.length() != dto.getDataSize()) {
                status = "9";
            }
            requestMap.clear();
            requestMap.put("receptionId", entity.getReceiptNo());
            requestMap.put("restoreLoginId", restoreLoginId);
            requestMap.put("restoreLoginPassword", restoreLoginPassword);
            requestMap.put("status", status);

            String reqestUrl = transferProjectFinishUrl;
//            // リストアI/Fにリストアの情報を送信する
//            if (entity.getRequestPHR_ID().equals(entity.getRestorePHR_ID())) {
            if (!entity.getRestorePhrAssociationNo().equals(phrAssociationId)) {
                reqestUrl = transferFinishUrl;
            }
            
            //httpUrl = new URL(url + reqestUrl);
            //connection = (HttpURLConnection) httpUrl.openConnection();
            connection = createHttpConnectDto(url + reqestUrl);

            //HttpSender.sendHttpPost(connection, requestMap);
            HttpUtility.sendHttpPost(connection, requestMap);
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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BackupInfoReturnDto returnDto = null;
            returnDto = gson.fromJson(responseString.toString(), BackupInfoReturnDto.class);

            // ステータスが1以外の時はエラー
            if (returnDto.getStatus() == null || returnDto.getStatus() != 1) {
                entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_ERROR);
                entity.setErrorMessage(returnDto.getErrorMessage());
                return null;
            }

            return file;
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

    /**
     * 削除I/Fを呼び出すメソッド
     *
     * @param entity
     * @return
     * @throws Throwable
     */
    private void callDeleteIF(BackupRestoreEventEntity entity) throws Throwable {
        Map<String, String> requestMap = new LinkedHashMap<>();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {

            URL httpUrl = new URL(url + deleteUrl);
           // connection = (HttpURLConnection) httpUrl.openConnection();
            connection = createHttpConnectDto(url + deleteUrl);

            requestMap.put("phrAssociationId", phrAssociationId);
            requestMap.put("projectId", entity.getRequestInsurerNo());
            requestMap.put("phrId", entity.getRequestPHR_ID());
            requestMap.put("restoreLoginId", restoreLoginId);
            requestMap.put("restoreLoginPassword", restoreLoginPassword);
            requestMap.put("restorePassword", entity.getDecriptPassword());

            //HttpSender.sendHttpPost(connection, requestMap);
            HttpUtility.sendHttpPost(connection, requestMap);
            
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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BackupInfoReturnDto returnDto = null;
            returnDto = gson.fromJson(responseString.toString(), BackupInfoReturnDto.class);

            // ステータスが1以外の時はエラー
            if (returnDto.getStatus() == null || returnDto.getStatus() != 1) {
                entity.setBackupRestoreStatusCd(BackupRestoreStatusEntity.STATUS_ERROR);
                entity.setErrorMessage(returnDto.getErrorMessage());
                return;
            }
            entity.setReceiptNo(returnDto.getReceptionId());

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

    /**
     * バックアップ情報の戻りDTO
     */
    private class BackupInfoReturnDto {

        /**
         * PHR事業者番号
         */
        private String phrAssociationId;
        /**
         * プロジェクト番号
         */
        private String projectId;
        /**
         * PHR利用者番号
         */
        private String phrId;
        /**
         * 受付ID
         */
        private String receptionId;
        /**
         * 受領ステータス
         */
        private Integer status;

        /**
         * エラーメッセージ
         */
        private String errorMessage;
        /**
         * データ取得URL
         */
        private String restoreURL;
        /**
         * ファイル名
         */
        private String fileName;
        /**
         * データサイズ
         */
        private Long dataSize;
        /**
         * リトライ回数
         */
        private Integer retryCount;
        /**
         * 有効期限
         */
        private Integer expirationDate;

        /**
         * @return the phrAssociationId
         */
        public String getPhrAssociationId() {
            return phrAssociationId;
        }

        /**
         * @param phrAssociationId the phrAssociationId to set
         */
        public void setPhrAssociationId(String phrAssociationId) {
            this.phrAssociationId = phrAssociationId;
        }

        /**
         * @return the projectId
         */
        public String getProjectId() {
            return projectId;
        }

        /**
         * @param projectId the projectId to set
         */
        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        /**
         * @return the phrId
         */
        public String getPhrId() {
            return phrId;
        }

        /**
         * @param phrId the phrId to set
         */
        public void setPhrId(String phrId) {
            this.phrId = phrId;
        }

        /**
         * @return the receptionId
         */
        public String getReceptionId() {
            return receptionId;
        }

        /**
         * @param receptionId the receptionId to set
         */
        public void setReceptionId(String receptionId) {
            this.receptionId = receptionId;
        }

        /**
         * @return the status
         */
        public Integer getStatus() {
            return status;
        }

        /**
         * @param status the status to set
         */
        public void setStatus(Integer status) {
            this.status = status;
        }

        /**
         * @return the errorMessage
         */
        public String getErrorMessage() {
            return errorMessage;
        }

        /**
         * @param errorMessage the errorMessage to set
         */
        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        /**
         * @return the restoreURL
         */
        public String getRestoreURL() {
            return restoreURL;
        }

        /**
         * @param restoreURL the restoreURL to set
         */
        public void setRestoreURL(String restoreURL) {
            this.restoreURL = restoreURL;
        }

        /**
         * @return the fileName
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * @param fileName the fileName to set
         */
        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        /**
         * @return the dataSize
         */
        public Long getDataSize() {
            return dataSize;
        }

        /**
         * @param dataSize the dataSize to set
         */
        public void setDataSize(Long dataSize) {
            this.dataSize = dataSize;
        }

        /**
         * @return the retryCount
         */
        public Integer getRetryCount() {
            return retryCount;
        }

        /**
         * @param retryCount the retryCount to set
         */
        public void setRetryCount(Integer retryCount) {
            this.retryCount = retryCount;
        }

        /**
         * @return the expirationDate
         */
        public Integer getExpirationDate() {
            return expirationDate;
        }

        /**
         * @param expirationDate the expirationDate to set
         */
        public void setExpirationDate(Integer expirationDate) {
            this.expirationDate = expirationDate;
        }

    }
    
    //リストアタイプ確認
    private int getRestoreType(String phrId,String oldPhrId,String oldPhrAssociationId) throws Throwable{
        int type;
        if(oldPhrAssociationId.equals(phrAssociationId)){
            String newInsurerNo = phrId.substring(0,7);
            String oldInsurerNo = oldPhrId.substring(0,7);
            if(newInsurerNo.equals(oldInsurerNo)){
                //プロジェクト移動
                    type = 2;
            }else{
                //同一リストア
                type = 1;
            }            
        }else{
            //事業者移動
            type = 3;            
        }
        return type;
    }
    /**
     * HTTP接続URLを作成する
     * @param url
     * @return
     * @throws HttpConnectionException 
     */
    private HttpURLConnection createHttpConnectDto(String url) throws HttpConnectionException {
        HttpConnectDto dto = new HttpConnectDto();
        dto.setClientAuth(true);
        dto.setClientCaSertFile(PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_CLIENT_CACERT_FILE));
        dto.setClientCertFile(PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_CLIENT_CERT_FILE));
        dto.setClientCertPinNo(PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_CLIENT_CERT_PIN));
        dto.setKeyStorePass(PhrConfig.getConfigProperty(ConfigConst.BACKUP_CENTER_CLIENT_KEYSTORE_PASSWORD));
        dto.setUrl(url);
        HttpURLConnection connection = HttpUtility.createHttpURLConnection(dto);
        return connection;
    }
    
}
