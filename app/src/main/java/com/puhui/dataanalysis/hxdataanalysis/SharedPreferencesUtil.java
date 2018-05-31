package com.puhui.dataanalysis.hxdataanalysis;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chenb on 2018/5/24.
 */

public class SharedPreferencesUtil {
    public SharedPreferencesUtil() {
    }

    public static void putLong(Context var0, String var1, String var2, long var3) {
        try {
            SharedPreferences var5 = var0.getSharedPreferences(var1, 0);
            var5.edit().putLong(var2, var3).commit();
        } catch (Throwable var6) {
            ;
        }

    }

    public static void putString(Context var0, String var1, String var2, String var3) {
        try {
            SharedPreferences var4 = var0.getSharedPreferences(var1, 0);
            var4.edit().putString(var2, var3).commit();
        } catch (Throwable var5) {
            ;
        }

    }

    public static long getLong(Context var0, String var1, String var2, long var3) {
        try {
            SharedPreferences var5 = var0.getSharedPreferences(var1, 0);
            return var5.getLong(var2, var3);
        } catch (Throwable var6) {
            return var3;
        }
    }

    public static String getString(Context var0, String var1, String var2, String var3) {
        try {
            SharedPreferences var4 = var0.getSharedPreferences(var1, 0);
            return var4.getString(var2, var3);
        } catch (Throwable var5) {
            return var3;
        }
    }
}
