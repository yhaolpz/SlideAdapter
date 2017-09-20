package com.wyh.slideAdapter;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * created by yhao on 2017/9/15.
 */


class SlideItemDecoration extends RecyclerView.ItemDecoration {

    private int mItemPadding;

    SlideItemDecoration(int itemPadding) {
        mItemPadding = itemPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
                    outRect.set(0, 0, mItemPadding, 0);
            } else {
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
                    outRect.set(0, 0, 0, mItemPadding);
            }
        }
        if (layoutManager instanceof GridLayoutManager) {
            outRect.set(mItemPadding, mItemPadding, mItemPadding, mItemPadding);
        }
    }
}
