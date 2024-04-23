/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain.entity;

/**
 *
 * アカウントの種別を表す列挙型クラス
 * @author KISNOTE011
 */
public enum AccoutTypeCd {
	/**
	 * 「保険者」を表すオブジェクト。
	 */
	INSURER(1),

	/**
	 * 「疾病管理事業者」を表すオブジェクト。
	 */
	MANAGEMENT(2)
	;

	private final int id;
	private AccoutTypeCd(int id)
	{
		this.id = id;
	}

	/**
	 * オブジェクトを一意に表すオブジェクトIDを取得します。
	 * 
	 * @return オブジェクトID
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * 引数で指定された値（オブジェクトを一意に表すオブジェクトID）を解析して対応するオブジェクトを返します。
	 * 
	 * @param i
	 *            解析対象の値（オブジェクトID）
	 * @return 対応する状態オブジェクト
	 * @exception IllegalArgumentException
	 *                対応するオブジェクトが見つからない場合
	 */
	public static AccoutTypeCd parseAccoutTypeCd(int i)
	{
		if (i==INSURER.id) return INSURER;
		else if (i==MANAGEMENT.id) return MANAGEMENT;
		else throw new IllegalArgumentException("'" + i + "' に対応する " + AccoutTypeCd.class.getSimpleName() + " オブジェクトが見つかりません。");
	}
}
