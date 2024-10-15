package com.example.recipeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<WeatherRVModal> WeatherRVModalArrayList;

    public WeatherRVAdapter(Context context, ArrayList<WeatherRVModal> weatherRVModalArrayList) {
        this.context = context;
        WeatherRVModalArrayList = weatherRVModalArrayList;
    }


    @NonNull
    @Override
    public WeatherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.weather_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherRVAdapter.ViewHolder holder, int position) {

        WeatherRVModal modal = WeatherRVModalArrayList.get(position);
        Picasso.get().load("http:".concat(modal.getIcon())).into(holder.condition);

        holder.wind.setText(modal.getWindSpeed()+"km/h");
        holder.temperature.setText(modal.getTemperature()+"°C");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        try {
            java.util.Date t = input.parse(modal.getTime());
            holder.time.setText(output.format(t));
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    @Override
    public int getItemCount() {
        return WeatherRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time,temperature,wind;
        private ImageView condition;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            wind=itemView.findViewById(R.id.wind);
            temperature=itemView.findViewById(R.id.temperature);
            time=itemView.findViewById(R.id.time);
            condition=itemView.findViewById(R.id.condition);

        }
    }
}
