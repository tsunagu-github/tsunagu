/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.phone.facade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.utility.AesUtility;
import phr.web.phone.dto.RequestBaseDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseIdDto;

/**
 *
 * @author daisuke
 */
public class EncryptIdFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(EncryptIdFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        RequestBaseDto requestDto = gson.fromJson(json, RequestBaseDto.class);
        String key = "+jPyLh+d#GFC4T+n";
        String iv = "rn<chVq-srHQ6D+5";
        String text = requestDto.getPhrId();

        byte[] secretKeyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] secretIvBytes = iv.getBytes(StandardCharsets.UTF_8);
        byte[] originalBytes = text.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(secretIvBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptBytes = cipher.doFinal(originalBytes);
        String encId = Base64.encodeBase64String(encryptBytes);

        ResponseIdDto responseDto = new ResponseIdDto();
        responseDto.setEncId(encId);
        responseDto.setStatus(ResponseBaseDto.SUCCESS);
        return responseDto;
    }
}
