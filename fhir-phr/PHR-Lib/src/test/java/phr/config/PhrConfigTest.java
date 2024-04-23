/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.config;

//import phr.config.SystemConfigConst;
//import phr.config.PhrConfig;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import phr.utility.AesUtility;

/**
 *
 * @author daisuke
 */
public class PhrConfigTest {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PhrConfigTest.class);

    public PhrConfigTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getSystemConfigPropertyTest() {
        String configPath = PhrConfig.getSystemConfigProperty(SystemConfigConst.CONFIG_FILENAME);
        String host = PhrConfig.getConfigProperty(ConfigConst.DATABASE_HOST);

        logger.debug(configPath);
        assertEquals(configPath, "phr.config");
    }

    @Test
    public void AesUtilityTest() {
        AesUtility aes = new AesUtility("+jPyLh+d#GFC4T+n");
        String encId;
        try {
            encId = aes.encrypt("1234567");
            logger.debug(encId);
        } catch (Throwable ex) {
            Logger.getLogger(PhrConfigTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void AesUtility2Test() {
        try {
            String key = "+jPyLh+d#GFC4T+n";
            String text = "1234567";
            byte[] secretKeyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] originalBytes = text.getBytes(StandardCharsets.UTF_8);

            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptBytes = cipher.doFinal(originalBytes);

            String encId = Base64.encodeBase64String(encryptBytes);
            logger.debug(encId);
        } catch (Throwable ex) {
            Logger.getLogger(PhrConfigTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void AesUtility3Test() {
        try {
            String key = "+jPyLh+d#GFC4T+n";
            String iv = "rn<chVq-srHQ6D+5";
            String text = "1234567";
            byte[] secretKeyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] secretIvBytes = iv.getBytes(StandardCharsets.UTF_8);
            byte[] originalBytes = text.getBytes(StandardCharsets.UTF_8);

            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(secretIvBytes);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptBytes = cipher.doFinal(originalBytes);

            String encId = Base64.encodeBase64String(encryptBytes);
            logger.debug(encId);
        } catch (Throwable ex) {
            Logger.getLogger(PhrConfigTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
