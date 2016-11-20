package com.gcblog.stepalarm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gcblog.stepalarm.data.AlarmContract;
import com.gcblog.stepalarm.view.activity.AlarmReminderActivity_;

import org.androidannotations.annotations.EService;

/**
 * 闹钟触发Service
 */
public class AlarmService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int alarmId = Integer.parseInt(intent.getStringExtra(AlarmContract.IDD));
        //打开闹钟提醒页面
        AlarmReminderActivity_.intent(getApplication()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).extra(AlarmContract.IDD, alarmId).start();
        //重新设置一遍闹钟
        AlarmManagerHelper.setAlarms(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}