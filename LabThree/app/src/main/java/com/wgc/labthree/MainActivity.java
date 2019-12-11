package com.wgc.labthree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.activity;
//import android.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // set up navigation drawer menu click listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.search_cafe:
                        // new frament  to singleton ???????????
                        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_fragment, new MapFragment()).commit();
                        break;
                    case R.id.nearby_cafe:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_fragment, new RecyclerViewFragment()).commit();
                        break;
                    default:
                }
                onBackPressed();
                return true;
            }
        });

        // set up default fragment when app launch first time
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_fragment, new MapFragment()).commit();
            navigationView.setCheckedItem(R.id.search_cafe);
        }


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else {
            super.onBackPressed();
        }

    }
}
