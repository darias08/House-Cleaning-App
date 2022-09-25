package com.app.margaritahousecleaning;

import android.content.Intent;
import android.view.View;

public class UserProfile {

    public String firstName, lastName, streetAddress, zipCode, phoneNumber, email, password;

    public UserProfile(String firstName, String lastName, String streetAddress, String zipCode, String phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    UserProfile() {

    }

}



