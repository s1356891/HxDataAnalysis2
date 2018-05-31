package com.puhui.dataanalysis.hxdataanalysis;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenb on 2018/5/21.
 */

public class HXTaskManager {
    private static final ExecutorService service = Executors.newSingleThreadExecutor();

    public static void execute(Runnable runnable) {
        if (service != null) {
            service.execute(runnable);
        }
    }

}
