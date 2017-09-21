在 [动手打造史上最简单的 Recycleview 侧滑菜单](http://blog.csdn.net/yhaolpz/article/details/77366154) 中，萌生了将这种方案封装为一个开源库的想法，旨在实现调用方式最简单，且又不失可定制性。本库最大的特点的是采用了 Glide 简洁明了的链式调用方式，一句代码即可添加侧滑菜单、头部底部等。

特性：
===

1.自定义侧滑菜单布局

2.添加头部、底部

3.轻松实现加载更多

4.设置 item 间距

5.多种 item 类型

6.支持 LinearLayout 及 GridLayout

7.一句代码实现所有功能


效果:
===

**左侧滑菜单、右侧滑菜单、自定义菜单布局：**

![效果图](https://raw.githubusercontent.com/yhaolpz/SlideAdapter/master/img-folder/1.gif)&nbsp;&nbsp;![效果图](https://raw.githubusercontent.com/yhaolpz/SlideAdapter/master/img-folder/2.gif)&nbsp;&nbsp;![效果图](https://raw.githubusercontent.com/yhaolpz/SlideAdapter/master/img-folder/3.gif)

**头部、多头部：**

![效果图](https://raw.githubusercontent.com/yhaolpz/SlideAdapter/master/img-folder/4.jpg)&nbsp;&nbsp;![效果图](https://raw.githubusercontent.com/yhaolpz/SlideAdapter/master/img-folder/5.jpg)&nbsp;&nbsp;![效果图](https://raw.githubusercontent.com/yhaolpz/SlideAdapter/master/img-folder/6.jpg)

**底部、多底部、加载更多：**

![效果图](https://raw.githubusercontent.com/yhaolpz/SlideAdapter/master/img-folder/7.jpg)&nbsp;&nbsp;![效果图](https://raw.githubusercontent.com/yhaolpz/SlideAdapter/master/img-folder/8.gif)&nbsp;&nbsp;![效果图](https://raw.githubusercontent.com/yhaolpz/SlideAdapter/master/img-folder/9.gif)

集成：
===

第 1 步、在工程的 build.gradle 中添加：

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
第 2 步、在应用的  build.gradle 中添加：

```
	dependencies {
	        compile 'com.github.yhaolpz:SlideAdapter:1.0.0'
	}
```

使用：
===

下面通过简单案例演示如何在程序中使用 SlideAdapter，假设  item.xml 为：

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              android:orientation="vertical"
              android:background="#fff"
    >

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="16dp"
        android:textColor="#000"
        android:textSize="14sp"
        />

</LinearLayout>
```


数据类为：

```java
public class Bean {

    private String info;

    public Bean(String info) {
        this.info = info;
    }
    // 省略 get、set 方法...
}

```
数据为：
```java
 final List<Bean> data = new ArrayList<>();
 for (int i = 0; i < 30; i++) {
     data.add(new Bean("我是第" + i + "个item"));
 }
```



1.基本写法 ：
----------

```java
SlideAdapter.load(data)           //加载数据
            .item(R.layout.item)  //指定布局
            .into(recyclerView);  //填充到recyclerView中
```


2.数据绑定及事件监听：
----------


```java
SlideAdapter.load(data)
            .item(R.layout.item)
            .bind(itemBind)       //视图绑定
            .into(recyclerView);
```
在 itemBind 中进行数据绑定及控件的事件监听，相当于 Adapter 中的 onBindViewHolder ，实现 ItemBind 时需传入数据类型：
```java
ItemBind itemBind = new ItemBind<Bean>() {
     @Override
     public void onBind(ItemView itemView, Bean data, int position) {
         itemView.setText(R.id.textView, data.getInfo())
                 .setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                        //点击item
                     }
                  })
                 .setOnClickListener(R.id.textView, new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                        //点击textView
                     }
                 });
     }
};
```


3.添加 item 间距：
----------------

```java
SlideAdapter.load(data)
            .item(R.layout.item)
            .padding(2)           //item间距
            .bind(itemBind)
            .into(recyclerView);
```

4.添加侧滑菜单：
----------------

```java
SlideAdapter.load(data)
            .item(R.layout.item，0，0，R.layout.menu，0.35f)  //添加右侧侧滑菜单，宽为屏幕宽度的 35%
            .padding(2)
            .bind(itemBind)
            .into(recyclerView);
```
```java
SlideAdapter.load(data)
            .item(R.layout.item，R.layout.menu，0.4f，0，0)  //添加左侧侧滑菜单，宽为屏幕宽度的 40%
            .padding(2)
            .bind(itemBind)
            .into(recyclerView);
```
```java
SlideAdapter.load(data)
            .item(R.layout.item，R.layout.menu，0.4f，R.layout.menu，0.35)  //添加左侧和右侧侧滑菜单
            .padding(2)
            .bind(itemBind)
            .into(recyclerView);
```

上面调用的 item 方法为 ：

```java
item (int itemLayoutId,
      int leftMenuLayoutId, float leftMenuRatio,
      int rightMenuLayoutId, float rightMenuRatio)
