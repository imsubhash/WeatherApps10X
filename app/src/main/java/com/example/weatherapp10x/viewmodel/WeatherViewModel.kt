package com.example.weatherapp10x.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp10x.model.response.ForecastResponse
import com.example.weatherapp10x.model.response.WeatherResponse
import com.example.weatherapp10x.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherResponse = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse>
        get() = _weatherResponse


    private val _forecastResponse = MutableLiveData<ForecastResponse>()
    val forecastResponse: LiveData<ForecastResponse>
        get() = _forecastResponse

    private val _loading = MutableLiveData<Boolean>()
    val loading get() = _loading

    init {
        getCurrentWeather()
        getWeatherForecast()
    }

    private fun getCurrentWeather() = viewModelScope.launch {
        repository.getCurrentWeather().let { response ->
            _loading.postValue(true)
            if (response.isSuccessful) {
                _loading.postValue(false)
                _weatherResponse.postValue(response.body())
            } else {
                Log.d("tag", "getWeather Error: ${response.code()}")
            }
        }
    }

    private fun getWeatherForecast() = viewModelScope.launch {
        repository.getWeatherForecast().let { response ->
            if (response.isSuccessful) {
                _forecastResponse.postValue(response.body())
            } else {
                Log.d("tag", "getWeatherForecast Error: ${response.code()}")
            }
        }
    }

}