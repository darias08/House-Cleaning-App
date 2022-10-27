package com.app.margaritahousecleaning.Model;

public class BookedAppointmentInfo {

    String streetAddress, zipCode, serviceType;

    public BookedAppointmentInfo() {

    }

    public BookedAppointmentInfo(String streetAddress, String zipCode, String serviceType) {
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.serviceType = serviceType;
    }


    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
