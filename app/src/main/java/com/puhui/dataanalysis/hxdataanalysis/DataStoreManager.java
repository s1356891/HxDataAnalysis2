package com.puhui.dataanalysis.hxdataanalysis;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chenb on 2018/5/25.
 */

public class DataStoreManager {
    static HashMap a = new HashMap();
    static HashMap b = new HashMap();
    static String codeFormat = "utf-8";
    static byte[] bytes;
    private static volatile DataStoreManager dataStoreManager = null;


    public synchronized List a(HostService hostService) {
        ArrayList list = null;
        try {

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return list;
    }

    public final synchronized void onHXDataEventDataStore(DataStoreObj obj) {
        if (obj != null) {
            try {
                try {
                    if (obj.hostService == null || !obj.hostService.getType().equals("APP_SQL")) {
                        DomainJsonObj domainJsonObj = new DomainJsonObj(obj.eventType, obj.eventId);
                        JSONObject jsonObject;
                        domainJsonObj.setData(obj.map);
                        jsonObject = JsonObjUtil.getInstance().JsonOpt2JsonObj(domainJsonObj, true, obj.hostService);
                        StorageCallback.getInstance().writeFile(jsonObject.toString(),obj);
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            } catch (Throwable throwable) {

            }
        }
    }

    public synchronized List getList(HostService hostService) {
        ArrayList list = null;
        try {
            List list1 = StorageCallback.getInstance().getList(hostService, 100);
            if (list1.size() > 0) {
                list = new ArrayList();
                Iterator iterator = list1.iterator();
                while (iterator.hasNext()) {
                    byte[] bytes = (byte[]) iterator.next();
                    try {
//                        byte[] bytes1 = EncryptUtil.encrypt(bytes, this.bytes);
                        String str = new String(bytes);
                        list.add(str);
                    } catch (Throwable throwable) {

                    }
                }
            }
        } catch (Throwable throwable) {

        }
        return list;
    }


    public void sendMessageSuccess(HostService hostService) {
        try {
            StorageCallback.getInstance().confirmRead(hostService);
        } catch (Throwable throwable) {

        }
    }

    public static DataStoreManager getInstance() {
        if (dataStoreManager == null) {
            synchronized (DataStoreManager.class) {
                if (dataStoreManager == null) {
                    dataStoreManager = new DataStoreManager();
                }
            }
        }
        return dataStoreManager;
    }

    private DataStoreManager() {
        String pkgName = EncryptUtil.md5(HXDataConfig.context.getPackageName());
        if (HXDataConfig.context != null && pkgName != null) {
            bytes = pkgName.getBytes();
        } else {
            bytes = HXDataConfig.class.getSimpleName().getBytes();
        }
    }


    static {
        try {
            RegisterManager.getInstance().register(getInstance());
        } catch (Throwable throwable) {

        }
    }
}
