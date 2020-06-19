package reflect;

public @interface MethodInfo3Element {
    String[] argClassNames() default {};

    int minApi();
}
