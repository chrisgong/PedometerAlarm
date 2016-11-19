package com.gcblog.stepalarm.view.widget;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.gcblog.stepalarm.R;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by gc on 2016/11/17.
 */
public class DialogManager {

    public static final int TYPE_STEP = 0;
    public static final int TYPE_TAG = 1;
    public static final int TYPE_DEL = 2;

    private static final int[] STEP_ARRAYS = new int[]{0, 5, 10, 20, 30};

    private IDialogClickCallback mListener;

    private Context mContext;

    public DialogManager(IDialogClickCallback listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
    }

    /**
     * 步数选择弹窗
     */
    public void showStepChooseDialog() {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mContext.getResources().getStringArray(R.array.steps));
        ListView listView = new ListView(mContext);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = mContext.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(arrayAdapter);
        MaterialDialog dialog = new MaterialDialog(mContext).setTitle("选择步数").setContentView(listView);
        dialog.setPositiveButton("CANCEL", view -> dialog.dismiss());
        dialog.show();
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            dialog.dismiss();
            mListener.onResult(TYPE_STEP, STEP_ARRAYS[i]);
        });
    }

    /**
     * 标签输入
     */
    public void showInputTagsDialog() {
        EditText editText = new EditText(mContext);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        editText.setSingleLine();
        float scale = mContext.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        editText.setPadding(0, dpAsPixels, 0, dpAsPixels);
        MaterialDialog dialog = new MaterialDialog(mContext).setTitle("标签").setContentView(editText);
        dialog.setNegativeButton("CANCEL", view -> dialog.dismiss());
        dialog.setPositiveButton("OK", view -> {
            mListener.onResult(TYPE_TAG, editText.getText().toString());
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * 删除
     */
    public void showDeleteDialog() {
        MaterialDialog dialog = new MaterialDialog(mContext).setMessage("是否需要删除闹钟?");
        dialog.setNegativeButton("CANCEL", view -> dialog.dismiss());
        dialog.setPositiveButton("OK", view -> {
            mListener.onResult(TYPE_DEL, null);
            dialog.dismiss();
        });
        dialog.show();
    }

    public interface IDialogClickCallback {
        void onResult(int type, Object result);
    }
}
