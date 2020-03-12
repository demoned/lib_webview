package com.bojun.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.utils.TbsLog;

/**
 * 网页浏览器
 */
public class BrowserActivity extends AppCompatActivity {
    private static final String TAG = BrowserActivity.class.getSimpleName();
    private XWebView mWebView;
    private ImageButton mBack;
    private ImageButton mForward;
    private ImageButton mExit;
    private ImageButton mHome;
    private ImageButton mMore;
    private Button mGo;
    private EditText mUrl;
    private static final String mHomeUrl = "http://bj-ihealthcare.com:58102/vitalSignsH5/index.html#/vitalSigns?doctorId=0372&token=token_84eec8e852634c7da12d27aa5512178d&id=785406";
    private static final int MAX_LENGTH = 14;
    private final int disable = 120;
    private ProgressBar mPageLoadingProgressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
        initView();
    }

    private void initWebView() {
        ViewGroup mViewParent = (ViewGroup) findViewById(R.id.webView1);
        mWebView = new XWebView(this, null);
        mWebView.createView(mWebView, mViewParent);
        mWebView.setWebViewShowType(XWebView.PC_MODEL);
        mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);// new
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources().getDrawable(R.drawable.color_progressbar));
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
                    mPageLoadingProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
                    mWebView.setWebViewShowScope(XWebView.MIN_MODEL);
                } else {
                    mPageLoadingProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mPageLoadingProgressBar.setProgress(i);//设置进度值
                }
            }
        });
        long time = System.currentTimeMillis();
        mWebView.loadUrl(mHomeUrl);
        TbsLog.d("time-cost", "cost time: " + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    private void initView() {
        mBack = (ImageButton) findViewById(R.id.btnBack1);
        mForward = (ImageButton) findViewById(R.id.btnForward1);
        mExit = (ImageButton) findViewById(R.id.btnExit1);
        mHome = (ImageButton) findViewById(R.id.btnHome1);
        mGo = (Button) findViewById(R.id.btnGo1);
        mUrl = (EditText) findViewById(R.id.editUrl1);
        mMore = (ImageButton) findViewById(R.id.btnMore);
        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16) {
            mBack.setAlpha(disable);
            mForward.setAlpha(disable);
            mHome.setAlpha(disable);
        }
        mHome.setEnabled(false);

        mBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoBack())
                    mWebView.goBack();
            }
        });

        mForward.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoForward())
                    mWebView.goForward();
            }
        });

        mGo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = mUrl.getText().toString();
                mWebView.loadUrl(url);
                mWebView.requestFocus();
            }
        });

        mMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(BrowserActivity.this, "not completed",
                        Toast.LENGTH_LONG).show();
            }
        });

        mUrl.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mGo.setVisibility(View.VISIBLE);
                    if (null == mWebView.getUrl())
                        return;
                    if (mWebView.getUrl().equalsIgnoreCase(mHomeUrl)) {
                        mUrl.setText("");
                        mGo.setText("首页");
                        mGo.setTextColor(0X6F0F0F0F);
                    } else {
                        mUrl.setText(mWebView.getUrl());
                        mGo.setText("进入");
                        mGo.setTextColor(0X6F0000CD);
                    }
                } else {
                    mGo.setVisibility(View.GONE);
                    String title = mWebView.getTitle();
                    if (title != null && title.length() > MAX_LENGTH)
                        mUrl.setText(title.subSequence(0, MAX_LENGTH) + "...");
                    else
                        mUrl.setText(title);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }

        });

        mUrl.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                String url = null;
                if (mUrl.getText() != null) {
                    url = mUrl.getText().toString();
                }

                if (url == null
                        || mUrl.getText().toString().equalsIgnoreCase("")) {
                    mGo.setText("请输入网址");
                    mGo.setTextColor(0X6F0F0F0F);
                } else {
                    mGo.setText("进入");
                    mGo.setTextColor(0X6F0000CD);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
        });

        mHome.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null)
                    mWebView.loadUrl(mHomeUrl);
            }
        });

        mExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Process.killProcess(Process.myPid());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || mWebView == null || intent.getData() == null)
            return;
        mWebView.loadUrl(intent.getData().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }
}
