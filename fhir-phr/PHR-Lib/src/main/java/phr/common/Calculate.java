/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 値の変換を行うクラス
 * @author KISNOTE011
 */
public class Calculate {
//    /**
//     * <pre>AbsoluteDate型をDate型に変換します。</pre>
//     * @param absoluteDate
//     * @return rtnDate
//     * @throws ParseException
//     */
//    public static Date AbsoluteDateToDate(AbsoluteDate absoluteDate) throws ParseException
//    {
//            Date rtnDate = new Date();
//            StringBuffer dateString = new StringBuffer();
//            dateString.append(absoluteDate.getYear());
//            dateString.append(String.format("%02d", absoluteDate.getMonth()));
//            dateString.append(String.format("%02d", absoluteDate.getDate()));
//            rtnDate = new SimpleDateFormat("yyyyMMdd").parse(dateString.toString());
//            return rtnDate;
//    }
//
//
//    /**
//     * <pre>AbsoluteDate型をString型に変換します。</pre>
//     * @param absoluteDate
//     * @param divider
//     * @return rtnDate
//     * @throws ParseException
//     */
//    public static String AbsoluteDateToString(AbsoluteDate absoluteDate,String divider) throws ParseException
//    {
//            String rtnDate = "";
//            StringBuffer dateString = new StringBuffer();
//            dateString.append(absoluteDate.getYear());
//            dateString.append(divider);
//            dateString.append(String.format("%02d", absoluteDate.getMonth()));
//            dateString.append(divider);
//            dateString.append(String.format("%02d", absoluteDate.getDate()));
//            rtnDate = dateString.toString();
//            return rtnDate;
//    }
//
//    /**
//     * <pre>文字列yyyy/MM/dd,yyyy-MM-dd,yyyy MM ddをAbsoluteDate型に変換します。</pre>
//     * @param StringDate
//     * @return AbsoluteDate
//     * @throws ParseException
//     * datadomainで使用しているため改修する際は注意
//     */
//    public static AbsoluteDate stringDateToAbsoluteDate(String StringDate) throws ParseException
//    {
//            if(StringDate == null){
//                    return null;
//            }
//            if(StringDate.equals("")){
//                    return null;
//            }
//            String[] str1Ary = new String[3];
//
//            if(StringDate.contains("/")){
//                    str1Ary = StringDate.split("/");
//            }else if(StringDate.contains(" ")){
//                    str1Ary = StringDate.split(" ");
//            }else if(StringDate.contains("-")){
//                    str1Ary = StringDate.split(" ");
//            }else {
//                    if (StringDate.length() > 7) {
//                            str1Ary[0] = StringDate.substring(0, 4);
//                            str1Ary[1] = StringDate.substring(4, 6);
//                            str1Ary[2] = StringDate.substring(6, 8);
//                    }
//            }
//
//            if (str1Ary.length < 3)
//            {
//                    throw new ParseException(null, 0);
//            }
//
//            List<Integer> numList = new ArrayList<Integer>();
//            for(int i = 0;i < str1Ary.length ;i++ ){
//                    int insNum = Integer.parseInt(str1Ary[i]);
//                    numList.add(insNum);
//            }
//            int yyyy = numList.get(0);
//            int MM = numList.get(1);
//            int dd = numList.get(2);
//            AbsoluteDate absoluteDate = new AbsoluteDate(yyyy,MM,dd);
//            return absoluteDate;
//    }
//
//    /**
//     * <pre>文字列yyyy/MM/dd,yyyy-MM-dd,yyyy MM dd, yyyyMMdd をDate型に変換します。</pre>
//     * @param stringDate
//     * @return
//     * @throws ParseException
//     */
//    public static Date stringDateToDate(String stringDate) throws ParseException
//    {
//            if(stringDate == null){
//                    return null;
//            }
//            if(stringDate.equals("")){
//                    return null;
//            }
//            String[] str1Ary = new String[3];
//
//            Date rtnDate = new Date();
//            if(stringDate.contains("/")){
//                    str1Ary = stringDate.split("/");
//            }else if(stringDate.contains(" ")){
//                    str1Ary = stringDate.split(" ");
//            }else if(stringDate.contains("-")){
//                    str1Ary = stringDate.split(" ");
//            }else {
//                    if (stringDate.length() > 7) {
//                            str1Ary[0] = stringDate.substring(0, 4);
//                            str1Ary[1] = stringDate.substring(4, 6);
//                            str1Ary[2] = stringDate.substring(6, 8);
//                    }
//            }
//
//            if (str1Ary.length < 3)
//            {
//                    throw new ParseException(null, 0);
//            }
//
//            StringBuffer dateString = new StringBuffer();
//            dateString.append(str1Ary[0]);
//            dateString.append(str1Ary[1]);
//            dateString.append(str1Ary[2]);
//            rtnDate = new SimpleDateFormat("yyyyMMdd").parse(dateString.toString());
//            return rtnDate;
//    }
//
//    /**
//     * <pre>Date型にParse可能な文字列をyyyy/mm/dd型の文字列に変換します。</pre>
//     * @param stringDate
//     * @return
//     * @throws ParseException
//     */
//    public static String stringDateToStringDate(String stringDate) throws ParseException
//    {
//            if(stringDate == null){
//                    return null;
//            }
//            if(stringDate.equals("")){
//                    return null;
//            }
//            String[] str1Ary = new String[3];
//
//            if(stringDate.contains("/")){
//                    str1Ary = stringDate.split("/");
//            }else if(stringDate.contains(" ")){
//                    str1Ary = stringDate.split(" ");
//            }else if(stringDate.contains("-")){
//                    str1Ary = stringDate.split("-");
//            }else {
//                    if (stringDate.length() > 7) {
//                            str1Ary[0] = stringDate.substring(0, 4);
//                            str1Ary[1] = stringDate.substring(4, 6);
//                            str1Ary[2] = stringDate.substring(6, 8);
//                    }
//            }
//
//            if (str1Ary.length < 3)
//            {
//                    throw new ParseException(null, 0);
//            }
//
//            StringBuffer dateString = new StringBuffer();
//            dateString.append(str1Ary[0]);
//            dateString.append("/");
//            dateString.append(str1Ary[1]);
//            dateString.append("/");
//            dateString.append(str1Ary[2]);
//            String rtnDate = dateString.toString();
//            return rtnDate;
//    }
//
//    /**
//     * <pre>timestamp型を出力用文字列に変換します。</pre>
//     * @param timeStamp
//     * @param mode 0:yyyy/M/d H:m:s.S　1:yyyy/M/d H:m:s 2:yyyyMdHms 3:yyyyMMddHHmmss 4:yyyy/MM/dd HH:mm:ss
//     * @return stgTimeStamp
//     * @throws ParseException
//     */
//    public static String displayTimeStamp(AbsoluteTimeStamp timeStamp, int mode) throws ParseException
//    {
//            if (timeStamp == null)
//                    return "";
//
//            StringBuffer TimeStamp = new StringBuffer();
//            if(mode==0){
//                    TimeStamp.append(timeStamp.getYear()).append("/").append(timeStamp.getMonth()).append("/");
//                    TimeStamp.append(timeStamp.getDayOfMonth()).append(" ");
//                    TimeStamp.append(timeStamp.getHour()).append(":").append(timeStamp.getMinute()).append(":");
//                    TimeStamp.append(timeStamp.getSecond()).append(".").append(timeStamp.getMillisecond());
//            }else if(mode==1){
//                    TimeStamp.append(timeStamp.getYear()).append("/").append(timeStamp.getMonth()).append("/");
//                    TimeStamp.append(timeStamp.getDayOfMonth()).append(" ");
//                    TimeStamp.append(timeStamp.getHour()).append(":").append(timeStamp.getMinute()).append(":");
//                    TimeStamp.append(timeStamp.getSecond());
//            }else if(mode==2){
//                    TimeStamp.append(timeStamp.getYear()).append(timeStamp.getMonth()).append(timeStamp.getDayOfMonth());
//                    TimeStamp.append(timeStamp.getHour()).append(timeStamp.getMinute()).append(timeStamp.getSecond());
//            }else if(mode==3){
//                    TimeStamp.append(String.format("%04d",timeStamp.getYear())).append(String.format("%02d",timeStamp.getMonth())).append(String.format("%02d",timeStamp.getDayOfMonth()));
//                    TimeStamp.append(String.format("%02d",timeStamp.getHour())).append(String.format("%02d",timeStamp.getMinute())).append(String.format("%02d",timeStamp.getSecond()));
//            }else if(mode==4){
//                    TimeStamp.append(String.format("%04d",timeStamp.getYear())).append("/").append(String.format("%02d",timeStamp.getMonth())).append("/").append(String.format("%02d",timeStamp.getDayOfMonth())).append(" ");
//                    TimeStamp.append(String.format("%02d",timeStamp.getHour())).append(":").append(String.format("%02d",timeStamp.getMinute())).append(":").append(String.format("%02d",timeStamp.getSecond()));
//                    TimeStamp.append(".").append(String.format("%03d",timeStamp.getMillisecond()));
//            }
//            String stgTimeStamp = TimeStamp.toString();
//            return stgTimeStamp;
//    }
//
//    /**
//     * 現在時刻をTimeStampの形で取得します
//     * @return
//     */
//    public static AbsoluteTimeStamp getNowTimeStamp() {
//            Calendar cal1 = Calendar.getInstance();  //(1)オブジェクトの生成
//    int year = cal1.get(Calendar.YEAR);        //(2)現在の年を取得
//    int month = cal1.get(Calendar.MONTH) + 1;  //(3)現在の月を取得
//    int day = cal1.get(Calendar.DATE);         //(4)現在の日を取得
//    int hour = cal1.get(Calendar.HOUR_OF_DAY); //(5)現在の時を取得
//    int minute = cal1.get(Calendar.MINUTE);    //(6)現在の分を取得
//    int second = cal1.get(Calendar.SECOND);    //(7)現在の秒を取得
//    int milliSecond = cal1.get(Calendar.MILLISECOND);    //(8)現在のミリ秒を取得
//    AbsoluteTimeStamp timeStamp= new AbsoluteTimeStamp(year,month,day,hour,minute,second,milliSecond);
//            return timeStamp;
//    }
//
//    /**
//     * <pre>日付文字列　をtimestamp型に変換します。</pre>
//     * @param stringTimeStamp
//     * @param mode 0:yyyy/MM/dd 00:00:00.000　1:yyyy/MM/dd 23:59:59.999
//     * @return TimeStamp
//     * @throws ParseException
//     */
//    public static AbsoluteTimeStamp stringToTimeStamp(String stringTimeStamp,int mode) throws ParseException
//    {
//            String[] str1Ary = new String[3];
//
//    AbsoluteTimeStamp timeStamp =null;
//            if(stringTimeStamp.contains("/")){
//                    str1Ary = stringTimeStamp.split("/");
//            }else if(stringTimeStamp.contains(" ")){
//                    str1Ary = stringTimeStamp.split(" ");
//            }else if(stringTimeStamp.contains("-")){
//                    str1Ary = stringTimeStamp.split(" ");
//            }else {
//                    if (stringTimeStamp.length() > 7) {
//                            str1Ary[0] = stringTimeStamp.substring(0, 4);
//                            str1Ary[1] = stringTimeStamp.substring(4, 6);
//                            str1Ary[2] = stringTimeStamp.substring(6, 8);
//                    }
//            }
//
//            if (str1Ary.length < 3)
//            {
//                    throw new ParseException(null, 0);
//            }
//
//            if(mode==0){
//            timeStamp = new AbsoluteTimeStamp(Integer.parseInt(str1Ary[0]),Integer.parseInt(str1Ary[1]),Integer.parseInt(str1Ary[2]),0,0,0,0);
//            }else if(mode==1){
//            timeStamp = new AbsoluteTimeStamp(Integer.parseInt(str1Ary[0]),Integer.parseInt(str1Ary[1]),Integer.parseInt(str1Ary[2]),23,59,59,999);
//            }
//            return timeStamp;
//    }
//
//
//    /**
//     * <pre>指定された文字列が、半角英数字(記号含む)か否かを返します。</pre>
//     * @param value 処理対象となる文字列
//     * @return true 半角英数字である(もしくは対象文字がない), false 半角英数字でない
//     */
//    public static Boolean isHalfWidthAlphanumeric(String value) {
//            if ( value == null || value.length() == 0 ){
//                    return true;
//            }
//            int len = value.length();
//            byte[] bytes = value.getBytes();
//            if ( len != bytes.length ){
//                    return false;
//            }
//            return true;
//    }
//
//    /**
//     * <pre>指定された文字列が、全角スペースを含むか否かを返します。</pre>
//     * @param value 処理対象となる文字列
//     * @return true 含む, false 含まない
//     */
//    public static Boolean isMachZenkakuSpace(String value) {
//            boolean rtnflg=false;
//            if ( value == null || value.length() == 0 ){
//                    return rtnflg;
//            }
//    //判定するパターンを生成
//    Pattern p1 = Pattern.compile(".*[　].*");
//    Matcher m1 = p1.matcher(value);
//
//    boolean a = m1.find();
//    if(a){
//            rtnflg = true;
//    }
//            return rtnflg;
//    }
//
//    /**
//     * <pre>指定された文字列が、半角英字を含むか否かを返します。</pre>
//     * @param value 処理対象となる文字列
//     * @return true 含む, false 含まない
//     */
//    public static Boolean isMachHalfAlpha(String value) {
//            boolean rtnflg=false;
//            if ( value == null || value.length() == 0 ){
//                    return rtnflg;
//            }
//    //判定するパターンを生成
//    Pattern p1 = Pattern.compile(".*[a-zA-Z].*");
//    Matcher m1 = p1.matcher(value);
//
//    boolean a = m1.find();
//    if(a){
//            rtnflg = true;
//    }
//            return rtnflg;
//    }
//
//    /**
//     * <pre>指定された文字列が、数字を含むか否かを返します。</pre>
//     * @param value 処理対象となる文字列
//     * @return true 含む, false 含まない
//     */
//    public static Boolean isMachNumber(String value) {
//            boolean rtnflg=false;
//            if ( value == null || value.length() == 0 ){
//                    return rtnflg;
//            }
//    //判定するパターンを生成
//    Pattern p1 = Pattern.compile(".*[0-9０－９].*");
//    Matcher m1 = p1.matcher(value);
//
//    boolean a = m1.find();
//    if(a){
//            rtnflg = true;
//    }
//            return rtnflg;
//    }

