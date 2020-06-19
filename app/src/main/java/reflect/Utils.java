package reflect;

public final class Utils {
    public static Class<?> getClassForName(String name) throws ClassNotFoundException {
        if (name.equals("int")) {
            return Integer.TYPE;
        }
        if (name.equals("long")) {
            return Long.TYPE;
        }
        if (name.equals("boolean")) {
            return Boolean.TYPE;
        }
        if (name.equals("byte")) {
            return Byte.TYPE;
        }
        if (name.equals("short")) {
            return Short.TYPE;
        }
        if (name.equals("char")) {
            return Character.TYPE;
        }
        if (name.equals("float")) {
            return Float.TYPE;
        }
        if (name.equals("double")) {
            return Double.TYPE;
        }
        if (name.equals("void")) {
            return Void.TYPE;
        }
        return Class.forName(name);
    }
}
