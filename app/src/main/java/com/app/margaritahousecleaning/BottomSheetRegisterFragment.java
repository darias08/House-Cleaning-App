package com.app.margaritahousecleaning;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class  BottomSheetRegisterFragment extends BottomSheetDialogFragment {


    private EditText editTextFullName, editTextStreetAddress, editTextZipCode, editTextPhoneNumber, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://margarita-house-cleaning-default-rtdb.firebaseio.com/");
    private String userID;
    private TextView loginText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bottom_sheet_register, container, false);

        editTextFullName = v.findViewById(R.id.edit_text_FullName);
        editTextStreetAddress = v.findViewById(R.id.edit_text_StreetAddress);
        editTextZipCode = v.findViewById(R.id.edit_text_ZipCode);
        editTextPhoneNumber = v.findViewById(R.id.edit_text_PhoneNumber);
        editTextEmail = v.findViewById(R.id.edit_text_emailRegister);
        editTextPassword = v.findViewById(R.id.edit_text_passwordRegister);

        buttonRegister = v.findViewById(R.id.button_register1);
        progressBar = v.findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        editTextFullName.addTextChangedListener(textWatcher);
        editTextStreetAddress.addTextChangedListener(textWatcher);
        editTextZipCode.addTextChangedListener(textWatcher);
        editTextPhoneNumber.addTextChangedListener(textWatcher);
        editTextEmail.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);

        //This is for user who has an account and wants to be directed to the login page.
        loginText = v.findViewById(R.id.LoginText);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                BottomSheetLoginFragment bottomSheetLoginFragment = new BottomSheetLoginFragment();
                bottomSheetLoginFragment.show(getParentFragmentManager(), bottomSheetLoginFragment.getTag());
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get data from EditTexts into String variables. This is to get the user's response and store their information in the database.
                final String fullName = editTextFullName.getText().toString().trim();
                final String streetAddress = editTextStreetAddress.getText().toString().trim();
                final String zipCode = editTextZipCode.getText().toString().trim();
                final String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();


                //Check if the user has filled all the fields before sending the data to firebase.
                if (fullName.isEmpty()) {
                    editTextFullName.setError("Please provide a full name!");
                    editTextFullName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(streetAddress)) {
                    editTextStreetAddress.setError("Please provide a street address!");
                    editTextStreetAddress.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(zipCode)) {
                    editTextZipCode.setError("Please provide your area zip code!");
                    editTextZipCode.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    editTextPhoneNumber.setError("Please provide a phone number!");
                    editTextPhoneNumber.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Please provide an email address!");
                    editTextEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Please provide a valid email!");
                    editTextEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Password is required!");
                    editTextPassword.requestFocus();
                    return;
                }

                // This checks if the password contains less than or equal to 6 characters it will not proceed until it's over 6 characters.
                if (password.length() <= 6) {
                    editTextPassword.setError("Password must be greater than 6 Characters!");
                    editTextPassword.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullName, streetAddress, zipCode, phoneNumber, email, password);

                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            FirebaseDatabase.getInstance().getReference("Registered Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            //User is now registered to the app.
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Successfully created account!", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                                                progressBar.setVisibility(View.GONE);


                                            } else {
                                                Toast.makeText(getActivity(), "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }

                                        }
                                    });
                        } else {
                            Toast.makeText(getActivity(), "This account already exist!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                });

            }

        });

        return v;
    }


    private TextWatcher textWatcher = (new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String userFullName = editTextFullName.getText().toString().trim();
            String userStreetAddress = editTextStreetAddress.getText().toString().trim();
            String userZipCode = editTextZipCode.getText().toString().trim();
            String userPhoneNumber = editTextPhoneNumber.getText().toString().trim();
            String userEmail = editTextEmail.getText().toString().trim();
            String userPassword = editTextPassword.getText().toString().trim();

            buttonRegister.setEnabled(!userFullName.isEmpty() && !userStreetAddress.isEmpty() && !userZipCode.isEmpty() && !userPhoneNumber.isEmpty() && !userEmail.isEmpty() && !userPassword.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });




}

