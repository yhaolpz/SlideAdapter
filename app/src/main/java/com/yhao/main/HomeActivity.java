package com.yhao.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.xmwj.slidingmenu.R;
import com.yhao.slide.ItemBind;
import com.yhao.slide.ItemType;
import com.yhao.slide.SAdapter;
import com.yhao.slide.SlideAdapter;
import com.yhao.slide.SlideHolder;

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
        final List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("我是第" + i + "个item");
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyItemDecoration());


        SAdapter
                .data(data)
                .item(R.layout.item)
                .item(R.layout.item2, R.layout.right_menu, 0.2f, 0, 0)
                .bind(new ItemBind<String>() {
                    @Override
                    public void onBind(SlideHolder holder, String s, int position) {
                        TextView textView = holder.getView(R.id.textView);
                        textView.setText(s);
                    }
                })
                .type(new ItemType<String>() {

                    @Override
                    public int viewType(String data, int position) {
                        return position % 2 == 0 ? 1 : 2;
                    }
                })
                .into(mRecyclerView);


    }
}
