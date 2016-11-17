package com.gcblog.stepalarm.presenter;

import com.gcblog.stepalarm.data.model.AlarmModel;

import java.util.ArrayList;

/**
 * Created by gc on 2016/11/17.
 */

public interface AlarmContract {
    interface View {
        void init(ArrayList<AlarmModel> models);

        void refresh();
    }

    interface Presenter {
        void createAlarm(int hour, int min);

        void deleteAlarm(int id);
    }
}
