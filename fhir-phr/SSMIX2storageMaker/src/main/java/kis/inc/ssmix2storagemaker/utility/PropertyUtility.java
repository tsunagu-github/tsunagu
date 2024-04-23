package kis.inc.ssmix2storagemaker.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyUtility {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(PropertyUtility.class);
    
    private static String webInfDir;
    
    public static String getWebInfDir() {
        logger.trace("Start");
        URL url = PropertyUtility.class.getResource("PropertyUtility.class");
        String name = PropertyUtility.class.getSimpleName();
        //String Path = url.getPath().substring(1);
        String Path = url.getPath();
        String targets[] = Path.split("/");
        String target = "CsvConverter";
        for(int i = 0 ; i < targets.length ; i++){
            if(targets[i].contains("CsvConverter")) target = targets[i];
        }
        int length = Path.indexOf(target);
        webInfDir = Path.substring(0, length + target.length()-1);
    	logger.trace("End");
    	return webInfDir;
    }
    
    /**
	 * プロパティーの値を取得する
	 * @param key プロパティーKey
	 * @return 値
	 */
	public static String getProperty(String key) {
		try {
			String filePath = getWebInfDir() + "ss-mix2-system.config";

			String message = getProperty(key, filePath);
			return message;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 指定したプロパティーファイルよりKeyで指定した値を取得します。
	 * @param key	プロパティーファイルのKey
	 * @param filePath プロパティーファイルのパス
	 * @return keyの値
	 * @throws FileNotFoundException
	 */
	public static String getProperty(String key, String filePath) 
									throws FileNotFoundException {
    	File file = new File(filePath);
    	return getProperty(key, file);
	}
	
	/**
	 * 指定したプロパティーファイルよりKeyで指定した値を取得します。
	 * @param key
	 * @param file
	 * @return keyの値
	 * @throws FileNotFoundException
	 */
	public static String getProperty(String key, File file) 
									throws FileNotFoundException {
		 if (!file.exists())
			 throw new FileNotFoundException();
		 
		 InputStream inputStream = null;
		 try {
	    	inputStream = new FileInputStream(file);
	        return getProperty(key, inputStream);			
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioException) {}
			}
		}
	}
	
	/**
	 * 指定したプロパティーファイルよりKeyで指定した値を取得します。
	 * @param key
	 * @param file
	 * @return keyの値
	 * @throws FileNotFoundException
	 */
	public static String getProperty(String key, InputStream inputStream) {
		 try {
	    	Properties prop = new Properties();
	    	prop.load(inputStream);
	        String message = prop.getProperty(key);	        
	        return message;			
		} catch (IOException ioException) {
			logger.error("ファイルの読込に失敗しました。", ioException);
		} finally {

		}
		return null;
	}
}
