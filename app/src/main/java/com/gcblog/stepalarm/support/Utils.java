package com.gcblog.stepalarm.support;

import android.content.Context;
import android.view.View;

import com.gcblog.stepalarm.data.model.AlarmModel;

import java.util.ArrayList;
import java.util.Calendar;
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

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 测量控件高度
     */
    public static int measureViewHight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /**
     * 查询星期几
     * @param dayOfWeek
     * @return
     */
    public static String getRepeatingDay(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "周日";
            case Calendar.MONDAY:
                return "周一";
            case Calendar.TUESDAY:
                return "周二";
            case Calendar.WEDNESDAY:
                return "周三";
            case Calendar.THURSDAY:
                return "周四";
            case Calendar.FRIDAY:
                return "周五";
            case Calendar.SATURDAY:
                return "周六";
        }
        return "";
    }
}
