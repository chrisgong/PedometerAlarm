package com.gcblog.stepalarm.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gcblog.stepalarm.R;
import com.gcblog.stepalarm.data.model.AlarmModel;
import com.gcblog.stepalarm.presenter.AlarmPresenterImpl;
import com.gcblog.stepalarm.view.widget.DialogUtils;
import com.ramotion.foldingcell.FoldingCell;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by gc on 2016/11/17.
 */
@EViewGroup(R.layout.layout_alarm_cell)
public class AlarmItemView extends LinearLayout implements View.OnClickListener {

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
    protected CheckBox mCbContentVibrate;

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

    @Bean
    protected AlarmPresenterImpl mPresenter;

    private int mPosition;

    private IFolderCellUnfoldListener mListener;

    private AlarmModel mModel;

    public AlarmItemView(Context context) {
        super(context);
    }

    public void bind(AlarmModel model, int position, IFolderCellUnfoldListener listener) {
        this.mModel = model;
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
        if (min < 10) {
            minStr = "0" + min;
        } else {
            minStr = String.valueOf(min);
        }

        mTvTitleAmPm.setText(amPm);
        mTvTitleTime.setText(hour + ":" + minStr);
        mSwTitleEffect.setChecked(model.isEnabled);

        mTvContentAmPm.setText(amPm);
        mTvContentTime.setText(hour + ":" + minStr);
        mSwContentEffect.setChecked(model.isEnabled);

        handlerRepeat(model);
    }

    private void handlerRepeat(AlarmModel model) {
        StringBuffer repeatDays = new StringBuffer();
        if (model.repeatSunday) {
            repeatDays.append("周日").append(" ");
        }

        if (model.repeatMonday) {
            repeatDays.append("周一").append(" ");
        }

        if (model.repeatTuesday) {
            repeatDays.append("周二").append(" ");
        }

        if (model.repeatWednesday) {
            repeatDays.append("周三").append(" ");
        }

        if (model.repeatThursday) {
            repeatDays.append("周四").append(" ");
        }

        if (model.repeatFriday) {
            repeatDays.append("周五").append(" ");
        }

        if (model.repeatSaturday) {
            repeatDays.append("周六").append(" ");
        }

        mTbContentSunday.setChecked(model.repeatSunday);
        mTbContentMonday.setChecked(model.repeatMonday);
        mTbContentTuesday.setChecked(model.repeatTuesday);
        mTbContentWednesday.setChecked(model.repeatWednesday);
        mTbContentThursday.setChecked(model.repeatThursday);
        mTbContentFriday.setChecked(model.repeatFriday);
        mTbContentSaturday.setChecked(model.repeatSaturday);
        mCbContentRepeat.setChecked(model.repeatWeekly);
        mCbContentVibrate.setChecked(model.vibrate);
        mTvTitleDate.setText(TextUtils.isEmpty(repeatDays) ? "明天" : repeatDays.toString());
    }

    @Click({R.id.layout_expend_less, R.id.layout_expend_more})
    protected void handlerFold() {
        mFoldingCell.toggle(false);
        if (mListener != null) mListener.handlerUnfold(mPosition);
    }

    @Click(R.id.layout_content_sound)
    protected void openSoundSetting() {
        DialogUtils.showSoundChooseDialog(getContext(), this);
    }

    @Click(R.id.layout_content_step)
    protected void openStepSetting() {
        DialogUtils.showStepChooseDialog(getContext(), this);
    }

    @Click(R.id.tv_content_tag)
    protected void openTagSetting() {
        DialogUtils.showInputTagsDialog(getContext(), this);
    }

    @Click(R.id.layout_content_del)
    protected void openDelSetting() {
        DialogUtils.showDeleteDialog(getContext(), this);
    }

    @CheckedChange({R.id.cb_content_repeat, R.id.cb_content_vibrate, R.id.tb_content_sunday, R.id.tb_content_monday, R.id.tb_content_tuesday, R.id.tb_content_wednesday, R.id.tb_content_thursday, R.id.tb_content_friday, R.id.tb_content_saturday})
    protected void onChooseRepeat(CompoundButton evt, boolean checked) {
        switch (evt.getId()) {
            case R.id.tb_content_sunday:
                mModel.repeatSunday = checked;
                break;
            case R.id.tb_content_monday:
                mModel.repeatMonday = checked;
                break;
            case R.id.tb_content_tuesday:
                mModel.repeatTuesday = checked;
                break;
            case R.id.tb_content_wednesday:
                mModel.repeatWednesday = checked;
                break;
            case R.id.tb_content_thursday:
                mModel.repeatThursday = checked;
                break;
            case R.id.tb_content_friday:
                mModel.repeatFriday = checked;
                break;
            case R.id.tb_content_saturday:
                mModel.repeatSaturday = checked;
                break;
            case R.id.cb_content_repeat:
                mModel.repeatWeekly = checked;
                break;
            case R.id.cb_content_vibrate:
                mModel.vibrate = checked;
                break;
        }
        mPresenter.updateAlarm(mModel);
        handlerRepeat(mModel);
    }

    @CheckedChange({R.id.sw_title_effect, R.id.sw_content_effect})
    protected void onEffectTitleChange(CompoundButton evt, boolean checked) {
        mSwTitleEffect.setChecked(checked);
        mSwContentEffect.setChecked(checked);
        mModel.isEnabled = checked;
        mPresenter.updateAlarm(mModel);
    }

    @Override
    public void onClick(View view) {

    }
}
