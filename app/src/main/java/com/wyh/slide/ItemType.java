package com.wyh.slide;

/**
 * created by yhao on 2017/9/9.
 */


public abstract class ItemType<T> implements IItemType {


    @Override
    public final int type(Object data, int position) {
        return viewType((T) data, position);
    }

    public abstract int viewType(T data,int position);
}
