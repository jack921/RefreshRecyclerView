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

public class MyAdapterView extends RecyclerViewAdapter<Bean>{

    private Context context=null;

    public MyAdapterView(Context context){
            this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerviewadapter,parent,false));
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder viewHolder, int RealPosition, Bean data) {
        Log.e("onBindHolder",RealPosition+"");
        ((MyViewHolder)viewHolder).text.setText(data.getText());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView text=null;
        public MyViewHolder(View itemView) {
            super(itemView);
            text=(TextView)itemView.findViewById(R.id.text);
        }
    }
}
