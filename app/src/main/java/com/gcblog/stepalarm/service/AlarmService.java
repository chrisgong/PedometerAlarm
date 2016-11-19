package com.gcblog.stepalarm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gcblog.stepalarm.view.activity.AlarmScreenActivity;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;

/**
 * 闹钟触发Service
 */
@EService
public class AlarmService extends Service{

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("cim", "------------------AlarmService Receiver");
//		//打开闹钟提醒页面
//		Intent alarmIntent = new Intent(getBaseContext(), AlarmScreenActivity.class);
//		alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		alarmIntent.putExtras(intent);
//		getApplication().startActivity(alarmIntent);
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