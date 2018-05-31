package com.puhui.dataanalysis.hxdataanalysis;

import android.content.SharedPreferences;

/**
 * Created by chenb on 2018/5/24.
 */

public class SharedPreferencesManager {
    public static final String a = "TDpref.profile.key";
    public static final String b = "TDpref.session.key";
    public static final String c = "TDpref.lastactivity.key";
    public static final String d = "TDpref.start.key";
    public static final String e = "TDpref.init.key";
    public static final String f = "TDpref.actstart.key";
    public static final String g = "TDpref.end.key";
    public static final String h = "TDpref.ip";
    public static final String i = "TD_CHANNEL_ID";
    public static final String j = "TDappcontext_push";
    public static final String k = "TDpref.tokensync.key";
    public static final String l = "TDpref.push.msgid.key";
    public static final String m = "TDpref_longtime";
    public static final String n = "TDpref_shorttime";
    public static final String o = "TDaes_key";
    public static final String p = "TDpref_game";
    public static final String q = "TD_push_pref_file";
    static final String r = "TDisAppQuiting";
    public static final String s = "TDpref.last.sdk.check";
    public static final String t = "TDadditionalVersionName";
    public static final String u = "TDadditionalVersionCode";
    private static final String x = "TDtime_set_collect_net";
    private static final String y = "TDdeep_link_url";
    private static final String z = "TDtd_role_id";
    private static final String A = "TDpref.accountid.key";
    private static final String B = "TDpref.accountgame.key";
    private static final String C = "TDpref.missionid.key";
    private static final String D = "TDpref.activesessionid.key";
    private static final String E = "TDpref.game.session.start.key";
    private static final String F = "TDpref.game.session.end.key";
    private static final String G = "TDpref.game.session.startsystem.key";
    public static final long v = 868L;
    public static final long w = 0L;

    public SharedPreferencesManager() {
    }

