package com.wyh.slideAdapter;

/**
 * created by yhao on 2017/8/11.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;


public class SlideLayout extends HorizontalScrollView {


    private int mLeftMenuWidth=0;
    private int mRightMenuWidth;
    private boolean isOpen;

    public void setLeftMenuWidth(int leftMenuWidth) {
        mLeftMenuWidth = leftMenuWidth;
    }

    public void setRightMenuWidth(int rightMenuWidth) {
        mRightMenuWidth = rightMenuWidth;
    }

    public SlideLayout(Context context) {
        this(context, null);
    }


    public SlideLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
    }

    public void close() {
        isOpen = false;
        this.smoothScrollTo(mLeftMenuWidth, 0);
    }

    public void openLeftMenu() {
        isOpen = true;
        this.smoothScrollTo(0, 0);
        onOpenMenu();
    }

    public void openRightMenu() {
        isOpen = true;
        this.smoothScrollBy(mRightMenuWidth + mLeftMenuWidth + mRightMenuWidth, 0);
        onOpenMenu();
    }

    public boolean isOpen() {
        return isOpen;
    }


    public SlideAdapter getAdapter() {
        View view = this;
        while (true) {
            view = (View) view.getParent();
            if (view instanceof RecyclerView) {
                break;
            }
        }
        return (SlideAdapter) ((RecyclerView) view).getAdapter();
    }

    private void onOpenMenu() {
        getAdapter().holdOpenItem(this);
    }

    public void closeOpenMenu() {
        if (!isOpen()) {
            getAdapter().closeOpenItem();
        }
    }

    public SlideLayout getScrollingItem() {
        return getAdapter().getScrollingItem();
    }

    public void setScrollingItem(SlideLayout scrollingItem) {
        getAdapter().setScrollingItem(scrollingItem);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(mLeftMenuWidth, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        closeOpenMenu();
        if (getScrollingItem() != null && getScrollingItem() != this) {
            return false;
        }
        setScrollingItem(this);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                closeOpenMenu();
                setScrollingItem(this);
                break;
            case MotionEvent.ACTION_UP:
                setScrollingItem(null);
                int scrollX = getScrollX();
                if (System.currentTimeMillis() - downTime <= 100 && scrollX == mLeftMenuWidth) {
                    if (mCustomOnClickListener != null) {
                        mCustomOnClickListener.onClick();
                    }
                    return false;
                }
                if (scrollX < mLeftMenuWidth / 2) {
                    openLeftMenu();
                }
                if (scrollX >= mLeftMenuWidth / 2 && scrollX <= mLeftMenuWidth + mRightMenuWidth / 2) {
                    close();
                }
                if (scrollX > mLeftMenuWidth + mRightMenuWidth / 2) {
                    openRightMenu();
                }
                return false;
        }
        return super.onTouchEvent(ev);
    }

    long downTime = 0;

    interface CustomOnClickListener {
        void onClick();
    }

    private CustomOnClickListener mCustomOnClickListener;

    void setCustomOnClickListener(CustomOnClickListener listener) {
        this.mCustomOnClickListener = listener;
    }


}
