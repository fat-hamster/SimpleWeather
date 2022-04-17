package com.dmgpersonal.simpleweather.viewmodel

import com.dmgpersonal.simpleweather.model.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
