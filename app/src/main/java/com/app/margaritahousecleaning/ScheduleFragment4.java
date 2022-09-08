package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.dateSelected;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class ScheduleFragment4 extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private BottomNavigationItemView bottomNavigationItemView;

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userID;

    private CalendarView calendarView;
    private TextView myDate;
    private Calendar lastSelectedCalendar = null;

    private Button backbtn;
    private Button BookBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule4, container, false);

        //How to set bottom navigation Icon active.
        bottomNavigationView = (BottomNavigationView)v.findViewById(bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.scheduleFragment).setChecked(true);


        backbtn = v.findViewById(R.id.backBtn4);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleFragment4_to_scheduleFragment3);
            }
        });

        //This is to activate the Firebase to work.
        user = FirebaseAuth.getInstance().getCurrentUser();

        //This is to get user's information in the database.
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");

        //This is to get the user's ID from firebase.
        userID = user.getUid();

        final TextView userAddressTV = (TextView) v.findViewById(R.id.AddressUserInfo);
        final TextView userZipCodeTV = (TextView) v.findViewById(R.id.ZipCodeUserInfo);
        final TextView userPhoneNumberTV = (TextView) v.findViewById(R.id.PhoneUserInfo);
        final TextView userEmailTV = (TextView) v.findViewById(R.id.EmailUserInfo);


        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String streetAddress = userProfile.streetAddress;
                    String zipCode = userProfile.zipCode;
                    String phoneNumber = userProfile.phoneNumber;
                    String email = userProfile.email;


                    userAddressTV.setText(streetAddress);
                    userZipCodeTV.setText(zipCode);
                    userPhoneNumberTV.setText(phoneNumber);
                    userEmailTV.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something wrong happened. Please report this problem.", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}