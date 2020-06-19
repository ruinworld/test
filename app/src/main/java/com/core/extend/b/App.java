package com.core.extend.b;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //HookManager.hookClipboardManager(this);
        HookManager.hookActivityManager(this);
    }
}
