package com.yhao.slide;

/**
 * created by yhao on 2017/8/11.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


public class SlideLayout extends HorizontalScrollView {


    //菜单占屏幕宽度比
    private static final float radio = 0.3f;
    private final int mScreenWidth;
    private final int mMenuWidth;
    private boolean once = true;
    private boolean isOpen;

    public SlideLayout(Context context) {
        this(context, null);
    }


    public SlideLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth = ScreenUtil.getScreenWidth(context);
        mMenuWidth = (int) (mScreenWidth * radio);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
    }


    public void closeItem() {
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }


    private SlideAdapter getAdapter() {
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
        isOpen = true;
    }

    private void closeOpenMenu() {
        if (!isOpen) {
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            wrapper.getChildAt(0).getLayoutParams().width = mScreenWidth;
            wrapper.getChildAt(1).getLayoutParams().width = mMenuWidth;
            once = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (getScrollingItem() != null && getScrollingItem() != this) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                closeOpenMenu();
                setScrollingItem(this);
                break;
            case MotionEvent.ACTION_UP:
                setScrollingItem(null);
                int scrollX = getScrollX();
                if (System.currentTimeMillis() - downTime <= 100 && scrollX == 0) {
                    if (mCustomOnClickListener != null) {
                        mCustomOnClickListener.onClick();
                    }
                    return false;
                }
                if (Math.abs(scrollX) > mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    onOpenMenu();
                } else {
                    this.smoothScrollTo(0, 0);
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