/*
 // sending data to firebase Realtime Database
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check if the phone is not registered before

                            if (snapshot.hasChild(phoneNumberTxt)) {
                                Toast.makeText(getActivity(), "Account is already registered", Toast.LENGTH_SHORT).show();
                            }

                            else {


                                databaseReference.child("users").child(phoneNumberTxt).child("full name").setValue(fullNameTxt);
                                databaseReference.child("users").child(phoneNumberTxt).child("street address").setValue(streetAddressTxt);
                                databaseReference.child("users").child(phoneNumberTxt).child("zip code").setValue(zipCodeTxt);
                                databaseReference.child("users").child(phoneNumberTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(phoneNumberTxt).child("password").setValue(passwordTxt);

                                // show a success message then finish the activity.
                                Toast.makeText(getActivity(), "User registered successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
 */

/*
@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bottom_sheet_register, container, false);

        editTextFullName.addTextChangedListener(textWatcher);
        editTextStreetAddress.addTextChangedListener(textWatcher);
        editTextZipCode.addTextChangedListener(textWatcher);
        editTextPhoneNumber.addTextChangedListener(textWatcher);
        editTextEmail.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);
        }


private TextWatcher textWatcher = (new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String userFullName = editTextFullName.getText().toString().trim();
            String userStreetAddress = editTextStreetAddress.getText().toString().trim();
            String userZipCode = editTextZipCode.getText().toString().trim();
            String userPhoneNumber = editTextPhoneNumber.getText().toString().trim();
            String userInput = editTextEmail.getText().toString().trim();
            String passwordInput = editTextPassword.getText().toString().trim();

            buttonRegister.setEnabled(!userFullName.isEmpty() && !userStreetAddress.isEmpty() && !userZipCode.isEmpty() && !userPhoneNumber.isEmpty() && !userInput.isEmpty() && !passwordInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });
}

 */

/*
mAuth.createUserWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Account Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                        }
                        else {
                            Toast.makeText(getActivity(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
 */


/*


buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get data from EditTexts into String variables.
                final String fullNameTxt = editTextFullName.getText().toString();
                final String streetAddressTxt = editTextStreetAddress.getText().toString();
                final String zipCodeTxt = editTextZipCode.getText().toString();
                final String phoneNumberTxt = editTextPhoneNumber.getText().toString();
                final String emailTxt = editTextEmail.getText().toString();
                final String passwordTxt = editTextPassword.getText().toString();


                //Check if the user has filled all the fields before sending the data to firebase.
                if (TextUtils.isEmpty(fullNameTxt)) {
                    editTextFullName.setError("Please provide a full name.");
                    return;
                }
                if (TextUtils.isEmpty(streetAddressTxt)) {
                    editTextStreetAddress.setError("Please provide a street address.");
                    return;
                }
                if (TextUtils.isEmpty(zipCodeTxt)) {
                    editTextZipCode.setError("Please provide your area zip code.");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumberTxt)) {
                    editTextPhoneNumber.setError("Please provide a phone number.");
                    return;
                }
                if (TextUtils.isEmpty(emailTxt)) {
                    editTextEmail.setError("Please provide a email address.");
                    return;
                }
                if (TextUtils.isEmpty(passwordTxt)) {
                    editTextPassword.setError("Password is required.");
                    return;
                }

                // This checks if the password contains less than or equal to 6 characters it will not proceed until it's over 6 characters.
                if (passwordTxt.length() <= 6) {
                    editTextPassword.setError("Password must be greater than 6 Characters.");
                    return;
                }

                // sending data to firebase Realtime Database
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // check if the email is not registered before

                            if (snapshot.hasChild(emailTxt)) {
                                Toast.makeText(getActivity(), "Account is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                databaseReference.child("users").child(emailTxt).child("full name").setValue(fullNameTxt);
                                databaseReference.child("users").child(emailTxt).child("street address").setValue(streetAddressTxt);
                                databaseReference.child("users").child(emailTxt).child("zip code").setValue(zipCodeTxt);
                                databaseReference.child("users").child(emailTxt).child("phone number").setValue(phoneNumberTxt);
                                databaseReference.child("users").child(emailTxt).child("password").setValue(passwordTxt);

                                // show a success message then finish the activity.
                                Toast.makeText(getActivity(), "User registered successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));

                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        return v;
    }

 */