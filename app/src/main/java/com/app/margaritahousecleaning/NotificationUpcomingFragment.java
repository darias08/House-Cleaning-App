package com.app.margaritahousecleaning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationUpcomingFragment extends Fragment {
    DatabaseReference databaseReference;
    String userID;
    ImageView backBtn;
    TextView userAddress, userZipCode, userPhoneNumber, datePicked, timePicked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification_upcoming, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Booked Appointments");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        backBtn = v.findViewById(R.id.backBtnArrow);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.findNavController(view).navigate(R.id.action_notificationUpcomingFragment_to_scheduleUserBookedFragment);
            }
        });


        TextView userDatePicked = (TextView) v.findViewById(R.id.datePicked);
        TextView userTimePicked = (TextView) v.findViewById(R.id.timePicked);
        TextView userAddressInfo = (TextView) v.findViewById(R.id.userAddressInformation);
        TextView userZipCode = (TextView) v.findViewById(R.id.UserZipCodeInformation);
        TextView userPhoneNumber = (TextView) v.findViewById(R.id.userPhoneNumber1);
        TextView userResponse = (TextView) v.findViewById(R.id.userResponseTV);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAppointment userAppointment = snapshot.getValue(UserAppointment.class);

                if (userAppointment != null) {
                    String datePicked = userAppointment.datePicked;
                    String timePicked = userAppointment.timePicked;
                    String addressInfo = userAppointment.address;
                    String zipCodeInfo = userAppointment.zipcode;
                    String phoneNumber = userAppointment.phoneNumber;
                    String userReplied = userAppointment.userMessage;


                    userDatePicked.setText(datePicked);
                    userTimePicked.setText(timePicked);
                    userAddressInfo.setText(addressInfo);
                    userZipCode.setText(zipCodeInfo);
                    userPhoneNumber.setText(phoneNumber);
                    userResponse.setText(userReplied);

                }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }

}