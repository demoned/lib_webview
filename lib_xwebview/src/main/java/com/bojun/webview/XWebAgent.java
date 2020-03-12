package com.bojun.webview;

import android.content.Context;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

public class XWebAgent {
    private volatile static XWebAgent instance;

    public static XWebAgent getInstance() {
        if (null == instance) {
            synchronized (XWebAgent.class) {
                if (null == instance) {
                    instance = new XWebAgent();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(context, cb);
    }
}
