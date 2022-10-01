package com.app.margaritahousecleaning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NotificationActivity extends AppCompatActivity {

    ImageView backBtn, notificationBellRing, notificationBell;
    Button upcomingBtn, completedBtn;
    boolean clicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification_upcoming);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(NotificationActivity.this, R.color.redStatusBarColor));

        //Calling resources from .xml
        upcomingBtn = findViewById(R.id.UpcomingBtn);
        completedBtn = findViewById(R.id.completedBtn);


        backBtn = findViewById(R.id.backBtnArrow);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleUserBookedFragment scheduleUserBookedFragment = new ScheduleUserBookedFragment();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_left,
                                R.anim.slide_out_right
                        )
                        .replace(R.id.notificationContainer, scheduleUserBookedFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}