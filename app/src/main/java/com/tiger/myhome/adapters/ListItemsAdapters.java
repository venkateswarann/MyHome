package com.tiger.myhome.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiger.myhome.R;
import com.tiger.myhome.pojo.HomeItems;

import java.util.List;


public class ListItemsAdapters extends RecyclerView.Adapter<ListItemsAdapters.ViewHoler> {

     private Context context;

    private List<HomeItems> items;
    FirebaseFirestore db;


    public ListItemsAdapters(List<HomeItems> items, Context context) {
        this.items = items;
        this.context = context;
        db=FirebaseFirestore.getInstance();

    }



    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new ViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, final int position) {
        holder.itemName.setText(items.get(position).getItemName());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 db.collection("MyList")
                        .document().collection(items.get(position).getItemName()).document().delete()
                         .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Cant Delete",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override

    public int getItemCount() {

        return items.size();

    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        TextView itemName;
        ImageView imgDelete;
        public ViewHoler(View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.itemName);
            imgDelete=itemView.findViewById(R.id.imgDelete);
        }


    }


}