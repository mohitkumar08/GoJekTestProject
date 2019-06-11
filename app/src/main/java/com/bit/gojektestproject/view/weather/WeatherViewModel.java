package com.bit.gojektestproject.view.weather;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.support.annotation.NonNull;

import com.bit.gojektestproject.BuildConfig;
import com.bit.gojektestproject.data.server.NetworkClient;
import com.bit.gojektestproject.data.server.model.WeatherResponse;
import com.bit.gojektestproject.data.server.service.WeatherServiceApi;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<WeatherResponse> weatherLiveData = new MutableLiveData();
    private MutableLiveData<Throwable> errorLiveData = new MutableLiveData();

    private CompositeDisposable compositeDisposable;
    private Application application;
    private static final int DAYS = 4;
    private WeatherServiceApi weatherServiceApi;


    public WeatherViewModel(@NonNull final Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
        weatherServiceApi = new NetworkClient().getRetrofitClientInstance(BuildConfig.HOST_URL).create(WeatherServiceApi.class);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

    public void getWeatherDetail(Location location) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(location.getLatitude()).append(",").append(location.getLongitude());
        weatherServiceApi.getWeather(BuildConfig.API_KEY, queryBuilder.toString(), DAYS).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<WeatherResponse>() {
            @Override
            public void onSubscribe(final Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(final WeatherResponse weatherResponse) {
                getWeather().postValue(weatherResponse);
            }

            @Override
            public void onError(final Throwable e) {
                errorOnFetchWeather().postValue(e);
            }
        });

    }

    public MutableLiveData<WeatherResponse> getWeather() {
        return weatherLiveData;
    }

    public MutableLiveData<Throwable> errorOnFetchWeather() {
        return errorLiveData;
    }

}

