package com.yhao.slide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xmwj.slidingmenu.R;

import java.util.List;

import static com.yhao.main.HomeActivity.ddd;

/**
 * created by yhao on 2017/9/8.
 */


public class SlideAdapter extends RecyclerView.Adapter<SlideHolder> {


    //设置相关
    private List mData;
    private List<SAdapter.SlideItem> mSlideItems;
    private IItemBind mIItemBind;
    private IItemType mIItemType;
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
            mOpenItem.close();
            mOpenItem = null;
        }
    }


    @Override
    public SlideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SlideHolder.create(parent.getContext(), parent, mSlideItems.get(viewType - 1));
    }

    @Override
    public void onViewAttachedToWindow(final SlideHolder holder) {
        super.onViewAttachedToWindow(holder);
        ddd("onViewAttachedToWindow ");
    }

    @Override
    public void onBindViewHolder(final SlideHolder holder, int position) {
        ddd("onBindViewHolder ");
        final SAdapter.SlideItem item = mSlideItems.get(getItemViewType(position) - 1);
        holder.itemView.post(new Runnable() {
            @Override
            public void run() {
                View contentView = holder.getContentView();
                LinearLayout.LayoutParams contentParams = (LinearLayout.LayoutParams) contentView.getLayoutParams();
                contentParams.width = holder.itemView.getWidth();
                contentView.setLayoutParams(contentParams);
                View rightMenu = holder.getRightMenu();
                if (rightMenu != null) {
                    LinearLayout.LayoutParams rightMenuParams = (LinearLayout.LayoutParams) rightMenu.getLayoutParams();
                    rightMenuParams.width = (int) (holder.itemView.getWidth() * item.rightMenuRatio);
                    rightMenu.setLayoutParams(rightMenuParams);
                    ((SlideLayout) holder.getView(R.id.yhaolpz_slideLayout)).setRightMenuWidth(rightMenuParams.width);
                }
                View leftMenu = holder.getLeftMenu();
                if (leftMenu != null) {
                    LinearLayout.LayoutParams leftMenuParams = (LinearLayout.LayoutParams) leftMenu.getLayoutParams();
                    leftMenuParams.width = (int) (holder.itemView.getWidth() * item.leftMenuRatio);
                    leftMenu.setLayoutParams(leftMenuParams);
                    holder.getView(R.id.yhaolpz_slideLayout).scrollTo(leftMenuParams.width, 0);
                    ((SlideLayout) holder.getView(R.id.yhaolpz_slideLayout)).setLeftMenuWidth(leftMenuParams.width);
                }
            }
        });
        if (mIItemBind != null) {
            mIItemBind.bind(holder, mData.get(position), position);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return mIItemType == null ? 1 : mIItemType.type(mData.get(position), position);
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
    }


}
