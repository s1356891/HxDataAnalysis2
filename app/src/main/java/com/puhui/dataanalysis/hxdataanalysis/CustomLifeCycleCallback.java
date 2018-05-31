package com.puhui.dataanalysis.hxdataanalysis;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by chenb on 2018/5/21.
 */

public class CustomLifeCycleCallback implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        ActivityEventManager.resume(activity,HostService.appService);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityEventManager.pause(activity,HostService.appService);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
