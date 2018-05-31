package com.puhui.dataanalysis.hxdataanalysis.crashhandler;

import android.content.Context;
import android.util.Log;



//Logimport org.apache.http.entity.ByteArrayEntity;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.protocol.HTTP;
//import org.json.JSONException;
//import org.json.JSONObject;

import com.puhui.dataanalysis.hxdataanalysis.HXLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;


@SuppressWarnings("deprecation")
public class HttpUtil {
    public static String URL = "http://172.16.100.139:8080/New/Hello";
//    // private static String URL =
//    // "http://177.17.17.140:80/zzbz/index/Api/postMobileData";
//    private static String URL_FILE = "http://zzbz.servicedesk.hrbb.com.cn:8080/Api/postFiles";
//    // private static String URL_FILE =
//    // "http://177.17.17.140/zzbz/index/Api/postFiles";
//
//    private static final String APPLICATION_JSON = "application/x-www-form-urlencoded";
//
//    private static AsyncHttpClient client = new AsyncHttpClient();
//

    public static void sendFile2ecc(Context context, String fileNo
                                    ) throws FileNotFoundException {
        final File crashFile = new File(FileUtil.getPath(context) + "CrashFile.log");

//        File crashNoFile = new File(FileUtil.getPath(context) + fileNo + ".crash.log");

//        crashFile.renameTo(crashNoFile);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RandomAccessFile accessFile = new RandomAccessFile(crashFile, "rw");
                    byte[] bytes = new byte[(int) accessFile.length()];
                    accessFile.read(bytes);
                    HXLog.eForDeveloper(new String(bytes)+"====================================");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                uploadFile(URL,crashFile.getAbsolutePath());
            }
        }).start();

    }



    /**
     * Java原生的API可用于发送HTTP请求，即java.net.URL、java.net.URLConnection，这些API很好用、很常用，
     * 但不够简便；
     *
     * 1.通过统一资源定位器（java.net.URL）获取连接器（java.net.URLConnection） 2.设置请求的参数 3.发送请求
     * 4.以输入流的形式获取返回内容 5.关闭输入流
     *
     * @author H__D
     *
     */



        /**
         * 多文件上传的方法
         *
         * @param actionUrl：上传的路径
         * @param uploadFilePaths：需要上传的文件路径，数组
         * @return
         */
        @SuppressWarnings("finally")
        public static String uploadFile(String actionUrl, String uploadFilePaths) {
            String end = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            DataOutputStream ds = null;
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;

            try {
                // 统一资源
                URL url = new URL(actionUrl);
                // 连接类的父类，抽象类
                URLConnection urlConnection = url.openConnection();
                // http的连接类
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

                // 设置是否从httpUrlConnection读入，默认情况下是true;
                httpURLConnection.setDoInput(true);
                // 设置是否向httpUrlConnection输出
                httpURLConnection.setDoOutput(true);
                // Post 请求不能使用缓存
                httpURLConnection.setUseCaches(false);
                // 设定请求的方法，默认是GET
                httpURLConnection.setRequestMethod("POST");
                // 设置字符编码连接参数
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                // 设置字符编码
                httpURLConnection.setRequestProperty("Charset", "UTF-8");
                // 设置请求内容类型
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                // 设置DataOutputStream
                ds = new DataOutputStream(httpURLConnection.getOutputStream());

                    String uploadFile = uploadFilePaths;
                    String filename = uploadFile.substring(uploadFile.lastIndexOf("//") + 1);
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; " + "name=\"file" + 1 + "\";filename=\"" + filename
                            + "\"" + end);
                    ds.writeBytes(end);
                    FileInputStream fStream = new FileInputStream(uploadFile);
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int length = -1;
                    while ((length = fStream.read(buffer)) != -1) {
                        ds.write(buffer, 0, length);
                    }
                        ds.writeBytes(end);
                /* close streams */
                    fStream.close();

                ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
                ds.flush();
                if (httpURLConnection.getResponseCode() >= 300) {
                    throw new Exception(
                            "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
                }

                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    reader = new BufferedReader(inputStreamReader);
                    resultBuffer = new StringBuffer();
                    while ((tempLine = reader.readLine()) != null) {
                        resultBuffer.append(tempLine);
                        resultBuffer.append("\n");
                    }
                    new File(uploadFilePaths).delete();
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (ds != null) {
                    try {
                        ds.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                return resultBuffer.toString();
            }
        }





//    private static void uploadFile(File file) {
//        String end = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//        String actionUrl =URL;
//        HttpURLConnection con=null;
//        try {
//            URL url = new URL(actionUrl);
//            con = (HttpURLConnection) url.openConnection();
//            /* 允许Input、Output，不使用Cache */
//            con.setDoInput(true);
//            con.setDoOutput(true);
//            con.setUseCaches(false);
//            /* 设置传送的method=POST */
//            con.setRequestMethod("POST");
//            /* setRequestProperty */
//            con.setRequestProperty("Connection", "Keep-Alive");
//            con.setRequestProperty("Charset", "UTF-8");
//            con.setRequestProperty("Content-Type",
//                    "multipart/form-data;boundary=" + boundary);
//            /* 设置DataOutputStream */
//            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
//            ds.writeBytes(twoHyphens + boundary + end);
//            ds.writeBytes("Content-Disposition: form-data; "
//                    + "name=\"file1\";filename=\"" + file.getName() + "\"" + end);
//            ds.writeBytes(end);
//            /* 取得文件的FileInputStream */
//            FileInputStream fStream = new FileInputStream(file);
//            /* 设置每次写入1024bytes */
//            int bufferSize = 1024;
//            byte[] buffer = new byte[bufferSize];
//            int length = -1;
//            /* 从文件读取数据至缓冲区 */
//            while ((length = fStream.read(buffer)) != -1) {
//                /* 将资料写入DataOutputStream中 */
//                ds.write(buffer, 0, length);
//            }
//            ds.writeBytes(end);
//            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
//            /* close streams */
//            fStream.close();
//            ds.flush();
//            /* 取得Response内容 */
//            InputStream is = con.getInputStream();
//            int ch;
//            StringBuffer b = new StringBuffer();
//            while ((ch = is.read()) != -1) {
//                b.append((char) ch);
//            }
//            /* 将Response显示于Dialog */
//
//            /* 关闭DataOutputStream */
//            ds.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            if (con != null) {
//                con.disconnect();
//            }
//        }
//    }

}
