package com.app.margaritahousecleaning.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.margaritahousecleaning.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Checking_Bug";
    Button registerButton;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.Dark_Grey));
        setContentView(R.layout.activity_login);


        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, LoginUserActivity.class);
                startActivity(intent);
                Log.d(TAG, "onClick: Check_bug");


            }
        });


        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
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