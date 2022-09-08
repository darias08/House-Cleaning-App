package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ScheduleFragment3 extends Fragment {

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationItemView bottomNavigationItemView;


    private Button backbtn;
    private Button NextBtn;
    private EditText etRFA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule3, container, false);
        //How to set bottom navigation Icon active.
        bottomNavigationView = (BottomNavigationView)v.findViewById(bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.scheduleFragment).setChecked(true);

        backbtn = (Button) v.findViewById(R.id.backBtn3);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scheduleFragment3_to_scheduleFragment2);
            }
        });


        etRFA = v.findViewById(R.id.etRFA);
        final String userResponses = etRFA.getText().toString();

        NextBtn = v.findViewById(R.id.NextBtn3);
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                User user = new User();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                FirebaseDatabase.getInstance().getReference("Users Response").child("uid").setValue(user);

                Navigation.findNavController(view).navigate(R.id.action_scheduleFragment3_to_scheduleFragment4);
            }
        });





        return v;

    }
}