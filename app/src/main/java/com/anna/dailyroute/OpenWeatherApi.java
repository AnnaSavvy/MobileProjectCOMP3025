package com.anna.dailyroute;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface OpenWeatherApi {
    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}