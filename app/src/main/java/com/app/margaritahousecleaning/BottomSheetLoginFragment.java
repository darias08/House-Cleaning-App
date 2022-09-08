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

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;


public class BottomSheetLoginFragment extends BottomSheetDialogFragment {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://margarita-house-cleaning-default-rtdb.firebaseio.com/");

    private EditText editTextEmail, editTextPassword;
    private TextView registerText;
    private Button signInBtn;
    private ProgressBar progressBar2;
    private TextView ForgotPasswordText;
    private int progressBarCounter = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        return v;
    }
}
        /*
        View v = inflater.inflate(R.layout.bottom_sheet_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        //User forgot their password.
        ForgotPasswordText = v.findViewById(R.id.ForgotPasswordText);
        ForgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ForgotPasswordActivity.class);
                getActivity().startActivity(intent);
            }
        });

        //User email and password
        editTextEmail = v.findViewById(R.id.UserEmailLogin);
        editTextPassword = v.findViewById(R.id.UserPasswordLogin);

        //Progressbar for when user has entered their information.
        progressBar2 = v.findViewById(R.id.progressBar2);

        //This is for user who don't have an account and want to register.
        registerText = v.findViewById(R.id.RegisterText);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), RegisteredUserActivity.class);
                startActivity(intent);
            }

        });

        signInBtn = v.findViewById(R.id.signin);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.signin:
                        userLogin();
                        break;
                }
            }

            //This is getting the user information and converting it back to String datatype.
            private void userLogin() {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    editTextEmail.setError("Email is required!");
                    editTextEmail.requestFocus();
                    return;
                }

                //This is for if user has provided a correct email address or domain name.
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Please enter a valid email!");
                    editTextEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password is required!");
                    editTextPassword.requestFocus();
                    return;
                }

                if (password.length() <= 6) {
                    editTextPassword.setError("Please enter more than 6 characters.");
                    editTextPassword.requestFocus();
                    return;
                }

                progressBar2.setVisibility(View.VISIBLE);
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {

                        progressBarCounter++;
                        progressBar2.setProgress(progressBarCounter);

                        if (progressBarCounter == 10) {
                            timer.cancel();

                            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        if (user.isEmailVerified()) {

                                            startActivity(new Intent(getActivity().getApplicationContext(), MainHomeActivity.class));

                                        }
                                        else {
                                            user.sendEmailVerification();
                                            Toast.makeText(getActivity(), "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        Toast.makeText(getActivity(), "Incorrect email or password!", Toast.LENGTH_LONG).show();
                                        progressBar2.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    }
                };
                timer.schedule(timerTask, 100, 100);
            }
        });
        return v;


    }
}
 */
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


        Using Database to store user information except email.


public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.bottom_sheet_login, container, false);

        final EditText email = v.findViewById(R.id.UserEmailLogin);
        final EditText password = v.findViewById(R.id.UserPasswordLogin);
        final Button loginBtn = v.findViewById(R.id.signin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your mobile or password", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Check if mobile/phone is stored in firebase.
                            if(snapshot.hasChild(emailTxt)) {


                                // mobile exist in firebase

                                final String getPassword = snapshot.child(emailTxt).child("password").getValue(String.class);

                                if(getPassword.equals(emailTxt)) {
                                    Toast.makeText(getActivity(), "Successfully logged in", Toast.LENGTH_SHORT).show();

                                    //User logged into account.
                                    getActivity().startActivity(new Intent(getActivity(), MainHomeActivity.class));
                                    getActivity().finish();
                                }
                                else {
                                    Toast.makeText(getActivity(), "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getActivity(), "Wrong mobile or phone number", Toast.LENGTH_SHORT).show();
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