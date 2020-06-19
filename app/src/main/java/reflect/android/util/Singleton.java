package reflect.android.util;

import reflect.ClassRef;
import reflect.FieldRef;
import reflect.MethodInfo;
import reflect.MethodRef;

public class Singleton {
    public static java.lang.Class Class = ClassRef.init((java.lang.Class<?>) Singleton.class, "android.util.Singleton");
    @MethodInfo({})
    public static MethodRef get;
    public static FieldRef<Object> mInstance;
}
