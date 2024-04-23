/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：AES暗号化、複合を行うユーティリティー
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.utility;

import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * AES暗号化、複合を行うユーティリティー
 * @author daisuke
 */
public class AesUtility {

    private final String characterEncoding = "UTF-8";
    private final String cipherTransformation = "AES/CBC/PKCS5Padding";
    private final String aesEncryptionAlgorithm = "AES";

    private String key;

    /**
     * コンストラクタ
     *
     * @param key 暗号化Key文字列
     */
    public AesUtility(String key) {
        this.key = key;
    }

    /**
     * 文字列をAES暗号化してBase64文字列を返却します
     *
     * @param plainText 暗号化対象文字列
     * @return 暗号化後Base64文字列
     * @throws Throwable
     */
    public String encrypt(String plainText) throws Throwable {
        byte[] plainTextbytes = plainText.getBytes(characterEncoding);
        byte[] keyBytes = getKeyBytes(key);
        byte[] base64Bytes = Base64.encodeBase64(encrypt(plainTextbytes, keyBytes, keyBytes));
        return new String(base64Bytes);
    }

    /**
     * Base64文字列をAES復号して復号した文字列を返却します
     *
     * @param encryptedText 暗号化Base64文字列
     * @return 復号後文字列
     * @throws Throwable
     */
    public String decrypt(String encryptedText) throws Throwable {
        byte[] cipheredBytes = Base64.decodeBase64(encryptedText);
        byte[] keyBytes = getKeyBytes(key);
        return new String(decrypt(cipheredBytes, keyBytes, keyBytes), characterEncoding);
    }

    /**
     *
     * @param cipherText
     * @param keys
     * @param initialVector
     * @return
     * @throws Throwable
     */
    private byte[] decrypt(byte[] cipherText, byte[] keys, byte[] initialVector) throws Throwable {
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpecy = new SecretKeySpec(keys, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }

    /**
     *
     * @param plainText
     * @param keys
     * @param initialVector
     * @return
     * @throws Throwable
     */
    private byte[] encrypt(byte[] plainText, byte[] keys, byte[] initialVector) throws Throwable {
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keys, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        plainText = cipher.doFinal(plainText);
        return plainText;
    }

    /**
     *
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    private byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
        byte[] keyBytes = new byte[16];
        byte[] parameterKeyBytes = key.getBytes(characterEncoding);
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
        return keyBytes;
    }

}
