package reflect.android.os;

import android.os.IBinder;

import java.util.Map;

import reflect.ClassRef;
import reflect.MethodInfo;
import reflect.StaticFieldRef;
import reflect.StaticMethodRef;

public class ServiceManager {
    public static java.lang.Class Class = ClassRef.init((java.lang.Class<?>) ServiceManager.class, "android.os.ServiceManager");
    @MethodInfo({String.class, IBinder.class})
    public static StaticMethodRef addService;
    @MethodInfo({String.class})
    public static StaticMethodRef checkService;
    @MethodInfo({})
    public static StaticMethodRef getIServiceManager;
    @MethodInfo({String.class})
    public static StaticMethodRef getService;
    @MethodInfo({})
    public static StaticMethodRef listServices;
    public static StaticFieldRef<Map<String, IBinder>> sCache;
}
