package xyz.appint.union.utils;

import com.google.common.base.Defaults;

import java.sql.Timestamp;

/**
 * Created by Justin on 2014/9/16.
 */
public class TimestampUtil {
    public static Timestamp toTimestamp(long ts) {
        if (ts == 0) {
//            return new Timestamp(0);
            return null;
        }
        if (String.valueOf(ts).length() == 10) {
            ts = ts * 1000;
        }
        return new Timestamp(ts);
    }


    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static void main(String args[]) {

        now();

        System.err.println(Defaults.defaultValue(Timestamp.class));

        System.err.println(TimestampUtil.toTimestamp(0));

    }


    public static long getTimeBefore(int hours) {

        long now = hours * 60 * 60 * 1000;
        long before = now - 2 * 60 * 1000;

        return before;

    }

    public static long getTimeAfter(int hours) {
        long now = hours * 60 * 60 * 1000;
        long after = now + 2 * 60 * 1000;
        return after;
    }


}
