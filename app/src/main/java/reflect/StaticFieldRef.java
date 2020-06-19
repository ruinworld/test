package reflect;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public final class StaticFieldRef<T> {
    private Field field;

    public StaticFieldRef(@NonNull Class<?> cls, @NonNull Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public T get() {
        try {
            return (T) this.field.get((Object) null);
        } catch (Exception e) {
            return null;
        }
    }

    public void set(T value) {
        try {
            this.field.set((Object) null, value);
        } catch (Exception e) {
        }
    }
}
