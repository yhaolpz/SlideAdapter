package com.yhao.slide;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * created by yhao on 2017/9/8.
 */


public class SlideAdapter extends RecyclerView.Adapter<ViewHolder> {


    //设置相关
    private int mSize;
    private int mItemId;
    private ItemBind mItemBind;
    private ItemType mItemType;
    private int[] mItemIds;
    private int mLeftMenuId;
    private int mRightMenuId;
    private RecyclerView mRecyclerView;

    //侧滑相关
    private SlideLayout mOpenItem;
    private SlideLayout mScrollingItem;

    SlideLayout getScrollingItem() {
        return mScrollingItem;
    }

    void setScrollingItem(SlideLayout scrollingItem) {
        mScrollingItem = scrollingItem;
    }

    void holdOpenItem(SlideLayout openItem) {
        mOpenItem = openItem;
    }

    void closeOpenItem() {
        if (mOpenItem != null && mOpenItem.isOpen()) {
            mOpenItem.closeItem();
            mOpenItem = null;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemId = mItemType == null ? mItemId : mItemIds[viewType];
        return ViewHolder.create(parent.getContext(), parent, itemId, mLeftMenuId, mRightMenuId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mItemBind != null) {
            mItemBind.bind(holder, position);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mItemType == null ? 0 : mItemType.getItemType(position);
    }

    @Override
    public int getItemCount() {
        return mSize;
    }


    public static class Builder {

        private int itemId;
        private int[] itemIds;
        private int size;
        private ItemBind itemBind;
        private ItemType itemType;
        private int leftMenuId;
        private int rightMenuId;
        private RecyclerView recyclerView;

        public Builder item(int layoutId) {
            this.itemId = layoutId;
            return this;
        }

        public Builder leftMenu(int layoutId) {
            this.leftMenuId = layoutId;
            return this;
        }

        public Builder rightMenu(int layoutId) {
            this.rightMenuId = layoutId;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder bind(ItemBind itemBind) {
            this.itemBind = itemBind;
            return this;
        }

        public Builder items(ItemType itemType, @NonNull int... layoutIds) {
            this.itemType = itemType;
            this.itemIds = layoutIds;
            return this;
        }

        public SlideAdapter into(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return new SlideAdapter(this);
        }
    }


    private SlideAdapter(Builder build) {
        this.mSize = build.size;
        this.mLeftMenuId = build.leftMenuId;
        this.mRightMenuId = build.rightMenuId;
        this.mRecyclerView = build.recyclerView;
        this.mItemId = build.itemId;
        this.mItemBind = build.itemBind;
        this.mItemType = build.itemType;
        this.mItemIds = build.itemIds;
        mRecyclerView.setAdapter(this);
    }


}
