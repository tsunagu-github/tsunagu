/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.enums;

/**
 * サイト情報
 * @author KISNOTE011
 */
public enum SiteTypeEnum {
    KANJA(1),
    HOKEN(2),
    IRYOU(3);

    private final int id;
    private SiteTypeEnum(int id)
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
    public static SiteTypeEnum parseSiteTypeCd(int i)
    {
            if (i==KANJA.id) return KANJA;
            else if (i==HOKEN.id) return HOKEN;
            else if (i==IRYOU.id) return IRYOU;
            else throw new IllegalArgumentException("'" + i + "' に対応する " + SiteTypeEnum.class.getSimpleName() + " オブジェクトが見つかりません。");
    }

    /**
     * 引数で指定された値を解析して対応する名前を返します。
     * 
     * @param i
     *            解析対象の値（オブジェクトID）
     * @return 対応する状態オブジェクト
     * @exception IllegalArgumentException
     *                対応するオブジェクトが見つからない場合
     */
    public static String parseSiteTypeName(int i)
    {
            if (i==KANJA.id) return "患者View";
            else if (i==HOKEN.id) return "保険者View";
            else if (i==IRYOU.id) return "医療機関View";
            else throw new IllegalArgumentException("'" + i + "' に対応するサイト名が見つかりません。");
    }
}
