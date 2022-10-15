package com.app.margaritahousecleaning.ui.profile;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.app.margaritahousecleaning.MainHomeActivity;
import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.ScheduleUserFragment;
import com.app.margaritahousecleaning.SettingsFragmentTest;
import com.app.margaritahousecleaning.UserGoogleAccount;
import com.app.margaritahousecleaning.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    TextView userFullName, userStreetAddress, userZipCode, userPhoneNumber;
    Toolbar backArrowBtn;

    private DatabaseReference databaseReference;
    private String userID;
    private FirebaseUser user;
    ImageView  right_arrow_editName, right_arrow_editAddress, right_arrow_editZipCode, right_arrow_editPhoneNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        userFullName = v.findViewById(R.id.userFullNameTxt);
        userStreetAddress = v.findViewById(R.id.userStreetAddressTxt);
        userZipCode = v.findViewById(R.id.userZipCodeTxt);
        userPhoneNumber = v.findViewById(R.id.userPhoneNumberTxt);

        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               UserProfile userProfile = snapshot.getValue(UserProfile.class);

                if (userProfile != null) {
                    String fullName = userProfile.fullName;
                    String streetAddress = userProfile.streetAddress;
                    String zipCode = userProfile.zipCode;
                    String phoneNumber = userProfile.phoneNumber;


                    userFullName.setText(fullName);
                    userStreetAddress.setText(streetAddress);
                    userZipCode.setText(zipCode);
                    userPhoneNumber.setText(phoneNumber);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backArrowBtn = v.findViewById(R.id.toolbar_profile);
        backArrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_settingsFragment);
            }
        });

        right_arrow_editName = v.findViewById(R.id.right_arrow_name);
        right_arrow_editAddress = v.findViewById(R.id.right_arrow_address);
        right_arrow_editZipCode = v.findViewById(R.id.right_arrow_zipcode);
        right_arrow_editPhoneNumber = v.findViewById(R.id.right_arrow_phone);

        right_arrow_editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_profileEditFullName);
            }
        });
        right_arrow_editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_profileEditStreetAddress2);
            }
        });
        right_arrow_editZipCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_profileEditZipCode22);
            }
        });
        right_arrow_editPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_profileEditPhoneNumber22);
            }
        });


        return v;
    }

}
