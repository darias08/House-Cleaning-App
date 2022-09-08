package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.multiTimeRadioGroup;
import static com.app.margaritahousecleaning.R.id.NextBtn1;
import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.homeFragment;
import static com.app.margaritahousecleaning.R.id.locationFragment;
import static com.app.margaritahousecleaning.R.id.settingsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;


public class ScheduleFragment1 extends Fragment  {

    private CalendarView calendarView;

    BottomNavigationView bottomNavigationView;
    BottomNavigationItemView bottomNavigationItemView;
    Button backbtn1;
    Button nbtn1;
    MultiLineRadioGroup multiRadiogroup;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_schedule1, container, false);


        //How to set bottom navigation Icon active.
        bottomNavigationView = (BottomNavigationView) v.findViewById(bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.scheduleFragment).setChecked(true);

        //How to click a icon and transition to the next screen.
        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(homeFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleTestFragment_to_homeFragment2);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(locationFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleTestFragment_to_locationFragment2);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(settingsFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleTestFragment_to_settingsFragment2);
            }
        });

        backbtn1 = (Button) v.findViewById(R.id.backBtn1);
        backbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleTestFragment_to_scheduleFragment);
            }
        });

        nbtn1 = (Button) v.findViewById(R.id.NextBtn1);
        multiRadiogroup = (MultiLineRadioGroup) v.findViewById(R.id.multiTimeRadioGroup);

        //Setting up DatePicker for TextView
        calendarView = (CalendarView) v.findViewById(R.id.calendarView);

        nbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleTestFragment_to_scheduleFragment2);
            }
        });

        MultiLineRadioGroup mMultiLineRadioGroup = (MultiLineRadioGroup) v.findViewById(multiTimeRadioGroup);

        mMultiLineRadioGroup.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup group, RadioButton button) {
                Toast.makeText(getActivity(), "You have selected " + button.getText(), Toast.LENGTH_SHORT).show();
                nbtn1.setEnabled(true);
            }
        });



        return v;
    }
}
 /*
        bottomNavigationView.setSelectedItemId(R.id.calendar_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.calendar_menu:
                        return true;
                    case R.id.home_menu:
                        startActivity(new Intent(getApplicationContext(), HomeActivity2.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;
                    case R.id.location_menu:
                        return true;
                    case R.id.settings_menu:
                        return true;
                }
                return false;
            }
        });

//bottomNavigationView.setSelectedItemId(R.id.home);

        /*BottomNavigationView BottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_nav);
        mBottomNavigationView.getMenu().findItem(R.id.item_id).setChecked(true);


        nextbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==multiLineRadioGroup || v==nextbtn1) {
                    Navigation.findNavController(v).navigate(R.id.action_scheduleTestFragment_to_scheduleFragment2);
                }
                else {
                    Toast.makeText(getActivity(), "Please select a time.", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/

/*
 bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(scheduleFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_scheduleFragment);
            }
        });
 */