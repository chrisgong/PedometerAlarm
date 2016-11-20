package com.gcblog.stepalarm.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gcblog.stepalarm.data.AlarmContract;
import com.gcblog.stepalarm.data.local.AlarmDatabaseManager;
import com.gcblog.stepalarm.data.model.AlarmModel;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/11/19.
 */
public class AlarmManagerHelper {

    /**
     * 设置系统闹钟
     */
    public static void setAlarms(Context context) {
        cancelAlarms(context);
        List<AlarmModel> alarms = AlarmDatabaseManager.getAlarms();
        alarms.stream().filter(alarm -> alarm.isEnabled).forEach(alarm -> {
            PendingIntent pIntent = createPendingIntent(context, alarm);

            //设置闹钟时间
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarm.timeHour);
            calendar.set(Calendar.MINUTE, alarm.timeMinute);
            calendar.set(Calendar.SECOND, 00);

            //当前星期，日，分
            final int nowDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);

            //当前年日
            final int nowDayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

            if (!alarm.repeatWeekly) {
                if (alarm.timeHour >= nowHour && alarm.timeMinute > nowMinute) {
                    calendar.set(Calendar.DAY_OF_YEAR, nowDayOfYear);
                } else {
                    calendar.set(Calendar.DAY_OF_YEAR, nowDayOfYear);
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }
                setAlarm(context, calendar, pIntent);
            } else {
                boolean alarmSet = false;
                //First check if it's later in the week
                for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                    if (alarm.getRepeatingDay(dayOfWeek) && dayOfWeek >= nowDayOfWeek &&
                            !(dayOfWeek == nowDayOfWeek && alarm.timeHour < nowHour) &&
                            !(dayOfWeek == nowDayOfWeek && alarm.timeHour == nowHour && alarm.timeMinute <= nowMinute)) {
                        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                        setAlarm(context, calendar, pIntent);
                        alarmSet = true;
                        break;
                    }
                }

                //Else check if it's earlier in the week
                if (!alarmSet) {
                    for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                        if (alarm.getRepeatingDay(dayOfWeek) && dayOfWeek <= nowDayOfWeek && alarm.repeatWeekly) {
                            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                            calendar.add(Calendar.WEEK_OF_YEAR, 1);
                            setAlarm(context, calendar, pIntent);
                            alarmSet = true;
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * 取消闹钟
     */
    public static void cancelAlarms(Context context) {
        List<AlarmModel> alarms = AlarmDatabaseManager.getAlarms();
        if (alarms != null) {
            alarms.stream().filter(alarm -> alarm.isEnabled).forEach(alarm -> {
                Log.e("cim", "cancelAlarms：" + alarm.id);
                PendingIntent pIntent = createPendingIntent(context, alarm);
                android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pIntent);
            });
        }
    }

    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        Log.e("cim", "setAlarm()");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        } else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }

    private static PendingIntent createPendingIntent(Context context, AlarmModel model) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(AlarmContract.IDD, String.valueOf(model.id));
        return PendingIntent.getService(context, (int) model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
