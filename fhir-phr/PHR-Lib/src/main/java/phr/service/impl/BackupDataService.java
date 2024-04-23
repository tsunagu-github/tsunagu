/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.UUID;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.service.IBackupDataService;
import phr.service.IMakeStorageService;
import phr.service.IMergeStorageService;

/**
 *
 * アラート一覧画面のサービスクラス
 *
 * @author KISO-NOTE-005
 */
public class BackupDataService implements IBackupDataService {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(BackupDataService.class);

    /**
     * バックアップ用のzipを作成するサービス
     *
     * @param phrid
     * @return
     * @throws Throwable
     */
    public File makeBackupData(String phrid) throws Throwable {
        logger.debug("start");

        String rootPath = PhrConfig.getConfigProperty(ConfigConst.STORAGE_PATH);
        logger.debug("rootPath : " + rootPath);
        String savePath = PhrConfig.getConfigProperty(ConfigConst.BACKUP_STORAGE);
        logger.debug("savePath : " + savePath);

        //作成用のID作成
        //後にバックアップの処理IDなどに変えた方がよいが今はuuidとする・
        String uniqueId = null;
        UUID uuid = UUID.randomUUID();
        uniqueId = uuid.toString();
        uniqueId = uniqueId.replaceAll("-", "");

        //保存先のストレージをコピー
        copyStrageSTD(uniqueId, phrid, rootPath, savePath);
        copyStrageEXT(uniqueId, phrid, rootPath, savePath);

        String targetPath = savePath + "/" + uniqueId;

        logger.debug("標準化ストレージをDBから作成開始");
        IMakeStorageService service = new MakeStorageService();
        service.makeSTDStorage(phrid, targetPath + "/STD");
        logger.debug("標準化ストレージをDBから作成終了");
        
        //日付管理していないメッセージのマージ作業
        IMergeStorageService merge = new MergeStorageService();
        try{
            merge.mergeExecute(phrid, targetPath + "/STD");
        }catch(ParseException ex){
            logger.error("", ex);
        }

        logger.debug("拡張ストレージをDBから作成開始");
        service.makeEXTStorage(phrid, targetPath + "/EXT");
        logger.debug("拡張ストレージをDBから作成終了");
        
        
        //zipファイルの作成
        File zipFile = new File(targetPath);
        File zip = createZip(zipFile, phrid, uniqueId);

        //zip以外を削除する
        deleteStorage(zipFile);
        logger.debug("end");
        return zip;
    }

    private void copyStrageSTD(String id, String phrid, String rootPath, String savePath) throws IOException {

        String pass1 = phrid.substring(0, 3);
        String pass2 = phrid.substring(3, 6);

        String targetPath = rootPath + "/STD/" + pass1 + "/" + pass2 + "/" + phrid;

        logger.debug("targetPath : " + targetPath);
        File target = new File(targetPath);

        //過去に別事業者などからのバックアップを受け取ったことがあるかの確認
        //ない場合は終了
        //連続で処理されたらもしかしたら先頭に何か必要かもしれない
        String savetargetPath = savePath + "/" + id + "/STD/" + pass1 + "/" + pass2 + "/" + phrid;

        File saveDir = new File(savetargetPath);

        saveDir.mkdirs();
        if (!target.exists()) {
            return;
        }
        logger.debug("コピー開始");

        copyExecute(target, saveDir);
        changeFlg(saveDir);
        logger.debug("コピー終了");
    }

    private void copyStrageEXT(String id, String phrid, String rootPath, String savePath) throws IOException {

        String pass1 = phrid.substring(0, 3);
        String pass2 = phrid.substring(3, 6);

        String targetPath = rootPath + "/EXT/" + pass1 + "/" + pass2 + "/" + phrid;

        logger.debug("targetPath : " + targetPath);
        File target = new File(targetPath);

        //過去に別事業者などからのバックアップを受け取ったことがあるかの確認
        //ない場合は終了
        //連続で処理されたらもしかしたら先頭に何か必要かもしれない
        String savetargetPath = savePath + "/" + id + "/EXT/" + pass1 + "/" + pass2 + "/" + phrid;

        File saveDir = new File(savetargetPath);

        saveDir.mkdirs();
        if (!target.exists()) {
            return;
        }

        logger.debug("コピー開始");
        copyExecute(target, saveDir);
        logger.debug("コピー終了");
    }

    private void copyExecute(File target, File saveDir) throws IOException {
        File[] targetDirs = target.listFiles();

        for (File file : targetDirs) {
            if (file.isDirectory()) {
                String base = file.getName();
                String savePath = saveDir.getPath() + "/" + base;
                File save = new File(savePath);
                save.mkdirs();

                copyExecute(file, save);
            } else {
                String name = file.getName();
                File save = new File(saveDir.getPath() + "/" + name);
                Files.copy(file.toPath(), save.toPath());
            }
        }

    }

    private File createZip(File target, String phrid, String id) {
        logger.debug("zip圧縮処理の開始");
        ZipArchiveOutputStream zos = null;
        String path = PhrConfig.getConfigProperty(ConfigConst.BACKUP_STORAGE);
        File[] files = target.listFiles();
        try {
            File zipFile = new File(path + "/" + phrid + ".zip");
            zos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));

            fileTozip(files, zos, "");

            zos.close();
            return zipFile;
        } catch (IOException e) {
            logger.error("", e);
            e.printStackTrace();
        }
        logger.debug("zip圧縮処理の終了");
        return null;
    }

    private void fileTozip(File[] files, ZipArchiveOutputStream zos, String path) throws IOException {
        byte[] buf = new byte[1024];
        InputStream is = null;
        String target = "";
        for (File file : files) {
            logger.debug(file.getPath());
            if (file.isDirectory()) {
                logger.debug("対象がディレクトリなので次へ");
                if (path.length() > 0) {
                    target = path + "/" + file.getName();
                } else {
                    target = file.getName();
                }
                File[] targets = file.listFiles();
                logger.debug(target);
                fileTozip(targets, zos, target);
            } else {
                target = path + "/" + file.getName();
                logger.debug(target);
                ZipArchiveEntry entry = new ZipArchiveEntry(target);
                zos.putArchiveEntry(entry);
                FileInputStream fis = new FileInputStream(file);
                IOUtils.copy(fis, zos);
                zos.closeArchiveEntry();
                fis.close();
            }
        }
    }

    private void deleteStorage(File target) {

        if (!target.exists()) {
            return;
        }

        if (target.isFile()) {
            target.delete();
            return;
        }
        if (target.isDirectory()) {
            File[] files = target.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteStorage(files[i]);
            }
            target.delete();
        }
        /*
        File[] targets = target.listFiles();
        if(targets != null){
            for(File file : targets){
                deleteStorage(file);
            }
        }

        logger.debug("delete対象 : " + target.getPath());
        boolean result = target.delete();
        
        //削除に失敗するので念のために入れておく
        if(!result)deleteStorage(target);
         */
    }
    
    private void changeFlg(File saveDir){
        //コピーしたデータのフラグを2へ変更して無効データとする。
        for(File checkfile:saveDir.listFiles()){
            if(checkfile.getName().equals("-")){
                continue;
            }
            if(checkfile.isDirectory()){
              changeFlg(checkfile);
            }else{
                renameFile(checkfile);
            }

        }
    }
    private void renameFile(File oldData){
                StringBuilder sb = new StringBuilder(oldData.getPath());
                sb.deleteCharAt(sb.length()-1);
                sb.append("2");
                String newFileName = sb.toString();
                File renameData = new File(newFileName);
                oldData.renameTo(renameData);        
    }
}
