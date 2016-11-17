package com.gcblog.stepalarm.data.local;

import com.gcblog.stepalarm.data.AlarmContract;
import com.gcblog.stepalarm.data.model.AlarmModel;

import java.util.List;

import io.realm.Realm;

/**
 * Created by gc on 2016/11/16.
 */

public class AlarmDatabaseManager {

    /**
     * 主键自增
     *
     * @param realm
     * @return
     */
    public static long setUniqueId(Realm realm) {
        Number num = realm.where(AlarmModel.class).max(AlarmContract.IDD);
        if (num == null) return 1;
        else return ((long) num + 1);
    }

    /**
     * 创建闹钟
     *
     * @param model
     */
    public static void createAlarm(AlarmModel model) {
        RealmSet.getDefault().executeTransaction(realm -> {
            model.id = setUniqueId(realm);
            realm.copyToRealm(model);
        });
    }

    /**
     * 创建闹钟
     *
     * @param hour
     * @param min
     */
    public static void createAlarm(int hour, int min, IAlarmCreateListener listener) {
        AlarmModel model = new AlarmModel();
        RealmSet.getDefault().executeTransactionAsync(realm -> {
            model.id = setUniqueId(realm);
            model.timeHour = hour;
            model.timeMinute = min;
            realm.copyToRealm(model);
        }, () -> listener.onSuccess(model));
    }

    /**
     * 更新闹钟
     *
     * @param model
     */
    public static void updateAlarm(AlarmModel model) {
        RealmSet.getDefault().executeTransaction(realm -> realm.copyToRealmOrUpdate(model));
    }

    /**
     * 查询闹钟
     *
     * @param id
     * @return
     */
    public static AlarmModel getAlarm(long id) {
        return RealmSet.getDefault().copyFromRealm(RealmSet.getDefault().where(AlarmModel.class).equalTo(AlarmContract.IDD, id).findFirst());
    }

    /**
     * 查询所有闹钟
     *
     * @return
     */
    public static List<AlarmModel> getAlarms() {
        return RealmSet.getDefault().copyFromRealm(RealmSet.getDefault().where(AlarmModel.class).findAll());
    }

    /**
     * 删除闹钟
     *
     * @param id
     */
    public static void deleteAlarm(long id) {
        RealmSet.getDefault().executeTransaction(realm -> realm.where(AlarmModel.class).equalTo(AlarmContract.IDD, id).findFirst().deleteFromRealm());
    }
}
