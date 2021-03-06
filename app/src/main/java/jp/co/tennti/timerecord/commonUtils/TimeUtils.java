package jp.co.tennti.timerecord.commonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.tennti.timerecord.R;

/**
 * Created by peko on 2016/03/06.
 */
public class TimeUtils {

    public static final String PROVIS_TIME="18:00:00";
    /**
     * システム日付でのテーブル作成
     */
    public String getCurrentTableName() {
        //テーブル名作成
        StringBuffer builder = new StringBuffer();
        builder.append("time_record_");
        //builder.append(getCurrentYearAndMonth());
        builder.append(getCurrentYear());
        //String CUR_TIME_TABLE_NAME =  builder.toString();
        return builder.toString();
    }
    /**
     * 現在時刻の取得
     *
     */
    /*public static void getCurrentTime() {
        List<String> asList = Arrays.asList("日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日");
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int day_of_year = cal.get(Calendar.DAY_OF_YEAR);

        System.out.println("現在の日時は" + year + "年" + month + "月" + day + "日" + "(" + asList.get(week) + ")");
        System.out.println(hour + "時" + minute + "分" + second + "秒");
        System.out.println("今日は今年の" + day_of_year + "日目です");
    }*/
    /**
     * 現在時刻 FULL の取得
     * @return 現在時刻 yyyy-MM-dd HH:mm:ss
     */
    public static final String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        StringBuffer buffer = new StringBuffer();
        String yearStr  = String.valueOf(year);
        String monthStr = String.valueOf(month);
        String dayStr   = String.valueOf(day);
        String hourStr  = String.valueOf(hour);
        String minuteStr= String.valueOf(minute);
        String secondStr= String.valueOf(second);

        buffer.append(yearStr).append("-");
        addZeroBuf(month, buffer);
        buffer.append(monthStr).append("-");
        addZeroBuf(day, buffer);
        buffer.append(dayStr).append(" ");
        addZeroBuf(hour, buffer);
        buffer.append(hourStr).append(":");
        addZeroBuf(minute, buffer);
        buffer.append(minuteStr).append(":");
        addZeroBuf(second, buffer);
        buffer.append(secondStr);

