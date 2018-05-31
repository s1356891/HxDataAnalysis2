package com.puhui.dataanalysis.hxdataanalysis;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by chenb on 2018/5/28.
 */

public class v implements HostnameVerifier {
    v(){}
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
