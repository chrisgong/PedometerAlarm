package com.gcblog.stepalarm.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;

/**
 * 开机重新创建一遍闹钟
 */
@EReceiver
public class AlarmReceiver extends BroadcastReceiver {

	@ReceiverAction(actions = Intent.ACTION_BOOT_COMPLETED)
	public void onReceive(Context context, Intent intent) {
		AlarmManagerHelper.setAlarms(context);
	}
}
