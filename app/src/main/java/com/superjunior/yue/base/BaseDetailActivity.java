package com.superjunior.yue.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.superjunior.yue.R;

public abstract class BaseDetailActivity extends BaseActivity {

    private Toolbar mToolbar;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    protected abstract String getUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mWebView = (WebView) findViewById(R.id.webView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mWebView.setWebViewClient(new YueWebViewClient());
        mWebView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(getUrl());
            }
        }, 100);
    }

    @Override
    protected void setLayoutResID() {
        mLayoutResID = R.layout.activity_base_detail;
    }

    @Override
    protected void setStatusBarColor() {
        mStatusBarColor = R.color.colorPrimaryDark;
    }

    private class YueWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getUrl()));
            startActivity(intent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
