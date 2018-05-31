package com.puhui.dataanalysis.hxdataanalysis.crashhandler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class VersionUtil {
	static PackageManager pm;
	static PackageInfo pi;

	public static String getVersionName(Context ctx)
			throws NameNotFoundException {// 版本名
		pm = ctx.getPackageManager();
		pi = pm.getPackageInfo(ctx.getPackageName(),
				PackageManager.GET_ACTIVITIES);
		String versionName = "";
		if (pi != null) {
			versionName = pi.versionName == null ? "null" : pi.versionName;

		}
		return versionName;
	}

	public static String getVersionCode(Context ctx)
			throws NameNotFoundException {// 版本号
		pm = ctx.getPackageManager();
		pi = pm.getPackageInfo(ctx.getPackageName(),
				PackageManager.GET_ACTIVITIES);
		String versionCode = null;
		if (pi != null) {
			versionCode = pi.versionCode + "";
		}
		return versionCode;
	}

}
