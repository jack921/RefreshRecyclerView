package com.refreshrecyclerview.jack.refreshrecyclerview.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.refreshrecyclerview.jack.refreshrecyclerview.R;

/**
 */

public abstract class StaggeredRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    public static final int TYPE_FOOTER=Integer.MIN_VALUE;
    public static final int TYPE_HEADER=-1;
    private RecyclerView.ViewHolder footerView=null;
    private RecyclerView.ViewHolder headerView=null;
    private boolean hasFooter=false;
    private boolean hasHeader=false;
    private String DefaultText="正在加载中...";
    private int ItemCount=0;

    //数据itemViewHolder实现
    public abstract VH onCreateItemViewHolder(ViewGroup parent,int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        Log.e("RecyclerViewType",viewType+"");
        if(TYPE_FOOTER==viewType){
            if(footerView!=null){
                Log.e("hua","加载footerview");
                return footerView;
            }else{
                Log.e("hua","加载默认的footerview");
                return new StaggereddefaultFooterView(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.defaultfooterview,parent,false));
            }
        }else if(TYPE_HEADER==viewType){
            return headerView;
        }else{
            return onCreateItemViewHolder(parent,viewType);
        }
    }

    // 正常数据itemview的实现
    public abstract void onBindItemViewHolder(final VH holder,int position);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,int position) {
        if(holder instanceof StaggereddefaultFooterView||holder instanceof StaggeredUserFooterView){
            if(footerView==null){
                Log.e("defaultFooterView","defaultFooterView");
                ((StaggereddefaultFooterView)holder).mProgressBar.setVisibility(View.VISIBLE);
                ((StaggereddefaultFooterView)holder).mTip.setVisibility(View.VISIBLE);
                ((StaggereddefaultFooterView)holder).mTip.setText(DefaultText);
            }else{
                Log.e("hua","加载用户的footview");

            }
        }else if(holder instanceof  StaggeredUserHeaderView){
        }else{
            if(hasHeader){
                onBindItemViewHolder((VH)holder,position-1);
            }else{
                onBindItemViewHolder((VH)holder,position);
            }
        }
    }

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
        Log.e("handleposition",position+"");
        if (headerView!=null&&position==0) {
            Log.e("handleheader","headerview_position");
            StaggeredGridLayoutManager.LayoutParams p =
                    (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        }
        if(hasFooter&&ItemCount==(position)){
            Log.e("handlefooter","hasFooter_position"+position);
            StaggeredGridLayoutManager.LayoutParams p =
                    (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("ItemViewType_position",position+"");
        if(headerView!=null&&position==0){
            return TYPE_HEADER;
        }
        if(position==ItemCount&&hasFooter){
            return TYPE_FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return ItemCount+(hasFooter?1:0)+(hasHeader?1:0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void StartFooterView(View view){
        if(view!=null){
            footerView=new StaggeredUserFooterView(view);
        }
        hasFooter=!hasFooter;
        notifyDataSetChanged();
    }

    public void StopFooterView(){
        hasFooter=!hasFooter;
        notifyDataSetChanged();
    }

    public void setItemCount(int count){
        this.ItemCount=count;
    }

    public void setDefaultText(String text){
        DefaultText=text;
    }

    public void setHeaderView(View view){
        hasHeader=true;
        headerView=new StaggeredUserHeaderView(view);
    }

}

class StaggereddefaultFooterView extends RecyclerView.ViewHolder{
    public ProgressBar mProgressBar=null;
    public TextView mTip=null;

    public StaggereddefaultFooterView(View itemView) {
        super(itemView);
        mProgressBar=(ProgressBar)itemView.findViewById(R.id.DefaultRecyclerviewProgressbar);
        mTip=(TextView)itemView.findViewById(R.id.DefaultRecyclerviewTip);
    }
    public ProgressBar getProgressBar(){
        return mProgressBar;
    }
    public TextView getTextTip(){
        return mTip;
    }

}

class StaggeredUserFooterView extends RecyclerView.ViewHolder{
    public StaggeredUserFooterView(View itemView) {
        super(itemView);
    }
}

class StaggeredUserHeaderView extends RecyclerView.ViewHolder{
    public StaggeredUserHeaderView(View itemView) {
        super(itemView);
    }
}