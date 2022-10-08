package com.app.margaritahousecleaning;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class SettingsFragment extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String userID;
    private ImageView EditText1, EditText1OFF, EditText2OFF, EditText2, EditText3, EditText3OFF, EditText4, EditText4OFF, EditText5, EditText5OFF;
    private Button saveBtn;
    private EditText userFirstNameET, userLastNameET, userStreetAddressET, userZipCodeET, userPhoneNumberET;
    private int count = 0;
    private GoogleSignInClient mGoogleSignInClient;
    ImageView profileIcon;
    ImageButton imageButton;
    ImageView right_Arrow1, right_Arrow2, right_Arrow3, logOut;
    BottomNavigationView bottomNavigationView;
    BottomNavigationItemView bottomNavigationItemView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        //Fixed navigation bar when user clicks on a EditText. This prevents navigation bar being placed on top of keyboard.
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //Calling Resources from .xml
        profileIcon = v.findViewById(R.id.profileIcon);
        imageButton = v.findViewById(R.id.fab_add);
        EditText1 = v.findViewById(R.id.EditText1);
        EditText1OFF = v.findViewById(R.id.EditText1OFF);
        EditText2 = v.findViewById(R.id.EditText2);
        EditText2OFF = v.findViewById(R.id.EditText2OFF);
        EditText3 = v.findViewById(R.id.EditText3);
        EditText3OFF = v.findViewById(R.id.EditText3OFF);
        EditText4 = v.findViewById(R.id.EditText4);
        EditText4OFF = v.findViewById(R.id.EditText4OFF);
        EditText5 = v.findViewById(R.id.EditText5);
        EditText5OFF = v.findViewById(R.id.EditText5OFF);
        saveBtn = v.findViewById(R.id.saveBtn);

        //Right arrow image
        logOut = v.findViewById(R.id.r_arrow4);

        //Displaying user information from database (TextView).
        TextView userFirstNameTV = (TextView) v.findViewById(R.id.FirstNameTV);
        TextView userLastNameTV = (TextView) v.findViewById(R.id.LastNameTV);
        TextView userStreetAddressTV = ((TextView) v.findViewById(R.id.addressTV));
        TextView userZipCodeTV = (TextView) v.findViewById(R.id.ZipCodeTV);
        TextView userPhoneNumberTV = (TextView) v.findViewById(R.id.PhoneNumberTV);

        //Displaying user information from database (EditText).
        userFirstNameET = (EditText) v.findViewById(R.id.FirstNameET);
        userLastNameET = (EditText) v.findViewById(R.id.LastNameET);
        userStreetAddressET = (EditText) v.findViewById(R.id.addressET);
        userZipCodeET = (EditText) v.findViewById(R.id.ZipCodeET);
        userPhoneNumberET = (EditText) v.findViewById(R.id.PhoneNumberET);

        //Displaying editText Image so users can edit their profile.
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

        //Disabling editText when user has completed their updating profile.
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

        //Displaying editText Image so users can edit their profile.
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
                            userLastNameET.setError("Please provide a last name!");
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

        //Disabling editText when user has completed their updating profile.
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

        //Displaying editText Image so users can edit their profile.
        EditText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText3OFF.setColorFilter(Color.parseColor("#F1CD17"), PorterDuff.Mode.SRC_IN);
                userStreetAddressTV.setVisibility(View.INVISIBLE);
                userStreetAddressET.setVisibility(View.VISIBLE);
                EditText3OFF.setVisibility(View.VISIBLE);


                userStreetAddressET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String userStreetAddress = userStreetAddressET.getText().toString().trim();

                        if (userStreetAddress.isEmpty()) {
                            userStreetAddressET.setError("Please provide a street address!");
                            userStreetAddressET.requestFocus();
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

        //Disabling editText when user has completed their updating profile.
        EditText3OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userStreetAddressTV.setVisibility(View.VISIBLE);
                userStreetAddressET.setVisibility(View.INVISIBLE);
                EditText3OFF.setVisibility(View.INVISIBLE);
                EditText3.setVisibility(View.VISIBLE);

                saveBtn.setEnabled(false);
            }
        });

        //Displaying editText Image so users can edit their profile.
        EditText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText4OFF.setColorFilter(Color.parseColor("#F1CD17"), PorterDuff.Mode.SRC_IN);
                userZipCodeTV.setVisibility(View.INVISIBLE);
                userZipCodeET.setVisibility(View.VISIBLE);
                EditText4OFF.setVisibility(View.VISIBLE);


                userZipCodeET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String userZipCode = userZipCodeET.getText().toString().trim();

                        if (userZipCode.isEmpty()) {
                            userZipCodeET.setError("Please provide a zip code!");
                            userZipCodeET.requestFocus();
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

        //Disabling editText when user has completed their updating profile.
        EditText4OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userZipCodeTV.setVisibility(View.VISIBLE);
                userZipCodeET.setVisibility(View.INVISIBLE);
                EditText4OFF.setVisibility(View.INVISIBLE);
                EditText4.setVisibility(View.VISIBLE);

                saveBtn.setEnabled(false);
            }
        });


        //Displaying editText Image so users can edit their profile.
        EditText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText5OFF.setColorFilter(Color.parseColor("#F1CD17"), PorterDuff.Mode.SRC_IN);
                userPhoneNumberTV.setVisibility(View.INVISIBLE);
                userPhoneNumberET.setVisibility(View.VISIBLE);
                EditText5OFF.setVisibility(View.VISIBLE);

                userPhoneNumberET.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
                userPhoneNumberET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String userPhoneNumber = userPhoneNumberET.getText().toString().trim();

                        if (userPhoneNumber.isEmpty()) {
                            userPhoneNumberET.setError("Please provide a phone number!");
                            userPhoneNumberET.requestFocus();
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

        //Disabling editText when user has completed their updating profile.
        EditText5OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPhoneNumberTV.setVisibility(View.VISIBLE);
                userPhoneNumberET.setVisibility(View.INVISIBLE);
                EditText5OFF.setVisibility(View.INVISIBLE);
                EditText5.setVisibility(View.VISIBLE);

                saveBtn.setEnabled(false);
            }
        });


        //User Signing out of their account
        createRequest();
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        //Activating Firebase
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Retrieving user's information from database.
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");

        //User's ID
        userID = user.getUid();

        //retrieving user data from firebase.
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);

                if (userProfile != null) {
                    String firstNameTV = userProfile.firstName;
                    String firstNameET = userProfile.firstName;
                    String lastNameTV = userProfile.lastName;
                    String lastNameET = userProfile.lastName;
                    String streetAddressTV = userProfile.streetAddress;
                    String streetAddressET = userProfile.streetAddress;
                    String zipCodeTV = userProfile.zipCode;
                    String zipCodeET = userProfile.zipCode;
                    String phoneNumberTV = userProfile.phoneNumber;
                    String phoneNumberET = userProfile.phoneNumber;

                    //TextView
                    userFirstNameTV.setText(firstNameTV);
                    userLastNameTV.setText(lastNameTV);
                    userStreetAddressTV.setText(streetAddressTV);
                    userZipCodeTV.setText(zipCodeTV);
                    userPhoneNumberTV.setText(phoneNumberTV);

                    //EditText
                    userFirstNameET.setText(firstNameET);
                    userLastNameET.setText(lastNameET);
                    userStreetAddressET.setText(streetAddressET);
                    userZipCodeET.setText(zipCodeET);
                    userPhoneNumberET.setText(phoneNumberET);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //User saving their profile data.
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstNameET = userFirstNameET.getText().toString();
                String lastNameET = userLastNameET.getText().toString();
                String streetAddressET = userStreetAddressET.getText().toString();
                String zipCodeET = userZipCodeET.getText().toString();
                String phoneNumberET = userPhoneNumberET.getText().toString();

                userFirstNameTV.setText(firstNameET);
                userLastNameTV.setText(lastNameET);
                userStreetAddressTV.setText(streetAddressET);
                userZipCodeTV.setText(zipCodeET);
                userPhoneNumberTV.setText(phoneNumberET);

                HashMap hashMap = new HashMap();
                hashMap.put("firstName", firstNameET);
                hashMap.put("lastName", lastNameET);
                hashMap.put("streetAddress", streetAddressET);
                hashMap.put("zipCode", zipCodeET);
                hashMap.put("phoneNumber", phoneNumberET);

                databaseReference.child(userID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        Toast.makeText(getActivity(), "Your profile has been updated!", Toast.LENGTH_SHORT).show();

                    }

                });

            }
        });

        /*
        //How to set bottom navigation Icon active.
        bottomNavigationView = (BottomNavigationView) v.findViewById(bottom_navigation);
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
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_scheduleUserFragment2);
            }
        });
        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(locationFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_locationFragment);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(getActivity())
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

         */



        return v;
    }


    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                Toast.makeText(getActivity(), "You have logged out.", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });
    }

    //Request google account
    private void createRequest() {

    }
}