package com.example.weatherapp10x.api

import com.example.weatherapp10x.model.response.ForecastResponse
import com.example.weatherapp10x.model.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("APPID") apiKey: String
    ): Response<WeatherResponse>

    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("q") location: String,
        @Query("APPID") apiKey: String
    ): Response<ForecastResponse>
}