package reflect;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public final class FieldRef<T> {
    private Field field;

    public FieldRef(@NonNull Class<?> cls, @NonNull Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public T get(Object obj) {
        try {
            return (T) this.field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public void set(Object obj, T value) {
        try {
            this.field.set(obj, value);
        } catch (Exception e) {
        }
    }
}
