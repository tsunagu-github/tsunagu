package kis.inc.ssmix2storagemaker.utility;

import java.util.ArrayList;
import java.util.List;


public class StringUtility {

	public StringUtility() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	/**
	 * 文字列を分割します
	 * @param value
	 * @param separator
	 * @param escapeSeqence
	 * @return
	 */
	public static String[] split(String value , char separator , char escapeSequence){
		List<String> ret = new ArrayList<String>();
		String tmp = "";
		boolean isEscape = false;
		for(char c : value.toCharArray()){
			if(isEscape){
				tmp += c;
				isEscape = false;
				continue;
			}
			if(c == escapeSequence){
				tmp += c;
				isEscape = true;
				continue;
			}else if(c == separator){
				ret.add(tmp.toString());
				tmp = "";
				continue;
			}
			tmp += c;
		}
		ret.add(tmp.toString());
		return (String[])ret.toArray(new String[0]);
	}
	
	/**
	 * C#からの移植
	 * 文字列がnullもしくは空白・コントロール文字のみで構成される場合、trueを返します。
	 * @param value
	 * @return
	 */
	public static boolean isNullOrWhiteSpace(String value){
		if(value == null || value.trim().length() == 0)
			return true;
		return false;
	}
	/**
	 * C#からの移植
	 * 文字列がnullもしくは空文字の場合にtrueを返します
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(String value){
		if(value == null || value.length() == 0)
			return true;
		return false;
	}
	
	/**
	 * valueがnullの場合、replaceの文字列を返します。
	 * @param value
	 * @param replace
	 * @return
	 */
	public static String NVL(String value , String replace){
		if(value == null)
			return replace;
		return value;
	}
	
	/**
	 * escape文字(\)を倍に増やします。
	 * java/jsonともにescape文字(\)が単独で入っている場合、以下の現象が起きます。
	 * この現象を防ぐため、文字列中にescape文字(\)が入っている場合、倍に増やすことでそれぞれの現象を避けます。
	 * 1.json→java : org.glassfish.json.JsonTokenizerが落ちる
	 * 2.java→json : ブラウザがパースできずに空要素になる
	 * 
	 * @param value
	 * @return
	 */
	public static String convertForJSON(String value){
		if(!value.contains("\\")) return value;
		return value.replace("\\", "\\\\");
	}

        /*
        * String型における改行(\n)を置き換えてtrimを行う
        * 半角スペースのtrimを行う
        */
        public static String convertN(String value){
            if(value == null) return null;
            String result = value.replaceAll("\n", "");
            result = result.trim();
            
            return result;
            
        }

}
