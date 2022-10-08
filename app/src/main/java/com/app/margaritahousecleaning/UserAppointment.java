package com.app.margaritahousecleaning;


public class UserAppointment {

    public String datePicked, timePicked, phoneNumber, address, zipcode, userMessage, residentialCheckBox, officeCheckBox, bathroomCheckBox, carpetCheckBox;


    //Constructor
    public UserAppointment(String date_picked, String time_picked, String addressTV, String zipcodeTV, String phoneNumber, String userMessage) {
        this.datePicked = date_picked;
        this.timePicked = time_picked;
        this.address = addressTV;
        this.zipcode = zipcodeTV;
        this.phoneNumber = phoneNumber;
        this.userMessage = userMessage;
    }

    UserAppointment () {

    }

    //Setters method
    public void setResidentialCheckBox(String residentialCheckBox) {
        this.residentialCheckBox = residentialCheckBox;
    }

    public void setOfficeCheckBox(String officeCheckBox) {
        this.officeCheckBox = officeCheckBox;
    }


    public void setBathroomCheckBox(String bathroomCheckBox) {
        this.bathroomCheckBox = bathroomCheckBox;
    }

    public void setCarpetCheckBox(String carpetCheckBox) {
        this.carpetCheckBox = carpetCheckBox;
    }
}
