package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.homeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ScheduleUserBookedFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private BottomNavigationItemView bottomNavigationItemView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule_user_booked, container, false);


        //How to set bottom navigation Icon active.
        bottomNavigationView = (BottomNavigationView) v.findViewById(bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.scheduleFragment).setChecked(true);


        //How to click a icon and transition to the next screen.
        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(homeFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleUserBookedFragment_to_homeFragment);
            }
        });

        return v;
    }
}
