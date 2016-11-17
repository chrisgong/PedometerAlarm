package com.gcblog.stepalarm.view.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.gcblog.stepalarm.R;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by gc on 2016/11/17.
 */

public class DialogUtils {

    /**
     * 步数选择弹窗
     *
     * @param context
     */
    public static void showStepChooseDialog(Context context) {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.steps));
        ListView listView = new ListView(context);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(arrayAdapter);
        MaterialDialog dialog = new MaterialDialog(context).setTitle("选择步数").setContentView(listView);
        dialog.setPositiveButton("CANCEL", view -> dialog.dismiss());
        dialog.show();
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            dialog.dismiss();
        });
    }

    public static void showSoundChooseDialog(Context context) {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.sounds));
        ListView listView = new ListView(context);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, dpAsPixels, 0, dpAsPixels);
        listView.setDividerHeight(0);
        listView.setAdapter(arrayAdapter);
        MaterialDialog dialog = new MaterialDialog(context).setTitle("选择闹钟音乐").setContentView(listView);
        dialog.setPositiveButton("CANCEL", view -> dialog.dismiss());
        dialog.show();
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            dialog.dismiss();
        });
    }

    public static void showInputTagsDialog(Context context) {
        EditText editText = new EditText(context);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        editText.setSingleLine();
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        editText.setPadding(0, dpAsPixels, 0, dpAsPixels);
        MaterialDialog dialog = new MaterialDialog(context).setTitle("标签").setContentView(editText);
        dialog.setNegativeButton("CANCEL", view -> dialog.dismiss());
        dialog.setPositiveButton("OK", view -> dialog.dismiss());
        dialog.show();
    }

    public static void showDeleteDialog(Context context) {
        MaterialDialog dialog = new MaterialDialog(context).setMessage("是否需要删除闹钟?");
        dialog.setNegativeButton("CANCEL", view -> dialog.dismiss());
        dialog.setPositiveButton("OK", view -> dialog.dismiss());
        dialog.show();
    }
}
