package com.app.margaritahousecleaning.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Addresses implements Parcelable {

    private String name, addressId;

    public Addresses() {
    }

    protected Addresses(Parcel in) {
        name = in.readString();
        addressId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(addressId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Addresses> CREATOR = new Creator<Addresses>() {
        @Override
        public Addresses createFromParcel(Parcel in) {
            return new Addresses(in);
        }

        @Override
        public Addresses[] newArray(int size) {
            return new Addresses[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
