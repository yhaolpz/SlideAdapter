package com.wyh.slide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xmwj.slidingmenu.R;

import java.util.List;

/**
 * created by yhao on 2017/9/8.
 */


public class SlideAdapter extends RecyclerView.Adapter<ItemView> {


    //设置相关
    private List mData;
    private List<SAdapter.SlideItem> mSlideItems;
    private IItemBind mIItemBind;
    private IItemType mIItemType;
    private RecyclerView mRecyclerView;
    private int mItemViewWidth;

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
            mOpenItem.close();
            mOpenItem = null;
        }
    }

    @Override
    public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemView.create(parent.getContext(), parent, mSlideItems.get(viewType - 1));
    }


    @Override
    public void onBindViewHolder(final ItemView holder, int position) {
        final SAdapter.SlideItem item = mSlideItems.get(getItemViewType(position) - 1);
        int itemWidth = mItemViewWidth;
        View contentView = holder.getContentView();
        LinearLayout.LayoutParams contentParams = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        contentParams.width = itemWidth;
        contentView.setLayoutParams(contentParams);
        View rightMenu = holder.getRightMenu();
        if (rightMenu != null) {
            LinearLayout.LayoutParams rightMenuParams = (LinearLayout.LayoutParams) rightMenu.getLayoutParams();
            rightMenuParams.width = (int) (itemWidth * item.rightMenuRatio);
            rightMenu.setLayoutParams(rightMenuParams);
            ((SlideLayout) holder.getView(R.id.yhaolpz_slideLayout)).setRightMenuWidth(rightMenuParams.width);
        }
        View leftMenu = holder.getLeftMenu();
        if (leftMenu != null) {
            LinearLayout.LayoutParams leftMenuParams = (LinearLayout.LayoutParams) leftMenu.getLayoutParams();
            leftMenuParams.width = (int) (itemWidth * item.leftMenuRatio);
            leftMenu.setLayoutParams(leftMenuParams);
            holder.getView(R.id.yhaolpz_slideLayout).scrollTo(leftMenuParams.width, 0);
            ((SlideLayout) holder.getView(R.id.yhaolpz_slideLayout)).setLeftMenuWidth(leftMenuParams.width);
        }
        if (mIItemBind != null) {
            mIItemBind.bind(holder, mData.get(position), position);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mIItemType == null || mSlideItems.size() == 1 ? 1 : mIItemType.type(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    SlideAdapter(SAdapter.Builder build, RecyclerView recyclerView) {
        this.mSlideItems = build.slideItems;
        this.mIItemBind = build.itemBind;
        this.mIItemType = build.itemType;
        this.mData = build.data;
        this.mRecyclerView = recyclerView;
        this.mRecyclerView.setAdapter(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                setScrollingItem(null);
            }
        });

        //TODO 如果recycleView 相对于屏幕有边距，则对recycleView设置margin或padding ，
        //若父布局是 viewPager 可能会报错
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mRecyclerView.getLayoutParams();
        int recyclerViewMargin = layoutParams.leftMargin + layoutParams.rightMargin;
        int recyclerViewPadding = mRecyclerView.getPaddingLeft() + mRecyclerView.getPaddingRight();
        mItemViewWidth = ScreenSize.w(mRecyclerView.getContext()) - recyclerViewMargin - recyclerViewPadding;


    }


}
