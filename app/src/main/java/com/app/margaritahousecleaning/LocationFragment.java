package com.app.margaritahousecleaning;

import static com.app.margaritahousecleaning.R.id.bottom_navigation;
import static com.app.margaritahousecleaning.R.id.homeFragment;
import static com.app.margaritahousecleaning.R.id.scheduleFragment;
import static com.app.margaritahousecleaning.R.id.settingsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class LocationFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    BottomNavigationItemView bottomNavigationItemView;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng California = new LatLng(37.74266, -121.43499);
            googleMap.addMarker(new MarkerOptions().position(California).title("Margarita's House Cleaning Office"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(California, 15));
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_location, container, false);


        bottomNavigationView = (BottomNavigationView)v.findViewById(bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.locationFragment).setChecked(true);

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(scheduleFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_locationFragment_to_scheduleUserFragment22);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(homeFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_locationFragment_to_homeFragment);
            }
        });

        bottomNavigationItemView = (BottomNavigationItemView) v.findViewById(settingsFragment);
        bottomNavigationItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_locationFragment_to_settingsFragment);
            }
        });


        return v;
    }
}