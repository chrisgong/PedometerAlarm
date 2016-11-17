package com.gcblog.stepalarm.data.local;


import com.gcblog.stepalarm.AppContext;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by gc on 16/8/2.
 */

public class RealmSet {

    //数据库版本
    private static int REALM_VERSION = 1;

    /**
     * 默认realm库
     * @return
     */
    public static Realm getDefault() {
        return Realm.getDefaultInstance();
    }

    /**
     * 初始化Realm库
     */
    public static void init() {
        RealmConfiguration config = new RealmConfiguration.Builder(AppContext.getInstance().getApplicationContext())
                .name("alarm.realm")
                .schemaVersion(REALM_VERSION)
                .build();
        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
    }
}
