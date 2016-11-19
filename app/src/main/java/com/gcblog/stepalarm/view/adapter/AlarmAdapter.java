package com.gcblog.stepalarm.view.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;

import com.gcblog.stepalarm.data.model.AlarmModel;
import com.gcblog.stepalarm.presenter.AlarmPresenterImpl;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.HashSet;
import java.util.List;

/**
 * Created by gc on 2016/11/16.
 */
@EBean
public class AlarmAdapter extends RecyclerViewAdapterBase<AlarmModel, AlarmItemView> implements IFolderCellUnfoldListener {

    @RootContext
    protected Context mContext;

    private List<AlarmModel> mList;

    private HashSet<Integer> mUnfoldedIndexes = new HashSet<>();

    private FloatingActionButton mCreateButton;

    private FragmentManager mFragmentManager;

    private AlarmPresenterImpl mPresenter;

    public void setList(FragmentManager manager, FloatingActionButton fab, List<AlarmModel> list, AlarmPresenterImpl presenter) {
        this.mList = list;
        this.mCreateButton = fab;
        this.mFragmentManager = manager;
        this.mPresenter = presenter;
        notifyDataSetChanged();
    }

    @Override
    protected AlarmItemView onCreateItemView(ViewGroup parent, int viewType) {
        return AlarmItemView_.build(mContext);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<AlarmItemView> holder, int position) {
        AlarmItemView view = holder.getView();
        view.bind(mPresenter, mList.get(position), position, this, mFragmentManager);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void registerFold(int position) {
        mUnfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        mUnfoldedIndexes.add(position);
    }

    @Override
    public void handlerUnfold(int position) {
        if (mUnfoldedIndexes.contains(position)) {
            registerFold(position);
        } else {
            registerUnfold(position);
        }

        refreshCreateButton();
    }

    public void refreshCreateButton() {
        if (mUnfoldedIndexes.size() == 0) {
            mCreateButton.show();
        } else {
            mCreateButton.hide();
        }
    }
}
