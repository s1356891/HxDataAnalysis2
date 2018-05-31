package com.puhui.dataanalysis.hxdataanalysis;

import android.os.Process;

import java.util.Properties;
import java.util.zip.CRC32;

/**
 * Created by chenb on 2018/5/25.
 */

public class CRC32Util extends Properties implements Comparable<CRC32Util> {
    private String str;
    private byte[] bytes;
    private int crcValue;
    private int crcLength;
    private CRC32 crc32;

    @Override
    public int compareTo(CRC32Util o) {
        return this.getStr().compareTo(o.getStr());
    }

    public CRC32Util(String str) {
        this.str = str;
    }

    public CRC32Util(byte[] bytes) {
        this(getUUID(), bytes);
    }

    public static String getUUID() {
        return System.currentTimeMillis() + "_" + Long.toString((long) Process.myPid());
    }

    public String getStr() {
        return this.str;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int putProperty(String str, int i) {
        String property = (String) this.setProperty(str, String.valueOf(i));
        return property == null ? 0 : Integer.valueOf(property).intValue();
    }

    public int putProperty(String str, byte[] bytes) {
        String property = (String) this.setProperty(str, bytesToStr(bytes));
        return property == null ? 0 : Integer.valueOf(property).intValue();
    }

    public int getPropertyInt(String str) {
        return Integer.valueOf(super.getProperty(str)).intValue();
    }

    public byte[] getPropertyBytes(String str) {
        return strToBytes(super.getProperty(str));
    }


    public byte[] strToBytes(String string) {
        byte[] bytes = null;
        if (string != null) {
            bytes = string.getBytes();
        }
        return bytes;
    }


    public String bytesToStr(byte[] bytes) {
        return new String(bytes);
    }

    private CRC32Util(String str, byte[] bytes) {
        this(str);
        this.crc32 = new CRC32();
        this.writeData(bytes);
    }


    public void writeData(byte[] var1) {
        this.bytes = var1;
        this.crcLength = var1.length;
        this.crc32.reset();
        this.crc32.update(var1);
        this.crcValue = (int) this.crc32.getValue();
    }

    public static final class b {
        public static final String a = "Type";

        public b() {
        }
    }

    public static final class a {
        public static final String a = "rcs32";
        public static final String b = "length";
        public static final String c = "data";

        public a() {
        }
    }

    public int getCRCValue() {
        return this.crcValue;
    }

    public int getCRCLength() {
        return this.crcLength;
    }

}
