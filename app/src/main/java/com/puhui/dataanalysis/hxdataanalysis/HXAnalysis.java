package com.puhui.dataanalysis.hxdataanalysis;

import android.content.Context;

import com.puhui.dataanalysis.hxdataanalysis.crashhandler.CrashHandler;

import java.util.Map;

/**
 * Created by chenb on 2018/5/14.
 */

public class HXAnalysis {
    private static EventManager eventManager;

    public static synchronized void init(Context context) {
        initEventManager(context,HostService.appService);
        eventManager.init(context,HostService.appService);
    }

    public static synchronized void init(Context context, String appId, String channelId) {
        initEventManager(context,HostService.appService);
        eventManager.init(context, appId, channelId, HostService.appService);
    }

    public static void onEvent(Context context, String eventId) {
        onEvent(context, eventId, null);
    }


    public static void onEvent(Context context, String eventId, String label) {
        onEvent(context,eventId,label,null);
    }

    public static void onEvent(Context context, String eventId, String label, Map map) {
        eventManager.onEvent(context,eventId,label,map,HostService.appService);
    }

    private static synchronized void initEventManager(Context context, HostService hostService) {
        if (context != null) {
            HXDataConfig.context = context.getApplicationContext();
            if (HxAnalysisOption.collectCrash) {
                CrashHandler.getInstance().init(HXDataConfig.context);
            }
        }
        if (HXDataConfig.context == null) {
            HXLog.eForDeveloper("Init failed Context is null");
        } else {
            if (eventManager == null) {
                eventManager = EventManager.getInstance();
            }
        }
    }
}
