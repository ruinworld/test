package com.core.extend.b;

public class Log {
    private static final String TAG = "Log";
    private static boolean sDebug = BuildConfig.DEBUG;
    private static boolean sFileLog = false;

    private Log() {
    }

    public static void setDebugLog(boolean isDebug) {
        sDebug = isDebug;
    }

    public static void setsFileLog(boolean isDebug) {
        sFileLog = isDebug;
    }

    private static boolean isDebug() {
        return sDebug;
    }

    private static boolean isFileLog() {
        return sFileLog;
    }

    private static boolean isLoggable(int i) {
        return isDebug();
    }

    private static boolean isLoggable() {
        return isDebug();
    }

    private static String levelToStr(int level) {
        switch (level) {
            case 2:
                return "V";
            case 3:
                return "D";
            case 4:
                return "I";
            case 5:
                return "W";
            case 6:
                return "E";
            case 7:
                return "A";
            default:
                return "UNKNOWN";
        }
    }

    private static String getProcessName() {
        return "?";
    }

    public static void v(String tag, String format, Object... args) {
        v(tag, format, (Throwable) null, args);
    }

    public static void v(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable(2)) {
            if (tr == null) {
                android.util.Log.v(tag, String.format(format, args));
            } else {
                android.util.Log.v(tag, String.format(format, args), tr);
            }
        }
    }

    public static void d(String tag, String format, Object... args) {
        d(tag, format, (Throwable) null, args);
    }

    public static void d(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable(3)) {
            if (tr == null) {
                android.util.Log.d(tag, String.format(format, args));
            } else {
                android.util.Log.d(tag, String.format(format, args), tr);
            }
        }
    }

    public static void i(String tag, String format, Object... args) {
        i(tag, format, (Throwable) null, args);
    }

    public static void i(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable(4)) {
            if (tr == null) {
                android.util.Log.i(tag, String.format(format, args));
            } else {
                android.util.Log.i(tag, String.format(format, args), tr);
            }
        }
    }

    public static void w(String tag, String format, Object... args) {
        w(tag, format, (Throwable) null, args);
    }

    public static void w(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable(5)) {
            if (tr == null) {
                android.util.Log.w(tag, String.format(format, args));
            } else {
                android.util.Log.w(tag, String.format(format, args), tr);
            }
        }
    }

    public static void w(String tag, Throwable tr) {
        w(tag, "Log.warn", tr, new Object[0]);
    }

    public static void e(String tag, String format, Object... args) {
        e(tag, format, (Throwable) null, args);
    }

    public static void e(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable(6)) {
            if (tr == null) {
                android.util.Log.e(tag, String.format(format, args));
            } else {
                android.util.Log.e(tag, String.format(format, args), tr);
            }
        }
    }

    public static void wtf(String tag, String format, Object... args) {
        wtf(tag, format, (Throwable) null, args);
    }

    public static void wtf(String tag, Throwable tr) {
        wtf(tag, "wtf", tr, new Object[0]);
    }

    public static void wtf(String tag, String format, Throwable tr, Object... args) {
        if (isLoggable()) {
            if (tr == null) {
                android.util.Log.wtf(tag, String.format(format, args));
            } else {
                android.util.Log.wtf(tag, String.format(format, args), tr);
            }
        }
    }
}
