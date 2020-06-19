package com.core.extend.b;

import android.content.Context;
import android.os.Build;
import android.os.RemoteException;

import java.lang.reflect.Method;

public class ReplaceCallingPackageHookedMethodHandler extends HookedMethodHandler {
    public ReplaceCallingPackageHookedMethodHandler(Context hostContext) {
        super(hostContext);
    }

    /* access modifiers changed from: protected */
    public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
        if (Build.VERSION.SDK_INT >= 15 && args != null && args.length > 0) {
            for (int index = 0; index < args.length; index++) {
                if (args[index] != null && (args[index] instanceof String) && isPackagePlugin((String) args[index])) {
                    args[index] = this.mHostContext.getPackageName();
                }
            }
        }
        return super.beforeInvoke(receiver, method, args, hookContext);
    }

    private static boolean isPackagePlugin(String packageName) throws RemoteException {
        return false;
    }
}
