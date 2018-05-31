package com.puhui.dataanalysis.hxdataanalysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by chenb on 2018/5/22.
 */

public class RegisterManager {
    private final ConcurrentMap concurrentMap = new ConcurrentHashMap();
    private static volatile RegisterManager registerManager = null;
    private HashMap mHashMap = new HashMap();
    private final ThreadLocal threadLocal = new AT(this);
    private final ThreadLocal threadLocal2 = new AU(this);

    public static RegisterManager getInstance() {
        if (registerManager == null) {
            Class var0 = RegisterManager.class;
            synchronized (var0) {
                if (registerManager == null) {
                    registerManager = new RegisterManager();
                }
            }
        }

        return registerManager;
    }

    private RegisterManager() {
    }
    public void register(Object var1) {
        try {
            if(var1 == null) {
                return;
            }

            Map var2 = MethodFilter.filterMethod(var1);
            Iterator var3 = var2.keySet().iterator();

            while(var3.hasNext()) {
                Class var4 = (Class)var3.next();
                Object var5 = (Set)this.concurrentMap.get(var4);
                if(var5 == null) {
                    CopyOnWriteArraySet var6 = new CopyOnWriteArraySet();
                    var5 = (Set)this.concurrentMap.putIfAbsent(var4, var6);
                    if(var5 == null) {
                        var5 = var6;
                    }
                }

                Set var8 = (Set)var2.get(var4);
                if(!((Set)var5).addAll(var8)) {
                    return;
                }
            }
        } catch (Throwable var7) {
        }

    }

//    public void unregister(Object var1) {
//        try {
//            if(var1 == null) {
//                return;
//            }
//
//            Map var2 = MethodFilter.filterMethod(var1);
//            Iterator var3 = var2.entrySet().iterator();
//
//            while(var3.hasNext()) {
//                Map.Entry var4 = (Map.Entry)var3.next();
//                Set var5 = this.EventParams((Class)var4.getKey());
//                Collection var6 = (Collection)var4.getValue();
//                if(var5 == null || !var5.containsAll(var6)) {
//                    return;
//                }
//
//                Iterator var7 = var5.iterator();
//
//                while(var7.hasNext()) {
//                    aw var8 = (aw)var7.next();
//                    if(var6.contains(var8)) {
//                        var8.jsonObject();
//                    }
//                }
//
//                var5.removeAll(var6);
//            }
//        } catch (Throwable var9) {
//            bk.postSDKError(var9);
//        }
//
//    }

    public void post(Object var1) {
        HXLog.eForDeveloper("post");
        try {
            if(var1 != null) {
                Set var2 = this.b(var1.getClass());
                boolean var3 = false;
                Iterator var4 = var2.iterator();

                while(true) {
                    Set var6;
                    do {
                        do {
                            if(!var4.hasNext()) {
                                if(!var3 && !(var1 instanceof RegisterObj)) {
                                    this.post(new RegisterObj(this, var1));
                                }

                                this.b();
                                return;
                            }

                            Class var5 = (Class)var4.next();
                            var6 = this.a(var5);
                        } while(var6 == null);
                    } while(var6.isEmpty());

                    var3 = true;
                    Iterator var7 = var6.iterator();

                    while(var7.hasNext()) {
                        ObjMethod var8 = (ObjMethod)var7.next();
                        this.a(var1, var8);
                    }
                }
            }
        } catch (Throwable var9) {
        }
    }

    protected void a(Object var1, ObjMethod var2) {
        try {
            ((ConcurrentLinkedQueue) this.threadLocal.get()).offer(new EventParams(var1, var2));
        } catch (Throwable var4) {
            var4.printStackTrace();
        }

    }

