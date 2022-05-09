package com.dmgpersonal.simpleweather.model

interface Repository {
    fun getWeatherFromServer(city: City): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
    fun getWeatherFromLocalStorageCity(city: City): Weather
}