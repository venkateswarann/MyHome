package com.tiger.myhome.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.tiger.myhome.R;

import androidx.navigation.Navigation;

public class HomeFragment extends Fragment implements View.OnClickListener  {


    Button btnThings;
    Button btnWorks, btnAddnewItems;
    View homeView;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.home_fragment, container, false);

        btnThings = homeView.findViewById(R.id.btnThings);
        btnWorks = homeView.findViewById(R.id.btnWorks);
        btnAddnewItems = homeView.findViewById(R.id.btnAddnewItems);
        btnThings.setOnClickListener(this);
        btnWorks.setOnClickListener(this);
        btnAddnewItems.setOnClickListener(this);

        return homeView;
    }


    @Override
    public void onClick(View homeView) {
        switch (homeView.getId()) {
            case R.id.btnThings:
                Navigation.findNavController(homeView).navigate(R.id.listItemsFragment);
                break;
            case R.id.btnWorks:
                Navigation.findNavController(homeView).navigate(R.id.workToFinishFragment);
                break;
            case R.id.btnAddnewItems:
                Navigation.findNavController(homeView).navigate(R.id.addItemsFragment);
                break;
        }
    }
}