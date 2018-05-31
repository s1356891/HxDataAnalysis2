package com.puhui.dataanalysis.hxdataanalysis.crashhandler;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class FileUtil {

//	private static String CrashFilePath = "/doublet/crash/";// 深路径加密
    private static String CrashFilePath;
	private static int MAX_SIZE = 10485760;// 10MB

	/**
	 *
	 * @param infos
	 *            设备环境信息
	 * @param ex
	 *            异常堆栈信息
	 * @throws IOException
	 */
	public static void writeCrash2File(Context mContext, Map<String, String> infos, Throwable ex)
			throws IOException {
		CrashFilePath = "/data/data/" + mContext.getPackageName() + "/crash/";

		File file = new File(getPath(mContext) + "CrashFile.log");

		// 1.写入消息
		/* 拼接字段 */
		StringBuffer sb = new StringBuffer();

		if (file.exists()) {// 文件已存在

			sb.append("VersionName" + "=" + infos.get("VersionName") + "\n");
			sb.append("VersionCode" + "=" + infos.get("VersionCode") + "\n");
			sb.append("CrashTime" + "=" + infos.get("CrashTime") + "\n");
			sb.append("Network_Type" + "=" + infos.get("Network_Type") + "\n");

		} else {// 文件不存在-->获取设备等信息
			for (Map.Entry<String, String> entry : infos.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append(key + "=" + value + "\n");
			}
			sb.append("**************************************************************\n");

		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);

		sb.append("=================================================\n");

		// 2.检测当前崩溃日志文件大小
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			long size = fis.available();
			Log.i("FileSize", size + "");
			if (size > MAX_SIZE) {// 如果大于1MB则清理
				// TODO 清理规则
				deleteCrashFile(mContext,"");
			}

			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			// 写入相关Log到文件
			bw.write(sb.toString());
			bw.close();
			fw.close();
			fis.close();

		} else {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			file.createNewFile();
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			// 写入相关Log到文件
			bw.write(sb.toString());
			bw.newLine();
			bw.close();
			fw.close();

		}

	}

	/**
	 * 获取日志路径
	 *
	 * @return
	 */
	public static String getPath(Context mContext) {
		String filePath = "";
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {
//			String sdcardPath = Environment.getExternalStorageDirectory()
//					.getPath();
//			filePath = sdcardPath + CrashFilePath;
//
//		} else {
//			filePath = Environment.getDataDirectory().getPath() + CrashFilePath;
//		}
//
//		return filePath;
		filePath = "/data/data/" + mContext.getPackageName() + "/crash/";
//		filePath = FileTool.getFileRoot()+ mContext.getPackageName() + "/crash/";

		return filePath;
	}

	/**
	 * 清理日志文件
	 *
	 * @param ids
	 */
	public static void deleteCrashFile(Context mContext, String ids) {
		File file = new File(FileUtil.getPath(mContext) + "CrashFile.log");
		if(file.exists()) {
			file.delete();
		}
		//
		File crashNoFile = new File(FileUtil.getPath(mContext) + ids + ".crash.log");
		if(crashNoFile.exists()) {
			crashNoFile.delete();
		}
	}

	public static boolean isCrashFileExist(Context mContext) {
		File file = new File(FileUtil.getPath(mContext) + "CrashFile.log");
		return file.exists();
	}

}
