package com.yhao.slide;

/**
 * created by yhao on 2017/9/9.
 */


public abstract class ItemBind<T> implements IItemBind {


    @Override
    public final void bind(SlideHolder holder, Object data, int position) {
        onBind(holder, (T) data, position);
    }


    public abstract void onBind(SlideHolder holder, T data, int postion);
}
