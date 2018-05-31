package com.puhui.dataanalysis.hxdataanalysis;

import java.lang.reflect.Method;

/**
 * Created by chenb on 2018/5/22.
 */

public class ObjMethod {
    private final Object object;
    private final Method method;
    private final int hashCode;
    private boolean registered = true;

    ObjMethod(Object var1, Method var2) {
        if(var1 == null) {
            throw new NullPointerException("EventHandler target cannot be null.");
        } else if(var2 == null) {
            throw new NullPointerException("EventHandler method cannot be null.");
        } else {
            this.object = var1;
            this.method = var2;
            var2.setAccessible(true);
            boolean var3 = true;
            this.hashCode = (31 + var2.hashCode()) * 31 + var1.hashCode();
        }
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public void b() {
        this.registered = false;
    }

    public void handleEvent(Object var1) {
        if(!this.registered) {
//            s.eForInternal(new String[]{this.toString() + " has been invalidated and can no longer handle events."});
        }

        try {
            this.method.invoke(this.object, new Object[]{var1});
        } catch (Throwable var3) {
            ;
        }

    }

    public String toString() {
        return "[EventHandler " + this.method + "]";
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object var1) {
        try {
            if(this == var1) {
                return true;
            } else if(var1 == null) {
                return false;
            } else if(this.getClass() != var1.getClass()) {
                return false;
            } else {
                ObjMethod var2 = (ObjMethod)var1;
                return this.method.equals(var2.method) && this.object == var2.object;
            }
        } catch (Throwable var3) {
//            bk.postSDKError(var3);
            return false;
        }
    }
//    private final Object object;
//    private final Method method;
//    private final int hashCode;
//    private boolean isRegistered=true;
//    public ObjMethod(Object obj, Method method) {
//        if (obj == null) {
//            throw new NullPointerException("EventHandler target cannot be null.");
//        } else if (method == null) {
//            throw new NullPointerException("EventHandler method cannot be null.");
//        } else {
//            this.object = obj;
//            this.method = method;
//            method.setAccessible(true);
//            this.hashCode = (31 + method.hashCode()) * 31 + obj.hashCode();
//        }
//
//    }
//
//    public void unRegister() {
//        this.isRegistered = false;
//    }
//
//    public void handleEvent(Object object) {
//        if (!this.isRegistered) {
//            HXLog.eForDeveloper(this.toString() + " has been invalidated and can no longer handle events");
//        }
//        try {
//            this.method.invoke(this.object, new Object[]{object});
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    public boolean isRegistered() {
//        return this.isRegistered;
//    }
//
//    @Override
//    public String toString() {
//        return "[EventHandler " + this.method + "]";
//    }
//
//
//    @Override
//    public int hashCode() {
//        return this.hashCode;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        try {
//            if (this == obj) {
//                return true;
//            } else if (obj == null) {
//                return false;
//            } else if (this.getClass() != obj.getClass()) {
//                return false;
//            } else {
//                ObjMethod objMethod = (ObjMethod) obj;
//                return this.method.equals(objMethod.method) && this.object == ((ObjMethod) obj).object;
//            }
//        } catch (Throwable getUpdateTime) {
//            getUpdateTime.printStackTrace();
//            return false;
//        }
//    }
}
