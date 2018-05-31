package com.puhui.dataanalysis.hxdataanalysis;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/**
 * Created by chenb on 2018/5/28.
 */

public class SubmitHandler extends Handler {
    public SubmitManager submitManager;

    public SubmitHandler(SubmitManager submitManager, Looper looper) {
        super(looper);
        this.submitManager = submitManager;
    }

    @Override
    public void handleMessage(Message msg) {
        HXLog.eForDeveloper("SubmitHandler  handleMessage");
        try {
            switch (msg.what) {
                case 5:
                    if (msg.obj != null && msg.obj instanceof HostService) {
                        HostService hostService = (HostService) msg.obj;
                        SubmitManager.getInstance().submit(hostService);
                    }
                default:
                    HXLog.eForDeveloper("holadmessage");
                    this.submitManager.holdMessage();
            }
        } catch (Throwable throwable) {

        }
    }
}
