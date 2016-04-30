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
import com.refreshrecyclerview.jack.refreshrecyclerview.model.Bean;
import java.util.ArrayList;
import java.util.List;

public class LinearLayoutActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView=null;
    private List<Bean> listBean=new ArrayList<Bean>();
    private LinearLayoutManager mLinearLayout=null;
    private MyAdapterView myAdapterView=null;
    private boolean footerstatus=true;
    private Context context=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linearlayoutactivity);
        context=this;
        mLinearLayout=new LinearLayoutManager(this);
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mLinearLayout);
        myAdapterView=new MyAdapterView(this,listBean);
        myAdapterView.setHeaderView(LayoutInflater.from(context).inflate(R.layout.userfooterview,null));
        mRecyclerView.setAdapter(myAdapterView);

        if(!(mLinearLayout.findLastVisibleItemPosition()+1==listBean.size()))
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if((mLinearLayout.findLastVisibleItemPosition()+1==mLinearLayout.getItemCount())&&
                        (mLinearLayout.getItemCount()>mRecyclerView.getChildCount())){
                    if(footerstatus){
                        //滑到最底
//                      myAdapterView.StartFooterView(LayoutInflater.from(context).inflate(R.layout.userfooterview,null));
                        myAdapterView.StartFooterView(null);
                        footerstatus=false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for(int i=0;i<3;i++){
                                    listBean.add(new Bean("new"+i));
                                }
                                myAdapterView.StopFooterView();
                                myAdapterView.setItemCount(listBean.size());

                                footerstatus=true;
                            }
                        },3000);
                    }
                }
            }
        });
    }

    public void initData(){
        for(int i=0;i<30;i++){
            listBean.add(new Bean("jack"+i));
        }
    }



}

