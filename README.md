# Weather App

An Android weather application that fetches and displays real-time weather data for a given city using the [WeatherAPI](https://www.weatherapi.com/).

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Usage](#usage)
- [APIs Used](#apis-used)


## Features
- Search weather by city name
- Displays current temperature in Celsius
- Shows weather condition with icons (e.g., sunny, cloudy)
- Displays additional weather data like wind speed
- User-friendly UI with real-time data display

## Requirements
- Android Studio (Latest version recommended)
- Android SDK
- [WeatherAPI](https://www.weatherapi.com/) API Key
- Internet Connection

## Usage

1. On the main screen, enter the name of the city you want to get the weather information for in the search bar.
2. Press the search icon to fetch the current weather data.
3. The temperature, weather condition, and an appropriate weather icon will be displayed.
4. Scroll down to see additional information such as wind speed and the weather forecast.

## APIs Used
This project uses the [WeatherAPI](https://www.weatherapi.com/) to fetch weather data, including current temperature, condition, and forecast.

## Screenshots

### Example Screenshot

![Example Screenshot](https://github.com/chathumiamarasinghe/Weather-Android-App/blob/main/images/WhatsApp%20Image%202024-09-19%20at%2018.41.43.jpeg)



### Sample API Request
The app makes a GET request to the WeatherAPI to fetch weather data:

```http
GET https://api.weatherapi.com/v1/current.json?key=YOUR_API_KEY&q=Colombo
