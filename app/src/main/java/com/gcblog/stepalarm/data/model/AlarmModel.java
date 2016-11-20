package com.gcblog.stepalarm.data.model;

import java.util.Calendar;

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
    public String alarmSoundUrl;
    public String alarmSoundTitle;
    public boolean isEnabled;
    public int step;
    public String tag;
    public boolean hide;

    public AlarmModel() {
    }

    public boolean getRepeatingDay(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return repeatSunday;
            case Calendar.MONDAY:
                return repeatMonday;
            case Calendar.TUESDAY:
                return repeatTuesday;
            case Calendar.WEDNESDAY:
                return repeatWednesday;
            case Calendar.THURSDAY:
                return repeatThursday;
            case Calendar.FRIDAY:
                return repeatFriday;
            case Calendar.SATURDAY:
                return repeatSaturday;
        }
        return false;
    }

    @Override
    public String toString() {
        return "AlarmModel{" +
                "id=" + id +
                ", timeHour=" + timeHour +
                ", timeMinute=" + timeMinute +
                ", repeatSunday=" + repeatSunday +
                ", repeatMonday=" + repeatMonday +
                ", repeatTuesday=" + repeatTuesday +
                ", repeatWednesday=" + repeatWednesday +
                ", repeatThursday=" + repeatThursday +
                ", repeatFriday=" + repeatFriday +
                ", repeatSaturday=" + repeatSaturday +
                ", repeatWeekly=" + repeatWeekly +
                ", vibrate=" + vibrate +
                ", alarmSoundUrl='" + alarmSoundUrl + '\'' +
                ", alarmSoundTitle='" + alarmSoundTitle + '\'' +
                ", isEnabled=" + isEnabled +
                ", step=" + step +
                ", tag='" + tag + '\'' +
                ", hide=" + hide +
                '}';
    }
}
