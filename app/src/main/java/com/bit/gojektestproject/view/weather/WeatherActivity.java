package com.bit.gojektestproject.view.weather;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.bit.gojektestproject.BaseActivity;
import com.bit.gojektestproject.R;
import com.bit.gojektestproject.util.AppTypeFace;
import com.bit.gojektestproject.util.Constants;
import com.bit.gojektestproject.view.weather.adapter.WeatherForecastAdapter;

public final class WeatherActivity extends BaseActivity {

    private WeatherViewModel weatherViewModel;
    private Location cLocation;
    private RecyclerView forecastRecyclerView;
    private View errorView;
    private View progressView;
    private View weatherView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        initView();
        weatherViewModel = new ViewModelProvider(this, new AndroidViewModelFactory(getApplication())).get(WeatherViewModel.class);
        addObserver();

        if (getIntent() != null) {
            if (getIntent().hasExtra(Constants.LOCATION_OBJ)) {
                cLocation = getIntent().getParcelableExtra(Constants.LOCATION_OBJ);
            }
        }

        fetchWeather();
    }

    private void initView() {
        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(800);
        rotate.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.iv_loading).startAnimation(rotate);


        progressView = findViewById(R.id.layout_progress_view);
        forecastRecyclerView = findViewById(R.id.rv_forecast);
        errorView = findViewById(R.id.layout_error_view);
        weatherView = findViewById(R.id.layout_weather_view);
        findViewById(R.id.btn_retry).setOnClickListener(v -> {
            fetchWeather();
        });
        ((TextView)findViewById(R.id.tv_message)).setTypeface(AppTypeFace.getInstance(getApplicationContext()).getRobotoThinTypeFace());
    }

    private void fetchWeather() {
        errorView.setVisibility(View.GONE);
        weatherView.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
        weatherViewModel.getWeatherDetail(cLocation);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_weather;
    }

    private void addObserver() {
        weatherViewModel.getWeather().observe(this, weatherResponse -> {
            errorView.setVisibility(View.GONE);
            progressView.setVisibility(View.GONE);


            TextView tv_cc_temp = findViewById(R.id.tv_current_city_temp);
            tv_cc_temp.setText(getString(R.string.temp_in_degree, weatherResponse.getCurrentLocationWeather().getTempC().toString()));
            tv_cc_temp.setTypeface(AppTypeFace.getInstance(getApplicationContext()).getRobotoBlackTypeface());

            TextView tv_cc_Name = findViewById(R.id.tv_current_city_name);
            tv_cc_Name.setText(weatherResponse.getCurrentLocation().getName());
            tv_cc_Name.setTypeface(AppTypeFace.getInstance(getApplicationContext()).getRobotoThinTypeFace());

            weatherView.setVisibility(View.VISIBLE);

            DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
            forecastRecyclerView.addItemDecoration(itemDecorator);
            WeatherForecastAdapter weatherForecastAdapter = new WeatherForecastAdapter(weatherResponse.getForecast().getForecastday());
            forecastRecyclerView.setAdapter(weatherForecastAdapter);




        });
        weatherViewModel.errorOnFetchWeather().observe(this, throwable -> {
            progressView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        });

    }

}
