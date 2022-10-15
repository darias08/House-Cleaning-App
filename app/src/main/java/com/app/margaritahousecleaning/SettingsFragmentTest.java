package com.app.margaritahousecleaning;

import static android.app.Activity.RESULT_OK;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.nav_home;
import static com.app.margaritahousecleaning.R.id.nav_notification;
import static com.app.margaritahousecleaning.R.id.nav_schedule;
import static com.app.margaritahousecleaning.R.id.nav_settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.app.margaritahousecleaning.ui.profile.ProfileFragment;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class SettingsFragmentTest extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String userID;
    private ImageView locationArrow, cleaningArrow, aboutArrow, right_arrow_phone;
    private FirebaseStorage storage;
    private FirebaseAuth fAuth;
    private StorageReference storageReference;
    private FirebaseFirestore mStore;
    private Uri imageUri;
    ImageView profileImage;
    ImageButton plusBtn;
    Button editProfileBtn;

    private TextView personName;
    BottomNavigationView bottomNavigationView;
    private BottomNavigationItemView bottomNavigationItemView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings_test, container, false);
        getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));


        fAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).load(uri).into(profileImage);
            }
        });



        personName = v.findViewById(R.id.userNameText);

        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        //Displaying person name with google account
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserGoogleAccount userGoogleAccount = snapshot.getValue(UserGoogleAccount.class);

                if (userGoogleAccount != null) {
                    String name = userGoogleAccount.fullName;
                    personName.setText(name);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Displaying person name with regular email account
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);

                if (userProfile != null) {
                    String name = userProfile.fullName;
                    personName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editProfileBtn = v.findViewById(R.id.editProfile);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_profileFragment);
            }
        });

        cleaningArrow = v.findViewById(R.id.cleaningArrow);
        cleaningArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_servicesFragment_residentialTab);
            }
        });

        locationArrow = v.findViewById(R.id.officeArrow);
        locationArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_locationFragment);
            }
        });

        aboutArrow = v.findViewById(R.id.aboutArrow);
        aboutArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_settingsFragment_to_aboutPageFragment);
            }
        });


        profileImage = v.findViewById(R.id.profile_image);
        plusBtn = v.findViewById(R.id.fab_add);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfilePic();
            }
        });

        BottomNavigationView navigationView = (BottomNavigationView) v.findViewById(bottom_navigation);
        navigationView.getMenu().findItem(nav_settings).setChecked(true);

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_home);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_homeFragment);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_schedule);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_scheduleUserFragment2);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_notification);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_notificationUpcomingFragment);
            }
        });



        setHasOptionsMenu(true);

        return v;
    }




    private void chooseProfilePic() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == 1  && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            imageUri = data.getData();

            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        pd.setTitle("Uploading Image...");
        pd.show();
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);


        StorageReference ref = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Snackbar snackbar = Snackbar.make(getView(), "Your profile picture has been set!", Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(ContextCompat.getColor(getActivity(),R.color.Shadow_gray));
                        snackbar.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                        snackbar.show();

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getActivity()).load(uri).into(profileImage);
                            }
                        });


                        pd.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), "Failed to upload.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });

    }
}
