package com.gcblog.stepalarm.view.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gcblog.stepalarm.R;
import com.gcblog.stepalarm.data.model.AlarmModel;
import com.gcblog.stepalarm.data.model.RepeatDays;
import com.gcblog.stepalarm.view.widget.DialogUtils;
import com.ramotion.foldingcell.FoldingCell;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by gc on 2016/11/17.
 */
@EViewGroup(R.layout.layout_alarm_cell)
public class AlarmItemView extends LinearLayout {

    @ViewById(R.id.tv_title_am_pm)
    protected TextView mTvTitleAmPm;

    @ViewById(R.id.tv_title_time)
    protected TextView mTvTitleTime;

    @ViewById(R.id.tv_title_date)
    protected TextView mTvTitleDate;

    @ViewById(R.id.sw_title_effect)
    protected Switch mSwTitleEffect;

    @ViewById(R.id.tv_content_am_pm)
    protected TextView mTvContentAmPm;

    @ViewById(R.id.tv_content_time)
    protected TextView mTvContentTime;

    @ViewById(R.id.sw_content_effect)
    protected Switch mSwContentEffect;

    @ViewById(R.id.cb_content_repeat)
    protected CheckBox mCbContentRepeat;

    @ViewById(R.id.cb_content_vibrate)
    protected TextView mCbContentVibrate;

    @ViewById(R.id.tb_content_sunday)
    protected ToggleButton mTbContentSunday;

    @ViewById(R.id.tb_content_monday)
    protected ToggleButton mTbContentMonday;

    @ViewById(R.id.tb_content_tuesday)
    protected ToggleButton mTbContentTuesday;

    @ViewById(R.id.tb_content_wednesday)
    protected ToggleButton mTbContentWednesday;

    @ViewById(R.id.tb_content_thursday)
    protected ToggleButton mTbContentThursday;

    @ViewById(R.id.tb_content_friday)
    protected ToggleButton mTbContentFriday;

    @ViewById(R.id.tb_content_saturday)
    protected ToggleButton mTbContentSaturday;

    @ViewById(R.id.tv_content_sound_name)
    protected TextView mTvContentSoundName;

    @ViewById(R.id.tv_content_step)
    protected TextView mTvContentStep;

    @ViewById(R.id.tv_content_tag)
    protected TextView mTvContentTags;

    @ViewById(R.id.layout_folding_cell)
    protected FoldingCell mFoldingCell;

    private int mPosition;

    private IFolderCellUnfoldListener mListener;

    public AlarmItemView(Context context) {
        super(context);
    }

    public void bind(AlarmModel model, int position, IFolderCellUnfoldListener listener) {
        this.mPosition = position;
        this.mListener = listener;

        int hour = model.timeHour;
        int min = model.timeMinute;
        String amPm = "上午";
        if (hour > 12) {
            amPm = "下午";
            hour = hour - 12;
        }

        String minStr = "";
        if(min < 10){
            minStr = "0" + min;
        }else{
            minStr = String.valueOf(min);
        }

        mTvTitleAmPm.setText(amPm);
        mTvTitleTime.setText(hour + ":" + minStr);
        mSwTitleEffect.setChecked(model.isEnabled);

        mTvContentAmPm.setText(amPm);
        mTvContentTime.setText(hour + ":" + minStr);
        mSwContentEffect.setChecked(model.isEnabled);

        StringBuffer repeatDays = new StringBuffer();
        if (model.repeatingDays != null) {
            for (int i = 0; i < model.repeatingDays.size(); i++) {
                RepeatDays days = model.repeatingDays.get(i);
                if (days.value) {
                    switch (days.dayOfWeek) {
                        case 0:
                            repeatDays.append("周日").append(" ");
                            break;
                        case 1:
                            repeatDays.append("周一").append(" ");
                            break;
                        case 2:
                            repeatDays.append("周二").append(" ");
                            break;
                        case 3:
                            repeatDays.append("周三").append(" ");
                            break;
                        case 4:
                            repeatDays.append("周四").append(" ");
                            break;
                        case 5:
                            repeatDays.append("周五").append(" ");
                            break;
                        case 6:
                            repeatDays.append("周六").append(" ");
                            break;
                    }
                }
            }
        } else {
            repeatDays.append("明天");
        }
        mTvTitleDate.setText(repeatDays);
    }

    @Click({R.id.layout_expend_less, R.id.layout_expend_more})
    protected void handlerFold() {
        mFoldingCell.toggle(false);
        if (mListener != null) mListener.handlerUnfold(mPosition);
    }

    @Click(R.id.layout_content_sound)
    protected void openSoundSetting() {
        DialogUtils.showSoundChooseDialog(getContext());
    }

    @Click(R.id.layout_content_step)
    protected void openStepSetting() {
        DialogUtils.showStepChooseDialog(getContext());
    }

    @Click(R.id.tv_content_tag)
    protected void openTagSetting() {
        DialogUtils.showInputTagsDialog(getContext());
    }
}
