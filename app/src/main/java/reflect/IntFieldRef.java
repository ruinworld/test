package reflect;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public final class IntFieldRef {
    private Field field;

    public IntFieldRef(@NonNull Class<?> cls, @NonNull Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public int get(Object obj) {
        try {
            return this.field.getInt(obj);
        } catch (Exception e) {
            return 0;
        }
    }

    public void set(Object obj, int value) {
        try {
            this.field.setInt(obj, value);
        } catch (Exception e) {
        }
    }
}
