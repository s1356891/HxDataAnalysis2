package com.puhui.dataanalysis.hxdataanalysis;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by chenb on 2018/5/21.
 */

public class HxUtils {
    public static boolean b = true;
    public static boolean d = false;
    public static boolean e = false;
    public static final boolean isEmpty(String var0) {
        return var0 == null || "".equals(var0.trim());
    }

    public static boolean checkPermission(Context context, String permission) {
        try {
            return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } catch (Throwable throwable) {
            return false;
        }
    }
    public static final String split256(String var0) {
        return var0 == null?null:(var0.length() > 256?var0.substring(0, 256):var0);
    }


    public static boolean checkVersion(int var0) {
        return Build.VERSION.SDK_INT >= var0;
    }

}
