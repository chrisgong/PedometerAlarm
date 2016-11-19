package com.gcblog.stepalarm.support;

import com.gcblog.stepalarm.data.model.AlarmModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */

public class Utils {

    /**
     * 二分查找对应ID AlarmModel
     * @param models
     * @param id
     * @return
     */
    public static int halfSearch(List<AlarmModel> models, long id){
        int mid = -1, left, right;
        if(models != null && models.size() != 0) {
            left = 0;
            right = models.size() - 1;
            mid = (left + right) / 2;
            while (models.get(mid).id != id) {
                if (id > models.get(mid).id) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
                mid = (left + right) / 2;
            }
        }
        return mid;
    }
}
