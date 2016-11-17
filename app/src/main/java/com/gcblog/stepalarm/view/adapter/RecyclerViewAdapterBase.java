package com.gcblog.stepalarm.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by gc on 2016/11/17.
 */

public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent, viewType));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);
}
