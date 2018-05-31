package com.puhui.dataanalysis.hxdataanalysis.crashhandler;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";

	private static String VersionName = "VersionName";
	private static String VersionCode = "VersionCode";
	private static String CrashTime = "CrashTime";
	private static String Network_Type = "Network_Type";

	private static String CrashMessage = "很抱歉，程序出现异常，即将重启。";

	// CrashHandler 实例
	private static CrashHandler INSTANCE = new CrashHandler();

	// 程序的 Context 对象
	private Context mContext;

	// 系统默认的 UncaughtException 处理类
	private UncaughtExceptionHandler mDefaultHandler;

	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	/** 保证只有一个 CrashHandler 实例 */
	private CrashHandler() {

	}

	/** 获取 CrashHandler 实例 ,单例模式 */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * 初始化
	 *
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;

		// 获取系统默认的 UncaughtException 处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

		// 设置该 CrashHandler 为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			restart();
			// exit();
		}

	}

	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 使用 Toast 来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();

				Toast.makeText(mContext, CrashMessage, Toast.LENGTH_SHORT)
						.show();
				Looper.loop();
			}
		}.start();
//		TCAgent.onError(mContext, ex);
		SPUtil.setCrashState(mContext);// 设置Crash状态

		try {
			collectDeviceInfo(mContext);// 获取手机信息
		} catch (NameNotFoundException e) {

		}

		try {
			FileUtil.writeCrash2File(mContext,infos, ex);
		} catch (IOException e) {
			e.printStackTrace();
		}// 写入崩溃文件

		return true;
	}

	/**
	 * 获取手机信息
	 *
	 * @param ctx
	 * @throws NameNotFoundException
	 */
	private void collectDeviceInfo(Context ctx) throws NameNotFoundException {

		infos.put(VersionName, VersionUtil.getVersionName(ctx));// 版本名
		infos.put(VersionCode, VersionUtil.getVersionCode(ctx));// 版本号
		infos.put(Network_Type, NetUtil.getNetworkType(ctx));// 手机网络状态
		infos.put(CrashTime, TimeUtil.getTime());// 异常发生时间

		/* 报错信息收集 */
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				infos.put(field.getName(), field.get(null).toString());
			} catch (Exception e) {
				Log.e(TAG, "在收集crash信息出现错误");
			}
		}

	}

	private void restart() {
		restartApplication();

		android.os.Process.killProcess(android.os.Process.myPid());
	}



	private void restartApplication() {
		final Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mContext.startActivity(intent);
	}

}
