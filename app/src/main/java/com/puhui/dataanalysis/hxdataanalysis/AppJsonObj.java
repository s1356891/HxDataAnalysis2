package com.puhui.dataanalysis.hxdataanalysis;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by chenb on 2018/5/28.
 */

public class AppJsonObj extends JsonObjectOpt {
    private static HashMap hashMap = new HashMap();
    private static HashMap hashMap2 = new HashMap();
    private static volatile AppJsonObj appJsonObj = null;

    public static AppJsonObj getInstance() {
        if (appJsonObj == null) {
            synchronized (AppJsonObj.class) {
                if (appJsonObj == null) {
                    appJsonObj = new AppJsonObj();
                }
            }
        }
        return appJsonObj;
    }

    private AppJsonObj() {
        this.setJson("displayName", PackageInfo.getInstance().getLabel(HXDataConfig.context));
        this.setJson((String)"globalId", PackageInfo.getInstance().getPackageName(HXDataConfig.context));
        this.setJson((String)"versionName", SharedPreferencesManager.getVersionName());
        this.setJson((String)"versionCode", Integer.valueOf(SharedPreferencesManager.getVersionCode()));
        this.setJson((String)"installTime", PackageInfo.getInstance().getInstallTime(HXDataConfig.context));
        this.setJson((String)"updateTime", PackageInfo.getInstance().getUpdateTime(HXDataConfig.context));
    }

    public void putOpt(Object obj, HostService hostService) {
        hashMap.put(hostService.getType(), obj);
    }

    public void putOpt2(Object obj, HostService hostService) {
        hashMap2.put(hostService.getType(), obj);
    }

    private ArrayList getOptList() {
        ArrayList list = new ArrayList();
        try {
            Iterator iterator = hashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object object = entry.getKey();
                list.add(hashMap.get(object));
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return list;
    }

    public void setSubmitAppId(HostService hostService) {
        if (hostService != null) {
            Object object = hashMap.get(hostService.getType());
            if (object == null) {
                object = hashMap.get(((HostService) getOptList().get(0)).getType());
            }
            this.setJson("appKey", object);
        }
    }

    public void setSubmitChannelId(HostService service) {
        if (service != null) {
            try {
                Object object = hashMap2.get(service.getType());
                if (object == null) {
                    object = ((HostService) hashMap2.get(0)).getType();
                }
                this.setJson("channel", object);
            } catch (Throwable throwable) {

            }
        } else {
            this.setAppChannel("Default");
        }
    }

    public void setAppChannel(String var1) {
        this.setJson("channel", var1);
    }

    public void setUniqueId(String str){
        this.setJson("uniqueId",str);
    }

    static byte[] getPackageCer(Context context,String str){
        try {
            android.content.pm.PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 64);
            android.content.pm.Signature[] signatures = packageInfo.signatures;
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            byte[] bytes = signatures[0].toByteArray();
            factory.generateCertificate(new ByteArrayInputStream(bytes));
            return bytes;
        } catch (Throwable throwable) {
            return null;
        }
    }
}
