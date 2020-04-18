package com.tzh.trouter;

import android.app.Application;

import com.tzh.router.TRouter;

/**
 * create by tuzanhua on 2020/4/18
 */
public class TApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TRouter.getInstance().init(this);
    }
}
