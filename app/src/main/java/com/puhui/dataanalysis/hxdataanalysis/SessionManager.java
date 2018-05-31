package com.puhui.dataanalysis.hxdataanalysis;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by chenb on 2018/5/24.
 */

public class SessionManager {
    private static volatile SessionManager sessionManager = null;
    private static final HandlerThread mHandlerThread = new HandlerThread("PauseEventThread");
    private static Handler mHandler = null;

    public final void onHXDataEventSession(EventManager.EventParams var1) {
        if(var1 != null && var1.map != null) {
            try {
                int var2 = Integer.parseInt(String.valueOf(var1.map.get("apiType")));
                if(var2 == 10) {
                    this.getHandler(var1.map);
                } else if(var2 == 11) {
                    this.c(var1.map);
                }
            } catch (Throwable var3) {
            }

        }
    }

    private void a(HashMap var1) {
        try {
            long var2 = Long.valueOf(String.valueOf(var1.get("occurTime"))).longValue();
            HostService var4 = (HostService) var1.get("service");
            SharedPreferencesManager.c(var2, var4);
        } catch (Throwable var5) {
        }

    }

    private final void getHandler(HashMap var1) {
        try {
            HostService var2 = (HostService) var1.get("service");
            long var3 = Long.valueOf(String.valueOf(var1.get("occurTime"))).longValue();
            long var5 = SharedPreferencesManager.b(var2);
            long var7 = SharedPreferencesManager.d(var2);
            var7 = var7 > var5?var7:var5;
            if(var3 - var7 > HXDataConfig.E) {
                this.a(var2);
                this.a(var3, var2);
                SharedPreferencesManager.setLastActivity("");
            } else {
                HXLog.iForDeveloper("[Session] - Same session as before!");
                String var9 = SharedPreferencesManager.a(var2);
                SessionObj.getInstance().setSessionId(var9);
                SessionObj.getInstance().setSessionStartTime(var5);
            }
        } catch (Throwable var10) {

        }

    }

    private void a(HostService var1) {
        try {
            String var2 = SharedPreferencesManager.a(var1);
            if(var2 != null && !var2.trim().isEmpty()) {
                long var3 = SharedPreferencesManager.b(var1);
                long var5 = SharedPreferencesManager.d(var1);
                long var7 = var5 - var3;
                if(var1.getType().equals("APP") || var1.getType().equals("APP_SQL") || var1.getType().equals("TRACKING")) {
                    var7 = var7 < 500L?-1000L:var7;
                }

                DataStoreObj var9 = new DataStoreObj();
                var9.eventType = "session";
                var9.eventId = "end";
                TreeMap var10 = new TreeMap();
                var10.put("sessionId", var2);
                var10.put("start", Long.valueOf(var3));
                var10.put("duration", Long.valueOf(var7 / 1000L));
                var9.map = var10;
                var9.hostService = var1;
                RegisterManager.getInstance().post(var9);
                this.getHandler(var1);
                SharedPreferencesManager.a((String)null, var1);
            }
        } catch (Throwable var11) {

        }

    }

    private void a(long var1, HostService var3) {
        try {
            HXLog.iForDeveloper("[Session] - New session!");
            String var4 = UUID.randomUUID().toString();
            HXLog.iForDeveloper("[Session] - Id: " + var4);
            long var5 = SharedPreferencesManager.d(var3);
            long var7 = var1 - var5;
            var7 = 0L == var5?0L:var7;
            SharedPreferencesManager.a(var4, var3);
            SharedPreferencesManager.a(var1, var3);
            SessionObj.getInstance().setSessionId(var4);
            SessionObj.getInstance().setSessionStartTime(var1);
            DataStoreObj var9 = new DataStoreObj();
            var9.eventType = "session";
            var9.eventId = "begin";
            TreeMap var10 = new TreeMap();
            var10.put("sessionId", var4);
            var10.put("interval", Long.valueOf(var7 / 1000L));
            var9.map = var10;
            var9.hostService = var3;
            RegisterManager.getInstance().post(var9);
            this.getHandler(var3);
        } catch (Throwable var11) {

        }

    }

    private final void c(HashMap var1) {
        try {
            HostService var2 = (HostService) var1.get("service");
            long var3 = Long.valueOf(String.valueOf(var1.get("occurTime"))).longValue();
            if(var1.containsKey("sessionEnd")) {
                this.a(var2);
                return;
            }

            if(var1.containsKey("pageName")) {
                String var5 = String.valueOf(var1.get("pageName"));
                SharedPreferencesManager.setLastActivity(var5);
            }

            if(var2.getType().equals("GAME")) {
                this.getHandler(var2);
            }

            SharedPreferencesManager.c(var3, var2);
            HXDataConfig.y = null;
        } catch (Throwable var6) {

        }

    }

    private void getHandler(HostService var1) {
        RequestObj var2 = new RequestObj();
        var2.hostService = var1;
        var2.b = RequestObj.a.a;
        RegisterManager.getInstance().post(var2);
    }

    public static SessionManager getInstance() {
        if(sessionManager == null) {
            Class var0 = SessionManager.class;
            synchronized(SessionManager.class) {
                if(sessionManager == null) {
                    sessionManager = new SessionManager();
                }
            }
        }

        return sessionManager;
    }

    private SessionManager() {
    }

    public static Handler getHandler() {
        return mHandler;
    }

    static {
        mHandlerThread.start();
        mHandler = new SessionHandler(mHandlerThread.getLooper());

        try {
            RegisterManager.getInstance().register(getInstance());
        } catch (Throwable var1) {

        }

    }
}
