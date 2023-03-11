package com.example.weatherappusingopenmeteo.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappusingopenmeteo.R
import com.example.weatherappusingopenmeteo.data.local.model.HourlyWeatherEntity

class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val hourlyTime : TextView = itemView.findViewById(R.id.HourlytimeText)
    val hourlyTemperature : TextView = itemView.findViewById(R.id.Hourlytemperature)
    val hourlyWeatherCode : ImageView = itemView.findViewById(R.id.HourlyweatherCode)
    val hourlyWeatherDescription: TextView = itemView.findViewById(R.id.HourlyweatherDescription)

    fun bind(hourlyWeather: HourlyWeatherEntity?) {
        hourlyTime.text = hourlyWeather?.time ?: ""
        hourlyTemperature.text = "${hourlyWeather?.temperature2m} °C"
        val weatherType = hourlyWeather?.let { WeatherType.fromWMO(it.weathercode) }
        hourlyWeatherCode.setImageResource(weatherType?.iconRes ?: 0)
        hourlyWeatherDescription.text = weatherType?.weatherDesc ?: ""
    }

}
