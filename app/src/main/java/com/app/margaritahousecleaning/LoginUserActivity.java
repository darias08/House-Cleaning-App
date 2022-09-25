package com.app.margaritahousecleaning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class LoginUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://margarita-house-cleaning-default-rtdb.firebaseio.com/");
    private EditText editTextEmail, editTextPassword;
    private TextView registerText;
    private Button signInBtn, button;
    private ProgressBar progressBar2;
    private TextView ForgotPasswordText;
    private int progressBarCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginUserActivity.this, R.color.redStatusBarColor));


        mAuth = FirebaseAuth.getInstance();

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
                                startActivity(new Intent(LoginUserActivity.this.getApplicationContext(), MainHomeActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                progressBar2.setVisibility(View.VISIBLE);
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
        });

    }
    public void isUser() {

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