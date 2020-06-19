package reflect;

import android.os.Build;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class MethodRef<T> {
    private static final String TAG = MethodRef.class.getSimpleName();
    private Method method;

    public MethodRef(@NonNull Class<?> cls, @NonNull Field field) throws NoSuchMethodException, ClassNotFoundException {
        if (field.isAnnotationPresent(MethodInfo.class)) {
            this.method = cls.getDeclaredMethod(field.getName(), ((MethodInfo) field.getAnnotation(MethodInfo.class)).value());
            this.method.setAccessible(true);
        } else if (field.isAnnotationPresent(MethodInfo3.class)) {
            MethodInfo3Element[] elements = ((MethodInfo3) field.getAnnotation(MethodInfo3.class)).value();
            if (elements.length == 0) {
                throw new RuntimeException("MethodInfo3 is not correctly used!");
            }
            MethodInfo3Element selected = null;
            int k = 1;
            while (true) {
                if (k >= elements.length) {
                    break;
                } else if (Build.VERSION.SDK_INT < elements[k].minApi()) {
                    selected = elements[k - 1];
                    break;
                } else {
                    k++;
                }
            }
            String[] argClassNames = (selected == null ? elements[elements.length - 1] : selected).argClassNames();
            if (argClassNames.length == 0) {
                this.method = cls.getDeclaredMethod(field.getName(), new Class[0]);
                this.method.setAccessible(true);
                return;
            }
            Class<?>[] paramTypes = new Class[argClassNames.length];
            for (int i = 0; i < argClassNames.length; i++) {
                paramTypes[i] = Utils.getClassForName(argClassNames[i]);
            }
            this.method = cls.getDeclaredMethod(field.getName(), paramTypes);
            this.method.setAccessible(true);
        } else if (field.isAnnotationPresent(MethodReflectionInfo.class)) {
            String[] value = ((MethodReflectionInfo) field.getAnnotation(MethodReflectionInfo.class)).value();
            Class<?>[] paramTypes2 = new Class[value.length];
            for (int i2 = 0; i2 < value.length; i2++) {
                paramTypes2[i2] = Utils.getClassForName(value[i2]);
            }
            this.method = cls.getDeclaredMethod(field.getName(), paramTypes2);
            this.method.setAccessible(true);
        } else {
            Method[] declaredMethods = cls.getDeclaredMethods();
            int length = declaredMethods.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                Method m = declaredMethods[i3];
                if (m.getName().equals(field.getName())) {
                    this.method = m;
                    this.method.setAccessible(true);
                    break;
                }
                i3++;
            }
            if (this.method == null) {
                throw new NoSuchMethodException(field.getName());
            }
        }
    }

    public T invoke(Object obj, Object... params) {
        try {
            return (T) this.method.invoke(obj, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public T invokeWithException(Object obj, Object... params) throws InvocationTargetException, IllegalAccessException {
        return (T) this.method.invoke(obj, params);
    }
}
