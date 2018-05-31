package com.puhui.dataanalysis.hxdataanalysis;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by chenb on 2018/5/28.
 */

public class CustomFileFilter implements FileFilter {
    @Override
    public boolean accept(File var1) {
        try {
            if(var1 != null) {
                String var2 = var1.getName();
                if(var2 != null && var2.startsWith("cpu")) {
                    for(int var3 = 3; var3 < var2.length(); ++var3) {
                        if(var2.charAt(var3) < 48 || var2.charAt(var3) > 57) {
                            return false;
                        }
                    }

                    return true;
                }
            }
        } catch (Throwable var4) {
            ;
        }

        return false;
    }
}
