package reflect.android.app;

import android.os.IInterface;

import reflect.ClassRef;
import reflect.StaticFieldRef;
import reflect.StaticMethodRef;

public class ActivityManager {
    public static java.lang.Class Class = ClassRef.init((java.lang.Class<?>) ActivityManager.class, "android.app.ActivityManager");
    public static StaticFieldRef<Object> IActivityManagerSingleton;
    public static StaticMethodRef<IInterface> getService;
}
