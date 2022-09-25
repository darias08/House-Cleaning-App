package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.homeFragment;
import static com.app.margaritahousecleaning.R.id.locationFragment;
import static com.app.margaritahousecleaning.R.id.scheduleFragment;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ArrayListMultimap;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.Multimap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SettingsFragment extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String userID;
    private ImageView EditText1,EditText1OFF, EditText2OFF, EditText2, EditText3, EditText4, EditText5;
    private Button saveBtn;
    private EditText userFirstNameET, userLastNameET;
    private int count = 0;
    BottomNavigationView bottomNavigationView;
    BottomNavigationItemView bottomNavigationItemView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings_test, container, false);

        //Calling Resources from .xml
        EditText1 = v.findViewById(R.id.EditText1);
        EditText1OFF = v.findViewById(R.id.EditText1OFF);
        EditText2 = v.findViewById(R.id.EditText2);
        EditText2OFF = v.findViewById(R.id.EditText2OFF);
        EditText3 = v.findViewById(R.id.EditText3);
        EditText4 = v.findViewById(R.id.EditText4);
        EditText5 = v.findViewById(R.id.EditText5);
        saveBtn = v.findViewById(R.id.saveBtn);

        //Displaying user information from database (TextView).
        TextView userFirstNameTV = (TextView) v.findViewById(R.id.FirstNameTV);
        TextView userLastNameTV = (TextView) v.findViewById(R.id.LastNameTV);
        TextView userStreetAddress = (TextView) v.findViewById(R.id.address);
        TextView userZipCode = (TextView) v.findViewById(R.id.ZipCode);
        TextView userPhoneNumber = (TextView) v.findViewById(R.id.PhoneNumber);

        //Displaying user information from database (EditText).
        userFirstNameET = (EditText) v.findViewById(R.id.FirstNameET);
        userLastNameET = (EditText) v.findViewById(R.id.LastNameET);
        //EditText userStreetAddress = (EditText) v.findViewById(R.id.address);
        //EditText userZipCode = (EditText) v.findViewById(R.id.ZipCode);
        //EditText userPhoneNumber = (EditText) v.findViewById(R.id.PhoneNumber);


        EditText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText1OFF.setColorFilter(Color.parseColor("#F1CD17"), PorterDuff.Mode.SRC_IN);
                userFirstNameTV.setVisibility(View.INVISIBLE);
                userFirstNameET.setVisibility(View.VISIBLE);
                EditText1OFF.setVisibility(View.VISIBLE);



                userFirstNameET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String userFirstName = userFirstNameET.getText().toString().trim();

                        if (userFirstName.isEmpty()) {
                            userFirstNameET.setError("Please provide a first name!");
                            userFirstNameET.requestFocus();
                            return;
                        }
                        saveBtn.setEnabled(true);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });

        EditText1OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userFirstNameTV.setVisibility(View.VISIBLE);
                userFirstNameET.setVisibility(View.INVISIBLE);
                EditText1OFF.setVisibility(View.INVISIBLE);
                EditText1.setVisibility(View.VISIBLE);

                saveBtn.setEnabled(false);
            }
        });

        EditText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText2OFF.setColorFilter(Color.parseColor("#F1CD17"), PorterDuff.Mode.SRC_IN);
                userLastNameTV.setVisibility(View.INVISIBLE);
                userLastNameET.setVisibility(View.VISIBLE);
                EditText2OFF.setVisibility(View.VISIBLE);



                userLastNameET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String userLastName = userLastNameET.getText().toString().trim();

                        if (userLastName.isEmpty()) {
                            userLastNameET.setError("Please provide a first name!");
                            userLastNameET.requestFocus();
                            return;
                        }
                        saveBtn.setEnabled(true);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });

        EditText2OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLastNameTV.setVisibility(View.VISIBLE);
                userLastNameET.setVisibility(View.INVISIBLE);
                EditText2OFF.setVisibility(View.INVISIBLE);
                EditText2.setVisibility(View.VISIBLE);
                saveBtn.setEnabled(false);
            }
        });


        //Activating Firebase
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Retrieving user's information from database.
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");

        //User's ID
        userID = user.getUid();


        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);

                if (userProfile != null) {
                    String firstNameTV = userProfile.firstName;
                    String firstNameET = userProfile.firstName;
                    String lastNameTV = userProfile.lastName;
                    String lastNameET = userProfile.lastName;
                    String address = userProfile.streetAddress;
                    String zipCode = userProfile.zipCode;
                    String phoneNumber = userProfile.phoneNumber;

                    //TextView
                    userFirstNameTV.setText(firstNameTV);
                    userLastNameTV.setText(lastNameTV);
                    userStreetAddress.setText(address);
                    userZipCode.setText(zipCode);
                    userPhoneNumber.setText(phoneNumber);

                    //EditText
                    userFirstNameET.setText(firstNameET);
                    userLastNameET.setText(lastNameET);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String firstNameET = userFirstNameET.getText().toString();
               String lastNameET = userLastNameET.getText().toString();

               //setText to TextView from EditText
               userFirstNameTV.setText(firstNameET);
               userLastNameTV.setText(lastNameET);

                HashMap hashMap = new HashMap();
                hashMap.put("firstName", firstNameET);
                hashMap.put("lastName", lastNameET);


               databaseReference.child(userID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                   @Override
                   public void onSuccess(Object o) {

                       Toast.makeText(getActivity(), "Your profile has been updated!", Toast.LENGTH_SHORT).show();

                   }

               });

            }
        });

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