package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.date3;
import static com.app.margaritahousecleaning.R.id.homeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ScheduleUserBookedFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private BottomNavigationItemView bottomNavigationItemView;
    private TextView datePicked;
    private ImageView notificationBellRing, notificationBell;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule_user_booked, container, false);

        notificationBell = v.findViewById(R.id.NotificationBell);
        notificationBellRing = v.findViewById(R.id.NotificationBellRing);




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

        datePicked = v.findViewById(R.id.date3);
        datePicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You can only book one appointment at a time.", Toast.LENGTH_LONG).show();
            }
        });



        notificationBellRing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleUserBookedFragment_to_notificationUpcomingFragment);
                /*
                NotificationFragment notificationFragment = new NotificationFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.scheduleUserBookedContainer, notificationFragment, notificationFragment.getTag())
                        .commit();

                 */


            }
        });

        return v;
    }


}
