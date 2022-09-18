package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.homeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ScheduleFragment2 extends Fragment {

    private FirebaseUser user;
    private DatabaseReference databaseReference;

    private String userID;
    private Button backbtn2;
    private Button NextBtn;

    private BottomNavigationView bottomNavigationView;
    private BottomNavigationItemView bottomNavigationItemView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_schedule2, container, false);

        //How to set bottom navigation Icon active.
        bottomNavigationView = (BottomNavigationView)v.findViewById(bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.scheduleFragment).setChecked(true);


        //This is to activate the Firebase to work.
        user = FirebaseAuth.getInstance().getCurrentUser();

        //This is to get user's information in the database.
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");

        //This is to get the user's ID from firebase.
        userID = user.getUid();

        final TextView userAddressTV = (TextView) v.findViewById(R.id.UserAddressInfo);
        final TextView userZipCodeTV = (TextView) v.findViewById(R.id.UserZipCodeInfo);


        //Getting user information from the database
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null) {
                        String streetAddress = userProfile.streetAddress;
                        String zipCode = userProfile.zipCode;

                        userAddressTV.setText(streetAddress);
                        userZipCodeTV.setText(zipCode);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something wrong happened. Please report this problem.", Toast.LENGTH_SHORT).show();
            }
        });


        backbtn2 = (Button) v.findViewById(R.id.backBtn2);
        backbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleFragment2_to_scheduleTestFragment);
            }
        });

        NextBtn = v.findViewById(R.id.NextBtn2);
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleFragment2_to_scheduleFragment3);
            }
        });
        return v;
    }
}