        return buffer.toString();
    }
    /**
     * 現在時間の取得
     * @return 現在時間 HH:mm:ss
     */
    public String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        StringBuffer buffer = new StringBuffer();

        addZeroBuf(hour, buffer);
        buffer.append(String.valueOf(hour)).append(":");
        addZeroBuf(minute, buffer);
        buffer.append(String.valueOf(minute)).append(":");
        addZeroBuf(second, buffer);
        buffer.append(String.valueOf(second));

        return buffer.toString();
    }
    /**
     * 追加関数
     * @param num 判定数値
     * @param buffer 結合文字列
     */
    protected static void addZeroBuf(int num , StringBuffer buffer) {
        if(is10low(num)){
            buffer.append("0");
        }
    }
    /**
     * @param num 判定数値
     * @return 引数が大きければtrue
     */
    protected static boolean is10over(int num) {
        if (num < 10) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * @param num 判定数値
     * @return 引数が小さければtrue
     */
    protected static boolean is10low(int num) {
        if (num < 10) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 現在曜日の取得
     *
     * @return 現在の曜日：○曜日
     */
    public String getCurrentWeek() {
        final List<String> asList = Arrays.asList("日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日");
        final Calendar cal = Calendar.getInstance();
        final int week = cal.get(Calendar.DAY_OF_WEEK) - 1;

        return asList.get(week);
    }
    /**
     * 現在曜日の取得
     *
     * @return 現在の曜日略式：(曜日)
     */
    public String getCurrentWeekOmit() {
        final List<String> asList = Arrays.asList("日", "月", "火", "水", "木", "金", "土");
        final Calendar cal = Calendar.getInstance();
        final int week = cal.get(Calendar.DAY_OF_WEEK) - 1;

        StringBuffer buffer = new StringBuffer();
        buffer.append("(").append(asList.get(week)).append(")");

        return buffer.toString();
    }
    /**
     * 現在曜日の取得
     *y@param  String 指定対象日 yyy-MM-dd
     * @return 現在の曜日略式：(曜日)
     */
    public String getTargWeekOmit(String targDate) {
        final List<String> asList = Arrays.asList("日", "月", "火", "水", "木", "金", "土");
        final Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, Integer.parseInt(targDate.substring(0, 4)));
        cal.set(Calendar.MONTH, Integer.parseInt(targDate.substring(5,7)) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(targDate.substring(8,10)));
        final int week = cal.get(Calendar.DAY_OF_WEEK) - 1;

        StringBuffer buffer = new StringBuffer();
        buffer.append("(").append(asList.get(week)).append(")");

        return buffer.toString();
    }
    /**
     * 指定日の曜日を取得
     * @param  String 指定対象日： yyyy-MM-dd
     * @return 指定日の曜日略式：(曜日)
     */
    public String getTargetWeekOmit(String tarDate) {
        final List<String> asList = Arrays.asList("日", "月", "火", "水", "木", "金", "土");
        final Calendar cal = Calendar.getInstance();
        final int year     = Integer.parseInt(tarDate.substring(0, 4));
        final int month    = Integer.parseInt(tarDate.substring(5, 7)) -1;
        final int day      = Integer.parseInt(tarDate.substring(8, 10));
        cal.set(year, month, day);
        final int week = cal.get(Calendar.DAY_OF_WEEK) - 1;

        StringBuffer buffer = new StringBuffer();
        buffer.append("(").append(asList.get(week)).append(")");

        return buffer.toString();
    }

    /**
     * 現在年の取得
     *
     * @return 現在時刻yyyy
     */
    public static String getCurrentYear() {

        final Calendar cal = Calendar.getInstance();
        final int year     = cal.get(Calendar.YEAR);

        final String yearStr  = String.valueOf(year);

        StringBuffer builder = new StringBuffer();
        builder.append(yearStr);

        return builder.toString();
    }

    /**
     * 現在年月の取得
     *
     * @return 現在時刻yyyymm
     */
    public static String getCurrentYearAndMonth() {

        final Calendar cal = Calendar.getInstance();
        final int year     = cal.get(Calendar.YEAR);
        final int month    = cal.get(Calendar.MONTH) + 1;

        final String yearStr  = String.valueOf(year);
        final String monthStr = String.valueOf(month);

        StringBuffer builder = new StringBuffer();
        builder.append(yearStr);
        addZeroBuf(month, builder);
        builder.append(monthStr);

        return builder.toString();
    }

    /**
     * 現在年月の取得
     *
     * @return 現在時刻yyyy-MM
     */
    public String getCurrentYearMonthHyphen() {

        final Calendar cal = Calendar.getInstance();
        final int year     = cal.get(Calendar.YEAR);
        final int month    = cal.get(Calendar.MONTH) + 1;

        final String yearStr  = String.valueOf(year);
        final String monthStr = String.valueOf(month);

        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("-");
        addZeroBuf(month, buffer);
        buffer.append(monthStr);

        return buffer.toString();
    }

    /**
     * 現在年月の取得
     *
     * @return 現在時刻yyyy年
     */
    public String getCurrentYearJaCal() {

        final Calendar cal = Calendar.getInstance();
        final int year     = cal.get(Calendar.YEAR);

        final String yearStr  = String.valueOf(year);

        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("年");

        return  buffer.toString();
    }

    /**
     * 現在年月の取得
     *
     * @return 現在時刻yyyy年MM月
     */
    public String getCurrentYearMonthJaCal() {

        final Calendar cal = Calendar.getInstance();
        final int year     = cal.get(Calendar.YEAR);
        final int month    = cal.get(Calendar.MONTH) + 1;

        final String yearStr  = String.valueOf(year);
        final String monthStr = String.valueOf(month);

        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("年");
        addZeroBuf(month, buffer);
        buffer.append(monthStr).append("月");

        return  buffer.toString();
    }
    /**
     * 現在年月の取得
     *
     * @return 現在時刻yyyy年MM月dd日
     */
    public String getCurrentYearMonthDayJaCal() {

        final Calendar cal = Calendar.getInstance();
        final int year     = cal.get(Calendar.YEAR);
        final int month    = cal.get(Calendar.MONTH) + 1;
        final int day      = cal.get(Calendar.DATE);

        final String yearStr  = String.valueOf(year);
        final String monthStr = String.valueOf(month);
        final String dayStr   = String.valueOf(day);

        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("年");
        addZeroBuf(month, buffer);
        buffer.append(monthStr).append("月");
        addZeroBuf(day, buffer);
        buffer.append(dayStr).append("日");

        return buffer.toString();
    }
    /**
     * 現在年月日の取得
     *
     * @return 現在時刻yyyy-MM-dd
     */
    public static final String getCurrentYearMonthDay(){

        //String JOIN_YEAR_MONTH;
        final Calendar cal = Calendar.getInstance();
        final int year     = cal.get(Calendar.YEAR);
        final int month    = cal.get(Calendar.MONTH) + 1;
        final int day      = cal.get(Calendar.DATE);

        final String yearStr  = String.valueOf(year);
        final String monthStr = String.valueOf(month);
        final String dayStr   = String.valueOf(day);

        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("-");
        addZeroBuf(month, buffer);
        buffer.append(monthStr).append("-");
        addZeroBuf(day, buffer);
        buffer.append(dayStr);

        return buffer.toString();
    }

    /**
     * 現在年月の取得
     *
     * @return 現在時刻yyyy年MM月
     */
    public String getCurrentHourMinuteJaCal() {

        final Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        final String hourStr  = String.valueOf(hour);
        final String minutehStr = String.valueOf(minute);

        StringBuffer buffer = new StringBuffer();
        addZeroBuf(hour, buffer);
        buffer.append(hourStr).append("時");

        addZeroBuf(minute, buffer);
        buffer.append(minutehStr).append("分");

        return buffer.toString();
    }

    /**
     * 現在年月日の取得
     * @param  yyyyMMdd 指定日付:yyyy_MM_dd
     * @return 指定時刻yyyy-MM
     */
    public String getTargetYYYYMMHyphen( String yyyyMMdd) {

        //String JOIN_HOUR_MINUTE;
        final String yearStr = yyyyMMdd.substring(0, 4);
        final String monthStr = yyyyMMdd.substring(5, 7);

        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("-").append(monthStr);

        return buffer.toString();
    }
    /**
     * 現在年月日の変換
     * @param  指定日付:yyyy_MM_dd
     * @return 指定時刻yyyy-MM-dd
     */
    public String conTargetYYYYMMDDHyphen( String yyyyMMdd) {

        final String yearStr  = yyyyMMdd.substring(0, 4);
        final String monthStr = yyyyMMdd.substring(5, 7);
        final String dayStr   = yyyyMMdd.substring(8, 10);
        StringBuffer buffer   = new StringBuffer();
        buffer.append(yearStr).append("-").append(monthStr).append("-").append(dayStr);

        return buffer.toString();
    }

    /**
     * 現在時刻全容の変換
     * @param  hm 指定時間:hh_mm
     * @return 指定時間HH:mm:ss
     */
    public String conTargetTime( String hm) {
        String regex  = "[時分]";
        Pattern p     = Pattern.compile(regex);
        Matcher m     = p.matcher(hm);
        String result = m.replaceAll(":");
        final StringBuffer buffer = new StringBuffer();
        buffer.append(result).append(":").append(":00");

        return buffer.toString();
    }
    /**
     * 現在時刻全容の変換
     * @param  yyyyMMdd 指定日付:yyyy_MM_dd
     * @param  hms 指定時刻:hh_mm
     * @return 指定時刻yyyy-MM-dd HH:mm:ss
     */
    public String conTargetDateFullHyphen(String yyyyMMdd, String hm) {
        //String JOIN_HOUR_MINUTE;
        final  String yearStr  = yyyyMMdd.substring(0, 4);
        final String monthStr  = yyyyMMdd.substring(5, 7);
        final String dayStr    = yyyyMMdd.substring(8, 10);
        final String hourStr   = hm.substring(0, 2);
        final String minuteStr = hm.substring(3, 5);

        final StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("-").append(monthStr).append("-").append(dayStr).append(" ").append(hourStr).append(":").append(minuteStr).append(":00");

        return buffer.toString();
    }

    /**
     * 現在時刻[/]に変換の取得
     * @param  yyyyMMddhms 指定日付:yyyy_MM_dd
     * @return 指定時刻yyyy/MM/dd HH:mm:ss
     */
    public String conTargetDateFullSlash( String yyyyMMddhms) {

        final String yearStr  = yyyyMMddhms.substring(0, 4);
        final String monthStr = yyyyMMddhms.substring(5, 7);
        final String dayStr   = yyyyMMddhms.substring(8, 10);
        final String hms      = yyyyMMddhms.substring(11, 19);

        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("/").append(monthStr).append("/").append(dayStr).append(" ").append(hms);

        return buffer.toString();
    }

    /**
     * 現在年の取得
     * @param  yyyyMM 指定日付yyyy
     * @return 指定時刻yyyy
     */
    public String getTargetYYYY( String yyyyMM) {

        final String yearStr  = yyyyMM.substring(0, 4);
        StringBuffer buffer   = new StringBuffer();
        buffer.append(yearStr);
        return  buffer.toString();
    }
    /**
     * 現在年月日の取得
     * @param  yyyyMM 指定日付yyyy_MM
     * @return 指定時刻yyyyMM
     */
    public String getTargetYYYYMM( String yyyyMM) {

        final String yearStr  = yyyyMM.substring(0, 4);
        final String monthStr = yyyyMM.substring(5, 7);
        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr);
        buffer.append(monthStr);
        return  buffer.toString();
    }
    /**
     * 現在年月日の取得
     * @param  yyyyMMdd 指定日付 yyyy_MM_dd
     * @return 指定時刻yyyy年MM月
     */
    public String getTargetYYYYMMJaCal( String yyyyMMdd) {

        final String yearStr = yyyyMMdd.substring(0, 4);
        final String monthStr = yyyyMMdd.substring(5, 7);

        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("年").append(monthStr).append("月");

        return  buffer.toString();
    }
    /**
     * 年+月の結合
     * @param String year 指定年 yyyy
     * @param int month 指定月 MM
     * @return 結合時刻yyyy年MM月
     */
    public String joinTarYYYYMMJaCal( String year ,int month) {

        final String monthStr     = String.valueOf(month);
        final StringBuffer buffer = new StringBuffer();
        buffer.append(year).append("年");
        addZeroBuf(month, buffer);
        buffer.append(monthStr).append("月");

        return  buffer.toString();
    }
    /**
     * 現在年月日の取得
     * @param  yyyyMMdd 指定日付:yyyy_MM_dd
     * @return 指定時刻yyyy年MM月dd日
     */
    public String getTargetYYYYMMDDJaCal( String yyyyMMdd) {

        final String yearStr  = yyyyMMdd.substring(0, 4);
        final String monthStr = yyyyMMdd.substring(5, 7);
        final String dayStr   = yyyyMMdd.substring(8, 10);

        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("年").append(monthStr).append("月").append(dayStr).append("日");

        return  buffer.toString();
    }
    /**
     * 現在年月日の結合
     * 月に関しては渡す前にタス１する
     * @param year 対象年:yyyy
     *            @param month 対象月:MM
     *                       @param day 対象日:dd
     * @return 指定時刻yyyy年MM月dd日
     */
    public String getJoinYYYYMMDDJaCal( int year,int month,int day) {

        final String yearStr  = String.valueOf(year);
        final String monthStr = String.valueOf(month);
        final String dayStr   = String.valueOf(day);

        StringBuffer buffer = new StringBuffer();
        buffer.append(yearStr).append("年");
        addZeroBuf(month, buffer);
        buffer.append(monthStr).append("月");
        addZeroBuf(day, buffer);
        buffer.append(dayStr).append("日");

        return  buffer.toString();
    }

    /**
     * 現在時刻の取得
     * @param  hour 指定日付（時）:HH
     * @param  minute 指定日付（分）:mm
     * @return 指定時刻HH時mm分
     */
    public String getTargetHourMinuteJaCal(int hour,int minute) {

        final String hourStr      = String.valueOf(hour);
        final String minuteStr    = String.valueOf(minute);

        final StringBuffer buffer = new StringBuffer();
        addZeroBuf(hour, buffer);
        buffer.append(hourStr).append("時");
        addZeroBuf(minute, buffer);
        buffer.append(minuteStr).append("分");

        return  buffer.toString();
    }



    /**
     * 時刻の差分
     * @param  endDateStr 退社時間 yy/MM/dd HH:mm:ss
     * @return 差分時刻 HH:mm
     * @throws ParseException 型変換時に起こりうる例外
     */
    public String getTimeDiff(String endDateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat ("yy/MM/dd HH:mm:ss");
        String provisDateStr       = endDateStr.substring(0,10) +" "+ PROVIS_TIME;
        Date provisDate            = null; // 規定時刻
        Date endDate               = null; // 終了時刻
        try {
            provisDate = formatter.parse(provisDateStr);
            endDate = formatter.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int diff = endDate.compareTo(provisDate);
        String timeDiffStr;
        if (diff == 0) {
            //日付1と日付2は同じです
            timeDiffStr = getTimeCalculation(provisDate,endDate);
        } else if (diff > 0) {
            //日付1は日付2より未来の日付です
            timeDiffStr = getTimeCalculation(provisDate,endDate);
        } else {
            //日付1は日付2より過去の日付です
            timeDiffStr = "00:00:00";
        }
        return timeDiffStr;
    }
    /**
     * 時刻の差分計算クラス
     * @param  provisDate 規定時刻 yy/MM/dd HH:mm:ss
     * @param  endDate 退社時刻 yy/MM/dd HH:mm:ss
     * @return 差分時刻 HH:mm
     */
    public String getTimeCalculation(Date provisDate,Date endDate) {
        final long diffTime =  endDate.getTime()-provisDate.getTime();
        // 結果出力用フォーマット
        SimpleDateFormat timeFormatter = new SimpleDateFormat ("HH:mm:ss");
        timeFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return timeFormatter.format(new Date(diffTime));
    }
    /**
     * 時刻の追加計算クラス
     * @param  provisDate 規定時刻 yy/MM/dd HH:mm:ss
     * @param  endDate 退社時刻 yy/MM/dd HH:mm:ss
     * @return 差分時刻 HH:mm
     */
    public String addTimeCalculation(String provisTime,String addTime) {
        final StringBuffer buffer = new StringBuffer();
        String [] pt = provisTime.split(":");
        int ptHour   = Integer.parseInt(pt[0]);
        int ptMinute = Integer.parseInt( pt[1] );
        int ptSecond = Integer.parseInt(pt[2]);
        String [] st = addTime.split(":");
        int sttHour  = Integer.parseInt(st[0]);
        int stMinute = Integer.parseInt( st[1] );
        int stSecond = Integer.parseInt(st[2]);
        buffer.append(ptHour + sttHour).append(":").append(ptMinute + stMinute).append(":").append(ptSecond + stSecond);

        return buffer.toString();
    }
    /**
     * 金曜日判定クラス
     * @param  string 判定用曜日
     * @return boolean 判定結果
     */
    public boolean isFriday(String tarWeek) {
        if (tarWeek.equals("(金)")) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 土曜日判定クラス
     * @param  string 判定用曜日
     * @return boolean 判定結果
     */
    public boolean isSaturday(String tarWeek) {
        if (tarWeek.equals("(土)")) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 日曜日判定クラス
     * @param  string 判定用曜日
     * @return boolean 判定結果
     */
    public boolean isSunday(String tarWeek) {
        if (tarWeek.equals("(日)")) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 曜日判定クラス
     * @param  string 判定用曜日
     * @return int 判定結果
     * 日曜 :0 , 月曜:1 ,火曜:2 , 水曜:3 , 木曜:4 , 金曜:5, 土曜:6 , 判定不能:99
     */
    public int isWeekDrive(String tarWeek){
        if (tarWeek.equals("(日)")) {
            return 0;
        }
        if (tarWeek.equals("(月)")) {
            return 1;
        }
        if (tarWeek.equals("(火)")) {
            return 2;
        }
        if (tarWeek.equals("(水)")) {
            return 3;
        }
        if (tarWeek.equals("(木)")) {
            return 4;
        }
        if (tarWeek.equals("(金)")) {
            return 5;
        }
        if (tarWeek.equals("(土)")) {
            return 6;
        }
        return 99;
    }
    /**
     * 曜日背景セットクラス
     * @param  string 判定用曜日
     * @return int 判定結果
     * 日曜 :0 , 月曜:1 ,火曜:2 , 水曜:3 , 木曜:4 , 金曜:5, 土曜:6 , 判定不能:99
     */
    public int setBackgroundWeek(String tarWeek){
        if (isWeekDrive(tarWeek)== 0) {
            return R.drawable.color_sunday;
        }
        if (isWeekDrive(tarWeek)== 1) {
            return R.drawable.color_monday;
        }
        if (isWeekDrive(tarWeek)== 2) {
            return R.drawable.color_tuesday;
        }
        if (isWeekDrive(tarWeek)== 3) {
            return R.drawable.color_wednesday;
        }
        if (isWeekDrive(tarWeek)== 4) {
            return R.drawable.color_thursday;
        }
        if (isWeekDrive(tarWeek)== 5) {
            return R.drawable.color_friday ;
        }
        if (isWeekDrive(tarWeek)== 6) {
            return R.drawable.color_saturday;
        }
        return R.drawable.row_color1;
    }
    /**
     * システム日付が月初かどうか判定を行う
     * 月初の場合true
     * @return boolean 判定結果
     */
    public static boolean isBeginningMonth(){
        Calendar cal = Calendar.getInstance();
        /*int year  = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MARCH, month);*/
        int day   = cal.get(Calendar.DATE);
        int days  = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        return day == days;
    }
}