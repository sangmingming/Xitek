package me.isming.xitek.bbs;

import android.app.Application;

import com.tencent.bugly.Bugly;

import me.isming.xitek.bbs.util.Forums;

/**
 * Created by sam on 17/3/2.
 */
public class WujiApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Forums.getBoardEntity(this);
        Bugly.init(getApplicationContext(), "583d2e9b57", BuildConfig.DEBUG);

    }
}
