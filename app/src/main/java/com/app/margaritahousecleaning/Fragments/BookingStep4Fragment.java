package com.app.margaritahousecleaning.Fragments;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.margaritahousecleaning.Activities.LoginActivity;
import com.app.margaritahousecleaning.Activities.RegisteredUserActivity;
import com.app.margaritahousecleaning.Model.ApartmentClean;
import com.app.margaritahousecleaning.Model.BookedAppointmentInfo;
import com.app.margaritahousecleaning.Model.BookingInformation;

import com.app.margaritahousecleaning.Model.ConstructionClean;
import com.app.margaritahousecleaning.Model.OfficeClean;
import com.app.margaritahousecleaning.Model.ResidentialClean;
import com.app.margaritahousecleaning.Model.Services;
import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.UserAppointment;
import com.app.margaritahousecleaning.UserProfile;
import com.app.margaritahousecleaning.common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class BookingStep4Fragment extends Fragment {


    static BookingStep4Fragment instance;


    SimpleDateFormat simpleDateFormat;
    LocalBroadcastManager localBroadcastManager;
    Unbinder unbinder;
    private String userID;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private DocumentReference documentReference;
    private DocumentReference documentReferenceRB;
    private DocumentReference docRef;
    private int progressBarCounter = 5000;
    private int progressBarComplete = 6400;
    private int closeDialog = 6400;
    private int loadingDialog = 4000;
    private AlertDialog dialog;
    ViewPager viewPager;
    CollectionReference userRef;

    Button confirmBtn;

    @BindView(R.id.txt_booking_time_txt)
    TextView txt_booking_time_txt;
    @BindView(R.id.txt_street_address)
    TextView txt_street_address;
    @BindView(R.id.txt_phone_number_txt)
    TextView txt_phoneNumber_user;
    @BindView(R.id.txt_email_user)
    TextView txt_email_user;
    TextView txt_zip_code;
    TextView txt_cleaning_service;
    TextView txt_customer_name;

    BroadcastReceiver confirmBookingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setData();
        }
    };

    private void setData() {
        txt_booking_time_txt.setText(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                .append(" at ")
                .append(simpleDateFormat.format(Common.bookingDate.getTime())));

    }

    public static BookingStep4Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep4Fragment();

        return instance;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //Apply format for date display on Confirm
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(confirmBookingReceiver, new IntentFilter(Common.KEY_CONFIRM_BOOKING));


        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();
    }


    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(confirmBookingReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_four, container, false);



        confirmBtn = (Button) itemView.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShow();
            }
        });

        //Firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        documentReference = fStore.collection("Users").document(userID);
        documentReferenceRB = fStore.collection("CleaningServiceRB").document(userID);


        txt_street_address = itemView.findViewById(R.id.txt_street_address);
        txt_email_user = itemView.findViewById(R.id.txt_email_user);
        txt_phoneNumber_user = itemView.findViewById(R.id.txt_phone_number_txt);
        txt_zip_code = itemView.findViewById(R.id.txt_zip_code);
        txt_customer_name = itemView.findViewById(R.id.txt_customer_name);
        txt_cleaning_service = itemView.findViewById(R.id.txt_cleaning_service_type);


        unbinder = ButterKnife.bind(this, itemView);


        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    //Retrieving firestore database for user information.
                    String streetAddress = documentSnapshot.getString("streetAddress");
                    String zipCode = documentSnapshot.getString("zipCode");
                    String phoneNumber = documentSnapshot.getString("phoneNumber");
                    String email = documentSnapshot.getString("email");
                    String customerName = documentSnapshot.getString("fullName");

                    txt_street_address.setText(streetAddress);
                    txt_zip_code.setText(zipCode);
                    txt_phoneNumber_user.setText(phoneNumber);
                    txt_email_user.setText(email);
                    txt_customer_name.setText(customerName);
                }
                else {
                    Toast.makeText(getActivity(), "Document doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Document doesn't exist.", Toast.LENGTH_SHORT).show();
            }
        });

        documentReferenceRB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //Retrieving radioButton text from firestore

                if (documentSnapshot.exists()) {
                    String residential = documentSnapshot.getString("residentialCleaning");

                    txt_cleaning_service.setText(residential);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });






        return itemView;
    }



    private void dialogShow() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_popup_data);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        ProgressBar progressBar = dialog.findViewById(R.id.custom_progressBar);
        FrameLayout bookComplete = dialog.findViewById(R.id.BookComplete);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Hide progressbar
                progressBar.setVisibility(View.INVISIBLE);

                //Show complete check mark
                bookComplete.setVisibility(View.VISIBLE);

            }
        }, progressBarCounter);

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                //Process Timestamp
                //Timestamp is used to filter all booking with date is greater today
                //For only display all future booking

                String startTime = Common.convertTimeSlotToString(Common.currentTimeSlot);
                String[] convertTime = startTime.split("-"); // Split ex: 9:00 - 10:00
                //Get start time: get 9:00
                String[] startTimeConvert = convertTime[0].split(":");
                int startHourInt = Integer.parseInt(startTimeConvert[0].trim()); // we get 9
                int startMinInt = Integer.parseInt(startTimeConvert[1].trim()); //we get 00

                Calendar bookingDateWithOurHouse = Calendar.getInstance();
                bookingDateWithOurHouse.setTimeInMillis(Common.bookingDate.getTimeInMillis());
                bookingDateWithOurHouse.set(Calendar.HOUR_OF_DAY, startHourInt);
                bookingDateWithOurHouse.set(Calendar.MINUTE, startMinInt);

                //Create timestamp object and apply to BookingInformation
                Timestamp timestamp = new Timestamp(bookingDateWithOurHouse.getTime());

                //Create booking information
                BookingInformation bookingInformation = new BookingInformation();

                bookingInformation.setTimestamp(timestamp);

                bookingInformation.setDone(false); //Always false, because we will use this field to filter for display on user
                bookingInformation.setAppointmentId(Common.currentAppointment.getAppointmentId());

                bookingInformation.setTime(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                        .append(" at ")
                        .append(simpleDateFormat.format(Common.bookingDate.getTime())).toString());
                bookingInformation.setSlot(Long.valueOf(Common.currentTimeSlot));

                //Submit to Appointment Document
                DocumentReference bookingDate = FirebaseFirestore.getInstance()
                        .collection("AllAppointment")
                        .document(Common.service)
                        .collection("Branch")
                        .document(Common.currentAppointment.getAppointmentId())
                        .collection(Common.simpleDateFormat.format(Common.bookingDate.getTime()))
                        .document(String.valueOf(Common.currentTimeSlot));

                //Write data
                bookingDate.set(bookingInformation)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //Here we can write a function to check
                                //if already exist an booking, we will prevent new booking

                                addToUserBooking(bookingInformation);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                //Storing other information to database
                final String streetAddress = txt_street_address.getText().toString();
                final String zipCode = txt_zip_code.getText().toString();
                final String serviceType =txt_cleaning_service.getText().toString();

                BookedAppointmentInfo bookedAppointmentInfo = new BookedAppointmentInfo(streetAddress, zipCode, serviceType);
                FirebaseDatabase.getInstance().getReference("Booked Appointments")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(bookedAppointmentInfo);


            }
        }, progressBarComplete);


        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, closeDialog);
    }


    private void addToUserBooking(BookingInformation bookingInformation) {

        //First, create new Collection
        CollectionReference userBooking = FirebaseFirestore.getInstance()
                .collection("User")
                .document(Common.currentAppointment.getAppointmentId())
                .collection("Booking");

        //Check if exist document in this collection
        userBooking.whereEqualTo("done", false)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.getResult().isEmpty()){
                           //Set data
                           userBooking.document()
                                   .set(bookingInformation)
                                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void unused) {

                                           //addToCalendar(Common.bookingDate, Common.convertTimeSlotToString(Common.currentTimeSlot));

                                           Navigation.findNavController(getView()).navigate(R.id.action_scheduleUserFragment2_to_notificationFragment);
                                           resetStaticData();
                                           Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                                       }
                                   })
                                   .addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                       }
                                   });
                        }
                       else {
                           Navigation.findNavController(getView()).navigate(R.id.action_scheduleUserFragment2_to_notificationFragment);
                           resetStaticData();
                           Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
    }



    /*
    private void addToCalendar(Calendar bookingDate, String startDate) {

        String startTime = Common.convertTimeSlotToString(Common.currentTimeSlot);
        String[] convertTime = startTime.split("-"); // Split ex: 9:00 - 10:00
        //Get start time: get 9:00
        String[] startTimeConvert = convertTime[0].split(":");
        int startHourInt = Integer.parseInt(startTimeConvert[0].trim()); // we get 9
        int startMinInt = Integer.parseInt(startTimeConvert[1].trim()); //we get 00

        String[] endTimeConvert = convertTime[0].split(":");
        int endHourInt = Integer.parseInt(endTimeConvert[0].trim()); // we get 10
        int endMinInt = Integer.parseInt(endTimeConvert[1].trim()); //we get 00

        Calendar startEvent = Calendar.getInstance();
        startEvent.setTimeInMillis(bookingDate.getTimeInMillis());
        startEvent.set(Calendar.HOUR_OF_DAY, startHourInt);// Set event start time start hour
        startEvent.set(Calendar.MINUTE, startMinInt); //Set event start min

        Calendar endEvent = Calendar.getInstance();
        endEvent.setTimeInMillis(bookingDate.getTimeInMillis());
        endEvent.set(Calendar.HOUR_OF_DAY, endHourInt);// Set event start time start hour
        endEvent.set(Calendar.MINUTE, endMinInt); //Set event start min


        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        String startEventTime = calendarDateFormat.format(startEvent.getTime());
        String endEventTime = calendarDateFormat.format(endEvent.getTime());

        addToDeviceCalendar(startEventTime, endEventTime, "Cleaning Service Booking", new StringBuilder("Appointment from ")
                .append(startTime)
                .append(" with ")
                .append(Common.currentAppointment.getName())
                .append(" at ")
                .append(Common.current)
    }

     */



    private void resetStaticData() {
        Common.step = 0;
        Common.currentTimeSlot = -1;
        Common.currentAppointment = null;
        Common.bookingDate.add(Calendar.DATE, 0);
    }
}
