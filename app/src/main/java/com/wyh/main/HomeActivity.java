package com.wyh.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wyh.slide.ItemType;
import com.xmwj.slidingmenu.R;
import com.wyh.slide.ItemBind;
import com.wyh.slide.SAdapter;
import com.wyh.slide.ItemView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static void ddd(String text) {
        Log.d("YHAO", text);
    }

    public static void eee(String text) {
        Log.e("YHAO", text);
    }


    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        init();
    }

    private void init() {


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyItemDecoration());


        //TODO item 宽度为屏幕宽度减去 RecyclerView 的 margin和padding ，
        //TODO 所以只支持 LinearLayoutManager, 若设置RecyclerView边距只能设置RecyclerView的margin和padding


        final List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("我是第" + i + "个item");
        }

        ItemBind<String> itemBind = new ItemBind<String>() {
            @Override
            public void onBind(ItemView holder, String s, int position) {
                TextView textView = holder.getView(R.id.textView);
                textView.setText(s);
            }
        };



        //  .head 固定高度
        //  .elasticHead (有弹性的)    按顺序从上到下添加
        //  .onRefresh  监听刷新事件
        //  .foot
        //  .loadMore 监听加载更多事件


        SAdapter.load(data)
                .item(R.layout.item, 0, 0, R.layout.right_menu, 0.2f)
                .item(R.layout.item2)
                .item(R.layout.item)
                .type(new ItemType<String>() {

                    @Override
                    public int viewType(String data, int position) {
                        return position % 3 + 1;
                    }
                })
                .bind(new ItemBind<String>() {
                    @Override
                    public void onBind(final ItemView itemView, String data, int position) {
                        itemView.setText(R.id.textView, data)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(HomeActivity.this, "click", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setOnClickListener(R.id.textView, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(HomeActivity.this, "textView click", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .setOnClickListener(R.id.icon, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(HomeActivity.this, "menu click", Toast.LENGTH_SHORT).show();
                                        itemView.closeMenu();
                                    }
                                });
                    }
                })
                .into(mRecyclerView);


    }
}
