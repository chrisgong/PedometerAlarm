package com.gcblog.stepalarm.data.model;

import android.net.Uri;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlarmModel extends RealmObject {

    @PrimaryKey
    public long id;

    public int timeHour;
    public int timeMinute;
    public RealmList<RepeatDays> repeatingDays;
    public boolean repeatWeekly;
    public int alarmSoundRes;
    public String name;
    public boolean isEnabled;

    public AlarmModel() {
    }

    public AlarmModel(int timeHour, int timeMinute, RealmList<RepeatDays> repeatingDays, boolean repeatWeekly, int alarmSoundRes, String name, boolean isEnabled) {
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.repeatingDays = repeatingDays;
        this.repeatWeekly = repeatWeekly;
        this.alarmSoundRes = alarmSoundRes;
        this.name = name;
        this.isEnabled = isEnabled;
    }
}
