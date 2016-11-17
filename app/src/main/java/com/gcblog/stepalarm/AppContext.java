package com.gcblog.stepalarm;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.gcblog.stepalarm.data.local.RealmSet;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

/**
 * Created by gc on 2016/11/16.
 */

public class AppContext extends Application {

    private static AppContext INSTANCE = null;

    public static AppContext getInstance(){
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(INSTANCE == null){
            INSTANCE = this;
        }

        RealmSet.init();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }
}
