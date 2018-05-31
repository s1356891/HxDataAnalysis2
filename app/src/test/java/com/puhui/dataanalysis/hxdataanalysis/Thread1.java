package com.puhui.dataanalysis.hxdataanalysis;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by chenb on 2018/5/24.
 */

public class Thread1 extends ThreadLocal {
    @Override
    protected Object initialValue() {
        return new ConcurrentLinkedQueue<>();
    }

}
