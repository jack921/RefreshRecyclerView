package com.refreshrecyclerview.jack.refreshrecyclerview.widget;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.refreshrecyclerview.jack.refreshrecyclerview.interfaces.OnLoadMoreListener;
import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter {

    //所有的数据集合
    List<T> listdate=new ArrayList<>();
    //HeaderView
    private View HeaderView=null;
    //FooterView
    private View FooterView=null;
    //加载更多的默认的View
    private View DefauLoadView=null;
    //有HeaderView的时候
    private int HeaderViewStatus=-1;
    //有FooterView的时候
    private int FooterViewStatus=-2;
    //有上拉加载的时候
    private int DefaultLoadMoreStatus=-3;
    //加上HeadeView和FooterView的总个数
    private int AllNum=0;
    //下来加载监听接口
    private OnLoadMoreListener onLoadMoreListener;
    //是否设置上拉加载
    private boolean LoadMoreStatus=false;
    //正在加载就是false,结束加载就是true
    private boolean RefreshStatus=true;
    //true可以监听，已经在监听是false
    private boolean ListenerStatus=true;
    //true代表设置上拉加载，false代表取消上拉加载
    private boolean LoadMore=false;
    //RecyclerView对象
    private RecyclerView recyclerView;

    public void setListdate(List<T> data) {
        this.listdate=data;
        notifyDataSetChanged();
    }

    public void setHeaderView(View headerView) {
        this.HeaderView = headerView;
    }

    public void setFooterView(View footerView) {
        this.FooterView = footerView;
    }

    public void setDefauLoadView(View loadView){
        this.DefauLoadView=loadView;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public boolean setLoadMore(boolean status,RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
        return LoadMore=status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==HeaderViewStatus){
            return new UserHeaderView(HeaderView);
        }else if(viewType==FooterViewStatus){
            return new UserFooterView(FooterView);
        }else if(viewType==DefaultLoadMoreStatus){
            return new UserLoadMore(DefauLoadView);
        }else{
            return onCreateHolder(parent,viewType);
        }
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==HeaderViewStatus){
        }else if(getItemViewType(position)==FooterViewStatus){
        }else if(getItemViewType(position)==DefaultLoadMoreStatus){
            if(ListenerStatus){
                ListenerStatus=false;
                onLoadMoreListener.onLoadMore();
            }
        }else{
            int pos=0;
            if(HeaderView!=null){
                pos=position-1;
            }else{
                pos=position;
            }
            onBindHolder(holder,pos,listdate.get(pos));
        }

        Log.e("recyclerview_item",getItemCount()+"");
        Log.e("recyclerview_child",recyclerView.getChildCount()+"");
        int test1=getItemCount();
        int test2=recyclerView.getChildCount();
        if((position+1)==getItemCount()&&LoadMore==true&&(getItemCount()!=recyclerView.getChildCount()+1)){
            startLoadMore();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(HeaderView!=null&&position==0){
            return HeaderViewStatus;
        }
        //查看有没有设置上拉加载
        if(LoadMoreStatus){
            if(FooterView!=null&&(AllNum-2)==position){
                return FooterViewStatus;
            }
            if(AllNum==position+1){
               return DefaultLoadMoreStatus;
            }
        }else{
            if(FooterView!=null&&AllNum==position+1){
                return FooterViewStatus;
            }
        }

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        AllNum=listdate.size()+(HeaderView!=null?1:0)+(FooterView!=null?1:0)+(LoadMoreStatus==true?1:0);
        return AllNum;
    }

    //停止上拉加载
    public void stopLoadMore(){
        Log.e("onLoadMore","stopLoadMre");
        LoadMoreStatus=false;
        ListenerStatus=true;
        notifyDataSetChanged();
        RefreshStatus=true;
    }

    //开始上拉加载
    public void startLoadMore(){
        if(RefreshStatus){
            Log.e("onLoadMore","startLoadMore");
            LoadMoreStatus=true;
            RefreshStatus=false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            },0);
        }
    }

    //处理GridLayout
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                      if(getItemViewType(position)==HeaderViewStatus){
                           return gridManager.getSpanCount();
                      }
                      if(getItemViewType(position)==FooterViewStatus){
                           return gridManager.getSpanCount();
                      }
                      if(getItemViewType(position)==DefaultLoadMoreStatus){
                          return gridManager.getSpanCount();
                      }
                      return 1;
                }
            });
        }
    }

    //处理StaggeredGridLayout
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
        }
    }

    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    protected void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
        StaggeredGridLayoutManager.LayoutParams p=(StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        if (getItemViewType(position)==HeaderViewStatus) {
            p.setFullSpan(true);
        }
        if(getItemViewType(position)==FooterViewStatus){
            p.setFullSpan(true);
        }
        if(getItemViewType(position)==DefaultLoadMoreStatus){
            p.setFullSpan(true);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, final int viewType);
    public abstract void onBindHolder(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);

    public class UserFooterView extends RecyclerView.ViewHolder{
        public UserFooterView(View itemView) {
            super(itemView);
        }
    }

    public class UserHeaderView extends RecyclerView.ViewHolder{
        public UserHeaderView(View itemView) {
            super(itemView);
        }
    }

    public class UserLoadMore extends  RecyclerView.ViewHolder{
        public UserLoadMore(View itemView) {super(itemView); }
    }

}
