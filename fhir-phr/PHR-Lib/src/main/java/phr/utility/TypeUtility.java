/********************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名：Javaのデータ型に応じた各種変換チェック等を行うユーティリティー
 * 作成者          ：kis-inc
 * 作成日          ：2016/08/22
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 *******************************************************************************/
package phr.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Javaのデータ型に応じた各種変換チェック等を行うユーティリティー
 * @author daisuke
 */
public class TypeUtility {

    /**
     * ロギングコンポーネント
     *
     */
    private static final Log LOGGER = LogFactory.getLog(TypeUtility.class);

    /**
     * デフォルト日付フォーマット
     */
    private static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy/MM/dd");
    /**
     * デフォルト日付フォーマット（和暦）
     */
    private static final SimpleDateFormat DEFAULT_JA_SDF = new SimpleDateFormat("G yy/MM/dd", new Locale("ja", "JP", "JP"));

    // 英数字記号パタン
    private static final String ALPHA_NUMBER_SYMBOL_PATTERN = "[!-~]";
    // 英数字パタン
    private static final String ALPHA_NUMBER_PATTERN = "[A-Za-z0-9]";
    // 英字パタン
    private static final String ALPHA_PATTERN = "[A-Za-z]";
    // 数字パタン
    private static final String NUMBER_PATTERN = "[0-9]";
    // 半角パタン
    private static final String HALF_CHAR_PATTERN = "^[ -~｡-ﾟ]*$";
    // 全角カタカナパタン
    private static final String FULL_KATAKANA_PATTERN = "[ァ-ー　]";
    // 郵便番号パタン
    private static final String ZIP_CODE_PATTERN = "[0-9]{3}-[0-9]{4}";
    // 英数字記号＋半角スペースパタン
    private static final String ALPHA_NUMBER_SYMBOL_SPACE_PATTERN = "[!-~ ]";
    /**
     * 「全角」のみ *
     */
    public static final String MATCH_ZENKAKU = "\\u3040-\\u30FF";

    /**
     * 値がNullか判別します。 String型の場合はEmptyでもtrueを返却します。
     *
     * @param value 対象値
     * @return nullの場合true、null以外の場合false
     */
    public static boolean isNull(Object value) {

        if (value == null) {
            return true;
        }
        return false;
    }

