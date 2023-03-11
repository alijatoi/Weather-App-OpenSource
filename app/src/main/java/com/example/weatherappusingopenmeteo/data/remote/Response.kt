package com.example.weatherappusingopenmeteo.data.remote

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Exception) : ApiResponse<Nothing>()
}

sealed class WeatherLoadingState {
    object Error : WeatherLoadingState()
}
