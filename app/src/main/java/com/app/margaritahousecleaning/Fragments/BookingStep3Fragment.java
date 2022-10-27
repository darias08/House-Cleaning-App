package com.app.margaritahousecleaning.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.margaritahousecleaning.Adapter.MyTimeSlotAdapter;
import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.common.Common;
import com.app.margaritahousecleaning.common.SpacesItemDecoration;
import com.app.margaritahousecleaning.common.TimeSlot;
import com.app.margaritahousecleaning.interfacee.ITimeSlotLoadListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.model.CalendarItemStyle;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarPredicate;
import dmax.dialog.SpotsDialog;

public class BookingStep3Fragment extends Fragment implements ITimeSlotLoadListener {

    //Variable
    DocumentReference appointmentDoc;
    ITimeSlotLoadListener iTimeSlotLoadListener;
    AlertDialog dialog;

    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;

    @BindView(R.id.recycler_time_slot)
    RecyclerView recycler_time_slot;
    @BindView(R.id.calendarView)
    HorizontalCalendarView calendarView;
    SimpleDateFormat simpleDateFormat;

    BroadcastReceiver displayTimeSlot = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE, 0); //Add current date
            loadAvailableTimeSlotOfAppointment(Common.currentAppointment.getAppointmentId(), simpleDateFormat.format(date.getTime()));
        }
    };

    final int MAX_SELECTABLE_DATE_IN_FUTURE = 365;

    private void loadAvailableTimeSlotOfAppointment(String appointmentId, final String bookDate) {

        dialog.show();


            appointmentDoc = FirebaseFirestore.getInstance()
                    .collection("AllAppointment")
                    .document(Common.service)
                    .collection("Branch")
                    .document(Common.currentAppointment.getAppointmentId());

            //Retrieve information
            appointmentDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists())
                        {
                            CollectionReference date = FirebaseFirestore.getInstance()
                                    .collection("AllAppointment")
                                    .document(Common.service)
                                    .collection("Branch")
                                    .document(Common.currentAppointment.getAppointmentId())
                                    .collection(bookDate);

                            date.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful())
                                    {
                                        QuerySnapshot querySnapshot = task.getResult();
                                        if (querySnapshot.isEmpty()) //If no appointment available
                                            iTimeSlotLoadListener.onTimeSlotLoadEmpty();

                                        else{
                                            List<TimeSlot> timeSlots = new ArrayList<>();
                                            for(QueryDocumentSnapshot document: task.getResult())
                                                timeSlots.add(document.toObject(TimeSlot.class));
                                            iTimeSlotLoadListener.onTimeSlotLoadSuccess(timeSlots);
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    iTimeSlotLoadListener.onTimeSlotLoadFailed(e.getMessage());
                                }
                            });
                        }
                    }
                }
            });

    }

    static BookingStep3Fragment instance;
    public static BookingStep3Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep3Fragment();

        return instance;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iTimeSlotLoadListener = this;

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(displayTimeSlot, new IntentFilter(Common.KEY_DISPLAY_TIME_SLOT));

        simpleDateFormat = new SimpleDateFormat("MM_dd_yyyy"); // 10_19_2022

        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();



    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(displayTimeSlot);
        super.onDestroy();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View itemView =  inflater.inflate(R.layout.fragment_booking_step_three, container, false);
        unbinder  = ButterKnife.bind(this, itemView);

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 0); //Add current date


        init(itemView);
        return  itemView;

    }

    private void init(View itemView) {
        recycler_time_slot.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recycler_time_slot.setLayoutManager(gridLayoutManager);
        recycler_time_slot.addItemDecoration(new SpacesItemDecoration(8));


        //Calendar
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONDAY, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.FRIDAY, 365);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(itemView, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .mode(HorizontalCalendar.Mode.DAYS)
                .defaultSelectedDate(startDate)
                .build();


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                if (Common.bookingDate.getTimeInMillis() != date.getTimeInMillis())
                {
                    Common.bookingDate = date; // this code will not load again if you select a new day same with day selected
                    loadAvailableTimeSlotOfAppointment(Common.currentAppointment.getAppointmentId(),
                            simpleDateFormat.format(date.getTime()));
                }
            }
        });


        horizontalCalendar.refresh();

    }

    @Override
    public void onTimeSlotLoadSuccess(List<TimeSlot> timeSlotList) {
        MyTimeSlotAdapter adapter = new MyTimeSlotAdapter(getContext(), timeSlotList);
        recycler_time_slot.setAdapter(adapter);

        dialog.dismiss();
    }

    @Override
    public void onTimeSlotLoadFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onTimeSlotLoadEmpty() {
        MyTimeSlotAdapter adapter = new MyTimeSlotAdapter(getContext());
        recycler_time_slot.setAdapter(adapter);

        dialog.dismiss();
    }
}
