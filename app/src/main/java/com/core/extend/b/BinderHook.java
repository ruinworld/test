package com.core.extend.b;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.io.FileDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class BinderHook implements InvocationHandler {
    private static final boolean DEBUG_BINDER = BuildConfig.DEBUG;
    private static final String TAG = BinderHook.class.getSimpleName();
    protected final Context mHostContext;
    private final IInterface origIInterface;
    protected final Map<String, HookedMethodHandler> sHookedMethodHandlers = new HashMap();

    /* access modifiers changed from: protected */
    public abstract void initHookedMethods();

    /* access modifiers changed from: protected */
    public abstract boolean isEnabled();

    protected BinderHook(Context context, IInterface iInterface) {
        this.mHostContext = context;
        this.origIInterface = iInterface;
        if (isEnabled()) {
            initHookedMethods();
        }
    }

    /* access modifiers changed from: protected */
    public HookedMethodHandler getHookedMethodHandler(Method method) {
        return this.sHookedMethodHandlers.get(method.getName());
    }

    public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
        try {
            HookedMethodHandler methodHandler = getHookedMethodHandler(method);
            if (methodHandler != null) {
                return methodHandler.doHookInner(this.origIInterface, method, args);
            }
            return method.invoke(this.origIInterface, args);
        } catch (InvocationTargetException e) {
            if (DEBUG_BINDER) {
                StringBuilder sb = new StringBuilder();
                sb.append("current: ");
                sb.append(getClass().getName());
                sb.append(" original: ");
                sb.append(this.origIInterface.getClass().getName());
                sb.append(" method: ");
                sb.append(method.getName());
                sb.append(" args: [");
                if (args != null) {
                    for (Object object : args) {
                        sb.append(String.valueOf(object));
                        sb.append(", ");
                    }
                } else {
                    sb.append("null");
                }
                sb.append("]");
                Log.e(TAG, sb.toString(), new Object[0]);
                e.printStackTrace();
            }
            throw e.getTargetException();
        }
    }

    protected static int searchInstance(Object[] args, Class clazz, int begin) {
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

    public static class BinderWrapper implements IBinder {
        private final IBinder binder;
        private final IInterface proxy;

        public BinderWrapper(IBinder binder2, IInterface proxy2) {
            this.binder = binder2;
            this.proxy = proxy2;
        }

        public IInterface getProxy() {
            return this.proxy;
        }

        public final String getInterfaceDescriptor() throws RemoteException {
            return this.binder.getInterfaceDescriptor();
        }

        public final boolean pingBinder() {
            return this.binder.pingBinder();
        }

        public final boolean isBinderAlive() {
            return this.binder.isBinderAlive();
        }

        public final IInterface queryLocalInterface(String descriptor) {
            return this.proxy;
        }

        public final void dump(FileDescriptor fd, String[] args) throws RemoteException {
            this.binder.dump(fd, args);
        }

        public final void dumpAsync(FileDescriptor fd, String[] args) throws RemoteException {
            this.binder.dumpAsync(fd, args);
        }

        public final boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return this.binder.transact(code, data, reply, flags);
        }

        public final void linkToDeath(DeathRecipient recipient, int flags) throws RemoteException {
            this.binder.linkToDeath(recipient, flags);
        }

        public final boolean unlinkToDeath(DeathRecipient recipient, int flags) {
            return this.binder.unlinkToDeath(recipient, flags);
        }
    }
}