    /**
     * 値がNullか判別します。 String型の場合はEmptyでもtrueを返却します。
     *
     * @param value 対象値
     * @return nullの場合true、null以外の場合false
     */
    public static boolean isNullOrEmpty(String value) {

        if (value == null) {
            return true;
        }

        if (String.class.equals(value.getClass())) {
            if (value.trim().length() == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 値が数値に変換可能か判別します。
     *
     * @param value 対象値
     * @return nullの場合true、null以外の場合false
     */
    public static boolean isNumber(Object value) {

        if (value == null) {
            return false;
        }

        String val = value.toString();
        try {
            Double.parseDouble(val);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 日付文字列かチェックします
     *
     * @param value
     * @return
     */
    public static boolean isDate(String value) {
        if (value == null || value.length() == 0) {
            return true;
        }
        int len = value.length();
        byte[] bytes = value.getBytes();
        if (len != bytes.length) {
            return false;
        }
        return isDate(value, "yyyy/MM/dd");

    }

    /**
     * 日付文字列かチェックします
     *
     * @param value
     * @param format
     * @return
     */
    public static boolean isDate(String value, String format) {
        if (value == null || value.length() == 0) {
            return true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            sdf.parse(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Stringをintに変換します。 intに変換できない場合は「0」を返却します。
     *
     * @param value 対象文字列
     * @return 変換後の値
     */
    public static int stringToInt(String value) {
        return stringToInt(value, 0);
    }

    /**
     * Stringをintに変換します。 intに変換できない場合はerrorIntnの値を返却します。
     *
     * @param value 対象文字列
     * @param errorValue エラー時の値
     * @return 変換後の値
     */
    public static int stringToInt(String value, int errorValue) {

        if (isNull(value)) {
            return errorValue;
        }
        try {
            int returnValue = Integer.parseInt(value);
            return returnValue;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return errorValue;
    }

    /**
     * StringをIntegerに変換します。 Integerに変換できない場合はnullを返却します。
     *
     * @param value 対象文字列
     * @return 変換後の値
     */
    public static Integer stringToInteger(String value) {

        if (isNull(value)) {
            return null;
        }
        try {
            Integer returnValue = Integer.valueOf(value);
            return returnValue;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * Stringをlongに変換します。 longに変換できない場合は「0」を返却します。
     *
     * @param value 対象文字列
     * @return 変換後の値
     */
    public static long stringToLong(String value) {
        return stringToLong(value, 0);
    }

    /**
     * Stringをlongに変換します。 longに変換できない場合はerrorIntnの値を返却します。
     *
     * @param value 対象文字列
     * @param errorValue エラー時の値
     * @return 変換後の値
     */
    public static long stringToLong(String value, long errorValue) {

        if (isNull(value)) {
            return errorValue;
        }
        try {
            long returnValue = Long.parseLong(value);
            return returnValue;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return errorValue;
    }

    /**
     * Stringをdoubleに変換します。 doubleに変換できない場合はerrorValueの値を返却します。
     *
     * @param value 対象文字列
     * @param errorValue エラー時の値
     * @return 変換後の値
     */
    public static double stringToDouble(String value, double errorValue) {

        if (isNull(value)) {
            return errorValue;
        }
        try {
            double returnValue = Double.parseDouble(value);
            return returnValue;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return errorValue;
    }

    /**
     * StringをDoubleに変換します。 Longに変換できない場合はnullを返却します。
     *
     * @param value 対象文字列
     * @return 変換後の値
     */
    public static Double stringToLongObject(String value) {

        if (isNull(value)) {
            return null;
        }
        try {
            Double returnValue = Double.valueOf(value);
            return returnValue;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * StringをDateに変換します。(yyyy/MM/dd形式) Dateに変換できない場合はnullを返却します。
     *
     * @param value 対象文字列
     * @return 変換後の値
     */
    public static Date stringToDate(String value) {

        if (isNull(value)) {
            return null;
        }
        try {
            Date date = DEFAULT_SDF.parse(value);
            return date;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * StringをDateに変換します。（G yy/MM/dd形式） Dateに変換できない場合はnullを返却します。
     *
     * @param value 対象文字列
     * @return 変換後の値
     */
    public static Date stringToJapaneseDate(String value) {

        if (isNull(value)) {
            return null;
        }
        try {
            Date date = DEFAULT_JA_SDF.parse(value);
            return date;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * Stringを指定したフォーマットに従ってDateに変換します。 Dateに変換できない場合はnullを返却します。
     *
     * @param value
     * @param format
     * @return
     */
    public static Date stringToDate(String value, String format) {

        if (isNull(value)) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(value);
            return date;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * Stringを指定したフォーマットに従ってDateに変換します。 Dateに変換できない場合はnullを返却します。
     *
     * @param value
     * @param format
     * @return
     */
    public static Date stringToJapaneseDate(String value, String format) {

        if (isNull(value)) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("ja", "JP", "JP"));
        try {
            Date date = sdf.parse(value);
            return date;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * intをStringに変換します。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String intToString(int value) {
        return Integer.toString(value);
    }

    /**
     * intをフォーマットしたStringに変換します。
     *
     * @param value 対象値
     * @param format フォーマット
     * @return 変換後の値
     */
    public static String format(int value, String format) {
        return String.format(format, value);
    }

    /**
     * IntegerをStringに変換します。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String IntegerToString(Integer value) {
        if (isNull(value)) {
            return null;
        }

        return Integer.toString(value);
    }

    /**
     * IntegerをフォーマットしたStringに変換します。
     *
     * @param value 対象値
     * @param format フォーマット
     * @return 変換後の値
     */
    public static String format(Integer value, String format) {
        if (isNull(value)) {
            return null;
        }
        return String.format(format, value);
    }

    /**
     * longをStringに変換します。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String longToString(long value) {
        return Long.toString(value);
    }

    /**
     * longをフォーマットしたStringに変換します。
     *
     * @param value 対象値
     * @param format フォーマット
     * @return 変換後の値
     */
    public static String format(long value, String format) {
        return String.format(format, value);
    }

    /**
     * LongをStringに変換します。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String longToString(Long value) {
        return Long.toString(value);
    }

    /**
     * LongをフォーマットしたStringに変換します。
     *
     * @param value 対象値
     * @param format フォーマット
     * @return 変換後の値
     */
    public static String format(Long value, String format) {
        if (isNull(value)) {
            return null;
        }
        return String.format(format, value);
    }

    /**
     * doubleをStringに変換します。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String doubleToString(double value) {
        return Double.toString(value);
    }

    /**
     * doubleをフォーマットしたStringに変換します。
     *
     * @param value 対象値
     * @param format フォーマット
     * @return 変換後の値
     */
    public static String format(double value, String format) {
        return String.format(format, value);
    }

    /**
     * DoubleをStringに変換します。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String doubleToString(Double value) {
        return Double.toString(value);
    }

    /**
     * DoubleをフォーマットしたStringに変換します。
     *
     * @param value 対象値
     * @param format フォーマット
     * @return 変換後の値
     */
    public static String format(Double value, String format) {
        if (isNull(value)) {
            return null;
        }
        return String.format(format, value);
    }

    /**
     * DateをStringに変換します。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String dateToString(Date value) {
        if (isNull(value)) {
            return null;
        }
        try {
            String val = DEFAULT_SDF.format(value);
            return val;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * DateをStringに変換します。
     *
     * @param value 対象値
     * @return 変換後の値
     */
    public static String japaneseDateToString(Date value) {
        if (isNull(value)) {
            return null;
        }
        try {
            String val = DEFAULT_JA_SDF.format(value);
            return val;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * DateをフォーマットしたStringに変換します。
     *
     * @param value 対象値
     * @param format フォーマット
     * @return 変換後の値
     */
    public static String format(Date value, String format) {
        if (isNull(value)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String val = sdf.format(value);
            return val;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * DateをフォーマットしたStringに変換します。
     *
     * @param value 対象値
     * @param format フォーマット
     * @return 変換後の値
     */
    public static String formatJapanese(Date value, String format) {
        if (isNull(value)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("ja", "JP", "JP"));
            String val = sdf.format(value);
            return val;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * DateをフォーマットしたStringに変換します。
     *
     * @param value 対象値
     * @param format フォーマット
     * @return 変換後の値
     */
    public static String format(Timestamp value, String format) {
        if (isNull(value)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String val = sdf.format(value);
            return val;
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    /**
     * 半角英数字記号をチェックします。
     *
     * @param data
     * @param maxLength
     * @return
     */
    public static boolean isAlphaNumberSymbol(String data, int maxLength) {
        if (data == null) {
            return false;
        }

        String pattern = "^" + ALPHA_NUMBER_SYMBOL_PATTERN + "{0," + maxLength + "}$";
        return data.matches(pattern);
    }

    /**
     * 半角英数字記号（半角スペース含む）をチェックします。
     *
     * @param data
     * @param maxLength
     * @return
     */
    public static boolean isAlphaNumberSymbolSpace(String data, int maxLength) {
        if (data == null) {
            return false;
        }

        String pattern = "^" + ALPHA_NUMBER_SYMBOL_SPACE_PATTERN + "{0," + maxLength + "}$";
        return data.matches(pattern);
    }

    /**
     * 半角英数字をチェックします。
     *
     * @param data
     * @param maxLength
     * @return
     */
    public static boolean isAlphaNumber(String data, int maxLength) {
        if (data == null) {
            return false;
        }

        String pattern = "^" + ALPHA_NUMBER_PATTERN + "{0," + maxLength + "}$";
        return data.matches(pattern);
    }

    /**
     * 半角英字をチェックします。
     *
     * @param data
     * @param maxLength
     * @return
     */
    public static boolean isAlpha(String data, int maxLength) {
        if (data == null) {
            return false;
        }

        String pattern = "^" + ALPHA_PATTERN + "{0," + maxLength + "}$";
        return data.matches(pattern);
    }

    /**
     * 半角数字をチェックします。
     *
     * @param data
     * @param maxLength
     * @return
     */
    public static boolean isNumber(String data, int maxLength) {
        if (data == null) {
            return false;
        }

        String pattern = "^" + NUMBER_PATTERN + "{0," + maxLength + "}$";
        return data.matches(pattern);
    }

    /**
     * 半角文字をチェックします。
     *
     * @param data
     * @param maxLength
     * @return
     */
    public static boolean isHalfChar(String data, int maxLength) {
        if (data == null) {
            return false;
        }

        String pattern = HALF_CHAR_PATTERN;
        return data.matches(pattern);
    }

    /**
     * 全角カタカナをチェックします。
     *
     * @param data
     * @param maxLength
     * @return
     */
    public static boolean isFullKatakana(String data, int maxLength) {
        if (data == null) {
            return false;
        }

        String pattern = "^" + FULL_KATAKANA_PATTERN + "{0," + maxLength + "}$";
        return data.matches(pattern);
    }

    /**
     * 全角をチェックします。
     *
     * @param data
     * @return
     */
    public static boolean isFullCharacter(String data) {
        if (data == null) {
            return false;
        }

        String pattern = toMatchRegex(MATCH_ZENKAKU);
        return data.matches(pattern);
    }

    /**
     * 郵便番号をチェックします。
     *
     * @param data
     * @return
     */
    public static boolean isZipCode(String data) {
        if (data == null) {
            return false;
        }

        String pattern = "^" + ZIP_CODE_PATTERN + "$";
        return data.matches(pattern);
    }

    /**
     * /「指定コードのみ」とマッチングする正規表現を生成します
     *
     * @param codes
     * @return
     */
    public static String toMatchRegex(String codes) {
        return "^[" + codes + "]+$";
    }

    /**
     * クラスがプリミティブな型か確認します このメソッドは次のクラスもプリミティブな型として扱います。
     * （String、Date、Integer、Long、Double、Boolean、Short、Float、Byte、Timestamp）
     *
     * @param clazz
     * @return プリミティブ型の有無
     */
    @SuppressWarnings({"rawtypes"})
    public static boolean isPrimitive(Class clazz) {
        if (clazz.equals(String.class)) {
            return true;
        } else if (clazz.equals(Date.class)) {
            return true;
        } else if (clazz.equals(Integer.class)) {
            return true;
        } else if (clazz.equals(int.class)) {
            return true;
        } else if (clazz.equals(Long.class)) {
            return true;
        } else if (clazz.equals(long.class)) {
            return true;
        } else if (clazz.equals(Double.class)) {
            return true;
        } else if (clazz.equals(double.class)) {
            return true;
        } else if (clazz.equals(Boolean.class)) {
            return true;
        } else if (clazz.equals(boolean.class)) {
            return true;
        } else if (clazz.equals(Short.class)) {
            return true;
        } else if (clazz.equals(short.class)) {
            return true;
        } else if (clazz.equals(float.class)) {
            return true;
        } else if (clazz.equals(Float.class)) {
            return true;
        } else if (clazz.equals(char.class)) {
            return true;
        } else if (clazz.equals(byte.class)) {
            return true;
        } else if (clazz.equals(Byte.class)) {
            return true;
        } else if (clazz.equals(Timestamp.class)) {
            return true;
        }

        return false;
    }

    /**
     * ファイル名禁止文字をチェックする
     *
     * @param fileName
     * @return
     */
    public static boolean isAvailableFileName(String fileName) {
        if (isNullOrEmpty(fileName)) {
            return false;
        }
        // 次の9文字は使用不可(< > : * ? " / \ |)
        final String regularExpression = "^.*[(<|>|:|\\*|?|\"|/|\\\\|\\|)].*$";
        return !fileName.matches(regularExpression);
    }
    
    /**
     * java.util.Dateをjava.sql.Dateに変換する
     * @param date
     * @return 
     */
    public static java.sql.Date convertDate(Date date) {

        if (date == null)
            return null;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new java.sql.Date(cal.getTimeInMillis());
    }
}
