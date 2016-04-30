package com.refreshrecyclerview.jack.refreshrecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import com.refreshrecyclerview.jack.refreshrecyclerview.adapter.StaggeredMyAdapter;
import com.refreshrecyclerview.jack.refreshrecyclerview.model.Bean;
import java.util.ArrayList;
import java.util.List;

public class StaggeredActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView=null;
    private List<Bean> listBean=new ArrayList<Bean>();
    private StaggeredGridLayoutManager StaggeredLayout=null;
    private StaggeredMyAdapter myAdapterView=null;
    private Context context=null;
    private int SCROLL_STOP=0;
    private boolean footerstatus=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridlayoutactivity);
        context = this;
        StaggeredLayout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(StaggeredLayout);
        myAdapterView = new StaggeredMyAdapter(this, listBean);
        myAdapterView.setHeaderView(LayoutInflater.from(context).inflate(R.layout.userfooterview, null));
        mRecyclerView.setAdapter(myAdapterView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(SCROLL_STOP==newState&&(StaggeredLayout.getItemCount()>mRecyclerView.getChildCount())){
                    //停止滑动
                    int colnum=StaggeredLayout.getColumnCountForAccessibility(null,null);
                    int positions[]=new int[colnum];
                    StaggeredLayout.findLastVisibleItemPositions(positions);
                    for(int i=0;i<positions.length;i++){
                        if(StaggeredLayout.findViewByPosition(positions[i]).getBottom()<=mRecyclerView.getHeight()){
                            //滑到底部了
                            if(footerstatus){
//                             myAdapterView.StartFooterView(LayoutInflater.from(context).inflate(R.layout.userfooterview,null));
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
                            break;
                        }
                    }
                }
            }
        });

    }

    public void initData(){
        for(int i=0;i<50;i++){
            listBean.add(new Bean("jack"+i));
        }
    }

}

