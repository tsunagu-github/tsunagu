/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.execute;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.impl.BackupRestoreService;

/**
 *
 * @author daisuke
 */
public class BackupRestoreExecute {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupRestoreExecute.class);

    private static final File LOCK_DIRECTORY = new File("/var/lock/BackupRestoreExecute");

    static {
        LOCK_DIRECTORY.mkdirs();
    }

    /**
     * メインエントリーポイント
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        logger.trace("Start");
        int exitCode = 0;
        // 多重起動の防止処理
        File lockFile = new File(LOCK_DIRECTORY, "lock");
        try (FileChannel channel = FileChannel.open(lockFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                FileLock lock = channel.tryLock()) {
            if (lock == null) {
                new Exception("多重起動することは出来ません。");
                exitCode = -2;
                return;
            } else {
                // バッチ処理
                try {
                    BackupRestoreService service = new BackupRestoreService();
                    service.executeBackup();        // バックアップ
                    service.executeRestore();       // リストア
                    service.executeBackupDelete();  // 削除
                } catch (Throwable e) {
                    e.printStackTrace();
                    // System.err.println("BatchExecutorApp: 原因不明の例外が発生しました。");
                    exitCode = -99;
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // System.err.println("BatchExecutorApp: 原因不明の例外が発生しました。");
            exitCode = -99;
        } finally {
            logger.trace("end");
            System.exit(exitCode);
        }

    }
}
