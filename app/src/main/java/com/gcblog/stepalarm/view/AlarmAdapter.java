package com.gcblog.stepalarm.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcblog.stepalarm.R;
import com.gcblog.stepalarm.data.model.AlarmModel;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

/**
 * Created by gc on 2016/11/16.
 */

public class AlarmAdapter extends RecyclerView.Adapter {

    private List<AlarmModel> mList;

    private OnListItemClickListener mListener;

    private HashSet<Integer> mUnfoldedIndexes = new HashSet<>();

    public AlarmAdapter(List<AlarmModel> list, OnListItemClickListener lis) {
        this.mList = list;
        this.mListener = lis;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PatientContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_alarm_cell, null), mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (mUnfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        mUnfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        mUnfoldedIndexes.add(position);
    }


    public class PatientContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnListItemClickListener mListener;
        private LinearLayout mLayoutExpendMore, mLayoutExpendLess;
        private ImageView mIvExpendMore, mIvExpendLess;
        private FoldingCell mFoldingCell;

        public PatientContentViewHolder(View itemView, OnListItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            this.mLayoutExpendLess = (LinearLayout) itemView.findViewById(R.id.layout_expend_less);
            this.mLayoutExpendMore = (LinearLayout) itemView.findViewById(R.id.layout_expend_more);
            this.mIvExpendMore = (ImageView) itemView.findViewById(R.id.iv_expend_more);
            this.mIvExpendLess = (ImageView) itemView.findViewById(R.id.iv_expend_less);
            this.mFoldingCell = (FoldingCell) itemView.findViewById(R.id.layout_folding_cell);

            mLayoutExpendLess.setOnClickListener(this);
            mLayoutExpendMore.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mFoldingCell.toggle(false);
            registerToggle(getPosition());

            if (view.getId() == R.id.layout_expend_more) {

            } else if (view.getId() == R.id.layout_expend_less) {

            }
        }
    }

    public interface OnListItemClickListener {
        void onItemClick(int position);
    }
}
