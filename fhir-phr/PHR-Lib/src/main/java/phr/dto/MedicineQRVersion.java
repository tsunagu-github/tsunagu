/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.dto;

/**
 *
 * @author kis-note-027_user
 */
public enum MedicineQRVersion {
	/**
	 * 「電子お薬手帳データフォーマット仕様」バージョンを表すオブジェクト。
	 */
        FIXEDVALUE("JAHISTC"),
        VERSION1_0("JAHISTC01"),
        VERSION1_1("JAHISTC02"),
        VERSION2_0("JAHISTC03"),
        VERSION2_1("JAHISTC04"),
        VERSION2_2("JAHISTC05"),
        VERSION2_3("JAHISTC06"),
    VERSION2_4("JAHISTC07")
	;

        private final String text;
        
	private MedicineQRVersion(String text)
	{
                this.text = text;
	}

	/**
	 * オブジェクトを一意に表すオブジェクトIDを取得します。
	 * 
	 * @return オブジェクトID
	 */

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
//	 */
//	public static MedicineQRVersion parse(String s)
//	{
//		if (s.equals(VERSION.getText())) return VERSION;
//		else return null;
//	}
//
//	/**
//	 * 引数で指定された文字列（コード）を解析して対応するオブジェクトを返します。
//	 *
//	 * @param s コード
//	 * @return 対応するオブジェクト
//	 */
//	public Object parseObject(String s) {
//		return parse(s);
//	}    
}
