package com.bit.gojektestproject;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bit.gojektestproject.view.splash.SplashActivity;
import com.bit.gojektestproject.view.weather.WeatherActivity;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.patloew.rxlocation.RxLocation;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

abstract public class BaseActivity extends AppCompatActivity implements ResultCallback<LocationSettingsResult> {

    private Activity activity;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    protected Boolean isLocationDialogVisible = true;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
    }

    protected abstract int getContentView();

    protected void onRequestPermissionDenied() {
    }

    protected abstract Activity getActivityInstance();

    protected void receivedLastLocation(Location location) {
    }

    protected void locationDetectStatus(Boolean status) {
    }


    protected void requestForLocationPermission() {
        Dexter.withActivity(this)
                .withPermission(permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        checkLocationSettings();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        onRequestPermissionDenied();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(error -> {
        }).check();
    }


    protected void checkLocationSettings() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(final LocationSettingsResponse locationSettingsResponse) {
                locationDetectStatus(locationSettingsResponse.getLocationSettingsStates().isLocationPresent());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(BaseActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                    }
                }
            }
        });

    }

    @SuppressLint("MissingPermission")
    protected void gettingLocationOfUser() {
        RxLocation rxLocation = new RxLocation(getApplication());
        rxLocation.location().lastLocation().subscribeOn(Schedulers.io()).subscribe(new MaybeObserver<Location>() {
            @Override
            public void onSubscribe(final Disposable d) {

            }

            @Override
            public void onSuccess(final Location location) {
                Log.e("loca",location.toString());
                receivedLastLocation(location);
            }

            @Override
            public void onError(final Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
Log.e("comple","complete");
            }
        });
    }

    protected void openAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getApplication().getPackageName(), null);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.e("TAG", "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.e("TAG", "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        locationDetectStatus(true);
                        break;
                    case Activity.RESULT_CANCELED:
                        locationDetectStatus(false);
                        break;
                }
                break;
        }
    }


}
