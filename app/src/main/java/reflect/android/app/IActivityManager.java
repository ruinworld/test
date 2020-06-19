package reflect.android.app;

import android.content.pm.ProviderInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;

import reflect.ClassRef;
import reflect.FieldRef;
import reflect.MethodInfo;
import reflect.MethodRef;
import reflect.StaticFieldRef;

public class IActivityManager {
    public static final java.lang.Class Class = ClassRef.init(IActivityManager.class, "android.app.IActivityManager");
    @MethodInfo({IBinder.class, boolean.class})
    public static MethodRef getTaskForActivity;
    @MethodInfo({IBinder.class, int.class})
    public static MethodRef setRequestedOrientation;

    public static class ContentProviderHolder {
        public static StaticFieldRef CREATOR;
        public static java.lang.Class Class;
        public static FieldRef<ProviderInfo> info;
        public static FieldRef<IInterface> provider;

        static {
            if (Build.VERSION.SDK_INT < 26) {
                Class = ClassRef.init(ContentProviderHolder.class, "android.app.IActivityManager$ContentProviderHolder");
            } else {
                Class = ClassRef.init(ContentProviderHolder.class, "android.app.ContentProviderHolder");
            }
        }
    }
}
