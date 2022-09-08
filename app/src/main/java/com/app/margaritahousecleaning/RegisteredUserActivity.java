package com.app.margaritahousecleaning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
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


    private EditText editTextFullName, editTextStreetAddress, editTextZipCode, editTextPhoneNumber, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://margarita-house-cleaning-default-rtdb.firebaseio.com/");
    private String userID;
    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_user);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(RegisteredUserActivity.this, R.color.redStatusBarColor));


        editTextFullName = findViewById(R.id.edit_text_FullName);
        editTextStreetAddress = findViewById(R.id.edit_text_StreetAddress);
        editTextZipCode = findViewById(R.id.edit_text_ZipCode);
        editTextPhoneNumber = findViewById(R.id.edit_text_PhoneNumber);
        editTextEmail = findViewById(R.id.edit_text_emailRegister);
        editTextPassword = findViewById(R.id.edit_text_passwordRegister);

        buttonRegister = findViewById(R.id.button_register1);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        editTextFullName.addTextChangedListener(textWatcher);
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

