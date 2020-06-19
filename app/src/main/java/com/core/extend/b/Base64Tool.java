package com.core.extend.b;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import java.nio.charset.StandardCharsets;

public class Base64Tool {
    private static final String SALT = "qihoo.work.safe";
    private static final String SALT_AFTER = SALT.substring(SALT.length() / 2);
    private static final String SALT_BEFORE = SALT.substring(0, SALT.length() / 2);

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String decode(String inputStr) {
        String decodeStr = new String(Base64.decode(inputStr, 0), StandardCharsets.UTF_8);
        if (isEncodeStr(decodeStr)) {
            return decodeStr.substring(SALT_BEFORE.length(), decodeStr.length() - SALT_AFTER.length());
        }
        return inputStr;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String encode(String input) {
        return new String(Base64.encode((SALT_BEFORE + input + SALT_AFTER).getBytes(), 0), StandardCharsets.UTF_8);
    }

    private static boolean isEncodeStr(String inputStr) {
        if (!TextUtils.isEmpty(inputStr) && inputStr.endsWith(SALT_AFTER) && inputStr.startsWith(SALT_BEFORE)) {
            return true;
        }
        return false;
    }
}
