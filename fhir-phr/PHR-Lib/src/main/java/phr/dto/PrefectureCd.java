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
public enum PrefectureCd {
	/**
	 * 「電子お薬手帳データフォーマット仕様」都道府県コードを表すオブジェクト。
	 */
        HOKKAIDO("01"),
        AOMORI("02"),
        IWATE("03"),
        MIYAGI("04"),
	AKITA("05"),
        YAMAGATA("06"),
        FUKUSHIMA("07"),
        IBARAKI("08"),
        TOCHIGI("09"),
        GUNMA("10"),
        SAITAMA("11"),
        CHIBA("12"),
        TOKYO("13"),
        KANAGAWA("14"),
        NIGATA("15"),
        TOYAMA("16"),
        ISHIKAWA("17"),
        FUKUI("18"),
        YAMANASHI("19"),
        NAGANO("20"),
        GIFU("21"),
        SHIZUOKA("22"),
        AICHI("23"),
        MIE("24"),
        SHIGA("25"),
        KYOTO("26"),
        OSAKA("27"),
        HYOGO("28"),
        NARA("29"),
        WAKAYAMA("30"),
        TOTTORI("31"),
        SHIMANE("32"),
        OKAYAMA("33"),
        HIROSHIMA("34"),
        YAMAGUCHI("35"),
        TOKUSHIMA("36"),
        KAGAWA("37"),
        EHIME("38"),
        KOUCHI("39"),
        FUKUOKA("40"),
        SAGA("41"),
        NAGASAKI("42"),
        KUMAMOTO("43"),
        OITA("44"),
        MIYAZAKI("45"),
        KAGOSHIMA("46"),
        OKINAWA("47"),
        
        
	;

        private final String text;
        
	private PrefectureCd(String text)
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
	 */
	public static PrefectureCd parse(String s)
	{
		if (s.equals(HOKKAIDO.getText())) return HOKKAIDO;
                if (s.equals(AOMORI.getText())) return AOMORI;
                if (s.equals(IWATE.getText())) return IWATE;
                if (s.equals(MIYAGI.getText())) return MIYAGI;
                if (s.equals(AKITA.getText())) return AKITA;
                if (s.equals(YAMAGATA.getText())) return YAMAGATA;
                if (s.equals(FUKUSHIMA.getText())) return FUKUSHIMA;
                if (s.equals(IBARAKI.getText())) return IBARAKI;
                if (s.equals(TOCHIGI.getText())) return TOCHIGI;
                if (s.equals(GUNMA.getText())) return GUNMA;
                if (s.equals(SAITAMA.getText())) return SAITAMA;
                if (s.equals(CHIBA.getText())) return CHIBA;
                if (s.equals(TOKYO.getText())) return TOKYO;
                if (s.equals(KANAGAWA.getText())) return KANAGAWA;
                if (s.equals(NIGATA.getText())) return NIGATA;
                if (s.equals(TOYAMA.getText())) return TOYAMA;
                if (s.equals(ISHIKAWA.getText())) return ISHIKAWA;
                if (s.equals(FUKUI.getText())) return FUKUI;
                if (s.equals(YAMANASHI.getText())) return YAMANASHI;
                if (s.equals(NAGANO.getText())) return NAGANO;
                if (s.equals(GIFU.getText())) return GIFU;
                if (s.equals(SHIZUOKA.getText())) return SHIZUOKA;
                if (s.equals(AICHI.getText())) return AICHI;
                if (s.equals(MIE.getText())) return MIE;
                if (s.equals(SHIGA.getText())) return SHIGA;
                if (s.equals(KYOTO.getText())) return KYOTO;
                if (s.equals(OSAKA.getText())) return OSAKA;
                if (s.equals(HYOGO.getText())) return HYOGO;
                if (s.equals(NARA.getText())) return NARA;
                if (s.equals(WAKAYAMA.getText())) return WAKAYAMA;
                if (s.equals(TOTTORI.getText())) return TOTTORI;
                if (s.equals(SHIMANE.getText())) return SHIMANE;
                if (s.equals(OKAYAMA.getText())) return OKAYAMA;
                if (s.equals(HIROSHIMA.getText())) return HIROSHIMA;
                if (s.equals(YAMAGUCHI.getText())) return YAMAGUCHI;
                if (s.equals(TOKUSHIMA.getText())) return TOKUSHIMA;
                if (s.equals(KAGAWA.getText())) return KAGAWA;
                if (s.equals(EHIME.getText())) return EHIME;
                if (s.equals(KOUCHI.getText())) return KOUCHI;
                if (s.equals(FUKUOKA.getText())) return FUKUOKA;
                if (s.equals(SAGA.getText())) return SAGA;
                if (s.equals(NAGASAKI.getText())) return NAGASAKI;
                if (s.equals(KUMAMOTO.getText())) return KUMAMOTO;
                if (s.equals(OITA.getText())) return OITA;
                if (s.equals(MIYAZAKI.getText())) return MIYAZAKI;
                if (s.equals(KAGOSHIMA.getText())) return KAGOSHIMA;
                if (s.equals(OKINAWA.getText())) return OKINAWA;
		else return null;
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
