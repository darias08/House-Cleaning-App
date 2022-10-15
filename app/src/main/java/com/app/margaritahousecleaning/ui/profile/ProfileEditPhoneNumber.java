package com.app.margaritahousecleaning.ui.profile;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.UserProfile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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

public class ProfileEditPhoneNumber extends Fragment {

    private Toolbar toolbar;
    private EditText userPhoneNumberET;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String userID;
    private TextInputLayout phoneNumberInput;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.phone_number_edit, container, false);

        phoneNumberInput = v.findViewById(R.id.EditTextPhoneNumberFrame);

        //Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();


        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_profile_et);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileEditPhoneNumber2_to_profileFragment);
            }
        });

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);

                if (userProfile != null) {
                    String phoneNumber = userProfile.phoneNumber;
                    phoneNumberInput.getEditText().setText(phoneNumber);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        phoneNumberInput.getEditText().addTextChangedListener(new PhoneNumberFormattingTextWatcher());


        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_edit_phone, menu);
        MenuItem item = menu.findItem(R.id.saveTxtBtn);
        item.getActionView().findViewById(R.id.saveTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation(v);
            }
        });
    }

    private boolean validatingPhoneNumber() {

        String phoneNumber = phoneNumberInput.getEditText().getText().toString();

        if (phoneNumber.isEmpty()) {
            phoneNumberInput.setError("Please provide a phone number!");
            phoneNumberInput.requestFocus();
            return false;
        }
        if (phoneNumber.length() <= 13) {
            phoneNumberInput.setError("Please provide a valid phone number!");
            phoneNumberInput.requestFocus();
            return false;
        } else {
            phoneNumberInput.setError(null);
            return true;
        }
    }


    public void checkValidation(View v) {
        if (!validatingPhoneNumber()) {
            return;
        }

        String phoneNumber = phoneNumberInput.getEditText().getText().toString();
        phoneNumberInput.getEditText().setText(phoneNumber);

        HashMap hashMap = new HashMap();
        hashMap.put("phoneNumber", phoneNumber);
        databaseReference.child(userID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getActivity(), "Your profile has been updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
