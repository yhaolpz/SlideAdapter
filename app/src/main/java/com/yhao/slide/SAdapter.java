package com.yhao.slide;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * created by yhao on 2017/9/9.
 */


public class SAdapter {

    private static Builder mBuilder = null;

    public static class Builder {
        List data;
        List<SlideItem> slideItems;
        IItemBind itemBind;
        IItemType itemType;


        public Builder item(@NonNull int itemLayoutId) {
            this.item(itemLayoutId, 0, 0, 0, 0);
            return this;
        }

        public Builder item(@NonNull int itemLayoutId, @NonNull int leftMenuLayoutId, @NonNull float leftMenuRatio, @NonNull int rightMenuLayoutId, @NonNull float rightMenuRatio) {
            if (slideItems == null) {
                slideItems = new ArrayList<>();
            }
            slideItems.add(new SlideItem(itemLayoutId, leftMenuLayoutId, leftMenuRatio, rightMenuLayoutId, rightMenuRatio));
            return this;
        }

        public Builder data(List data) {
            this.data = data;
            return this;
        }

        public Builder bind(@NonNull IItemBind itemBind) {
            this.itemBind = itemBind;
            return this;
        }

        public Builder type(@NonNull IItemType itemType) {
            this.itemType = itemType;
            return this;
        }

        public SlideAdapter into(@NonNull RecyclerView recyclerView) {
            SlideAdapter adapter = new SlideAdapter(mBuilder, recyclerView);
            mBuilder = null;
            return adapter;
        }
    }

    public static Builder data(List data) {
        return getBuilder().data(data);
    }

    public static Builder item(@NonNull int itemLayoutId) {
        return getBuilder().item(itemLayoutId, 0, 0, 0, 0);
    }

    public static Builder item(@NonNull int itemLayoutId, @NonNull int leftMenuLayoutId, @NonNull float leftMenuRatio, @NonNull int rightMenuLayoutId, @NonNull float rightMenuRatio) {
        return getBuilder().item(itemLayoutId, leftMenuLayoutId, leftMenuRatio, rightMenuLayoutId, rightMenuRatio);
    }


    private static Builder getBuilder() {
        if (mBuilder == null) {
            mBuilder = new Builder();
        }
        return mBuilder;
    }

    static class SlideItem {
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

}
