package com.dmgpersonal.simpleweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmgpersonal.simpleweather.model.Repository
import com.dmgpersonal.simpleweather.model.RepositoryImpl
import java.lang.Thread.sleep
import kotlin.random.Random

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    fun getWeatherFromLocalSource() = getDataFromLocalSource()
    fun getWeatherFromRemoteSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(2000)
            when(Random.nextInt(0, 10)) {
                4 -> liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorage()))
                else -> liveDataToObserve.postValue(AppState.Error(Throwable()))
            }

        }.start()
    }
}