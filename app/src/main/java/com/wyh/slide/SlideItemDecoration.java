package com.wyh.slide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * created by yhao on 2017/9/15.
 */


class SlideItemDecoration extends RecyclerView.ItemDecoration {

    private int mDividerWidth;
    private Paint mPaint;

    SlideItemDecoration(Context context, int dividerWidth, int color) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(context, color));
        mDividerWidth = dividerWidth;
        mPaint.setStrokeWidth(mDividerWidth * 2);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
                    outRect.set(0, 0, mDividerWidth, 0);
            } else {
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
                    outRect.set(0, 0, 0, mDividerWidth);
            }
        }
        if (layoutManager instanceof GridLayoutManager) {
            outRect.set(mDividerWidth, mDividerWidth, mDividerWidth, mDividerWidth);
        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager && !(layoutManager instanceof GridLayoutManager)) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
//            drawGrideview(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        // recyclerView是否设置了paddingLeft和paddingRight
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            // divider的top 应该是 item的bottom 加上 marginBottom 再加上 Y方向上的位移
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(child.getTranslationY());
            // divider的bottom就是top加上divider的高度了
            final int bottom = top + mDividerWidth;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        // 和drawVertical差不多 left right 与 top和bottom对调一下
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin +
                    Math.round(child.getTranslationX());
            final int right = left + mDividerWidth;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }


}
