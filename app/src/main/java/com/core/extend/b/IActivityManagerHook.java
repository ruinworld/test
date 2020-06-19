package com.core.extend.b;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;

public class IActivityManagerHook extends BinderHook {
    /* access modifiers changed from: private */
    public static final String TAG = IActivityManagerHook.class.getSimpleName();
    /* access modifiers changed from: private */
    public final IBinder origBinder;

    public IActivityManagerHook(Context context, IInterface iInterface) {
        super(context, iInterface);
        this.origBinder = iInterface.asBinder();
    }

    /* access modifiers changed from: protected */
    public boolean isEnabled() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void initHookedMethods() {
        this.sHookedMethodHandlers.put("asBinder", new asBinder(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivity", new startActivity(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityAsUser", new startActivityAsUser(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityAsCaller", new startActivityAsCaller(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityAndWait", new startActivityAndWait(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityWithConfig", new startActivityWithConfig(this.mHostContext));
        this.sHookedMethodHandlers.put("startNextMatchingActivity", new startNextMatchingActivity(this.mHostContext));
        this.sHookedMethodHandlers.put("startActivityFromRecents", new startActivityFromRecents(this.mHostContext));
        this.sHookedMethodHandlers.put("getAppTasks", new getAppTasks(this.mHostContext));
        this.sHookedMethodHandlers.put("addAppTask", new addAppTask(this.mHostContext));
        this.sHookedMethodHandlers.put("getProcessesInErrorState", new getProcessesInErrorState(this.mHostContext));
        this.sHookedMethodHandlers.put("getRunningServiceControlPanel", new getRunningServiceControlPanel(this.mHostContext));
        this.sHookedMethodHandlers.put("startInstrumentation", new startInstrumentation(this.mHostContext));
        this.sHookedMethodHandlers.put("getActivityClassForToken", new getActivityClassForToken(this.mHostContext));
        this.sHookedMethodHandlers.put("getPackageForToken", new getPackageForToken(this.mHostContext));
        this.sHookedMethodHandlers.put("getMyMemoryState", new getMyMemoryState(this.mHostContext));
        this.sHookedMethodHandlers.put("navigateUpTo", new navigateUpTo(this.mHostContext));
        this.sHookedMethodHandlers.put("isUserRunning", new isUserRunning(this.mHostContext));
        if (Build.VERSION.SDK_INT >= 21) {
            this.sHookedMethodHandlers.put("shouldUpRecreateTask", new shouldUpRecreateTask(this.mHostContext));
        } else {
            this.sHookedMethodHandlers.put("targetTaskAffinityMatchesActivity", new targetTaskAffinityMatchesActivity(this.mHostContext));
        }
        this.sHookedMethodHandlers.put("setAppLockedVerifying", new setAppLockedVerifying(this.mHostContext));
        this.sHookedMethodHandlers.put("updateConfiguration", new updateConfiguration(this.mHostContext));
        this.sHookedMethodHandlers.put("updatePersistentConfiguration", new updatePersistentConfiguration(this.mHostContext));
        this.sHookedMethodHandlers.put("handleApplicationCrash", new handleApplicationCrash(this.mHostContext));
        if (Build.VERSION.SDK_INT >= 21) {
            this.sHookedMethodHandlers.put("addPackageDependency", new addPackageDependency(this.mHostContext));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.sHookedMethodHandlers.put("registerUidObserver", new registerUidObserver(this.mHostContext));
            this.sHookedMethodHandlers.put("unregisterUidObserver", new unregisterUidObserver(this.mHostContext));
        }
        if (Build.VERSION.SDK_INT >= 29) {
        }
    }

    private static class addPackageDependency extends HookedMethodHandler {
        public addPackageDependency(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            hookContext.setFakedResult((Object) null);
            return true;
        }
    }

    private static class handleApplicationCrash extends HookedMethodHandler {
        public handleApplicationCrash(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            hookContext.setFakedResult((Object) null);
            System.exit(0);
            return true;
        }
    }

    private static class targetTaskAffinityMatchesActivity extends HookedMethodHandler {
        public targetTaskAffinityMatchesActivity(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            if (args == null || args.length <= 0 || !(args[0] instanceof IBinder)) {
                return super.beforeInvoke(receiver, method, args, hookContext);
            }
            hookContext.setFakedResult(true);
            return true;
        }
    }

    private static class registerUidObserver extends HookedMethodHandler {
        public registerUidObserver(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            hookContext.setFakedResult((Object) null);
            return true;
        }
    }

    private static class unregisterUidObserver extends HookedMethodHandler {
        public unregisterUidObserver(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            hookContext.setFakedResult((Object) null);
            return true;
        }
    }

    private static class shouldUpRecreateTask extends ReplaceCallingPackageHookedMethodHandler {
        public shouldUpRecreateTask(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookedMethodHandler.HookContext hookContext) throws Throwable {
            hookContext.setFakedResult(false);
            return true;
        }
    }

    private class asBinder extends HookedMethodHandler {
        public asBinder(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public final boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            hookContext.setFakedResult(IActivityManagerHook.this.origBinder);
            return true;
        }
    }

    private static class startActivity extends AccurateReplaceCallingPkgHookedMethodHandler2 {
        public startActivity(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean doReplaceIntentForStartActivityAPILow(Object[] args, int intentOfArgIndex) throws RemoteException {
            if (intentOfArgIndex >= 0 && args != null && args.length > 6 && args.length > intentOfArgIndex) {
                Intent intent = (Intent) args[intentOfArgIndex];
                if (intent != null){
                    Log.d(TAG,"intent:"+intent.toString());
                }else{
                    Log.d(TAG,"intent is null");
                }
            }
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookedMethodHandler.HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "beforeInvoke", new Object[0]);
            int intentOfArgIndex = IActivityManagerHook.findFirstIntentIndexInArgs(args);
            Log.d(TAG,"intentOfArgIndex:"+intentOfArgIndex);
            Log.d(TAG,"args.length:"+args.length);
            Intent intent = (Intent) args[intentOfArgIndex];
            ComponentName component = intent.getComponent();
            String className = component.getClassName();
            Log.d(TAG,"className:"+className);
            if (className.equals("SecondActivity")){
                return false;
            }

            ComponentName componentName = new ComponentName("com.core.extend.b", "SecondActivity");
            Intent newIntent = new Intent();
            newIntent.setComponent(componentName);
            //newIntent.setFlags(intent.getFlags());
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mHostContext.startActivity(newIntent);
//            args[intentOfArgIndex] = newIntent;
//            args[1] = mHostContext.getPackageName();
            return false;
        }

        @Override
        public void afterInvoke(Object receiver, Method method, Object[] args, Object invokeResult, HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "afterInvoke", new Object[0]);

            int intentOfArgIndex = IActivityManagerHook.findFirstIntentIndexInArgs(args);
            Log.d(TAG,"intentOfArgIndex:"+intentOfArgIndex);
            Log.d(TAG,"args.length:"+args.length);
            Intent intent = (Intent) args[intentOfArgIndex];
            intent.setClassName("com.core.extend.b", "ThirdActivity");

            super.afterInvoke(receiver, method, args, invokeResult, hookContext);
        }

        /* access modifiers changed from: protected */
        public int obtainReplaceIndex() {
            if (Build.VERSION.SDK_INT >= 18) {
                return 1;
            }
            return -1;
        }
    }

    private static class startActivityAsUser extends startActivity {
        public startActivityAsUser(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookedMethodHandler.HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "startActivityAsUser", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }

    private static class startActivityAsCaller extends startActivity {
        public startActivityAsCaller(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookedMethodHandler.HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "startActivityAsCaller", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }

    private static class startActivityAndWait extends startActivity {
        public startActivityAndWait(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookedMethodHandler.HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "startActivityAndWait", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }

    private static class startActivityWithConfig extends startActivity {
        public startActivityWithConfig(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookedMethodHandler.HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "startActivityWithConfig", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }

    private static class startNextMatchingActivity extends startActivity {
        public startNextMatchingActivity(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookedMethodHandler.HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "startNextMatchingActivity", new Object[0]);
            doReplaceIntentForStartActivityAPILow(args, IActivityManagerHook.findFirstIntentIndexInArgs(args));
            return false;
        }

        /* access modifiers changed from: protected */
        public int obtainReplaceIndex() {
            return -1;
        }
    }

    private static class startActivityFromRecents extends HookedMethodHandler {
        public startActivityFromRecents(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "startActivityFromRecents", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }

    private static class getAppTasks extends AccurateReplaceCallingPkgHookedMethodHandler2 {
        public getAppTasks(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookedMethodHandler.HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "getAppTasks", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }

        /* access modifiers changed from: protected */
        public int obtainReplaceIndex() {
            return 0;
        }
    }

    private static class addAppTask extends ReplaceCallingPackageHookedMethodHandler {
        public addAppTask(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookedMethodHandler.HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "addAppTask", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }

    private static class getProcessesInErrorState extends HookedMethodHandler {
        public getProcessesInErrorState(Context hostContext) {
            super(hostContext);
        }
    }

    private static class getRunningServiceControlPanel extends HookedMethodHandler {
        public getRunningServiceControlPanel(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "getRunningServiceControlPanel", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }

    private static class startInstrumentation extends HookedMethodHandler {
        public startInstrumentation(Context hostContext) {
            super(hostContext);
        }
    }

    private static class getActivityClassForToken extends HookedMethodHandler {
        getActivityClassForToken(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "getActivityClassForToken", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }

    private static class getPackageForToken extends HookedMethodHandler {
        getPackageForToken(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "getPackageForToken", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }


    private static class getMyMemoryState extends HookedMethodHandler {
        getMyMemoryState(Context hostContext) {
            super(hostContext);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "getMyMemoryState", new Object[0]);
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }


    private static class navigateUpTo extends HookedMethodHandler {
        navigateUpTo(Context hostContext) {
            super(hostContext);
        }
    }

    /* access modifiers changed from: private */
    public static int findFirstIntentIndexInArgs(Object[] args) {
        if (args != null && args.length > 0) {
            int i = 0;
            for (Object arg : args) {
                if (arg != null && (arg instanceof Intent)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    private class isUserRunning extends HookedMethodHandler {
        public isUserRunning(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            Log.i(IActivityManagerHook.TAG, "isUserRunning", new Object[0]);
            hookContext.setFakedResult(true);
            return true;
        }

        public void afterInvoke(Object receiver, Method method, Object[] args, Object invokeResult, HookContext hookContext) throws Throwable {
            super.afterInvoke(receiver, method, args, invokeResult, hookContext);
        }
    }

    private class setAppLockedVerifying extends HookedMethodHandler {
        private setAppLockedVerifying(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public final boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            int index = searchInstance(args, String.class, 0);
            if (index >= 0) {
                args[index] = this.mHostContext.getPackageName();
            }
            return super.beforeInvoke(receiver, method, args, hookContext);
        }
    }

    private class updateConfiguration extends HookedMethodHandler {
        private updateConfiguration(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public final boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            hookContext.setFakedResult((Object) null);
            return true;
        }
    }

    private class updatePersistentConfiguration extends HookedMethodHandler {
        private updatePersistentConfiguration(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public final boolean beforeInvoke(Object receiver, Method method, Object[] args, HookContext hookContext) throws Throwable {
            hookContext.setFakedResult((Object) null);
            return true;
        }
    }

    private static class ServiceInfoResult {
        @Nullable
        final ServiceInfo serviceInfo;
        final boolean skip;

        ServiceInfoResult(boolean skip2, @Nullable ServiceInfo info) {
            this.skip = skip2;
            this.serviceInfo = info;
        }
    }
}
