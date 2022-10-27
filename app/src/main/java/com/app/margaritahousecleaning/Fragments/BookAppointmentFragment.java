package com.app.margaritahousecleaning.Fragments;

import static com.app.margaritahousecleaning.R.id.nav_home;
import static com.app.margaritahousecleaning.R.id.nav_notification;
import static com.app.margaritahousecleaning.R.id.nav_schedule;
import static com.app.margaritahousecleaning.R.id.nav_settings;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.app.margaritahousecleaning.Adapter.MyViewPagerAdapter;
import com.app.margaritahousecleaning.Model.Addresses;
import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.common.Common;
import com.app.margaritahousecleaning.common.NonSwipeViewPager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shuhart.stepview.StepView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookAppointmentFragment extends Fragment {

    LocalBroadcastManager localBroadcastManager;
    CollectionReference locationRef;

    @BindView(R.id.step_view1)
    StepView stepView1;
    @BindView(R.id.view_page)
    NonSwipeViewPager viewPager;
    @BindView(R.id.back_btn)
    Button backBtn;
    @BindView(R.id.next_btn)
    Button nextBtn;
    private BottomNavigationItemView bottomNavigationItemView;
    private BottomNavigationView bottomNavigationView;


    //Broadcast Receiver
    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int step = intent.getIntExtra(Common.KEY_STEP, 0);
            if (step == 1) {
                Common.currentAppointment = intent.getParcelableExtra(Common.KEY_APPOINTMENT_STORE);
                nextBtn.setEnabled(true);
            }


            if (step == 2) {
                Common.currentAppointment = intent.getParcelableExtra(Common.KEY_LOCATION_SELECTED);
            }

            if (step == 3) {
                Common.currentTimeSlot = intent.getIntExtra(Common.KEY_TIME_SLOT, -1);
                nextBtn.setEnabled(true);
            }
            setColorButton();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_appointment, container, false);
        viewPager = (NonSwipeViewPager) v.findViewById(R.id.view_page);
        ButterKnife.bind(getActivity());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



        stepView1 = v.findViewById(R.id.step_view1);
        viewPager = v.findViewById(R.id.view_page);


        backBtn = v.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.step == 3 || Common.step > 0) {
                    Common.step--;
                    viewPager.setCurrentItem(Common.step);
                }
            }
        });

        nextBtn = v.findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (Common.step < 3 || Common.step == 0) {
                    Common.step++; //Increment by 1
                    if (Common.step == 2) // Picking a time slot
                    {
                        if (Common.currentAppointment != null) {
                            loadTimeSlotOfUser(Common.currentAppointment.getAppointmentId());
                        }
                    }
                    else if (Common.step == 3) // Confirm
                    {
                        if (Common.currentTimeSlot != -1) {
                            confirmBooking();
                        }
                    }

                    viewPager.setCurrentItem(Common.step);
                }
            }
        });


        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        localBroadcastManager.registerReceiver(buttonNextReceiver, new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));


        //View
        viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(4); // 4 represents the Total fragments being used
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {

                    //Show step
                    stepView1.go(i, true);

                    if ( i == 0)
                        backBtn.setEnabled(false);

                    else
                        backBtn.setEnabled(true);

                    if (i == 0)
                        nextBtn.setEnabled(false);

                    if (i == 2)
                        nextBtn.setEnabled(false);

                    else
                        nextBtn.setEnabled(true);
                        onPause();


                    if (i == 3) { //Confirming appointment step
                        nextBtn.setEnabled(false);


                    }


                     setColorButton();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //Book appointment array list
        List<String> stepList = new ArrayList<>();
        stepList.add("Appointment");
        stepList.add("Location");
        stepList.add("Date/Time");
        stepList.add("Confirm");
        stepView1.setSteps(stepList);


        bottomNavigationView = (BottomNavigationView) v.findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(nav_schedule).setChecked(true);

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_home);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleUserFragment2_to_homeFragment);
                resetStaticData();
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_notification);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleUserFragment2_to_notificationUpcomingFragment);
                resetStaticData();
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_settings);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_scheduleUserFragment2_to_settingsFragment);
                resetStaticData();
            }
        });



        return v;
    }

    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

    }

    private void resetStaticData() {
        Common.step = 0;
        Common.currentTimeSlot = -1;
        Common.currentAppointment = null;
        Common.bookingDate.add(Calendar.DATE, 0);
    }

    private void confirmBooking() {

        //Send broadcast to fragment step four
        Intent intent = new Intent(Common.KEY_CONFIRM_BOOKING);
        localBroadcastManager.sendBroadcast(intent);

    }

    private void loadTimeSlotOfUser(String appointmentId) {
        //Send Local Broadcast to Fragment Step 3
        Intent intent = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
        localBroadcastManager.sendBroadcast(intent);

    }

    @SuppressLint("ResourceAsColor")
    private void setColorButton() {

        if (nextBtn.isEnabled()) {
            nextBtn.setBackgroundResource(R.color.Light_Gray);
            nextBtn.setTextColor(this.getResources().getColor(R.color.white));
        }
        else{
            nextBtn.setBackgroundResource(R.color.gray);
            nextBtn.setTextColor(this.getResources().getColor(R.color.transparentWhite2));
        }

        if (backBtn.isEnabled()) {
            backBtn.setBackgroundResource(R.color.Light_Gray);
            backBtn.setTextColor(this.getResources().getColor(R.color.white));
        }
        else{
            backBtn.setBackgroundResource(R.color.gray);
            backBtn.setTextColor(this.getResources().getColor(R.color.transparentWhite2));
        }
    }
}



