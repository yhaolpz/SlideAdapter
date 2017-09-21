package com.wyh.slideAdapter;

/**
 * created by yhao on 2017/9/9.
 */


public abstract class ItemBind<T> implements IItemBind {


    @Override
    public final void bind(ItemView itemView, Object data, int position) {
        onBind(itemView, (T) data, position);
    }


    public abstract void onBind(ItemView itemView, T data, int position);
}
