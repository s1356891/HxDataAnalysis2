package com.puhui.dataanalysis.hxdataanalysis;

/**
 * Created by chenb on 2018/5/21.
 */

public class AppHost extends HostService {
    public AppHost(String type, int i) {
        super(type, i);
    }

    @Override
    public String getHost() {
        return "http://172.16.100.139:8080/New/EventCollect";
    }

    @Override
    public String getAddress() {
        return "172.16.100.139:8080/New/Hello";
    }

    @Override
    public String getDataFormat() {
        return "JSON";
    }
}
