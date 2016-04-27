package com.refreshrecyclerview.jack.refreshrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.refreshrecyclerview.jack.refreshrecyclerview.R;
import com.refreshrecyclerview.jack.refreshrecyclerview.model.Bean;
import com.refreshrecyclerview.jack.refreshrecyclerview.widget.RecyclerViewAdapter;
import java.util.List;

/**
 */

public class MyAdapterView extends RecyclerViewAdapter<Bean,MyAdapterView.MyViewHolder>{

    private Context context=null;
    private List<Bean> listBean=null;

    public MyAdapterView(Context context, List<Bean> listBean){
            this.context=context;
            this.listBean=listBean;
            setItemCount(listBean.size());
    }

    @Override
    public MyViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerviewadapter,parent,false));
    }

    @Override
    public void onBindItemViewHolder(MyViewHolder holder, int position) {
        Log.e("ListBeanText",listBean.get(position).getText()+"");
        holder.text.setText(listBean.get(position).getText());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView text=null;
        public MyViewHolder(View itemView) {
            super(itemView);
            text=(TextView)itemView.findViewById(R.id.text);
        }
    }
}