    public static String a() {
        if(HXDataConfig.context == null) {
            return null;
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_longtime", "TDaes_key", (String)null);
            } catch (Throwable var1) {
                return null;
            }
        }
    }

    public static void setAESKey(String var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_longtime", "TDaes_key", var0);
            } catch (Throwable var2) {
            }

        }
    }

    public static String a(HostService var0) {
        if(HXDataConfig.context != null && var0 != null) {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_longtime" + var0.getApiType(), "TDpref.session.key", (String)null);
            } catch (Throwable var2) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void a(String var0, HostService var1) {
        if(HXDataConfig.context != null && var1 != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_longtime" + var1.getApiType(), "TDpref.session.key", var0);
            } catch (Throwable var3) {
            }

        }
    }

    public static void b() {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferences var0 = HXDataConfig.context.getSharedPreferences("TD_CHANNEL_ID", 0);
                var0.edit().putBoolean("location_called", true).commit();
            } catch (Throwable var1) {
            }

        }
    }

    public static boolean c() {
        if(HXDataConfig.context == null) {
            return false;
        } else {
            try {
                SharedPreferences var0 = HXDataConfig.context.getSharedPreferences("TD_CHANNEL_ID", 0);
                return var0.getBoolean("location_called", false);
            } catch (Throwable var1) {
                return false;
            }
        }
    }

    public static void setLastActivity(String var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_shorttime", "TDpref.lastactivity.key", var0);
            } catch (Throwable var2) {
                ;
            }

        }
    }

    public static String d() {
        if(HXDataConfig.context == null) {
            return "";
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_shorttime", "TDpref.lastactivity.key", "");
            } catch (Throwable var1) {
                return "";
            }
        }
    }

    public static long b(HostService var0) {
        if(HXDataConfig.context != null && var0 != null) {
            try {
                return SharedPreferencesUtil.getLong(HXDataConfig.context, "TDpref_longtime" + var0.getApiType(), "TDpref.start.key", 0L);
            } catch (Throwable var2) {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    public static void a(long var0, HostService var2) {
        if(HXDataConfig.context != null && var2 != null) {
            try {
                SharedPreferencesUtil.putLong(HXDataConfig.context, "TDpref_longtime" + var2.getApiType(), "TDpref.start.key", var0);
            } catch (Throwable var4) {
                ;
            }

        }
    }

    public static void b(long var0, HostService var2) {
        if(HXDataConfig.context != null && var2 != null) {
            try {
                SharedPreferencesUtil.putLong(HXDataConfig.context, "TDpref_longtime" + var2.getApiType(), "TDpref.init.key", var0);
            } catch (Throwable var4) {
                ;
            }

        }
    }

    public static long c(HostService var0) {
        if(HXDataConfig.context != null && var0 != null) {
            try {
                return SharedPreferencesUtil.getLong(HXDataConfig.context, "TDpref_longtime" + var0.getApiType(), "TDpref.init.key", 0L);
            } catch (Throwable var2) {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    public static void setInitTime(long var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putLong(HXDataConfig.context, "TDpref_longtime", "TDpref.init.key", var0);
            } catch (Throwable var3) {
                ;
            }

        }
    }

    public static long e() {
        if(HXDataConfig.context == null) {
            return 0L;
        } else {
            try {
                return SharedPreferencesUtil.getLong(HXDataConfig.context, "TDpref_longtime", "TDpref.init.key", 0L);
            } catch (Throwable var1) {
                return 0L;
            }
        }
    }

    public static void setActivityStartTime(long var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putLong(HXDataConfig.context, "TDpref_shorttime", "TDpref.actstart.key", var0);
            } catch (Throwable var3) {
                ;
            }

        }
    }

    public static long f() {
        if(HXDataConfig.context == null) {
            return 0L;
        } else {
            try {
                return SharedPreferencesUtil.getLong(HXDataConfig.context, "TDpref_shorttime", "TDpref.actstart.key", 0L);
            } catch (Throwable var1) {
                return 0L;
            }
        }
    }

    public static long d(HostService var0) {
        if(HXDataConfig.context != null && var0 != null) {
            try {
                return SharedPreferencesUtil.getLong(HXDataConfig.context, "TDpref_shorttime" + var0.getApiType(), "TDpref.end.key", 0L);
            } catch (Throwable var2) {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    public static void c(long var0, HostService var2) {
        if(HXDataConfig.context != null && var2 != null) {
            try {
                SharedPreferencesUtil.putLong(HXDataConfig.context, "TDpref_shorttime" + var2.getApiType(), "TDpref.end.key", var0);
            } catch (Throwable var4) {
                ;
            }

        }
    }

    public static void setPostProfile(boolean var0) {
        try {
            SharedPreferencesUtil.putLong(HXDataConfig.context, "TDpref_longtime", "TDpref.profile.key", var0?1L:0L);
        } catch (Throwable var2) {
            ;
        }

    }

    public static void b(String var0, HostService var1) {
        if(HXDataConfig.context != null && var1 != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_longtime" + var1.getApiType(), "TDisAppQuiting", var0);
            } catch (Throwable var3) {
                ;
            }

        }
    }

    public static String e(HostService var0) {
        if(HXDataConfig.context != null && var0 != null) {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_longtime" + var0.getApiType(), "TDisAppQuiting", "-1");
            } catch (Throwable var2) {
                return "-1";
            }
        } else {
            return "-1";
        }
    }

    public static void setAdditionalVersionName(String var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_longtime", "TDadditionalVersionName", var0);
            } catch (Throwable var2) {
                ;
            }

        }
    }

    public static String g() {
        if(HXDataConfig.context == null) {
            return null;
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_longtime", "TDadditionalVersionName", (String)null);
            } catch (Throwable var1) {
                return null;
            }
        }
    }

    public static void setAdditionalVersionCode(long var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putLong(HXDataConfig.context, "TDpref_longtime", "TDadditionalVersionCode", var0);
            } catch (Throwable var3) {
                ;
            }

        }
    }

    public static long h() {
        if(HXDataConfig.context == null) {
            return -1L;
        } else {
            try {
                return SharedPreferencesUtil.getLong(HXDataConfig.context, "TDpref_longtime", "TDadditionalVersionCode", -1L);
            } catch (Throwable var1) {
                return -1L;
            }
        }
    }

    public static int getVersionCode() {
        try {
            return h() != -1L?Integer.parseInt(String.valueOf(h())):PackageInfo.getInstance().getVersionCode(HXDataConfig.context);
        } catch (Throwable var1) {
            return -1;
        }
    }

    public static String getVersionName() {
        try {
            return g() != null?g():PackageInfo.getInstance().getVersionName(HXDataConfig.context);
        } catch (Throwable var1) {
            return "unknown";
        }
    }

    public static void a(String var0, String var1) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_shorttime", var0, var1);
            } catch (Throwable var3) {
                ;
            }

        }
    }

    public static String a(String var0) {
        if(HXDataConfig.context == null) {
            return null;
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_shorttime", var0, (String)null);
            } catch (Throwable var2) {
                return null;
            }
        }
    }

    public static void b(String var0, String var1) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_shorttime", var0, var1);
            } catch (Throwable var3) {
                ;
            }

        }
    }

    public static String b(String var0) {
        if(HXDataConfig.context == null) {
            return null;
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_shorttime", var0, (String)null);
            } catch (Throwable var2) {
                return null;
            }
        }
    }

    public static void setLastRoleName(String var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_shorttime", "TDtd_role_id", var0);
            } catch (Throwable var2) {
                ;
            }

        }
    }

    public static String k() {
        if(HXDataConfig.context == null) {
            return null;
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_shorttime", "TDtd_role_id", (String)null);
            } catch (Throwable var1) {
                return null;
            }
        }
    }

    public static long l() {
        if(HXDataConfig.context == null) {
            return 0L;
        } else {
            try {
                return SharedPreferencesUtil.getLong(HXDataConfig.context, "TDpref_shorttime", "TDtime_set_collect_net", 0L);
            } catch (Throwable var1) {
                return 0L;
            }
        }
    }

    public static void setCollectNetInfoTime(long var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putLong(HXDataConfig.context, "TDpref_shorttime", "TDtime_set_collect_net", var0);
            } catch (Throwable var3) {
                ;
            }

        }
    }

    public static String m() {
        if(HXDataConfig.context == null) {
            return null;
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_shorttime", "TDdeep_link_url", (String)null);
            } catch (Throwable var1) {
                return null;
            }
        }
    }

    public static void setDeepLink(String var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_shorttime", "TDdeep_link_url", var0);
            } catch (Throwable var2) {
                ;
            }

        }
    }

    public static String n() {
        if(HXDataConfig.context == null) {
            return "";
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_game", "TDpref.accountid.key", "");
            } catch (Throwable var1) {
                return "";
            }
        }
    }

    public static void setAccountId(String var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_game", "TDpref.accountid.key", var0);
            } catch (Throwable var2) {
                ;
            }

        }
    }

    public static String c(String var0) {
        if(HXDataConfig.context == null) {
            return "";
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_game", var0 + "TDpref.accountgame.key", "");
            } catch (Throwable var2) {
                return "";
            }
        }
    }

    public static void c(String var0, String var1) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_game", var0 + "TDpref.accountgame.key", var1);
            } catch (Throwable var3) {
                ;
            }

        }
    }

    public static void setMissionId(String var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TDpref_game", "TDpref.missionid.key", var0);
            } catch (Throwable var2) {
                ;
            }

        }
    }

    public static String o() {
        if(HXDataConfig.context == null) {
            return "";
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TDpref_game", "TDpref.missionid.key", "");
            } catch (Throwable var1) {
                return "";
            }
        }
    }

    public static void p() {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putLong(HXDataConfig.context, "TDpref_game", "TDpref.game.session.startsystem.key", System.currentTimeMillis());
            } catch (Throwable var1) {
                ;
            }

        }
    }

    public static void setPushAppContext(String var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TD_push_pref_file", "TDappcontext_push", var0);
            } catch (Throwable var2) {
                ;
            }

        }
    }

    public static String q() {
        if(HXDataConfig.context == null) {
            return "";
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TD_push_pref_file", "TDappcontext_push", "");
            } catch (Throwable var1) {
                return "";
            }
        }
    }

    public static void setPushSyncTokenLastTime(long var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putLong(HXDataConfig.context, "TD_push_pref_file", "TDpref.tokensync.key", var0);
            } catch (Throwable var3) {
                ;
            }

        }
    }

    public static long r() {
        if(HXDataConfig.context == null) {
            return 0L;
        } else {
            try {
                return SharedPreferencesUtil.getLong(HXDataConfig.context, "TD_push_pref_file", "TDpref.tokensync.key", 0L);
            } catch (Throwable var1) {
                return 0L;
            }
        }
    }

    public static void setPushLastMsgId(String var0) {
        if(HXDataConfig.context != null) {
            try {
                SharedPreferencesUtil.putString(HXDataConfig.context, "TD_push_pref_file", "TDpref.push.msgid.key", var0);
            } catch (Throwable var2) {
                ;
            }

        }
    }

    public static String s() {
        if(HXDataConfig.context == null) {
            return "";
        } else {
            try {
                return SharedPreferencesUtil.getString(HXDataConfig.context, "TD_push_pref_file", "TDpref.push.msgid.key", "");
            } catch (Throwable var1) {
                return "";
            }
        }
    }
}
