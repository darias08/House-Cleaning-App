package com.app.margaritahousecleaning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
                //Navigation.findNavController(view).navigate(R.id.action_scheduleUserBookedFragment_to_notificationUpcomingFragment);
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
