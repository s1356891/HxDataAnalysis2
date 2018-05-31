package com.puhui.dataanalysis.hxdataanalysis;

import android.os.Message;

import java.util.Map;

/**
 * Created by chenb on 2018/5/28.
 */

public class EventCallback implements Runnable {
    private EventManager eventManager;
    private HostService hostService;
    private String eventId;
    private String label;
    private Map map;

    public EventCallback(EventManager eventManager, HostService hostService, String eventId, String label, Map map) {
        this.eventManager = eventManager;
        this.hostService = hostService;
        this.eventId = eventId;
        this.label = label;
        this.map = map;
    }

    @Override
    public void run() {
        try {
            EventManager.EventParams params = new EventManager.EventParams();
            params.map.put("service", this.hostService);
            params.map.put("apiType", Integer.valueOf(2));
            params.map.put("eventId", this.eventId);
            params.map.put("eventLabel", this.label == null ? null: this.label);
            params.map.put("map", this.map);
            params.map.put("occurTime", String.valueOf(System.currentTimeMillis()));
            Message.obtain(EventManager.getHandler(),102,params).sendToTarget();
        } catch (Throwable throwable) {

        }
    }
}
