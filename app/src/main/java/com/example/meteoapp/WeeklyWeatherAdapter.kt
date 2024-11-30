package com.example.meteoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeeklyWeatherAdapter(
    private var forecastList: List<WeeklyWeatherResponse.ForecastDay>
) : RecyclerView.Adapter<WeeklyWeatherAdapter.WeeklyWeatherViewHolder>() {

    // ViewHolder to hold references to the views for each item
    class WeeklyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvTemp: TextView = itemView.findViewById(R.id.tvTemp)
        val tvCondition: TextView = itemView.findViewById(R.id.tvCondition)
        val weatherIcon: ImageView = itemView.findViewById(R.id.weatherIcon) // ImageView for weather icon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weekly_weather, parent, false)
        return WeeklyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeeklyWeatherViewHolder, position: Int) {
        val forecast = forecastList[position]

        // Set date, temperature, and condition
        holder.tvDate.text = forecast.date
        holder.tvTemp.text = "High: ${forecast.day.maxtemp_c}°C / Low: ${forecast.day.mintemp_c}°C"
        holder.tvCondition.text = forecast.day.condition.text

        // Set the weather icon based on condition text
        val weatherIconRes = getWeatherIcon(forecast.day.condition.text)
        holder.weatherIcon.setImageResource(weatherIconRes)
    }

    override fun getItemCount(): Int = forecastList.size

    // Method to update the data in the adapter
    fun updateData(newForecastList: List<WeeklyWeatherResponse.ForecastDay>) {
        forecastList = newForecastList
        notifyDataSetChanged()
    }

    // Helper function to map weather conditions to corresponding icon resource
    private fun getWeatherIcon(conditionText: String): Int {
        return when (conditionText.lowercase()) {
            "sunny" -> R.drawable.sunny
            "cloudy" -> R.drawable.cloudy
            "rainy" -> R.drawable.rainy
            "snowy" -> R.drawable.snow
            "stormy" -> R.drawable.stormy
            else -> R.drawable.pictures // Default icon for unspecified conditions
        }
    }
}
