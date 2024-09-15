package com.example.recipeapp;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RelativeLayout RLHome;
    private ProgressBar progressBar;
    private TextView CityName, Temp, Weather;
    private RecyclerView recyclerView;
    private TextInputEditText textEditLayout;
    private ImageView blackShade,SearchIcon,WeatherIcon;
    private ArrayList WeatherRVModalArrayList;
    private WeatherRVAdapter weatherRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_home);

        RLHome = findViewById(R.id.RLHome);
        progressBar = findViewById(R.id.progressBar);
        CityName = findViewById(R.id.CityName);
        Temp = findViewById(R.id.Temp);
        Weather = findViewById(R.id.Weather);
        recyclerView = findViewById(R.id.recyclerView);
        textEditLayout = findViewById(R.id.textEditLayout);
        SearchIcon = findViewById(R.id.SearchIcon);
        WeatherIcon = findViewById(R.id.WeatherIcon);
        WeatherRVModalArrayList = new ArrayList<>();
        weatherRVAdapter = new WeatherRVAdapter(this, WeatherRVModalArrayList);
        recyclerView.setAdapter(weatherRVAdapter);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getWeatherData(String cityName) {
        String url="http://api.weatherapi.com/v1/forecast.json?key=ebedc33555f545b697522459241509&q="+cityName+"&days=1&aqi=yes&alerts=yes";
    }
}