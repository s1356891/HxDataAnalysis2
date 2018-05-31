package com.puhui.dataanalysis.hxdataanalysis;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chenb on 2018/5/14.
 */

public class MethodFilter {
    private static final Map mMap = new ConcurrentHashMap();

    private MethodFilter() {

    }
    private static void a(Class var0) {
        try {
            HashMap var1 = new HashMap();
            Method[] var2 = var0.getDeclaredMethods();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Method var5 = var2[var4];
                if(var5.getName().startsWith("onHXData") && var5.getParameterTypes().length == 1) {
                    Class[] var6 = var5.getParameterTypes();
                    if(var6.length != 1) {
//                        s.eForInternal(new String[]{"Method " + var5 + " must have one and only one argument."});
                    }

                    Class var7 = var6[0];
                    if(var7.isInterface()) {
//                        s.eForInternal(new String[]{"Method " + var5 + " must have EventParams argument whose type is EventParams class which can be instantialized."});
                    }

                    if((var5.getModifiers() & 1) == 0) {
//                        s.eForInternal(new String[]{"Method " + var5 + " must be 'public'."});
                    }

                    Object var8 = (Set)var1.get(var7);
                    if(var8 == null) {
                        var8 = new HashSet();
                        var1.put(var7, var8);
                    }

                    ((Set)var8).add(var5);
                }
            }

            mMap.put(var0, var1);
        } catch (Throwable var9) {
//            bk.postSDKError(var9);
        }

    }

    static Map filterMethod(Object var0) {
        HashMap var1 = new HashMap();

        try {
            Class var2 = var0.getClass();
            if(!mMap.containsKey(var2)) {
                a(var2);
            }

            Map var3 = (Map)mMap.get(var2);
            if(var3 != null && !var3.isEmpty()) {
                Iterator var4 = var3.entrySet().iterator();

                while(var4.hasNext()) {
                    Map.Entry var5 = (Map.Entry)var4.next();
                    HashSet var6 = new HashSet();
                    Iterator var7 = ((Set)var5.getValue()).iterator();

                    while(var7.hasNext()) {
                        Method var8 = (Method)var7.next();
                        var6.add(new ObjMethod(var0, var8));
                    }

                    var1.put(var5.getKey(), var6);
                }
            }
        } catch (Throwable var9) {
//            bk.postSDKError(var9);
        }

        return var1;
    }

//    private static void filter(Class clz) {
//        try {
//            HashMap hashMap = new HashMap();
//            Method[] methods = clz.getDeclaredMethods();
//            int mLength = methods.length;
//            for (int getVersionCode = 0; getVersionCode < mLength; getVersionCode++) {
//                Method method = methods[getVersionCode];
//                if (method.getName().startsWith("onHXData") && method.getParameterTypes().length == 1) {
//                    HXLog.eForDeveloper("getVersionCode==" + getVersionCode);
//                    Class[] paramsTypes = method.getParameterTypes();
//                    Class params = paramsTypes[0];
//                    if (params.isInterface()) {
//                        HXLog.eForDeveloper("Method " + method + " must have EventParams argument whose type is EventParams class which can be instantialized.");
//                    }
//
//                    if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
//                        HXLog.eForDeveloper("Method" + method + "must be public");
//                    }
//                    Object var8 = (Set) hashMap.get(params);
//                    if (var8 == null) {
//                        var8 = new HashSet();
//                        hashMap.put(params, var8);
//                    }
//                    ((Set) var8).add(method);
//                }
//            }
//            mMap.put(clz, hashMap);
//        } catch (Throwable getUpdateTime) {
//            getUpdateTime.printStackTrace();
//        }
//    }
//
//    static Map filterMethod(Object object) {
//        HashMap map = new HashMap();
//        try {
//            Class clz = object.getClass();
//            if (!mMap.containsKey(clz)) {
//                filter(clz);
//            }
//            Map map1 = (Map) mMap.get(clz);
//            if (map1 != null && !map1.isEmpty()) {
//                Iterator iterator = map1.entrySet().iterator();
//                while (iterator.hasNext()) {
//                    Map.Entry entry = (Map.Entry) iterator.next();
//                    HashSet set = new HashSet();
//                    Iterator setIterator = ((Set) entry.getValue()).iterator();
//                    while (setIterator.hasNext()) {
//                        Method method = (Method) setIterator.next();
//                        set.add(new ObjMethod(object, method));
//                    }
//                    map.put(entry.getKey(), set);
//                }
//            }
//        } catch (Throwable getUpdateTime) {
//            getUpdateTime.printStackTrace();
//        }
//        return map;
//    }
}
