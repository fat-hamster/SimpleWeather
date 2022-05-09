package com.dmgpersonal.simpleweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.simpleweather.model.*
import com.dmgpersonal.simpleweather.view.cities_list.CitiesListFragment
import java.lang.Thread.sleep
import kotlin.random.Random

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    fun getWeatherFromLocalSourceCity(city: City) {
        liveDataToObserve.value = AppState.Loading
        val cities: List<Weather>
        if (CitiesListFragment.isDataSetRus) {
            cities = getRussianCities()
        } else {
            cities = getWorldCities()
        }
        for (weather in cities) {
            if (weather.city == city) {
                liveDataToObserve.value = AppState.SuccessData(weather)
                return
            }
        }

        liveDataToObserve.value = AppState.SuccessData(Weather()) // default
    }

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)
    //fun getWeatherFromRemoteSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            when(Random.nextInt(0, 10)) {
                in 2..7 -> liveDataToObserve.postValue(AppState.SuccessList(if (isRussian)
                    repositoryImpl.getWeatherFromLocalStorageRus() else
                    repositoryImpl.getWeatherFromLocalStorageWorld()
                ))
                else -> liveDataToObserve.postValue(AppState.Error(Throwable()))
            }

        }.start()
    }
}