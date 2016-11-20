package com.gcblog.stepalarm.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gcblog.stepalarm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 倒计数控件
 */
public class CountdownView extends ScrollView {
    private LinearLayout mViews;
    private List<String> mContentItems;
    public static final int OFF_SET_DEFAULT = 1;
    private int mOffset = OFF_SET_DEFAULT; // 偏移量（需要在最前面和最后面补全）
    private int mDisplayItemCount; // 每页显示的数量
    private int mSelectedIndex = 1;
    private int mInitialY;
    private Runnable mScrollerTask;
    private int mNewCheck = 50;
    private int mItemHeight = 0;

    public CountdownView(Context context) {
        super(context);
        init(context);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    public void setContentItems(List<String> list) {
        if (null == mContentItems) {
            mContentItems = new ArrayList<>();
        }
        mContentItems.clear();
        mContentItems.addAll(list);
        // 前面和后面补全
        for (int i = 0; i < mOffset; i++) {
            mContentItems.add(0, "");
            mContentItems.add("");
        }
        initData();
    }

    public void setOffset(int offset) {
        this.mOffset = offset;
    }

    private void init(Context context) {
        this.setVerticalScrollBarEnabled(false);

        mViews = new LinearLayout(context);
        mViews.setOrientation(LinearLayout.VERTICAL);
        this.addView(mViews);

        mScrollerTask = () -> {
            int newY = getScrollY();
            if (mInitialY - newY == 0) { // stopped
                final int remainder = mInitialY % mItemHeight;
                final int divided = mInitialY / mItemHeight;
                if (remainder == 0) {
                    mSelectedIndex = divided + mOffset;
                } else {
                    if (remainder > mItemHeight / 2) {
                        CountdownView.this.post(() -> {
                            CountdownView.this.smoothScrollTo(0, mInitialY - remainder + mItemHeight);
                            mSelectedIndex = divided + mOffset + 1;
                        });
                    } else {
                        CountdownView.this.post(() -> {
                            CountdownView.this.smoothScrollTo(0, mInitialY - remainder);
                            mSelectedIndex = divided + mOffset;
                        });
                    }
                }
            } else {
                mInitialY = getScrollY();
                CountdownView.this.postDelayed(mScrollerTask, mNewCheck);
            }
        };
    }

    private void initData() {
        mDisplayItemCount = mOffset * 2 + 1;
        for (String item : mContentItems) {
            mViews.addView(createView(item));
        }
    }

    private TextView createView(String item) {
        TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.layout_reminder_textview, null);
        tv.setText(item);
        if (0 == mItemHeight) {
            mItemHeight = getViewMeasuredHeight(tv);
            mViews.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight * mDisplayItemCount));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
            this.setLayoutParams(new LinearLayout.LayoutParams(lp.width, mItemHeight * mDisplayItemCount));
        }
        return tv;
    }

    public void setSelection(int position) {
        final int p = position;
        mSelectedIndex = p + mOffset;
        this.post(() -> CountdownView.this.smoothScrollTo(0, p * mItemHeight));
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    private int getViewMeasuredHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
        return view.getMeasuredHeight();
    }
}