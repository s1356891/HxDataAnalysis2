package com.puhui.dataanalysis.hxdataanalysis;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by chenb on 2018/5/24.
 */

public class SessionHandler extends Handler {
    public SessionHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            HostService var2 = (HostService) msg.obj;
            if(SharedPreferencesManager.e(var2).equals("1")) {
                EventManager.getHandler().removeMessages(103);
                EventManager.EventParams params = new EventManager.EventParams();
                params.map.put("apiType", Integer.valueOf(11));
                params.map.put("occurTime", String.valueOf(System.currentTimeMillis()));
                params.map.put("sessionEnd", Integer.valueOf(1));
                params.map.put("service", var2);
                Message.obtain(EventManager.getHandler(), 102, params).sendToTarget();
                SharedPreferencesManager.b("2", var2);
            }
        } catch (Exception var4) {

        }
    }
}
