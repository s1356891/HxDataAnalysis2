package com.puhui.dataanalysis.hxdataanalysis;

/**
 * Created by chenb on 2018/5/21.
 */

public class SqlHost extends HostService {
    public SqlHost(String type, int i) {
        super(type, i);
    }

    @Override
    public String getHost() {
        return "https://data.haixiangjinfu.com/getSignatureInfo/appService";
    }

    @Override
    public String getAddress() {
        return "data.haixiangjinfu.com";
    }

    @Override
    public String getDataFormat() {
        return "MP";
    }
}
