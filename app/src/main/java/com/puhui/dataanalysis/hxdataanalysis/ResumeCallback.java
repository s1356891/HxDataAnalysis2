package com.puhui.dataanalysis.hxdataanalysis;

import android.os.Message;

/**
 * Created by chenb on 2018/5/29.
 */

public class ResumeCallback implements Runnable {
    public HostService hostService;

    public ResumeCallback(HostService hostService) {
        this.hostService = hostService;
    }

    @Override
    public void run() {
        try {
            EventManager.EventParams eventParams = new EventManager.EventParams();
            eventParams.map.put("apiType", Integer.valueOf(10));
            eventParams.map.put("occurTime", Long.valueOf(System.currentTimeMillis()));
            eventParams.map.put("service", this.hostService);
            Message.obtain(EventManager.getHandler(), 102, eventParams).sendToTarget();
        } catch (Throwable throwable) {

        }
    }
}