    /**
     * <pre>指定された文字列が、半角英字と数字を１文字以上含むか否かを返します。</pre>
     * @param value 処理対象となる文字列
     * @return true 含む, false 含まない
     */
    public static Boolean isMachAlphaNum(String value) {
        boolean rtnflg=false;
        if ( value == null || value.length() == 0 ){
                return rtnflg;
        }
        //判定するパターンを生成
        Pattern p1 = Pattern.compile(".*[a-zA-Z].*");
        Matcher m1 = p1.matcher(value);
        Pattern p2 = Pattern.compile(".*[0-9].*");
        Matcher m2 = p2.matcher(value);
        boolean a = m1.find();
        boolean b = m2.find();
        if(a && b){
            rtnflg = true;
        }
        return rtnflg;
    }

    /**
     * <pre>指定された文字列が、半角英字と数字と記号を１文字以上含むか否かを返します。</pre>
     * @param value 処理対象となる文字列
     * @return true 含む, false 含まない
     */
    public static Boolean isMachAlphaNumChara(String value) {
        boolean rtnflg=false;
        if ( value == null || value.length() == 0 ){
            return rtnflg;
        }
        //判定するパターンを生成
        Pattern p1 = Pattern.compile(".*[a-zA-Z].*");
        Matcher m1 = p1.matcher(value);
        Pattern p2 = Pattern.compile(".*[0-9].*");
        Matcher m2 = p2.matcher(value);
        Pattern p3 = Pattern.compile(".*[\\p{Punct}].*");
        Matcher m3 = p3.matcher(value);
        boolean a = m1.find();
        boolean b = m2.find();
        boolean c = m3.find();
        if(a && b && c){
         rtnflg = true;
        }
        return rtnflg;
    }

