package com.yhao.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.xmwj.slidingmenu.R;
import com.yhao.slide.ItemBind;
import com.yhao.slide.ItemType;
import com.yhao.slide.SlideAdapter;
import com.yhao.slide.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static void logd(String text) {
        Log.d("YHAO", text);
    }

    public static void loge(String text) {
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


        new SlideAdapter
                .Builder()
                .item(R.layout.item)
                .rightMenu(R.layout.right_menu)
                .size(data.size())
                .bind(new ItemBind() {
                    @Override
                    public void bind(ViewHolder holder, int position) {
                        TextView textView = holder.getView(R.id.textView);
                        textView.setText(data.get(position));
                    }
                })
                .into(mRecyclerView);






//           .items(new ItemType() {
//            @Override
//            public int getItemType(int position) {
//                return position % 2 == 0 ? 0 : 1;
//            }
//        }, R.layout.item, R.layout.item2)


    }
}
