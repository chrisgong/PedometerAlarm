package com.gcblog.stepalarm.view.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.gcblog.stepalarm.R;
import com.gcblog.stepalarm.data.model.AlarmModel;
import com.gcblog.stepalarm.presenter.AlarmPresenterImpl;
import com.gcblog.stepalarm.support.Utils;
import com.gcblog.stepalarm.view.widget.DialogManager;
import com.ramotion.foldingcell.FoldingCell;

import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

/**
 * Created by gc on 2016/11/17.
 */
@EViewGroup(R.layout.layout_alarm_cell)
public class AlarmItemView extends LinearLayout implements TimePickerDialog.OnTimeSetListener, DialogManager.IDialogClickCallback {

    @ViewById(R.id.tv_title_am_pm)
    protected TextView mTvTitleAmPm;

    @ViewById(R.id.tv_title_time)
    protected TextView mTvTitleTime;

    @ViewById(R.id.tv_title_date)
    protected TextView mTvTitleDate;

    @ViewById(R.id.tv_title_tag)
    protected TextView mTvTitleTag;

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

    @ViewById(R.id.layout_content_weekly_choose)
    protected LinearLayout mLayoutWeeklyChoose;

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

    protected AlarmPresenterImpl mPresenter;

    private int mPosition;

    private IFolderCellUnfoldListener mListener;

    private AlarmModel mModel;

    private FragmentManager mFragmentManager;

    private DialogManager mDialogManager;

    private boolean mEffectChecked = false;
    private Animation mAnimationShowWeekly, mAnimationHideWeekly;

    public AlarmItemView(Context context) {
        super(context);
    }

    public void bind(AlarmPresenterImpl presenter, AlarmModel model, int position, IFolderCellUnfoldListener listener, FragmentManager manager) {
        this.mModel = model;
        this.mPosition = position;
        this.mListener = listener;
        this.mFragmentManager = manager;
        this.mPresenter = presenter;
        mDialogManager = new DialogManager(this, getContext());
        mAnimationShowWeekly = AnimationUtils.loadAnimation(getContext(), R.anim.anim_in);
        mAnimationHideWeekly = AnimationUtils.loadAnimation(getContext(), R.anim.anim_out);

        handlerDate(model);
    }

    private void handlerDate(AlarmModel model) {
        int hour = model.timeHour;
        int min = model.timeMinute;
        String amPm = getResources().getString(R.string.am);
        if (hour > 12) {
            amPm = getResources().getString(R.string.pm);
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

        mTbContentSunday.setChecked(model.repeatSunday);
        mTbContentMonday.setChecked(model.repeatMonday);
        mTbContentTuesday.setChecked(model.repeatTuesday);
        mTbContentWednesday.setChecked(model.repeatWednesday);
        mTbContentThursday.setChecked(model.repeatThursday);
        mTbContentFriday.setChecked(model.repeatFriday);
        mTbContentSaturday.setChecked(model.repeatSaturday);
        mCbContentRepeat.setChecked(model.repeatWeekly);
        mCbContentVibrate.setChecked(model.vibrate);
        mTvTitleDate.setText(mPresenter.getAlarmDate(mModel));
        mTvContentTags.setText(model.tag);
        if (!TextUtils.isEmpty(model.tag)) {
            mTvTitleTag.setVisibility(View.VISIBLE);
            mTvTitleTag.setText(model.tag);
        } else {
            mTvTitleTag.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model.alarmSoundTitle)) {
            mTvContentSoundName.setText(model.alarmSoundTitle);
        } else {
            mTvContentSoundName.setText(getResources().getString(R.string.none));
        }
    }

    @Click({R.id.layout_expend_less, R.id.layout_expend_more})
    protected void handlerFold() {
        mFoldingCell.toggle(false);
        if (!mFoldingCell.isUnfolded()) {
            mLayoutWeeklyChoose.clearAnimation();
        }
        if (mListener != null) mListener.handlerUnfold(mPosition);
    }

