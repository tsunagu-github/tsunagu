/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kis-note-027_user
 */
public enum OrganizationTypeCd {
	/**
	 * 「医科」を表すオブジェクト。
	 */
	MEDICAL("1","医科"),

        /**
	 * 「歯科」を表すオブジェクト。
	 */
	DENTAL("3","歯科"),

        /**
	 * 「調剤」を表すオブジェクト。
	 */
	DISPENSE("4","調剤")
	;

	private final String id;
        private final String text;
        
	private OrganizationTypeCd(String id,String text)
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
	public static OrganizationTypeCd parse(String s)
	{
		if (s.equals(MEDICAL.getId())) return MEDICAL;
		else if (s.equals(DENTAL.getId())) return DENTAL;
		else if (s.equals(DISPENSE.getId())) return DISPENSE;
		else return null;
	}
	/**
	 * リストを取得します
	 * @return
	 */
	public static List<OrganizationTypeCd> getList() {
		List<OrganizationTypeCd> list = new ArrayList();

		list.add(MEDICAL);
		list.add(DENTAL);
		list.add(DISPENSE);
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
