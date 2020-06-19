package reflect;

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public final class ConstructorRef<T> {
    private Constructor<?> constructor;

    public ConstructorRef(@NonNull Class<?> cls, @NonNull Field field) throws NoSuchMethodException {
        if (field.isAnnotationPresent(MethodInfo.class)) {
            this.constructor = cls.getDeclaredConstructor((field.getAnnotation(MethodInfo.class)).value());
        } else if (field.isAnnotationPresent(MethodReflectionInfo.class)) {
            String[] value = (field.getAnnotation(MethodReflectionInfo.class)).value();
            Class[] paramTypes = new Class[value.length];
            int i = 0;
            while (i < value.length) {
                try {
                    paramTypes[i] = Class.forName(value[i]);
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.constructor = cls.getDeclaredConstructor(paramTypes);
        } else {
            this.constructor = cls.getDeclaredConstructor(new Class[0]);
        }
        this.constructor.setAccessible(true);
    }

    public T newInstance() {
        try {
            return (T) this.constructor.newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    public T newInstance(Object... params) {
        try {
            return (T) this.constructor.newInstance(params);
        } catch (Exception e) {
            return null;
        }
    }
}
