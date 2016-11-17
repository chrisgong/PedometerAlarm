package com.gcblog.stepalarm.presenter;

import android.support.annotation.NonNull;

import com.gcblog.stepalarm.data.local.AlarmDatabaseManager;
import com.gcblog.stepalarm.data.local.IAlarmCreateListener;
import com.gcblog.stepalarm.data.model.AlarmModel;
import com.gcblog.stepalarm.view.activity.MainActivity;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

/**
 * Created by gc on 2016/11/17.
 */
@EBean
public class AlarmPresenterImpl implements AlarmContract.Presenter, IAlarmCreateListener {

    private AlarmContract.View mView;

    private ArrayList<AlarmModel> mAlarmModels = new ArrayList<>();

    public void setView(@NonNull AlarmContract.View view) {
        this.mView = view;
        mAlarmModels.addAll(AlarmDatabaseManager.getAlarms());
        mView.init(mAlarmModels);
    }

    @Override
    public void createAlarm(int hour, int min) {
        AlarmDatabaseManager.createAlarm(hour, min, this);
    }

    @Override
    public void deleteAlarm(int id) {
        AlarmDatabaseManager.deleteAlarm(id);
        for (int i = 0; i < mAlarmModels.size(); i++) {
            if(mAlarmModels.get(i).id == id){
                mAlarmModels.remove(i);
            }
        }
        mView.refresh();
    }

    @Override
    public void onSuccess(AlarmModel model) {
        mAlarmModels.add(0, model);
        mView.refresh();
    }
}
