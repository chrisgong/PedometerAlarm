package com.gcblog.stepalarm.data.local;

import com.gcblog.stepalarm.data.model.AlarmModel;

/**
 * Created by gc on 2016/11/17.
 */

public interface IAlarmCreateListener {
    void onSuccess(AlarmModel model);
}
