package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.RGroup;
import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.homeFragment;
import static com.app.margaritahousecleaning.R.id.locationFragment;
import static com.app.margaritahousecleaning.R.id.userPhoneNum;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import in.daemondhruv.customviewgroup.ConstraintRadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.Calendar;
import java.util.ArrayList;


public class ScheduleUserFragment extends Fragment implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    private FirebaseUser user;
    private DatabaseReference registeredUsers;
    private DatabaseReference bookedAppointments;
    private FirebaseAuth mAuth;
    private String userID;
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationItemView bottomNavigationItemView;
    private DatePickerDialog datePickerDialog;
    private com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd;
    private EditText dateTXT, etRFA, etTime;
    private ImageView calendar, timeIcon, questionMark;
    private TextView tvTime, tvUserLocation, userAddress, userZipCode, userPhoneNumber, userInputAddress, userInputZipCode, tvRFA;
    private View underlineAddress, underlineZipCode, backgroundRFA, backgroundView;
    private CheckBox checkBox, checkBox2, checkBox3, checkBox4, checkBox5;
    private Button bookBtn, noBtn, yesBtn;
    private RadioButton RB_10am, RB_11am, RB_12pm, RB_1pm, RB_2pm, RB_3pm, RB_4pm, RB_5pm;
    private Dialog dialog;
    private BottomNavigationView bottomNavigationView1;
    private LinearLayout linearLayoutBackground, linearLayoutBackground1;
    private int count = 0;
    private UserAppointment userAppointment;
    private ProgressBar progressBar;
    private int progressBarCounter = 5000;
    private int progressBarComplete = 6300;
    private FirebaseDatabase firebaseDatabase;


    // Max dates to select in the future total.
    final int MAX_SELECTABLE_DATE_IN_FUTURE = 365;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule_user, container, false);

        //This is to get the user's ID from firebase.
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //This is for users to book their appointments.
        bookedAppointments = FirebaseDatabase.getInstance().getReference("Booked Appointments");

        //This is to get user's information in the database.
        registeredUsers = FirebaseDatabase.getInstance().getReference("Registered Users");


        //Calling the resources from the xml and defining their variables.
        dateTXT = v.findViewById(R.id.date);
        calendar = v.findViewById(R.id.calendarImage);
        etTime = v.findViewById(R.id.timeET);
        timeIcon = v.findViewById(R.id.timeIcon);
        tvTime = v.findViewById(R.id.timeSelectionText);
        backgroundView = v.findViewById(R.id.backgroundView2);
        tvUserLocation = v.findViewById(R.id.UserAppointmentLocation);
        RB_2pm = v.findViewById(R.id.RB_2pm);
        userAddress = v.findViewById(R.id.userAddress1);
        userZipCode = v.findViewById(R.id.userZipCode1);
        userPhoneNumber = v.findViewById(userPhoneNum);
        userInputAddress = v.findViewById(R.id.userAddressInfo);
        userInputZipCode = v.findViewById(R.id.userZipCodeInfo1);
        underlineAddress = v.findViewById(R.id.underlineAddress);
        underlineZipCode = v.findViewById(R.id.underlineZipCode);
        questionMark = v.findViewById(R.id.questionMark);
        tvRFA = v.findViewById(R.id.tvRFA);
        checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) v.findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) v.findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) v.findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) v.findViewById(R.id.checkBox5);
        backgroundRFA = v.findViewById(R.id.backgroundRFA);
        bookBtn = v.findViewById(R.id.bookBtn);
        linearLayoutBackground = v.findViewById(R.id.linearLayoutBackground);
        linearLayoutBackground1 = v.findViewById(R.id.linearLayoutBackground1);
        bottomNavigationView1 = v.findViewById(bottom_navigation);


        dateTXT.setOnClickListener(view -> {
            Calendar now = Calendar.getInstance();
            if (dpd == null) {
                dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        ScheduleUserFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
            } else {

                dpd.initialize(
                        ScheduleUserFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
            }


            // restrict to weekdays only
            ArrayList<Calendar> weekdays = new ArrayList<Calendar>();
            Calendar day1 = Calendar.getInstance();
            for (int i = 0; i < MAX_SELECTABLE_DATE_IN_FUTURE; i++) {
                if (day1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && day1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    Calendar d = (Calendar) day1.clone();
                    weekdays.add(d);
                }
                day1.add(Calendar.DATE, 1);
            }
            Calendar[] weekdayDays = weekdays.toArray(new Calendar[weekdays.size()]);
            dpd.setSelectableDays(weekdayDays);

            dpd.setOnCancelListener(dialog -> {
                Log.d("DatePickerDialog", "Dialog was cancelled");
                dpd = null;
            });
            dpd.show(requireFragmentManager(), "Datepickerdialog");


        });


        //Clicking on select a time EditText Button and making a popup for user to select a time.
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //Bottom Navigation Icons
        bottomNavigationItemView = v.findViewById(homeFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_scheduleUserFragment2_to_homeFragment);

            }
        });

        //Selecting checkboxes
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    count++;
                    bookBtn.setEnabled(true);
                } else {
                    count--;
                    if (count < 1) {
                        bookBtn.setEnabled(false);
                    }
                }
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    count++;
                    bookBtn.setEnabled(true);
                } else {
                    count--;
                    if (count < 1) {
                        bookBtn.setEnabled(false);
                    }
                }
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    count++;
                    bookBtn.setEnabled(true);
                } else {
                    count--;
                    if (count < 1) {
                        bookBtn.setEnabled(false);
                    }
                }
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    count++;
                    bookBtn.setEnabled(true);
                } else {
                    count--;
                    if (count < 1) {
                        bookBtn.setEnabled(false);
                    }
                }
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog3();
            }
        });


        //How to set bottom navigation Icon active.
        bottomNavigationView = (BottomNavigationView) v.findViewById(bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.scheduleFragment).setChecked(true);

        //Switching screens from navigation bar.

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(homeFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleUserFragment2_to_homeFragment);
            }
        });


        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(locationFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleUserFragment2_to_locationFragment);
            }
        });


        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void showDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.mycustompopup);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        //Calling the resources from the xml and defining their variables.
        TextView cancelTxt = dialog.findViewById(R.id.CancelBtn);
        TextView confirmTxt = dialog.findViewById(R.id.ConfirmBtn);
        ConstraintRadioGroup rg = dialog.findViewById(R.id.RGroup);


        /*Fetching Data Server for time picked.
        String time_picked = RB_3pm.getText().toString();

        mAuth.addIdTokenListener(new FirebaseAuth.IdTokenListener() {
            @Override
            public void onIdTokenChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        });
        bookedAppointments.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String time_picked = snapshot.child("time_picked").getValue().toString();

                if (!time_picked.isEmpty()){
                    RB_3pm.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         */

        //User closes selected time.
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //closing dialog popup
                dialog.dismiss();
            }
        });

        //Confirming user appointment time.
        ConstraintRadioGroup radioGroup = (ConstraintRadioGroup) dialog.findViewById(RGroup);
        radioGroup.setOnCheckedChangeListener(new ConstraintRadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(ConstraintRadioGroup group, int checkedId) {
                confirmTxt.setTextColor(Color.WHITE);
                confirmTxt.setEnabled(true);


                //User selects a time.
                confirmTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calling the RadioGroup and getting each radioButton.
                        int selectedTime = rg.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) dialog.findViewById(selectedTime);


                        //Converting RadioButton to String
                        String radioText = radioButton.getText().toString();
                        etTime.setText(radioText);

                        //closing popup
                        dialog.dismiss();

                        //Displaying user location information when user has selected their time.
                        tvUserLocation.setVisibility(View.VISIBLE);
                        userAddress.setVisibility(View.VISIBLE);
                        userZipCode.setVisibility(View.VISIBLE);
                        userInputAddress.setVisibility(View.VISIBLE);
                        userInputZipCode.setVisibility(View.VISIBLE);
                        underlineAddress.setVisibility(View.VISIBLE);
                        underlineZipCode.setVisibility(View.VISIBLE);
                        backgroundView.setVisibility(View.VISIBLE);
                        backgroundRFA.setVisibility(View.VISIBLE);
                        checkBox.setVisibility(View.VISIBLE);
                        checkBox2.setVisibility(View.VISIBLE);
                        checkBox3.setVisibility(View.VISIBLE);
                        checkBox4.setVisibility(View.VISIBLE);
                        checkBox5.setVisibility(View.VISIBLE);
                        questionMark.setVisibility(View.VISIBLE);
                        tvRFA.setVisibility(View.VISIBLE);
                        bookBtn.setVisibility(View.VISIBLE);


                        //Setting up animations for user contact information.
                        Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_in_left_slower);
                        tvUserLocation.setAnimation(animation);
                        userAddress.setAnimation(animation);
                        userZipCode.setAnimation(animation);
                        userInputAddress.setAnimation(animation);
                        userInputZipCode.setAnimation(animation);
                        underlineAddress.setAnimation(animation);
                        underlineZipCode.setAnimation(animation);
                        backgroundView.setAnimation(animation);
                        backgroundRFA.setAnimation(animation);
                        checkBox.setAnimation(animation);
                        checkBox2.setAnimation(animation);
                        checkBox3.setAnimation(animation);
                        checkBox4.setAnimation(animation);
                        checkBox5.setAnimation(animation);
                        questionMark.setAnimation(animation);
                        tvRFA.setAnimation(animation);
                        bookBtn.setAnimation(animation);


                    }

                });

                //Question Mark popup dialog
                questionMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                //User Information
                TextView userInputAddress = (TextView) getView().findViewById(R.id.userAddressInfo);
                TextView userInputZipCode = (TextView) getView().findViewById(R.id.userZipCodeInfo1);
                TextView userPhoneNumber = (TextView) getView().findViewById(R.id.userPhoneNum);


                //Getting user information from the database
                registeredUsers.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserProfile userProfile = snapshot.getValue(UserProfile.class);

                        if (userProfile != null) {
                            String streetAddress = userProfile.streetAddress;
                            String zipCode = userProfile.zipCode;
                            String phoneNumber = userProfile.phoneNumber;


                            userInputAddress.setText(streetAddress);
                            userInputZipCode.setText(zipCode);
                            userPhoneNumber.setText(phoneNumber);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Something wrong happened. Please report this problem.", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void showDialog3() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custompopup);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();


        //Calling the resources from the xml and defining their variables.
        Button yesBtn = dialog.findViewById(R.id.yesBtn);
        Button noBtn = dialog.findViewById(R.id.noBtn);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //closing dialog
                dialog.dismiss();

                //ProgressBar initiating.
                linearLayoutBackground.setVisibility(View.VISIBLE);

                //Disable all interactive clicks
                dateTXT.setEnabled(false);
                etTime.setEnabled(false);
                questionMark.setEnabled(false);
                checkBox.setEnabled(false);
                checkBox2.setEnabled(false);
                checkBox3.setEnabled(false);
                checkBox4.setEnabled(false);
                checkBox5.setEnabled(false);
                bookBtn.setEnabled(false);
                Menu menuNav = bottomNavigationView1.getMenu();
                MenuItem homeFragment = menuNav.findItem(R.id.homeFragment);
                homeFragment.setEnabled(false);
                Menu menuNav1 = bottomNavigationView1.getMenu();
                MenuItem locationFragment = menuNav1.findItem(R.id.locationFragment);
                locationFragment.setEnabled(false);
                Menu menuNav2 = bottomNavigationView1.getMenu();
                MenuItem settingsFragment = menuNav2.findItem(R.id.settingsFragment);
                settingsFragment.setEnabled(false);

                //Complete book appointment check mark
                thread1();

                //Storing all data information to database.
                thread2();

            }
        });
    }

    private void thread1() {
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Hide progress bar
                linearLayoutBackground.setVisibility(View.INVISIBLE);
                //Show complete check mark
                linearLayoutBackground1.setVisibility(View.VISIBLE);
            }
        }, progressBarCounter);
    }

    private void thread2() {
        //Storing user information
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Setting up to store all information to database.
                final String dateET = dateTXT.getText().toString();
                final String timeET = etTime.getText().toString();
                final String phoneNumber = userPhoneNumber.getText().toString();
                final String addressTV = userInputAddress.getText().toString();
                final String zipcodeTV = userInputZipCode.getText().toString();
                final String residentialCheckBox = checkBox.getText().toString();
                final String officeCheckBox = checkBox2.getText().toString();
                final String bathroomCheckBox = checkBox3.getText().toString();
                final String carpetCheckBox = checkBox4.getText().toString();


                UserAppointment userAppointment = new UserAppointment(dateET, timeET, addressTV, zipcodeTV, phoneNumber);

                FirebaseDatabase.getInstance().getReference("Booked Appointments")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userAppointment)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Navigation.findNavController(getView()).navigate(R.id.action_scheduleUserFragment2_to_scheduleUserBookedFragment);

                                    if (checkBox.isChecked()) {
                                        userAppointment.setResidentialCheckBox(residentialCheckBox);
                                        bookedAppointments.getDatabase().getReference("Booked Appointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userAppointment);
                                    }

                                    if (checkBox2.isChecked()) {
                                        userAppointment.setOfficeCheckBox(officeCheckBox);
                                        bookedAppointments.getDatabase().getReference("Booked Appointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userAppointment);
                                    }

                                    if (checkBox3.isChecked()) {
                                        userAppointment.setBathroomCheckBox(bathroomCheckBox);
                                        bookedAppointments.getDatabase().getReference("Booked Appointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userAppointment);
                                    }

                                    if (checkBox4.isChecked()) {
                                        userAppointment.setCarpetCheckBox(carpetCheckBox);
                                        bookedAppointments.getDatabase().getReference("Booked Appointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userAppointment);
                                    }

                                    }
                                }
                        });
            }
        }, progressBarComplete);
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = (++monthOfYear) + "/" + dayOfMonth + "/" + year;
        dateTXT.setText(date);
        dpd = null;

        //Displaying the time selection when user has selected a date.
        etTime.setVisibility(View.VISIBLE);
        timeIcon.setVisibility(View.VISIBLE);
        tvTime.setVisibility(View.VISIBLE);

        //Setting up animations for select time.
        Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_in_right_slower);
        etTime.setAnimation(animation);
        timeIcon.setAnimation(animation);
        tvTime.setAnimation(animation);
    }

}



