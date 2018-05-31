package com.puhui.dataanalysis.hxdataanalysis;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by chenb on 2018/5/22.
 */

public class SdkInit {
    private static volatile SdkInit sdkInit;
    private static boolean isInitialed;

    public final void onHXDataInitEvent(EventManager.EventParams eventParams) {
        HXLog.eForDeveloper("onHXDataInitEvent");
        if (Integer.parseInt(String.valueOf(eventParams.map.get("apiType"))) != 1) {
            return;
        }
        try {
            String action = String.valueOf(eventParams.map.get("action"));
            HostService hostService = (HostService) eventParams.map.get("service");
            if (action.equals("init")) {
                Context context = HXDataConfig.context;
                String appId = String.valueOf(eventParams.map.get("appId"));
                String channelId = String.valueOf(eventParams.map.get("channelId"));

                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                Bundle bundle = applicationInfo.metaData;
                String appIdMeta = getMetaInfo(bundle, "HX_APP_ID");
                String channelIdMeta = getMetaInfo(bundle, "TD_CHANNEL_ID");
                String finalAppId = HxUtils.isEmpty(appIdMeta) ? appId : appIdMeta;
                String finalChannelId = HxUtils.isEmpty(channelIdMeta) ? channelIdMeta : channelId;
                if (HxUtils.isEmpty(finalAppId)) {
                    HXLog.eForDeveloper("[SDKInit] HX AppId is null");
                    return;
                }

                if (hostService.getType().equals("APP")) {
                    logInit(context, hostService);
                }
                OnEventManager.getInstance();
                sendInitEventWithFeatures(hostService);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void sendInitEventWithFeatures(HostService hostService) {
        try {
            if (hostService == null) {
                HXLog.eForDeveloper("HostParams is null...");
                return;
            }
            TreeMap treeMap = new TreeMap();
            boolean flag = System.currentTimeMillis() - SharedPreferencesManager.c(hostService) <= 5000L;
            treeMap.put("first", Boolean.valueOf(flag));
            DataStoreObj dataStoreObj = new DataStoreObj();
            dataStoreObj.eventType = "app";
            dataStoreObj.eventId = "init";
            dataStoreObj.map = treeMap;
            dataStoreObj.hostService = hostService;
            RegisterManager.getInstance().post(dataStoreObj);
            RequestObj requestObj = new RequestObj();
            requestObj.hostService = hostService;
            requestObj.b = RequestObj.a.a;
            RegisterManager.getInstance().post(requestObj);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void logInit(Context var1, HostService var2) {
        long var3 = System.currentTimeMillis();
        if(!isInitialed) {
            try {
                String var5 = "TalkingData App Analytics SDK init...\n\tSDK_VERSION is: Android+TD+V4.0.7 gp Type:" +"native" + "\n\tApp ID is: " + "ab.EventParams(var1, var2)" + "\n\tApp Channel is: " + "ab.jsonObject(var1, var2)" + "\n\tHXData is: " + "HXData+Android:611396c309c2d4f8613caadffccc04ab+HxOfficial";
//                if(ab.jsonObject || s.EventParams) {
                    HXLog.eForDeveloper(var5);
//                }

                isInitialed = true;
            } catch (Throwable throwable) {
                HXLog.eForDeveloper("[SDKInit] Failed to initialize!");
            }
        }

    }

    private static String getMetaInfo(Bundle var0, String var1) {
        try {
            if (var0 != null) {
                Iterator var2 = var0.keySet().iterator();

                while (var2.hasNext()) {
                    String var3 = (String) var2.next();
                    if (var3.equalsIgnoreCase(var1)) {
                        return String.valueOf(var0.get(var1));
                    }
                }
            }
        } catch (Throwable var4) {
            ;
        }

        return null;
    }


    public static SdkInit getInstance() {
        if (sdkInit == null) {
            synchronized (SdkInit.class) {
                if (sdkInit == null) {
                    sdkInit = new SdkInit();
                }
            }
        }
        return sdkInit;
    }

    private SdkInit() {

    }

    static {
        try {
            RegisterManager.getInstance().register(getInstance());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
