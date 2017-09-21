package com.wyh.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.wyh.slideAdapter.BottomListener;
import com.wyh.slideAdapter.FooterBind;
import com.wyh.slideAdapter.HeaderBind;
import com.wyh.slideAdapter.ItemBind;
import com.wyh.slideAdapter.ItemView;
import com.wyh.slideAdapter.SlideAdapter;
import com.xmwj.slidingmenu.R;


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


//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<Bean> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add(new Bean("我是第" + i + "个item"));
        }


        final List<Bean> data2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data2.add(new Bean("我是一个新item"));
        }


        ItemBind<Bean> itemBind = new ItemBind<Bean>() {
            @Override
            public void onBind(final ItemView itemView, Bean data, int position) {
                itemView.setText(R.id.textView, data.getInfo())
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
                        .setOnClickListener(R.id.like, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(HomeActivity.this, "点击喜欢菜单", Toast.LENGTH_SHORT).show();
                                itemView.closeMenu();
                            }
                        });
            }
        };


        SlideAdapter.load(data)
                .item(R.layout.item, 0, 0, R.layout.menu, 0.35f)
                .padding(1)
                .header(R.layout.head, 0.1f)
                .footer(R.layout.foot, 0.1f)
                .bind(itemBind)
                .bind(new HeaderBind() {
                    @Override
                    public void onBind(ItemView header, int order) {
                        header.setOnClickListener(R.id.headText, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(HomeActivity.this, "head click", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .bind(new FooterBind() {
                    @Override
                    public void onBind(ItemView footer, int order) {
                        footer.setOnClickListener(R.id.footerText, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(HomeActivity.this, "foot click", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .listen(new BottomListener() {
                    @Override
                    public void onBottom(final ItemView footer, final SlideAdapter slideAdapter) {
                        footer.setText(R.id.footerText, "正在加载，请稍后...");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        slideAdapter.loadMore(data2);
                                        footer.setText(R.id.footerText, "正在加载");
                                    }
                                });

                            }
                        }).start();
                    }
                })
                .into(mRecyclerView);


    }
}
