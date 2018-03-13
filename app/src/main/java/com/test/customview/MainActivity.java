package com.test.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.test.customview.activity.MenuViewActivity;
import com.test.customview.activity.VolumeActivity;
import com.test.customview.utils.Utils;
import com.test.customview.view.MenuView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final static  int MENU_VIEW=0;
    private final static  int VOLUME_VIEW=1;
    private ArrayList<String> titleList=new ArrayList<>();
    EasyRecyclerView mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitle();
        mList= (EasyRecyclerView) findViewById(R.id.mList);
        mList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, Utils.convertDpToPixel(0.5f,this), 0,0);//颜色 & 高度 & 左边距 & 右边距
        itemDecoration.setDrawLastItem(true);//有时候你不想让最后一个item有分割线,默认true.
        itemDecoration.setDrawHeaderFooter(false);//是否对Header于Footer有效,默认false.
        mList.addItemDecoration(itemDecoration);
        MyAdapter myAdapter=new MyAdapter(MainActivity.this,titleList);
        mList.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                    switch (position){
                        case MENU_VIEW:
                            startActivity(new Intent().setClass(MainActivity.this, MenuViewActivity.class));
                            break;
                        case VOLUME_VIEW:
                            startActivity(new Intent().setClass(MainActivity.this, VolumeActivity.class));
                            break;
                    }
            }
        });

        putBalls(3,4,"");
    }
    private void initTitle(){
        titleList.add("menuView");
        titleList.add("volumeView");
    }
    private  class MyAdapter extends RecyclerArrayAdapter<String>{

        public MyAdapter(Context context, List<String> objects) {
            super(context, objects);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new StringViewHolder(parent);
        }
    }
    public class StringViewHolder extends BaseViewHolder<String> {
        private TextView mTv_name;


        public StringViewHolder(ViewGroup parent) {
            super(parent,R.layout.item_list);
            mTv_name = $(R.id.title);
        }

        @Override
        public void setData(final String s){
            mTv_name.setText(s);
        }
    }
    public void putBalls(int balls,int boxes,String result){
        if(balls==0){
            for(int i = 0 ;i< boxes ;i++)
                result = result+ 0;
            System.out.println(result.toString());
            return;
        }

        for(int i =balls;i >=0;i--){
            String s1 = result+i;
            if(boxes == 1){
                System.out.println(s1.toString());
                return;
            }
            else
                putBalls(balls-i, boxes-1, s1);
        }


    }
}
