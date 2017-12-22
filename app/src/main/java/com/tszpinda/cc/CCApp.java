package com.tszpinda.cc;

import android.app.Application;

public class CCApp extends Application {


    private LocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        locationService = new LocationService(getApplicationContext());
    }

    public LocationService getLocationService() {
        return locationService;
    }
}
