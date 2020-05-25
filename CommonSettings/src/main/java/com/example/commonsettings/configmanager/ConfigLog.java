package com.example.commonsettings.configmanager;


public class ConfigLog {

    private static final String TAG = "Settings";

    public static void d(String tag, String msg) {

    }

    public static void d(String msg) {
        d("", msg);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(String msg) {
        e("", msg, null);
    }

    public static void e(String tag, String msg, Throwable tr) {

    }

    public static void e(Throwable tr) {
        e("", "", tr);
    }

    public static void eAndPrintStack(String msg) {
        Throwable t = new Throwable();
        t.printStackTrace();
    }
}
