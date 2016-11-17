package com.gcblog.stepalarm.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gc on 2016/11/17.
 */

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}
