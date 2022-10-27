package com.app.margaritahousecleaning.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.margaritahousecleaning.Fragments.BookAppointmentFragment;
import com.app.margaritahousecleaning.Fragments.BookingStep1Fragment;
import com.app.margaritahousecleaning.Fragments.BookingStep2Fragment;
import com.app.margaritahousecleaning.Fragments.BookingStep3Fragment;
import com.app.margaritahousecleaning.Fragments.BookingStep4Fragment;

import java.util.HashMap;
import java.util.Map;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private Map<Integer, String> mFragmentTags;
    private FragmentManager mFragmentManager;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();

    }


    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return BookingStep1Fragment.getInstance();

            case 1:
                return BookingStep2Fragment.getInstance();

            case 2:
                return BookingStep3Fragment.getInstance();

            case 3:
              return new BookingStep4Fragment();


        }

        return null;
    }




    @Override
    public int getCount() {
        return 4;
    }
}
