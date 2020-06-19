package com.core.extend.b;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.IInterface;

import java.lang.reflect.Method;

final class IClipboardHook extends BinderHook {
    IClipboardHook(Context context, IInterface iInterface) {
        super(context, iInterface);
    }

    /* access modifiers changed from: protected */
    public final void initHookedMethods() {
        Log.d("XYZ","initHookedMethods","初始化");
        this.sHookedMethodHandlers.put("getPrimaryClip", new ClipBoardProxy(this.mHostContext, -1));
        if (Build.VERSION.SDK_INT > 17) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.sHookedMethodHandlers.put("setPrimaryClip", new ClipBoardProxy(this.mHostContext, 1));
            } else {
                this.sHookedMethodHandlers.put("setPrimaryClip", createPackageNameReplacerByLastArg(this.mHostContext));
            }
            this.sHookedMethodHandlers.put("getPrimaryClipDescription", createPackageNameReplacerByLastArg(this.mHostContext));
            this.sHookedMethodHandlers.put("hasPrimaryClip", createPackageNameReplacerByLastArg(this.mHostContext));
            this.sHookedMethodHandlers.put("addPrimaryClipChangedListener", createPackageNameReplacerByLastArg(this.mHostContext));
            this.sHookedMethodHandlers.put("removePrimaryClipChangedListener", createPackageNameReplacerByLastArg(this.mHostContext));
            this.sHookedMethodHandlers.put("hasClipboardText", createPackageNameReplacerByLastArg(this.mHostContext));
            this.sHookedMethodHandlers.put("shouldShowClipboardDialog", createPackageNameReplacerByLastArg(this.mHostContext));
        }
    }

    /* access modifiers changed from: protected */
    public final boolean isEnabled() {
        return true;
    }

    private static HookedMethodHandler createPackageNameReplacerByLastArg(Context context) {
        return new ClipBoardProxy(context, -1);
    }

    static class ClipBoardProxy extends HookedMethodHandler.replacePackageName {
        ClipBoardProxy(Context context, int packageNameIndex) {
            super(context, packageNameIndex);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookedMethodHandler.HookContext hookContext) throws Throwable {
            try {
                //Log.d("bobo", "beforeInvoke" + method.getName() + ProcessUtils.isPluginManagerService(), new Object[0]);
                if ("getPrimaryClip".equals(method.getName())) {
                    Log.d("bobo", "getPrimaryClip", new Object[0]);
                    ClipData clipData = (ClipData) method.invoke(receiver, args);
                    Log.d("bobo", "getPrimaryClip", new Object[0]);
                    if (clipData == null) {
                        Log.d("bobo", "clipData==null", new Object[0]);
                        return super.beforeInvoke(receiver, method, args, hookContext);
                    }
                    ClipData.Item item = clipData.getItemAt(0);
                    Log.d("bobo", "item==" + item.toString(), new Object[0]);
                    CharSequence text = item.getText();
                    Log.d("bobo", "text==" + text, new Object[0]);
                    ClipData data = ClipData.newPlainText(null, Base64Tool.decode(text + ""));
                    Log.d("bobo", "data==" + data.toString(), new Object[0]);
                    hookContext.setFakedResult(data);
                    return false;
                } else if ("setPrimaryClip".equals(method.getName())) {
                    Log.d("bobo", "setPrimaryClip", new Object[0]);
                    args[0] = ClipData.newPlainText(null, Base64Tool.encode(((ClipData)args[0]).getItemAt(0).getText().toString()));
                    method.invoke(receiver, args);
                    return false;
                } else {
                    if ("shouldShowClipboardDialog".equals(method.getName())) {
                        hookContext.setFakedResult(false);
                        return false;
                    }
                    return super.beforeInvoke(receiver, method, args, hookContext);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }
}
