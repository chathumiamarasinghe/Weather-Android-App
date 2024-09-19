package com.example.recipeapp;

import android.Manifest;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private RelativeLayout RLHome;
    private ProgressBar progressBar;
    private TextView CityName, Temp, Weather;
    private RecyclerView recyclerView;
    private TextInputEditText textEditLayout;
    private ImageView SearchIcon, WeatherIcon;
    private ArrayList<WeatherRVModal> WeatherRVModalArrayList;
    private WeatherRVAdapter weatherRVAdapter;
    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            cityName = getCityName(location.getLongitude(), location.getLatitude());
            getWeatherData(cityName);
        } else {
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
        }

        textEditLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = textEditLayout.getText().toString();
                if (city.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    CityName.setText(city);
                    getWeatherData(city);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private String getCityName(double longitude, double latitude) {
        String cityName = "Not Found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);
            for (Address adr : addresses) {
                if (adr != null) {
                    String city = adr.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                    } else {
                        Log.d("TAG", "CITY NOT FOUND");
                        Toast.makeText(this, "CITY NOT FOUND", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityName;
    }

    private void getWeatherData(String cityName) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=ebedc33555f545b697522459241509&q=" + cityName + "&days=1&aqi=yes&alerts=yes";
        CityName.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                RLHome.setVisibility(View.VISIBLE);
                RLHome.setVisibility(View.VISIBLE);
                WeatherRVModalArrayList.clear();
                try {
                    String temp = response.getJSONObject("current").getString("temp_c");
                    Temp.setText(temp + "°C");
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionIcon)).into(WeatherIcon);
                    Weather.setText(condition);

                    if (isDay == 1) {
                        Picasso.get().load("https://media.istockphoto.com/id/1007768414/photo/blue-sky-with-bright-sun-and-clouds.jpg?s=612x612&w=0&k=20&c=MGd2-v42lNF7Ie6TtsYoKnohdCfOPFSPQt5XOz4uOy4=").into(WeatherIcon);
                    } else {
                        Picasso.get().load("https://media.istockphoto.com/id/532378051/photo/night-sky-with-stars-and-clouds.jpg?s=612x612&w=0&k=20&c=M6HA8A8tq5cbJsAU0y39Qx6dTyoIti-CaYMi9WWbB_U=").into(WeatherIcon);
                    }

                    JSONObject forecastObj = response.getJSONObject("forecast");
                    JSONObject forecast0 = forecastObj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray = forecast0.getJSONArray("hour");

                    for (int i = 0; i < hourArray.length(); i++) {
                        JSONObject hourObj = hourArray.getJSONObject(i);
                        String time = hourObj.getString("time");
                        String temperature = hourObj.getString("temp_c");
                        String wind = hourObj.getString("wind_kph");
                        String icon = hourObj.getJSONObject("condition").getString("icon");
                        WeatherRVModalArrayList.add(new WeatherRVModal(time, temperature, wind, icon));
                    }
                    weatherRVAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
