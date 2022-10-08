package com.app.margaritahousecleaning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://margarita-house-cleaning-c3bd7-default-rtdb.firebaseio.com/");
    private EditText editTextEmail, editTextPassword;
    private TextView registerText;
    private Button signInBtn, googleAccountBtn;
    private ProgressBar progressBar2;
    private TextView ForgotPasswordText;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    private static final String TAG = "GOOOGLE_SIGN_IN_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginUserActivity.this, R.color.redStatusBarColor));


        //configure the Google Signin
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        //initiate firebase auth
        mAuth = FirebaseAuth.getInstance();

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
        editTextEmail = findViewById(R.id.edit_text_emailLogin);
        editTextPassword = findViewById(R.id.edit_text_passwordLogin);

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

        signInBtn = findViewById(R.id.signin);
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


                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()) {
                                startActivity(new Intent(LoginUserActivity.this.getApplicationContext(), MainActivity.class));
                                Toast.makeText(getApplicationContext(), "You have successfully logged in!", Toast.LENGTH_SHORT).show();
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                progressBar2.setVisibility(View.VISIBLE);
                            } else {
                                user.sendEmailVerification();
                                Toast.makeText(LoginUserActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(LoginUserActivity.this, "Incorrect email or password!", Toast.LENGTH_LONG).show();
                            progressBar2.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

    }

    /*
    //if user is already signed in then go to home activity
    private void checkUser() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            Log.d(TAG, "checkUser: Already signed in.");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
    */



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
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();


                        Log.d(TAG, "onSuccess: Email: " +email);
                        Log.d(TAG, "onSuccess: UID: " + uid);

                        //Check if user is new or existing
                        if (authResult.getAdditionalUserInfo().isNewUser()) {
                            //user is new = Account created
                            Log.d(TAG, "onSuccess: Account Created...\n" + email);
                        }
                        else {
                            //existing user - logged in
                            Log.d(TAG, "onSuccess: Existing user...\n" + email);
                        }

                        //start home activity
                        startActivity(new Intent(LoginUserActivity.this.getApplicationContext(), MainActivity.class));
                        Toast.makeText(getApplicationContext(), "You have successfully logged in!", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        progressBar2.setVisibility(View.VISIBLE);
                        finish();
                        
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: log in failed"+e.getMessage());
                    }
                });

    }

}


/*

   progressBar2.setVisibility(View.VISIBLE);
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {


                        progressBarCounter++;
                        progressBar2.setProgress(progressBarCounter);

                        if (progressBarCounter == 30) {
                            timer.cancel();

                            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        if (user.isEmailVerified()) {

                                            startActivity(new Intent(LoginUserActivity.this.getApplicationContext(), MainHomeActivity.class));
                                            progressBar2.setVisibility(View.GONE);
                                        }

                                        else {
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
                    }
                };
                timer.schedule(timerTask, 30, 30);

            }
        });

    }
}
 */