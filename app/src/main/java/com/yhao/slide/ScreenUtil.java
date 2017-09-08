package com.yhao.slide;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * created by yhao on 2017/8/18.
 */


class ScreenUtil {

    static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