    @Click(R.id.layout_content_sound)
    protected void openSoundSetting() {
        mPresenter.openSoundChoose(mPosition);
    }

    @Click(R.id.layout_content_step)
    protected void openStepSetting() {
        mDialogManager.showStepChooseDialog();
    }

    @Click(R.id.tv_content_tag)
    protected void openTagSetting() {
        mDialogManager.showInputTagsDialog();
    }

    @Click(R.id.layout_content_del)
    protected void openDelSetting() {
        mDialogManager.showDeleteDialog();
    }

    @CheckedChange({R.id.tb_content_sunday, R.id.tb_content_monday, R.id.tb_content_tuesday, R.id.tb_content_wednesday, R.id.tb_content_thursday, R.id.tb_content_friday, R.id.tb_content_saturday})
    protected void onChooseDayRepeat(CompoundButton evt, boolean checked) {
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
        }
        mPresenter.updateAlarm(mModel);
        mPresenter.resetAlarm();
        handlerDate(mModel);
    }

    @CheckedChange(R.id.cb_content_repeat)
    protected void onChooseRepeat(CompoundButton evt, boolean checked) {
        mModel.repeatWeekly = checked;
        mModel.repeatSunday = checked;
        mModel.repeatMonday = checked;
        mModel.repeatTuesday = checked;
        mModel.repeatWednesday = checked;
        mModel.repeatThursday = checked;
        mModel.repeatFriday = checked;
        mModel.repeatSaturday = checked;
        mPresenter.updateAlarm(mModel);
        mPresenter.resetAlarm();
        handlerDate(mModel);
        handlerWeeklyChoose(checked);
    }

    private void handlerWeeklyChoose(boolean checked) {
        mLayoutWeeklyChoose.setVisibility(checked ? View.VISIBLE : View.GONE);
        mLayoutWeeklyChoose.startAnimation(checked ? mAnimationShowWeekly : mAnimationHideWeekly);

        int height = Utils.measureViewHight(mFoldingCell);
        int chooseHeight = Utils.dip2px(getContext(), 40);

        LayoutParams params = (LayoutParams) mFoldingCell.getLayoutParams();
        params.height = checked ? height : height - chooseHeight;
        mFoldingCell.setLayoutParams(params);
    }

    @CheckedChange(R.id.cb_content_vibrate)
    protected void onChooseVibrate(CompoundButton evt, boolean checked) {
        mModel.vibrate = checked;
        mPresenter.updateAlarm(mModel);
    }

    @CheckedChange({R.id.sw_title_effect, R.id.sw_content_effect})
    protected void onEffectTitleChange(CompoundButton cb, boolean checked) {
        mEffectChecked = false;
        mSwTitleEffect.setChecked(checked);
        mSwContentEffect.setChecked(checked);
        mModel.isEnabled = checked;
        mPresenter.updateAlarm(mModel);
        if (!mEffectChecked) {
            mPresenter.resetAlarm();
            mEffectChecked = true;
        }
    }

    @Click({R.id.layout_title_time, R.id.layout_content_time})
    protected void changeTime() {
        TimePickerDialog.newInstance(this, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true).show(mFragmentManager, "");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mModel.timeHour = hourOfDay;
        mModel.timeMinute = minute;
        mPresenter.updateAlarm(mModel);
        handlerDate(mModel);
    }

    @Override
    public void onResult(int type, Object result) {
        switch (type) {
            case DialogManager.TYPE_DEL:
                mFoldingCell.setUnfoldedListener(unfolded -> {
                    if (!unfolded) mPresenter.deleteAlarm(mModel.id, mPosition);
                });
                mFoldingCell.toggle(true);
                mListener.handlerUnfold(mPosition);
                break;
            case DialogManager.TYPE_TAG:
                mModel.tag = (String) result;
                mPresenter.updateAlarm(mModel);
                break;
            case DialogManager.TYPE_STEP:
                mModel.step = (int) result;
                mPresenter.updateAlarm(mModel);
                break;
        }
    }
}
