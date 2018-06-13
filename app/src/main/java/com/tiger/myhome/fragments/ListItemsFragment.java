package com.tiger.myhome.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiger.myhome.R;
import com.tiger.myhome.adapters.ListItemsAdapters;
import com.tiger.myhome.pojo.HomeItems;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class ListItemsFragment extends Fragment    implements EventListener<QuerySnapshot> {

    RecyclerView itemsRecyclerView;
    View  view=null;
    List<HomeItems> homeItemsList=new ArrayList<>();
    HomeItems obj;
     DocumentReference mDatabase;
     FirebaseFirestore db;
     String TAG="firelog";
    ListItemsAdapters listItemsAdapters;
    private ArrayList<DocumentSnapshot> mSnapshots = new ArrayList<>();
    HomeItems items;



    public ListItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      if (view==null){
          view=inflater.inflate(R.layout.fragment_list_items, container, false);

          itemsRecyclerView = view.findViewById(R.id.itemsRcylrView);
        adddata();
        initAdapter();
        return view;
    }else
        return view;
    }





    private void initAdapter() {
         listItemsAdapters =new ListItemsAdapters(homeItemsList,getActivity());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
       itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.setLayoutManager(layoutManager);
        itemsRecyclerView.setAdapter(listItemsAdapters);
    }


    void adddata() {

        db = FirebaseFirestore.getInstance();
        db.collection("MyList").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "onEvent: " + e);

                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                    if (doc.getType() == DocumentChange.Type.ADDED  )  {

                        HomeItems items = doc.getDocument().toObject(HomeItems.class);
                        homeItemsList.add(items);
                        listItemsAdapters.notifyDataSetChanged();

                    }else if ( doc.getType()==DocumentChange.Type.REMOVED ){
                        listItemsAdapters.notifyDataSetChanged();
                    }else if(doc.getType()==DocumentChange.Type.MODIFIED){

                        listItemsAdapters.notifyDataSetChanged();
                    }

                }


               /* for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (change.getType()) {
                        case ADDED:
                            onDocumentAdded(change);
                            break;
                        case MODIFIED:
                            onDocumentModified(change);
                            break;
                        case REMOVED:
                            onDocumentRemoved(change);
                            break;
                    }
                }*/

            }
        });
    }



    protected void onDocumentAdded(DocumentChange change) {
        items = change.getDocument().toObject(HomeItems.class);
        homeItemsList.add(items);
        mSnapshots.add(change.getNewIndex(), change.getDocument());
        listItemsAdapters.notifyItemInserted(change.getNewIndex());
    }

    protected void onDocumentModified(DocumentChange change) {
        if (change.getOldIndex() == change.getNewIndex()) {
            // Item changed but remained in same position
            mSnapshots.set(change.getOldIndex(), change.getDocument());
            listItemsAdapters.notifyItemChanged(change.getOldIndex());
        } else {
            // Item changed and changed position
            mSnapshots.remove(change.getOldIndex());
            mSnapshots.add(change.getNewIndex(), change.getDocument());
            listItemsAdapters.notifyItemMoved(change.getOldIndex(), change.getNewIndex());
        }
    }

    protected void onDocumentRemoved(DocumentChange change) {
        mSnapshots.remove(change.getOldIndex());
        listItemsAdapters.notifyItemRemoved(change.getOldIndex());
    }

    @Override
    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
        if (e != null) {
            Log.w(TAG, "onEvent:error", e);
            onError(e);
            return;
        }
    }

    protected void onError(FirebaseFirestoreException e) {
        Log.w(TAG, "onError", e);
    };
}



