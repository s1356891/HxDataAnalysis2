package com.puhui.dataanalysis.hxdataanalysis;

import android.os.Message;

/**
 * Created by chenb on 2018/5/21.
 */

public class InitCallback implements Runnable {
    private EventManager manager;
    private String appId;
    private String channelId;
    private HostService host;
    public InitCallback(EventManager manager, String appId, String channelId, HostService host) {
        this.manager = manager;
        this.appId = appId;
        this.channelId = channelId;
        this.host = host;
    }

    @Override
    public void run() {
        try {
            SdkInit.getInstance();
            EventManager.EventParams params = new EventManager.EventParams();
            params.map.put("apiType", Integer.valueOf(1));
            params.map.put("appId", this.appId != null ? appId : "");
            params.map.put("channelId", this.channelId != null ? channelId : "");
            params.map.put("service", this.host);
            params.map.put("action", "init");
            Message.obtain(EventManager.getHandler(), 101, params).sendToTarget();
        } catch (Throwable e) {

        }
    }
}
