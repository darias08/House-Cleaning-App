package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.homeFragment;
import static com.app.margaritahousecleaning.R.id.locationFragment;
import static com.app.margaritahousecleaning.R.id.scheduleFragment;
import static com.app.margaritahousecleaning.R.id.settingsFragment;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class HomeFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    BottomNavigationItemView bottomNavigationItemView;
    NavController navController;
    Button scheduleBtn;
    Button locationBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(scheduleFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_scheduleFragment);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(locationFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_locationFragment);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(settingsFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_settingsFragment);
            }
        });

        scheduleBtn = v.findViewById(R.id.button_input);
        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_scheduleFragment);
            }
        });

        locationBtn = v.findViewById(R.id.button_input2);
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_locationFragment);
            }
        });

        return v;
    }

}

/*
 BottomNavigationView mBottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_nav);
mBottomNavigationView.getMenu().findItem(R.id.item_id).setChecked(true);
 */

/*
     bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(R.id.scheduleFragment);


        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_scheduleFragment2);
            }
        });

        return v;
    }

}
 */




        /*
        menuItem = v.findViewById(R.id.scheduleFragment2);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Navigation.findNavController((View) menuItem).navigate(R.id.ScheduleFragmentXML);
                return true;
            }
        });
        return v;
    }
}

         */