package com.example.weatherapp10x.model.response

data class ForecastResponse(
    val list: List<Forecast>
)

data class Forecast(
    val main: Main,
    val dt_txt: String
)
