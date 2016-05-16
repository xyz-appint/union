package xyz.appint.union.utils;

import java.util.Date;

public class ConstellationUtils {

    public static String getConstellation(Date date) {
        return getConstellation(DateTool.getMonth(date), DateTool.getDay(date));
    }


    public static String getConstellation(String date) {
        return getConstellation(DateTool.getMonth(date), DateTool.getDay(date));
    }

    /**
     * 根据传入天数和月份计算星座
     *
     * @param month
     * @param day
     * @return
     */
    public static String getConstellation(int month, int day) {
        String constellation = "";
        String error = "invalid day!";
        if (day > 31 || day < 1) {
            return error;
        } else {
            switch (month) {
                case 1:
                    if (day < 20)
                        constellation = "摩羯座";
                    else
                        constellation = "水瓶座";
                    break;
                case 2:
                    if (day < 20)
                        constellation = "水瓶座";
                    else
                        constellation = "双鱼座";
                    break;
                case 3:
                    if (day < 21)
                        constellation = "双鱼座";
                    else
                        constellation = "白羊座";
                    break;
                case 4:
                    if (day < 21)
                        constellation = "白羊座";
                    else
                        constellation = "金牛座";
                    break;
                case 5:
                    if (day < 21)
                        constellation = "金牛座";
                    else
                        constellation = "双子座";
                    break;
                case 6:
                    if (day < 23)
                        constellation = "双子座";
                    else
                        constellation = "巨蟹座";
                    break;
                case 7:
                    if (day < 23)
                        constellation = "巨蟹座";
                    else
                        constellation = "狮子座";
                    break;
                case 8:
                    if (day < 23)
                        constellation = "狮子座";
                    else
                        constellation = "处女座";
                    break;
                case 9:
                    if (day < 23)
                        constellation = "处女座";
                    else
                        constellation = "天秤座";
                    break;
                case 10:
                    if (day < 23)
                        constellation = "天秤座";
                    else
                        constellation = "天蝎座";
                    break;
                case 11:
                    if (day < 22)
                        constellation = "天蝎座";
                    else
                        constellation = "射手座";
                    break;
                case 12:
                    if (day < 22)
                        constellation = "射手座";
                    else
                        constellation = "摩羯座";
                    break;
            }
        }
        return constellation;
    }

    public static void main(String[] args) {
        int month = 7;
        int day = 31;
        String name = ConstellationUtils.getConstellation("1985-01-01");
        System.out.println("星座：" + name);
    }
}