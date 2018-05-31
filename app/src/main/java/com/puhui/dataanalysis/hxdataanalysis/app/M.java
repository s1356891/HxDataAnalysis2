package com.puhui.dataanalysis.hxdataanalysis.app;

import android.app.Application;

import com.puhui.dataanalysis.hxdataanalysis.HXAnalysis;
import com.puhui.dataanalysis.hxdataanalysis.crashhandler.CrashHandler;

/**
 * Created by chenb on 2018/5/23.
 */

public class M extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HXAnalysis.init(this, "ar32423", "324324");
    }
}
