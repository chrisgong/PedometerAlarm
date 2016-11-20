package com.gcblog.stepalarm.view.activity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gcblog.stepalarm.R;
import com.gcblog.stepalarm.data.AlarmContract;
import com.gcblog.stepalarm.data.local.AlarmDatabaseManager;
import com.gcblog.stepalarm.data.model.AlarmModel;
import com.gcblog.stepalarm.support.Utils;
import com.gcblog.stepalarm.view.widget.CountdownView;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WakeLock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import rx.Observable;

@EActivity(R.layout.activity_reminder)
public class AlarmReminderActivity extends Activity {

    private static final int WAKELOCK_TIMEOUT = 60 * 1000;

    private int mStepIndex = 0;

    @ViewById(R.id.cv_reminder_step)
    protected CountdownView mCvStep;

    @ViewById(R.id.reminder_tag)
    protected TextView mTvReminderTag;

    @ViewById(R.id.reminder_date_time)
    protected TextView mTvReminderDateTime;

    @Extra(AlarmContract.IDD)
    protected int mAlarmId = -1;

    private AlarmModel mModel;

    private MediaPlayer mPlayer;

    @SystemService
    protected PowerManager mPowManger;

    private PowerManager.WakeLock mWakeLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Runnable releaseWakelock = () -> {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            if (mWakeLock != null && mWakeLock.isHeld()) {
                mWakeLock.release();
            }
        };
        new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);
    }

    @AfterViews
    protected void init() {
        mModel = AlarmDatabaseManager.getAlarm(mAlarmId);
        Log.e("cim", "model:" + mModel.toString());
        if (mModel != null) {
            handlerTagAndDateTimeView();
            handlerStepCountdownView();
            handlerTone();
        }
    }

    protected void handlerTone() {
        mPlayer = new MediaPlayer();
        try {
            if (!TextUtils.isEmpty(mModel.alarmSoundUrl)) {
                Uri toneUri = Uri.parse(mModel.alarmSoundUrl);
                if (toneUri != null) {
                    mPlayer.setDataSource(this, toneUri);
                    mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mPlayer.setLooping(true);
                    mPlayer.prepare();
                    mPlayer.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 倒计数处理
     */
    private void handlerStepCountdownView() {
        ArrayList<String> items = handlerStep(mModel.step);
        mCvStep.setOffset(1);
        mCvStep.setContentItems(items);

        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> {
                    if (mStepIndex == items.size()) {
                        if (mPlayer.isPlaying()) {
                            mPlayer.stop();
                            mPlayer.release();
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            this.finishAndRemoveTask();
                        }
                    }
                    mCvStep.setSelection(++mStepIndex);
                });
    }

    /**
     * 步数数据处理
     */
    private ArrayList<String> handlerStep(int step) {
        ArrayList<String> steps = new ArrayList<>();
        for (int i = 0; i < step; i++) {
            steps.add(String.valueOf(step - i));
        }
        return steps;
    }

    /**
     * 日期时间标签显示
     */
    private void handlerTagAndDateTimeView() {
        if (!TextUtils.isEmpty(mModel.tag)) {
            mTvReminderTag.setText(mModel.tag);
        }
        String weekOfDay = Utils.getRepeatingDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        String amPm = getResources().getString(R.string.am);
        if (mModel.timeHour > 12) {
            amPm = getResources().getString(R.string.pm);
            mModel.timeHour = mModel.timeHour - 12;
        }

        String minStr = "";
        if (mModel.timeMinute < 10) {
            minStr = "0" + mModel.timeMinute;
        } else {
            minStr = String.valueOf(mModel.timeMinute);
        }

        mTvReminderDateTime.setText(weekOfDay + " " + amPm + mModel.timeHour + ":" + minStr);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Acquire wakelock
        if (mWakeLock == null) {
            mWakeLock = mPowManger.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), getClass().getSimpleName());
        }

        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }
    }

    @Override
    public void onAttachedToWindow() {
        //关键：在onAttachedToWindow中设置FLAG_HOMEKEY_DISPATCHED
        // Set the window to keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        super.onAttachedToWindow();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
        }
    }
}
