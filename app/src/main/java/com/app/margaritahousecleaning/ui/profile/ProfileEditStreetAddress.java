package com.app.margaritahousecleaning.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.UserProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileEditStreetAddress extends Fragment {

    private Toolbar toolbar;
    private EditText userStreetAddressET;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String userID;
    private TextInputLayout addressInput;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.street_address_edit, container, false);

        addressInput = v.findViewById(R.id.EditTextStreetAddressFrame);

        //Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_profile_et);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileEditStreetAddress2_to_profileFragment2);
            }
        });


        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);

                if (userProfile != null) {
                    String streetAddress = userProfile.streetAddress;
                    addressInput.getEditText().setText(streetAddress);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_edit_address, menu);
        MenuItem item = menu.findItem(R.id.saveTxtBtn);
        item.getActionView().findViewById(R.id.saveTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation(v);
            }
        });
    }

    private boolean validatingStreetAddress() {

        String streetAddress = addressInput.getEditText().getText().toString();

        if (streetAddress.isEmpty()) {
            addressInput.setError("Please provide a Street Address!");
            addressInput.requestFocus();
            return false;
        }
         else {
            addressInput.setError(null);
            return true;
        }
    }


    public void checkValidation(View v) {
        if (!validatingStreetAddress()) {
            return;
        }

        String streetAddress = addressInput.getEditText().getText().toString();
        addressInput.getEditText().setText(streetAddress);

        HashMap hashMap = new HashMap();
        hashMap.put("phoneNumber", streetAddress);
        databaseReference.child(userID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getActivity(), "Your profile has been updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
