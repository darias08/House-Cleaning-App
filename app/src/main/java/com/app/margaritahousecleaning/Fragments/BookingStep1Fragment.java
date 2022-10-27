package com.app.margaritahousecleaning.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.margaritahousecleaning.Adapter.MyAppointmentAdapter;
import com.app.margaritahousecleaning.Model.Appointment;
import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.UserGoogleAccount;
import com.app.margaritahousecleaning.common.Common;
import com.app.margaritahousecleaning.common.SpacesItemDecoration;
import com.app.margaritahousecleaning.interfacee.IAllAppointmentLoadListener;
import com.app.margaritahousecleaning.interfacee.IBranchLoadListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class BookingStep1Fragment extends Fragment implements IAllAppointmentLoadListener, IBranchLoadListener {


    private Button nextBtn;

    //Variable
    CollectionReference allAppointmentRef;
    CollectionReference branchRef;

    IAllAppointmentLoadListener iAllAppointmentLoadListener;
    IBranchLoadListener iBranchLoadListener;

    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.recycler_appointment)
    RecyclerView recycler_appointment;

    TextView textHello;
    CheckBox checkBox1, checkBox2;
    Unbinder unbinder;



    static BookingStep1Fragment instance;

    public static BookingStep1Fragment getInstance() {
        if (instance == null)
            instance = new BookingStep1Fragment();

            return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allAppointmentRef = FirebaseFirestore.getInstance().collection("AllAppointment");
        iAllAppointmentLoadListener = this;
        iBranchLoadListener = this;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_one, container, false);
        unbinder = ButterKnife.bind(this, itemView);

        nextBtn = itemView.findViewById(R.id.next_btn);


        initView();
        loadAllAppointment();

        return itemView;
    }

    private void initView() {
        recycler_appointment.setHasFixedSize(true);
        recycler_appointment.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recycler_appointment.addItemDecoration(new SpacesItemDecoration(4));
    }

    private void loadAllAppointment() {
            allAppointmentRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                List<String> list = new ArrayList<>();
                                list.add("Create your appointment");
                                for (QueryDocumentSnapshot documentSnapshot:task.getResult())
                                list.add(documentSnapshot.getId());
                                iAllAppointmentLoadListener.onAllAppointmentLoadSuccess(list);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            iAllAppointmentLoadListener.onAllAppointmentLoadFailed(e.getMessage());
                        }
                    });

    }

    @Override
    public void onAllAppointmentLoadSuccess(List<String> areaNameList) {

        spinner.setItems(areaNameList);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position > 0) {
                    loadBranchOfService(item.toString());
                }
                else {
                    recycler_appointment.setVisibility(View.GONE);

                }
            }
        });

    }

    @Override
    public void onAllAppointmentLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    private void loadBranchOfService(String serviceName) {

        Common.service = serviceName;

        branchRef = FirebaseFirestore.getInstance()
                .collection(("AllAppointment"))
                .document(serviceName)
                .collection("Branch");

        branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Appointment> list = new ArrayList<>();

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()) {
                        Appointment appointment = documentSnapshot.toObject(Appointment.class);
                        appointment.setAppointmentId(documentSnapshot.getId());
                        list.add(appointment);
                    }
                    iBranchLoadListener.onBranchLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBranchLoadListener.onBranchLoadFailed(e.getMessage());
            }
        });

    }

    @Override
    public void onBranchLoadSuccess(List<Appointment> appointmentList) {

        MyAppointmentAdapter adapter = new MyAppointmentAdapter(getActivity(), appointmentList);
        recycler_appointment.setAdapter(adapter);
        recycler_appointment.setVisibility(View.VISIBLE);
        /*
        textHello = (TextView) getView().findViewById(R.id.textHello);
        textHello.setVisibility(View.VISIBLE);
        checkBox1 = (CheckBox) getView().findViewById(R.id.checkbox1);
        checkBox1.setVisibility(View.VISIBLE);
        checkBox2 = (CheckBox) getView().findViewById(R.id.checkbox2);
        checkBox2.setVisibility(View.VISIBLE);

         */
    }

    private void profileClicked() {
    }

    @Override
    public void onBranchLoadFailed(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }
}
