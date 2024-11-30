package com.example.meteoapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> = _weatherData

    private val _weeklyWeatherData = MutableLiveData<WeeklyWeatherResponse?>()
    val weeklyWeatherData: LiveData<WeeklyWeatherResponse?> = _weeklyWeatherData

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeather(apiKey, city)
                _weatherData.postValue(response)
                _error.postValue(null)
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weather: ${e.message}", e)
                _weatherData.postValue(null)
                _error.postValue("Error: ${e.message}")
            }
        }
    }

    fun fetchWeeklyWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeeklyWeather(apiKey, city)
                _weeklyWeatherData.postValue(response)
                _error.postValue(null)
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weekly weather: ${e.message}", e)
                _weeklyWeatherData.postValue(null)
                _error.postValue("Error: ${e.message}")
            }
        }
    }
}
