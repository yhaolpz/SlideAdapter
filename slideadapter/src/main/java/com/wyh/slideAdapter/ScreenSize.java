package com.wyh.slideAdapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * created by yhao on 2017/9/12.
 */


class ScreenSize {

    private static int width = 0;
    private static int height = 0;

    static int w(Context context) {
        if (width == 0) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            width = outMetrics.widthPixels;
        }
        return width;
    }

    static int h(Context context) {
        if (height == 0) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            height = outMetrics.heightPixels;
        }
        return height;
    }

}
