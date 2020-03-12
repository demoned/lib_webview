package com.bojun.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class XWebView extends WebView {
    //PC版方式
    public final static int PC_MODEL = 0x010;
    //Android版方式
    public final static int PHONE_MODEL = 0x011;
    //最大化显示
    public final static int MAX_MODEL = 0x012;
    //最小显示
    public final static int MIN_MODEL = 0x013;

    @SuppressLint("SetJavaScriptEnabled")
    public XWebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        this.setWebViewClient(client);
        this.initWebViewSettings();
        this.getView().setClickable(true);
    }

    public void createView(XWebView xWebView, ViewGroup viewParent) {
        viewParent.addView(xWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    /**
     * 设置webView 显示的方式,PC模式:PC_MODEL,手机模式:PHONE_MODEL
     *
     * @param webViewShowType
     */
    public void setWebViewShowType(int webViewShowType) {
        if (webViewShowType == PC_MODEL) {
            this.getSettings().setUserAgentString(WebConfig.PC_TYPE);
        } else if (webViewShowType == PHONE_MODEL) {
            this.getSettings().setUserAgentString(WebConfig.PHONE_TYPE);
        }
    }

    /**
     * 设置webView 显示模式, 最大: MAX_MODEL,最小: MIN_MODEL
     *
     * @param webViewShowScope
     */
    public void setWebViewShowScope(int webViewShowScope) {
        if (webViewShowScope == MAX_MODEL) {
            this.setInitialScale(100);
        } else if (webViewShowScope == MIN_MODEL) {
            this.setInitialScale(25);
        }
    }

    /**
     * 设置webView背景颜色
     *
     * @param webViewBgColor
     */
    public void setWebViewBgColor(int webViewBgColor) {
        if (webViewBgColor == 0) {
            setBackgroundColor(85621);
        } else {
            this.setBackgroundColor(webViewBgColor);
        }
    }


    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
}
