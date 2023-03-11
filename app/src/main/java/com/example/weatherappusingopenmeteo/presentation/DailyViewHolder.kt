package com.example.weatherappusingopenmeteo.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappusingopenmeteo.R
import com.example.weatherappusingopenmeteo.data.local.model.DailyWeatherEntity

class DailyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val datetime : TextView = itemView.findViewById(R.id.DailytimeText)
    val maxTemperature : TextView = itemView.findViewById(R.id.DailymaxTemperature)
    val minTemperature : TextView = itemView.findViewById(R.id.DailyminTemperature)
    val dailyImage : ImageView = itemView.findViewById(R.id.DailyweatherCode)

   fun bind(dailyWeather: DailyWeatherEntity?) {
        maxTemperature.text = "${dailyWeather?.temperature2mMax} °C"
        minTemperature.text = "${dailyWeather?.temperature2mMin} °C"
        datetime.text = dailyWeather?.time ?: ""
        val weatherType = WeatherType.fromWMO(dailyWeather?.weatheCode ?: 0)
        dailyImage.setImageResource(weatherType.iconRes)
    }

}