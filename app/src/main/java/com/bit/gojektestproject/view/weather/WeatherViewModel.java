package com.bit.gojektestproject.view.weather;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.bit.gojektestproject.data.server.NetworkClient;
import com.bit.gojektestproject.data.server.service.WeatherServiceApi;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public final class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<Object> weatherLiveData = new MutableLiveData();
    private CompositeDisposable compositeDisposable;
    private Application application;
    private int offset = 0;
    private static final int LIMIT = 20;
    private WeatherServiceApi weatherServiceApi;

    public WeatherViewModel(@NonNull final Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
      //  weatherServiceApi = new NetworkClient().getRetrofitClientInstance("BuildConfig.HOST_URL").create(WeatherServiceApi.class);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

    private void downloadFromServer() {
        weatherServiceApi.getPokemonList(offset, LIMIT).subscribeOn(Schedulers.io());
    }

    public MutableLiveData<Object> getWeather() {
        return weatherLiveData;
    }

}

