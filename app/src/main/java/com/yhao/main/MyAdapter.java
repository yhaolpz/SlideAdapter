package com.yhao.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xmwj.slidingmenu.R;

import java.util.List;

import static com.yhao.main.HomeActivity.ddd;
import static com.yhao.main.HomeActivity.eee;


/**
 * created by yhao on 2017/8/18.
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> mData;
    private Context mContext;

    public MyAdapter(List<String> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        eee("onCreateViewHolder");
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ddd("onBindViewHolder pos=" + position + " holderPos=" + holder.textView.getTag());
        holder.textView.setText(mData.get(position));
        holder.textView.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;


        MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

}
