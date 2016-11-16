package com.gcblog.stepalarm.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.TextView;

import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.gcblog.stepalarm.R;
import com.gcblog.stepalarm.data.model.AlarmModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, SwipeRefreshLayout.OnRefreshListener, AlarmAdapter.OnListItemClickListener{
    private static final String TIME_PATTERN = "HH:mm";

    private Calendar mCalendar;
    private SimpleDateFormat mTimeFormat;

    private TextView mTvTime;
    protected RecyclerView mRecyclerView;
    protected AlarmAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCalendar = Calendar.getInstance();
        mTimeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> TimePickerDialog.newInstance(MainActivity.this, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true).show(getFragmentManager(), ""));

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_alarm);

        RecyclerView.LayoutManager manager = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);


        List<AlarmModel> modelListView = new ArrayList<>();
        modelListView.add(new AlarmModel());
        modelListView.add(new AlarmModel());
        modelListView.add(new AlarmModel());

        mAdapter = new AlarmAdapter(modelListView, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(int position) {

    }
}
