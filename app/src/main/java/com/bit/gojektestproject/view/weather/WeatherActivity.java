package com.bit.gojektestproject.view.weather;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import android.os.Bundle;

import com.bit.gojektestproject.BaseActivity;
import com.bit.gojektestproject.R;

public final class WeatherActivity extends BaseActivity {

    private WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherViewModel = new ViewModelProvider(this, new AndroidViewModelFactory(getApplication())).get(WeatherViewModel.class);
        addObserver();

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_weather;
    }

    @Override
    protected Activity getActivityInstance() {
        return this;
    }

    private void addObserver() {

    }

}
