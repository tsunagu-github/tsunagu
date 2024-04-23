
package phr.datadomain.entity;

/**
 *
 * @author iwaasa
 */
public enum DosageTypeCd {
	/**
	 * 「調剤」を表すオブジェクト。
	 */
	DOSAGE("1"),

	/**
	 * 「処方」を表すオブジェクト。
	 */
	RECIPE("2")
	;

	private final String id;
	private DosageTypeCd(String id)
	{
		this.id = id;
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

	/**
	 * 引数で指定された値（オブジェクトを一意に表すオブジェクトID）を解析して対応するオブジェクトを返します。
	 * 
	 * @param i
	 *            解析対象の値（オブジェクトID）
	 * @return 対応する状態オブジェクト
	 * @exception IllegalArgumentException
	 *                対応するオブジェクトが見つからない場合
	 */
	public static DosageTypeCd parseDosageTypeCd(String i)
	{
		if (i.equals(DOSAGE.id)) return DOSAGE;
		else if (i.equals(RECIPE.id)) return RECIPE;
		else throw new IllegalArgumentException("'" + i + "' に対応する " + DosageTypeCd.class.getSimpleName() + " オブジェクトが見つかりません。");
	}
    
}
