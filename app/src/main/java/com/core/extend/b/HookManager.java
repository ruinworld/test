package com.core.extend.b;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import reflect.android.app.ActivityManager;
import reflect.android.app.IActivityManager;
import reflect.android.content.ClipboardManager;
import reflect.android.content.ClipboardManager26;
import reflect.android.content.IClipboard;
import reflect.android.os.ServiceManager;
import reflect.android.util.Singleton;

public class HookManager {
    private static final String TAG = HookManager.class.getSimpleName();

    interface BinderHookCreator {
        BinderHook createBinderHook(Context context, IBinder iBinder);
    }

    /* access modifiers changed from: private */
    public static void hookClipboardManager(final Context context) {
        Object clipboardService = null;
        boolean hasError = false;
        try {
            clipboardService = context.getSystemService(Context.CLIPBOARD_SERVICE);
        } catch (Throwable th) {
            hasError = true;
        }
        if ((hasError || clipboardService == null) && Thread.currentThread() != Looper.getMainLooper().getThread()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    HookManager.hookClipboardManager(context);
                }
            });
        } else if (Build.VERSION.SDK_INT < 26) {
            ClipboardManager.getService.invoke(new Object[0]);
            final IInterface service = ClipboardManager.sService.get();
            if (service != null) {
                ClipboardManager.sService.set(hookService(context, "clipboard", IClipboard.Class, new BinderHookCreator() {
                    public final BinderHook createBinderHook(Context context, IBinder iBinder) {
                        return new IClipboardHook(context, service);
                    }
                }));
            }
        } else if (ClipboardManager26.mService != null) {
            final IInterface service2 = ClipboardManager26.mService.get(clipboardService);
            if (service2 != null) {
                ClipboardManager26.mService.set(clipboardService, hookService(context, "clipboard", IClipboard.Class, new BinderHookCreator() {
                    public final BinderHook createBinderHook(Context context, IBinder iBinder) {
                        return new IClipboardHook(context, service2);
                    }
                }));
            }
        } else if (ClipboardManager.sService != null) {
            try {
                if (ClipboardManager.getService != null) {
                    ClipboardManager.getService.invoke(new Object[0]);
                }
                final IInterface service3 = ClipboardManager.sService.get();
                if (service3 != null) {
                    ClipboardManager.sService.set(hookService(context, "clipboard", IClipboard.Class, new BinderHookCreator() {
                        public final BinderHook createBinderHook(Context context, IBinder iBinder) {
                            return new IClipboardHook(context, service3);
                        }
                    }));
                }
            } catch (Throwable th2) {
                Log.e(TAG, "ERROR: HOOK ClipboardManager failed.", new Object[0]);
            }
        }
    }

    public static void hookActivityManager(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            final IInterface am = ActivityManager.getService.invoke(new Object[0]);
            IInterface service = hookService(context, "activity", IActivityManager.Class, new BinderHookCreator() {
                public BinderHook createBinderHook(Context context, IBinder iBinder) {
                    return new IActivityManagerHook(context, am);
                }
            });
            if (service != null) {
                Singleton.mInstance.set(ActivityManager.IActivityManagerSingleton.get(), service);
            }
        } else {
            IInterface am2 = ActivityManagerNative.getDefault.invoke(new Object[0]);
            if (am2 != null) {
                InvocationHandler handler = new IActivityManagerHook(context, am2);
                IInterface proxy = (IInterface) Proxy.newProxyInstance(am2.getClass().getClassLoader(), new Class[]{IActivityManager.Class}, handler);
                if (Build.VERSION.SDK_INT >= 14) {
                    Singleton.mInstance.set(ActivityManagerNative.gDefault.get(), proxy);
                } else {
                    ActivityManagerNative.gDefault.set(proxy);
                }
                ServiceManager.sCache.get().put("activity", new BinderHook.BinderWrapper(am2.asBinder(), proxy));
            }
        }
        if (Build.VERSION.SDK_INT >= 29) {
//            final IInterface at = ActivityTaskManager.getService.invoke(new Object[0]);
//            IInterface service2 = hookService(context, "activity_task", IActivityTaskManager.Class, new BinderHookCreator() {
//                public BinderHook createBinderHook(Context context, IBinder iBinder) {
//                    return new IActivityTaskManagerHook(context, at);
//                }
//            });
//            if (service2 != null) {
//                Singleton.mInstance.set(ActivityTaskManager.IActivityTaskManagerSingleton.get(), service2);
//            }
        }
    }


    private static IInterface hookService(Context context, String name, Class cls, BinderHookCreator creator) {
        if (context == null || name == null || cls == null || creator == null) {
            return null;
        }
        IBinder service = (IBinder) ServiceManager.checkService.invoke(name);
        if (service == null || (service instanceof BinderHook.BinderWrapper)) {
            return null;
        }
        InvocationHandler handler = creator.createBinderHook(context, service);
        IInterface proxy = (IInterface) Proxy.newProxyInstance(service.getClass().getClassLoader(), new Class[]{cls}, handler);
        ServiceManager.sCache.get().put(name, new BinderHook.BinderWrapper(service, proxy));
        return proxy;
    }

}
