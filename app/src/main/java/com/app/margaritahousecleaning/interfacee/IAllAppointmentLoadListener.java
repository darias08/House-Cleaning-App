package com.app.margaritahousecleaning.interfacee;

import java.util.List;

public interface IAllAppointmentLoadListener {

    void onAllAppointmentLoadSuccess(List<String> areaNameList);
    void onAllAppointmentLoadFailed(String message);

}
