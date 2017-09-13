package com.wyh.slide;

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
        BottomListener bottomListener;
        List<NormalItem> headers;
        List<NormalItem> footers;
        ElasticHead elasticHead;


        Builder load(List data) {
            this.data = data;
            return this;
        }

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


        public Builder refreshHeader(@NonNull int layoutId) {
            this.refreshHeader(layoutId, 0);
            return this;
        }

        public Builder refreshHeader(@NonNull int layoutId,@NonNull float heightRatio) {
            return this;
        }


        public Builder header(@NonNull int layoutId) {
            this.header(layoutId, 0);
            return this;
        }

        public Builder header(@NonNull int layoutId, @NonNull float heightRatio) {
            if (headers == null) {
                headers = new ArrayList<>();
            }
            headers.add(new NormalItem(layoutId, heightRatio));
            return this;
        }

        public Builder footer(@NonNull int layoutId) {
            this.footer(layoutId, 0);
            return this;
        }

        public Builder footer(@NonNull int layoutId, @NonNull float heightRatio) {
            if (footers == null) {
                footers = new ArrayList<>();
            }
            footers.add(new NormalItem(layoutId, heightRatio));
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

        public Builder listen(@NonNull BottomListener bottomListener) {
            this.bottomListener = bottomListener;
            return this;
        }

        public RecyclerView.Adapter into(@NonNull RecyclerView recyclerView) {
            SlideAdapter adapter = new SlideAdapter(mBuilder, recyclerView);
            mBuilder = null;
            return adapter;
        }
    }

    public static Builder load(List data) {
        return getBuilder().load(data);
    }


    private static Builder getBuilder() {
        if (mBuilder == null) {
            mBuilder = new Builder();
        }
        return mBuilder;
    }

}
