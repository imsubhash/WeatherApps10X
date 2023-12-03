package com.example.weatherapp10x.repository

import com.example.weatherapp10x.api.ApiService
import javax.inject.Inject

class WeatherRepository

@Inject
constructor(private val apiService: ApiService) {
    private val location = "Bengaluru"
    private val apiKey = "9b8cb8c7f11c077f8c4e217974d9ee40"

    suspend fun getCurrentWeather() = apiService.getCurrentWeather(location, apiKey)
    suspend fun getWeatherForecast() = apiService.getWeatherForecast(location, apiKey)
}