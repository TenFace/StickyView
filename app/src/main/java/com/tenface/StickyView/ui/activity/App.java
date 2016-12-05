package com.tenface.StickyView.ui.activity;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by TenFace on 2016/12/5.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 初始化融云
         * */
        RongIM.init(this);
    }
}
