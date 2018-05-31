package com.puhui.dataanalysis.hxdataanalysis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by chenb on 2018/5/28.
 */

public class HttpUtil {
    private static final int d = 600;
    private static final int e = 60000;
    private static final int f = 60000;
    public static int a = '\uea60';
    public static int b = '\uea60';
    public static volatile HashMap c = new HashMap();
    private static Context g = null;
    private static volatile HashMap hashMap = new HashMap();

    public HttpUtil() {
    }
    public static String post(String address,String data){
        HXLog.eForDeveloper(data+" data======");
        String message="";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        try {
            URL url=new URL(address);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("connection", "keep-alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
            connection.connect();
            OutputStream outputStream=connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.close();
            InputStream inputStream=connection.getInputStream();
            byte[] bytes=new byte[1024];
            StringBuffer sb1=new StringBuffer();
            int length=0;
            while ((length=inputStream.read(bytes))!=-1){
                String s=new String(bytes, Charset.forName("utf-8"));
                sb1.append(s);
            }
            message=sb1.toString();
            inputStream.close();
            connection.disconnect();
        } catch (Exception e) {
            HXLog.eForDeveloper(e.toString()+"exception=========================");

            e.printStackTrace();
        }
        return message;
    }


    public static HttpUtil.d getHttpResponse(Context var0, String var1, String var2, String var3, String var4, byte[] var5) {
        g = var0;
        b(var1, var2);
        return a(var1, var2, var3, var4, var5);
    }

    public static String a(String var0, boolean var1) {
        return b(var0, (String)null, var1);
    }

    public static String a(String var0, String var1, boolean var2) {
        return b(var0, var1, var2);
    }

    public static SSLSocketFactory a(boolean var0, X509Certificate var1) {
        try {
            SSLContext var2 = SSLContext.getInstance("TLS");
            if(var0 && var1 != null) {
                HttpUtil.c var3 = new HttpUtil.c(var1);
                var2.init((KeyManager[])null, new TrustManager[]{var3}, (SecureRandom)null);
            } else {
                var2.init((KeyManager[])null, (TrustManager[])null, (SecureRandom)null);
            }

            SSLSocketFactory var5 = var2.getSocketFactory();
            return var5;
        } catch (Throwable var4) {
            return null;
        }
    }

    private static String b(String var0, String var1, boolean var2) {
        if(HxUtils.isEmpty(var0)) {
            return null;
        } else {
            Object var3 = null;
            BufferedReader var4 = null;
            StringBuffer var5 = new StringBuffer();

            String var8;
            try {
                URL var6 = new URL(var0);
                var3 = (HttpURLConnection)a((URL)var6, (String)null, false);
                if(var0.toLowerCase().startsWith("https") && !HxUtils.isEmpty(var1)) {
                    c.put(Long.valueOf(Thread.currentThread().getId()), var6.getHost());
                    HttpsURLConnection var7 = a((URLConnection)var3, (String)var1);
                    var3 = var7;
                }

                ((HttpURLConnection)var3).setRequestMethod("GET");
                int var26 = ((HttpURLConnection)var3).getResponseCode();
                if(var26 == 200) {
                    if(var2) {
                        var5.append(new String(a(((HttpURLConnection)var3).getInputStream()), "utf-8"));
                        return var5.toString();
                    } else {
                        var4 = new BufferedReader(new InputStreamReader(((HttpURLConnection)var3).getInputStream()));

                        while((var8 = var4.readLine()) != null) {
                            var5.append("/n");
                            var5.append(var8);
                        }

                        return var5.toString();
                    }
                }

                var8 = String.valueOf(var26);
            } catch (Throwable var24) {
                return var5.toString();
            } finally {
                try {
                    if(var4 != null) {
                        var4.close();
                    }
                } catch (Throwable var23) {
                    ;
                }

                try {
                    if(var3 != null) {
                        ((HttpURLConnection)var3).disconnect();
                    }
                } catch (Throwable var22) {
                    ;
                }

            }

            return var8;
        }
    }

    private static byte[] a(InputStream var0) {
        byte[] var1 = null;

        try {
            GZIPInputStream var2 = new GZIPInputStream(var0);
            byte[] var3 = new byte[1024];
            boolean var4 = true;
            ByteArrayOutputStream var5 = new ByteArrayOutputStream();

            int var7;
            while((var7 = var2.read(var3, 0, var3.length)) != -1) {
                var5.write(var3, 0, var7);
            }

            var1 = var5.toByteArray();
            var5.flush();
            var5.close();
            var2.close();
        } catch (Throwable var6) {
        }

        return var1;
    }

    private static URL a(String var0, String var1)throws Exception {
        URL var2 = new URL(var0);
        return NetworkUtil.isProxy()?var2:new URL(var2.getProtocol(), var1, var2.getPort(), var2.getFile());
    }

    private static void a(String var0) {
        String var1 = a(var0, 1);
        if(var1 != null && !var1.equalsIgnoreCase(a(var0, 3)) && g != null) {
            SharedPreferences var2 = PreferenceManager.getDefaultSharedPreferences(g);
            var2.edit().putString(EncryptUtil.sha256(var0), a(var0, 1)).apply();
            a(var0, a(var0, 1), 3);
        }

    }

    private static HttpUtil.d a(String var0, String var1, String var2, String var3, byte[] var4) {
        HttpUtil.d var5 = new HttpUtil.d(600);

        try {
            if(a(var0, 2) != null) {
                var5 = a(a(var0, 2), var2, var3, var4, var0);
                if(var5.a == 600) {
                    a(var0, (String)null, 2);
                }
            } else {
                if(a(var0, 1) != null) {
                    var5 = a(a(var0, 1), var2, var3, var4, var0);
                    if(var5.a != 600) {
                        a(var0, a(var0, 1), 2);
                        a(var0);
                    }
                }

                if(var5.a == 600 && a(var0, 3) != null) {
                    var5 = a(a(var0, 3), var2, var3, var4, var0);
                    if(var5.a != 600) {
                        a(var0, a(var0, 3), 2);
                    }
                }

                if(var5.a == 600 && a(var0, 4) != null) {
                    var5 = a(a(var0, 4), var2, var3, var4, var0);
                    if(var5.a != 600) {
                        a(var0, a(var0, 4), 2);
                    }
                }
            }

            return var5;
        } catch (Throwable var7) {
            return var5;
        }
    }

    private static HttpUtil.d a(String var0, String var1, String var2, byte[] var3, String var4) {
        try {
            if(var1.startsWith("https://")) {
                c.put(Long.valueOf(Thread.currentThread().getId()), var4);
            }

            Object var5 = null;
            if(var1.toLowerCase().startsWith("https") && var2.trim().isEmpty()) {
                var5 = a(new URL(var1), var4, true);
            } else {
                var5 = a(a(var1, var0), var4, true);
            }

            if(var5 == null) {
                return new HttpUtil.d(600, "");
            } else {
                if(var1.toLowerCase().startsWith("https") && !var2.trim().isEmpty()) {
                    var5 = a((URLConnection)var5, (String)var2);
                }

                return a((byte[])var3, (URLConnection)var5);
            }
        } catch (Throwable var6) {
            return new HttpUtil.d(600, "");
        }
    }

    private static URLConnection a(URL var0, String var1, boolean var2) {
        try {
            URLConnection var3 = var0.openConnection();
            var3.setRequestProperty("Accept-Encoding", "");
            var3.setRequestProperty("User-Agent", "");
            if(var1 != null) {
                var3.setRequestProperty("Host", var1);
                var3.setRequestProperty("Content-Type", "");
            }

            var3.setDoInput(true);
            if(var2) {
                var3.setDoOutput(true);
            }

            var3.setConnectTimeout(a);
            var3.setReadTimeout(b);
            return var3;
        } catch (Throwable var5) {
            return null;
        }
    }

    private static HttpsURLConnection a(URLConnection var0, String var1) {
        try {
            HttpsURLConnection var2 = (HttpsURLConnection)var0;
            SSLContext var3 = null;
            if(HxUtils.checkVersion(16)) {
                var3 = SSLContext.getInstance("TLSv1.2");
            } else {
                var3 = SSLContext.getInstance("TLSv1");
            }

            HttpUtil.c var4 = new HttpUtil.c(b(var1));
            var3.init((KeyManager[])null, new TrustManager[]{var4}, (SecureRandom)null);
            v var5 = new v();
            var2.setHostnameVerifier(var5);
            var2.setSSLSocketFactory(var3.getSocketFactory());
            return var2;
        } catch (Throwable var6) {
            return null;
        }
    }

    private static HttpUtil.d a(byte[] var0, URLConnection var1) {
        if(var0 != null && var0.length != 0 && var1 != null) {
            long var2 = SystemClock.elapsedRealtime();
            InputStream var4 = null;
            OutputStream var5 = null;
            BufferedReader var6 = null;
            HttpURLConnection var7 = (HttpURLConnection)var1;
            StringBuffer var8 = new StringBuffer();
            int var9 = 600;

            try {
                var7.setRequestMethod("POST");
                var5 = var7.getOutputStream();
                var5.write(var0);
                var5.close();
                var9 = var7.getResponseCode();
                if(var9 > 400) {
                    var4 = var7.getErrorStream();
                } else {
                    var4 = var7.getInputStream();
                }

                var6 = new BufferedReader(new InputStreamReader(var4));

                String var10;
                while((var10 = var6.readLine()) != null) {
                    var8.append(var10);
                    var8.append('\n');
                }
            } catch (Throwable var31) {
                ;
            } finally {
                try {
                    if(var5 != null) {
                        var5.close();
                    }
                } catch (Throwable var30) {
                    ;
                }

                try {
                    if(var6 != null) {
                        var6.close();
                    }
                } catch (Throwable var29) {
                    ;
                }

                try {
                    if(var4 != null) {
                        var4.close();
                    }
                } catch (Throwable var28) {
                    ;
                }

                try {
                    if(var7 != null) {
                        var7.disconnect();
                    }
                } catch (Throwable var27) {
                }

                a = '\uea60';
                b = '\uea60';
                a(var7, (long)var0.length, var2, var8);
            }

            return new HttpUtil.d(var9, var8.toString());
        } else {
            return new HttpUtil.d(600, "");
        }
    }

    private static void a(HttpURLConnection var0, long var1, long var3, StringBuffer var5) {
        try {
            if(var0 != null) {
                TreeMap var7 = new TreeMap();
                URL var8 = var0.getURL();
                var7.put("targetUrl", var8.toString());
                InetAddress var9 = InetAddress.getByName(var8.getHost());
                var7.put("targetIP", var9.getHostAddress());
                boolean var6;
                if(var0.getResponseCode() == 200) {
                    var7.put("reqSize", Long.valueOf(var1));
                    var7.put("respTime", Long.valueOf(SystemClock.elapsedRealtime() - var3));
                    var6 = true;
                } else {
                    var7.put("errorMsg", var5.toString());
                    var6 = false;
                }
            }
        } catch (Throwable var10) {
        }

    }

    private static X509Certificate b(String var0) {
        if(var0 != null && !var0.trim().isEmpty()) {
            ByteArrayInputStream var1 = new ByteArrayInputStream(var0.getBytes());
            X509Certificate var2 = null;

            try {
                CertificateFactory var3 = CertificateFactory.getInstance("X.509");
                var2 = (X509Certificate)var3.generateCertificate(var1);
            } catch (Exception var12) {
                ;
            } finally {
                try {
                    if(var1 != null) {
                        var1.close();
                    }
                } catch (Throwable var11) {
                    ;
                }

            }

            return var2;
        } else {
            return null;
        }
    }

    private static synchronized void a(String var0, String var1, int var2) {
        if(!HxUtils.isEmpty(var0) && hashMap.containsKey(var0)) {
            if(hashMap != null) {
                HttpUtil.a var3 = (HttpUtil.a) hashMap.get(var0);
                switch(var2) {
                    case 1:
                        var3.b = var1;
                        break;
                    case 2:
                        var3.d = var1;
                        break;
                    case 3:
                        var3.c = var1;
                        break;
                    case 4:
                        var3.a = var1;
                }

            }
        }
    }

    private static synchronized void b(String var0, String var1) {
        if(!HxUtils.isEmpty(var0) && !hashMap.containsKey(var0)) {
            if(hashMap != null) {
                try {
                    HttpUtil.a var2 = new HttpUtil.a();
                    var2.e = var0;
                    var2.a = var1;
                    var2.c = PreferenceManager.getDefaultSharedPreferences(g).getString(EncryptUtil.sha256(var0), (String)null);
                    InetAddress var3 = InetAddress.getByName(var0);
                    var2.b = var3.getHostAddress();
                    hashMap.put(var0, var2);
                } catch (Throwable var4) {
                    ;
                }

            }
        }
    }

    private static synchronized String a(String var0, int var1) {
        if(!HxUtils.isEmpty(var0) && hashMap.containsKey(var0)) {
            if(hashMap == null) {
                return null;
            } else {
                HttpUtil.a var2 = (HttpUtil.a) hashMap.get(var0);
                if(var2 == null) {
                    return null;
                } else {
                    switch(var1) {
                        case 1:
                            return var2.b;
                        case 2:
                            return var2.d;
                        case 3:
                            return var2.c;
                        case 4:
                            return var2.a;
                        default:
                            return null;
                    }
                }
            }
        } else {
            return null;
        }
    }

    private static void a(HttpsURLConnection var0) {
        if(var0 != null) {
            ;
        }

    }

    static class b {
        static final int a = 1;
        static final int b = 2;
        static final int c = 3;
        static final int d = 4;

        b() {
        }
    }

    static class a {
        String a = null;
        String b = null;
        String c = null;
        String d = null;
        String e = null;

        a() {
        }
    }

    static class c implements X509TrustManager {
        X509Certificate a;

        c(X509Certificate var1) {
            this.a = var1;
        }

        public void checkClientTrusted(X509Certificate[] var1, String var2) {
        }

        public void checkServerTrusted(X509Certificate[] var1, String var2) {
            if(var1.length == 0) {
                ;
            }

            if(!var1[0].getIssuerDN().equals(this.a.getSubjectDN())) {
                ;
            }

            try {
                String var3 = var1[0].getSubjectDN().getName();
                int var4 = var3.indexOf("CN=");
                if(var4 >= 0) {
                    var3 = var3.substring(var4 + 3);
                    int var5 = var3.indexOf(",");
                    if(var5 >= 0) {
                        var3 = var3.substring(0, var5);
                    }
                }

                String[] var8 = var3.split("\\.");
                String var6 = var3;
                if(var8.length >= 2) {
                    var6 = var8[var8.length - 2] + "." + var8[var8.length - 1];
                }

                if(!HttpUtil.c.containsKey(Long.valueOf(Thread.currentThread().getId()))) {
                    throw new CertificateException("No valid host provided!");
                }

                if(!((String)HttpUtil.c.get(Long.valueOf(Thread.currentThread().getId()))).endsWith(var6)) {
                    throw new CertificateException("Server certificate has incorrect host name!");
                }

                var1[0].verify(this.a.getPublicKey());
                var1[0].checkValidity();
            } catch (Throwable var7) {
                if(var7 instanceof CertificateException) {
                    ;
                }
            }

        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public static class d {
        public int a;
        public String b;

        d(int var1, String var2) {
            this.a = var1;
            this.b = var2;
        }

        d(int var1) {
            this(var1, "");
        }

        public int a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }
    }
}
