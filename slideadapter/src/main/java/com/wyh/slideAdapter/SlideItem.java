package com.wyh.slideAdapter;

/**
 * created by yhao on 2017/9/13.
 */


 class SlideItem {
    int itemLayoutId;
    int leftMenuLayoutId;
    float leftMenuRatio;
    int rightMenuLayoutId;
    float rightMenuRatio;

    SlideItem(int itemLayoutId, int leftMenuLayoutId, float leftMenuRatio, int rightMenuLayoutId, float rightMenuRatio) {
        this.itemLayoutId = itemLayoutId;
        this.leftMenuLayoutId = leftMenuLayoutId;
        this.leftMenuRatio = leftMenuRatio;
        this.rightMenuLayoutId = rightMenuLayoutId;
        this.rightMenuRatio = rightMenuRatio;
    }
}
