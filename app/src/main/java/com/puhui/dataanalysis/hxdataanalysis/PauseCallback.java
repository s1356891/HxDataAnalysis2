package com.puhui.dataanalysis.hxdataanalysis;

import android.app.Activity;
import android.os.Message;

/**
 * Created by chenb on 2018/5/29.
 */

final class PauseCallback implements Runnable {
    private HostService hostService;
    private Activity activity;

    public PauseCallback(HostService hostService, Activity activity) {
        this.hostService = hostService;
        this.activity = activity;
    }

    @Override
    public void run() {
        try {
            EventManager.EventParams params = new EventManager.EventParams();
            params.map.put("apiType", Integer.valueOf(11));
            params.map.put("service", this.hostService);
            params.map.put("pageName", this.activity != null ? this.activity.getLocalClassName() : "");
            params.map.put("occurTime", String.valueOf(System.currentTimeMillis()));
            Message.obtain(EventManager.getHandler(), 102, params).sendToTarget();
        } catch (Throwable throwable) {

        }
    }
}
