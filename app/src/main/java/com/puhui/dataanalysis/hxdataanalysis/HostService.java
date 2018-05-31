package com.puhui.dataanalysis.hxdataanalysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenb on 2018/5/21.
 */

public abstract class HostService {
    private static volatile List services = new ArrayList();
    private String type;
    private int i;
    public static HostService appService = new AppHost("APP", 0);
    public static HostService e = new SqlHost("APP_SQL", 7);
    public static HostService[] hosts;

    protected HostService(String type, int i) {
        this.type = type;
        this.i = i;
        addServices(type);
    }

    private void addServices(String type) {
        try {
            if (!HxUtils.isEmpty(type) && !services.contains(type)) {
                services.add(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getApiType() {
        return this.i;
    }


    public String getType() {
        return this.type;
    }

    public abstract String getHost();

    public abstract String getAddress();

    public abstract String getDataFormat();

    public static HostService[] getHosts() {
        return hosts;
    }
    public static ArrayList getServices() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < services.size(); i++) {
            arrayList.add(getHost((String) services.get(i)));
        }
        return arrayList;
    }


    public static HostService getHost(String var0) {
        return var0.equals(appService.getType()) ? appService : (var0.equals(e.getType()) ? e : null);
    }

    static {
        hosts = new HostService[]{appService, e};
    }
}
