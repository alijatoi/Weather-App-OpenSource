package com.example.weatherappusingopenmeteo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weatherappusingopenmeteo.data.local.model.DailyWeatherEntity
import com.example.weatherappusingopenmeteo.data.local.model.HourlyWeatherEntity
import com.example.weatherappusingopenmeteo.presentation.viewholders.DailyViewHolder
import com.example.weatherappusingopenmeteo.presentation.viewholders.HourlyViewHolder
import javax.inject.Inject

class  WeatherAdapter @Inject constructor() : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_1 = 1
        private const val VIEW_TYPE_2 = 2
    }

    private val hourlyWeather = mutableListOf<HourlyWeatherEntity?>()
    private val dailyWeather = mutableListOf<DailyWeatherEntity?>()

    fun setHourly(data: List<HourlyWeatherEntity?  >) {
        val diffResult = DiffUtil.calculateDiff(HourlyDiffCallback(hourlyWeather, data))
        hourlyWeather.clear()
        hourlyWeather.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }
    fun setDaily(data: List<DailyWeatherEntity?>) {
        val diffResult = DiffUtil.calculateDiff(
            DailyDiffCallback(dailyWeather, data)
        )
        dailyWeather.clear()
        dailyWeather.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType) {
            VIEW_TYPE_1 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_item,parent,false)
                HourlyViewHolder(view)
            }
            VIEW_TYPE_2 ->
            {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_item2,parent,false)
                DailyViewHolder(view)
            }

        else -> throw IllegalArgumentException("Invalid view type")
    }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(hourlyWeather.isNotEmpty()) {

            when (holder.itemViewType) {
                VIEW_TYPE_1 -> {
                    val item = hourlyWeather[position]
                    val viewHolder = holder as HourlyViewHolder
                    viewHolder.bind(item)
                }
                VIEW_TYPE_2 -> {
                    val item = dailyWeather[position % 4]
                    val viewHolder = holder as DailyViewHolder
                    viewHolder.bind(item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return hourlyWeather.size + dailyWeather.size
    }

    override fun getItemViewType(position: Int) =
        if (position < 4) VIEW_TYPE_1 else VIEW_TYPE_2
}

class HourlyDiffCallback(
    private val oldList: MutableList<HourlyWeatherEntity?>,
    private val newList: List<HourlyWeatherEntity?>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition]!!.time == newList[newItemPosition]!!.time
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}
class DailyDiffCallback(
    private val oldList: MutableList<DailyWeatherEntity?>,
    private val newList: List<DailyWeatherEntity?>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition]!!.time == newList[newItemPosition]!!.time
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}

