package com.app.margaritahousecleaning.common;

import android.content.Intent;

import com.app.margaritahousecleaning.Model.Addresses;
import com.app.margaritahousecleaning.Model.Appointment;
import com.app.margaritahousecleaning.UserGoogleAccount;
import com.app.margaritahousecleaning.UserProfile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Common {
    public static final String IS_LOGIN = "IsLogin";
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_APPOINTMENT_STORE = "APPOINTMENT_SAVE";
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final String KEY_STEP = "STEP";
    public static final String KEY_LOCATION_SELECTED = "LOCATION_SELECTED";
    public static final int TIME_SLOT_TOTAL = 7;
    public static final String KEY_ADDRESS_LOAD_DONE = "ADDRESS_LOAD_DONE";
    public static final Object DISABLE_TAG = "DISABLE";
    public static final String KEY_TIME_SLOT = "TIME_SLOT";
    public static final String KEY_CONFIRM_BOOKING = "CONFIRM_BOOKING";
    public static final String KEY_SHOW_APPOINTMENT_INFO = "SHOW_APPOINTMENT_INFO";
    public static Appointment currentAppointment;
    public static int step = 0; //Init first step is 0
    public static String service = "";
    public static UserProfile currentUser;
    public static UserGoogleAccount currentGoogleUser;
    public static int currentTimeSlot = -1;
    public static Calendar bookingDate = Calendar.getInstance();
    public static SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("MM_dd_yyyy"); //Only use when need format key

    public static String convertTimeSlotToString(int slot) {

        switch (slot) {
            case 0:
                return "10:00 a.m. - 11:00 a.m.";

            case 1:
                return "11:00 a.m. - 12:00 p.m.";

            case 2:
                return "12:00 p.m. - 1:00 p.m.";

            case 3:
                return "1:00 p.m. - 2:00 p.m.";

            case 4:
                return "2:00 p.m. - 3:00 p.m.";

            case 5:
                return "3:00 p.m. - 4:00 p.m.";

            case 6:
                return "4:00 p.m. - 5:00 p.m.";

            default:
                return "Closed";
        }
    }
}
