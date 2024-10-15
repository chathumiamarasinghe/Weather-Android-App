package com.example.recipeapp;

public class WeatherRVModal {

    public String time;
    public String temperature;
    public String condition;
    public String wind;

    public WeatherRVModal(String time, String temperature, String condition, String wind) {
        this.time = time;
        this.temperature = temperature;
        this.condition = condition;
        this.wind = wind;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWindSpeed() {
        return wind;
    }

    public void setWindSpeed(String wind) {
        this.wind = wind;
    }

    public String getIcon() {
        return condition;
    }

    public void setIcon(String condition) {
        this.condition = condition;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

}
