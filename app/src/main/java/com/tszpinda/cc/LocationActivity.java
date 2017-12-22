package com.tszpinda.cc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.databinding.DataBindingUtil;

import com.tszpinda.cc.databinding.ActivityLocationBinding;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class LocationActivity extends AppCompatActivity {

    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.err.println("onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.err.println("onResume");
        final Location currentLocation = new Location("Current Location");
        final ActivityLocationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        binding.setVariable(BR.currentLocation, currentLocation);
        binding.executePendingBindings();
        createLocationSubscription(currentLocation);
    }

    private void createLocationSubscription(final Location currentLocation) {
        checkLocationPermission();
        CCApp application = (CCApp)getApplication();
        LocationService locationService = application.getLocationService();
        Observable<com.tszpinda.cc.Location> updatedLocation = locationService.getLocationUpdates();
        Subscription sub = updatedLocation.subscribe(location -> {
           String name = location.getName();
           currentLocation.setName(name);
           System.err.println(name);
        });
        this.subscriptions.add(sub);
        System.err.println("create subscription:" + this.subscriptions.hasSubscriptions());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkLocationPermission() {
        if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            String[] requestedPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(requestedPermissions, 92831);
            return;
        }
        if (!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            String[] requestedPermissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(requestedPermissions, 92831);
            return;
        }
    }

    private boolean hasPermission(final String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 92831) {

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        cleanSubscriptions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanSubscriptions();
    }

    private void cleanSubscriptions() {
        if(!subscriptions.isUnsubscribed()) {
            subscriptions.clear();
            System.err.println("clean subscription:" + this.subscriptions.hasSubscriptions());
        }
    }

}
