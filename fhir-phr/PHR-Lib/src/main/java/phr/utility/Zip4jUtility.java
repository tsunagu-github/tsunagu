/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.utility;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * zip4jを利用したファイル圧縮と解凍
 * @author KISNOTE011
 */
public class Zip4jUtility {
    /**
     * ロギングコンポーネント
     **/
    private static final Log logger = LogFactory.getLog(Zip4jUtility.class);

    private final int compressionMethod = Zip4jConstants.COMP_DEFLATE;
    private final int compressionLevel = Zip4jConstants.DEFLATE_LEVEL_NORMAL;
    private int encryptionMethod = Zip4jConstants.ENC_METHOD_STANDARD;
    private int aesKeyStrength = Zip4jConstants.AES_STRENGTH_256;

    public Zip4jUtility(){
    }

    /**
     * @param encryptionMethod 暗号化方式
     * @param aesKeyStrength 暗号化鍵種類
     */
    public Zip4jUtility(int encryptionMethod, int aesKeyStrength) {
        this.encryptionMethod = encryptionMethod;
        this.aesKeyStrength = aesKeyStrength;
    }

    /**
     * @param input 処理対象パス
     * @param output 結果パス
     * @param password パスワード
     * @throws net.lingala.zip4j.exception.ZipException
     * @throws java.io.IOException
     */
    public void zip(String input, String output, String password)
            throws ZipException, IOException {
        zip(input, output, password, "");
    }

    /**
     * @param input 処理対象パス
     * @param output 結果パス
     * @param password パスワード
     * @param fileNameCharset ファイル名の文字コード
     * @throws net.lingala.zip4j.exception.ZipException
     * @throws java.io.IOException
     */
    public void zip(String input, String output, String password, String fileNameCharset)
        throws ZipException, IOException {

    	logger.debug("start zip");
    	try {
            ZipFile zipFile = new ZipFile(output);
            if (!fileNameCharset.isEmpty()) {
                // ファイル名の文字コード設定
                zipFile.setFileNameCharset(fileNameCharset);
            }

            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(compressionMethod);
            parameters.setCompressionLevel(compressionLevel);
            if (!password.isEmpty()) {
                // パスワード設定
                parameters.setEncryptFiles(true);
                parameters.setEncryptionMethod(encryptionMethod);
                parameters.setAesKeyStrength(aesKeyStrength);
                parameters.setPassword(password);
            } else {
                parameters.setEncryptionMethod(Zip4jConstants.ENC_NO_ENCRYPTION);
            }

            File inputFile = new File(input);
            if (inputFile.isDirectory()) {
                zipFile.createZipFileFromFolder(inputFile, parameters, false, 0);
            } else {
                zipFile.addFile(inputFile, parameters);
            }
            logger.debug("end zip");
        } catch (ZipException e) {
            logger.error(e);
            throw e;
    	}
    }

    public void unzip(String input, String output, String password) throws ZipException {
    	logger.debug("start unzip");
    	try {
            ZipFile zipFile = new ZipFile(input);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(output);
            logger.debug("end unzip");
        } catch (ZipException e) {
            logger.error(e);
            throw e;
    	}
    }
}
