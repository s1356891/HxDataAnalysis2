package com.puhui.dataanalysis.hxdataanalysis;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.File;

/**
 * Created by chenb on 2018/5/24.
 */

public class PackageInfo {
    private static volatile PackageInfo a = null;
    private android.content.pm.PackageInfo b = null;

    private PackageInfo() {
    }

    public static PackageInfo getInstance() {
        if(a == null) {
            Class var0 = PackageInfo.class;
            synchronized(PackageInfo.class) {
                if(a == null) {
                    a = new PackageInfo();
                }
            }
        }

        return a;
    }

    private synchronized boolean i(Context var1) {
        try {
            if(this.b == null) {
                this.b = var1.getPackageManager().getPackageInfo(var1.getPackageName(),64);
            }

            return true;
        } catch (Throwable var3) {
            return false;
        }
    }

    public String getPackageName(Context var1) {
        try {
            if(var1 == null) {
                return null;
            } else {
                this.i(var1);
                return var1.getPackageName();
            }
        } catch (Throwable var3) {
            return "";
        }
    }

    public int getVersionCode(Context var1) {
        if(var1 == null) {
            return -1;
        } else {
            try {
                return !this.i(var1)?-1:this.b.versionCode;
            } catch (Throwable var3) {
                return -1;
            }
        }
    }

    public String getVersionName(Context var1) {
        if(var1 == null) {
            return "unknown";
        } else {
            try {
                return !this.i(var1)?"unknown":this.b.versionName;
            } catch (Throwable var3) {
                return "unknown";
            }
        }
    }

    public long getInstallTime(Context var1) {
        if(var1 == null) {
            return -1L;
        } else {
            try {
                if(!this.i(var1)) {
                    return -1L;
                }

                if(HxUtils.checkVersion(9)) {
                    return this.b.firstInstallTime;
                }
            } catch (Throwable var3) {
                ;
            }

            return -1L;
        }
    }

    public long getUpdateTime(Context var1) {
        if(var1 == null) {
            return -1L;
        } else {
            try {
                if(!this.i(var1)) {
                    return -1L;
                }

                if(HxUtils.checkVersion(9)) {
                    return this.b.lastUpdateTime;
                }
            } catch (Throwable var3) {
                ;
            }

            return -1L;
        }
    }

    public long getSourceDir(Context var1) {
        if(var1 == null) {
            return -1L;
        } else {
            try {
                this.i(var1);
                String var2 = var1.getApplicationInfo().sourceDir;
                return (new File(var2)).length();
            } catch (Throwable var3) {
                return -1L;
            }
        }
    }

    public String getSignatureInfo(Context var1) {
        if(var1 == null) {
            return null;
        } else {
            try {
                if(!this.i(var1)) {
                    return null;
                } else {
                    Signature[] var2 = this.b.signatures;
                    if(var2.length < 1) {
                        return null;
                    } else {
                        StringBuffer var3 = new StringBuffer();
                        var3.append(var2[0].toCharsString());
                        return var3.toString();
                    }
                }
            } catch (Throwable var4) {
                return null;
            }
        }
    }

    public String getLabel(Context var1) {
        if(var1 == null) {
            return null;
        } else {
            try {
                this.i(var1);
                return var1.getApplicationInfo().loadLabel(var1.getPackageManager()).toString();
            } catch (Throwable var3) {
                return null;
            }
        }
    }
}
