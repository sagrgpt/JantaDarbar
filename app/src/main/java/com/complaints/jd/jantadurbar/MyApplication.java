package com.complaints.jd.jantadurbar;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by sagar on 14/4/17.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
