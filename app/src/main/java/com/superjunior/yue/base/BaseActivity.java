package com.superjunior.yue.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.superjunior.yue.util.CommonUtils;

/**
 * Created by alwayskim on 2016/11/12.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected int mStatusBarColor;
    protected int mLayoutResID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutResID();
        setStatusBarColor();
        initStatusBar();
        setContentView(mLayoutResID);
        initViews();
    }

    protected abstract void initViews();

    protected abstract void setStatusBarColor();

    protected abstract void setLayoutResID();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void initStatusBar() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mStatusBarColor == -1) {
//                mStatusBarColor = R.color.material_green_900;
                return;
            }
            window.setStatusBarColor(CommonUtils.getColor(mStatusBarColor));
            Log.i("BaseActivity", "set statusbar color");
        }
        Log.i("BaseActivity", "set  color");
    }
}
