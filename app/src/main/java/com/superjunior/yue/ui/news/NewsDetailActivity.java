package com.superjunior.yue.ui.news;

import com.superjunior.yue.base.BaseDetailActivity;
import com.superjunior.yue.util.Constants;

public class NewsDetailActivity extends BaseDetailActivity {

    @Override
    protected String getUrl() {
        return getIntent().getStringExtra(Constants.URI);
    }

}
