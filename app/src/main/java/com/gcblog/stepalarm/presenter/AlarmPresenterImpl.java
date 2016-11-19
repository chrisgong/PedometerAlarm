package com.gcblog.stepalarm.presenter;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.gcblog.stepalarm.data.local.AlarmDatabaseManager;
import com.gcblog.stepalarm.data.local.IAlarmCreateListener;
import com.gcblog.stepalarm.data.model.AlarmModel;
import com.gcblog.stepalarm.service.AlarmManagerHelper;
import com.gcblog.stepalarm.support.Utils;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by gc on 2016/11/17.
 */
@EBean
public class AlarmPresenterImpl implements AlarmContract.Presenter, IAlarmCreateListener {

    @RootContext
    protected Context mContext;

    private AlarmContract.View mView;

    private ArrayList<AlarmModel> mAlarmModels = new ArrayList<>();

    public void setView(AlarmContract.View view) {
        this.mView = view;
        mAlarmModels.clear();
        mAlarmModels.addAll(AlarmDatabaseManager.getAlarms());
        mView.init(mAlarmModels);
    }

    @Override
    public void createAlarm(int hour, int min) {
        AlarmDatabaseManager.createAlarm(hour, min, this);
    }

    @Override
    public void updateAlarm(AlarmModel model) {
        AlarmDatabaseManager.updateAlarm(model);
    }

    @Override
    public void openSoundChoose(int position) {
        mView.openSoundChoose(position);
    }

    @Override
    public void resetAlarm() {
        AlarmManagerHelper.setAlarms(mContext);
    }

    @Override
    public void deleteAlarm(long id, int position) {
        AlarmDatabaseManager.hideAlarm(id);
        mAlarmModels.remove(Utils.halfSearch(mAlarmModels, id));
        mView.refresh();
    }

    @Override
    public void onSuccess(AlarmModel model) {
        mAlarmModels.add(0, model);
        mView.refresh();
    }

    /**
     * 判断闹钟日期
     *
     * @param model
     * @return
     */
    public String getAlarmDate(AlarmModel model) {
        StringBuffer repeatDays = new StringBuffer();
        if (model.repeatSunday) {
            repeatDays.append("周日").append(" ");
        }

        if (model.repeatMonday) {
            repeatDays.append("周一").append(" ");
        }

        if (model.repeatTuesday) {
            repeatDays.append("周二").append(" ");
        }

        if (model.repeatWednesday) {
            repeatDays.append("周三").append(" ");
        }

        if (model.repeatThursday) {
            repeatDays.append("周四").append(" ");
        }

        if (model.repeatFriday) {
            repeatDays.append("周五").append(" ");
        }

        if (model.repeatSaturday) {
            repeatDays.append("周六").append(" ");
        }

        if (TextUtils.isEmpty(repeatDays)) {
            long nowTime = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, model.timeHour);
            calendar.set(Calendar.MINUTE, model.timeMinute);
            long alarmTime = calendar.getTimeInMillis();

            if (nowTime < alarmTime) {
                repeatDays.append("今天");
            } else {
                repeatDays.append("明天");
            }
        }

        return repeatDays.toString();
    }

    /**
     * 更新闹钟声音资源
     *
     * @param position
     * @param soundIdUrl
     */
    public void updateAlarmSound(int position, String soundIdUrl) {
        String soundResTitle = RingtoneManager.getRingtone(mContext, Uri.parse(soundIdUrl)).getTitle(mContext);
        mAlarmModels.get(position).alarmSoundTitle = soundResTitle;
        mAlarmModels.get(position).alarmSoundUrl = soundIdUrl;
        AlarmDatabaseManager.updateSoundRes((int) mAlarmModels.get(position).id, soundIdUrl, soundResTitle);
    }

    public void clearAlarm(){
        AlarmManagerHelper.cancelAlarms(mContext);
    }
}
