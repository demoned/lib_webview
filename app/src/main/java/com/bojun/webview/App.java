package com.bojun.webview;

import android.app.Application;

import com.bojun.webview.XWebAgent;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XWebAgent.getInstance().init(this);
    }
}
