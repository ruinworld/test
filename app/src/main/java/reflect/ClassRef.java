package reflect;

import androidx.annotation.NonNull;

import com.core.extend.b.BuildConfig;
import com.core.extend.b.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public final class ClassRef {
    private static final HashMap<Class<?>, Constructor<?>> REF_CONSTRUCTORS = new HashMap<>();
    private static final String TAG = ClassRef.class.getSimpleName();

    static {
        try {
            REF_CONSTRUCTORS.put(FieldRef.class, FieldRef.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_CONSTRUCTORS.put(MethodRef.class, MethodRef.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_CONSTRUCTORS.put(IntFieldRef.class, IntFieldRef.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_CONSTRUCTORS.put(LongFieldRef.class, LongFieldRef.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_CONSTRUCTORS.put(BooleanFieldRef.class, BooleanFieldRef.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_CONSTRUCTORS.put(StaticFieldRef.class, StaticFieldRef.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_CONSTRUCTORS.put(StaticIntFieldRef.class, StaticIntFieldRef.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_CONSTRUCTORS.put(StaticMethodRef.class, StaticMethodRef.class.getConstructor(new Class[]{Class.class, Field.class}));
            REF_CONSTRUCTORS.put(ConstructorRef.class, ConstructorRef.class.getConstructor(new Class[]{Class.class, Field.class}));
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage(), new Object[0]);
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public static Class<?> init(@NonNull Class<?> destClass, @NonNull String srcClassName) {
        Class<?> srcClass = null;
        try {
            srcClass = Class.forName(srcClassName);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ERROR: " + e.getMessage(), new Object[0]);
        }
        if (srcClass != null) {
            return init(destClass, srcClass);
        }
        return null;
    }

    public static Class<?> init(@NonNull Class<?> destClass, @NonNull Class<?> srcClass) {
        Constructor<?> constructor;
        try {
            Field[] declaredFields = destClass.getDeclaredFields();
            if (declaredFields == null) {
                return srcClass;
            }
            for (Field filed : declaredFields) {
                if (Modifier.isStatic(filed.getModifiers()) && (constructor = REF_CONSTRUCTORS.get(filed.getType())) != null) {
                    filed.set(null, constructor.newInstance(new Object[]{srcClass, filed}));
                }
            }
            return srcClass;
        } catch (Exception e) {
            Log.e(TAG, "ERROR: ref class init error. " + e.getMessage(), new Object[0]);
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
