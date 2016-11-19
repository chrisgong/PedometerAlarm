package com.gcblog.stepalarm.view.activity;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.gcblog.stepalarm.R;
import com.gcblog.stepalarm.data.model.AlarmModel;
import com.gcblog.stepalarm.presenter.AlarmContract;
import com.gcblog.stepalarm.presenter.AlarmPresenterImpl;
import com.gcblog.stepalarm.view.WrapContentLinearLayoutManager;
import com.gcblog.stepalarm.view.adapter.AlarmAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, AlarmContract.View {

    @ViewById(R.id.recycler_alarm)
    protected RecyclerView mRecyclerView;

    @ViewById(R.id.fab)
    protected FloatingActionButton mFab;

    @Bean
    protected AlarmAdapter mAdapter;

    @Bean
    protected AlarmPresenterImpl mPresenter;

    private Calendar mCalendar;

    private int mChooseSoundPostion;

    @AfterViews
    protected void initView() {
        mCalendar = Calendar.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_alarm);
        RecyclerView.LayoutManager manager = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.setView(this);
    }

    @Click(R.id.fab)
    protected void add() {
        TimePickerDialog.newInstance(MainActivity.this, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        mPresenter.createAlarm(hourOfDay, minute);
    }

    @Override
    public void init(ArrayList<AlarmModel> models) {
        mAdapter.setList(getFragmentManager(), mFab, models, mPresenter);
    }

    @Override
    public void refresh() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void openSoundChoose(int position) {
        this.mChooseSoundPostion = position;
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            mPresenter.updateAlarmSound(mChooseSoundPostion, uri.toString());
            refresh();
        }
    }
}
