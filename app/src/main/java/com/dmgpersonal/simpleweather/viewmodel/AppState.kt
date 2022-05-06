package com.dmgpersonal.simpleweather.viewmodel

import com.dmgpersonal.simpleweather.model.Weather

sealed class AppState {
    data class SuccessList(val weatherData: List<Weather>) : AppState()
    data class SuccessData(val weather: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
