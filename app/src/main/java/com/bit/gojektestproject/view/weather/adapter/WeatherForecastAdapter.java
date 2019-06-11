package com.bit.gojektestproject.view.weather.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bit.gojektestproject.R;
import com.bit.gojektestproject.data.server.model.ForecastDay;
import com.bit.gojektestproject.util.AppTypeFace;
import com.bit.gojektestproject.view.weather.adapter.WeatherForecastAdapter.WeatherForecastViewHolder;

import java.util.List;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastViewHolder> {

    private List<ForecastDay> forecastDay;

    public WeatherForecastAdapter(final List<ForecastDay> forecastDay) {
        this.forecastDay = forecastDay;
    }

    @NonNull
    @Override
    public WeatherForecastViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forcast_item_layout, parent, false);
        return new WeatherForecastViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final WeatherForecastViewHolder weatherForecastViewHolder, final int position) {
        ForecastDay obj = forecastDay.get(position);
        if (obj.getDay().getAvgtempC() != null) {
            weatherForecastViewHolder.tvTemperature.setText(obj.getDay().getAvgtempC().toString()+" C");
        }
        weatherForecastViewHolder.tvDay.setText(obj.getDayOfWeek());
    }


    @Override
    public int getItemCount() {
        return forecastDay.size();
    }


    public static class WeatherForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDay;
        private TextView tvTemperature;


        public WeatherForecastViewHolder(View v) {
            super(v);
            tvDay = v.findViewById(R.id.tv_day);
            tvTemperature = v.findViewById(R.id.tv_temperature);
            tvDay.setTypeface(AppTypeFace.getInstance(v.getContext()).getRobotoRegularTypeface());
            tvDay.setTypeface(AppTypeFace.getInstance(v.getContext()).getRobotoRegularTypeface());
        }
    }

}