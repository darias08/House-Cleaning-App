package com.app.margaritahousecleaning;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://margarita-house-cleaning-default-rtdb.firebaseio.com/");
    private FirebaseAuth mAuth;
    private Button registerButton;


    Button button;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.redStatusBarColor));
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginUserActivity.class);
                startActivity(intent);
            }
        });
        

        button = findViewById(R.id.registerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //How to open a fragment from Activity
                Intent intent = new Intent(LoginActivity.this, RegisteredUserActivity.class);
                startActivity(intent);

            }
        });
    }
}

 /*
        How to create a bottomSheetDialog within an activity.

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetLoginFragment bottomSheetLoginFragment = new BottomSheetLoginFragment();
                bottomSheetLoginFragment.show(getSupportFragmentManager(), bottomSheetLoginFragment.getTag());
            }
        });

         */

/*

 Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(LoginActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_login, (LinearLayout) findViewById(R.id.bottomSheetContainer));
                bottomSheetView.findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        bottomSheetDialog.dismiss();

                        //Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                        //startActivity(intent);
                        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //finish();
                        //bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

 */