package com.app.margaritahousecleaning.Fragments;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.nav_notification;
import static com.app.margaritahousecleaning.R.id.nav_settings;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.app.margaritahousecleaning.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ImageSlider imageSlider;
    private BottomNavigationItemView bottomNavigationItemView;
    private BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //Banner
        imageSlider = v.findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://jacklauriegroup.com/wp-content/uploads/2021/01/5-Signs-Its-Time-to-Fire-Your-Office-Cleaning-Company.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://content.thriveglobal.com/wp-content/uploads/2018/05/Best_Kitchen.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nebula.wsimg.com/485177c04228ee0f828467c9d4287de6?AccessKeyId=531592D248B589D87A56&disposition=0&alloworigin=1", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://taskmoby.com/wp-content/uploads/2022/01/house-cleaning.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.rd.com/wp-content/uploads/2022/02/GettyImages-1216606844.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nebula.wsimg.com/f07775ff757d7b8786b378ad7d4c69f3?AccessKeyId=531592D248B589D87A56&disposition=0&alloworigin=1", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);



        //Navigating to schedule fragment.
        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(R.id.nav_schedule);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_scheduleUserFragment2);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_notification);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_notificationUpcomingFragment);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(nav_settings);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_settingsFragment);
            }
        });

        return v;
    }

}