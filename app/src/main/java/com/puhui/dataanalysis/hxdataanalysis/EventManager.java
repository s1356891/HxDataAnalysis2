package com.puhui.dataanalysis.hxdataanalysis;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;

import com.puhui.dataanalysis.hxdataanalysis.crashhandler.CrashHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenb on 2018/5/14.
 */

public class EventManager {
    private static Handler mHandler;
    private static final HandlerThread mHandlerThread;
    public static volatile EventManager eventManager;
    public static volatile boolean isInitialed;
    public static boolean isRegistered;
    private CustomLifeCycleCallback cycleCallback;
    public EventManager() {
        eventManager = this;
    }

    static synchronized EventManager getInstance() {
        if (eventManager == null) {
            Class clz = EventManager.class;
            synchronized (clz) {
                if (eventManager == null) {
                    eventManager = new EventManager();
                }
            }
        }
        return eventManager;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public  void init(Context context,HostService hostService) {
        init(context,null,null,hostService);
    }

    public  void init(Context context, String appId, String channelId, HostService hostService) {
        try {
            if (context == null) {
                HXLog.eForDeveloper("Init failed Context is null");
                return;
            }

            if (!HxUtils.checkPermission(context, "android.permission.INTERNET")) {
                HXLog.eForDeveloper("[SDKInit] Permission \"android.permission.INTERNET\" is needed.");
                return;
            }
            if (hostService == null) {
                HXLog.eForDeveloper("Failed to initialize");
                return;
            }
            try {
                if (!isInitialed) {
                    HXDataConfig.context = context.getApplicationContext();
                    this.registerLifeCycleCallback();
                    isInitialed = true;
                }
                HXTaskManager.execute(new InitCallback(this,appId,channelId,hostService));
            } catch (Throwable throwable) {
                HXLog.eForDeveloper("[SDKInit] Failed to initialize!");
                throwable.printStackTrace();
            }

        } catch (Throwable throwable) {

        }
    }

    private void registerLifeCycleCallback() {
        Class application;
        if (HxUtils.checkVersion(14)) {
            try {
                Application app = null;
                if (HXDataConfig.context instanceof Activity) {
                    app = ((Activity) HXDataConfig.context).getApplication();
                } else if (HXDataConfig.context instanceof Application) {
                    app = (Application) HXDataConfig.context;
                }
                if (app != null && !isRegistered) {
                    application = Class.forName("android.app.Application$ActivityLifecycleCallbacks");
                    Method method = app.getClass().getMethod("registerActivityLifecycleCallbacks", new Class[]{application});
                    cycleCallback = new CustomLifeCycleCallback();
                    method.invoke(app, new Object[]{cycleCallback});
                    isRegistered = true;
                }
            } catch (Throwable throwable) {

            }
        }
    }


    public void onEvent(Context context, String eventId, String label, Map map, HostService hostService) {
        try {
            if (TextUtils.isEmpty(eventId)) {
                HXLog.eForDeveloper("onEvent() event id is empty.");
                return;
            }
            if (!isInitialed && eventId != null) {
                this.init(context, null, null, hostService);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onEvent being called! eventId: ");
            stringBuilder.append(eventId);
            if (hostService.getApiType() != 3) {
                stringBuilder.append(",eventLabel: ");
                stringBuilder.append(label == null ? "null" : label);
            }
            stringBuilder.append(", eventMap: ");
            stringBuilder.append(map == null ? "null" : "mapSize: " + String.valueOf(map.size()));
            HXLog.eForDeveloper(stringBuilder.toString());
            HXTaskManager.execute(new EventCallback(this,hostService,eventId,label,map));
        } catch (Exception e) {

        }
    }

    static {
        mHandlerThread = new HandlerThread("ProcessingThread");
        mHandlerThread.start();
        mHandler = new EventHandler(mHandlerThread.getLooper());
    }


    public static class EventParams {
        public HashMap map = new HashMap();

        public EventParams() {

        }
    }
}

