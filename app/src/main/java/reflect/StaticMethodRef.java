package reflect;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class StaticMethodRef<T> {
    private Method method;

    public StaticMethodRef(@NonNull Class<?> cls, @NonNull Field field) throws NoSuchMethodException, ClassNotFoundException {
        if (field.isAnnotationPresent(MethodInfo.class)) {
            this.method = cls.getDeclaredMethod(field.getName(), ((MethodInfo) field.getAnnotation(MethodInfo.class)).value());
            this.method.setAccessible(true);
        } else if (field.isAnnotationPresent(MethodReflectionInfo.class)) {
            String[] value = ((MethodReflectionInfo) field.getAnnotation(MethodReflectionInfo.class)).value();
            Class<?>[] paramTypes = new Class[value.length];
            for (int i = 0; i < value.length; i++) {
                paramTypes[i] = Utils.getClassForName(value[i]);
            }
            this.method = cls.getDeclaredMethod(field.getName(), paramTypes);
            this.method.setAccessible(true);
        } else {
            Method[] declaredMethods = cls.getDeclaredMethods();
            int length = declaredMethods.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Method method2 = declaredMethods[i2];
                if (method2.getName().equals(field.getName())) {
                    this.method = method2;
                    this.method.setAccessible(true);
                    break;
                }
                i2++;
            }
            if (this.method == null) {
                throw new NoSuchMethodException(field.getName());
            }
        }
    }

    public T invoke(Object... params) {
        try {
            return (T) this.method.invoke((Object) null, params);
        } catch (Exception e) {
            return null;
        }
    }

    public T invokeWithException(Object... params) throws InvocationTargetException, IllegalAccessException {
        return (T) this.method.invoke((Object) null, params);
    }
}
