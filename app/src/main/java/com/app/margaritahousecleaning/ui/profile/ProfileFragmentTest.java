package com.app.margaritahousecleaning.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.app.margaritahousecleaning.MainActivity;
import com.app.margaritahousecleaning.R;
import com.app.margaritahousecleaning.UserProfile;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


public class ProfileFragmentTest extends Fragment {


    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String userID;
    private ImageView right_arrow_name, right_arrow_address, right_arrow_zipcode, right_arrow_phone;
    private FirebaseStorage storage;
    private FirebaseAuth fAuth;
    private StorageReference storageReference;
    private FirebaseFirestore mStore;
    private Uri imageUri;
    ImageView profileImage;
    ImageButton plusBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        fAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).load(uri).into(profileImage);
            }
        });

        //storage = FirebaseStorage.getInstance();
        //storageReference = storage.getReference();

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar_profile);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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


        //Retrieving user's information from database.
        databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");

        TextView userFullName = (TextView) v.findViewById(R.id.userFullNameTxt);
        TextView userPhoneNumber = (TextView) v.findViewById(R.id.userPhoneNumberTxt);
        TextView userStreetAddress = (TextView) v.findViewById(R.id.userStreetAddressTxt);
        TextView userZipCode = (TextView) v.findViewById(R.id.userZipCodeTxt);

        //retrieving user data from firebase.
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);

                if (userProfile != null) {
                    String fullName = userProfile.fullName;
                    String streetAddress = userProfile.streetAddress;
                    String phoneNumber = userProfile.phoneNumber;
                    String zipCode = userProfile.zipCode;


                    //TextView
                    userFullName.setText(fullName);
                    userPhoneNumber.setText(phoneNumber);
                    userStreetAddress.setText(streetAddress);
                    userZipCode.setText(zipCode);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        right_arrow_name = v.findViewById(R.id.right_arrow_name);
        right_arrow_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_profileEditName);
            }
        });
        right_arrow_address = v.findViewById(R.id.right_arrow_address);
        right_arrow_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileEditStreetAddress_to_profileFragment);
            }
        });
        right_arrow_phone = v.findViewById(R.id.right_arrow_phone);
        right_arrow_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_profileEditPhoneNumber2);
            }
        });
        right_arrow_zipcode = v.findViewById(R.id.right_arrow_zipcode);
        right_arrow_zipcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_profileEditZipCode2);
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

            //profileImage.setImageURI(imageUri);

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
                        snackbar.setBackgroundTint(ContextCompat.getColor(getActivity(),R.color.redStatusBarColor));
                        snackbar.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                        snackbar.show();
                        //Snackbar.make(getActivity().findViewById(android.R.id.content), "Your profile picture has been set!", Snackbar.LENGTH_LONG).show();

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
