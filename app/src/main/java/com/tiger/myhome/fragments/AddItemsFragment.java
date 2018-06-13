package com.tiger.myhome.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiger.myhome.R;
import com.tiger.myhome.adapters.ListAddedItemsLocalAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by venkates.narayanan on 13,June,2018
 */
public class AddItemsFragment extends Fragment {

    View view;
    RecyclerView addedItemRecyclerView;
    List<String> addedItemsList;
    EditText txtinputItem;
    Button btnAddItem;
    ListAddedItemsLocalAdapter listItemsAdapters;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_add_items, container, false);
        addedItemRecyclerView=view.findViewById(R.id.addedRcyclrView);
        txtinputItem=view.findViewById(R.id.txtinputItem);
        btnAddItem=view.findViewById(R.id.btnAdd);
        addedItemsList=new ArrayList<>();
        initAdapter();
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initList(txtinputItem.getText().toString());
                addDateToServer(txtinputItem.getText().toString());
                listItemsAdapters.notifyDataSetChanged();

            }
        });

        return view;

    }

    private void initList(String value) {
        addedItemsList.add(value);
    }

    private void initAdapter() {
        listItemsAdapters = new ListAddedItemsLocalAdapter(addedItemsList,getActivity());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        addedItemRecyclerView.setHasFixedSize(true);
        addedItemRecyclerView.setLayoutManager(layoutManager);
        addedItemRecyclerView.setAdapter(listItemsAdapters);

    }


    private void addDateToServer(String data) {
        db = FirebaseFirestore.getInstance();


        Map<String, Object> user = new HashMap<>();
        user.put("itemName", data);

        // Add a new document with a generated ID
        db.collection("MyList")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("send", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("send", "Error adding document", e);
                    }
                });
    }
}
