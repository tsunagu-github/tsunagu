package kis.inc.ssmix2storagemaker.config;

import java.text.MessageFormat;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.InputStream;
import kis.inc.ssmix2storagemaker.Enums.ErrorIdEnum;
import kis.inc.ssmix2storagemaker.search.dto.ErrorDefinitionDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorConfig {
    
    private static final Log logger = LogFactory.getLog(ErrorConfig.class);

	private static ErrorConfig instance;
	private static final String FILENAME = "ErrorMessage.xml";
	
	private static Hashtable<String, ErrorDefinitionDto> errorTable;
	
	
	private static ErrorConfig getInstance(){
		if(instance == null)
			instance = new ErrorConfig();
		return instance;
	}
	
	private ErrorConfig() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public static ErrorDefinitionDto get(ErrorIdEnum errorName , String... params){
		if(errorTable == null)
			getInstance().initialize();
		if(!errorTable.containsKey(errorName.toString()))
			return null;
		ErrorDefinitionDto ret= null;
		try {
			ret = errorTable.get(errorName.toString()).clone();
			ret.setErrorMessage(MessageFormat.format(ret.getErrorMessage() , params).replace("[]", ""));
		} catch (CloneNotSupportedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * クラスの初期化を行います
	 */
	private void initialize(){
		try{
			DOMParser parser = new DOMParser();
			parser.parse(getErrorMessageStream());
			Document doc = parser.getDocument();
			this.errorTable = new Hashtable<String, ErrorDefinitionDto>();
			NodeList nodelist = doc.getElementsByTagName("Error");
			for(int i = 0 ; i < nodelist.getLength() ; i++){
				Element e = (Element) nodelist.item(i);
				ErrorDefinitionDto dto = new ErrorDefinitionDto();
				dto.setName(e.getAttribute("name"));
				dto.setErrorId(e.getAttribute("id"));
				dto.setStatus(Integer.parseInt(e.getAttribute("status")));
				dto.setErrorMessage(e.getFirstChild().getNodeValue());
				this.errorTable.put(dto.getName(), dto);
			}
		}catch(Exception e){
			logger.error(MessageFormat.format("×エラー定義ファイルが見つからないか、不正な形式です。{0}", ErrorConfig.FILENAME));
		}
	}
	public static InputSource getErrorMessageStream() {
            InputStream inputStream = ErrorConfig.class.getResourceAsStream("/" + FILENAME);
            InputSource inputSource = new InputSource(inputStream);
            return inputSource;
        }
}