    protected void b() {
        try {
            HXLog.eForDeveloper("poll");
            if(!((Boolean)this.threadLocal2.get()).booleanValue()) {
                this.threadLocal2.set(Boolean.valueOf(true));

                while(true) {
                    EventParams var1 = (EventParams)((ConcurrentLinkedQueue)this.threadLocal.get()).poll();
                    if(var1 == null) {
                        return;
                    }
                    if(var1.b.isRegistered()) {
                        this.b(var1.a, var1.b);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            this.threadLocal2.set(Boolean.valueOf(false));
        }

    }

    protected void b(Object var1, ObjMethod var2) {
        try {
            var2.handleEvent(var1);
        } catch (Throwable var4) {
        }

    }

    Set a(Class var1) {
        try {
            return (Set)this.concurrentMap.get(var1);
        } catch (Throwable var3) {
            return null;
        }
    }

    Set b(Class var1) {
        try {
            Set var2 = (Set)this.mHashMap.get(var1);
            if(var2 == null) {
                var2 = this.c(var1);
                this.mHashMap.put(var1, var2);
            }

            return var2;
        } catch (Throwable var3) {
            return null;
        }
    }

    private Set c(Class var1) {
        try {
            LinkedList var2 = new LinkedList();
            HashSet var3 = new HashSet();
            var2.add(var1);

            while(!var2.isEmpty()) {
                Class var4 = (Class)var2.remove(0);
                var3.add(var4);
                Class var5 = var4.getSuperclass();
                if(var5 != null) {
                    var2.add(var5);
                }
            }

            return var3;
        } catch (Throwable var6) {
            return null;
        }
    }

    static class EventParams {
        final Object a;
        final ObjMethod b;

        public EventParams(Object var1, ObjMethod var2) {
            this.a = var1;
            this.b = var2;
        }
    }

//    public void register(Object object) {
//        if (object == null) {
//            return;
//        }
//        try {
//            Map map = MethodFilter.filterMethod(object);
//            Iterator iterator = map.keySet().iterator();
//            while (iterator.hasNext()) {
//                Class clz = (Class) iterator.next();
//                Object obj = (Set)this.concurrentMap.get(clz);
//                if (obj == null) {
//                    CopyOnWriteArraySet arraySet = new CopyOnWriteArraySet();
//                    obj = (Set)this.concurrentMap.putIfAbsent(clz, arraySet);
//                    if (obj == null) {
//                        obj = arraySet;
//                    }
//                }
//                Set set = (Set) map.get(clz);
//                HXLog.eForDeveloper(concurrentMap.toString());
//                if (!((Set) obj).addAll(set)) {
//                    return;
//                }
//                HXLog.eForDeveloper(concurrentMap.toString());
//            }
//        } catch (Throwable getUpdateTime) {
//            getUpdateTime.printStackTrace();
//        }
//    }
//
//    public void post(Object obj) {
//        try {
//            if (obj != null) {
//                Set set = this.getClass(obj.getClass());
//                boolean flag = false;
//                Iterator iterator = set.iterator();
//                while (true) {
//                    Set set2;
//                    do {
//                        do {
//                            if (!iterator.hasNext()) {
//                                if (!flag && !(obj instanceof RegisterObj)) {
//                                    this.post(new RegisterObj(this, obj));
//                                }
//                                this.handleEvent();
//                                return;
//                            }
//                            Class clz = (Class) iterator.next();
//                            set2 = this.getHandleMethodSet(clz);
//                            HXLog.eForDeveloper(set2.toString());
//                        } while (set2 == null);
//                    } while (set2.isEmpty());
//                    flag = true;
//                    Iterator iterator1 = set2.iterator();
//                    while (iterator1.hasNext()) {
//                        ObjMethod objMethod = (ObjMethod) iterator.next();
//                        this.setHandleMethodSet(obj, objMethod);
//                    }
//                }
//            }
//        } catch (Throwable getUpdateTime) {
//            getUpdateTime.printStackTrace();
//        }
//    }
//
//
//    protected void setHandleMethodSet(Object obj, ObjMethod objMethod) {
//        try {
//            HXLog.eForDeveloper("offer");
//            ((ConcurrentLinkedQueue) threadLocal.get()).offer(new RegisterMethod(obj, objMethod));
//        } catch (Throwable throwable) {
//
//        }
//    }
//
//    protected void doIt(Object obj, ObjMethod objMethod) {
//        try {
//            HXLog.eForDeveloper("doIt");
//            objMethod.handleEvent(obj);
//        } catch (Throwable throwable) {
//
//        }
//    }
//
//    protected void handleEvent() {
//        HXLog.eForDeveloper("handleEvent");
//        try {
//            if (!((Boolean) this.threadLocal2.get()).booleanValue()) {
//                threadLocal2.set(Boolean.valueOf(true));
//                while (true) {
//                    RegisterManager.RegisterMethod registerMethod = (RegisterMethod) ((ConcurrentLinkedQueue) threadLocal.get()).poll();
//                    if (registerMethod == null) {
//                        HXLog.eForDeveloper("registerMethod == null");
//                        return;
//                    }
//                    HXLog.eForDeveloper("registerMethod.objMethod.isRegistered()"+registerMethod.objMethod.isRegistered());
//                    if (registerMethod.objMethod.isRegistered()) {
//                        HXLog.eForDeveloper("doIt");
//                        this.doIt(registerMethod.object, registerMethod.objMethod);
//                    }
//                }
//            }
//        } finally {
//            this.threadLocal2.set(Boolean.valueOf(false));
//        }
//    }
//
//    Set getHandleMethodSet(Class clz) {
//        try {
//            HXLog.eForDeveloper("getHandleMethodSet");
//            return (Set) this.concurrentMap.get(clz);
//        } catch (Throwable throwable) {
//            return null;
//        }
//    }
//
//
//    Set getClass(Class obj) {
//        try {
//            Set set = (Set) this.mHashMap.get(obj);
//            if (set == null) {
//                set = this.getClassWithSuper(obj);
//                this.mHashMap.put(obj, set);
//            }
//            return set;
//        } catch (Throwable throwable) {
//            return null;
//        }
//    }
//
//
//    private Set getClassWithSuper(Class var1) {
//        try {
//            LinkedList var2 = new LinkedList();
//            HashSet var3 = new HashSet();
//            var2.add(var1);
//
//            while (!var2.isEmpty()) {
//                Class var4 = (Class) var2.remove(0);
//                var3.add(var4);
//                Class var5 = var4.getSuperclass();
//                if (var5 != null) {
//                    var2.add(var5);
//                }
//            }
//
//            return var3;
//        } catch (Throwable var6) {
//            return null;
//        }
//    }
//
//
//    static class RegisterMethod {
//        final Object object;
//        final ObjMethod objMethod;
//
//        public RegisterMethod(Object object, ObjMethod objMethod) {
//            this.object = object;
//            this.objMethod = objMethod;
//        }
//    }


}
