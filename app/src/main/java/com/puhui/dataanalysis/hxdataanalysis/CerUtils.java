package com.puhui.dataanalysis.hxdataanalysis;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by chenb on 2018/5/28.
 */

public class CerUtils {
    CerUtils() {
    }

    protected static String a(Context var0) {
        String var1 = var0.getPackageName();
        String var2 = a(var0, var1);
        return var2 + var1;
    }

    protected static String a(Context var0, String var1) {
        String var2 = "";

        try {
            PackageInfo var3 = var0.getPackageManager().getPackageInfo(var1, 64);
            Signature[] var4 = var3.signatures;
            CertificateFactory var5 = CertificateFactory.getInstance("X.509");
            X509Certificate var6 = (X509Certificate)var5.generateCertificate(new ByteArrayInputStream(var4[0].toByteArray()));
            var2 = a(var6);
        } catch (PackageManager.NameNotFoundException var7) {
            ;
        } catch (CertificateException var8) {
            ;
        }

        StringBuffer var9 = new StringBuffer();

        for(int var10 = 0; var10 < var2.length(); ++var10) {
            var9.append(var2.charAt(var10));
            if(var10 > 0 && var10 % 2 == 1 && var10 < var2.length() - 1) {
                var9.append(":");
            }
        }

        return var9.toString();
    }

    static String a(X509Certificate var0) {
        try {
            byte[] var1 = a(var0.getEncoded());
            return CerUtils.a.a(var1);
        } catch (CertificateEncodingException var2) {
            return null;
        }
    }

    static byte[] a(byte[] var0) {
        try {
            MessageDigest var1 = MessageDigest.getInstance("SHA1");
            return var1.digest(var0);
        } catch (NoSuchAlgorithmException var2) {
            return null;
        }
    }

    static class a {
        a() {
        }

        public static String a(byte[] var0) {
            char[] var1 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            StringBuilder var2 = new StringBuilder(var0.length * 2);

            for(int var3 = 0; var3 < var0.length; ++var3) {
                var2.append(var1[(var0[var3] & 240) >> 4]);
                var2.append(var1[var0[var3] & 15]);
            }

            return var2.toString();
        }
    }
}
