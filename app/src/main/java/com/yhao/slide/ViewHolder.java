package com.yhao.slide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xmwj.slidingmenu.R;

/**
 * created by yhao on 2017/9/8.
 */


public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mItemView;

    private ViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mViews = new SparseArray<>();
    }

    static ViewHolder create(Context context, ViewGroup parent, int layoutId, int leftMenuId, int rightMenuId) {
        if (leftMenuId == 0 && rightMenuId == 0) {
            return new ViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false));
        }
        View root = LayoutInflater.from(context).inflate(R.layout.slide_layout, parent, false);
        LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.linearLayout);
        if (leftMenuId != 0 && rightMenuId != 0) {
            LayoutInflater.from(context).inflate(leftMenuId, linearLayout, true);
            LayoutInflater.from(context).inflate(layoutId, linearLayout, true);
            LayoutInflater.from(context).inflate(rightMenuId, linearLayout, true);
            return null;
        }
        if (leftMenuId != 0) {
            LayoutInflater.from(context).inflate(leftMenuId, linearLayout, true);
            LayoutInflater.from(context).inflate(layoutId, linearLayout, true);
            return new ViewHolder(root);
        } else {
            LayoutInflater.from(context).inflate(layoutId, linearLayout, true);
            LayoutInflater.from(context).inflate(rightMenuId, linearLayout, true);
            return new ViewHolder(root);
        }

    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
