package com.puhui.dataanalysis.hxdataanalysis;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by chenb on 2018/5/28.
 */

public class OnEventManager {
    private static volatile OnEventManager onEventManager;

    public static OnEventManager getInstance() {
        if (onEventManager == null) {
            synchronized (OnEventManager.class) {
                if (onEventManager == null) {
                    onEventManager = new OnEventManager();
                }
            }
        }
        return onEventManager;
    }

    public final void onHXDataEventAppEvent(EventManager.EventParams params) {
        try {
            if (params != null && params.map != null && Integer.parseInt(String.valueOf(params.map.get("apiType"))) == 2) {
                HostService hostService = (HostService) params.map.get("service");
                if (SharedPreferencesManager.a(hostService) != null && !SharedPreferencesManager.a(hostService).isEmpty()) {
                    TreeMap treeMap = new TreeMap();
                    String eventId = String.valueOf(params.map.get("eventId"));
                    Object map = params.map.get("map");
                    if (map != null && map instanceof Map) {
                        treeMap.putAll((Map)map);
                    }
                    DataStoreObj dataStoreObj = new DataStoreObj();
                    dataStoreObj.eventType = "appEvent";
                    dataStoreObj.eventId = eventId;
                    dataStoreObj.hostService = hostService;
                    TreeMap treeMap1 = new TreeMap();
                    treeMap1.put("eventLabel", String.valueOf(params.map.get("eventLabel")));
                    treeMap1.put("eventParam", new JSONObject(resetData(treeMap)));
                    dataStoreObj.map = treeMap1;
                    RegisterManager.getInstance().post(dataStoreObj);
                }
            }
        } catch (Throwable throwable) {

        }
    }

    private Map resetData(Map var1) {
        TreeMap var2 = new TreeMap();

        try {
            if(var1 != null && var1.size() == 0) {
                return var2;
            }

            int var3 = 0;
            Iterator var4 = var1.entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry var5 = (Map.Entry)var4.next();
                if(var5.getValue() instanceof Number) {
                    var2.put(var5.getKey(), var5.getValue());
                } else {
                    var2.put(var5.getKey(), String.valueOf(var5.getValue()));
                }

                ++var3;
                if(var3 == 50) {
                    break;
                }
            }
        } catch (Throwable var6) {
        }

        return var2;
    }

    private OnEventManager() {

    }
    static {
        try {
            RegisterManager.getInstance().register(getInstance());
        } catch (Throwable throwable) {

        }
    }
}
