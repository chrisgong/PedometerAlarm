package com.gcblog.stepalarm;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.gcblog.stepalarm.data.local.RealmSet;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

/**
 * Created by gc on 2016/11/16.
 */

public class AppContext extends Application {

    // 初始化
    private static AppContext mInstance = new AppContext();

    public static AppContext getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RealmSet.init();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }
}
