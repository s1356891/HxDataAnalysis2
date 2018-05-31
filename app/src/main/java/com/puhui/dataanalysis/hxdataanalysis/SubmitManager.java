package com.puhui.dataanalysis.hxdataanalysis;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.zip.CRC32;

/**
 * Created by chenb on 2018/5/28.
 */

public class SubmitManager {
    private static String version = "v1";
    private static String codeFormat = "utf-8";
    private static final CRC32 crc32 = new CRC32();
    private static final int timeout = 30000;
    private long h = 0L;
    private Handler mHandler = null;
    private boolean isSubmitting = false;
    private static volatile SubmitManager submitManager;
    private static HandlerThread handlerThread;
    public static SubmitManager getInstance() {
        if (submitManager == null) {
            synchronized (SubmitManager.class) {
                if (submitManager == null) {
                    submitManager = new SubmitManager();
                }
            }
        }
        return submitManager;
    }

    private SubmitManager() {
        handlerThread = new HandlerThread("ModuleDataForward");
        handlerThread.start();
        this.mHandler = new SubmitHandler(this, handlerThread.getLooper());
        holdMessage();
    }
    public void holdMessage() {
        HXLog.eForDeveloper("holdmsg");
        if (!this.mHandler.hasMessages(5)) {
            Random random = new Random();
            int delayTime;
            if (NetworkUtil.isNetWork(HXDataConfig.context)) {
                delayTime = !isSubmitting ? 30000 + random.nextInt(30000) : 30000;
            } else {
                delayTime = !this.isSubmitting ? 120000 + (random.nextInt('\uea60') - 30000) : 120000;
            }
            HXLog.eForDeveloper("holdmsg" + delayTime);
            ArrayList arrayList = HostService.getServices();
            Iterator iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                HostService hostService = (HostService) iterator.next();
                this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 5, hostService), (long) delayTime);
            }
        }
    }

    public void submit(HostService hostService) {
        if (NetworkUtil.isConnected(HXDataConfig.context)) {
            try {
                StorageCallback.getInstance().getFileLock(hostService);
                List list = DataStoreManager.getInstance().getList(hostService);
                if (null == list || list.size() <= 0) {
                    HXLog.eForDeveloper("No new data found!");
                    return;
                }
                HXLog.eForDeveloper("New data found, Submitting...");
                String str = list2String(list);
//                byte[] bytes = EncryptUtil.str2Bytes(str);
//                crc32.reset();
//                crc32.update(bytes);
                StringBuilder stringBuilder = new StringBuilder(hostService.getHost());
//                if (hostService.getType().equals("TRACKING")) {
//                    stringBuilder.append("/" + Long.toHexString(crc32.getValue()));
//                    stringBuilder.append("/1");
//                } else {
//                    stringBuilder.append("/" + version);
//                    stringBuilder.append("/" + Long.toHexString(crc32.getValue()));
//                }
//                if ("APP".equals(hostService.getType())) {
//                    stringBuilder = new StringBuilder(hostService.getHost());
//                    stringBuilder.append("?crc=" + Long.toHexString(crc32.getValue()));
//                }
                HXLog.eForDeveloper("submit=========="+str);
                String  responseCode= HttpUtil.post(hostService.getHost(),str);
                JSONObject json = new JSONObject(responseCode);

                if (json.optString("code").equals("0")) {
                    HXLog.eForDeveloper("Data submitted successfully");
                    this.h = SystemClock.elapsedRealtime();
                    this.isSubmitting = true;
                    DataStoreManager.getInstance().sendMessageSuccess(hostService);
                } else {
                    this.isSubmitting = false;
                    HXLog.eForDeveloper("Failed to submit data!");
                }
            } catch (Throwable throwable) {

            }finally {
                StorageCallback.getInstance().releaseFileLock(hostService);
            }
        }
    }

    private static String list2String(List list) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            builder.append(s);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]");
        return builder.toString();
    }


    public final void onHXDataEventForwardRequest(RequestObj requestObj) {
        if (requestObj != null && HXDataConfig.context != null) {
            if (requestObj.b.equals(RequestObj.a.a)) {
                if (this.mHandler.hasMessages(5, requestObj.hostService)) {
                    this.mHandler.removeMessages(5);
                }
                Message.obtain(this.mHandler, 5, requestObj.hostService).sendToTarget();
            } else if (requestObj.b.equals(RequestObj.a.b)) {
                if (this.mHandler.hasMessages(5)) {
                    this.mHandler.removeMessages(5);
                }
                long time = SystemClock.elapsedRealtime() - this.h;
                long time2 = Math.abs(time - 30000L);
                time2 = time2 > 30000L ? 30000L : time2;
                Message message = Message.obtain(this.mHandler, 5, requestObj.hostService);
                this.mHandler.sendMessageDelayed(message, time2);
            }
        }
    }

    static {
        try {
            RegisterManager.getInstance().register(getInstance());
        } catch (Throwable throwable) {

        }
    }
}
