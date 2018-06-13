package com.tiger.myhome.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.tiger.myhome.R;

import java.util.List;

public class ListAddedItemsLocalAdapter extends RecyclerView.Adapter<ListAddedItemsLocalAdapter.ViewHolder> {

     Context context;

    private List<String> items;


    public ListAddedItemsLocalAdapter(List<String> items, Context context) {
        this.items = items;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(items.get(position));

    }

    @Override

    public int getItemCount() {
        if(items==null){
            return 0;
        }else
        return items.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
        }
    }
}
