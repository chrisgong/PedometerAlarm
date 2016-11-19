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
    public int step;
    public String tag;
    public boolean hide;

    public AlarmModel() {
    }
}
