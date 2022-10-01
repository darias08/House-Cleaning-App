package com.app.margaritahousecleaning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

public class MainHomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(MainHomeActivity.this, R.color.redStatusBarColor));


    }
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}

/*
bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.home_menu:
                        fragment = new HomeFragment();
                        break;
                    case R.id.calendar_menu:
                        fragment = new ScheduleFragment();
                        break;
                    case R.id.location_menu:
                        break;
                    case R.id.settings_menu:
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();

                return true;
            }
        });
    }

 */