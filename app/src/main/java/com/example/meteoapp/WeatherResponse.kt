package com.example.meteoapp

data class WeatherResponse(
    val location: Location,
    val current: Current
) {
    data class Location(val name: String)
    data class Current(
        val temp_c: Double,
        val humidity: Int,
        val condition: Condition
    )
    data class Condition(val text: String)
}
