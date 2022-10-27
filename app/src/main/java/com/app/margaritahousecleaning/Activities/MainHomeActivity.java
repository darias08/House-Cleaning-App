package com.app.margaritahousecleaning.Activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.UserProfile;
import com.app.margaritahousecleaning.common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shuhart.stepview.StepView;

public class MainHomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    CollectionReference userRef;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainHomeActivity.this, R.color.Dark_Grey));
        setContentView(R.layout.activity_main_home);

        mAuth = FirebaseAuth.getInstance();

        //Init
        userRef = FirebaseFirestore.getInstance().collection("User");

        if (getIntent() != null) {
            boolean isLogin = getIntent().getBooleanExtra(Common.IS_LOGIN, false);
            if (isLogin){
                //Check if user exists

                mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                        if (firebaseAuth != null) {

                            DocumentReference currentUser = userRef.document(firebaseAuth.getCurrentUser().getUid());

                            currentUser.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                    DocumentSnapshot userSnapShot = task.getResult();
                                                    if (!userSnapShot.exists()) {
                                                        showUpdateDialog(firebaseAuth.getCurrentUser().getUid());
                                                    }
                                                    else{
                                                        //If user already available in our system
                                                        Common.currentUser = userSnapShot.toObject(UserProfile.class);
                                                    }
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        }



    }

    private void showUpdateDialog(String uid) {
        //Init dialog

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }



}