    /**
     * <pre>指定された文字列が、半角英大文字と半角英小文字と数字と記号を１文字以上含むか否かを返します。</pre>
     * @param value 処理対象となる文字列
     * @return true 含む, false 含まない
     */
    public static Boolean isMachUpperLowerNumChara(String value) {
        boolean rtnflg=false;
        if ( value == null || value.length() == 0 ){
            return rtnflg;
        }
        //判定するパターンを生成
        Pattern p1 = Pattern.compile(".*[a-z].*");
        Matcher m1 = p1.matcher(value);
        Pattern p2 = Pattern.compile(".*[A-Z].*");
        Matcher m2 = p2.matcher(value);
        Pattern p3 = Pattern.compile(".*[0-9].*");
        Matcher m3 = p3.matcher(value);
        Pattern p4 = Pattern.compile(".*[\\p{Punct}].*");
        Matcher m4 = p4.matcher(value);
        boolean a = m1.find();
        boolean b = m2.find();
        boolean c = m3.find();
        boolean d = m4.find();
        if(a && b && c && d){
            rtnflg = true;
        }
        return rtnflg;
    }
//
//    /**
//     * <pre>現在日時を指定のフォーマットで取得返します。</pre>
//     * @param dateFormat 返却する日時のフォーマット　例：yyyy/MM/dd HH:mm:ss.SSS
//     * @return dateTime
//     */
//    public static String nowDateTime(String dateFormat) {
//    Date date = new Date();
//    SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat);
//    String dateTime = sdf1.format(date);
//            return dateTime;
//    }
//
//    /**
//     * <pre>指定された文字列が正の整数かチェックします</pre>
//     * @param val チェックする文字列
//     * @return boolean
//     */
//    public static boolean isPositiveInteger(String val) {
//            String regex = "^(0|[1-9]\\d*)$";
//        Pattern p = Pattern.compile(regex);
//        Matcher m1 = p.matcher(val);
//        boolean rtn = m1.find();
//        return rtn;
//    }
//
//    /**
//     * <pre>指定された文字列がyyyy/mm/ddのフォーマットかチェックします</pre>
//     * @param val チェックする文字列
//     * @return boolean
//     */
//    public static boolean isDateFormat(String val) {
//            String regex = "\\d{4}/\\d{2}/\\d{2}";
//        Pattern p = Pattern.compile(regex);
//        Matcher m1 = p.matcher(val);
//        boolean rtn = m1.find();
//        return rtn;
//    }
//
//    /**
//     * <pre>指定された文字列長に応じたDataフォーマットの文字列を返します</pre>
//     * @param value 処理対象となる文字列
//     * @return String Dataフォーマット文字列
//     */
//    public static String createDataFormat(String value) {
//            if ( value == null) return null;
//
//            if(value.indexOf(".") == -1){
//                    // "."を含まない場合、ミリ秒以下は無しとする
//                    switch (value.length()){
//                            case 4:
//                                    // 4桁の場合、西暦4桁と判断
//                                    return "yyyy";
//                            case 6:
//                                    // 2桁の場合、西暦＋月と判断
//                                    return "yyyyMM";
//                            case 8:
//                                    // 8桁の場合、西暦＋月＋日と判断
//                                    return "yyyyMMdd";
//                            case 10:
//                                    // 10桁の場合、西暦＋月＋日＋時と判断
//                                    return "yyyyMMddHH";
//                            case 12:
//                                    // 12桁の場合、西暦＋月＋日＋時＋分と判断
//                                    return "yyyyMMddHHmm";
//                            case 14:
//                                    // 14桁の場合、西暦＋月＋日＋時＋分＋秒と判断
//                                    return "yyyyMMddHHmmss";
//                        default:
//                            // それ以外の場合
//                            return null;
//                    }
//            } else {
//                    // "."を含む場合、ミリ秒以下が有りとする
//
//                    // "."を除いて処理を行う
//                    String newValue = value.replace(".", "");
//
//                    switch (newValue.length()){
//                            case 15:
//                                    // 15桁の場合、西暦＋月＋日＋時＋分＋秒＋ミリ秒(1桁)と判断
//                                    return "yyyyMMddHHmmssS";
//                            case 16:
//                                    // 16桁の場合、西暦＋月＋日＋時＋分＋秒＋ミリ秒(2桁)と判断
//                                    return "yyyyMMddHHmmssSS";
//                            case 17:
//                                    // 17桁の場合、西暦＋月＋日＋時＋分＋秒＋ミリ秒(3桁)と判断
//                                    return "yyyyMMddHHmmssSSS";
//                            case 18:
//                                    // 18桁の場合、西暦＋月＋日＋時＋分＋秒＋ミリ秒(4桁)と判断
//                                    return "yyyyMMddHHmmssSSSS";
//                        default:
//                            return null;
//                    }
//            }
//    }
//
//    /**
//     * yyyy/mm/dd または　yyyymmdd　のフォーマットで存在する日付かどうか厳密にチェックします。
//     * @param dateStr
//     * @return
//     * @throws ParseException
//     */
//    public static String checkTextBoxExactDate(String dateStr) throws ParseException {
//            boolean check = Calculate.isDateFormat(dateStr);
//            if(!check){
//                    if(dateStr.replace("/", "").length() < 8 ) {
//                            throw new ParseException("", 0); 
//                    }else{
//                            dateStr = Calculate.stringDateToStringDate(dateStr);
//                    }
//            }
//            Calculate.checkExistDate(dateStr);//失敗したらParseException
//            return dateStr;
//    }
//
//    /**
//     * 存在する日付かどうか厳密にチェックします
//     * 日付が存在しないときはParseException
//     * @param editAccountDto
//     * @return 
//     * @throws ParseException
//     */
//    public static boolean checkExistDate(String str)throws ParseException {
//            DateFormat format = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.JAPAN);
//            //DateFormat format = DateFormat.getDateInstance();
//            format.setLenient(false);
//            format.parse(str);//失敗したらParseException
//            return true;
//    }
//
//    /**
//     * <pre>指定された文字列が、半角英字と全角英字を１文字以上含むか否かを返します。</pre>
//     * @param value 処理対象となる文字列
//     * @return true 含む, false 含まない
//     */
//    public static Boolean isMachAlphaChara(String value) {
//            boolean rtnflg=false;
//            if ( value == null || value.length() == 0 ){
//                    return rtnflg;
//            }
//   //判定するパターンを生成
//   Pattern p1 = Pattern.compile(".*[a-zA-Z].*");
//   Matcher m1 = p1.matcher(value);
//
//   Pattern p2 = Pattern.compile(".*[ａ－ｚＡ-Ｚ].*");
//   Matcher m2 = p2.matcher(value);
//
//   boolean a = m1.find();
//   boolean b = m2.find();
//   if(a || b){
//    rtnflg = true;
//   }
//            return rtnflg;
//    }
//
//    /**
//     * <pre>指定された文字列が、半角数字と全角数字を１文字以上含むか否かを返します。</pre>
//     * @param value 処理対象となる文字列
//     * @return true 含む, false 含まない
//     */
//    public static Boolean isMachNumberChara(String value) {
//            boolean rtnflg=false;
//            if ( value == null || value.length() == 0 ){
//                    return rtnflg;
//            }
//
//            for (int i = 0; i < value.length(); i++) {
//                    if (Character.isDigit(value.charAt(i)) == false) {
//                            return false;
//                    } 
//            }
//            return true;
//    }
//
//    /**
//     * <pre>指定された文字列が、カタカナを１文字以上含むか否かを返します。</pre>
//     * @param value 処理対象となる文字列
//     * @return true 含む, false 含まない
//     */
//    public static Boolean isMachKatanaka(String value) {
//            boolean rtnflg=false;
//            if ( value == null || value.length() == 0 ){
//                    return rtnflg;
//            }
//
//            for (int i=0; i<value.length(); i++) {
//                    char c = value.charAt(i);
//                    Pattern p = Pattern.compile("[ー－・゛゜ゝゞ]");
//                   Matcher m = p.matcher(value);
//                   boolean b = m.find();
//                    if (Character.UnicodeBlock.of(c) != Character.UnicodeBlock.KATAKANA && !b){
//                            return false;
//                    }
//            } 
//            return true;
//    }
//
//    /**
//     * 正規表現のチェック
//     * @param value
//     * @param regexp
//     */
//    public static boolean checkRegexp(String value, String regexp){
//            boolean rtnflg=false;
//            if ( value == null || value.length() == 0 ){
//                    return rtnflg;
//            }
//            Pattern p = Pattern.compile(regexp);
//            Matcher m = p.matcher(value);
//            rtnflg = m.find();
//            return rtnflg;
//    }
}
