package com.app.margaritahousecleaning.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisteredUserActivity extends AppCompatActivity {


    private EditText editTextFullName1, editTextStreetAddress1, editTextZipCode1, editTextPhoneNumber1, editTextEmail1, editTextPassword;
    private Button buttonRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://margarita-house-cleaning-c3bd7-default-rtdb.firebaseio.com/");
    private String userID;
    private TextView loginText;
    private TextInputLayout textInputPassword1;
    private FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_user);
        getWindow().setStatusBarColor(ContextCompat.getColor(RegisteredUserActivity.this, R.color.Dark_Grey));


        editTextFullName1 = findViewById(R.id.EditTextFullName1);
        editTextStreetAddress1 = findViewById(R.id.ETStreetAddress);
        editTextZipCode1 = findViewById(R.id.EditTextZipCode);
        editTextPhoneNumber1 = findViewById(R.id.EditTextPhoneNumber);
        editTextPhoneNumber1.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        editTextEmail1 = findViewById(R.id.edit_text_emailRegister);
        textInputPassword1 = findViewById(R.id.PasswordFieldRegister);
        editTextPassword = findViewById(R.id.passwordET);

        buttonRegister = findViewById(R.id.button_register1);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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
        

    }

    private boolean validateName() {
        String fullName = editTextFullName1.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName1.setError("Please provide a full name.");
            editTextFullName1.requestFocus();
            return false;
        } else {
            editTextFullName1.setError(null);
            return true;
        }
    }

    private boolean validateStreetAddress() {
        String streetAddress = editTextStreetAddress1.getText().toString().trim();

        if (streetAddress.isEmpty()) {
            editTextStreetAddress1.setError("Please provide a street address.");
            editTextStreetAddress1.requestFocus();
            return false;
        } else {
            editTextStreetAddress1.setError(null);
            return true;
        }
    }

    private boolean validateZipCode() {
        String zipCode = editTextZipCode1.getText().toString();

        if (zipCode.isEmpty()) {
            editTextZipCode1.setError("Please provide a zip code!");
            editTextZipCode1.requestFocus();
            return false;
        }
        if (zipCode.length() <= 4) {
            editTextZipCode1.setError("Please provide a full zip code!");
            editTextZipCode1.requestFocus();
            return false;
        } else {
            editTextZipCode1.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = editTextPhoneNumber1.getText().toString().trim();

        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber1.setError("Please provide a phone number!");
            editTextPhoneNumber1.requestFocus();
            return false;
        }
        if (phoneNumber.length() <= 13) {
            editTextPhoneNumber1.setError("Please provide a full phone number!");
            editTextPhoneNumber1.requestFocus();
            return false;
        } else {
            editTextPhoneNumber1.setError(null);
            return true;
        }
    }

    private boolean validateEmailAddress() {
        String emailAddress = editTextEmail1.getText().toString().trim();

        if (emailAddress.isEmpty()) {
            editTextEmail1.setError("Please provide a email address!");
            editTextEmail1.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            editTextEmail1.setError("Please provide a valid email address!");
            editTextEmail1.requestFocus();
            return false;
        } else {
            editTextEmail1.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = textInputPassword1.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            textInputPassword1.setError("Password is empty!");
            textInputPassword1.requestFocus();
            return false;
        }
        if (password.length() <= 6) {
            textInputPassword1.setError("Password must be greater than 6 characters!");
            textInputPassword1.requestFocus();
            return false;

        } else {
            textInputPassword1.setError(null);
            return true;
        }
    }

    public void registerInput (View v) {
        if (!validateEmailAddress() | !validateName() | !validatePassword() | !validatePhoneNumber() | !validateStreetAddress() | !validateZipCode()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // register the user in firebase


        String fullName = editTextFullName1.getText().toString().trim();
        String email = editTextEmail1.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber1.getText().toString().trim();
        String streetAddress = editTextStreetAddress1.getText().toString().trim();
        String zipCode = editTextZipCode1.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("Users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("fullName", fullName);
                    user.put("streetAddress", streetAddress);
                    user.put("zipCode", zipCode);
                    user.put("phoneNumber", phoneNumber);
                    user.put("password", password);
                    user.put("email", email);
                    documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
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
        /*
                    UserProfile userProfile = new UserProfile(fullName, streetAddress, zipCode, phoneNumber, email, password);

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

         */
    }
}

