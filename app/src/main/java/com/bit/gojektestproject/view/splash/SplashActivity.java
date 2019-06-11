package com.bit.gojektestproject.view.splash;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.bit.gojektestproject.BaseActivity;
import com.bit.gojektestproject.R;
import com.bit.gojektestproject.util.Constants;

import com.bit.gojektestproject.view.weather.WeatherActivity;
import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import java.util.concurrent.TimeUnit;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements LifecycleObserver {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private View progressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressView = findViewById(R.id.ll_progress_view);
        getSupportActionBar().hide();
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


    @OnLifecycleEvent(Event.ON_START)
    void onActivityResume() {
        requestForLocationPermission();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    protected void locationDetectStatus(final Boolean status) {
        super.locationDetectStatus(status);
        if (status) {
            gettingLocationOfUser1();
        }
    }

    @SuppressLint("MissingPermission")
    protected void gettingLocationOfUser1() {
        progressView.setVisibility(View.VISIBLE);
        RxLocation rxLocation = new RxLocation(getApplication());
        rxLocation.setDefaultTimeout(5, TimeUnit.SECONDS);
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000);


        rxLocation.location().lastLocation().subscribeOn(Schedulers.io()).subscribe(new MaybeObserver<Location>() {
            Location location;

            @Override
            public void onSubscribe(final Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(final Location location) {
                this.location = location;
                receivedLastLocation(location);
            }

            @Override
            public void onError(final Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                if (location == null) {
                    compositeDisposable.add(rxLocation.location().updates(locationRequest).subscribe(new Consumer<Location>() {
                        @Override
                        public void accept(final Location location) throws Exception {
                            receivedLastLocation(location);
                        }
                    }));
                }
            }
        });
    }

    @Override
    protected void receivedLastLocation(final Location location) {
        super.receivedLastLocation(location);
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra(Constants.LOCATION_OBJ, location);
        startActivity(intent);
        SplashActivity.this.finish();
    }


}
