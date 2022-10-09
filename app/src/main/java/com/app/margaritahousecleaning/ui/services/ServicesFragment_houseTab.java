package com.app.margaritahousecleaning.ui.services;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.app.margaritahousecleaning.MainActivity;
import com.app.margaritahousecleaning.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

public class ServicesFragment_houseTab extends Fragment {

    private ImageSlider imageSlider;
    private BottomNavigationItemView bottomNavigationItemView;
    private ImageView img1,img2, img3, img4, img5;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_services, container, false);

        //Invoking resources from .xml
        img1 = v.findViewById(R.id.houseImg1);
        img2 = v.findViewById(R.id.houseImg2);
        img3 = v.findViewById(R.id.houseImg3);
        img4 = v.findViewById(R.id.houseImg4);
        img5 = v.findViewById(R.id.houseImg5);


        //Displaying title for toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Services");

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(R.id.homeFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_servicesFragment_to_nav_home);
            }
        });

        //Clicking on photos
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImage1();
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImage2();
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImage3();
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImage4();
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImage5();
            }
        });

        //Lock navigation drawer
        ((MainActivity)getActivity()).setDrawer_Locked();

        setHasOptionsMenu(true);
        return v;
    }


    public void dialogImage1() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.house_image1);
        dialog.getWindow().setBackgroundDrawableResource(R.color.DarkBackground);
        dialog.show();
    }

    public void dialogImage2() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.house_image2);
        dialog.getWindow().setBackgroundDrawableResource(R.color.DarkBackground);
        dialog.show();
    }

    public void dialogImage3() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.house_image3);
        dialog.getWindow().setBackgroundDrawableResource(R.color.DarkBackground);
        dialog.show();
    }

    public void dialogImage4() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.house_image4);
        dialog.getWindow().setBackgroundDrawableResource(R.color.DarkBackground);
        dialog.show();
    }

    public void dialogImage5() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.house_image5);
        dialog.getWindow().setBackgroundDrawableResource(R.color.DarkBackground);
        dialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.nav_service_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cc:
                Navigation.findNavController(getView()).navigate(R.id.action_servicesFragment_to_servicesFragment_commercialTab);
                break;
            case R.id.action_mc:
                Navigation.findNavController(getView()).navigate(R.id.action_servicesFragment_to_servicesFragment_miscTab);
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }




    /*
    =========How to hide icon back button =========
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);

    }

    ===Hiding action bar when user clicks on schedule and returns action bar when they return back to previous fragment =========
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

     */
}