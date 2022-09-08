package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.homeFragment;
import static com.app.margaritahousecleaning.R.id.locationFragment;
import static com.app.margaritahousecleaning.R.id.scheduleFragment;
import static com.app.margaritahousecleaning.R.id.settingsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SettingsFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    BottomNavigationItemView bottomNavigationItemView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        //How to set bottom navigation Icon active.
        bottomNavigationView = (BottomNavigationView)v.findViewById(bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.settingsFragment).setChecked(true);

        //How to click a icon and transition to the next screen.
        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(homeFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_homeFragment);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(scheduleFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_scheduleFragment);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(locationFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_locationFragment);
            }
        });


        return v;
    }
}