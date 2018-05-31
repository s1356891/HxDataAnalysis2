package com.puhui.dataanalysis.hxdataanalysis;

import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by chenb on 2018/5/24.
 */

public class SessionObj extends JsonObjectOpt {
    static SessionObj sessionObj;

    private SessionObj() {
    }

    public static synchronized SessionObj getInstance() {
        if(sessionObj == null) {
            sessionObj = new SessionObj();
        }

        return sessionObj;
    }

    public void setSessionId(String var1) {
        this.setJson("sessionId", var1);
    }

    public void setCurrentPageName(String var1) {
        this.setJson("page", var1);
    }

    public void setAccount(JSONObject var1) {
        this.setJson("account", var1);
    }

    public void setSubaccount(JSONObject var1) {
        this.setJson("subaccount", var1);
    }

    public void setDeepLink(String var1) {
        try {
            String var2 = URLEncoder.encode(var1, "utf-8");
            this.setJson("deeplink", var2);
            SharedPreferencesManager.setDeepLink(var2);
        } catch (Throwable var3) {
        }

    }

    public void setSessionStartTime(long var1) {
        this.setJson("sessionStartTime", Long.valueOf(var1));
    }

    public void setPushInfo(String var1) {
        this.setJson("push", var1);
    }

    public void setAntiCheatingstatus(int var1) {
        this.setJson("antiCheating", Integer.valueOf(var1));
    }
}
