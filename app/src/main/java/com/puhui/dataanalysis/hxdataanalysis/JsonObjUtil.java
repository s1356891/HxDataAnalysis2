package com.puhui.dataanalysis.hxdataanalysis;

import org.json.JSONObject;

/**
 * Created by chenb on 2018/5/29.
 */

public class JsonObjUtil {
    private static AppJsonObj a = null;
    private static volatile JsonObjUtil e = null;

    public JsonObjUtil() {
    }

    public synchronized JSONObject JsonOpt2JsonObj(DomainJsonObj var1, boolean var2) {
        return this.JsonOpt2JsonObj(var1, var2, (HostService)null);
    }

    public synchronized JSONObject JsonOpt2JsonObj(DomainJsonObj var1, boolean var2, HostService var3) {
        if(var1 != null && var1.getJsonObj() != null) {
            try {
                if(a == null) {
                    a = AppJsonObj.getInstance();
                    String var4 = HXDataConfig.context.getPackageName();
                    String var5 = CerUtils.a(HXDataConfig.context, var4);
                    a.setUniqueId(var5);
                }

                a.setSubmitAppId(var3);
                a.setSubmitChannelId(var3);

            } catch (Throwable var13) {
            }
            JSONObject var15 = new JSONObject();
            try {
                var15.put("version", "2.0");
                var15.put("action",var1.getJsonObj() );
                var15.put("app", a.getJsonObj());
                var15.put("appContext", SessionObj.getInstance().getJsonObj());
                long var16 = System.currentTimeMillis();
                var15.put("ts", var16);
                var15.put("fingerprint", EncryptUtil.md5(var16 + CerUtils.a(HXDataConfig.context) ));
            } catch (Throwable var12) {
            }

            return var15;
        } else {
            return null;
        }
    }

    public static JsonObjUtil getInstance() {
        if(e == null) {
            Class var0 = JsonObjUtil.class;
            synchronized(JsonObjUtil.class) {
                if(e == null) {
                    e = new JsonObjUtil();
                }
            }
        }

        return e;
    }
}
