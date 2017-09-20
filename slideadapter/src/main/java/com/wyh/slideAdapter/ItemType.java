package com.wyh.slideAdapter;

/**
 * created by yhao on 2017/9/9.
 */


public abstract class ItemType<T> implements IItemType {


    @Override
    public final int type(Object data, int position) {
        return getItemOrder((T) data, position);
    }

    public abstract int getItemOrder(T data,int position);
}
