package com.puhui.dataanalysis.hxdataanalysis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.SystemClock;
import android.text.TextUtils;

import java.net.Socket;

/**
 * Created by chenb on 2018/5/28.
 */

public class NetworkUtil {
    static long f = -300000L;
    static boolean d = false;
    public static boolean isNetWork(Context var0) {
        try {
            if(HxUtils.checkPermission(var0, "android.permission.ACCESS_NETWORK_STATE")) {
                ConnectivityManager var1 = (ConnectivityManager)var0.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo var2 = var1.getActiveNetworkInfo();
                return var2 != null && 1 == var2.getType() && var2.isConnected();
            }
        } catch (Throwable var3) {
            ;
        }

        return false;
    }

    public static boolean isConnected(Context var0) {
        try {
            boolean var1 = true;
            if(HxUtils.checkPermission(var0, "android.permission.ACCESS_NETWORK_STATE")) {
                var1 = false;
                ConnectivityManager var2 = (ConnectivityManager)var0.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo var3 = var2.getActiveNetworkInfo();
                if(var3 != null) {
                    return var3.isConnected();
                }

                NetworkInfo var4 = var2.getNetworkInfo(0);
                if(var4 == null || !var4.getState().equals(NetworkInfo.State.UNKNOWN)) {
                    return false;
                }

                var1 = true;
            }

            if(var1 && SystemClock.elapsedRealtime() - f > 300000L) {
                f = SystemClock.elapsedRealtime();
                Socket var17 = null;

                try {
                    if(isProxy()) {
                        var17 = new Socket(Proxy.getDefaultHost(), Proxy.getDefaultPort());
                    } else {
                        var17 = new Socket("data.hxjf.com", 80);
                    }

                    d = true;
                } catch (Throwable var14) {
                    d = false;
                } finally {
                    if(var17 != null) {
                        try {
                            var17.close();
                        } catch (Throwable var13) {
                            ;
                        }
                    }

                }
            }
        } catch (Throwable var16) {
        }

        return d;
    }

    public static boolean isProxy() {
        try {
            if(HxUtils.checkVersion(11)) {
                String var0 = System.getProperty("http.proxyHost");
                return !TextUtils.isEmpty(var0);
            } else {
                return !TextUtils.isEmpty(Proxy.getDefaultHost());
            }
        } catch (Throwable var1) {
            return false;
        }
    }
}
