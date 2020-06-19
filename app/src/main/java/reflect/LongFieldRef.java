package reflect;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public final class LongFieldRef {
    private Field field;

    public LongFieldRef(@NonNull Class<?> cls, @NonNull Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public long get(Object obj) {
        try {
            return this.field.getLong(obj);
        } catch (Exception e) {
            return 0;
        }
    }

    public void set(Object obj, long value) {
        try {
            this.field.setLong(obj, value);
        } catch (Exception e) {
        }
    }
}
