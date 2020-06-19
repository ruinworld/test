package reflect;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

public final class StaticIntFieldRef {
    private Field field;

    public StaticIntFieldRef(@NonNull Class<?> cls, @NonNull Field field2) throws NoSuchFieldException {
        this.field = cls.getDeclaredField(field2.getName());
        this.field.setAccessible(true);
    }

    public int get() {
        try {
            return this.field.getInt((Object) null);
        } catch (Exception e) {
            return 0;
        }
    }

    public void set(int value) {
        try {
            this.field.setInt((Object) null, value);
        } catch (Exception e) {
        }
    }
}
