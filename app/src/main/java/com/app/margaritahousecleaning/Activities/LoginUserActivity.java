package com.app.margaritahousecleaning.Activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.UserGoogleAccount;
import com.app.margaritahousecleaning.common.Common;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://margarita-house-cleaning-c3bd7-default-rtdb.firebaseio.com/");
    private TextView registerText;
    private Button  googleAccountBtn;
    private ProgressBar progressBar2;
    private TextView ForgotPasswordText;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    private TextInputLayout emailInput, passwordInput;
    private static final String TAG = "GOOOGLE_SIGN_IN_TAG";
    private FirebaseFirestore fStore;
    private CollectionReference userRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginUserActivity.this, R.color.Dark_Grey));

        //configure the Google Signin
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        //initiate firebase auth
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //checkUser();

        //Google SignInButton: Click to begin Google SignIn
        googleAccountBtn = findViewById(R.id.googleAccountBtn);
        googleAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //begin google sign in
                Log.d(TAG, "onClick: begin Google SignIn");
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        //User forgot their password.
        ForgotPasswordText = findViewById(R.id.forgotPasswordText);
        ForgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginUserActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //User email and password
        emailInput = findViewById(R.id.emailFieldLogin1);
        passwordInput = findViewById(R.id.passwordFieldLogin);


        //Progressbar for when user has entered their information.
        progressBar2 = findViewById(R.id.progressBar);

        //This is for user who don't have an account and want to register.
        registerText = findViewById(R.id.registerTextAccount);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginUserActivity.this.getApplicationContext(), RegisteredUserActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }
    /*
     mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user.isEmailVerified()) {
                            startActivity(new Intent(LoginUserActivity.this.getApplicationContext(), MainHomeActivity.class));
                            Toast.makeText(getApplicationContext(), "You have successfully logged in!", Toast.LENGTH_SHORT).show();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            progressBar2.setVisibility(View.VISIBLE);
                        } else {
                            user.sendEmailVerification();
                            Toast.makeText(LoginUserActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginUserActivity.this, "Incorrect email or password!", Toast.LENGTH_LONG).show();
                        progressBar2.setVisibility(View.GONE);
                    }
                }
            });
     */

    private boolean validateEmailAddress() {
        String emailAddress = emailInput.getEditText().getText().toString().trim();

        if (emailAddress.isEmpty()) {
            emailInput.setError("Please provide a email address!");
            emailInput.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            emailInput.setError("Please provide a valid email address!");
            emailInput.requestFocus();
            return false;
        } else {
            emailInput.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = passwordInput.getEditText().getText().toString();

        if (password.isEmpty()) {
            passwordInput.setError("Please provide a password!");
            return false;
        }
        else {
            passwordInput.setError(null);
            return true;
        }
    }

    public void signInInput(View v) {
        if (!validateEmailAddress() | !validatePassword()) {
            return;
        }

        progressBar2 = findViewById(R.id.progressBar);

        String email = emailInput.getEditText().getText().toString().trim();
        String password = passwordInput.getEditText().getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        Intent intent = new Intent(LoginUserActivity.this.getApplicationContext(), MainHomeActivity.class);
                        intent.putExtra(Common.IS_LOGIN, false);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "You have successfully logged in!", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        progressBar2.setVisibility(View.VISIBLE);
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(LoginUserActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginUserActivity.this, "Incorrect email or password!", Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.GONE);
                }
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Log.d(TAG, "onActivityResult: Google Signin intent result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                //Succesfully signed into google account.
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);

            }
            catch (Exception e){
                //failed to sign into google
                Log.d(TAG, "onActivityResult: "+ "Error has occurred.");
            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with google account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login successful
                        Log.d(TAG, "onSuccess: Logged In");

                        //get Logged in user
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();

                        //get user info
                        String email = firebaseUser.getEmail();
                        String fullName = firebaseUser.getDisplayName();

                        userRef = FirebaseFirestore.getInstance().collection("Users");
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("Users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("fullName", fullName);
                        user.put("email", email);

                        documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(LoginUserActivity.this.getApplicationContext(), MainHomeActivity.class);
                                intent.putExtra(Common.IS_LOGIN, false);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "You have successfully logged in!", Toast.LENGTH_SHORT).show();
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                progressBar2.setVisibility(View.VISIBLE);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: log in failed" + e.getMessage());
                            }
                        });
                    }
                });
    }
}
