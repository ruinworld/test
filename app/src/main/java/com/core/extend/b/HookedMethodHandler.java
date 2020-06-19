package com.core.extend.b;

import android.content.Context;

import java.lang.reflect.Method;

public class HookedMethodHandler {
    private static final String TAG = HookedMethodHandler.class.getSimpleName();
    protected final Context mHostContext;

    public HookedMethodHandler(Context hostContext) {
        this.mHostContext = hostContext;
    }

    public static class HookContext {
        Object fakedResult;
        Object hookParam;
        boolean useFakedResult;

        private HookContext() {
            this.fakedResult = null;
            this.useFakedResult = false;
            this.hookParam = null;
        }

        public void setParam(Object param) {
            this.hookParam = param;
        }

        public Object getParam() {
            return this.hookParam;
        }

        public void setFakedResult(Object fakedResult2) {
            this.fakedResult = fakedResult2;
            this.useFakedResult = true;
        }
    }

    public Object doHookInner(Object receiver, Method method, Object[] args) throws Throwable {
        long b = System.currentTimeMillis();
        try {
            HookContext hookContext = new HookContext();
            Object invokeResult = null;
            if (!beforeInvoke(receiver, method, args, hookContext)) {
                invokeResult = method.invoke(receiver, args);
            }
            afterInvoke(receiver, method, args, invokeResult, hookContext);
            if (hookContext.useFakedResult) {
                invokeResult = hookContext.fakedResult;
                long time = System.currentTimeMillis() - b;
                if (time > 50) {
                    Log.i(TAG, "doHookInner method(%s.%s) cost %s ms", method.getDeclaringClass().getName(), method.getName(), Long.valueOf(time));
                }
            } else {
                long time2 = System.currentTimeMillis() - b;
                if (time2 > 50) {
                    Log.i(TAG, "doHookInner method(%s.%s) cost %s ms", method.getDeclaringClass().getName(), method.getName(), Long.valueOf(time2));
                }
            }
            return invokeResult;
        } catch (Exception e) {
            Log.e(TAG, e.toString(), new Object[0]);
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
            throw e;
        } catch (Throwable th) {
            long time3 = System.currentTimeMillis() - b;
            if (time3 > 50) {
                Log.i(TAG, "doHookInner method(%s.%s) cost %s ms", method.getDeclaringClass().getName(), method.getName(), Long.valueOf(time3));
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
        return false;
    }

    /* access modifiers changed from: protected */
    public void afterInvoke(Object receiver, Method method, Object[] args, Object invokeResult, HookContext hookContext) throws Throwable {
    }

    public int searchInstance(Object[] args, Class clazz, int begin) {
        if (args == null) {
            return -1;
        }
        while (begin < args.length) {
            if (clazz.isInstance(args[begin])) {
                return begin;
            }
            begin++;
        }
        return -1;
    }

    public static HookedMethodHandler createPackageNameReplacerByFirstArg(Context context) {
        return new replacePackageName(context, 0);
    }

    public static HookedMethodHandler createPackageNameReplacerByLastArg(Context context) {
        return new replacePackageName(context, -1);
    }

    public static class replacePackageName extends HookedMethodHandler {
        protected final int packageNameIndex;

        public replacePackageName(Context context, int packageNameIndex2) {
            super(context);
            this.packageNameIndex = packageNameIndex2;
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            if (args != null) {
                int index = this.packageNameIndex;
                if (index < 0) {
                    index += args.length;
                }
                if (index >= 0 && index < args.length && (args[index] == null || (args[index] instanceof String))) {
                    args[index] = this.mHostContext.getPackageName();
                }
            }
            return super.beforeInvoke(receiver, method, args, hookContext);
        }

        /* access modifiers changed from: protected */
        public void afterInvoke(Object receiver, Method method, Object[] args, Object invokeResult, HookContext hookContext) throws Throwable {
            super.afterInvoke(receiver, method, args, invokeResult, hookContext);
        }
    }
}
