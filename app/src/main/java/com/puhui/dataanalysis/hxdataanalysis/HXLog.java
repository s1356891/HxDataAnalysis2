package com.puhui.dataanalysis.hxdataanalysis;

import android.util.Log;

/**
 * Created by chenb on 2018/5/21.
 */

public class HXLog {
    public static boolean a = true;
    public static void iForDeveloper(String var0) {
        if(a) {
            a(var0, 4);
        }
    }

    public static void dForDeveloper(String var0) {
        if(a) {
            a(var0, 3);
        }

    }

    public static void eForDeveloper(String var0) {
        if(a) {
            a(var0, 6);
        }

    }

    public static void a(String var0, Throwable var1) {
        if(a) {
            a(var0, 4);
            var1.printStackTrace();
        }

    }


    private static void a(String var0, int var1) {
        if(var0 != null) {
            try {
                int var2 = var0.length();
                int var3 = 0;
                int var4 = 2000;

                for(int var5 = 0; var5 < 100; ++var5) {
                    if(var2 <= var4) {
                        b(var0.substring(var3, var2), var1);
                        break;
                    }

                    b(var0.substring(var3, var4), var1);
                    var3 = var4;
                    var4 += 2000;
                }
            } catch (Throwable var6) {

            }

        }
    }
    private static void b(String var0, int var1) {
        String var2 = "HXLog";
        switch(var1) {
            case 2:
                Log.v(var2, var0);
                break;
            case 3:
                Log.d(var2, var0);
                break;
            case 4:
                Log.i(var2, var0);
                break;
            case 5:
                Log.w(var2, var0);
                break;
            case 6:
                Log.e(var2, var0);
        }

    }
}
