package com.app.margaritahousecleaning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SettingsLocationTab extends Fragment {

    private TextView tv_lat, tv_lon, tv_altitude;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.user_location_settings, container, false);

        return v;

    }

}
