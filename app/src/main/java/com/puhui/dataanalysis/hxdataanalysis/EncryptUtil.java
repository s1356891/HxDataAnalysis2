package com.puhui.dataanalysis.hxdataanalysis;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by chenb on 2018/5/25.
 */

public class EncryptUtil {

    private static byte[] o = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
    public static String md5(String var0) {
        try {
            byte[] var1 = MessageDigest.getInstance("MD5").digest(var0.getBytes("UTF-8"));
            return a(var1);
        } catch (Exception var2) {
            return null;
        }
    }

    public static String sha256(String var0) {
        if(var0 == null) {
            return null;
        } else {
            MessageDigest var1 = null;

            try {
                var1 = MessageDigest.getInstance("SHA-256");
                byte[] var2 = var1.digest(var0.getBytes("UTF-8"));
                return a(var2);
            } catch (Throwable var3) {
                return null;
            }
        }
    }

    public static String a(byte[] var0) {
        try {
            StringBuilder var1 = new StringBuilder();
            byte[] var2 = var0;
            int var3 = var0.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                byte var5 = var2[var4];
                int var6 = var5 & 255;
                if(var6 < 16) {
                    var1.append('0');
                }

                var1.append(Integer.toHexString(var6));
            }

            return var1.toString();
        } catch (Throwable var7) {
            return null;
        }
    }

    public static byte[] encrypt(byte[] var0, byte[] var1) {
        try {
            DESKeySpec var2 = new DESKeySpec(var1);
            SecretKeyFactory var3 = SecretKeyFactory.getInstance("DES");
            SecretKey var4 = var3.generateSecret(var2);
            Cipher var5 = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec var6 = new IvParameterSpec(o);
            var5.init(1, var4, var6);
            byte[] var7 = var5.doFinal(var0);
            return var7;
        } catch (Throwable var8) {
            return null;
        }
    }


    public static byte[] str2Bytes(String var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        DeflaterOutputStream var2 = null;
        Deflater var3 = new Deflater(9, true);

        try {
            var2 = new DeflaterOutputStream(var1, var3);
            var2.write(var0.getBytes("UTF-8"));
        } catch (Throwable var13) {
            ;
        } finally {
            if(var2 != null) {
                try {
                    var2.close();
                } catch (Throwable var12) {
                    ;
                }
            }

        }

        var3.end();
        return var1.toByteArray();
    }


}
