package com.puhui.dataanalysis.hxdataanalysis;

import android.app.Activity;
import android.os.Message;

/**
 * Created by chenb on 2018/5/29.
 */

final class ActivityEventManager {
    private ActivityEventManager() {

    }

    static void resume(Activity activity, HostService hostService) {
        try {
            if (hostService.getType().equals("APP")) {
                SessionManager.getHandler().removeMessages(0);
                if (activity != null && (activity.getChangingConfigurations() & 128) == 128) {
                    HXLog.eForDeveloper("Ignore page changing during screen switch");
                    return;
                }
                HXTaskManager.execute(new ResumeCallback(hostService));

            }
        } catch (Throwable throwable) {

        }
    }

    static void pause(Activity activity, HostService hostService) {
        try {
            if (hostService.getType().equals("APP") || hostService.getType().equals("APP_SQL")) {
                SessionManager.getHandler().removeMessages(0);
                Message message = Message.obtain();
                message.obj = hostService;
                message.what = 0;
                SessionManager.getHandler().sendMessageDelayed(message, 30000L);
            }
            HXTaskManager.execute(new PauseCallback(hostService,activity));
        } catch (Throwable throwable) {

        }
    }
}
