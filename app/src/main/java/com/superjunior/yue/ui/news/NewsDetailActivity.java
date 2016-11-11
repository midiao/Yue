package com.superjunior.yue.ui.news;

import android.os.Bundle;

import com.superjunior.yue.base.BaseDetailActivity;
import com.superjunior.yue.util.CommonUtils;

public class NewsDetailActivity extends BaseDetailActivity {

    public static int REQUEST_START = 1;
    public static int RESULT_CODE = 2;
    @Override
    protected String getUrl() {
        return getIntent().getStringExtra(CommonUtils.URI);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CODE, getIntent());
    }
}
