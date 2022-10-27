package com.app.margaritahousecleaning.Adapter;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.margaritahousecleaning.Fragments.BookingStep4Fragment;
import com.app.margaritahousecleaning.Model.ApartmentClean;
import com.app.margaritahousecleaning.Model.Appointment;
import com.app.margaritahousecleaning.Model.ConstructionClean;
import com.app.margaritahousecleaning.Model.OfficeClean;
import com.app.margaritahousecleaning.Model.ResidentialClean;
import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.UserGoogleAccount;
import com.app.margaritahousecleaning.common.Common;
import com.app.margaritahousecleaning.interfacee.IRecyclerItemSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.daemondhruv.customviewgroup.ConstraintRadioGroup;

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.MyViewHolder> {

    private static final String TAG = "BUNDLE";
    private String userID;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private DocumentReference documentReference;
    private DocumentReference documentReferenceRB;
    TextView txt_user_name;
    Context context;
    List<Appointment> appointmentList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;
    TextView cleaningServiceTitleTxt;
    RadioButton residentialRB, apartmentRB, officeRB, constructionRB;


    public MyAppointmentAdapter(Context context, List<Appointment> appointmentList) {
        this.context = context;
        this.appointmentList = appointmentList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_appointment, viewGroup, false);

        //Firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        documentReference = fStore.collection("Users").document(userID);

        txt_user_name = itemView.findViewById(R.id.txt_user_name);


        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String fullName = documentSnapshot.getString("fullName");
                    txt_user_name.setText(fullName);

                }else {
                    Toast.makeText(context.getApplicationContext(), "Document doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context.getApplicationContext(), "Document doesn't exist.", Toast.LENGTH_SHORT).show();
            }
        });

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //myViewHolder.txt_appointment_name.setText(appointmentList.get(i).getName());
        //myViewHolder.txt_appointment_service.setText(appointmentList.get(i).getService());


        if (!cardViewList.contains(myViewHolder.card_appointment))
            cardViewList.add(myViewHolder.card_appointment);

        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                //Set white background for all card not be selected
                for (CardView cardView : cardViewList)
                    cardView.setCardBackgroundColor(ContextCompat.getColor(context,
                            R.color.Light_Gray));

                //Set selected BG for only selected item
                myViewHolder.card_appointment.setCardBackgroundColor(ContextCompat.getColor(context,
                        R.color.redStatusBarColor));


                cleaningServiceTitleTxt = (TextView) view.findViewById(R.id.cleaningServiceTxt);
                cleaningServiceTitleTxt.setVisibility(view.VISIBLE);
                ConstraintRadioGroup radioGroup = (ConstraintRadioGroup) view.findViewById(R.id.RadioGroup);
                radioGroup.setVisibility(View.VISIBLE);

                residentialRB = (RadioButton) view.findViewById(R.id.residentialRB);
                apartmentRB = (RadioButton) view.findViewById(R.id.apartmentRB);
                officeRB = (RadioButton) view.findViewById(R.id.officeRB);
                constructionRB = (RadioButton) view.findViewById(R.id.constructionRB);

                //Firebase
                fAuth = FirebaseAuth.getInstance();
                fStore = FirebaseFirestore.getInstance();
                userID = fAuth.getCurrentUser().getUid();


                radioGroup.setOnCheckedChangeListener(new ConstraintRadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(ConstraintRadioGroup group, int checkedId) {
                        switch (checkedId) {


                            case R.id.residentialRB:

                                String residential = residentialRB.getText().toString();

                                DocumentReference documentReferenceRB = fStore.collection("CleaningServiceRB").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("residentialCleaning", residential);
                                documentReferenceRB.set(user);

                                //Enabling next button
                                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                                intent.putExtra(Common.KEY_APPOINTMENT_STORE, appointmentList.get(pos));
                                intent.putExtra(Common.KEY_STEP, 1);
                                localBroadcastManager.sendBroadcast(intent);

                                break;

                            case R.id.apartmentRB:
                                String apartment = apartmentRB.getText().toString();

                                DocumentReference documentReferenceRB1 = fStore.collection("CleaningServiceRB").document(userID);
                                Map<String, Object> user1 = new HashMap<>();
                                user1.put("apartmentCleaning", apartment);
                                documentReferenceRB1.set(user1);

                                Intent intent1 = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                                intent1.putExtra(Common.KEY_APPOINTMENT_STORE, appointmentList.get(pos));
                                intent1.putExtra(Common.KEY_STEP, 1);
                                localBroadcastManager.sendBroadcast(intent1);
                                break;

                            case R.id.officeRB:
                                String officeCleaning = officeRB.getText().toString();
                                OfficeClean officeClean = new OfficeClean(officeCleaning);
                                FirebaseDatabase.getInstance().getReference("Services")
                                        .child(FirebaseAuth.getInstance().getUid()).setValue(officeClean);

                                Intent intent2 = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                                intent2.putExtra(Common.KEY_APPOINTMENT_STORE, appointmentList.get(pos));
                                intent2.putExtra(Common.KEY_STEP, 1);
                                localBroadcastManager.sendBroadcast(intent2);
                                break;

                            case R.id.constructionRB:
                                String constructionCleaning = constructionRB.getText().toString();
                                ConstructionClean construction = new ConstructionClean(constructionCleaning);
                                FirebaseDatabase.getInstance().getReference("Services")
                                        .child(FirebaseAuth.getInstance().getUid()).setValue(construction);

                                Intent intent3 = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                                intent3.putExtra(Common.KEY_APPOINTMENT_STORE, appointmentList.get(pos));
                                intent3.putExtra(Common.KEY_STEP, 1);
                                localBroadcastManager.sendBroadcast(intent3);
                                break;
                        }
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_appointment_name, txt_appointment_service;
        CardView card_appointment;
        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_appointment = (CardView) itemView.findViewById(R.id.card_appointment);
            //txt_appointment_name = (TextView) itemView.findViewById(R.id.txt_service_name);
            //txt_appointment_service = (TextView) itemView.findViewById(R.id.txt_service_title);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view, getAdapterPosition());
        }
    }
}