```
不添加哪一侧，就把对应参数传入 0 即可。

5.侧滑菜单的数据绑定及事件监听：
----------------

菜单布局为 itemView 的一部分，所以对侧滑菜单的数据绑定及事件监听直接在 ItemBind 中进行即可。

比如侧滑菜单布局 menu.xml 为下：

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:background="#e61313"
              android:gravity="center">
        <ImageView
            android:id="@+id/rightMenu_Like"
            android:layout_width="30dp"
            android:layout_height="30dp"
            android:src="@drawable/like"/>
</LinearLayout>
```
对 rightMenu_Like 控件设置点击事件监听，就像对 item 中的普通控件设置一样：

```java
ItemBind itemBind = new ItemBind<Bean>() {
     @Override
     public void onBind(ItemView itemView, Bean data, int position) {
         itemView.setOnClickListener(R.id.rightMenu_Like, new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                        //点击rightMenu_Like
                     }
                 });
     }
};
```



6.多种 item 布局：
----------------

```java
SlideAdapter.load(data)
            .item(R.layout.item,0,0,R.layout.menu,0.35)   // 添加次序为 1
            .item(R.layout.item)                          // 添加次序为 2
            .type(new ItemType<Bean>() {
                 @Override
                 public int getItemOrder(Bean data, int position) {
                      // 返回 item 添加次序
                      return position % 2 == 0 ? 1 : 2;
                 }
             })
            .padding(2)
            .bind(itemBind)
            .into(recyclerView);
```

7.添加头部：
----------------

```java
SlideAdapter.load(data)
            .item(R.layout.item)
            .header(R.layout.head,0.1f) //添加一个头部，高为屏幕高度的 10%
            .padding(2)
            .bind(itemBind)
            .into(recyclerView);
```
```java
SlideAdapter.load(data)
            .item(R.layout.item)
            .header(R.layout.head)        //添加第1个头部
            .header(R.layout.head2,0.1f)  //添加第2个头部
            .padding(2)
            .bind(itemBind)
            .into(recyclerView);
```

8.头部的数据绑定及事件监听：
----------------

假设头部布局 head.xml 为下：

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:background="#fff"
              android:gravity="center"
    >

    <TextView
        android:layout_gravity="center"
        android:id="@+id/headText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="头部"
        android:textColor="#000"
        android:gravity="center"
        />

</LinearLayout>
```
对头部的数据绑定及事件监听在 HeaderBind 中实现：
```java
SlideAdapter.load(data)
            .item(R.layout.item)
            .header(R.layout.head,0.2f)    //添加第1个头部
            .header(R.layout.head2)        //添加第2个头部
            .bind(new HeaderBind() {
                    @Override
                    public void onBind(ItemView header, int order) {
                        if (order == 1) {
                            header.setText(R.id.headText, "我是第一个头部");
                        }
                        ...
                    }
             })
            .padding(2)
            .bind(itemBind)
            .into(recyclerView);
```

9.添加底部：
----------------
```java
SlideAdapter.load(data)
            .item(R.layout.item)
            .footer(R.layout.foot,0.1f)    //添加一个底部，高为屏幕高度的 10%
            .padding(2)
            .bind(itemBind)
            .into(recyclerView);
```
```java
SlideAdapter.load(data)
            .item(R.layout.item)
            .footer(R.layout.foot)        //添加第1个底部
            .footer(R.layout.foot,0.1f)   //添加第2个底部
            .padding(2)
            .bind(itemBind)
            .into(recyclerView);
```

10.底部的数据绑定及事件监听：
----------------

假设底部布局 foot.xml 为下：

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:background="#fff"
              android:gravity="center"
    >

    <TextView
        android:layout_gravity="center"
        android:id="@+id/footerText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="底部"
        android:textColor="#000"
        android:gravity="center"
        />

</LinearLayout>
```
对头部的数据绑定及事件监听在 FooterBind 中实现：

```java
SlideAdapter.load(data)
            .item(R.layout.item)
            .footer(R.layout.foot)            //添加第1个底部
            .footer(R.layout.foot,0.1f)       //添加第2个底部
            .bind(new FooterBind() {
                    @Override
                    public void onBind(ItemView footer, int order) {
                        if (order == 2) {
                            footer.setText(R.id.footerText, "我是第2个底部");
                        }
                        ...
                    }
             })
            .padding(2)
            .bind(itemBind)
            .into(recyclerView);
```

11.加载更多：
----------------

```java
SlideAdapter.load(data)
            .item(R.layout.item)
            .footer(R.layout.foot,0.1f)
            .padding(2)
            .bind(itemBind)
            .listen(new BottomListener() {
                    @Override
                    public void onBottom(final ItemView footer, final SlideAdapter slideAdapter) {
                       footer.setText(R.id.footerText, "正在加载，请稍后...");
                       List data2 = getMoreData();
                       slideAdapter.loadMore(data2);
                       footer.setText(R.id.footerText, "加载完成");
                    }
             })
            .into(recyclerView);
```
onBottom 方法中的 footer 为最后一个底部，若未添加底部，则 footer 为空。其中 SlideAdapter  的 loadMore() 方法专门用来加载更多数据，需在主线程中调用。

总结
==

这是我的第一个开源库，功力尚浅，如果你在使用过程中遇到了什么问题，随时可以向我反馈，我会第一时间回复并改正。如果你想在此库的基础上添加自己的功能，欢迎 fork、欢迎 star 。

# License
```text
Copyright 2017 Wang YingHao

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```










