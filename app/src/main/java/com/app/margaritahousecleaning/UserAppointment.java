package com.app.margaritahousecleaning;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class UserAppointment {

    public String date_picked, time_picked, address, zipcode, residentialCheckBox, officeCheckBox;

    //Constructor
    public UserAppointment(String dateET, String timeET, String addressTV, String zipcodeTV) {
        this.date_picked = dateET;
        this.time_picked = timeET;
        this.address = addressTV;
        this.zipcode = zipcodeTV;
    }

    //Using getter/setters method
    public String getResidentialCheckBox() {
        return residentialCheckBox;
    }

    public void setResidentialCheckBox(String residentialCheckBox) {
        this.residentialCheckBox = residentialCheckBox;
    }

    public String getOfficeCheckBox() {
        return officeCheckBox;
    }

    public void setOfficeCheckBox(String officeCheckBox) {
        this.officeCheckBox = officeCheckBox;
    }
}
