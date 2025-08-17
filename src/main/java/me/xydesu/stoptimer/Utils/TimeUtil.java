package me.xydesu.stoptimer.Utils;

public class TimeUtil {
    public static String formatTime(long seconds) {
        long h = seconds / 3600;
        long m = (seconds % 3600) / 60;
        long s = seconds % 60;

        if (h > 0) return h + "小時 " + m + "分鐘 " + s + "秒";
        if (m > 0) return m + "分鐘 " + s + "秒";
        return s + "秒";
    }
}