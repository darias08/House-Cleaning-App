package com.app.margaritahousecleaning.Model;

import com.google.firebase.Timestamp;

public class BookingInformation {
    private String appointmentId, time, customerName, customerStreetAddress, customerZipCode;
    private Long slot;
    private com.google.firebase.Timestamp timestamp;
    private boolean done;

    public BookingInformation() {
    }

    public BookingInformation(String appointmentId, String time, String customerName, String customerStreetAddress, String customerZipCode, Long slot, Timestamp timestamp, boolean done) {
        this.appointmentId = appointmentId;
        this.time = time;
        this.customerName = customerName;
        this.customerStreetAddress = customerStreetAddress;
        this.customerZipCode = customerZipCode;
        this.slot = slot;
        this.timestamp = timestamp;
        this.done = done;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerStreetAddress() {
        return customerStreetAddress;
    }

    public void setCustomerStreetAddress(String customerStreetAddress) {
        this.customerStreetAddress = customerStreetAddress;
    }

    public String getCustomerZipCode() {
        return customerZipCode;
    }

    public void setCustomerZipCode(String customerZipCode) {
        this.customerZipCode = customerZipCode;
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


}
