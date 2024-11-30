package com.example.meteoapp

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): WeatherResponse

    @GET("forecast.json")
    suspend fun getWeeklyWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int = 7
    ): WeeklyWeatherResponse
}
