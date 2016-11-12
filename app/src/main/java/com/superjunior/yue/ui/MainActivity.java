package com.superjunior.yue.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.superjunior.yue.R;
import com.superjunior.yue.base.BaseActivity;
import com.superjunior.yue.ui.news.NewsFragment;
import com.superjunior.yue.ui.zhihudaily.ZhiHuDailyFragment;
import com.superjunior.yue.ui.zhihudaily.ZhiHuDailyPresenter;
import com.superjunior.yue.util.ActivityUtils;
import com.superjunior.yue.util.CommonUtils;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private NewsFragment mNewsFragment;
    private ZhiHuDailyFragment mDailyFragment;
    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(CommonUtils.getColor(R.color.material_white));
        mToolbar.setTitle(R.string.news);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        CommonUtils.checkNotNull(actionBar).setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(CommonUtils.checkNotNull(mNavigationView));
        mNewsFragment = new NewsFragment();
        mDailyFragment = new ZhiHuDailyFragment();
        mCurrentFragment = mNewsFragment;
        ActivityUtils.addFragment(getSupportFragmentManager(), mNewsFragment, R.id.contentFrame);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.news_navigation_menu_item:
                                mToolbar.setTitle(R.string.news);
                                ActivityUtils.switchFragment(mFragmentManager, mCurrentFragment, mNewsFragment);
                                mCurrentFragment = mNewsFragment;
                                break;
                            case R.id.science_navigation_menu_item:
                                mToolbar.setTitle(R.string.zhihudaily);
                                new ZhiHuDailyPresenter(mDailyFragment);
                                ActivityUtils.switchFragment(mFragmentManager, mCurrentFragment, mDailyFragment);
                                mCurrentFragment = mDailyFragment;
                                break;
                            default:
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
