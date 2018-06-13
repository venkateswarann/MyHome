package com.tiger.myhome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tiger.myhome.R;

import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class HomeActivity extends AppCompatActivity {

    NavHostFragment navhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        navhost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(HomeActivity.this, navhost.getNavController());

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navhost.getNavController().navigateUp();

    }
}