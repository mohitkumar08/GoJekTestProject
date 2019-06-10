package com.bit.gojektestproject.view.splash;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bit.gojektestproject.BaseActivity;
import com.bit.gojektestproject.R;
import com.bit.gojektestproject.view.weather.WeatherActivity;
import com.patloew.rxlocation.RxLocation;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements LifecycleObserver  {

    private boolean showSnackBar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onRequestPermissionDenied() {
        final Snackbar snackbar = Snackbar
                .make(findViewById(R.id.main_coordinatorLayout), getString(R.string.location_setting), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.setting), view -> {
                    openAppSetting();
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    protected Activity getActivityInstance() {
        return this;
    }

    @Override
    protected void receivedLastLocation(final Location location) {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @OnLifecycleEvent(Event.ON_START)
    void onActivityResume() {
        gettingLocationOfUser1();
        //requestForLocationPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
        showSnackBar = false;
    }

    @Override
    protected void locationDetectStatus(final Boolean status) {
        super.locationDetectStatus(status);
        Log.e("locationDetectStatus","locationDetectStatus"+status);
        if (status){
            gettingLocationOfUser();
        }
    }

    @SuppressLint("MissingPermission")
    protected void gettingLocationOfUser1() {
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
}
