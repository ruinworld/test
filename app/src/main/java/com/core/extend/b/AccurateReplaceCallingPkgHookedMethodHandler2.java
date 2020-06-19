package com.core.extend.b;

import android.content.Context;

import java.lang.reflect.Method;

public class AccurateReplaceCallingPkgHookedMethodHandler2 extends HookedMethodHandler {
    public static final int INVALID_INDEX = -1;

    public AccurateReplaceCallingPkgHookedMethodHandler2(Context hostContext) {
        super(hostContext);
    }

    /* access modifiers changed from: protected */
    public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
        int index = obtainReplaceIndex();
        if (index >= 0) {
            args[index] = this.mHostContext.getPackageName();
        }
        return super.beforeInvoke(receiver, method, args, hookContext);
    }

    /* access modifiers changed from: protected */
    public int obtainReplaceIndex() {
        return -1;
    }
}
