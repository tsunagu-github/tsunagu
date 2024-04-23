
package phr.datadomain.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kis-note-027_user
 */
public enum RecordCreatorTypeCd {
	/**
	 * 「医療関係者」を表すオブジェクト。
	 */
	MEDICAL("1","医療関係者"),

        /**
	 * 「患者等」を表すオブジェクト。
	 */
	PATIENT("2","患者等"),

        /**
	 * 「その他」を表すオブジェクト。
	 */
	OTHER("8","その他"),

        /**
	 * 「不明」を表すオブジェクト。
	 */
	UNKNOWN("9","不明")
	;

	private final String id;
        private final String text;
        
	private RecordCreatorTypeCd(String id,String text)
	{
		this.id = id;
                this.text = text;
	}

	/**
	 * オブジェクトを一意に表すオブジェクトIDを取得します。
	 * 
	 * @return オブジェクトID
	 */
	public String getId()
	{
		return id;
	}

	public String getText()
	{
		return text;
	}
               

	/**
	 * 引数で指定された文字列（コード）を解析して対応するオブジェクトを返します。対応するオブジェクトが存在しない場合は null
	 * を返します。
	 *
	 * @param s コード
	 * @return 対応するオブジェクト
	 */
	public static RecordCreatorTypeCd parse(String s)
	{
		if (s.equals(MEDICAL.getId())) return MEDICAL;
		else if (s.equals(PATIENT.getId())) return PATIENT;
		else if (s.equals(OTHER.getId())) return OTHER;
		else if (s.equals(UNKNOWN.getId())) return UNKNOWN;
		else return null;
	}
	/**
	 * リストを取得します
	 * @return
	 */
	public static List<RecordCreatorTypeCd> getList() {
		List<RecordCreatorTypeCd> list = new ArrayList();

		list.add(MEDICAL);
		list.add(PATIENT);
		list.add(OTHER);
		list.add(UNKNOWN);
		return list;
	}
	/**
	 * 引数で指定された文字列（コード）を解析して対応するオブジェクトを返します。
	 *
	 * @param s コード
	 * @return 対応するオブジェクト
	 */
	public Object parseObject(String s) {
		return parse(s);
	}      
}
