package com.refreshrecyclerview.jack.refreshrecyclerview.widget;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.refreshrecyclerview.jack.refreshrecyclerview.R;

/**
 * Created by Jack on 2016/3/23.
 */

public abstract class RecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    public static final int TYPE_FOOTER=Integer.MIN_VALUE;
    public static final int TYPE_HEADER=-1;
    private RecyclerView.ViewHolder footerView=null;
    private RecyclerView.ViewHolder headerView=null;
    private boolean hasFooter=false;
    private String DefaultText="正在加载中...";
    private int ItemCount=0;
    private int UserLayout=0;

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
                return new defaultFooterView(LayoutInflater.from(
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
            if(holder instanceof defaultFooterView||holder instanceof UserFooterView){
                if(footerView==null){
                    Log.e("defaultFooterView","defaultFooterView");
                    ((defaultFooterView)holder).mProgressBar.setVisibility(View.VISIBLE);
                    ((defaultFooterView)holder).mTip.setVisibility(View.VISIBLE);
                    ((defaultFooterView)holder).mTip.setText(DefaultText);
                }else{
                    Log.e("hua","加载用户的footview");
                }
            }else if(holder instanceof  UserHeaderView){

            }else{
                onBindItemViewHolder((VH)holder,position);
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
        return ItemCount+(hasFooter?1:0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void StartFooterView(View view){
        if(view!=null){
            footerView=new UserFooterView(view);
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
        headerView=new UserHeaderView(view);
    }

}

 class defaultFooterView extends RecyclerView.ViewHolder{
    public ProgressBar mProgressBar=null;
    public TextView mTip=null;
    public defaultFooterView(View itemView) {
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

 class UserFooterView extends RecyclerView.ViewHolder{
    public UserFooterView(View itemView) {
        super(itemView);
    }
}

 class UserHeaderView extends RecyclerView.ViewHolder{
    public UserHeaderView(View itemView) {
        super(itemView);
    }
}