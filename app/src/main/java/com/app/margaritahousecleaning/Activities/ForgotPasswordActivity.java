package com.app.margaritahousecleaning.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.margaritahousecleaning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    public ImageView BackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setStatusBarColor(ContextCompat.getColor(ForgotPasswordActivity.this, R.color.Dark_Grey));

        emailEditText = (EditText) findViewById(R.id.resetEmailText);
        resetPassword = (Button) findViewById(R.id.ResetPasswordBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        //TextWatcher is used for to check if all the requirements are filled it will enable the button to be active.
        emailEditText.addTextChangedListener(textWatcher);

        auth = FirebaseAuth.getInstance();

        BackArrow = findViewById(R.id.BackArrowBtn);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginUserActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }

            private void resetPassword() {
                String email = emailEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    emailEditText.setError("email is required!");
                    emailEditText.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Please enter a valid email!");
                    emailEditText.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()) {
                         Toast.makeText(ForgotPasswordActivity.this, "Reset password submitted. Check your spam/junk if you don't see it.", Toast.LENGTH_LONG).show();
                     }
                     else {
                         Toast.makeText(ForgotPasswordActivity.this, "There is no account associated with this email.", Toast.LENGTH_LONG).show();
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
            String userEmail = emailEditText.getText().toString().trim();
            resetPassword.setEnabled(!userEmail.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });

}