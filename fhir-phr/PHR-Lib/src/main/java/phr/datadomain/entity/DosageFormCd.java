/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain.entity;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author kis-note-027_user
 */
public enum DosageFormCd {
	/**
	 * 「内服」を表すオブジェクト。
	 */
	INTERNAL("1","内服"),

        /**
	 * 「内滴」を表すオブジェクト。
	 */
	INNERDROP("2","内滴"),

        /**
	 * 「頓服」を表すオブジェクト。
	 */
	ASNEED("3","頓服"),
        
        /**
	 * 「注射」を表すオブジェクト。
	 */
	INJECTION("4","注射"),

        /**
	 * 「外用」を表すオブジェクト。
	 */
	EXTERNAL("5","外用"),

        /**
	 * 「浸煎」を表すオブジェクト。
	 */
	INFUSION("6","浸煎"),

        /**
	 * 「湯薬」を表すオブジェクト。
	 */
	TISANE("7","湯薬"),
        
        /**
	 * 「材料」を表すオブジェクト。
	 */
	MATERIAL("9","材料"),
        /**
	 * 「その他」を表すオブジェクト。
	 */
	OTHER("10","その他")
	;

	private final String id;
        private final String text;
        
	private DosageFormCd(String id,String text)
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
	public static DosageFormCd parse(String s)
	{
		if (s.equals(INTERNAL.getId())) return INTERNAL;
		else if (s.equals(INNERDROP.getId())) return INNERDROP;
		else if (s.equals(ASNEED.getId())) return ASNEED;
		else if (s.equals(INJECTION.getId())) return INJECTION;
		else if (s.equals(EXTERNAL.getId())) return EXTERNAL;
		else if (s.equals(INFUSION.getId())) return INFUSION;
                else if (s.equals(TISANE.getId())) return TISANE;
                else if (s.equals(MATERIAL.getId())) return MATERIAL;
                else if (s.equals(OTHER.getId())) return OTHER;
		else return null;
	}
	/**
	 * リストを取得します
	 * @return
	 */
	public static List<DosageFormCd> getList() {
		List<DosageFormCd> list = new ArrayList();

		list.add(INTERNAL);
		list.add(INNERDROP);
		list.add(ASNEED);
		list.add(INJECTION);
		list.add(EXTERNAL);
		list.add(INFUSION);
                list.add(TISANE);
                list.add(MATERIAL);
                list.add(OTHER);
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
