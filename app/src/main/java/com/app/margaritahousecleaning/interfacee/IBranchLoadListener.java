package com.app.margaritahousecleaning.interfacee;

import com.app.margaritahousecleaning.Model.Appointment;

import java.util.List;

public interface IBranchLoadListener {
    void onBranchLoadSuccess(List<Appointment> appointmentList);
    void onBranchLoadFailed(String message);
}
