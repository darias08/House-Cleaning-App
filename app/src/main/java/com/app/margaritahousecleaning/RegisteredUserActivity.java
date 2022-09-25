package com.app.margaritahousecleaning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisteredUserActivity extends AppCompatActivity {


    private EditText editTextFirstName, editTextLastName, editTextStreetAddress, editTextZipCode, editTextPhoneNumber, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://margarita-house-cleaning-default-rtdb.firebaseio.com/");
    private String userID;
    private TextView loginText;

    //Global Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_user);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(RegisteredUserActivity.this, R.color.redStatusBarColor));


        editTextFirstName = findViewById(R.id.edit_text_FirstName);
        editTextLastName = findViewById(R.id.edit_text_LastName);
        editTextStreetAddress = findViewById(R.id.edit_text_StreetAddress);
        editTextZipCode = findViewById(R.id.edit_text_ZipCode);
        editTextPhoneNumber = findViewById(R.id.edit_text_PhoneNumber);
        editTextEmail = findViewById(R.id.edit_text_emailRegister);
        editTextPassword = findViewById(R.id.edit_text_passwordRegister);

        buttonRegister = findViewById(R.id.button_register1);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        editTextFirstName.addTextChangedListener(textWatcher);
        editTextLastName.addTextChangedListener(textWatcher);
        editTextStreetAddress.addTextChangedListener(textWatcher);
        editTextZipCode.addTextChangedListener(textWatcher);
        editTextPhoneNumber.addTextChangedListener(textWatcher);
        editTextEmail.addTextChangedListener(textWatcher);
        editTextPassword.addTextChangedListener(textWatcher);

        //This is for user who has an account and wants to be directed to the login page.
        loginText = findViewById(R.id.LoginText);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisteredUserActivity.this, LoginUserActivity.class);
                startActivity(intent);
                finish();

            }
        });

        String[] permission = {
                Manifest.permission.READ_PHONE_NUMBERS
        };

        requestPermissions(permission, 102);

        editTextPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPhoneNumberToEditText();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Turn EditText into String variables.
                final String firstName = editTextFirstName.getText().toString().trim();
                final String lastName = editTextLastName.getText().toString().trim();
                final String streetAddress = editTextStreetAddress.getText().toString().trim();
                final String zipCode = editTextZipCode.getText().toString().trim();
                final String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();


                //Check if the user has filled all the fields before sending the data to firebase.
                if (firstName.isEmpty()) {
                    editTextFirstName.setError("Please provide a first name!");
                    editTextFirstName.requestFocus();
                    return;
                }
                if (lastName.isEmpty()) {
                    editTextLastName.setError("Please provide a last name!");
                    editTextLastName.requestFocus();
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
                            UserProfile userProfile = new UserProfile(firstName, lastName, streetAddress, zipCode, phoneNumber, email, password);

                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            FirebaseDatabase.getInstance().getReference("Registered Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            //User is now registered to the app.
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisteredUserActivity.this, "Successfully created account!", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(RegisteredUserActivity.this, LoginActivity.class));
                                                progressBar.setVisibility(View.GONE);


                                            } else {
                                                Toast.makeText(RegisteredUserActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisteredUserActivity.this, "This account already exist!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                });

            }

        });
    }

    private void setPhoneNumberToEditText() {

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        editTextPhoneNumber.setText(telephonyManager.getLine1Number());
    }

    //Textwatcher is if the user has filled out all spaces in order for the button to become active.
    private TextWatcher textWatcher = (new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String userFirstName = editTextFirstName.getText().toString().trim();
            String userLastName = editTextLastName.getText().toString().trim();
            String userStreetAddress = editTextStreetAddress.getText().toString().trim();
            String userZipCode = editTextZipCode.getText().toString().trim();
            String userPhoneNumber = editTextPhoneNumber.getText().toString().trim();
            String userEmail = editTextEmail.getText().toString().trim();
            String userPassword = editTextPassword.getText().toString().trim();

            buttonRegister.setEnabled(!userFirstName.isEmpty() && !userLastName.isEmpty() && !userStreetAddress.isEmpty() && !userZipCode.isEmpty() && !userPhoneNumber.isEmpty() && !userEmail.isEmpty() && !userPassword.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });
}

