package com.example.meteoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var progressBar: ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        val etCityName: EditText = findViewById(R.id.etCityName)
        val btnFetchWeather: Button = findViewById(R.id.btnFetchWeather)
        val rvWeeklyWeather: RecyclerView = findViewById(R.id.rvWeeklyWeather)
        progressBar = findViewById(R.id.progressBar)

        val tvCity: TextView = findViewById(R.id.tvCity)
        val tvCurrentTemperature: TextView = findViewById(R.id.tvCurrentTemperature)
        val tvWeatherCondition: TextView = findViewById(R.id.tvWeatherCondition)
        val tvHumidityWind: TextView = findViewById(R.id.tvHumidityWind)

        // Add TextView for the date
        val tvDate: TextView = findViewById(R.id.tvDate)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        // Set current date in tvDate TextView
        tvDate.text = "Today, ${getCurrentDate()}"

        // Set up RecyclerView
        val weeklyWeatherAdapter = WeeklyWeatherAdapter(emptyList())
        rvWeeklyWeather.adapter = weeklyWeatherAdapter
        rvWeeklyWeather.layoutManager = LinearLayoutManager(this)

        // Observe current weather data
        viewModel.weatherData.observe(this) { weather: WeatherResponse? ->
            if (weather != null) {
                val city = weather.location.name
                val temperature = weather.current.temp_c
                val humidity = weather.current.humidity
                val condition = weather.current.condition.text

                // Update Current Weather Card with data
                tvCity.text = "City: $city"
                tvCurrentTemperature.text = "Temp: $temperature°C"
                tvWeatherCondition.text = "Condition: $condition"
                tvHumidityWind.text = "Humidity: $humidity%"
            } else {
                Toast.makeText(this, "No weather data available.", Toast.LENGTH_SHORT).show()
            }
            progressBar.visibility = View.GONE
        }

        // Observe weekly weather data
        viewModel.weeklyWeatherData.observe(this) { weeklyWeather ->
            if (weeklyWeather != null) {
                rvWeeklyWeather.visibility = View.VISIBLE
                weeklyWeatherAdapter.updateData(weeklyWeather.forecast.forecastday)
            } else {
                rvWeeklyWeather.visibility = View.GONE
                Toast.makeText(this, "No weekly weather data available.", Toast.LENGTH_SHORT).show()
            }
            progressBar.visibility = View.GONE
        }

        // Observe errors
        viewModel.error.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
            }
            progressBar.visibility = View.GONE
        }

        // Button click listener
        btnFetchWeather.setOnClickListener {
            val city = etCityName.text.toString().trim()
            if (city.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                viewModel.fetchWeather(city, "11b55a8fdd294ca4bd0112805242811")
                viewModel.fetchWeeklyWeather(city, "11b55a8fdd294ca4bd0112805242811")
            } else {
                Toast.makeText(this, "Enter a valid city name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to get the current date in the format "3 May"
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("d MMM", Locale.getDefault()) // "d MMM" formats like "3 May"
        val date = Date() // Current date
        return dateFormat.format(date)
    }
}
