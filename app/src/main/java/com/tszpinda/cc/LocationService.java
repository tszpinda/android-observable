package com.tszpinda.cc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;

class LocationService {

    private Observable<android.location.Location> locationUpdates;

    LocationService(Context context) {
        init(context);
    }

    @SuppressLint("MissingPermission")
    void init(Context context) {
        LocationRequest request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                //.setNumUpdates(5)
                .setInterval(60000);

        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(context);

        locationUpdates = locationProvider.getUpdatedLocation(request);
    }

    public Observable<com.tszpinda.cc.Location> getLocationUpdates() {
        return locationUpdates.map(l -> {
            String name = "" + l.getLatitude() + ", " + l.getLongitude() + ": " + System.currentTimeMillis();
            return new com.tszpinda.cc.Location(name);
        });
    }
}
