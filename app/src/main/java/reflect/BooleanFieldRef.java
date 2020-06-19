package reflect;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public final class BooleanFieldRef {
    private Field field;

    public BooleanFieldRef(@NonNull Class<?> cls, @NonNull Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public boolean get(Object obj) {
        try {
            return this.field.getBoolean(obj);
        } catch (Exception e) {
            return false;
        }
    }

    public void set(Object obj, boolean value) {
        try {
            this.field.setBoolean(obj, value);
        } catch (Exception e) {
        }
    }
}
