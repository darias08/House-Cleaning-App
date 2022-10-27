package com.app.margaritahousecleaning.Fragments;

import static com.app.margaritahousecleaning.R.id.nav_home;
import static com.app.margaritahousecleaning.R.id.nav_notification;
import static com.app.margaritahousecleaning.R.id.nav_schedule;
import static com.app.margaritahousecleaning.R.id.nav_settings;


import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.app.margaritahousecleaning.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class Notification extends Fragment {
    DatabaseReference databaseReference;
    String userID;
    ImageView backBtn;
    TextView userAddress, userZipCode, userPhoneNumber, datePicked, timePicked;
    BottomNavigationItemView bottomNavigationItemView;
    CardView card_booking_info;
    TextView txt_customer_address, txt_customer_phone, txt_customer_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Booked Appointments");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        card_booking_info = v.findViewById(R.id.card_booking_info);


        BottomNavigationView navigationView = (BottomNavigationView) v.findViewById(R.id.bottom_navigation);
        navigationView.getMenu().findItem(nav_notification).setChecked(true);

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_home);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_notificationUpcomingFragment_to_homeFragment);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_schedule);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_notificationUpcomingFragment_to_scheduleUserFragment2);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_settings);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.notification_to_settings);
            }
        });




        return v;
    }

}