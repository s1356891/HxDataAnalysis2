package com.puhui.dataanalysis.hxdataanalysis;

/**
 * Created by chenb on 2018/5/23.
 */

public class AU extends ThreadLocal{
    private RegisterManager a;
    AU(RegisterManager var1) {
        this.a = var1;
    }

    @Override
    public Boolean initialValue() {
        return Boolean.valueOf(false);
    }
}
