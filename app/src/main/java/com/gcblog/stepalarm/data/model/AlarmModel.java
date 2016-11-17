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
    public boolean repeatSunday;
    public boolean repeatMonday;
    public boolean repeatTuesday;
    public boolean repeatWednesday;
    public boolean repeatThursday;
    public boolean repeatFriday;
    public boolean repeatSaturday;
    public boolean repeatWeekly;
    public boolean vibrate;
    public int alarmSoundRes;
    public String name;
    public boolean isEnabled;

    public AlarmModel() {
    }

    public AlarmModel(int timeHour, int timeMinute, boolean repeatSunday, boolean repeatMonday, boolean repeatTuesday, boolean repeatWednesday, boolean repeatThursday, boolean repeatFriday, boolean repeatSaturday, boolean repeatWeekly, int alarmSoundRes, String name, boolean isEnabled) {
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.repeatSunday = repeatSunday;
        this.repeatMonday = repeatMonday;
        this.repeatTuesday = repeatTuesday;
        this.repeatWednesday = repeatWednesday;
        this.repeatThursday = repeatThursday;
        this.repeatFriday = repeatFriday;
        this.repeatSaturday = repeatSaturday;
        this.repeatWeekly = repeatWeekly;
        this.alarmSoundRes = alarmSoundRes;
        this.name = name;
        this.isEnabled = isEnabled;
    }
}
