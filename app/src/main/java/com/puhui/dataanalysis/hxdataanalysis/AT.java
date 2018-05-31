package com.puhui.dataanalysis.hxdataanalysis;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by chenb on 2018/5/23.
 */

public class AT extends ThreadLocal{
    private RegisterManager a;
    AT(RegisterManager var1) {
        this.a = var1;
    }

    @Override
    public ConcurrentLinkedQueue initialValue() {
        return new ConcurrentLinkedQueue();
    }
}
