package com.app.margaritahousecleaning;

public class UserProfile {

    public String fullName, streetAddress, zipCode, phoneNumber, email, password;

    public UserProfile(String fullName, String streetAddress, String zipCode, String phoneNumber, String email, String password) {
        this.fullName = fullName;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    UserProfile() {

    }

}


