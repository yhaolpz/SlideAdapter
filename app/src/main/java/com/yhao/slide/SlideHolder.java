package com.yhao.slide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xmwj.slidingmenu.R;

import static com.yhao.main.HomeActivity.ddd;

/**
 * created by yhao on 2017/9/8.
 */


public class SlideHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mItemView;
    private View mContent;
    private View mLeftView;
    private View mRightView;


    SlideHolder(View itemView, View content, View leftMenu, View rightMenu) {
        super(itemView);
        mItemView = itemView;
        mContent = content;
        mLeftView = leftMenu;
        mRightView = rightMenu;
        mViews = new SparseArray<>();
    }

    static SlideHolder create(Context context, ViewGroup parent, final SAdapter.SlideItem slideItem) {
        final View itemView = LayoutInflater.from(context).inflate(R.layout.yhaolpz_slide_layout, parent, false);
        LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.yhaolpz_linearLayout);
        final View content;
        View leftMenu = null;
        View rightMenu = null;
        if (slideItem.leftMenuLayoutId != 0) {
            leftMenu = LayoutInflater.from(context).inflate(slideItem.leftMenuLayoutId, linearLayout, false);
            linearLayout.addView(leftMenu);
        }
        content = LayoutInflater.from(context).inflate(slideItem.itemLayoutId, linearLayout, false);
        linearLayout.addView(content);
        if (slideItem.rightMenuLayoutId != 0) {
            rightMenu = LayoutInflater.from(context).inflate(slideItem.rightMenuLayoutId, linearLayout, false);
            linearLayout.addView(rightMenu);
        }
        return new SlideHolder(itemView, content, leftMenu, rightMenu);
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    View getContentView() {
        return mContent;
    }

    View getLeftMenu() {
        return mLeftView;
    }

    View getRightMenu() {
        return mRightView;
    }

}
