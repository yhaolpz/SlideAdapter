package com.wyh.slideAdapter;

/**
 * created by yhao on 2017/9/13.
 */


class NormalItem {
    NormalItem(int layoutId, float heightRatio) {
        this.layoutId = layoutId;
        this.heightRatio = heightRatio;
    }

    int layoutId;
    float heightRatio;  // 占屏幕高度比例
}