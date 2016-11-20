package com.gcblog.stepalarm.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.gcblog.stepalarm.R;
import com.gcblog.stepalarm.view.widget.WheelView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

import static android.content.ContentValues.TAG;

@EActivity(R.layout.activity_reminder)
public class AlarmReminderActivity extends Activity {

    private static final String[] PLANETS = new String[]{
            "30", "29", "28", "27", "26", "24", "23", "22", "21",
            "20", "19", "18", "17", "16", "15", "14", "13", "12",
            "10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};
    int index = 0;
    @ViewById(R.id.wv_reminder_step)
    protected WheelView mWvStep;

    @AfterViews
    protected void init() {
        mWvStep.setOffset(1);
        mWvStep.setItems(Arrays.asList(PLANETS));

        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> {
                    if (index == PLANETS.length) {
                       this.finish();
                    }
                    mWvStep.setSeletion(++index);
                });
    }
}
