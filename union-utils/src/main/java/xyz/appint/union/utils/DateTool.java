package xyz.appint.union.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTool {
    // private static int weeks = 0;
    private static int MaxDate;//一月最大天数
    private static int MaxYear;//一年最大天数

    // 计算两个Date间的天数
    public int DateDiff(Date d1, Date d2) {
        int d = 0;
        try {
            long l = d1.getTime() - d2.getTime();
            d = (int) (l / 60 / 60 / 1000 / 24);
        } catch (Exception e) {
        }

        return d;
    }

    // 计算两个Date间的天数
    public int DateDiff(Date d2) {
        int d = 0;
        GregorianCalendar calendar = new GregorianCalendar();
        Date d1 = calendar.getTime();
        d = DateDiff(d1, d2);

        return d;
    }

    // 计算两个Date间的天数
    public int DateDiff(String d1) {
        int d = 0;
        if (d1 == null)
            return d;
        try {
            d1 = d1.replaceAll("-", "/");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d2 = new Date(df.parse(d1).getTime());
            d = DateDiff(d2);
        } catch (Exception e) {
        }
        return d;
    }

    // 计算两个Date间的时间差(精确到秒)
    public static int DateDiff(String date1, String date2) {
        // date1 = formatDate(date1, "yyyy-MM-dd hh:mm:ss");
        // date2 = formatDate(date2, "yyyy-MM-dd hh:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = stringToGCalendar(date2);
        try {
            gc1.setTime(df.parse(date1));
            gc2.setTime(df.parse(date2));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        Date d1 = gc1.getTime();
        Date d2 = gc2.getTime();
        int d = 0;
        try {
            long l = d1.getTime() - d2.getTime();
            d = (int) l / 1000;
        } catch (Exception e) {
        }
        return d;
    }

    public static String dateAdd(int dayNumber, String strInputDate) {
        return dateAdd(GregorianCalendar.DATE, dayNumber, strInputDate);
    }

    public static String minuteAdd(int num, String strInputDate) {
        return dateAdd(GregorianCalendar.MINUTE, num, strInputDate);
    }

    public static String hourAdd(int num, String strInputDate) {
        return dateAdd(GregorianCalendar.HOUR, num, strInputDate);
    }

    /**
     * @param datePart
     * @param number
     * @param strInputDate
     * @return
     */
    public static String dateAdd(int datePart, int number, String strInputDate) {
        return dateAdd(datePart, number, strInputDate, "yyyy-MM-dd");
    }

    public static String dateAdd(int datePart, int number, String strInputDate, String formatType) {
        GregorianCalendar gc = stringToGCalendar(strInputDate);
        gc.add(datePart, number);
        Date d = gc.getTime();
        DateFormat df = new SimpleDateFormat(formatType);
        return df.format(d);
    }

    // 输入GregorianCalendar取年
    public static int getYear(GregorianCalendar gc) {
        return gc.get(Calendar.YEAR);
    }

    // 输入Date取年
    public static int getYear(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return gc.get(Calendar.YEAR);
    }

    // 输入字符串取年
    public static int getYear(String date) {
        try {
            if (date == null || date.length() < 1) {
                return 0;
            }
            date = formatDate(date, "");
            GregorianCalendar gc = stringToGCalendar(date);
            return gc.get(Calendar.YEAR);
        } catch (Exception e) {
        }
        return 0;
    }

    // 输入GregorianCalendar取月
    public static int getMonth(GregorianCalendar gc) {
        return gc.get(Calendar.MONTH) + 1;
    }

    // 输入Date取月
    public static int getMonth(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return gc.get(Calendar.MONTH) + 1;
    }

    // 输入字符串取月
    public static int getMonth(String date) {
        if (date == null
                || (date.indexOf("-") == -1 && date.indexOf("/") == -1))
            return 1;
        try {
            date = formatDate(date, "");

            GregorianCalendar gc = stringToGCalendar(date);
            return gc.get(Calendar.MONTH) + 1;
        } catch (Exception e) {
        }
        return 1;
    }

    // 输入GregorianCalendar取月
    public static int getDay(GregorianCalendar gc) {
        return gc.get(Calendar.DATE);
    }

    // 输入Date取月
    public static int getDay(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return gc.get(Calendar.DATE);
    }

    // 输入字符串取月
    public static int getDay(String date) {
        date = formatDate(date, "");
        GregorianCalendar gc = stringToGCalendar(date);
        return gc.get(Calendar.DATE);
    }

    // 输入GregorianCalendar取星期
    public static int getDAY_OF_WEEK(GregorianCalendar gc) {
        int weekday = gc.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekday == 0)
            weekday = 7;
        return weekday;
    }

    // 输入Date取星期
    public static int getDAY_OF_WEEK(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        int weekday = gc.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekday == 0)
            weekday = 7;
        return weekday;
    }

    // 输入字符串取星期
    public static int getDAY_OF_WEEK(String date) {
        GregorianCalendar gc = stringToGCalendar(date);
        int weekday = gc.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekday == 0)
            weekday = 7;
        return weekday;
    }

    // 获取日期的全部数字
    public static String getFullTime() {
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        String time = "";
        // time = gc.get(Calendar.YEAR) + "";
        // time = time + gc.get(Calendar.MONTH) + 1 + "";
        time = time + gc.get(Calendar.DATE) + "";
        time = time + gc.get(Calendar.HOUR_OF_DAY) + "";
        time = time + gc.get(Calendar.MINUTE) + "";
        time = time + gc.get(Calendar.SECOND) + "";
        return time;
    }

    public static String getDate(java.sql.Date date) {
        if (date == null) {
            return "";
        }
        return date.toString();
    }

    public static String getDate(Date date) {
        if (date == null) {
            return "";
        }
        return date.toString();
    }

    // 输入GregorianCalendar得到所需格式的日期字符
    public static String formatDate(GregorianCalendar gc, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                formatType);
        String strDate = formatter.format(gc.getTime());
        return strDate;
    }

    // 输入Date得到所需格式的日期字符
    public static String formatDate(Date date, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        String strDate = formatter.format(date);
        return strDate;
    }

    // 输入字符串得到所需格式的日期字符
    public static String formatDate(String dateOfString, String formatType) {

        if (dateOfString == null || dateOfString.length() < 1) {
            return "";
        }
        dateOfString = dateOfString.trim();
        if (dateOfString.indexOf("-") > 0 && dateOfString.indexOf(".") > 0) {
            dateOfString = dateOfString.substring(0, dateOfString.indexOf("."));
        }

        if (formatType == null || formatType.length() < 1) {
            formatType = "yyyy-MM-dd";
        }
        if (dateOfString.length() < 5 && dateOfString.indexOf("-") == -1) {
            dateOfString = dateOfString + "-01-01";
        } else if (dateOfString.length() < 6 && dateOfString.indexOf("-") > -1) {
            dateOfString = dateOfString + "01-01";
        } else if (dateOfString.length() < 8) {
            dateOfString = dateOfString + "-01";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(
                formatType);
        GregorianCalendar gc = stringToGCalendar(dateOfString);
        String strDate = formatter.format(gc.getTime());
        return strDate;
    }

    // 把字符串变为对应的日期类型
    // 把字符串变为对应的日期类型
    public static GregorianCalendar stringToGCalendar(String strInputDate) {
        String formatType = "yyyy-MM-dd";
        String tempInputDate = "";

        tempInputDate = strInputDate;
        if (strInputDate.indexOf(".") >= 0 && strInputDate.length() <= 19) {
            formatType = "yyyy.MM.dd";
        }
        // 判断是哪种格式.
        if (tempInputDate.indexOf(":") >= 0) {
            formatType = formatType + " HH:mm";
        }
        tempInputDate = tempInputDate.substring(tempInputDate.indexOf(":") + 1);
        if (tempInputDate.indexOf(":") >= 0) {
            formatType = formatType + ":ss";
        }

        if (strInputDate.lastIndexOf(".") == 19) {
            formatType = formatType + ".SSS";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        ParsePosition pos = new ParsePosition(0);
        Date sourceDate = formatter.parse(strInputDate, pos);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(sourceDate);
        return gc;
    }

    // 输入该月第一天星期几和该月最大日期，得到其它相对日期星期数
    public String DateToWeek(int w, int maxday, int d) {
        String dateWeek = "";
        if (d < maxday + 1) {
            int dw = (w + d) % 7;

            switch (dw) {
                case 1:
                    dateWeek = "<font color=red>日</font>";
                    break;
                case 2:
                    dateWeek = "一";
                    break;
                case 3:
                    dateWeek = "二";
                    break;
                case 4:
                    dateWeek = "三";
                    break;
                case 5:
                    dateWeek = "四";
                    break;
                case 6:
                    dateWeek = "五";
                    break;
                case 0:
                    dateWeek = "<font color=red>六</font>";
                    break;

            }
        }
        return dateWeek;
    }

    public static String formate(int seconds) {
        long hour = seconds / 3600;
        long minute = seconds % 3600 / 60;
        long second = seconds % 60;
        String b = "";
        String c = "";
        String d = "";
        if (second < 10) {
            b = "0";
        }

        if (minute < 10) {
            c = "0";
        }
        if (hour < 10) {
            d = "0";
        }

        return d + hour + ":" + c + minute + ":" + b + second;
    }

    public static int DateDiffNow(Timestamp t) {
        return DateDiffNow(DateTool.timestampToString(t));
    }

    public static String getNow() {
        return getNow("yyyy-MM-dd");
    }

    public static String getNow(String dateStyle) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(dateStyle);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return bartDateFormat.format(cal.getTime());
    }

    public static int DateDiffNow(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = new GregorianCalendar();
        try {
            gc1.setTime(df.parse(date));
            gc2.setTime(df.parse(getNow()));
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        Date d1 = gc1.getTime();
        Date d2 = gc2.getTime();
        int d = 0;
        try {
            long l = d2.getTime() - d1.getTime();
            d = (int) (l / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
        }
        return d;
    }

    public static long DateDiffNow2(String date) {
        // date1 = formatDate(date1, "yyyy-MM-dd hh:mm:ss");
        // date2 = formatDate(date2, "yyyy-MM-dd hh:mm:ss");
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = new GregorianCalendar();
        try {
            gc1.setTime(df.parse(date));
            gc2.setTime(now);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        Date d1 = gc1.getTime();
        Date d2 = gc2.getTime();
        long d = 0;
        try {
            long l = d1.getTime() - d2.getTime();
            d = (long) l / 1000;
        } catch (Exception e) {
        }
        return d;
    }

    public static int dayForWeek(String pTime) {
        int dayForWeek = 0;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(pTime));

            dayForWeek = c.get(Calendar.DAY_OF_WEEK);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayForWeek;
    }

    public static String getWeekStr(int val) {
        String retStr = "";
        switch (val) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                break;
        }
        return retStr;
    }

    public static String getNowWeek() {
        return getWeekStr(DateTool.dayForWeek(getNow()));
    }

    public static long DateDiffMillisecond(String date1, String date2) {
        // Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = new GregorianCalendar();
        try {
            gc1.setTime(df.parse(date1));
            gc2.setTime(df.parse(date2));
        } catch (ParseException e1) {

        }
        Date d1 = gc1.getTime();
        Date d2 = gc2.getTime();
        long l = (d2.getTime() - d1.getTime());
        return l;
    }

    public static long DateDiffSecond(String date1, String date2) {
        long diff = DateDiffMillisecond(date1, date2);
        return diff / 1000;
    }

    public static long DateDiffDay(String date1, String date2) {
        long diff = DateDiffMillisecond(date1, date2);
        return diff / (1000 * 60 * 60 * 24);
    }


    public static String timestampToString(Timestamp t) {
        return timestampToString(t, "yyyy-MM-dd HH:mm:ss");
    }

    public static String timestampToString(Timestamp t, String pattern) {
        if (t == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(pattern);
        String str = df.format(t);
        return str;
    }

    public static Timestamp stringToTimestamp(String d) {
        return stringToTimestamp(d, "yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp stringToTimestamp(String d, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date date = sdf.parse(d);
            Timestamp t = new Timestamp(date.getTime());
            return t;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }


    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = myFormatter.parse(date1);
            Date mydate = myFormatter.parse(date2);

            long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
            return day;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    // 计算当月最后一天,返回字符串
    public static String geLastDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);//加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 上月第一天
    public static String getPreviousMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);//减一个月，变为下月的1号
        //lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获取当月第一天
    public static String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得本周星期日的日期
    public static String getCurrentWeekday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }


    //获取当天时间
    public static String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    // 获得当前日期与本周日相差的天数
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;        //因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 0) {
            return -6;
        } else if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    //获得本周一的日期
    public static String getMondayOFWeek() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    //获得相应周的周六的日期
    public static String getSaturday() {
        int weeks = 0;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得上周星期日的日期
    public static String getPrevWeekSunday() {
        int weeks = 0;
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得上周星期一的日期
    public static String getPrevWeekday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * -1);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得下周星期一的日期
    public static String getNextMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得下周星期日的日期
    public static String getNextSunday() {

        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }


    public static int getMonthPlus() {
        Calendar cd = Calendar.getInstance();
        int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
        cd.set(Calendar.DATE, 1);//把日期设置为当月第一天
        cd.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        MaxDate = cd.get(Calendar.DATE);
        if (monthOfNumber == 1) {
            return -MaxDate;
        } else {
            return 1 - monthOfNumber;
        }
    }

    //获得上月最后一天的日期
    public static String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);//减一个月
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获得下个月第一天的日期
    public static String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);//减一个月
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获得下个月最后一天的日期
    public static String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);//加一个月
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获得明年最后一天的日期
    public static String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);//加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获得明年第一天的日期
    public static String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);//加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;

    }

    //获得本年有多少天
    public static int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    public static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    //获得本年第一天的日期
    public static String getCurrentYearFirst() {
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }


    //获得本年最后一天的日期 *
    public static String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式
        String years = dateFormat.format(date);
        return years + "-12-31";
    }


    //获得上年第一天的日期 *
    public static String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "-1-1";
    }

    //获得上年最后一天的日期
    public static String getPreviousYearEnd() {
        int weeks = 0;
        weeks--;
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        getThisSeasonTime(11);
        return preYearDay;
    }

    //获得本季度
    public static String getThisSeasonTime(int month) {
        int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month + "-" + end_days;
        return seasonDate;

    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year  年
     * @param month 月
     * @return 最后一天
     */
    public static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 是否为今天
     *
     * @param ts
     * @return
     */
    public static boolean isToday(long ts) {
//		Date today=new Date(); 
//		today.setHours(23); 
//		today.setMinutes(59); 
//		today.setSeconds(59); 
        //Calendar.set(Calendar.HOUR_OF_DAY, int hours)

        GregorianCalendar today = new GregorianCalendar();
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);

        long diff = today.getTime().getTime() - ts;
        if (diff < 0) diff = 0;
        long days = diff / (1000 * 60 * 60 * 24);

        if (days == 0) return true;

        return false;
    }

    public static void main(String args[]) {
        /*
        long leveaingSencond = DateTool.DateDiffNow2("2007-12-16 19:00:00");
		System.out.println(leveaingSencond);
		DateTool.dateAdd(GregorianCalendar.DATE, 1, "2010-10-12");
		int dateInterval = 1;
		System.out.println(DateTool.dateAdd(-(dateInterval - 1),StrUtils.getNow()));
		System.out.println(DateTool.dateAdd(-10, StrUtils.getNow()));
		String str = "2010-10-14 19:13:00";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		try {
			d = df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// System.out.println(DateTool.minuteAdd(-10,
		// StrUtils.getNow("yyyy-MM-dd HH:mm:ss")));

		// System.out.println(DateTool.formatDate("2012-02-20 23:12:13",
		// "HH:mm:ss"));


		System.out.println(DateTool.DateDiffMillisecond(StrUtils.getNow("yyyy-MM-dd HH:mm:ss"), ""));

		System.out.println(DateTool.hourAdd(10 , StrUtils.getNow("yyyy-MM-dd HH:mm:ss")));
		System.out.println(Integer.MAX_VALUE);
		
		
		System.out.println(getFirstDayOfMonth());
		
		//System.out.println();

		System.out.println(DateTool.dateAdd(((1+6)-DateTool.dayForWeek(StrUtils.getNow())),StrUtils.getNow()));
		
		System.out.println(DateTool.DateDiffDay("2013-02-28 18:32:14" , StrUtils.getNow()));
		

		System.out.println(DateTool.DateDiffSecond(StrUtils.getNow("yyyy-MM-dd HH:mm:ss"), "2013-02-29 18:32:14"));
		
		Random random = new Random();
		for(int i = 0 ; i < 10 ; i++) {
			System.out.println( random.nextInt(1 + 1) + 3 - 1);
		}
		*/
        System.out.println("=======" + DateTool.DateDiffNow("2013-08-19 16:41:51"));

        System.out.println(System.currentTimeMillis() / 1000);

        System.err.println(getNowTime("HH:mm:ss"));

        DateTool.dateAdd(GregorianCalendar.DATE, 1, "2010-10-12 11:36:48.132");

        System.out.println(DateTool.stringToTimestamp("2013-07-27 11:36:48"));
        System.out.println(DateTool.stringToTimestamp("2013-07-27 11:36:48", "yyyy-MM-dd HH:mm:ss"));
        System.out.println("判断是否为当天:" + DateTool.isToday(System.currentTimeMillis() - (1000 * 60 * 60 * 11)));
        System.out.println("获取当天日期:" + DateTool.getNowTime("yyyy-MM-dd"));
        System.out.println("获取本周一日期:" + DateTool.getMondayOFWeek());
        System.out.println("获取本周日的日期~:" + DateTool.getCurrentWeekday());
        System.out.println("获取上周一日期:" + DateTool.getPrevWeekday());
        System.out.println("获取上周日日期:" + DateTool.getPrevWeekSunday());
        System.out.println("获取下周一日期:" + DateTool.getNextMonday());
        System.out.println("获取下周日日期:" + DateTool.getNextSunday());
        System.out.println("获得相应周的周六的日期:" + DateTool.getNowTime("yyyy-MM-dd"));
        System.out.println("获取本月第一天日期:" + DateTool.getFirstDayOfMonth());
        System.out.println("获取本月最后一天日期:" + DateTool.geLastDayOfMonth());
        System.out.println("获取上月第一天日期:" + DateTool.getPreviousMonthFirst());
        System.out.println("获取上月最后一天的日期:" + DateTool.getPreviousMonthEnd());
        System.out.println("获取下月第一天日期:" + DateTool.getNextMonthFirst());
        System.out.println("获取下月最后一天日期:" + DateTool.getNextMonthEnd());
        System.out.println("获取本年的第一天日期:" + DateTool.getCurrentYearFirst());
        System.out.println("获取本年最后一天日期:" + DateTool.getCurrentYearEnd());
        System.out.println("获取去年的第一天日期:" + DateTool.getPreviousYearFirst());
        System.out.println("获取去年的最后一天日期:" + DateTool.getPreviousYearEnd());
        System.out.println("获取明年第一天日期:	" + DateTool.getNextYearFirst());
        System.out.println("获取明年最后一天日期:		" + DateTool.getNextYearEnd());
        System.out.println("获取本季度第一天到最后一天:	" + DateTool.getThisSeasonTime(11));
        System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:" + DateTool.getTwoDay("2008-12-1", "2008-9-29"));


        System.out.println(getMondayOFWeek());
    }
}