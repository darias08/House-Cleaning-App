package com.app.margaritahousecleaning;

import android.content.Intent;
import android.view.View;

public class User {

    public String fullName, streetAddress, zipCode, phoneNumber, email, password;

    public User(String fullName, String streetAddress, String zipCode, String phoneNumber, String email, String password) {
        this.fullName = fullName;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public User() {

    }




}
