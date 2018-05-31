package com.puhui.dataanalysis.hxdataanalysis.crashhandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtil {

	private static String CrashState = "CrashState";

	private static SharedPreferences sp;

	public static void setCrashState(Context ctx) {
		sp = ctx.getSharedPreferences(CrashState, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(CrashState, true);
		editor.commit();
	}

	public static void clearCrashState(Context ctx) {
		sp = ctx.getSharedPreferences(CrashState, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(CrashState, false);
		editor.commit();
	}

	public static boolean loadCrashState(Context ctx) {

		sp = ctx.getSharedPreferences(CrashState, Context.MODE_PRIVATE);
		boolean tag = sp.getBoolean(CrashState, false);
		return tag;
	}

}
