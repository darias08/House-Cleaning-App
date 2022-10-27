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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.app.margaritahousecleaning.Activities.MainHomeActivity;
import com.app.margaritahousecleaning.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

public class ServicesFragment_miscTab extends Fragment {

    private ImageSlider imageSlider;
    private BottomNavigationItemView bottomNavigationItemView;
    private ImageView img1,img2, img3, img4, img5;
    private Toolbar toolbar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_misc, container, false);




        //Invoking resources from .xml
        img1 = v.findViewById(R.id.misc_img1);
        img2 = v.findViewById(R.id.misc_img2);
        img3 = v.findViewById(R.id.misc_img3);
        img4 = v.findViewById(R.id.misc_img4);
        img5 = v.findViewById(R.id.misc_img5);


        //Image onClick
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


        toolbar = v.findViewById(R.id.toolbar_cleaning_services);
        ((MainHomeActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_servicesFragment_miscTab2_to_settingsFragment);
            }
        });


        setHasOptionsMenu(true);
        return v;

    }

    private void dialogImage1() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.misc_image1);
        getActivity().getWindow().setBackgroundDrawableResource(R.color.DarkBackground);
        dialog.show();

    }

    private void dialogImage2() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.misc_image2);
        getActivity().getWindow().setBackgroundDrawableResource(R.color.DarkBackground);
        dialog.show();
    }

    private void dialogImage3() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.misc_image3);
        getActivity().getWindow().setBackgroundDrawableResource(R.color.DarkBackground);
        dialog.show();
    }

    private void dialogImage4() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.misc_image4);
        getActivity().getWindow().setBackgroundDrawableResource(R.color.DarkBackground);
        dialog.show();
    }

    private void dialogImage5() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.misc_image5);
        getActivity().getWindow().setBackgroundDrawableResource(R.color.DarkBackground);
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
            case R.id.action_hc:
                Navigation.findNavController(getView()).navigate(R.id.action_servicesFragment_miscTab2_to_servicesFragment_residentialTab);
                break;

            case R.id.action_cc:
                Navigation.findNavController(getView()).navigate(R.id.action_servicesFragment_miscTab2_to_servicesFragment_commercialTab2);
        }

        return super.onOptionsItemSelected(item);
    }

}