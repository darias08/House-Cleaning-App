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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
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

import java.util.HashMap;
import java.util.Map;

public class ProfileEditPhoneNumber extends Fragment {


    private String userID;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private DocumentReference documentReference;
    private TextInputLayout phoneNumberInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.phone_number_edit, container, false);

        phoneNumberInput = v.findViewById(R.id.EditTextPhoneNumberFrame);
        phoneNumberInput.getEditText().addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        //Firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        documentReference = fStore.collection("Users").document(userID);


        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_profile_et);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileEditPhoneNumber2_to_profileFragment);
            }
        });

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String phoneNumber = documentSnapshot.getString("phoneNumber");
                    phoneNumberInput.getEditText().setText(phoneNumber);

                }else {
                    Toast.makeText(getActivity(), "Document doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Document doesn't exist.", Toast.LENGTH_SHORT).show();
            }
        });





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

        Map<String, Object> hashMap = new HashMap<>();

        hashMap.put("phoneNumber", phoneNumber);
        documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(getActivity(), "Your profile has been updated!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
