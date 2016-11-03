package com.superjunior.yue;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.superjunior.yue.news.NewsFragment;
import com.superjunior.yue.util.ActivityUtils;
import com.superjunior.yue.util.CommonUtils;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private NewsFragment mNewsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsFragment = new NewsFragment();
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

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.material_grey_200);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(CommonUtils.getColor(R.color.material_white));
        mToolbar.setTitle(R.string.news);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        CommonUtils.checkNotNull(actionBar).setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(CommonUtils.checkNotNull(mNavigationView));
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
                                ActivityUtils.replaceFragment(getSupportFragmentManager(), mNewsFragment, R.id.contentFrame);
                                break;
                            case R.id.science_navigation_menu_item:
                                mToolbar.setTitle(R.string.science);
                                ActivityUtils.replaceFragment(getSupportFragmentManager(), mNewsFragment, R.id.contentFrame);
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
