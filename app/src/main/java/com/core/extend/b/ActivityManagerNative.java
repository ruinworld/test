package com.core.extend.b;

import android.os.IInterface;

import reflect.ClassRef;
import reflect.MethodInfo;
import reflect.StaticFieldRef;
import reflect.StaticMethodRef;

public class ActivityManagerNative {
    public static java.lang.Class Class = ClassRef.init(ActivityManagerNative.class, "android.app.ActivityManagerNative");
    public static StaticFieldRef<Object> gDefault;
    @MethodInfo({})
    public static StaticMethodRef<IInterface> getDefault;
}
