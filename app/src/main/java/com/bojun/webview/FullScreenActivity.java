package com.bojun.webview;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * webView实现视频的全屏播放功能
 */
public class FullScreenActivity extends AppCompatActivity {
    private XWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filechooser_layout);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        webView = (XWebView) findViewById(R.id.web_file_chooser);
        webView.loadUrl("file:///android_asset/webpage/fullscreenVideo.html");
        webView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        webView.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {

            }

            @JavascriptInterface
            public void onX5ButtonClicked() {
                FullScreenActivity.this.enableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onCustomButtonClicked() {
                FullScreenActivity.this.disableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onLiteWndButtonClicked() {
                FullScreenActivity.this.enableLiteWndFunc();
            }

            @JavascriptInterface
            public void onPageVideoClicked() {
                FullScreenActivity.this.enablePageVideoFunc();
            }
        }, "Android");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向webView发出信息
     */
    private void enableX5FullscreenFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

    private void disableX5FullscreenFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

    private void enableLiteWndFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启小窗模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

    private void enablePageVideoFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示全屏；不设置默认false，
            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，
            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }
}
