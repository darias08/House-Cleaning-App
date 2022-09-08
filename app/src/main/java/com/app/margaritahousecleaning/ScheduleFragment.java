package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.homeFragment;
import static com.app.margaritahousecleaning.R.id.locationFragment;
import static com.app.margaritahousecleaning.R.id.dateSelected;
import static com.app.margaritahousecleaning.R.id.settingsFragment;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;


public class ScheduleFragment extends Fragment {

    private CalendarView calendarView;
    private TextView dateSelected;
    private Calendar lastSelectedCalendar = null;

    private BottomNavigationView bottomNavigationView;
    private BottomNavigationItemView bottomNavigationItemView;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        //How to set bottom navigation Icon active.
        bottomNavigationView = (BottomNavigationView)v.findViewById(bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.scheduleFragment).setChecked(true);

        //How to click a icon and transition to the next screen.
        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(homeFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleFragment_to_homeFragment);
            }
        });
        //Navigation.findNavController(view).navigate(R.id.action_scheduleFragment_to_scheduleTestFragment2);
        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(locationFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleFragment_to_locationFragment);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(settingsFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleFragment_to_settingsFragment);
            }
        });

        //Setting up DatePicker for TextView
        calendarView = (CalendarView) v.findViewById(R.id.calendarView);
        dateSelected = v.findViewById(R.id.dateSelected);

        lastSelectedCalendar = Calendar.getInstance();

        //Setting up present date calendar.
        calendarView.setMinDate(lastSelectedCalendar.getTimeInMillis() - 1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                Calendar checkCalendar = Calendar.getInstance();
                checkCalendar.set(year, month, dayOfMonth);
                if (checkCalendar.equals(lastSelectedCalendar))
                    return;
                if (checkCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || checkCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                    calendarView.setDate(lastSelectedCalendar.getTimeInMillis());
                else
                    lastSelectedCalendar = checkCalendar;
            }
        });

        button = (Button) v.findViewById(R.id.NextBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_scheduleFragment_to_scheduleTestFragment2);
            }
        });

        return v;
    }

}


//bottomNavigationView.setSelectedItemId(R.id.home);

        /*BottomNavigationView BottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_nav);
        mBottomNavigationView.getMenu().findItem(R.id.item_id).setChecked(true);
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