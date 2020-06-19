package reflect.android.app;

import android.os.IInterface;

import reflect.ClassRef;
import reflect.StaticFieldRef;
import reflect.StaticMethodRef;

public class ActivityTaskManager {
    public static java.lang.Class Class = ClassRef.init(ActivityTaskManager.class, "android.app.ActivityTaskManager");
    public static StaticFieldRef<Object> IActivityTaskManagerSingleton;
    public static StaticMethodRef<IInterface> getService;
}
