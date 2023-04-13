package com.example.weatherappusingopenmeteo.data.local.model


import com.google.gson.annotations.SerializedName

class CityData : ArrayList<CityData.CityDataItem>(){
    data class CityDataItem(
        @SerializedName("country")
        val country: String,
        @SerializedName("is_capital")
        val isCapital: Boolean,
        @SerializedName("latitude")
        val latitude: Double,
        @SerializedName("longitude")
        val longitude: Double,
        @SerializedName("name")
        val name: String,
        @SerializedName("population")
        val population: Int
    )
}