package com.puhui.dataanalysis.hxdataanalysis;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by chenb on 2018/5/14.
 */

public class EventHandler extends Handler {
    EventHandler(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message message) {
        DataStoreManager.getInstance();
        SubmitManager.getInstance();
        if (message.obj != null && message.obj instanceof EventManager.EventParams) {
            EventManager.EventParams params = (EventManager.EventParams) message.obj;
            try {
                RegisterManager.getInstance().post(params);
            } catch (Throwable throwable) {

            }
        }
    }
}
