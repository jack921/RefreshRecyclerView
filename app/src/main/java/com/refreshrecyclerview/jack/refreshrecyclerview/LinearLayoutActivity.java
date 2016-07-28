package com.refreshrecyclerview.jack.refreshrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import com.refreshrecyclerview.jack.refreshrecyclerview.adapter.MyAdapterView;
import com.refreshrecyclerview.jack.refreshrecyclerview.interfaces.OnLoadMoreListener;
import com.refreshrecyclerview.jack.refreshrecyclerview.model.Bean;
import java.util.ArrayList;
import java.util.List;

public class LinearLayoutActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView=null;
    private List<Bean> listBean=new ArrayList<Bean>();
    private LinearLayoutManager mLinearLayout=null;
    private MyAdapterView myAdapterView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linearlayoutactivity);
        initData();
        mLinearLayout=new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mLinearLayout);
        myAdapterView=new MyAdapterView(this);
        myAdapterView.setListdate(listBean);
        myAdapterView.setHeaderView(LayoutInflater.from(this).inflate(R.layout.defaultfooterview,null));
        myAdapterView.setFooterView(LayoutInflater.from(this).inflate(R.layout.defaultfooterview,null));
        myAdapterView.setLoadMore(true);
        myAdapterView.setDefauLoadView(LayoutInflater.from(this).inflate(R.layout.userfooterview,null));
        mRecyclerView.setAdapter(myAdapterView);
        myAdapterView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        for(int i=0;i<10;i++){
                            listBean.add(new Bean("jack"+i));
                        }
                        myAdapterView.stopLoadMore();
                    }
                },3000);
            }
        });

    }

    public void initData(){
        for(int i=0;i<10;i++){
            listBean.add(new Bean("jack"+i));
        }
    }



}

