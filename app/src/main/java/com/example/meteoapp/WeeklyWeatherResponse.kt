package com.example.meteoapp

data class WeeklyWeatherResponse(
    val location: Location,
    val forecast: Forecast
) {
    data class Location(val name: String)
    data class Forecast(val forecastday: List<ForecastDay>)
    data class ForecastDay(
        val date: String,
        val day: Day
    )
    data class Day(
        val avgtemp_c: Double,   // Average temperature
        val maxtemp_c: Double,   // Max temperature
        val mintemp_c: Double,   // Min temperature
        val condition: Condition
    )
    data class Condition(val text: String, val icon: String)
}
