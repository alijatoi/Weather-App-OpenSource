package com.example.weatherappusingopenmeteo.presentation.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappusingopenmeteo.R
import com.example.weatherappusingopenmeteo.domain.model.HourlyWeatherEntity
import com.example.weatherappusingopenmeteo.domain.utils.WeatherType

class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val hourlyTime : TextView = itemView.findViewById(R.id.HourlytimeText)
    private val hourlyTemperature = itemView.findViewById<TextView>(R.id.Hourlytemperature)
    private val hourlyWeatherCode : ImageView = itemView.findViewById(R.id.HourlyweatherCode)
    private val hourlyWeatherDescription: TextView = itemView.findViewById(R.id.HourlyweatherDescription)

    fun bind(hourlyWeather: HourlyWeatherEntity?) {
        hourlyTime.text = hourlyWeather?.time ?: ""
        hourlyTemperature.text = "${hourlyWeather?.temperature2m} °C"
        val weatherType = hourlyWeather?.let { WeatherType.fromWMO(it.weathercode) }
        hourlyWeatherCode.setImageResource(weatherType?.iconRes ?: 0)
        hourlyWeatherDescription.text = weatherType?.weatherDesc ?: ""
    }

}
