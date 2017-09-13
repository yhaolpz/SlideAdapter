package com.wyh.slide;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xmwj.slidingmenu.R;

import java.util.List;

/**
 * created by yhao on 2017/9/8.
 * <p>
 * viewType:
 * item: 1-99
 * header:101-199
 * footer:201-299
 */


public class SlideAdapter extends RecyclerView.Adapter<ItemView> {


    private List mData;
    private List<SlideItem> mSlideItems;
    private IItemBind mIItemBind;
    private IItemType mIItemType;
    private int mItemViewWidth;
    private List<NormalItem> mHeaders;
    private List<NormalItem> mFooters;
    private static final int TYPE_ITEM_ORIGIN = 1;
    private static final int TYPE_HEADER_ORIGIN = 101;
    private static final int TYPE_FOOTER_ORIGIN = 201;


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
        if (isHeader(viewType)) {
            return ItemView.create(parent.getContext(), parent, mHeaders.get(viewType - TYPE_HEADER_ORIGIN));
        }
        if (isFooter(viewType)) {
            return ItemView.create(parent.getContext(), parent, mFooters.get(viewType - TYPE_FOOTER_ORIGIN));
        }
        return ItemView.create(parent.getContext(), parent, mSlideItems.get(viewType - 1));
    }


    @Override
    public void onBindViewHolder(final ItemView holder, int position) {
        View contentView = holder.getContentView();
        LinearLayout.LayoutParams contentParams = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        contentParams.width = mItemViewWidth;
        if (isHeader(getItemViewType(position))) {
            //todo bind 头部数据和事件
            if (mHeaders.get(position).heightRatio > 0) {
                contentParams.height = (int) (ScreenSize.h(contentView.getContext()) * mHeaders.get(position).heightRatio);
                contentView.setLayoutParams(contentParams);
            }
            return;
        }
        if (isFooter(getItemViewType(position))) {
            //todo bind 头部数据和事件
            if (mFooters.get(position - getHeaderNum() - mData.size()).heightRatio > 0) {
                contentParams.height = (int) (ScreenSize.h(contentView.getContext()) * mFooters.get(position - getHeaderNum() - mData.size()).heightRatio);
                contentView.setLayoutParams(contentParams);
            }
            return;
        }
        contentView.setLayoutParams(contentParams);
        initLeftRightMenu(holder, mItemViewWidth, position);
        if (mIItemBind != null) {
            mIItemBind.bind(holder, mData.get(position - getHeaderNum()), position - getHeaderNum());
        }
    }

    private void initLeftRightMenu(ItemView holder, int itemWidth, int position) {
        final SlideItem item = mSlideItems.get(getItemViewType(position) - 1);
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
    }

    private boolean isHeader(int viewType) {
        return viewType >= TYPE_HEADER_ORIGIN && viewType < TYPE_FOOTER_ORIGIN;
    }

    private boolean isFooter(int viewType) {
        return viewType >= TYPE_FOOTER_ORIGIN;
    }

    private int getHeaderNum() {
        return mHeaders == null ? 0 : mHeaders.size();
    }

    private int getFooterNum() {
        return mFooters == null ? 0 : mFooters.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (getHeaderNum() > 0 && position < getHeaderNum()) {
            return TYPE_HEADER_ORIGIN + position;
        }
        if (getFooterNum() > 0 && position >= getHeaderNum() + mData.size()) {
            return TYPE_FOOTER_ORIGIN + position - getHeaderNum() - mData.size();
        }
        return mIItemType == null || mSlideItems.size() == 1 ? 1 : mIItemType.type(mData.get(position - getHeaderNum()), position - getHeaderNum());
    }

    @Override
    public int getItemCount() {
        return mData.size() + getHeaderNum() + getFooterNum();
    }


    SlideAdapter(final SAdapter.Builder build, final RecyclerView recyclerView) {
        this.mSlideItems = build.slideItems;
        this.mIItemBind = build.itemBind;
        this.mIItemType = build.itemType;
        this.mData = build.data;
        this.mHeaders = build.headers;
        this.mFooters = build.footers;
        recyclerView.setAdapter(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                setScrollingItem(null);
            }
        });

        if (build.bottomListener != null) {
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (!recyclerView.canScrollVertically(1)) {
                        build.bottomListener.onBottom();
                    }
                    return false;
                }
            });
        }


        //TODO 如果recycleView 相对于屏幕有边距，则对recycleView设置margin或padding ，
        //若父布局是 viewPager 可能会报错
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        int recyclerViewMargin = layoutParams.leftMargin + layoutParams.rightMargin;
        int recyclerViewPadding = recyclerView.getPaddingLeft() + recyclerView.getPaddingRight();
        mItemViewWidth = ScreenSize.w(recyclerView.getContext()) - recyclerViewMargin - recyclerViewPadding;


    }


}
